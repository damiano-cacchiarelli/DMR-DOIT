export class Iscritto {
    identificativo: string;
    email: string;
    password: string;

    tipo: string = "";

    constructor(identificativo: string, email: string, password: string) {
        this.identificativo = identificativo;
        this.email = email;
        this.password = password;
    }
}
