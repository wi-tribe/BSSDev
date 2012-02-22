<%@ page contentType="text/html;charset=UTF-8" language="java" import="customfields.*, com.portal.bas.*, com.portal.bas.comp.*, com.portal.pcm.*, com.portal.pcm.fields.* , com.portal.app.ccare.comp.*, Wtb.MyAccount.*, com.portal.web.comp.*, com.portal.web.*, java.net.*, java.util.*, java.text.*"%>
<%@ include file="includes/constants.jsp"%>
<%@ include file="includes/ServiceConstants.jsp"%>
<%
String reffrom = request.getParameter("salesweb");
String urlFrom = request.getHeader("referer");
String salesId = request.getParameter("Dealerid");

if (reffrom == null)
    reffrom = "";
if (salesId == null) {
    //out.print("sales")
}
%>

<jsp:useBean id="accountCreationBean" class="com.portal.bas.comp.PIAComponentCollection" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<script type="text/javascript" src="js/jquery.js" language="javascript"></script>
<script language="javascript" type="text/javascript" src="js/script.js"></script>
<script language="javascript" type="text/javascript" src="js/date.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.datePicker.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.bgiframe.js"></script>
<script language="javascript" type="text/javascript" src="js/utilfunctions.js"></script>
<link rel="Shortcut Icon" href="src/images/favicon.ico" type="image/x-icon" />
<style type="text/css">@import "css/style.css";</style>

<%  
PLightComponentHelper lcParent;

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
String sBusType = request.getParameter("busType");
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
lcParent = accountCreationBean.getChild(PARENT);
%>

