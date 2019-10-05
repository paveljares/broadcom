package cz.jares.pavel.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.jares.pavel.service.Cache;
import cz.jares.pavel.web.controller.dto.ConfigDto;

/**
 *
 *  This controller serve base information about application and
 * it contains other methods for controlling of state. 
 * 
 * @author jaresp
 *
 */
@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Value("${app.version}")
	private String version;
	
	@Value("#{configDto}")
	private ConfigDto configDto;

	@Autowired
	private List<Cache> cachies;
	
	/**
	 *  To get version of application. This version is generated on building (see 
	 * file app.properties).
	 * 
	 * @return version of application
	 */
	@RequestMapping(value="/version", method=RequestMethod.GET, produces=MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String getVersion() {
		return version;
	}

	/**
	 *  This method return the base method which contains the base configuration values.
	 * 
	 * @return ConfigDto with base configuration
	 */
	@RequestMapping(value="/config", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ConfigDto getConfig() {
		return configDto;
	}
	
	/**
	 *  Method evict all ehCaches using inside the application.
	 */
	@RequestMapping(value="/evictCache", method=RequestMethod.GET)
	public void evictCache() {
		for (final Cache cache : cachies) {
			cache.evictCache();
		}
	}
	
}
