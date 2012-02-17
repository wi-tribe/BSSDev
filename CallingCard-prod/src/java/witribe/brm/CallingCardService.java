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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.ResponseWrapper;
import utils.MyLog4j;

/**
 *
 * @author PKSaadB
 */
@WebService(serviceName="CallingCardService", targetNamespace="http://wi-tribe.net.pk")
public class CallingCardService {

    Properties businessProps = null;
    MyLog4j callLoger = new MyLog4j();
    /** This is a sample web service operation */
    @WebMethod(operationName="hello")
    public String hello(@WebParam(name="name") String txt) {
        return "Hello "+txt+" !";
    }

    private boolean loadProperties()
    {
        businessProps = new Properties();
        InputStream localInputStream = getClass().getResourceAsStream("/business.properties");
        try{
            businessProps.load(new BufferedInputStream(localInputStream));
        } catch (IOException ex) {
            //Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
            callLoger.log("Function:loadproperties ", ex.toString() );
            return false;
        }
        return true;
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "RechargeCallingCard")
    @ResponseWrapper(className="RechargeCallingCardResponse")
    @WebResult(name="RechargeCallingCardResponse")
    public RechargeCallingCardResponse RechargeCallingCard(@WebParam(name = "voucherPin")
    String voucherPin, @WebParam(name = "Timestamp")
    String Timestamp) {
        String funN = "RechargeCallingCard()";
        try {
            callLoger.smsLogger("\r\n\r\n___________________   \r\n", funN, Timestamp , voucherPin);
        } catch (IOException ex) {
            callLoger.log("Function:RechargeCallingCard ", ex.toString() );
        }
        // ended by malik
        loadProperties();
        String accountNumber = businessProps.getProperty("callingcard.account_id");
        RechargeCallingCardResponse result = new RechargeCallingCardResponse();
        BRMOpcodes oc = null;
        try {
            oc = new BRMOpcodes();
        } catch (EBufException ex) {
            callLoger.log("Function:RechargeCallingCard line:82 ", ex.toString() );
        }
        FList balGrpFlist = oc.BAL_GET_ACCT_BAL_GRP_AND_SVC(accountNumber);
        if(balGrpFlist==null)
        {
            result.ReturnCode="1";
            result.ReturnString="AccountNumberNotFound";
            callLoger.log1("ReturningCode: "+result.ReturnCode.toString(), "ReturningString: "+result.ReturnString.toString());
            return result;
        }
        SparseArray resultFld = null;
        FList balGrpSvcFlist = null;
        try {
            /////////////////TRY START
            resultFld = balGrpFlist.get(FldResults.getInst());
            for(int index=0; index < resultFld.size(); index++ )
            {
                balGrpSvcFlist = resultFld.elementAt(index);
                Poid svcPoid = balGrpSvcFlist.get(FldServiceObj.getInst());
                if(svcPoid.toString().contains("/service/ip"))
                {
                    break;
                }                
            }            
            //balGrpSvcFlist.dump();
            Poid billInfoPoid = balGrpSvcFlist.get(FldBillinfoObj.getInst());
            Poid balGrpPoid = balGrpSvcFlist.get(FldBalGrpObj.getInst());
            Poid acctPoid = balGrpSvcFlist.get(FldAccountObj.getInst());

            String voucherID  = oc.searchDeviceID(voucherPin);
            Date resTime = new Date();
            SimpleDateFormat df =  new SimpleDateFormat();
            df.applyPattern("yyyy-MM-dd HH:mm:ss");
            result.timestamp = df.format(resTime);
            result.voucherSerial=voucherID;
            result.voucherPin= voucherPin;
            FList resultList = oc.PYMT_TOPUP(acctPoid, billInfoPoid, balGrpPoid, voucherID, voucherPin, 0);
            result.faceValue=resultList.get(FldAmount.getInst()).toString();
            result.balanceExpiryTime = ""+resultList.get(FldValidTo.getInst()).getTime()/1000;//0 PIN_FLD_VALID_TO                TSTAMP [0] (0) 01/01/1970 00:00:00:000 AM
            result.ReturnCode="0";
            result.ReturnString=null;
            
            callLoger.log1("RechargcallingCard: ", result.toString());
            
 
        } catch (EBufException ex) {
            Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
            ArrayList error = oc.getVoucherErrorMessage(ex);
            callLoger.log(result.ReturnCode=error.get(0).toString(), result.ReturnString=error.get(1).toString());
        }
 
        return result;
    }
    
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "VerifyCallingCard")
    @ResponseWrapper(className="VerifyCallingCardResponse")
    @WebResult(name="VerifyCallingCardResponse")
    public VerifyCallingCardResponse VerifyCallingCard(@WebParam(name = "voucherPin")
    String voucherPin, @WebParam(name = "Timestamp")
    String Timestamp) {
        String funN = "VerifyCallingCard()";
        try {
            callLoger.smsLogger("\r\n\r\n___________________   \r\n", funN, Timestamp , voucherPin);
        } catch (IOException ex) {
            callLoger.log("Function:VerifyCallingCard ", ex.toString() );
        }
        // ended by malik
        loadProperties();
        String accountNumber = businessProps.getProperty("callingcard.account_id");
        VerifyCallingCardResponse result = new VerifyCallingCardResponse();
        //TODO write your implementation code here:
        
        //TODO write your implementation code here:
        BRMOpcodes oc = null;
        try {
            oc = new BRMOpcodes();
        } catch (EBufException ex) {
            callLoger.log("Function:VerifyCallingCard Line:170 ", ex.toString() );
        }
        FList balGrpFlist = oc.BAL_GET_ACCT_BAL_GRP_AND_SVC(accountNumber);
        if(balGrpFlist==null)
        {
            result.ReturnCode="1";
            result.ReturnString="AccountNumberNotFound";   
            callLoger.log1("ReturningCode: "+result.ReturnCode.toString(), "ReturningString: "+result.ReturnString.toString());
            return result;
        }
       // balGrpFlist.dump();
        SparseArray resultFld = null;
        FList balGrpSvcFlist = null;
        try {
            /////////////////TRY START
            resultFld = balGrpFlist.get(FldResults.getInst());
            for(int index=0; index < resultFld.size(); index++ )
            {
                balGrpSvcFlist = resultFld.elementAt(index);
                Poid svcPoid = balGrpSvcFlist.get(FldServiceObj.getInst());
                
               // System.out.println("service Poid"+svcPoid.toString());
                if(svcPoid.toString().contains("/service/ip"))
                {
                    break;
                }                
            }            
            //balGrpSvcFlist.dump();
            
            Poid billInfoPoid = balGrpSvcFlist.get(FldBillinfoObj.getInst());
          //  System.out.println("********* line numner: 197");
            Poid balGrpPoid = balGrpSvcFlist.get(FldBalGrpObj.getInst());
            Poid acctPoid = balGrpSvcFlist.get(FldAccountObj.getInst());
//System.out.println("********* line numner: 199");
            String voucherID  = oc.searchDeviceID(voucherPin);
            Date resTime = new Date();
            SimpleDateFormat df =  new SimpleDateFormat();
            df.applyPattern("yyyy-MM-dd HH:mm:ss");
  //          System.out.println("********* line numner: 205");
            result.timestamp = df.format(resTime);
           // System.out.println("********* line numner: 207");
            result.voucherSerial=voucherID;
            result.voucherPin= voucherPin;
            FList resultList = oc.PYMT_TOPUP(acctPoid, billInfoPoid, balGrpPoid, voucherID, voucherPin, 128);
            result.faceValue=resultList.get(FldAmount.getInst()).toString();
            result.balanceExpiryTime = ""+resultList.get(FldValidTo.getInst()).getTime()/1000;//0 PIN_FLD_VALID_TO                TSTAMP [0] (0) 01/01/1970 00:00:00:000 AM
            result.ReturnCode="0";
            result.ReturnString=null;
            callLoger.log1("VerifayingCallingCard: ", result.print());
 
        } catch (EBufException ex) {
            //Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
            ArrayList error = oc.getVoucherErrorMessage(ex);
             callLoger.log(result.ReturnCode=error.get(0).toString(), result.ReturnString=error.get(1).toString());
        }
 
        return result;
    }
    

  
}
