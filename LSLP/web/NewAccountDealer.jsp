


<%@ page contentType="text/html;charset=UTF-8" language="java" import="customfields.*, com.portal.bas.*, com.portal.bas.comp.*, com.portal.pcm.* , com.portal.app.ccare.comp.*, Wtb.MyAccount.*, com.portal.web.comp.*, com.portal.web.*, java.net.*, java.util.*, java.text.*" %>

<%@ include file="includes/constants.jsp"%>
<%@ include file="includes/ServiceConstants.jsp"%>

<script type="text/javascript" src="js/jquery.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="js/script.js"></script>
<script language="javascript" type="text/javascript" src="js/date.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.datePicker.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.bgiframe.js"></script>
<script language="javascript" type="text/javascript" src="js/utilfunctions.js"></script>
<script language="javascript" type="text/javascript" src="js/validations.js"></script>
<script language="javascript" type="text/javascript" src="js/validates.js"></script>

<%
session.removeAttribute("accountCreationBean");
session.removeAttribute(PLANS_BEAN);
session.removeAttribute(SERVICE_DESCR);
String ncompanyUrl = "http://www.wi-tribe.pk/";
String nAboutUs = "overview.php";
String nProductSvs = "product.php";
String nCoverage ="";
String nMediaCenter = "press_releases.php";
String nCareer = "careers.php";
String nContact = "contact_us.php";

String reffrom = request.getParameter("salesweb");
String urlFrom = request.getHeader("referer");
String salesId = request.getParameter("Dealerid");
if (reffrom == null)
    reffrom = "";
if (salesId == null)
    salesId = "";

