"use strict";
define( [
    "event-store-client",
    "config"
], function(
    EventStore,
    config
)
{
    const eventStore = new EventStore.Connection( config.eventStore );

    eventStore.writeEvent = function( streamName, event )
    {
        // create generic eventId
        event.eventId = EventStore.Connection.createGuid();

        return new Promise( ( res, rej ) => {
            eventStore._writeEvents( streamName, EventStore.ExpectedVersion.Any, false, [event],
                config.eventStore.credentials, function( completed )
                {
                    console.log( "event store writeEvent completed with: " + completed );
                    res( event );
                } );
        } );
    };

    eventStore._writeEvents = eventStore.wirteEvents;
    eventStore.writeEvents = function( streamName, events )
    {
        console.log( "try write events with", events )
        // create generic eventId
        for ( var i = 0, ev; ev = events[ i ]; ++i )
            ev.eventId = EventStore.Connection.createGuid();

        return new Promise( ( res, rej ) => {
            eventStore._writeEvents( streamName, EventStore.ExpectedVersion.Any, false, events,
                config.eventStore.credentials, function( completed )
                {
                    console.log( "event store writeEvents completed with: " + completed );
                    res( events );
                } );
        } );
    };

    return eventStore;
} );