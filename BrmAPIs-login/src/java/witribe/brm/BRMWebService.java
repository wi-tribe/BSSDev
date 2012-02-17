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
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.ws.ResponseWrapper;

/**
 *
 * @author PKSaadB
 */

@WebService(targetNamespace="http://wi-tribe.net.pk",
	        serviceName="BRMWebService"
//            wsdlLocation="http://wi-tribe.net.pk/BRMWebService?wsdl",
)
public class BRMWebService {
    Properties businessProps = null;
    MyLog4j logger = new MyLog4j();
    void BRMWebService() 
    {
    }

    /** This is a sample web service operation */
    @WebMethod(operationName="hello")
    public String hello(@WebParam(name="name") String name) {
        
        BRMOpcodes oc = null;
        oc = new BRMOpcodes();
        oc.TEST_LOOPBACK("TEST");
        
        loadProperties();
        return "Hello "+name+" !"+businessProps.getProperty("dbutil.port"); 
    }
    
    private boolean loadProperties()
    {
        businessProps = new Properties();
        InputStream localInputStream = getClass().getResourceAsStream("/business.properties");
        try{
            businessProps.load(new BufferedInputStream(localInputStream));
        } catch (IOException ex) {
            logger.smsLogger("Function:loadProperties, ", "Line:63, Exception: ", ex.toString());
            return false;
        }
        return true;
    }
    
    
    @WebMethod(operationName = "VerifyVoucher3rdParty")
    @ResponseWrapper(className="VoucherResponse")
    @WebResult(name="VoucherResponse")
    public VoucherResponse VerifyVoucher3rdParty(
            @WebParam(name = "voucherPin")String voucherPin, 
            @WebParam(name = "value") int value,
            @WebParam(name = "Timestamp") String Timestamp
         ) 
    {
        logger.smsLogger("Timestamp: "+Timestamp," __________VerifyVoucher3rdParty Start_______________",null);
        logger.smsLogger("Timestamp:"+Timestamp,", Function: VerifyVoucher3rdParty() , value:"+value+", PINS: "+voucherPin,"");
        VoucherResponse result = this.VerifyVoucher(voucherPin, Timestamp);
        int faceValue = 0;
        if(result.faceValue!= null)
        {
            faceValue = new Float(result.faceValue).intValue();
            if(faceValue!=value)
            {
                result.ReturnCode = "101";
                result.ReturnString="value.mismatch"; 
            }
        }
        logger.smsLogger("Timestamp: "+Timestamp,", Function:VerifyVoucher3rdParty(), Reply: ",result.toString());
        return result;
    }
    
    @WebMethod(operationName = "RechargeVoucher3rdParty")
    @ResponseWrapper(className="VoucherResponse")
    @WebResult(name="VoucherResponse")
    public VoucherResponse RechargeVoucher3rdParty(
            @WebParam(name = "voucherPin")String voucherPin, 
            @WebParam(name = "value") int value,
            @WebParam(name = "Timestamp") String Timestamp
         ) 
    {
        logger.smsLogger("Timestamp: "+Timestamp," __________RechargeVoucher3rdParty Start_______________",null);
        logger.smsLogger("Timestamp:"+Timestamp,", Function: RechargeVoucher3rdParty() , value:"+value+", PINS: "+voucherPin,"");
        VoucherResponse result = this.VerifyVoucher(voucherPin, Timestamp);
      //  System.out.println("*************  result: "+result.toString());
        int faceValue = 0;
//        if(result == null)
//        {
//          
//        }
        
        if(result.faceValue!= null)
        {
            faceValue = new Float(result.faceValue).intValue();
            if(faceValue!=value)
            {
                result.ReturnCode = "101";
                result.ReturnString="value.mismatch";
            }
        }       
        if(result.ReturnCode.equals("0"))
        {
            
            logger.smsLogger("Timestamp: "+Timestamp,", Function:RechargeVoucher3rdParty(), Line:121 Reply: ",result.toString());
            result = this.RechargeVoucher(voucherPin, Timestamp);
        }
       
        logger.smsLogger("Timestamp: "+Timestamp,", Function:RechargeVoucher3rdParty(), Line:124 Reply: ",result.toString());
        return result;
    }
     
    

