/**
 * Project  		: WiTribe
 * Module   		: SelfCare
 * Author               : Ramesh Udatha
 * Date and Version     : 6 Apr 2009 , 1.0
 * Last modified by     : Ramesh Udatha
 * Copyright            : SCSL
 */

package Wtb.MyAccount;

import com.portal.bas.*;
import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import com.portal.bas.comp.PIAComponentCollection;
import com.portal.bas.comp.PIAComponentCollectionBean;
import customfields.*;
import java.util.*;
import javax.crypto.NullCipher;

/**
 * This object holds the CPE object parameters and if any further validations to be taken care at Device object level.
 *
 */
public class ValidateIP extends PIAComponentCollectionBean  {
    
    /** Creates a new instance of ValidateIP */
    public ValidateIP()  throws RemoteException {
        mRemoteHost = "";
    }
    
    /**
     *  sets the UserIP address
     *  @sUserIP   Set the UserIP address.
     */
    public void setRemoteHost(String sUserIP)  throws RemoteException {
        mRemoteHost = sUserIP;
    }
    
    
    /**
     *  returns the User IP address
     *
     */
    public String getRemoteHost()   throws RemoteException{
        return mRemoteHost;
    }
    
    
    /**
     *  This method validates the user IP address exists in the WTB_BLOCKED_HOSTS or not.
     *  If Not exists, return false so that user can see the login page.
     *  If exists, it validates no of attempts
     * If attempts <5 then returns false so that user can see the login page.
     * If attempts >5 then validate the last login attempt time.
     * if difference is > 30 then returns false so that user can see the login page.
     * else return true so that user can see the IP blocked message.
     */
    public boolean checkIP()  throws RemoteException {
        Poid mpoid = getPoid();
        boolean lbreturn = false;
        if (mpoid == null) {
            return false;
        } else {
            try{
                FList ipdetails = mResults;
                
                int attempt = ipdetails.get(WtbFldBlockedipList.getInst()).getAnyElement().get(WtbFldAttempt.getInst()).intValue ();
                //String host = ipdetails.get(WtbFldHostIp.getInst());
                Date lasttime = (Date)ipdetails.get(WtbFldBlockedipList.getInst()).getAnyElement().get(WtbFldLastAttemptTime.getInst());
                if(DefaultLog.doLog(8)) {
                    DefaultLog.log(8, "ValidateIP=> IP Data:" + attempt + ":" + lasttime.toString());
                }
                if(attempt < 5) {
                    return false;
                } else {
                    Date dt = new Date();
                    long diff = (dt.getTime() - lasttime.getTime())/(60000);
                    if(diff > 30)
                        return false;
                    else
                        return true;
                }
            } catch(Exception e) {
                if(DefaultLog.doLog(8))
                    DefaultLog.log(8, "ValidateIP=> IP Data:" + e.getMessage());
            }
        }
        
        return lbreturn;
    }
    
     public PCachedContext getLocalContext() {
	    try {
                PCachedContext ctx = new PCachedContext();
                ctx.connect();
                return ctx;
	    } catch (EBufException ebufex) {
                DefaultLog.log(ebufex.toString ());
	    }
            return null;
	}
    
    
    /**
     *  This method used to get the record in WTB_BLOCKED_HOSTS table.
     *  for the iP address user is trying to access MyAccount Application.
     */
    public Poid getPoid() throws RemoteException {
        Poid lpoid = null;
        List resList= new ArrayList();
        FList FListIn = new FList();
        FList outlist = new FList();
        FList args = new FList();
        FList wtbCode=new FList();
        Poid searchPoid = new Poid(1,-1,"/search");
        SparseArray argslist = new SparseArray();
        SparseArray argsArray = new SparseArray();
        SparseArray wtbRequestList= new SparseArray();
        PCachedContext ctx=null;
        
        
        
        try {
            ctx = getLocalContext();
            String searchTmpt = "select X from /WTB_BLOCKED_HOSTS where F1 = V1 ";
            FListIn.set(FldPoid.getInst(), searchPoid);
            FListIn.set(FldFlags.getInst(),256);
            FListIn.set(FldTemplate.getInst(), searchTmpt);
            FListIn.setElement(FldResults.getInst(),-1);
            
            wtbCode.set(WtbFldHostIp.getInst(),mRemoteHost);
            wtbRequestList.add(wtbCode);
            args.set(WtbFldBlockedipList.getInst(),wtbRequestList);
            argslist.add(1,args);
            FListIn.set(FldArgs.getInst(),argslist);
            if(DefaultLog.doLog(8))
                DefaultLog.log(8, "ValidateIP=> Input flist for Search:" + FListIn.toString());
            outlist = ctx.opcode(PortalOp.SEARCH,FListIn);
            
            if(!outlist.hasField(FldResults.getInst())) {
                if(DefaultLog.doLog(8))
                    DefaultLog.log(8, "WtbUtil=>No Poid found for the host Address");
                return lpoid;
                //throw new RemoteException("error.pay");
            }
            
            SparseArray resultsArray = outlist.get(FldResults.getInst());
            if(resultsArray == null)
                return null;
            Enumeration enumVals = resultsArray.getValueEnumerator();
            do
            {
                if(!enumVals.hasMoreElements())
                    break;
                FList r1 = (FList)enumVals.nextElement();
                Poid poid = r1.get(FldPoid.getInst());
                if(poid == null) {
                    if(DefaultLog.doLog(8))
                        DefaultLog.log(8, "No poid");
                    return null;
                }
                mResults = r1;
                return poid;
                
            } while(true);
            
        } catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
            //throw new Exception(e);
        }
        finally{
        releaseContext(ctx);
        }
        
