
//call back function for step 1
function mcallBack()
{
	gcallbackfunc=null;
	content=null;
}

function check_package()
{
	content="package_details";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
	
	content="package";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
	
	customer_type=document.select_package.customer_type.options[document.select_package.customer_type.selectedIndex].value;
	package_type=document.select_package.package_type.options[document.select_package.package_type.selectedIndex].value;
	
	if(package_type!='0')
	{		
		var getstr ="?customer_type="+customer_type+"&package_type="+package_type;
		if(package_type=='1')
		{
			customGetText("<img src='images/loading.gif' border=0> retrieving prepaid packages ...");
			makeRequest('package/perpaid_packages.php', getstr);			
		}
		else if(package_type=='2')
		{
			customGetText("<img src='images/loading.gif' border=0> retrieving bill cycles ...");
			makeRequest('package/postpaid_cycles.php', getstr);			
		}
	}
	
}

function show_packages()
{
	if(content!='myspan' && content!=null)
	{
		setTimeout("show_postpaid_offer()", 500);
		return;
	}

	content="p_packages";
	clearGetSpan();
	customer_type=document.select_package.customer_type.options[document.select_package.customer_type.selectedIndex].value;
	package_type=document.select_package.package_type.options[document.select_package.package_type.selectedIndex].value;
	bill_cycle_id=document.select_package.billing_cycle.options[document.select_package.billing_cycle.selectedIndex].value;
	gcallbackfunc="mcallBack()";
	customGetText("<img src='images/loading.gif' border=0> retrieving Packages ...");
	var getstr ="?customer_type="+customer_type+"&package_type="+package_type+"&bill_cycle_id="+bill_cycle_id;
	makeRequest('package/postpaid_packages.php', getstr);

}

function show_postpaid_offer()
{
	if(content!='myspan' && content!=null)
	{
		setTimeout("show_postpaid_offer()", 500);
		return;
	}
	
	content="package";
	clearGetSpan();
	customer_type=document.select_package.customer_type.options[document.select_package.customer_type.selectedIndex].value;
	package_type=document.select_package.package_type.options[document.select_package.package_type.selectedIndex].value;
	bill_cycle_id=document.select_package.billing_cycle.options[document.select_package.billing_cycle.selectedIndex].value;
	gcallbackfunc="mcallBack()";
	customGetText("<img src='images/loading.gif' border=0> retrieving Packages ...");
	var getstr ="?customer_type="+customer_type+"&package_type="+package_type+"&bill_cycle_id="+bill_cycle_id;
	makeRequest('package/packages.php', getstr);	

}
function package_details()
{
	if(content!='myspan' && content!=null)
	{
		setTimeout("package_details()", 500);
		return;
	}

	content="package_details";
	clearGetSpan();
	package_id=document.select_package.package_id.options[document.select_package.package_id.selectedIndex].value;
    customer_cate=document.select_package.fut_customer_select.options[document.select_package.fut_customer_select.selectedIndex].value;
	gcallbackfunc="mcallBack()";
	customGetText("<img src='images/loading.gif' border=0> retrieving package details ...");
	var getstr ="?package_id="+package_id+"&customer_cate="+customer_cate;
	makeRequest('package/package_details.php', getstr);	

    var fut_customer=$.trim(document.select_package.fut_customer_select.value);
	if(fut_customer == "employee")
    {
        document.getElementById("in_customer_div").style.display= "inline";
        document.getElementById("comp_info_div").style.display= "none";
    }else{
        document.getElementById("comp_info_div").style.display= "inline";
        document.getElementById("in_customer_div").style.display= "none";

    }
    
	document.getElementById("customer_information_div").style.display = 'inline';
	document.getElementById("customer_address_div").style.display = 'inline';
	document.getElementById("customer_account_div").style.display = 'inline';
	document.getElementById("next_div").style.display = 'inline';

}

