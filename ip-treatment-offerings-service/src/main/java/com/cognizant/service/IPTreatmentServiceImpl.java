package com.cognizant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.entity.IPTreatmentPackages;
import com.cognizant.entity.SpecialistDetails;
import com.cognizant.repository.IPTreatmentRepository;
import com.cognizant.repository.SpecialistRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IPTreatmentServiceImpl implements IPTreatmentService {

	@Autowired
	private IPTreatmentRepository treatmentrepo;

	@Autowired
	private SpecialistRepository specialistrepo;

	@Override
	public List<IPTreatmentPackages> getAllPackages() {
		log.debug("Start: {}", "getAllPackages");

		List<IPTreatmentPackages> findAll = treatmentrepo.findAll();

		log.debug("End: {}", "getAllPackages");
		return findAll;
	}

	@Override
	public List<IPTreatmentPackages> getPackageByName(String packageName) {
		log.debug("Start: {}", "getPackageByName");
		List<IPTreatmentPackages> pack = treatmentrepo.findBytreatmentPackageName(packageName);

		log.debug("End: {}", "getPackageByName");
		return pack;
	}

	@Override
	public List<SpecialistDetails> getAllSpecialists() {
		log.debug("Start: {}", "getAllSpecialists");
		List<SpecialistDetails> allSpecialists = specialistrepo.findAll();

		log.debug("End: {}", "getAllSpecialists");
		return allSpecialists;
	}

}
