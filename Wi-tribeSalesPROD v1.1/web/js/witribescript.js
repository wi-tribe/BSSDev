     function IsValidEmail(email) {
                $('p.validationmsg').hide();
        	//var emailFilter=/^.+@.+\..{2,3}$/;
                var emailFilter=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[(2([0-4]\d|5[0-5])|1?\d{1,2})(\.(2([0-4]\d|5[0-5])|1?\d{1,2})){3} \])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                if(!emailFilter.test(email.value)) {
                    showMessage(email, 'Invalid Email');
                }
		return(emailFilter.test(email.value));
	}
       function getServicetype(){
            
            var selectedtext =document.leadform.servicetype.options[document.leadform.servicetype.options.selectedIndex].value;
            if(selectedtext == "Dialup"){
                //document.getElementById('broadbandid').style.display = "";
                document.getElementById('dialupid').style.display = "";
            } else {
                document.getElementById('dialupid').style.display = "none";
                //document.getElementById('broadbandid').style.display = "none";
              } 
         
            }
      
	function IsNumber(val) {
                var value = val.value;
                var numberFilter=/^\d{0,16}$/;
                if(numberFilter.test(value)){
                return true;
                } else {
		return false;
                }
	}
        function IsNumberDevice(val) {
                var value = val.value;
                var numberFilter=/^\d{0,5}$/;
                if(numberFilter.test(value)){
                return true;
                } else {
		return false;
                }
	}
       function IsNumberInv(val) {
                var value = val.value;
                var numberFilter=/^\d{0,9}$/;
                if(numberFilter.test(value)){
                return true;
                } else {
		return false;
                }
	}
	function ValidateInformationform(form) {
		$('p.validationmsg').hide();
                 
		if(form.firstname.value == '') {
			showMessage(form.firstname, 'Please Enter First Name');
			return(false);
		}
		if(!isAlphaNumeric(form.firstname)) {
                        return(false);
                }
		if(form.lastname.value == '') {
			showMessage(form.lastname, 'Please Enter Last Name');
			return(false);
		}
                if(!isAlphaNumeric(form.lastname)) {
                        return(false);
                }
               /* if(!isAlphaNumeric(form.CNICid)) {
                        return(false);
                }*/
                if(!isValidCNIC(form)) {
                        return(false);
                }
                if(!isAlphaNumeric(form.jobtitle)) {
                        return(false);
                }
                if(!isAlphaNumeric(form.occupation)) {
                        return(false);
                }
                
                if(form.email.value == '') {
			showMessage(form.email, 'Please Enter Your Email-ID');
			return(false);
		} else {
                    if(!IsValidEmail(form.email))
                   return IsValidEmail(form.email);
                }
                if(form.contactnumber.value == '') {
			showMessage(form.contactnumber, 'Please Enter Contact Number');
			return(false);
		}
                if(!IsNumber(form.contactnumber)){
                        showMessage(form.contactnumber, 'Please Enter Numbers Only');
                        return(false);
                }
                if(form.contactnumber.value.length < 10 || form.contactnumber.value.length >11 || form.contactnumber.value.charAt(0) != 0){
                        showMessage(form.contactnumber, 'Please enter contact number like 03XXXXXXXXX with ten digits min and max eleven digits');
                        return(false);
                }
                if(form.leadaddress.value == '') {
			showMessage(form.leadaddress, 'Please Enter Lead Address');
			return(false);
		}
                if (form.leadaddress.value.length > 200 ) {
                        showMessage(form.leadaddress, "Lead Address shouldn't contain more than 200 Characters");
			return(false);
                      }
               /* if(form.plot.value == '') {
			showMessage(form.plot, 'Please Enter Plot Info');
			return(false);
		}
                if(!isAlphaNumeric(form.plot)) {
                        return(false);
                }
                if(form.street.value == '') {
			showMessage(form.street, 'Please Enter Street Info');
			return(false);
		}
                if(!isAlphaNumeric(form.street)) {
                        return(false);
                }*/
                if(form.country.value == '') {
			showMessage(form.country, 'Please Enter Country');
			return(false);
		}
                if(!isAlphaNumeric(form.country)) {
                        return(false);
                }
                if(form.province.value == '') {
			showMessage(form.province, 'Please Enter Province');
			return(false);
		}
                /*if(!isAlphaNumeric(form.province)) {
                        return(false);
                }*/
                if(form.city.value == '') {
			showMessage(form.city, 'Please Enter City');
			return(false);
		}
                /*if(!isAlphaNumeric(form.city)) {
                        return(false);
                }*/
                if(!IsNumber(form.zip)){
                        showMessage(form.zip, 'Please Enter Numbers Only');
                        return(false);
                }
                if(form.zone.value == '') {
			showMessage(form.zone, 'Please Enter Zone');
			return(false);
		}
                /*if(!isAlphaNumeric(form.zone)) {
                        return(false);
                }*/
                if(document.getElementById('otherzone').style.display == "" && form.zone.value == 'other') {
                        if(form.otherzone.value == ''){
			showMessage(form.otherzone, 'Please Enter otherZone');
			return(false);
                    }
                    if(!isAlphaNumeric(form.otherzone)) {
                        return(false);
                    }
                 }
               if(document.getElementById('othersubzone').style.display == "" && form.subzone.value == 'other') {
                    if(form.othersubzone.value == ''){
			showMessage(form.othersubzone, 'Please Enter otherSubZone');
			return(false);
                    }
                    if(!isAlphaNumeric(form.othersubzone)) {
                        return(false);
                    }
                }
                if(form.subzone.value == '') {
			showMessage(form.subzone, 'Please Enter SubZone');
			return(false);
		}
                /*if(!isAlphaNumeric(form.subzone)) {
                        return(false);
                }*/
              
                 /*if(form.zip.value == '') {
			showMessage(form.zip, 'Please Enter Zip');
			return(false);
		}*/
               if(!IsNumber(form.willingPay)){
                        showMessage(form.willingPay, 'Please Enter Numbers Only');
                        return(false);
                }
                if(!IsNumber(form.speed)){
                        showMessage(form.speed, 'Please Enter Numbers Only');
                        return(false);
                }
                if(!IsNumber(form.volumelimit)){
                        showMessage(form.volumelimit, 'Please Enter Numbers Only');
                        return(false);
                }
                if(!IsNumber(form.monthlyspend)){
                        showMessage(form.monthlyspend, 'Please Enter Numbers Only');
                        return(false);
                }
                /*if(form.leadsource.value == '') {
			showMessage(form.leadsource, 'Please Enter LeadSource');
			return(false);
		}
                
                if(form.packageinfo.value == '') {
			showMessage(form.packageinfo, 'Please Enter Package Information');
			return(false);
		}*/
                 if(!isAlphaNumeric(form.packageinfo)) {
                        return(false);
                }
                if(!isAlphaNumeric(form.nameofISP)) {
                        return(false);
                }
                /*if(form.servicetype.value == '') {
			showMessage(form.servicetype, 'Please Enter Type of Service');
			return(false);
		}
                if(form.willingPay.value == '' && document.getElementById('dialupid').style.display == "") {
			showMessage(form.willingPay, 'Please Enter Willing Pay');
			return(false);
		}
                if(form.broadbandtype.value == '' && document.getElementById('broadbandid').style.display == "") {
			showMessage(form.broadbandtype, 'Please Enter Broadband Type');
			return(false);
		}*/
                if (form.query.value.length > 500 ) {
                        showMessage(form.query, "Query shouldn't contain more than 500 Characters");
			return(false);
                      }
             if(!isAlphaNumeric(form.volumelimit)) {
                        return(false);
                }
                 if(!isAlphaNumeric(form.query)) {
                        return(false);
                }
                if(!isValidCustomerID(form.referredBy)) {
                        showMessage(form.referredBy, 'Please enter Customer Id as 0.0.0.1-123456 !');
                        return(false);
                }
                
		return(true);
	}
	function showMessage(field, msg) {
		$(field).parent('td').find('p.validationmsg').html(msg).show('fast');
		field.focus();
	}
// Added by Bhavana
       function ValidateZoneSubzone(form)
           {
           $('p.validationmsg').hide();
            
               if(form.zone.value == 'Add New')
                 {
                  form.newzone.value = passwordtrim(form.newzone.value);
                  if(form.newzone.value == '')
                     {
                     showMessage(form.newzone, 'Please Enter zone');
                     return(false);
		     }
                 if(!isAlphaNumeric(form.newzone)) {
                        return(false);
                    }
            }
                
                if(form.subzone.value == 'Add New')
                  {  
                    form.newsubzone.value = passwordtrim(form.newsubzone.value);    
                    if(form.newsubzone.value == '')
                        {
                     showMessage(form.newsubzone, 'Please Enter Subzone');
                     return(false);
		        }
                      if(!isAlphaNumeric(form.newsubzone)) {
                        return(false);
                        }  
                    }
            }

