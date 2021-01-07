import { TipologiaRuolo } from "./tipologia-ruolo.enum";

export class Ruolo {
    ruolo: TipologiaRuolo;

    constructor(ruolo: TipologiaRuolo){
        this.ruolo = ruolo;
    }
}
