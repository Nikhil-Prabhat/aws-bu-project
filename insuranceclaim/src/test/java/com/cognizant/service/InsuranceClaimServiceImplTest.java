package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.client.IPTreatmentServiceClient;
import com.cognizant.entity.InitiateClaim;
import com.cognizant.entity.InsurerDetail;
import com.cognizant.model.InsuranceInfo;
import com.cognizant.repository.InitiateClaimRepository;
import com.cognizant.repository.InsurerDetailRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsuranceClaimServiceImplTest {

	@Mock
	private InitiateClaim claim;

	@Mock
	private InsuranceInfo info;

	@Mock
	private InsurerDetail insurerdetail;

	@Mock
	private InitiateClaimRepository initateRepo;

	@Mock
	private InsurerDetailRepository insurerRepo;

	@Mock
	private IPTreatmentServiceClient treatmentclient;

	@InjectMocks
	private InsuranceClaimServiceImpl insurerclaimservice;

	@Before
	public void setup() {
		claim = new InitiateClaim();
		claim.setId(1);
		claim.setAilment("Urology");
		claim.setInsurerName("ABC");
		claim.setPatientname("XYZ");
		claim.setTreatmentPackageName("Package 1");

		info = new InsuranceInfo();
		info.setName("XYZ");
		info.setAge(22);
		info.setAilment("Urology");
		info.setCost(3000);
		info.setTreatmentPackageName("Package 1");

		insurerdetail = new InsurerDetail();
		insurerdetail.setDisbursementDuration(5);
		insurerdetail.setInsuranceAmountLimit(500);
		insurerdetail.setInsurerId(123);
		insurerdetail.setInsurerName("ABC");
		insurerdetail.setInsurerPackageName("Package 5");

	}

	@Test
	public void testGetAllInsurerDetail() {

		Mockito.when(insurerRepo.findAll()).thenReturn(new ArrayList<InsurerDetail>());
		List<InsurerDetail> getallInsurerDetail = insurerclaimservice.getallInsurerDetail();
		assertNotNull(getallInsurerDetail);

	}

	@Test
	public void testgetInsurerByName() {

		Mockito.when(insurerRepo.findByName(insurerdetail.getInsurerPackageName()))
				.thenReturn(new ArrayList<InsurerDetail>());
		List<InsurerDetail> getallInsurerDetail = insurerclaimservice
				.getInsurerByName(insurerdetail.getInsurerPackageName());
		assertNotNull(getallInsurerDetail);
	}

	@Test
	public void testbalanceamounttobepaid() {

		Mockito.when(initateRepo.save(claim)).thenReturn(claim);
		Mockito.when(insurerRepo.findInsurer(claim.getInsurerName())).thenReturn(insurerdetail);
		Mockito.when(treatmentclient.getInsurance("token", claim.getPatientname(), claim.getAilment(),
				claim.getTreatmentPackageName())).thenReturn(info);
		int balanceamounttobepaid = insurerclaimservice.balanceamounttobepaid("token", claim);
		assertEquals(2500, balanceamounttobepaid);

	}

}