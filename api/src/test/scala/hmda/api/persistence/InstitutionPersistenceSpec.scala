package hmda.api.persistence

import akka.testkit.TestProbe
import hmda.api.persistence.CommonMessages.GetState
import hmda.api.processing.ActorSpec
import hmda.api.persistence.InstitutionPersistence._
import hmda.api.util.TestData

class InstitutionPersistenceSpec extends ActorSpec {

  val institutionsActor = createInstitutionsFiling(system)

  val probe = TestProbe()

  "Institution Filings" must {
    "be created and read back" in {
      val institutions = TestData.institutions
      for (institution <- institutions) {
        probe.send(institutionsActor, CreateInstitution(institution))
      }
      probe.send(institutionsActor, GetState)
      probe.expectMsg(institutions)
    }
    //    "be created, modified and read back" in {
    //      val institution = TestData.institutions.head
    //      probe.send(institutionActor, CreateInstitution(institution))
    //      val modified = institution.copy(name = "new name")
    //      probe.send(institutionActor, ModifyInstitution(modified))
    //      probe.send(institutionActor, GetInstitutionByIdAndPeriod(modified.id, modified.period))
    //      probe.expectMsg(modified)
    //    }
  }

}
