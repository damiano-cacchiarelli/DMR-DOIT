import { Progetto } from "../../progetto/progetto";
import { Valutazione } from "../../progetto/valutazione";
import { Ruolo } from "./ruolo";
import { TipologiaRuolo } from "./tipologia-ruolo.enum";

export class Esperto extends Ruolo {

    rango: number;
    valutazioniEffettuate: Valutazione[];

    constructor(rango: number, id: number, progettiPersonali: Progetto[], valutazioniEffettuate: Valutazione[], ruolo: TipologiaRuolo) {
        super(ruolo, id, progettiPersonali);
        this.rango = rango;
        this.valutazioniEffettuate = valutazioniEffettuate;
    }
}
