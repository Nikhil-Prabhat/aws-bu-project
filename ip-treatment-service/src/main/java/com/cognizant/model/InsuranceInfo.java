package com.cognizant.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InsuranceInfo {

	private String name;
	private String ailment;
	private String treatmentPackageName;
	private Integer cost;
}
