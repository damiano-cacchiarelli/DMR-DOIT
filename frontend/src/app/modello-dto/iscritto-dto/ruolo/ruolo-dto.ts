import { TipologiaRuolo } from "../../../modello/iscritto/ruolo/tipologia-ruolo.enum";

export class RuoloDto {
    
    ruolo: TipologiaRuolo;

    constructor(ruolo: TipologiaRuolo){
        this.ruolo = ruolo;
    }
}
