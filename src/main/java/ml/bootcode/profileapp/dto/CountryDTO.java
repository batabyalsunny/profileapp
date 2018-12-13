/**
 * 
 */
package ml.bootcode.profileapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ml.bootcode.profileapp.models.State;

import javax.persistence.*;
import java.util.List;

/**
 * @author sunnyb
 *
 */
public class CountryDTO {

	private Long id;
	private String name;

	@JsonIgnore
	private List<State> states;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
}
