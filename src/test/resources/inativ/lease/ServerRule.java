package inativ.lease;

import org.junit.rules.ExternalResource;

public class ServerRule extends ExternalResource {
    private TestServer server;

    @Override
    protected void before() throws Throwable {
        // Launch REST server
        server = new TestServer();
        server.run();
    }

    @Override
    protected void after() {
        server.stop();
    }

    public String getBaseUrl() {
        return server.getBaseUrl();
    }

}