function check_commitment()
{
	selected =document.select_package.commitments.value;
    service_fee = document.select_package.service_fee.value;
	sec_deposit = document.select_package.security_deposit.value;
	activation_fee = document.select_package.activation_fee.value;	
    commitment_info = selected.split(';;');
    serv_amount = service_fee-commitment_info[1]; 
    document.getElementById("service_fee_div").innerHTML = serv_amount+ " PKR";
	dep_amount = sec_deposit-commitment_info[1];
    document.getElementById("security_deposit_div").innerHTML = dep_amount+ " PKR";
	
	total_amount = (dep_amount+1)+ (activation_fee-1);
	document.select_package.total_fee.value = total_amount;
    document.getElementById("total_fee_div").innerHTML = " Total: "+total_amount+" PKR";
}

function FUT_customer()
{
	var fut_customer=$.trim(document.customer_information.fut_customer_select.value);
	if(fut_customer == "internal_customer")
	{
		document.getElementById("in_customer_div").style.display= "inline";
		document.getElementById("ex_customer_div").style.display= "none";
	}else if(fut_customer == "external_customer")
	{
		document.getElementById("ex_customer_div").style.display= "inline";
		document.getElementById("in_customer_div").style.display= "none";
	}
}
function verify_fut_Customer()
{
    var fut_id = document.select_package.fut_customer_id.value;
    var callback = function(data)
                   {
                       if(data=="false")
                       {    document.getElementById("fut_check_msg_div").innerHTML= "Invalid FUT Customer";
                            document.select_package.fut_cutomer_verified.value = "false";
                       }
                       else {
                            document.getElementById("fut_check_msg_div").innerHTML= "Valid FUT customer";
                            document.account_info.cpe_mac_address.value = data;
                            document.account_info.cpe_mac_address.readOnly = true;
                            show_postpaid_offer();
                       }
                   };
    var arr = {fut_customer_id:fut_id};
    $.post("package/validate_fut_customer.php",arr,callback);


}
function select_customer()
{
    hideCustomerForm();
	var fut_customer=$.trim(document.select_package.fut_customer_select.value);
	if(fut_customer == "fut_customer")
	{
		document.getElementById("fut_customer_div").style.display= "inline";
        document.getElementById("package").innerHTML= "";
        document.getElementById("package_details").innerHTML= "";
	//	document.getElementById("employee_div").style.display= "none";
	}else if(fut_customer == "new_customer" || fut_customer == "employee")
	{
		document.getElementById("fut_customer_div").style.display= "none";
        document.getElementById("package").innerHTML= "";
        document.getElementById("package_details").innerHTML= "";

        show_postpaid_offer();
   //     document.getElementById("employee_div").style.display= "inline";
	}

}

function hideCustomerForm()
{
        document.getElementById("comp_info_div").style.display= "none";
        document.getElementById("customer_information_div").style.display = 'none';
        document.getElementById("customer_address_div").style.display = 'none';
        document.getElementById("customer_account_div").style.display = 'none';
        document.getElementById("next_div").style.display = 'none';

}

function same_billing_address()
{
    if(document.customer_address.chkAddress.checked == true)
	{
        document.customer_address.billing_hno.value         =document.customer_address.service_hno.value;
        document.customer_address.billing_street_road.value =document.customer_address.service_street_road.value;
        document.customer_address.billing_area.value        =document.customer_address.service_area.value;
        document.customer_address.billing_city.value        =document.customer_address.service_city.value;

        document.customer_address.billing_hno.readOnly          = true;
        document.customer_address.billing_street_road.readOnly  = true;
        document.customer_address.billing_area.readOnly         = true;
        document.customer_address.billing_city.readOnly         = true;
        
	}else
	{
        document.customer_address.billing_hno.readOnly          = false;
        document.customer_address.billing_street_road.readOnly  = false;
        document.customer_address.billing_area.readOnly         = false;
        document.customer_address.billing_city.readOnly         = false;
	}
	

}

function check_username()
{
    content="username_availability";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
    customGetText("<img src='images/loading.gif' border=0> checking availability ...");
    getstr="?username="+document.account_info.account_username.value;
    makeRequest('customer/check_username_availability.php', getstr);
    
}

