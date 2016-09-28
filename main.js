"use strict"; 
var requirejs = require( 'requirejs' );

requirejs.config( {
  paths: {
    "init"                    : "init",

    "config"                  : "config/index",
    "config.loadApp"          : "config/loadApp",
    "config.loadProjection"   : "config/loadProjection",
    
    "handlers.command"        : "handlers/command",
    "handlers.query"          : "handlers/query",

    "eventStore"              : "eventStore",

    // repositories
    "repositories.contracts"  : "repositories/contracts",

    // aggregates models
    "aggregate.Contract"      : "aggregates/Contract",
    
    // routes
    "router"                  : "routes/router",

    // get routes
    "routes.get"              : "routes/get/index",
    "routes.get.contracts"    : "routes/get/contracts",

    // post routes
    "routes.post"             : "routes/post/index",
    "routes.post.contract"    : "routes/post/contract"
    
  }
  ,nodeRequire: require
} );

requirejs( "init" );