package it.unicam.dmr.doit.view;

import java.util.Scanner;

public class View implements IView {

	public static int selezionaProgetto(Scanner s) {
		System.out.println("Inserisci id progetto");
		return Integer.parseInt(s.nextLine());
	}
}
