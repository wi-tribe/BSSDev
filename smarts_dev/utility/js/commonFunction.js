

var arrDiv=new Array("checklist_div","personal_information_div","product_div","address_div","terms_condition_div","cpe_div","confirm_order_div");
/**
 * @param global variable req
 */
var req;

/**
 * Function showOtherEquip to check for the new hardware (like router)
 * @param formName is used as a form name
 */


/**
 * Function checkAvailability to check username is available or not in IntraISP
 * @param formName is used as a form name
 * @param divName is used as a divname
 */

function checkAvailability(formName,divName)
{
	var username=formName.elements['user_name'].value;
	var uname=username.split("@");
	var uname_len=uname[0].length;
	if (username!="") 
	{ 
		if (uname_len<6)
		{
		  alert("The Length of the field should not be less than 6");
		  formName.elements['user_name'].focus();
		  
		}else if(!formName.elements['user_name'].value.match(/^[A-Za-z 0-9 \. \_]{1,}$/))
		{
			alert("Please enter only character data");
			formName.elements['user_name'].focus();
			return false;
		}
		else
		{
		  new Ajax.Updater(divName, "check_availability.php?username="+username);
		}
	}	
}

/**
 * Function checkAvailability_id to check identification number is exist or not
 * @param formName is used as a form name
 * @param divName is used as a divname
 * @return true
 */
function checkAvailability_id(formName,divName)
{
	var identification_number=formName.elements['identification_number'].value;
	//alert(identification_number);
	new Ajax.Updater(divName, "check_availability_id.php?identification_number="+identification_number);
	return true;
}


/**
 * Function checkUsername to check username is exist is exist then show error message
 * @param formName is used as a form name
 * @param divName is used as a divname
 * @return true/false
 */
function checkUsername(formName)
{
	if(formName.elements['availability'])
	{
		if(formName.elements['availability'].value==1)
		{
			alert("This username is already exist, please enter another username");
			formName.elements['user_name'].focus();
			return false;
		}
		else
			return true;
	}
	else
		return true;
}

/**
 * Function fillValue to assign the value of offer name from product form to confirm order form
 * @return true
 */

function fillValue()
{
	document.confirm_order.product_offer_name.value=document.product.product_offer_name.value;
	return true;
}

/**
 * Function showProduct to display the product list for selected offer in product form
 * @param url is used call the ajax to show the file form specified url
 * @param divName is used as a divname
 */
function showProduct(url,divName)
{
	document.getElementById(divName).style.display="inline";
	x=document.product.billing_cycle.options[document.product.billing_cycle.selectedIndex].value;
	y=document.product.customer_type.options[document.product.customer_type.selectedIndex].value;
	//alert(y);
	//var E=$('sndBox');
	//E.startWaiting('bigBalckWaiting');
	new Ajax.Updater({ success: divName}, url+"?ct="+y+"&bc="+x);	
}


/**
 * Function confirm_showProduct to display the product list for selected offer in confirm order form
 * @param url is used call the ajax to show the file form specified url
 * @param divName is used as a divname
 * @return true
 */
function confirm_showProduct(url,divName)
{
	document.getElementById(divName).style.display="inline";
	x=document.confirm_order.billing_cycle.options[document.confirm_order.billing_cycle.selectedIndex].value;
	y=document.confirm_order.customer_type.options[document.confirm_order.customer_type.selectedIndex].value;
	
	new Ajax.Request(url+"?ct="+y+"&bc="+x, 
		{   method: 'get',   
					 onSuccess: function(transport)
					 {     
						document.getElementById(divName).innerHTML=transport.responseText;
						setTimeout("setFormValue()",800);
					} 
		}); 
	
	return true;
}

/**
 * Function setFormValue to display the product list for selected offer in confirm order form
 * @return true
 */
function setFormValue()
{
	document.confirm_order.product_offer_name.value=document.product.product_offer_name.value;
	document.confirm_order.product_offer_name.disabled=true;
	
	if(document.product.discount)
	{
		if(document.product.discount.value!="")
		{
			if(document.confirm_order.discount)
			{
				document.confirm_order.discount.value=document.product.discount.value;
				document.confirm_order.discount_id.value=document.product.discount_id.value;
			}
		}
		
		if(document.confirm_order.discount)
			document.confirm_order.discount.disabled=true;
	}
	if(document.product.total_offer_amount)
	{	
		if(document.product.total_offer_amount.value!="")
		{
			document.confirm_order.tot_of_amt.value=document.product.total_offer_amount.value;
			document.getElementById("total1").innerHTML="Total: "+document.product.total_offer_amount.value+" JD";
		}
	}
}


/**
 * Function printOrder to open the popup to show the customer contract
 */
function printOrder()
{
	var offer_id=document.confirm_order.product_offer_name.value;
	var ct=document.confirm_order.customer_type.value;
	var bc=document.confirm_order.billing_cycle.value;
	var bd=document.confirm_order.billing_district.value;
	var sd=document.confirm_order.service_district.value;
	
	document.confirm_order.Finish.disabled=false;
	if(document.confirm_order.tax_prayer_id)
	{
		if(document.confirm_order.tax_prayer_id.checked==true)
			var tax=1;
		else
			var tax=0;
	}
	if(document.confirm_order.chknewequip)
	{
		if(document.confirm_order.chknewequip.checked==true)
		{
			var equip_offer_id=document.confirm_order.equip_offer_name1.value;
			window.open('order_detail.php?offer_id='+offer_id+"&ct="+ct+"&bc="+bc+"&bd="+bd+"&sd="+sd+"&tx="+tax+'&eoid='+equip_offer_id+'&st=1','','scrollbars=yes,menubar=no,height=600,width=1000,resizable=yes,toolbar=no,status=no;topmargin=0');	
		}
		else
		{
			window.open('order_detail.php?offer_id='+offer_id+"&ct="+ct+"&bc="+bc+"&bd="+bd+"&sd="+sd+"&tx="+tax,'','scrollbars=yes,menubar=no,height=600,width=1000,resizable=yes,toolbar=no,status=no;topmargin=0');	
		}
	}
	else
	{
		window.open('order_detail.php?offer_id='+offer_id+"&ct="+ct+"&bc="+bc+"&bd="+bd+"&sd="+sd+"&tx="+tax,'','scrollbars=yes,menubar=no,height=600,width=1000,resizable=yes,toolbar=no,status=no;topmargin=0');	
	}
}


