/**
 * 
 */

function error(message, type) {
	var err_dom = document.getElementById("error-for-" + type)
	err_dom.innerHTML = message;
}
function validate() {
	error("*","principle");
	error("*","interest");
	var form = document.mc;
	var principle = form.principle.value
	var interest = form.interest.value
	var retval = true;
	if (isNaN(principle) || principle <= 0) {
		error("Principle must be positive.", "principle");
		retval = false;
	}
	
	if (isNaN(interest) || interest < 0 || interest > 100) {
		error("Interset must be between 0 and 100", "interest");
		retval = false;
	}
	return retval;
}

function getRates(principle) { 
	var url = ""; 
	var data = "ajax=yes"; 
	data += "&principle=" + principle; 
	doSimpleAjax(url, data, function(request) {
	 if (request.readyState == 4 && request.status == 200) {
		 var target = document.getElementById("error-for-interest");
		 target.innerHTML = request.responseText;
	 }
	}); 
} 

function updateInterest(interest) {
	document.mc.interest.value = interest;
}
 
function doSimpleAjax(address, data, handler) { 
	var request = new XMLHttpRequest(); 
	request.onreadystatechange = function()	{ handler(request); }; 
	request.open("GET", (address + "?" + data), true); 
	request.send(null); 
} 


