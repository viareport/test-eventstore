"use strict";
define( [
    "express",
    "body-parser"
],
function(
    express,
    bodyParser
)
{
    return function()
    {
        const app = express();

        // config express to parse body
        app.use( bodyParser.urlencoded( { extended: false } ) );

        return app;
    }
} );