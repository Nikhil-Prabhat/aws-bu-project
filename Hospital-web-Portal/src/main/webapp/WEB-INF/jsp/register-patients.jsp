<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1" name="viewport"
	content="width=device-width, initial-scale=1">
<title>Offerings</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
	integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
	crossorigin="anonymous"></script>



<!-- <link href="css/custom.css" type="text/css" rel="stylesheet">  -->

<script>
	// Disable form submissions if there are invalid fields
	(function() {
		'use strict';
		window.addEventListener('load', function() {
			// Get the forms we want to add validation styles to
			var forms = document.getElementsByClassName('needs-validation');
			// Loop over them and prevent submission
			var validation = Array.prototype.filter.call(forms, function(form) {
				form.addEventListener('submit', function(event) {
					if (form.checkValidity() === false) {
						event.preventDefault();
						event.stopPropagation();
					}
					form.classList.add('was-validated');
				}, false);
			});
		}, false);
	})();
</script>

</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<a class="navbar-brand" href="/viewallpackages">International
			Patient Treatment Management System </a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav  ml-auto">



			</ul>

			<form class="form-inline" style="position: relative; right: 10px" action="/viewallpackages">
				<button class="btn btn-success" type="submit">Home</button>
			</form>

			<form class="form-inline" action="/logout">

				<button class="btn btn-danger" type="submit">Log Out</button>
			</form>
		</div>
	</nav>
	<br>
	<div class="container">

		<h2 class="display-4 text-danger mt-5">Register Patients</h2>
		<form action="/registerpatient" class="needs-validation" novalidate
			method="post" modelAttribute="patient"
			style="position: relative; top: 40px">
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="name"><strong>Name of the Patient</strong></label> <input
						type="text" class="form-control" name="name"
						placeholder="Enter Patient's Name" required>
					<div class="valid-feedback">Valid.</div>
					<div class="invalid-feedback">Please fill out this field.</div>
				</div>
				<div class="form-group col-md-6">
					<label for="age"><strong>Enter Age of the Patient</strong></label>
					<input type="number" class="form-control" name="age"
						placeholder="Enter Patient's Age" required>
					<div class="valid-feedback">Valid.</div>
					<div class="invalid-feedback">Please fill out this field.</div>
				</div>
			</div>

			<div class="form-group">
				<label for="ailment"><strong>Enter the Ailment</strong></label> <select
					class="form-control" name="ailment">
					<option selected="true" disabled="disabled">Enter the
						ailment</option>
					<option value="Urology">Urology</option>
					<option value="Orthopaedics">Orthopaedics</option>
				</select>


				<div class="valid-feedback">Valid.</div>
				<div class="invalid-feedback">Please fill out this field.</div>
			</div>

			<div class="form-group">
				<label for="treatmentPackageName"><strong>Choose
						the Package for Patient</strong></label> <select class="form-control"
					name="treatmentPackageName">
					<option selected="true" disabled="disabled">Choose the
						Package</option>
					<option value="Package 1">Package 1</option>
					<option value="Package 2">Package 2</option>
				</select>
			</div>
			<div class="form-group">

				<label for="treatmentCommencementDate"><strong>Treatment
						Commencement Date</strong></label> <input type="date" class="form-control"
					name="treatmentCommencementDate"
					placeholder="Enter the commencement date in dd/MM/yyyy" required>
				<div class="valid-feedback">Valid.</div>
				<div class="invalid-feedback">Please fill out this field.</div>


			</div>

			<button type="submit" class="btn btn-success">Submit</button>
		</form>
	</div>
</body>
</html>