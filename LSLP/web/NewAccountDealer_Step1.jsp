<%@ page contentType="text/html;charset=UTF-8" language="java" import="customfields.*, com.portal.bas.*,com.witribe.util.*, com.portal.bas.comp.*, com.portal.pcm.* , com.portal.app.ccare.comp.*, Wtb.MyAccount.*, com.portal.web.comp.*, com.portal.web.*, java.net.*, java.util.*, java.text.*" %>
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
%>
<jsp:useBean id="accountCreationBean" class="com.portal.bas.comp.PIAComponentCollection" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
PLightComponentHelper lcSalutation, lcFirstName, lcMiddleName, lcLastName, lcSvcAddress, lcSvcCity, lcSvcState, lcSvcZip, lcSvcCountry, lcSvcEmail, lcCNIC, lcDOB;
PLightComponentHelper lcHomePhone, lcWorkPhone, lcFaxPhone, lcMobilePhone;
PLightComponentHelper lcBusinessType, lcTitle, lcOrganization, lcBillType, lcActSvsType;
PLightComponentHelper lcCoverageType, lcLatitude, lcLongitude, lcSLA;
PLightComponentHelper lcContractPeriod, lcProPackage, lcContractStart, lcContractEnd,  lcActUsageLimit, lcSvsLoginType, lcTaxExptType, lcTaxExcptPercent, lcWtbActSvsType, lcSelPlan, lcParent, lcAffinitySelectedSlab;
PLightComponentHelper lcPaymentAlert;
PLightComponentHelper lcSalesID;
PLightComponentHelper lcMAC;

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
    lcSalesID = accountCreationBean.getChild(SALES_ID);

    // Business Type
    lcBusinessType = ServletUtil.addComponent(accountCreationBean, WTB_BUS_TYPE,"FldAcctinfo[0].FldBusinessType");
    //CNIC & Passport No(Individual) or NTN & SubId(Corporate)
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
    // added by muralidhar Application Type
    ServletUtil.addComponent(accountCreationBean, WTB_CLIENT_APP_NAME, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldClientAppName","FldProfileObj.WtbFldCustinfo.WtbFldClientAppName");
    // ended by muralidhar
    // added by muralidhar Payment Alert
    lcPaymentAlert = ServletUtil.addComponent(accountCreationBean, PAYMENT_ALERT, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldPymtalertSubscriptionId","FldProfileObj.WtbFldCustinfo.WtbFldPymtalertSubscriptionId");
    // ended by muralidhar
    // Additional Fields
    lcCoverageType = ServletUtil.addComponent(accountCreationBean, COVERAGE_TYPE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtCoverageType","FldProfileObj.WtbFldCustinfo.WtbFldCtCoverageType");
    ServletUtil.addComponent(accountCreationBean, LEAD_IDENTITY, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldLeadIdentity","FldProfileObj.WtbFldCustinfo.WtbFldLeadIdentity");

    lcLatitude	= ServletUtil.addComponent(accountCreationBean, LATITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLatitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLatitude");
    lcLongitude = ServletUtil.addComponent(accountCreationBean, LONGITUDE, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtLongitude","FldProfileObj.WtbFldCustinfo.WtbFldCtLongitude");
    lcSLA = ServletUtil.addComponent(accountCreationBean, SLA, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldCtSla","FldProfileObj.WtbFldCustinfo.WtbFldCtSla");
    ServletUtil.addComponent(accountCreationBean, SALES_ID, "FldProfiles[0].FldInheritedInfo.WtbFldCustinfo.WtbFldSalesId","FldProfileObj.WtbFldCustinfo.WtbFldSalesId");
    ServletUtil.addComponent(accountCreationBean, CPE_DEVICE_ID, "FldDevices[0].FldDeviceObj");
// Parent- Child Configuaration
    lcAffinitySelectedSlab = ServletUtil.addComponent(accountCreationBean, "lstAffinitySlab", "FldAcctinfo[0].FldAacSerialNum");
    lcParent = ServletUtil.addComponent(accountCreationBean, PARENT, "FldGroupInfo[0].FldParent");
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
    lcPaymentAlert = accountCreationBean.getChild(PAYMENT_ALERT);
    lcCoverageType = accountCreationBean.getChild(COVERAGE_TYPE );
    lcLatitude = accountCreationBean.getChild(LATITUDE);

    lcLongitude = accountCreationBean.getChild(LONGITUDE);
    lcSLA = accountCreationBean.getChild(SLA);
    lcSalesID = accountCreationBean.getChild(SALES_ID);
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
    lcAffinitySelectedSlab = accountCreationBean.getChild( "lstAffinitySlab");
}

WtbSelfCareUtility wsutil  = (WtbSelfCareUtility) pCS.createController("Wtb.MyAccount.WtbSelfCareUtility");
Properties props = pCS.getDefaultProperties();
wsutil.setProperties(props);

List lstContractPeriod = null;
try {
	lstContractPeriod = wsutil.getContractPeriod();
} catch(Exception ex) {
	System.out.println(ex.toString());
	ex.printStackTrace();
}
//out.print("<pre>" + wsutil.getDefaultContractPeriod() +"</pre>");


SimpleDateFormat formatter = new SimpleDateFormat(props.getProperty("applicationdateformat"));

String brand=null;
Map formInput = ServletUtil.gatherFormInput(request);
System.out.println(formInput.get(WTB_ACCT_SERVICE_TYPE));
String referrer = (String) formInput.get("referrer");
String sActSvsType = "";
String cBusinessType = "";

if (referrer.equals(DSTEP1))
          {
    ServletUtil.setLightDataForAll(formInput, accountCreationBean, APP_NAME);
    cBusinessType = (String)lcBusinessType.getLightData();
     if(formInput.get(WTB_DOB) != null && ((String)formInput.get(WTB_DOB)) != "") {

        //lcDOB.setLightData(formatter.parse((String)formInput.get(WTB_DOB)));
         // ----    Changes By PKAasimN         ----//
         // ----    Addition of 12 hours to DOB for Resolving Unix Date Issue  ---//

         System.out.println(formInput.get(WTB_DOB).toString());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println(java.util.TimeZone.getDefault().getID());
                int minutesToAdd = 720;  // 12 hrs
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(formInput.get(WTB_DOB).toString()));
                cal.add(Calendar.MINUTE, minutesToAdd);
                System.out.println(
                        cal.get(Calendar.DATE)
                + "/" + (cal.get(Calendar.MONTH)+1)
                + "/" + cal.get(Calendar.YEAR)
                + " " + cal.get(Calendar.HOUR)
                + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND) );

                lcDOB.setLightData(formatter.parse(cal.get(Calendar.DATE)
                + "/" + (cal.get(Calendar.MONTH)+1)
                + "/" + cal.get(Calendar.YEAR)
                + " " + cal.get(Calendar.HOUR)
                + ":" + cal.get(Calendar.MINUTE)
                + ":" + cal.get(Calendar.SECOND)));

                // ----    Changes Ended  ---//
    } else {
        lcDOB.setLightData(null);
    }

     if (sActSvsType.equals("1")) {
       lcBillType.setLightData(PREPAID);
      System.out.println("in if condition of prepaid");
    } else {
         System.out.println("in else condition of postpaid");
        lcBillType.setLightData(INVOICE);
    }
}
%>

<%
PIACAPackageInfoBean plansBean = (PIACAPackageInfoBean) pCS.createController("com.portal.app.ccare.comp.PIACAPackageInfoBeanImpl");
String pricingPlanStr = props.getProperty(PRICEPLANSPECIFIER);
if (pricingPlanStr == null) {
    pricingPlanStr = DEFAULTPRICEPLAN;
}
String cCity = ((String)lcSvcCity.getLightData()).trim().toLowerCase();
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


PModelHandle plansModel = null;
try {
	plansModel = (PModelHandle) plansBean.listOfPlans(pricingPlanStr);
} catch (Exception ex) {
	System.out.println(ex.toString());
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

        if(callFrom == 'DEVICE') {
		displaymsg = templateMsg.replace("[MsgToShow]", "Validating mac address. Please wait..");
	}
	if(callFrom == 'SERIALNO') {
		displaymsg = templateMsg.replace("[MsgToShow]", "Validating Serial Number. Please wait..");
	}
	document.getElementById('progressbar').innerHTML = displaymsg;
	document.getElementById('progressbar').style.visible="block";
	document.getElementById('progressbar').style.visibility="visible";
    }

    // ------------------------------------------ Changes by PKAasimN -----------------------------------------------------------------//
    function getMacDetails(type)
    {
    $('p.validationmsg').hide();
    document.frmCreate2.<%=CPE_DEVICE_ID%>.value = '';
    document.frmCreate2.<%=CPE_MAC_STATUS%>.value = '';
    document.getElementById("info").innerHTML='<label>' + ' ' + '</label>';
    document.getElementById("dtype").innerHTML= '<label>' + ' ' + '</label>';
    document.getElementById("dman").innerHTML= '<label>' + '  ' + '</label>';
    document.getElementById("dmdl").innerHTML= '<label>' +  ' ' + '</label>';
    document.getElementById("dwan").innerHTML='<label>' + ' ' + '</label>';
    document.getElementById("dnovoipnumbers").innerHTML='<label>' + ' ' + '</label>';
    document.frmCreate2.<%=CPE_MAC_ADDRESS%>.value = trimString(document.frmCreate2.<%=CPE_MAC_ADDRESS%>.value);
    document.frmCreate2.<%=CPE_SERIAL_NUMBER%>.value = trimString(document.frmCreate2.<%=CPE_SERIAL_NUMBER%>.value);
    var proceed = false;

    if(type == "0")
    {
        if(document.frmCreate2.<%=CPE_MAC_ADDRESS%>.value != "")
        proceed = true;
    }

    if(type == "1")
    {
        if(document.frmCreate2.<%=CPE_SERIAL_NUMBER%>.value != "")
        proceed = true;
    }
        if(proceed)
        {
            if(type == "0")
            {
                    showprogess('DEVICE');
            }
            else
            {
                    showprogess('SERIALNO');
            }
        var url="getDeviceDetails.jsp";
        var customerIDValue = '';
        var params = '';

         if(type == "0")
         {
             customerIDValue =  document.getElementById("<%=CPE_MAC_ADDRESS%>").value;
             params = "WANAddr="+customerIDValue+"&SalesId="+"<%=(String)lcSalesID.getLightData()%>";

          }
           else
           {
             customerIDValue =  document.getElementById("<%=CPE_SERIAL_NUMBER%>").value;
               params = "DeviceId="+customerIDValue+"&SalesId="+"<%=(String)lcSalesID.getLightData()%>";
           }

            http.open("POST", url , true);
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            http.setRequestHeader("Content-length", params.length);
            http.setRequestHeader("Connection", "close");
            http.onreadystatechange = handleDeviceDetails;
            http.send(params);
        }
       else
        {
            if(type == "0")

                {
                    showMessage(document.frmCreate2.<%=CPE_MAC_ADDRESS%>,'Enter MAC Address of the device.');
                }

            if(type == "1")
                {
                    showMessage(document.frmCreate2.<%=CPE_SERIAL_NUMBER%>,'Enter Serial Number of the device.');
                }
        }
}

function handleDeviceDetails()
{
	if(http.readyState == 4 && http.status == 200)
        {
		hideprogess();
                parseXML(http.responseText);
	}
}

function parseXML(text)
{
	try //Internet Explorer
	{
		xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async="false";
		xmlDoc.loadXML(text);
	}
	catch(e)
	{
		try //Firefox, Mozilla, Opera, etc.
		{
			parser=new DOMParser();
			xmlDoc=parser.parseFromString(text,"text/xml");
		}
		catch(e)
		{
			//alert(e.message);
			return;
		}
	}
document.getElementById("info").innerHTML='<label>' + xmlDoc.getElementsByTagName("INFO")[0].childNodes[0].nodeValue + '</label>';
//document.getElementById("did").innerHTML='<label>' + xmlDoc.getElementsByTagName("DEVICEID")[0].childNodes[0].nodeValue + '</label>';
//document.getElementById("dstate").innerHTML='<label>' + "" + xmlDoc.getElementsByTagName("DEVICESTATE")[0].childNodes[0].nodeValue + '</label>';
document.getElementById("dtype").innerHTML= '<label>' + xmlDoc.getElementsByTagName("DEVICETYPE")[0].childNodes[0].nodeValue + '</label>';
//document.getElementById("dlan").innerHTML='<label>' + xmlDoc.getElementsByTagName("LAN")[0].childNodes[0].nodeValue + '</label>';
document.getElementById("dman").innerHTML= '<label>' + xmlDoc.getElementsByTagName("MANUFACTURER")[0].childNodes[0].nodeValue + '</label>';
document.getElementById("dmdl").innerHTML= '<label>' +  xmlDoc.getElementsByTagName("MODEL")[0].childNodes[0].nodeValue + '</label>';
document.getElementById("dwan").innerHTML='<label>' + xmlDoc.getElementsByTagName("WAN")[0].childNodes[0].nodeValue + '</label>';
//document.getElementById("dpid").innerHTML='<label>' + xmlDoc.getElementsByTagName("POID")[0].childNodes[0].nodeValue + '</label>';
document.getElementById("dnovoipnumbers").innerHTML='<label>(' + xmlDoc.getElementsByTagName("NO_VOIP_PORTS")[0].childNodes[0].nodeValue + ')</label>';
document.frmCreate2.<%=CPE_DEVICE_ID%>.value=xmlDoc.getElementsByTagName("POID")[0].childNodes[0].nodeValue;
document.frmCreate2.<%=CPE_MAC_STATUS%>.value=xmlDoc.getElementsByTagName("INFO")[0].childNodes[0].nodeValue;
document.frmCreate2.<%=CPE_SERIAL_NUMBER%>.value = xmlDoc.getElementsByTagName("DEVICEID")[0].childNodes[0].nodeValue;
document.frmCreate2.<%=CPE_MAC_ADDRESS%>.value = xmlDoc.getElementsByTagName("WAN")[0].childNodes[0].nodeValue;
document.frmCreate2.VoIPEnabled.value = xmlDoc.getElementsByTagName("VOIP_ENABLED")[0].childNodes[0].nodeValue;
document.frmCreate2.NoOfVoIpPorts.value = xmlDoc.getElementsByTagName("NO_VOIP_PORTS")[0].childNodes[0].nodeValue;
document.frmCreate2.DeviceType.value = xmlDoc.getElementsByTagName("DEVICETYPE")[0].childNodes[0].nodeValue;

//get filter details from DB
loadContent(xmlDoc.getElementsByTagName("MODEL")[0].childNodes[0].nodeValue,xmlDoc.getElementsByTagName("DEVICETYPE")[0].childNodes[0].nodeValue);
}

// ------------------------------------------ AJAX getMACbasedfilter String Start Here -----------------------------------------------------------------//
var xmlhttp;
var SearchString="";
function loadContent(model,devicetype)
{
    var url="getMACBasedPlanFilterString.jsp";
    var params = '';
    params = "?model="+model+"&devicetype="+devicetype;
    url =url+params;
    http.open("POST", url , true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Content-length", params.length);
    http.setRequestHeader("Connection", "close");
    http.onreadystatechange = getOutput;
    http.send(params);

 /*xmlhttp=GetXmlHttpObject();

  if (xmlhttp==null)
  {
   alert ("Your browser does not support Ajax HTTP");
   return;
  }

    var url="getMACBasedPlanFilterString.jsp";
    url=url+"?model="+model+"&devicetype="+devicetype;

    xmlhttp.onreadystatechange=getOutput;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
        */
}

function getOutput()
{
  if (http.readyState==4)
  {
      updatePlans(http.responseText);
  }
}

function GetXmlHttpObject()
{
    if (window.XMLHttpRequest)
    {
       return new XMLHttpRequest();
    }
    if (window.ActiveXObject)
    {
      return new ActiveXObject("Microsoft.XMLHTTP");
    }
 return null;
}

//---------------------------------- AJAX Ends getMACbasedfilterString -----------------------------------------//
    function hideprogess() {
	document.getElementById('progressbar').style.visible="none";
	document.getElementById('progressbar').style.visibility="hidden";
    }
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
        var national = '<%=(String)formInput.get("nationality")%>';
        var busType = '<%=(String)lcBusinessType.getLightData ()%>';
        if( ( busType == "1" ) ||  (busType == "5" ) ||  (busType == "3" ) ||  (busType == "8" ) ||  (busType == "9" ) ) {
            cnic = trimString(form.identity.value);
            if(cnic == ''){
                showMessage(form.identity, 'Please Enter Your CNIC/Passport Number');
                return;
            }
            if(isValidCNIC(cnic, national)) {
		//
            } else {
                if(national == "1") {
                    showMessage(form.identity, 'CNIC Format Expected: 0000000000000(13 digits)');
                } else {
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
            var busType = '<%=(String)lcBusinessType.getLightData ()%>';
            var ctl;
            if(( busType == "1" ) ||  (busType == "5" ) ||  (busType == "3" ) ||  (busType == "8" ) ||  (busType == "9" )){
		ctl = document.frmCreate2.identity;
            } else {
		ctl = document.frmCreate2.NTN;
            }
            if(res.indexOf('OK.') > -1) {
                showMessage(ctl, "New Subscriber, Proceed with creating the account.", true);
                document.frmCreate2.txtDuplicateChk.value="OK";
            }
            if(res.indexOf('DUPLICATE.') > -1) {
                showMessage(ctl, "There are already max no of Accounts with this Identity.", true);
                document.frmCreate2.txtDuplicateChk.value="DUPLICATE";
            }
            if(res.indexOf('BLOCKED.') > -1) {
                showMessage(ctl, "Sale not allowed on this customer ID.", true);
                document.frmCreate2.txtDuplicateChk.value="BLOCKED";
            }
        }
    }

    function showme(chk) {
		document.getElementById('ParentAccontDetails').innerHTML = "";
	if(chk.checked == true) {
		ActCreation = window.open("NewAccount_Child.jsp?busType=<%=(String)lcBusinessType.getLightData()%>","ActCreation", "menubar=0,resizable=0,scrollbars=1,width=650,height=450");
                //ActCreation = window.open("NewAccount_Child.jsp?busType=<%=(String)lcBusinessType.getLightData()%>","ActCreation");
                ActCreation.moveTo(100,100);
                ActCreation.focus();

	} else {
                 document.getElementById('ParentDetails').style.display = "none";
                document.frmCreate2.payee.value="";
                document.frmCreate2.parentpoid.value="";
                document.frmCreate2.lstAffinitySlab.value="";
                ActCreation.close();
	}
    }

    function callbyChild(parentpoid, payee, parentslab, Accountname) {
        if(payee == "" || parentpoid == "" ) {
            document.frmCreate2.payee.value="";
            document.frmCreate2.parentpoid.value="";
            document.frmCreate2.lstAffinitySlab.value="";
            document.frmCreate2.child.checked = false;
			document.getElementById('ParentAccontDetails').innerHTML = "";
        } else {
            document.frmCreate2.payee.value=payee;
            document.frmCreate2.parentpoid.value=parentpoid;
            document.frmCreate2.lstAffinitySlab.value=parentslab;
            document.getElementById('ParentDetails').style.display = "";
            document.getElementById('ParentAccontDetails').innerHTML = "<SPAN class=display>Parent Account : " + Accountname + "(" + parentpoid + ")</SPAN>";
        }
    }
function bodyload(){
document.getElementById('ParentDetails').style.display = "none";
}




</script>
    <body onload="bodyload();">
                              <div id="inner-content">
                               <!-- Inner content starts here -->
                                <!-- messge start -->
                                <P>Fields marked with an asterisk (<SPAN class=mandatory>*</SPAN>) are mandatory.</P>
                                <!-- message end -->
                                <div class="shadow-box">
                                    <div class="shadow-box-inner">
                                          <form name=frmCreate2   method=post action="./DealerAccountCreationStep2Action.do" onsubmit="javascript: return validateDealerNewAcctStep2(this)">
                                             <table width="90%" border="0" cellspacing="0" cellpadding="0" class="Act-listing">
<% if(!((String)lcBusinessType.getLightData ()).equals("4") ) { %>
                                                 <tr class="info-listing">
    <td ><input type="checkbox" name="child" onclick='showme(this)'/><P class=validationmsg></P> </td>
<td colspan=3> <label>This Account Has Parent</label></td> </tr>
<tr id="ParentDetails" class='info-listing'><td colspan='3'><div id=ParentAccontDetails><span class=display></span></div>
    <input type="hidden" name="parentpoid" value="" />
    <input type="hidden" name="payee" value=""/>
    <input type="hidden" name=lstAffinitySlab value=""/></td> </tr>


<% } %>
                                                <tr  height=30><th colspan=4 >Customer Identity  </th></tr>
<% if((cBusinessType.equalsIgnoreCase("1")) ||(cBusinessType.equalsIgnoreCase("5")) || (cBusinessType.equalsIgnoreCase("9")) || (cBusinessType.equalsIgnoreCase("3")) || (cBusinessType.equalsIgnoreCase("8"))){%>
<tr  class='info-listing'>
	<td>
		<div id=wtb_org_id1>
			<label>
<%
	if( ((String)formInput.get("nationality")).trim().equals("1")) {
	out.print("CNIC");
	}else {
	out.print("Passport");
	}
%>

				<SPAN class=mandatory>*</SPAN>
			</label>
		</div>
	</td>
	<td colspan=3>
		<input class="txtxx" type="text" name="<%=IDENTITY%>" value="<%=((String)lcCNIC.getLightData() == null ? "" : (String)lcCNIC.getLightData())%>" onchange='document.frmCreate2.txtDuplicateChk.value=""' maxlength="20" id="identity" onblur='checkDuplicateCNIC(frmCreate2);'/><p class=validationmsg></p>
		<input size=3 type="hidden" id="txtDuplicateChk" name="txtDuplicateChk" value='' />
		<input type="hidden" name="business_type" value="<%=(String)lcBusinessType.getLightData ()%>"/>
		<input type="hidden" name="nationality" value="<%=(String)formInput.get("nationality")%>"/>
	</td>
	<!--<td> <input type=button name="btnChkCNIC" value="Verify Identity" onclick='checkDuplicateCNIC(frmCreate2);'></td>-->
</tr>
<% } else { %>
<tr  class='info-listing'><td><label>NTN <SPAN class=mandatory>*</SPAN></label></td>
    <td colspan=3>
    <input class="txtxx" type="text" name="NTN" maxlength="20" value="" id="NTN" onchange='document.frmCreate2.txtDuplicateChk.value=""' onblur='appendNTN(frmCreate2);checkDuplicateCNIC(frmCreate2);'/><p class=validationmsg></p>
    </td>
    </tr>
<tr  class='info-listing'><td><div id=wtb_org_id1><label> Sub-Id </label></div></td>
    <td colspan=3>
    <input class="txtxx" type="text" name="subid" maxlength="20" value="" id="subid" /><p class=validationmsg></p>
    </td>
    <td colspan=3>
    <input type="hidden" name="<%=IDENTITY%>" value="<%=((String)lcCNIC.getLightData() == null ? "" : (String)lcCNIC.getLightData())%>" id="identity"/>
    <input type="hidden" id="txtDuplicateChk" name="txtDuplicateChk" value='' />
    <input type="hidden" name="business_type" value="<%=(String)lcBusinessType.getLightData ()%>"/>
    <input type="hidden" name="nationality" value="<%=(String)formInput.get("nationality")%>"/>
    </td>
    </tr>
<% }%>
                                                <tr  class="info-listing">
                                                    <td ><label>Payment method<SPAN class=mandatory>*</SPAN></label></td>
                                                    <td colspan="3">
                                                        <%
                                                        String paytypes[] =props.getProperty("consumerPaymentType").split("\\|");

							if(  ((String)lcBillType.getLightData()).equals(INVOICE) ) {
								paytypes =props.getProperty("consumerPostPaidPaymentType").split("\\|");
							} else {
								paytypes =props.getProperty("consumerPrePaidPaymentType").split("\\|");
							}

                                                        %>


                                                      <select class=secectxx name="<%=BILL%>" onchange='PaymentMethodChange(this,document.frmCreate2.<%=WTB_ACCT_SERVICE_TYPE%>,document.frmCreate2.<%=WTB_ACCT_SERVICE_TYPE%>)'>
                                                           <option value="">Select payment method</option>




                                                            <%

                                                                for(int i=0;i<paytypes.length;i++) {
                                                                    String sTemp = paytypes[i];
                                                                    String sOptVal = sTemp.substring(0,sTemp.indexOf("#"));
                                                                    String sOptText = sTemp.substring(sTemp.indexOf("#")+1);
                                                                    boolean blDisplaySelected = false;

                                                                    if( (String)lcBillType.getLightData() != null && ((String) lcBillType.getLightData()).equals(sOptVal))
                                                                       blDisplaySelected = true;



                                                            %>
                                                                <option <%=(blDisplaySelected) ? " selected " : "" %>  value="<%=sOptVal%>"><%=sOptText%></option>
                                                            <%
                                                                }

                                                            %>


                                                    </select><p class=validationmsg></p></td>
                                                </tr>
                                                <tr  height=30><th colspan=4 >Package </th></tr>
                                                <% if(((String)lcBusinessType.getLightData ()).equals("4") ) {
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
                                                <tr  class="info-listing"><script language=javascript>

                                                    function updatepkgdetails(sel) {
                                                        if(sel.value != "") {
                                                            document.frmCreate2.<%=PROMO_PKG%>.value=sel.value;

                                                            var period = parseInt(sel.value);
                                                            if(!IsNumber(sel.value)) {
                                                                showMessage(sel,'Please Enter only integer value');
                                                                document.frmCreate2.contract_end.value = "";
                                                                return(false);
                                                            }

                                                            var dt= Date.fromString(document.frmCreate2.<%=CONTRACT_START%>.value);
                                                            document.frmCreate2.<%=CONTRACT_START%>.value = dt.asString();
                                                            document.frmCreate2.<%=CONTRACT_END%>.value = dt.addMonths(period).asString();
                                                       }
                                                       else {
                                                            document.frmCreate2.<%=PROMO_PKG%>.value = "";
                                                            document.frmCreate2.<%=CONTRACT_END%>.value = "";
                                                       }
                                                    }
                                                    </script><p class=validationmsg></p>
                                                    <td ><label>Contract Period<span class=mandatory>&nbsp;*</span></label></td>
                                                    <td colspan="3"><select class=txtxx name="<%=CONTRACT_PERIOD%>" onchange='updatepkgdetails(this)' >
                                                            <option value="">Select Contract Period</option>
                                                        <%

                                                            if(lstContractPeriod != null) {
                                                                if(lstContractPeriod.size() > 0) {
                                                                    for(int i=0; i< lstContractPeriod.size(); i++) {
                                                                        String sTemp = ((FList)lstContractPeriod.get(i)).get(WtbFldContractPeriod.getInst()).toString();
                                                                        if((String)lcContractPeriod.getLightData() != null  && ((String)lcContractPeriod.getLightData()).equals(sTemp) ) {
                                                                            out.print("<option selected value='" + sTemp + "'>" + sTemp + " Months</option>");
                                                                        } else if(wsutil.getDefaultContractPeriod() == Integer.parseInt(sTemp)) {
                                                                            out.print("<option selected value='" + sTemp + "'>" + sTemp + " Months</option>");
                                                                        } else {
                                                                            out.print("<option value='" + sTemp + "'>" + sTemp + " Months</option>");
                                                                        }

                                                                    }
                                                                }
                                                            }

                                                            %>
                                                    </select><p class=validationmsg></p></td>                                                    </td>
                                                </tr>
                                                <tr  class='info-listing'>
                                                <td><label>Payment Alerts</label></td>
                                                <td colspan=2><select name="slsubscribe">
                                                        <option selected value='0'>Un-Subscribe</option>
                                                        <option value='1'>Subscribe</option>
                                                            </select>
                                                </td>
                                                </tr>
                                                <tr  class="info-listing">
                                                    <td ><label>Start Date</label></td>
                                                    <td ><input type=hidden name="<%=PROMO_PKG%>" value="<%=((String) lcProPackage.getLightData() == null ? "" : (String)  lcProPackage.getLightData() )%>"/><input type=textbox  class=txtxx  name="<%=CONTRACT_START%>" readonly value="<%=formatter.format(wsutil.getServerTime())%>"/></td>
                                                    <td ><label>End Date</label></td>
													<%
													String strCtEndDate = "";
													try {
														if((Date) lcContractEnd.getLightData() != null) {
															strCtEndDate = formatter.format((Date) lcContractEnd.getLightData());
														}
													} catch(Exception ex) {
														System.out.println(ex.toString());
													}
													%>
                                                    <td ><input type=textbox  class=txtxx  name="<%=CONTRACT_END%>" readonly value="<%=strCtEndDate%>"/><P class=validationmsg></P>
                                                    <!--added by muralidhar -->
                                                    <input type=hidden name="<%=WTB_CLIENT_APP_NAME%>" value="LSLP"/>

                                                </td>
                                                </tr>


                                                <tr  class="info-listing"><td valign=top><label>Service Login Type<span class="mandatory">&nbsp;*</span></label></td>
                                                    <td colspan=3><select name="<%=SVS_LOGIN_TYPE%>">
                                                                        <option value="">Select Service Login Type</option>
                                                                        <% String[] svsLoginTypes = props.getProperty("consumerServiceLoginType").split("\\|");
                                                                            for(int i=0;i<svsLoginTypes.length;i++) {
                                                                                String sTemp = svsLoginTypes[i];
                                                                                String sOptVal = sTemp.substring(0,sTemp.indexOf("#"));
                                                                                String sOptText = sTemp.substring(sTemp.indexOf("#")+1);
                                                                                if( (String) lcSvsLoginType.getLightData() != null && ((String) lcSvsLoginType.getLightData()).equals(sOptVal)) {
                                                                        %>
                                                                        <option selected value="<%=sOptVal%>"><%=sOptText%></option>
                                                                        <%
                                                                            } else {
                                                                         %>
                                                                        <option value="<%=sOptVal%>"><%=sOptText%></option>
                                                                        <%

                                                                            }
                                                                            }
                                                                        %>
                                                        </select>
                                                          <P class=validationmsg></P>
                                                    </td>
                                                </tr>
                                                <tr  height=30 id="taxrow1"><th colspan=4 >Taxation </th></tr>
                                                <tr  id="taxrow2" class="info-listing">
                                                    <td ><label>Tax Exempt Type</label></td>
                                                    <td colspan="3"><table><tr>
                                                                <td colspan=3><select name="<%=TAX_EXPT_TYPE%>">
                                                                        <option value="">Select Tax Exempt Type </option>
                                                                        <option <%=(( (String)lcTaxExptType.getLightData() != null && ((String)lcTaxExptType.getLightData()).equals("WHT")) ? " selected " : "")%> value="WHT">WHT</option>
                                                                        <option <%=(( (String)lcTaxExptType.getLightData() != null && ((String)lcTaxExptType.getLightData()).equals("FED")) ? " selected " : "")%> value="FED">FED</option>
                                                                        <option <%=(( (String)lcTaxExptType.getLightData() != null && ((String)lcTaxExptType.getLightData()).equals("DUAL")) ? " selected " : "")%> value="DUAL">BOTH</option>
                                                                </select>
                                                                    </td>
                                                        </tr></table>
                                                    <P class=validationmsg></P></td>
                                                </tr>
                                                <tr  id="taxrow3" class="info-listing">
                                                <td ><label>Tax Exempt(%)</label></td>
                                                <td colspan="3"><input type=text maxlength="5" class="txtxx" name="<%=TAX_EXPT_PERCENT%>" Value="<%=( ((String)lcTaxExcptPercent.getLightData() == null) ?  "0.00" : (String)lcTaxExcptPercent.getLightData() )%>" /><p class=validationmsg></p></td>
                                                </tr>
                                                
           <!---------------------------------- Code Changes by PKAasimN ------------------------------------------->
           <!---------------------------------- MAC Based Device Qualification Check ------------------------------>
           
 <tr  height=30 id="taxrow1"><th colspan=4 >Device Details </th></tr>
<tr  class='info-listing'>
    <td ><label>CPE MAC Address<span class="mandatory">&nbsp;*</span></label></td>
    <td colspan=2><input tabindex=1   class="txtxx" Type="text"  Maxlength=50 Value="" Name="<%=CPE_MAC_ADDRESS%>" id="<%=CPE_MAC_ADDRESS%>" onblur="getMacDetails(0)"/><P class=validationmsg></P></td>

</tr>
<tr  class='info-listing'>
    <td><label>Serial Number<span class="mandatory">&nbsp;*</span></label></td>
    <td colspan=2><input  class="txtxx" Type="text"  Maxlength=50 Value="" Name="<%=CPE_SERIAL_NUMBER%>" id="<%=CPE_SERIAL_NUMBER%>" onblur="getMacDetails(1)"/><P class=validationmsg></P></td>
    

</tr>
<tr class="info-listing">
    <td colspan=3>
        <table border=01 width=120% cellpadding=0 cellspacing=0 class='Act-listing'>
                    <input type=hidden  name="<%=CPE_MAC_STATUS%>" id="<%=CPE_MAC_STATUS%>" value=""/>
                    <input type=hidden name="<%=CPE_DEVICE_ID%>" id="<%=CPE_DEVICE_ID%>" value=""/>
                    <input  type=hidden name="VoIPEnabled" id="VoIPEnabled" value=""/>
                    <input type=hidden name="NoOfVoIpPorts" id="NoOfVoIpPorts" value=""/>
                    <input type=hidden name="DeviceType" id="DeviceType" value=""/>
                    <input type=hidden name="SearchStr" id="SearchStr" value=""/>

                <tr><th>MAC Address</th><th>CPE Type  </th><th>Manufacturer</th><th>Status</th><th>VoIP Enabled</th>
                </tr>
                <tr><td><div id="dwan">&nbsp;</div></td>
                <td><div id="dtype">&nbsp;</div> </td>
                <td><div id="dman">&nbsp;</div>
                <div id="did">&nbsp;</div>
                <div id="dmdl">&nbsp;</div>
                    <div id="dpid">&nbsp;</div>
                <div id="dlan">&nbsp;</div></td>
                <td><div id="info">&nbsp;</div>
                <div id="dstate">&nbsp;</div></td>
                <td align=right><div id="dnovoipnumbers">&nbsp;</div></td>
        </tr></table>
    </td>
</tr>
        <!---------------------------------- Code Changes by PKAasimN Ends ------------------------------------------->

<tr height=30><th colspan=3 >Purchase Plan (Plan list)<div id=divnumberofplans></div></th><td>
        <select disabled class=secectxx id="<%=WTB_ACCT_SERVICE_TYPE%>" name="<%=WTB_ACCT_SERVICE_TYPE%>" style="visibility:hidden">
                                                        
                                                <option <%=(( (String)formInput.get(WTB_ACCT_SERVICE_TYPE)!= null &&  ((String)formInput.get(WTB_ACCT_SERVICE_TYPE)).equals("1")) ? " selected " : "") %> value=1>Pre-Paid</option>
                                                   <option <%=(( (String)formInput.get(WTB_ACCT_SERVICE_TYPE)!= null &&  ((String)formInput.get(WTB_ACCT_SERVICE_TYPE)).equals("2")) ? " selected " : "") %> value=2>Post paid</option>
                                                   </select><input  type=hidden id="plan" name="<%=WTB_ACCT_SERVICE_TYPE%>" value="<%=( ((String)formInput.get(WTB_ACCT_SERVICE_TYPE) == null) ?  "" : (String)formInput.get(WTB_ACCT_SERVICE_TYPE) )%>"/></td></tr>
                                                <tr  class="info-listing"><td colspan=4>
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
        } %>
    planArray[<%=i%>] = new Array("<%=String.valueOf(i)%>","<%=sTempPlanName%>");
<%  } %>
<% } %>

var oldServicetype = "";
var numberofplans = 0;
var plan_text="";
// --------------------------------- Changes by PKAasimN ------------------------//
function updatePlans(SearchString) {
   var SearchArray = new Array();

    var lstPlanList = document.getElementById("lstPlanList");
    if(lstPlanList.options.length>0)
    {
        oldServicetype="";
    }

   var str = SearchString.split(",");
   for(var i=0;i<str.length;i++)
    {
        SearchArray[i] = str[i];
        SearchArray[i] = trim(SearchArray[i]);
    }

    if(<%=cBusinessType%> == "1" || <%=cBusinessType%> == "2" ) {
        if(oldServicetype != document.frmCreate2.accountservicetype.value || oldServicetype== "") {
            while(lstPlanList.options.length > 0) {
                document.frmCreate2.lstPlanList.options.remove(0);
                numberofplans =numberofplans -1;
            }

            for(i=0; i< planArray.length;i++) {
                var opt = new Option();
                var str = planArray[i];

                //var filter1 = document.frmCreate2.accountservicetype.value;
                var filter1 = <%=(String)formInput.get(WTB_ACCT_SERVICE_TYPE)%>;
                if(filter1 == "1")
                    filter1 = "PREPAID";
                else
                    filter1 = "POSTPAID";

                if(str[1].toUpperCase().indexOf(filter1) > -1) {
                    opt.value = str[0];
                    opt.text = str[1];
                    for(var j = 0; j < SearchArray.length; j++){
                        if(str[1].toUpperCase().indexOf(SearchArray[j]) != -1)
                        {
                             document.frmCreate2.planselected.options.add(opt);
                             numberofplans = numberofplans + 1;
                        }
                    }
                }
            }
            oldServicetype = document.frmCreate2.accountservicetype.value;
            document.frmCreate2.planselected.options.selectedIndex = 0;

            if(numberofplans==0 || lstPlanList.options.length < 1)
            {
                for(i=0; i< planArray.length;i++) {
                    var opt = new Option();
                    var str = planArray[i];

                    //var filter1 = document.frmCreate2.accountservicetype.value;
                    var filter1 = <%=(String)formInput.get(WTB_ACCT_SERVICE_TYPE)%>;
                    if(filter1 == "1")
                        filter1 = "PREPAID";
                    else
                        filter1 = "POSTPAID";

                    if(str[1].toUpperCase().indexOf(filter1) > -1) {
                        opt.value = str[0];
                        opt.text = str[1];

                         document.frmCreate2.planselected.options.add(opt);
                         numberofplans = numberofplans + 1;
                    }
                }
            }
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
        return(false);
    }
    else {
	document.getElementById('divnumberofplans').innerHTML = "";
    }
}
// --------------------------------- Changes by PKAasimN Ends ------------------------//
function loadFUTPlanList() {
	if(oldServicetype != document.frmCreate2.accountservicetype.value || oldServicetype== "") {
			while(document.frmCreate2.planselected.options.length > 0) {
				document.frmCreate2.planselected.options.remove(0);
				numberofplans = numberofplans - 1;
			}
	for(i=0; i< planArray.length;i++) {
		var opt = new Option();
		var str = planArray[i];
		opt.value = str[0];
		opt.text = str[1];
		document.frmCreate2.planselected.options.add(opt);
		numberofplans = numberofplans + 1;
	}
	oldServicetype = document.frmCreate2.accountservicetype.value;
	document.frmCreate2.planselected.options.selectedIndex = 0;
	}
}



function loadFUTPlanList() {
	if(oldServicetype != document.frmCreate2.accountservicetype.value || oldServicetype== "") {
			while(document.frmCreate2.planselected.options.length > 0) {
				document.frmCreate2.planselected.options.remove(0);
				numberofplans = numberofplans - 1;
			}
	for(i=0; i< planArray.length;i++) {
		var opt = new Option();
		var str = planArray[i];
		opt.value = str[0];
		opt.text = str[1];
		document.frmCreate2.planselected.options.add(opt);
		numberofplans = numberofplans + 1;
	}
	oldServicetype = document.frmCreate2.accountservicetype.value;
	document.frmCreate2.planselected.options.selectedIndex = 0;
	}
}

function PaymentMethodChange(sel,sel1,txt)
{
	/*if(sel.options[sel.selectedIndex].value == '<%=PREPAID%>')
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
        */

        //updatePlans();
}
<% if(sActSvsType.equals ("1")) { %>
        document.getElementById("taxrow1").style.display="none";
        document.getElementById("taxrow2").style.display="none";
        document.getElementById("taxrow3").style.display="none";
<% } else { %>
        document.getElementById("taxrow1").style.display="";
        document.getElementById("taxrow2").style.display="";
        document.getElementById("taxrow3").style.display="";
<% } %>
</script>
                                                </td></tr>
                                                <tr><td colspan=4><!-- hidden fields -->
                                                        <input Type="hidden" Name="referrer" Value="<%=DSTEP2%>"/>
                                                        <input Type="hidden" Name="brand" Value="<%=brand%>"/>

                                                        </td></tr>
                                                <tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th >
                                                        <input type="image" name="submit" src="images/btn-go.gif" alt="Request Change" title="Request Change"
                                                               onmouseover="this.src='images/btn-go-on.gif'" onmouseout="this.src='images/btn-go.gif'" />
                                                </th></tr>
                                                <tr><th>&nbsp;</th><th>&nbsp;</th><th>&nbsp;</th><th >&nbsp;</th></tr>
                                            </table>
                                        </form>
                                        <script language=javascript>
                                           // updatePlans();
                                            <% if(wsutil.getDefaultContractPeriod()  > 0) {%>
                                                updatepkgdetails(document.frmCreate2.<%=CONTRACT_PERIOD%>);
                                            <% } %>
                                    </script>

                                    </div>
                                </div>
                                <!-- Inner content ends here -->
                            </div>

        <div id="progressbar" style="width: 300px; height: 100px; align:center; position: absolute; z-index:10; left:150px; right:200px; -moz-opacity:10; background:#c60651; font-family:Verdana, Arial, Helvetica, sans-serif; font-size:12px; visibility: hidden">
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
