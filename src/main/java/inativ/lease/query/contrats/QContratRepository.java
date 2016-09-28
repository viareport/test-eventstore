package inativ.lease.query.contrats;

import java.util.Collections;
import java.util.List;

import inativ.lease.EventStore;

public class QContratRepository {
    
    private EventStore eventStore = new EventStore();

    public List<QContrat> findContrats() {
        return Collections.emptyList();
    }

}
