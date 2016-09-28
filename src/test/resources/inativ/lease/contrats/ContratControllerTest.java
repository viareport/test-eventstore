package inativ.lease.contrats;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;

import inativ.lease.ServerRule;
import inativ.lease.command.contrats.commands.CommandHandler;
import inativ.lease.command.contrats.commands.CreateContratCommand;



public class ContratControllerTest {
    
    private static final Gson GSON = new Gson();

    @ClassRule
    public static ServerRule server = new ServerRule();

    private static String baseUrl;
    
    private CommandHandler commandHandler = new CommandHandler();

    @BeforeClass
    public static void init() {
        baseUrl = server.getBaseUrl() + "/contrats";
    }
    
    @Test
    public void create_contrat_return_201_when_ok() {
        CreateContratCommand command = new CreateContratCommand(UUID.randomUUID().toString(), "Location voiture");
        given()
            .contentType(ContentType.JSON.toString())
            .body(GSON.toJson(command))
            .when()
            .post(baseUrl)
            .then()
            .assertThat()
            .statusCode(201);
    }
    
    @Test
    public void get_contrats_return_created_contrats() {
        // GIVEN
        CreateContratCommand createCommand = new CreateContratCommand(UUID.randomUUID().toString(), "Label");
        commandHandler.handleCreateContrat(createCommand);
        
        // WHEN - THEN
        given().when().get(baseUrl).then().assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON.toString())
            .body("size()", equalTo(1))
            .body("[0].reference", equalTo(createCommand.reference))
            .body("[0].label", equalTo(createCommand.label));
    }

}
