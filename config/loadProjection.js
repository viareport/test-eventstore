"use strict"; 
define( [
    "config",
    "aggregate.Contract",
    "repositories.contracts",
    "eventStore"
], function(
    config,
    Contract,
    contractsRepository,
    eventStore
)
{
    // temp - this should be removed when projection is persisted
    // load all events, an play it

    function playEvent( evt ) {
        console.log("play event > " + evt.streamId, evt );
        switch ( evt.eventType ) {
            case "CreateContract": 
                var contract = new Contract( evt.data );
                contractsRepository.add( contract );
                break;
        }
    }

    return function() {

        return new Promise( (res, rej ) => {
            eventStore.readStreamEventsForward( "$ce-contrat", 0, 100, false, false, playEvent,
                config.eventStore.credentials, function(){ res(); } );
        } );
    }
} );