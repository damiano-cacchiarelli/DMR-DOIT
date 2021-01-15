import { Progetto } from "../../progetto/progetto";
import { Valutazione } from "../../progetto/valutazione";
import { Ruolo } from "../ruolo";
import { TipologiaRuolo } from "../tipologia-ruolo.enum";

export class Esperto extends Ruolo {

    rango: number;
    listaValutazioni: Valutazione[];

    constructor(rango: number, id: number, progettiPersonali: Progetto[], listaValutazioni: Valutazione[], ruolo: TipologiaRuolo) {
        super(ruolo, id, progettiPersonali);
        this.rango = rango;
        this.listaValutazioni = listaValutazioni;
    }
}
