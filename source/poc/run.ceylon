import ceylon.json {
	Object
}
import spark {
	Spark,
	Request,
	Response,
	Route
}

native shared void run();

"Run the module `poc`."
native ("jvm") shared void run() {
	
	// Youhou on lance Spark
	Spark.get("/hello", MaRoute());
}

native ("jvm") class MaRoute() satisfies Route {
	shared actual Object? handle(Request request, Response response) {
		response.status(200);
		response.type("application/json");
		return Object { "hello"->"world" };
	}
}