// End bhavana
    function ValidateZone(form) {
		$('p.validationmsg').hide();
		if(form.zone.value == '') {
                   	showMessage(form.zone, 'Please Enter Zone');
			return(false);
		}
                if(!isAlphaNumeric(form.zone)) {
                        return(false);
                }
                return true;
                }
        function numbersonly(e)
          {
             var unicode=e.charCode? e.charCode : e.keyCode
             if (unicode!=8 && unicode != 13)
               {
                   if (unicode<45||unicode>57||unicode==47||unicode == 45) //if not a number
                   {
                   alert("Please Enter Numbers only");
                   return false //disable key press
                   }
               }
         }

         function prevPageClick(page){
            document.forms[1].page.value = page;
            document.forms[1].flag.value = "P";
            document.forms[1].submit();
        }
        function nextPageClick(page){
            document.forms[1].page.value = page;
            document.forms[1].flag.value = "N";
            document.forms[1].submit();
        }
        function prevUserPageClick(page){
            document.forms[1].page.value = page;
            document.forms[1].flag.value = "P";
            document.forms[1].submit();
        }
        function nextUserPageClick(page){
            document.forms[1].page.value = page;
            document.forms[1].flag.value = "N";
            document.forms[1].submit();
        }
        function signup1() {
                document.f1.action="./SubmitHotspot.do";
                document.f1.submit();
        }

        function ValidateHotspotform(form) {
                
		$('p.validationmsg').hide();
		if(form.userId.value == '') {
			showMessage(form.userId, 'Please Enter User-ID');
			return(false);
		}
		
		if(form.password.value == '') {
			showMessage(form.password, 'Please Enter Password');
			return(false);
		}
                
		return(true);
	}
        function HotspotSignUpform(form) {
        $('p.validationmsg').hide();
        if(form.email.value == '') {
			showMessage(form.email, 'Please Enter Email-ID');
			return(false);
		} else {
                    if(!IsValidEmail(form.email))
                   return IsValidEmail(form.email);
                }
                if(form.name.value == '') {
			showMessage(form.name, 'Please Enter Name');
			return(false);
		}
                if(form.userId.value == '') {
			showMessage(form.userId, 'Please Enter User-Id to Sign-Up');
			return(false);
		}
                if(form.mobile.value == '') {
			showMessage(form.mobile, 'Please Enter Contact Number');
			return(false);
		}
                if(form.password.value == '') {
			showMessage(form.password, 'Please Enter Password to Sign-Up');
			return(false);
		}
              
                return(true);
            }
    function ValidateRegisterForm(form){
            
            $('p.validationmsg').hide();
                if(form.name.value == '') {
			showMessage(form.name, 'Please Enter Name');
			return(false);
		}
                if(form.email.value == '') {
			showMessage(form.email, 'Please Enter Email-ID');
			return(false);
		} else {
                     if(!IsValidEmail(form.email))
                   return IsValidEmail(form.email);
                }
                if(form.mobile.value == '') {
			showMessage(form.mobile, 'Please Enter Contact Number');
			return(false);
		} else {
                     if(!IsValidPhone(form.mobile))
                   return IsValidPhone(form.mobile);
                }

                if(form.area.value == '') {
			showMessage(form.area, 'Please Enter Area');
			return(false);
		}
                if(form.userId.value == '') {
			showMessage(form.userId, 'Please Enter User-Id to Sign-Up');
			return(false);
		}
               
                if(form.password.value == '') {
			showMessage(form.password, 'Please Enter Password to Sign-Up');
			return(false);
		}
              
                return(true);

            }
           
   
      function CommonDelete(actionName){
          var checkedValue = "";
          var count=0;
            if(document.forms[1].check.length == undefined){   
                if(document.forms[1].check != undefined){
                    //alert("checked value"+document.forms[1].check.checked);
                        if(document.forms[1].check.checked){
                              document.forms[1].action=actionName;
                              document.forms[1].submit();
                              return true;
                        }else{
                                 alert("Please check the checkbox to Delete");
                                 return false;
                         }
                    } 
                }else {
                        for (var i=0; i < document.forms[1].check.length; i++)
                         {                           
                        if (document.forms[1].check[i].checked)
                           {
                                count++;
                                var checkedValue = document.forms[1].check[i].value;
                            }
                          }
                         if(count > 1)
                         {
                             alert("Please select only one checkbox to Delete");
                             return false;
                         }else if(count == 0){
                            alert("Please select atleast one checkbox to Delete");
                            return false;
                             }
                             if(actionName == "DeleteSalesHierarchy.do")
                                {                                 
                                    if(!confirm("This sales id may have inventory, if deleted inventory has to be mapped manually, Do you want to continue"))
                                    {                                        
                                        return false;
                                    }                                                
                                }
                               document.forms[1].action=actionName;
                               document.forms[1].submit();
                               return true;
                    }
            }
 function changeCseAccountStatus(actionName){
          var checkedValue = "";
          var count=0;
          var selStatus = document.forms[1].cseStatus.value;
            if(document.forms[1].check.length == undefined){   
                if(document.forms[1].check != undefined){
                    //alert("checked value"+document.forms[1].check.checked);
                        if(document.forms[1].check.checked){
                              document.forms[1].action=actionName;
                              document.forms[1].submit();
                              return true;
                        } else {
                                alert("Please check the checkbox to "+selStatus+" CSE ");
                                return false;
                         }
                    } 
                }else {
                        for (var i=0; i < document.forms[1].check.length; i++)
                         {                           
                        if (document.forms[1].check[i].checked)
                           {
                                count++;
                                var checkedValue = document.forms[1].check[i].value;
                            }
                          }
                         if(count > 1)
                         {
                             alert("Please select only one checkbox to "+selStatus+" CSE");
                             return false;
                         }else if(count == 0){
                            alert("Please select atleast one checkbox to "+selStatus+"  CSE");
                            return false;
                             }
                   
                             document.forms[1].action=actionName;
                               document.forms[1].submit();
                               return true;
                    }
            }
function changeShopStatus(actionName){
          var checkedValue = "";
          var count=0;
          var selStatus = document.forms[1].shopStatus.value;
            if(document.forms[1].check.length == undefined){   
                if(document.forms[1].check != undefined){
                    //alert("checked value"+document.forms[1].check.checked);
                        if(document.forms[1].check.checked){
                              document.forms[1].action=actionName;
                              document.forms[1].submit();
                              return true;
                        } else {
                                alert("Please check the checkbox to "+selStatus+" SHOP ");
                                return false;
                         }
                    } 
                }else {
                        for (var i=0; i < document.forms[1].check.length; i++)
                         {                           
                        if (document.forms[1].check[i].checked)
                           {
                                count++;
                                var checkedValue = document.forms[1].check[i].value;
                            }
                          }
                         if(count > 1)
                         {
                             alert("Please select only one checkbox to "+selStatus+" SHOP");
                             return false;
                         }else if(count == 0){
                            alert("Please select atleast one checkbox to "+selStatus+"  SHOP");
                            return false;
                             }
                   
                             document.forms[1].action=actionName;
                               document.forms[1].submit();
                               return true;
                    }
            }
 function CommonModify(actionName){
                var checkedValue = "";
                var count=0;
            if(document.forms[1].check.length == undefined){   
                if(document.forms[1].check != undefined){
                    //alert("checked value"+document.forms[1].check.checked);
                        if(document.forms[1].check.checked){
                              document.forms[1].action=actionName;
                              document.forms[1].submit();
                              return true;
                        }else{
                                 alert("Please check the checkbox to modify");
                                 return false;
                         }
                    } 
                }else {
                        for (var i=0; i < document.forms[1].check.length; i++)
                        {
                            if (document.forms[1].check[i].checked)
                               {
                               count++;
                                var checkedValue = document.forms[1].check[i].value;
                                 } 
                         }
                         if(count > 1)
                         {
                             alert("Please select only one checkbox to Modify");
                             return false;
                         } else if(count == 0){
                             alert("Please select atleast one checkbox to Modify");
                             return false;
                            }          
                          document.forms[1].action=actionName;
                          document.forms[1].submit();
                          return true;         
                 }
            }
   

