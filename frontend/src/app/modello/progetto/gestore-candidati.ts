export class GestoreCandidati {
    candidatureAperte: boolean;
    identificativiCandidati: string[];
    identificativiPartecipanti: string[];

    constructor(candidatureAperte: boolean, identificativiCandidati: string[], identificativiPartecipanti: string[]){
        this.candidatureAperte = candidatureAperte;
        this.identificativiCandidati = identificativiCandidati;
        this.identificativiPartecipanti = identificativiPartecipanti;
    }
}
