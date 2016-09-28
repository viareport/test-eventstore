package inativ.lease.command.contrats.commands;

public class CreateContratCommand {

    public String reference;
    public String label;
    
    public CreateContratCommand(String reference, String label) {
        this.reference = reference;
        this.label = label;
    }

}
