<%@ page contentType="text/html;charset=UTF-8" language="java" import="customfields.*, com.portal.bas.*, com.portal.bas.comp.*, com.portal.pcm.*, com.portal.pcm.fields.* , com.portal.app.ccare.comp.*, Wtb.MyAccount.*, com.portal.web.comp.*, com.portal.web.*, java.net.*, java.util.*, java.text.*"%>

<%@ include file="includes/constants.jsp"%>
<%@ include file="includes/ServiceConstants.jsp"%>

<%
String reffrom = request.getParameter("salesweb");
String urlFrom = request.getHeader("referer");
String salesId = request.getParameter("Dealerid");


if (reffrom == null)
    reffrom = "";
if (salesId == null)


%>

<jsp:useBean id="accountCreationBean" class="com.portal.bas.comp.PIAComponentCollection" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 

<%  
PLightComponentHelper lcSalutation, lcFirstName, lcMiddleName, lcLastName, lcSvcAddress, lcSvcCity, lcSvcState, lcSvcZip, lcSvcCountry, lcSvcEmail, lcCNIC, lcDOB;
PLightComponentHelper lcHomePhone, lcWorkPhone, lcFaxPhone, lcMobilePhone;
PLightComponentHelper lcBusinessType, lcTitle, lcOrganization, lcBillType, lcActSvsType;
PLightComponentHelper lcCoverageType, lcLatitude, lcLongitude, lcSLA;
PLightComponentHelper lcContractPeriod, lcProPackage, lcContractStart, lcContractEnd,  lcActUsageLimit, lcSvsLoginType, lcTaxExptType, lcTaxExcptPercent, lcWtbActSvsType, lcSelPlan, lcParent;
PLightComponentHelper  lcCStart, lcCEnd;
String planIndex = "";

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


PCachedContext conn = null;
PModelHandle mH = null;
PModelHandle myModel = null;

if (accountCreationBean.getController() == null) {
    pCS =  new PPooledConnectionClientServices((PClientServices)application.getAttribute(ServletUtil.PARENT_SERVICE));
    accountCreationBean.setServices(pCS);
    session.setAttribute(CREATE_CONNECTION, pCS);
    ConnectionListener listener = new ConnectionListener(session.getCreationTime() , pCS);
    session.setAttribute(CREATE_LISTENER, listener);
    ServletUtil.saveLocaleInfo(request);
    pCS.registerApp(APP_NAME, (Locale) session.getAttribute(ServletUtil.LOCALE), null);
    
    accountCreationBean.createController("com.portal.app.ccare.comp.PIACreateAccountBeanImpl");
    
}

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
lcCStart = accountCreationBean.getChild(CONTRACT_START);
lcCEnd = accountCreationBean.getChild(CONTRACT_END);

lcTitle =  accountCreationBean.getChild(TITLE);
lcOrganization = accountCreationBean.getChild(COMPANY);
lcBillType = accountCreationBean.getChild(BILL);
lcActSvsType = accountCreationBean.getChild(WTB_ACCT_SERVICE_TYPE);
lcContractPeriod = accountCreationBean.getChild(CONTRACT_PERIOD);
lcProPackage = accountCreationBean.getChild(PROMO_PKG);
lcSvsLoginType = accountCreationBean.getChild( SVS_LOGIN_TYPE);
lcTaxExptType = accountCreationBean.getChild( TAX_EXPT_TYPE);
lcTaxExcptPercent = accountCreationBean.getChild( TAX_EXPT_PERCENT);
lcWtbActSvsType = accountCreationBean.getChild( WTB_ACCT_SERVICE_TYPE);
lcParent = accountCreationBean.getChild(PARENT);
String sTPin ="";
String inv_name, inv_address, inv_city, inv_state, inv_zip, inv_country, inv_email;
inv_name="";
inv_address="";
inv_city="";
inv_state="";
inv_zip="";
inv_email="";
PIACAPackageInfoBean plansBean = (PIACAPackageInfoBean)session.getAttribute(PLANS_BEAN);
PModelHandle plansModel = (PModelHandle)plansBean.listOfPlans();
String brand=null;

