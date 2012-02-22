/*
 * MailerFactory.java
 *
 * Created on August 21, 2009, 2:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Wtb.MyAccount;

/**
 *
 * @author ST61248
 */
import com.portal.bas.PCachedContext;
import com.portal.pcm.EBufException;
import com.portal.pcm.*;
import com.portal.bas.*;
import com.portal.pcm.fields.*;
import com.portal.bas.comp.PIAComponentCollection;
import com.portal.bas.comp.PIAComponentCollectionBean;
import java.rmi.RemoteException;
import com.portal.pfc.infranet.connpool.*;
import java.util.*;

public class MailerFactory extends PIAComponentCollectionBean {

    private Properties mProperties;
    public static final int WELCOME_MAIL=3;
    public static final int PASSWORD_TPIN_MAIL=4;
    public static final int RESEND_PASSWORD_MAIL=2;
    public static final int RESET_TPIN_MAIL=1;
    
    public  MailerFactory() throws RemoteException{} ;
/*
0 PIN_FLD_POID                      POID [0] 0.0.0.1 /wtb_email_details -1 1
0 PIN_FLD_ACCOUNT_OBJ               POID [0] 0.0.0.1 /account 30155 10
0 PIN_FLD_EMAIL_ADDR                 STR [0] "no-reply@wi-tribe.pk"
0 PIN_FLD_FLAGS                      INT [0] 0
0 PIN_FLD_NAME                       STR [0] "wi-tribe"
0 PIN_FLD_TYPE_STR                   STR [0] "2"
 */
    public void setProperties(Properties props) {
	mProperties = props;
}
    public boolean sendMail(int type,Poid acctPoid) throws RemoteException {
        
         PortalContext ctx=null;
        try{
            ConnectionFactory cf=null;
            ConnectionPool.createInstance(cf,mProperties);
            ctx=ConnectionPool.getInstance().getConnection();
        }
        catch(EBufException e){
            try {
                ctx = ConnectionPool.getInstance().getConnection();
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while getting Connection: " + ebuf.toString());
            }
        }

        FList in = new FList();
        in.set(FldPoid.getInst(), new Poid(1, -1L, "/wtb_email_details"));
        in.set(FldAccountObj.getInst(),acctPoid);
        in.set(FldEmailAddr.getInst(),"no-reply@wi-tribe.pk");
        in.set(FldFlags.getInst(),0);
        in.set(FldName.getInst(),"wi-tribe");
        in.set(FldTypeStr.getInst(),type+"");
        try {
            
            FList out = ctx.opcode(PortalOp.CREATE_OBJ, in);
            return true;

        } catch (EBufException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
        }
        return false;
    }
}
