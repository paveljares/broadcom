package cz.jares.pavel.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cz.jares.pavel.service.Cache;
import cz.jares.pavel.web.controller.dto.ConfigDto;

/**
 * 
 * @author jaresp
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {
	
	private int evictCounter;
	
	private ApiController apiController=new ApiController() {
		{
			ReflectionTestUtils.setField(this, "version", "mockedVersion");
			
			ConfigDto configDto=new ConfigDto();
			configDto.setGmapApiKey("gmap");
			ReflectionTestUtils.setField(this, "configDto", configDto);
			
			Cache cache=Mockito.mock(Cache.class);
			ReflectionTestUtils.setField(this, "cachies", Collections.singletonList(cache));

			ReflectionTestUtils.setField(this, "cachies", Collections.singletonList(new Cache() {
				@Override
				public void evictCache() {
					ApiControllerTest.this.evictCounter++;
				}
			}));
		}
	};
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
	}

	@Test
	public void testListPeopleInGroup() throws IllegalArgumentException, InvocationTargetException, Exception {
		mockMvc	.perform(get("/api/version"))
				.andExpect(status().isOk())
				.andExpect(content().string("mockedVersion"));
		
		mockMvc	.perform(get("/api/config"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'gmapApiKey':'gmap'}"));
		
		Assert.assertEquals(0, evictCounter);
		mockMvc	.perform(get("/api/evictCache"))
				.andExpect(status().isOk());
		Assert.assertEquals(1, evictCounter);
	}
}
