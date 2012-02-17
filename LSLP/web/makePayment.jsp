<%@page contentType="text/html" import="java.util.*,java.text.SimpleDateFormat"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%System.out.println("into makepayment jsp");%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
     <script type="text/javascript" charset="utf-8">
            $(function()
            {
                    $('.date-pick').datePicker({startDate:'01/01/1900'});
            });
        </script> 
<script language="javascript">
    function onSubmit(frm){
        var flag=document.getElementById('flag').value;  
       // alert(flag);
      if( amountPaid(frm)) {
        if(flag == 0){  
                // alert("into if");
            frm.action="AccountSearch.do?parameter=makePayment";
            //document.NewAccountDealerFormBean.submit();
        }if (flag == 1){  
        //alert("into else");
            frm.action="AccountSearch.do?parameter=makeTotalPayment"
            //document.NewAccountDealerFormBean.submit();
        }
        return true;
       }
       
         return false;
    }
	function isValidAmount(val) {
	var reName=/^-?\d+(\.\d+)?$/;
	return(reName.test(val));
}


function isValidDateFormat(val) {
	var reName=/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/;
	return(reName.test(val));
}
    function amountPaid(frm){
    //alert("amountPaid"+trimString(frm.paidAmount.value));
        frm.paidAmount.value = frm.paidAmount.value;
        //alert("amountPaid111"+frm.paidAmount.value);
        var amount=frm.paidAmount.value; 
        // alert("amount"+amount);
        var payType=frm.paymentType.value;
        //alert("payType"+payType);
      
        if(payType == ""){
            alert("Please select Payment Type ");
            return false;
        }
        
        if(!isValidAmount(amount)) {
            showMessage(frm.paidAmount, 'Please enter valid amount ');            
            return(false);
        }
        
        var payType=document.getElementById('paymentType').value; 
        if(payType == '10011'){
            if(amount <= 0 || amount == ""){
                alert("Please enter valid amount");
                return false;
            }
            var curr=frm.curr.value;      
            var currency=frm.currency.value; 
            if(currency != curr){
                alert("You cannot make payment other than Account Currency");
                return false;
            }         
            if(!isValidDateFormat(frm.receiptDate.value)) {
			showMessage(frm.receiptDate, 'Invalid date format');
			return(false);
		}
		if(!isDate(frm.receiptDate.value)) {
			showMessage(frm.receiptDate, 'Please enter valid receipt date');
			return(false);
		}
                var dt = new Date(frm.receiptDate.value);
                var dt1 = new Date();
                if(dt > dt1) {
                    alert('Receipt date cannot be future date');
                    return(false);
                }
            return(true);
        }
        if(payType == '10012'){
            
            frm.paidAmount.value = trimString(frm.paidAmount.value);
            frm.chequeDate.value = trimString(frm.chequeDate.value);
            frm.chequeNo.value = trimString(frm.chequeNo.value);            
            frm.bankName.value = trimString(frm.bankName.value);
          //  frm.branchName.value = trimString(frm.branchName.value);
         //   frm.payersName.value = trimString(frm.payersName.value);
            
            var amount=frm.paidAmount.value;    
            var chequeDate=frm.chequeDate.value;             
            var chequeNo=frm.chequeNo.value;             
            var bankName=frm.bankName.value; 
            //var branchName=frm.branchName.value; 
            //var payersName=frm.payersName.value;
            if(amount <= 0 || amount == ""){
                alert("Please enter valid amount");
                return false;
            }
            
            
            if(!isValidDateFormat(chequeDate)) {
			showMessage(frm.chequeDate, 'Invalid date format');
			return(false);
		}
		if(!isDate(chequeDate)) {
			showMessage(frm.chequeDate, 'Please enter valid cheque date');
			return(false);
		}
                var dt = new Date(chequeDate);
                var dt1 = new Date();
                if(dt > dt1) {
                    alert('Cheque cannot be future date');
                    return(false);
                }
            
            
            if(!isValidChequeNo(chequeNo)) {
                showMessage(frm.chequeNo, 'Please enter valid cheque number');
                return false;
            }           
            
            if(!isValidBankName(bankName)) {
                showMessage(frm.bankName, 'Please enter valid bank name');
                return false;
            }
            
          //  if(!isValidBranchName(branchName)) {
          //      showMessage(frm.branchName, 'Please enter valid branch name');
         //       return false;
         //   }
            
         //   if(!isValidPayersName(payersName)) {
         //       showMessage(frm.payersName, 'Please enter valid name');
          //      return false;
          //  }
            var curr=frm.curr.value;      
            var currency=frm.currency.value; 
            if(currency == curr){
                return true;
            }else{
                alert("You annot make payment other than account currency.");
                return false;
            }
        }
        if(payType == '10101'){
            frm.paidAmount.value = trimString(frm.paidAmount.value);
            frm.creditCardNo.value = trimString(frm.creditCardNo.value);
            frm.expiryDate.value = trimString(frm.expiryDate.value);
            frm.transactionNo.value = trimString(frm.transactionNo.value);
            frm.transactionDate.value = trimString(frm.transactionDate.value);
            
            var amount=frm.paidAmount.value;
            var creditCardNo=frm.creditCardNo.value;
            var cardType=frm.cardType.value;
            var expiryDate=frm.expiryDate.value;
            var transactionNo=frm.transactionNo.value;
            var transactionDate=frm.transactionDate.value;            
            if(amount <= 0 || amount == ""){
                alert("Please enter valid amount");
                return false;
            }
            if(cardType == ""){
                alert("Please enter Card Type");
                return false;
            }
            if(creditCardNo == ""){
                alert("Please enter Credit Card No");
                return false;
            }  
            if(!IsNumber(creditCardNo)) {
			alert('Invalid Card number format');
			return false;
		}
            if(!testCreditCard(frm.creditCardNo,frm.cardType)) {
			alert('Invalid Card number');
			return(false);
		}    
            if(expiryDate == ""){
                alert("Please enter Expiry Date");
                return false;
            } 
            if(!validateExpiryDate(frm.expiryDate)) {
                showMessage(frm.expiryDate, 'Please enter valid date');
                return(false);
            }
            if(transactionNo != ""){
                if(!isValidTransactionNo(transactionNo)) {
			showMessage(frm.transactionNo, 'Only numbers between (2-12)');
			return(false);
		}
             }   
             if(transactionDate != ""){
                if(!isValidDateFormat(transactionDate)) {
			showMessage(frm.transactionDate, 'Invalid date format');
			return(false);
		}
		if(!isDate(transactionDate)) {
			showMessage(frm.transactionDate, 'Please enter valid date');
			return(false);
		}
                var dt = new Date(transactionDate);
                var dt1 = new Date();
                if(dt > dt1) {
                    alert('Transaction date cannot be future date');
                    return(false);
                }
            }
            var curr=frm.curr.value;      
            var currency=frm.currency.value; 
            if(currency == curr){
                return true;
            }else{
                alert("Cannot make payments other than account currency");
                return false;
            }
        }
    }
    
    function payment(){
        if(document.getElementById('paymentType').value == 10012){
            document.getElementById("creditCardDetails").style.visibility = 'hidden';
            document.getElementById("chequeDetails").style.visibility='visible';
            document.getElementById("cashReceiptDetails").style.visibility='hidden';
            document.getElementById("tablerowcredit").style.display='none';
        document.getElementById("tablerowchecque").style.display='block';
            document.getElementById("tablerowcash").style.display='none';
        }
        if(document.getElementById('paymentType').value == 10101){
            document.getElementById("creditCardDetails").style.visibility = 'visible';
            document.getElementById("chequeDetails").style.visibility='hidden';
            document.getElementById("cashReceiptDetails").style.visibility='hidden';
            document.getElementById("tablerowcredit").style.display='block';
        document.getElementById("tablerowchecque").style.display='none';
            document.getElementById("tablerowcash").style.display='none';
        }
        if(document.getElementById('paymentType').value == 10011){
            document.getElementById("creditCardDetails").style.visibility = 'hidden';
            document.getElementById("chequeDetails").style.visibility='hidden';
            document.getElementById("cashReceiptDetails").style.visibility='visible';
            document.getElementById("tablerowcredit").style.display='none';
            document.getElementById("tablerowchecque").style.display='none';
            document.getElementById("tablerowcash").style.display='block';            
        }
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
}var dtCh= "/";
var minYear=1900;
var maxYear=2100;


	function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
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
		alert("The date format should be : mm/dd/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Please enter a valid day")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	alert("dtStr"+dtStr);
	
