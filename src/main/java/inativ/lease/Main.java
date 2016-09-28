package inativ.lease;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static spark.Spark.exception;
import static spark.Spark.halt;
import static spark.Spark.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static final String APP_PATH = "/apilease";

    public void run() {
        LOGGER.info("Run Spark server");

        // Catch généralisé
        exception(Exception.class, (e, request, response) -> {
            LOGGER.error("Request error intercepted", e);
            halt(SC_INTERNAL_SERVER_ERROR, Throwables.getRootCause(e).getMessage());
        });

        // Declaration des routes applicatives controllers
        Routes.init(APP_PATH);
    }

    public Main(int port) {
        LOGGER.info("Instantiate Spark server with port {}", port);
        port(port);
    }


    // Start
    public static void main(String[] args) {
        int port = 4580; // default port
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.error("Cannot parse {} as port number, use default port {} instead", args[0], port);
            }
        }
        new Main(port).run();
    }
}
