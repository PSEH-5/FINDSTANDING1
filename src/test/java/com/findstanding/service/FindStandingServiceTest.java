package com.findstanding.service;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class FindStandingServiceTest {
	
	@InjectMocks
	FindStandingService findStandingService;
	
	@Mock
	RestTemplate restTemplate;
	

	private String getAllCountryUrl="getAllCountryUrl";
	private String getAllLeagueUrl="getAllLeagueUrl";
	private String getAllStandingUrl="getAllStandingUrl";
	private String apiKey="apiKey";
	
	@Before
	public void setUp(){
		
	}

}
