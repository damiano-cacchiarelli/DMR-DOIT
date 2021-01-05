import { Iscritto } from "./iscritto";

export class Ente extends Iscritto {

    sede: string;
    annoDiFondazione: Date;

    constructor(identificativo: string, email: string, password: string, sede: string, annoDiFondazione: Date) {
        super(identificativo, email, password);
        this.sede = sede;
        this.annoDiFondazione = annoDiFondazione;

        super.tipo = "ente";
    }
}
