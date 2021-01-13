import { Progetto } from "../../progetto/progetto";
import { Ruolo } from "../ruolo";
import { TipologiaRuolo } from "../tipologia-ruolo.enum";

export class Proponente extends Ruolo {

    proposte: Progetto[];

    constructor(proposte: Progetto[], ruolo: TipologiaRuolo, id: number){

        super(ruolo, id);
        this.proposte = proposte;
        
    }

}