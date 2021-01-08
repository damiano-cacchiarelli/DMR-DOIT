import { Tag } from "./tag";

export class Progetto {
    nome: string;
    obiettivi: string;
    requisiti: string;
    tags: Tag[];

    constructor(nome: string, obiettivi: string,  requisiti: string,  tags: Tag[] ){
        this.nome = nome;
        this.obiettivi = obiettivi;
        this.requisiti = requisiti;
        this.tags = tags;
    }
}
