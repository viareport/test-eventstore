package inativ.lease.command.contrats.commands;

import inativ.lease.command.contrats.CContrat;
import inativ.lease.command.contrats.CContratRepository;

public class CommandHandler {
    // TODO utiliser une factory
    private CContratRepository repository = new CContratRepository();
    
    public void handleCreateContrat(CreateContratCommand command) {
        if(!repository.isContratExist(command.reference)) {
            repository.save(CContrat.create(command.reference, command.label));
        }
    }

    public void handleChangeLabelContrat(ChangeContratLabelCommand command) {
        CContrat contrat = repository.findContratById(command.reference);
        contrat.changeLabel(command.label);
        repository.save(contrat);
    }
}
