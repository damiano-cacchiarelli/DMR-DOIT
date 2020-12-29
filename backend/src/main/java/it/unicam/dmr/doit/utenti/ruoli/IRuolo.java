package it.unicam.dmr.doit.utenti.ruoli;

import java.util.Collection;

import it.unicam.dmr.doit.progetto.Progetto;

public interface IRuolo {
	public TipologiaRuolo getRuolo();
	public Collection<Progetto> getProgettiPersonali();
}
