import { TipologiaRuolo } from "./tipologia-ruolo.enum";

export class RuoloDto {
    ruolo: TipologiaRuolo;

    constructor(ruolo: TipologiaRuolo){
        this.ruolo = ruolo;
    }
}
