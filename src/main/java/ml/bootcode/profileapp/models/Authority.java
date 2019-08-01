/**
 *
 */
package ml.bootcode.profileapp.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author sunnybatabyal
 *
 */
@Entity
public class Authority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToMany(mappedBy = "authorities")
	@JsonIgnore
	private List<Designation> designations;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the designations
	 */
	public List<Designation> getDesignations() {
		return designations;
	}

	/**
	 * @param designations the designations to set
	 */
	public void setDesignations(List<Designation> designations) {
		this.designations = designations;
	}
}
