import { IscrittoDto } from "./iscritto-dto";

export class PersonaDto extends IscrittoDto {
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
