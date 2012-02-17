<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" import="com.witribe.ctutil.*, customfields.*, com.portal.bas.*, com.portal.bas.comp.*, com.portal.pcm.* , com.portal.app.ccare.comp.*, Wtb.MyAccount.*, com.portal.web.comp.*, com.portal.web.*, java.net.*, java.util.*, java.text.*"   errorPage="error.jsp"  %>
<%@ include file="includes/constants.jsp"%>
<%@ include file="includes/ServiceConstants.jsp"%>
<%
// ***********************My Account related code*********************************
String reffrom = request.getParameter("salesweb");
String urlFrom = request.getHeader("referer");
if (reffrom == null)
    reffrom = "";

//Only User can come from webportal can access this page.
if(reffrom.equals("DEALER")){
//
} else {
    response.sendRedirect("index.jsp");
}
// *********************** My Account code ends here *****************************
%>
<jsp:useBean id="accountCreationBean" class="com.portal.bas.comp.PIAComponentCollection" scope="session"/>
<%
String windowTitle = WINDOW_TITLE;
String welcomeName = "Guest";

String sectionImage = "text-login.gif"; //Billing: text-billing1.gif ;  MyAccount: text-my-account.gif
%>
<%
// ***********************My Account related code*********************************
//Page level local variables

String endpointString = "";
String strHouseNo = "";
String strStreet = "";
String strZone = "";
String strCity = "";
String strTask = "";

String sResult1 = "";
String sResult2 = "";
String strLongitude = "";
String strLatitude = "";
String covStatus = "";
String sla = "";
String scoOrdinates = "";
double x=0.00;
double y=0.00;
String sx = "";
String sy = "";
boolean proceed = false;
double usbCoverage = 0.0;
double indoorCoverage = 0.0;
double outdoorCoverage = 0.0;
String susbCoverage = "";
String sindoorCoverage = "";
String soutdoorCoverage = "";
String arrCoOrdinates[] = null;
String arrResult2[] = null;
String cvgType = "";
String result;
String sCVGStatus = "";
String dCVGStatus = "";
// ***********************My Account related code*********************************
PLightComponentHelper lcSalutation, lcFirstName, lcMiddleName, lcLastName, lcSvcAddress, lcSvcCity, lcSvcState, lcSvcZip, lcSvcCountry, lcSvcEmail, lcCNIC, lcDOB;
PLightComponentHelper lcHomePhone, lcWorkPhone, lcFaxPhone, lcMobilePhone;
PLightComponentHelper lcBusinessType, lcTitle, lcOrganization, lcBillType, lcActSvsType;
PLightComponentHelper lcCoverageType, lcLatitude, lcLongitude, lcSLA;
PLightComponentHelper lcContractPeriod, lcProPackage, lcContractStart, lcContractEnd,  lcActUsageLimit, lcSvsLoginType, lcTaxExptType, lcTaxExcptPercent, lcWtbActSvsType, lcSelPlan ;

String strNationality = "";

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
    
    lcSalutation = ServletUtil.addComponent(accountCreationBean, SALUTE, "FldNameinfo[1].FldSalutation");
    lcFirstName= ServletUtil.addComponent(accountCreationBean, FIRSTNAME, "FldNameinfo[1].FldFirstName");
    lcMiddleName = ServletUtil.addComponent(accountCreationBean, MIDDLENAME, "FldNameinfo[1].FldMiddleName");
    lcLastName = ServletUtil.addComponent(accountCreationBean, LASTNAME, "FldNameinfo[1].FldLastName");
    lcSvcAddress = ServletUtil.addComponent(accountCreationBean, ADDRESS1, "FldNameinfo[1].FldAddress");
    lcSvcCity = ServletUtil.addComponent(accountCreationBean, CITY, "FldNameinfo[1].FldCity");
    lcSvcState = ServletUtil.addComponent(accountCreationBean, STATE, "FldNameinfo[1].FldState");
    lcSvcCountry = ServletUtil.addComponent(accountCreationBean, COUNTRY, "FldNameinfo[1].FldCountry");
    lcSvcZip = ServletUtil.addComponent(accountCreationBean, ZIP, "FldNameinfo[1].FldZip");
    lcSvcEmail = ServletUtil.addComponent(accountCreationBean, EMAIL, "FldNameinfo[1].FldEmailAddr");
    lcHomePhone = ServletUtil.addComponent(accountCreationBean, HOMEPHONE,"FldNameinfo[1].FldPhones[1].FldPhone");
    lcWorkPhone = ServletUtil.addComponent(accountCreationBean, WORKPHONE,"FldNameinfo[1].FldPhones[2].FldPhone");
    lcFaxPhone = ServletUtil.addComponent(accountCreationBean, FAX,"FldNameinfo[1].FldPhones[3].FldPhone");
    lcMobilePhone = ServletUtil.addComponent(accountCreationBean, MOBILE,"FldNameinfo[1].FldPhones[5].FldPhone");
    
// Business Type
    lcBusinessType = ServletUtil.addComponent(accountCreationBean, WTB_BUS_TYPE,"FldAcctinfo[0].FldBusinessType");
//CNIC & Passport No
    lcCNIC = ServletUtil.addComponent(accountCreationBean, IDENTITY,"FldAcctinfo[0].FldAccessCode1");
