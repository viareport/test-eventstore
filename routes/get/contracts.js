"use strict";
define( [
], function(
)
{
    return function( req, res )
    {
        res.send( `Hello world
        <form action="/add" method="POST">
            Reference: <input type="text" name="reference" /><br />
            Label: <input type="text" name="label" /><br />

            <br />
            <input type="submit"/>
        </form>
        ` );
    };
} );