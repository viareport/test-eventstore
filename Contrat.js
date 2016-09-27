class OpenedContratEvent {
	constructor(reference, label) {
		this.reference = reference;
		this.label = label;
	}
}

class Contrat {
	constructor(reference, label){
	 	this.reference = reference;
	 	this.label = label;
	}

/*
	applyOpenedContratEvent(openedContratEvent) {
    	this.reference = openedContratEvent.reference;
    	this.label = openedContratEvent.label;
	}
*/

	applyChangedLabel(label) {
		this.label = label;
	} 

	// factory avec validation de la commande
	static create({reference, label}){
		if(!reference) {
			throw "Contrat invalide : pas de référence";
		} else {
			return new Contrat(reference, label);
		}
	}

}
module.exports = Contrat;