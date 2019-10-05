package cz.jares.pavel.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import cz.jares.pavel.dto.City;
import cz.jares.pavel.dto.ResultSet;

/**
 * 
 * @author jaresp
 *
 */
public interface MapService extends Cache {

	/**
	 *  Method for find information about cities in GeoNames.org
	 *  
	 * @param name - search pattern
	 * @param pageable - define paggind, null means to use default by GeoNames.org
	 * @return converted rearchResult
	 */
	@Cacheable(value="mapResultData")
	public ResultSet<City> find(String name, Pageable pageable);
	
	@CacheEvict(value="mapResultData")
	public default void evictCache() {
	}
	
}
