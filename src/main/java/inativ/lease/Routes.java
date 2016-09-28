package inativ.lease;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import inativ.lease.command.contrats.CContratController;
import inativ.lease.query.contrats.QContratController;

public class Routes {
   
    private Routes() {
    }

    public static void init(String applicationPath) {
        post(applicationPath + "/contrats", CContratController::createContrat);
        put(applicationPath + "/contrats", CContratController::updateContrat);
        get(applicationPath + "/contrats", QContratController::getContrats);
    }

}
