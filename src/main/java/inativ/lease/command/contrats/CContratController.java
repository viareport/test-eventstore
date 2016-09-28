package inativ.lease.command.contrats;

import org.apache.http.HttpStatus;

import com.google.gson.Gson;

import inativ.lease.command.contrats.commands.ChangeContratLabelCommand;
import inativ.lease.command.contrats.commands.CommandHandler;
import inativ.lease.command.contrats.commands.CreateContratCommand;
import spark.Request;
import spark.Response;

public class CContratController {
    private static final Gson GSON = new Gson();
    private static final CommandHandler commandHandler = new CommandHandler();
    
    public static String createContrat(Request request, Response response) {
        CreateContratCommand command = GSON.fromJson(request.body(), CreateContratCommand.class);
        commandHandler.handleCreateContrat(command);
        response.status(HttpStatus.SC_CREATED);
        return "";
    }
    
    public static String updateContrat(Request request, Response response) {
        ChangeContratLabelCommand command = GSON.fromJson(request.body(), ChangeContratLabelCommand.class);
        commandHandler.handleChangeLabelContrat(command);
        return "";
    }
}