function validate_cnic()
{
	var type = $.trim(document.customer_information.identification_type.value);
	var cnic = $.trim(document.customer_information.identification_number.value);
	if( type!="" && cnic!="")
	{   content="cnic_validate";
		clearGetSpan();
		gcallbackfunc="mcallBack()";
		customGetText("<img src='images/loading.gif' border=0> checking cnic/passport...");
    	getstr="?type="+type+"&cnic="+cnic;
    	makeRequest('customer/validate_cnic.php', getstr);
	}
}

function check_Phone()
{
	var phone = $.trim(document.customer_information.telephone_number.value);
	if( phone!="")
	{   content="phone_validate";
		clearGetSpan();
		gcallbackfunc="mcallBack()";
		customGetText("<img src='images/loading.gif' border=0> checking phone...");
    	getstr="?phone="+phone;
    	makeRequest('customer/check_phone.php', getstr);
	}
}

function validate_MAC()
{
    content="cpe_mac_validation";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
    customGetText("<img src='images/loading.gif' border=0> validating CPE ...");
    getstr="?cpe_mac_address="+document.account_info.cpe_mac_address.value;
    makeRequest('customer/validate_mac.php', getstr);

}

function goto_confirm_order()
{
	confirm_mac();
    
}

function validate_all()
{
	var customer_validation = validate_customer_info();
    var address_validation  = validate_customer_address();
    var account_validation  = validate_customer_account();

     var verified=customer_validation && address_validation && account_validation;
	
	
	if(verified)
    {
        update_values();
        document.getElementById("necessary_field_msg").style.display = 'none'; 
        document.getElementById("package_selection").style.display = 'none';
        document.getElementById("customer_information_div").style.display = 'none';
        document.getElementById("customer_address_div").style.display = 'none';
        document.getElementById("customer_account_div").style.display = 'none';
        document.getElementById("next_div").style.display = 'none';
        document.getElementById("order_confirmation").style.display = 'inline';
    }
	return true;	
}

