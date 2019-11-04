package com.findstanding.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.findstanding.dto.CountryDTO;
import com.findstanding.dto.LeagueDTO;
import com.findstanding.dto.StandingDTO;
import com.findstanding.dto.StandingResponseDTOs;

@Service
public class FindStandingService {

	@Value("${country.url}")
	private String getAllCountryUrl;
	@Value("${country.league}")
	private String getAllLeagueUrl;
	@Value("${standing.url}")
	private String getAllStandingUrl;
	
	@Value("${api.key}")
	private String apiKey;

	
	@Autowired
	RestTemplate restTemplate;
	
	
	public StandingDTO findStanding(String countryName,String leagueName, String teamName){
		StandingDTO standingDTO= null;
		String countryId=fetchCountryByName(countryName);
		if(StringUtils.isNotBlank(countryId)){
		String leagueId=fetchLeagueByCountryId(countryId, leagueName);
		if(StringUtils.isNotBlank(leagueId)){
			standingDTO=fetchStandingByLeagueId(leagueId, teamName);
			if(null!=standingDTO ){
				standingDTO.setCountryId(countryId);
			}
		}
		}
		return standingDTO;
	}


	private String fetchCountryByName(String countryName) {
		String countryId=null;
		Map<String,String> map= new HashMap<>();
		map.put("apiKey", apiKey);
		ResponseEntity<List<CountryDTO>> resEntity=restTemplate.exchange(getAllCountryUrl,HttpMethod.GET,null,new ParameterizedTypeReference<List<CountryDTO>>() {
        },map);
		if(null !=resEntity){
			List<CountryDTO> countryDTOs=resEntity.getBody();
			if(null!=countryDTOs&& countryDTOs.size()>0){
				List<CountryDTO> newCountryLst=countryDTOs.stream().filter(conDTO-> conDTO.getCountryName().equalsIgnoreCase(countryName)).collect(Collectors.toList());
				if(null !=newCountryLst&&newCountryLst.size()>0){
					countryId= newCountryLst.get(0).getCountryId();
				}
			}
		}
		return countryId;
        
	}
	
	
	private String fetchLeagueByCountryId(String countyId,String leagueName) {
		String leagueId=null;
		Map<String,String> map= new HashMap<>();
		map.put("apiKey", apiKey);
		map.put("countryId", countyId);
		ResponseEntity<List<LeagueDTO>> resEntity=restTemplate.exchange(getAllLeagueUrl,HttpMethod.GET,null,new ParameterizedTypeReference<List<LeagueDTO>>() {
        },map);
		if(null !=resEntity){
			List<LeagueDTO> leagueDTOs=resEntity.getBody();
			if(null!=leagueDTOs&& leagueDTOs.size()>0){
				List<LeagueDTO> newLeagueLst=leagueDTOs.stream().filter(leagueDTO-> leagueDTO.getCountryName().equalsIgnoreCase(leagueName)).collect(Collectors.toList());
				if(null !=newLeagueLst&&newLeagueLst.size()>0){
					leagueId= newLeagueLst.get(0).getLeagueId();
				}
			}
		}
		return leagueId;
        
	}
	
	
	private StandingDTO fetchStandingByLeagueId(String leagueId,String teamName) {
		Map<String,String> map= new HashMap<>();
		map.put("apiKey", apiKey);
		map.put("leagueId", leagueId);
		ResponseEntity<List<StandingDTO>> resEntity=restTemplate.exchange(getAllStandingUrl,HttpMethod.GET,null,new ParameterizedTypeReference<List<StandingDTO>>() {
        },map);
		if(null !=resEntity){
			return resEntity.getBody().stream().filter(standings->standings.getTeamName().equalsIgnoreCase(teamName)).collect(Collectors.toList()).get(0);
	
		}
		return null;
		
        
	}
	
	
	
}
