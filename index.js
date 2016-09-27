const Contrat = require('./Contrat');
const ContratService = require('./ContratService');

var EventStore = require('event-store-client');

var config = {
    'host': "192.168.1.30",
    'port': 1113,

    'credentials': {
        'username': "admin",
        'password': "changeit"
    },
    'debug': true,
    onConnect: function () {
        eventClose = false;
    },
    onClose: function () {
        eventClose = true;
    }
};

var newEvent = {
    eventId: EventStore.Connection.createGuid(),
    eventType: 'TYPE',
    data: {
    }
};

var eventStore = new EventStore.Connection(config);

var events= [];

eventStore.writeEvents("streamName", EventStore.ExpectedVersion.Any, false, events, config.credentials, function (completed) {
});


var contratService = new ContratService(eventStore, test);

function openContrat(contratCommand) {
    var contrat = Contrat.create(contratCommand);
    contratService.openContrat(contrat);
}
function updateContratLabel(contratCommand) {
    contratService.updateLabel(contratCommand.reference, contratCommand.label);
}

function loadContratByReference(contratQuery) {
    return contratService.getByReference(contratQuery.reference);
}

const openContratCommand = {
    reference: "ref1",
    label: "label"
};

const contratByReferenceQuery = {
    reference: "ref1"
};

const changeLabelContratCommand = {
    reference: "ref1",
    label: "label3"
};

function test() {
    //openContrat(openContratCommand);
    const contratLoaded = loadContratByReference(contratByReferenceQuery);
    console.log(contratLoaded);
    updateContratLabel(changeLabelContratCommand);
    console.log("updated", contratLoaded);
}

