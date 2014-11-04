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



