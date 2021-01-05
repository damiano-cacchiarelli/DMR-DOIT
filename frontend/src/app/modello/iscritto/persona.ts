import { Iscritto } from "./iscritto";

export class Persona extends Iscritto {
    nome: string;
    cognome: string;
    cittadinanza: string;
    sesso: string;
    telefono: string;

    constructor(identificativo: string, email: string, password: string, nome: string, cognome: string, cittadinanza: string, sesso: string, telefono: string) {
        super(identificativo, email, password);
        this.nome = nome;
        this.cognome = cognome;
        this.cittadinanza = cittadinanza;
        this.sesso = sesso;
        this.telefono = telefono;

        super.tipo = "persona";
    }
}
