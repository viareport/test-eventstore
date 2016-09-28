package inativ.lease.query.contrats;

import com.google.common.net.MediaType;
import com.google.gson.Gson;

import inativ.lease.query.contrats.queries.QueryHandler;
import spark.Request;
import spark.Response;

public class QContratController {
    private static final Gson GSON = new Gson();
    private static final QueryHandler queryHandler = new QueryHandler();
    
    public static String getContrats(Request request, Response response) {
        response.type(MediaType.JSON_UTF_8.toString());
        return GSON.toJson(queryHandler.findContrats());
    }

}
