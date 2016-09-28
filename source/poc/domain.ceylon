import ceylon.collection {
	MutableList,
	ArrayList
}

Contrat createContrat(String reference, String label) {
	if (String.empty(reference)) {
		throw Exception("Impossible de creer un contrat sans reference");
	}
	Contrat c = Contrat.empty();
	c.addUncommitedEvents(CreatedContratEvent(reference, label));
	c.addUncommitedEvents(RelabeledContratEvent(reference, label));
	return c;
}

class Contrat {
	shared variable String reference = "";
	shared variable String label = "";
	shared MutableList<Event> uncommitedEvents = ArrayList<Event>();
	
	shared new empty() {
	}
	
	shared new (String reference, String label) {
		this.reference = reference;
		this.label = label;
	}
	
	shared void applyCreatedContratEvent(CreatedContratEvent evt) {
		this.reference = evt.reference;
		this.label = evt.label;
	}
	
	shared void applyRelabeledContratEvent(RelabeledContratEvent evt) {
		this.label = evt.label;
	}
	
	shared void addUncommitedEvents(Event evt) {
		switch (evt)
		case (is CreatedContratEvent) {
			this.applyCreatedContratEvent(evt);
		}
		case (is RelabeledContratEvent) {
			this.applyRelabeledContratEvent(evt);
		}
		else {}
		
		uncommitedEvents.add(evt);
	}
}

interface Event {
	shared formal String category;
}
class CreatedContratEvent(shared String reference, shared String label) satisfies Event {
	shared actual String category => "CONTRAT";
}
class RelabeledContratEvent(shared String contratId, shared String label) satisfies Event {
	shared actual String category => "CONTRAT";
}
