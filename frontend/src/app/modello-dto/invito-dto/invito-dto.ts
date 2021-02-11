import { TipologiaInvito } from "../../modello/invito/tipologia-invito.enum";

export class InvitoDto {
    
    contenuto: string;
    tipologiaInvito: TipologiaInvito;
    idDestinatario: string[];
    idProgetto: number;

    constructor(contenuto: string, tipologiaInvito: TipologiaInvito, idDestinatario: string[], idProgetto: number){
        this.contenuto = contenuto;
        this.tipologiaInvito = tipologiaInvito;
        this.idDestinatario = idDestinatario;
        this.idProgetto = idProgetto;
    }
}
