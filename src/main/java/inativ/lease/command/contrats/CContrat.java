package inativ.lease.command.contrats;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import inativ.lease.command.contrats.events.ContratCreatedEvent;
import inativ.lease.command.contrats.events.ContratLabelChangedEvent;
import inativ.lease.command.contrats.events.Event;


public class CContrat {
    public String reference;
    public List<Event> uncommitedEvents = new ArrayList<>();

    public static CContrat create(String reference, String label) {
        if(Strings.isNullOrEmpty(reference)) {
           throw new RuntimeException("Contract reference is null");
        }
        
        CContrat contrat = new CContrat();
        contrat.apply(new ContratCreatedEvent(reference, label));
        return contrat;
    }

    private void apply(ContratCreatedEvent contratCreatedEvent) {
        this.reference = contratCreatedEvent.reference;
        this.uncommitedEvents.add(contratCreatedEvent);
    }

    public void changeLabel(String newLabel) {
        this.apply(new ContratLabelChangedEvent(newLabel));
    }
    

    private void apply(ContratLabelChangedEvent contratLabelChangedEvent) {
        this.uncommitedEvents.add(contratLabelChangedEvent);
    }
}
