package inativ.lease.query.contrats.queries;

import java.util.List;

import inativ.lease.query.contrats.QContrat;
import inativ.lease.query.contrats.QContratRepository;

public class QueryHandler {

    // TODO utiliser une factory
    private QContratRepository repository = new QContratRepository();

    public List<QContrat> findContrats() {
        return repository.findContrats();
    }
    
    
}