//Only User can come from webportal can access this page.
if(reffrom.equals("DEALER")){
//
} else {
    response.sendRedirect("index.jsp");
}
%>
<jsp:useBean id="accountCreationBean" class="com.portal.bas.comp.PIAComponentCollection" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%
PLightComponentHelper lcSalutation, lcFirstName, lcMiddleName, lcLastName, lcSvcAddress, lcSvcCity, lcSvcState, lcSvcZip, lcSvcCountry, lcSvcEmail, lcCNIC, lcDOB;
PLightComponentHelper lcHomePhone, lcWorkPhone, lcFaxPhone, lcMobilePhone;
PLightComponentHelper lcBusinessType, lcTitle, lcOrganization, lcBillType, lcActSvsType;
//PLightComponentHelper lcCoverageType, lcLatitude, lcLongitude, lcSLA, lcParent, lcAffinitySelectedSlab, lcReferredBy, lcDCL, lcDCP;
PLightComponentHelper lcCoverageType, lcLatitude, lcLongitude, lcSLA, lcParent, lcAffinitySelectedSlab, lcReferredBy;

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
    lcReferredBy = ServletUtil.addComponent(accountCreationBean, "ReferredBy", "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldReferredBy","FldProfileObj.WtbFldCustinfo.WtbFldReferredBy"); 
    // Business Type
    lcBusinessType = ServletUtil.addComponent(accountCreationBean, WTB_BUS_TYPE,"FldAcctinfo[0].FldBusinessType");
    //CNIC & Passport No
    lcCNIC = ServletUtil.addComponent(accountCreationBean, IDENTITY,"FldAcctinfo[0].FldAccessCode1");
    ServletUtil.addComponent(accountCreationBean, TPIN,"FldAcctinfo[0].FldAccessCode2");
    //Date of Birth
    lcDOB = ServletUtil.addComponent(accountCreationBean, WTB_DOB, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldDobT","FldProfileObj.WtbFldCustinfo.WtbFldDobT");
    //Occupation
    lcTitle = ServletUtil.addComponent(accountCreationBean, TITLE, "FldNameinfo[1].FldTitle");
    //Organization
    lcOrganization = ServletUtil.addComponent(accountCreationBean, COMPANY, "FldNameinfo[1].FldCompany");
    //Payment method
    lcBillType = ServletUtil.addComponent(accountCreationBean, BILL, "FldBillinfo[0].FldPayType");
    //ContractPeriod , Start Date, End date
    ServletUtil.addComponent(accountCreationBean, CONTRACT_PERIOD, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractPeriod","FldProfileObj.WtbFldContractDetails.WtbFldContractPeriod");
    ServletUtil.addComponent(accountCreationBean, CONTRACT_START, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractStartT","FldProfileObj.WtbFldContractDetails.WtbFldContractStartT");
    ServletUtil.addComponent(accountCreationBean, CONTRACT_END, "FldProfiles[1].FldInheritedInfo.WtbFldContractDetails.WtbFldContractEndT","FldProfileObj.WtbFldContractDetails.WtbFldContractEndT");
    
    //Package 
    ServletUtil.addComponent(accountCreationBean, PROMO_PKG,"FldAcctinfo[0].FldAacPackage");
    ServletUtil.addComponent(accountCreationBean, "lstAffinitySlab", "FldAcctinfo[0].FldAacSerialNum");
    //Service Login type
    ServletUtil.addComponent(accountCreationBean, SVS_LOGIN_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldServiceLoginType","FldProfileObj.WtbFldCustinfo.WtbFldServiceLoginType");
    //Taxation
    ServletUtil.addComponent(accountCreationBean, TAX_EXPT_TYPE, "FldProfiles[2].FldInheritedInfo.WtbFldTaxExemptions[0].WtbFldExemptionType","FldProfileObj.WtbFldTaxExemptions[any].WtbFldExemptionType");
    ServletUtil.addComponent(accountCreationBean, TAX_EXPT_PERCENT, "FldProfiles[2].FldInheritedInfo.WtbFldTaxExemptions[0].WtbFldPercent","FldProfileObj.WtbFldTaxExemptions[any].WtbFldPercent");
    ServletUtil.addComponent(accountCreationBean, PAYMENT_ALERT, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldPymtalertSubscriptionId","FldProfileObj.WtbFldCustinfo.WtbFldPymtalertSubscriptionId");
    
    // account type
    lcActSvsType = ServletUtil.addComponent(accountCreationBean, WTB_ACCT_SERVICE_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldAccountServiceType","FldProfileObj.WtbFldCustinfo.WtbFldAccountServiceType");

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
    lcCoverageType = ServletUtil.addComponent(accountCreationBean, COVERAGE_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtCoverageType","FldProfileObj.WtbFldCustinfo.WtbFldCtCoverageType");
    ServletUtil.addComponent(accountCreationBean, LEAD_IDENTITY, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldLeadIdentity","FldProfileObj.WtbFldCustinfo.WtbFldLeadIdentity");
    
    lcLatitude	= ServletUtil.addComponent(accountCreationBean, LATITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLatitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLatitude");
    lcLongitude = ServletUtil.addComponent(accountCreationBean, LONGITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLongitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLongitude");
    lcSLA = ServletUtil.addComponent(accountCreationBean, SLA, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtSla","FldProfileObj.WtbFldCustinfo.WtbFldCtSla");
    ServletUtil.addComponent(accountCreationBean, SALES_ID, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldSalesId","FldProfileObj.WtbFldCustinfo.WtbFldSalesId");
    ServletUtil.addComponent(accountCreationBean, CPE_DEVICE_ID, "FldDevices[0].FldDeviceObj");
// Parent- Child Configuaration
	lcParent = ServletUtil.addComponent(accountCreationBean, PARENT, "FldGroupInfo[0].FldParent");
} else {
    
    lcSalutation = accountCreationBean.getChild(SALUTE); 
    lcFirstName = accountCreationBean.getChild(FIRSTNAME);
    lcMiddleName = accountCreationBean.getChild(MIDDLENAME);
    lcLastName = accountCreationBean.getChild(LASTNAME);


    lcSvcCity = accountCreationBean.getChild(CITY);

    lcSvcEmail = accountCreationBean.getChild(EMAIL);
    lcDOB = accountCreationBean.getChild(WTB_DOB);
    lcHomePhone = accountCreationBean.getChild(HOMEPHONE);
    lcWorkPhone = accountCreationBean.getChild(WORKPHONE);
    lcFaxPhone = accountCreationBean.getChild(FAX );
    lcMobilePhone = accountCreationBean.getChild(MOBILE);
    lcBusinessType = accountCreationBean.getChild(WTB_BUS_TYPE);
    lcTitle =  accountCreationBean.getChild(TITLE);
    lcOrganization = accountCreationBean.getChild(COMPANY);
    lcBillType = accountCreationBean.getChild(BILL); 
    lcActSvsType = accountCreationBean.getChild(WTB_ACCT_SERVICE_TYPE); 
    lcReferredBy = accountCreationBean.getChild("ReferredBy"); 
}

WtbSelfCareUtility wsutil  = (WtbSelfCareUtility) pCS.createController("Wtb.MyAccount.WtbSelfCareUtility");
Properties props = pCS.getDefaultProperties();

wsutil.setProperties(props);
List lstCities = wsutil.getCityList();
SimpleDateFormat formatter = new SimpleDateFormat(props.getProperty("applicationdateformat")); 
String brand=null;
%>


<%
PIACAPackageInfoBean plansBean = (PIACAPackageInfoBean) pCS.createController("com.portal.app.ccare.comp.PIACAPackageInfoBeanImpl");
String pricingPlanStr = props.getProperty(PRICEPLANSPECIFIER);
if (pricingPlanStr == null) {
    pricingPlanStr = DEFAULTPRICEPLAN;
}
PModelHandle plansModel = null;
try {
 plansModel = (PModelHandle) plansBean.listOfPlans(pricingPlanStr);
} catch(Exception e) {
	System.out.println(e.toString());
}
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
String sectionImage = "text-login.gif";
//Billing: text-billing1.gif ;  MyAccount: text-my-account.gif
%>
 
    <script type="text/javascript" charset="utf-8">
$(function()
{
	$('.date-pick').datePicker({startDate:'01/01/1900'});
});
    </script>
    <script language=javascript>
    //Pagelevel -  Validations.
    var http = createRequestObject(); 

    var Fwin = null;
    Fwin_Wid = 400;
    Fwin_Hgt = 240;
    Fwin_Left = (screen) ? screen.width/2 - Fwin_Wid/2 : 100;
    Fwin_Top =  (screen) ? screen.availHeight/2 - Fwin_Hgt/2 : 100;
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
                 <div id="inner-content">
                               
                                <P>Fields marked with an asterisk (<span class=mandatory>*</span>) are mandatory.</P>
                                <!-- message end -->
                                <div class="shadow-box">
                                    <div class="shadow-box-inner">
                                        <form name=createFormStep1_Dealer   method=post action="./DealerAccountCreationStep1Action.do" onsubmit="javascript: return validateDealerNewAcctForm(this)">
                                            <table width="90%" border="0" cellspacing="0" cellpadding="0" class="Act-listing">
                                                <tr   class='info-listing'><td><label>Sales Id</label></td><td><label><%=salesId%></label><input type="hidden" readonly name="<%=SALES_ID%>" value="<%=salesId%>"></td></tr>
                                                <tr  class='info-listing'><td><label>Account Type<SPAN class=mandatory>*</SPAN></label></td><td colspan=3>
                                                        <select class=secectxx name="<%=WTB_BUS_TYPE%>" id="accttype" >
                                                            <option value=1>Individual</option>
                                                            <!--<option value=2>Corporate</option>-->
                                                            <option value=3>Affinity Child</option>
							    <option value=4>Affinity</option>
                                                            <option value=5>FUT</option>
                                                            <!--<option value=7>SOHO</option>-->
                                                            <option value=8>Employee</option>
                                                            <option value=9>Test</option>
                                                            
                                                </select></td></tr><tr height=25><th colspan=4 align=center>Personal Information</th></tr>
                                                <tr  class='info-listing'><td><label>Title<SPAN class=mandatory>*</SPAN></label></td><td colspan=3>
                                                        <select class="secectxx" name=<%=SALUTE%>>
                                                            <% 
                                                            
                                                            {
    String titles[] =props.getProperty("consumerSalutation").split("\\|");
    String salutation = ((String)lcSalutation.getLightData() == null ? "Mr." : (String) lcSalutation.getLightData());
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
                                                <tr  class='info-listing'><td width=100><label>First Name<SPAN class=mandatory>*</SPAN></label></td><td colspan=3><input class="txtxxx" type=text name="<%=FIRSTNAME%>" value="<%=((String)lcFirstName.getLightData() == null ? "" : (String)lcFirstName.getLightData())%>" id="FirstName" maxlength="25"><P class=validationmsg></P></td></tr>
                                                <tr  class='info-listing'><td><label>Middle Name</label></td><td colspan=3><input class="txtxxx"  type=text name="<%=MIDDLENAME%>" value="<%=((String)lcMiddleName.getLightData() == null ? "" : (String)lcMiddleName.getLightData())%>" maxlength="25"><P class=validationmsg></P></td></tr>
                                                <tr  class='info-listing'><td><label>Last Name<SPAN class=mandatory>*</SPAN></label></td><td  colspan=3><input class="txtxxx"  type=text name="<%=LASTNAME%>" value="<%=((String)lcLastName.getLightData() == null ? "" : (String)lcLastName.getLightData())%>" maxlength="25"><P class=validationmsg></P></td></tr>
                                                <tr class='info-listing'><td><label>Nationality<span class="mandatory">*</span></label></td><td>
                                                        <select name=nationality id="nationality1">
                                                            <option value=1>Pakistani</option>
                                                            <option value=2>Foreigner</option>
                                                        </select>
                                                </td><td><label>Date of Birth<SPAN class=mandatory>*</SPAN></label></td><td><input type="text" value="" class=" date-pick dp-applied" name="<%= WTB_DOB %>" id="dob" maxlength="10"  /><P class=validationmsg></P></td></tr>
                                                
                                                
                                                <tr><th colspan=4>Contact No's </th></tr>
                                                <tr  class='info-listing'><td><label>Home<span class="mandatory">&nbsp;*</span></label></td><td><input class="txtxx"  type=text name="<%=HOMEPHONE%>" value="<%=((String)lcHomePhone.getLightData() == null ? "" : (String)lcHomePhone.getLightData())%>" id="homephone" maxlength="25"><p class=validationmsg></p></td><td><label>Work</label></td><td><input class="txtxx"  type=text name="<%=WORKPHONE%>" id="workphone" maxlength="25" value="<%=((String)lcWorkPhone.getLightData() == null ? "" : (String)lcWorkPhone.getLightData())%>"><P class=validationmsg></P></td></tr>
                                                <tr  class='info-listing'><td><label>Mobile<span class="mandatory">&nbsp;*</span></label></td><td>  <input class="txtxx"  type=text name="<%=MOBILE%>" maxlength="25" value="<%=((String)lcMobilePhone.getLightData() == null ? "" : (String)lcMobilePhone.getLightData())%>" id="mobilephone"><P class=validationmsg></P></td><td><label>Fax</label></td><td><input class="txtxx"  maxlength="25" id=faxphone type=text name="<%=FAX%>" value="<%=((String)lcFaxPhone.getLightData() == null ? "" : (String)lcFaxPhone.getLightData())%>"><P class=validationmsg></P></td></tr>
                                                <tr  class='info-listing'><td><label>Email<SPAN class=mandatory>*</SPAN></label></td><td  colspan=3><input class="txtxxx"  type=text name="<%=EMAIL%>"  value="<%=((String)lcSvcEmail.getLightData() == null ? "" : (String)lcSvcEmail.getLightData())%>" maxlength="50"><P class=validationmsg></P></td></tr>





                                                <!-- New Chnages By MD USMAN-->







                                                          <%--
                                          Address Field is Started

                                            --%>&nbsp;*

                                        <tr height=25><th colspan=4 align=center>Address</th></tr>

                                        <tr class='info-listing'>
                                            <td><label><font color="black">Plot. No.</font><span class=mandatory>*</span></label></td>

                                            <td>
                                                <INPUT type=hidden name=gc><INPUT type=hidden name=coverage>
                                                <INPUT class=txtxx MAXLENGTH="50"  type=text name=sHNo  property="sHNo" value=""><p class=validationmsg></p>
                                            </td>                                                          
                                            <td>
                                                 <label><font color="black">Street / Road</font><span class=mandatory >*</span></label>
                                            </td>

                                            <TD><INPUT MAXLENGTH="50"  class=txtxx type=text name=sStreet property="sStreet" value=""><p class=validationmsg></p></TD>
                                        </tr>
                                                            <!--added by satya-->

                                            <!--
                                            <tr id="lahore_mblock" class='info-listing'>
                                                   <TD class=txtxx><div id=lblBlock>Blocks</div></TD>
                                                   <TD class=normalColoredFont align=left><INPUT MAXLENGTH="50"  class=textboxGray id="block_div" type=text name="SubZone" value=""><p class=validationmsg></p></TD>
                                                </tr>

                                                <tr STYLE="display:none" id="lahore_msubzone">
                                                    <TD class=textboxBur align=left><div id=lblSubZone>Phases</div></TD>
                                                    <TD class=normalColoredFont align=left><INPUT MAXLENGTH="50"  class=textboxGray id="subzone_div" type=text name="SubZone" value=""><p class=validationmsg></p></TD>
                                                </tr>
                                            -->




                                        <tr class='info-listing'>
                                            <td><label><font color="black">Sector</font><span class=mandatory>*</span></label></td>
                                            <td><INPUT class=txtxx MAXLENGTH="50"  type=text name=sZone  property="sZone" value=""><p class=validationmsg></p></td>
                                            <td><label><font color="black">City</font><span class=mandatory >*</span></label></td>

                                            <td><select class="textboxGray" name="city" onchange='javascript: GetLabels(this.value);'>
                                                                        <%
                                                                                    if (lstCities != null) {
                                                                                        if (lstCities.size() > 0) {
                                                                                            String sTempCity = ((String) lcSvcCity.getLightData() == null ? "" : (String) lcSvcCity.getLightData());
                                                                                            for (int i = 0; i < lstCities.size(); i++) {
                                                                                                String sTemp = ((FList) lstCities.get(i)).get(WtbFldCity.getInst());
                                                                                                String sTempProv = ((FList) lstCities.get(i)).get(WtbFldProvince.getInst());
                                                                                                if (sTemp.equals(sTempCity)) {
                                                                                                    out.print("<option title='" + sTempProv + "'  selected value='" + sTemp + "'>" + sTemp + "</option>");
                                                                                                } else {
                                                                                                    out.print("<option  title='" + sTempProv + "'    value='" + sTemp + "'>" + sTemp + "</option>");
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                        %>
                                                                     

                                                                    </select> <p class=validationmsg></p>
                                            </td>
                                        </tr>



                                        <tr class='info-listing'>
                                            <td><label><font color="black">Latitude</font><span class=mandatory>*</span></label></td>

                                            <td>
                                                <input class=txtxx MAXLENGTH="50"  type=text name=<%= LATITUDE %>  property="sLatitude" MAXLENGTH="50" id="sLatitude"></input><p class=validationmsg></p>
                                            </td>
                                            <td>
                                                 <label><font color="black">Longitude</font><span class=mandatory >*</span></label>
                                            </td>

                                            <TD><input class=txtxx type=text name=<%= LONGITUDE %> property="sLongitude" MAXLENGTH="50" id="sLongitude"></input><p class=validationmsg></p></TD>
                                        </tr>

                                        
                                                            
                                                                        <tr class='info-listing'>
                                                                            <td>
                                                                                &nbsp;
                                                                            </td>
                                                                        </tr>
                                                            <%--
                                                            end-Address Field is by USMAN

                                                            --%>


                                                            


                                                            <!-- change bu usman end -->













                                                <tr  class='info-listing'>
                                                    <td><label>Job title</label></td>
                                                    <td colspan=3><input type="text" class="txtxxx" name="<%=TITLE%>"  id="title" maxlength="50" value="<%=((String)lcTitle.getLightData() == null ? "" : (String)lcTitle.getLightData())%>" /><P class=validationmsg></P></td>
                                                </tr>
                                                <tr  class='info-listing'>
                                                    <td ><label>Company</label></td>
                                                    <td colspan="3"><input type="text" class="txtxxx" name="<%=COMPANY%>"  Value="" id="CompanyName" maxlength="50" value="<%=((String)lcOrganization.getLightData() == null ? "" : (String)lcOrganization.getLightData())%>" /><P class=validationmsg></P></td>
                                                </tr>
                                                <tr  class='info-listing'>
                                                    <td ><label>Referred By(i.e. 0.0.0.1-123)</label></td>
                                                    <td colspan="3"><input type="text" class="txtxx" name="ReferredBy1" Value=""  id="CompanyName" maxlength="50" value=""  /><P class=validationmsg></P>
													<input type=hidden name="ReferredBy" value=''>
													</td>
                                                </tr>
                                               
                                                <tr  class='info-listing'>
                                                    <td><label>Service Type</label></td>
                                                    <td colspan=3>
                                                        <select class=secectxx name="<%=WTB_ACCT_SERVICE_TYPE%>" onchange="document.createFormStep1_Dealer.<%=WTB_ACCT_SERVICE_TYPE%>.value=document.createFormStep1_Dealer.<%=WTB_ACCT_SERVICE_TYPE%>.value;">
                                                            <option <%=""%> value=1>Pre-Paid</option>
                                                            <option  <%=""%> value=2>Post paid</option>
                                                        </select>
                                                        <input  type=hidden name="<%=WTB_ACCT_SERVICE_TYPE%>" value="<%="1"%>">
                                                    </td>
                                                </tr>

                                                    <!--CHNGES NEW-->




                                                     <TD><INPUT value=QUERY type=hidden name=TASK></INPUT>
                                                                        <input type=hidden name=state value=""></input>
                                                                        <TD>
                                                                            <input type=hidden name="address1" value=""></input>
                                                                            <INPUT TYPE="hidden" NAME="zip" value="" ></INPUT>
                                                                            <input type=hidden name="country" value="<%=DEFAULTCOUNTRY%>"></input>
                                                                            <input type=hidden name="coverage_type" value=""></input>
                                                                            <input type=hidden name=latitude value=""></input>
                                                                            <input type=hidden name=longitude value=""></input>
                                                                            <input type=hidden name=sla value=""></input>
                                                                            <input type=hidden name=nationality value=nationality></input>

                                                                            
                                                                                <input type=hidden name=salesweb value="DEALER"></input>
                                                                                <!--<input type=image src="images/Bypass.gif" onclick='javascript:bypassCoverage(document.manualaddress);'  onmouseover="this.src='images/Bypass-on.gif'" onmouseout="this.src='images/Bypass.gif'">-->
                                                                        </TD>

                                                                        <!-- hidden fields & End Wi-tribe Changes @23Dec2010
                                                                        <!-- hidden fields &Start Wi-tribe Changes @25JAN2010





                                                    <!--ENDCHNGES-->

                                                <tr><td colspan=4><!-- hidden fields -->
                                                        <INPUT Type="hidden" Name="referrer" Value="<%=DSTEP1%>">
                                                        <INPUT Type="hidden" Name="brand" Value="<%=brand%>">
                                                        <input type="hidden" name="Deliver_method" value="1"><!-- 0-Email &&& 1-Postal --> 
                                                        <INPUT Type="hidden" Name="<%=CURRENCY%>" Value="<%=props.getProperty(CURRENCYCODE)%>">
                                                        <INPUT Type="hidden" Name="<%=AACACCESS%>" Value="<%=props.getProperty(NEWACCOUNTLEAD)%>">
                                                        

                                                            


                                                        <input type="hidden" name="<%=LEAD_IDENTITY%>" value="<%=""%>">
                                                        <input type="hidden" name="<%=NEW_ACCT_BY%>" vlaue="<%=reffrom%>">
                                                        <input type=hidden name=salesweb value="DEALER">
                                                </td></tr>
                                                <tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th >
                                                        <input type="image" name="submit" src="images/btn-go.gif" alt="Request Change" title="Request Change" 
                                                               onmouseover="this.src='images/btn-go-on.gif'" onmouseout="this.src='images/btn-go.gif'" />
                                                </th></tr>
                                                <tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th >&nbsp;</th></tr>
                                            </table>
                                        </form>
                                    </div>
                                </div>
                                <!-- Inner content ends here -->                        
                            
            
        
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
 