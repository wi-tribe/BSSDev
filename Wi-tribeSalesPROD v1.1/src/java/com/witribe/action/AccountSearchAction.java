/*
 * AccountSearchAction.java
 *
 * Created on October 7, 2010, 12:26 PM
 */

package com.witribe.action;


import com.witribe.bo.AccountSearchBO;
import com.witribe.dao.AccountSearchDAO;
import com.witribe.inventory.dao.RaiseRequestDAO;
//import Helper.BRMHelper;
import  com.witribe.account.actionform.AccountSearchform;
import  com.witribe.actionform.LeadEntryForm;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.pcm.EBufException;
import com.portal.pcm.Poid;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import jo.witribe.app.salesportal.BRMPRestrictions;
import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import com.witribe.vo.AccountDetails;
import com.witribe.vo.BillingAddress;
import com.witribe.vo.Address;
import com.witribe.util.BRMHelper;
import com.witribe.vo.AccountSearchJavaBean;
import com.portal.pcm.FList;
import com.portal.pcm.FlistToXML;
/**
 *
 * @author LB85028
 * @version
 */

public class AccountSearchAction extends DispatchAction implements java.io.Serializable {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
   // public static final Logger logger =Logger.getLogger("LSLPLog");// For log4j i
    /**
     * This is the Struts action method called on
     * http://.../actionPath?method=myAction1,
     * where "method" is the value specified in <action> element :
     * ( <action parameter="method" .../> )
     */
    public AccountSearchAction() throws IOException {
        Properties props = new Properties();
       // props.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
    }
    
    public ActionForward searchAccount(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
        //logger.debug("In search Account Action....");
        String target="search";
        return mapping.findForward(target);
    }
    
    /**
     * This is the Struts action method called on
     * http://.../actionPath?method=myAction2,
     * where "method" is the value specified in <action> element :
     * ( <action parameter="method" .../> )
     */
    public ActionForward searchAccountDetails(ActionMapping mapping, ActionForm  form,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
        System.out.println("In search Account Details Action....");
        
        String target="";
        HttpSession session=request.getSession(true);
        String accountNumber = null;
        String nationalId = null;
        String userName = null;
        AccountSearchBO accountSearchBO = null;
        
        try{
             accountNumber=request.getParameter("accountNo");
             nationalId=request.getParameter("CNICid");
             userName=request.getParameter("firstname");
            System.out.println("Account number in Action "+accountNumber);
            System.out.println("National id in Action "+nationalId);
            System.out.println("In search Account Details Action....");
            System.out.println("User Name in Action "+userName);
            AccountSearchform accountSearchJavaBean = new AccountSearchform();
            if(accountNumber!=null){
            accountSearchJavaBean.setAccountNumber(accountNumber);
            }
             if(nationalId!=null){
            accountSearchJavaBean.setNationalId(nationalId);
             }
             if(userName!=null){
            accountSearchJavaBean.setUserName(userName);
             }
           // Poid brandObjPoid=(Poid)session.getAttribute("brandPoid");
             accountSearchBO =new AccountSearchBO();
            List accountList=accountSearchBO.getAccountDetail(session,accountSearchJavaBean);
           // logger.info("list Size in action "+accountList.size());
            if (accountList.size() > 0){
                request.setAttribute("accountList",accountList);
            }
            target="searchAccountDetails";
        }catch(Exception e){
          //  logger.error(e);
            System.out.println("Exception "+e);
           
            e.printStackTrace();
        }
        return mapping.findForward(target);
    }
    
