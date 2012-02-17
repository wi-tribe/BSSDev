<%@ page contentType="text/html;charset=UTF-8" 
         language="java" 
         import="customfields.*, 
                 com.portal.bas.*,
                 com.portal.bas.comp.*, 
                 com.portal.pcm.*,
                 com.portal.app.ccare.comp.*, 
                 Wtb.MyAccount.*,
                 com.portal.web.comp.*, 
                 com.portal.web.*, 
                 java.net.*, 
                 java.util.*, 
                 java.text.*"   
         errorPage="error.jsp"  %>
<%@ include file="includes/constants.jsp"%>
<%@ include file="includes/ServiceConstants.jsp"%>
<script language="javascript" type="text/javascript" src="js/validations.js"></script>
<script language="javascript" type="text/javascript" src="js/validates.js"></script>

<%
session.removeAttribute("accountCreationBean");
session.removeAttribute(PLANS_BEAN);
session.removeAttribute(SERVICE_DESCR);
String reffrom = request.getParameter("salesweb");
String urlFrom = request.getHeader("referer");
if (reffrom == null)
    reffrom = "";
    
//Only User can come from webportal can access this page.
if(reffrom.equals("SALES")){
    //
} else {
        response.sendRedirect("index.jsp");

}
//Page level Variables declaration.
String cSalutation, cFirstName, cLastName, cMiddleName, cEmail, cPhoneNo, cCNIC, cOccupation, cCompany, cPlot, cStreet, cSubzone ;
String cZone, cCity, cState, cZip, cCountry, cCoverage, cBusinessType, cServiceType , cAddress, cTitle,cSalesID, cLeadId;
String cCoveragetype, cLongitude, clatitude,cSLA;
PLightComponentHelper lcParent, lcAffinitySelectedSlab, lcReferredBy;
PLightComponentHelper lcPaymentAlert;

//Read data received from the Lead Management page.
cSalutation = (( request.getParameter(SALUTE) == null) ? "Mr." : request.getParameter(SALUTE));
cFirstName =  (( request.getParameter(FIRSTNAME) == null) ? "" : request.getParameter(FIRSTNAME));
cMiddleName = (( request.getParameter(MIDDLENAME) == null) ? "" : request.getParameter(MIDDLENAME));
cLastName = (( request.getParameter(LASTNAME) == null) ? "" : request.getParameter(LASTNAME));
cEmail =  (( request.getParameter(EMAIL) == null) ? "" : request.getParameter(EMAIL));
cCity =  (( request.getParameter(CITY) == null) ? "Karachi" : request.getParameter(CITY));
cState = (( request.getParameter(STATE) == null) ? "" : request.getParameter(STATE));
cZip = (( request.getParameter(ZIP) == null) ? "" : request.getParameter(ZIP));
cCountry = (( request.getParameter(COUNTRY) == null) ? "" : request.getParameter(COUNTRY));
cPhoneNo = (( request.getParameter("cnotype1") == null) ? "" : request.getParameter("cnotype1"));
cCNIC =  (( request.getParameter("cnic") == null) ? "" : request.getParameter("cnic"));
cAddress = (( request.getParameter("Address1") == null) ? "" : request.getParameter("Address1"));
cServiceType = (( request.getParameter("accountservicetype1") == null) ? "2" : request.getParameter("accountservicetype1"));
cTitle = (( request.getParameter(TITLE) == null) ? "" : request.getParameter(TITLE));
cCompany = (( request.getParameter("company") == null) ? "" : request.getParameter("company"));
cPlot = (( request.getParameter("sHNo") == null) ? "" : request.getParameter("sHNo") + "");
cStreet = (( request.getParameter("sStreet") == null) ? "" : request.getParameter("sStreet") + "");
cSubzone = (( request.getParameter("sRoad") == null) ? " " : request.getParameter("sRoad") + "");
cZone = (( request.getParameter("sSector") == null) ? "" : request.getParameter("sSector"));
cBusinessType = (( request.getParameter("business_type") == null) ? "1" : request.getParameter("business_type"));
cCoveragetype = (( request.getParameter("sCoverage") == null) ? "" : request.getParameter("sCoverage"));
cLongitude = (( request.getParameter("sLongitude") == null) ? "" : request.getParameter("sLongitude"));
clatitude = (( request.getParameter("sLatitude") == null) ? "" : request.getParameter("sLatitude"));
cSLA = (( request.getParameter("sla") == null) ? "" : request.getParameter("sla"));
cSalesID =  (( request.getParameter("salesId") == null) ? "" : request.getParameter("salesId"));
cLeadId =  (( request.getParameter("leadId") == null) ? "" : request.getParameter("leadId"));

//session variables required after the lead convered to account.
session.setAttribute("newAcct_Leadid", cLeadId);
session.setAttribute("newAcct_createBy", reffrom);
session.setAttribute("newAcct_salesId", cSalesID);
cAddress = cPlot  + "   " + cStreet +"   " +  cSubzone + "   " +  cZone;

%>
<jsp:useBean id="accountCreationBean" class="com.portal.bas.comp.PIAComponentCollection" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%
PPooledConnectionClientServices pCS = (PPooledConnectionClientServices) session.getAttribute(CREATE_CONNECTION);
if (pCS == null) {
	// Create a new client service
	pCS =  new PPooledConnectionClientServices( (PClientServices) application.getAttribute(ServletUtil.PARENT_SERVICE));

	// Setup a connection for this transaction
	Object o = ServletUtil.logIn(pCS);
	application.setAttribute("CREATE_CONNECTION", pCS);
	session.setAttribute("CREATE_CONNECTION", pCS);
	// Initialize the listener with the client services object
	// so it can handle return of the connection when session expires
	ConnectionListener listener = new ConnectionListener(session.getCreationTime() , pCS);
	application.setAttribute("CREATE_LISTENER", listener);
	session.setAttribute("CREATE_LISTENER", listener);
	// since controller is New we need to grab the http headers
	// to determine the locale etc.
	ServletUtil.saveLocaleInfo(request);
	pCS.registerApp("CONTENT_APP_NAME", (Locale) session.getAttribute(ServletUtil.LOCALE), null);
 }
