export class Curriculum {

    id: number;
    professione: string;
    lingue: string;
    sito: string;

    constructor(id: number, professione: string ,lingue: string, sito: string) {
        
        this.id = id;
        this.lingue = lingue;
        this.professione = professione;
        this.sito = sito;
    }
}