function validate_customer_info()
{
	first_name=$.trim(document.customer_information.first_name.value);
    first_name=$.trim(document.customer_information.first_name.value);
    last_name=$.trim(document.customer_information.last_name.value);
    iden_type=$.trim(document.customer_information.identification_type.value);
    iden_number=$.trim(document.customer_information.identification_number.value);
	cnic_allowed= document.customer_information.cnic_allowed.value;
    dob=$.trim(document.customer_information.date_of_birth.value);
    ntn_number=true;
    tel_number=$.trim(document.customer_information.telephone_number.value);
    phone_allowed= document.customer_information.phone_allowed.value;
	email_add=$.trim(document.customer_information.email_address.value);
	
    validated = true;
    errors="";
    
    if(!isstr(first_name))
    {   validated= false;
        errors+="Please Enter first name in Enlisgh Alphabets<BR>";
    }
    if(!isstr(last_name))
    {   validated= false;
        errors+="Please Enter last name in Enlisgh Alphabets<BR>";
    }
    if(!isstr(iden_type))
    {   validated= false;
        errors+="Please select Indentification Type<BR>";
    }
    if(iden_type=="CNIC" )
    {
        if(!isNumeric(iden_number) || (iden_number.length!=13))
        {   validated= false;
            errors+="Incorrect CNIC number entered<BR>";
        }
    }
	if(cnic_allowed=='false')
    {
        validated=false;
        errors+="cannot sell to this "+iden_type+"<BR>";
    }
	
    if(iden_type=="Passport")
    {
        if(iden_number=="" )
        {   validated= false;
            errors+="Please Enter Passport Number in Alpha-numeric only<BR>";
        }
    }
	
/*	var fut_customer=$.trim(document.customer_information.fut_customer_select.value);
	
	if(fut_customer=="internal_customer")
	{
		customer_desg = $.trim(document.customer_information.in_customer_desg.value);
		customer_dept = $.trim(document.customer_information.in_customer_dept.value);
		if(!isstr(customer_desg))
		{
			validated= false;
			errors+="Please Enter Internal Customer's Designation (English Alphabets)<BR>";
		}
		if(!isstr(customer_dept))
		{
			validated= false;
			errors+="Please Enter Internal Customer's Department (English Alphabets)<BR>";
		}
	}else if(fut_customer=="external_customer")
	{
		customer_ref= $.trim(document.customer_information.ex_customer_ref.value);
		customer_dept = $.trim(document.customer_information.ex_customer_dept.value);
		if(!isstr(customer_ref))
		{
			validated= false;
			errors+="Please Enter External Customer's Reference (English Alphabets)<BR>";
		}
		if(!isstr(customer_dept))
		{
			validated= false;
			errors+="Please Enter External Customer's Department (English Alphabets)<BR>";
		}
	}else
	{
		validated= false;
		errors+="Please select FUT Customer Type<BR>";
	}
*/
 /*   if(dob!="")
    {   validated= false;
         errors+="Please Enter Date of Birth<BR>"
    } */
//    if(!isstr(document.customer_information.company_name.value))
//    {   company_name= false;  }
//    if(!isAlphaNum(document.customer_information.ntn_number.value))
//    {   validated= false;
 //        errors+="Please Enter NTN Number in Alpha-numeric only<BR>";
 //   }
    if(!isNumeric(tel_number))
    {   validated= false;
         errors+="Please Enter Telephone number (digits only)<BR>";
    }
	
	if(phone_allowed=='false')
    {
        validated=false;
        errors+="cannot sell to this phone Number<BR>";
    }
    if(!isEmail(email_add))
    {   validated= false;
        errors+="Please Enter valid Email address <BR>";
    }
	
    document.getElementById("customer_info_error").innerHTML = errors;
    return validated;
}
function validate_customer_address()
{
    validated=true;
    errors="";
    if($.trim(document.customer_address.service_hno.value)=="")
    {   validated= false;
         errors+="Please Enter House Number<BR>";
    }
    if($.trim(document.customer_address.service_street_road.value)=="")
    {   validated= false;
         errors+="Please Enter Street/ Road<BR>";
    }
    if($.trim(document.customer_address.service_area.value)=="")
    {   validated= false;
         errors+="Please Enter area<BR>";
    }
    if($.trim(document.customer_address.service_city.value)=="")
    {   validated= false;
         errors+="Please Enter city<BR>";
    }
    document.getElementById("service_address_error").innerHTML = errors;

    billing_hno=true;
    billing_street_road=true;
    billing_area=true;
    billing_city=true;
    errors="";
    if($.trim(document.customer_address.billing_hno.value)=="")
    {   validated= false;
          errors+="Please Enter House Number<BR>";
    }
    if($.trim(document.customer_address.billing_street_road.value)=="")
    {   validated= false;
        errors+="Please Enter Street/ Road<BR>";
    }
    if($.trim(document.customer_address.billing_area.value)=="")
    {   validated= false;
        errors+="Please Enter area<BR>";
    }
    if($.trim(document.customer_address.billing_city.value)=="")
    {   validated= false;
        errors+="Please Enter city<BR>";
    }
    document.getElementById("billing_address_error").innerHTML = errors;

    return validated;
}

function validate_customer_account()
{
    validated=true;
    errors="";
    user_available = document.account_info.username_available.value;
    cpe_validated = document.account_info.cpe_validated.value;

    username =document.account_info.account_username.value;
    if(($.trim(username)=="") ||(username.length<6) )
    {   validated= false;
         errors+="Please Enter username (min 6 characters)<BR>";
    }else if(user_available=='false')
    {
        validated=false;
        errors+="Username is not available<BR>";
    }
//    password=document.account_info.account_password.value;
//    if(($.trim(password)=="") || (password.length<6) )
//    {   validated= false;
//         errors+="Please Enter password (min 6 characters)<BR>";
//    }
 //  payment_method=document.account_info.payment_method.value;
	cpe_mac_address=$.trim(document.account_info.cpe_mac_address.value);
    if(!(isCPEMAC(cpe_mac_address) && cpe_mac_address.length==12))
    {
        validated=false;
        errors+="Invalid CPE MAC Address";
    }else if(cpe_validated=='false')
    {
        validated=false;
        errors+="CPE is not available for sale";
    }
    document.getElementById("account_info_error").innerHTML = errors;
    return validated;
}

