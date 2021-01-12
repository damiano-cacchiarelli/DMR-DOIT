import { PersonaDto } from "./persona-dto";

export class Persona extends PersonaDto {

    constructor(identificativo: string, email: string, password: string, nome: string,
        cognome: string, cittadinanza: string, sesso: string, telefono: string) {
        super(identificativo, email, password, nome, cognome, cittadinanza, sesso, telefono);
    }
}
