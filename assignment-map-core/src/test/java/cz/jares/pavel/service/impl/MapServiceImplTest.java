package cz.jares.pavel.service.impl;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import cz.jares.pavel.dto.City;
import cz.jares.pavel.dto.PageInfo;
import cz.jares.pavel.dto.ResultSet;
import cz.jares.pavel.dto.geoname.Result;
import cz.jares.pavel.service.MapService;
import cz.jares.pavel.test.HttpUtils;
import cz.jares.pavel.test.TestContext;

/**
 * 
 * @author jaresp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={TestContext.class})
public class MapServiceImplTest {

	@MockBean
	private RestTemplate restTemplate;
	
	@Autowired
	private MapService mapService;
	
	@Value("${geonames.endpoint.search}")
	private String endpoint;
	
	@Value("${geonames.username}")
	private String username;
	
	@Before
	@After
	public void evictCache() {
		mapService.evictCache();
	}
	
	private cz.jares.pavel.dto.geoname.City getRestCity(int order, int lat, int lng) {
		cz.jares.pavel.dto.geoname.City city=new cz.jares.pavel.dto.geoname.City();
		city.setCountry("Czech" + order);
		city.setName("Prague" + order);
		city.setPopulation(order * 10_000_000L);
		city.setLatitude(BigDecimal.valueOf(lat));
		city.setLongitude(BigDecimal.valueOf(lng));
		return city;
	}
	
	private void assertEquals(City a, int order, int lat, int lng) {
		Assert.assertNotNull(a);
		Assert.assertEquals("Czech" + order, a.getCountry());
		Assert.assertEquals("Prague" + order, a.getName());
		Assert.assertNotNull(a.getPopulation());
		Assert.assertEquals(order * 10_000_000L, a.getPopulation().longValue());
				
		Assert.assertNotNull(a.getGps());
		Assert.assertNotNull(a.getGps().getLatitude());
		Assert.assertEquals(BigDecimal.valueOf(lat), a.getGps().getLatitude());
		Assert.assertNotNull(a.getGps().getLongitude());
		Assert.assertEquals(BigDecimal.valueOf(lng), a.getGps().getLongitude());
	}
	
	@Test
	public void testBaseCall() {
		PageInfo pi=new PageInfo();
		pi.setPageNumber(12);
		pi.setPageSize(10);

		Result callResult=new Result();
		callResult.setSize(123L);
		
		callResult.setItems(Arrays.asList(
			getRestCity(1, 10, 20),
			getRestCity(2, 50, 0)
		));
		
		Mockito.
			when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Result.class))).
			thenReturn(callResult);
		
		ResultSet<City> out=mapService.find("praha", pi);
		
		Assert.assertNotNull(out);
		Assert.assertEquals(123, out.getSize());
		Assert.assertEquals(120, out.getOffset());
		Assert.assertNotNull(out.getItems());
		Assert.assertEquals(2, out.getItems().size());
		
		assertEquals(out.getItems().get(0), 1, 10, 20);
		assertEquals(out.getItems().get(1), 2, 50, 0);
	}
	
	@Test
	public void testEmpty() {
		PageInfo pi=new PageInfo();

		Result callResult=new Result();
		callResult.setSize(0L);
		
		Mockito.
			when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(Result.class))).
			thenReturn(callResult);
		
		ResultSet<City> out=mapService.find("prague", pi);
		
		Assert.assertNotNull(out);
		Assert.assertEquals(0, out.getSize());
		Assert.assertNull(out.getItems());
	}
	
	private Pageable getPageable(int pageNumber, int pageSize) {
		PageInfo out=new PageInfo();
		out.setPageNumber(pageNumber);
		out.setPageSize(pageSize);
		return out;
	}
	
	private void assertParams(String url, String username, String q, String maxRows, String startRow) throws MalformedURLException {
		Map<String, String> params=new HashMap<>();
		params.put("username", username);
		params.put("q", q);
		params.put("maxRows", maxRows);
		params.put("startRow", startRow);
		
		HttpUtils.assertUri(endpoint, params, url);
	}
		
	@Test
	public void testUri() throws MalformedURLException {
		MapServiceImplWrapper w=new MapServiceImplWrapper();
		assertParams(
			w.getUri("name", getPageable(0, 0)),
			username, "name", null, "0"
		);
		assertParams(
			w.getUri("name2", getPageable(5, 10)),
			username, "name2", "10", "50"
		);
		assertParams(
			w.getUri(null, getPageable(-1, -1)),
			username, "", null, "0"
		);
	}
	
	private class MapServiceImplWrapper extends MapServiceImpl {
		
		public MapServiceImplWrapper() {
			ReflectionTestUtils.setField(this, "endpoint", endpoint);
			ReflectionTestUtils.setField(this, "username", username);
		}
		
		@Override
		public String getUri(String name, Pageable pageable) {
			return super.getUri(name, pageable);
		}
	}
	
}
