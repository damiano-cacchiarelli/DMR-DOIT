import { ValutazioneCandidati } from "./valutazione-candidati";

export class Valutazione {
    recensione: string;
    idProgetto: number;
    valutazioneCandidati: ValutazioneCandidati;

    constructor(recensione: string, idProgetto: number, valutazioneCandidati: ValutazioneCandidati){
        this.recensione = recensione;
        this.idProgetto = idProgetto;
        this.valutazioneCandidati = valutazioneCandidati;
    }
}
