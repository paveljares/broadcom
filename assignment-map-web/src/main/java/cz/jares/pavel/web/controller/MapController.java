package cz.jares.pavel.web.controller;

import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.jares.pavel.dto.City;
import cz.jares.pavel.dto.PageInfo;
import cz.jares.pavel.dto.ResultSet;
import cz.jares.pavel.service.MapService;

/**
 * 
 * @author jaresp
 *
 */
@Controller
@RequestMapping("/map")
public class MapController {

	private static final Logger LOG=LoggerFactory.getLogger(MapController.class);
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private MapService mapService;
	
	/**
	 *  Same method like {@link MapController#search(String, Map)}, just this method find all values
	 * because there is missing filtering string.
	 * 
	 * @param parameters
	 * @return
	 */
	@RequestMapping(value="/search", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultSet<City> search(@RequestParam Map<String, String> parameters) {
		return search(new String(), parameters);
	}
	
	/**
	 *  This method find all cities, which are mathing to the search pattern in attribute text. Search
	 * result using paging via attributes page and size. They are fetch from URL parameters.
	 *  If size of page is zero or less, it will be ignored. Page is counted from zero and negative value
	 * will be ignored. 
	 * 
	 * @param text
	 * @param parameters
	 * @return
	 */
	@RequestMapping(value="/search/{text}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResultSet<City> search(@PathVariable("text") String text, @RequestParam Map<String, String> parameters) {
		LOG.debug("#MCs01: call for cities: {}", text);
		
		final Pageable pageable=mapper.map(parameters, PageInfo.class);
		final ResultSet<City> output=mapService.find(text, pageable);
		
		if (LOG.isDebugEnabled()) {
			if ((output==null) || CollectionUtils.isEmpty(output.getItems())) {
				LOG.debug("#MCs02: no result found for {}", text);	
			} else {
				LOG.debug("#MCs03: found {} records", output.getItems().size());
			}
		}
		
		return output;
	}
	
}
