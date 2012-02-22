var dtCh= "/";
var minYear=1900;
var maxYear=2200;

function isInteger(s){
	var i;
	for (i = 0; i < s.length; i++){   
		// Check that current character is number.
		var c = s.charAt(i);
		if (((c < "0") || (c > "9"))) return false;
	}
	// All characters are numbers.
	return true;
}

function convertopoid(actno,actpoid) {
	 
	var txt = actno.value;
	var poid = '';
	if(trimString(txt) != '' ) {
		if(isValidAccountNoFormat(txt)) {
			poid = poid + txt.substr(0,7);
			poid = poid + ' /account ';
			poid = poid + txt.substr(8,txt.length) + ' 0';
		} 
	}
	actpoid.value = poid;
	
}

function isValidAccountNoFormat(vActNo) {
	var numberFilter=/^[0].[0].[0].[1]-\d{2}/;//allow alpha numrics only
    var isAlpha = numberFilter.test(vActNo);
      if(!isAlpha) {
         return(false); 
        }
    return(true);
}

function stripCharsInBag(s, bag){
	var i;
        var returnString = "";
        // Search through string's characters one by one.
        // If character is not in bag, append to returnString.
        for (i = 0; i < s.length; i++){   
            var c = s.charAt(i);
            if (bag.indexOf(c) == -1) returnString += c;
        }
        return returnString;
    }
    
    function daysInFebruary (year){
        // February has 29 days in any year evenly divisible by four,
        // EXCEPT for centurial years which are not also divisible by 400.
        return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
    }
    function DaysArray(n) {
        for (var i = 1; i <= n; i++) {
            this[i] = 31
            if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
            if (i==2) {this[i] = 29}
        } 
        return this
    }

function isValidDateFormat(val) {
        var dtformat = /^\d{2}\/\d{2}\/\d{4}$/;
        return(dtformat.test(val));
    }

    function isValidDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strDay=dtStr.substring(0,pos1)
	var strMonth=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		//"Not a valid format it should be dd/mm/yyyy"
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		//"Not a valid month"
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		//"Not a valid day"
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		// Not enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		//Not a valid date")
		return false
	}
        return true
    }

function isValidName(val) {
        var reName=/^[a-zA-Z .]{1,}$/;
        //return(reName.test(val));
		return(true);
    }
    
    function isValidAddress(val) {
        var reName=/^[a-zA-Z0-9 ,:\(\)\-\.]{1,}$/;
       // return(reName.test(val));
	   return (true);
    }
function isValidTitle(val) {
        var reName=/^[a-zA-Z. ()]{1,}$/;
        return(reName.test(val));
    }
	function isValidOrganization(val) {
        var reName=/^[a-zA-Z ,:\(\)\-\.]{1,}$/;
        return(reName.test(val));
    }
    function isValidCity(val) {
        var reName=/^[a-zA-Z ]{1,}$/;
        return(reName.test(val));
    }

    function isValidCNIC(val, nationality) {
      if(nationality == 1) {
            var regExpCNIC = /^[0-9]{13}$/;
            return(regExpCNIC.test(val));
        }
        else {
            regExpPP=/^[a-zA-Z0-9]{5,20}$/;
            return(regExpPP.test(val));
        }
    }
function IsValidHome(val) {
    //var numberFilter=/^[0]\d{10}$/;//validate ten digit numbers only
	var numberFilter=/^[0]\d{9,10}$/;
    return(numberFilter.test(val));   
    }

function IsValidMobile(val) {
    //var numberFilter=/^[0]\d{10}$/;//validate eleven digit numbers only
	var numberFilter=/^[0]\d{9,10}$/;
    return(numberFilter.test(val));   
    }
	
function IsValidForeignerContact(val) {
    var numberFilter=/^\d{0,}$/;//validate eleven digit numbers only
    return(numberFilter.test(val));   
    }

function IsValidPhone(val) {
        var phformat1=/^\92-\d{3}-\d{7}$/;
        var phformat2=/^\92 \d{3} \d{7}$/;
        if(phformat1.test(val) || phformat2.test(val))
            return true;
        else
            return false;    
    }

function IsValidEmail(value) {
    var emailFilter=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[(2([0-4]\d|5[0-5])|1?\d{1,2})(\.(2([0-4]\d|5[0-5])|1?\d{1,2})){3} \])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return(emailFilter.test(value));
}
function IsValidIPAddress(value){
     var ipFilter=/^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/;//validation for ip address xxx.xxx.xxx.xxx
     return(ipFilter.test(value));
 }
 function IsPassword(value){
     var passwordFilter=/^[A-Za-z]\w{6,}[A-Za-z]$/ ; //password must be 8 characters length,start and end must be letters
     return(passwordFilter.test(value));
 }
function IsNumber(value) {
    var numberFilter=/^\d{0,}$/;
    return(numberFilter.test(value));//number validation,check whether given value numbe ror not
}
function IsNumberAlt(value) {
    var numberFilter=/^[-+]?\d+(\.\d+)?$/;//number validation,check whether given value numbe ror not
    return(numberFilter.test(value));
}
function trimString(value) {
    return value.replace(/^\s*/, "").replace(/\s*$/, "");//trim before after field spaces
}
function IsNumber1(value) {
    var numberFilter=/^\d{0,6}$/;//check for numbers b/w 0-6
    return(numberFilter.test(value));
}
function IsNumber4(value) {
    var numberFilter=/^\d{4}$/;//validate b/w o-9999 only
    return(numberFilter.test(value));
}
function IsNumber5(value) {
    var numberFilter=/^\d{5}$/;//validate upto five digit numbers only
    return(numberFilter.test(value));
}
function IsNumber6(value) {
    var numberFilter=/^\d{6}$/;//validate upto six digit numbers only
    return(numberFilter.test(value));
}
function IsNumericNumbers(value) {
    var numberFilter=/^\d{1,}$/;//validate upto six digit numbers only
    return(numberFilter.test(value));
}
function IsNumber13(value) {
    var numberFilter=/^\d{13}$/;//validate thirteen digit numbers only
    return(numberFilter.test(value));
}	
function IsNumber16(value) {
    var numberFilter=/^\d{16}$/;//validate upto sixteen digit numbers only
    return(numberFilter.test(value));
}	
function IsNumber17(value) {
    var numberFilter=/^\d{17}$/;//validate upto seventeen digit numbers only
    return(numberFilter.test(value));
}		

function  IsAlphabets(value) {
var numberFilter=/^[a-zA-Z ]{1,}$/ ;//allow alphabets only
    return(numberFilter.test(value));
}


function isValidName(value) {
var numberFilter=/^[a-zA-Z ]{1,}$/ ;//allow alphabets only
    return(numberFilter.test(value));
}

function IsValidOccupation(value) {
    var numberFilter=/^[a-zA-Z_., ()]{1,}$/ ;//allow alphabets only
    return(numberFilter.test(value));
}

function IsAlphaNumeric(value) {
    var numberFilter=/^[a-zA-Z0-9]{1,}$/;//allow alpha numrics only
    return(numberFilter.test(value));
}
function IsValidText(value) {
    var numberFilter=/^[-a-zA-Z0-9,_. ]{0,}$/;//allow valid text
    return(numberFilter.test(value));
}
function IsValidText_WithoutSpace(value) {
    var numberFilter=/^[-a-zA-Z0-9,_.@]{0,}$/;//allow valid text without space 
    return(numberFilter.test(value));
}
function checkPercent(value) {
    var pexp = /^\d{0,3}(\.\d{0,2})?$///check for percent
    if (value && !pexp.test(value)) {
        return(false);
    }
    else {
        if(value > 100)
            return(false);
    }
    return (true);
}

