package cz.jares.pavel.dto.geoname;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jaresp
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Result {

	@JsonProperty("totalResultsCount")
	private Long size;
	
	@JsonProperty("geonames")
	private List<City> items; 
	
	public Result() {
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public List<City> getItems() {
		return items;
	}

	public void setItems(List<City> items) {
		this.items = items;
	}
	
}
