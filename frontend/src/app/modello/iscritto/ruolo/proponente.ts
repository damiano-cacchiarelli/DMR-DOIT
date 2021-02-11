import { Progetto } from "../../progetto/progetto";
import { Ruolo } from "./ruolo";
import { TipologiaRuolo } from "./tipologia-ruolo.enum";

export class Proponente extends Ruolo {


    constructor(ruolo: TipologiaRuolo, id: number, progettiPersonali: Progetto[]) {

        super(ruolo, id, progettiPersonali);
    }

}