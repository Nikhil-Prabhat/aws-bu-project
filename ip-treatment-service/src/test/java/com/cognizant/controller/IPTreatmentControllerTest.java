package com.cognizant.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.cognizant.client.AuthClient;
import com.cognizant.client.AuthResponse;
import com.cognizant.entity.PatientDetail;
import com.cognizant.entity.SpecialistDetails;
import com.cognizant.entity.TreatmentPlan;
import com.cognizant.exception.PackageNotFoundException;
import com.cognizant.model.IPTreatmentPackages;
import com.cognizant.model.PackageDetails;
import com.cognizant.repository.PatientDetailRepository;
import com.cognizant.repository.TreatmentPlanRepository;
import com.cognizant.service.IPTreatmentServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPTreatmentControllerTest {

	@Mock
	private IPTreatmentServiceImpl ipTreatmentServiceImpl;
	
	@InjectMocks
	private IPTreatmentController ipTreatmentController;
	
	@Mock
	private AuthClient authClient;
	
	
	
	@Mock
	private PatientDetailRepository patientDetailRepository;
	
	@Mock
	private TreatmentPlanRepository treatmentPlanRepository;
	
	@Mock
	private PatientDetail patientDetail;
	
	@Mock
	private SpecialistDetails specialistDetails;
	
	@Mock
	private TreatmentPlan treatmentPlan;
	
	@Mock
	private PackageDetails packageDetails;
	
	@Mock
	private IPTreatmentPackages ipTreatmentPackages;
	
	private IPTreatmentPackages ipTreatmentPackages2;
	private SpecialistDetails specialistDetails2;
	private PatientDetail patientDetail2;
	private TreatmentPlan treatmentPlan2;
	
	@Test
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
	public void testGetFormulateTreatmentTimetablePass() throws PackageNotFoundException, ParseException
	{
		Mockito.when(authClient.verifyToken("token")).thenReturn(new AuthResponse("admin", "admin", true));
		ResponseEntity<?> getFormulateTreatmentTimetable=ipTreatmentController.
				formulateTreatmentTimetable("token", "Amit", 50, "Urology", "Package 1", "2020-10-20");
		assertNotNull(getFormulateTreatmentTimetable);
	}
	
	@Test
	public void testGetFormulateTreatmentTimetableFail() throws PackageNotFoundException, ParseException
	{
		Mockito.when(authClient.verifyToken("token")).thenReturn(new AuthResponse("admin", "admin", false));
		ResponseEntity<?> getFormulateTreatmentTimetable=ipTreatmentController.
				formulateTreatmentTimetable("token", "Amit", 50, "Urology", "Package 1", "2020-10-20");
		assertEquals(403,getFormulateTreatmentTimetable.getStatusCodeValue());
	}
	
	@Test
	public void testGetInsurancePass()
	{
		Mockito.when(authClient.verifyToken("token")).thenReturn(new AuthResponse("admin", "admin", true));
		ResponseEntity<?> getInsurance=ipTreatmentController.getInsurance("token", "Amit", "Urology", "Package 1");
		assertNotNull(getInsurance);
	}
	
	@Test
	public void testGetInsuranceFail()
	{
		Mockito.when(authClient.verifyToken("token")).thenReturn(new AuthResponse("admin", "admin", false));
		ResponseEntity<?> getInsurance=ipTreatmentController.getInsurance("token", "Amit", "Urology", "Package 1");
		assertEquals(403,getInsurance.getStatusCodeValue());
	}
}