/**
 * Function showHideDiv to show or hide the div
 * @param arrDiv is passed the array of div
 * @param divName is used as a div name that has to show
 */
function showHideDiv(arrDiv,divname)
{ 
	for(i=0;i<arrDiv.length;i++)
	{
		if(arrDiv[i]==divname)
			document.getElementById(divname).style.display = 'inline';
		else
			document.getElementById(arrDiv[i]).style.display = 'none';		
	}
}

/**
 * Function CheckEnglishData to allow only english data
 * @return true/false
 */
 function CheckEnglishData(field)
{
	value = field.value;
	if(!value.match('/[a-zA-Z]/'))
	{
		alert("enter English alphabets only");
		field.focus();
	}
}
/*function CheckEnglishData(e)
{
	if(window.event)
		keyPressed = window.event.keyCode; // IE hack
	else
		keyPressed = e.which;
	if((keyPressed>96 && keyPressed<123 )|| (keyPressed>64 && keyPressed<91) )
	{
		return true;
	}
	else
	{
		alert("Please enter english charecters only");
		return false;
	}
//	alert(keyPressed);
}
*/

/**
 * Function checkUsernameAvailability to check username and display message
 * @param formName is used as a form name
 * @param divName is used as a divname
 * @return true/false
 */
function checkUsernameAvailability(formName,divName)
{
	var username=formName.elements['user_name'].value;
	new Ajax.Request("check_username.php?username="+username, 
		{   method: 'get',   
					 onSuccess: function(transport) 
					 {     
					 	var chk=transport.responseText;	
						if(chk=="true")
						{
							return true;
						}
						else
						{
							formName.elements['user_name'].focus();
							document.getElementById(divName).innerHTML="<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'><tr><td width='24%' class='rightAlign tdHeight normalFont'>&nbsp;</td><td width='76%' class='mandatoryRed'>This username is already exist</td><td width='0%' class='rightAlign tdHeight normalFont'>&nbsp;</td><td width='0%'>&nbsp;</td><input type='hidden' name='availability' value='1' /></tr></table>";
							return false;
						}
					} 
		}); 
	
	
}

/**
 * Function checkPasswordMaxLength to check minimum and maximum length of password field
 * @param formName is used as a form name
 * @return true/false
 */
function checkPasswordMaxLength(formName)
{
	//alert(formName.elements['password'].value.length);
	var uname=formName.elements['user_name'].value;
	var username=uname.split("@");
	
	//alert(username[0].length);
	if(username[0].length<6)
	{
		alert("The length of this field should not be less than 6");
		formName.elements['user_name'].focus();
		return false;
	}
	else if(username[0].length>15)
	{
		alert("The length of this field should not be grater than 15");
		formName.elements['user_name'].focus();
		return false;
	}
	else if(formName.elements['password'].value.length>15)
	{
		alert("The length of this field should not be grater than 15");
		formName.elements['password'].focus();
		return false;
	}
	else if(formName.elements['password'].value!=formName.elements['re_type_password'].value)
	{
		alert("Password and re typed Password are not same");
		formName.elements['re_type_password'].focus();
		return false;
	}	
	else
		return true;
}

/**
 * Function checkPassword to check password field and re-type password field are same or not
 * @param formName is used as a form name
 * @return true/false
 */
function checkPassword(formName)
{
	if(formName.elements['password'].value!=formName.elements['re_type_password'].value)
	{
		alert("Password and re typed Password are not same");
		formName.elements['re_type_password'].focus();
		return false;
	}
	else
		return true;
}


/**
 * Function checkAddress to check billing address is same as service address then it copy as it is
 */
function checkAddress()
{
	if(document.address.chkAddress.checked==true)
	{	
		document.address.service_address1.value=document.address.billing_address1.value;
		document.address.service_address2.value=document.address.billing_address2.value;
		document.address.service_post_box.value=document.address.billing_post_box.value;
		document.address.service_district.value=document.address.billing_district.value;
		document.address.service_city.value=document.address.billing_city.value;
		document.address.service_zip_code.value=document.address.billing_zip_code.value;
		document.address.service_day_time.value=document.address.billing_day_time.value;
		document.address.service_mobile.value=document.address.billing_mobile.value;
	}
	else
	{
		document.address.service_address1.value="";
		document.address.service_address2.value="";
		document.address.service_post_box.value="";
		document.address.service_district.value="";
		document.address.service_city.value="";
		document.address.service_zip_code.value="";
		document.address.service_day_time.value="";
		document.address.service_mobile.value="";
	}
	
}

/**
 * Function fillConfirmOrder to fill all the data from other forms into the confirm order as it is
 * @return true/false
 */
function fillConfirmOrder()
{
	for (var i=0; i < confirm_order.elements.length; i++)
	{
		var eles = confirm_order.elements[i];
	
		if(eles.type=='text' || eles.type=='password' || eles.type=='select-one' || eles.type=='textarea')
		{
			eles.value=getParentFormValue(eles.name);
		} else if(eles.type=='radio')
		{
			if(eles.value==getParentFormValue(eles.name))
			eles.checked=true;
		}
	}
	
	if(document.personal_information.tax_prayer_id.checked==true)
	{
		document.confirm_order.tax_prayer_id.checked=true;
	}
	else
	{
		document.confirm_order.tax_prayer_id.checked=false;
	}
	
return true;
}

/**
 * Function getParentFormValue to get the value from parent form
 * @param fieldName is used as a field Name
 * @return the field value
 */
