var EventStore = require('event-store-client');

var config = {
    'eventStore': {
        'address': "127.0.0.1",
        'port': 1113,

        'credentials': {
            'username': "admin",
            'password': "changeit"
        }
    },
    'debug': false,
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