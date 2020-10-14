package com.cognizant.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.model.IPTreatmentPackages;
import com.cognizant.entity.SpecialistDetails;



@FeignClient(url = "${offerings.url}",name = "${offerings.name}")
public interface IPTreatmentOfferingsClient 
{
	@GetMapping("/iptreatmentpackages")
	public List<IPTreatmentPackages> getAllPackages(@RequestHeader(name = "Authorization") String token);
	
	@GetMapping("/iptreatmentpackageByName/{packageName}")
	public List<IPTreatmentPackages> getPackageByName(@RequestHeader(name = "Authorization") String token,@PathVariable("packageName")String packageName) ;
	
	@GetMapping("/specialists")
	public List<SpecialistDetails> getAllSpecialists(@RequestHeader(name = "Authorization") String token);
}
