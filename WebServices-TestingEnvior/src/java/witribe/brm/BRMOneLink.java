/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package witribe.brm;

import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import utils.MyLog4j;
import utils.SessionDetails;
import utils.DBManager;
/**
 *
 * @author PKMubasharH
 */
@WebService(serviceName = "OneLink")
public class BRMOneLink{

        Properties businessProps = null;
        Statement itemList = null;
        ResultSet itemListResult = null;
        MyLog4j callLoger = new MyLog4j(); // create Mylog4j.java 
        Integer active = 10100;
        Integer inactive = 10102;
        Integer closed = 10103;
        String user = "";
        String pass = "";
        String port = "";
        String ip   = "";
      //  BRMOpcodes oc = new BRMOpcodes();
        @Resource
        private WebServiceContext wsContext;
       SessionDetails details = new SessionDetails();
       
       protected  String authoRization(String FunctionName)
       {
         loadProperties();
        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
        user = req.getRemoteUser();
        callLoger.smsLog(user);
        try {
            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
               user = businessProps.getProperty("sms." + req.getRemoteUser() + ".user");
                pass = businessProps.getProperty("sms." + req.getRemoteUser() + ".pass");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Caller Function: "+FunctionName+"()", user+", isn't an authorised User:", e.toString()+"\r\n\t ____________END authoRization() __________\r\n");
            return null;
        }
            
           return user;
       }
       
         private boolean loadProperties() {

        businessProps = new Properties();
        InputStream localInputStream = getClass().getResourceAsStream("/business.properties");
        try {
            businessProps.load(new BufferedInputStream(localInputStream));
        } catch (IOException ex) {
            callLoger.smsLogger("Exception:loadProperties Line:76", ex.toString(), "");
            return false;
        }
        return true;
    }