String[] serviceDescriptions = null;
Properties props = pCS.getDefaultProperties();
Map formInput = ServletUtil.gatherFormInput(request);
SimpleDateFormat formatter = new SimpleDateFormat(props.getProperty("applicationdateformat"));



String referrer = (String) formInput.get("referrer");
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

   lcCStart.setLightData(dtTmpCStart);
   lcCEnd.setLightData(dtTmpCEnd);
    
}

planIndex = (String) formInput.get("planselected");
String sActSvsType = "";
String cBusinessType = (String)lcWtbActSvsType.getLightData();
%>

<!--
0 PIN_FLD_POID                      POID [0] 0.0.0.1 /search 0 0
0 PIN_FLD_FLAGS                      INT [0] 256
0 PIN_FLD_OP_CORRELATION_ID          STR [0] "1:HDL-PCS469800:UnknownProgramName:0:AWT-EventQueue-0:7:1245071952:0"
0 PIN_FLD_TEMPLATE                   STR [0] "select X from /account where F1 like V1 OR F2 like V2  "
0 PIN_FLD_RESULTS                  ARRAY [*] allocated 2, used 2
1     PIN_FLD_ACCOUNT_NO             STR [0] NULL
1     PIN_FLD_NAMEINFO             ARRAY [1] allocated 1, used 1
2         PIN_FLD_FIRST_NAME         STR [0] NULL
0 PIN_FLD_ARGS                     ARRAY [1] allocated 1, used 1
1     PIN_FLD_ACCESS_CODE1           STR [0] "9%"
0 PIN_FLD_ARGS                     ARRAY [2] allocated 1, used 1
1     PIN_FLD_ACCOUNT_NO             STR [0] "%0%"

----
1     PIN_FLD_ACCOUNT_NO             STR [0] NULL
1     PIN_FLD_POID                  POID [0] NULL
1     PIN_FLD_BRAND_OBJ             POID [0] NULL
1     PIN_FLD_NAMEINFO             ARRAY [*] allocated 3, used 3
2         PIN_FLD_LAST_NAME          STR [0] NULL
2         PIN_FLD_FIRST_NAME         STR [0] NULL
2         PIN_FLD_COMPANY            STR [0] NULL
0 PIN_FLD_POID                      POID [0] 0.0.0.1 /search -1 0
0 PIN_FLD_ARGS                     ARRAY [1] allocated 1, used 1
1     PIN_FLD_POID                  POID [0] 0.0.0.1 /service/ip -1 0
0 PIN_FLD_ARGS                     ARRAY [2] allocated 1, used 1
1     PIN_FLD_LOGIN                  STR [0] "%"
0 PIN_FLD_ARGS                     ARRAY [3] allocated 1, used 1
1     PIN_FLD_ACCESS_CODE1           STR [0] "012345%"
0 PIN_FLD_ARGS                     ARRAY [4] allocated 1, used 1
1     PIN_FLD_POID                  POID [0] NULL
0 PIN_FLD_ARGS                     ARRAY [5] allocated 1, used 1
1     PIN_FLD_ACCOUNT_OBJ           POID [0] NULL
0 PIN_FLD_TEMPLATE                   STR [0] "select X from /account 1, /service 2 where 2.F1 = V1 and lower( 2.F2 ) like lower( V2 ) and lower( 1.F3 ) like lower( V3 ) and 1.F4 = 2.F5 "
0 PIN_FLD_FLAGS                      INT [0] 256

