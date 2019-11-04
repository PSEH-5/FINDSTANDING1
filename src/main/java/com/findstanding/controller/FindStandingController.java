package com.findstanding.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.findstanding.dto.StandingDTO;
import com.findstanding.service.FindStandingService;

@RestController
@RequestMapping("/standings")
public class FindStandingController {
	
	@Autowired
	FindStandingService findStandingService;
	
	
 @GetMapping("/findStanding/{countryName}/{leagueName}/{teamName}")
 public ResponseEntity<StandingDTO> findStanding(@PathVariable String countryName, @PathVariable String leagueName, @PathVariable String teamName){
	ResponseEntity<StandingDTO> responseEntity=null;
	 if(StringUtils.isBlank(countryName)&& StringUtils.isBlank(leagueName) && StringUtils.isBlank(teamName)){
		 responseEntity= new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}else{
		StandingDTO standingDTO=findStandingService.findStanding(countryName, leagueName, teamName);
		if(null !=standingDTO){
			 responseEntity= new ResponseEntity<>(standingDTO,HttpStatus.OK);
		}
	}
	return responseEntity;
	 
 }
 

}