    private VoucherResponse RechargeVoucher(String voucherPin, String Timestamp) 
    {
        logger.smsLogger("Timestamp: "+Timestamp," __________ RechargeVoucher Start_______________",null);
        loadProperties();
        String accountNumber = businessProps.getProperty("thirdparty.account_id");
        logger.smsLogger("Timestamp:"+Timestamp,", Function: RechargeVoucher() , Account_id: "+accountNumber+", PINS: "+voucherPin,"");
        VoucherResponse result = new VoucherResponse();
        BRMOpcodes oc = new BRMOpcodes();
        FList balGrpFlist = oc.BAL_GET_ACCT_BAL_GRP_AND_SVC(accountNumber,Timestamp);
        if(balGrpFlist==null)
        {
            result.ReturnCode="1";
            result.ReturnString="AccountNumberNotFound";
            logger.smsLogger("Timestamp: "+Timestamp,", Function:RechargeVoucher(), Line:151 Reply: ",result.toString());
            return result;
        }
        SparseArray resultFld = null;
        FList balGrpSvcFlist = null;
        try {
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

            String voucherID  = oc.searchDeviceID(voucherPin,Timestamp);
            Date resTime = new Date();
            SimpleDateFormat df =  new SimpleDateFormat();
            df.applyPattern("yyyy-MM-dd HH:mm:ss");
            result.timestamp = df.format(resTime);
            result.voucherSerial=voucherID;
            result.voucherPin= voucherPin;
            FList resultList = oc.PYMT_TOPUP(Timestamp, acctPoid, billInfoPoid, balGrpPoid, voucherID, voucherPin, 0);
            result.faceValue=resultList.get(FldAmount.getInst()).toString();
            result.balanceExpiryTime = ""+resultList.get(FldValidTo.getInst()).getTime()/1000;//0 PIN_FLD_VALID_TO                TSTAMP [0] (0) 01/01/1970 00:00:00:000 AM
            result.ReturnCode="0";
            result.ReturnString=null;
            
            logger.smsLogger("Timestamp: "+Timestamp,", Function:RechargeVoucher(),", result.toString());
 
        } catch (EBufException ex) {
            ArrayList error = oc.getVoucherErrorMessage(ex);
            result.ReturnCode=error.get(0).toString();
            result.ReturnString=error.get(1).toString();         
            logger.smsLogger("Function:RechargeVoucher(),", "Line:195, Exception:", ex.toString());
        }
 
        return result;
    }
    
    private VoucherResponse VerifyVoucher(String voucherPin,String Timestamp) {
        logger.smsLogger("Timestamp: "+Timestamp," __________ VerifyVoucher Start_______________",null);
        loadProperties();
        String accountNumber = businessProps.getProperty("thirdparty.account_id");
        logger.smsLogger("Timestamp:"+Timestamp,", Function: VerifyVoucher() , Account_id: "+accountNumber+", PINS: "+voucherPin,"");
        VoucherResponse result = new VoucherResponse();
        BRMOpcodes oc = new BRMOpcodes();
        FList balGrpFlist = oc.BAL_GET_ACCT_BAL_GRP_AND_SVC(accountNumber,Timestamp);
        if(balGrpFlist==null)
        {
            result.ReturnCode="1";
            result.ReturnString="AccountNumberNotFound"; 
            logger.smsLogger("Timestamp: "+Timestamp,", Function:VerifyVoucher(), Line:212 Reply: ",result.toString());
            return result;
        }
       // balGrpFlist.dump();
        SparseArray resultFld = null;
        FList balGrpSvcFlist = null;
        try {
            resultFld = balGrpFlist.get(FldResults.getInst());
            for(int index=0; index < resultFld.size(); index++ )
            {
                balGrpSvcFlist = resultFld.elementAt(index);
                Poid svcPoid = balGrpSvcFlist.get(FldServiceObj.getInst());
                
                //System.out.println("service Poid"+svcPoid.toString());
                if(svcPoid.toString().contains("/service/ip"))
                {
                    break;
                }                
            }            
           // balGrpSvcFlist.dump();
            Poid billInfoPoid = balGrpSvcFlist.get(FldBillinfoObj.getInst());
            Poid balGrpPoid = balGrpSvcFlist.get(FldBalGrpObj.getInst());
            Poid acctPoid = balGrpSvcFlist.get(FldAccountObj.getInst());

            String voucherID  = oc.searchDeviceID(voucherPin,Timestamp);
           // System.out.println("********* search Device: "+voucherID);
            FList resultList = null;
                if (voucherID != "") {
                    resultList = oc.PYMT_TOPUP(Timestamp,acctPoid, billInfoPoid, balGrpPoid, voucherID, voucherPin, 128);
                }
            Date resTime = new Date();
            SimpleDateFormat df =  new SimpleDateFormat();
            df.applyPattern("yyyy-MM-dd HH:mm:ss");
            result.timestamp = df.format(resTime);
            
            if(resultList != null)
            {
                result.voucherSerial=voucherID;
                result.voucherPin= voucherPin;
                // resultList.dump();
                result.faceValue=resultList.get(FldAmount.getInst()).toString();
                result.balanceExpiryTime = ""+resultList.get(FldValidTo.getInst()).getTime()/1000;//0 PIN_FLD_VALID_TO                TSTAMP [0] (0) 01/01/1970 00:00:00:000 AM
                result.ReturnCode="0";
                result.ReturnString=null;
                logger.smsLogger("Timestamp: "+Timestamp,", Function:VerifyVoucher(),", result.toString());
            }else{
                logger.smsLogger("Function:VerifyVoucher ", "NULL Result","");
                ArrayList error = oc.getVoucherErrorMessage((EBufException)oc.ebuf);
                result.ReturnCode=error.get(0).toString();
                result.ReturnString=error.get(1).toString();
            }
        } catch (EBufException ex) {   
           logger.smsLogger("Function:VerifyVoucher(),", "Line:253, Exception:", ex.toString());
        }
//        System.out.println("************* verify voucher result: "+result.toString());
        return result;
    }
    
}
