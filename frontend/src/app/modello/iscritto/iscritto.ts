import { Curriculum } from "./curriculum";
import { IscrittoDto } from "../../modello-dto/iscritto-dto/iscritto-dto";
import { Ruolo } from "./ruolo/ruolo";

export class Iscritto extends IscrittoDto{

    curriculum: Curriculum;
    ruoli: Ruolo[];

    constructor(identificativo: string, email: string, password: string, curriculum: Curriculum,  ruoli: Ruolo[] ){
        super(identificativo, email, password);
        this.curriculum = curriculum;
        this.ruoli = ruoli;

    }
}
