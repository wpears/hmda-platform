package hmda.dataBrowser.repositories

import hmda.dataBrowser.models.{ FilerInstitutionResponse2017, FilerInstitutionResponse2018, QueryField, Statistic }
import monix.eval.Task

trait Cache {
  def find(queryFields: List[QueryField]): Task[Option[Statistic]]

  def findFilers2018(queryFields: List[QueryField]): Task[Option[FilerInstitutionResponse2018]]

  def findFilers2017(queryFields: List[QueryField]): Task[Option[FilerInstitutionResponse2017]]

  def update(queryFields: List[QueryField], statistic: Statistic): Task[Statistic]

  def updateFilers2017(queryFields: List[QueryField], filerInstitutionResponse: FilerInstitutionResponse2017): Task[FilerInstitutionResponse2017]

  def updateFilers2018(queryFields: List[QueryField], filerInstitutionResponse: FilerInstitutionResponse2018): Task[FilerInstitutionResponse2018]

  def invalidate(queryField: List[QueryField]): Task[Unit]
}
