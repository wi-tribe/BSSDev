<%@ page contentType="text/html;charset=UTF-8" 
language="java" 
import="java.util.*,
com.portal.bas.*, 
com.portal.pcm.*,
com.portal.pcm.fields.*,
com.portal.bas.comp.*,
java.text.* , 
customfields.*,
com.portal.app.ccare.comp.*,
com.portal.web.comp.*, 
com.portal.web.*,
com.portal.web.ServletUtil, 
com.portal.pfc.util.PI18nHelper,
Wtb.MyAccount.*"
%>
<%@ include file="includes/constants.jsp"%>
<%@ include file="includes/ServiceConstants.jsp"%>
<jsp:useBean id="accountCreationBean" class="com.portal.bas.comp.PIAComponentCollection" scope="session"/>
<script type="text/javascript" src="js/jquery.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="js/script.js"></script>
<script language="javascript" type="text/javascript" src="js/date.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.datePicker.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.bgiframe.js"></script>
<script language="javascript" type="text/javascript" src="js/utilfunctions.js"></script>
<script language="javascript" type="text/javascript" src="js/validations.js"></script>
<script language="javascript" type="text/javascript" src="js/validates.js"></script>
<%
PPooledConnectionClientServices pCS = (PPooledConnectionClientServices)session.getAttribute(CREATE_CONNECTION);
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

Properties props = pCS.getDefaultProperties();
boolean singleLogin = (new Boolean((String)props.getProperty(SINGLE_lOGIN))).booleanValue();
boolean confirm = false;
int noofServices = 0;
String [] access1 = new String[100];
String errMsg = "", selBillType="";
String errFld = "";
String brand=null;
String bill = "";
String invname = "";
String ipAddress = "";
String  sCustEmailid = "";
String sMailName = "";
String sTPin ="";
boolean existingLogin = false;
MyAcctUtility myutil;
PModelHandle mH = null;
PModelHandle myModel = null;
String inv_name, inv_address, inv_city, inv_state, inv_zip, inv_country, inv_email;
inv_name="";
inv_address="";
inv_city="";
inv_state="";
inv_zip="";
inv_email="";
String planname = "";
String r_LeadId, r_Acct_CreateFrom,r_SalesID, r_RedirectURL ;
r_LeadId = (( session.getAttribute("newAcct_Leadid") == null) ? "custom " : (String)session.getAttribute("newAcct_Leadid"));
r_Acct_CreateFrom = (( session.getAttribute("newAcct_createBy") == null) ? "custom " : (String)session.getAttribute("newAcct_createBy"));
r_SalesID = (( session.getAttribute("newAcct_salesId") == null) ? "custom " : (String)session.getAttribute("newAcct_salesId"));
r_RedirectURL = ( (props.getProperty("leadacctmapurl") == null)? "login.jsp" : props.getProperty("leadacctmapurl"));
%>

<%
SimpleDateFormat formatter = new SimpleDateFormat(props.getProperty("applicationdateformat"));
PLightComponentHelper lcSalutation, lcFname, lcLname, lcMname;
PLightComponentHelper lcTitle, lcCompany, lcEmail, lcAddress, lcDeviceID;
PLightComponentHelper lcCity, lcState, lcZip, lcCountry, lcSLA, lccoverageType, lclongitude, lcLatitude, lcLeadID;
PLightComponentHelper lcLocale, lcBillType, lcInvoiceName, lcDeliveryPrefer, lcInvoiceAddress;
PLightComponentHelper lcInvoiceCity, lcInvoiceState, lcInvoiceZip, lcInvoiceCountry, lcInvoiceEmailAddr;
PLightComponentHelper lcPpCity, lcPpState, lcPpZip, lcPpCountry, lcPpAddr, lcPpName, lcPpEmail;
PLightComponentHelper lcBankNo, lcAccountNum , lcDDType, lcDDName, lcDDState, lcDDZip, lcDDCountry;
PLightComponentHelper lcDDCity, lcDDAddress, lcCCName ,lcCCCity,lcCCState,lcCCZip,lcCCCountry;
PLightComponentHelper lcCCAddress,lcDebitNum, lcCV ,lcDebitExp,lcCurrency, lcTExpType, lcTExpPercnt;
PLightComponentHelper lcHomePhone,lcWorkPhone,lcPager,lcFax,lcMobile, lcPPkg,  lcSLogType;
PLightComponentHelper lcAacAccess, lcDOB, lcWtbServiceType, lcCustBusType, lcCNIC, lcCPeriod, lcCStart, lcCEnd, lcSalesID,  lcTPIN, lcParent, lcAffinitySelectedSlab;
PLightComponentHelper lcPaymentAlert;;

myutil = (MyAcctUtility) pCS.createController("Wtb.MyAccount.MyAcctUtility");

lcSalutation = accountCreationBean.getChild(SALUTE);
lcFname = accountCreationBean.getChild(FIRSTNAME);
lcMname = accountCreationBean.getChild(MIDDLENAME);
lcLname = accountCreationBean.getChild(LASTNAME);
lcAddress = accountCreationBean.getChild(ADDRESS1);
lcCity = accountCreationBean.getChild(CITY);
lcState = accountCreationBean.getChild(STATE);
lcCountry = accountCreationBean.getChild(COUNTRY);
lcZip = accountCreationBean.getChild(ZIP);
lcEmail = accountCreationBean.getChild(EMAIL);

lcHomePhone = accountCreationBean.getChild(HOMEPHONE);
lcWorkPhone = accountCreationBean.getChild(WORKPHONE);
lcFax = accountCreationBean.getChild(FAX);
lcMobile = accountCreationBean.getChild(MOBILE);
//lcPager = accountCreationBean.getChild(PAGER);

lcCustBusType = accountCreationBean.getChild(WTB_BUS_TYPE);
lcCNIC = accountCreationBean.getChild(IDENTITY);
lcTPIN = accountCreationBean.getChild(TPIN);
lcDOB = accountCreationBean.getChild(WTB_DOB);
lcTitle = accountCreationBean.getChild(TITLE);
lcCompany = accountCreationBean.getChild(COMPANY);
lcBillType = accountCreationBean.getChild(BILL);
lcCPeriod = accountCreationBean.getChild(CONTRACT_PERIOD);
lcCStart = accountCreationBean.getChild(CONTRACT_START);
lcCEnd = accountCreationBean.getChild(CONTRACT_END);
lcPPkg = accountCreationBean.getChild(PROMO_PKG);
lcSLogType = accountCreationBean.getChild(SVS_LOGIN_TYPE);
lcTExpType = accountCreationBean.getChild(TAX_EXPT_TYPE);
lcTExpPercnt = accountCreationBean.getChild(TAX_EXPT_PERCENT);
lcWtbServiceType = accountCreationBean.getChild(WTB_ACCT_SERVICE_TYPE);
lcAacAccess = accountCreationBean.getChild(AACACCESS);
lcLocale = accountCreationBean.getChild(LOCALE);
lcCurrency = accountCreationBean.getChild(CURRENCY);
lcAffinitySelectedSlab = accountCreationBean.getChild( "lstAffinitySlab");
lcInvoiceName = accountCreationBean.getChild(INVNAME);
lcDeliveryPrefer = accountCreationBean.getChild(DELIVERMETHOD);
lcInvoiceAddress = accountCreationBean.getChild(INVADDRESS1);
lcInvoiceCity = accountCreationBean.getChild(INVCITY);
lcInvoiceState = accountCreationBean.getChild(INVSTATE);
lcInvoiceZip = accountCreationBean.getChild(INVZIP);
lcInvoiceCountry =  accountCreationBean.getChild(INVCOUNTRY);
lcInvoiceEmailAddr = accountCreationBean.getChild(INVEMAIL);

lcPpName = accountCreationBean.getChild(PP_NAME);
lcPpEmail = accountCreationBean.getChild(PP_EMAIL);
lcPpAddr = accountCreationBean.getChild(PP_ADDR);
lcPpCity = accountCreationBean.getChild(PP_CITY);
lcPpState = accountCreationBean.getChild(PP_STATE);
lcPpZip = accountCreationBean.getChild(PP_ZIP);
lcPpCountry = accountCreationBean.getChild(PP_COUNTRY);

lcDDName = accountCreationBean.getChild(DDNAME);
lcBankNo = accountCreationBean.getChild(BANK);
lcAccountNum = accountCreationBean.getChild(ACCTNUM);
lcDDType = accountCreationBean.getChild(ACCTTYPE);
lcDDAddress = accountCreationBean.getChild(DDADDR1);
lcDDCity = accountCreationBean.getChild(DDCITY);
lcDDState = accountCreationBean.getChild(DDSTATE);
lcDDZip = accountCreationBean.getChild(DDZIP);
lcDDCountry = accountCreationBean.getChild(DDCOUNTRY);

lcCCName = accountCreationBean.getChild(CCNAME);
lcDebitNum = accountCreationBean.getChild(DEBITNUM);
lcCV = accountCreationBean.getChild(CV);
lcDebitExp = accountCreationBean.getChild(DEBITEXP);
lcCCAddress = accountCreationBean.getChild(CCADDR1);
lcCCCity = accountCreationBean.getChild(CCCITY);
lcCCState = accountCreationBean.getChild(CCSTATE);
lcCCZip = accountCreationBean.getChild(CCZIP);
lcCCCountry = accountCreationBean.getChild(CCCOUNTRY);
lcPaymentAlert = accountCreationBean.getChild(PAYMENT_ALERT);

lccoverageType = accountCreationBean.getChild(COVERAGE_TYPE);
lcLatitude = accountCreationBean.getChild(LATITUDE);
lclongitude = accountCreationBean.getChild(LONGITUDE);
lcSLA = accountCreationBean.getChild(SLA);
lcSalesID = accountCreationBean.getChild(SALES_ID);
lcDeviceID = accountCreationBean.getChild(CPE_DEVICE_ID);
lcLeadID = accountCreationBean.getChild(LEAD_IDENTITY);
lcParent = accountCreationBean.getChild(PARENT);

