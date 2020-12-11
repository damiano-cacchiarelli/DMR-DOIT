package it.unicam.dmr.doit.utenti.curriculum;

public class Competenze {

	private String settore;
	private String lingue;
	
	public Competenze(String settore, String lingue) {
	
		this.settore = settore;
		this.lingue = lingue;
	}
	
	public String getSettore() {
		return settore;
	}
	
	public void setSettore(String settore) {
		this.settore = settore;
	}
	
	public String getLingue() {
		return lingue;
	}
	
	public void setLingue(String lingue) {
		this.lingue = lingue;
	}
	
	
	
}
