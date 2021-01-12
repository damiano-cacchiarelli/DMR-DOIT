import { Progetto } from "../../progetto/progetto";
import { IscrittoDto } from "../iscritto-dto";
import { TipologiaRuolo } from "../tipologia-ruolo.enum";

export class Esperto {
    rango: number;
    progettiValutati: Progetto[];
    ruolo: TipologiaRuolo;
    id: number;
    iscritto: IscrittoDto;

    constructor(rango: number, progettiValutati: Progetto[], ruolo: TipologiaRuolo, id: number, iscritto: IscrittoDto) {
        this.rango = rango;
        this.progettiValutati = progettiValutati;
        this.ruolo = ruolo;
        this.id = id;
        this.iscritto = iscritto;

    }
}