   public ActionForward getParticularAccountDetails(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
        String target="";
        AccountDetails accountDetails=null;
        List accountDetailsList =null;
        List invoiceList =null;
        List paymentList = null;
        List inventoryList = null;
        List phoneNumbers = null;
        List svsList = null;
        Hashtable accountStatus = null;
        HttpSession session=request.getSession(true);
        session.removeAttribute("accountObj");
        String messageVasPurchase="true";
        try{
            Poid servicePoid=(Poid)session.getAttribute("loggedinServicePoid");
           
          
            int accountNumber=Integer.parseInt(request.getParameter("accountNumber"));
            String name=request.getParameter("userName");
            String email=request.getParameter("email");
            String balGrpObj=request.getParameter("balGrpObj");
            request.setAttribute("balGrpObj",balGrpObj);
            System.out.println("user Name in getParticularAccountDetails :::"+name);
            String accountObj=Integer.toString(accountNumber);
            session.setAttribute("accountObj",accountObj);
            accountDetails= new AccountDetails();   
            BRMHelper brmHelper = new BRMHelper();
             pCS=brmHelper.getPPooledConnectionClientServices(session,request);
            AccountSearchDAO accountSearchDAO = new AccountSearchDAO();
            AccountSearchBO accountSearchBO = new AccountSearchBO();
            accountDetailsList = accountSearchBO.getParticularAccountDetails(accountNumber,pCS);
            
             String serviceAddress=""; 
             String billingAddress=""; 
             if(accountDetailsList!= null){
                 
           for(int i=0;i<=accountDetailsList.size()-1;i++){
               Address obj=(Address)accountDetailsList.get(0);            
                
                
                if(!obj.getAddress().equalsIgnoreCase("")) {
                    serviceAddress= serviceAddress + obj.getAddress() +"\n";
                }
                if(!obj.getCity().equalsIgnoreCase("")) {
                    serviceAddress= serviceAddress + obj.getCity() +"\n";
                }
                if(!obj.getState().equalsIgnoreCase("")) {
                    serviceAddress= serviceAddress + obj.getState() +"\n";
                }
                if(!obj.getCountry().equalsIgnoreCase("")) {
                    serviceAddress= serviceAddress + obj.getCountry() +"\n";
                }
                if(!obj.getZipCode().equalsIgnoreCase("")) {
                    serviceAddress= serviceAddress + " - " +obj.getZipCode() ;
                }
                System.out.println("serviceAddress ............"+serviceAddress);
               
            }
            for(int i=1;i<=accountDetailsList.size()-1;i++){
               BillingAddress billAddress=(BillingAddress)accountDetailsList.get(1);            
                
                
                if(!billAddress.getBAddress().equalsIgnoreCase("")) {
                    billingAddress= billingAddress + billAddress.getBAddress() +"\n";
                }
                if(!billAddress.getBCity().equalsIgnoreCase("")) {
                    billingAddress= billingAddress + billAddress.getBCity() +"\n";
                }
                if(!billAddress.getBState().equalsIgnoreCase("")) {
                    billingAddress= billingAddress + billAddress.getBState() +"\n";
                }
                if(!billAddress.getBCountry().equalsIgnoreCase("")) {
                    billingAddress= billingAddress + billAddress.getBCountry() +"\n";
                }
                if(!billAddress.getBZipCode().equalsIgnoreCase("")) {
                    billingAddress= billingAddress + " - " +billAddress.getBZipCode() ;
                }
                System.out.println("billingAddress ............"+billingAddress);
               
            }
        }
            
            accountDetails.setBillingAddress(billingAddress);
            accountDetails.setServiceAddress(serviceAddress);
            
            phoneNumbers= accountSearchBO.getPhoneNumbers(accountNumber);
            accountDetails.setPhoneNumber(phoneNumbers);
            String homeNo=accountDetails.getPhoneNumber(1);
            accountDetails.setHomeNo(homeNo);
            String officeNo=accountDetails.getPhoneNumber(2);
            accountDetails.setOfficeNo(officeNo);
            String faxNo=accountDetails.getPhoneNumber(3);
            accountDetails.setFaxNo(faxNo);
            String mobNo=accountDetails.getPhoneNumber(4);
            accountDetails.setMobileNo(mobNo);
            
             accountStatus=accountSearchBO.getAccountStatus(accountNumber);
            System.out.println("accountStatus"+accountStatus.size());
            
            request.setAttribute("balGrpObj",(String)accountStatus.get("ActBalGrp"));
            accountDetails.setUserName((String)accountStatus.get("ActName"));
            accountDetails.setEmailAddress((String)accountStatus.get("ActEmail"));
            accountDetails.setStatus((String)accountStatus.get("ActStatus"));
            accountDetails.setAccountNo((String)accountStatus.get("ActNo"));
           // accountDetails.setServiceType((String)accountStatus.get("ActSvsType"));
            
          /*   invoiceList=accountSearchBO.getInvoiceHistory(request,accountNumber,pCS);
             System.out.println("invoiceList"+invoiceList.size());
             accountDetails.setInvoiceDetails(invoiceList);*/
             paymentList=accountSearchBO.getPayHistory(session,accountNumber,pCS);
            
             accountDetails.setPayment(paymentList); 
             
            svsList=accountSearchDAO.getWimaxlogin(accountNumber);
            System.out.println("svsList"+svsList.size());
            accountDetails.setServiceDetails(svsList);
            request.setAttribute("accountDetails",accountDetails);
           
           String currency=accountSearchBO.getCurrency(accountNumber);
            System.out.println("currency"+currency);
            request.setAttribute("currency",currency);
            
        }catch(Exception e){
           // logger.error(e);
            e.printStackTrace();
        }
        
         System.out.println("before getParticularDetails");
        target="getParticularDetails";
        return mapping.findForward(target);
    }
   
