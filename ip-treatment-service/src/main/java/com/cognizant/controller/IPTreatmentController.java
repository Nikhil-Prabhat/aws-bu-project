package com.cognizant.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.client.AuthClient;
import com.cognizant.client.AuthResponse;
import com.cognizant.entity.PatientDetail;
import com.cognizant.entity.TreatmentPlan;
import com.cognizant.exception.PackageNotFoundException;
import com.cognizant.model.InsuranceInfo;
import com.cognizant.service.IPTreatmentServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/treatment")
@Slf4j
public class IPTreatmentController {
	@Autowired
	private IPTreatmentServiceImpl serviceImpl;

	@Autowired
	private AuthClient authClient;

	@GetMapping("/formulateTreatmentTimetable")
	public ResponseEntity<?> formulateTreatmentTimetable(@RequestHeader(name = "Authorization") String token,
			@RequestParam("name") String name, @RequestParam("age") int age, @RequestParam("ailment") String ailment,
			@RequestParam("treatmentPackageName") String treatmentPackageName,
			@RequestParam("treatmentCommencementDate") String treatmentCommencementDate)
			throws PackageNotFoundException, ParseException {
		log.debug("Start : {}", "formulateTreatmentTimeTable");
		AuthResponse authResponse = authClient.verifyToken(token);

		if (authResponse.isValid()) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sf.parse(treatmentCommencementDate);
			TreatmentPlan formulateTreatmentTimetable = serviceImpl.formulateTreatmentTimetable(token, name, age,
					ailment, treatmentPackageName, date);
			
			log.debug("End : {}", "formulateTreatmentTimeTable");
			return new ResponseEntity<>(formulateTreatmentTimetable, HttpStatus.OK);
		} else {
			log.error("Access Denied : {}", "formulateTreatmentTimeTable");
			return new ResponseEntity<>("You are not LoggedIn", HttpStatus.FORBIDDEN);
		}

	}

	@GetMapping("/insurance")
	public ResponseEntity<?> getInsurance(@RequestHeader(name = "Authorization") String token,
			@RequestParam("name") String name, @RequestParam("ailment") String ailment,
			@RequestParam("treatmentPackageName") String treatmentPackageName) {
		log.debug("Start : {}", "getInsurance");
		AuthResponse authResponse = authClient.verifyToken(token);

		if (authResponse.isValid()) {
			InsuranceInfo insurance = serviceImpl.getInsurance(token,name, ailment, treatmentPackageName);
			
			log.debug("End : {}", "getInsurance");
			return new ResponseEntity<>(insurance, HttpStatus.OK);
		} else {
			log.error("Access Denied : {}", "getInsurance");
			return new ResponseEntity<>("You are not LoggedIn", HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping("/getallpatients")
	public ResponseEntity<?> getAllPatients(@RequestHeader(name = "Authorization") String token) {
		log.debug("Start : {}", "getAllPatients");
		AuthResponse authResponse = authClient.verifyToken(token);

		if (authResponse.isValid()) {
			List<PatientDetail> allPatients = serviceImpl.getAllPatients();
			log.debug("End : {}", "getAllPatients");
			return new ResponseEntity<>(allPatients, HttpStatus.OK);
		} else {
			log.error("Access Denied : {}", "getAllPatients");
			return new ResponseEntity<>("You are not LoggedIn", HttpStatus.FORBIDDEN);
		}
	}

}
