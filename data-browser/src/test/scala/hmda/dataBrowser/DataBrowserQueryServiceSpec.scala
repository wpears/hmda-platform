package hmda.dataBrowser

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.testkit.TestKit
import hmda.dataBrowser.models.{ Aggregation, FieldInfo, ModifiedLarEntity, QueryField, QueryFields, Statistic }
import hmda.dataBrowser.repositories._
import hmda.dataBrowser.services.DataBrowserQueryService
import monix.eval.Task
import monix.execution.ExecutionModel
import monix.execution.schedulers.TestScheduler
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{ Matchers, WordSpecLike }

class DataBrowserQueryServiceSpec
  extends TestKit(ActorSystem("data-browser-query-service-spec"))
    with WordSpecLike
    with MockFactory
    with ScalaFutures
    with Matchers {
  implicit val mat: ActorMaterializer   = ActorMaterializer()
  implicit val scheduler: TestScheduler = TestScheduler(ExecutionModel.SynchronousExecution)

  val cache: Cache                = mock[Cache]
  val repo: ModifiedLarRepository2018 = mock[ModifiedLarRepository2018]
  val repo2017: ModifiedLarRepository2017 = mock[ModifiedLarRepository2017]
  val service                     = new DataBrowserQueryService(repo, repo2017, cache)

  "DataBrowserQueryService" must {
    "call fetchData without using the cache" in {
      val expected = sampleMlar
      (repo.find _).expects(*).returns(Source.single(expected))
      val source = service.fetchData(QueryFields("2018", Nil))
      val futRes = source.runWith(Sink.head)

      whenReady(futRes) { res =>
        (cache.find _).expects(*).never()
        (cache.findFilers _).expects(*).never()
        res shouldBe expected
      }
    }

    "permuteQueryFields should generate all permutations of the provided QueryFields" in {
      val q1     = QueryField(name = "one", values = List("x", "y"))
      val q2     = QueryField(name = "two", values = List("a", "b"))
      val actual = service.permuteQueryFields(q1 :: q2 :: Nil)
      val expected = List(
        List(
          QueryField("one", List("x")),
          QueryField("two", List("a"))
        ),
        List(
          QueryField("one", List("x")),
          QueryField("two", List("b"))
        ),
        List(
          QueryField("one", List("y")),
          QueryField("two", List("a"))
        ),
        List(
          QueryField("one", List("y")),
          QueryField("two", List("b"))
        )
      )
      actual should contain theSameElementsAs expected
    }


    def sampleMlar = ModifiedLarEntity(
      filingYear = 2019,
      lei = "EXAMPLELEI",
      msaMd = 1,
      state = "STATE",
      county = "COUNTY",
      tract = "TRACT",
      conformingLoanLimit = "LOANLIMIT",
      loanProductType = "LOANTYPE",
      dwellingCategory = "DWELLINGCAT",
      ethnicityCategorization = "ETHNICITYCAT",
      raceCategorization = "RACECAT",
      sexCategorization = "SEXCATEG",
      actionTakenType = 1,
      purchaserType = 1,
      preapproval = 1,
      loanType = 1,
      loanPurpose = 1,
      lienStatus = 1,
      reverseMortgage = 1,
      lineOfCredits = 1,
      businessOrCommercial = 100,
      loanAmount = 1,
      loanValueRatio = "2.4%",
      interestRate = "RATESPREAD",
      rateSpread = "1",
      hoepaStatus = 100,
      totalLoanCosts = "100",
      totalPoints = "ORIGINATIONCHARGES",
      originationCharges = "1",
      discountPoints = "lendercredits",
      lenderCredits = "loanterm",
      loanTerm = "penalty",
      paymentPenalty = "ratespread",
      rateSpreadIntro = "1",
      amortization = 1,
      insertOnlyPayment = 1,
      baloonPayment = 1,
      otherAmortization = 1,
      propertyValue = "cm",
      constructionMethod = "1",
      occupancyType = 1,
      homeSecurityPolicy = 1,
      landPropertyInterest = 1,
      totalUnits = "1",
      mfAffordable = "1",
      income = "1",
      debtToIncome = "1",
      creditScoreTypeApplicant = 1,
      creditScoreTypeCoApplicant = 1,
      ethnicityApplicant1 = "1",
      ethnicityApplicant2 = "1",
      ethnicityApplicant3 = "1",
      ethnicityApplicant4 = "1",
      ethnicityApplicant5 = "1",
      ethnicityCoApplicant1 = "1",
      ethnicityCoApplicant2 = "1",
      ethnicityCoApplicant3 = "1",
      ethnicityCoApplicant4 = "1",
      ethnicityCoApplicant5 = "1",
      ethnicityObservedApplicant = "1",
      ethnicityObservedCoApplicant = "1",
      raceApplicant1 = "1",
      raceApplicant2 = "1",
      raceApplicant3 = "1",
      raceApplicant4 = "1",
      raceApplicant5 = "1",
      rateCoApplicant1 = "1",
      rateCoApplicant2 = "1",
      rateCoApplicant3 = "1",
      rateCoApplicant4 = "1",
      rateCoApplicant5 = "2",
      raceObservedApplicant = 2,
      raceObservedCoApplicant = 2,
      sexApplicant = 2,
      sexCoApplicant = 2,
      observedSexApplicant = 2,
      observedSexCoApplicant = 1,
      ageApplicant = "1",
      ageCoApplicant = "1",
      applicantAgeGreaterThan62 = "1",
      coapplicantAgeGreaterThan62 = "1",
      applicationSubmission = 1,
      payable = 1,
      aus1 = "1",
      aus2 = "1",
      aus3 = "1",
      aus4 = "1",
      aus5 = "1",
      denialReason1 = "1",
      denialReason2 = "1",
      denialReason3 = "1",
      denialReason4 = "1",
      population = "1",
      minorityPopulationPercent = "1",
      ffiecMedFamIncome = "1",
      medianIncomePercentage = "1",
      ownerOccupiedUnits = "1",
      oneToFourFamUnits = "1",
      medianAge = 1
    )
  }
}