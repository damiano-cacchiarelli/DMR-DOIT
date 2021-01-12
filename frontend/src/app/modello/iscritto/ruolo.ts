import { RuoloDto } from "./ruolo-dto";
import { TipologiaRuolo } from "./tipologia-ruolo.enum";

export class Ruolo extends RuoloDto{
    
    id: number;

    constructor(ruolo: TipologiaRuolo, id: number){
        super(ruolo);
        this.id = id;
    }

}