<%
SparseArray results = new SparseArray();
String First_name = "";
String Account_number= (String) lcParent.getLightData();
Poid AcctPoid=null;
String sCNIC=null;
String sNTN=null;
String mCNIC=null;
String mNTN=null;
String sAcctNo=null;
String mAcctNo=null;
String mLogin=null;
String mPayee=null;
String mAcctPoid=null;
String serLogin=null;
if ("POST".equals(request.getMethod()) ) {
    sCNIC = (String)request.getParameter("CNIC");
    sNTN =  (String)request.getParameter("NTN");
    sAcctNo = (String)request.getParameter("AccountNumber");
    serLogin= (String)request.getParameter("ServiceLogin");
    mPayee = (String)request.getParameter("payee");
    mCNIC = "%" + sCNIC + "%";
    mNTN = "%" + sNTN + "%";
    mAcctNo = "%" + sAcctNo + "%";
    mLogin = "%"+serLogin+"%";
    FList input = new FList();
    FList output1 = new FList();
    
    try{
        conn = new PCachedContext();
        conn.connect();
        input.set(FldPoid.getInst(), new Poid(conn.getCurrentDB(), -1L, "/search"));
        input.set(FldFlags.getInst(), 256);
        StringBuffer template = new StringBuffer(1024);
        String stTemplate1 = "";
		//if(sBusType.equalsIgnoreCase("3") ) {
			stTemplate1 ="select X from /account where lower( 1.F1 ) like lower( V1 ) and lower( 1.F2 ) like lower( V2 ) and F3 in ( 10100 , 10101 ) and lower( 1.F4 ) like lower( V4 ) ";
		//}
		//else {
			//stTemplate1 ="select X from /account where lower( 1.F1 ) like lower( V1 ) and lower( 1.F2 ) like lower( V2 ) and F3 in ( 10100 , 10101 ) and F4 = " + sBusType +" ";
		//}
        template.append(stTemplate1);
        input.set(FldTemplate.getInst(), template.toString());
        SparseArray resArray = new SparseArray();
        FList resFList = new FList();
        SparseArray argsArray = new SparseArray();
        FList args1 = new FList();
        args1.set(FldStatus.getInst(), 1);
        FList args3 = new FList();
        args3.set(FldAccessCode1.getInst(), mCNIC);
        FList args4 = new FList();
        args4.set(FldAccessCode1.getInst(), mNTN);
        FList args6 = new FList();
        args6.set(FldAccountNo.getInst(), mAcctNo);
		FList args2 = new FList();
		args2.set(FldBusinessType.getInst(), 0);
        argsArray.add(1, args3);
        argsArray.add(2, args6);
        argsArray.add(3, args1);
	argsArray.add(4, args4);
        input.set(FldArgs.getInst(), argsArray);
        input.setElement(FldResults.getInst(),0);
        //out.print("<pre>"+input.toString()+"</pre>");
        output1 = conn.opcode(7, input);
       // out.print("<pre>" + output1.toString() + "</pre>");
        results = output1.get(FldResults.getInst());
        
        if(results == null){
            out.print("You Have given wrong CNIC/Acct/NTN Number. Please try again");
        }
        
    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        conn.close(true);
    }
}
%>
<script language="javascript" type="text/javascript" src="js/validations.js"></script>
<html xmlns="http://www.w3.org/1999/xhtml">
    <body>
        <div id="content1">
            <h2  style="background:url() no-repeat;">Search Account</h2>
            <div id="inner-content">
			<script language=javascript>
				function validateChildForm(frm) {
					if(trimString(frm.AccountNumber.value) == '' && trimString(frm.CNIC.value) == '' && trimString(frm.NTN.value) == '') {
						alert('Please enter either account number (or) CNIC value to proceed further');
						return(false);
					}
					if(trimString(frm.CNIC.value) != '') {
						if(!isValidCNIC(trimString(frm.CNIC.value),1)) { 
							alert('Please enter valid CNIC format(Should be 13 digit Number)');
							return(false);
						}
					}
                                        if(trimString(frm.NTN.value) != '') {
						if(!isValidCNIC(trimString(frm.NTN.value),2)) { 
							alert('Please enter valid NTN Number)');
							return(false);
						}
					}
					if(trimString(frm.AccountNumber.value) != '') {
						if(!isValidAccountNoFormat(trimString(frm.AccountNumber.value)) ) { 
							alert('Please enter valid Account No format');
							return(false);
						}
					}

				}
			</script>
                <form Action="NewAccount_Child.jsp" name="configChild" Method="post" onsubmit='return validateChildForm(this)' class="info-listing"  >
                    <table width="500" border="0" cellspacing="0" cellpadding="0" >
                        <tr height=40 valign=middle><td width="35"><label for="CompanyName">CNIC : </label></td><td>
                        <input maxlength="13" type=text name="CNIC" value=""><P class=validationmsg></P></td></tr>
                        <tr height=40 valign=middle><td width="35"><label for="CompanyName">NTN : </label></td><td>
                        <input maxlength="13" type=text name="NTN" value=""><P class=validationmsg></P></td></tr>
                        <tr height=40 valign=middle>
                            <td width="40"><label for="CompanyName">AccountNumber: </label></td><td>
                        <input  type=text name="AccountNumber" value=""><P class=validationmsg></P><br>(format: 0.0.0.1-767678)</td></tr>
						<!--<tr height=40 valign=middle>
                            <td width="40"><label for="CompanyName">User Name : </label></td><td>
                        <input  type=text name="ServiceLogin" value=""><P class=validationmsg></P></td></tr>-->
                        <script language="javascript">
                        function newAcctChild(frm){    
                            /*function IsNumber(value) {
                                var numberFilter=/^\d{0,}$/;
                                return(numberFilter.test(value));//number validation,check whether given value numbe ror not
                            }
                            function IsValidAcctNo(value) {
                                    var numberFilter=/^[0-9.-]{1,}$/;//allow valid text
                                    return(numberFilter.test(value));
                            }
                            if(frm.CNIC.value == '' && frm.AccountNumber.value == '') {
                                alert("Please Enter either CNIC or Account Number of Parent Account");
                                return(false);
                            }
                            if(!IsNumber(frm.CNIC.value)){
                                alert("Please Enter Numbers Only");
                                return(false);
                            }
                            if(!IsValidAcctNo(frm.AccountNumber.value)){
                                alert("Please Enter in Account Number Format Only(ex. 0.0.0.1-12345)");
                                return(false);
                            }*/
                            return true;
                        }
                        </script>
                        <tr ><td align=right width="35">
                                <input  type="submit" value="Search" >
                        </td><td><input  type ="button"  value="Close" onclick="selfClose()"></td></tr>
                        
                        
                        <tr height=10><td colspan=2>&nbsp;</td></tr>
                    </table>
                </form>
                <% 
                if ( "POST".equals(request.getMethod()) ) {
                %>
                
                <form  action="NewAccount_Child.jsp?busType=<%=sBusType%>" name=frmResult method="post">
                    <table align=left border="0"  cellspacing="0" cellpadding="0" class="profile-listing">
                        <% if(results.getSize() == 0){%>
                        <tr><td> No Accounts Found </td></tr>
                        
                        <%}else{%>
                        <tr><td width=50 align=center valign=middle><label>CheckAccount</label></td>
                            <td align=left width=100 valign=middle><label>Account&nbsp;Name</label></td>
                            <td align=left width=50 valign=middle><label>Business&nbsp;Type</label></td>
                        <td align=left width=100 valign=middle><label>Account&nbsp;Number</label></td></tr>
                        <%  int size = results.getSize();
                            int flag = 0;
                            String strSerialNum = "";
                            String Business_type_no = "";
                            String Business_type = "";
                            for (int i=0;i<size;i++) {

                                try{
                                    FList orderList = results.elementAt(i);
                                    AcctPoid = orderList.get(FldPoid.getInst());
                                    strSerialNum = "";
                                    strSerialNum = orderList.get(FldAacSerialNum.getInst());
                                    Business_type_no = orderList.get(FldBusinessType.getInst()).toString();
                                    if(Integer.parseInt(Business_type_no) == 1){
                                       Business_type = "Consumer Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 2){
                                        Business_type = "Business Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 3){
                                        Business_type = "AffinityChild Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 4){
                                        Business_type = "Affinity Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 5){
                                        Business_type = "FUT Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 6){
                                        Business_type = "CSR Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 7){
                                        Business_type = "SOHO Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 8){
                                        Business_type = "Employee Account" ;
                                    } else if(Integer.parseInt(Business_type_no) == 9){
                                        Business_type = "Test Account" ;
                                    } 
                                    Account_number=orderList.get(FldAccountNo.getInst());
                                    SparseArray name=orderList.get(FldNameinfo.getInst());
                                    FList orderList1 = name.elementAt(1);
                                    First_name = orderList1.get(FldFirstName.getInst()) + " " + orderList1.get(FldLastName.getInst());
                                    
                                } catch(Exception e2){
                                    e2.printStackTrace();
                                }  
                        %>  
                        <tr><td align=middle valign=middle> <input type="radio" name="parentpoid" value="<%=AcctPoid%>" onclick="document.frmResult.finalparentpoid.value=this.value; document.frmResult.finalParentSerialNum.value=document.frmResult.txtacctserialnum<%=i%>.value; document.frmResult.finalAccountName.value='<%=First_name%>'"><p class="validationmsg"></p></td> 
                            <td align= valign=middle><%=First_name%> </td>
                            <td align= valign=middle><%=Business_type%> </td>
                        <td align= valign=middle><%=Account_number%><input type=hidden name=txtacctserialnum<%=i%> Value='<%=strSerialNum%>' ></td></tr>
                    
                        <% } %>  
                        <tr><th  colspan=2 align=center valign=middle><label>Parent(Payee/Non-Payee?) </label></th>
                            <td><select name="payee" id="payee" class="secectxx" onchange="document.frmResult.finalparentpayee.value=this.value;">
                                    <option value="0">Select Parent Type</option>
                                    <option value="1">Payee</option>
                                    <option value="2">Non-Payee</option>
                        </select></td></tr>
                        <INPUT type="hidden" name="finalparentpoid" value="">
                        <INPUT type="hidden" name="finalparentpayee" value="">
                        <input type=hidden name="finalParentSerialNum" value="">
						<input type=hidden name="finalAccountName" value="">
                        <tr><td colspan=2 align=left  width="20"><input  type ="button"  value="Proceed" onclick="return valResult()"></td>
                        <td><input  type ="button"  value="Close" onclick="selfClose()"></td></tr>
                   
                </form>
                <% } %>
                 </table> 
                <%
                }%>
                <script language="javascript">
                                    function valResult(){                                       
                                       //alert(document.frmResult.finalparentpoid.value);
                                       //alert(document.frmResult.finalparentpayee.value);
                                       
                                        if(document.frmResult.finalparentpoid.value == 0){
                                           alert("Please Select Parent Account Number");
                                           return false;
                                       }
                                        if(document.frmResult.finalparentpayee.value == 0){
                                           alert("Please Select Payee Type (Payee/Non-Payee)");
                                           return false;
                                        }
                                       opener.callbyChild(document.frmResult.finalparentpoid.value, document.frmResult.finalparentpayee.value,document.frmResult.finalParentSerialNum.value ,document.frmResult.finalAccountName.value ); //document.createFormStep2.parentpoid.value = document.frmResult.finalparentpoid.value; 
                                       //opener.document.createFormStep2.payee.value = document.frmResult.finalparentpayee.value;
                                       self.close(); 
                                    }
                                    function selfClose() {
                                        opener.callbyChild('',''); 
                                        self.close();
                                    }
                </script>
                
            </div>
        </div>
    </body>
</html>
