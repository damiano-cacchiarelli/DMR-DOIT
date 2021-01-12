import { Progetto } from "../../progetto/progetto";
import { Ruolo } from "../ruolo";
import { TipologiaRuolo } from "../tipologia-ruolo.enum";

export class Progettista extends Ruolo {

    candidature: Progetto[];

    constructor(candidature: Progetto[], ruolo: TipologiaRuolo, id: number){

        super(ruolo, id);
        this.candidature = candidature;
        
    }

}