package com.cognizant.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsuranceInfoTest {
	InsuranceInfo insuranceInfo = new InsuranceInfo();

	@Test
	public void testInsuranceInfoAllConstructor() {
		InsuranceInfo info = new InsuranceInfo("Amit", "XYZ", "Package 10", 2500);
		assertEquals(info.getName(), "Amit");
	}

	@Test
	public void testGetName() {
		insuranceInfo.setName("Amit");
		assertEquals(insuranceInfo.getName(), "Amit");
	}

	@Test
	public void testGetAilment() {
		insuranceInfo.setAilment("XYZ");
		assertEquals(insuranceInfo.getAilment(), "XYZ");
	}

	@Test
	public void testGetTtreatmentPackageName() {
		insuranceInfo.setTreatmentPackageName("Package 12");
		assertEquals(insuranceInfo.getTreatmentPackageName(), "Package 12");
	}

	@Test
	public void testGetCost() {
		insuranceInfo.setCost(2000);
		assertEquals(insuranceInfo.getCost(), new Integer(2000));
	}

	@Test
	public void testtoString() {
		String s = insuranceInfo.toString();
		assertEquals(insuranceInfo.toString(), s);
	}

}