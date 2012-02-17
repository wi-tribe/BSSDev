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
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    void BRMWebService() 
    {
    }

    /** This is a sample web service operation */
    @WebMethod(operationName="hello")
    public String hello(@WebParam(name="name") String name) {
        
        BRMOpcodes oc = null;
        try {
            oc = new BRMOpcodes();
        } catch (EBufException ex) {
            Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        oc.TEST_LOOPBACK();
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
            Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    /**
     * Web service operation
     */
    @WebMethod(operationName = "UpdatePhoneNumber")
    public Boolean UpdatePhoneNumber(@WebParam(name = "AccountNumber")
    String AccountNumber, @WebParam(name = "PhoneType")
    int PhoneType, @WebParam(name = "NewPhone")
    String NewPhone) {
        //TODO write your implementation code here: 
        /* PhoneType:
         *          MOBILE  = 1     BRM = 5
         *          HOME    = 2     BRM = 1
         *          WORK    = 3     BRM = 2
         */
        if(PhoneType==1){
            PhoneType=5;
        }else if(PhoneType==2){
            PhoneType=1;
        }else if(PhoneType==3){
            PhoneType=2;
        }
        
        try 
        {
            Connection mConn = DBUtil.getConnection();
            if (mConn != null && !mConn.isClosed()) 
            {
                String sql = "update account_phones_t set phone = '"+NewPhone+"' where obj_id0="+AccountNumber+" and type = "+PhoneType;   
                System.out.println("query:"+sql);
                Statement sql_stmt = mConn.createStatement();
                int result = sql_stmt.executeUpdate(sql);
                System.out.println("Result:"+result);
                sql_stmt.close();
                DBUtil.closeConnection();
                if(result>=1)
                {  
                    return true;
                }
                    
            }
        } catch (SQLException e) {

        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "VoucherTopupBySMS")
    public String VoucherTopupBySMS( @WebParam(name = "Code") String code,
                                @WebParam(name = "AccountNumber") String AccountNumber,
                                @WebParam(name = "VoucherPin") String[] VoucherPin
                              ) 
    { 
        loadProperties();
        //TODO write your implementation code here:
        BRMOpcodes oc = null;
        try {
            oc = new BRMOpcodes();
        } catch (EBufException ex) {
            Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        FList balGrpFlist = oc.BAL_GET_ACCT_BAL_GRP_AND_SVC(AccountNumber);
        if(balGrpFlist==null)
        {
            return businessProps.getProperty("sms.topup.acct_mismatch.text");
        }
        balGrpFlist.dump();
        SparseArray resultFld = null;
        FList balGrpSvcFlist = null;
        try {
            /////////////////TRY START
            resultFld = balGrpFlist.get(FldResults.getInst());
            for(int index=0; index < resultFld.size(); index++ )
            {
                balGrpSvcFlist = resultFld.elementAt(index);
                Poid svcPoid = balGrpSvcFlist.get(FldServiceObj.getInst());
                
                System.out.println("service Poid"+svcPoid.toString());
                if(svcPoid.toString().contains("/service/ip"))
                {
                    break;
                }                
            }            
            //balGrpSvcFlist.dump();
            Poid billInfoPoid = balGrpSvcFlist.get(FldBillinfoObj.getInst());
            Poid balGrpPoid = balGrpSvcFlist.get(FldBalGrpObj.getInst());
            Poid acctPoid = balGrpSvcFlist.get(FldAccountObj.getInst());

            int totalAmount =0;
            for(String vPin :VoucherPin )
            {
                String voucherID  = oc.searchDeviceID(vPin);
                FList result = oc.PYMT_TOPUP(acctPoid, billInfoPoid, balGrpPoid, voucherID, vPin, 0);
                if(result!=null)
                {
                    totalAmount+= result.get(FldAmount.getInst()).intValue();
                }
            }
            //totalAmount=350;
                if(totalAmount==0)
                {
                    return businessProps.getProperty("sms.topup.data.failed.text");
                }
                String msg = businessProps.getProperty("sms.topup.data.success.text");
                msg=msg.replace("_AMOUNT_",""+totalAmount);
                return msg;
        /////////////////TRY END 
        } catch (EBufException ex) {
            Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return "Failure";
    
    }

    
      
    

}
