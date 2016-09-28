import ceylon.collection {
	ArrayList,
	MutableList,
	MutableMap,
	HashMap
}

class ContratRepository(EventStore store) {
	MutableMap<String,Contrat> contrats = HashMap<String,Contrat>();
	
	for (evt in store.eventsByCategory("CONTRAT")) {
		print("Alimentation DB in memory (write side)");
		switch (evt)
		case (is CreatedContratEvent) {
			Contrat c = Contrat.empty();
			c.applyCreatedContratEvent(evt);
			contrats.put(c.reference, c);
		}
		case (is RelabeledContratEvent) {
			Contrat? c = contrats.get(evt.contratId);
			if (exists c) {
				c.applyRelabeledContratEvent(evt);
				contrats.put(c.reference, c);
			}
		}
		else {}
	}
	
	shared void create(Contrat newlyContrat) {
		//contrats.put(newlyContrat.reference, newlyContrat);
		store.publish(newlyContrat.uncommitedEvents);
	}
	
	shared void updateLabel(String contratId, String label) {
	}
}

class EventStore() {
	MutableList<Event> events = ArrayList<Event>();
	
	shared {Event*} eventsByCategory(String category) {
		return events.filter((evt) => evt.category.equals(category));
	}
	shared void publish({Event*} evts) {
		events.addAll(evts);
	}
}
