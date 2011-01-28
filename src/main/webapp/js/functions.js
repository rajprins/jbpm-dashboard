

function showAlert(msg) {
	alert(msg);
}

function msgNotAvailable() {
	alert('Sorry, this function is not yet available...', 'Oops');
}

function applyFilter() {
	document.getElementsByName('name')[0].value=document.getElementsByName('name_i')[0].value;
	document.getElementsByName('version')[0].value=document.getElementsByName('version_i')[0].value;
	document.getElementById('sf').submit();
	return false;
}