function CommonInactivateCse(actionName){
          var checkedValue = "";
          var count=0;
            if(document.forms[1].check.length == undefined){   
                if(document.forms[1].check != undefined){
                    //alert("checked value"+document.forms[1].check.checked);
                        if(document.forms[1].check.checked){
                              document.forms[1].action=actionName;
                              document.forms[1].submit();
                              return true;
                        }else{
                                 alert("Please check the checkbox to Inactivate CSE ");
                                 return false;
                         }
                    } 
                }else {
                        for (var i=0; i < document.forms[1].check.length; i++)
                         {                           
                        if (document.forms[1].check[i].checked)
                           {
                                count++;
                                var checkedValue = document.forms[1].check[i].value;
                            }
                          }
                         if(count > 1)
                         {
                             alert("Please select only one checkbox to Inactivate CSE");
                             return false;
                         }else if(count == 0){
                            alert("Please select atleast one checkbox to Inactivate CSE");
                            return false;
                             }
                         /*if(actionName == "DeleteSalesHierarchy.do")
                                {                                 
                                    if(!confirm("This sales id may have inventory, if Inactivated inventory has to be mapped manually, Do you want to continue"))
                                    {                                        
                                        return false;
                                    }                                                
                                }*/
                               document.forms[1].action=actionName;
                               document.forms[1].submit();
                               return true;
                    }
            }
  function changeCseAccountStatus(actionName){
          var checkedValue = "";
          var count=0;
          var selStatus = document.forms[1].cseStatus.value;
            if(document.forms[1].check.length == undefined){   
                if(document.forms[1].check != undefined){
                    //alert("checked value"+document.forms[1].check.checked);
                        if(document.forms[1].check.checked){
                              document.forms[1].action=actionName;
                              document.forms[1].submit();
                              return true;
                        } else {
                                alert("Please check the checkbox to "+selStatus+" CSE ");
                                return false;
                         }
                    } 
                }else {
                        for (var i=0; i < document.forms[1].check.length; i++)
                         {                           
                        if (document.forms[1].check[i].checked)
                           {
                                count++;
                                var checkedValue = document.forms[1].check[i].value;
                            }
                          }
                         if(count > 1)
                         {
                             alert("Please select only one checkbox to "+selStatus+" CSE");
                             return false;
                         }else if(count == 0){
                            alert("Please select atleast one checkbox to "+selStatus+"  CSE");
                            return false;
                             }
                   
                             document.forms[1].action=actionName;
                               document.forms[1].submit();
                               return true;
                    }
            }


 function CommonCloseCse(actionName){
          var checkedValue = "";
          var count=0;
            if(document.forms[1].check.length == undefined){   
                if(document.forms[1].check != undefined){
                    //alert("checked value"+document.forms[1].check.checked);
                        if(document.forms[1].check.checked){
                              document.forms[1].action=actionName;
                              document.forms[1].submit();
                              return true;
                        }else{
                                 alert("Please check the checkbox to Close CSE ");
                                 return false;
                         }
                    } 
                }else {
                        for (var i=0; i < document.forms[1].check.length; i++)
                         {                           
                        if (document.forms[1].check[i].checked)
                           {
                                count++;
                                var checkedValue = document.forms[1].check[i].value;
                            }
                          }
                         if(count > 1)
                         {
                             alert("Please select only one checkbox to close CSE");
                             return false;
                         }else if(count == 0){
                            alert("Please select atleast one checkbox to close CSE");
                            return false;
                             }
                         /*if(actionName == "DeleteSalesHierarchy.do")
                                {                                 
                                    if(!confirm("This sales id may have inventory, if Inactivated inventory has to be mapped manually, Do you want to continue"))
                                    {                                        
                                        return false;
                                    }                                                
                                }*/
                               document.forms[1].action=actionName;
                               document.forms[1].submit();
                               return true;
                    }
            }

   function passwordtrim(str) {
                return str.replace(/^\s+|\s+$/g,"");
            }
      function ValidateSalesForm(form,page){
            
            $('p.validationmsg').hide();
                 
                if(form.firstname.value == '') {
			showMessage(form.firstname, 'Please Enter First Name');
			return(false);
		}
                if(!isAlphaNumeric(form.firstname)) {
                        return(false);
                }
                if(form.lastname.value == '') {
			showMessage(form.lastname, 'Please Enter Last Name');
			return(false);
		}
                if(!isAlphaNumeric(form.lastname)) {
                        return(false);
                }
               if(form.contactnumber.value == '') {
			showMessage(form.contactnumber, 'Please Enter Contact Number');
			return(false);
		}  
                if(!IsNumber(form.contactnumber)){
                        showMessage(form.contactnumber, 'Please Enter Numbers Only');
                        return(false);
                }
                                               
                if(form.contactnumber.value.length < 10 || form.contactnumber.value.length >11 || form.contactnumber.value.charAt(0) != 0){
                        showMessage(form.contactnumber, 'Please enter contact number like 03XXXXXXXXX with ten digits min and max eleven digits');
                        return(false);
                }
                 
                if(form.email.value == '') {
			showMessage(form.email, 'Please Enter Email-ID');
			return(false);
		} else {
                    if(!IsValidEmail(form.email))
                   return IsValidEmail(form.email);
                }
                
                if(form.plot.value == '') {
			showMessage(form.plot, 'Please Enter Plot Info');
			return(false);
		}
                if(!isAlphaNumeric(form.plot)) {
                        return(false);
                }
                if(form.street.value == '') {
			showMessage(form.street, 'Please Enter Street Name');
			return(false);
		}
                if(!isAlphaNumeric(form.street)) {
                        return(false);
                }
                if(form.country) {
                 if(form.country.value == '') {
			showMessage(form.country, 'Please Enter Country');
			return(false);
		}
               
                if(!isAlphaNumeric(form.country)) {
                        return(false);
                }
                }
                if(form.province) {
                     if(form.province.value == '') {
                            showMessage(form.province, 'Please Enter Province');
                            return(false);
                    }
                    /*if(!isAlphaNumeric(form.province)) {
                            return(false);
                    }*/
                }
                if(form.city) {
                     if(form.city.value == '') {
                            showMessage(form.city, 'Please Enter City');
                            return(false);
                    }
                   /* if(!isAlphaNumeric(form.city)) {
                            return(false);
                    }*/
                }
                if(form.zip) {
                 /*if(form.zip.value == '') {
			showMessage(form.zip, 'Please Enter Zip');
			return(false);
		}*/
                if(!IsNumber(form.zip)){
                        showMessage(form.zip, 'Please Enter Numbers Only');
                        return(false);
                }
                
                }
                if(form.zone) {
                     if(form.zone.value == '') {
                            showMessage(form.zone, 'Please Enter Zone');
                            return(false);
                    }
                   /* if(!isAlphaNumeric(form.zone)) {
                            return(false);
                    }*/
                }
                if(form.subzone) {
                     if(form.subzone.value == '') {
                            showMessage(form.subzone, 'Please Enter SubZone');
                            return(false);
                    }
                   /* if(!isAlphaNumeric(form.subzone)) {
                            return(false);
                    }*/
                }
              if(page != 'profile'){
                 form.userId.value = passwordtrim(form.userId.value);
                 if(form.userId) {
                    if(form.userId.value == '') {
                            showMessage(form.userId, 'Please Enter User-Id');
                            return(false);
                    }
                    if(!isAlphaNumeric(form.userId)) {
                            return(false);
                    }
                form.password.value = passwordtrim(form.password.value);
                if(form.password.value == '') {
			showMessage(form.password, 'Please Enter Password');
			return(false);
		} /*else {
                    form.password.value = passwordtrim(form.password.value);
                 }*/
                 form.password1.value = passwordtrim(form.password1.value);
                if(form.password1.value == '') {
			showMessage(form.password1, 'Please Confirm Password');
			return(false);
		} /*else {
                   form.password1.value = passwordtrim(form.password1.value);
                }*/
                if(form.password.value != form.password1.value) {
			showMessage(form.password1, "Passwords doesn't match");
			return(false);
		}
            }
            } else {
                 form.password.value = passwordtrim(form.password.value);
                if(form.password.value == '') {
			showMessage(form.password, 'Please Enter Password');
			return(false);
		} /*else {
                    form.password.value = passwordtrim(form.password.value);
                 }*/
                 form.password1.value = passwordtrim(form.password1.value);
                if(form.password1.value == '') {
			showMessage(form.password1, 'Please Confirm Password');
			return(false);
		}  /*else {
                   form.password1.value = passwordtrim(form.password1.value);
                }*/
                if(form.password.value != form.password1.value) {
			showMessage(form.password1, "Passwords doesn't match");
			return(false);
		}
            
            }
                return(true);

            }
            
           function ValidateShopForm(form){
            
            $('p.validationmsg').hide();

                if(form.shopId.value == '') {
			showMessage(form.shopId, 'Please Enter Shop Id');
			return(false);
		}
                if(!isAlphaNumeric(form.shopId)) {
                        return(false);
                }
                if(form.shopname.value == '') {
			showMessage(form.shopname, 'Please Enter Shop Name');
			return(false);
		}
                if(!isAlphaNumeric(form.shopname)) {
                        return(false);
                }
                if(form.plot.value == '') {
			showMessage(form.plot, 'Please Enter Plot Info');
			return(false);
		}
                if(!isAlphaNumeric(form.plot)) {
                        return(false);
                }
                if(form.street.value == '') {
			showMessage(form.street, 'Please Enter Street Name');
			return(false);
		}
                if(!isAlphaNumeric(form.street)) {
                        return(false);
                }
                 if(form.country.value == '') {
			showMessage(form.country, 'Please Enter Country');
			return(false);
		}
                if(!isAlphaNumeric(form.country)) {
                        return(false);
                }
                 if(form.province.value == '') {
			showMessage(form.province, 'Please Enter Province');
			return(false);
		}
                /*if(!isAlphaNumeric(form.province)) {
                        return(false);
                }*/
                if(form.city.value == '') {
			showMessage(form.city, 'Please Enter City');
			return(false);
		}
                /*if(!isAlphaNumeric(form.city)) {
                        return(false);
                }*/
                 if(!IsNumber(form.zip)){
                        showMessage(form.zip, 'Please Enter Numbers Only');
                        return(false);
                }
              
                 if(form.zone.value == '') {
			showMessage(form.zone, 'Please Enter Zone');
			return(false);
		}
                /*if(!isAlphaNumeric(form.zone)) {
                        return(false);
                }*/
                 if(form.subzone.value == '') {
			showMessage(form.subzone, 'Please Enter SubZone');
			return(false);
		}
                /*if(!isAlphaNumeric(form.subzone)) {
                        return(false);
                }*/
                
                
                
                 /*if(form.zip.value == '') {
			showMessage(form.zip, 'Please Enter Zip');
			return(false);
		}*/
                
                return(true);

            }
        function ValidateRaiseReqForm(form){
            
            $('p.validationmsg').hide();
                var invtype = document.getElementById('itype').options[document.getElementById('itype').selectedIndex].value;
                if(invtype == ''){
                 showMessage(document.getElementById('itype'), 'Please select InventoryType');
			return(false);
                     }
                var subtype = document.getElementById('subtypecpe').options[document.getElementById('subtypecpe').selectedIndex].value;
                if(subtype == ''){
                 showMessage(document.getElementById('subtypecpe'), 'Please select SubType');
			return(false);
                     }
                if(document.getElementById('invmake1').style.display == ""){
                var make = document.getElementById('invmake').options[document.getElementById('invmake').selectedIndex].value;
                if(make == ''){
                       showMessage(document.getElementById('invmake'), 'Please select Make');
			return(false);
                }
                } else {
                var make = document.getElementById('make1').options[document.getElementById('make1').selectedIndex].value;
                if(make == ''){
                       showMessage(document.getElementById('make1'), 'Please select Make');
			return(false);
                }
                }
                if(document.getElementById('invmodel1').style.display == ""){
                var model = document.getElementById('invmodel').options[document.getElementById('invmodel').selectedIndex].value;
                if(model == ''){
                       showMessage(document.getElementById('invmodel'), 'Please select Model');
			return(false);
                }
                } else {

                var model = document.getElementById('model1').options[document.getElementById('model1').selectedIndex].value;
                if(model == ''){
                       showMessage(document.getElementById('model1'), 'Please select Model');
			return(false);
                }
                }
                if(form.numberofdevices.value == '') {
			showMessage(form.numberofdevices, 'Please Enter Quantity');
			return(false);
		}
                if(!IsNumberDevice(form.numberofdevices)){
                        showMessage(form.numberofdevices, 'Please Enter Numbers Only');
                        return(false);
                }
                if(form.numberofdevices.value == 0){
                        showMessage(form.numberofdevices, 'Please Enter Valid Number of devices');
                        return(false);
                }
                if(form.reqbydate.value == '') {
			showMessage(form.reqbydate, 'Please Enter Required By Date');
			return(false);
		}
            }

 function ValidateRaiseVoucherForm(form){
            
            $('p.validationmsg').hide();
                if(form.quantity.value == '') {
			showMessage(form.quantity, 'Please Enter Quantity');
			return(false);
		}
                if(!IsNumberDevice(form.quantity)){
                        showMessage(form.quantity, 'Please Enter Numbers Only');
                        return(false);
                }
                if(form.quantity.value == 0){
                        showMessage(form.quantity, 'Please Enter Valid Quantity');
                        return(false);
                }
}

 function ValidateInventoryLevelReqForm(form){
            
            $('p.validationmsg').hide();
                if(document.getElementById('invmake1').style.display == ""){
                var make = document.getElementById('invmake').options[document.getElementById('invmake').selectedIndex].value;
                if(make == ''){
                       showMessage(document.getElementById('invmake'), 'Please select Make');
			return(false);
                }
                } else {
                var make = document.getElementById('make1').options[document.getElementById('make1').selectedIndex].value;
                if(make == ''){
                       showMessage(document.getElementById('make1'), 'Please select Make');
			return(false);
                }
                }
                if(document.getElementById('invmodel1').style.display == ""){
                var model = document.getElementById('invmodel').options[document.getElementById('invmodel').selectedIndex].value;
                if(model == ''){
                       showMessage(document.getElementById('invmodel'), 'Please select Model');
			return(false);
                }
                } else {

                var model = document.getElementById('model1').options[document.getElementById('model1').selectedIndex].value;
                if(model == ''){
                       showMessage(document.getElementById('model1'), 'Please select Model');
			return(false);
                }
                }
                
                if(form.minlevel.value == '') {
			showMessage(form.minlevel, 'Please Enter Minimum Inventory number');
			return(false);
		}
               if(form.maxlevel.value == '') {
			showMessage(form.maxlevel, 'Please Enter Maximum Inventory number');
			return(false);
		}
                if(!IsNumberInv(form.minlevel)){
                        showMessage(form.minlevel, 'Please Enter Numbers Only');
                        return(false);
                }
                if(!IsNumberInv(form.maxlevel)){
                        showMessage(form.maxlevel, 'Please Enter Numbers Only');
                        return(false);
                }
                if(form.minlevel.value == 0){
                        showMessage(form.minlevel, 'Please Enter Valid Number of devices');
                        return(false);
                }
                 if(form.maxlevel.value == 0){
                        showMessage(form.maxlevel, 'Please Enter Valid Number of devices');
                        return(false);
                }
                 if(parseInt(form.minlevel.value) > parseInt(form.maxlevel.value)){
                        showMessage(form.maxlevel, 'Min level should be greater than Max level');
                        return(false);
                }
               
            }

        function matchCurrentDate()
            {
                Date.format = 'dd/mm/yyyy';
                var D=new Date(),Y=D.getFullYear(),M=D.getMonth()+1;
                var dt=D.getDate();
                var curDate=dt+"/"+M+"/"+Y;
                var cdate=document.forms[1].reqbydate.value;
                if(Date.parse(curDate)>Date.parse(cdate))
                {
                        alert("Required By Date can not be less than todays date");
                        return false;
                }
                else
                        return true;
            }
