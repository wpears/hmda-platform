package hmda.persistence.demo

import hmda.model.ResourceUtils
import hmda.model.fi._

object DemoFilings extends ResourceUtils {

  val values: Seq[Filing] = {
    val lines = resourceLines("/demoFilings.csv")

    lines.map { line =>
      val values = line.split('|').map(_.trim)
      val period = values(0)
      val institutionId = values(1)
      val filingStatus = toFilingStatus(values(2))

      Filing(
        period,
        institutionId,
        filingStatus
      )
    }.toSeq
  }

  def toFilingStatus(text: String): FilingStatus = {
    text match {
      case "not-started" => NotStarted
      case "in-progress" => InProgress
      case "completed" => Completed
      case "cancelled" => Cancelled
    }
  }

}
