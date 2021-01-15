export class Tag {

    public static readonly COLORI: string[] = ["primary", "secondary", "warning",  "success", "danger"];

    nome: string;
    constructor(nome: string) {
        this.nome = nome;
    }

}
