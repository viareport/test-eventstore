"use strict";
define( [
    "eventStore"
], function(
    eventStore
)
{
    var contracts = {};

    class ContractsRepository{
        constructor() {
        }

        getContractByRef( reference ) {
            return new Promise( ( res, rej ) => {
                if ( contracts[ reference ] )
                    return res( contracts[ reference ] );
                else
                    return res( null );
                // return eventStore.read( trucmush );
                // mettre res( contract ) dans la callback 
            } );
        }

        add( contract ) {
            contracts[ contract.reference ] = contract;
        }

        /**
         * Event handler => write to the store then apply the event 
         */
        commitEvents( contract ) {
            return eventStore.writeEvents( "contract-" + contract.reference, contract.queueEvents )
                .then( eventSuccess => {
                    contract.queueEvents = []; // clear events
                    return eventSuccess;
                } );
        }
    }

    return new ContractsRepository();
} );