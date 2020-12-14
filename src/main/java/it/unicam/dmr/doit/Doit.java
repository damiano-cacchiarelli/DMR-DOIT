package it.unicam.dmr.doit;

import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerEsperto;
import it.unicam.dmr.doit.controller.ControllerProgettista;
import it.unicam.dmr.doit.controller.ControllerProponente;
import it.unicam.dmr.doit.controller.ControllerUtente;
import it.unicam.dmr.doit.utenti.Esperto;
import it.unicam.dmr.doit.utenti.Progettista;
import it.unicam.dmr.doit.utenti.Proponente;
import it.unicam.dmr.doit.utenti.Utente;
import it.unicam.dmr.doit.view.View;
import it.unicam.dmr.doit.view.ViewEsperto;
import it.unicam.dmr.doit.view.ViewProgettista;
import it.unicam.dmr.doit.view.ViewProponente;
import it.unicam.dmr.doit.view.ViewUtente;

public class Doit {

	private GestoreUtenti utenti;
	private VetrinaProgetti vetrina;

	public Doit() {
		utenti = new GestoreUtenti();
		vetrina = new VetrinaProgetti();
	}


	public VetrinaProgetti getVetrina() {
		return vetrina;
	}

	public GestoreUtenti getUtenti() {
		return utenti;
	}

	private static Scanner s;

	public static void main(String[] args) {

		Doit doit = new Doit();

		Utente matteo = new Utente("1", "Matteo", "Romagnoli");
		Proponente roberto = new Proponente("2", "Roberto ", "Cesetti ", "Montegiorgio",
				"Teconologia ", "Sommo srl");
		Progettista damiano = new Progettista("3", "Dam", "Cacchiu");
		Esperto lello = new Esperto("4", "g41", "Diversi");
		doit.utenti.aggiungiInscritto(matteo);
		doit.utenti.aggiungiInscritto(roberto);
		doit.utenti.aggiungiInscritto(damiano);
		doit.utenti.aggiungiInscritto(lello);

		ViewProponente viewProponente = new ViewProponente(new ControllerProponente(doit, roberto));
		ViewProgettista viewProgettista = new ViewProgettista(new ControllerProgettista(doit, damiano));
		ViewEsperto viewEsperto = new ViewEsperto(new ControllerEsperto(doit, lello));
		ViewUtente viewUtente = new ViewUtente(new ControllerUtente(doit, matteo));

		s = new Scanner(System.in);
		startMessage();
		operazioni();
		while (s.hasNext()) {
			try {
				switch (Integer.parseInt(s.nextLine())) {

				case 1:
					System.out.println("proponiProgetto Sei nel ruolo di proponente - Bentornato " + roberto.toString());
					viewProponente.proponiProgetto(s);
					break;
				case 2:
					System.out.println("permetteValutazioneProgetto Sei nel ruolo di proponente - Bentornato " + roberto.toString());
					viewProponente.permetteValutazioneProgetto(s, View.selezionaProgetto(s));
					break;
				case 3:
					System.out.println("invitaProgettista Sei nel ruolo di proponente - Bentornato " + roberto.toString());
					viewProponente.invitaProgettista(s, View.selezionaProgetto(s));
					break;
				case 4:
					System.out.println("passaAFaseSuccessiva Sei nel ruolo di proponente - Bentornato " + roberto.toString());
					viewProponente.passaAFaseSuccessiva(s, View.selezionaProgetto(s));
					break;
				case 5:
					System.out.println("ricercaProgetto Sei nel ruolo di utente - Bentornato " + matteo.toString());
					viewUtente.ricercaProgetto(s);
					break;	
				case 6:
					System.out.println("modificaCompetenze Sei nel ruolo di progettista - Bentornato " + damiano.toString());
					viewProgettista.modificaCompetenze(s);;
					break;	
				case 7:
					System.out.println("gestisciRichieste Sei nel ruolo di progettista - Bentornato " + damiano.toString());
					viewProgettista.gestisciRichieste(s);;
					break;	
				case 8:
					System.out.println("candidatiAlProgetto Sei nel ruolo di progettista - Bentornato " + damiano.toString());
					viewProgettista.candidatiAlProgetto(s);
					break;	
				case 9:
					System.out.println("valutaProgetto Sei nel ruolo di esperto - Bentornato " + lello.toString());
					viewEsperto.valutaProgetto(s);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				System.err.println("Errore generico: " + e);
				e.printStackTrace();
			}
			operazioni();
		}
		s.close();
	}
	
	private static void startMessage() {
		System.out.println(" -ssyyyo.syyyysso+:.    `syyyys.                  /yyyys: -yss+/o.oyosyyyso.``              \r\n" + 
				" .syyyyy.yyyyyyyyyyys:  /-oyyyyy-               `oyyyyy:/ -ysyyyo.+ssoyyys+soo+.            \r\n" + 
				" -ssyyys.+++++osyyyyyy: +y:/yyyyy-             `oyyyys-oo -syysos.-++/+oooossooo`           \r\n" + 
				" -s+syys`      `/syyyys./yy/:syyos/           .syyyy+-syo -syosys`       `+sysys:           \r\n" + 
				" -yssyys`        yyyyyy--//-.`:o//o:         -so+so-:syyo -yyssys`        :sssos/           \r\n" + 
				" -syyyys`        yyyyyy-:++++-`--:/oo`      :sssss:/syyso -o:+oso`       -osssso`           \r\n" + 
				" -ssssss`        ssssss-:oooss:`+sssso-   `+sssso.-sssss+ .oo+sso./+++/+osssoo/`            \r\n" + 
				" -+ssoo+         ssssss-/ssoos:  :sssss: .ossss+` -sssss+ -ossoso.osssssosos+-              \r\n" + 
				" .ooss+/         ssssss-/sssss:   -ossso./ssss/   -ossss+ .+ooooo.++++++o+o/os:`            \r\n" + 
				" -soosoo`        sssoss./sssss:    .osso./sso:    .ossss+ .+++/oo`      `.-:::+-            \r\n" + 
				" -s+oooo`      `/ssssso`/sssss:     `+so./so-     -ososo+ -+oooso`        - `-::            \r\n" + 
				" -oooooo`:/:/:+ooooooo- /ooooo:       /o./+.      :ooooo+ .+/:+/.          -:///`           \r\n" + 
				" .+ooo++.ooooooooooo/.  :ooooo:        -`-`       :ooooo+ .:/:.:.         `+o/+/-           \r\n" + 
				" .++//+/`+++++++/:-`    :+++++-                   -+++++: `-:-:-.          --``+- ");
	}
	
	private static void operazioni() {
		System.out.println("\n\nDOIT - operazioni disponibili:\n"
				+ "- 1 proponi porgetto\n"
				+ "- 2 permette valutazione progetto\n"
				+ "- 3 invita progettista\n"
				+ "- 4 passa a fase successiva\n"
				+ "\n"
				+ "- 5 ricerca progetto\n"
				+ "\n"
				+ "- 6 gestisci competenze\n"
				+ "- 7 gestisci richieste\n"
				+ "- 8 candidati ad un progetto\n"
				+ "\n"
				+ "- 9 valuta progetto");
	}
}