String country = DEFAULTCOUNTRY;

PIACAPackageInfoBean plansBean = (PIACAPackageInfoBean)session.getAttribute(PLANS_BEAN);
PModelHandle plansModel = (PModelHandle)plansBean.listOfPlans();
com.portal.app.comp.PIAPhoneTableBean phoneBean = (com.portal.app.comp.PIAPhoneTableBean)pCS.createController("com.portal.app.comp.PIAPhoneTableBeanImpl");
PIAInvoicePOBean invoiceBean = (PIAInvoicePOBean)pCS.createController("com.portal.app.ccare.comp.PIAInvoicePOBeanImpl");
//PIAPrepaidPOBean prepaidBean = (PIAPrepaidPOBean)pCS.createController("com.portal.app.ccare.comp.PIAPrepaidPOBeanImpl");
PIAFUSADirDebitBean fusaDDBean = (PIAFUSADirDebitBean)pCS.createController("com.portal.app.ccare.comp.PIAFUSADirDebitBeanImpl");
PIACreditCardBean ccBean = (PIACreditCardBean)pCS.createController("com.portal.app.ccare.comp.PIACreditCardBeanImpl");

com.portal.web.comp.PLocaleInfoBean localeBean = (com.portal.web.comp.PLocaleInfoBean) pCS.createController("com.portal.web.comp.PLocaleInfoBeanImpl");
localeBean.listOfLocales();

com.portal.app.ccare.comp.PIAProfileBeanImpl prBean = (com.portal.app.ccare.comp.PIAProfileBeanImpl) pCS.createController("com.portal.app.ccare.comp.PIAProfileBeanImpl");
prBean.setObjectType("wtb_customer_details");

//load the resource bundle for error messages.
ResourceBundle bundle = (ResourceBundle)session.getAttribute(ServletUtil.BUNDLE);
lcDDType = accountCreationBean.getChild(ACCTTYPE);
//load city list from config object
WtbSelfCareUtility wsutil  = (WtbSelfCareUtility) pCS.createController("Wtb.MyAccount.WtbSelfCareUtility");
wsutil.setProperties(props);
List lstCities = null;
try {
	lstCities = wsutil.getCityList();
} catch(Exception e) {
	System.out.println(e.toString());
}

// Display messages
String SUCCESS = bundle.getString(CA_SUCCESS);
String FAILURE = bundle.getString(CA_FAILURE);
String INTERNAL_ERROR = bundle.getString(CA_INTERNAL_ERROR);
String REMEMBER_ID = bundle.getString(CA_REMEMBER_ID);
String DUPLICATE_ERROR = bundle.getString(CA_DUPLICATE_ERROR);
String ipuserid = "";
String updateStatus = "";
HashMap errorMap = null;
String planIndex = null;
String[] serviceDescriptions = null;
String[] login = null;
String[] password = null;
String sucMsg = "";
String pPoid = null;
String pType = null;
// Variables specific to extended services
int serviceIndex = 0;
PIAComponentCollection[] serviceColl = null;
StringBuffer displayBuf = new StringBuffer();

if (session != null) {
    session.setAttribute(COUNTRY, country);
    session.setAttribute(BILL, INVOICE);
    session.setAttribute(DELIVERSAME,ON );
    session.setAttribute(INVNAME,ON );
}

Map formInput = ServletUtil.gatherFormInput(request);
String referrer = (String) formInput.get("referrer");
sCustEmailid =  (String)lcEmail.getLightData();