function getParentFormValue(fieldName)
{
	for (var i=0; i < product.elements.length; i++)
	{
		var eles = product.elements[i];
	
		if(eles.name==fieldName && (eles.type=='text' || eles.type=='password' || eles.type=='textarea' || eles.type=='select-one'))
		{
			return eles.value;
		} else if(eles.name==fieldName && eles.type=='radio')
		{
			return getSelectedRadioValue(eval("product."+fieldName));
		}
	}
	
	for (var i=0; i < personal_information.elements.length; i++)
	{
		var eles = personal_information.elements[i];
	
		if(eles.name==fieldName && (eles.type=='text' || eles.type=='password' || eles.type=='textarea' || eles.type=='select-one'))
		{
			return eles.value;	
		} 
		else if(eles.name==fieldName && eles.type=='radio')
		{
			return getSelectedRadioValue(eval("personal_information."+fieldName));
		}
	}

	

	for (var i=0; i < address.elements.length; i++)
	{
		var eles = address.elements[i];
	
		if(eles.name==fieldName && (eles.type=='text' || eles.type=='password' || eles.type=='textarea' || eles.type=='select-one'))
		{
			return eles.value;
		} else if(eles.name==fieldName && eles.type=='radio')
		{
			return getSelectedRadioValue(eval("address."+fieldName));
		}
	}

	for (var i=0; i < terms_condition.elements.length; i++)
	{
		var eles = terms_condition.elements[i];
	
		if(eles.name==fieldName && (eles.type=='text' || eles.type=='password' || eles.type=='textarea' || eles.type=='select-one'))
		{
			return eles.value;
		} else if(eles.name==fieldName && eles.type=='radio')
		{
			return getSelectedRadioValue(eval("terms_condition."+fieldName));
		}
	}
	
	for (var i=0; i < cpe.elements.length; i++)
	{
		var eles = cpe.elements[i];
	
		if(eles.name==fieldName && (eles.type=='text' || eles.type=='password' || eles.type=='textarea' || eles.type=='select-one'))
		{
			return eles.value;
		} else if(eles.name==fieldName && eles.type=='radio')
		{
			return getSelectedRadioValue(eval("cpe."+fieldName));
		}
	}
	
return "";
}


/**
 * Function getSelectedRadio to fill all the radio button data from other forms into the confirm order as it is
 * @param buttonGroup is used as a radio button Name
 * @return the field value
 */

function getSelectedRadio(buttonGroup) {
// returns the array number of the selected radio button or -1 if no button is selected
if (buttonGroup[0]) { // if the button group is an array (one button is not an array)
for (var i=0; i<buttonGroup.length; i++) {
	if (buttonGroup[i].checked) {
	return i
	}
}
} else {
	if (buttonGroup.checked) { return 0; } // if the one button is checked, return zero
	}
	// if we get to this point, no radio button is selected
	return -1;
} // Ends the "getSelectedRadio" function

function getSelectedRadioValue(buttonGroup) {
// returns the value of the selected radio button or "" if no button is selected
var i = getSelectedRadio(buttonGroup);
if (i == -1) {
return "";
} else {
if (buttonGroup[i]) { // Make sure the button group is an array (not just one button)
return buttonGroup[i].value;
} else { // The button group is just the one button, and it is checked
return buttonGroup.value;
}
}
} // Ends the "getSelectedRadioValue" function


/**
 * Function disabledConfirmOrder to disabled the data of confirm order
 * @return true
 */
function disabledConfirmOrder()
{
	for (var i=0; i < confirm_order.elements.length; i++)
	{
		var element = confirm_order.elements[i];		
		
		if(element.type=='select-one')
		element.disabled='disabled';
		
		if(element.type=='radio')
		element.disabled='disabled';
		
		if(element.type=='text' || element.type=='password')
		element.disabled='disabled';
		
		if(element.type=='checkbox')
		element.disabled='disabled';
	}		
	return true;
}

/**
 * Function enabledConfirmOrder to enabled the data of confirm order
 * @return true/false
 */
function enabledConfirmOrder()
{
	for (var i=0; i < confirm_order.elements.length; i++)
	{
		var element = confirm_order.elements[i];		
		
		if(element.type=='select-one')
		element.disabled=false;
		
		if(element.type=='radio')
		element.disabled=false;
		
		if(element.type=='text' || element.type=='password')
		element.disabled=false;
		
		if(element.type=='checkbox')
		element.disabled=false;
	}	
	return true;
}

/**
 * Function checkCondition to check the terms and condtion accepted by the customer or not
 * @return true/false
 */
function checkCondition()
{
	if(document.terms_condition.chkTermsCondition.checked==false)
	{
		alert("Please select Terms & Conditions");
		return false;
	}
	else
		return true;
}

/**
 * Function setOfferDetail to set the offer price in product form
 * @param formName is used as a form name
 * @param i as integer as incremental value
 */
function setOfferDetail(formName,i)
{
	if(product.elements['total_offer_amount_'+i+''].value!="")
		document.product.payment.value=parseFloat(product.elements['total_offer_amount_'+i+''].value);		
}


/**
 * Function openPopup to open the popup for terms and condition
 */
function openPopup()
{
	window.open('terms_cond.php','','scrollbars=yes,menubar=no,height=400,width=500,resizable=yes,toolbar=no,status=no;topmargin=0');
}

/**
 * Function checkMandatoryFields to check the customer type and set the mandatory fields in personal information form
 * @param formName is used as a form name
 * @return true
 */
function checkMandatoryFields(formName)
{
	if(formName.elements['customer_type'].value==2)
	{
		document.getElementById('cn').style.display="inline";
		document.getElementById('fn').style.display="none";
		document.getElementById('ln').style.display="none";
		document.getElementById('db').style.display="none";		
		return true;		
	}
	else
	{
		document.getElementById('fn').style.display="inline";
		document.getElementById('ln').style.display="inline";
		document.getElementById('cn').style.display="none";
		document.getElementById('db').style.display="inline";		
		return true;
	}
}

/**
 * Function setOfferDetail_confirm to set the offer price in confirm order form
 * @param formName is used as a form name
 * @param i as integer as incremental value
 */
function setOfferDetail_confirm(formName,i)
{
	if(confirm_order.elements['total_offer_amount_'+i+''].value!="")
		document.confirm_order.payment.value=parseFloat(confirm_order.elements['total_offer_amount_'+i+''].value);
}

/**
 * Function validateField to check the mandatory fields
 * @param formName is used as a form name
 * @return true/false
 */
