"use strict";
define( [
    "handlers.command"
], function(
    commandHandler
)
{
    return function( req, res )
    {
        var reference = req.body.reference || "";
        var label     = req.body.label || "";
        console.log( "receive POST CONTRACT ", reference, label );
        
        if ( !reference || !label )
            return res.status( 401 ).send( "Champs manquants" );
        
        commandHandler.dispatch( { 
            "eventName": "addContract",
            "data": {
                "reference": reference,
                "label": label
            }
        } )
        .then( succes => {
            res.send( "Contract ajouté" );
        } )
        .catch( err => {
            res.status( 501 ).send( "ERREUR: " + err );
        } );

    };
} );