package it.unicam.dmr.doit.dataTransferObject.progetto;

import java.util.List;

import javax.validation.constraints.NotNull;

import it.unicam.dmr.doit.controller.Utils;

public class TagListDto {
	
	@NotNull(message = Utils.nonNullo)
	private List<TagDto> tags;
	
	public TagListDto() { }
	
	public TagListDto(List<TagDto> tags){
		this.tags = tags;
	}
	
	public List<TagDto> getTags() {
		return tags;
	}
	
	public void setTags(List<TagDto> tags) {
		this.tags = tags;
	}
}