function validateField(formName)
{
	//alert(formName.name);
	//alert(document.all["emaildiv1"].style.display);
	if((formName.elements['equip_offer_name1'] && document.all["equip_offer_residential"].style.display=="inline") || (formName.elements['equip_offer_name2'] && document.all["equip_offer_business"].style.display=="inline"))
	{
		if(formName.elements['equip_offer_name1'].value=="" && document.all["equip_offer_residential"].style.display=="inline")
		{
			alert("This field should not be empty");
			formName.elements['equip_offer_name1'].focus();
			return false;
		}
		else if(formName.elements['equip_offer_name2'].value=="" && document.all["equip_offer_business"].style.display=="inline")
		{
			alert("This field should not be empty");
			formName.elements['equip_offer_name2'].focus();
			return false;
		}
		else
			return true;
	}
	else if(!formName.elements['first_name'].value.match(/^[A-Za-z \- \s]{1,}$/))
	{
		alert("Enter only character data");
		formName.elements['first_name'].focus();
		return false;
	}
	else if(!formName.elements['last_name'].value.match(/^[A-Za-z \- \s]{1,}$/))
	{
		alert("Enter only character data");
		formName.elements['last_name'].focus();
		return false;
	}
	else if ((formName.name=="personal_information" && document.all["emaildiv1"].style.display=="inline") || (formName.name=="confirm_order" && document.all["confirm_emaildiv1"].style.display=="inline"))
	{
		
		if(!formName.elements['email_username1'].value.match(/^[A-Za-z 0-9 \. \_]{1,}$/))
		{
			alert("This field should not be empty and enter only character data");
			formName.elements['email_username1'].focus();
			return false;
		}
		else if(formName.elements['email_username1'].length<8)
		{
			alert("Enter minimum 8 characters");
			formName.elements['email_username1'].focus();
			return false
		}
		else if(formName.elements['email_password1'].value=="")
		{
			alert("This field should not be empty");
			formName.elements['email_password1'].focus();
			return false;
		}
		else if(formName.elements['email_password1'].length<8)
		{
			alert("Enter minimum 8 characters");
			formName.elements['email_password1'].focus();
			return false
		}
		else
			return true;
	}
	else if(formName.elements['contact_person'].value=="")
	{
		return true;
	}	
	else if(formName.elements['contact_person'].value!="")
	{
		if(!formName.elements['contact_person'].value.match(/^[A-Za-z \- \s]{1,}$/))
		{
			alert("Enter only character data");
			formName.elements['contact_person'].focus();
			return false;
		}
		else
			return true;
	}
	else
		return true;
	
}

/**
 * Function confirm_checkMandatoryFields to check the customer type and set the mandatory fields in confirm order form
 * @param formName is used as a form name
 * @return true
 */
function confirm_checkMandatoryFields(formName)
{
	//alert(product.elements['customer_type'].value);
	if(product.elements['customer_type'].value==2)
	{
		document.getElementById('c_cn').style.display="inline";
		document.getElementById('c_fn').style.display="none";
		document.getElementById('c_ln').style.display="none";
		document.getElementById('c_db').style.display="none";		
		return true;		
	}
	else
	{
		document.getElementById('c_fn').style.display="inline";
		document.getElementById('c_ln').style.display="inline";
		document.getElementById('c_cn').style.display="none";
		document.getElementById('c_db').style.display="inline";		
		return true;
	}
}

/**
 * Function chkMandFields to check the customer type and set the mandatory fields
 * @param formName is used as a form name from the value taken
 * @param formName1 is used as a form name where the value to set
 * @return true/false
 */
function chkMandFields(formName,formName1)
{
	if(formName.elements['customer_type'].value==2)
	{
		if(formName1.elements['company_name'].value=="")
		{
			alert("This field should not be empty");
			formName1.elements['company_name'].focus();
			return false;
		}
		else
			return true;
	}
	else
	{
		if(formName1.elements['first_name'].value=="")
		{
			alert("This field should not be empty");
			formName1.elements['first_name'].focus();
			return false;
			
			if(!document.getElementById('first_name').value.match(/^[A-Za-z]+$/))
			{
				alert("Enter only character data");
				document.getElementById('first_name').focus();
				return false;
			}
			
		}
		else if(formName1.elements['last_name'].value=="")
		{
			alert("This field should not be empty");
			formName1.elements['last_name'].focus();
			return false;
			
			if(!document.getElementById('last_name').value.match(/^[A-Za-z]+$/))
			{
				alert("Enter only character data");
				document.getElementById('last_name').focus();
				return false;
			}
		}
		else if(formName1.elements['date_of_birth'].value=="")
		{
			alert("Please select date of birth");
			formName1.elements['date_of_birth'].focus();
			return false;
		}
		else
			return true;		
	}
}

/**
 * Function checkField to check the tax examption field as a mandatory fields
 * @param formName is used as a form name from the value taken
 * @return true/false
 */
function checkField(formName)
{
	if(formName.elements['tax_prayer_id'].checked==true)
	{
		if(formName.elements["tax_payer_doc"].value=="")
		{
			alert("This field should not be empty");
			formName.elements["tax_payer_doc"].focus();
			return false;
		}
		else
			return true;
	}
	else
		return true;
}

/**
 * Function showTC to display the terms and condition according to offer selected
 * @param formName is used as a form name
 * @param divName is used as a div name
 * @return true
 */
function showTC(formName,divName)
{	
	var prod=formName.elements['offer_id'].value;
	if(divName=='confirm_tc')
	{
		document.getElementById('confirm_tc').style.display="inline";
		document.getElementById('t_c').style.display="none";
	}
	else
	{
		document.getElementById('confirm_tc').style.display="none";
		document.getElementById('t_c').style.display="inline";
	}
	new Ajax.Updater(divName, 'terms_conditions.php?offer_id='+prod); 
			
	return true;
}


/**
 * Function addDomainName is used to add the domain name "@wi-tribe.com";
 * @param formName is used as a form name
 */
function addDomainName(formName)
{
	var uname=formName.elements['user_name'].value;
	var username=uname.split("@");
	//var uname_len=uname.length;
	if (username=="")
	{
		 alert("This field should not be empty");
		 formName.elements['user_name'].focus();
	} else 
	{
		if(username[1]!='wi-tribe.com')
			formName.elements['user_name'].value=formName.elements['user_name'].value+"@wi-tribe.com";
		else
			formName.elements['user_name'].value=uname;
	}
}

/**
 * Function copyField to copy the value from personal_information to address form
 * @param formName is used as a form name from the value has to take
 * @param formName1 is used as a form name where the value has to be copied
 */
function copyField(formName,formName1)
{
	formName1.elements['billing_mobile'].value=formName.elements['mobile_number'].value;
	formName1.elements['billing_day_time'].value=formName.elements['telephone_number'].value;
}

/**
 * Function filterOffer to submit the product form
 * @param formName is used as a form name
 */
function filterOffer(fornName)
{
	document.product.action="#";
	document.product.submit();
}


/**
 * Function displayOffer to display the offer detail in product form
 * @param formName is used as a form name
 * @param divName is used as a div name
 * @param myinc is used as a incremented value
 * @return true
 */
