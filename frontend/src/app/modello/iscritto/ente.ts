import { EnteDto } from "../../modello-dto/iscritto-dto/ente-dto";

export class Ente extends EnteDto{

    constructor(identificativo: string, email: string, password: string, sede: string, annoDiFondazione: Date){
        super(identificativo, email, password, sede, annoDiFondazione);
    }
}
