"use strict";
define( [
    "routes.get",
    "routes.post"
], function(
    getRoutes,
    postRoutes
)
{
    return function( app )
    {
        app.get( '/', getRoutes.contracts );
        app.get( '/list', getRoutes.contracts );
        app.post( '/add', postRoutes.contract );
    };
} );