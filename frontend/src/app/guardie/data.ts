import { TipologiaRuolo } from "../modello/iscritto/ruolo/tipologia-ruolo.enum";

export class Data {

    ruoliNecessari: TipologiaRuolo[];
    loggedIn: boolean;

    constructor(ruoliNecessari: TipologiaRuolo[] = [], loggedIn: boolean = true){
        this.ruoliNecessari = ruoliNecessari;
        this.loggedIn = loggedIn;
    }
}