// trim String
    function trim(str) {
            chars = "\\n" || "\\s";
            return ltrim(rtrim(str, chars), chars);
    }
    function ltrim(str, chars) {

            return str.replace(new RegExp("^[" + chars + "]+", "g"), "");
    }
 
    function rtrim(str, chars) {

            return str.replace(new RegExp("[" + chars + "]+$", "g"), "");
    }

 

 function getShopListforSales(salestype, salesid){            
                       
            document.getElementById('noshops').style.display = "none";
            document.getElementById('childsalespersonnelid').style.display = "none";
            document.getElementById('shoprow').style.display = "none";
            document.getElementById('nochilds').style.display = "";
            document.getElementById('submitbutton').style.display = "none";
            if(salesid != "")
            {            
            callAjaxForParentSHandShop(salestype, salesid);
            if(salestype == "TL")
            {
                document.getElementById('shoprow').style.display = "";
                document.getElementById('childsalespersonnelid').style.display = "";
               // callAjaxForShopbyTL(salesid);
            }
            else
            {
                document.getElementById('shoprow').style.display = "none";                
            }
            }
            
        }
function checkOtherSubZone(value){
if(value == "other"){
document.getElementById('othersubzone').style.display = "";
document.getElementById('othersubzone1').style.display = "";
} else {
document.getElementById('othersubzone').style.display = "none";
document.getElementById('othersubzone1').style.display = "none";
}
}

function CheckAddSubzone(value){
if(value == "Add New"){
document.getElementById('newsubzone').style.display = "";
} else {
document.getElementById('newsubzone').style.display = "none";
}
}

var fromlead = 0;
function fetchSubAddressLead(type,address){
     // alert(type+address);
        var childid;
        if(type == 2){		
                document.getElementById('city').options.length = 1;
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;

	}
	else if(type == 3){            
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("zone");
          
	}
	else if(type == 4){	                
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("subzone");
                if(address == 'other'){   
                           //document.getElementById('subzone').options.length = 0;
                           var nOption = document.createElement('option'); 
			   var isText = document.createTextNode('other');
			   nOption.setAttribute('value','other');
			   nOption.appendChild(isText); 
			   document.getElementById('subzone').appendChild(nOption);
                           
            }
            
	}
if(address !="")
{
    if(address != 'other'){   
        var flag = 1;
        callAjaxForSubAddress(type,address,flag);
        fromlead = 1;
        document.getElementById('otherzone').style.display = "none";
        document.getElementById('othersubzone').style.display = "none";
        document.getElementById('otherzone1').style.display = "none";
        document.getElementById('othersubzone1').style.display = "none";
               } else {
            document.getElementById('otherzone').style.display = "";
            document.getElementById('otherzone1').style.display = "";
            if(document.getElementById('subzone').options[document.getElementById('subzone').selectedIndex].value == ""){
                         document.getElementById('othersubzone').style.display = "none";  
                         document.getElementById('othersubzone1').style.display = "none"; 
                        }     
     }
} else {
    document.getElementById('otherzone').style.display = "none";
    document.getElementById('otherzone1').style.display = "none";
    document.getElementById('othersubzone').style.display = "none";
    document.getElementById('othersubzone1').style.display = "none";
}
 }

//Added for HotSpot Address
function fetchSubAddressLeadHotSpot(type,address){
     // alert(type+address);
        var childid;
        if(type == 2){		
                document.getElementById('city').options.length = 1;
            }
if(address !="")
{
        var flag = 1;
        callAjaxForSubAddressHotSpot(type,address,flag);
        
 }

 }

function callAjaxForSubAddressHotSpot(type,address,flag) {
  //alert("calledhere");
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforSubaddressHotSpot;    
    globaltype = type;    
    var url ="AjaxAction.do?fun=getSubAddressbyType&type="+type+"&address="+address+"&flag="+flag;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);     
 } 
function processResponseforSubaddressHotSpot() {
    if(xmlHttp.readyState == 4)
    {
        var results = xmlHttp.responseText; 
        var theOptionschild = "";      
	var childid ="";
	if(globaltype == 2){
		childid =document.getElementById("city");                
	}
	if(results)
	{
		theOptionschild = results.split(',');           
		if(theOptionschild.length > 0)
		{
                        
			for(i = 0; i < theOptionschild.length; i++) {           
			   var nOption = document.createElement('option'); 
			   var isText = document.createTextNode(theOptionschild[i]);
			   nOption.setAttribute('value',theOptionschild[i]);
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
			 }
   		}        
	}
   }
}

