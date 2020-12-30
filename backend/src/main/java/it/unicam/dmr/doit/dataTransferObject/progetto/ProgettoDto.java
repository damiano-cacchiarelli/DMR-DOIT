package it.unicam.dmr.doit.dataTransferObject.progetto;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.progetto.Fase;
import it.unicam.dmr.doit.progetto.Stato;
import it.unicam.dmr.doit.progetto.Valutazione;
import it.unicam.dmr.doit.utenti.ruoli.Progettista;
import it.unicam.dmr.doit.utenti.ruoli.Proponente;

public class ProgettoDto {
	
	@NotNull
	@NotBlank
	private int id;
	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String obiettivi;
	@NotNull
	@NotBlank
	private String requisiti;
	@NotNull
	@NotBlank
	private Stato stato;
	@NotNull
	@NotBlank
	private Fase fase;
	@NotNull
	@NotBlank
	private Date creatoIl;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proponente_id", nullable = false)
	private Proponente proponente;
	
	
	/*DA MODIFICARE*/
	@ManyToMany(mappedBy = "candidature", fetch = FetchType.LAZY)
    private Set<Progettista> progettisti = new HashSet<>();
	@Transient
	private LinkedList<Valutazione> listaValutazioni = new LinkedList<Valutazione>();
	
	public ProgettoDto() {}
	
	public ProgettoDto(@NotNull @NotBlank String nome, @NotNull @NotBlank String obiettivi,
			@NotNull @NotBlank String requisiti, @NotNull Stato stato, @NotNull Fase fase, Date creatoIl,
			Proponente proponente, Set<Progettista> progettisti, LinkedList<Valutazione> listaValutazioni) {

		this.nome = nome;
		this.obiettivi = obiettivi;
		this.requisiti = requisiti;
		this.stato = stato;
		this.fase = fase;
		this.creatoIl = creatoIl;
		this.proponente = proponente;
		this.progettisti = progettisti;
		this.listaValutazioni = listaValutazioni;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getObiettivi() {
		return obiettivi;
	}
	public void setObiettivi(String obiettivi) {
		this.obiettivi = obiettivi;
	}
	public String getRequisiti() {
		return requisiti;
	}
	public void setRequisiti(String requisiti) {
		this.requisiti = requisiti;
	}
	public Stato getStato() {
		return stato;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}
	public Fase getFase() {
		return fase;
	}
	public void setFase(Fase fase) {
		this.fase = fase;
	}
	public Date getCreatoIl() {
		return creatoIl;
	}
	public void setCreatoIl(Date creatoIl) {
		this.creatoIl = creatoIl;
	}
	public Proponente getProponente() {
		return proponente;
	}
	public void setProponente(Proponente proponente) {
		this.proponente = proponente;
	}
	public Set<Progettista> getProgettisti() {
		return progettisti;
	}
	public void setProgettisti(Set<Progettista> progettisti) {
		this.progettisti = progettisti;
	}
	public LinkedList<Valutazione> getListaValutazioni() {
		return listaValutazioni;
	}
	public void setListaValutazioni(LinkedList<Valutazione> listaValutazioni) {
		this.listaValutazioni = listaValutazioni;
	}
	
	
	
}