if (accountCreationBean.getController() == null) {
    pCS =  new PPooledConnectionClientServices( (PClientServices)
    application.getAttribute(ServletUtil.PARENT_SERVICE));
    accountCreationBean.setServices(pCS);
    session.setAttribute(CREATE_CONNECTION, pCS);
    ConnectionListener listener = new ConnectionListener(session.getCreationTime() , pCS);
    session.setAttribute(CREATE_LISTENER, listener);
    ServletUtil.saveLocaleInfo(request);
    pCS.registerApp(APP_NAME, (Locale) session.getAttribute(ServletUtil.LOCALE), null);
    accountCreationBean.createController("com.portal.app.ccare.comp.PIACreateAccountBeanImpl");
    ServletUtil.addComponent(accountCreationBean, SALUTE, "FldNameinfo[1].FldSalutation");
    ServletUtil.addComponent(accountCreationBean, FIRSTNAME, "FldNameinfo[1].FldFirstName");
    ServletUtil.addComponent(accountCreationBean, MIDDLENAME, "FldNameinfo[1].FldMiddleName");
    ServletUtil.addComponent(accountCreationBean, LASTNAME, "FldNameinfo[1].FldLastName");
    ServletUtil.addComponent(accountCreationBean, ADDRESS1, "FldNameinfo[1].FldAddress");
    ServletUtil.addComponent(accountCreationBean, CITY, "FldNameinfo[1].FldCity");
    ServletUtil.addComponent(accountCreationBean, STATE, "FldNameinfo[1].FldState");
    ServletUtil.addComponent(accountCreationBean, COUNTRY, "FldNameinfo[1].FldCountry");
    ServletUtil.addComponent(accountCreationBean, ZIP, "FldNameinfo[1].FldZip");
    ServletUtil.addComponent(accountCreationBean, EMAIL, "FldNameinfo[1].FldEmailAddr");
    ServletUtil.addComponent(accountCreationBean, HOMEPHONE,"FldNameinfo[1].FldPhones[1].FldPhone");
    ServletUtil.addComponent(accountCreationBean, WORKPHONE,"FldNameinfo[1].FldPhones[2].FldPhone");
    ServletUtil.addComponent(accountCreationBean, FAX,"FldNameinfo[1].FldPhones[3].FldPhone");
    ServletUtil.addComponent(accountCreationBean, MOBILE,"FldNameinfo[1].FldPhones[5].FldPhone");
    //ServletUtil.addComponent(accountCreationBean, PAGER, "FldNameinfo[1].FldPhones[5].FldPhone");
    // Business Type
    ServletUtil.addComponent(accountCreationBean, WTB_BUS_TYPE,"FldAcctinfo[0].FldBusinessType");
    //CNIC & Passport No
    ServletUtil.addComponent(accountCreationBean, IDENTITY,"FldAcctinfo[0].FldAccessCode1");
    ServletUtil.addComponent(accountCreationBean, TPIN,"FldAcctinfo[0].FldAccessCode2");
    //Date of Birth
    ServletUtil.addComponent(accountCreationBean, WTB_DOB, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldDobT","FldProfileObj.WtbFldCustinfo.WtbFldDobT");
    //Occupation
    ServletUtil.addComponent(accountCreationBean, TITLE, "FldNameinfo[1].FldTitle");
    //Organization
    ServletUtil.addComponent(accountCreationBean, COMPANY, "FldNameinfo[1].FldCompany");
    //Payment method
    ServletUtil.addComponent(accountCreationBean, BILL, "FldBillinfo[0].FldPayType");
    //ContractPeriod , Start Date, End date
    ServletUtil.addComponent(accountCreationBean, CONTRACT_PERIOD, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractPeriod","FldProfileObj.WtbFldContractDetails.WtbFldContractPeriod");
    ServletUtil.addComponent(accountCreationBean, CONTRACT_START, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractStartT","FldProfileObj.WtbFldContractDetails.WtbFldContractStartT");
    ServletUtil.addComponent(accountCreationBean, CONTRACT_END, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractEndT","FldProfileObj.WtbFldContractDetails.WtbFldContractEndT");

    
    //Package & Usage Limit
    ServletUtil.addComponent(accountCreationBean, PROMO_PKG,"FldAcctinfo[0].FldAacPackage");
    //Service Login type
    ServletUtil.addComponent(accountCreationBean, SVS_LOGIN_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldServiceLoginType","FldProfileObj.WtbFldCustinfo.WtbFldServiceLoginType");
    //Taxation
    ServletUtil.addComponent(accountCreationBean, TAX_EXPT_TYPE, "FldProfiles[2].FldInheritedInfo.WtbFldTaxExemptions[0].WtbFldExemptionType","FldProfileObj.WtbFldTaxExemptions[any].WtbFldExemptionType");
    ServletUtil.addComponent(accountCreationBean, TAX_EXPT_PERCENT, "FldProfiles[2].FldInheritedInfo.WtbFldTaxExemptions[0].WtbFldPercent","FldProfileObj.WtbFldTaxExemptions[any].WtbFldPercent");
    // account type
    ServletUtil.addComponent(accountCreationBean, WTB_ACCT_SERVICE_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldAccountServiceType","FldProfileObj.WtbFldCustinfo.WtbFldAccountServiceType");

    ServletUtil.addComponent(accountCreationBean, AACACCESS,"FldAacAccess");
    ServletUtil.addComponent(accountCreationBean, LOCALE, "FldLocales[1].FldLocale");
    ServletUtil.addComponent(accountCreationBean, CURRENCY, "FldBillinfo[0].FldCurrency","FldBillinfo[0].FldCurrency");
    ServletUtil.addComponent(accountCreationBean, CURRENCY, "FldAcctinfo[0].FldCurrency","FldAcctinfo[0].FldCurrency");
    ServletUtil.addComponent(accountCreationBean, INVNAME, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldName", "FldPayinfoObj.FldInvInfo[0].FldName");
    ServletUtil.addComponent(accountCreationBean, DELIVERMETHOD, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldDeliveryPrefer", "FldPayinfoObj.FldInvInfo[0].FldDeliveryPrefer");
    ServletUtil.addComponent(accountCreationBean, INVADDRESS1, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldAddress", "FldPayinfoObj.FldInvInfo[0].FldAddress");
    ServletUtil.addComponent(accountCreationBean, INVCITY, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldCity", "FldPayinfoObj.FldInvInfo[0].FldCity");
    ServletUtil.addComponent(accountCreationBean, INVSTATE, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldState", "FldPayinfoObj.FldInvInfo[0].FldState");
    ServletUtil.addComponent(accountCreationBean, INVZIP, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldZip", "FldPayinfoObj.FldInvInfo[0].FldZip");
    ServletUtil.addComponent(accountCreationBean, INVCOUNTRY, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldCountry", "FldPayinfoObj.FldInvInfo[0].FldCountry");
    ServletUtil.addComponent(accountCreationBean, INVEMAIL, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldDeliveryDescr", "FldPayinfoObj.FldInvInfo[0].FldDeliveryDescr");
    //Added by muralidhar
    ServletUtil.addComponent(accountCreationBean, WTB_CLIENT_APP_NAME, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldClientAppName","FldProfileObj.WtbFldCustinfo.WtbFldClientAppName");
    //end by muralidhar
    ServletUtil.addComponent(accountCreationBean, PP_NAME, "FldPayinfo[1].FldInheritedInfo[0].WtbFldPpdInfo[0].FldName","FldPayinfoObj.WtbFldPpdInfo[0].FldName");
    ServletUtil.addComponent(accountCreationBean, PP_EMAIL, "FldPayinfo[1].FldInheritedInfo[0].WtbFldPpdInfo[0].FldEmailAddr","FldPayinfoObj.WtbFldPpdInfo[0].FldEmailAddr");
    ServletUtil.addComponent(accountCreationBean, PP_ADDR, "FldPayinfo[1].FldInheritedInfo[0].WtbFldPpdInfo[0].FldAddress","FldPayinfoObj.WtbFldPpdInfo[0].FldAddress");
    ServletUtil.addComponent(accountCreationBean, PP_CITY, "FldPayinfo[1].FldInheritedInfo[0].WtbFldPpdInfo[0].FldCity","FldPayinfoObj.WtbFldPpdInfo[0].FldCity");
    ServletUtil.addComponent(accountCreationBean, PP_STATE, "FldPayinfo[1].FldInheritedInfo[0].WtbFldPpdInfo[0].FldState","FldPayinfoObj.WtbFldPpdInfo[0].FldState");
    ServletUtil.addComponent(accountCreationBean, PP_COUNTRY, "FldPayinfo[1].FldInheritedInfo[0].WtbFldPpdInfo[0].FldCountry","FldPayinfoObj.WtbFldPpdInfo[0].FldCountry");
    ServletUtil.addComponent(accountCreationBean, PP_ZIP, "FldPayinfo[1].FldInheritedInfo[0].WtbFldPpdInfo[0].FldZip","FldPayinfoObj.WtbFldPpdInfo[0].FldZip");

    ServletUtil.addComponent(accountCreationBean, DDNAME, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldName", "FldPayinfoObj.FldDdInfo[0].FldName");
    ServletUtil.addComponent(accountCreationBean, BANK, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldBankNo", "FldPayinfoObj.FldDdInfo[0].FldBankNo");
    ServletUtil.addComponent(accountCreationBean, ACCTNUM, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldDebitNum", "FldPayinfoObj.FldDdInfo[0].FldDebitNum");
    ServletUtil.addComponent(accountCreationBean, ACCTTYPE, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldType", "FldPayinfoObj.FldDdInfo[0].FldType");
    ServletUtil.addComponent(accountCreationBean, DDADDR1, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldAddress", "FldPayinfoObj.FldDdInfo[0].FldAddress");
    ServletUtil.addComponent(accountCreationBean, DDCITY, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldCity","FldPayinfoObj.FldDdInfo[0].FldCity");
    ServletUtil.addComponent(accountCreationBean, DDSTATE, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldState", "FldPayinfoObj.FldDdInfo[0].FldState");
    ServletUtil.addComponent(accountCreationBean, DDZIP, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldZip", "FldPayinfoObj.FldDdInfo[0].FldZip");
    ServletUtil.addComponent(accountCreationBean, DDCOUNTRY, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldCountry", "FldPayinfoObj.FldDdInfo[0].FldCountry");
    
    ServletUtil.addComponent(accountCreationBean, CCNAME, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldName", "FldPayinfoObj.FldCcInfo[0].FldName");
    ServletUtil.addComponent(accountCreationBean, DEBITNUM, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldDebitNum", "FldPayinfoObj.FldCcInfo[0].FldDebitNum");
    ServletUtil.addComponent(accountCreationBean, CV, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldSecurityId", "FldPayinfoObj.FldCcInfo[0].FldSecurityId");
    ServletUtil.addComponent(accountCreationBean, DEBITEXP, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldDebitExp", "FldPayinfoObj.FldCcInfo[0].FldDebitExp");
    ServletUtil.addComponent(accountCreationBean, CCADDR1, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldAddress", "FldPayinfoObj.FldCcInfo[0].FldAddress");
    ServletUtil.addComponent(accountCreationBean, CCCITY, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldCity", "FldPayinfoObj.FldCcInfo[0].FldCity");
    ServletUtil.addComponent(accountCreationBean, CCSTATE, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldState", "FldPayinfoObj.FldCcInfo[0].FldState");
    ServletUtil.addComponent(accountCreationBean, CCZIP, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldZip", "FldPayinfoObj.FldCcInfo[0].FldZip");
    ServletUtil.addComponent(accountCreationBean, CCCOUNTRY, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldCountry", "FldPayinfoObj.FldCcInfo[0].FldCountry");
    // Additional Fields
    ServletUtil.addComponent(accountCreationBean, COVERAGE_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtCoverageType","FldProfileObj.WtbFldCustinfo.WtbFldCtCoverageType");
    ServletUtil.addComponent(accountCreationBean, LEAD_IDENTITY, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldLeadIdentity","FldProfileObj.WtbFldCustinfo.WtbFldLeadIdentity");
    
    ServletUtil.addComponent(accountCreationBean, LATITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLatitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLatitude");
    ServletUtil.addComponent(accountCreationBean, LONGITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLongitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLongitude");
    ServletUtil.addComponent(accountCreationBean, SLA, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtSla","FldProfileObj.WtbFldCustinfo.WtbFldCtSla");
    ServletUtil.addComponent(accountCreationBean, SALES_ID, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldSalesId","FldProfileObj.WtbFldCustinfo.WtbFldSalesId");
    
            
    ServletUtil.addComponent(accountCreationBean, CPE_DEVICE_ID, "FldDevices[0].FldDeviceObj");
         
    // Parent- Child Configuaration
    lcParent = ServletUtil.addComponent(accountCreationBean, PARENT, "FldGroupInfo[0].FldParent");
    lcAffinitySelectedSlab = ServletUtil.addComponent(accountCreationBean, "lstAffinitySlab", "FldAcctinfo[0].FldAacSerialNum");
    lcReferredBy = ServletUtil.addComponent(accountCreationBean, "ReferredBy", "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldReferredBy","FldProfileObj.WtbFldCustinfo.WtbFldReferredBy"); 
    lcPaymentAlert = ServletUtil.addComponent(accountCreationBean, PAYMENT_ALERT, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldPymtalertSubscriptionId","FldProfileObj.WtbFldCustinfo.WtbFldPymtalertSubscriptionId");
}

WtbSelfCareUtility wsutil  = (WtbSelfCareUtility) pCS.createController("Wtb.MyAccount.WtbSelfCareUtility");
Properties props = pCS.getDefaultProperties();
wsutil.setProperties(props);

List lstContractPeriod = wsutil.getContractPeriod();  
//out.print(wsutil.getServerTime().toString());

SimpleDateFormat formatter = new SimpleDateFormat(props.getProperty("applicationdateformat")); 

String brand=null;
%>

<%
PIACAPackageInfoBean plansBean = (PIACAPackageInfoBean) pCS.createController("com.portal.app.ccare.comp.PIACAPackageInfoBeanImpl");
String pricingPlanStr = props.getProperty(PRICEPLANSPECIFIER);
if (pricingPlanStr == null) {
    pricingPlanStr = DEFAULTPRICEPLAN;
}
//If the business Type is FUT(5) Then plan list should only starting with FUT
cCity = cCity.trim().toLowerCase();
cCity = cCity.replaceFirst(cCity.substring(0,1), cCity.substring(0,1).toUpperCase());
pricingPlanStr = cCity;
if(cBusinessType.equals("3")) {
  pricingPlanStr = "Affinity_Child";	
}
if(cBusinessType.equals("4")) {
  pricingPlanStr = "Affinity";	
}
if(cBusinessType.equals("5")) {
  pricingPlanStr = "FUT";	
}
if(cBusinessType.equals("6")) {
  pricingPlanStr = "CSR";	
}
if(cBusinessType.equals("7")) {
  pricingPlanStr = "SOHO";	
}
if(cBusinessType.equals("7")) {
  pricingPlanStr = "SOHO";	
}
if(cBusinessType.equals("8")) {
  pricingPlanStr = "Employee";	
}
if(cBusinessType.equals("9")) {
  pricingPlanStr = "Test";	
}
System.out.println("**Final Plan List selected:" + pricingPlanStr);
PModelHandle plansModel = null;
try {
	 plansModel = (PModelHandle) plansBean.listOfPlans(pricingPlanStr);
} catch(Exception e) {
	System.out.println(e.toString());
}
//PModelHandle plansModel = (PModelHandle) plansBean.listOfPlans(cCity.toLowerCase());
session.setAttribute(PLANS_BEAN, plansBean);
String planIndex = "0";
%>
<%
String windowTitle = WINDOW_TITLE;
String welcomeName = "";
if (session != null) {
    welcomeName = "";
} else {
    welcomeName = "";
}
String sectionImage = "text-login.gif"; //Billing: text-billing1.gif ;  MyAccount: text-my-account.gif
%>


<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
       
    </head>
    <script type="text/javascript" charset="utf-8">
$(function()
{
	$('.date-pick').datePicker({startDate:'01/01/1900'});
});
    </script>
    <script language=javascript>
        var arrPlanList = new Array(); // Global array varibale to load the planlist for the City.

function refreshplanlist(frm,servType,sla)
{
	var str, val,txt;
	var addstatus = false;
	str = ''; 
	val ='';
	txt = '';
	//servType = "Prepaid";
	while(frm.planselected.options.length > 0) {
		frm.planselected.options[0]=null;
	}
	for(i=0; i<arrPlanList.length;i++) {
		addstatus = false;
		if(servType != '') {
			servType = servType.toUpperCase();
			txt = arrPlanList[i].toUpperCase();
			if(txt.indexOf(servType) > 0)
				addstatus=true;
			}
			else if(sla != '') {
				addstatus = true;
			}
			else {
				addstatus = true;
			}

		if(addstatus) { 
			var opt = new Option();
			str = arrPlanList[i];
			val = str.substr(0,str.indexOf("::"));
			txt = str.substr(str.indexOf("::") + 2);
			opt.value=val;
			opt.text= txt;
			frm.planselected.options.add(opt);
		}
	}
}
//Pagelevel -  Validations.
var http = createRequestObject(); 
function appendNTN(form){
$('p.validationmsg').hide();
var ntn = trimString(form.NTN.value);
var sid = trimString(form.subid.value);
form.identity.value = ntn +"-"+ sid;
return;
}
function checkDuplicateCNIC(form) {
    $('p.validationmsg').hide();
    form.txtDuplicateChk.value="";
    var cnic = '';
    var national = '';
    var vBusType = form.business_type.options[form.business_type.selectedIndex].value;
    if( (vBusType == "1" ) ||  (vBusType == "5" )||  (vBusType == "3" )||  (vBusType == "8" )||  (vBusType == "9" )) {
	cnic = trimString(form.identity.value);
	national = trimString(form.nationality.options[form.nationality.selectedIndex].value);
	if(cnic == ''){
		showMessage(form.identity, 'Please Enter Your CNIC/Passport Number');
		return;
	}

	if(isValidCNIC(cnic, national)) {
		//
	}
	else {
		if(national == "1") {
			showMessage(form.identity, 'CNIC Format Expected: 0000000000000(13 digits)');
		}
		else {
			showMessage(form.identity, 'Please enter valid passport no.');
		}
		return;
	}
    } else {
	cnic = trimString(form.NTN.value);
	if(cnic == '') {
		showMessage(form.NTN, 'Please Enter Your NTN Number');
		return;
	} else {
            cnic = cnic + "-";
	}
    }
	showprogess('IDENTITY');
		var url="chkforDuplicate.jsp";
		var params = "identity="+cnic;
		http.open("POST", url , true);
		http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		http.setRequestHeader("Content-length", params.length);
		http.setRequestHeader("Connection", "close");
		http.onreadystatechange = handleDuplicateResponse;
		http.send(params);
}

function handleDuplicateResponse() {
    //alert(http.readyState);
    if(http.readyState == 4 && http.status == 200) {
	    hideprogess();
        var res = http.responseText;
        res =res.replace(' ','');
        //alert(res);
	var ctl;
        var vBusType = document.createform1.business_type.options[document.createform1.business_type.selectedIndex].value; 
	if( ( vBusType == "1" )  ||  (vBusType == "5" )  ||  (vBusType == "3" ) ||  (vBusType == "8" ) ||  (vBusType == "9" )){
		ctl = document.createform1.identity;
	} else {
		ctl = document.createform1.NTN;
	}
        if(res.indexOf('OK.') > -1) {
            showMessage(ctl, "New Subscriber, Proceed with creating the account.", true);
            document.createform1.txtDuplicateChk.value="OK";
        }
        if(res.indexOf('DUPLICATE.') > -1) {
            showMessage(ctl, "There are already max no of Accounts with this Identity.", true);
            document.createform1.txtDuplicateChk.value="DUPLICATE";
        }
        if(res.indexOf('BLOCKED.') > -1) {
            showMessage(ctl, "Sale not allowed on this customer ID.", true);
            document.createform1.txtDuplicateChk.value="BLOCKED";
        }
    }
}
    </script>
    
<script language='javascript'>
var templateMsg ="<table border=0 width=300 height=100 cellpadding=0 cellspacing='0'  class='shadow-box-inner.bill-listing'><tr height=40><td align=center valign=middle><font color=#FFFFFF>[MsgToShow]</font></td></tr><tr height=40><td valign=middle align=center><img src='images/wtbLoading.gif'></td</tr></table>";
function showprogess(callFrom) {

	move_center_center(); 
	var displaymsg = '';
	if(callFrom == 'IDENTITY') { 
		displaymsg = templateMsg.replace("[MsgToShow]", "Identity verification is in progress..");
	}
	if(callFrom == 'DEVICE') { 
		displaymsg = templateMsg.replace("[MsgToShow]", "Validating mac address. Please wait..");
	}
	if(callFrom == 'COVERAGE') { 
		displaymsg = templateMsg.replace("[MsgToShow]", "Checking coverage is in progress..");
	}
	document.getElementById('progressbar').innerHTML = displaymsg;
	document.getElementById('progressbar').style.visible="block";
	document.getElementById('progressbar').style.visibility="visible";
	
	}
function hideprogess() {
	document.getElementById('progressbar').style.visible="none";
	document.getElementById('progressbar').style.visibility="hidden";
}

</script>
    <body>
        <div id="content">
                          <div id="inner-content">
                               <div class="shadow-box">
                                    <div class="shadow-box-inner">
                                        <form name="createform1" id="createform1"  method=post action="./LeadAccountCreationNextAction.do" onsubmit="javascript: return validateNewAccountForm(this)">
                                            <table width="90%" border="0" cellspacing="0" cellpadding="0" class="Act-listing">
                                                <tr class='info-listing'><td width="25%"><label>Sales Id</label></td><td colspan=3><label><%=cSalesID%></label><input type="hidden" readonly name="<%=SALES_ID%>" value="<%=cSalesID%>"></td></tr>
						<tr height=25><th colspan=4 align=center>Personal Information</th></tr>
                                                <tr  class='info-listing'><td width=35%><label>Title<SPAN class=mandatory>*</SPAN></label></td><td colspan=3>
                                                    <select class="secectxx" name=<%=SALUTE%>>
                                                            <% 
                                                            {
    String titles[] =props.getProperty("consumerSalutation").split("\\|");
    String salutation = cSalutation;//(String) lcSalutation.getLightData();
    if (salutation == null) salutation="Mr.";
    for (int i=0;i<titles.length;i++) {
        if (salutation.equals(titles[i])) { %>
                                                            <OPTION SELECTED><%=titles[i]%>
                                                                         <% } else { %>
                                                                         <OPTION><%=titles[i]%>
                                                            <% }
                                                            }
                                                            } %>
                                                            
                                                </select></td></tr>
                                                <tr  class='info-listing'><td width=100><label>First Name<SPAN class=mandatory>*</SPAN></label></td><td colspan=3><input class="txtxxx" type=text name="<%=FIRSTNAME%>" value="<%=cFirstName%>" id="FirstName" maxlength="25"><P class=validationmsg></P></td></tr>
                                                <tr  class='info-listing'><td><label>Middle Name</label></td><td colspan=3><input class="txtxxx"  type=text name="<%=MIDDLENAME%>" value="<%=cMiddleName%>" maxlength="25"><P class=validationmsg></P></td></tr>
                                                <tr  class='info-listing'><td><label>Last Name<SPAN class=mandatory>*</SPAN></label></td><td  colspan=3><input class="txtxxx"  type=text name="<%=LASTNAME%>" value="<%=cLastName%>" maxlength="25"><P class=validationmsg></P></td></tr>
                                                <tr  class='info-listing'><td><label>Service Address</label></td><td  colspan=3><%=cAddress.replace("   ",", ")%>, <%=cCity%>, <%=cState%>, <%=cCountry%> - <%=cZip%>.
                                                        <input type=hidden name="<%=ADDRESS1%>" value="<%=cAddress%>"> 
                                                        <input type=hidden name="<%=CITY%>" value="<%=cCity%>">
                                                        <input type=hidden name="<%=STATE%>" value="<%=cState%>">
                                                        <input type=hidden name="<%=COUNTRY%>" value="<%=cCountry%>">
                                                        <input type=hidden name="<%=ZIP%>" value="<%=cZip%>">
                                                </td></tr>
                                                <tr  class='info-listing'><td><label>Email<SPAN class=mandatory>*</SPAN></label></td><td  colspan=3><input class="txtxxx"  type=text name="<%=EMAIL%>" value="<%=cEmail%>" maxlength="50"><P class=validationmsg></P></td></tr>
                                                <tr ><th colspan=4>Contact No's </th></tr>
                                                <tr class='info-listing'><td><label>Home<span class="mandatory">&nbsp;*</span></label></td><td><input class="txtxx"  type=text name="<%=HOMEPHONE%>" value="" id="homephone" maxlength="25"><P class=validationmsg></P></td><td width=25%><label>Work</label></td><td><input class="txtxx"  type=text name="<%=WORKPHONE%>" id="workphone" maxlength="25" value=""><P class=validationmsg></P></td></tr>
                                                <tr class='info-listing'><td><label>Mobile<span class="mandatory">&nbsp;*</span></label></td><td><input class="txtxx" id="mobilephone"  type=text name="<%=MOBILE%>" maxlength="25" value="<%=cPhoneNo%>"><P class=validationmsg></P></td><td><label>Fax</label></td><td><input class="txtxx"  maxlength="25" id=faxphone type=text name="<%=FAX%>" value=""><P class=validationmsg></P></td></tr>
                                                <tr><th  colspan=4>Additional Information</th></tr>
                                                <tr class='info-listing'><td><label>Business Type<SPAN class=mandatory>*</SPAN></label></td><td colspan=3><select class=secectxx name=<%=WTB_BUS_TYPE%>>
                                                            <% if (cBusinessType.equals ("1")) { %>
                                                            <option value=1>Individual</option>
                                                            <% } %>
                                                            <% if (cBusinessType.equals ("3")) { %>
                                                            <option value=3>Affinity Child</option>
                                                            <% } %>
                                                            <% if (cBusinessType.equals ("4")) { %>
                                                            <option value=4>Affinity</option>
                                                            <% } %>
                                                            
                                                            <!--<option value=2>Corporate</option>
							    <option value=3>SOHO</option>
							    <option value=4>SME</option> -->
                                                            <% if (cBusinessType.equals ("5")) { %>
							    <option value=5>FUT</option>
                                                            <% } %>
                                                            <% if (cBusinessType.equals ("8")) { %>
							    <option value=5>Employee</option>
                                                            <% } %>
                                                            <% if (cBusinessType.equals ("9")) { %>
							    <option value=5>Test</option>
                                                            <% } %>
                                                </select></td></tr>
                                                <script language=javascript>
                                                    function changeidentitylabel(sel) {
                                                         document.createform1.txtDuplicateChk.value="";
                                                         document.createform1.<%=IDENTITY%>.value="";
                                                        if(sel.options[sel.selectedIndex].value == "1") {
															
                                                         document.getElementById('wtb_org_id1').innerHTML = "<label>CNIC<SPAN class=mandatory>*</SPAN></label>";
                                                         }                                      else {
															
                                                         document.getElementById('wtb_org_id1').innerHTML = "<label>Passport<SPAN class=mandatory>*</SPAN></label>";
                                                         }
                                                    }
                                                </script>
						<%  if (cBusinessType.equals ("1")  || cBusinessType.equals ("5")  || cBusinessType.equals ("9")  || cBusinessType.equals ("3")  || cBusinessType.equals ("8")  ) {  %>
                                                <tr class='info-listing'><td><label>Nationality<span class="mandatory">*</span></label></td><td colspan="3">
                                                        <select name=nationality onchange='changeidentitylabel(this)'>
                                                            <option value=1>Pakistani</option>
                                                            <option value=2>Foreigner</option>
                                                            
                                                        </select>
                                                </td></tr><tr class='info-listing'><td><div id=wtb_org_id1><label>Identity<SPAN class=mandatory>*</SPAN></label></div></td>
                                                    <td colspan=2>
                                                        <input class="txtxx" type=text name="<%=IDENTITY%>" value="<%=cCNIC%>" maxlength="25" id="identity" onchange='document.createform1.txtDuplicateChk.value=""' onblur='checkDuplicateCNIC(createform1);'>
                                                        <input size=3 type=hidden name=txtDuplicateChk value='' ><P class=validationmsg></P><p>&nbsp;</p>
                                                        <div>(eg: CNIC/Passport No.)</div><p>&nbsp;</p>
                                                    </td><td><!--<input type=button name="btnChkCNIC" value="Verify Identity" onclick='checkDuplicateCNIC(createform1);'>  -->
                                                </td></tr>
                                                <% } else { %>
						<tr class='info-listing'><td><label>NTN<span class="mandatory">*</span></label></td><td colspan="3">
							<input  class="txtxx" type=text name="NTN" value="" maxlength="25"  onchange='document.createform1.txtDuplicateChk.value=""'  onblur='checkDuplicateCNIC(createform1);'><P class=validationmsg></P>
                                                </td></tr>
						<tr class='info-listing'><td><div id=wtb_org_id1><label>SUB_ID</label></div></td>
                                                    <td colspan=2>
						    <input type=text name=SUB_ID value="" maxlength=25 class="txtxx">
                                                        <input class="txtxx" type=text name="<%=IDENTITY%>" value="<%=cCNIC%>" maxlength="25" id="identity" onchange='document.createform1.txtDuplicateChk.value=""' onblur='checkDuplicateCNIC(createform1);'>
                                                        <input size=3 type=hidden name=txtDuplicateChk value='' ><p>&nbsp;</p>
                                                    </td><td><!--<input type=button name="btnChkCNIC" value="Verify Identity" onclick='checkDuplicateCNIC(createform1);'>  -->
                                                </td></tr>
                                                
						<% }  %>
                                                <tr class='info-listing'><td><label>Date of Birth<SPAN class=mandatory>*</SPAN></label></td><td colspan=3><input type="text" class=" date-pick dp-applied" name="<%=WTB_DOB%>" id="dob" maxlength="10"  /><P class=validationmsg></P></td></tr>
                                                <tr class='info-listing'>
                                                    <td><label>Job title</label></td>
                                                    <td colspan=3><input type="text" class="txtxxx" name="<%=TITLE%>"  id="title" maxlength="50" value="<%=cTitle%>" /><P class=validationmsg></P></td>
                                                </tr>
                                                <tr class='info-listing'>
                                                    <td ><label>Company</label></td>
                                                    <td colspan="3"><input type="text" class="txtxxx" name="<%=COMPANY%>"  Value="" id="CompanyName" maxlength="50" value="<%=cCompany%>" /><P class=validationmsg></P></td>
                                                </tr>
                                                <tr  class='info-listing'>
                                                    <td ><label>Referred By(i.e. 0.0.0.1-123)</label></td>
                                                    <td colspan="3"><input type="text" class="txtxx" name="ReferredBy1" Value="<%=request.getParameter("referredBy")%>" readonly id="CompanyName" maxlength="50" value=""  /><P class=validationmsg></P>
													<input type=hidden name="ReferredBy" value=''>
													</td>
                                                </tr>
												<tr class='info-listing'>
                                                    <td ><label>Payment method</label><SPAN class=mandatory>*</SPAN></td>
                                                    <td colspan="3">
                                                        <%
                                                        String paytypes[] =props.getProperty("consumerPaymentType").split("\\|");
							if(cServiceType.trim().equals(ACCOUNT_SERVICE_TYPE_PREPAID)) {
								paytypes = props.getProperty("consumerPrePaidPaymentType").split("\\|");
							} else {
								paytypes = props.getProperty("consumerPostPaidPaymentType").split("\\|");
							}
                                                        %>
                                                        
                                                        <select class=secectxx name="<%=BILL%>" onchange='PaymentMethodChange(this,document.createform1.<%=WTB_ACCT_SERVICE_TYPE%>1,document.createform1.<%=WTB_ACCT_SERVICE_TYPE%>)'>
                                                            <option value="">Select payment method</option>
                                                            <%
                                                                for(int i=0;i<paytypes.length;i++) {
                                                                    String sTemp = paytypes[i];
                                                                    String sOptVal = sTemp.substring(0,sTemp.indexOf("#"));
                                                                    String sOptText = sTemp.substring(sTemp.indexOf("#")+1);
                                                                    boolean blDisplaySelected = false;
                                                                    if(cServiceType.trim().equals(ACCOUNT_SERVICE_TYPE_PREPAID) && sOptVal.trim().equals(PREPAID) ) {
                                                                        blDisplaySelected = true;
                                                                    }
                                                                    if(cServiceType.trim().equals(ACCOUNT_SERVICE_TYPE_POSTPAID) && sOptVal.trim().equals(INVOICE) ) {
                                                                        blDisplaySelected = true;
                                                                    }
                                                                    
                                                                        
                                                            %>
                                                                <option <%=(blDisplaySelected) ? " selected " : "" %>  value="<%=sOptVal%>"><%=sOptText%></option>
                                                            <%
                                                                }

                                                            %>
		
           
                                                    </select><P class=validationmsg></P></td>
                                                </tr>
                                                </tr>
                                                <tr  class='info-listing'>
                                                <td><label>Payment Alerts</label></td>
                                                <td colspan=2><select name="slsubscribe">
                                                        <option selected value='0'>Un-Subscribe</option>
                                                        <option value='1'>Subscribe</option>
                                                            </select>
                                                </td>
                                                </tr>   
                                                <tr height=30><th colspan=4 >Package </th></tr>
                                                <% if(cBusinessType.equals("4") ) {
                                                        List lstAffinitySlabs = wsutil.getAffinitySlabList();
                                                            if ((lstAffinitySlabs != null ) && lstAffinitySlabs.size() > 0 ) {
                                                            %>
                                                <tr class="info-listing">
                                                    <td><label>Select Affinity Slab<span class=mandatory>&nbsp;*</span></label></td>
                                                    <td colspan="3">
                                                        <select class=txtxx name="lstAffinitySlab">
                                                            <% 
                                                                String strslab = "";
                                                                for(int i=0; i< lstAffinitySlabs.size(); i++) {
                                                                    strslab = "";
                                                                    strslab = (String)((FList)lstAffinitySlabs.get(i)).getValues().nextElement();
                                                                    out.print( "<option value='" + strslab + "'>" + strslab + "</option>"); 
                                                                }
                                                            %>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <% } } %>
                                                <tr  class='info-listing'><script language=javascript>
                                                    
                                                    function updatepkgdetails(sel) {
                                                        if(sel.value != "") {    
                                                            document.createform1.<%=PROMO_PKG%>.value=sel.value;
                                                        
                                                            var period = parseInt(sel.value);
                                                            if(!IsNumber(sel.value)) {
                                                                showMessage(sel,'Please Enter only integer value');
                                                                document.createform1.contract_end.value = "";
                                                                return(false);
                                                            }
                                                            
                                                            var dt= Date.fromString(document.createform1.<%=CONTRACT_START%>.value);
                                                            document.createform1.<%=CONTRACT_START%>.value = dt.asString();
                                                            document.createform1.<%=CONTRACT_END%>.value = dt.addMonths(period).asString();
                                                       }
                                                       else {
                                                            document.createform1.<%=PROMO_PKG%>.value = "";
                                                            document.createform1.<%=CONTRACT_END%>.value = "";
                                                       }
                                                    }
                                                    </script><P class=validationmsg></P>
                                                    <td ><label>Contract Period<SPAN class=mandatory>*</SPAN></label></td>
                                                    <td colspan="3"><select class=txtxx name="<%=CONTRACT_PERIOD%>" onchange='updatepkgdetails(this)' >
                                                            <option value="">Select Contract Period</option>
                                                        <%

                                                            if(lstContractPeriod != null) {
                                                                if(lstContractPeriod.size() > 0) {
                                                                    for(int i=0; i< lstContractPeriod.size(); i++) {
                                                                        String sTemp = ((FList)lstContractPeriod.get(i)).get(WtbFldContractPeriod.getInst()).toString (); //get(WtbFldContractPeriod.getInst());
                                                                        if(wsutil.getDefaultContractPeriod() == Integer.parseInt(sTemp)) {
                                                                            out.print("<option selected value='" + sTemp + "'>" + sTemp + " Months</option>");
                                                                        } else {
                                                                            out.print("<option value='" + sTemp + "'>" + sTemp + " Months</option>");
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            %>
                                                    </select> <P class=validationmsg></P></td>                                                    </td>
                                                </tr>
                                                <tr   class='info-listing'>
                                                    <td ><label>Start Date</label></td>
                                                    <td ><input type=hidden name="<%=PROMO_PKG%>" value=""><input type=textbox  class=txtxx  name="<%=CONTRACT_START%>" readonly value="<%=formatter.format(wsutil.getServerTime())%>"></td>
                                                    <td ><label>End Date</label></td>
                                                    <td ><input type=textbox  class=txtxx  name="<%=CONTRACT_END%>" readonly value=""><P class=validationmsg></P></td>
                                                    <!--added by muralidhar -->
                                                    <input type=hidden name="<%=WTB_CLIENT_APP_NAME%>" value="LSLP">
                                                </tr>
                                               
                                                <tr   class='info-listing'><td valign=top><label>Service Login Type<SPAN class=mandatory>*</SPAN></label></td>
                                                    <td colspan=3><select name="<%=SVS_LOGIN_TYPE%>">
                                                                        <option value="">Select Service Login Type</option>
                                                                        <% String[] svsLoginTypes = props.getProperty("consumerServiceLoginType").split("\\|");
                                                                            for(int i=0;i<svsLoginTypes.length;i++) {
                                                                                String sTemp = svsLoginTypes[i];
                                                                                String sOptVal = sTemp.substring(0,sTemp.indexOf("#"));
                                                                                String sOptText = sTemp.substring(sTemp.indexOf("#")+1);
                                                                        %>   
                                                                        <option value="<%=sOptVal%>"><%=sOptText%></option>
                                                                        <%
                                                                            }
                                                                        %>
                                                        </select>
                                                          <P class=validationmsg></P>      
                                                    </td>
                                                </tr>
                                                <tr id="taxrow1" height=30><th colspan=4 >Taxation </th></tr>
                                                <tr  id="taxrow2"  class='info-listing'>
                                                    <td ><label>Tax Exempt Type</label></td>
                                                    <td colspan="3"><table><tr>
                                                                <td colspan=3><select name="<%=TAX_EXPT_TYPE%>">
                                                                        <option value="" > Select Tax Exempt type </option>
                                                                        <option value="WHT">WHT</option>
                                                                        <option value="FED">FED</option>
                                                                        <option value="DUAL">BOTH</option>
                                                                </select> 
                                                                    </td>
                                                        </tr></table>
                                                    <P class=validationmsg></P></td>
                                                </tr>
                                                <tr  id="taxrow3"  class='info-listing'>
                                                <td ><label>Tax Exempt(%)<SPAN class=mandatory>*</SPAN></label></td>
                                                <td colspan="3"><input type=text maxlength="5" class="txtxx" name="<%=TAX_EXPT_PERCENT%>" Value="0.00"><P class=validationmsg></P></td><tr>
                                                    <script language=javascript>
                                                       function showme(chk) {
	document.getElementById('ParentAccontDetails').innerHTML = "";
	if(chk.checked == true) {
		ActCreation = window.open("NewAccount_Child.jsp?busType=<%=cBusinessType.trim()%>","ActCreation", "menubar=0,resizable=0,scrollbars=1,width=650,height=450");
                ActCreation.moveTo(100,100);
                ActCreation.focus();

	} else {
                document.createform1.payee.value="";
                document.createform1.parentpoid.value="";
				document.createform1.lstAffinitySlab.value="";
                ActCreation.close();
	}
}

function callbyChild(parentpoid, payee, parentslab, Accountname) {
    if(payee == "" || parentpoid == "" ) {
        document.createform1.payee.value="";
        document.createform1.parentpoid.value="";
        document.createform1.lstAffinitySlab.value="";
		document.getElementById('ParentAccontDetails').innerHTML = "";
        document.createform1.child.checked = false;
    } else {
        document.createform1.payee.value=payee;
        document.createform1.parentpoid.value=parentpoid;
        document.createform1.lstAffinitySlab.value=parentslab;
		document.getElementById('ParentAccontDetails').innerHTML = "<SPAN class=display>Parent Account : " + Accountname + "(" + parentpoid + ")</SPAN>";
    }
    
}
                                                    </script>
                                                    <% if(!cBusinessType.equals("4") ) { %>
                                                    <tr id="taxrow3" class="info-listing">
                                                <td ><input type="checkbox" name=chkSelectParent onchange="showme(this)" name="child" /><P class=validationmsg></P> </td>
                                                <td> <label>This Account Has Parent<br>
	<div id=ParentAccontDetails><span class=display></span></div></label><input type="hidden" name="parentpoid" value="" ><input type="hidden" name="payee" value=""><input type="hidden" name=lstAffinitySlab value=""> </td> </tr>    
                                                <% } %>
                                                <tr height=30><th colspan=3 >Purchase Plan(Plan List) <div id=divnumberofplans></div></th><th><select disabled class=secectxx name="<%=WTB_ACCT_SERVICE_TYPE%>1" style="visibility:hidden">
                                                            <option <%=(cServiceType.equals("1") ? " selected " : "") %> value=1>Pre-Paid</option>
                                                            <option  <%=(cServiceType.equals("2") ? " selected " : "") %> value=2>Post paid</option>
                                                </select><input  type=hidden name="<%=WTB_ACCT_SERVICE_TYPE%>" value="<%=cServiceType%>"></th></tr>
                                                <tr   class='info-listing'><td>&nbsp;</td><td colspan=3>
                                                    
                                                <div id="divPlanlist" style="OVERFLOW: auto;WIDTH: 400px;HEIGHT: 147px" onscroll="OnDivScroll();">
                                                    <select id="lstPlanList" name="<%=WTB_PLAN_SEL%>" size=5 width=100%>
                                                </select></div>
<script language=javascript>
     function OnDivScroll()
 {
    var lstPlanList = document.getElementById("lstPlanList");
    if (lstPlanList.options.length > 8)
    {
        lstPlanList.size=lstPlanList.options.length;
    }
    else
    {
        lstPlanList.size=8;
    }
}
var planArray = new Array();
<%
List plList = plansBean.getPlans();
if(plList != null) {
    for(int i=0;i<plList.size();i++) {
    String sTempPlanName = (String)plList.get(i);
    if(sTempPlanName.indexOf("[") > -1 ) {
        sTempPlanName = sTempPlanName.substring(0,sTempPlanName.indexOf("["));
        }
%>
planArray[<%=i%>] = new Array("<%=String.valueOf(i)%>","<%=sTempPlanName%>");
<%
    }
}


%>
var oldServicetype = "";
var numberofplans = 0;
function updatePlans() {
	if(document.createform1.business_type.options[document.createform1.business_type.selectedIndex].value != "5") {
		if(oldServicetype != document.createform1.accountservicetype.value || oldServicetype== "") {
			while(document.createform1.planselected.length > 0) {
				document.createform1.planselected.remove(0);
				numberofplans =numberofplans -1;
			}

			for(i=0; i< planArray.length;i++) {
				var opt = new Option();
				var str = planArray[i];
				var filter1 = document.createform1.accountservicetype.value;
				if(filter1 == "1") 
					filter1 = "PREPAID";
				else 
					filter1 = "POSTPAID";
				if(str[1].toUpperCase().indexOf(filter1) > -1) {
						opt.value = str[0];
						opt.text = str[1];
                        
						document.createform1.planselected.options.add(opt);
						numberofplans = numberofplans + 1;
				}
			}
			oldServicetype = document.createform1.accountservicetype.value;
			document.createform1.planselected.selectedIndex = 0;
		}
		else {
        
		}
	}
	else {
		loadFUTPlanList();
	}
	if(numberofplans < 1) 
	{
		
		document.getElementById('divnumberofplans').innerHTML = "No plans available";
	}else {
		
		document.getElementById('divnumberofplans').innerHTML = "";
	}
}

function loadFUTPlanList() {
	if(oldServicetype != document.createform1.accountservicetype.value || oldServicetype== "") {
			while(document.createform1.planselected.length > 0) {
				document.createform1.planselected.remove(0);
				numberofplans = numberofplans - 1;
			}
	for(i=0; i< planArray.length;i++) {
		var opt = new Option();
		var str = planArray[i];
		opt.value = str[0];
		opt.text = str[1];
		document.createform1.planselected.options.add(opt);
		numberofplans = numberofplans + 1;
	}
	oldServicetype = document.createform1.accountservicetype.value;
	document.createform1.planselected.selectedIndex = 0;
	}
}
function PaymentMethodChange(sel,sel1,txt)
{
	if(sel.options[sel.selectedIndex].value == '<%=PREPAID%>')
	{
		// Prepaid
		sel1.selectedIndex=0;
                txt.value=1;
                document.getElementById("taxrow1").style.display="none";
                document.getElementById("taxrow2").style.display="none";
                document.getElementById("taxrow3").style.display="none";
	}
	else
	{
		sel1.selectedIndex=1;
                txt.value=2;
                document.getElementById("taxrow1").style.display="";
                document.getElementById("taxrow2").style.display="";
                document.getElementById("taxrow3").style.display="";
	}
        updatePlans();
}


</script>
                                                </td></tr>
                                                <tr   class='info-listing'><td colspan=4><!-- hidden fields -->

                                                        <INPUT Type="hidden" Name="referrer" Value="<%=STEP1%>">
                                                        <INPUT Type="hidden" Name="brand" Value="<%=brand%>">
                                                        <input type="hidden" name="Deliver_method" value="1"><!-- 0-Email &&& 1-Postal --> 
                                                        <INPUT Type="hidden" Name="<%=CURRENCY%>" Value="<%=props.getProperty(CURRENCYCODE)%>">
                                                        <INPUT Type="hidden" Name="<%=AACACCESS%>" Value="<%=props.getProperty(NEWACCOUNTAPPLICATION)%>">
                                                        <input type="hidden" name="<%=COVERAGE_TYPE%>" value="<%=cCoveragetype%>">
                                                        <input type="hidden" name="<%=LONGITUDE%>" value="<%=cLongitude%>">
                                                        <input type="hidden" name="<%=LATITUDE%>" value="<%=clatitude%>">
                                                        <input type="hidden" name="<%=SLA%>" value="<%=cSLA%>">
							<input type="hidden" name="<%=LEAD_IDENTITY%>" value="<%=cLeadId%>">
							<input type="hidden" name="<%=NEW_ACCT_BY%>" vlaue="<%=reffrom%>">
                                                        </td></tr>
                                                <tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th >
                                                        <input type="image" name="submit" src="images/btn-go.gif" alt="Request Change" title="Request Change" 
                                                               onmouseover="this.src='images/btn-go-on.gif'" onmouseout="this.src='images/btn-go.gif'" />
                                                </th></tr>
                                                <tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th >&nbsp;</th></tr>
                                            </table>
                                        </form>
                                        <script language=javascript>
                                            updatePlans();
                                            <% if(cServiceType.trim().equals(ACCOUNT_SERVICE_TYPE_PREPAID)) {   %>
                                                    document.getElementById("taxrow1").style.display="none";
                                                    document.getElementById("taxrow2").style.display="none";
                                                    document.getElementById("taxrow3").style.display="none";
                                            <% } else { %>   
                                                    document.getElementById("taxrow1").style.display="";
                                                    document.getElementById("taxrow2").style.display="";
                                                    document.getElementById("taxrow3").style.display="";
                                            <% } %>
                                            <% if(wsutil.getDefaultContractPeriod()  > 0) {%>
                                                updatepkgdetails(document.createform1.<%=CONTRACT_PERIOD%>);
                                            <% } %>
                                        </script>
                                    </div>
                                </div>
                                <!-- Inner content ends here -->                        
                            </div>
                        </div>
                    </div>
                </div>
            
	<! --  Div layer for  Progress -->
<div id="progressbar" style="width: 300px; height: 100px; align:center; position: absolute; z-index:10; left:150px; right:200px; -moz-opacity:10; filter: alpha(opacity=100); background:#c60651; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; visibility: hidden">
</div> 
<!--  Div Layer for Progress - end -->

<script type="text/javascript"><!--
var floatingMenuId = 'progressbar';
var floatingMenu =
{
    targetX: -250,
    targetY: 10,

    hasInner: typeof(window.innerWidth) == 'number',
    hasElement: typeof(document.documentElement) == 'object'
        && typeof(document.documentElement.clientWidth) == 'number',

    menu:
        document.getElementById
        ? document.getElementById(floatingMenuId)
        : document.all
          ? document.all[floatingMenuId]
          : document.layers[floatingMenuId]
};

floatingMenu.move = function ()
{
    floatingMenu.menu.style.left = floatingMenu.nextX + 'px';
    floatingMenu.menu.style.top = floatingMenu.nextY + 'px';
}

floatingMenu.computeShifts = function ()
{
    var de = document.documentElement;

    floatingMenu.shiftX =  
        floatingMenu.hasInner  
        ? pageXOffset  
        : floatingMenu.hasElement  
          ? de.scrollLeft  
          : document.body.scrollLeft;  
    if (floatingMenu.targetX < 0)
    {
        floatingMenu.shiftX +=
            floatingMenu.hasElement
            ? de.clientWidth
            : document.body.clientWidth;
    }

    floatingMenu.shiftY = 
        floatingMenu.hasInner
        ? pageYOffset
        : floatingMenu.hasElement
          ? de.scrollTop
          : document.body.scrollTop;
    if (floatingMenu.targetY < 0)
    {
        if (floatingMenu.hasElement && floatingMenu.hasInner)
        {
            // Handle Opera 8 problems
            floatingMenu.shiftY +=
                de.clientHeight > window.innerHeight
                ? window.innerHeight
                : de.clientHeight
        }
        else
        {
            floatingMenu.shiftY +=
                floatingMenu.hasElement
                ? de.clientHeight
                : document.body.clientHeight;
        }
    }
}

floatingMenu.calculateCornerX = function()
{
    if (floatingMenu.targetX != 'center')
        return floatingMenu.shiftX + floatingMenu.targetX;

    var width = parseInt(floatingMenu.menu.offsetWidth);

    var cornerX =
        floatingMenu.hasElement
        ? (floatingMenu.hasInner
           ? pageXOffset
           : document.documentElement.scrollLeft) + 
          (document.documentElement.clientWidth - width)/2
        : document.body.scrollLeft + 
          (document.body.clientWidth - width)/2;
    return cornerX;
};

floatingMenu.calculateCornerY = function()
{
    if (floatingMenu.targetY != 'center')
        return floatingMenu.shiftY + floatingMenu.targetY;

    var height = parseInt(floatingMenu.menu.offsetHeight);

    // Handle Opera 8 problems
    var clientHeight = 
        floatingMenu.hasElement && floatingMenu.hasInner
        && document.documentElement.clientHeight 
            > window.innerHeight
        ? window.innerHeight
        : document.documentElement.clientHeight

    var cornerY =
        floatingMenu.hasElement
        ? (floatingMenu.hasInner  
           ? pageYOffset
           : document.documentElement.scrollTop) + 
          (clientHeight - height)/2
        : document.body.scrollTop + 
          (document.body.clientHeight - height)/2;
    return cornerY;
};

floatingMenu.doFloat = function()
{
    var stepX, stepY;

    floatingMenu.computeShifts();

    var cornerX = floatingMenu.calculateCornerX();

    var stepX = (cornerX - floatingMenu.nextX) * .07;
    if (Math.abs(stepX) < .5)
    {
        stepX = cornerX - floatingMenu.nextX;
    }

    var cornerY = floatingMenu.calculateCornerY();

    var stepY = (cornerY - floatingMenu.nextY) * .07;
    if (Math.abs(stepY) < .5)
    {
        stepY = cornerY - floatingMenu.nextY;
    }

    if (Math.abs(stepX) > 0 ||
        Math.abs(stepY) > 0)
    {
        floatingMenu.nextX += stepX;
        floatingMenu.nextY += stepY;
        floatingMenu.move();
    }

    setTimeout('floatingMenu.doFloat()', 20);
};

// addEvent designed by Aaron Moore
floatingMenu.addEvent = function(element, listener, handler)
{
    if(typeof element[listener] != 'function' || 
       typeof element[listener + '_num'] == 'undefined')
    {
        element[listener + '_num'] = 0;
        if (typeof element[listener] == 'function')
        {
            element[listener + 0] = element[listener];
            element[listener + '_num']++;
        }
        element[listener] = function(e)
        {
            var r = true;
            e = (e) ? e : window.event;
            for(var i = element[listener + '_num'] -1; i >= 0; i--)
            {
                if(element[listener + i](e) == false)
                    r = false;
            }
            return r;
        }
    }

    //if handler is not already stored, assign it
    for(var i = 0; i < element[listener + '_num']; i++)
        if(element[listener + i] == handler)
            return;
    element[listener + element[listener + '_num']] = handler;
    element[listener + '_num']++;
};

floatingMenu.init = function()
{
    floatingMenu.initSecondary();
    floatingMenu.doFloat();
};

// Some browsers init scrollbars only after
// full document load.
floatingMenu.initSecondary = function()
{
    floatingMenu.computeShifts();
    floatingMenu.nextX = floatingMenu.calculateCornerX();
    floatingMenu.nextY = floatingMenu.calculateCornerY();
    floatingMenu.move();
}

if (document.layers)
    floatingMenu.addEvent(window, 'onload', floatingMenu.init);
else
{
    floatingMenu.init();
    floatingMenu.addEvent(window, 'onload',
        floatingMenu.initSecondary);
}

//--></script>
<script type="text/javascript"><!--
function move_upper_left()
{
floatingMenu.targetX=10;
floatingMenu.targetY=10;
}

function move_upper_center()
{
floatingMenu.targetX='center';
floatingMenu.targetY=10;
}

function move_upper_right()
{
floatingMenu.targetX=-250;
floatingMenu.targetY=10;
}

function move_center_left()
{
floatingMenu.targetX=10;
floatingMenu.targetY='center';
}

function move_center_center()
{
floatingMenu.targetX='center';
floatingMenu.targetY='center';
}

function move_center_right()
{
floatingMenu.targetX=-250;
floatingMenu.targetY='center';
}

function move_lower_left()
{
floatingMenu.targetX=10;
floatingMenu.targetY=-110;
}

function move_lower_center()
{
floatingMenu.targetX='center';
floatingMenu.targetY=-110;
}

function move_lower_right()
{
floatingMenu.targetX=-250;
floatingMenu.targetY=-110;
}
move_center_center(); 
//--></script>
    </body>
</html>