//End
// Added for Zones and subzones to add

function AddNewZoneSubzone(type,address){
     // alert(type+address);
        var childid;
        var fromlead = 0;
        if(type == 1){
                document.getElementById('province').options.length = 1;	
                document.getElementById('city').options.length = 1;
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("province");
   	}
        else if(type == 2){		
                document.getElementById('city').options.length = 1;
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("city");
                /*if(address == 'Add New'){   
                           //document.getElementById('subzone').options.length = 0;
                           var nOption = document.createElement('option'); 
			   var isText = document.createTextNode('Add New');
			   nOption.setAttribute('value','Add New');
			   nOption.appendChild(isText); 
			   document.getElementById('city').appendChild(nOption);            
                        }*/
    	}
	else if(type == 3){            
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("zone");
                if(address == 'Add New'){   
                           //document.getElementById('subzone').options.length = 0;
                           var nOption = document.createElement('option'); 
			   var isText = document.createTextNode('Add New');
			   nOption.setAttribute('value','Add New');
			   nOption.appendChild(isText); 
			   document.getElementById('zone').appendChild(nOption);            
                        }
    	}
	else if(type == 4){	                
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("subzone");
                
                if(address == 'Add New'){   
                           //document.getElementById('subzone').options.length = 0;
                           var nOption = document.createElement('option'); 
			   var isText = document.createTextNode('Add New');
			   nOption.setAttribute('value','Add New');
			   nOption.appendChild(isText); 
			   document.getElementById('subzone').appendChild(nOption);            
                        }
            }
if(address !="")
{
    if(address != 'Add New'){ 
        var flag = 1;
        fromlead = 1;
        callAjaxForAddZoneSubzone(type,address,flag);
        
        //document.getElementById('newprovince').style.display = "none";
        //document.getElementById('newcity').style.display = "none";
        document.getElementById('newzone').style.display = "none";
        document.getElementById('newsubzone').style.display = "none";
        } else {
            //document.getElementById('newprovince').style.display = "";
           // document.getElementById('newcity').style.display = "";
            document.getElementById('newzone').style.display = "";
            document.getElementById('newsubzone').style.display = "";
             /*if(document.getElementById('province').options[document.getElementById('province').selectedIndex].value != "Add New"){
                         document.getElementById('newprovince').style.display = "none";  
                        } 
            if(document.getElementById('city').options[document.getElementById('city').selectedIndex].value != "Add New"){
                         document.getElementById('newcity').style.display = "none";  
                        } */
            if(document.getElementById('zone').options[document.getElementById('zone').selectedIndex].value != "Add New"){
                         document.getElementById('newzone').style.display = "none";  
                        }  
            if(document.getElementById('subzone').options[document.getElementById('subzone').selectedIndex].value != "Add New"){
                         document.getElementById('newsubzone').style.display = "none";  
                        }   
               
     }
} else {
    /*document.getElementById('newprovince').style.display = "none";
    document.getElementById('newcity').style.display = "none";
    document.getElementById('newzone').style.display = "none";
    document.getElementById('newsubzone').style.display = "none";
    if(document.getElementById('province').options[document.getElementById('province').selectedIndex].value != "Add New"){
                         document.getElementById('newprovince').style.display = "none";  
                        } 
            if(document.getElementById('city').options[document.getElementById('city').selectedIndex].value != "Add New"){
                         document.getElementById('newcity').style.display = "none";  
                        } */
            if(document.getElementById('zone').options[document.getElementById('zone').selectedIndex].value != "Add New"){
                         document.getElementById('newzone').style.display = "none";  
                        }  
            if(document.getElementById('subzone').options[document.getElementById('subzone').selectedIndex].value != "Add New"){
                         document.getElementById('newsubzone').style.display = "none";  
                        }   
}
 }
// end
// Delete Zone and Subzone

function DeleteNewZoneSubzone(type,address){
     // alert(type+address);
        var childid;
        var fromlead = 0;
        if(type == 1){
                document.getElementById('province').options.length = 1;	
                document.getElementById('city').options.length = 1;
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("province");
   	}
        else if(type == 2){		
                document.getElementById('city').options.length = 1;
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("city");
    	}
	else if(type == 3){            
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("zone");
    	}
	else if(type == 4){	                
                document.getElementById('subzone').options.length = 1;
                childid =document.getElementById("subzone");
       }
if(address !="")
{
        var flag = 1;
        fromlead = 1;
        callAjaxForDeleteZoneSubzone(type,address,flag);
     }
} 
 
//End

function fetchSubAddress(type,address){
     // alert(type+address);
        if(type == 2){		
                document.getElementById('city').options.length = 1;
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;

	}
	else if(type == 3){            
                document.getElementById('zone').options.length = 1;
                document.getElementById('subzone').options.length = 1;
          
	}
	else if(type == 4){	                
                document.getElementById('subzone').options.length = 1;
            }
            
if(address !="")
{
    if(address != 'other'){
        var flag = 0;
        callAjaxForSubAddress(type,address,flag);
        
        } 
} 
 }
 ////////////////////Start Ajax Calls /////////////////////////////////////
function getHTTPObject()
{
    var xmlHttp;
    if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
       } else if (window.ActiveXObject) {
        isIE = true;
       // alert("ie");
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }
    return xmlHttp;
 }   
 var xmlHttp;
var xmlHttp1;
/// Start List shops The following ajax code is to fetch the list of shops for tl 
 function callAjaxForShopbyTL(salesid) {
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforShop;
    //var salesType = document.getElementById("salestype");
    var url ="AjaxAction.do?fun=callAjaxForShopbyTL&salesid="+salesid;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 
 } 
   function processResponseforShop() {
    if(xmlHttp.readyState == 4){
      var shopid = document.getElementById("shopid");
      var results = xmlHttp.responseText; 
      //alert(results);
      shopid.length = 0;
      var theOptions = results.split(',');
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           nOption.setAttribute('value',theOptions[i]);
           nOption.appendChild(isText); 
           shopid.appendChild(nOption);    
        }
    }
  }

function callAjaxForParentSH(salestype,salesid) {
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforChildSH;
    //var salesType = document.getElementById("salestype");
    var url ="AjaxAction.do?fun=callAjaxForParentSH&salesid="+salesid+"&salestype="+salestype;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
     
 } 
   function processResponseforChildSH() {
    if(xmlHttp.readyState == 4){
      var childid = document.getElementById("childsalespersonnelid");
      var results = xmlHttp.responseText; 
      childid.length = 0;
      childid.options.length = 0;
      document.getElementById('childsalespersonnelid').style.display = "";
      var theOptions = results.split(',');
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           nOption.setAttribute('value',theOptions[i]);
           nOption.appendChild(isText); 
           childid.appendChild(nOption);    
        }
    }
  }
var globalsalestype;
var globaltype;

function callAjaxForSubAddress(type,address,flag) {
  //alert("calledhere");
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforSubaddress;    
    globaltype = type;    
    var url ="AjaxAction.do?fun=getSubAddressbyType&type="+type+"&address="+address+"&flag="+flag;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);     
 } 
function callAjaxForAddZoneSubzone(type,address,flag) {
  //alert("calledhere");
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforZoneSubzone;    
    globaltype = type;    
    var url ="AjaxAction.do?fun=getSubAddressbyType&type="+type+"&address="+address+"&flag="+flag;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);     
 }
function callAjaxForDeleteZoneSubzone(type,address,flag) {
  //alert("calledhere");
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforDeleteZoneSubzone;    
    globaltype = type;    
    var url ="AjaxAction.do?fun=getSubAddressbyType&type="+type+"&address="+address+"&flag="+flag;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);     
 }
