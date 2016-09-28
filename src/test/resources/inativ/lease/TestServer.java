package inativ.lease;

import inativ.lease.Main;
import spark.Spark;

public class TestServer extends Main {
    public static final int TEST_PORT = 4581;
    protected int port;

    public TestServer() {
        this(TEST_PORT);
    }

    public TestServer(int port) {
        super(port);
        this.port = port;
    }

    @Override
    public void run() {
        super.run();
        // BUGFIX Pour éviter les téléscopages malheureux entre tests
        Spark.awaitInitialization();
    }

    public void stop() {
        Spark.stop();
    }

    public String getBaseUrl() {
        return "http://localhost:" + port + "/apilease";
    }

}
