import { Progetto } from "../progetto/progetto";
import { InvitoDto } from "./invito-dto";
import { RuoloSoggetto } from "./ruolo-soggetto.enum";
import { TipologiaInvito } from "./tipologia-invito.enum";
import { TipologiaRisposta } from "./tipologia-risposta.enum";

export class Invito extends InvitoDto {

    id: string;
    soggetto: RuoloSoggetto;
    data: Date;
    tipologiaRisposta: TipologiaRisposta;
    idMittente: string;
    progetto: Progetto;

    constructor(contenuto: string, tipologiaInvito: TipologiaInvito, idDestinatario: string, idProgetto: number, id: string, soggetto: RuoloSoggetto, data: Date, tipologiaRisposta: TipologiaRisposta, idMittente: string,
        progetto: Progetto) {
        super(contenuto, tipologiaInvito, [idDestinatario], idProgetto);
        this.id = id;
        this.soggetto = soggetto;
        this.data = data;
        this.tipologiaRisposta = tipologiaRisposta;
        this.idMittente = idMittente;
        this.progetto = progetto;
    }
}
