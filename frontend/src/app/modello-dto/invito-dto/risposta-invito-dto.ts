import { TipologiaRisposta } from "../../modello/invito/tipologia-risposta.enum";

export class RispostaInvitoDto {
    idInvito: string;
    risposta: TipologiaRisposta;

    constructor(idInvito: string, risposta: TipologiaRisposta){
        this.idInvito = idInvito;
        this.risposta = risposta;
    }
}
