package cz.jares.pavel.service;
/**
 * 
 *  Interface for marking services, which use ehCache. All those beans should implements this
 * methods to allow evict caches (all used in the beans).
 *  Via this interface is possible to evict all cachies in whole application.
 * 
 * @author jaresp
 *
 */
public interface Cache {

	/**
	 *  Evict all caches used by bean.
	 * 
	 *  For implementation please add annotation {@link org.springframework.cache.annotation.Cacheable} 
	 * with references to all used caches. 
	 */
	public void evictCache();
	
}