return true
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


    function firstView(){
        document.getElementById("creditCardDetails").style.visibility = 'hidden';
        document.getElementById("chequeDetails").style.visibility='hidden';
        document.getElementById("cashReceiptDetails").style.visibility='hidden';
        document.getElementById("tablerowcredit").style.display='none';
        document.getElementById("tablerowchecque").style.display='none';
        document.getElementById("tablerowcash").style.display='none';
    }
        </script>
    </head>
    <body onload="firstView()">
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                    
                    <ul class="form">
                        <form  name="makePayment" method="POST" action="AccountSearch.do?parameter=makePayment" onsubmit="return onSubmit(this)" >
                            <table width="679" border="0" cellspacing="0" cellpadding="0" class="info-listing" >
                                <tr  border="0" cellspacing="0" cellpadding="0">
                                    <td width="30%"> Payment Type <SPAN class=mandatory>*</SPAN></td>
                                    <td>
                                        <select name="paymentType" id="paymentType" class="select" onchange="payment()">
                                            <option value="">select</option>
                                            <option value="10011">Cash</option>
                                            <option value="10012">Cheque</option>                                
                                            <option value="10101">Credit Card</option>
                                        </select>
                                    </td>
                                </tr>
                                
                                <tr width="30%">
                                    <td>Due Amount</td>
                                    <td><jsp:include flush="true" page="currency.jsp">
                                            <jsp:param name="currency" value='<%= request.getAttribute("currency")%>'/>
                                    </jsp:include> <%= request.getAttribute("due")%></td>
                                </tr>    
                                <tr>
                                    <td>Amount Paid <SPAN class=mandatory>*</SPAN></td>
                                    <td><input type="text" name="paidAmount" class="txt" value=''/><P class=validationmsg></P></td>
                                </tr>
                                <tr>
                                    <td>Currency <SPAN class=mandatory>*</SPAN></td>
                                    <td>
                                        <select name="currency">
                                            <jsp:include flush="true" page="currencyList.jsp"/>
                                        </select>
                                    </td>
                                </tr>
                                <tr id="tablerowcash"><td colspan=2>
                                        <div id="cashReceiptDetails">
                                            <table class="info-listing">
                                                <tr>
                                                    <td width="30%">Receipt No </td>
                                                    <td><input type="text" name="receiptNo" id="receiptNo" class="txt"><P class=validationmsg></P></td>
                                                </tr>
                                                <% 
                                                Date d=new Date();
                                                SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
                                                String strCreatedT=sdf.format(d);                                        
                                                %>
                                                <tr>
                                                    <td>Receipt Date<br>(ex: mm/dd/yyyy) <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input type="text" class="date-pick dp-applied datetxt" name="receiptDate" id="receiptDate" maxlength="10" value="<%= strCreatedT %>"  /><P class=validationmsg></P></td>
                                                </tr>                                    
                                            </table>
                                        </div>
                                </td></tr>
                                <tr id="tablerowchecque"><td colspan=2>
                                        <div id="chequeDetails" >
                                            <table width="100%" class="info-listing">
                                                <tr>
                                                    <td width="30%">Cheque Date<br>(Ex: mm/dd/yyyy) <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input type="text" class="date-pick dp-applied datetxt" name="chequeDate" id="chequeDate" maxlength="10"  /><P class=validationmsg></P></td>                            
                                                </tr>
                                                <tr>
                                                    <td>Cheque Number <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input type="text" name="chequeNo" id="chequeNo" class="txt"><P class=validationmsg></P></td>
                                                </tr>                                    
                                                <tr>
                                                    <td>Bank Name <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input type="text" name="bankName" id="bankName" class="txt"><P class=validationmsg></P></td>
                                                </tr>
                                             <!--   <tr>
                                                    <td>Branch Name <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input type="text" name="branchName" id="branchName" class="txt"><P class=validationmsg></P></td>
                                                </tr>
                                                <tr>
                                                    <td>Payers Name <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input type="text" name="payersName" id="payersName" class="txt"><P class=validationmsg></P></td>
                                                </tr> -->
                                            </table>
                                        </div>
                                </td></tr>
                                <tr id="tablerowcredit"><td colspan=2>
                                        <div id="creditCardDetails">
                                            <table class="info-listing">
                                                <tr>
                                                    <td>Card Type <SPAN class=mandatory>*</SPAN></td>
                                                    <td>
                                                        <select name="cardType" id="cardType" class="select">
                                                            <option value="">Select</option>
                                                            <option value="AmEx">American Express</option>
                                                            <option value="CarteBlanche">Carte Blanche</option>
                                                            <option value="DinersClub">Diners Club</option>
                                                            <option value="Discover">Discover</option>
                                                            <option value="EnRoute">enRoute</option>
                                                            <option value="JCB">JCB</option>
                                                            <option value="Maestro">Maestro</option>
                                                            <option value="MasterCard">MasterCard</option>
                                                            <option value="Solo">Solo</option>
                                                            <option value="Switch">Switch</option>
                                                            <option selected value="Visa">Visa</option>
                                                            <option value="VisaElectron">Visa Electron</option>
                                                            <option value="LaserCard">Laser</option>
                                                        </select>                                            
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="30%">Credit Card No <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input type="text" name="creditCardNo" id="creditCardNo" class="txt"></td>
                                                </tr>
                                                <tr>
                                                    <td>Expiry date(MMYY) <SPAN class=mandatory>*</SPAN></td>
                                                    <td><input class=txt type="text" name="expiryDate" id="expiryDate" maxlength="10" maxlength="4" /><P class=validationmsg></P></td>
                                                </tr>
                                                <tr>
                                                    <td>Transaction No </td>
                                                    <td><input type="text" name="transactionNo" id="transactionNo" class="txt"><P class=validationmsg></P></td>
                                                </tr>
                                                <tr>
                                                    <td>Transaction Date </td>
                                                    <td><input type="text" class="date-pick dp-applied datetxt" name="transactionDate" id="transactionDate" maxlength="10" value="<%= strCreatedT %>"  /><P class=validationmsg></P></td>
                                                </tr>
                                            </table>
                                        </div>
                                </td></tr>
                            </table>
                            <table class="info-listing">
                                <tr>
                                    <td> <input class=submit-btn type="submit" name="Submit" value="Submit"/> </td>
                                </tr>
                            </table>
                            
                            
                            <input type="hidden" name="accountNumber" value="<%= request.getAttribute("accountNumber")%>">
                            <input type="hidden" name="flag" value="<%= request.getAttribute("flag") %>">
                            <input type="hidden" name="curr" value="<%= request.getAttribute("currency") %>">
                            <input type="hidden" name="billNo" value="<%= request.getAttribute("billNo") %>">
                        </form>
                    </ul>
                </div>
            </div>
        </div>
    </body>
</html>
