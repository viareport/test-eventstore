"use strict";
define( [
    "eventStore"
], function(
    eventStore
)
{
    return class Contract {
        constructor( args ) {
            args = args || {};
            this.reference = args.reference;
            this.label = args.label;

            this.queueEvents = [];
        }

        /***
         * Mute the object and store the event
         */
        applyEvent( event ) {
            for ( var i in event.data )
                this[ i ] = event.data[ i ];
            
            this.queueEvents.push( event );
        }
        
        static create( data ) {
            if ( !data.reference || !data.label )
                throw "Contract.create: Contrat invalide";
            
            var contract = new Contract();
            contract.applyEvent( {
                "eventType": "CreateContract",
                "data": data
            } );
            return contract;
        }
    }
} );