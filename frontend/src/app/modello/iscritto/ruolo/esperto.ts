import { Valutazione } from "../../progetto/valutazione";
import { Ruolo } from "../ruolo";
import { TipologiaRuolo } from "../tipologia-ruolo.enum";

export class Esperto extends Ruolo {

    rango: number;
    listaValutazioni: Valutazione[];

    constructor(rango: number, listaValutazioni: Valutazione[], ruolo: TipologiaRuolo, id: number) {

        super(ruolo, id);
        this.rango = rango;
        this.listaValutazioni = listaValutazioni;

    }
}