function processResponseforSubaddress() {
    if(xmlHttp.readyState == 4)
    {
        var results = xmlHttp.responseText; 
        var theOptionschild = "";      
	var childid ="";
	if(globaltype == 2){
		childid =document.getElementById("city");                
	}
	else if(globaltype == 3){
		childid =document.getElementById("zone");                
	}
	else if(globaltype == 4){
		childid =document.getElementById("subzone");
	}
	if(results)
	{
		theOptionschild = results.split(',');           
		if(theOptionschild.length > 0)
		{
                        
			for(i = 0; i < theOptionschild.length; i++) {           
			   var nOption = document.createElement('option'); 
			   var isText = document.createTextNode(theOptionschild[i]);
			   nOption.setAttribute('value',theOptionschild[i]);
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
			 }
                        
                         if((globaltype == 3 || globaltype == 4) && fromlead == 1)
                            {   
                                fromlead = 0;
                           var nOption = document.createElement('option'); 
			   var isText = document.createTextNode('other');
			   nOption.setAttribute('value','other');
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
                        }
                    
		}        
	}
           
       
   }
}
//Delete Zone and Subzone by Swapna
function processResponseforDeleteZoneSubzone(){
 if(xmlHttp.readyState == 4)
    {
        var results = xmlHttp.responseText; 
        var theOptionschild = "";      
	var childid ="";
        if(globaltype == 1){
		childid =document.getElementById("province");                
	}
	if(globaltype == 2){
		childid =document.getElementById("city");                
	}
	else if(globaltype == 3){
		childid =document.getElementById("zone");                
	}
	else if(globaltype == 4){
		childid =document.getElementById("subzone");
	}
	if(results)
	{
		theOptionschild = results.split(',');           
		if(theOptionschild.length > 0)
		{
                        
			for(i = 0; i < theOptionschild.length; i++) {           
			   var nOption = document.createElement('option'); 
			   var isText = document.createTextNode(theOptionschild[i]);
			   nOption.setAttribute('value',theOptionschild[i]);
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
			 }
                                             
		}        
	} 
   }
}//End
function processResponseforZoneSubzone() {
    if(xmlHttp.readyState == 4)
    {
        var results = xmlHttp.responseText; 
        var theOptionschild = "";      
	var childid ="";
        if(globaltype == 1){
		childid =document.getElementById("province");                
	}
	if(globaltype == 2){
		childid =document.getElementById("city");                
	}
	else if(globaltype == 3){
		childid =document.getElementById("zone");                
	}
	else if(globaltype == 4){
		childid =document.getElementById("subzone");
	}
	if(results)
	{
		theOptionschild = results.split(',');           
		if(theOptionschild.length > 0)
		{
                        
			for(i = 0; i < theOptionschild.length; i++) {           
			   var nOption = document.createElement('option'); 
			   var isText = document.createTextNode(theOptionschild[i]);
			   nOption.setAttribute('value',theOptionschild[i]);
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
			 }
                          if(globaltype == 3 || globaltype == 4)
                            {   
                           var nOption = document.createElement('option'); 
			   var isText = document.createTextNode('Add New');
			   nOption.setAttribute('value','Add New');
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
                    }   
                    
		}        
	} else {
            
                    if(globaltype == 3 || globaltype == 4)
                            {   
                           var nOption = document.createElement('option'); 
			   var isText = document.createTextNode('Add New');
			   nOption.setAttribute('value','Add New');
			   nOption.appendChild(isText); 
			   childid.appendChild(nOption);    
                    }

            }
           
       
   }
}



function callAjaxForParentSHandShop(salestype,salesid) {
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforChildSHandShop;
    //var salesType = document.getElementById("salestype");
    globalsalestype = salestype;
    var url ="AjaxAction.do?fun=callAjaxForParentSHandShop&salesid="+salesid+"&salestype="+salestype;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);     
 } 

function processResponseforChildSHandShop() {
    if(xmlHttp.readyState == 4){
      var childid = document.getElementById("childsalespersonnelid");
      var shopid = document.getElementById("shopid");
      var results = xmlHttp.responseText; 
      childid.length = 0;
      childid.options.length = 0;
      
      shopid.length = 0;
      shopid.options.length = 0;
      
      
      var theElements = results.split('||');
      var theOptionschild = "";
      //alert(theElements[0]);
      //alert(theElements[1]);
      if(theElements[0])
      {
       theOptionschild = theElements[0].split(',');           
      if(theOptionschild.length > 0)
      {
        
      //  childid.length = 0;
        document.getElementById('childsalespersonnelid').style.display = "";
        document.getElementById('nochilds').style.display = "none";
        document.getElementById('submitbutton').style.display = "";
        for(i = 0; i < theOptionschild.length; i++) {
           
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptionschild[i]);
           nOption.setAttribute('value',theOptionschild[i]);
           nOption.appendChild(isText); 
           childid.appendChild(nOption);    
         }
        }        
      }
      else
        {
            document.getElementById('childsalespersonnelid').style.display = "none";            
            document.getElementById('nochilds').style.display = "";
            document.getElementById('submitbutton').style.display = "none";
         }
      if(theElements[1])
      {
        var theOptionsshop = theElements[1].split(',');
     
      if(theOptionsshop.length > 0)
      {
      document.getElementById('shopid').style.display = "";
      document.getElementById('noshops').style.display = "none";
      if(theOptionschild.length > 0)
      {
         document.getElementById('submitbutton').style.display = "";
      }
      //shopid.length = 0;
        for(i = 0; i < theOptionsshop.length; i++) {
           
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptionsshop[i]);
           nOption.setAttribute('value',theOptionsshop[i]);
           nOption.appendChild(isText); 
           shopid.appendChild(nOption);    
        }
      }
       
      }
      else
        {             
            document.getElementById('noshops').style.display = "";
            document.getElementById('shopid').style.display = "none";                       
            if(theOptionschild.length > 0  && (globalsalestype == "TL"))
            {                
                document.getElementById('submitbutton').style.display = "none";
            }
        }
       
    }
  }

/// End List shops


////start ajax call for assign tl to shop sunil

 function callAjaxForTLtoShop(shopid) {
  // alert(shopid);
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseForTLtoShop;
    var url ="AjaxAction.do?fun=callAjaxForTLtoShop&shopid="+shopid;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
     
 } 
   function processResponseForTLtoShop() {
    if(xmlHttp.readyState == 4){
      var salesid = document.getElementById("salespersonnel_id");
      var shopzone = document.getElementById("zone");
      var results = xmlHttp.responseText; 
      //alert(results);
      
      if(results != 'exception') {
      var theOptions = results.split(',');
      if(theOptions.length > 0 ){
        for(j = 0; j < theOptions.length; j++) {
        var index = theOptions[j].lastIndexOf('@');
        var zoneval = theOptions[j].substring(index+1);
        shopzone.setAttribute('value',zoneval);
        }
        while (salesid.hasChildNodes()) {
        salesid.removeChild(salesid.firstChild);
       }
        //salesid.removeAllChild(); 
       // salesid.length = 0;
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var index1 = theOptions[i].lastIndexOf('@');
           var theoptionval = theOptions[i].substring(0,index1);
           var isText = document.createTextNode(theoptionval);
           var val = (theoptionval.split('-'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           salesid.appendChild(nOption); 
           
        }
}
        }
    }
  }

////end ajax call for assign tl to shop sunil


////start ajax call for Resources for resource type bhawna
 function callAjaxForVoucherInfo(resType) {
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforVoucher;
   
    var url ="AjaxAction.do?fun=callAjaxForVoucherInfo&resType="+resType;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 
 } 
   function processResponseforVoucher() {
    if(xmlHttp.readyState == 4){
      var voucherInfo = document.getElementById("voucherInfo");
      var results = xmlHttp.responseText; 
      //alert(results);
      voucherInfo.length = 0;
      var theOptions = results.split(',');
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           nOption.setAttribute('value',theOptions[i]);
           nOption.appendChild(isText); 
           voucherInfo.appendChild(nOption);    
        }
    }
  }

/// Start Create sales person ajax call

function checkDuplicate(){
 $('p.validationmsg').hide();
  document.getElementById('cityspan').style.display = "none";
  document.getElementById('zonespan').style.display = "none";
  document.getElementById('subzonespan').style.display = "none";
  document.getElementById('cityspanstar').style.display = "none";
  document.getElementById('zonespanstar').style.display = "none";
  document.getElementById('subzonespanstar').style.display = "none";
  var salestype = document.getElementById("salestype").value;
  var province = document.getElementById("province").value;
  var city = document.getElementById("city").value;
  var country = document.getElementById("country").value;
  var url ="AjaxAction.do";
  url = url+"?salestype="+salestype;
  url = url+"&fun=checkDuplicate";
  url = url+"&city="+city;
  url = url+"&country="+country;
  url = url+"&province="+province; 
  if(salestype == '1' || salestype == '-1' ) {
    document.getElementById('cityspanstar').style.display = "";
    document.getElementById('zonespanstar').style.display = "";
    document.getElementById('subzonespanstar').style.display = "";
    document.getElementById('channeltypeid').style.display = "none";
    }else if(salestype == '2') {
    document.getElementById('cityspan').style.display = "";
    document.getElementById('zonespanstar').style.display = "";
    document.getElementById('subzonespanstar').style.display = "";  
    document.getElementById('channeltypeid').style.display = "none";
    xmlHttp = getHTTPObject();
    xmlHttp.onreadystatechange = respCheckDuplicate;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
   } else if (salestype == '3') {
     document.getElementById('cityspan').style.display = "";
     document.getElementById('zonespan').style.display = "";
     document.getElementById('subzonespanstar').style.display = "";
     document.getElementById('channeltypeid').style.display = "none";
     //document.getElementById('cityspanstar').style.display = "none";
     //document.getElementById('zonespanstar').style.display = "none";
     xmlHttp = getHTTPObject();
     var zone = document.getElementById("zone").value;
     url = url+"&zone="+zone;
     xmlHttp.onreadystatechange = respCheckDuplicate;
     xmlHttp.open("GET", url, true);
     xmlHttp.send(null);
    }else{
        document.getElementById('cityspan').style.display = "";
        document.getElementById('zonespan').style.display = "";
        document.getElementById('subzonespan').style.display = "";
        document.getElementById('channeltypeid').style.display = "";
        //document.getElementById('cityspanstar').style.display = "none";
        //document.getElementById('zonespanstar').style.display = "none";
        //document.getElementById('subzonespanstar').style.display = "none";
    }
 
    return true;
    
  }
  function respCheckDuplicate() {
       if(xmlHttp.readyState == 4){
            var value = xmlHttp.responseText;
            var form = document.getElementById("salesform");
            if(value == 'duplicateCity') {
                showMessage(form.city, 'Duplicate City');
                return (false);
             } else if(value == 'duplicateZone') {
                showMessage(form.zone, 'Duplicate Zone');
                return (false);
             }else if(value == 'exception') {
                showMessage(form.salestype, 'System Error occured');
                return (false);
             }
            
        } 

  }