   public ActionForward getInvoiceDetails(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException { 

        String target="";
        List invoiceList =null;
        List paymentList = null;
        String acctNo = null;
        String accntArray[];
        HttpSession session=request.getSession(true);
        try {
            
            System.out.println("accountNumber"+request.getParameter("accountNumber"));
            String accountNo=request.getParameter("accountNumber");
            if(accountNo!= null){
               accntArray =  accountNo.split("-");
               System.out.println("accntArray[0]"+accntArray[0]);  
               System.out.println("accntArray[1]"+accntArray[1]);
                
               acctNo = accntArray[1];
            }
            int accountNumber=Integer.parseInt(acctNo);
            
            AccountDetails accountDetails= new AccountDetails();   
            BRMHelper brmHelper = new BRMHelper();
           
            AccountSearchBO accountSearchBO = new AccountSearchBO();
            
            pCS=brmHelper.getPPooledConnectionClientServices(session,request);

            invoiceList = accountSearchBO.getInvoiceHistory(request,accountNumber,pCS);
            System.out.println("invoiceList"+invoiceList.size());
            accountDetails.setInvoiceDetails(invoiceList);
            paymentList = accountSearchBO.getPayHistory(session,accountNumber,pCS);

            accountDetails.setPayment(paymentList);

            String currency = accountSearchBO.getCurrency(accountNumber);
            System.out.println("currency"+currency);
            request.setAttribute("currency",currency); 
            request.setAttribute("accountDetails",accountDetails);
            
           
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (EBufException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
        System.out.println("before getParticularInvoiceDetails");
        target="getParticularInvoiceDetails";
        return mapping.findForward(target);
       
       
       
       
   }
   
   
   
    public ActionForward getTotalInvoiceDetails(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException { 

        String target="";
        List invoiceList =null;
        List paymentList = null;
        String acctNo = null;
        String accntArray[];
        HttpSession session=request.getSession(true);
        try {
            
            System.out.println("accountNumber"+request.getParameter("accountNumber"));
            String accountNo=request.getParameter("accountNumber");
            if(accountNo!= null){
               accntArray =  accountNo.split("-");
               System.out.println("accntArray[0]"+accntArray[0]);  
               System.out.println("accntArray[1]"+accntArray[1]);
                
               acctNo = accntArray[1];
            }
            int accountNumber=Integer.parseInt(acctNo);
            
            AccountDetails accountDetails= new AccountDetails();   
            BRMHelper brmHelper = new BRMHelper();
           
            AccountSearchBO accountSearchBO = new AccountSearchBO();
            
            pCS=brmHelper.getPPooledConnectionClientServices(session,request);

            invoiceList = accountSearchBO.getInvoiceHistory(request,accountNumber,pCS);
            System.out.println("invoiceList"+invoiceList.size());
            accountDetails.setInvoiceDetails(invoiceList);
            paymentList = accountSearchBO.getPayHistory(session,accountNumber,pCS);

            accountDetails.setPayment(paymentList);
            
            String currency = accountSearchBO.getCurrency(accountNumber);
            System.out.println("currency"+currency);
            System.out.println("totalDue"+request.getAttribute("totalDue"));
            
            String totaldue =(String)request.getAttribute("totalDue");
            double due=Double.parseDouble(totaldue);

            NumberFormat formatter = new DecimalFormat("#0.000");
            System.out.println("The Decimal Value is:"+formatter.format(due));
            request.setAttribute("due",formatter.format(due));
            request.setAttribute("accountNumber",accountNo);
            request.setAttribute("currency",currency);
            
            request.setAttribute("flag",1);
           
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (EBufException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
        System.out.println("paymentPage");
        target="paymentPage";
        return mapping.findForward(target);
       
       
       
       
   }
    
    
    public ActionForward getInvoice(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
        
        BRMHelper brmHelper= new BRMHelper();
        HttpSession session=request.getSession(true);
        try{
            pCS=brmHelper.getPPooledConnectionClientServices(session,request);
            AccountSearchBO accountSearchBO=new AccountSearchBO();
            long actpoid = Long.parseLong((String)request.getParameter("accountp"));
            long billpoid = Long.parseLong((String)request.getParameter("billp"));
            System.out.println("actpoid..."+actpoid+" billpoid... "+billpoid);
            Object ob = accountSearchBO.getInvoice(billpoid,actpoid,pCS);
            System.out.println("Invoice Template.."+ob.toString());
            session.setAttribute("InvoiceHTML", ob);
        } catch(Exception e){
            
            System.out.println("Exception.."+e);
            e.printStackTrace();
            
        }
        return mapping.findForward("success");
    }
    
     public ActionForward getPayReceipt(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
        
        BRMHelper brmHelper= new BRMHelper();
        HttpSession session=request.getSession(true);
        String paymentId = null;
      // List paymentReceiptList = null;
        FList paymentReceiptList = null;
        FlistToXML fx=FlistToXML.getInstance();
        try{
            pCS=brmHelper.getPPooledConnectionClientServices(session,request);
            AccountSearchBO accountSearchBO=new AccountSearchBO();
            paymentId = (String)request.getParameter("paymentId");
            paymentReceiptList = accountSearchBO.getPaymentDetails(paymentId,pCS);
            fx.convert(paymentReceiptList);
            fx.getXMLDocAsString();
            System.out.println("paymentReceiptList.."+fx.getXMLDocAsString());
            request.setAttribute("paymentReceiptList", fx.getXMLDocAsString());
        } catch(Exception e){
            
            System.out.println("Exception.."+e);
            e.printStackTrace();
            
        }
        return mapping.findForward("success");
    }
    
    
   public ActionForward paymentPage(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
       
        System.out.println("into paymentPage :::");
        String target="";
        HttpSession session=request.getSession(true);
        String messageCash = "true";
        String messageCheque = "true";
        String messageCredit = "true";
        String accntArray[];
        String acctNo =null;
        System.out.println("into paymentPage111111 :::");
        try{
            
            String billNo=request.getParameter("billNo");
            System.out.println("billNo in action"+billNo);
            request.setAttribute("billNo",billNo);
             System.out.println("accountNumber"+request.getParameter("accountNumber"));
            String accountNo=request.getParameter("accountNumber");
           /* if(accountNo!= null){
               accntArray =  accountNo.split("-");
               System.out.println("accntArray[0]"+accntArray[0]);  
               System.out.println("accntArray[1]"+accntArray[1]);
                
               acctNo = accntArray[1];
            } */
            int accountNumber=Integer.parseInt(accountNo);
            
          
            System.out.println("accountNumber in action "+accountNumber);
            System.out.println("accountNumber flag "+request.getParameter("flag"));
            request.setAttribute("accountNumber",accountNumber);
            double due=Double.parseDouble(request.getParameter("due"));
//          request.setAttribute("due",due);
            NumberFormat formatter = new DecimalFormat("#0.000");
            System.out.println("The Decimal Value is:"+formatter.format(due));
            request.setAttribute("due",formatter.format(due));
            String flag=request.getParameter("flag");
            System.out.println("flag "+flag);
            request.setAttribute("flag",flag);
            request.setAttribute("accountNumber",accountNo);
           
            AccountSearchBO accountSearchBO=new AccountSearchBO();
            String currency=accountSearchBO.getCurrency(accountNumber);
            System.out.println("currency in action :::::"+currency);
            request.setAttribute("currency",currency);
            target="paymentPage";
            
             System.out.println("target paymentPage :::"+target);
        }catch(Exception e){
            System.out.println("Exception"+e);
        }
        return mapping.findForward(target);
    }
    
  public ActionForward makePayment(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
        String target="";
        HttpSession session=request.getSession(true);
        String result="";
        System.out.println("into makePayment"+request.getParameter("accountNumber"));
        String accntArray[] = null;
        String acctNo = null;
        try{
            BRMHelper brmHelper= new BRMHelper();
            pCS=brmHelper.getPPooledConnectionClientServices(session,request);
            AccountSearchJavaBean accountSearchJavaBean=new AccountSearchJavaBean();
            //Poid brandPoid=(Poid)session.getAttribute("brandPoid");
            String paymentType=request.getParameter("paymentType");
            String paidAmount=request.getParameter("paidAmount");
            String currency=request.getParameter("currency");
            String billNo=request.getParameter("billNo");
             System.out.println("into makePayment1111111");
            String accountNo =(String)request.getParameter("accountNumber");
             
          /*  if(accountNo!= null){
               accntArray =  accountNo.split("-");
               System.out.println("accntArray[0]"+accntArray[0]);  
               System.out.println("accntArray[1]"+accntArray[1]);
                
               acctNo = accntArray[1];
            } */
         //   int accountNumber=Integer.parseInt(acctNo);
           //  String accountNumber=(String)session.getAttribute("accountNo");
           // int accountObj=Integer.parseInt(request.getParameter("accountNumber"));
           int accountObj=Integer.parseInt(accountNo);
            
            // String sCSRLogin = request.getParameter("csrlogin");
           // Poid locationObj = (Poid)session.getAttribute("locationObj");
          //  long locationId = locationObj.getId();
            //InventoryDAO inventoryDAO=new InventoryDAO();
           // List arrList = inventoryDAO.getLocationName(locationId,brandPoid.getId());
           // String locationName = "";
          /*  if(arrList.size()>0){
                locationName = (String)arrList.get(0);
            } */
            if(paymentType.equals("10011")){
                if(request.getParameter("receiptNo")!=null){
                    String receiptNo=request.getParameter("receiptNo");
                    accountSearchJavaBean.setReceiptNo(receiptNo);
                }
                String receiptDate =(String)request.getParameter("receiptDate");
                accountSearchJavaBean.setReceiptDate(receiptDate);
            }
            if(paymentType.equals("10012")){
                String chequeDate=request.getParameter("chequeDate");
                String chequeNo=request.getParameter("chequeNo");                
                String bankName=request.getParameter("bankName");
                String branchName=request.getParameter("branchName");
                String payersName=request.getParameter("payersName");
                accountSearchJavaBean.setChequeDate(chequeDate);
                accountSearchJavaBean.setChequeNo(chequeNo);                
                accountSearchJavaBean.setBankName(bankName);
                accountSearchJavaBean.setBranchName(branchName);
                accountSearchJavaBean.setPayersName(payersName);
            }
            if(paymentType.equals("10101")){
                accountSearchJavaBean.setCreditCardNo(request.getParameter("creditCardNo"));
                accountSearchJavaBean.setCardType(request.getParameter("cardType"));
                accountSearchJavaBean.setExpiryDate(request.getParameter("expiryDate"));
                accountSearchJavaBean.setTransactionNo(request.getParameter("transactionNo"));
                accountSearchJavaBean.setTransactionDate(request.getParameter("transactionDate"));
            }
            System.out.println("Payment Type :"+paymentType);
            System.out.println("Paid Amount :"+paidAmount);
            System.out.println("currency :"+currency);
            System.out.println("BillNo :"+billNo);
            System.out.println("acctNo :"+acctNo);
            accountSearchJavaBean.setPaymentType(paymentType);
            accountSearchJavaBean.setPaidAmount(paidAmount);
            accountSearchJavaBean.setCurrency(currency);
            accountSearchJavaBean.setBillNo(billNo);
            accountSearchJavaBean.setAccountNumber(accountNo);
           // accountSearchJavaBean.setCsrMakingPayment(sCSRLogin);
          //  accountSearchJavaBean.setProgramName(locationName);
            AccountSearchBO accountSearchBO=new AccountSearchBO();
            result=accountSearchBO.createSinglePayment(session,accountObj,accountSearchJavaBean,pCS);
            request.setAttribute("paymentNo",result);
        }catch(Exception e){
            System.out.println(e);
        }
        if(result.equals("")){
            String message="Payment not Done";
            request.setAttribute("message",message);
            target="failure";
        }else{
            String message="made Payment";
            request.setAttribute("message",message);
            target="makePayment";
        }
        return mapping.findForward(target);
    }
    
    public ActionForward makeTotalPayment(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, EBufException {
        String target="";
        HttpSession session=request.getSession(true);
        String result="";
        String accntArray[];
        String acctNo=null;
        try{
            System.out.println("makeTotalPayment");
            System.out.println("curr"+request.getParameter("curr"));
            BRMHelper brmHelper= new BRMHelper();
            pCS=brmHelper.getPPooledConnectionClientServices(session,request);
        //    Poid brandPoid=(Poid)session.getAttribute("brandPoid");
            AccountSearchJavaBean accountSearchJavaBean=new AccountSearchJavaBean();
            String paymentType=request.getParameter("paymentType");
            String paidAmount=request.getParameter("paidAmount");
            String currency=request.getParameter("currency");
           // String accountNumber=(String)session.getAttribute("accountNo");
            String sCSRLogin = request.getParameter("csrlogin");
            String accountNo =(String)request.getParameter("accountNumber");
             
            if(accountNo!= null){
               accntArray =  accountNo.split("-");
               System.out.println("accntArray[0]"+accntArray[0]);  
               System.out.println("accntArray[1]"+accntArray[1]);
                
               acctNo = accntArray[1];
            }
            
           //   int accountNumber=Integer.parseInt(acctNo);
           //  String accountNumber=(String)session.getAttribute("accountNo");
           // int accountObj=Integer.parseInt(request.getParameter("accountNumber"));
           int accountObj=Integer.parseInt(acctNo);
            
           // int accountObj=Integer.parseInt(request.getParameter("accountNumber"));
            System.out.println("Payment Type :"+paymentType);
            System.out.println("Paid Amount :"+paidAmount);
            System.out.println("currency :"+currency);
            System.out.println("acctNo :"+acctNo);
           // Poid locationObj = (Poid)session.getAttribute("locationObj");
         //   long locationId = locationObj.getId();
       /*     InventoryDAO inventoryDAO=new InventoryDAO();
            List arrList = inventoryDAO.getLocationName(locationId,brandPoid.getId());
            String locationName = "";
            if(arrList != null){
                locationName = (String)arrList.get(0);
            }*/
            if(paymentType.equals("10011")){
                if(request.getParameter("receiptNo")!=null){
                    String receiptNo=request.getParameter("receiptNo");
                    accountSearchJavaBean.setReceiptNo(receiptNo);
                }
                String receiptDate =(String)request.getParameter("receiptDate");
                accountSearchJavaBean.setReceiptDate(receiptDate);
            }
            if(paymentType.equals("10012")){
                String chequeDate=request.getParameter("chequeDate");
                String chequeNo=request.getParameter("chequeNo");                
                String bankName=request.getParameter("bankName");
                String branchName=request.getParameter("branchName");
                String payersName=request.getParameter("payersName");
                accountSearchJavaBean.setChequeDate(chequeDate);
                accountSearchJavaBean.setChequeNo(chequeNo);                
                accountSearchJavaBean.setBankName(bankName);
                accountSearchJavaBean.setBranchName(branchName);
                accountSearchJavaBean.setPayersName(payersName);
            }
            if(paymentType.equals("10101")){
                accountSearchJavaBean.setCreditCardNo(request.getParameter("creditCardNo"));
                accountSearchJavaBean.setCardType(request.getParameter("cardType"));
                accountSearchJavaBean.setExpiryDate(request.getParameter("expiryDate"));
                accountSearchJavaBean.setTransactionNo(request.getParameter("transactionNo"));
                accountSearchJavaBean.setTransactionDate(request.getParameter("transactionDate"));
            }
            accountSearchJavaBean.setPaymentType(paymentType);
            accountSearchJavaBean.setPaidAmount(paidAmount);
            accountSearchJavaBean.setCurrency(currency);
            accountSearchJavaBean.setAccountNumber(acctNo);
           // accountSearchJavaBean.setProgramName(locationName);
            accountSearchJavaBean.setCsrMakingPayment(sCSRLogin);
            AccountSearchBO accountSearchBO=new AccountSearchBO();
            result=accountSearchBO.createTotalPayment(session,accountObj,accountSearchJavaBean,pCS);
            request.setAttribute("paymentNo",result);
        }catch(Exception e){
            System.out.println("Exception"+e);
        }
        if(result.equals("")){
            String message="Payment not Done";
            request.setAttribute("message",message);
            target="failure";
        }else{
            String message="made Payment";
            request.setAttribute("message",message);
            target="makeTotalPayment";
        }
        return mapping.findForward(target);
    }
    
    PPooledConnectionClientServices pCS;
}