        return lpoid;
        
        
    }
    
    /**
     *  This method is used to delete the record from WTB_BLOCKED_HOSTS table.
     *  When there is a successful login, then that IP record will be removed the system
     */
    public void deleteHost() throws RemoteException {
        PCachedContext ctx=null;
        FList outlist = new FList();
        FList FListIn = new FList();
        try{
            ctx = getLocalContext();
            mpoid = getPoid();
            while(mpoid!=null){
                if(mpoid != null){
                    if(DefaultLog.doLog(2))
                        DefaultLog.log(this, 2, "ValidateIP=> POID OF HOST:" + mpoid.toString());
                    FListIn.set(FldPoid.getInst(),mpoid);
                    outlist = ctx.opcode(2,FListIn);
                }
                mpoid = getPoid();
            }
            
        } catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
        }
        releaseContext(ctx);
    }
    
    /**
     *  This method is to incremetn the failed login attempt count in the WTB_BLOCKED_HOSTS Table.
     *  If there is no record exists, then it will add a new record with that IP address.
     *
     */
    public int incrementCount() throws RemoteException {
        PCachedContext ctx=null;
        
        FList outlist = new FList();
        FList FListIn = new FList();
        int attempts = 0;
        try{
            ctx = getLocalContext();
            mpoid = getPoid();
            if(mpoid == null) {
                addHostDetails();
                return 1;
            } else {
                FList ipdetails = mResults;
                attempts = ipdetails.get(WtbFldBlockedipList.getInst()).getAnyElement().get(WtbFldAttempt.getInst()).intValue ();
                Date lastAccessTime = ipdetails.get(WtbFldBlockedipList.getInst()).getAnyElement().get(WtbFldLastAttemptTime.getInst());
                Date  now = new Date();
                long diff = (now.getTime() - lastAccessTime.getTime())/(60000);
                
                if(diff > 30)
                    attempts = 1;
                else
                    attempts = attempts + 1;
                
                FListIn.set(FldPoid.getInst(),mpoid);
                SparseArray list = new SparseArray();
                FList in = new FList();
                in.set(WtbFldAttempt.getInst(),attempts);
                in.set(WtbFldLastAttemptTime.getInst(), new Date());
                list.add(in);
                FListIn.set(WtbFldBlockedipList.getInst(),list);
                outlist = ctx.opcode(PortalOp.WRITE_FLDS,FListIn);
                return attempts;
            }
            
        } catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
            //throw new Exception(e);
        }
        releaseContext(ctx);
        return 0;
    }
    
    /**
     *  This method is called from incrementCount() to add new record into WTB_BLOCKED HOSTS table.
     */
    public void addHostDetails() throws RemoteException {
        
        
        String remoteHost = mRemoteHost;
        Date dt = new Date();
        FList outlist = new FList();
        FList FListIn = new FList();
        PCachedContext ctx=null;
        try {
            ctx = getLocalContext();
            FListIn.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/WTB_BLOCKED_HOSTS"));
            FListIn.set(FldAccountObj.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/account"));
            FList argsList = new FList();
            argsList.set(WtbFldHostIp.getInst(),mRemoteHost);
            argsList.set(WtbFldAttempt.getInst(),1);
            argsList.set(WtbFldLastAttemptTime.getInst(), new Date());
            SparseArray argsArray = new SparseArray();
            argsArray.add(argsList);
            FListIn.set(WtbFldBlockedipList.getInst(), argsArray);
            outlist = ctx.opcode(1,FListIn);
        } catch(Exception e) {
            //
        }
        releaseContext(ctx);
    }
    
    /**
     *  this method needs to be overridden since this class is extending PIAComponentCollectionBean.
     */
    public void update(int i, Object obj) {
        
    }
    
    /**
     *  this method needs to be overridden since this class is extending PIAComponentCollectionBean.
     */
    public Object getSelectionDataFor(String dataItem, int index) {
        return null;
    }
    
    
    private String mRemoteHost;
    private Poid mpoid;
    private FList mResults;
}
