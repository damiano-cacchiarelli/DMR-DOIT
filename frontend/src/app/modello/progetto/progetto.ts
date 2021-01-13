import { Data } from "@angular/router";
import { Fase } from "./fase.enum";
import { GestoreCandidati } from "./gestore-candidati";
import { ProgettoDto } from "./progetto-dto";
import { Stato } from "./stato.enum";
import { Tag } from "./tag";
import { Valutazione } from "./valutazione";

export class Progetto extends ProgettoDto {

    id: number;
    stato: Stato;
    fase: Fase;
    creatoIl: Data;
    idProponente: string;
    lastValutazione: Valutazione;
    gestoreCandidati: GestoreCandidati;

    constructor(nome: string, obiettivi: string, requisiti: string, tags: Tag[], id: number,
        stato: Stato, fase: Fase, creatoIl: Data, idProponente: string, lastValutazione: Valutazione, gestoreCandidati: GestoreCandidati) {
        super(nome, obiettivi, requisiti, tags);
        this.id = id;
        this.stato = stato;
        this.fase = fase;
        this.creatoIl = creatoIl;
        this.idProponente = idProponente;
        this.lastValutazione = lastValutazione;
        this.gestoreCandidati = gestoreCandidati;
    }
}