function IsValidMac(value) {
    var dateFilter=/^[a-fA-F0-9]{2}[-][a-fA-F0-9][-][a-fA-F0-9]{2}[-][a-fA-F0-9]{2}[-][a-fA-F0-9]{2}[-][a-fA-F0-9]{2}$/;
    return(dateFilter.test(value));//check for mac xx.xx.xx.xx.xx
}
function IsValidZipCode(value) {
   var zipFilter = /^\d{7}?$/;//check for valid zip code
  // return (zipFilter.test(value));
  return true;
}

function IsValidExpiry(value) 
{
    var expFilter = /^\d{2}[/]\d{2}$/;
    if(expFilter.test(value)) 
    {
        //check for mm/yy formt
        monthpart = value.substring(0,2)
        yearpart = value.substring(3)
        if(checkForYear(yearpart) && checkforMonth(monthpart)) 
        {
                var dt= new Date();
                var thisyear = dt.getFullYear();
                thisyear = "" + thisyear;
                if(yearpart == thisyear.substring(2))
                {
                    var thismonth = dt.getMonth();
                    thismonth=thismonth+1;
                    if(parseInt(monthpart) <  thismonth)
                    {
                        return false;
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
        else 
        {
            return(false);
        }
    }
    else
    {
        return false;
    }
}    
    function checkforMonth(val) {
        if(parseInt(val) < 12 ) 
            return true
        else 
            return false
    }
    
    function checkForYear(val){
     var strYear = val;
     var datNow = new Date();
     var intYear = datNow.getFullYear()
     if(strYear.length==2) {
        if (parseInt(strYear) > 40){
          strYear = "19" + strYear;
          
          }
          else {
            intYear = "" + intYear;
            strYear = intYear.substring(0,2) + strYear;
            
          }
     }
     if(parseInt(strYear) < parseInt(intYear)){
          
          return false;
     }
     return true;
}


function showMessageDiv(field, msg) {
    $(field).parent('div').find('p.validationmsg').html(msg).show('slow');
    field.setfocus();
}
function showMessage(field, msg) {
    $(field).parent('td').find('p.validationmsg').html(msg).show('slow');
    field.setfocus();
}
function showMessage(field, msg, focustrue) {
    $(field).parent('td').find('p.validationmsg').html(msg).show('slow');
    //field.focus();
}
function ValidateContactPRForm(frm) {
    $('p.validationmsg').hide();
    if(frm.txtfirstName.value == '') {
        showMessage(frm.txtfirstName, 'Please Enter Your Name');
        return(false);
    }

    if(frm.txtEmailId.value == '') {
        showMessage(frm.txtEmailId, 'Please enter your email ID');
        return(false);
    }
    if(!IsValidEmail(frm.txtEmailId.value)) {
        showMessage(frm.txtEmailId, 'Please enter a valid email ID');
        return(false);
    }
    if(frm.txtEmailId.value == frm.txtConfirmId.value ){
        //
    } else {
        showMessage(frm.txtConfirmId, 'Please confirm your id');
        return(false);
    }

    return(true);
}

function ValidateContactform(frm) {
    $('p.validationmsg').hide();
    if(frm.txtfirstName.value == '') {
        showMessage(frm.txtfirstName, 'Please Enter Your Name');
        return(false);
    }

    if(!IsAlphabets(frm.txtfirstName.value) ) {
        showMessage(frm.txtfirstName, 'Please Enter Name in English');
        return(false);
    }

    if(frm.txtlastName.value == '') {
        showMessage(frm.txtlastName, 'Please enter your Last name');
        return(false);
    }

    if(!IsAlphabets(frm.txtlastName.value) ) {
        showMessage(frm.txtlastName, 'Please Enter Name in English');
        return(false);
    }

    if(!IsNumber(frm.txtmobileNumber.value) ) {
        showMessage(frm.txtmobileNumber, 'Please Enter numbers only');
        return(false);
    }

    if(!IsValidText(frm.txtCompany.value) ) {
        showMessage(frm.txtCompany, 'Please Enter Name in English');
        return(false);
    }

    if(!IsValidText(frm.txtCity.value) ) {
        showMessage(frm.txtCity, 'Please Enter city in English');
        return(false);
    }

    if(frm.selectCountry.value == '') {
        showMessage(frm.selectCountry, 'Please select country');
        return(false);
    }

    if(frm.txtEmailId.value == '') {
        showMessage(frm.txtEmailId, 'Please enter your email ID');
        return(false);
    }

    if(!IsValidEmail(frm.txtEmailId.value)) {
        showMessage(frm.txtEmailId, 'Please enter a valid email ID');
        return(false);
    }
    if(frm.txtEmailId.value == frm.txtConfirmId.value ){
        //
    } else {
        showMessage(frm.txtConfirmId, 'Please confirm your id');
        return(false);
    }

    if(!IsValidText(frm.txtAddress.value) ) {
        showMessage(frm.txtAddress, 'Enter Address in English');
        return(false);
    }
    if(!IsValidText(frm.comments.value) ) {
        showMessage(frm.comments, 'Enter Comments in English');
        return(false);
    }

    return(true);
}
function ValidateRequestContactform(frm) {
    $('p.validationmsg').hide();
    if(frm.first_name.value == '') {
        showMessage(frm.first_name, 'Please enter your first Name');
        return(false);
    }

    if(frm.last_name.value == '') {
        showMessage(frm.last_name, 'Please enter your last Name');
        return(false);
    }

    if(frm.email_id.value == '') {
        showMessage(frm.email_id, 'Please enter your email ID');
        return(false);
    }

    if(!IsValidEmail(frm.email_id.value)) {
        showMessage(frm.email_id, 'Please enter a valid email ID');
        return(false);
    }

    if(frm.mob_number.value == '') {
        showMessage(frm.mob_number, 'Please enter your mobile number');
        return(false);
    }

    if(frm.city.value == '') {
        showMessage(frm.city, 'Please enter your City');
        return(false);
    }

    if(frm.address.value == '') {
        showMessage(frm.address, 'Please enter your Adress');
        return(false);
    }
}
function ValidateContactCCform(frm) {
    $('p.validationmsg').hide();
    if(frm.first_name.value == '') {
        showMessage(frm.first_name, 'Please Enter Your Name');
        return(false);
    }

    if(frm.last_name.value == '') {
        showMessage(frm.last_name, 'Please Enter Your Name');
        return(false);
    }


    if(frm.email_id.value == '') {
        showMessage(frm.email_id, 'Please Enter Your email ID');
        return(false);
    }

    if(!IsValidEmail(frm.email_id.value)) {
        showMessage(frm.email_id, 'Please enter a valid email ID');
        return(false);
    }

    if(frm.mob_number.value == '') {
        showMessage(frm.mob_number, 'Please select Mobile Number');
        return(false);
    }

    if(frm.city.value == '') {
        showMessage(frm.city, 'Please Enter Your City');
        return(false);
    }

    if(frm.address.value == '') {
        showMessage(frm.address, 'Please Enter Your Adress');
        return(false);
    }
    return(true);
}

/* This fuction is to validate the login Page(login.jsp). */
function validateLoginForm(frm) {
    $('p.validationmsg').hide();	// Hide the Validation Message DIV tag for the total page..
    frm.username.value = trimString(frm.username.value);  // trim leading and tailing spaces.

    //Check user entered the user name or not
    if(frm.username.value == '') {
        showMessage(frm.username, 'Please enter your user name');
        return(false);
    }

    //Check user name has strings or not
    /*if(!IsAlphabets(frm.username.value)) {
        showMessage(frm.username, 'User Name should accept strings only');
        return(false);
    }*/

    frm.password.value = trimString(frm.password.value); // trim leading and tailing spaces.
    //Check user entered password or not				
    if(frm.password.value == '') {
        showMessage(frm.password, 'Please enter password');
        return(false);
    }

    //check password has alpha numeric or not
    /*if(!IsValidText(frm.password.value)){
        showMessage(frm.password,'Password should contain alpha numeric only');
        return(false);
    }*/
    return(true);
}	


function validateUsernameForm(frm) {
    $('p.validationmsg').hide();
    if(frm.user_name.value == '') {
        showMessage(frm.user_name, 'Please enter your User ID');
        return(false);
    }

    if(!IsValidEmail(frm.user_name.value)) {
        showMessage(frm.user_name, 'Please enter a valid user ID');
        return(false);
    }

    return(true);
}	
function validateSecretQAForm(frm) {
    $('p.validationmsg').hide();
    if(frm.secret_answer.value == '') {
        showMessage(frm.secret_answer, 'Please enter secret answer');
        return(false);
    }

    return(true);
}


/* This fuction is to validate the change personal info Page(changepinfo.jsp). */
function validateChangePersonalnfoForm(frm) {
    $('p.validationmsg').hide();// Hide the Validation Message DIV tag for the total page..

    frm.txtDateofbirth.value = trimString(frm.txtDateofbirth.value);  // trim leading and tailing spaces.
    //check user entered the date of birth or not
    if(frm.txtDateofbirth.value == '') {
        showMessage(frm.txtDateofbirth, 'Please select Date of Birth');
        return(false);
    }

   if(!isValidDateFormat(frm.txtDateofbirth.value)) {
		showMessage(frm.txtDateofbirth,'accpepted format: dd/mm/yyyy.');
        return(false);
   }

    //check the dob in dd/mm/yyyy format or not 
    if(!isValidDate(frm.txtDateofbirth.value)) {
        showMessage(frm.txtDateofbirth,'Please Enter valid Date');
        return(false);
    }

    var dts = frm.txtDateofbirth.value;
    var dt = Date.fromString(dts)
    var today = new Date();
    //check the dob is less than the current date or not
    if(dt >= today) {
        showMessage(frm.txtDateofbirth,'Please Enter valid Date');
        return(false);
    }

    frm.email.value = trimString(frm.email.value);  // trim leading and tailing spaces.
    //check user entered the email or not
    if(frm.email.value == '') {
        showMessage(frm.email, 'Please enter your email ID');
        return(false);
    }

    //check the email is in correct format or not
    if(!IsValidEmail(frm.email.value)) {
        showMessage(frm.email, 'Please enter a valid email ID');
        return(false);
    }

    /*var txtCtrl = document.getElementById("cnic");// trim leading and tailing spaces.
	txtCtrl.value = trimString(txtCtrl.value);*/

	
	if(!IsNumber13(frm.cnic.value)) {
		//alert(frm.cnic.value);
		
		var txtCtrl = document.getElementById("homephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
		 showMessage(txtCtrl, 'Please Enter Your Home Phone e.g. 0518250340');
		 return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Home Phone e.g. 0518250340");
				return(false);
			}
		}
		txtCtrl = document.getElementById("workphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Work Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("faxphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Fax Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("mobilephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
		 showMessage(txtCtrl, 'Please Enter Your Mobile Phone e.g. 03015500958');
		 return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidMobile(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Mobile Number e.g. 03015500958");
					return(false);
			}
		}
	}else{
		var txtCtrl = document.getElementById("homephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Home Phone e.g. 0518250340');
			return(false);
		 }
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Home Phone e.g. 0518250340");
				return(false);
			}
		}	
		
		txtCtrl = document.getElementById("workphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Work Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("faxphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Fax Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("mobilephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Mobile Number e.g. 03015500958');
			return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Mobile Number e.g. 03015500958");
				return(false);
			}
		}
	
	}

	
    frm.title.value = trimString(frm.title.value);  // trim leading and tailing spaces.
    //check the occupation has string or not
    if(!isValidTitle(frm.title.value) && (frm.title.value)!= '') {
        showMessage(frm.title, 'Job title does not accept any special characters.');
        return(false);
    }

    frm.company.value = trimString(frm.company.value);  // trim leading and tailing spaces.
    //check the company has string or not
    if(!isValidOrganization(frm.company.value)&& (frm.company.value)!= '') {
        showMessage(frm.company, 'Organization does not accept any special characters.');
        return(false);
    }

    return(true);
}

function validateChangeEmailForm(frm) {
    if(frm.email_address.value == '') {
        showMessage(frm.email_address, 'Please enter your email ID');
        return(false);
    }
    if(!IsValidEmail(frm.email_address.value)) {
        showMessage(frm.email_address, 'Please enter a valid email ID');
        return(false);
    }

    return(true);
}

function validateChangeAddForm(frm) {
    $('p.validationmsg').hide();
    if(frm.bank) {
        if(frm.bank.value == "") {
            showMessage(frm.bank, 'Enter Bank Code.');
            return(false);
        }
        if(!IsNumber(frm.bank.value) ) {
            showMessage(frm.bank, 'Please Enter Your Sixteen digit Financial Institution Number');
            return(false);
        }
    }
    if(frm.account_num) {
        if(frm.account_num.value == "") {
            showMessage(frm.account_num, 'Enter Account Number.');
            return(false);
        }

        if(!IsNumber(frm.account_num.value) ) {
            showMessage(frm.account_num, 'Please Enter Your Seventeen Digit Account Number');
            return(false);
        }
    }
    if(frm.debitnum) {
        if(frm.debitnum.value == ''){
            showMessage(frm.debitnum, 'Please Enter Your Card Number');
            return(false);
        }

        if(!IsNumber(frm.debitnum.value) ) {
            showMessage(frm.debitnum, 'Please Enter Your Sixteen digit Card Number');
            return(false);
        }
    }
    if(frm.cv) {
        if(frm.cv.value == ''){
            showMessage(frm.cv, 'Please Enter Your CVV Number');
            return(false);
        }

        if(!IsNumber(frm.cv.value) ) {
            showMessage(frm.cv, 'Please Enter Your Four Digit CVV Number');
            return(false);
        }
    }
    if(frm.debitexp) {
        if(frm.debitexp.value == '') {
            showMessage(frm.debitexp, 'Please Enter Your Card Expiration Date (MM/YY)');
            return(false);
        }

        if(!IsValidExpiry(frm.debitexp.value)) {
            showMessage(frm.debitexp, 'Please Enter Your Card Expiration Date (MM/YY)');
            return(false);
        }
    }
    var today = new Date();
    var dts = frm.debitexp.value;
    var parts = dts.split('/');

    if(frm.invoice_name) {
        if(frm.invoice_name.value == '') {
            showMessage(frm.invoice_name, 'Please Enter Your Billing Name');
            return(false);
        }	

        if(!IsAlphabets(frm.invoice_name.value) ) {
            showMessage(frm.invoice_name, 'Billing Name should accept strings only');
            return(false);
        }

        if(frm.bill_address1.value == '') {
            showMessage(frm.bill_address1, 'Please Enter Your Street Address');
            return(false);
        }


        if(!IsValidText(frm.bill_address1.value) ) {
            showMessage(frm.bill_address1, 'Address should accept strings only');
            return(false);
        }

        if(frm.bill_city.value == '') {
            showMessage(frm.bill_city, 'Please Enter Your City');
            return(false);
        }

        if(!IsAlphabets(frm.bill_city.value) ) {
            showMessage(frm.bill_city, 'City should accept strings only');
            return(false);
        }

        if(frm.bill_state.value == '') {
            showMessage(frm.bill_state, 'Please Select Your State');
            return(false);
        }

        if(!IsAlphabets(frm.bill_state.value) ) {
            showMessage(frm.bill_state, 'State should accept strings only');
            return(false);
        }
		
		frm.bill_zip.value = trimString(frm.bill_zip.value);
       /* if(frm.bill_zip.value == '') {
            showMessage(frm.bill_zip, 'Please Enter Your Zip Code');
            return(false);
        }
		*/
		 if(frm.bill_zip.value != '') {
        if(!IsValidZipCode(frm.bill_zip.value) ) {
            showMessage(frm.bill_zip, 'Please Enter Valid Zip Code');
            return(false);
        }
		 }
        if(frm.inv_email.value == '') {
            showMessage(frm.inv_email, 'Please Enter Your E-Mail');
            return(false);
        }

        if(!IsValidEmail(frm.inv_email.value) ) {
            showMessage(frm.inv_email, 'Email should be in xx@xx.xxx format only');
            return(false);
        }
    }
    return(true);
}

/* This fuction is to validate the T-Pin Page(resettpin.jsp). */
function validateTPinForm(frm) {
    $('p.validationmsg').hide();// Hide the Validation Message DIV tag for the total page..
    frm.tpin.value = trimString(frm.tpin.value);  // trim leading and tailing spaces.
    //check user entered the t-pin or not
    if(frm.tpin.value == '') {
        showMessage(frm.tpin, 'Please enter your T-PIN');
        return(false);
    }

    //check t-pin should have four digits or not
    if(!IsNumber4(frm.tpin.value) ) {
        showMessage(frm.tpin, 'T-PIN should accept four digits only.');
        return(false);
    }
    return(true);
}

/* This fuction is to validate the change password Page(chpwd.jsp). */
function validateChangePWDForm(frm) {
    $('p.validationmsg').hide();
    var listofser = frm.txtlists.value;
    var flag=false;
    for(i=0;i<listofser;i++)
    {
            //check corresponding check box is checked or not
        if (eval('frm.checkbox'+i+'.checked') == true){
            flag=true	

            //check user entered the new password or not
			var newpwd = eval('frm.new_passwd'+i+'.value')
				newpwd = trimString(newpwd);
            if( newpwd == '') {
                showMessage(eval('frm.new_passwd'+i), 'Please Enter Your New Password');
                return(false);
            }

			var newpwdconfirm = eval('frm.confirm'+i+'.value');
			newpwdconfirm = trimString(newpwdconfirm)
            //check user entered the confirm password or not
            if( newpwdconfirm == '') 
            {
                showMessage(eval('frm.confirm'+i), 'Please Enter Confirm New Password');
                return(false);
            }

            //check new password and confirm password are same or not
            if(newpwdconfirm != newpwd) {
                showMessage(eval('frm.confirm'+i), 'Your passwords do not match.');
                return (false);
            }
        }
    }
    if(flag==false){
        alert('You didn\'t choose any of the checkboxes,Please choose your services, for which you are going to change the password.');
        return(false);
    }

    return(true);
}
/* This fuction is to validate the payment history Page(PayHistory.jsp). */
function validatePayHistory(frm) {
    $('p.validationmsg').hide();
    frm.startdt.value = trimString(frm.startdt.value);  // trim leading and tailing spaces.
    //check start date entered or not
    if(frm.startdt.value == '') {
        showMessage(frm.startdt, 'Please select start date');
        return(false);
    }

	if(!isValidDateFormat(frm.startdt.value)) {
		 showMessage(frm.startdt,'valid format: DD/MM/YYYY');
        return(false);
	}

    //check start date is in dd/mm/yyyy format or not
    if(!isValidDate(frm.startdt.value)) {
        showMessage(frm.startdt,'Please enter valid date');
        return(false);
    }

    frm.enddt.value = trimString(frm.enddt.value);  // trim leading and tailing spaces.
    //check end date entered or not
    if(frm.enddt.value == '') {
        showMessage(frm.enddt, 'Please Select End Date');
        return(false);
    }

	if(!isValidDateFormat(frm.enddt.value)) {
		 showMessage(frm.enddt,'valid format: DD/MM/YYYY');
        return(false);
	}

    //check end date is in dd/mm/yyyy format or not
    if(!isValidDate(frm.enddt.value)) {
        showMessage(frm.enddt,'Please Enter valid End Date');
        return(false);
    }

    var edt = Date.fromString(frm.enddt.value);
    var sdt = Date.fromString(frm.startdt.value);

    if(sdt.getMonth().value<0 && sdt.getMonth().value>11){return(false);}

    if(sdt == 'NaN' ){showMessage(frm.startdt,'Enter date in DD/MM/YYYY formate only');return(false);}

    if(edt == 'NaN' ){showMessage(frm.enddt,'Enter date in DD/MM/YYYY formate only');return(false);}

    
    //check start date is less than the end date
    if ( sdt > edt ){
        showMessage(frm.startdt,'Start Date is greater than End Date');
        return(false);
    }
    return(true);
}

function validateVoucherTopUpForm(frm) {
    $('p.validationmsg').hide();

    if(frm.tf_voucher_id.value == '') {
        showMessage(frm.tf_voucher_id, 'Please Select Voucher Id');
        return(false);
    }
    if(!IsAlphaNumeric(frm.tf_voucher_id.value) ) {
        showMessage(frm.tf_voucher_id, 'No special characters allowed');
        return(false);
    }
    if(frm.tf_voucher_pin.value == '') {
        showMessage(frm.tf_voucher_pin, 'Please Select Voucher Pin');
        return(false);
    }
    if(!IsNumber(frm.tf_voucher_pin.value) ) {
        showMessage(frm.tf_voucher_pin, 'Please Enter Numbers Only');
        return(false);
    }
    if(!IsNumber6(frm.tf_voucher_pin.value) ) {
        showMessage(frm.tf_voucher_pin, 'Please Enter Six Digit PinF');
        return(false);
    }

    return(true);
}

/* This fuction is to validate the new account Page(NewAccount.jsp). */
function validateNewAccountForm(frm) {
	$('p.validationmsg').hide();
	frm.firstname.value = trimString(frm.firstname.value);  // trim leading and tailing spaces.
    //check user entered the first name or not 	
    if(frm.firstname.value == '') {
        showMessage(frm.firstname,'Please Enter Your First Name');
        return(false);
    }

    //check first name has the string or not
    if(!isValidName(frm.firstname.value) ) {
        showMessage(frm.firstname, 'First Name should accept strings only');
        return(false);
    }

    frm.middlename.value = trimString(frm.middlename.value);  // trim leading and tailing spaces.
    //check middle name has the string or not
    if(!isValidName(frm.middlename.value) && frm.middlename.value != '') {
        showMessage(frm.middlename, 'Middle Name should accept strings only');
        return(false);
    }

    frm.lastname.value = trimString(frm.lastname.value);  // trim leading and tailing spaces.
    //check user entered the last name or not 	
    if(frm.lastname.value == ''){
        showMessage(frm.lastname,'Please Enter Your Last Name');
        return(false);
    }

    //check last name has string or not
    if(!isValidName(frm.lastname.value) ) {
        showMessage(frm.lastname, 'Last Name should accept strings only');
        return(false);
    }

    frm.email.value = trimString(frm.email.value);  // trim leading and tailing spaces.
    //check user entered the email or not
    if(frm.email.value == ''){
        showMessage(frm.email, 'Please Enter Your e-mail');
        return(false);
    }

    //check email is in correct format or not
    if(!IsValidEmail(frm.email.value)) {
        showMessage(frm.email, 'Please Enter Your Valid e-mail');
        return(false);
    }
    var vBusType = frm.business_type.options[frm.business_type.selectedIndex].value;
    if( ( vBusType == "1")  || (vBusType == "5")  || (vBusType == "3") || (vBusType == "8") || (vBusType == "9")) {	
		frm.identity.value = trimString(frm.identity.value);  // trim leading and tailing spaces.
		//check user entered his cnic/passport or not
		if(frm.nationality.options[frm.nationality.selectedIndex].value == 1 && frm.identity.value=='' ){
			showMessage(frm.identity, 'Please Enter Your CNIC Number');
			return(false);
		}
		if(frm.nationality.options[frm.nationality.selectedIndex].value == 2 && frm.identity.value=='' ){ 
			showMessage(frm.identity, 'Please Enter Your Passport Number');
			return(false);		
		}
		if(! isValidCNIC( frm.identity.value, frm.nationality.options[frm.nationality.selectedIndex].value )) {
			if( frm.nationality.options[frm.nationality.selectedIndex].value == "1") {
				showMessage(frm.identity, 'CNIC Format Expected: 0000000000000(13 digits)');
				return(false);
			}
			if( frm.nationality.options[frm.nationality.selectedIndex].value == "2") {
				showMessage(frm.identity, 'Please enter valid passport no.');
				return(false);
			}
		}
		//check for dupilcate check done or not
		if(frm.txtDuplicateChk.value == '') {
			showMessage(frm.identity,"Please click on the 'Verify Identity' button to validate the User identity");
			return (false);
		}
	} else {
		// validations for corporate accounts
		frm.NTN.value = trimString(frm.NTN.value);
		if(frm.NTN.value == '' ) {
			showMessage(frm.NTN,"Please enter NTN value");
			return (false);
		}
		//check for dupilcate check done or not
		if(frm.txtDuplicateChk.value == '') {
			showMessage(frm.identity,"Please click on the 'Verify Identity' button to validate the User identity");
			return (false);
		}
	}
    //check for blocked account
    if(frm.txtDuplicateChk.value == 'DUPLICATE' || frm.txtDuplicateChk.value == 'BLOCKED'  ) {
        showMessage(frm.identity,"There are already max Users with this identity already exists / user is blocked. You cannot create account for this user.");
        return(false);
    }

	//basing on national/foreigner home and mobile number validations
	if(frm.nationality.options[frm.nationality.selectedIndex].value == 1){
		var txtCtrl = document.getElementById("homephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Home Phone e.g. 0518250340');
			return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Home Phone e.g. 0518250340");
				return(false);
			}
		}
		
		txtCtrl = document.getElementById("workphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Work Phone e.g. 0518250340");
				return(false);
			}
		}
		
		txtCtrl = document.getElementById("faxphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Fax Phone e.g. 0518250340");
				return(false);
			}
		}
		
		txtCtrl = document.getElementById("mobilephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Mobile Number e.g. 03015500958');
			return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidMobile(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Mobile Number e.g. 03015500958");
				return(false);
			}
		}

	} else {
		var txtCtrl = document.getElementById("homephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Home Phone e.g. 0518250340');
			return(false);
		 }
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Home Phone e.g. 0518250340");
				return(false);
			}
		}	
		
		txtCtrl = document.getElementById("workphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Work Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("faxphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Fax Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("mobilephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Mobile Number e.g. 03015500958');
			return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Mobile Number e.g. 03015500958");
				return(false);
			}
		}
	
	}

    frm.txtDateofbirth.value = trimString(frm.txtDateofbirth.value);  // trim leading and tailing spaces.
    //check user entered the dob or not
    if(frm.txtDateofbirth.value == ''){
        showMessage(frm.txtDateofbirth, 'Please Enter Your Date Of Birth');
        return(false);
    }
    if(!isValidDateFormat(frm.txtDateofbirth.value)) {
        showMessage(frm.txtDateofbirth,'Invalid date format. Accepted Format is: DD/MM/yyyy.');
        return(false);
    }
    //check the dob in dd/mm/yyyy format or not 
    if(!isValidDate(frm.txtDateofbirth.value)) {
        showMessage(frm.txtDateofbirth,'Please Enter valid Date.');
        return(false);
    }

    var dts = frm.txtDateofbirth.value;
    var dt = Date.fromString(dts)
    var today = new Date();
                //check the dob is less than the current date
    if(dt >= today) {
        showMessage(frm.txtDateofbirth,'Please Enter valid Date of Birth in DD/MM/yyyy format');
        return(false);
    }

    frm.title.value = trimString(frm.title.value);
	frm.company.value = trimString(frm.company.value);
    //check occupation has strings or not
    var v1BusType = frm.business_type.options[frm.business_type.selectedIndex].value;

	if (( v1BusType == "1") ||  (v1BusType == "4") ||  (v1BusType == "5")  ){
	    if(!isValidTitle(frm.title.value) &&  frm.title.value != '') {
		    showMessage(frm.title,'Job tile should accept strings only');
			return(false);
	    }
	    //check organization has strings or not
		if(!isValidOrganization(frm.company.value) &&  frm.company.value != '') {
			showMessage(frm.company,'Organization should accept strings only');
			return(false);
		}
	} else {
		if( frm.title.value == '') {
				showMessage(frm.title,'Please enter Job title.');
				return(false);
		}
		if(!isValidTitle(frm.title.value) &&  frm.title.value != '') {
		    showMessage(frm.title,'Job tile should accept strings only');
			return(false);
	    }
		if(frm.company.value == '') {
			showMessage(frm.title,'Please enter your Company name.');
				return(false);
		}
		if(!isValidOrganization(frm.company.value) &&  frm.company.value != '') {
			showMessage(frm.company,'Organization should accept strings only');
	        return(false);
		}
		
	}
	if (( v1BusType == "1") ||  (v1BusType == "3")  ||  (v1BusType == "5") ||  (v1BusType == "8") ||  (v1BusType == "9") ){
		//
	} else {
		frm.identity.value = frm.NTN.value ;
		frm.SUB_ID.value = trimString(frm.SUB_ID.value);
		if(frm.SUB_ID.value != '')
			frm.identity.value = frm.identity.value + "-" + frm.SUB_ID.value;
	}
	frm.ReferredBy.value='';
	frm.ReferredBy1.value = trimString(frm.ReferredBy1.value);
	if(frm.ReferredBy1.value != '') {
		if(!isValidAccountNoFormat(frm.ReferredBy1.value)) {
			showMessage(frm.ReferredBy1,'Please enter valid account no format(i.e. 0.0.0.1-1122)');
			return(false);
		} 
		convertopoid(frm.ReferredBy1,frm.ReferredBy);
	}
    //check contract period selected or not 
    if(trimString(frm.contract_period.options[frm.contract_period.selectedIndex].value) == '') {
        showMessage(frm.contract_period,'Please Enter contract period in month(s)');
        return(false);
    }

    //check contract period selected or not 
    if(frm.contract_period.selectedIndex == 0) {
        showMessage(frm.contract_period,'Please select contract period in month(s)');
        return(false);
    }
    updatepkgdetails(frm.contract_period);
    //check contract end entered or not      
    if(frm.contract_end.value == '') {
        showMessage(frm.contract_period,'Please enter proper contract end date');
        return(false);
    }


    if(frm.bill.selectedIndex == 0 ) {
        frm.execptpercent.value = 0;
        frm.execpttype.selectedIndex = 0;
    } else {
		frm.execptpercent.value = trimString(frm.execptpercent.value);
		if(frm.execptpercent.value == '') {
			showMessage(frm.execptpercent,'Please Enter exemption percentage');
			return(false);
		}

		if(!checkPercent(frm.execptpercent.value)) {
			showMessage(frm.execptpercent,'Please Enter valid exemption percentage');
			return(false);
		}
    }
    //check service login type selected or not
    if(frm.svslogintype.selectedIndex == 0) {
        showMessage(frm.svslogintype,'Please select service login type');
        return(false);
    }

	if(trimString(frm.parentpoid.value) =='' && v1BusType == "3" ) {
		showMessage(frm.chkSelectParent,'Affinity Child Account cannot be created with out selecting the Affinity Parent Account');
		return(false);
	}



	if(frm.planselected.options.length < 1)  {
		document.getElementById("divnumberofplans").innerHTML = "You Cannot proceed with creating the account since there are no plans available with this list.";
		return(false);
	}
	if( frm.planselected.options.length > 0 )  {
		if(frm.planselected.selectedIndex < 0) {
			document.getElementById("divnumberofplans").innerHTML = "You Cannot proceed with creating the account since there are no plans available with this list.";
			return(false);
		}
		else {
			return(true);
		}
	} else {
		document.getElementById("divnumberofplans").innerHTML = "You Cannot proceed with creating the account since there are no plans available with this list.";
		return(false);

	}

}
/* This fuction is to validate the new account next Page(NewAccount_next.jsp). */
function  newAcctNext(frm) {
    $('p.validationmsg').hide();

    var ipServiceExists = frm.ipserviceexists.value;
    var voipLinesexists = false;
    var noofvoiplines = 0;
    var voipLineNumbers = "";
    var j=frm.val.value;
    for(i=0;i<j;i++)
    {
        //check service user name entered or not
        if(eval('frm.login_'+i+'.value') == "") {
            showMessage(eval('frm.login_'+i), 'Enter User Name.');
            return(false);
        }
        if(eval('frm.login_'+ i +'.text') == 'ip') {
            //check service user name has strings only
            if(!IsValidText_WithoutSpace(eval('frm.login_'+ i +'.value'))){
                showMessage(eval('frm.login_'+ i), 'Enter User Name properly.');
                return(false);
            }
        }
//        alert(eval('frm.login_'+ i +'.value'));
//        alert(eval('frm.login_'+ i +'.text'));
        
        if(eval('frm.login_'+ i +'.value').indexOf('/device/num') > 0) { 
            voipLinesexists = true;
            noofvoiplines = noofvoiplines + 1;
            if(voipLineNumbers.indexOf('**' + eval('frm.login_'+ i +'.value') + '**') > -1) {
                showMessage(eval('frm.login_'+ i), 'You cannot select the same voip line for both services');
                return(false);
            } else {   
                voipLineNumbers = voipLineNumbers + '**' + eval('frm.login_'+ i +'.value') + '**';
            }
            
        }
        
        //alert(voipLinesexists);
        //alert(noofvoiplines);

        //check password entered or not
        if(eval('frm.pin_'+ i +'.value') == "") {
            showMessage(eval('frm.pin_'+ i), 'Enter Password.');
            return(false);
        }

        //check password has alpha numeric only 
        if(!IsValidText_WithoutSpace(eval('frm.pin_'+ i +'.value'))){
            showMessage(eval('frm.pin_'+ i), 'Enter Password properly.');
            return(false);
        }

        //check confirm password entered or not
        if(eval('frm.pinv_'+ i+'.value') == "") {
            showMessage(eval('frm.pinv_'+ i), 'Enter Confirm Password.');
            return(false);
        }

        //check confirm password has alpha numeric only
        if(!IsValidText_WithoutSpace(eval('frm.pinv_' + i + '.value'))){
            showMessage(eval('frm.pinv_'+i), 'Enter Confirm Password properly.');
            return(false);
        }

        //check both password and confirm password are same or not
        if(eval('frm.pin_'+ i +'.value') != eval('frm.pinv_'+ i +'.value')) {
            showMessage(eval('frm.pin_'+ i), 'Your passwords do not match. Please type again.');
            return (false);
        }
    }
    if(ipServiceExists != "true" && frm.tmpBusinessType.value != "4") {
        alert('You Cannot create account without Ip Service. You Cannot proceed further');
        return(false);
    }
    
    //check mac address entered or not
    frm.macaddress.value=trimString(frm.macaddress.value);
	frm.serialnumber.value=trimString(frm.serialnumber.value);
    if(frm.macaddress.value == "" && frm.serialnumber.value == "") {
        showMessage(frm.macaddress, 'Enter MAC address of the device.');
        return(false);
    }
    /*
    if(!IsValidMac(frm.macaddress.value)) {
        showMessage(frm.macaddress, 'Enter MAC valid address of the device.');
        return(false);
    }
    */
    //check the get mac details done or not
    if(frm.MacStatus.value == "") {
        showMessage(frm.macaddress, 'Click on the Get MAC Device details.');
        return(false);
    }

    //check the mac is available or not
    if(frm.MacStatus.value != "NEW") {
        showMessage(frm.macaddress, 'You cannot associate this divice for the account.');
        return(false);
    }
    //alert(voipLinesexists);
    if(voipLinesexists == true) {
        //alert(frm.VoIPEnabled.value);
        if(frm.VoIPEnabled.value == false) {
            showMessage(frm.macaddress, 'This device is NOT VoIP Enabled.Selected plan contains VoIP Service you cannot associate device to this Account.');
            return(false);
        }
        if(frm.NoOfVoIpPorts.value < noofvoiplines) {
            showMessage(frm.macaddress, 'This device is supported for this plan as No of ports available is less than the No. of VoIP Services.');
            return(false);
        }
    }
    //return(false);
    if(frm.bank) {
        if(frm.bank.value == "") {
            showMessage(frm.bank, 'Enter Bank Code.');
            return(false);
        }

        if(!IsNumber(frm.bank.value) ) {
            showMessage(frm.bank, 'Please Enter Your Sixteen digit Financial Institution Number');
            return(false);
        }
    }
    if(frm.account_num) {
        if(frm.account_num.value == "") {
            showMessage(frm.account_num, 'Enter Account Number.');
            return(false);
        }

        if(!IsNumber(frm.account_num.value) ) {
            showMessage(frm.account_num, 'Please Enter Your Seventeen Digit Account Number');
            return(false);
        }
    }
    if(frm.debitnum) {
        if(frm.debitnum.value == ''){
            showMessage(frm.debitnum, 'Please Enter Your Card Number');
            return(false);
        }

        if(!IsNumber16(frm.debitnum.value) ) {
            showMessage(frm.debitnum, 'Please Enter Your Sixteen digit Card Number');
            return(false);
        }
    }
    if(frm.cv) {
        if(frm.cv.value == ''){
            showMessage(frm.cv, 'Please Enter Your CVV Number');
            return(false);
        }

        if(!IsNumber(frm.cv.value) ) {
            showMessage(frm.cv, 'Please Enter Your Four Digit CVV Number');
            return(false);
        }
    }
    if(frm.debitexp) {
        if(frm.debitexp.value == '') {
            showMessage(frm.debitexp, 'Please Enter Your Card Expiration Date (MM/YY)');
            return(false);
        }

        if(!IsValidExpiry(frm.debitexp.value)) {
            showMessage(frm.debitexp, 'Please Enter Your Card Expiration Date (MM/YY)');
            return(false);
        }
        //var today = new Date();
        //var dts = frm.debitexp.value;
        //var parts = dts.split('/');
    }

    if(frm.invoice_name) {
        frm.invoice_name.value=trimString(frm.invoice_name.value);
        if(frm.invoice_name.value == '') {
            showMessage(frm.invoice_name, 'Please Enter Your Billing Name');
            return(false);
        }	

        if(!IsAlphabets(frm.invoice_name.value) ) {
            showMessage(frm.invoice_name, 'Billing Name should accept strings only');
            return(false);
        }
        frm.txtPlot.value = trimString(frm.txtPlot.value);
        if(frm.txtPlot.value == '') {
            showMessage(frm.txtPlot, 'Please Enter Your House/Plot Address');
            return(false);
        }
        if(!isValidAddress(frm.txtPlot.value)) {
            showMessage(frm.txtPlot, 'Please Enter valid House/Plot Address');
            return(false);
        }
        
        frm.txtSteet1.value = trimString(frm.txtSteet1.value);
        if(frm.txtSteet1.value == '') {
            showMessage(frm.txtSteet1, 'Please Enter Your Street Address');
            return(false);
        }
        if(!isValidAddress(frm.txtSteet1.value)) {
            showMessage(frm.txtSteet1, 'Please Enter valid Street Address');
            return(false);
        }
        frm.txtSubzone.value = trimString(frm.txtSubzone.value);
        if(frm.txtSubzone.value == '') {
            showMessage(frm.txtSubzone, 'Please Enter Your sub zone Address');
            return(false);
			//frm.txtSubzone.value = frm.txtSubzone.value + " ";
        }
        if(!isValidAddress(frm.txtSubzone.value)) {
            showMessage(frm.txtSubzone, 'Please Enter valid sub zone Address');
            return(false);
        }
        frm.txtzone.value = trimString(frm.txtzone.value);
        if(frm.txtzone.value == '') {
            showMessage(frm.txtzone, 'Please Enter Your zone Address');
            return(false);
        }
        if(!isValidAddress(frm.txtzone.value)) {
            showMessage(frm.txtzone, 'Please Enter valid zone Address');
            return(false);
        }
        

        /*
        if(frm.bill_address1.value == '') {
            showMessage(frm.bill_address1, 'Please Enter Your Street Address');
            return(false);
        }


        if(!IsValidText(frm.bill_address1.value) ) {
            showMessage(frm.bill_address1, 'Address should accept strings only');
            return(false);
        }
        */
        if(frm.bill_city.value == '') {
            showMessage(frm.bill_city, 'Please Enter Your City');
            return(false);
        }

        if(!IsAlphabets(frm.bill_city.value) ) {
            showMessage(frm.bill_city, 'City should accept strings only');
            return(false);
        }

        if(frm.bill_state.value == '') {
            showMessage(frm.bill_state, 'Please Select Your State');
            return(false);
        }

        if(!IsAlphabets(frm.bill_state.value) ) {
            showMessage(frm.bill_state, 'State should accept strings only');
            return(false);
        }
		frm.bill_zip.value = trimString(frm.bill_zip.value);
                //changed by usman
        if(frm.bill_zip.value == '' ) {
            showMessage(frm.bill_zip, 'Please Enter Your Zip Code');
            return(false);
        }
		if(frm.bill_zip.value != '') {
        if(!IsValidZipCode(frm.bill_zip.value) ) {
            showMessage(frm.bill_zip, 'Please Enter Valid Zip Code');
            return(false);
        }
		}

            var plotNo, streetNo, subzone, zone, city, state, zip;
            plotNo = trimString(frm.txtPlot.value);
            streetNo = trimString(frm.txtSteet1.value);
            subzone = trimString(frm.txtSubzone.value);
            zone = trimString(frm.txtzone.value);
            city = trimString(frm.bill_city.value);
            state = trimString(frm.bill_state.value);
            zip = trimString(frm.bill_zip.value);
            frm.address1.value = plotNo ;
            
            frm.address1.value = frm.address1.value  + "   " + streetNo;
            frm.address1.value = frm.address1.value  + "   " + subzone;
            frm.address1.value = frm.address1.value  + "   " + zone;
            frm.address1.value = frm.address1.value  + "   " + city;
            frm.address1.value = frm.address1.value  + "   " + state;
            frm.address1.value = frm.address1.value  + "   " + zip;

      if(frm.inv_email) {
        if(frm.email == '') {
            showMessage(frm.email, 'Please Enter Your E-Mail');
            return(false);
        }

        if(!IsValidEmail(frm.email.value) ) {
            showMessage(frm.email, 'Email should be in xx@xx.xxx format only');
            return(false);
        }
      }
    }

    return(true);
}

