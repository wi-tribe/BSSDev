function toggleTextOut(e,txtval) {
	if(e.value=="") e.value = txtval;
}
function toggleTextIn(e,txtval) {
	if(e.value==txtval) e.value="";
	else e.select();
}

$(document).ready(function() {
	$("a[@class=external]").attr('target', '_blank');
	selectField();
});


function searchboxValidate(){

	sb = document.getElementById('txtsearch').value;
	sb=trimAll(sb);
	if(sb=="Search here..." || sb==""){
	document.getElementById('txtsearch').focus();
	return false;
	}
	return true;
}
function selectField(){
	$(".form input, .form textarea, .form select").click(function()
		{
			$(this).addClass('selected');
			//$(this).parent().parent().addClass('rowselected');
		}
	)
	
	$(".form input, .form textarea, .form select").blur(function()
		{
			$(this).removeClass('selected');
			//$(this).parent().parent().removeClass('rowselected');
		}
	)
	
	
}

