package witribe.brm;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author PKSaadB
 */
public class RechargeCallingCardResponse 
{
    public String timestamp=null;
    public String voucherSerial=null;
    public String voucherPin=null;
    public String faceValue=null;
    public String balanceExpiryTime=null;
    public String ReturnCode=null;
    public String ReturnString=null;
    
    public RechargeCallingCardResponse()
    {
        
    }
    @Override
    public String toString()
    {
        String result = null;
        result = "AccountDetails   ("
                + "\n\ttimeStamp: "+timestamp
                +"\n\tvoucherSerial: "+voucherSerial
                +"\n\tvoucherPin: "+voucherPin
                +"\n\tfaceValue: "+faceValue
                +"\n\tbalanceExpiryTime: "+balanceExpiryTime
                +"\n\tReturnCode: "+ReturnCode
                +"\n\tReturnString: "+ReturnString
                +"\n)";
        return result;
    }
        
}