/// End Create sales person ajax call

////////////////////End Ajax Calls /////////////////////////////////////
var arrpin = new Array('!','@','#','%','^','&','*','(',')','_','+','=','{','}','[',']','|',"\\",':','\"',';'," '   ",'~','`','<','>','?',
											',',' ','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A',
											'B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');

/* function checkReason(form) {
             if(form.reason.value == 'other' ) {
                    $('p.validationmsg').hide();
                    if (form.reason1.value.length > 150 ) {
                        showMessage(form.reason1, 'Only 150 characters allowed for reason !');
			return(false);                                           
                        }
                     if (form.reason1.value == '' ) {
                        showMessage(form.reason1, 'Please enter the reason !');
			return(false); 
                        }
                     if(!isAlphaNumeric(form.reason1)) {
                           return(false);  
                        }
                }
                return(true);
            }*/

function checkReason(form) {
            
                    $('p.validationmsg').hide();
                    if (form.reasonComment.value.length > 150 ) {
                        showMessage(form.reasonComment, 'Only 150 characters allowed for reason !');
			return(false);                                           
                        }
                     if (form.reasonComment.value == '' ) {
                        showMessage(form.reasonComment, 'Please enter the reason !');
			return(false); 
                        }
                     if(!isAlphaNumeric(form.reasonComment)) {
                           return(false);  
                        }
              
            return(true);
}

function isAlphaNumeric(obj) {
                             $('p.validationmsg').hide();
                                var numberFilter=/^[a-zA-Z0-9-/ ]{0,}$/;//allow alpha numrics only
                                var isAlpha = numberFilter.test(obj.value)
                                if(!isAlpha) {
                                    showMessage(obj, 'Please enter Alpha Numeric values !');
                                    return(false); 
                                    }
                                return(true);
                }
function isValidCustomerID(obj){
    var numberFilter=/^[0].[0].[0].[1]-[0-9]*/;//allow alpha numrics only
    var isAlpha = numberFilter.test(obj.value);
    if(obj.value != ''){
      if(!isAlpha) {
         showMessage(obj, "Please enter Customer Id as 0.0.0.1-123456 !");
         return(false); 
        }
    }
    return(true);

}
// submit for show lead screen
 function formSubmit(actionName){
                document.forms[1].action=actionName;
                document.forms[1].submit();
            }
            function formSubmitAccount(actionName){
                 formSubmit(actionName);
            /*alert(actionName);
            var cCheckFlag = document.locater.cCheckFlag.value;
               if(cCheckFlag == 'Not Checked') {
                    if(confirm('Do you want to continue without Coverage Check ?')) {
                        formSubmit(actionName);
                     } 
                 } else if(cCheckFlag == 'Not Available') {
                    if(confirm('Do you want to continue without Coverage ?')) {
                         formSubmit(actionName);
                     }
                 } else {*/
                       
                // }
                }
var Fwin = null;
Fwin_Wid = 400;
Fwin_Hgt = 320;
Fwin_Left = (screen) ? screen.width/2 - Fwin_Wid/2 : 100;
Fwin_Top =  (screen) ? screen.availHeight/2 - Fwin_Hgt/2 : 100;
function openFormWin(url) {
Fwin = open(url,'Fwin','width='+Fwin_Wid+',height='+Fwin_Hgt+',left='+
Fwin_Left+',top='+Fwin_Top+'');
setTimeout('Fwin.focus()',100);
}
// phone number  validation
 function IsValidPhone(number) {
        $('p.validationmsg').hide();
        var phformat1=/^\+92-\d{3}-\d{7}$/;
        var phformat2=/^\+92 \d{3} \d{7}$/;
        if(phformat1.test(number.value) || phformat2.test(number.value))
            return true;
        else
        showMessage(number, 'Number format should be  +92-xxx-xxxxxxx');
            return false;    
    }
function isValidCNIC(form) {
    $('p.validationmsg').hide();
    if(document.getElementById('CNIC').style.display == "" || document.getElementById('Passport').style.display == "")
        {
        if(form.CNICid.value != '' || form.Passport.value != ''){
        var nationality = document.forms[1].nationality.options[document.forms[1].nationality.selectedIndex].value;
       
        if(nationality == "1") {
            var val = document.forms[1].CNICid.value;
            var regExpCNIC = /^[0-9]{13}$/;
                if(regExpCNIC.test(val)){
                    return true;
                } else {
                    showMessage(form.CNICid, 'CNIC Id should be 13 digit number');
                    //alert("CNIC Id should be 13 digit number");
                    return false;
                }
          
        }
        else {
            var val = document.forms[1].Passport.value;
            var regExpPP=/^[a-zA-Z0-9]{5,20}$/;
                 if(regExpPP.test(val)){
                  return true;
                } else {
                    showMessage(form.Passport, 'Passport should be Alphanumeric');
                    //alert("Passport should be Alphanumeric");
                    return false;
                }

        }
    }
}

    return true;
}

function callajaxReason(reasonid){
                    if(reasonid == 11){
                        //alert("hi");
                        } else if(reasonid == 12) {
                        } else if(reasonid == 13){
                        } else{
                     callAjaxForReason(reasonid);
                    }
                }
         function callAjaxForReason(reasonid) {
                //alert(salesid);
                xmlHttp = getHTTPObject();
                xmlHttp.onreadystatechange = processResponseforReason;        
                var url ="AjaxAction.do?fun=getProspectReason&reasonId="+reasonid;
                //alert("tom1");
                xmlHttp.open("GET", url, true);
                xmlHttp.send(null);     
             } 

       function processResponseforReason() {
                if(xmlHttp.readyState == 4)
                {
                    var result = xmlHttp.responseText; 
                    var childid ="";
                    childid =document.getElementById('transitionState');
                    childid.setAttribute('value',result);
               }
            }
// Added by Swapna
function callAJAXZoneList(city) {
//alert(' zone ' + city);
if(city != null && city != ''){
 xmlHttp1 = getHTTPObject();
 xmlHttp1.onreadystatechange = processResponseforCity;
    var url ="AjaxAction.do?fun=callAJAXZoneList&city="+city;
    xmlHttp1.open("GET", url, true);
    xmlHttp1.send(null);
 }
 } 
   function processResponseforCity() {
    if(xmlHttp1.readyState == 4){
      var zoneid = document.getElementById("zoneid");
      var results = xmlHttp1.responseText; 
      //alert(results);
      zoneid.length = 0;
      var theOptions = results.split(',');
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           nOption.setAttribute('value',theOptions[i]);
           nOption.appendChild(isText); 
           zoneid.appendChild(nOption);    
        }
    }

}

/*function callAJAXProductList(city) {
//alert(' product ' + city);
if(city != null && city != ''){
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforCity_Product;
    var url ="AjaxAction.do?fun=callAJAXProductList&city="+city;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }
 } 
   function processResponseforCity_Product() {
    if(xmlHttp.readyState == 4){
      var productid = document.getElementById("productid");
      var results = xmlHttp.responseText; 
      //alert(results);
      productid.length = 0;

      var theOptions = results.split(',');
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('-'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           productid.appendChild(nOption);     
        }
    }

}*/
function callAJAXProductList(city,Product_Id) {
if(city != null && city != ''){
 xmlHttp = getHTTPObject();
xmlHttp.onreadystatechange = function(){ processResponseforCity_Product(Product_Id); }
  var url ="AjaxAction.do?fun=callAJAXProductList&city="+city;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }
 } 

  function processResponseforCity_Product(Product_ID) {
    if(xmlHttp.readyState == 4){
      var productid = document.getElementById("productid");
      var results = xmlHttp.responseText; 
      //alert(results);
      productid.length = 0;
      var theOptions = results.split(',');
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('-'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           productid.appendChild(nOption);
          if(val == Product_ID) {
                productid.options[i].selected=true;
             //   alert("DONE")
          }
        }
    }

}



