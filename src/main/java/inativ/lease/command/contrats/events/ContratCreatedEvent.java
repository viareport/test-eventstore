package inativ.lease.command.contrats.events;

public class ContratCreatedEvent implements Event {

    public String reference;
    public String label;

    public ContratCreatedEvent(String reference, String label) {
        this.reference = reference;
        this.label = label;
    }

}
