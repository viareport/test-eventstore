package write.contrats

import write.Event

/**
 * Created 28/09/16.
 */

class Contrat {
    var reference: String? = ""
    var label: String? = ""
    val events: List<Event>

    constructor(reference:String, label:String) {
        val createdContratEvent = CreatedContratEvent(reference, label)
        this.applyChange(createdContratEvent)
        events = listOf(createdContratEvent)
    }

    private fun  applyChange(createdContratEvent: CreatedContratEvent) {
        this.reference = createdContratEvent.reference
        this.label = createdContratEvent.label
    }

}