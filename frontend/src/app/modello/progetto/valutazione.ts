import { Data } from "@angular/router";
import { Esperto } from "../iscritto/ruolo/esperto";
import { ValutazioneCandidati } from "./valutazione-candidati";

export class Valutazione {
    recensione: string;
    idProgetto: number;
    valutazioneCandidati: ValutazioneCandidati[];
    data: Data;
    identificativoEsperto: string;

    constructor(recensione: string, idProgetto: number, valutazioneCandidati: ValutazioneCandidati[], data: Data, identificativoEsperto: string){
        this.recensione = recensione;
        this.idProgetto = idProgetto;
        this.valutazioneCandidati = valutazioneCandidati;
        this.data = data;
        this.identificativoEsperto = identificativoEsperto;
    }
}
