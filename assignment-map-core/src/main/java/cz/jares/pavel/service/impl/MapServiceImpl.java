package cz.jares.pavel.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cz.jares.pavel.dto.City;
import cz.jares.pavel.dto.ResultSet;
import cz.jares.pavel.dto.geoname.Result;
import cz.jares.pavel.service.MapService;

/**
 * 
 * @author jaresp
 *
 */
@Service
public class MapServiceImpl implements MapService {

	@Value("${geonames.endpoint.search}")
	private String endpoint;
	
	@Value("${geonames.username}")
	private String username;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Mapper mapper;
	
	/**
	 *  Method construct URI by endpoint URL and other parameters
	 *   
	 * @param name - search pattern
	 * @param pageable - pagging of result
	 * @return - URI of service endpoint with parameters
	 */
	protected String getUri(String name, Pageable pageable) {
		final UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(endpoint);
		builder	.queryParam("username", username)
				.queryParam("q", name==null?new String():name);
		
		if (pageable!=null) {
			if (pageable.getPageSize()>0) {
				builder.queryParam("maxRows", pageable.getPageSize());
			}
			if (pageable.getOffset()>=0) {
				builder.queryParam("startRow", pageable.getOffset());
			}
		}
		
		return builder.toUriString();
	}
	
	@Override
	public ResultSet<City> find(String name, Pageable pageable) {
		final String uri=getUri(name, pageable);
		final Result result=restTemplate.getForObject(uri, Result.class);
	
		@SuppressWarnings("unchecked")
		final ResultSet<City> output=mapper.map(result, ResultSet.class);
		output.setOffset(pageable.getOffset());
		
		return output;
	}

}
