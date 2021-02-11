import { TipologiaRuolo } from "./tipologia-ruolo.enum";

export class RuoloOpzioni {

    public static readonly RUOLI: Map<TipologiaRuolo, RuoloOpzioni> = new Map([
        [TipologiaRuolo.ROLE_PROPONENTE, new RuoloOpzioni(TipologiaRuolo.ROLE_PROPONENTE, "Questo ruolo permette di proporre progetti e gestirli.", "primary")],
        [TipologiaRuolo.ROLE_PROGETTISTA, new RuoloOpzioni(TipologiaRuolo.ROLE_PROGETTISTA, "Questo ruolo permette di partecipare a progetti.", "success")],
        [TipologiaRuolo.ROLE_ESPERTO, new RuoloOpzioni(TipologiaRuolo.ROLE_ESPERTO, "Questo ruolo permette di valutare progetti e relativi candidati.", "secondary")],
        [TipologiaRuolo.ROLE_SPONSOR, new RuoloOpzioni(TipologiaRuolo.ROLE_SPONSOR, "Questo ruolo permette di finanziare progetti.", "danger")]
    ]);

    tipo: TipologiaRuolo;
    nome: string;
    descrizione: string;
    colore: string;

    private constructor(tipo: TipologiaRuolo, descrizione: string, colore: string) {
        this.tipo = tipo;
        this.nome = tipo.replace("ROLE_", "");
        this.nome = this.nome.charAt(0) + this.nome.substring(1).toLocaleLowerCase();
        this.descrizione = descrizione;
        this.colore = colore;
    }
}