function validateChBusTypeForm(frm) {
    $('p.validationmsg').hide();

    if(frm.type.value == ''){
        showMessage(frm.type, 'Please Enter Your Business Type');
        return(false);		
    }
    if(!IsNumber1(frm.type.value)){
        showMessage(frm.type, 'Please Enter one number from (1-5) only');
        return(false);
    }

    return(true);

}


/* Validation function for New account creation from dealers(NewAccountDealer.jsp) */
function validateDealerNewAcctForm(frm)
{
    $('p.validationmsg').hide();
    frm.firstname.value = trimString(frm.firstname.value);  // trim leading and tailing spaces.
    //check user entered the first name or not 	
    if(frm.firstname.value == '') {
        showMessage(frm.firstname,'Please Enter Your First Name');
        return(false);
    }

    //check first name has the string or not
    if(!isValidName(frm.firstname.value) ) {
        showMessage(frm.firstname, 'Name cannot accept any special characters');
        return(false);
    }

    frm.middlename.value = trimString(frm.middlename.value);  // trim leading and tailing spaces.
    //check middle name has the string or not
    if(!isValidName(frm.middlename.value) && frm.middlename.value != '') {
        showMessage(frm.middlename, 'Name cannot accept any special characters');
        return(false);
    }

    frm.lastname.value = trimString(frm.lastname.value);  // trim leading and tailing spaces.
    //check user entered the last name or not 	
    if(frm.lastname.value == ''){
        showMessage(frm.lastname,'Please Enter Your Last Name');
        return(false);
    }

    //check last name has string or not
    if(!isValidName(frm.lastname.value) ) {
        showMessage(frm.lastname, 'Name cannot accept any special characters');
        return(false);
    }

    frm.txtDateofbirth.value = trimString(frm.txtDateofbirth.value);  // trim leading and tailing spaces.
    //check user entered the dob or not
    if(frm.txtDateofbirth.value == ''){
        showMessage(frm.txtDateofbirth, 'Please Enter Your Date Of Birth');
        return(false);
    }
    if(!isValidDateFormat(frm.txtDateofbirth.value)) {
        showMessage(frm.txtDateofbirth,'Invalid date format. Accepted Format is: DD/MM/YYYY.');
        return(false);
    }
    //check the dob in dd/mm/yyyy format or not 
    if(!isValidDate(frm.txtDateofbirth.value)) {
        showMessage(frm.txtDateofbirth,'Please Enter valid Date.');
        return(false);
    }

    var dts = frm.txtDateofbirth.value;
    var dt = Date.fromString(dts)
    var today = new Date();
                //check the dob is less than the current date
    if(dt >= today) {
        showMessage(frm.txtDateofbirth,'Please Enter valid Date of Birth in DD/MM/yyyy format');
        return(false);
    }
    
	//basing on national/foreigner home and mobile number validations
	if(frm.nationality1.options[frm.nationality1.selectedIndex].value == 1){
		
		var txtCtrl = document.getElementById("homephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Home Phone e.g. 0518250340');
			return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Home Phone e.g. 0518250340");
				return(false);
			}
		}
		
		txtCtrl = document.getElementById("workphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Work Phone e.g. 0518250340");
				return(false);
			}
		}
		
		txtCtrl = document.getElementById("faxphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidHome(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Fax Phone e.g. 0518250340");
				return(false);
			}
		}
		
		txtCtrl = document.getElementById("mobilephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Mobile Number e.g. 03015500958');
			return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidMobile(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Mobile Number e.g. 03015500958");
				return(false);
			}
		}

	}else{
		var txtCtrl = document.getElementById("homephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Home Phone e.g. 0518250340');
			return(false);
		 }
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Home Phone e.g. 0518250340");
				return(false);
			}
		}	
		
		txtCtrl = document.getElementById("workphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Work Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("faxphone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Fax Phone e.g. 0518250340");
				return(false);
			}
		}

		txtCtrl = document.getElementById("mobilephone");
		txtCtrl.value = trimString(txtCtrl.value);
		if(txtCtrl.value == '') {
			showMessage(txtCtrl, 'Please Enter Your Mobile Number e.g. 03015500958');
			return(false);
		}
		if(txtCtrl.value != '') {
			if(!IsValidForeignerContact(txtCtrl.value)) {
				showMessage(txtCtrl, "Please Enter Valid Mobile Number e.g. 03015500958");
				return(false);
			}
		}
	
	}

          frm.email.value = trimString(frm.email.value);  // trim leading and tailing spaces.
            //check user entered the email or not
            if(frm.email.value == ''){
                showMessage(frm.email, 'Please Enter Your e-mail');
                return(false);
            }

            //check email is in correct format or not
            if(!IsValidEmail(frm.email.value)) {
                showMessage(frm.email, 'Please Enter Your Valid e-mail');
                return(false);
            }

            //changes by usman on 23 june 2011 starts here

            frm.sHNo.value = trimString(frm.sHNo.value);
            if((frm.sHNo.value) == '') {
                showMessage(frm.sHNo, "Please enter plot no.");
                return (false);
            }
            frm.sStreet.value = trimString(frm.sStreet.value);
            if((frm.sStreet.value) == '') {
                showMessage(frm.sStreet, "Please enter street.");
                return false;
            }
            frm.sZone.value = trimString(frm.sZone.value);
            if((frm.sZone.value) == '') {
                showMessage(frm.sZone, "Please enter sector.")
                return false;
            }
            frm.city.value = trimString(frm.city.value);
            if((frm.city.value) == '') {
                showMessage(frm.city, "Please select city.");
                return false;
            }

            var ssHNo, ssStreet, ssZone;
            ssHNo = trimString(frm.sHNo.value);
            ssStreet = trimString(frm.sStreet.value);
            ssZone = trimString(frm.sZone.value);
            if(ssHNo == '') ssHNo = "-";
            if(ssStreet == '') ssStreet = '-';
            if(ssZone == '') ssZone = '-';
            frm.address1.value = ssHNo ;
            frm.address1.value = frm.address1.value  + "   " + ssStreet;
            frm.address1.value = frm.address1.value  + "   " + "_";
            frm.address1.value = frm.address1.value  + "   " + ssZone;
            ResultType = "MANUAL";
            frm.sLatitude.value = trimString(frm.sLatitude.value);


            if((frm.sLatitude.value) == '') {
                showMessage(frm.sLatitude, "Please enter Latitude");
                return false;
            }


            frm.sLongitude.value = trimString(frm.sLongitude.value);
            if((frm.sLongitude.value) == '') {
                showMessage(frm.sLongitude, "Please enter Longitude");
                return false;
            }


    
    //changes end


    frm.title.value = trimString(frm.title.value);
    //check occupation has strings or not
    if(!isValidTitle(frm.title.value) &&  frm.title.value != '') {
        showMessage(frm.title,'Job title should accept strings only');
        return(false);
    }

    frm.company.value = trimString(frm.company.value);
    //check organization has strings or not
    if(!isValidOrganization(frm.company.value) &&  frm.company.value != '') {
        showMessage(frm.company,'Organization should accept strings only');
        return(false);
    }

	frm.ReferredBy.value='';
	frm.ReferredBy1.value = trimString(frm.ReferredBy1.value);
	if(frm.ReferredBy1.value != '') {
		if(!isValidAccountNoFormat(frm.ReferredBy1.value)) {
			showMessage(frm.ReferredBy1,'Please enter valid account no format(i.e. 0.0.0.1-1122)');
			return(false);
		} 
		convertopoid(frm.ReferredBy1,frm.ReferredBy);
	}
    return true;
}

