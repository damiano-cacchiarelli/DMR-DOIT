import { Data } from "@angular/router";
import { Fase } from "./fase.enum";
import { Progetto } from "./progetto";
import { Stato } from "./stato.enum";
import { Tag } from "./tag";
import { Valutazione } from "./valutazione";

export class ProgettoDettagliato extends Progetto {

    id: number;
    stato: Stato;
    fase: Fase;
    creatoIl: Data;
    idProponente: string;
    listaValutazioni: Valutazione[];

    constructor(nome: string, obiettivi: string, requisiti: string, tags: Tag[], id: number,
        stato: Stato, fase: Fase, creatoIl: Data, idProponente: string, listaValutazioni: Valutazione[]) {
        super(nome, obiettivi, requisiti, tags);
        this.id = id;
        this.stato = stato;
        this.fase = fase;
        this.creatoIl = creatoIl;
        this.idProponente = idProponente;
        this.listaValutazioni = listaValutazioni;

    }
}
