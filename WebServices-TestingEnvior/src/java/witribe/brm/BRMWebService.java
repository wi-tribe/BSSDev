/*
 * To change this template, choose Tools | Templates
 * and open the template searchFlist the editor.
 */
package witribe.brm;

import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import com.wtb.flds.WtbFldProfileId;
import com.wtb.flds.WtbFldSvcInfo;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import utils.MyLog4j;
import utils.SendMail;
import utils.SessionDetails;
import utils.RequestNetNanny;

/**
 *
 * @author PKSaadB
 */
@WebService(targetNamespace = "http://wi-tribe.net.pk",
serviceName = "BRMWebService" //            wsdlLocation="http://wi-tribe.net.pk/BRMWebService?wsdl",
)
public class BRMWebService {

    Properties businessProps = null;
    Integer active = 10100;
    Integer inactive = 10102;
    Integer closed = 10103;
    @Resource
    private WebServiceContext wsContext;
    String user = "";
    String pass = "";
    String ip = "";
    String port = "";

    void BRMWebService() {
    }
    /** This is a sample web service operation */
    MyLog4j callLoger = new MyLog4j(); // create Mylog4j.java 
    RequestNetNanny netNanny = new RequestNetNanny();
    SessionDetails details = new SessionDetails();

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String name,
            @WebParam(name = "TransID") String TransId) {
        // added by malik
        callLoger.smsLogger("TransID: " + TransId, " Hello Start______________________", null);
        callLoger.smsLogger("Function:Hello()", "TransectionId:" + TransId, "");
        // ended by malik
        BRMOpcodes oc = new BRMOpcodes();
        oc.TEST_LOOPBACK(TransId);
        loadProperties();
        return "Hello " + name + " !" + businessProps.getProperty("dbutil.port");
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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "VoucherTopupBySMS")
    public String VoucherTopupBySMS(@WebParam(name = "Code") String code,
            @WebParam(name = "AccountNumber") String AccountNumber,
            @WebParam(name = "VoucherPin") String[] VoucherPin,
            @WebParam(name = "TransID") String TransId) {

        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
        user = req.getRemoteUser();
        callLoger.smsLog(user);
        callLoger.smsLogger("TransID: " + TransId, " __________VoucherTopupBySMS Start_______________", null);
        callLoger.smsLogger("TransID:" + TransId, ", Function: VoucherTopupBySMS() , Code:" + code + ", AccountNumber:" + AccountNumber + ", PINS: " + Arrays.toString(VoucherPin), "");
        try {
            loadProperties();
            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                user = businessProps.getProperty("sms." + req.getRemoteUser() + ".user");
                pass = businessProps.getProperty("sms." + req.getRemoteUser() + ".pass");
                ip = businessProps.getProperty("sms." + req.getRemoteUser() + ".ip");
                port = businessProps.getProperty("sms." + req.getRemoteUser() + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);
                FList balGrpFlist = cons.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber, TransId);
                if (balGrpFlist == null) {
                    return businessProps.getProperty("sms.topup.acct_mismatch.text");
                }
                //balGrpFlist.dump();

                Poid svcPoid = null;
                SparseArray resultFld = null;
                FList balGrpSvcFlist = null;
                try {
                    /////////////////TRY START
                    resultFld = balGrpFlist.get(FldResults.getInst());
                    for (int index = 0; index < resultFld.size(); index++) {
                        balGrpSvcFlist = resultFld.elementAt(index);
                        svcPoid = balGrpSvcFlist.get(FldServiceObj.getInst());
                        if (svcPoid.toString().contains("/service/ip")) {
                            break;
                        }
                    }
                    Poid billInfoPoid = balGrpSvcFlist.get(FldBillinfoObj.getInst());
                    Poid balGrpPoid = balGrpSvcFlist.get(FldBalGrpObj.getInst());
                    Poid acctPoid = balGrpSvcFlist.get(FldAccountObj.getInst());
                    boolean isPnGoCust = isPayAsYouGoCustomer(acctPoid, TransId);
                    boolean isPreHrBase = isPrepaidHourlyBaseCustomer(acctPoid, TransId);
                    int totalAmount = 0;
                    for (String vPin : VoucherPin) {
                        String voucherID = cons.searchDeviceID(vPin, TransId);
                        voucherID = this.validateSMSTopUp(voucherID);
                        FList result = null;
                        if (voucherID != "") {
                            result = cons.PYMT_TOPUP(TransId, acctPoid, billInfoPoid, balGrpPoid, voucherID, vPin, 0);
                        }
                        if (result != null) {

                            totalAmount += result.get(FldAmount.getInst()).intValue();
                            if (isPnGoCust) {
                                purchasePayAsYouGoDeal(TransId, acctPoid, svcPoid, result.get(FldAmount.getInst()).intValue());
                            } else if (isPreHrBase) {
                                purchasePrepaidHourlyBaseDeal(TransId, acctPoid, svcPoid, result.get(FldAmount.getInst()).intValue());
                            } else {
                                callLoger.smsLogger("PaynGo customer check: ", "false", "");
                            }
                        } else {
                            callLoger.smsLogger("Function:VoucherTopupBySMS ", "NULL Result", "");
                        }
                    }

                    if (totalAmount == 0) {
                        String msg = businessProps.getProperty("sms.topup.data.failed.text");
                        callLoger.smsLogger("FUNCTION:VoucherTopupBySMS(), REPLY: ", " TransId: " + TransId, msg + "\r\n\t ____________END VoucherTopupBySMS __________\r\n");
                        return msg;
                    }
                    String msg = businessProps.getProperty("sms.topup.data.success.text");
                    msg = msg.replace("_AMOUNT_", "" + totalAmount);
                    callLoger.smsLogger("FUNCTION:VoucherTopupBySMS(), REPLY: ", " TransId: " + TransId, msg + "\r\n\t ____________END VoucherTopupBySMS __________\r\n");

                    return msg;
                    /////////////////TRY END 
                } catch (EBufException ex) {
                    callLoger.smsLogger("EXECPTION IN VoucherTopupBySMS", ex.toString(), "");
                }
                callLoger.smsLogger("FUNCTION: VoucherTopupBySMS(), REPLY: failed, ", "TransId: " + TransId, "\r\n\t ____________END SMS TopUp __________\r\n");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Function: VoucherTopupBySMS(), ", ", Exception user not valid", e.toString());
            return req.getRemoteUser() + "this USER is not Valid: ";
        }
        return "failed";//businessProps.getProperty("sms.topup.data.failed.text");



    }

    @WebMethod(operationName = "BillInfoBySMS")
    public String BillInfoBySMS(@WebParam(name = "Code") String code,
            @WebParam(name = "AccountNumber") String AccountNumber,
            @WebParam(name = "TransID") String TransId) {

        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
        user = req.getRemoteUser();
        callLoger.smsLog(user);
        callLoger.smsLogger("TransID: " + TransId, ", __________ BillInfoBySMS Start_______________", null);
        callLoger.smsLogger("TransID:" + TransId, ", Function: BillInfoBySMS() , Code:" + code + ", AccountNumber:" + AccountNumber + ",", "");

        try {
            loadProperties();
            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                user = businessProps.getProperty("sms." + req.getRemoteUser() + ".user");
                pass = businessProps.getProperty("sms." + req.getRemoteUser() + ".pass");
                ip = businessProps.getProperty("sms." + req.getRemoteUser() + ".ip");
                port = businessProps.getProperty("sms." + req.getRemoteUser() + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);



                //   BRMOpcodes oc = new BRMOpcodes();

                FList balGrpFlist = cons.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber, TransId);
                if (balGrpFlist == null) {
//            AppLogger.close();
                    return businessProps.getProperty("sms.topup.acct_mismatch.text");

                }
                Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account " + AccountNumber);
                FList balSummaryFlist = cons.AR_GET_BAL_SUMMARY(acctPoid, TransId);

                try {
                    BigDecimal billDue = balSummaryFlist.get(FldOpenbillDue.getInst());
                    BigDecimal unappliedAmount = balSummaryFlist.get(FldUnappliedAmount.getInst());
                    float amount = billDue.floatValue() + unappliedAmount.floatValue();
                    String msg = businessProps.getProperty("sms.info.bill.text");
                    msg = msg.replace("_AMOUNT_", String.format("%#.2f", amount));
                    callLoger.smsLogger("FUNCTION:BillInfoBySMS(),Reply: ", " TransId: " + TransId, msg + "\r\n\t ____________END BillInfoBySMS __________\r\n");
                    return msg;

                } catch (EBufException ex) {
                    callLoger.smsLogger("EXCEPTION:BillInfoBySMS line:238", ex.toString(), "");
                }

                callLoger.smsLogger("FUNCTION:BillInfoBySMS(), Reply: ", " NULL, TransId: " + TransId, "\r\n\t ____________END BillInfoBySMS __________\r\n");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Function: BillInfoBySMS(), ", ", Exception user not valid", e.toString());
            return req.getRemoteUser() + "this USER is not Valid: ";
        }
        return null;
    }

    @WebMethod(operationName = "BalanceInfoBySMS")
    public String BalanceInfoBySMS(@WebParam(name = "Code") String code,
            @WebParam(name = "AccountNumber") String AccountNumber,
            @WebParam(name = "TransID") String TransId) {

        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
        user = req.getRemoteUser();
        callLoger.smsLog(user);
        callLoger.smsLogger("TransID: " + TransId, ", __________ BalanceInfoBySMS Start_______________", null);

        callLoger.smsLogger("TransID: " + TransId, ", Function: BalanceInfoBySMS(), Code:" + code + ", AccountNumber:" + AccountNumber + ",", null);

        try {
            loadProperties();
            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                user = businessProps.getProperty("sms." + req.getRemoteUser() + ".user");
                pass = businessProps.getProperty("sms." + req.getRemoteUser() + ".pass");
                ip = businessProps.getProperty("sms." + req.getRemoteUser() + ".ip");
                port = businessProps.getProperty("sms." + req.getRemoteUser() + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);


                // BRMOpcodes oc = new BRMOpcodes();

                FList balGrpFlist = cons.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber, TransId);
                if (balGrpFlist == null) {
//            AppLogger.close();
                    return businessProps.getProperty("sms.topup.acct_mismatch.text");

                }
                Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account " + AccountNumber);
                FList balFlist = cons.BAL_GET_BALANCES(acctPoid, TransId);
                try {
                    SparseArray balArray = balFlist.get(FldBalances.getInst());
                    FList volumeBal = balArray.elementAt(1000106);
                    //volumeBal.dump();
                    BigDecimal mbAvailable = volumeBal.get(FldCurrentBal.getInst());
                    //System.out.println("********** "+mbAvailable);
                    String msg = businessProps.getProperty("sms.info.volume.text");
                    msg = msg.replace("_REMAINING_VOLUME_", String.format("%#.2f", -1 * mbAvailable.floatValue() / 1024));

                    callLoger.smsLogger("FUNCTION:BalanceInfoBySMS(),", " Reply:, TransId: " + TransId, msg + "\r\n\t ____________END BalanceInfoBySMS __________\r\n");

                    return msg;

                } catch (EBufException ex) {
//             AppLogger.log(AppLogger.SEVERE, ex.toString());
                    callLoger.smsLogger("EXCEPTION:BalanceInfoBySMS Line:291", ex.toString(), "");
                }
                callLoger.smsLogger("FUNCTION:BalanceInfoBySMS(),Reply: ", "NULL, transId: " + TransId, "\r\n\t ____________END BalanceInfoBySMS __________\r\n");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Function: BalanceInfoBySMS(), ", ", Exception user not valid", e.toString());
            return req.getRemoteUser() + "this USER is not Valid: ";
        }
        return null;
    }

    @WebMethod(operationName = "PackageInfoBySMS")
    public String PackageInfoBySMS(@WebParam(name = "Code") String code,
            @WebParam(name = "AccountNumber") String AccountNumber,
            @WebParam(name = "TransID") String TransId) {



        MessageContext mc = wsContext.getMessageContext();
        System.out.println("*********** line:156" + AccountNumber + " user: " + mc.toString());
        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
        user = req.getRemoteUser();

        callLoger.smsLog(user);
        callLoger.smsLogger("TransID: " + TransId, ", __________ PackageInfoBySMS Start_______________", null);
        callLoger.smsLogger("TransID:" + TransId, ", Function: PackageInfoBySMS(), Code:" + code + ", AccountNumber:" + AccountNumber + ", ", null);

        try {
            loadProperties();
            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                user = businessProps.getProperty("sms." + req.getRemoteUser() + ".user");
                pass = businessProps.getProperty("sms." + req.getRemoteUser() + ".pass");
                ip = businessProps.getProperty("sms." + req.getRemoteUser() + ".ip");
                port = businessProps.getProperty("sms." + req.getRemoteUser() + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);
                //   System.out.println("*********** line:156"+AccountNumber);
                //  BRMOpcodes oc = new BRMOpcodes();

                FList balGrpFlist = cons.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber, TransId);
                if (balGrpFlist == null) {
//            AppLogger.close();
                    //     System.out.println("***********NOT null: "+AccountNumber);
                    return businessProps.getProperty("sms.topup.acct_mismatch.text");

                }
                Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account " + AccountNumber);
                FList acctPlanFlist = cons.PCM_OP_CUST_POL_GET_SUBSCRIBED_PLANS(acctPoid, TransId);
                //  acctPlanFlist.dump();
                try {
                    // System.out.print("********************** Enter in try ");
                    Poid planPoid = (acctPlanFlist.getElement(FldPlan.getInst(), 0)).get(FldPlanObj.getInst());
                    //System.out.print("********************** before read_obj"+planPoid.toString());
                    FList planFlist = cons.READ_OBJ(planPoid, TransId);
                    // System.out.print("********************** after read_obj");
                    // planFlist.dump();
                    String strPlanName = planFlist.get(FldDescr.getInst());
                    String msg = businessProps.getProperty("sms.info.pkg.text");
                    msg = msg.replace("_PACAKGE_NAME_", strPlanName);
                    callLoger.smsLogger("FUNCTION:PackageInfoBySMS(),", "TransId: " + TransId + ", Reply: ", msg + "\r\n\t ____________END PackageInfoBySMS __________\r\n");
//            AppLogger.log(AppLogger.INFO,"Reply: "+msg);
//            AppLogger.close();
                    return msg;
                } catch (EBufException ex) {
                    callLoger.smsLogger("EXCEPTION:PackageInfoBySMS Line:339 ", ex.toString(), "");
//             AppLogger.log(AppLogger.SEVERE, ex.toString());
                }
                callLoger.smsLogger("FUNCTION:PackageInfoBySMS, ", "TransId: " + TransId + "Reply: NULL", "\r\n\t ____________END PackageInfoBySMS __________\r\n");
//        AppLogger.log(AppLogger.INFO,"Reply: NULL");
//        AppLogger.close();
            }
        } catch (Exception e) {
            callLoger.smsLogger("Function: PackageInfoBySMS(), ", ", Exception user not valid", e.toString());
            return req.getRemoteUser() + "this USER is not Valid: ";
        }
        return null;
    }

    @WebMethod(operationName = "PurchaseAddonBySMS")
    public String PurchaseAddonBySMS(@WebParam(name = "Code") String code,
            @WebParam(name = "AccountNumber") String AccountNumber,
            @WebParam(name = "TransID") String TransId) {

        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
        user = req.getRemoteUser();
        callLoger.smsLog(user);
        callLoger.smsLogger("TransID: " + TransId, ", _________________ PurchaseAddonBySMS Start__________________", null);
        callLoger.smsLogger("TransID:" + TransId, ", Function: PurchaseAddonBySMS(), Code:" + code + ", AccountNumber:" + AccountNumber + ", ", null);
        loadProperties();
        String replySMS = businessProps.getProperty("sms.addon.default.text");
        try {
            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                user = businessProps.getProperty("sms." + req.getRemoteUser() + ".user");
                pass = businessProps.getProperty("sms." + req.getRemoteUser() + ".pass");
                ip = businessProps.getProperty("sms." + req.getRemoteUser() + ".ip");
                port = businessProps.getProperty("sms." + req.getRemoteUser() + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);

                // BRMOpcodes oc = new BRMOpcodes();
        /* malik: to check the  existenc account number's  */
                FList balGrpFlist = cons.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber, TransId);
                if (balGrpFlist == null) {
                    return businessProps.getProperty("sms.topup.acct_mismatch.text");

                }
                Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account " + AccountNumber);
                //System.out.println("************ poid is: "+acctPoid.toString());
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
                //System.out.println("************"+svcStatus);
                code = code.toLowerCase();


                if (svcStatus.equals(inactive)) {
                    replySMS = businessProps.getProperty("sms.addon.inactive.text");
                    callLoger.smsLogger("FUNCTION:PurchaseAddonBySMS(), ", " ERR_ACCOUNT_STATUS, TransId: " + TransId, replySMS + "\r\n\t ____________END PurchaseAddonBySMS __________\r\n");

                    return replySMS;
                } else if (svcStatus.equals(closed)) {
                    replySMS = businessProps.getProperty("sms.addon.closed.text");
                    callLoger.smsLogger("FUNCTION:PurchaseAddonBySMS(), ", " ERR_ACCOUNT_STATUS, TransId: " + TransId, replySMS + "\r\n\t ____________END PurchaseAddonBySMS __________\r\n");
                    return replySMS;
                }

                //  System.out.println("********************************* enter in active ** ");
                if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.2gb.code"))) {
                    Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon.2gb.deal"));
                    FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, true);
                    if (result != null) {
                        replySMS = businessProps.getProperty("sms.addon.2gb.text");
                    }
                } else if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.5gb.code"))) {

                    Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon.5gb.deal"));
                    FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, true);
                    if (result != null) {
                        replySMS = businessProps.getProperty("sms.addon.5gb.text");
                    }


                } else if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.speedboost.code"))) {
                    String strSpeedBoost = getSpeedBoostCode(svcPoid, TransId);
                    if (strSpeedBoost != null) {
                        Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon." + strSpeedBoost + ".deal"));
                        FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, true);
                        if (result != null) {
                            replySMS = businessProps.getProperty("sms.addon.speedboost.text");
                        }

                        ////////////////////////////// for 512 ////////////////////////////////////


                    }
                } else if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.happydays1.code"))) {
                    Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon.happydays1.deal"));
                    FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, true);
                    if (result != null) {
                        replySMS = businessProps.getProperty("sms.addon.happydays1.text");
                    }

                } else if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.happydays3.code"))) {
                    Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon.happydays3.deal"));
                    FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, true);
                    if (result != null) {
                        replySMS = businessProps.getProperty("sms.addon.happydays3.text");
                    }

                } /* new addon PH  */ else if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.ph.code"))) {
                    FList acctInfo = cons.READ_OBJ(acctPoid, TransId);
                    FList svcInfo = cons.READ_OBJ(svcPoid, TransId);
                    String loginID;
                    FList acctNameInfo;
                    String name = "";
                    String city = "";
                    String email = "";

                    try {
                        acctNameInfo = acctInfo.get(FldNameinfo.getInst()).elementAt(1);
                        loginID = svcInfo.get(FldLogin.getInst());
                        name = acctNameInfo.get(FldFirstName.getInst()) + " " + acctNameInfo.get(FldLastName.getInst());
                        city = acctNameInfo.get(FldCity.getInst());
                        email = acctNameInfo.get(FldEmailAddr.getInst());
                        callLoger.smsLogger("TransId: " + TransId + " Fnction:AddonPurchase, Power hour: ", "License No. ", email);
                        Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon.ph.deal"));
                        FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, true);

                        if (result != null) {
                            replySMS = businessProps.getProperty("sms.addon.ph.text");   ////
                            SendMail sendMail = new SendMail();
                            String fileName = "power hours subscription.html";

                            try {
                                InputStream in = getClass().getResourceAsStream(fileName);
                                String emailBody = convertStreamToString(in);
                                emailBody = emailBody.replace("[CUSTOMER_ID]", AccountNumber); //added by malik
                                emailBody = emailBody.replace("[USERNAME]", loginID);
                                emailBody = emailBody.replace("[FirstName LastName]", name); // added by malik
                                sendMail.sendMail(emailBody, email, businessProps.getProperty("sms.addon.ph.mail.from"), businessProps.getProperty("sms.addon.ph.mail.sub"));
                                callLoger.smsLogger("Email sent to :", "", email);
                            } catch (Exception e) {
                                callLoger.smsLogger("Function:AddonPurchase(), Power Hours: ", "Exception: Line:215", e.toString());
                            }/////END MAIL SENT------// 
                        }
                    } catch (EBufException ex) {
                        callLoger.smsLogger("Exception Line:380, PurchaseAddon, ", "", ex.toString());
                    }
                } /* Ended */ /* Parental control  */ else if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.pc.code"))) {
                    //   System.out.println("************   antivirus code ************* ");
                    FList acctInfo = cons.READ_OBJ(acctPoid, TransId);
                    FList svcInfo = cons.READ_OBJ(svcPoid, TransId);
                    // svcInfo.dump();
                    String loginID;
                    FList acctNameInfo;
                    String Fname = "";
                    String Lname = "";
                    String xml = "";
                    String license = "";
                    String name = "";
                    String city = "";
                    String email = "";
                    String mobile = "";
                    //  String loginName = "";

                    try {

                        acctNameInfo = acctInfo.get(FldNameinfo.getInst()).elementAt(1);
                        loginID = svcInfo.get(FldLogin.getInst());
                        // System.err.println("********** "+loginID);
                        //acctNameInfo = acctInfo.get(
                        // acctNameInfo.dump();
                        name = acctNameInfo.get(FldFirstName.getInst()) + " " + acctNameInfo.get(FldLastName.getInst());
                        city = acctNameInfo.get(FldCity.getInst());
                        email = acctNameInfo.get(FldEmailAddr.getInst());
                        mobile = acctNameInfo.get(FldEmailAddr.getInst());//---correct this mobile nummber
                        Fname = acctNameInfo.get(FldFirstName.getInst());
                        Lname = acctNameInfo.get(FldLastName.getInst());
                        xml = netNanny.createActivationXML(email, AccountNumber, Fname, Lname);
                        license = netNanny.sendRequest(xml, AccountNumber);
                        if (license != null) {
                            details.insertNetNannyDetails(AccountNumber, email, license, "1");
                            Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon.pc.deal"));
                            FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, false);
                            if (result != null) {
                                replySMS = businessProps.getProperty("sms.addon.pc.text");

                                /////START  MAIL SEND------------------------
                                SendMail sendMail = new SendMail();
                                String fileName = "EmailParentSub.html";
                                String massege = null;
                                try {
                                    InputStream in = getClass().getResourceAsStream(fileName);
                                    String emailBody = convertStreamToString(in);
                                    emailBody = emailBody.replace("[CUSTOMER_ID]", AccountNumber); //added by malik
                                    emailBody = emailBody.replace("[USERNAME]", loginID);
                                    emailBody = emailBody.replace("[CUSTOMER_NAME]", name); // added by malik
                                    emailBody = emailBody.replace("[LICENSE_KEY]", license); // added by malik
//                            String downloadLink = "http://wi-tribe.pk/antivirus/32bit/isb_rwp/index.php";
//                            String downloadLink64 = "http://wi-tribe.pk/antivirus/64bit/isb_rwp/index.php";
//                            if (city.equalsIgnoreCase("rawalpindi") || city.equalsIgnoreCase("islamabad") || city.equalsIgnoreCase("lahore") || city.equalsIgnoreCase("karachi") || city.equalsIgnoreCase("faisalabad")) {
//                                downloadLink = businessProps.getProperty("sms.addon.av." + city.toLowerCase() + ".link");
//                            }
//
//                            if (city.equalsIgnoreCase("rawalpindi") || city.equalsIgnoreCase("islamabad") || city.equalsIgnoreCase("lahore") || city.equalsIgnoreCase("karachi") || city.equalsIgnoreCase("faisalabad")) {
//                                downloadLink64 = businessProps.getProperty("sms.addon.av." + city.toLowerCase() + ".link64");
//                            }
//
//                            emailBody = emailBody.replace("http://my.wi-tribe.pk/src/login.jsp", downloadLink);
//                            emailBody = emailBody.replace("http://my.wi-tribe.pk/src/down.jsp", downloadLink64);
                                    sendMail.sendMail(emailBody, email, businessProps.getProperty("sms.addon.pc.mail.from"), businessProps.getProperty("sms.addon.pc.mail.sub"));

                                    callLoger.smsLogger("Email sent to :", "", email);

//                            emailBody = "Dear CCL2 <br> " + name + " has subscribed to the Symantec Antivirus service. Customer details are:"
//                                    + "<br>"
//                                    + "<br>1)    Customer ID     : " + AccountNumber
//                                    + "<br>2)	Name            : " + name
//                                    + "<br>3)	City            : " + city
//                                    + "<br>4)	Mobile Number   : " + mobile
//                                    + "<br><br>	<strong style='color: red;'>Please activate the antivirus software.</strong>"
//                                    + "<br><br>Regards,"
//                                    + "<br>wi-tribe Pakistan";
//
//                            sendMail.sendMail(emailBody, businessProps.getProperty("sms.addon.av.mail.cc"), businessProps.getProperty("sms.addon.av.mail.from"), businessProps.getProperty("sms.addon.av.mail.sub"));

                                } catch (Exception e) {
                                    callLoger.smsLogger("Function:AddonPurchase(), parental control: ", "Exception: Line:215", e.toString());
                                }
//                        /////END MAIL SENT------------------------

                            }
                        } else {
                            callLoger.smsLogger("FUNCTION:PurchaseAddonBySMS(), licence not found, ", "Reply: ", replySMS);
                            return replySMS;
                        }


                    } catch (EBufException ex) {
                        //Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
                        callLoger.smsLogger("Exception Line:515, PurchaseAddon, ", "", ex.toString());

                    }

                    callLoger.smsLogger("FUNCTION:PurchaseAddonBySMS() Line:519 ", " Reply: ", replySMS);
                    return replySMS;

                } /* Ended */ else if (code.equalsIgnoreCase(businessProps.getProperty("sms.addon.antivirus.code"))) {
                    //   System.out.println("************   antivirus code ************* ");
                    Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + businessProps.getProperty("sms.addon.antivirus.deal"));
                    FList result = cons.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, false);
                    if (result != null) {
                        replySMS = businessProps.getProperty("sms.addon.antivirus.text");
                        FList acctInfo = cons.READ_OBJ(acctPoid, TransId);
                        //acctInfo.dump();
                        FList acctNameInfo;
                        String name = "";
                        String city = "";
                        String email = "";
                        String mobile = "";
                        try {

                            acctNameInfo = acctInfo.get(FldNameinfo.getInst()).elementAt(1);
                            name = acctNameInfo.get(FldFirstName.getInst()) + " " + acctNameInfo.get(FldLastName.getInst());
                            city = acctNameInfo.get(FldCity.getInst());
                            email = acctNameInfo.get(FldEmailAddr.getInst());
                            mobile = acctNameInfo.get(FldEmailAddr.getInst());//---correct this mobile nummber


                        } catch (EBufException ex) {
                            //Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
                            callLoger.smsLogger("Exception Line:558, PurchaseAddon, ", ex.toString(), "");

                        }



                        /////START  MAIL SEND------------------------
                        SendMail sendMail = new SendMail();
                        String fileName = "subemail.html";
                        String massege = null;
                        try {
                            InputStream in = getClass().getResourceAsStream(fileName);
                            String emailBody = convertStreamToString(in);
                            emailBody = emailBody.replace("RA_CUSTOMER_ID", AccountNumber);
                            //emailBody = emailBody.replace("RA_USERNAME", loginName);
                            emailBody = emailBody.replace("RA_FIRST_LAST_NAME", name);
                            String downloadLink = "http://wi-tribe.pk/antivirus/32bit/isb_rwp/index.php";
                            String downloadLink64 = "http://wi-tribe.pk/antivirus/64bit/isb_rwp/index.php";
                            if (city.equalsIgnoreCase("rawalpindi") || city.equalsIgnoreCase("islamabad") || city.equalsIgnoreCase("lahore") || city.equalsIgnoreCase("karachi") || city.equalsIgnoreCase("faisalabad")) {
                                downloadLink = businessProps.getProperty("sms.addon.av." + city.toLowerCase() + ".link");
                            }

                            if (city.equalsIgnoreCase("rawalpindi") || city.equalsIgnoreCase("islamabad") || city.equalsIgnoreCase("lahore") || city.equalsIgnoreCase("karachi") || city.equalsIgnoreCase("faisalabad")) {
                                downloadLink64 = businessProps.getProperty("sms.addon.av." + city.toLowerCase() + ".link64");
                            }

                            emailBody = emailBody.replace("http://my.wi-tribe.pk/src/login.jsp", downloadLink);
                            emailBody = emailBody.replace("http://my.wi-tribe.pk/src/down.jsp", downloadLink64);
                            sendMail.sendMail(emailBody, email, businessProps.getProperty("sms.addon.av.mail.from"), businessProps.getProperty("sms.addon.av.mail.sub"));

                            callLoger.smsLogger("Email sent to :", email, "");

                            emailBody = "Dear CCL2 <br> " + name + " has subscribed to the Symantec Antivirus service. Customer details are:"
                                    + "<br>"
                                    + "<br>1)    Customer ID     : " + AccountNumber
                                    + "<br>2)	Name            : " + name
                                    + "<br>3)	City            : " + city
                                    + "<br>4)	Mobile Number   : " + mobile
                                    + "<br><br>	<strong style='color: red;'>Please activate the antivirus software.</strong>"
                                    + "<br><br>Regards,"
                                    + "<br>wi-tribe Pakistan";

                            sendMail.sendMail(emailBody, businessProps.getProperty("sms.addon.av.mail.cc"), businessProps.getProperty("sms.addon.av.mail.from"), businessProps.getProperty("sms.addon.av.mail.sub"));

                        } catch (Exception e) {
                            callLoger.smsLogger("Exception Line:601, purcahseAddon, ", e.toString(), "");
                        }
                        /////END MAIL SENT------------------------
                    }

                }
                callLoger.smsLogger("FUNCTION:PurchaseAddonBySMS(),", " TransId:" + TransId + "Reply: ", replySMS + "\r\n\t ____________END PurchaseAddonBySMS __________\r\n");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Function: PurchaseAddonBySMS(), ", ", Exception user not valid", e.toString());
            return req.getRemoteUser() + "this USER is not Valid: ";
        }
        return replySMS;
    }

    @WebMethod(operationName = "AccountInfo")
    public String AccountInfo(@WebParam(name = "cnic") String cnic, @WebParam(name = "transid") String TransId,
            @WebParam(name = "code") String code) {

        MessageContext mc = wsContext.getMessageContext();
        HttpServletRequest req = (HttpServletRequest) mc.get(MessageContext.SERVLET_REQUEST);
        user = req.getRemoteUser();
        callLoger.smsLog(user);
        callLoger.smsLogger("TransID: " + TransId, ", ______________ AccountInfo Start_______________", null);
        callLoger.smsLogger("TransID:" + TransId, ", Function: AccountInfo() , Code:" + code + ", CNIC: " + cnic, null);

      //  int a = 13;
        String replySms = "";
        try {

            loadProperties();
            if (businessProps.getProperty("sms." + req.getRemoteUser() + ".user").equals(req.getRemoteUser())) {
                //     System.out.println("USer: "+user); 
                user = businessProps.getProperty("sms." + req.getRemoteUser() + ".user");
                pass = businessProps.getProperty("sms." + req.getRemoteUser() + ".pass");
                //  System.out.println("USer: "+user+" pass: "+pass);
                ip = businessProps.getProperty("sms." + req.getRemoteUser() + ".ip");
                port = businessProps.getProperty("sms." + req.getRemoteUser() + ".port");
                BRMOpcodes cons = new BRMOpcodes(user, pass, ip, port);
//
//
//                if (cnic.length() != a) {
//                    replySms = businessProps.getProperty("sms.topup.cnic_mismatch.text");
//                    callLoger.smsLogger("CNIC's chracters are not equal to 13 ", replySms + "\r\n\t ____________END AccountInfo __________\r\n", null);
//                    return replySms;
//                }
                //   BRMOpcodes oc = new BRMOpcodes();
                FList balGrpFlist = cons.GET_ACC_INFO(cnic, TransId);

                if (balGrpFlist == null) {
                  //  callLoger.smsLogger("Function:AccountInfo, Flist(NULL): ", balGrpFlist.toString(), "");
                    return businessProps.getProperty("sms.topup.cnic_mismatch.text");

                }

                Poid svcPoid = null;
                SparseArray resultFld = null;
                FList balGrpSvcFlist = null;
                List poids = new ArrayList();
                String account_id = "";
                try {
                    /////////////////TRY START
                    resultFld = balGrpFlist.get(FldResults.getInst());
                    for (int index = 0; index < resultFld.size(); index++) {
                        balGrpSvcFlist = resultFld.elementAt(index);
                        svcPoid = balGrpSvcFlist.get(FldPoid.getInst());
                        poids.add(svcPoid.getId());
                    }
                } catch (EBufException ex) {
                    callLoger.smsLogger("Exception:AccountInfo, Line:690 ", ex.toString(), "");
                }


                for (int i = 0; i < poids.size(); i++) {
                    account_id = poids.get(i).toString();
                    replySms = replySms + account_id + ",Rs." + getAccountInfo(account_id, TransId) + ";";

                }
                callLoger.smsLogger("ReplySMS:AccountInfo, ", replySms + "\r\n\t ____________END AccountInfo __________\r\n", "");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Function: AccountInfo(), ", ", Exception user not valid", e.toString());
            return req.getRemoteUser() + "this USER is not Valid: ";
        }
        return replySms;
    }

    private String getSpeedBoostCode(Poid svcPoid, String TransId) {
        try {
            BRMOpcodes oc = new BRMOpcodes();
            FList searchFlist = new FList();
            searchFlist.set(FldPoid.getInst(), new Poid(1, -1L, "/search"));
            searchFlist.set(FldFlags.getInst(), 256);
            searchFlist.set(FldTemplate.getInst(), "select X from /profile/ipservice_info where F1 = V1 and poid_type = '/profile/ipservice_info' ");
            FList serviceFlist = new FList();
            serviceFlist.set(FldServiceObj.getInst(), svcPoid);
            searchFlist.setElement(FldArgs.getInst(), 1, serviceFlist);
            searchFlist.setElement(FldResults.getInst(), 1, new FList());
            //searchFlist.dump();
            FList outputFlist = oc.SEARCH(searchFlist, TransId);
            // outputFlist.dump();
            try {
                String profile_id = outputFlist.getElement(FldResults.getInst(), 0).getElement(WtbFldSvcInfo.getInst(), 0).get(WtbFldProfileId.getInst());
                if (profile_id.contains("post_b_256_64")) {
                    return "speedboost256";
                } else if (profile_id.contains("post_b_512_128")) {
                    return "speedboost512";
                } else if (profile_id.contains("post_b_1024_256")) {
                    return "speedboost1024";
                }

            } catch (EBufException ex) {
                callLoger.smsLogger("Exception:getSpeedBoostCode Line:653", ex.toString(), "");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Exception:getSpeedBoostCode Line:694", e.toString(), "");
        }

        return null;
    }

    private boolean isPayAsYouGoCustomer(Poid acctPoid, String TransId) {
        boolean paygCustomer = false;
        try {
            BRMOpcodes oc = new BRMOpcodes();
            FList input = new FList();
            input.set(FldPoid.getInst(), acctPoid);
            input.set(FldScopeObj.getInst(), acctPoid);
            input.set(FldStatusFlags.getInst(), 1);
            FList output = oc.SUBSCRIPTION_GET_PURCHASED_OFFERINGS(input, TransId);

            try {
                SparseArray productsArray = output.get(FldProducts.getInst());
                for (int index = 0; index < productsArray.size(); index++) {
                    FList product = productsArray.elementAt(index);
                    if (product.get(FldDescr.getInst()).contains("PayAsYouGo")) {
                        paygCustomer = true;
                        callLoger.smsLogger("is PayAsYouGo customer?: ", "true", "");
                    }
                }
            } catch (EBufException ex) {
                callLoger.smsLogger("is PayAsYouGo customer?: ", "False: ", ex.toString());
            }
        } catch (Exception e) {
            callLoger.smsLogger("Exception:isPayAsYouGoCustomer, ", "Line:719 ", e.toString());
        }
        return paygCustomer;
    }

    private boolean purchasePayAsYouGoDeal(String TransId, Poid acctPoid, Poid svcPoid, int amount) {

        try {
            String strDealPoid = null;
            if (amount == 100) {
                strDealPoid = "1918459037";
            } else if (amount == 250) {
                strDealPoid = "1918456477";
            } else if (amount == 500) {
                strDealPoid = "1918458525";
            }
            callLoger.smsLogger(" purchasing PayAsYouGo Deal ...", "(deal ID:" + strDealPoid + ") AMOUNT=" + amount, "");
            if (strDealPoid == null) {
                return false;
            }
            Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + strDealPoid);
            BRMOpcodes oc = new BRMOpcodes();
            FList outflist = oc.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, false);
            if (outflist != null) {
                callLoger.smsLogger("successfully PayAsYouGo purcahsed: ", dealPoid.toString(), "");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Exception:purchasePayAsYouGoDeal, ", "Line:768 ", e.toString());
        }
        return true;
    }

    private boolean isPrepaidHourlyBaseCustomer(Poid acctPoid, String TransId) {
        boolean preHrCustomer = false;
        try {
            BRMOpcodes oc = new BRMOpcodes();
            FList input = new FList();
            input.set(FldPoid.getInst(), acctPoid);
            input.set(FldScopeObj.getInst(), acctPoid);
            input.set(FldStatusFlags.getInst(), 1);
            FList output = oc.SUBSCRIPTION_GET_PURCHASED_OFFERINGS(input, TransId);
            try {
                SparseArray productsArray = output.get(FldProducts.getInst());
                for (int index = 0; index < productsArray.size(); index++) {
                    FList product = productsArray.elementAt(index);
                    //System.out.println("product----\n"+product.get(FldDescr.getInst()));    
                    if (product.get(FldDescr.getInst()).contains("Prepaid - Base Wimax")) {
                        preHrCustomer = true;
                        callLoger.smsLogger("is prepaid customer?: ", "true", null);
                    }
                }
            } catch (EBufException ex) {
                callLoger.smsLogger("is prepaid customer?: ", "false ", ex.toString());
            }
        } catch (Exception e) {
            callLoger.smsLogger("Exception:isPrepaidHourlyBaseCustomer, ", "Line:768 ", e.toString());
        }
        return preHrCustomer;
    }

    private boolean purchasePrepaidHourlyBaseDeal(String TransId, Poid acctPoid, Poid svcPoid, int amount) {
        // System.out.println("purchasing PAYG deal ... ");
        String strDealPoid = null;
        try {
            if (amount == 100) {

                strDealPoid = "1780715803";
            } else if (amount == 250) {
                strDealPoid = "1780717083";
            } else if (amount == 500) {
                strDealPoid = "1780718107";
            } else if (amount == 1000) {
                strDealPoid = "1780719131";
            }
            callLoger.smsLogger(" purchasing perapid Deal ...", "(deal ID:" + strDealPoid + ") AMOUNT=" + amount, "");
            if (strDealPoid == null) {
                return false;
            }
            Poid dealPoid = Poid.valueOf("0.0.0.1 /deal " + strDealPoid);
            BRMOpcodes oc = new BRMOpcodes();
            FList outflist = oc.SUBSCRIPTION_PurchaseDeal(TransId, acctPoid, svcPoid, dealPoid, false);
            if (outflist != null) {
                callLoger.smsLogger("successfully prepaid purcahsed: ", dealPoid.toString(), "");
            }
        } catch (Exception e) {
            callLoger.smsLogger("Exception:purchasePrepaidHourlyBaseDeal, ", "Line:797 ", e.toString());
        }
        return true;
    }

    private String validateSMSTopUp(String voucherID) {
        try {
            if (voucherID.startsWith("11WT050") || voucherID.startsWith("10FR100") || voucherID.startsWith("11FR100") || voucherID.startsWith("10FA100") || voucherID.startsWith("11FA100") || voucherID.startsWith("11EP0200")) {
                System.out.println("voucher(" + voucherID + ") can't be toped up by SMS");
                voucherID = "";
            }
        } catch (Exception e) {
            callLoger.smsLogger("Exception:validateSMSTopUp, ", "Line:805 ", e.toString());
        }
        return voucherID;
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        return sb.toString();
    }

    private float getAccountInfo(String AccountNumber, String TransId) {
        float amount = 0;
        try {
            BRMOpcodes oc = new BRMOpcodes();
            Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account " + AccountNumber);
            FList balSummaryFlist = oc.AR_GET_BAL_SUMMARY(acctPoid, TransId);
            try {
                BigDecimal billDue = balSummaryFlist.get(FldOpenbillDue.getInst());
                BigDecimal unappliedAmount = balSummaryFlist.get(FldUnappliedAmount.getInst());
                amount = billDue.floatValue() + unappliedAmount.floatValue();
            } catch (EBufException ex) {
//             AppLogger.log(AppLogger.SEVERE, ex.toString());
                callLoger.smsLogger("EXCEPTION:BallInfo(), Line:880 ", "EBuf Exception", ex.toString());
            }
        } catch (Exception e) {
            callLoger.smsLogger("EXCEPTION:BallInfo(), ", " Line:834", e.toString());
        }
        return amount;
    }
}
