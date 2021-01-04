package it.unicam.dmr.doit.invito;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@SuppressWarnings("serial")
public class InvitoId implements Serializable {
	
	private String id;
	@Enumerated(EnumType.STRING)
	private RuoloSoggetto soggetto;

	public InvitoId() {
	}

	public InvitoId(String id, RuoloSoggetto soggetto) {
		super();
		this.id = id;
		this.soggetto = soggetto;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setSoggetto(RuoloSoggetto soggetto) {
		this.soggetto = soggetto;
	}
	
	public RuoloSoggetto getSoggetto() {
		return soggetto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((soggetto == null) ? 0 : soggetto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof InvitoId))
			return false;
		InvitoId other = (InvitoId) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (soggetto != other.soggetto)
			return false;
		return true;
	}

	public enum RuoloSoggetto {
		MITTENTE, DESTINATARIO
	}
}
