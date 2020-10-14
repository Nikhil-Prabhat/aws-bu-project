package com.cognizant.controller;

import java.text.ParseException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.client.AuthClient;
import com.cognizant.client.InsuranceClient;
import com.cognizant.client.IpTreatmentOfferingsClient;
import com.cognizant.client.TreatmentClient;
import com.cognizant.exception.PackageNotFoundException;
import com.cognizant.model.IPTreatmentPackages;
import com.cognizant.model.InitiateClaim;
import com.cognizant.model.InsurerDetail;
import com.cognizant.model.PDetail;
import com.cognizant.model.PatientDetail;
import com.cognizant.model.SpecialistDetails;
import com.cognizant.model.TreatmentPlan;
import com.cognizant.model.UserLoginCredential;
import com.cognizant.model.UserToken;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebController {

	private String token = "";

	@Autowired
	AuthClient authClient;

	@Autowired
	IpTreatmentOfferingsClient offeringsClient;

	@Autowired
	TreatmentClient treatmentClient;

	@Autowired
	InsuranceClient insuranceClient;

	@GetMapping("/login")
	public ModelAndView getLoginPage() {
		log.debug("Start : {}", "getLoginPage");
		ModelAndView m = new ModelAndView("login");
		log.debug("End : {}", "getLoginPage");
		return m;
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public ModelAndView postLogin(@ModelAttribute("userlogin") UserLoginCredential userlogin,
			HttpServletRequest request) {
		log.debug("Start : {}", "postLogin");
		UserToken res = null;
		try {
			res = authClient.login(userlogin);
			log.debug("Credentials : {}", res);
		} catch (Exception e) {
			log.error("Exception Thrown : {}", "postLogin");
			ModelAndView modelandview = new ModelAndView("login");
			modelandview.addObject("failstatus", true);
			return modelandview;
		}
		request.getSession().setAttribute("token", "Bearer " + res.getAuthToken());
		request.getSession().setAttribute("name", userlogin.getUserid());
		log.debug("End : {}", "postLogin");
		return viewAllPackages(request);

	}

	@RequestMapping(path = "/viewallpackages", method = RequestMethod.GET)
	public ModelAndView viewAllPackages(HttpServletRequest request) {
		log.debug("Start : {}", "viewAllPackages");
		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");
		if (token != null) {

			ModelAndView modelandview = new ModelAndView("offerings");

			List<IPTreatmentPackages> allPackages = offeringsClient.getAllPackages(token);

			modelandview.addObject("allPackages", allPackages);
			modelandview.addObject("name", name);
			log.debug("End : {}", "viewAllPackages");
			return modelandview;
		} else {
			log.error("Token Expired : {}", "viewAllPackages");
			return new ModelAndView("login");
		}

	}

	@RequestMapping(path = "/getspecialists", method = RequestMethod.GET)
	public ModelAndView getAllSpecialistes(HttpServletRequest request) {
		log.debug("Start : {}", "getAllSpecialistes");

		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");
		if (token != null) {
			List<SpecialistDetails> allSpecialists = offeringsClient.getAllSpecialists(token);

			ModelAndView modelandview = new ModelAndView("specialists");
			modelandview.addObject("allspecialists", allSpecialists);
			log.debug("End : {}", "getAllSpecialistes");
			return modelandview;
		} else {
			log.error("Token Expired : {}", "getAllSpecialistes");
			return new ModelAndView("login");

		}
	}

	@RequestMapping(path = "/getallpatients", method = RequestMethod.GET)
	public ModelAndView getPatientsPage(HttpServletRequest request) {
		log.debug("Start : {}", "getPatientsPage");
		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");
		if (token != null) {
			List<PatientDetail> allPatients = treatmentClient.getAllPatients(token);

			ModelAndView modelandview = new ModelAndView("patients");
			modelandview.addObject("allPatients", allPatients);
			log.debug("End : {}", "getPatientsPage");
			return modelandview;
		} else {
			log.error("Token Expired : {}", "getPatientsPage");
			return new ModelAndView("login");

		}
	}

	@RequestMapping(path = "/registerpatient", method = RequestMethod.GET)
	public ModelAndView getRegisterPatientsPage(HttpServletRequest request) {

		log.debug("Start : {}", "getRegisterPatientsPage");
		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");
		if (token != null) {

			ModelAndView modelandview = new ModelAndView("register-patients");
			log.debug("End : {}", "getRegisterPatientsPage");
			return modelandview;
		} else {
			log.error("Token Expired : {}", "getRegisterPatientsPage");
			return new ModelAndView("login");

		}
	}

	@RequestMapping(path = "/registerpatient", method = RequestMethod.POST)
	public ModelAndView getTreatmentPlanPage(@ModelAttribute("patient") PDetail patient, HttpServletRequest request)
			throws PackageNotFoundException, ParseException {

		log.debug("Start : {}", "getTreatmentPlanPage");
		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");
		if (token != null) {

			TreatmentPlan treatmentPlan = treatmentClient.formulateTreatmentTimetable(token, patient.getName(),
					patient.getAge(), patient.getAilment(), patient.getTreatmentPackageName(),
					patient.getTreatmentCommencementDate());

			ModelAndView modelandview = new ModelAndView("patient-treatment-plan");
			modelandview.addObject("patientdetails", patient);
			modelandview.addObject("treatmentPlan", treatmentPlan);
			log.debug("End : {}", "getTreatmentPlanPage");
			return modelandview;
		} else {
			log.error("Token Expired : {}", "getTreatmentPlanPage");
			return new ModelAndView("login");

		}
	}

	@RequestMapping(path = "/insurerdetails", method = RequestMethod.GET)
	public ModelAndView getInsurerDetailsPage(HttpServletRequest request) {
		log.debug("Start : {}", "getInsurerDetailsPage");
		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");
		if (token != null) {

			List<InsurerDetail> allInsurer = insuranceClient.getAllInsurer(token);

			ModelAndView modelandview = new ModelAndView("insurer-details");
			modelandview.addObject("allInsurer", allInsurer);
			log.debug("End : {}", "getInsurerDetailsPage");
			return modelandview;
		} else {
			log.error("Token Expired : {}", "getInsurerDetailsPage");
			return new ModelAndView("login");

		}
	}

	@RequestMapping(path = "/claiminsurance", method = RequestMethod.GET)
	public ModelAndView getInitiateClaimPage(HttpServletRequest request) {
		log.debug("Start : {}", "getInitiateClaimPage");
		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");

		if (token != null) {
			String patientName = request.getParameter("name");
			String ailment = request.getParameter("ailment");
			String packageName = request.getParameter("pkg");

			InitiateClaim claim = new InitiateClaim();
			ModelAndView modelandview = new ModelAndView("claim-insurance");
			claim.setAilment(ailment);
			claim.setTreatmentPackageName(packageName);
			claim.setPatientname(patientName);
			modelandview.addObject("claim", claim);
			log.debug("End : {}", "getInitiateClaimPage");
			return modelandview;
		} else {
			log.error("Token Expired : {}", "getInitiateClaimPage");
			return new ModelAndView("login");

		}
	}

	@RequestMapping(path = "/claiminsurance", method = RequestMethod.POST)
	public ModelAndView getInitiateClaimData(@ModelAttribute("claim") InitiateClaim claim, HttpServletRequest request,
			BindingResult result) {
		log.debug("Start : {}", "getInitiateClaimData");
		String token = (String) request.getSession().getAttribute("token");
		String name = (String) request.getSession().getAttribute("name");

		if (token != null) {

			ModelAndView modelandview = null;

			try {
				int amount = insuranceClient.balanceamounttobepaid(token, claim);

				modelandview = new ModelAndView("insurance");
				modelandview.addObject("initiateclaim", claim);
				modelandview.addObject("amount", amount);

			} catch (Exception e) {
				modelandview = new ModelAndView("claim-insurance");
				log.error("Exception Thrown : {}", "getInitiateClaimData");
				modelandview.addObject("error", true);
			}
			log.debug("End : {}", "getInitiateClaimData");
			return modelandview;

		} else {
			log.error("Token Expired : {}", "getInitiateClaimData");
			return new ModelAndView("login");

		}
	}

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		log.debug("Start : {}", "logout");
		session.setAttribute("token", null);
		session.setAttribute("name", null);
		session.invalidate();
		ModelAndView modelAndView = new ModelAndView("login");
		log.debug("End : {}", "logout");
		return modelAndView;
	}

}
