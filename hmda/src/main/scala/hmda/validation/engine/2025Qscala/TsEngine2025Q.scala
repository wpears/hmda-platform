package hmda.validation.engine

import hmda.model.filing.ts.TransmittalSheet
import hmda.validation.context.ValidationContext
import hmda.validation.rules.ts.validity._
import hmda.validation.engine.TsEngine2025

// $COVERAGE-OFF$
private[engine] object TsEngine2025Q extends ValidationEngine[TransmittalSheet] {

  override def syntacticalChecks(ctx: ValidationContext) = TsEngine2025.syntacticalChecks(ctx)

  override def validityChecks(ctx: ValidationContext) = TsEngine2025.validityChecks(ctx).diff((Vector(V718.withContext(ctx)))) //++ Vector(V602)

}
// $COVERAGE-ON$