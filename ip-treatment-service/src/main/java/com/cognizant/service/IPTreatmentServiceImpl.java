package com.cognizant.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.client.IPTreatmentOfferingsClient;
import com.cognizant.entity.PatientDetail;
import com.cognizant.entity.TreatmentPlan;
import com.cognizant.exception.PackageNotFoundException;
import com.cognizant.model.IPTreatmentPackages;
import com.cognizant.model.InsuranceInfo;
import com.cognizant.entity.SpecialistDetails;
import com.cognizant.repository.PatientDetailRepository;
import com.cognizant.repository.TreatmentPlanRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IPTreatmentServiceImpl implements IPTreatmentService {

	@Autowired
	private IPTreatmentOfferingsClient client;

	@Autowired
	private PatientDetailRepository patientrepo;

	@Autowired
	private TreatmentPlanRepository treatmentrepo;

	@Override
	public TreatmentPlan formulateTreatmentTimetable(String token, String name, int age, String ailment,
			String treatmentPackageName, Date treatmentCommencementDate) throws PackageNotFoundException {
		log.debug("Start : {}", "formulateTreatmentTimeTable");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(treatmentCommencementDate);

		PatientDetail detail = new PatientDetail();
		detail.setName(name);
		detail.setAge(age);
		detail.setAilment(ailment);
		detail.setTreatmentPackageName(treatmentPackageName);
		detail.setTreatmentCommencementDate(treatmentCommencementDate);

		patientrepo.save(detail);

		TreatmentPlan plan = new TreatmentPlan();
		List<IPTreatmentPackages> packageByName = client.getPackageByName(token, treatmentPackageName);

		for (IPTreatmentPackages packages : packageByName) {

			if (packages.getAilmentCategory().equalsIgnoreCase(ailment)) {
				plan.setPackageName(packages.getPackageDetails().getTreatmentPackageName());
				plan.setTestDetails(packages.getPackageDetails().getTestDetails());
				plan.setCost(packages.getPackageDetails().getCost());
				plan.setTreatmentCommencementDate(treatmentCommencementDate);

				Calendar c = Calendar.getInstance();
				c.setTime(treatmentCommencementDate);
				c.add(Calendar.DATE, (packages.getPackageDetails().getTreatmentDuration() * 7));
				Date endDate = c.getTime();
				plan.setTreatmentEndDate(endDate);
			} else {

			}
		}

		List<SpecialistDetails> allSpecialists = client.getAllSpecialists(token);

		if (treatmentPackageName.equalsIgnoreCase("Package 1")) {
			int min = Integer.MAX_VALUE;
			for (SpecialistDetails details : allSpecialists) {
				if (details.getAreaOfExpertise().equalsIgnoreCase(ailment)) {
					if (min > details.getExperienceInYears()) {
						min = details.getExperienceInYears();
					}
				}
			}
			for (SpecialistDetails details : allSpecialists) {
				if (details.getAreaOfExpertise().equalsIgnoreCase(ailment)) {
					if (min == details.getExperienceInYears()) {
						plan.setSpecialist(details);
					}
				}
			}

		} else {
			int max = Integer.MIN_VALUE;
			for (SpecialistDetails details : allSpecialists) {
				if (details.getAreaOfExpertise().equalsIgnoreCase(ailment)) {
					if (max < details.getExperienceInYears()) {
						max = details.getExperienceInYears();
					}
				}
			}
			for (SpecialistDetails details : allSpecialists) {
				if (details.getAreaOfExpertise().equalsIgnoreCase(ailment)) {
					if (max == details.getExperienceInYears()) {
						plan.setSpecialist(details);
					}
				}
			}
		}

		treatmentrepo.save(plan);

		log.debug("End : {}", "formulateTreatmentTimeTable");
		return plan;
	}

	@Override
	public InsuranceInfo getInsurance(String token, String name, String ailment, String treatmentPackageName) {
		log.debug("Start : {}", "getInsurance");
		List<IPTreatmentPackages> packageByName = client.getPackageByName(token, treatmentPackageName);
		int cost = 0;
		for (IPTreatmentPackages i : packageByName) {
			if (i.getAilmentCategory().equals(ailment)) {
				cost = i.getPackageDetails().getCost();
				break;
			}
		}

		InsuranceInfo insuranceInfo = new InsuranceInfo();
		insuranceInfo.setName(name);
		insuranceInfo.setAilment(ailment);
		insuranceInfo.setCost(cost);
		insuranceInfo.setTreatmentPackageName(treatmentPackageName);

		log.debug("End : {}", "getInsurance");
		return insuranceInfo;
	}

	@Override
	public List<PatientDetail> getAllPatients() {
		log.debug("Start : {}", "getAllPatients");
		List<PatientDetail> findAll = patientrepo.findAll();

		log.debug("End : {}", "getAllPatients");
		return findAll;

	}

}