if (referrer.equals(STEP1)) {
    
// Input data for services
    planIndex = (String) formInput.get("planselected");
    session.setAttribute(PLAN_INDEX, planIndex);
    
    serviceDescriptions = plansBean.getServiceDescriptions(plansModel, planIndex);
    
    
    
    
    session.setAttribute(SERVICE_DESCR, serviceDescriptions);
    ServletUtil.setLightDataForAll(formInput, accountCreationBean, APP_NAME);
    if(formInput.get(WTB_DOB) != null && ((String)formInput.get(WTB_DOB)) != "") {
        lcDOB.setLightData(formatter.parse((String)formInput.get(WTB_DOB)));
    } else {
        lcDOB.setLightData(null);
    }
    
//Generate the  TPIN
    int aInt = 0;
    while(!(aInt >=1000 && aInt<=9999)){
        aInt =     ((int) (Math.random() * 9999) + 1000);
    }
    sTPin = Integer.toString(aInt);
    Date dtTmpCStart = formatter.parse((String)formInput.get(CONTRACT_START));
    Date dtTmpCEnd = formatter.parse((String)formInput.get(CONTRACT_END));
    Date dtCurrent = new Date();
    long lCurrentTime = dtCurrent.getTime() - dtTmpCStart.getTime();
//out.print(end);
    dtTmpCStart.setTime(dtTmpCStart.getTime() + lCurrentTime);
    dtTmpCEnd.setTime(dtTmpCEnd.getTime() + lCurrentTime);
//out.print(end);
    Calendar calendDate = Calendar.getInstance();
    calendDate.setTime(dtTmpCEnd);
    if(calendDate.get(Calendar.DAY_OF_MONTH)==1){
        dtTmpCEnd = calendDate.getTime();
    }else{
        calendDate.set(Calendar.DAY_OF_MONTH,calendDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendDate.add(Calendar.DAY_OF_MONTH,1);
        dtTmpCEnd = calendDate.getTime();
    }
    
    lcCStart.setLightData(dtTmpCStart);
    lcCEnd.setLightData(dtTmpCEnd);
    
    inv_address = (String)lcAddress.getLightData();
    inv_city = (String) lcCity.getLightData();
    inv_state = (String) lcState.getLightData();
    inv_zip = (String) lcZip.getLightData();
    inv_country = DEFAULTCOUNTRY;
    inv_email = (String)lcEmail.getLightData();
    inv_name = (String)lcFname.getLightData()+ " " +(String)lcLname.getLightData();
    bill = (String)lcBillType.getLightData();
}// end of referrer - Step - 1
else if (referrer.equals(DSTEPCHILDLEAD)) {
// Input data for services
    planIndex = (String) formInput.get("planselected");
    serviceDescriptions = plansBean.getServiceDescriptions(plansModel, planIndex);
    session.setAttribute(SERVICE_DESCR, serviceDescriptions);
    inv_address = (String)lcAddress.getLightData();
    inv_city = (String) lcCity.getLightData();
    inv_state = (String) lcState.getLightData();
    inv_zip = (String) lcZip.getLightData();
    inv_country = DEFAULTCOUNTRY;
    inv_email = (String)lcEmail.getLightData();
    inv_name = (String)lcFname.getLightData() + " " +(String)lcLname.getLightData();
    
    pType = (String) request.getParameter("payee");
    pPoid= (String) request.getParameter("parentpoid");
    lcParent.setLightData(pPoid);
}
//--End Parent/Child Configuaration
else if (referrer.equals(STEP2)) {
    String debitexp = (String) formInput.get(DEBITEXP);
    
    if(debitexp != null) {
        String validatedDate = ServletUtil.validateDate(debitexp);
// Replace with the validated date
        formInput.put(DEBITEXP, validatedDate);
    }
    access1=request.getParameterValues("accesslevel");
    //System.out.println("accesslevel:" + access1[0]);
    //System.out.println("length:"+access1.length);
// Set data for all the BAS aware fieldBeans
    ServletUtil.setLightDataForAll(formInput, accountCreationBean, APP_NAME);
    
//Propgate the name, address,city, state, zip, country to the actual fieldbeans
//depending on the bill type
    String address, city, state, zip;
    
    String userLocale = (String) formInput.get(LOCALE);
    lcLocale.setLightData(userLocale);
    
    Locale accountJavaLocale = localeBean.mapInfranetLocaleToJavaLocale(userLocale);
    String name = PI18nHelper.formatName(accountJavaLocale, (String)formInput.get(FIRSTNAME), (String)formInput.get(LASTNAME));
    bill = (String)lcBillType.getLightData();
    
    inv_name = (String)formInput.get("invoice_name");
    inv_address = (String)formInput.get("bill_address1");
    inv_city = (String)formInput.get("bill_city");
    inv_state = (String)formInput.get("bill_state");
    inv_zip = (String)formInput.get("bill_zip");
    inv_country = (String)formInput.get("bill_country");
    inv_email = (String)formInput.get("inv_email");
    
    
    if (bill.equals(INVOICE)) {
        session.setAttribute(BILL, INVOICE);
        lcInvoiceName.setLightData(inv_name);
        lcInvoiceAddress.setLightData(inv_address);
        lcInvoiceCity.setLightData(inv_city);
        lcInvoiceState.setLightData(inv_state);
        lcInvoiceZip.setLightData(inv_zip);
        lcInvoiceCountry.setLightData(inv_country);
        lcInvoiceEmailAddr.setLightData(inv_email);
    } else if(bill.equals(PREPAID)) {
        session.setAttribute(BILL, PREPAID);
        lcPpName.setLightData(inv_name);
        lcPpEmail.setLightData(inv_email);
        lcPpAddr.setLightData(inv_address);
        lcPpCity.setLightData(inv_city);
        lcPpState.setLightData(inv_state);
        lcPpZip.setLightData(inv_zip);
        lcPpCountry.setLightData(inv_country);
    } else if (bill.equals(CREDIT)) {
        session.setAttribute(BILL, CREDIT);
        lcCCAddress.setLightData(inv_address);
        lcCCCity.setLightData(inv_city);
        lcCCState.setLightData(inv_state);
        lcCCZip.setLightData(inv_zip);
        lcCCCountry.setLightData(inv_country);
        lcCCName.setLightData(inv_name);
    } else if (bill.equals(DEBIT)) {
        session.setAttribute(BILL, DEBIT);
        lcDDAddress.setLightData(inv_address);
        lcDDCity.setLightData(inv_city);
        lcDDState.setLightData(inv_state);
        lcDDZip.setLightData(inv_zip);
        lcDDCountry.setLightData(inv_country);
        lcDDType.setLightData(formInput.get(ACCTTYPE));
        lcDDName.setLightData(inv_name);
    }
    
    myModel = pCS.createNewModel(PModelHandle.UNTYPED);
    if (bill.equals(INVOICE)) {
        invoiceBean.defaultsForStoring(myModel);
    } else if (bill.equals(DEBIT)) {
        fusaDDBean.defaultsForStoring(myModel);
    } else if (bill.equals(CREDIT)) {
        ccBean.defaultsForStoring(myModel);
    }
    
    
// collect data for storing
    mH = accountCreationBean.startSingleModelDataCollection(PCollectDataEvent.FOR_STORING, myModel);
    
// phoneBean processes the input data for phone in account creation
    int[] types = new int[4];
    String[] phones = new String[4];
//iterate through the input for the phones
    
    
    types[0]=1;
    phones[0]=(String)lcHomePhone.getLightData();
    types[1]=2;
    phones[1]=(String)lcWorkPhone.getLightData();
    types[2]=3;
    phones[2]=(String)lcFax.getLightData();
    types[3]=5;
    phones[3]=(String)lcMobile.getLightData();
    phoneBean.setPhoneData(mH, types, phones);
    String macAddress = "";
    confirm = true;
    serviceDescriptions = (String[]) session.getAttribute(SERVICE_DESCR);
    if(serviceDescriptions != null ) {
    login = new String[serviceDescriptions.length];
    password = new String[serviceDescriptions.length];
    for (int i = 0; i < serviceDescriptions.length; i++) {
        login[i] = (String) formInput.get("login_" + String.valueOf(i));
        password[i] =(String) formInput.get("pin_" + String.valueOf(i));
        
        if (!password[i].equals( (String)formInput.get("pinv_" + String.valueOf(i)) )) {
            confirm = false;
            updateStatus = bundle.getString(ERR_PASSWDMATCH);
        }
    }
    
    serviceColl = new PIAComponentCollection[serviceDescriptions.length];
    for (serviceIndex = 0; serviceIndex < serviceDescriptions.length; serviceIndex++) {
        String serviceStr = "";
        int index1 = serviceDescriptions[serviceIndex].indexOf(' ');
        if (index1 > 0) {
            serviceStr = serviceDescriptions[serviceIndex].substring(0, index1);
        } else {
            serviceStr = serviceDescriptions[serviceIndex];
        }
    }
    }
} // End of referrer Step-2
ipAddress = (String) formInput.get("ipAddress");

if (confirm) {
    if (bill.equals(INVOICE)) {
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldDebitExp", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldDebitNum", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldSecurityId", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldBankNo", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldDebitNum", null);
    } else if(bill.equals(PREPAID)) {
        myutil.setPrepaidData(mH);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldDebitExp", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldDebitNum", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldCcInfo[0].FldSecurityId", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldBankNo", null);
        PClientContext.getServices().setModelField(mH, "FldPayinfo[1].FldInheritedInfo[0].FldDdInfo[0].FldDebitNum", null);
        
    } else if (bill.equals(DEBIT)) {
//fusaDDBean.defaultsForStoring(myModel);
    } else if (bill.equals(CREDIT)) {
//ccBean.defaultsForStoring(myModel);
    }
    
    
//planBean processes the input data for services in account creation
    planIndex = (String) session.getAttribute(PLAN_INDEX);
    
    plansBean.mergeModelHandle(mH, (PModelHandle) plansBean.getSelectedPlanModel(plansModel, planIndex, login, password, ipAddress, serviceColl));
    PCachedContext conn = new PCachedContext();
    conn.connect();
    boolean lProceedCommit = true;
    prBean.graftForAccountCreation(mH);
    myutil.updateProfileIds(mH);
    
    com.portal.app.ccare.comp.PIACreateAccountBean cb = (com.portal.app.ccare.comp.PIACreateAccountBean) accountCreationBean.getController();
// -- Begin Append device Obj to the flist.
    boolean appenddevice = false;
    FList newAcctFl = ((FList)conn.lookupModel(mH));
    if(newAcctFl.hasField(FldServices.getInst()) && newAcctFl.hasField(FldDevices.getInst())) {
        FList dFL1 = new FList();
        dFL1.setElement(FldDevices.getInst(),0,newAcctFl.getElement(FldDevices.getInst(),0));
        newAcctFl.remove(FldDevices.getInst());
        SparseArray servicesArray = newAcctFl.get(FldServices.getInst());
        if(servicesArray != null) {
            for(Enumeration servicesEnum = servicesArray.getValueEnumerator(); servicesEnum.hasMoreElements();) {
                FList serviceFList = (FList)servicesEnum.nextElement();
                if((serviceFList.get(FldServiceObj.getInst()).toString().indexOf("/service/ip") > -1) && appenddevice==false ) {
                    serviceFList.set(FldDevices.getInst(),dFL1.get(FldDevices.getInst()));
                    appenddevice = true;
                }
            }
        }
    }
// -- End Device association.
    
//--Start Child Account Creation
    if(lcParent.getLightData() != null){
//--set the child account FList
        String paymentType = (String) request.getParameter("PayType");
        if(paymentType.equalsIgnoreCase("1")){
//pay by parent
//--set the FList  as 1.Pay Type=1007,2.reset the invoice address and set as subordinate
//Start PayInfo
            
            SparseArray payInfoArray = newAcctFl.get(FldPayinfo.getInst());
            if(payInfoArray != null){
                for(Enumeration payInfoEnum = payInfoArray.getValueEnumerator(); payInfoEnum.hasMoreElements();) {
                    FList payInfoFList = (FList) payInfoEnum.nextElement();
                    payInfoFList.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/payinfo/subord"));
                    payInfoFList.set(FldPayType.getInst(), 10007);
                    payInfoFList.remove(FldName.getInst());
                    payInfoFList.remove(FldInheritedInfo.getInst());
                }
            }
//End Pay Info
//Start Bill Info
            SparseArray billInfoArray = newAcctFl.get(FldBillinfo.getInst());
            int i =1;
            String p = (String) lcParent.getLightData();
            Poid ParentPoid = Poid.valueOf(p);
            /**Start parent Billinfo Obj**/
            FList input = new FList();
            FList out1 = new FList();
            input.set(FldPoid.getInst(),ParentPoid);
            out1= conn.opcode(3704, input);
            SparseArray result = out1.get(FldBillinfo.getInst());
            FList orderList = result.elementAt(0);
            Poid billInfoPoid = orderList.get(FldPoid.getInst());
            Poid arBillInfoPoid = orderList.get(FldArBillinfoObj.getInst());
            if(billInfoArray != null){
                for(Enumeration billInfoEnum = billInfoArray.getValueEnumerator(); billInfoEnum.hasMoreElements();) {
                    FList billInfoFList = (FList) billInfoEnum.nextElement();
                    billInfoFList.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/billinfo"));
                    billInfoFList.set(FldBillinfoId.getInst(), "Bill Unit(1)");
                    billInfoFList.set(FldBillWhen.getInst(), i);
                    billInfoFList.set(FldParentBillinfoObj.getInst(), billInfoPoid);
                    billInfoFList.set(FldArBillinfoObj.getInst(), arBillInfoPoid);
                    billInfoFList.set(FldPayType.getInst(), 10007);
                    billInfoFList.remove(FldBillType.getInst());
                }
            }
            /**End Parent Billinfo Obj**/
            
        }
    }
//--End Child Account Creation
    
    planname = newAcctFl.get(FldName.getInst());
    sCustEmailid = (String)lcEmail.getLightData();
    sMailName = (String) lcFname.getLightData() + "  " +(String) lcLname.getLightData();
//out.print("<pre>" + newAcctFl.toString () + "</pre><hr>");
//if(1==1) return;   
//***************************************************
    Poid actDealObj = newAcctFl.get(FldDealObj.getInst());
    Poid actPlanObj = newAcctFl.get(FldPoid.getInst());
    
//out.print (actDealObj.toString ());
    FList infl = new FList();
    FList actoutputdeal = new FList();
    SparseArray sArray = null;
    if(actDealObj != null && actDealObj.getId() > 0) {
        
        infl.set(FldPoid.getInst(), actDealObj);
        actoutputdeal = conn.opcode(3,infl);
        actoutputdeal.remove(FldPoid.getInst());
        actoutputdeal.set(FldDealObj.getInst(), actDealObj);
        newAcctFl.setElement(FldDeals.getInst(),0, actoutputdeal);
        
        FList dealinfoproduts = conn.opcode(3,infl);
//set planobj for each product
        sArray = dealinfoproduts.get(FldProducts.getInst());
        if(sArray != null){
            Enumeration results = sArray.getValueEnumerator();
            while(results.hasMoreElements()){
                FList tmp = (FList)results.nextElement();
                if(!(tmp.hasField(FldPlanObj.getInst()))) {
                    tmp.set(FldPlanObj.getInst(), actPlanObj);
                }
            }
        }
        newAcctFl.set(FldDealInfo.getInst(), dealinfoproduts);
    }
    
//FList svsfl = newAcctFl.getElement (FldServices.getInst (),0);
//out.print ("<pre>" + svsfl.toString () + "<pre><hR><br>");
    String st1 = "";
    StringTokenizer st = null;
   if(access1 != null){ 
    if (access1.length > 1)
            {
			for (int i=0; i < access1.length; i++ )
			{
					st1 = st1 + access1[i]+"/";
			}
		
     }else{
			st1 = access1[0];
                        //System.out.println("access:"+access1[0]);
		
		}
            if (access1.length > 1)   { 
                st = new StringTokenizer(st1,"/");
            }   

}
    SparseArray ssArray = null;
    int noOfBalalceGroups=0;
    if(newAcctFl.hasField(FldServices.getInst())) {
        ssArray = newAcctFl.get(FldServices.getInst());
        if(ssArray != null) {
            Enumeration serviceResults = ssArray.getValueEnumerator();
            while(serviceResults.hasMoreElements()) {
                FList serviceTemp = (FList) serviceResults.nextElement();
                if(serviceTemp.get(FldBalInfoIndex.getInst()) > noOfBalalceGroups) {
                    noOfBalalceGroups = noOfBalalceGroups +1;
                }
                if(serviceTemp.get(FldServiceObj.getInst()).getType().equalsIgnoreCase("/service/telco/voip")) {
                    Poid voipObj = Poid.valueOf(serviceTemp.get(FldLogin.getInst()));
                    if ( voipObj.getId() < 1 )
                        lProceedCommit = false;
                    FList tmpVoIPObj = new FList();
                    tmpVoIPObj.set(FldPoid.getInst(), voipObj);
                    FList resVoIPObj = conn.opcode(3,tmpVoIPObj);
//out.print("<pre>" + resVoIPObj.get(FldDeviceId.getInst()) + "</pre>");
                    serviceTemp.set(FldLogin.getInst(),resVoIPObj.get(FldDeviceId.getInst()));
                     int intacclevel = 0;
                    //System.out.println("xxxx:"+access1[0]);
                    //System.out.println("yyyy:"+access1[1]);
                    //System.out.println("ZZZZ:"+access1.length);
                    //intacclevel = Integer.parseInt(access1[0]);
                    if(access1.length>1){
                    intacclevel = Integer.parseInt(st.nextToken());
                    //out.print("<pre>" + access +"</pre>");
                    } else{
                    intacclevel = Integer.parseInt(access1[0]);
                    }
                    //FList flvoipinfo1 = new FList();
                    //flvoipinfo1.set(FldAccFlag.getInst(), intacclevel);
                    serviceTemp.set(FldAccFlag.getInst(), intacclevel);
                   // serviceTemp.setElement(WtbFldVoipLineInfo.getInst(),0, flvoipinfo1);
                    FList flVoipDevicesObj = new FList();
                    flVoipDevicesObj.set(FldDeviceObj.getInst(), voipObj);
                    serviceTemp.setElement(FldDevices.getInst(),0, flVoipDevicesObj);
                }
 /*out.print("<pre>******************</pre>");
out.print("<pre>" + serviceTemp.toString() + "</pre>");
                out.print("<pre>******************</pre>");*/
                
                FList svsfl = serviceTemp;//.getElement (FldServices.getInst (),0);
//out.print ("<pre>" + svsfl.toString () + "<pre><hR><br>");
                Poid svsDealObj = svsfl.get(FldDealObj.getInst());
                if(svsDealObj != null ) {
                    if(svsDealObj.getId() < 1) {
                        sArray = svsfl.get(FldDeals.getInst());
                        if(sArray != null) {
                            Enumeration dealresults = sArray.getValueEnumerator();
                            int nodeals=0;
                            while(dealresults.hasMoreElements()) {
                                FList tmplist = (FList) dealresults.nextElement();
                                if(tmplist.hasField(FldDealObj.getInst())) {
                                    svsDealObj = tmplist.get(FldDealObj.getInst());
//out.print("<pre>" + svsDealObj.toString() + "</pre>");
                                } else {
                                    svsDealObj = null;
                                }
                                if(svsDealObj != null && svsDealObj.getId() > 0 ) {
                                    nodeals = nodeals + 1;
                                    infl = new FList();
                                    infl.set(FldPoid.getInst(), svsDealObj);
                                    actoutputdeal = conn.opcode(3,infl);
                                    actoutputdeal.remove(FldPoid.getInst());
                                    actoutputdeal.set(FldDealObj.getInst(), svsDealObj);
//svsfl.setElement (FldDeals.getInst (), (nodeals-1), actoutputdeal);
//setting dealinfo at service level
                                    FList svsDealinfo = conn.opcode(3,infl);
                                    sArray = null;
                                    sArray = svsDealinfo.get(FldProducts.getInst());
                                    if(sArray != null){
                                        Enumeration results = sArray.getValueEnumerator();
                                        while(results.hasMoreElements()){
                                            FList tmp = (FList)results.nextElement();
                                            if(!(tmp.hasField(FldPlanObj.getInst()))) {
                                                tmp.set(FldPlanObj.getInst(), actPlanObj);
                                            }
                                        }
                                    }
// svsfl.set(FldDealInfo.getInst(),svsDealinfo);
                                    /**added by karan */
                                    actoutputdeal.set(FldDealInfo.getInst(), svsDealinfo);
                                    svsfl.setElement(FldDeals.getInst(), (nodeals-1), actoutputdeal);
                                }
                            }
                        }
                    } else {
                        infl = new FList();
                        infl.set(FldPoid.getInst(), svsDealObj);
                        actoutputdeal = conn.opcode(3,infl);
                        actoutputdeal.remove(FldPoid.getInst());
                        actoutputdeal.set(FldDealObj.getInst(), svsDealObj);
//svsfl.setElement (FldDeals.getInst (), 0, actoutputdeal);
//setting dealinfo at service level
                        FList svsDealinfo = conn.opcode(3,infl);
                        sArray = null;
                        sArray = svsDealinfo.get(FldProducts.getInst());
                        if(sArray != null){
                            Enumeration results = sArray.getValueEnumerator();
                            while(results.hasMoreElements()){
                                FList tmp = (FList)results.nextElement();
                                if(!(tmp.hasField(FldPlanObj.getInst()))) {
                                    tmp.set(FldPlanObj.getInst(), actPlanObj);
                                }
                            }
                        }
                        /**added by karan
 
                        svsf1.setElement(FldDeals.getInst(),0,actoutputdeal);*/
                        actoutputdeal.set(FldDealInfo.getInst(),svsDealinfo);
// svsfl.setElement (FldDeals.getInst (), 0, actoutputdeal);
                        svsfl.setElement(FldDeals.getInst(),0,actoutputdeal);
//svsfl.set(FldDealInfo.getInst (), svsDealinfo);
                    }
                    
                }
                
                
//out.print("<pre>" + serviceTemp + "</pre>");
            }
        }
    }
    
    if(noOfBalalceGroups > 0) {
/*
0 PIN_FLD_BAL_INFO      ARRAY [0] allocated 20, used 4
1     PIN_FLD_NAME            STR [0] "Balance Group<Account>"
1     PIN_FLD_LIMIT         ARRAY [586] allocated 20, used 1
2         PIN_FLD_CREDIT_LIMIT DECIMAL [0] NULL pin_decimal_t ptr
1     PIN_FLD_POID           POID [0] 0.0.0.1 /balance_group -1 0
1     PIN_FLD_BILLINFO      ARRAY [0]     NULL array ptr
 
        */
        FList flNewBalInfo = new FList();
        flNewBalInfo.set(FldName.getInst(),"Balance Group<Account>");
        flNewBalInfo.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/balance_group"));
        flNewBalInfo.setElement(FldBillinfo.getInst(),0, null);
        FList flCreditLimit = new FList();
        flCreditLimit.set(FldCreditLimit.getInst());
        flNewBalInfo.setElement(FldLimit.getInst(),Integer.parseInt( props.getProperty(CURRENCYCODE)),flCreditLimit);
        newAcctFl.setElement(FldBalInfo.getInst(),0,flNewBalInfo);
        FList flfAcctInfo1 = newAcctFl.getElement(FldBalInfo.getInst(),1);
        if(!flfAcctInfo1.hasField(FldPoid.getInst())) {
            flfAcctInfo1.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/balance_group"));
        }
        if(!flfAcctInfo1.hasField(FldBillinfo.getInst())) {
            flfAcctInfo1.setElement(FldBillinfo.getInst(),0, null);
        }
        
        
    }
// Final verifications
    FList flfAcctInfo = newAcctFl.getElement(FldAcctinfo.getInst(),0);
    if(!flfAcctInfo.hasField(FldPoid.getInst())) {
        flfAcctInfo.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/account"));
    }
    if(!flfAcctInfo.hasField(FldBalInfo.getInst())) {
        flfAcctInfo.setElement(FldBalInfo.getInst(),0, null);
    }
    flfAcctInfo = newAcctFl.getElement(FldBillinfo.getInst(),0);
    if(!flfAcctInfo.hasField(FldPoid.getInst())) {
        flfAcctInfo.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/billinfo"));
    }
    if(!flfAcctInfo.hasField(FldBalInfo.getInst())) {
        flfAcctInfo.setElement(FldBalInfo.getInst(),0, null);
    }
    if(!flfAcctInfo.hasField(FldBillinfoId.getInst())) {
        flfAcctInfo.set(FldBillinfoId.getInst(),"Bill Unit(1)");
    }
    flfAcctInfo = newAcctFl.getElement(FldPayinfo.getInst(),1);
    flfAcctInfo.set(FldName.getInst(), "Invoice1");
    
//out.print("<pre>" + newAcctFl.toString () + "</pre><hr>");
  /*System.out.println("*****************************************FLIST***********************************");  
  System.out.println(newAcctFl.toString());
      System.out.println("*****************************************FLIST-END***********************************"); */ 
//************************************************************ 
//out.print ("<pre>" + actoutputdeal.toString () + "<pre><hR><br>");
    
// -- End Device association.
//out.print("<pre>" + ((FList)conn.lookupModel(mH)).toString() + "</pre>");
//if(1==1) return;
    com.portal.app.util.CustomerValErrorData retVal[] = (com.portal.app.util.CustomerValErrorData[]) cb.createAccount(mH);
    if (retVal != null ) {
//errors may have occured
    if (retVal.length == 0) {
//cannot pinpoint error
        updateStatus = INTERNAL_ERROR;
    } else {
//extract error
        errorMap = ServletUtil.parseErrorData(accountCreationBean, retVal);
        
        updateStatus = FAILURE;
        if (errorMap.size() == 0) {
            errMsg = retVal[0].getErrorMessage();
            errFld = retVal[0].getFieldSpecification();
                
            if ( errMsg.toLowerCase().startsWith(DUPLICATE_ERROR.toLowerCase()) && (errFld.endsWith(FLD_LOGIN))) {
                updateStatus = bundle.getString("error.duplogin");
                existingLogin = true;
            }
                updateStatus = updateStatus + "<BR>" + errMsg;
                
        }
    }
    } else {
    updateStatus = SUCCESS;
    sTPin = (String)lcTPIN.getLightData();
    sucMsg=" <table width=90% border=0 cellpadding=0 cellspacing=0  class='Act-listing'><tr><th>Welcome to Wi-tribe</th></tr><tr   class='info-listing'><td>";
    sucMsg = sucMsg + "Lead#<b> [#LEADNO]</b> successfully converted to Account# <b>[#BRMACTNO]</b><br><br>";
    sucMsg = sucMsg + "New Provisioning Order Request#<b>[#ORDERID]</b> sent to provisioning System<br><br>";
    sucMsg = sucMsg + "Please remember the Userid to access MyAccount application:<b>[#MYACCTUSERID]</b><br></td></tr>";
    
    sucMsg = sucMsg + "<tr><td>Thank you.</td></tr></table>";
    if(serviceDescriptions != null ){
    for (int i = 0; i < serviceDescriptions.length; i++) {
        displayBuf.setLength(0);
        int index = serviceDescriptions[i].indexOf(' ');
        if (index > 0) {
            displayBuf.append(serviceDescriptions[i].substring(index + 1));
            displayBuf.append(" (");
            displayBuf.append(serviceDescriptions[i].substring(0, index));
            displayBuf.append(")");
        } else {
            displayBuf.append(serviceDescriptions[i]);
        }
        if( serviceDescriptions[i].indexOf("/service/ip") > -1) ipuserid = login[i];
        
    } } else {
            ipuserid = "";
        } 
    myutil.setCNIC((String)lcCNIC.getLightData());
    FList outlist = new FList();
    if(!((String)lcCustBusType.getLightData()).equalsIgnoreCase("4")) {
            outlist = myutil.getNewAccountOrderdetails(ipuserid);
        } else {
            outlist = myutil.getAffinityNewAccountOrderdetails( (String)lcEmail.getLightData(), (String)lcFname.getLightData(), (String)lcAddress.getLightData() );
        }
    String ActId = outlist.get(FldAccountObj.getInst()).toString();
//ActId = ActId.substring(ActId.indexOf("/account") + 9);
    
    String sOrderId = "";
    if(outlist.hasField(FldOrderId.getInst())) {
        sOrderId = outlist.get(FldOrderId.getInst());
    }
    if (sOrderId == null) {
        sOrderId = "";
    }
    String acctpassword =  myutil.getAcctPwd();
    sucMsg=sucMsg.replace("[#LEADNO]",r_LeadId);
    sucMsg=sucMsg.replace("[#BRMACTNO]",ActId);
    sucMsg=sucMsg.replace("[#ORDERID]",sOrderId);
    sucMsg=sucMsg.replace("[#MYACCTUSERID]",ipuserid);
    
    MailerFactory mail = (MailerFactory)pCS.createController("Wtb.MyAccount.MailerFactory");
    mail.setProperties(props);
    Poid actObj = outlist.get(FldAccountObj.getInst());
    mail.sendMail(3, actObj);
    if(serviceDescriptions != null ){
    mail.sendMail(4, actObj);
    }
    session.removeAttribute("accountCreationBean");
%>

<form name=frmUpdateLSLP method=post action="./RegisterAccount.do?siteId=SC">
    <input type=hidden name=acctNo value="<%=ActId%>">
    <input type=hidden name=salesId value="<%=r_SalesID%>">
    <input type=hidden name=leadId value="<%=r_LeadId%>">
    <input type=hidden name="siteId" value="SC">
    <input type=hidden name="acctType" value="<%=bill%>">
    <input type=hidden name=orderId value="<%=sOrderId%>">
    <input type=hidden name="cnic" value="<%= (String)lcCNIC.getLightData()%>">
    <input type=hidden name=rMessage value="<%=sucMsg%>">
</form>

<script language=javascript>
    document.frmUpdateLSLP.submit();
</script>
<%
//(pCS.getAppController()).releaseConnection(conn);
conn.close(true);
return;
}
} //end of if confirm

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<%
String windowTitle = WINDOW_TITLE;
String welcomeName = "";
if (session != null) {
    welcomeName = "Guest";
} else {
    welcomeName = "Guest";
}
String sectionImage = "text-login.gif"; //Billing: text-billing1.gif ;  MyAccount: text-my-account.gif
%>
<html xmlns="http://www.w3.org/1999/xhtml">