//Date of Birth
    lcDOB = ServletUtil.addComponent(accountCreationBean, WTB_DOB, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldDobT","FldProfileObj.WtbFldCustinfo.WtbFldDobT");
//Occupation
    lcTitle = ServletUtil.addComponent(accountCreationBean, TITLE, "FldNameinfo[1].FldTitle");
//Organization
    lcOrganization = ServletUtil.addComponent(accountCreationBean, COMPANY, "FldNameinfo[1].FldCompany");
//Payment method
    lcBillType = ServletUtil.addComponent(accountCreationBean, BILL, "FldBillinfo[0].FldBillType");
//ContractPeriod , Start Date, End date
    lcContractPeriod = ServletUtil.addComponent(accountCreationBean, CONTRACT_PERIOD, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractPeriod","FldProfileObj.WtbFldContractDetails.WtbFldContractPeriod");
    lcContractStart = ServletUtil.addComponent(accountCreationBean, CONTRACT_START, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractStartT","FldProfileObj.WtbFldContractDetails.WtbFldContractStartT");
    lcContractEnd = ServletUtil.addComponent(accountCreationBean, CONTRACT_END, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractEndT","FldProfileObj.WtbFldContractDetails.WtbFldContractEndT");
//Package
    lcProPackage = ServletUtil.addComponent(accountCreationBean, PROMO_PKG,"FldAcctinfo[0].FldAacPackage");
//Service Login type
    lcSvsLoginType = ServletUtil.addComponent(accountCreationBean, SVS_LOGIN_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldServiceLoginType","FldProfileObj.WtbFldCustinfo.WtbFldServiceLoginType");
//Taxation
    lcTaxExptType = ServletUtil.addComponent(accountCreationBean, TAX_EXPT_TYPE, "FldProfiles[2].FldInheritedInfo.WtbFldTaxExemptions[0].WtbFldExemptionType","FldProfileObj.WtbFldTaxExemptions[any].WtbFldExemptionType");
    lcTaxExcptPercent = ServletUtil.addComponent(accountCreationBean, TAX_EXPT_PERCENT, "FldProfiles[2].FldInheritedInfo.WtbFldTaxExemptions[0].WtbFldPercent","FldProfileObj.WtbFldTaxExemptions[any].WtbFldPercent");
// account type
    lcWtbActSvsType = lcActSvsType = ServletUtil.addComponent(accountCreationBean, WTB_ACCT_SERVICE_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldAccountServiceType","FldProfileObj.WtbFldCustinfo.WtbFldAccountServiceType");
    
    ServletUtil.addComponent(accountCreationBean, AACACCESS,"FldAacAccess");
    ServletUtil.addComponent(accountCreationBean, LOCALE, "FldLocales[1].FldLocale");
    ServletUtil.addComponent(accountCreationBean, CURRENCY, "FldBillinfo[0].FldCurrency","FldBillinfo[0].FldCurrency");
    
    ServletUtil.addComponent(accountCreationBean, INVNAME, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldName", "FldPayinfoObj.FldInvInfo[0].FldName");
    ServletUtil.addComponent(accountCreationBean, DELIVERMETHOD, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldDeliveryPrefer", "FldPayinfoObj.FldInvInfo[0].FldDeliveryPrefer");
    ServletUtil.addComponent(accountCreationBean, INVADDRESS1, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldAddress", "FldPayinfoObj.FldInvInfo[0].FldAddress");
    ServletUtil.addComponent(accountCreationBean, INVCITY, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldCity", "FldPayinfoObj.FldInvInfo[0].FldCity");
    ServletUtil.addComponent(accountCreationBean, INVSTATE, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldState", "FldPayinfoObj.FldInvInfo[0].FldState");
    ServletUtil.addComponent(accountCreationBean, INVZIP, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldZip", "FldPayinfoObj.FldInvInfo[0].FldZip");
    ServletUtil.addComponent(accountCreationBean, INVCOUNTRY, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldCountry", "FldPayinfoObj.FldInvInfo[0].FldCountry");
    ServletUtil.addComponent(accountCreationBean, INVEMAIL, "FldPayinfo[1].FldInheritedInfo[0].FldInvInfo[0].FldDeliveryDescr", "FldPayinfoObj.FldInvInfo[0].FldDeliveryDescr");
    
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
    lcCoverageType = ServletUtil.addComponent(accountCreationBean, COVERAGE_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtCoverageType","FldProfileObj.WtbFldCustinfo.WtbFldCtCoverageType");
    ServletUtil.addComponent(accountCreationBean, LEAD_IDENTITY, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldLeadIdentity","FldProfileObj.WtbFldCustinfo.WtbFldLeadIdentity");
    
    lcLatitude	= ServletUtil.addComponent(accountCreationBean, LATITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLatitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLatitude");
    lcLongitude = ServletUtil.addComponent(accountCreationBean, LONGITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLongitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLongitude");
    lcSLA = ServletUtil.addComponent(accountCreationBean, SLA, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtSla","FldProfileObj.WtbFldCustinfo.WtbFldCtSla");
    ServletUtil.addComponent(accountCreationBean, SALES_ID, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldSalesId","FldProfileObj.WtbFldCustinfo.WtbFldSalesId");
    ServletUtil.addComponent(accountCreationBean, CPE_DEVICE_ID, "FldDevices[0].FldDeviceObj");
} else {
    
    lcSalutation = accountCreationBean.getChild(SALUTE);
    lcFirstName = accountCreationBean.getChild(FIRSTNAME);
    lcMiddleName = accountCreationBean.getChild(MIDDLENAME);
    lcLastName = accountCreationBean.getChild(LASTNAME);
    lcSvcAddress = accountCreationBean.getChild(ADDRESS1);
    lcSvcCity = accountCreationBean.getChild(CITY);
    lcSvcState = accountCreationBean.getChild(STATE);
    lcSvcZip = accountCreationBean.getChild(ZIP);
    
    lcSvcEmail = accountCreationBean.getChild(EMAIL);
    lcCNIC = accountCreationBean.getChild(IDENTITY);
    lcDOB = accountCreationBean.getChild(WTB_DOB);
    lcHomePhone = accountCreationBean.getChild(HOMEPHONE);
    lcWorkPhone = accountCreationBean.getChild(WORKPHONE);
    lcFaxPhone = accountCreationBean.getChild(FAX );
    lcMobilePhone = accountCreationBean.getChild(MOBILE);
    lcBusinessType = accountCreationBean.getChild(WTB_BUS_TYPE);
    lcCoverageType = accountCreationBean.getChild(COVERAGE_TYPE );
    lcLatitude = accountCreationBean.getChild(LATITUDE);
    lcLongitude = accountCreationBean.getChild(LONGITUDE);
    lcSLA = accountCreationBean.getChild(SLA);
    
    lcTitle =  accountCreationBean.getChild(TITLE);
    lcOrganization = accountCreationBean.getChild(COMPANY);
    lcBillType = accountCreationBean.getChild(BILL);
    lcActSvsType = accountCreationBean.getChild(WTB_ACCT_SERVICE_TYPE);
    lcContractPeriod = accountCreationBean.getChild(CONTRACT_PERIOD);
    lcProPackage = accountCreationBean.getChild(PROMO_PKG);
    lcContractStart = accountCreationBean.getChild(CONTRACT_START);
    lcContractEnd = accountCreationBean.getChild(CONTRACT_END);
    lcSvsLoginType = accountCreationBean.getChild( SVS_LOGIN_TYPE);
    lcTaxExptType = accountCreationBean.getChild( TAX_EXPT_TYPE);
    lcTaxExcptPercent = accountCreationBean.getChild( TAX_EXPT_PERCENT);
    lcWtbActSvsType = accountCreationBean.getChild( WTB_ACCT_SERVICE_TYPE);
}
Properties props = pCS.getDefaultProperties();
SimpleDateFormat formatter = new SimpleDateFormat(props.getProperty("applicationdateformat"));
String strGoogleKey = ( (props.getProperty("googleapikey") == null)? "login.jsp" : props.getProperty("googleapikey"));
WtbSelfCareUtility wsutil  = (WtbSelfCareUtility) pCS.createController("Wtb.MyAccount.WtbSelfCareUtility");
wsutil.setProperties(props);
List lstCities = wsutil.getCityList();
String brand=null;
Map formInput = ServletUtil.gatherFormInput(request);
String referrer = (String) formInput.get("referrer");
String sActSvsType = "";
String cBusinessType = "";

if (referrer.equals(DSTEP1)) {
    ServletUtil.setLightDataForAll(formInput, accountCreationBean, APP_NAME);
    if(formInput.get(WTB_DOB) != null && ((String)formInput.get(WTB_DOB)) != "") {
        lcDOB.setLightData(formatter.parse((String)formInput.get(WTB_DOB)));
    } else {
        lcDOB.setLightData(null);
    }
    
    sActSvsType = (String)formInput.get("accountservicetype1");
    if (sActSvsType.equals("1")) {
        lcBillType.setLightData(PREPAID);
    } else {
        lcBillType.setLightData(INVOICE);
    }
    cBusinessType = (String)lcBusinessType.getLightData();
    strNationality = (String)formInput.get("nationality");
//out.print (strNationality);
}
// *********************** My Account code ends here *****************************
%>
<html xmlns="http://www.w3.org/1999/xhtml">
 <head>
	<%@ include file="includes/headinc.jsp" %>
	<script src="js/jquery/jquery.js" type="text/javascript" language="javascript"></script>
	<script src="js/jquery/ui.core.js" type="text/javascript" language="javascript"></script>
	<link href="css/Stylesstyle.css" rel="stylesheet" type="text/css">
	<script language="javascript" src="js/xmlPost.js" type="text/javascript"></script>
	<script language="javascript" src="js/xmlGet.js" type="text/javascript"></script>
	<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAaNcmKM0QttFysMz8uw35sxQzB10VroHqKjktk46NizRkuHodLxQUFqIHJFDSnzmXFekHUxqfYU0ZvQ" type="text/javascript"></script>
	<script src="http://serverapi.arcgisonline.com/jsapi/gmaps/?v=1.3" type="text/javascript" ></script>
	<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
	  </head>
<SCRIPT>
    var ipAddress = "115.167.72.8";
var token ="U6Y7oqEpm8jHzb13-pd2mAL5FGlixRKJQA7BpNREoQn1YU6toIMmaTu3CmgP19EQDjhkGG64RSObwD_gT07DDw..";
function mcallBack()
{
	gcallbackfunc=null;
	content=null;
}

function search_address()
{
	$('p.validationmsg').hide();
        content="search_results_div";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
	customGetText("<img src='images/loading.gif' border=0> searching address ...");
	plot_no=document.address.HouseNo.value;
	road_no=document.address.Street.value;
	sector=document.address.Zone.value;
	City=document.address.City.value;
	getstr = "?HouseNo="+plot_no+"&Street="+road_no+"&Zone="+sector+"&City="+City;
	makeRequest('coverage/search.php', getstr);				
}

function showMessageDiv(field, msg) {
    $(field).parent('div').find('p.validationmsg').html(msg).show('slow');
    field.focus();
}
var l_lat;
var l_lng;
function locate()
{
	content="locate_div";
        //alert("hi in locate");
        $('p.validationmsg').hide();
	if(document.address.City.options[document.address.City.selectedIndex].value == 'Select') {
            showMessage(document.address.City, "Please select city.");
            return;
        }
      // Commented by swapna
      /*
        //Validations for Lahore Added by Satya
        if(document.address.City.options[document.address.City.selectedIndex].value == "Lahore"){
            if(document.address.Zone.options[document.address.Zone.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.Zone, "Please select scheme.");
                return;
            }
            if(document.address.SubZone.options[document.address.SubZone.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.SubZone, "Please select Phase.");
                return;
            }
            if(document.address.Block.options[document.address.Block.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.Street, "Please select Block.");
                return;
            }
            if(document.address.Street.options[document.address.Street.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.Street, "Please select Street.");
                return;
            }
            if(document.address.HouseNo.options[document.address.HouseNo.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.HouseNo, "Please select House.");
                return;
            }
        }else{*/
        
            if(document.address.Zone.options[document.address.Zone.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.Zone, "Please select sector.");
                return;
            }
            if(document.address.Street.options[document.address.Street.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.Street, "Please select Street.");
                return;
            }
            if(document.address.HouseNo.options[document.address.HouseNo.selectedIndex].value == 'Select') {
                showMessageDiv(document.address.HouseNo, "Please select plot.");
                return;
            }
        //}  //Commented by Swapna
        clearGetSpan();
        var url;
	gcallbackfunc="show_map()";
	customGetText("<img src='images/loading.gif' border=0> locating address ...");
	plot_no=trimString(document.address.HouseNo.value);
	road_no=trimString(document.address.Street.value);
        block=trimString(document.address.Block.value);
	subzone=trimString(document.address.SubZone.value);
        sector=trimString(document.address.Zone.value);
        City=trimString(document.address.City.value);
    //Commented by Swapna
        
        //Lahore Coverage chk added by satya
       /*  if(City == "Lahore"){
            if(road_no == 'NUN'){
             url = "LahoreApiCall.jsp?q=hl&city=lahore&scheme="+sector+"&phase="+subzone+"&block="+block+"&house="+plot_no;
            }else{
             url = "LahoreApiCall.jsp?q=hl&city=lahore&scheme="+sector+"&phase="+subzone+"&block="+block+"&street="+road_no+"&house="+plot_no;
            }
            
            http.open("POST", url , true );
            http.onreadystatechange = handleCoverageResponseLahoreLatLng;
            http.send();
            ResultType = "AUTO";
                       
        }else{ */
            gc = plot_no+","+road_no+","+sector+","+City;
            //alert(gc);
            getstr = "HouseNo="+plot_no+"&Street="+road_no+"&Zone="+sector+"&City="+City;
            url = "CTCheckStatus_Dealer.jsp";
            http.open("POST", url , true);
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            http.setRequestHeader("Content-length", getstr.length);
            http.setRequestHeader("Connection", "close");
            http.onreadystatechange = handleCoverageResponse;
            handler = "";
            http.send(getstr);
            ResultType = "AUTO";
         //}
}

function locatemanual()
{
	$('p.validationmsg').hide();
        content="locate_div";
	clearGetSpan();
       // Commented by Swapna  
        //Lahore Validations added by satya
       /* if(document.manualaddress.city.options[document.manualaddress.city.selectedIndex].value == "Lahore"){
        
            if(trimString(document.manualaddress.HouseNo.value) == '') {
                showMessage(document.manualaddress.HouseNo, "Please enter House no.");
                return;
            }
            if(trimString(document.manualaddress.Street.value) == '') {
                showMessage(document.manualaddress.Street, "Please enter Street.");
                return;
            }
            if(trimString(document.manualaddress.Block.value) == '') {
                showMessage(document.manualaddress.Block, "Please enter Block.");
                return;
            }
            if(trimString(document.manualaddress.SubZone.value) == '') {
                showMessage(document.manualaddress.SubZone, "Please enter Phase.");
                return;
            }
            if(trimString(document.manualaddress.Zone.value) == '') {
                showMessage(document.manualaddress.Zone, "Please enter Scheme.");
                return;
            }
        }else{ */
            if(trimString(document.manualaddress.HouseNo.value) == '') {
                showMessage(document.manualaddress.HouseNo, "Please enter plot no.");
                return;
            }
            if(trimString(document.manualaddress.Street.value) == '') {
                showMessage(document.manualaddress.Street, "Please enter street.");
                return;
            }
            if(trimString(document.manualaddress.Zone.value) == '') {
                showMessage(document.manualaddress.Zone, "Please enter sector.");
                return;
            }
         //}
           
            if(trimString(document.manualaddress.y.value) == '') {
                showMessage(document.manualaddress.y, "Please enter latitude.");
                return;
            }
            if(trimString(document.manualaddress.x.value) == '') {
                showMessage(document.manualaddress.x, "Please enter longtitude.");
                return;
            }
         
        var url;
        gcallbackfunc="show_map()";
	customGetText("<img src='images/loading.gif' border=0> locating address ...");
	City=document.manualaddress.city.value;
        plot_no=document.manualaddress.HouseNo.value;
	road_no=document.manualaddress.Street.value;
        block = '';
        subzone=document.manualaddress.SubZone.value;
	sector=document.manualaddress.Zone.value;
	
        x = trimString(document.manualaddress.x.value);
        y = trimString(document.manualaddress.y.value);
     // Commented by swapna   
        //Lahore Coverage chk Added by satya
     /* if(City == "Lahore"){
        url = "LahoreApiCall.jsp?q=IsAreaCoveredByLatLng&lat="+x+"&lng="+y;
        http.open("POST", url , true );
        http.onreadystatechange = handleCoverageResponseLahoreManual;
        http.send();   
         ResultType = "MANUAL";
     }else{ */
	gc = plot_no+","+road_no+","+sector+","+City;
	//alert(gc);
	getstr = "HouseNo="+plot_no+"&Street="+road_no+"&Zone="+sector+"&City="+City+"&query=manual&x="+x+"&y="+y;
        url = "CTCheckStatus_Dealer.jsp";        
        http.open("POST", url , true);
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.setRequestHeader("Content-length", getstr.length);
        http.setRequestHeader("Connection", "close");
        http.onreadystatechange = handleCoverageResponse;
        handler = "";
        http.send(getstr);
        ResultType = "MANUAL";
     // }
}
// Commented by Swapna
//Lahore response functions added by satya 
/*function handleCoverageResponseLahoreLatLng() {
     $('p.validationmsg').hide();
    var url;
     if(http.readyState == 4 && http.status == 200) {       
        var response = http.responseText;
        //alert(response);        
        var op = response.split(",");        
        for(i=0; i<3;i++) {
            op[i] = op[i].replace("<strong>","");
            op[i] = op[i].replace("</strong>","");
            op[i] = op[i].slice(op[i].indexOf('(') + 1 , op[i].indexOf('%'));
            //alert(op[i]);
        }
        l_lat = '31.'+ parseInt(trimString(op[0])*7);
	l_lng = '74.'+parseInt(trimString(op[1])*3);
        x = l_lat;
        y = l_lng;
        lahoreZooom = trimString(op[2]);
        if(road_no == 'NUN'){
         url = "LahoreApiCall.jsp?q=IsAreaCoveredByGeoCodeAddress&city=lahore&scheme="+sector+"&phase="+subzone+"&block="+block+"&house="+plot_no;
        }else{
         url = "LahoreApiCall.jsp?q=IsAreaCoveredByGeoCodeAddress&city=lahore&scheme="+sector+"&phase="+subzone+"&block="+block+"&street="+road_no+"&house="+plot_no;
        }
        http.open("POST", url , true );
        http.onreadystatechange = handleCoverageResponseLahore;
        http.send();
      }
}
function handleCoverageResponseLahore() {
     $('p.validationmsg').hide();
    if(http.readyState == 4 && http.status == 200) {
       
        var response = http.responseText;
        //alert(response); 
        var op = response.split("#");        
        for(i=0; i<3;i++) {
            op[i] = op[i].replace("<strong>","");
            op[i] = op[i].replace("</strong>","");
            op[i] = op[i].slice(op[i].indexOf('(') + 1 , op[i].indexOf('%'));
            //alert(op[i]);
        }
        //x=l_lat;
        //y=l_lng;
        //alert(x);
        //alert(y);
        getstr = "HouseNo="+plot_no+"&Street="+road_no+"&block="+block+"&SubZone="+subzone+"&Zone="+sector+"&City="+City+"&x="+x+"&y="+y+"&USB="+op[0]+"&INDOOR="+op[1]+"&OUTDOOR="+op[2];
        var url = "CTCheckStatusLahore_Dealer.jsp";
        //alert(url);
        http.open("POST", url , true);
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.setRequestHeader("Content-length", getstr.length);
        http.setRequestHeader("Connection", "close");
        http.onreadystatechange = handleCoverageResponse;
        handler = "";
        http.send(getstr);
      }
}
function handleCoverageResponseLahoreManual() {
     $('p.validationmsg').hide();
    if(http.readyState == 4 && http.status == 200) {
       var url;
        var response = http.responseText;
        //alert(response); 
        var op = response.split(";");        
        for(i=0; i<3;i++) {
            op[i] = op[i].replace("<strong>","");
            op[i] = op[i].replace("</strong>","");
            op[i] = op[i].slice(op[i].indexOf('(') + 1 , op[i].indexOf('%'));
            //alert(op[i]);
        }
        //x=l_lat;
        //y=l_lng;
        //alert(x);
        //alert(y);
        getstr = "HouseNo="+plot_no+"&Street="+road_no+"&block="+block+"&SubZone="+subzone+"&Zone="+sector+"&City="+City+"&x="+x+"&y="+y+"&USB="+op[0]+"&INDOOR="+op[1]+"&OUTDOOR="+op[2];
        url = "CTCheckStatusLahore_Dealer.jsp";
        http.open("POST", url , true);
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.setRequestHeader("Content-length", getstr.length);
        http.setRequestHeader("Connection", "close");
        http.onreadystatechange = handleCoverageResponse;
        handler = "";
        http.send(getstr);
      }
}
*/
// End by Swapna
function handleCoverageResponse() {
    $('p.validationmsg').hide();
    if(http.readyState == 4 && http.status == 200) {
        customGetText("");
        var response = http.responseText;
        document.getElementById("locate_div").innerHTML = response;
        show_map();
     }
}

function locate_poi()
{
    $('p.validationmsg').hide();
    content="locate_div";
	clearGetSpan();
	gcallbackfunc="mcallBack()";
    customGetText(" locating address ...");
	gc = plot_no+","+road_no+","+sector+","+City;
    getstr = "?address="+gc;
}

function clearDropDownList(theDropDown)
{
    $('p.validationmsg').hide();
     var numberOfOptions = theDropDown.options.length;
     for (i=0; i<numberOfOptions; i++)
     {
          //Note: Always remove(0) and NOT remove(i)
          theDropDown.remove(0)
    }
}

var http = createRequestObject(); 
var handler = "";    
var ResultType = "";
//added by satya
function GetLabels(City){
    $('p.validationmsg').hide();
        clearDropDownList(document.address.Zone);
        clearDropDownList(document.address.SubZone);
        clearDropDownList(document.address.Block);
	clearDropDownList(document.address.Street);
	clearDropDownList(document.address.HouseNo);
       // Commented By Swapna   
      /*  if(City == 'Lahore') {           
            document.getElementById('lahore_msubzone').style.visibility="visible";
            document.getElementById('lahore_msubzone').style.visibility="visible";
            document.getElementById('lblSector').innerHTML = "Schemes";
            document.getElementById('lahore_mblock').style.visibility="visible";
            document.getElementById('lblmStreet').innerHTML = "Streets";
            document.getElementById('lblPlot').innerHTML = "Houses";
            document.getElementById('locate').style.display= "";
        }else{ */
            document.getElementById('lahore_msubzone').style.visibility="hidden";
            document.getElementById('lahore_mblock').style.visibility="hidden";
            document.getElementById('lblSector').innerHTML = "Sector";
            document.getElementById('lblmStreet').innerHTML = "Street / Category";
            document.getElementById('lblPlot').innerHTML = "Plot No / Name";
            document.getElementById('locate').style.display= "";
        //}
}
function GetZones(City)
{
$('p.validationmsg').hide();
        
       // clearDropDownList(document.address.Zone);
       // clearDropDownList(document.address.SubZone);
       // clearDropDownList(document.address.Block);
//	clearDropDownList(document.address.Street);
//	clearDropDownList(document.address.HouseNo); 
        document.address.state.value=document.address.City.options[document.address.City.selectedIndex].title;
        content="zone_div";
        clearGetSpan();
        gcallbackfunc="mcallBack()";
	customGetText("<img src='images/loading.gif' border=0> locating address ...");
        getstr = "City="+City + "&ReqFor=1";
        // Commented By Swapna
        //code added by satya
       /* if(City == 'Lahore') {
        //alert(lahore);
            document.getElementById('lahore_subzone').style.display="";
            document.getElementById('lahore_block').style.display="";
            document.getElementById('lblZone').innerHTML = "Schemes";
            document.getElementById('lblStreet').innerHTML = "Streets";
            document.getElementById('lblHouseno').innerHTML = "Houses";
            var url = "LahoreApiCall.jsp?q=GetSchemes&city=lahore"
            http.open("POST", url , true );
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            http.setRequestHeader("Content-length", getstr.length);
            http.setRequestHeader("Connection", "close");
            http.onreadystatechange = handleSchemesResponse;
            handler = "1";
            http.send();
         } else {   */     
            document.getElementById('lahore_subzone').style.display="none";
            document.getElementById('lahore_block').style.display="none";
            document.getElementById('lblZone').innerHTML = "Sector";
            document.getElementById('lblStreet').innerHTML = "Street / Category";
            document.getElementById('lblHouseno').innerHTML = "Plot No / Name";
            var url = "CTSupport.jsp";
            http.open("POST", url , true);
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            http.setRequestHeader("Content-length", getstr.length);
            http.setRequestHeader("Connection", "close");
            http.onreadystatechange = handleZonesResponse;
            handler = "1";
            http.send(getstr);
         // }
}
function handleZonesResponse() {
    $('p.validationmsg').hide();
    var ResultString = "";
    if(http.readyState == 4 && http.status == 200) {
        customGetText("");
        var response = http.responseText;
        if(response.indexOf("Exception:") < 0) {
            var arrZones = response.split(";");
            ResultString = "<select class='selectbox' name='Zone' onchange='javascript: GetStreets(this.value);'>"
            ResultString = ResultString + "<option value='Select'>Select  </option>";
            for(i=0; i< arrZones.length; i++) { 
                //arrZones[i] = trimString(arrZones[i]);
                //if(arrZones[i] != "")
                    ResultString = ResultString + "<option value='" + arrZones[i] + "'>" + arrZones[i] + "</option>";
            }
            ResultString = ResultString + "</select> <p class=validationmsg></p>";
        } else {
            ResultString = "Error: Try again.";
        }
        document.getElementById("zone_div").innerHTML = ResultString;
     }
        
}
//function added by satya
 function handleSchemesResponse() {
    $('p.validationmsg').hide();
    var ResultString = "";
    if(http.readyState == 4 && http.status == 200) {
        customGetText("");
        var response = http.responseText;
        //alert("response");
        if(response.indexOf("Exception:") < 0) {
            var arrZones = response.split(";");
            ResultString = "<select class='selectbox' name='Zone' onchange='javascript: GetPhases(this.value);'>"
            ResultString = ResultString + "<option value='Select'>Select  </option>";
            for(i=0; i< arrZones.length; i++) { 
                //arrZones[i] = trimString(arrZones[i]);
                //if(arrZones[i] != "")
                    ResultString = ResultString + "<option value='" + arrZones[i] + "'>" + arrZones[i] + "</option>";
            }
            ResultString = ResultString + "</select> <p class=validationmsg></p>";
        } else {
            ResultString = "Error: Try again.";
        }
        document.getElementById("zone_div").innerHTML = ResultString;
     }
        
}
//function added by satya
function GetPhases(scheme)
{
    $('p.validationmsg').hide();
    clearDropDownList(document.address.HouseNo);
    City = document.address.City.value;
    
    content="subzone_div";
    clearGetSpan();
    
    gcallbackfunc="mcallBack()";
    customGetText("<img src='images/loading.gif' border=0> locating address ...");
    var url = "LahoreApiCall.jsp?q=GetPhases&city=lahore&scheme="+scheme;
    http.open("POST", url , true );
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Content-length", getstr.length);
    http.setRequestHeader("Connection", "close");
    http.onreadystatechange = handlePhasesResponse;
    handler = "1";
    http.send();
}
//function added by satya
function handlePhasesResponse() {
    $('p.validationmsg').hide();
    var ResultString = "";
    if(http.readyState == 4 && http.status == 200) {
        customGetText("");
        var response = http.responseText;
        //alert(response);
        if(response.indexOf("Exception:") < 0) {
            var arrZones = response.split(";");
            ResultString = "<select class='selectbox' name='SubZone' onchange='javascript: GetBlocks(this.value);'>"
            ResultString = ResultString + "<option value='Select'>Select  </option>";
            for(i=0; i< arrZones.length; i++) { 
                //arrZones[i] = trimString(arrZones[i]);
                //if(arrZones[i] != "")
                    ResultString = ResultString + "<option value='" + arrZones[i] + "'>" + arrZones[i] + "</option>";
            }
            ResultString = ResultString + "</select> <p class=validationmsg></p>";
        } else {
            ResultString = "Error: Try again.";
        }
        document.getElementById("subzone_div").innerHTML = ResultString;
     }
        
}
function GetBlocks(phase)
{
    $('p.validationmsg').hide();
    clearDropDownList(document.address.HouseNo);
    City = document.address.City.value;        
    zone = document.address.Zone.value; 
    
    content="block_div";
    clearGetSpan();
    
    gcallbackfunc="mcallBack()";
    customGetText("<img src='images/loading.gif' border=0> locating address ...");
    var url = "LahoreApiCall.jsp?q=GetBlocks&city=lahore&scheme="+zone+"&phase="+phase;
    http.open("POST", url , true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Content-length", getstr.length);
    http.setRequestHeader("Connection", "close");
    http.onreadystatechange = handleBlocksResponse;
    handler = "1";
    http.send();
}

function handleBlocksResponse() {
    $('p.validationmsg').hide();
    var ResultString = "";
    if(http.readyState == 4 && http.status == 200) {
        customGetText("");
        var response = http.responseText;
        //alert(response);
        if(response.indexOf("Exception:") < 0) {
            var arrBlocks = response.split(";");
            ResultString = "<select class='selectbox' name='Block' onchange='javascript: GetStreets(this.value);'>"
            ResultString = ResultString + "<option value='Select'>Select  </option>";
            for(i=0; i< arrBlocks.length; i++) { 
                //arrStreets[i] = trimString(arrStreets[i]);
                //if(arrStreets[i] != "")
                    ResultString = ResultString + "<option value='" + arrBlocks[i] + "'>" + arrBlocks[i] + "</option>";
            }
            ResultString = ResultString + "</select><p class=validationmsg></p>";
        } else {
            ResultString = "Error: Try again.";
        }
        //alert(ResultString);
        document.getElementById("block_div").innerHTML = ResultString;
     }
        
}
function GetStreets(block)
{
    $('p.validationmsg').hide();
    clearDropDownList(document.address.HouseNo);
    City = document.address.City.value;    
    zone = document.address.Zone.value;
    subzone = document.address.SubZone.value;
   
      
    content="street_div";
    clearGetSpan();
    gcallbackfunc="mcallBack()";
    customGetText("<img src='images/loading.gif' border=0> locating address ...");
   
    getstr = "City="+City+"&Zone="+zone+"&ReqFor=2";
// Commented by Swapna
    //code added by satya
    /*if(City == "Lahore"){
        var url = "LahoreApiCall.jsp?q=GetStreets&city=lahore&scheme="+zone+"&phase="+subzone+"&block="+block;
        http.open("POST", url , true );
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.setRequestHeader("Content-length", getstr.length);
        http.setRequestHeader("Connection", "close");
        http.onreadystatechange = handleStreetsResponse;
        handler = "1";
        http.send();
    }else{ */
        var url = "CTSupport.jsp";
        http.open("POST", url , true);
        http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        http.setRequestHeader("Content-length", getstr.length);
        http.setRequestHeader("Connection", "close");
        http.onreadystatechange = handleStreetsResponse;
        handler = "";
        http.send(getstr);
    //}
    
}
function handleStreetsResponse() {
    $('p.validationmsg').hide();
    var ResultString = "";
    if(http.readyState == 4 && http.status == 200) {
        customGetText("");
        var response = http.responseText;
        if(response.indexOf("Exception:") < 0) {
            var arrStreets = response.split(";");
            ResultString = "<select class='selectbox' name='Street' onchange='javascript: GetHouses(this.value);'>"
            ResultString = ResultString + "<option value='Select'>Select  </option>";
            for(i=0; i< arrStreets.length; i++) { 
                //arrStreets[i] = trimString(arrStreets[i]);
                //if(arrStreets[i] != "")
                    ResultString = ResultString + "<option value='" + arrStreets[i] + "'>" + arrStreets[i] + "</option>";
            }
            ResultString = ResultString + "</select><p class=validationmsg></p>";
        } else {
            ResultString = "Error: Try again.";
        }
        document.getElementById("street_div").innerHTML = ResultString;
     }
        
}

function GetHouses(street)
{
$('p.validationmsg').hide();
City = trimString(document.address.City.value);
zone = trimString(document.address.Zone.value);
subzone = trimString(document.address.SubZone.value);
block =  trimString(document.address.Block.value);
var url;
content="house_div";
clearGetSpan();
gcallbackfunc="mcallBack()";
customGetText("<img src='images/loading.gif' border=0> locating address ...");   
 getstr = "City="+City+"&Zone="+zone+"&Street="+street+"&ReqFor=3";
 // Commented By Swapna
 //code added by satya
 /*if(City == "Lahore"){
    customGetText("<img src='images/loading.gif' border=0> locating address ...");    
    street = trimString(street);
    if(street == 'NUN'){
         url = "LahoreApiCall.jsp?q=GetHouses&city=lahore&scheme="+zone+"&phase="+subzone+"&block="+block;
    }else{
         url = "LahoreApiCall.jsp?q=GetHouses&city=lahore&scheme="+zone+"&phase="+subzone+"&block="+block+"&street="+street;
    }
    http.open("POST", url , true );
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Content-length", getstr.length);
    http.setRequestHeader("Connection", "close");
    http.onreadystatechange = handlePlotResponse;
    handler = "1";
    http.send();
 }else{ */
    //alert("hi");
    var url = "CTSupport.jsp";
    http.open("POST", url , true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Content-length", getstr.length);
    http.setRequestHeader("Connection", "close");
    http.onreadystatechange = handlePlotResponse;
    handler = "";
    http.send(getstr);				
  //}
}

function handlePlotResponse() {
    $('p.validationmsg').hide();
    var ResultString = "";
    if(http.readyState == 4 && http.status == 200) {
        customGetText("");
        var response = http.responseText;
        
        if(response.indexOf("Exception:") < 0) {
            var arrHouse = response.split(";");
            ResultString = "<select class='selectbox' name='HouseNo' onchange='javascript:locate();' >"
            ResultString = ResultString + "<option value='Select'>Select  </option>";
            for(i=0; i< arrHouse.length; i++) { 
                //arrHouse[i] = trimString(arrHouse[i]);
                //if(arrHouse[i] != "")
                    ResultString = ResultString + "<option value='" + arrHouse[i] + "'>" + arrHouse[i] + "</option>";
            }
            ResultString = ResultString + "</select><p class=validationmsg></p>";
        } else {
            ResultString = "Error: Try again.";
        }
        document.getElementById("house_div").innerHTML = ResultString;
     }
        
}

var x,y,gcG,lahoreZooom;
var map="nullV";
var urlAd;

function show_map()
{
    $('p.validationmsg').hide();
    
	gc=document.address.HouseNo.value+","+document.address.Street.value+","+document.address.Zone.value+","+document.address.City.value;
	// Commented By Swapna
        /*if(document.address.City.value == 'Lahore') {
            document.locater.coordinates.value = l_lat + ',' + l_lng;
            coord = document.locater.coordinates.value;
            x = l_lat;
            y = l_lng;
            
        }
        else */
            coord = document.locater.coordinates.value;
            
        //alert(coord);
        coverage_value = document.locater.coverage_value.value;
	xy = coord.split(',');
        x = xy[0];
	y = xy[1];
	gcG = gc;
       
        /*if(document.address.City.value == 'Lahore') {
           initGMap(x,y,18);
        } else { */
             initialize();
        //}
        
}
function initGMap(lat,lng,zoom){
    	if (GBrowserIsCompatible()) {
 
 		var  mapTypes = [G_NORMAL_MAP,G_HYBRID_MAP,G_SATELLITE_MAP,G_SATELLITE_3D_MAP];
		for(var i = 0; i < mapTypes.length; i++){
			mapTypes[i].getMaximumResolution = function(latlng){ return 18;};
			mapTypes[i].getMinimumResolution = function(latlng){ return 1;};
		}
        //map = new GMap2(document.getElementById("map_canvas"), {mapTypes: mapTypes});
		
		
		//var  mapTypes = [G_NORMAL_MAP,G_HYBRID_MAP,G_SATELLITE_MAP];
		map = new GMap2(document.getElementById("map_canvas"),{mapTypes:mapTypes});
		map.addControl(new GMapTypeControl());
		map.addControl(new GLargeMapControl());
		map.enableContinuousZoom();
		map.enableDoubleClickZoom();
		
		map.setMapType(G_HYBRID_MAP);
		map.enableScrollWheelZoom();
		
		
		if(zoom==12){
			map.setCenter(new GLatLng(31.4869428108,74.325256347), zoom);
			alert("NOT FOUND");
		}
		else
		{
			var l_lat = lat;
			var l_lng = lng;
			
			
			var l_point = new GLatLng(parseFloat(l_lat),parseFloat(l_lng));
			
			
			map.setCenter(l_point, zoom);
			var marker = new GMarker(l_point);
			map.addOverlay(marker);
		}

    }
  }//end function

/*function swapKML()
{
    var dynamicMap = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/Coverage/MapServer",null,0.45,dynmapcallback);
		
}

function swapUSBCoverage()
{
    //var dynamicMap15 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/USBCoverage/MapServer",null,0.45,dynmapcallback15);
    var token = 6QWnlW4UAFC1kLLI__Ahy7FJpfIv825jdWApGBda6Ecx-2lmbLS4L1aEftJwfcb7rR2iFnmHkDey0ewEzpfxqg..;
    var Host = 115.167.72.8;
    var dynamicMap15 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+Host+"/ArcGIS/rest/services/USBCoverage/MapServer?token="+token,null,0.45,dynmapcallback15);
		
}

function swapOutDoorCoverage()
{
    //var dynamicMap5 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/OutDoorCoverage/MapServer",null,0.45,dynmapcallback5);
    var token = 6QWnlW4UAFC1kLLI__Ahy7FJpfIv825jdWApGBda6Ecx-2lmbLS4L1aEftJwfcb7rR2iFnmHkDey0ewEzpfxqg..;
    var Host = 115.167.72.8;
   var dynamicMap9 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+Host+"/ArcGIS/rest/services/InDoorCoverage/MapServer?token="+token,null,0.45,dynmapcallback9);// Changed by swapna
}
function swapInDoorCoverage()
{
    //var dynamicMap9 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/InDoorCoverage/MapServer",null,0.45,dynmapcallback9);
    var token = 6QWnlW4UAFC1kLLI__Ahy7FJpfIv825jdWApGBda6Ecx-2lmbLS4L1aEftJwfcb7rR2iFnmHkDey0ewEzpfxqg..;
    var Host = 115.167.72.8;
    var dynamicMap9 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+Host+"/ArcGIS/rest/services/InDoorCoverage/MapServer?token="+token,null,0.45,dynmapcallback9);// Changed by swapna
}

function swapRoadsKML()
{
    var dynamicMap2 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/IsbRoads/MapServer",null,0.80,dynmapcallback2);
    var dynamicMap3 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/PakistanRoads/MapServer",null,0.80,dynmapcallback3);
}

function swapZonesKML()
{
    var dynamicMap4 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://10.1.82.11/ArcGIS/rest/services/PakistanZones/MapServer",null,1.0,dynmapcallback4);
}

function swapSitePoints() 
{ 
    var dynamicPoint =  
    new  esri.arcgis.gmaps.DynamicMapServiceLayer("http://115.167.72.11/ArcGIS/rest/services/CoverageSitePoints/MapServer",null,1.0,dynmapCallSite); 
                
} */

// Added by Swapna for Coverage Layer change

function swapKML()
{
    var dynamicMap = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/As/Coverage/MapServer?token="+token,null,0.45,dynmapcallback);
		
}

function swapUSBCoverage()
{
    var dynamicMap15 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/ArcGIS/rest/services/USBCoverage/MapServer?token="+token,null,0.45,dynmapcallback15);
		
}

function swapOutDoorCoverage()
{
    var dynamicMap5 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/ArcGIS/rest/services/OutDoorCoverage/MapServer?token="+token,null,0.45,dynmapcallback5);
}

function swapInDoorCoverage()
{
    var dynamicMap9 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/ArcGIS/rest/services/InDoorCoverage/MapServer?token="+token,null,0.45,dynmapcallback9);
}

function swapRoadsKML()
{
    var dynamicMap2 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/ArcGIS/rest/services/IsbRoads/MapServer?token="+token,null,0.80,dynmapcallback2);
    var dynamicMap3 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/ArcGIS/rest/services/PakistanRoads/MapServer?token="+token,null,0.80,dynmapcallback3);
}

function swapZonesKML()
{
    var dynamicMap4 = new esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/ArcGIS/rest/services/PakistanZones/MapServer?token="+token,null,1.0,dynmapcallback4);
}
function swapSitePoints() 
{ 
		var dynamicPoint =  
		new  esri.arcgis.gmaps.DynamicMapServiceLayer("http://"+ipAddress+"/ArcGIS/rest/services/CoverageSitePoints/MapServer?token="+token,null,1.0,dynmapCallSite); 
                
} 

// end by swapna

function locatemanualformap(x,y)
{
document.kmlBoxes.ad_input.checked=true;
swapInput();
document.manualaddress.y.value = y;
document.manualaddress.x.value = x;
locatemanual();

}

function initialize()
{
    var  mapTypes = [G_NORMAL_MAP,G_HYBRID_MAP,G_SATELLITE_MAP,G_SATELLITE_3D_MAP];
    var zoomlvl = 18;
       	if (GBrowserIsCompatible())
	{
		if(map=="nullV"){
                
               /* if(trimString(document.address.City.value) == 'Lahore')
                {
                    
                    for(var i = 0; i < mapTypes.length; i++){
                        mapTypes[i].getMaximumResolution = function(latlng){ return 18;};
                        mapTypes[i].getMinimumResolution = function(latlng){ return 1;};
                    }
                    map = new GMap2(document.getElementById("map_canvas"),{mapTypes:mapTypes});
                } else { */
                    map = new GMap2(document.getElementById("map_canvas"));
                //}
		map.enableScrollWheelZoom();
		
		GEvent.addListener(map,'zoomend',function(){
 
				zooming();
 
			});
		
		
		if(document.address.Zone.value==""){
			zoomlvl=13;
		}else if(document.address.Street.value==""){
			zoomlvl=15;		
		}else if(document.address.HouseNo.value==""){
			zoomlvl=16;
		}else{
			zoomlvl=18;
		}
		/* if(document.address.City.value == 'Lahore') {
                    zoomlvl = 18;
                    if(zoom==12){
			map.setCenter(new GLatLng(31.4869428108,74.325256347), zoomlvl);
			alert("NOT FOUND");
                    } else {
                        //
                    }
                } */
                
         	var givenmaptypes = map.getMapTypes();
         	map.setMapType(givenmaptypes[2]);
        	//alert("create controller");
		var mapControl = new GMapTypeControl();
		map.addControl(mapControl);      	
		map.addControl(new GLargeMapControl());
                // Commented by Swapna
               /* if(trimString(document.address.City.value) == 'Lahore') {
                    map.addControl(new GLargeMapControl());
                    map.enableContinuousZoom();
                    map.enableDoubleClickZoom();
                    map.setMapType(G_HYBRID_MAP);
                }*/
        	//alert("create controller-1");		
		var ovCont = new GOverviewMapControl();
		ovCont.setMapType(givenmaptypes[2]);
		map.addControl(ovCont);
                GEvent.addListener(map,"click", function(overlay, point) {
                    if (point) { 
                        var myHtml = "The GPoint value is: " + point.x + " at zoom level " + map.getZoom();
                        locatemanualformap(point.x,point.y)   
                        }
                        });
		}
		//alert("adding listener");
                map.setCenter(new GLatLng(y, x), zoomlvl);
		var point = new GLatLng( y, x);
		var gmarker = new GMarker(point);
        	//alert("create controller-3");
                map.clearOverlays()
		map.addOverlay(gmarker);
		swapSitePoints();
 
    }
}




var dynMapOv;
var flag0=0;
var flag=0;
var flag1=0;
var flag2=0;
var dynMapOv2;
var dynMapOv3;
var dynMapOv4;

var dynMapOv5;
var dynMapOv9;
var dynMapOv15;
var dynMap; 



function dynmapcallback(groundov)
{
    if(document.kmlBoxes.C.checked)
    {
        map.addOverlay(groundov);
        flag0=1;
        dynMapOv = groundov;
    }else{
        map.removeOverlay(dynMapOv);
        flag0=0;
    }
}


function dynmapCallSite(groundov) { 

      //Add groundoverlay to map using map.addOverlay() 
	  map.addOverlay(groundov); 
	  dynMap= groundov; 
 } 
 
function dynmapcallback15(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes2.U.checked)
    {
        map.addOverlay(groundov);
        dynMapOv15 = groundov;
    }else{
        map.removeOverlay(dynMapOv15);
		
	}
}

function dynmapcallback9(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes2.I.checked)
    {
        map.addOverlay(groundov);
        dynMapOv9 = groundov;
    }else
    {
        map.removeOverlay(dynMapOv9);
    }
}
function dynmapcallback5(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes2.O.checked)
    {
        map.addOverlay(groundov);
        dynMapOv5 = groundov;
	}else
    {
        map.removeOverlay(dynMapOv5);
    }
}

function zooming()
{
    if (map.getZoom()<16)
	{
		if(flag==1)
		{
			map.removeOverlay(dynMapOv2);
			map.removeOverlay(dynMapOv3);
			flag=0;
		}
		if(flag1==0)
		{swapZonesKML();}
	}
	else
	{
	   if(flag1==1)
		{
			map.removeOverlay(dynMapOv4);
			flag1=0;
		}
        if(flag==0)
		{swapRoadsKML();}
	}
}


function dynmapcallback2(groundov)
{
    //Add groundoverlay to map using map.addOverlay()
	if(document.kmlBoxes.R.checked && map.getZoom()>=16)
	{
        map.addOverlay(groundov);
        flag=1;
        dynMapOv2 = groundov;
    }else
    {
        map.removeOverlay(dynMapOv2);
        flag=0;
    }
}

function dynmapcallback3(groundov) 
{
    //Add groundoverlay to map using map.addOverlay()
    if(document.kmlBoxes.R.checked  && map.getZoom()>=16)
    {
        map.addOverlay(groundov);
        flag=1;
        dynMapOv3 = groundov;
    }
    else
    {
        map.removeOverlay(dynMapOv3);
        flag=0;
    }
}

function dynmapcallback4(groundov)
{
    //	alert(map.getZoom());
    //	Add groundoverlay to map using map.addOverlay()
    if(document.kmlBoxes.Z.checked  && map.getZoom()<16)
    {
        map.addOverlay(groundov);
        flag1=1;
        dynMapOv4 = groundov;
    }else {
        map.removeOverlay(dynMapOv4);
        flag1=0;
    }
}
function stopLocate()
{
//Commented by Swapna
     /*if(document.manualaddress.city.options[document.manualaddress.city.selectedIndex].value == "Lahore"){
        //alert("lahore in stoplocate");
        document.manualaddress.getElementById('locate').style.visibility="hidden";
     }else{ */
        //alert("other");
        document.manualaddress.getElementById('locate').style.visibility="visible";
     //} 
}

function swapInput()
{
	 if(document.kmlBoxes.ad_input.checked == true)
	{
                 
		document.getElementById("drill").style.display= "none";
		document.getElementById("manual").style.display = 'inline';
	}else
	{
		document.getElementById("drill").style.display= "inline";
		document.getElementById("manual").style.display = 'none';
	}
         
       
}

	
function bypassCoverage(frm) {
    $('p.validationmsg').hide();
    
            if(trimString(frm.HouseNo.value) == '') {
                showMessage(frm.HouseNo, "Please enter plot no.");
                return;
            }
            if(trimString(frm.Street.value) == '') {
                showMessage(frm.Street, "Please enter street.");
                return;
            }
            if(trimString(frm.Zone.value) == '') {
                showMessage(frm.Zone, "Please enter sector.");
                return;
            }
            if(trimString(frm.city.value) == '') {
                showMessage(frm.city, "Please select city.");
                return;
            }
            
	
    frm.x.value="";
    frm.y.value="";
    frm.latitude.value = frm.y.value;
    frm.longitude.value = frm.x.value;
    frm.coverage_type.value='(Bypassed)';
    var sHNo, sStreet, sZone;
    sHNo = trimString(frm.HouseNo.value);
    sStreet = trimString(frm.Street.value);
    sZone = trimString(frm.Zone.value);
    if(sHNo == '') sHNo = "-";
    if(sStreet == '') sStreet = '-';
    if(sZone == '') sZone = '-';
    frm.address1.value = sHNo ;
    frm.address1.value = frm.address1.value  + "   " + sStreet;
    frm.address1.value = frm.address1.value  + "   " + "_";
    frm.address1.value = frm.address1.value  + "   " + sZone;
    frm.action = "./DealerAccountCreationStep1Action.do";
    frm.method = "post";
    frm.submit();
}
	</SCRIPT>    
	<body>
    <!-- Inner content starts here -->
    <h3 class="heading">Coverage Check</h3>
    <!--<P>Fields marked with an asterisk (<SPAN class=mandatory>*</SPAN>) are required.</P> -->
    <!-- message end -->
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <TABLE border=3 cellSpacing=0 cellPadding=0 width="98%" align=left class="Act-listing">
                <tr class='info-listing'><td colspan=3><DIV style="WIDTH: 650px; HEIGHT: 500px" id=map_canvas></DIV></td></tr>
                <tr class='info-listing'><td colspan=3>&nbsp;</td></tr>
                <tr  class='info-listing'>
                    <td  valign="top" width=40%>
                        <!--  address form -->
                        <FORM name=address onsubmit='return (false);'>
                            <DIV  id=drill >
                                <TABLE border=0 cellpadding=0 CELLSPACING="0" class=ct-info>
                                    <TBODY>
                                        <tr><th colspan=2><SPAN 
                                            style="COLOR: #c60551; FONT-SIZE: 12px"><STRONG>Address</STRONG></SPAN></th></tr>
                                        <TR >
                                            <TD class=textboxBur height=22 align=left >City</TD>
                                            <TD align=left ><select class="textboxGray" name="City" onchange="javascript: GetZones(this.value);">
                                                    <option value='Select'>Select City </option>
                                                    <%
                                                    if(lstCities != null) {
    if(lstCities.size() > 0) {
        String sTempCity = ((String)lcSvcCity.getLightData() == null ? "" : (String)lcSvcCity.getLightData());
        for(int i=0; i< lstCities.size(); i++) {
            String sTemp = ((FList)lstCities.get(i)).get(WtbFldCity.getInst());
            String sTempProv = ((FList)lstCities.get(i)).get(WtbFldProvince.getInst());
            if(sTemp.equals(sTempCity)) {
                out.print("<option title='"+ sTempProv +"'  selected value='" + sTemp + "'>" + sTemp + "</option>");
            } else {
                out.print("<option  title='"+ sTempProv +"'    value='" + sTemp + "'>" + sTemp + "</option>");
            }
        }
    }
                                                    }
                                                    %>
                                        </select><p class=validationmsg></p></TD></TR>
                                        <TR>
                                            <TD class=textboxBur align=left><div id=lblZone>Sector</div></TD>
                                            <TD align=left>
                                                <div class="normalGrayFont" id="zone_div">
                                                    <select class="textboxGray" name="Zone" onchange="javascript: GetPhases(this.value);">
                                                        <option value="Select">Select</option>
                                                    </select><p class=validationmsg></p>
                                                </div>
                                            </TD>
                                        </TR>
                                        <!--code added by satya-->
                                        <TR STYLE="display:none" id="lahore_subzone">
                                            <TD class=textboxBur align=left><div id=lblSubZone>Phases</div></TD>
                                            <TD class=normalColoredFont align=left>
                                                <div class="normalGrayFont" id="subzone_div">
                                                    <select class="textboxGray" name="SubZone" onchange="javascript: GetBlocks(this.value);">
                                                        <option value="Select">Select</option>
                                                    </select><p class=validationmsg></p>
                                        </div></TD></TR>
                                        <TR STYLE="display:none" id="lahore_block">
                                            <TD class=textboxBur align=left><div id=lblBlock>Blocks</div></TD>
                                            <TD class=normalColoredFont align=left>
                                                <div class="normalGrayFont" id="block_div">
                                                    <select class="textboxGray" name="Block" onchange="javascript: GetStreets(this.value);">
                                                        <option value="Select">Select</option>
                                                    </select><p class=validationmsg></p>
                                        </div></TD></TR>
                                        <TR>
                                            <TD class=textboxBur align=left><div id=lblStreet>Street / Category</div></TD>
                                            <TD class=normalColoredFont align=left>
                                                <div class="normalGrayFont" id="street_div">
                                                    <select class="textboxGray" name="Street" onchange="javascript: GetHouses(this.value);">
                                                        <option value="Select">Select</option>
                                                    </select><p class=validationmsg></p>
                                        </div></TD></TR>
                                        <TR>
                                            <TD class=textboxBur align=left><div id=lblHouseno>Plot No / Name</div></TD>
                                            <TD class=normalColoredFont align=left>
                                                <div class="normalGrayFont" id="house_div">
                                                    <select class="textboxGray" name="HouseNo" onchange="javascript:locate();" >
                                                        <option value="Select">Select</option>
                                                    </select><p class=validationmsg></p>
                                        </div></TD></TR>
                                        <tr ><td>&nbsp;</td><td align="left">
                                                <input type ="hidden" name="TASK" value="QUERY"/>
                                                <input type ="hidden" name="Response" value="">
                                                <input type=hidden name=nationality value="<%=strNationality%>">
                                                <INPUT Type="hidden" Name="referrer" Value="<%=DSTEPCT%>">
                                                <INPUT Type="hidden" Name="state" Value="">
                                                <input type=image src="images/btn-locate.gif" onclick='javascript: locate();'  onmouseover="this.src='images/btn-locate-on.gif'" onmouseout="this.src='images/btn-locate.gif'">
                                                
                                        </td></tr>
                        </TBODY></TABLE></DIV></FORM>
                        <!-- Address form Ends here -->
                        <!-- Manual Address form Starts here -->
                        <FORM method=post name=manualaddress onsubmit='return(false);'">
                            <DIV style="DISPLAY: none" id=manual>
                                
                                <TABLE  class=ct-info>
                                    <TBODY>
                                        <tr><th colspan=2><SPAN 
                                            style="COLOR: #c60551; FONT-SIZE: 12px"><STRONG>Address</STRONG></SPAN></th></tr>
                                        <TR height=22>
                                            <TD class=textboxBur id="lblPlot">Plot. No.</TD>
                                            <TD class=normalColoredFont ALIGN="left"><INPUT type=hidden name=gc> 
                                            <INPUT type=hidden name=coverage> <INPUT class=textboxGray MAXLENGTH="50"  type=text name=HouseNo  value=""><p class=validationmsg></p></TD>
                                        </TR>
                                        <TR height=22>
                                            <TD class=textboxBur id="lblmStreet">Street / Road</TD>
                                            <TD class=normalColoredFont align=left><INPUT MAXLENGTH="50"  class=textboxGray type=text name=Street value=""><p class=validationmsg></p></TD>
                                        </TR>
                                        <!--added by satya-->
                                        <TR STYLE="display:none" id="lahore_mblock">
                                            <TD class=textboxBur align=left><div id=lblBlock>Blocks</div></TD>
                                            <TD class=normalColoredFont align=left><INPUT MAXLENGTH="50"  class=textboxGray id="block_div" type=text name="SubZone" value=""><p class=validationmsg></p></TD>
                                        </TR>
                                        <TR STYLE="display:none" id="lahore_msubzone">
                                            <TD class=textboxBur align=left><div id=lblSubZone>Phases</div></TD>
                                            <TD class=normalColoredFont align=left><INPUT MAXLENGTH="50"  class=textboxGray id="subzone_div" type=text name="SubZone" value=""><p class=validationmsg></p></TD>
                                        </TR>
                                        <TR height=22>
                                            <TD class=textboxBur id="lblSector">Sector</TD>
                                            <TD class=normalColoredFont><INPUT class=textboxGray MAXLENGTH="50" type=text name=Zone value=""><p class=validationmsg></p></TD>
                                        </TR>
                                        <TR>
                                            <TD class=textboxBur height=22>City</TD>
                                            <TD><select class="textboxGray" name="city" onchange='javascript: GetLabels(this.value);'>
                                                    <%
                                                    if(lstCities != null) {
                                                    if(lstCities.size() > 0) {
                                                        String sTempCity = ((String)lcSvcCity.getLightData() == null ? "" : (String)lcSvcCity.getLightData());
                                                        for(int i=0; i< lstCities.size(); i++) {
                                                            String sTemp = ((FList)lstCities.get(i)).get(WtbFldCity.getInst());
                                                            String sTempProv = ((FList)lstCities.get(i)).get(WtbFldProvince.getInst());
                                                            if(sTemp.equals(sTempCity)) {
                                                                out.print("<option title='"+ sTempProv +"'  selected value='" + sTemp + "'>" + sTemp + "</option>");
                                                            } else {
                                                                out.print("<option  title='"+ sTempProv +"'    value='" + sTemp + "'>" + sTemp + "</option>");
                                                            }
                                                        }
                                                    }
                                                                                                    }
                                              %>
                                            </select><p class=validationmsg></p></TD>
                                        </TR>
                                        <TR>
                                            <TD class=textboxBur>Latitude</TD>
                                            <TD class=normalColoredFont><INPUT class=textboxGray type=text name=y MAXLENGTH="50"><p class=validationmsg></p></TD>
                                        </TR>
                                        <TR>
                                            <TD class=textboxBur>Longitude</TD>
                                            <TD class=normalColoredFont><INPUT class=textboxGray type=text name=x MAXLENGTH="50"><p class=validationmsg></p></TD>
                                        </TR>
                                        
                                        <TR>
                                            <TD><INPUT value=QUERY type=hidden name=TASK>
                                                <input type=hidden name=state value="">
                                                <input type=image name="locate" id="locate" src="images/btn-locate.gif" onclick='javascript:locatemanual();'   onmouseover="this.src='images/btn-locate-on.gif'" onmouseout="this.src='images/btn-locate.gif'">
                                            </TD>
                                            <TD>
                                                <input type=hidden name="address1" value="-   -   -   -">
                                                <INPUT TYPE="hidden" NAME="zip" value="" >
                                                <input type=hidden name="country" value="<%=DEFAULTCOUNTRY%>">
                                                <input type=hidden name="coverage_type" value="">
                                                <input type=hidden name=latitude value="">
                                                <input type=hidden name=longitude value="">
                                                <input type=hidden name=sla value="">
                                                <input type=hidden name=nationality value="<%=strNationality%>">
                                                <INPUT Type="hidden" Name="referrer" Value="<%=DSTEPCT%>">
                                                <input type=hidden name=salesweb value="DEALER">
                                                <!--<input type=image src="images/Bypass.gif" onclick='javascript:bypassCoverage(document.manualaddress);'  onmouseover="this.src='images/Bypass-on.gif'" onmouseout="this.src='images/Bypass.gif'">-->
                                            </TD>
                                        </TR>
                                    </TBODY>
                                </TABLE>
                            </DIV>
                        </FORM>
                        <!-- Manual Address form Ends here -->
                    </td>
                    <td align=left width=30% >
                        <!-- Utility Layers form starts here -->
                                                    
                        <DIV id=kml_div2  style="color:#7f7f7f">
                            <FORM name=kmlBoxes2>
                                <table border=0  cellpadding=0 cellspacing=0  class=ct-info>
                                    <tr><th colspan=2><SPAN 
                                        style="COLOR: #c60551; FONT-SIZE: 12px"><STRONG>Coverage Layers</STRONG></SPAN></th></tr>
                                    <tr>
                                        <td width=10%><INPUT onclick=javascript:swapUSBCoverage(); value=USB type=checkbox name=U></td><td width=90%><LABEL>USB</LABEL>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><INPUT onclick=javascript:swapInDoorCoverage(); value=InDoor type=checkbox name=I></td><td><LABEL>In Door</LABEL>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><INPUT onclick=javascript:swapOutDoorCoverage(); value=OutDoor type=checkbox name=O></td><td><LABEL>Out Door</LABEL>
                                        </td>
                                    </tr>
                                </table>
                                
                            </FORM>
                        </DIV>
                        
                        <!-- Utility Layers form ends  here -->
                    </td>
                    <td width=30%>
                        <DIV id=kml_div >
                            <FORM name=kmlBoxes>
                                <table  border=0 cellpadding=0 cellspacing=0 class=ct-info>
                                    <tr><th><SPAN 
                                        style="COLOR: #c60551; FONT-SIZE: 12px"><STRONG>Digital Maps</STRONG></SPAN></th></tr>
                                    <tr>
                                        <td><INPUT onclick="" value=Parcels type=checkbox name=P> <LABEL>Parcels</LABEL>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <INPUT onclick=javascript:swapRoadsKML(); value=Roads type=checkbox name=R> <LABEL>Roads</LABEL>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><INPUT onclick=javascript:swapZonesKML(); value=Zones type=checkbox name=Z> <LABEL>Zones</LABEL></td>
                                    </tr>
                                    <tr>
                                        <td><INPUT onclick=javascript:swapInput(); value=ad_input type=checkbox name=ad_input> <LABEL>Address is not found</LABEL>
                                        </td>
                                    </tr>
                                </table>
                            </FORM>
                        </DIV>
                    </td>
                </tr>
                <script lanaguage=javascript>
                                                function validateCTForm(frm) {
                                                    //alert(frm.nationality.value);
                                                    //alert(frm.referrer.value);
                                                    //alert(frm.gc.value);
                                                    frm.latitude.value = frm.y.value;
                                                    frm.longitude.value = frm.x.value;
                                                    frm.coverage_type.value=frm.coverage.value;
                                                    var sHNo, sStreet, sZone;
                                                    
                                                    if(ResultType == 'AUTO') {
                                                        sHNo = trimString(document.address.HouseNo.value);
                                                        sStreet = trimString(document.address.Street.value);
                                                        sZone = trimString(document.address.Zone.value);
                                                        if(sHNo =='') sHNo = "-";
                                                        if(sStreet == '') sStreet = '-';
                                                        if(sZone == '') sZone = '';
                                                        frm.address1.value =  sHNo;
                                                        frm.address1.value = frm.address1.value  + "   " + sStreet;
                                                        frm.address1.value = frm.address1.value  + "   " + "_";
                                                        frm.address1.value = frm.address1.value  + "   " + sZone;
                                                        frm.city.value = document.address.City.value;
                                                        frm.state.value = document.address.state.value;
                                                    } else {
                                                        sHNo = trimString(document.manualaddress.HouseNo.value);
                                                        sStreet = trimString(document.manualaddress.Street.value);
                                                        sZone = trimString(document.manualaddress.Zone.value);
                                                        if(sHNo =='') sHNo = "-";
                                                        if(sStreet == '') sStreet = '-';
                                                        if(sZone == '') sZone = '';
                                                        frm.address1.value = sHNo ;
                                                        frm.address1.value = frm.address1.value  + "   " + sStreet;
                                                        frm.address1.value = frm.address1.value  + "   " + "_";
                                                        frm.address1.value = frm.address1.value  + "   " + sZone;
                                                        frm.city.value = document.manualaddress.city.value;
                                                        frm.state.value = document.manualaddress.state.value;
                                                    }
                                                    /*if(frm.cvgchkstatue.value == 'not covered')
                                                    {
                                                        var answer = confirm ("Area is not under Coverage Location. You cannot create the account. Do you want to continue bypassing the Coverage Check?");
                                                        if(!answer) {
                                                            return(false);
                                                        }
                                                        else {
                                                            frm.coverage_type.value = frm.coverage_type.value + " (Bypassed)";
                                                            return(true);
                                                        }
						  }
                                                    else {
                                                        return(true);
                                                       }*/
                                                    return(true);
                                                }
                </script>                                            
                <tr   class='info-listing'><td colspan=3>
                        <FORM class=bigBoldFont method=post name=locater action="./DealerAccountCreationStep1Action.do" onsubmit='return validateCTForm(this)'>
                            <DIV id=locate_div class=normalGrayFont align=center>
                                <!-- Generated out put  for http://oe-fut.wi-tribe.net.pk/dev/coverage/search_gc.php?HouseNo=145&street=11&zone=MARGALLA TOWN&City=Islamabad-->
                                <!-- Retun output format structure begin-->
                                                                                
                                <!-- REturn output format structure - ends -->
                                                                                
                                <!--  Generated output ends here -->
                            </DIV>
                            <input type=hidden name="address1" value="-   -   -   -">
                            <input type=hidden name="city" value="Islamabad">
                            <INPUT TYPE="hidden" NAME="zip" value="" >
                            <INPUT TYPE="hidden" NAME="state" value="Karachi">
                            <input type=hidden name="country" value="<%=DEFAULTCOUNTRY%>">
                            <input type=hidden name="coverage_type" value="">
                            <input type=hidden name=latitude value="">
                            <input type=hidden name=longitude value="">
                            <input type=hidden name=sla value="">
                            <input type=hidden name=nationality value="<%=strNationality%>">
                            <INPUT Type="hidden" Name="referrer" Value="<%=DSTEPCT%>">
                            <input type=hidden name=salesweb value="DEALER">
                        </FORM>
                        
                </td></tr>
            </table>
            <SCRIPT language="javascript">
    x="73.03311243";
    y="33.68435332";
    //y= "31.4869428108";
   // x = "74.325256347";
    initialize(x,y);
            </SCRIPT>
        </div>
    </div>
    
    
    <!-- Inner content ends here -->                        
    </div>
    
</body>
</html>