function displayOffer(formName,divName,myinc)
{
	document.getElementById(divName).style.display="inline";
	var oid=formName.elements['product_offer_name'].value;
	var ct=formName.elements['customer_type'].value;
	var bc=formName.elements['billing_cycle'].value;
		
	if(document.getElementById("unique_name1"))
		document.getElementById("unique_name1").style.display="inline";
	
	if(document.getElementById("unique_name2"))
		document.getElementById("unique_name2").style.display="inline";	
		
	if(document.personal_information.tax_prayer_id)
	{
		if(document.personal_information.tax_prayer_id.checked==true)
			var tax=1;
		else
			var tax=0;
	}
	
	new Ajax.Updater(divName, "offer_detail.php?offer_id="+oid+"&ct="+ct+"&bc="+bc+"&increment="+myinc+"&tx="+tax);	
	total_amt(formName,tax);	
	return true;
}


/**
 * Function total_amt to calculate the total amount including discount
 * @param fname as a form name
 * @return true
 */
function total_amt(fname,tax)
{ 
	var oid=fname.elements['product_offer_name'].value;	
	var ct=fname.elements['customer_type'].value;
	var bid=fname.elements['billing_cycle'].value;
	var formName=fname.name;
	
	if(fname.elements['discount'])
		var disid=fname.elements['discount'].value;
	else
		var disid="";
	
	
	if(formName=="confirm_order")
	{
		if(document.confirm_order.tax_prayer_id)
		{
			if(document.confirm_order.tax_prayer_id.checked==true)
				var tax=1;
			else
				var tax=0;
		}
	}
	//alert(tax);
	
	new Ajax.Request("offer_tax.php?oid="+oid+"&discount_id="+disid+"&ct="+ct+"&bid="+bid+"&tx="+tax, 
		{   method: 'get',   
			 onSuccess: function(transport) 
			 {     
				var price_text=transport.responseText;
				//alert(price_text);
				if(document.getElementById("unique_name1"))
					document.getElementById("unique_name1").innerHTML=price_text;					
				
				if(document.getElementById("unique_name2"))
					document.getElementById("unique_name2").innerHTML=price_text;
				//alert(price_text);
				if(fname.name=="confirm_order")
				{
					//alert(document.confirm_order.tot_of_amt.value);
					if(document.confirm_order.txttotal)
					{
						document.confirm_order.txttotal.value="";
						document.confirm_order.txttotal.value=makeFloatAsRound(parseFloat(document.confirm_order.tot_of_amt.value)+parseFloat(document.confirm_order.equip_tot_of_amt.value));
						document.getElementById("totalamount").innerHTML=document.confirm_order.txttotal.value;
					}
					//new Ajax.Updater('total1', "total_amt.php?total_amt="+total_amount);			
				}
			} 
		});	
	
	
	
}


/**
 * Function sell_checkTaxPayer to check the existing customer has a tax examption or not in sell new offer to existing customer who has cancelled offer
 * @param formName is used as a form name
 * @param divId is used as a div id
 * @return true
 */
function sell_checkTaxPayer(formName,divId)
{
	//alert(formName.elements['tax_prayer_id'].checked);
	if(formName.elements['tax_prayer_id'].checked==true)
		document.getElementById(divId).style.display="inline";
	else
		document.getElementById(divId).style.display="none";
	
	if(formName.name=="confirm_order")
	{
		confirm_displayOffer(formName,"confirm_offer",1);
		
	}
	
	return true;
}

/**
 * Function filterOffer to submit the confirm_order form
 * @param formName is used as a form name
 */
function filterOffer_confrim(fornName)
{
	document.confirm_order.action="#";
	document.confirm_order.submit();
}



/**
 * Function displayEquipOffer to display the offer detail i.e. to sell the other hardware equipment
 * @param formName is used as a form name
 * @param divName is used as a div name
 * @param myinc is used as a incremented value
 * @param tax_st is used as a tax examption
 * @return true
 */
function displayEquipOffer(formName,divName,myinc,tax_st)
{
	if(document.confirm_order.customer_type.value==1)
		var oid=formName.elements['equip_offer_name1'].value;
		
	if(document.confirm_order.customer_type.value==2)
		var oid=formName.elements['equip_offer_name2'].value;
	
	var ct=formName.elements['customer_type'].value;
	var bc=formName.elements['billing_cycle'].value;
	
	if(document.confirm_order.tax_prayer_id)
	{
		if(document.confirm_order.tax_prayer_id.checked==true)
			var tax=1;
		else
			var tax=0;
	}
	
	new Ajax.Request("equip_offer_detail.php?offer_id="+oid+"&ct="+ct+"&bc="+bc+"&increment="+myinc+"&tx="+tax, 
		{   method: 'get',   
			 onSuccess: function(transport) 
			 {     
				//var price_text=transport.responseText;
				document.getElementById(divName).innerHTML=transport.responseText;
				if(formName.name=="confirm_order") 
				{
					//alert(document.confirm_order.tot_of_amt.value);
					if(document.confirm_order.txttotal)
					{
						document.confirm_order.txttotal.value="";
						document.confirm_order.txttotal.value=makeFloatAsRound(parseFloat(document.confirm_order.tot_of_amt.value)+parseFloat(document.confirm_order.equip_tot_of_amt.value));
						//alert(document.confirm_order.txttotal.value);
						document.getElementById("totalamount").innerHTML=document.confirm_order.txttotal.value;
					}
					//new Ajax.Updater('total1', "total_amt.php?total_amt="+total_amount);			
				}
			} 
		}); 
	
	//new Ajax.Updater(divName, "equip_offer_detail.php?offer_id="+oid+"&ct="+ct+"&bc="+bc+"&increment="+myinc+"&tx="+tax);	
	//setTimeout("add_amt()",2500);	
	
	return true;
}


/**
 * Function add_amt to add the offer price and other hardware equipment price
 */
function add_amt()
{
	if(document.confirm_order.chknewequip.checked==true)
	{
		if(document.confirm_order.txttotal)
		{
			document.confirm_order.txttotal.value=parseFloat(document.confirm_order.tot_of_amt.value)+parseFloat(document.confirm_order.equip_tot_of_amt.value);
			var totamt=document.confirm_order.txttotal.value;
			tamt=makeFloatAsRound(totamt);
			document.confirm_order.txttotal.value=tamt;
			document.getElementById("totalamount").innerHTML=tamt;
		}
	}
}

