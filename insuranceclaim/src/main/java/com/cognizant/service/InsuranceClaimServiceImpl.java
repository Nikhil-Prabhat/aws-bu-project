package com.cognizant.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.client.IPTreatmentServiceClient;
import com.cognizant.entity.InitiateClaim;
import com.cognizant.entity.InsurerDetail;
import com.cognizant.model.InsuranceInfo;
import com.cognizant.repository.InitiateClaimRepository;
import com.cognizant.repository.InsurerDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InsuranceClaimServiceImpl implements InsuranceClaimService {
	@Autowired
	private InitiateClaimRepository initiateClaimRepository;

	@Autowired
	private InsurerDetailRepository insurerDetailRepository;

	@Autowired
	private IPTreatmentServiceClient ipclient;

	@Override
	public List<InsurerDetail> getallInsurerDetail() {
		log.debug("Start : {}", "getallInsurerDetail");
		List<InsurerDetail> findAll = insurerDetailRepository.findAll();

		log.debug("End : {}", "getallInsurerDetail");
		return findAll;
	}

	@Override
	public List<InsurerDetail> getInsurerByName(String insurerpackageName) {
		log.debug("Start : {}", "getInsurerByName");
		List<InsurerDetail> findByName = insurerDetailRepository.findByName(insurerpackageName);

		log.debug("End : {}", "getInsurerByName");
		return findByName;
	}

	@Override
	public int balanceamounttobepaid(String token, InitiateClaim initiateclaim) {
		log.debug("Start : {}", "balanceamounttobepaid");
		InsuranceInfo insurance = ipclient.getInsurance(token, initiateclaim.getPatientname(),
				initiateclaim.getAilment(), initiateclaim.getTreatmentPackageName());

		initiateClaimRepository.save(initiateclaim);
		log.debug("InitiateClaim : {}", initiateclaim);
		InsurerDetail findInsurer = insurerDetailRepository.findInsurer(initiateclaim.getInsurerName());

		int amount = insurance.getCost() - findInsurer.getInsuranceAmountLimit();

		log.debug("End : {}", "balanceamounttobepaid");
		return amount;

	}
}
