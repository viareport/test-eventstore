package write.contrats

import write.Event

/**
 * Created 28/09/16.
 */

class CreatedContratEvent(val reference: String, val label: String) : Event("CREATED_CONTRAT_EVENT") {
    override val data: Map<String, Any>
        get() = mapOf(Pair("reference",reference),Pair("label",label))
}