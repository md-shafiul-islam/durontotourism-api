/**
 * @author Md. Shafiul Islam
 */

$(document).ready(function() {

	$('.hightLightText').summernote();
	$('.description').summernote();
	$('.editor').summernote();
	
	$('.itVendor').on("change", sendDataToController);
	

	function sendDataToController(e) {

		console.log("data Change!!")

		var element = $(this);
		var id = element.val();

		var aId = {};

		aId["id"] = id;

		console.log("ID: " + id)
		console.log("Change Data: " + element.val());

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/product/vendor-detail",
			data : JSON.stringify(aId),
			dataType : 'json',
			cache : false,
			timeout : 600000,
			success : function(data) {

				console.log(data);

				var vElement = "";
				
				var startTag = "<div class='col-md-6'>";
				var endTag = "</div>";
				
				var sLabel = "<label class='inf-label'>";
				var eLabel = "</label>";
				
				console.log("Vendor Id: " +data.vGenId);
				console.log("Vendor Company Name: " +data.companyName);
				
				vElement += "<div class='row'>";
				
					vElement += "<div class='row mp-10'> <h2> Vendor Info</h2>";
					
						vElement += "<div class='col-md-12'> ";
		
							vElement += sLabel + "Vendor Id: "+ eLabel;
							vElement += sLabel + "Company Name: " + data.companyName + eLabel;
						
						vElement += "</div>";
					
					vElement += "</div>";
				
					//Personal info start
					vElement += "<div class='row mp-10'>";
				
				
					$.each(data.contactPersons, function(key, value) {

						vElement += startTag;
						
							vElement += "<h3> Person Information: </h3>";
					
							vElement += sLabel + "Name: " + value.name + eLabel;
						
							vElement += sLabel + "Phone No. 1: "+ value.phoneNo  + eLabel;
							vElement += sLabel + "Phone No. 2: "+ value.phoneNo2 + eLabel;
						
							vElement += sLabel +  "Email: "+ value.email + eLabel;

							vElement += endTag;
					
					});
				
					vElement += endTag; // End row //Personal info 
				
					vElement += "<div class='row mp-10'>"; // Start row Address

					$.each(data.addresses, function(key, value) {

					
						vElement += startTag;
					
							vElement += "<h3> Address Information: </h3>";
					
							
							vElement += sLabel + "House: "+ value.house  + eLabel;
							
						
							vElement += sLabel + "Street: "+ value.street  + eLabel;
							vElement += sLabel + "Village/Area: " + value.village + eLabel;
							
							vElement += sLabel + "Zip Code: " + value.zip_code + eLabel;
						
							vElement += sLabel +  "City: " + value.city + eLabel;
							vElement += sLabel + "County: '"+ value.country.isoCode + "' "+value.country.name + eLabel;

							vElement += endTag;
				
					});
				
					vElement += endTag; // End row Address
				
				vElement += "</div>";
				
				console.log(vElement);
				
				$("#jsonLoadVendor").html(vElement);

			},
			error : function(e) {

				console.log(e);

				/*
				 * var json = "<h4>Ajax Response</h4><pre>" +
				 * e.responseText + "</pre>"; $('#feedback').html(json);
				 * 
				 * console.log("ERROR : ", e); $("#btn-search").prop("disabled",
				 * false);
				 */

			}
		});

		return false;

	}
	
	

});