function confirm_mac()
{
	content="cpe_mac_validation";
	clearGetSpan();
	gcallbackfunc="validate_all()";
    customGetText("<img src='images/loading.gif' border=0> validating CPE ...");
    getstr="?cpe_mac_address="+document.account_info.cpe_mac_address.value;
    makeRequest('customer/validate_mac.php', getstr);
}

function randomPassword(length)
{
  chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  pass = "";
  for(x=0;x<length;x++)
  {
    i = Math.floor(Math.random() * 62);
    pass += chars.charAt(i);
  }
  return pass;
}

function update_values()
{
	customer_type=document.select_package.customer_type.options[document.select_package.customer_type.selectedIndex];
	document.confirm_order.customer_type.value = customer_type.value;
	document.getElementById("customer_type_div").innerHTML = customer_type.text;
	
	////////////////////////////////////////////////////////////////////////////
	package_type=document.select_package.package_type.options[document.select_package.package_type.selectedIndex];
	document.confirm_order.customer_type.value = customer_type.value;
	document.getElementById("package_type_div").innerHTML = package_type.text;

	///////////////////////////////////////////////////////////////////////////
	if(document.select_package.billing_cycle)
	{
		bill_cycle=document.select_package.billing_cycle.options[document.select_package.billing_cycle.selectedIndex];
		document.confirm_order.billing_cycle.value = bill_cycle.value;
		document.getElementById("billing_cycle_div").innerHTML = bill_cycle.text;
	}

    ///////////////////////////////////////////////////////////////////////////
    customer_cate=document.select_package.fut_customer_select.options[document.select_package.fut_customer_select.selectedIndex];
	document.getElementById("customer_cate_div").innerHTML = customer_cate.text
    document.confirm_order.customer_category.value = customer_cate.value;
    document.confirm_order.fut_customer_id.value = document.select_package.fut_customer_id.value;

	///////////////////////////////////////////////////////////////////////////
	package_id=document.select_package.package_id.options[document.select_package.package_id.selectedIndex];			
	document.confirm_order.package.value = package_id.value;
	document.getElementById("package_div").innerHTML = package_id.text;
	
	/////////////////////////////////////////////////////////////////
	update_package_details();
	update_customer_info();
	update_addresses();
	update_account_details();
	return true;
	
}
function update_package_details()
{
	/*content="package_details_div";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
	
	package_id=document.select_package.package_id.options[document.select_package.package_id.selectedIndex].value;
	customGetText("<img src='images/loading.gif' border=0> Loading Customer Data");
	var getstr ="?package_id="+package_id;
	makeRequest('package/package_details.php', getstr);	
    */

    var selected =document.select_package.commitments.value;
    commitment_info = selected.split(';;');
    document.getElementById("package_details_div").innerHTML = document.getElementById("package_details").innerHTML;
    document.getElementById("package_details_div").innerHTML += "<input type='hidden' name='commitment_id' value='"+commitment_info[0]+"'/>";
    document.getElementById("package_details_div").innerHTML += "<input type='hidden' name='discount_value' value='"+commitment_info[1]+"'/>";
    document.confirm_order.commitments.selectedIndex = document.select_package.commitments.selectedIndex;
    document.confirm_order.commitments.disabled = true;
    

}

