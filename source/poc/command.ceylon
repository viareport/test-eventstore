class OpenContratCommand(shared String reference, shared String label) {}
class ChangeLabelContratCommand(shared String contratId, shared String label) {}

class CommandHandler() {
	shared void execute(OpenContratCommand|ChangeLabelContratCommand cmd) {
		ContratRepository repository = ContratRepository(EventStore());
		switch (cmd)
		case (is OpenContratCommand) {
			Contrat newlyContrat = createContrat(cmd.reference, cmd.label);
			repository.create(newlyContrat);
		}
		case (is ChangeLabelContratCommand) {
			repository.updateLabel(cmd.contratId, cmd.label);
		}
	}
}
