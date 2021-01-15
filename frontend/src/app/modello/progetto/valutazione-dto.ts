import { ValutazioneCandidati } from "./valutazione-candidati";

export class ValutazioneDto {
    recensione: string;
    idProgetto: number;
    valutazioniCandidati: ValutazioneCandidati[];
    
    constructor(recensione: string, idProgetto: number, valutazioniCandidati: ValutazioneCandidati[]) {
        this.recensione = recensione;
        this.idProgetto = idProgetto;
        this.valutazioniCandidati = valutazioniCandidati;
    }
}
