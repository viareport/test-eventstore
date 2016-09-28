package inativ.lease.command.contrats;

import java.util.HashMap;
import java.util.Map;

import inativ.lease.EventStore;

public class CContratRepository {
    
    public static Map<String, CContrat> contrats = new HashMap<>();
    // TODO factory ou IoC
    private EventStore eventStore = new EventStore();

    public boolean isContratExist(String reference) {
        return contrats.containsKey(reference);
    }

    public void save(CContrat contrat) {
        eventStore.publish(contrat.uncommitedEvents);
        contrats.put(contrat.reference, contrat);
    }

    public CContrat findContratById(String reference) {
        if (contrats.containsKey(reference)) {
            return contrats.get(reference);    
        }
        
        throw new RuntimeException(reference  + " does not exist in the list of contracts");
    }

}