function update_customer_info()
{
	title=document.customer_information.title.value;
	document.confirm_order.title.value = title;
	document.getElementById("title_div").innerHTML = title;

    ///////////////////////////////////////////////////////////////////
	first_name=document.customer_information.first_name.value;
	document.confirm_order.first_name.value = first_name;
	document.getElementById("first_name_div").innerHTML = first_name;

    ///////////////////////////////////////////////////////////////////
	last_name=document.customer_information.last_name.value;
	document.confirm_order.last_name.value = last_name;
	document.getElementById("last_name_div").innerHTML = last_name;

    ///////////////////////////////////////////////////////////////////
	iden_type=document.customer_information.identification_type.value;
	document.confirm_order.identification_type.value = iden_type;
	document.getElementById("identification_type_div").innerHTML =iden_type ;

	///////////////////////////////////////////////////////////////////
	iden_number=document.customer_information.identification_number.value;
	document.confirm_order.identification_number.value = iden_number;
	document.getElementById("identification_number_div").innerHTML = iden_number;

	///////////////////////////////////////////////////////////////////
	dob=document.customer_information.date_of_birth.value;
	document.confirm_order.date_of_birth.value = dob;
	document.getElementById("date_of_birth_div").innerHTML = dob;

    ///////////////////////////////////////////////////////////////////
	occupation=document.customer_information.occupation.value;
	document.confirm_order.occupation.value = occupation;
	document.getElementById("occupation_div").innerHTML = occupation;

	///////////////////////////////////////////////////////////////////
	company_name=document.customer_information.company_name.value;
	document.confirm_order.company_name.value = company_name;
	document.getElementById("company_name_div").innerHTML = company_name;

	///////////////////////////////////////////////////////////////////
	ntn_number=document.customer_information.ntn_number.value;
	document.confirm_order.ntn_number.value = ntn_number;
	document.getElementById("ntn_number_div").innerHTML = ntn_number;

   ///////////////////////////////////////////////////////////////////
	gst_number=document.customer_information.gst_number.value;
	document.confirm_order.gst_number.value = gst_number;
	document.getElementById("gst_number_div").innerHTML = gst_number;

	var fut_customer=$.trim(document.select_package.fut_customer_select.value);

	//document.confirm_order.fut_customer_value.value = fut_customer;
	
	if(fut_customer == "employee")
    {
         document.getElementById("company_details_confirm_div").style.display= "none";
		in_customer_desg = $.trim(document.customer_information.in_customer_desg.value);
		document.confirm_order.in_customer_desg.value = in_customer_desg;
		document.getElementById("in_customer_desg_div").innerHTML = in_customer_desg;

		in_customer_dept = $.trim(document.customer_information.in_customer_dept.value);
		document.confirm_order.in_customer_dept.value = in_customer_dept;
		document.getElementById("in_customer_dept_div").innerHTML = in_customer_dept;
		
	//	document.confirm_order.internal_customer_confirm_div.style.display= "inline";
	//	document.confirm_order.external_customer_confirm_div.style.display= "none";

	}
    /*else if(fut_customer=="external_customer")
	{
		ex_customer_ref = $.trim(document.customer_information.ex_customer_ref.value);
		document.confirm_order.ex_customer_ref.value = ex_customer_ref;
		document.getElementById("ex_customer_ref_div").innerHTML = ex_customer_ref;

		ex_customer_dept = $.trim(document.customer_information.ex_customer_dept.value);
		document.confirm_order.ex_customer_dept.value = ex_customer_dept;
		document.getElementById("ex_customer_dept_div").innerHTML = ex_customer_dept;
		
		document.confirm_order.in_customer_desg.value = "";
		document.getElementById("in_customer_desg_div").innerHTML = "";
		document.confirm_order.in_customer_dept.value = "";
		document.getElementById("in_customer_dept_div").innerHTML = "";
		
	//	document.confirm_order.external_customer_confirm_div.style.display= "inline";
	//	document.confirm_order.internal_customer_confirm_div.style.display= "none";
	} 
*/
	///////////////////////////////////////////////////////////////////
	tel_number=document.customer_information.telephone_number.value;
	document.confirm_order.telephone_number.value = tel_number;
	document.getElementById("telephone_number_div").innerHTML = tel_number;
	
	///////////////////////////////////////////////////////////////////
    mob_number=document.customer_information.mobile_number.value;
	document.confirm_order.mobile_number.value = mob_number;
	document.getElementById("mobile_number_div").innerHTML = mob_number;

	///////////////////////////////////////////////////////////////////
	email_add=document.customer_information.email_address.value;
	document.confirm_order.email_address.value = email_add;
	document.getElementById("email_address_div").innerHTML = email_add;
	
	///////////////////////////////////////////////////////////////////
	fax_number=document.customer_information.fax_number.value;
	document.confirm_order.fax_number.value = fax_number;
	document.getElementById("fax_number_div").innerHTML = fax_number;

}