    /** This is a sample web service operation */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {

//        loadProperties();
//        MessageContext mc = wsContext.getMessageContext();
//        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
//        user = req.getRemoteUser();
//        callLoger.smsLog(user);
        String authorisedUser = authoRization("Hello");
        if(authorisedUser != null )
        {
        try {
             loadProperties();
//            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                user = businessProps.getProperty("sms." + authorisedUser + ".user");
                pass = businessProps.getProperty("sms." + authorisedUser + ".pass");
                ip   = businessProps.getProperty("sms." + authorisedUser + ".ip");
                port = businessProps.getProperty("sms." + authorisedUser + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);
                cons.TEST_LOOPBACK(txt);
                System.out.println("\n** the Final result is: password: " + pass + "\n User " + user);
                callLoger.smsLogger("Function: Hello(), password: " + pass, ", user is: ", user);
          //  }
        } catch (Exception e) {
            callLoger.smsLogger("Function: Hello(), ", ", Exception line 108: ", e.toString());
         //   return "this USER is not Valid: " + req.getRemoteUser();
        }
      }
        else
        {
            return "This user has not authorization ";
        }
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BillInfo")
    public String BillInfo(@WebParam(name = "AccountNumber") String AccountNumber, @WebParam(name = "bankMnemmonic") String bankMnemmonic, @WebParam(name = "reserved") String reserved) {
        //TODO write your implementation code here:
 
//        MessageContext mc = wsContext.getMessageContext();
//        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
//        user = req.getRemoteUser();
//        callLoger.smsLog(user);

        loadProperties();
        String replySMS = businessProps.getProperty("sms.addon.default.text");
        String authorisedUser = authoRization("BillInfo");
        callLoger.smsLogger("bankMnemmonic: " + bankMnemmonic, ", __________ BillInfoByOneLink Start_______________", null);
        callLoger.smsLogger("bankMnemmonic:" + bankMnemmonic, ", Function: BillInfo() , AccountNumber:" + AccountNumber + ",", "");
              
        if ("".equals(AccountNumber) || "?".equals(AccountNumber)) {
            callLoger.smsLogger("AccountNumber = "+AccountNumber, ", Please Enter your account number ", "\r\n\t ____________END BillInfo() __________\r\n");
            return "Please Enter your account number";
        }
        if(authorisedUser != null )
        {
        try {
           //     if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                user = businessProps.getProperty("sms." + authorisedUser + ".user");
                pass = businessProps.getProperty("sms." + authorisedUser + ".pass");
                ip   = businessProps.getProperty("sms." + authorisedUser + ".ip");
                port = businessProps.getProperty("sms." + authorisedUser + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);
            
           // BRMOpcodes oc = new BRMOpcodes();
          //   BRMWebService brmObj = new BRMWebService();
            FList balGrpFlist = cons.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber, bankMnemmonic);
            
            if (balGrpFlist == null) {
                return businessProps.getProperty("sms.topup.acct_mismatch.text");
            }
            Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account " + AccountNumber);
             FList acctPlanFlist = cons.PCM_OP_CUST_POL_GET_SUBSCRIBED_PLANS(acctPoid, bankMnemmonic);
            Poid svcPoid = null;
            SparseArray resultFld = null;
            FList balGrpSvcFlist = null;
            Integer svcStatus = 0;
            /* malik: to check the service mean it is IP or voip service  */
            try {
                resultFld = balGrpFlist.get(FldResults.getInst()); // copy all data of the account due to FldResults
                for (int index = 0; index < resultFld.size(); index++) {
                    balGrpSvcFlist = resultFld.elementAt(index);
                    svcPoid = balGrpSvcFlist.get(FldServiceObj.getInst());
                    if (svcPoid.toString().contains("/service/ip")) {
                        svcStatus = balGrpSvcFlist.get(FldStatus.getInst());
                        break;
                    }
                }
            } catch (EBufException ex) {
                callLoger.smsLogger("EXCEPTION IN PurchaseAddonBySMS: ", ex.toString(), "");
            }
            if (svcStatus.equals(inactive)) {
                svcStatus = 01;
                replySMS = businessProps.getProperty("sms.addon.inactive.text");
                callLoger.smsLogger("FUNCTION:PurchaseAddonBySMS(), ", " ERR_ACCOUNT_STATUS, bankMnemonic: " + bankMnemmonic, replySMS + " Account Status: 0" + svcStatus + "\r\n\t ____________END PurchaseAddonBySMS __________\r\n");

                return replySMS + " 0" + svcStatus;
            } else if (svcStatus.equals(closed)) {
                svcStatus = 02;
                replySMS = businessProps.getProperty("sms.addon.closed.text");
                callLoger.smsLogger("FUNCTION:PurchaseAddonBySMS(), ", " ERR_ACCOUNT_STATUS, bankMnemonic: " + bankMnemmonic, replySMS + " Account Status: 0" + svcStatus + "\r\n\t ____________END PurchaseAddonBySMS __________\r\n");
                return replySMS + " 0" + svcStatus;
            }
            String name = "";
            FList balSummaryFlist = cons.AR_GET_BAL_SUMMARY(acctPoid, bankMnemmonic);
            FList acctInfo = cons.READ_OBJ(acctPoid, bankMnemmonic);
            FList acctNameInfo;
            try {
                
                BigDecimal billDue = balSummaryFlist.get(FldOpenbillDue.getInst());
                BigDecimal unappliedAmount = balSummaryFlist.get(FldUnappliedAmount.getInst());
                float amount = billDue.floatValue() + unappliedAmount.floatValue();
                String msg = businessProps.getProperty("sms.info.bill.text");
                acctNameInfo = acctInfo.get(FldNameinfo.getInst()).elementAt(1); // name  information
                name = acctNameInfo.get(FldFirstName.getInst()) + " " + acctNameInfo.get(FldLastName.getInst());
                msg = msg.replace("_AMOUNT_", String.format("%#.2f", amount));
               Poid planPoid = (acctPlanFlist.getElement(FldPlan.getInst(), 0)).get(FldPlanObj.getInst());
                    FList planFlist = cons.READ_OBJ(planPoid, bankMnemmonic);
                    String strPlanName = planFlist.get(FldDescr.getInst());
                    String pakInfo = businessProps.getProperty("sms.info.pkg.text");
                    pakInfo = pakInfo.replace("_PACAKGE_NAME_", strPlanName);
                svcStatus = 00;
                callLoger.smsLogger("FUNCTION:BillInfoBySMS(),Reply: ", " TransId: " + bankMnemmonic, " Dear Customer: " + name + " " + msg + " And Package: \n" + pakInfo + "\r\n\t ____________END BillInfoBySMS __________\r\n");
                return " Dear Customer: " + name + " " + msg + "status: 0" + svcStatus + " \n And " + pakInfo;

            } catch (EBufException ex) {
                callLoger.smsLogger("EXCEPTION:BillInfoBySMS line:238", ex.toString(), "");
            }

            callLoger.smsLogger("FUNCTION:BillInfoBySMS(), Reply: ", " NULL, TransId: " + bankMnemmonic, "\r\n\t ____________END BillInfoBySMS __________\r\n");
    //    } 
        }catch (Exception e) {
            callLoger.smsLogger("Function: BillInfo(), ", ", Exception is: ", e.toString());
    //        return "this USER is not Valid: " + req.getRemoteUser();
        }
    }
      else
        {
            return "This user has not authorization ";
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BillPayment")
    public String BillPayment(@WebParam(name = "AccountNumber") String AccountNumber, @WebParam(name = "Amount") String Amount, @WebParam(name = "BankMnemonic") String BankMnemonic, @WebParam(name = "Reserved") String Reserved) {
        //TODO write your implementation code here:
        callLoger.smsLogger("TransID: " + BankMnemonic, ", __________ BillPayment Start_______________", null);
        callLoger.smsLogger("TransID: " + BankMnemonic, ", Function: BillPayment(), Amount:" + Amount + ", AccountNumber:" + AccountNumber + ",", null);
        DBManager myBRMDB = new DBManager();
        DateFormat dff = new SimpleDateFormat("dd/MM/yyyy");
        myBRMDB.openDB();
        String saleRepId = null;
        String shopId = null;
        String paymentNo = "";
        try {

            loadProperties();
            BRMOpcodes oc = new BRMOpcodes();
            BigDecimal bd = new BigDecimal(Amount);
            Date paymentDate = new Date();
            FList balGrpFlist = oc.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber, BankMnemonic);
            if (balGrpFlist == null) {
//            AppLogger.close();
                return businessProps.getProperty("sms.topup.acct_mismatch.text");

            }
            Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account " + AccountNumber);
            FList flist = new FList();
            flist.set(FldPoid.getInst(), Poid.valueOf("0.0.0.1 /account " + AccountNumber));
            flist.set(FldProgramName.getInst(), "ATM Payment");
            flist.set(FldDescr.getInst(), "One Link");
            FList flCC = new FList();
            flCC.set(FldCommand.getInst(), 0);
            flCC.set(FldCurrency.getInst(), 586);
            flCC.set(FldAccountObj.getInst(), acctPoid);
            flCC.set(FldAccountNo.getInst(), AccountNumber);
            flCC.set(FldPayType.getInst(), 10011); // change 1011
            flCC.set(FldActgType.getInst(), 2);
            flCC.set(FldCurrency.getInst(), 586);
            flCC.set(FldAmount.getInst(), bd);
            flCC.set(FldPoid.getInst(), acctPoid);
            flCC.set(FldChannelId.getInst(), 1); // change chanel


            FList cashInfoElem = new FList();
            cashInfoElem.set(FldEffectiveT.getInst(), paymentDate);
            cashInfoElem.set(FldReceiptNo.getInst(), "");

            SparseArray cashArray = new SparseArray();
            cashArray.add(cashInfoElem);

            FList inheritedInfoSubstruct = new FList();
            inheritedInfoSubstruct.setElement(FldCashInfo.getInst(), 1, cashInfoElem);

            FList paymentDetails = new FList();
            paymentDetails.set(FldInheritedInfo.getInst(), inheritedInfoSubstruct);
            paymentDetails.set(FldDescr.getInst(), "");
            flCC.set(FldPayment.getInst(), paymentDetails);
            flist.setElement(FldCharges.getInst(), 0, flCC);
            FList outputlist = oc.Bill_Payment(flist);

            if (outputlist.hasField(FldResults.getInst())) {
                SparseArray servicesArray = outputlist.get(FldResults.getInst());
                Enumeration enumVals = servicesArray.getValueEnumerator();
                while (enumVals.hasMoreElements()) {
                    FList successResult = (FList) enumVals.nextElement();
                    Poid accNo = successResult.get(FldAccountObj.getInst());
                    String[] accNo1 = accNo.toString().split(" ");

                    String itemInfo = "SELECT i.account_obj_id0,a.account_no,i.item_no,i.item_total amount,eb.TRANS_ID,concat(first_name,last_name) name,b.city FROM   ITEM_T i, EVENT_T e , EVENT_BILLING_PAYMENT_T eb,account_t a,account_nameinfo_t b where i.poid_id0 = e.item_obj_id0 and eb.obj_id0 = e.poid_id0 and i.account_obj_id0 = a.poid_id0 and a.poid_id0 =i.account_obj_id0 and a.poid_id0 =b.obj_id0 and i.item_no in(select a.item_no from item_t a where a.poid_type='/item/payment' and  a.account_obj_id0= " + accNo1[2] + " and a.created_t>1295503204 and  a.item_no not in " + "(select b.payment_no from receipt_info_t b where  b.account_obj_id0= " + accNo1[2] + ")) ";

                    itemList = null;
                    itemListResult = null;
                    itemList = myBRMDB.getConnection().createStatement();
                    itemListResult = myBRMDB.executeQuery(itemList, itemInfo);
                    while (itemListResult.next()) {
                        FList receptInfo = new FList();
                        int amnt = (int) (itemListResult.getDouble(4) * -1.0D);
                        receptInfo.set(FldPoid.getInst(), Poid.valueOf("0.0.0.1 /receipt_info -1 0"));
                        receptInfo.set(FldAccountObj.getInst(), Poid.valueOf("0.0.0.1 /account " + accNo1[2]));
                        receptInfo.set(FldAacAccess.getInst(), dff.format(paymentDate));
                        receptInfo.set(FldDescr.getInst(), CommonFunctions.title(CommonFunctions.convert(amnt)));
                        receptInfo.set(FldTitle.getInst(), "Cash Payment");
                        receptInfo.set(FldAccountNo.getInst(), itemListResult.getString(2));
                        receptInfo.set(FldItemNo.getInst(), itemListResult.getString(3));
                        receptInfo.set(FldAmount.getInst(), new BigDecimal(itemListResult.getDouble(4) * -1.0D));
                        receptInfo.set(FldTransId.getInst(), itemListResult.getString(5));
                        receptInfo.set(FldName.getInst(), itemListResult.getString(6));
                        receptInfo.set(FldCity.getInst(), itemListResult.getString(7));
                        receptInfo.set(FldItemName.getInst(), "");
                        receptInfo.set(FldReasonCode.getInst(), "");
                        receptInfo.set(FldAccessCode1.getInst(), saleRepId);
                        if (shopId == null) {
                            shopId = "";
                        }
                        receptInfo.set(FldAacSource.getInst(), shopId);
                        FList res = new FList();
                        //ctx.opcode(1, receptInfo);
                        res = oc.Creat_OBJ(receptInfo);
                        paymentNo = itemListResult.getString(3);
                        System.out.println("Exiting---------------");
                    }
                    myBRMDB.closeStatement(itemList);
                    myBRMDB.closeResultSet(itemListResult);
                }
            }
            callLoger.smsLogger("FUNCTION:BalanceInfoBySMS(),Reply: ", "NULL, transId: " + BankMnemonic, "\r\n\t ____________END BalanceInfoBySMS __________\r\n");
        } catch (Exception e) {
            callLoger.smsLogger("EXCEPTION:BalanceInfoBySMS Line:273", e.toString(), "");
        }


        return null;
    }
}