function validateDealerNewAcctStep2(frm) 
{
    $('p.validationmsg').hide();
    frm.identity.value = trimString(frm.identity.value);  // trim leading and tailing spaces.
    //check user entered his cnic/passport or not form.nationality.selectedIndex
   /* if(frm.business_type.value == 1 && frm.identity.value=='' ){
        showMessage(frm.identity, 'Please Enter Your CNIC Number');
        return(false);
    }else{ 
        showMessage(frm.identity, 'Please Enter Your Passport Number');
        return(false);		
    }
	frm.nationality.options[frm.nationality.selectedIndex].value
*/
if(trimString(frm.parentpoid.value) =='' && frm.business_type.value == "3" ) {
		showMessage(frm.chkSelectParent,'Affinity Child Account cannot be created with out selecting the Affinity Parent Account');
		return(false);
	}

	if(frm.nationality.value == 1 && frm.identity.value=='' ){
        showMessage(frm.identity, 'Please Enter Your CNIC Number');
        return(false);
    }
	if(frm.nationality.value == 2 && frm.identity.value=='' ){ 
        showMessage(frm.identity, 'Please Enter Your Passport Number');
        return(false);		
    }
    //check for dupilcate check done or not
    if(frm.txtDuplicateChk.value == '') {
        showMessage(frm.identity,"Please click on the 'Verify Identity' button to validate the User identity");
        return (false);
    }

    //check for blocked account
    if(frm.txtDuplicateChk.value == 'DUPLICATE' || frm.txtDuplicateChk.value == 'BLOCKED'  ) {
        showMessage(frm.identity,"There are already max Users with this identity already exists / user is blocked. You cannot create account for this user.");
        return(false);
    }
    if(frm.bill.selectedIndex == 0)
    {
        showMessage(frm.bill, 'Please select payment method.');
        return(false);
    }
    if(frm.contract_period.selectedIndex == 0)
    {
        showMessage(frm.contract_period, 'Please select contract period');
        return(false);
    }
    if(frm.svslogintype.selectedIndex == 0)
    {
        showMessage(frm.svslogintype, 'Please select service login type');
        return(false);
    }
    if(frm.bill.selectedIndex == 0 ) {
        frm.execptpercent.value = 0;
        frm.execpttype.selectedIndex = 0;
    } else {
    frm.execptpercent.value = trimString(frm.execptpercent.value);
    if(frm.execptpercent.value == '') {
        showMessage(frm.execptpercent,'Please Enter exemption percentage');
        return(false);
    }

    if(!checkPercent(frm.execptpercent.value)) {
        showMessage(frm.execptpercent,'Please Enter valid exemption percentage');
        return(false);
    }
}
if(frm.planselected.options.length < 1)  {
    document.getElementById("divnumberofplans").innerHTML = "You Cannot proceed with creating the account since there are no plans available with this list.";
    return(false);
}

if( frm.planselected.options.length > 0 )  {
    if(frm.planselected.selectedIndex < 0) {
        document.getElementById("divnumberofplans").innerHTML = "You Cannot proceed with creating the account since there are no plans available with this list.";
        return(false);
    }
    else {
        return(true);
    }
} else {
    document.getElementById("divnumberofplans").innerHTML = "You Cannot proceed with creating the account since there are no plans available with this list.";
    return(false);

}


    return(true);
}

