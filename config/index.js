"use strict";
define( [],
function()
{
    return {
    
        port: 3000,

        'eventStore': {
            'host': "127.0.0.1",
            'port': 1113,

            'credentials': {
                'username': "admin",
                'password': "changeit"
            },
            'debug': true,
            onConnect: function () {
                console.log( "eventStore connected" );
            },
            onClose: function () {
                console.log( "eventStore disconnected" );
            }
        }
    };
} );