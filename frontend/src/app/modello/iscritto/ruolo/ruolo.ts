import { Progetto } from "../../progetto/progetto";
import { RuoloDto } from "../../../modello-dto/iscritto-dto/ruolo/ruolo-dto";
import { TipologiaRuolo } from "./tipologia-ruolo.enum";

export class Ruolo extends RuoloDto{
    
    id: number;
    progettiPersonali: Progetto[];

    constructor(ruolo: TipologiaRuolo, id: number, progettiPersonali: Progetto[]){
        super(ruolo);
        this.id = id;
        this.progettiPersonali = progettiPersonali;
    }

}