-->
<% 
SparseArray results = new SparseArray();
String First_name = "";
String Account_number= (String) lcParent.getLightData();
Poid AcctPoid=null;
String sCNIC=null;
String mCNIC=null;
String sAcctNo=null;
String mAcctNo=null;
String mLogin=null;
String mPayee=null;
String mAcctPoid=null;
if ("POST".equals(request.getMethod()) ) {
    
    sCNIC = (String)request.getParameter("CNIC");
    sAcctNo = (String)request.getParameter("AccountNumber");
    mPayee = (String)request.getParameter("payee");
    
    
    mCNIC = sCNIC + "%";
    mAcctNo =  "%" + sAcctNo + "%";
    mLogin = "%";
    
    FList input = new FList();
    FList output1 = new FList();
    
    try{
        conn = new PCachedContext();
        conn.connect();
        input.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/search"));
        input.set(FldFlags.getInst(), 256);
        StringBuffer template = new StringBuffer(1024);
        template.append("select X from /account 1, /service/ip 2 where 2.F1 = V1 and lower( 2.F2 ) like lower( V2 ) and lower( 1.F3 ) like lower( V3 ) and 1.F5 like V5 and 1.F4 = 2.F6 ");
        input.set(FldTemplate.getInst(), template.toString());
        SparseArray resArray = new SparseArray();
        FList resFList = new FList();
        SparseArray argsArray = new SparseArray();
        FList args1 = new FList();
        args1.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/service/ip"));
        FList args2 = new FList();
        args2.set(FldLogin.getInst(), mLogin);
        FList args3 = new FList();
        args3.set(FldAccessCode1.getInst(), mCNIC);
        FList args4 = new FList();
        args4.set(FldPoid.getInst(),new Poid(0, 0L));
        FList args5=new FList();
        args5.set(FldAccountObj.getInst(), new Poid(0, 0L));
        FList args6 = new FList();
        args6.set(FldAccountNo.getInst(), mAcctNo);
        argsArray.add(1, args1);
        argsArray.add(2, args2);
        argsArray.add(3, args3);
        argsArray.add(4, args4);
        argsArray.add(5, args6);
        argsArray.add(6, args5);
        input.set(FldArgs.getInst(), argsArray);
        input.setElement(FldResults.getInst(),0);
        output1 = conn.opcode(7, input);
        results = output1.get(FldResults.getInst());
        
        if(results == null){
            out.print("You Have given wrong CNIC/Acct Number. Please try again");
        }
        
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        conn.close(true);
    }
}
%>


