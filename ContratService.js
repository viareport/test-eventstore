var EventStore = require('event-store-client');
var Contrat = require('./Contrat');
const contrats = {};

var config = {
    'eventStore': {
        'address': "192.168.1.30",
        'port': 1113,

        'credentials': {
            'username': "admin",
            'password': "changeit"
        }
    },
    'debug': true,
    onConnect: function () {
        eventClose = false;
    },
    onClose: function () {
        eventClose = true;
    }
};

function onLoadedContrat(evt) {
	console.log("onLoadedContrat > " + evt.streamId, evt);
	switch (evt.eventType) {
		case "OpenedContrat": 
			contrats[evt.streamId] = Contrat.create(evt.data);
			break;
		case "LabelChangedContrat":
			contrats[evt.streamId].applyChangedLabel(evt.data.label);
			break;
	}
}

class ContratService {

	constructor(eventStore, callback) {
		this.eventStore = eventStore;

		var ref = "ref1";
		var streamId = "contrat-" + ref;
		var correlationId = eventStore.subscribeToStream(streamId, true, function(evt) {
	    	onLoadedContrat(evt);
}, null, null, config.credentials, null);
		this.eventStore.readStreamEventsForward(streamId, 0, 42, 
			false, false, function(evt){ onLoadedContrat(evt) }, config.credentials, callback);
	}

	getByReference(reference) {
		return contrats["contrat-"+reference] || null;
	}

	openContrat(contrat) {
		if(!contrats["contrat-"+contrat.reference]) {
			//contrats[contrat.reference] = contrat;
			var newEvent = {
			    eventId: EventStore.Connection.createGuid(),
			    eventType: 'OpenedContrat',
			    data: {
			    	reference:contrat.reference,
			    	label:contrat.label
			    }
			};
			this.eventStore.writeEvents("contrat-"+contrat.reference, EventStore.ExpectedVersion.Any, false, [newEvent], config.credentials, function (completed) { console.log("completed", newEvent)});
		} else {
			console.log("ERROR: Référence existante");
		}
	}

	updateLabel(reference, label) {
		if(contrats["contrat-"+reference]) {
			var newEvent = {
			    eventId: EventStore.Connection.createGuid(),
			    eventType: 'LabelChangedContrat',
			    data: {
			    	label:label
			    }
			};
			this.eventStore.writeEvents("contrat-"+reference, EventStore.ExpectedVersion.Any, false, [newEvent], config.credentials, function (completed) { console.log("completed", newEvent)});
		} else {
			console.log("ERROR: Référence non existante");
		}
	}
}

module.exports = ContratService;