function update_addresses()
{
	///////////////////////////////////////////////////////////////////
	service_hno=document.customer_address.service_hno.value;
	document.confirm_order.service_hno.value = service_hno;
	document.getElementById("service_hno_div").innerHTML = service_hno;
 
	///////////////////////////////////////////////////////////////////
	service_street_road=document.customer_address.service_street_road.value;
	document.confirm_order.service_street_road.value = service_street_road;
	document.getElementById("service_street_road_div").innerHTML = service_street_road;

	///////////////////////////////////////////////////////////////////
	service_area=document.customer_address.service_area.value;
	document.confirm_order.service_area.value = service_area;
	document.getElementById("service_area_div").innerHTML = service_area;
	
	///////////////////////////////////////////////////////////////////
	service_city=document.customer_address.service_city.value;
	document.confirm_order.service_city.value = service_city;
	document.getElementById("service_city_div").innerHTML = service_city;
	
	//////////////////////////////BILLING ADDRESS////////////////////////////////
	billing_hno=document.customer_address.billing_hno.value;
	document.confirm_order.billing_hno.value = billing_hno;
	document.getElementById("billing_hno_div").innerHTML = billing_hno;

	///////////////////////////////////////////////////////////////////
	billing_street_road=document.customer_address.billing_street_road.value;
	document.confirm_order.billing_street_road.value = billing_street_road;
	document.getElementById("billing_street_road_div").innerHTML = billing_street_road;

	///////////////////////////////////////////////////////////////////
	billing_area=document.customer_address.billing_area.value;
	document.confirm_order.billing_area.value = billing_area;
	document.getElementById("billing_area_div").innerHTML = billing_area;
	
	///////////////////////////////////////////////////////////////////
	billing_city=document.customer_address.billing_city.value;
	document.confirm_order.billing_city.value = billing_city;
	document.getElementById("billing_city_div").innerHTML = billing_city;

	
}

function update_account_details()
{

	///////////////////////////////////////////////////////////////////
	username =document.account_info.account_username.value;
	document.confirm_order.account_username.value = username;
	document.getElementById("account_username_div").innerHTML = username;

///////////////////////////////////////////////////////////////////
	password=document.account_info.account_password.value;
    if(password=="")
    {
        password = randomPassword(8);
    }
	document.confirm_order.account_password.value = password;
	document.getElementById("account_password_div").innerHTML = password;

    ///////////////////////////////////////////////////////////////////
/*	payment_method=document.account_info.payment_method.value;
	document.confirm_order.payment_method.value = payment_method;
	document.getElementById("payment_method_div").innerHTML = payment_method;
*/
	cpe_mac_address=document.account_info.cpe_mac_address.value;
	document.confirm_order.cpe_mac_address.value = cpe_mac_address;
	document.getElementById("cpe_mac_address_div").innerHTML = cpe_mac_address;

}

function editOrder()
{
    document.getElementById("necessary_field_msg").style.display = 'inline';
	document.getElementById("order_confirmation").style.display = 'none';
	document.getElementById("package_selection").style.display = 'inline';
	document.getElementById("customer_information_div").style.display = 'inline';
	document.getElementById("customer_address_div").style.display = 'inline';
	document.getElementById("customer_account_div").style.display = 'inline';
	document.getElementById("next_div").style.display = 'inline';
}