/**
 * Function makeFloatAsRound to round the total amount
 * @param totamt is a total amount
 * @return total amount
 */
function makeFloatAsRound(totamt)
{
	var mf=totamt*100;
	var rd=Math.round(mf);
	var total_amount=rd/100;
	return total_amount;
}

/**
 * Function confirm_displayOffer to display the offer detail in confirm_order form
 * @param formName is used as a form name
 * @param divName is used as a div name
 * @param myinc is used as a incremented value
 * @return true
 */
function confirm_displayOffer(formName,divName,myinc)
{
	var oid=formName.elements['product_offer_name'].value;
	var ct=formName.elements['customer_type'].value;
	var bc=formName.elements['billing_cycle'].value;
	if(document.confirm_order.tax_prayer_id)
	{
		if(document.confirm_order.tax_prayer_id.checked==true)
			var tax=1;
		else
			var tax=0;
	}

	new Ajax.Updater(divName, "offer_detail.php?offer_id="+oid+"&ct="+ct+"&bc="+bc+"&increment="+myinc+"&tx="+tax);	
	
	total_amt(formName,tax);	
	return true;
}

/**
 * Function fillOffer to fill the offers in a dropdown list in confirm order
 * @return true
 */
function fillOffer()
{		
	var optn = document.createElement("OPTION");

	optn.text = document.product.offer_name.value;
	optn.value = document.product.product_offer_name.value;
	return true;
}

/**
 * Function customerType to show the offer as per the customer type
 * @param formName is used as a form name
 * @param divName is used as a div name
 * @param divName1 is used as a div name
 */
function customerType(formName,divName,divName1)
{
	formName.elements['billing_cycle'].value="";
	
	if(formName.elements['product_offer_name'])
		formName.elements['product_offer_name'].value="";
	
	document.getElementById(divName).style.display="none";
	document.getElementById(divName1).style.display="none";
	
	if(document.getElementById("unique_name1"))
		document.getElementById("unique_name1").style.display="none";
	
	if(document.getElementById("unique_name2"))
		document.getElementById("unique_name2").style.display="none";
	
	if(formName.name=="confirm_order")
	{
		document.confirm_order.chknewequip.checked=false;
		document.getElementById("equip_offer_residential").style.display="none";
		document.getElementById("equip_offer_business").style.display="none";
		document.getElementById("new_equip_offer").style.display="none";
		displayEquipOffer(formName,'new_equip_offer',0)
	}
}


/**
 * Function billingCycle to hide the offer div if billing cycle chagned
 * @param formName is used as a form name
 * @param divName is used as a div name
 */
function billingCycle(formName,divName)
{
	document.getElementById(divName).style.display="none";
	
	if(document.getElementById("unique_name1"))
		document.getElementById("unique_name1").style.display="none";
	
	if(document.getElementById("unique_name2"))
		document.getElementById("unique_name2").style.display="none";
}


/**
 * Function checkTaxPayer to check the existing customer has a tax examption or not in sell new offer to existing customer who has cancelled offer
 * @param formName is used as a form name
 * @param divId is used as a div id
 * @return true
 */
function checkTaxPayer(formName,divId)
{
	if(formName.elements['tax_prayer_id'].checked==true)
	{
		document.getElementById(divId).style.display="inline";
		var txst=0;		
	}
	else
	{
		var txst=1;
		document.getElementById(divId).style.display="none";
	}
	
	if(formName.name=="confirm_order")
	{
		//confirm_displayOffer(formName,"confirm_offer",1);
		if(document.confirm_order.tax_prayer_id)
		{
			if(document.confirm_order.tax_prayer_id.checked==true)
				var tax=1;
			else
				var tax=0;
		}
		total_amt(formName,tax);
		
		if(formName.elements['chknewequip'])
		{
			if(formName.elements['chknewequip'].checked==true)
			{
				displayEquipOffer(formName,"new_equip_offer",1);
			}
		}
	}
		return true;
}


/**
 * Function checkDocuments to check the document list
 * @return true/false
 */
function checkDocuments()
{
	for(i=0;i<document.checklist.chklist.length;i++)
	{
		if(document.checklist.chklist[i].value=="true")
		{
			if(document.checklist.chklist[i].checked==false)
			{
				alert("Please select the mandatory documents");
				return false;
			}
		}	
	}	
	return true;
}

/**
 * Function showCpeDetail to show the CPE detail
 * @param formName is used as a form name
 * @param divName is used as a div name
 * @return true
 */
function showCpeDetail(formName,divName)
{
	var mac=formName.elements['mac_address'].value;
	
	if(mac!="")
	{	
		new Ajax.Updater(divName, "cpe_detail.php?mac_address="+mac);
	}
	
	if(formName.elements['flag'])
	{
		if(formName.elements['flag'].value==1 || formName.elements['flag'].value==2)
			return false;
		else
			return true;
	}
	else
		return true;
}

/**
 * Function checkCPE to check the CPE is valid or not
 * @param formName is used as a form name
 * @return true/false
 */
function checkCPE(formName)
{
	if(formName.elements['flag'])
	{
		if(formName.elements['flag'].value==1)
		{
			alert("Invalid MAC Address, Please re-enter");
			formName.elements['mac_address'].value="";
			formName.elements['mac_address'].focus();
			return false;
		}
	}
	
	if(formName.elements['cpe_flag'])
	{
		if(formName.elements['cpe_flag'].value==1)
		{
			alert("Please enssure CPE is return on hand");
			//formName.elements['mac_address'].value="";
			//formName.elements['mac_address'].focus();
			return false;
		}
	}
	
	return true;
}

/**
 * Function validatezippo to validate the zip code and post box on the basis of notification by email or postal in address form
 * @return true/false
 */
function validatezippo()
{ 
	if(document.personal_information.notification_by.value==2)
	{
		var bill_po = document.address.billing_post_box.value;
		var bill_zip = document.address.billing_zip_code.value;

		if(!isInteger(bill_po))
		{
			alert("Please enter numeric data");
			document.address.billing_post_box.focus();
			document.address.billing_post_box.select();
			return(false);
		}
		else if(!isInteger(bill_zip))
		{
			alert("Please enter numeric data");
			document.address.billing_zip_code.focus();
			document.address.billing_zip_code.select();
			return(false);
		}		
		else
		{
			return true;
		}
	}
	else
	{
		return true;
	}
}