function callTransferInventory(type,salesid) {
if(salesid != null && salesid != ''){
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforTransfer;
    //var salesType = document.getElementById("salestype");
    globaltype = type;
    var url ="AjaxAction.do?fun=callTransferInventory&salesid="+salesid+"&type="+type;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }
 } 
   function processResponseforTransfer() {
    if(xmlHttp.readyState == 4){
    if(globaltype == 2){
    //alert("hi");
      var childRSMId = document.getElementById("childRSMId");
      var tochildRSMId = document.getElementById("tochildRSMId");
      
      var results = xmlHttp.responseText; 
      //alert(results);
      childRSMId.length = 1;
      tochildRSMId.length = 1;
      var theOptions = results.split(',');
      
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('-'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           childRSMId.appendChild(nOption);    
        }
        for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('-'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           tochildRSMId.appendChild(nOption);    
        }
      
        
    } else if(globaltype == 5){
        var tochildTLId = document.getElementById("tochildTLId");
      
      var results = xmlHttp.responseText; 
      //alert(results);
      tochildTLId.length = 1;
    
      var theOptions = results.split(',');
      for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('-'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           tochildTLId.appendChild(nOption);    
        }
   
    
    }else if(globaltype == 1){
            var childTLId = document.getElementById("childTLId");
            var tochildTLId = document.getElementById("tochildTLId");
            var results = xmlHttp.responseText; 
              //alert(results);
              childTLId.length = 1;
              tochildTLId.length = 1;
              var theOptions = results.split(',');
            for(i = 0; i < theOptions.length; i++) {
               var nOption = document.createElement('option'); 
               var isText = document.createTextNode(theOptions[i]);
               var val = (theOptions[i].split('-'))[0];
               nOption.setAttribute('value',val);
               nOption.appendChild(isText); 
               childTLId.appendChild(nOption);    
            }
            for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('-'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           tochildTLId.appendChild(nOption);    
        }
     }else if(globaltype == 3) {
        var fromShopId = document.getElementById("shopId");
        var results = xmlHttp.responseText; 
          fromShopId.length = 1;
        var theOptions = results.split(',');
        for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('@'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           fromShopId.appendChild(nOption);
        } 
} else if(globaltype == 6) {
        var toShopId = document.getElementById("toShop");
        var results = xmlHttp.responseText; 
        toShopId.length = 0;

        var theOptions = results.split(',');
        for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           var val = (theOptions[i].split('@'))[0];
           nOption.setAttribute('value',val);
           nOption.appendChild(isText); 
           toShopId.appendChild(nOption);   
            
        }

    } else if(globaltype == 4) {

        var invId = document.getElementById("itype");
        var results = xmlHttp.responseText; 
        //alert(results);
        invId.length = 0;
        var theOptions = results.split(',');
       /* if(theOptions.length > 0){
            while (invId.hasChildNodes()) {
                invId.removeChild(invId.firstChild);
                }
            }*/
        for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var isText = document.createTextNode(theOptions[i]);
           //var val = (theOptions[i].split('-'))[0];
           nOption.setAttribute('value',theOptions[i]);
           nOption.appendChild(isText); 
           invId.appendChild(nOption);    
        }
    }
  }
}

function callSpecificInventory(invtype,invsubtype) {

if(invtype != null && invtype != '' && invsubtype != null && invsubtype != ''){
 xmlHttp = getHTTPObject();

 xmlHttp.onreadystatechange = processResponseforSpecificInv;
   // globaltype = type;
    var url ="AjaxAction.do?fun=callSpecificInventory&invtype="+invtype+"&invsubtype="+invsubtype;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }
 } 
   function processResponseforSpecificInv() {

    if(xmlHttp.readyState == 4){
       
            var make = document.getElementById('invmake');
            var model = document.getElementById('invmodel');
      var results = xmlHttp.responseText; 
      make.length = 0;
        model.length = 0;
        var theOptions = results.split(',');
        var eoption = document.createElement('option');
        eoption.setAttribute('value',"");
        var isText1 = document.createTextNode("");
        eoption.appendChild(isText1); 
        make.appendChild(eoption); 
       for(i = 0; i < theOptions.length; i++) {
           var nOption = document.createElement('option'); 
           var val = (theOptions[i]);
           nOption.setAttribute('value',val);
           var isText = document.createTextNode(val);
           nOption.appendChild(isText); 
           make.appendChild(nOption); 
        }

}
}
function callSpecificMakeInventory(invtype,invsubtype,make) {

if(invtype != null && invtype != '' && invsubtype != null && invsubtype != '' && make != null && make != ""){
 xmlHttp = getHTTPObject();

 xmlHttp.onreadystatechange = processResponseforSpecificInvMake;
   // globaltype = type;
    var url ="AjaxAction.do?fun=callSpecificMakeInventory&invtype="+invtype+"&invsubtype="+invsubtype+"&make="+make;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }
 } 
   function processResponseforSpecificInvMake() {

    if(xmlHttp.readyState == 4){
           var model = document.getElementById('invmodel');
           var results = xmlHttp.responseText; 

           model.length = 0;
           var theOptions = results.split(',');
      
   for(j = 0; j < theOptions.length; j++) {
           var nOption = document.createElement('option'); 
           
           var val = (theOptions[j]);
           nOption.setAttribute('value',val);
           var isText = document.createTextNode(val);
           nOption.appendChild(isText); 
           model.appendChild(nOption);    
        }
        
       

}
}
function callInventory(invtype,invsubtype) {
if(invtype != null && invtype != ''){
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforSpecificInv1;
   // globaltype = type;
    var url ="AjaxAction.do?fun=callInventory&invtype="+invtype+"&invsubtype="+invsubtype;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);

 }

 } 
 function processResponseforSpecificInv1() {

    if(xmlHttp.readyState == 4){
        var subtype = document.getElementById('subtypecpe');
        var make = document.getElementById('invmake');
        var model = document.getElementById('invmodel');
        
          subtype.length = 0;
            make.length = 0;
            model.length = 0;
        var results = xmlHttp.responseText; 
        var theOptions = results.split(',');
         var eoption = document.createElement('option');
                    eoption.setAttribute('value',"");
                    var isText1 = document.createTextNode("");
                   eoption.appendChild(isText1); 
                    subtype.appendChild(eoption); 
         for(var k = 0; k < theOptions.length; k++) {
                   var nOption = document.createElement('option'); 
                   var val = (theOptions[k]);
                   nOption.setAttribute('value',val);
                   var isText = document.createTextNode(val);
                   nOption.appendChild(isText); 
                    subtype.appendChild(nOption);   
                }
}

}

function callDistributeInventory(invtype,invsubtype) {
if(invtype != null && invtype != ''){
    xmlHttp = getHTTPObject();
    xmlHttp.onreadystatechange = processResponseforSpecificDistributeInv1;
   // globaltype = type;
    var url ="AjaxAction.do?fun=callInventory&invtype="+invtype+"&invsubtype="+invsubtype;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }

 } 
 function processResponseforSpecificDistributeInv1() {

    if(xmlHttp.readyState == 4){
        var subtype = document.getElementById('subtypecpe');
          subtype.length = 0;
        var results = xmlHttp.responseText; 
        var theOptions = results.split(',');
                 var eoption = document.createElement('option');
                    eoption.setAttribute('value',"");
                    var isText1 = document.createTextNode("");
                   eoption.appendChild(isText1); 
                    subtype.appendChild(eoption); 
         for(var k = 0; k < theOptions.length; k++) {
                   var nOption = document.createElement('option'); 
                   var val = (theOptions[k]);
                   nOption.setAttribute('value',val);
                   var isText = document.createTextNode(val);
                   nOption.appendChild(isText); 
                    subtype.appendChild(nOption);   
                }
}

}


function callShopInventory(shopId,invtype,invsubtype) {
if(invsubtype != null && invsubtype != ''){
    xmlHttp = getHTTPObject();
    xmlHttp.onreadystatechange = processResponseforshopInv;
   // globaltype = type;
    var url ="AjaxAction.do?fun=callInventorySubType&shopId="+shopId+"&invtype="+invtype+"&invsubtype="+invsubtype;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }

 } 

 function processResponseforshopInv() {
    
    if(xmlHttp.readyState == 4){

        var subtype = document.getElementById('intype');
        subtype.length = 0;
        var results = xmlHttp.responseText; 
        if (results != ""){
        //alert(results);
        var theOptions1 = results.split(',');
        
        for (var j = 0; j < theOptions1.length; j++) {

        //alert(theOptions1[j]);
        
        var theOptions = theOptions1[j].split('+');
        //alert(theOptions[0]);
        for(var k = 1; k < theOptions.length; k++) {
                   var nOption = document.createElement('option'); 
                   var val = (theOptions[k]);
                 //  nOption.setAttribute('value',val);
                    nOption.setAttribute('value',theOptions[0]);
                  // nOption.setAttribute('value',theOptions[j]);
                   var isText = document.createTextNode(val);

                   nOption.appendChild(isText); 
                    subtype.appendChild(nOption);   
                }
			}
		}

if(results=="")
{
 
    $('p.validationmsg').hide();
    showMessage(document.getElementById("intype"), 'There is no Inventory Assign to this Shop');
    return false;
}
}

}

 function showMessage(field, msg) {
		$(field).parent('td').find('p.validationmsg').html(msg).show('fast');
	
	}
function callMinMax(invtype,invsubtype,make,model,shopId) {

if(invtype != null && invtype != '' && invsubtype != null && invsubtype != ''&& make != null && make != '' && model !=null && model!='' && shopId !=null && shopId !=''){
 xmlHttp = getHTTPObject();
 xmlHttp.onreadystatechange = processResponseforInv1;
   // globaltype = type;
    var url ="AjaxAction.do?fun=callMinMax&invtype="+invtype+"&invsubtype="+invsubtype+"&make="+make+"&model="+model+"&shopId="+shopId;
    xmlHttp.open("GET", url, true);
    xmlHttp.send(null);
 }
 } 
   function processResponseforInv1() {
    if(xmlHttp.readyState == 4){
    
      var minlevel = document.getElementById("minl");
        var maxlevel = document.getElementById("maxl");
      var results = xmlHttp.responseText; 
      var theOptions = results.split(',');
       for(i = 0; i < theOptions.length; i++) {
           var val = (theOptions[i].split('-'))[0];
            minlevel.setAttribute('value',val);
        }
    for(j = 0; j < theOptions.length; j++) {
           var val = (theOptions[j].split('-'))[1];
         maxlevel.setAttribute('value',val);
            
        }

}
}
