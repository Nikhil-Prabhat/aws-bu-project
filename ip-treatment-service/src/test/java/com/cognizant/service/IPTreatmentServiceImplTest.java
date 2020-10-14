package com.cognizant.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.client.AuthClient;
import com.cognizant.client.AuthResponse;
import com.cognizant.client.IPTreatmentOfferingsClient;
import com.cognizant.entity.PatientDetail;
import com.cognizant.entity.SpecialistDetails;
import com.cognizant.entity.TreatmentPlan;
import com.cognizant.exception.PackageNotFoundException;
import com.cognizant.model.IPTreatmentPackages;
import com.cognizant.model.InsuranceInfo;
import com.cognizant.model.PackageDetails;
import com.cognizant.repository.PatientDetailRepository;
import com.cognizant.repository.TreatmentPlanRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPTreatmentServiceImplTest 
{
//	@InjectMocks
//	private IPTreatmentServiceImpl ipTreatmentServiceImpl;
//	
//	@Mock
//	private PatientDetailRepository patientDetailRepository;
//	
//	@Mock
//	private TreatmentPlanRepository treatmentPlanRepository;
//	
//	@Mock
//	private AuthClient authClient;
//	
//	@Mock
//	private IPTreatmentOfferingsClient ipTreatmentOfferingsClient;
//	
//	@Mock
//	private IPTreatmentPackages ipTreatmentPackages;
//	
//	@Mock
//	private PackageDetails packageDetails;
//	
//	@Mock
//	private SpecialistDetails specialistDetails;
//	
//	@Mock
//	private TreatmentPlan treatmentPlan;
//	
//	@Mock
//	private PatientDetail patientDetail;
//	
//	@Mock
//	private AuthResponse authResponse;
//	
//	@Before
//	public void setup()
//	{
//		ipTreatmentPackages.setAilmentId(12);
//		ipTreatmentPackages.setAilmentCategory("ABC");
//		packageDetails.setTreatmentPackageName("ABCD");
//		packageDetails.setTestDetails("XYZ");
//		packageDetails.setCost(3000);
//		packageDetails.setTreatmentDuration(4);
//		ipTreatmentPackages.setPackageDetails(packageDetails);
//		specialistDetails.setSpecialistId(15);
//		specialistDetails.setName("XYZ");
//		specialistDetails.setAreaOfExpertise("PQRS");
//		specialistDetails.setExperienceInYears(5);
//		specialistDetails.setContactNumber(new Long(1234567890));
//		
//		
//		patientDetail.setPatientId(7);
//		patientDetail.setName("MNO");
//		patientDetail.setAge(50);
//		patientDetail.setAilment("XYZ");
//		patientDetail.setTreatmentPackageName("ABCD");
//		patientDetail.setTreatmentCommencementDate(new Date());
//		
//		
//		treatmentPlan.setTreatmentId(1234);
//		treatmentPlan.setPackageName("ABCD");
//		treatmentPlan.setTestDetails("XYZ");
//		treatmentPlan.setCost(3000);
//		treatmentPlan.setSpecialist(specialistDetails);
//		treatmentPlan.setTreatmentCommencementDate(new Date());
//		treatmentPlan.setTreatmentEndDate(new Date());
//		
//		
//	}
//	
//	@Test
//	public void testFormulateTreatmentTimetable() throws PackageNotFoundException
//	{
//		List<IPTreatmentPackages> list=new ArrayList<>();
//		List<SpecialistDetails> list2=new ArrayList<>();
//		list.add(ipTreatmentPackages);
//		list2.add(specialistDetails);
//		Mockito.when(ipTreatmentOfferingsClient.getPackageByName("token","ABCD")).thenReturn(list);
//		Mockito.when(ipTreatmentOfferingsClient.getAllSpecialists("token")).thenReturn(list2);
//		
//		Mockito.when(patientDetailRepository.save(patientDetail)).thenReturn(patientDetail);
//		Mockito.when(treatmentPlanRepository.save(treatmentPlan)).thenReturn(treatmentPlan);
//		
//		Mockito.when(ipTreatmentServiceImpl.formulateTreatmentTimetable
//				("token", "MNO", 50, "XYZ", "ABCD", new Date())).thenReturn(treatmentPlan);
////		assertEquals(ipTreatmentServiceImpl;
//	}
	
	@InjectMocks
	private IPTreatmentServiceImpl ipTreatmentServiceImpl;
	
	@Mock
	private PatientDetailRepository patientDetailRepository;
	
	@Mock
	private TreatmentPlanRepository treatmentPlanRepository;
	
	@Mock
	private AuthClient authClient;
	
	@Mock
	private IPTreatmentOfferingsClient ipTreatmentOfferingsClient;
	
	@Mock
	private IPTreatmentPackages ipTreatmentPackages;
	
	@Mock
	private PackageDetails packageDetails;
	
	@Mock
	private SpecialistDetails specialistDetails;
	
	@Mock
	private TreatmentPlan treatmentPlan;
	
	@Mock
	private PatientDetail patientDetail;
	
	@Mock
	private InsuranceInfo insuranceInfo;
	
	@Mock
	private AuthResponse authResponse;
	
	IPTreatmentPackages ipTreatmentPackages2;
	SpecialistDetails specialistDetails2;
	PatientDetail patientDetail2;
	TreatmentPlan treatmentPlan2;
	
	@Before
	public void setup()
	{
		packageDetails=new PackageDetails("Package 1", "Test 1", 3000, 4);
		PackageDetails packageDetails2=new PackageDetails("Package 2", "Test 2", 2000, 5);
		ipTreatmentPackages=new IPTreatmentPackages(12, "Urology", packageDetails);
		ipTreatmentPackages2=new IPTreatmentPackages(15, "Urology", packageDetails2);
		specialistDetails=new SpecialistDetails();
		specialistDetails.setSpecialistId(15);
		specialistDetails.setName("Aman");
		specialistDetails.setAreaOfExpertise("Urology");
		specialistDetails.setExperienceInYears(5);
		specialistDetails.setContactNumber(new Long(1234567890));
		specialistDetails2=new SpecialistDetails(10,"Ajay","Urology",10,new Long(1234567890));
		
		
		patientDetail=new PatientDetail();
		patientDetail.setPatientId(7);
		patientDetail.setName("Amit");
		patientDetail.setAge(50);
		patientDetail.setAilment("Urology");
		patientDetail.setTreatmentPackageName("Package 1");
		patientDetail.setTreatmentCommencementDate(new Date());
		patientDetail2=new PatientDetail(6, "Ram", 25, "Urology", "Package 2", new Date());
		
		treatmentPlan=new TreatmentPlan();
		treatmentPlan.setTreatmentId(1234);
		treatmentPlan.setPackageName("Package 1");
		treatmentPlan.setTestDetails("Test 1");
		treatmentPlan.setCost(3000);
		treatmentPlan.setSpecialist(specialistDetails);
		treatmentPlan.setTreatmentCommencementDate(new Date());
		treatmentPlan.setTreatmentEndDate(new Date());
		treatmentPlan2=new TreatmentPlan(5678, "Package 2", "Test 2", 2000, specialistDetails2, new Date(), new Date());
		
		
	}
	
	@Test
	public void testJuniorFormulateTreatmentTimetable() throws PackageNotFoundException
	{
		List<IPTreatmentPackages> list=new ArrayList<>();
		List<SpecialistDetails> list2=new ArrayList<>();
		list.add(ipTreatmentPackages);
		list2.add(specialistDetails);
		Mockito.when(ipTreatmentOfferingsClient.getPackageByName("token","Package 1")).thenReturn(list);
		Mockito.when(ipTreatmentOfferingsClient.getAllSpecialists("token")).thenReturn(list2);
		
		Mockito.when(patientDetailRepository.save(patientDetail)).thenReturn(patientDetail);
		Mockito.when(treatmentPlanRepository.save(treatmentPlan)).thenReturn(treatmentPlan);
		
		
		Mockito.when(ipTreatmentServiceImpl.formulateTreatmentTimetable
				("token", "Amit", 50, "Urology", "Package 1", new Date())).thenReturn(treatmentPlan);
		
		
		
	}
	
	@Test
	public void testSeniorFormulateTreatmentTimetable() throws PackageNotFoundException
	{
		List<IPTreatmentPackages> list=new ArrayList<>();
		List<SpecialistDetails> list2=new ArrayList<>();
		list.add(ipTreatmentPackages2);
		list2.add(specialistDetails2);
		Mockito.when(ipTreatmentOfferingsClient.getPackageByName("token","Package 2")).thenReturn(list);
		Mockito.when(ipTreatmentOfferingsClient.getAllSpecialists("token")).thenReturn(list2);
		
		Mockito.when(patientDetailRepository.save(patientDetail2)).thenReturn(patientDetail2);
		Mockito.when(treatmentPlanRepository.save(treatmentPlan2)).thenReturn(treatmentPlan2);
		
		
		Mockito.when(ipTreatmentServiceImpl.formulateTreatmentTimetable
				("token", "Ram", 25, "Urology", "Package 2", new Date())).thenReturn(treatmentPlan2);
		
		
		
	}
	@Test
	public void testGetInsurance() 
	{
		Mockito.when(patientDetailRepository.getPatient("Amit", "Package 1")).thenReturn(patientDetail);
		Mockito.when(treatmentPlanRepository.getTreatmentPlanDetails("Urology", "Package 1")).thenReturn(treatmentPlan);
		ipTreatmentServiceImpl.getInsurance("token","Amit", "Urology", "Package 1");
	}
	
	@Test
	public void testGetPatient() 
	{
		List<PatientDetail> list = new ArrayList<>();
		list.add(patientDetail);
		list.add(patientDetail2);
		
		Mockito.when(ipTreatmentServiceImpl.getAllPatients()).thenReturn(list);
		assertNotNull(list);
	}
}