/**
 * Function isInteger to validate the data is integer or not
 * @param data as a field value
 * @return data
 */
function isInteger(data)
{
	var R=/^[0-9]{1,}$/;
	return R.test(data);
}




/**
 * Function Address_checkMandatoryFields to validate the zip code and post box on the basis of notification by email or postal in address form
 * @return true/false
 */
function Address_checkMandatoryFields()
{
	if(personal_information.elements['notification_by'].value==2)
	{
		address.document.getElementById('po').style.display="inline";
		address.document.getElementById('zc').style.display="inline";
		return true;
	}
	else
	{
		address.document.getElementById('po').style.display="none";
		address.document.getElementById('zc').style.display="none";
		return true;
	}	
}

/**
 * Function validatezippo to validate the zip code and post box on the basis of notification by email or postal in confirm_order form
 * @return true/false
 */
function validatezippo_confirmorder()
{ 
	if(document.confirm_order.notification_by.value==2)
	{
		
		var bill_po = document.confirm_order.billing_post_box.value;
		var bill_zip = document.confirm_order.billing_zip_code.value;
		
		if(!isInteger(bill_po))
		{
			alert("Please enter numeric data");
			document.confirm_order.billing_post_box.focus();
			document.confirm_order.billing_post_box.select();
			return(false);
		}
		else if(!isInteger(bill_zip))
		{
			alert("Please enter numeric data");
			document.confirm_order.billing_zip_code.focus();
			document.confirm_order.billing_zip_code.select();
			return(false);
		}	
		else
		{
			return true;
		}
	}
	else
	{
		return true;
	}
}


/**
 * fucntion sell_checkAddress to copy the billing address to service address in confirm_order form (cancelled offer)
 */
function sell_checkAddress()
{
	if(document.confirm_order.chkAddress.checked==true)
	{	
		document.confirm_order.service_address1.value=document.confirm_order.billing_address1.value;
		document.confirm_order.service_address2.value=document.confirm_order.billing_address2.value;
		document.confirm_order.service_post_box.value=document.confirm_order.billing_post_box.value;
		document.confirm_order.service_district.value=document.confirm_order.billing_district.value;
		document.confirm_order.service_city.value=document.confirm_order.billing_city.value;
		document.confirm_order.service_zip_code.value=document.confirm_order.billing_zip_code.value;
		document.confirm_order.service_day_time.value=document.confirm_order.billing_day_time.value;
		document.confirm_order.service_mobile.value=document.confirm_order.billing_mobile.value;
	}
	else
	{
		document.confirm_order.service_address1.value="";
		document.confirm_order.service_address2.value="";
		document.confirm_order.service_post_box.value="";
		document.confirm_order.service_district.value="";
		document.confirm_order.service_city.value="";
		document.confirm_order.service_zip_code.value="";
		document.confirm_order.service_day_time.value="";
		document.confirm_order.service_mobile.value="";
	}

}

/**
 * fucntion sell_confirm_billingCycle to reset the offer dropdown
 * @param formName as a form name
 * @param divName as a div name
 */
function sell_confirm_billingCycle(formName,divName)
{
	document.getElementById(divName).style.display="none";	
}


/**
 * Function sell_displayOffer to display the offer detail in confirm_order form for the cancelled offer
 * @param formName is used as a form name
 * @param divName is used as a div name
 * @param myinc is used as a incremented value
 * @return true
 */
function sell_displayOffer(formName,divName,myinc)
{
	document.getElementById(divName).style.display="inline";
	var oid=formName.elements['product_offer_name'].value;
	var ct=formName.elements['customer_type'].value;
	var bc=formName.elements['billing_cycle'].value;
		
	document.confirm_order.chknewequip.disabled=false;

	if(document.confirm_order.tax_prayer_id)
	{
		if(document.confirm_order.tax_prayer_id.checked==true)
			var tax=1;
		else
			var tax=0;
	}
	
	new Ajax.Updater(divName, "offer_detail.php?offer_id="+oid+"&ct="+ct+"&bc="+bc+"&increment="+myinc+"&tx="+tax);
	new Ajax.Updater("confirm_tc", 'terms_conditions.php?offer_id='+oid);
	total_amt(formName,tax);
	return true;
}

/**
 * Function sell_confirm_showProduct to display the product list for selected offer in confirm order form for the cancelled offer
 * @param url is used call the ajax to show the file form specified url
 * @param divName is used as a divname
 * @return true
 */
function sell_confirm_showProduct(url,divName)
{
	document.getElementById(divName).style.display="inline";
	x=document.confirm_order.billing_cycle.options[document.confirm_order.billing_cycle.selectedIndex].value;
	y=document.confirm_order.customer_type.options[document.confirm_order.customer_type.selectedIndex].value;
	
	new Ajax.Request(url+"?ct="+y+"&bc="+x, 
		{   method: 'get',   
			 onSuccess: function(transport) 
			 {     
				document.getElementById(divName).innerHTML=transport.responseText;				
			} 
		}); 
	
	return true;
}

/**
 * function makeReadOnly is used to make the fields readonly
 */
function makeReadOnly()
{
	document.confirm_order.date_of_birth.readOnly=true;	
	document.confirm_order.identification_type.disabled=true;
	document.confirm_order.identification_number.readOnly=true;	
	document.confirm_order.user_name.readOnly=true;	
}

/**
 * function changeCPE is used to change the CPE
 * @param cid as a customer id
 */
function changeCPE(cid)
{
	if(document.confirm_order.rdcpe[0].checked==true)
	{
		new Ajax.Updater("newcpediv", "sell_cpe.php?st=0&customer_id="+cid);
		document.getElementById("confirm_cpe_detail").style.display="none";
		
		if(document.confirm_order.flag)
			document.confirm_order.flag.value=0;
	}
	
	if(document.confirm_order.rdcpe[1].checked==true)
	{
		new Ajax.Updater("newcpediv", "sell_cpe.php?st=1&customer_id="+cid);
		document.getElementById("confirm_cpe_detail").style.display="inline";		
	}	
	
}

/**
 * function showRouterDetail is used to show the router detail
 * @return true
 */
function showRouterDetail()
{
	var r_address=confirm_order.elements['router_address'].value;

	if(r_address!="")
	{
		new Ajax.Updater("router_detail", "router_detail.php?router_address="+r_address);
	}

	return true;
}

