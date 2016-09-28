"use strict"; 
define( [
    "config",
    "config.loadApp",
    "config.loadProjection",
    "router"
],
function(
    config,
    loadApp,
    loadProjection,
    router
)
{
    console.log( "> server start..." );
    const app = loadApp();
    router( app );
    
    loadProjection()
    .then( success => {
        app.listen( config.port, function()
        {
            console.log( "server listening " + config.port );
        } );
    } );
} );