import { InvitoDto } from "../../modello-dto/invito-dto/invito-dto";
import { RuoloSoggetto } from "./ruolo-soggetto.enum";
import { TipologiaInvito } from "./tipologia-invito.enum";
import { TipologiaRisposta } from "./tipologia-risposta.enum";

export class Invito extends InvitoDto {

    id: string;
    soggetto: RuoloSoggetto;
    data: Date;
    tipologiaRisposta: TipologiaRisposta;
    idMittente: string;
    idProgetto: number;
    nomeProgetto: string;

    constructor(contenuto: string, tipologiaInvito: TipologiaInvito, idDestinatario: string, id: string, soggetto: RuoloSoggetto, data: Date, tipologiaRisposta: TipologiaRisposta, idMittente: string,
        idProgetto: number, nomeProgetto: string) {
        super(contenuto, tipologiaInvito, [idDestinatario], idProgetto);
        this.id = id;
        this.soggetto = soggetto;
        this.data = data;
        this.tipologiaRisposta = tipologiaRisposta;
        this.idMittente = idMittente;
        this.idProgetto = idProgetto;
        this.nomeProgetto = nomeProgetto;
    }
}
