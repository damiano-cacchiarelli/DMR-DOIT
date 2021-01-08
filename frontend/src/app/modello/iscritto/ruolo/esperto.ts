import { Progetto } from "../../progetto/progetto";
import { Iscritto } from "../iscritto";
import { TipologiaRuolo } from "../tipologia-ruolo.enum";

export class Esperto {
    rango: number;
    progettiValutati: Progetto[];
    ruolo: TipologiaRuolo;
    id: number;
    iscritto: Iscritto;

    constructor(rango: number, progettiValutati: Progetto[], ruolo: TipologiaRuolo, id: number, iscritto: Iscritto) {
        this.rango = rango;
        this.progettiValutati = progettiValutati;
        this.ruolo = ruolo;
        this.id = id;
        this.iscritto = iscritto;

    }
}
