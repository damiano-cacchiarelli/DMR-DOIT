package it.unicam.dmr.doit;

import java.util.Scanner;

import it.unicam.dmr.doit.controller.ControllerEsperto;
import it.unicam.dmr.doit.controller.ControllerProgettista;
import it.unicam.dmr.doit.controller.ControllerProponente;
import it.unicam.dmr.doit.utenti.Esperto;
import it.unicam.dmr.doit.utenti.Progettista;
import it.unicam.dmr.doit.utenti.Proponente;
import it.unicam.dmr.doit.utenti.Utente;
import it.unicam.dmr.doit.view.ViewEsperto;
import it.unicam.dmr.doit.view.ViewProgettista;
import it.unicam.dmr.doit.view.ViewProponente;

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

		Utente matteo = new Utente("Matteo099", "Matteo", "Romagnoli");
		Proponente roberto = new Proponente("roberto_cesetti_company", "Roberto ", "Cesetti ", "Montegiorgio",
				"Teconologia ", "Sommo srl");
		Progettista damiano = new Progettista("damiano_cacciarelli", "Dam", "Cacchiu");
		Esperto lello = new Esperto("espertcoglione", "g41", "Diversi");
		//damiano.getCurriculum().setCompetenze("Onnisciente.");
		//damiano.getCurriculum().setDatiPersonali("Nato a Deneb (costellazione del cigno) il 33/0/666.");
		doit.utenti.aggiungiInscritto(matteo);
		doit.utenti.aggiungiInscritto(roberto);
		doit.utenti.aggiungiInscritto(damiano);
		doit.utenti.aggiungiInscritto(lello);

		ViewProponente viewProponente = new ViewProponente(new ControllerProponente(doit, roberto));
		ViewProgettista viewProgettista = new ViewProgettista(new ControllerProgettista(doit, damiano));
		ViewEsperto viewEsperto = new ViewEsperto(new ControllerEsperto(doit, lello));

		s = new Scanner(System.in);
		startMessage();
		System.out.println("DOIT\nOpzioni:\n- 1 proponi porgetto\n- 2 modifica competenze\n- 3 valuta progetto");
		while (s.hasNext()) {
			try {
				switch (Integer.parseInt(s.nextLine())) {
				case 1:
					System.out.println("Sei nel ruolo di proponente - Bentornato " + roberto.toString());
					viewProponente.proponiProgetto(s);
					break;
				case 2:
					System.out.println("Sei nel ruolo di progettista - Bentornato " + damiano.toString());
					viewProgettista.modificaCompetenze(s);
					break;
				case 3:
					System.out.println("Sei nel ruolo di esperto - Bentornato " + lello.toString());
					viewEsperto.valutaProgetto(s);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				System.err.println("Errore generico: " + e);
			}
			System.out.println("DOIT\nOpzioni:\n- 1 proponi porgetto\n- 2 modifica competenze\n- 3 valuta progetto");
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
}