<%
String windowTitle = WINDOW_TITLE;
String welcomeName = "";
if (session != null) {
    welcomeName = session.getAttribute("firstName") + " " + session.getAttribute("lastName");
} else {
    welcomeName = "Guest";
}
String sectionImage = "text-login.gif"; //Billing: text-billing1.gif ;  MyAccount: text-my-account.gif
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%@ include file="includes/headinc.jsp" %>
    </head>
    <body>
        <div id="outer-container">
            <div id="container">
                <div id="header">
                    <h1 id="logo"><a href="#" title="Wi-Tribe">Wi-Tribe<img src="images/logo-wi-tribe.gif" title="Wi-Tribe" alt="Wi-Tribe" border="0" /></a></h1>
                    <%@ include file="includes/searchform.jsp" %>
                    <div id="login-info">
                        Welcome! <strong><%=welcomeName%></strong>
                    </div>
                    <%@include file="includes/topnav.jsp" %>
                    <%@include file="includes/addbanner.jsp" %>
                </div>
                <div id="wrap">
                    <div id="wrap-inner">
                        <%@ include file="includes/leftmenu_noacct.jsp" %>
                        <div id="content">
                            <h2 style="background:url(images/<%=sectionImage%>) no-repeat;">Login</h2>
                            <div id="inner-content">
                                <form Action="NewAccount_Child_Lead.jsp" name="configChild" Method="post">
                                    <table width="520" border="0" cellspacing="0" cellpadding="0" class="profile-listing">
                                        <tr height=40 valign=middle><td width="35"><label for="CompanyName">CNIC</label></td><td>
                                            <input maxlength="13" type=text name="CNIC" value=""><P class=validationmsg></P>
                                            <td width="35"><label for="CompanyName">AccountNumber</label></td><td>
                                        <input  type=text name="AccountNumber" value=""><P class=validationmsg></P></td></tr>
                             <tr ><td align=right width="35">
                                                <input Type="hidden" Name="referrer" Value="<%=DSTEPCHILDLEAD%>">
                                                <input type="hidden" name="planselected" value="<%=planIndex%>"> 
                                                <input  type=submit value=Search>
                                        </td></tr>
                                        
                                       
                                        <tr height=10><td colspan=2>&nbsp;</td></tr>
                                    </table>
                                </form>
                                <% 
                                if ( "POST".equals(request.getMethod()) ) {
                                %>
                                
                                <form  action="NewAccount_next.jsp" method="post" onsubmit="return newAcctChildLead(this)">
                                    
                                    <table align=center border="0"  cellspacing="0" cellpadding="0" class="profile-listing">
                                        
                                        
                                        <tr><td align=center valign=middle><label>CheckAccount</label></td>
                                            <td align=center valign=middle><label>First Name</label></td>
                                        <td align=center valign=middle><label>Account Number</label></td></tr>
                                        <%  int size = results.getSize();
                                        for (int i=0;i<size;i++) {
                                            
                                            try{
                                                FList orderList = results.elementAt(i);
                                                AcctPoid = orderList.get(FldPoid.getInst());
                                                Account_number=orderList.get(FldAccountNo.getInst());
                                                SparseArray name=orderList.get(FldNameinfo.getInst());
                                                FList orderList1 = name.elementAt(1);
                                                First_name = orderList1.get(FldFirstName.getInst());
                                            } catch(Exception e2){
                                                e2.printStackTrace();
                                            } 
                                        %>
                                        <tr><td align=middle valign=middle> <input type="radio" name="parentpoid" value="<%=AcctPoid%>"/><p class="validationmsg"></p></td> 
                                            <td align=center valign=middle><%=First_name%> </td>
                                        <td align=center valign=middle><%=Account_number%></td></tr>
                                        <% } %>
                                        <script type="text/javascript" charset="utf-8">
                                        var option = -1;
                                        var listAcct = frm.acctSize.value;      
                                        for (var i=listAcct-1; i > -1; i--) {
                                        if (eval('frm.parentacctno'+i+'.checked') == true) {
                                        option = i; i = -1;
                                        }
                                        }
                                        var val = eval('frm.parentacctno'+option+'.value');
                                        if (option == -1) {
                                        alert("You must select a radio button");
                                        return false;
                                        }else{
                                        //alert(val);
                                        }
                                        </script>
                                        
                                        <tr><td align=center valign=middle><label>Parent As</label></td>
                                            <td> <select name="payee" class="secectxx">
                                                    <option value="">Select Parent Type</option>
                                                    <option value="1">Payee</option>
                                                    <option value="2">Non-Payee</option>
                                        </select></td></tr>	
                                        
                                        <INPUT type="hidden" name="parentas" value= "<%=mPayee%>">
                                        <INPUT type="hidden" name="acctSize" value="<%=size%>">
                                       
                                        <INPUT Type="hidden" Name="referrer" Value="<%=DSTEPCHILDLEAD%>">
                                        <INPUT Type="hidden" Name="brand" Value="<%=brand%>">
                                        <INPUT type="hidden" name="Deliver_method" value="1"><!-- 0-Email &&& 1-Postal --> 
                                        <INPUT Type="hidden" Name="<%=CURRENCY%>" Value="<%=props.getProperty(CURRENCYCODE)%>">
                                        <INPUT Type="hidden" Name="<%=AACACCESS%>" Value="<%=props.getProperty(NEWACCOUNTLEAD)%>">
                                        <INPUT type="hidden" name="<%=LEAD_IDENTITY%>" value="<%=""%>">
                                        <INPUT type="hidden" name="<%=NEW_ACCT_BY%>" value="<%=reffrom%>">
                                        <input type=hidden name="planselected" value="<%=planIndex%>">
                                        <INPUT type="hidden" name="salesweb" value="DEALER">
                                        
                                        
                                        
                                        <tr><td align=left  width="20"><input  type=submit value=Proceed></td></tr>
                                    </table> 
                                    </td></tr>
                                </form>
                                <% } %>
                                
                            </div>
                        </div>
                        <!-- Inner content ends here -->                        
                    </div>
                </div>
            </div>
        </div>
        </div>
        <%@ include file="includes/footer.jsp" %>
        </div>
    </body>
</html>
