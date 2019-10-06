package cz.jares.pavel.web.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.jares.pavel.dto.City;
import cz.jares.pavel.dto.PageInfo;
import cz.jares.pavel.dto.ResultSet;
import cz.jares.pavel.service.MapService;

/**
 * 
 * @author jaresp
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class MapControllerTest {

	@InjectMocks
	private MapController mapController=new MapController();
	
	@Mock
	private MapService mapService;
		
	private MockMvc mockMvc;
	
	private void addParameterTest(int order, int page, int size, String pattern) {
		final ResultSet<City> resultSet=new ResultSet<>();
		resultSet.setSize(order);
		final PageInfo pageInfo=new PageInfo();
		pageInfo.setPageNumber(page);
		pageInfo.setPageSize(size);
		when(mapService.find(pattern, pageInfo)).thenReturn(resultSet);
	}
	
	@Before
	public void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(mapController).build();
		
		ReflectionTestUtils.setField(mapController, "mapper", new DozerBeanMapper(Collections.singletonList("META-INF/dozer/mapping.xml")));
		
		addParameterTest(1, 1, 5, "test");
		addParameterTest(2, 10, 20, "test2");
		addParameterTest(3, 0, 0, "test3");
		addParameterTest(4, 8, 0, "");
	}

	@Test
	public void testListPeopleInGroup() throws IllegalArgumentException, InvocationTargetException, Exception {
		mockMvc	.perform(get("/map/search/test?page=1&size=5"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'size':1}"));
		
		mockMvc	.perform(get("/map/search/test2?page=10&size=20"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'size':2}"));
		
		mockMvc	.perform(get("/map/search/test3"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'size':3}"));
		
		mockMvc	.perform(get("/map/search?page=8"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'size':4}"));	
	}
	
}
