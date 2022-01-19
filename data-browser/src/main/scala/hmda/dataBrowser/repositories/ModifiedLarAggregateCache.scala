package hmda.dataBrowser.repositories

import hmda.dataBrowser.models.{ FilerInstitutionResponse2017, FilerInstitutionResponseLatest, QueryField, Statistic }
import monix.eval.Task

trait Cache {
  def find(queryFields: List[QueryField], year: Int): Task[Option[Statistic]]

  def findFilers2018(queryFields: List[QueryField], year: Int): Task[Option[FilerInstitutionResponseLatest]]

  def findFilers2017(queryFields: List[QueryField], year: Int): Task[Option[FilerInstitutionResponse2017]]

  def update(queryFields: List[QueryField], year: Int, statistic: Statistic): Task[Statistic]

  def updateFilers2017(
    queryFields: List[QueryField],
    year: Int,
    filerInstitutionResponse: FilerInstitutionResponse2017
  ): Task[FilerInstitutionResponse2017]

  def updateFilers2018(
    queryFields: List[QueryField],
    year: Int,
    filerInstitutionResponse: FilerInstitutionResponseLatest
  ): Task[FilerInstitutionResponseLatest]

  def invalidate(queryField: List[QueryField], year: Int): Task[Unit]
}