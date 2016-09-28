package inativ.lease.command.contrats.events;

public class ContratLabelChangedEvent implements Event {

    public String label;
    
    public ContratLabelChangedEvent(String label) {
        this.label = label;
    }
    
}
