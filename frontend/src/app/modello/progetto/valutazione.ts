import { Data } from "@angular/router";
import { Esperto } from "../iscritto/ruolo/esperto";
import { ValutazioneDto } from "./valutazione-dto";
import { ValutazioneCandidati } from "./valutazione-candidati";

export class Valutazione extends ValutazioneDto{
    creatoIl: Data;
    identificativoEsperto: string;

    constructor(recensione: string, idProgetto: number, valutazioneCandidati: ValutazioneCandidati[], creatoIl: Data, identificativoEsperto: string){
        super(recensione, idProgetto, valutazioneCandidati);
        this.creatoIl = creatoIl;
        this.identificativoEsperto = identificativoEsperto;
        
    }
}
