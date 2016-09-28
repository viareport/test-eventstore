"use strict"; 
define( [
    "repositories.contracts",
    "aggregate.Contract"
], function(
    repository,
    Contract
)
{
    class Handler {
        constructor() {

        }

        dispatch( command ) {
            switch( command.eventName )
            {
                case "addContract":
                    return this.addContract( command.data );
                
                case "updateLabelContract":
                    return this.updateLabelContract( command.data );
            }
        }


        addContract( data ) {
            return repository.getContractByRef( data.reference )
                .then( contract => {
                    if ( contract )
                        throw "Contrat déjà existant";
                    
                    console.log( "creating contract" );
                    return Contract.create( data );
                } )
                .then( contract => {
                    console.log( "contrat " + contract.reference + " - " + contract.label + " crée avec succès." );
                    console.log( "Commiting events..." );
                    repository.add( contract );
                    repository.commitEvents( contract );
                } )
                .then( success => {
                    console.log( "addContract end > success" );
                })
                .catch( err => {
                    throw "ERREUR addContract: " + err;
                } );
        }
    }

    return new Handler();
} );