<script language="javascript" type="text/javascript">
//Make the XMLHttpRequest Object
//var http = createRequestObject();

function sendRequest(method, url){
//	if(method == 'get' || method == 'GET'){
//		http.open(method,url);
//		http.onreadystatechange = handleResponse;
//		http.send(null);
//	}
}

function handleResponse(){
	if(http.readyState == 4 && http.status == 200){
		var response = http.responseText;
		if(response){
			//document.getElementById("ajax_res").innerHTML = response;
		}
	} 
}
var http = createRequestObject(); 
function getMacDetails(type) {
    $('p.validationmsg').hide();
    document.frmCreate2.<%=CPE_DEVICE_ID%>.value='';
    document.frmCreate2.<%=CPE_MAC_STATUS%>.value = '';
    document.getElementById("info").innerHTML='<label>' + ' ' + '</label>';
    document.getElementById("dtype").innerHTML= '<label>' + ' ' + '</label>';
    document.getElementById("dman").innerHTML= '<label>' + ' ' + '</label>';
    document.getElementById("dmdl").innerHTML= '<label>' +  ' ' + '</label>';
    document.getElementById("dwan").innerHTML='<label>' + ' ' + '</label>';
    document.getElementById("dnovoipnumbers").innerHTML='<label>' + ' ' + '</label>';
    document.frmCreate2.<%=CPE_MAC_ADDRESS%>.value = trimString(document.frmCreate2.<%=CPE_MAC_ADDRESS%>.value);
    document.frmCreate2.<%=CPE_SERIAL_NUMBER%>.value = trimString(document.frmCreate2.<%=CPE_SERIAL_NUMBER%>.value);
	var proceed = false;

    if(type == "0") {
		if(document.frmCreate2.<%=CPE_MAC_ADDRESS%>.value != "") 
			proceed = true;
    } else {
		if(document.frmCreate2.<%=CPE_SERIAL_NUMBER%>.value != "") 
			proceed = true;
	}
        if(proceed ) {
		 if(type == "0")
                showprogess('DEVICE');
            else 
                showprogess('SERIALNO');
            var url="getDeviceDetails.jsp";
            var customerIDValue = ''; 
            var params = '';
            if(type == "0") {
              customerIDValue =  document.getElementById("<%=CPE_MAC_ADDRESS%>").value;
             //params = "WANAddr="+customerIDValue;
             params = "WANAddr="+customerIDValue+"&SalesId="+"<%=(String)lcSalesID.getLightData()%>";
           } 
            else {
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
        else {
		if(type == "0")
            showMessage(document.frmCreate2.<%=CPE_MAC_ADDRESS%>,'Enter MAC Address of the device.');
		if(type == "1")
			 showMessage(document.frmCreate2.<%=CPE_SERIAL_NUMBER%>,'Enter Serial Number of the device.');
        }
}
function handleDeviceDetails() {
	
	//alert(http.readyState);
	if(http.readyState == 4 && http.status == 200) {
		//document.frmCreate2.deviceDetails.value = http.responseText;
		hideprogess();
		parseXML(http.responseText);
	}
}

var xmlDoc;

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
	if(callFrom == 'SERIALNO') { 
		displaymsg = templateMsg.replace("[MsgToShow]", "Validating Serial Number. Please wait..");
	}
	if(callFrom == 'COVERAGE') { 
		displaymsg = templateMsg.replace("[MsgToShow]", "Checking coverage is in progress..");
	}
        if(callFrom == 'VOIP') { 
		displaymsg = templateMsg.replace("[MsgToShow]", "Checking Special Numbers is in progress..");
	}
	
	document.getElementById('progressbar').innerHTML = displaymsg;
	document.getElementById('progressbar').style.visible="block";
	document.getElementById('progressbar').style.visibility="visible";
	}
function hideprogess() {
	document.getElementById('progressbar').style.visible="none";
	document.getElementById('progressbar').style.visibility="hidden";
}
var arrVoipLines = new Array();
function changeVoIPLineNumbers(sel1,selVoipLine, txPassword,ckCheckBox,index) {
    $('p.validationmsg').hide();
    if(ckCheckBox.status == true) {
        if(trimString(txPassword.value) == '' ) {
            alert('Enter password');
            return;
        } else {
            if(sel1.options[sel1.selectedIndex].value > -1) {
                    while(selVoipLine.options.length > 0) {
                        selVoipLine.options[0]=null;
                    }
                    var topt = new Option();
        topt.text = "Select VoipLine No";
        topt.value = "-1";
        selVoipLine.options.add(topt);
        
        if( eval("arrVoipSplNumbers_"+index + "!= null") && eval("arrVoipSplNumbers_"+index + "!= ''")  ){
        for(i=0; i <eval("arrVoipSplNumbers_"+index + ".length") ; i++ ) {
            if(sel1.options[sel1.selectedIndex].value == eval("arrVoipSplNumbers_"+index + "["+i+"][0]")) {
                var opt = new Option();
                opt.value=eval("arrVoipSplNumbers_"+index + "["+i+"][1]");
                opt.text=eval("arrVoipSplNumbers_"+index + "["+i+"][2]");
                selVoipLine.options.add(opt);
        }
        }
        }
          } else {
                showMessage(selVoipLine, 'Please select the Vanity Type');
                return(false);
            }
        }
    } else {
        if(sel1.options[sel1.selectedIndex].value > -1) {
            while(selVoipLine.options.length > 0) {
            selVoipLine.options[0]=null;
	}
        var topt = new Option();
        topt.text = "Select VoipLine No";
        topt.value = "-1";
        selVoipLine.options.add(topt);
                for(i=0; i <arrVoipLines.length ; i++ ) {
            if(sel1.options[sel1.selectedIndex].value == arrVoipLines[i][0]) {
                var opt = new Option();
                opt.value=arrVoipLines[i][1];
                opt.text=arrVoipLines[i][2];
                selVoipLine.options.add(opt);
            }
        }
        } else {
            showMessage(selVoipLine, 'Please select the Vanity Type');
            return(false);
        }
    }
    
}
var http = createRequestObject();
var gvoipindex = -1;

function changeVoIPLineNumbers1(sel1,voipindex, voipline, vanity) {
    gvoipindex = -1;
    gvoipindex = voipindex;
    vanity.selectedIndex = 0;
    while(voipline.options.length > 0) {
            voipline.options[0]=null;
	}
        var topt = new Option();
        topt.text = "Select VoipLine No";
        topt.value = "-1";
        voipline.add(topt);
        $('p.validationmsg').hide();
            var url="getSpecialNumbers.jsp";
            var customerIDValue = ''; 
            var params = '';
            var serviceType='';
              //customerIDValue =  document.getElementById("password1").value;
              customerIDValue =  sel1.value;
              if(customerIDValue  =="")
              {
              alert("please eneter password");
              }
              var billType=<%=bill%>;
              //alert(billType);
              if(billType=="10000"){
              serviceType=1;
              }
              else{
              serviceType=2;
              }
             params = "WANAddr="+customerIDValue+"&City="+"<%=(String)lcCity.getLightData()%>"+"&ServiceType="+serviceType;
             http.open("POST", url , true);
             http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
             http.setRequestHeader("Content-length", params.length);
             http.setRequestHeader("Connection", "close");
             http.onreadystatechange = handleDeviceDetails1;
             http.send(params);
          
}
function handleDeviceDetails1() {
	
	if(http.readyState == 4 && http.status == 200) {
        
		//document.frmCreate2.deviceDetails.value = http.responseText;
		hideprogess();  
                parsXML(http.responseText);
      }
        
}

var xmlDoc;

function parsXML(text)
{
	try //Internet Explorer
	{
		xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async="false";
                 
		xmlDoc.loadXML(text);
                 
                xmlObj=xmlDoc.documentElement;
                 
                var nodelist = xmlObj.getElementsByTagName("VOIPNUMBER");
		//alert(gvoipindex);
                eval("arrVoipSplNumbers_" + gvoipindex + " = new Array()");
                
		for(i=0;i<nodelist.length;i++) {
                voidpoidid = nodelist[i].childNodes[0].childNodes[0].nodeValue;
			voidnum = nodelist[i].childNodes[1].childNodes[0].nodeValue;
			vanity = nodelist[i].childNodes[2].childNodes[0].nodeValue;
			//alert(voidpoidid);
			//alert(voidnum);
                    //alert("arrVoipSplNumbers_" + gvoipindex + "[" + i + "] = new Array('" + vanity + "','" + voidpoidid + "','" + voidnum +"')");
                     eval("arrVoipSplNumbers_" + gvoipindex + "[" + i + "] = new Array('" + vanity + "','" + voidpoidid + "','" + voidnum +"')");
                                                        
		}
                //alert( xmlObj.getElementsByTagName("VOIPNUM")[0].childNodes[0].nodeValue);
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
//alert( xmlObj.getElementsByTagName("VOIPNUM")[0].childNodes[0].nodeValue);
//alert(voipList);

}

</script>

 <script language="javascript">
     //For check box
    function showHideDiv( chk, channelidnum, sel1, sel2)
    {
       channelidnum.value = '';
        if(chk.status== true) {
        channelidnum.style.visibility = "visible";
        sel1.selectedIndex = 0;
        sel2.selectedIndex = 0;
         while(sel2.options.length > 0) {
            sel2.options[0]=null;
	}
        var topt = new Option();
        topt.text = "Select VoipLine No";
        topt.value = "-1";
        sel2.options.add(topt);
        } else {
            channelidnum.style.visibility = "hidden";
                    sel1.selectedIndex = 0;
        sel2.selectedIndex = 0;
 while(sel2.options.length > 0) {
            sel2.options[0]=null;
	}
        var topt = new Option();
        topt.text = "Select VoipLine No";
        topt.value = "-1";
        sel2.options.add(topt);
        }
    
    
             
        
        
    }
function hidePwd()
    {
    
   //
   }
    </script>   


 <body onload="javascript:hidePwd();">



<div id="inner-content">

<!-- Inner content starts here -->                       
<!-- messge start -->
<P>Fields marked with an asterisk (<SPAN class=mandatory>*</SPAN>) are mandatory.</P> 
<!-- message end -->
<div class="shadow-box">
<div class="shadow-box-inner">
<% if (existingLogin) {%>
<table border=0 cellpadding=0 cellspacing=0 width=95% class='Act-listing'>
    <tr><th><%=updateStatus%></th></tr>
</table>
<% } else if(!updateStatus.equals(SUCCESS)) { %>
<table border=0 cellpadding=0 cellspacing=0 width=95% class='Act-listing'>
    <tr><th><%=updateStatus%></th></tr>
</table>
<% } %>
<% if(!updateStatus.equals(SUCCESS)) {%>
<%!
StringBuffer serviceBuf = new StringBuffer();
StringBuffer uidService = new StringBuffer();
boolean IpServiceExists = false;
boolean VoIPServiceExists = false;
%>
<!--  -->
<form name=frmCreate2 method="post" action="./LeadAccountCreationNextAction.do" onsubmit="return newAcctNext(this) ">
<table width="95%" border="0" cellspacing="0" cellpadding="0"  class="Act-listing">
<% if(serviceDescriptions != null ) {    %>    
<tr><th colspan="3"><label for="SectionHead">Service Login Details</label></td></tr>
<tr  class='info-listing'>
    <td><label>User Name<span class="mandatory">&nbsp;*</span></label></td>
    <td><label><!-- Password<span class="mandatory">&nbsp;*</span> --></label></td>
    <td><label><!-- Re-Type Password<span class="mandatory">&nbsp;*</span> --></label></td>
</tr>
<% } else {%>            
<tr><th colspan="3"><label for="SectionHead">Service  Details</label></td></tr>
<tr  class='info-listing'>
    <td colspan=3><label>This account does not associated with any Services.</label></td>
</tr>

<% } %>
<% 
boolean macdisplay = false;
int voiplinenumber=0;
List VoIPList = null;
if(serviceDescriptions != null ) {
for (int i = 0; i < serviceDescriptions.length; i++) {
    noofServices= noofServices + 1;
// get the service type and generate a display string from the service description string
    serviceBuf.setLength(0);
    displayBuf.setLength(0);
    int index = serviceDescriptions[i].indexOf(' ');
    if (index > 0) {
        serviceBuf.append(serviceDescriptions[i].substring(0, index));
        displayBuf.append(serviceDescriptions[i].substring(index + 1));
        displayBuf.append(" (");
        displayBuf.append(serviceDescriptions[i].substring(0, index));
        displayBuf.append(")");
    } else {
        serviceBuf.append(serviceDescriptions[i]);
        displayBuf.append(serviceDescriptions[i]);
    }
    uidService.setLength(0);
    uidService.append(i);
    if(displayBuf.indexOf("/service/ip") > -1) {
        IpServiceExists = true;
%>
<tr><th colspan=3 align=left><label for="address1">(<%=(i+1)%>). <%=displayBuf%></label></th></tr>
<tr class='info-listing'>
    <td><input type="text" tabindex=0 class="txtxx" Name='<%="login_"+uidService%>' MAXLENGTH="25"  Value=""  /><p class="validationmsg"></p></td>
    <td><input style="visibility:hidden" type="password" class="txtxx" Name='<%="pin_"+uidService%>' MAXLENGTH="25"  Value="password" /><p class="validationmsg"></p></td>
    <td><input style="visibility:hidden"  type="password" class="txtxx" Name='<%="pinv_"+uidService%>' MAXLENGTH="25"  Value="password" /><p class="validationmsg"></p></td>
</tr>
<% 
    } else if (displayBuf.indexOf("/service/telco/voip") > -1) {
        voiplinenumber = voiplinenumber +1;
        if (voiplinenumber == 1) {
            if(bill.equals(PREPAID)) {
                VoIPList = myutil.getVoIPNumbers((String)lcCity.getLightData(),1);
            } else {
                VoIPList = myutil.getVoIPNumbers((String)lcCity.getLightData(),2);
            }
        }
%>
<script language="javascript">
                                                        var arrVoipSplNumbers_<%=voiplinenumber%> = new Array();
            <%
            if(VoIPList != null) {
            for(int indexvoiplist=0 ; indexvoiplist< VoIPList.size(); indexvoiplist++) {
            FList flTemp = (FList)VoIPList.get(indexvoiplist);
            //out.print("<pre>" + flTemp.toString() + "</pre>");
            //out.print("<pre>" + flTemp.get(FldDeviceNum.getInst()).get(FldVanity.getInst()) + "</pre>");

            %>
                                                        arrVoipLines[<%=indexvoiplist%>] = new Array("<%=flTemp.get(FldDeviceNum.getInst()).get(FldVanity.getInst())%>","<%=(flTemp.get(FldPoid.getInst()).toString())%>","<%=flTemp.get(FldDeviceId.getInst())%>");
            <% } } %>
</script>
<tr><th colspan=3 align=left><label for="address1">(<%=(i+1)%>). <%=displayBuf%></label></th></tr>
<tr class='info-listing'>
    <td><select name='<%="voip" + voiplinenumber%>' onchange='document.frmCreate2.<%="login_"+uidService%>.value=this.options[this.selectedIndex].value;'>
            <option value="">Select VoIp Line Number</option>
    </select><input type="hidden" title=voip tabindex=0 class="txtxx" Name='<%="login_"+uidService%>' MAXLENGTH="25"  Value=""  /><p class="validationmsg"></p></td>
    <td><select name='sel_vanity_<%="voip" + voiplinenumber%>' onchange='changeVoIPLineNumbers(this,document.frmCreate2.<%="voip" + voiplinenumber%>,document.frmCreate2.<%="channelidnum" + voiplinenumber%>, document.frmCreate2.<%="reserved" + voiplinenumber%>,<%=voiplinenumber%>)'>
            <option selected value='-1'>Select Vanity Type</option>
            <option value='1'>Normal</option>
            <option value='2'>Platinum</option>
            <option value='3'>Gold</option>
            <option value='4'>Silver</option>
    </select><input style="visibility:hidden" title="voippwd" type="password" class="txtxx" Name='<%="pin_"+uidService%>' MAXLENGTH="25"  Value="password" /><p class="validationmsg"></p></td>
    <td><input style="visibility:hidden" title=voippwd type="password" class="txtxx" Name='<%="pinv_"+uidService%>' MAXLENGTH="25"  Value="password" /><p class="validationmsg"></p></td>
</tr>

<tr class='info-listing'> </td>
        <div id="div1" >
        <td id="channelidnum"  style="visibility:hidden">
                Password for SpecialNum: <input type="password" Id ="password1" name='<%="channelidnum" + voiplinenumber%>' value="" onblur='changeVoIPLineNumbers1(document.frmCreate2.<%="channelidnum" + voiplinenumber%>,<%=voiplinenumber%>, document.frmCreate2.<%="voip" + voiplinenumber%>, document.frmCreate2.sel_vanity_<%="voip" + voiplinenumber%>)'/>
                
        </td>
    </div> 
        <td>
          ReservedNumbers <input type="checkbox" value="reserved" name='<%="reserved" + voiplinenumber%>' onclick='showHideDiv(this,document.frmCreate2.<%="channelidnum" + voiplinenumber%>, document.frmCreate2.sel_vanity_<%="voip" + voiplinenumber%>,document.frmCreate2.<%="voip" + voiplinenumber%>)'/>
        </td>
  
  </tr>
<tr  class='info-listing'>
<td><label>Access Level</label></td>
       <td colspan=2><select name="accesslevel"  id="accesslevel_<%=voiplinenumber%>" >
              <!--<option selected value='0'>Select Access Level</option>-->
              <% if(bill.equals(PREPAID))
                  { %>
              <option selected value='2'>INTL</option>
                 <% } 
              else { %>
              <option selected value='1'>NWD</option>
              <option value='2'>INTL</option>
              <% } %>
               </select>
       </td>
</tr>

<% } else if (displayBuf.indexOf("/service/email") > -1) {%>
<tr><th colspan=3 align=left><label for="address1">(<%=(i+1)%>). <%=displayBuf%></label></th></tr>
<tr class='info-listing'>
    <td><input type="text" tabindex=0 class="txtxx" Name='<%="login_"+uidService%>' MAXLENGTH="25"  Value=""  /><p class="validationmsg"></p></td>
    <td><input style="visibility:hidden" type="password" class="txtxx" Name='<%="pin_"+uidService%>' MAXLENGTH="25"  Value="password" /><p class="validationmsg"></p></td>
    <td><input style="visibility:hidden"  type="password" class="txtxx" Name='<%="pinv_"+uidService%>' MAXLENGTH="25"  Value="password" /><p class="validationmsg"></p></td>
</tr>
<% } %>
<%
if(displayBuf.indexOf("/service/ip") > -1) {
        macdisplay = true;
}
    
} } // end of for
if(macdisplay) { //Display mac address capture section%>   
<tr><th colspan="3"><label for="SectionHead">Device Details</label></td></tr>
<tr  class='info-listing'>
    <td><label>CPE MAC Address<span class="mandatory">&nbsp;*</span></label></td>
    <td colspan=2><INPUT tabindex=1   class="txtxx" Type="text"  Maxlength=50 Value="<%  if (referrer.equals(STEP2)) { out.print((String)formInput.get(CPE_MAC_ADDRESS));} %>" Name="<%=CPE_MAC_ADDRESS%>" id="<%=CPE_MAC_ADDRESS%>" onblur="getMacDetails(0)"><P class=validationmsg></P></td>
    <!--<td> <input type=button value='Get MAC Details' onclick="getMacDetails()"> </td>-->
    
</tr>
<tr  class='info-listing'>
    <td><label>Serial Number<span class="mandatory">&nbsp;*</span></label></td>
    <td colspan=2><INPUT  class="txtxx" Type="text"  Maxlength=50 Value="<%  if (referrer.equals(STEP2)) { out.print((String)formInput.get(CPE_SERIAL_NUMBER));} %>" Name="<%=CPE_SERIAL_NUMBER%>" id="<%=CPE_SERIAL_NUMBER%>" onblur="getMacDetails(1)"><P class=validationmsg></P></td>
    <!--<td> <input type=button value='Get MAC Details' onclick="getMacDetails()"> </td>-->
                                                    
</tr>
<tr class="info-listing">
    <td colspan=3>
        <table border=0 width=100% cellpadding=0 cellspacing=0 class='Act-listing'>
            <tr><th>MAC Address</th><th>CPE Type</th><th>Manufacturer</th><th>Model</th><th>Status</th><th>VoIP Enabled</th></tr>
            <tr><td><div id="dwan">&nbsp;</div>
                    <input type=hidden  name="<%=CPE_MAC_STATUS%>" id="<%=CPE_MAC_STATUS%>" value="">
                    <input type=hidden name="<%=CPE_DEVICE_ID%>" id="<%=CPE_DEVICE_ID%>" value="">
                    <input  type=hidden name="VoIPEnabled" id="VoIPEnabled" value="">
                    <input type=hidden name="NoOfVoIpPorts" id="NoOfVoIpPorts" value="">
                </td>
                <td> <div id="dtype">&nbsp;</div></td>
                <td><div id="dman">&nbsp;</div>
                <div id="did">&nbsp;</div></td>
                <td><div id="dmdl">&nbsp;</div>
                    <div id="dpid">&nbsp;</div>
                <div id="dlan">&nbsp;</div></td>
                <td><div id="info">&nbsp;</div>
                <div id="dstate">&nbsp;</div></td>
                <td align=right><div id="dnovoipnumbers">&nbsp;</div></td>
        </tr></table>
    </td>
</tr>
<%} // Display Mac address capture seciton ends here%>
<!--  Invoice address section -->
<%                                                  
String sPlot = "";
String sStreet = "";
String sZone = "";
String sSubZone = "";

String[] sTempBillAddr = null;

selBillType = (String)lcBillType.getLightData();
if(selBillType.equals(INVOICE) || selBillType.equals(DEBIT) || selBillType.equals(CREDIT) ) {
    sTempBillAddr = inv_address.split("   ");
    sPlot = sTempBillAddr[0];
    sStreet = sTempBillAddr[1];
    sSubZone = sTempBillAddr[2];
    sZone = sTempBillAddr[3];
    
    String blEmail = " Checked ";
    String  blPost = " checked ";
    String deliverMethodStr = "";
    if (deliverMethodStr != null) {
        if("0".equals(deliverMethodStr) ) {
            blEmail = " checked ";
            blPost = "";
        }
        if("1".equals(deliverMethodStr) )  {
            blPost = " checked ";
            blEmail = "";
        }
    }
%>
<tr><th colspan="3"><table border=0 cellpadding=0 width=100% cellspacing=0 class="info-listing">
    <%  if(selBillType.equals(DEBIT)) { %>
    <!-- Dibit card details start -->
<tr><th colspan="4"><label for="SectionHead">Direct Debit Details</label></td></tr>
<tr>
<td><label>Account Type<span class="mandatory">&nbsp;*</span></label></td>
<td><select NAME="account_type" class="txtxx">
        <% { String accounts[] ={"Checking","Savings","Corporate"};
        String ddtype = (String) lcDDType.getLightData();
        if (ddtype == null) ddtype="1";
        for (int i=0;i<accounts.length;i++) {
            if (ddtype.equals(String.valueOf(i+1))) { %>
        <OPTION VALUE="<%=String.valueOf(i+1)%>" SELECTED> <%=accounts[i]%>
                                                           <% } else { %>
                                                           <OPTION VALUE="<%=String.valueOf(i+1)%>"> <%=accounts[i]%>
        <% }
        }
        } %>
</select></p></td><td><label>Bank code<span class="mandatory">&nbsp;*</span></td>
<td ><input TYPE="TEXT" class="txtxx"  NAME="bank" Value="<%=((String) lcBankNo.getLightData() == null ? "" : (String) lcBankNo.getLightData())%>" maxlength="16"><P class=validationmsg></p></td></tr>
<tr  class=info-listing><td><label>Account No.<span class="mandatory">&nbsp;*</span></td><td colspan=3>
        <input Type="text" class="txtxx"  Name="account_num" Value="<%=((String) lcAccountNum.getLightData() == null ? "" : (String) lcAccountNum.getLightData())%>" maxlength="20"><P class=validationmsg></p>
        <script language=javascript>
                                                                    <% if (ServletUtil.checkError(errorMap, lcBankNo) || ServletUtil.checkError(errorMap, lcBankNo) || ServletUtil.checkError(errorMap, lcBankNo)) { %> 
                                                                        showMessage(document.frmCreate2.bank, 'Please verify Bank Code details');
                                                                    <% } %>
                                                                    <% if (ServletUtil.checkError(errorMap, lcAccountNum) || ServletUtil.checkError(errorMap, lcAccountNum) || ServletUtil.checkError(errorMap, lcAccountNum)) { %> 
                                                                        showMessage(document.frmCreate2.account_num, 'Please Bank a/c No details');
                                                                    <% } %>
        </script>
</td></tr>
<!-- Dibit card details end  -->
<% } // Debit ends here%>
<%  if(selBillType.equals(CREDIT)) {
        String debitNo = "";
%>
<!-- Credit card details start -->
<tr><th colspan="4"><label for="SectionHead">Credit Card Details</label></td></tr>

<tr><td><label>Card No.<span class="mandatory">&nbsp;*</span></td>
    <td colspan=3>
        <INPUT Type="text"  class="txtxxx" Name="debitnum" Value="<%=((String) lcDebitNum.getLightData() == null ? "" : (String) lcDebitNum.getLightData())%>" MAXLENGTH="16">
        <P class=validationmsg></p>
    </td>
</tr>
<tr><td><label>CVV2/CID<span class="mandatory">&nbsp;*</span></td>
    <td><INPUT Type="text" class="txtxx" Name="cv" Value="<%=((String)lcCV.getLightData() == null ? "" : (String)lcCV.getLightData())%>" MAXLENGTH="4">
        <P class=validationmsg></p>
    </td>
    <td><label>Expiry Date<span class="mandatory">&nbsp;*</span></td>
    <td><INPUT Type="text" Name="debitexp" class="txtxx" Value="<%=((String) lcDebitExp.getLightData() == null ? "" : (String) lcDebitExp.getLightData())%>" MAXLENGTH="5">
        <P class=validationmsg></p>
        <script language=javascript>
                                                                    <% if (ServletUtil.checkError(errorMap, lcDebitNum) || ServletUtil.checkError(errorMap, lcDebitNum) || ServletUtil.checkError(errorMap, lcDebitNum)) { %> 
                                                                        showMessage(document.frmCreate2.debitnum, 'Please verify Credit Card No details');
                                                                    <% } %>
                                                                    <% if (ServletUtil.checkError(errorMap, lcCV) || ServletUtil.checkError(errorMap, lcCV) || ServletUtil.checkError(errorMap, lcCV)) { %> 
                                                                        showMessage(document.frmCreate2.cv, 'Please verify CVV No details');
                                                                    <% } %>
                                                                    <% if (ServletUtil.checkError(errorMap, lcDebitExp) || ServletUtil.checkError(errorMap, lcDebitExp) || ServletUtil.checkError(errorMap, lcDebitExp)) { %> 
                                                                        showMessage(document.frmCreate2.debitnum, 'Please verify Credit Card expiry details');
                                                                    <% } %>
        </script>
        
</td></tr>

<!-- Credit card details end  -->
<% } // credit ends here%>
<tr><th colspan="4"><label for="SectionHead">Billing Address</label></td></tr>
<% if(selBillType.equals(INVOICE)) { %>
<tr class=info-listing>
<Td><label for="address1">Delivery Method</lable></TD><td>
    <table border=0 cellpadding=0 cellspacing=0 class="info-listing">
        <tr><Td>
                <input type=radio <%=blPost%> name="Deliver_method" value="1"><label for="address1"> Postal&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
            </td><td>
    <input <%=blEmail%> type=radio name="Deliver_method" value="0"> <label for="address1">E-Mail</label></td></tr></table>
</td>
<td>&nbsp;</td><td>&nbsp;</td>
</tr>
<% } %>

<tr>
    <td><label for="address1">Name<span class="mandatory">&nbsp;*</span></label></td>
    <td ><input type="text" class="txtxx" name="invoice_name" value="<%=((inv_name != null) ? inv_name : "" )%>" /><p class="validationmsg"></p></td>
    <%  if(selBillType.equals(INVOICE)) { %>
    <td><label for="address1">Email<span class="mandatory">&nbsp;*</span></label></td>
    <td >
        <input type="text" class="txtxx" name="inv_email" value="<%=((inv_email != null ) ? inv_email : "")%>" /><p class="validationmsg"></p>
    </td>
    <% } else { %>
    <td colspan=2>&nbsp;</td>
    <% } %>
</tr>
<script language="javascript">
                                                function updateBillingAddress() {
                                                    document.frmCreate2.bill_address1.value =  document.frmCreate2.txtPlot.value + "   ";
                                                    document.frmCreate2.bill_address1.value =  document.frmCreate2.bill_address1.value + document.frmCreate2.txtSteet1.value + "   ";
                                                    document.frmCreate2.bill_address1.value =  document.frmCreate2.bill_address1.value + document.frmCreate2.txtSubzone.value + "   ";
                                                    document.frmCreate2.bill_address1.value =  document.frmCreate2.bill_address1.value + document.frmCreate2.txtzone.value + "   ";
                                                }
</script>
<tr><td><label for="address1">House/Plot<span class="mandatory">&nbsp;*</span></label></td>
    <td ><input type="text" class="txtxx" name="txtPlot" value="<%=sPlot%>" /><p class="validationmsg"></p></td>
    <td><label for="address1">Street<span class="mandatory">&nbsp;*</span></label></td>
    <td ><input type="text" class="txtxx" name="txtSteet1" value="<%=sStreet%>" /><p class="validationmsg"></p></td>
</tr>
<tr>
    <td><label for="address2">Subzone<span class="mandatory">&nbsp;*</span></label></td>
    <td ><input type="text" class="txtxx" name="txtSubzone" value="<%=sSubZone%>" /><p class="validationmsg"></p></td>
    <td><label for="address2">Zone<span class="mandatory">&nbsp;*</span></label></td>
    <td ><input type="text" class="txtxx" name="txtzone" value="<%=sZone%>" /><p class="validationmsg"></p>
        
    <input type=hidden NAME="bill_address1" value="<%=inv_address%>" /></td>
</tr>
<tr  class="info-listing"><td><label>City<span class="mandatory">&nbsp;*</span></label></td><td align="left">
        <select name=bill_city class="txtxx" onchange='document.frmCreate2.bill_state.value=this.options[this.selectedIndex].title;'>
            <option title="" value="">Select City</option>
            <%
            if(lstCities != null) {
        if(lstCities.size() > 0) {
            for(int i=0; i< lstCities.size(); i++) {
                String sTemp = ((FList)lstCities.get(i)).get(WtbFldCity.getInst());
                String sTempProv = ((FList)lstCities.get(i)).get(WtbFldProvince.getInst());
                if(inv_city.equalsIgnoreCase(sTemp)) {
                    out.print("<option  title='"+ sTempProv +"'   selected value='" + sTemp + "'>" + sTemp + "</option>");
                } else {
                    out.print("<option  title='"+ sTempProv +"'   value='" + sTemp + "'>" + sTemp + "</option>");
                }
            }
        }
            } 
            
            %>
        </select>
    <P class=validationmsg></p></td>
<td><label>Zip</label></td><td><INPUT TYPE="TEXT" NAME="bill_zip" class="txtxx" SIZE="9" value="<%=inv_zip%>" ><P class=validationmsg></p></td></tr>
<tr><td><label>Province<span class="mandatory">&nbsp;*</span></label></td><td><INPUT TYPE="TEXT" class="txtxx" readonly  NAME="bill_state" value="<%=inv_state%>" ><P class=validationmsg></p></td>
<td><label>Country</td><td><%=DEFAULTCOUNTRY%><input type=hidden name="bill_country" value="<%=DEFAULTCOUNTRY%>"></td></tr>

</table> </td>
</tr>



<%}//invoice/debit/credit ends here 
if((String)lcTPIN.getLightData()!=null ) {
sTPin = (String) lcTPIN.getLightData ();
}
%> 
<tr  class="bill-listing"><th>&nbsp;</th><th>
        <input type="hidden" name="ipserviceexists" value=<%=IpServiceExists%>>
        <INPUT type="hidden" name="PayType" value="<%=pType%>">
        <INPUT type="hidden" name="ParentPoid" value="<%=pPoid%>">
        <INPUT type="hidden" name="val" value=<%=noofServices%>>
            <INPUT Type="hidden" Name="ipAddress" Value="<%=request.getRemoteAddr()%>">
        <INPUT Type="hidden" Name="referrer" Value="<%=STEP2%>">
        <INPUT Type="hidden" Name="brand" Value="<%=brand%>">
        <INPUT Type="hidden" Name="currency" Value="<%=props.getProperty("localCurrency")%>">
        <INPUT Type="hidden" Name="aacaccess" Value="WEBKIT">
        <input type=hidden Name=<%=TPIN%> value="<%=sTPin%>">
        <input type=hidden Name="tmpBusinessType" value="<%=(String)lcCustBusType.getLightData()%>">
               
               <%
               
               String strLocal = "en_US";
               
               %>
               <input Type="hidden" Name="locale" Value="<%=strLocal%>">
        
        
        <INPUT tabindex=2 ALIGN="center" class=submit-btn 
               onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
               onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
               src="images/btn-submit-thickbox.gif" 
               name=btnSubmit 
               <% if( selBillType.equals(INVOICE) ) {%>
               ONCLICK="javascript: updateBillingAddress()"
               <%  } %>
></th><th>&nbsp;</th></tr>
</table>
<%}%>         

</form>
<% if( updateStatus.equals(SUCCESS)) {%>
<table width=90% border=0 cellpadding=0 cellspacing=0><tr><Td>
            <%=sucMsg%>
</TD></TR></table>
<% session.invalidate();
} %>  
</div>
</div>
<!-- Inner content ends here -->                        
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

