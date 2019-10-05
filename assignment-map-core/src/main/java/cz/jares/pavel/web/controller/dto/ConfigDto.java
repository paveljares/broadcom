package cz.jares.pavel.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jaresp
 *
 */
public class ConfigDto {

	@JsonProperty("gmapApiKey")
	public String gmapApiKey;
	
	public ConfigDto() {
	}

	public String getGmapApiKey() {
		return gmapApiKey;
	}

	public void setGmapApiKey(String gmapApiKey) {
		this.gmapApiKey = gmapApiKey;
	}
	
}