/**
 * function validateRouter is used to check the router mac address is a mandatory filed and valid or not
 * @return true
 */
function validateRouter()
{
			
	if(document.confirm_order.router_address)
	{
		if(document.confirm_order.router_address.value=="")
		{
			alert("This field should not be empty");
			document.confirm_order.router_address.focus();
			return false;
		}
	}
	
	if(document.confirm_order.router_validate)
	{ 
		if(document.confirm_order.router_validate.value==1)
		{
			alert("Enter Valid Router Mac Address");
			document.confirm_order.router_address.focus();
			return false;
		}			
	}
	
	return true;	
}

/**
 * Function Confirm_Address_checkMandatoryFields to validate the zip code and post box on the basis of notification by email or postal in confirm_order form form
 * @return true
 */
function Confirm_Address_checkMandatoryFields(formName)
{

	if(formName.elements['notification_by'].value==2)
	{
		confirm_order.document.getElementById('co_po').style.display="inline";
		confirm_order.document.getElementById('co_zc').style.display="inline";
		return true;
	}
	else
	{
		confirm_order.document.getElementById('co_po').style.display="none";
		confirm_order.document.getElementById('co_zc').style.display="none";
		return true;
	}	
}

/**
 * Function checkBeforeSubmit to submit the form (cancelled offer)
 * @return true
 */
function checkSellBeforeSubmit()
{
	document.confirm_order.action="transaction.php";
	document.confirm_order.submit();
	return true;
}

/**
 * Function checkTelephoneNumber to check validate the mobile number or telephone number one of them should be a mandatory field
 * @param formName as a form name
 * @return true/false
 */
function checkTelephoneNumber(formName)
{
	if(formName.name=="personal_information")
	{
		if(formName.elements['mobile_number'].value=="" && formName.elements['telephone_number'].value=="")
		{
			alert("Please enter either mobile number or telephone number");
			formName.elements['mobile_number'].focus();
			return false;		
		}
		else
			return true;
	}
	
	if(formName.name=="address")
	{
		if(formName.elements['billing_day_time'].value=="" && formName.elements['billing_mobile'].value=="")
		{
			alert("Please enter either mobile number or telephone number");
			formName.elements['billing_day_time'].focus();
			return false;		
		}
		else if(formName.elements['service_day_time'].value=="" && formName.elements['service_mobile'].value=="")
		{
			alert("Please enter either mobile number or telephone number");
			formName.elements['service_day_time'].focus();
			return false;		
		}
		else
			return true;
	}
	
	if(formName.name=="confirm_order")
	{
		if(formName.elements['mobile_number'].value=="" && formName.elements['telephone_number'].value=="")
		{
			alert("Please enter either mobile number or telephone number");
			formName.elements['mobile_number'].focus();
			return false;		
		}		
		else if(formName.elements['billing_day_time'].value=="" && formName.elements['billing_mobile'].value=="")
		{
			alert("Please enter either mobile number or telephone number");
			formName.elements['billing_day_time'].focus();
			return false;		
		}
		else if(formName.elements['service_day_time'].value=="" && formName.elements['service_mobile'].value=="")
		{
			alert("Please enter either mobile number or telephone number");
			formName.elements['service_day_time'].focus();
			return false;		
		}
		else
			return true;
	}
}


/**
 * Function checkBeforeSubmit to submit the form
 * @return true
 */
function checkBeforeSubmit()
{
	new Ajax.Request("ajax_process_validation.php", 
		{   method: 'post', parameters: $('confirm_order').serialize(),
					 onSuccess: function(transport)
					 {     
						var rtext=transport.responseText;
						//alert(transport.responseText);
						if(rtext=="success")
						{
							document.confirm_order.action="process.php";
							document.confirm_order.submit();
							return true;
						}
						else
						{
							alert(rtext);
							return false;
						}
						
					} 
		}); 
	
}


/**
 * Function emailIntigration to show the email username and email password field depanding on offer product
 * @param formName as a form name
 * @param formName1 as a form name
 * @return true
 */
function emailIntigration(formName,formName1)
{
	//alert(formName.elements['email_intigration']);
	if(formName.elements['email_intigration'])
	{
		if(formName.elements['email_intigration'].value!=0 || formName.elements['email_intigration'].value!="")
		{
			for(i=1;i<=formName.elements['email_intigration'].value;i++)
			{
				if(formName1.name=="confirm_order")
					document.getElementById("confirm_emaildiv"+i).style.display="inline";
				else
				{
					document.getElementById("emaildiv"+i).style.display="inline";					
				}
			}
		}
		else
		{
			if(formName1.name=="confirm_order")
				document.getElementById("confirm_emaildiv1").style.display="none";
			else
				document.getElementById("emaildiv1").style.display="none";
				
			formName1.elements['email_username1'].value="";
			formName1.elements['email_password1'].value="";
		}			
	}
	else
	{
		if(formName1.name=="confirm_order")
			document.getElementById("confirm_emaildiv1").style.display="none";
		else
			document.getElementById("emaildiv1").style.display="none";					
	
	
		formName1.elements['email_username1'].value="";
		formName1.elements['email_password1'].value="";
	}
	return true;
}

/**
 * Function checkEmailUserName to check the email username is available or not
 * @param formName as a form name
 */
function checkEmailUserName(formName)
{
	//alert(formName.name);
	var emailusername=formName.elements['email_username1'].value;
	
	if(emailusername=="")
		return;
	if(formName.name=="confirm_order")
		var divname="confirm_email_error";
	else
		var divname="email_error";
	
	//alert(divname);
	new Ajax.Updater(divname,"check_email.php?emailuser="+emailusername);
}


/**
 * Function checkEmail to check the email username is not available the show error message
 * @param formName as a form name
 * @param true/false
 */
function checkEmail(formName)
{
	if(formName.elements['email_flag'])
	{
		if(formName.elements['email_flag'].value==0)
		{
			alert("Please enter another username for email");
			formName.elements['email_username1'].focus();
			return false;
		}
		else
			return true;
	}
	else
		return true;
}


/**
 * function to formSubit is used to submit the form
 * return true;
 */
function formSubmit()
{
	var agree=confirm("Do you want to exit order entry without creating a customer?");
	if(agree)
	{
		document.confirm_order.action="prospecting_customer.php";
		document.confirm_order.submit();
		return true;
	}
	else
	{
		return false;
	}	
}