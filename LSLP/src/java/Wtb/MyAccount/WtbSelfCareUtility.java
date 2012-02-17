/**
 * Project  		: WiTribe
 * Module   		: SelfCare
 * Author               : Ramesh Udatha
 * Date and Version     : 6 Apr 2009 , 1.0
 * Last modified by     : Ramesh Udatha
 * Copyright            : SCSL
 */

package Wtb.MyAccount;

import com.portal.app.util.CustomerValErrorData;
import com.portal.app.util.CustomerValErrorParser;
import com.portal.bas.*;
import com.portal.pcm.*;
import com.portal.bas.comp.PIAComponentCollection;
import com.portal.bas.comp.PIAComponentCollectionBean;
import com.portal.pcm.fields.*;
import java.rmi.RemoteException;
import java.io.*;
import customfields.*;
import java.util.*;
import com.portal.pricing.PricingUtils;
import com.portal.pricing.Constants;
import com.portal.pfc.infranet.connpool.*;


/**
 * This used to get some of the BRM Server data for UI Components.
 *
 */
public class WtbSelfCareUtility extends PIAComponentCollectionBean {
    
    /** Creates a new instance of WtbSelfCareUtility */
    public WtbSelfCareUtility() throws RemoteException {
        mCities = new ArrayList();
        mAffinitySlabs = new ArrayList();
        mUsageLimit = new ArrayList();
        mContractPeriod = new ArrayList();
        mHost = new String();
        mSmtp_Port = new String();
        mSmtp_Username = new String();
        mSmtp_Password = new String();
        mDefaultContractPeriod = 0;
    }
    
    /**
     *  This method returns the Webserver time, i.e. new Date().
     *  
     */
    public Date getServerTime() throws RemoteException {
        Date retDate = new Date();
        return retDate;
    }
    
    /**
     *  This method returns the Mail Server details.
     *  
     */
     public String getHost() {
        return mHost;
    }
     
     public String getPort() {
        return mSmtp_Port;
    }
     
     public String getUserName() {
        return mSmtp_Username;
    }
     
     public String getPassword() {
        return mSmtp_Password;
    }
     public PortalContext  getLocalContext() {
	   PortalContext ctx=null;
            try{
                ConnectionFactory cf=null;
                ConnectionPool.createInstance(cf,mProperties);
                ctx=ConnectionPool.getInstance().getConnection();
            }
            catch(EBufException e){
                try {
                    ctx = ConnectionPool.getInstance().getConnection();
                    return ctx;
                } catch (EBufException ebuf) {
                    DefaultLog.log("Exception while getting Connection: " + ebuf.toString());
                    return null;
                }
            }
           return ctx;

	}
    
    
    /**
     * This method returns the contract period details to populate in list box
     * contract period details will be received from /config/contract_period.
     */
    public List getContractPeriod() throws RemoteException {
        PortalContext ctx = null;
        
        try {
            ctx = getLocalContext();
            mContractPeriod = null;
            mContractPeriod = new ArrayList();
            FList inFList = new FList();
            SparseArray argsArray = new SparseArray();
            FList argsList = new FList();
            
            argsList.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(),-1,"/config/contract_period"));
            argsArray.add(1, argsList);
            inFList.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1, "/search"));
            inFList.set(FldFlags.getInst(), 256);
            inFList.set(FldTemplate.getInst(), "select X from /config/contract_period where F1.type like V1 ");
            inFList.setElement(FldResults.getInst(),-1);
            inFList.set(FldArgs.getInst(), argsArray);
            inFList.dump();
            FList outFList = ctx.opcode(7, inFList);
            FList resFList = outFList.getElement(FldResults.getInst(), 0);
            if(resFList == null ) {
                return null;
            }
            SparseArray citiesarray = PricingUtils.getArrayFromFlist(resFList, WtbFldContractDetails.getInst(), new SparseArray());
            for(Enumeration enumeration = citiesarray.getValueEnumerator(); enumeration.hasMoreElements();) {
                FList flContractPeriod = (FList)enumeration.nextElement();
                if(flContractPeriod.get(WtbFldDefaultContractPeriod.getInst()).intValue() > 0 ) { 
                    mDefaultContractPeriod = flContractPeriod.get(WtbFldContractPeriod.getInst()).intValue();
                }
                mContractPeriod.add(flContractPeriod);
            }
        } catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
            throw new RemoteException("error.infranet");
        }
       finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
}
        
        return mContractPeriod;
    }
    
    public List getAffinitySlabList() throws RemoteException {
        PortalContext ctx=null;
        
        try {
            ctx = getLocalContext();
            mAffinitySlabs = null;
            mAffinitySlabs = new ArrayList();
            FList inFList = new FList();
            SparseArray argsArray = new SparseArray();
            FList argsList = new FList();
            
            argsList.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(),-1,"/config/affinity_slabs"));
            argsArray.add(1, argsList);
            inFList.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1, "/search"));
            inFList.set(FldFlags.getInst(), 256);
            inFList.set(FldTemplate.getInst(), "select X from /config/affinity_slabs where F1.type like V1 ");
            inFList.setElement(FldResults.getInst(),-1);
            inFList.set(FldArgs.getInst(), argsArray);
            
            FList outFList = ctx.opcode(7, inFList);
            FList resFList = outFList.getElement(FldResults.getInst(), 0);
            if(resFList == null ) {
                return null;
            }
            SparseArray slabsarray = PricingUtils.getArrayFromFlist(resFList, FldAttributes.getInst(), new SparseArray());
            for(Enumeration enumeration = slabsarray.getValueEnumerator(); enumeration.hasMoreElements();) {
                mAffinitySlabs.add((FList)enumeration.nextElement());
            }
        } catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
            throw new RemoteException("error.infranet");
        }
        
        finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
        }
        return mAffinitySlabs;
    }
    
    /**
     *  This method returns city list to populate in City drop down in address data entry UI components
     *
     */
    public List getCityList() throws RemoteException {
        PortalContext ctx=null;
        
        try {
            ctx = getLocalContext();
            mCities = null;
            mCities = new ArrayList();
            FList inFList = new FList();
            SparseArray argsArray = new SparseArray();
            FList argsList = new FList();
            
            argsList.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(),-1,"/config/city_codes"));
            argsArray.add(1, argsList);
            inFList.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1, "/search"));
            inFList.set(FldFlags.getInst(), 256);
            inFList.set(FldTemplate.getInst(), "select X from /config/city_codes where F1.type like V1 ");
            inFList.setElement(FldResults.getInst(),-1);
            inFList.set(FldArgs.getInst(), argsArray);
            
            FList outFList = ctx.opcode(7, inFList);
            FList resFList = outFList.getElement(FldResults.getInst(), 0);
            if(resFList == null ) {
                return null;
            }
            SparseArray citiesarray = PricingUtils.getArrayFromFlist(resFList, WtbFldCityCodes.getInst(), new SparseArray());
            for(Enumeration enumeration = citiesarray.getValueEnumerator(); enumeration.hasMoreElements();) {
                mCities.add((FList)enumeration.nextElement());
            }
        } catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
            throw new RemoteException("error.infranet");
        }
        
        finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
}
        return mCities;
    }
    
    /**
     *  This method returns mail details
    0 PIN_FLD_POID                      POID [0] 0.0.0.1 /search 0 0
    0 PIN_FLD_FLAGS                      INT [0] 256
    0 PIN_FLD_OP_CORRELATION_ID          STR [0] "1:HDL-PCS469800:UnknownProgramName:0:AWT-EventQueue-0:7:1242813578:0"
    0 PIN_FLD_TEMPLATE                   STR [0] "select X from /config/communication_params where F1.type like V1 "
    0 PIN_FLD_RESULTS                  ARRAY [*] allocated 0, used 0
    0 PIN_FLD_ARGS                     ARRAY [1] allocated 1, used 1
    1     PIN_FLD_POID                  POID [0] 0.0.0.1 /config/communication_params -1 0

     */
    public boolean getSMTPDetails() throws RemoteException{ 
        
        PortalContext ctx=null;
        
        
        try {
            ctx = getLocalContext();
            FList inFList = new FList();
            SparseArray argsArray = new SparseArray();
            FList argsList = new FList();
            
            argsList.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(),-1,"/config/communication_params"));
            argsArray.add(1, argsList);
            inFList.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1, "/search"));
            inFList.set(FldFlags.getInst(), 256);
            inFList.set(FldTemplate.getInst(), "select X from /config/communication_params where F1.type like V1 ");
            inFList.setElement(FldResults.getInst(),-1);
            inFList.set(FldArgs.getInst(), argsArray);
            FList outFList = ctx.opcode(7, inFList);
            FList resFList = outFList.getElement(FldResults.getInst(), 0);
            if(resFList == null ) {
                return false;
            }
                      
            SparseArray configarray = PricingUtils.getArrayFromFlist(resFList, WtbFldCommunicationParams.getInst(), new SparseArray());
            for(Enumeration enumeration = configarray.getValueEnumerator(); enumeration.hasMoreElements();) {
                
                FList cur=(FList)enumeration.nextElement();
                cur.dump();
                
                if((cur.get(WtbFldCommunicationType.getInst())).equalsIgnoreCase("EMAIL")){
                    mHost=(String)cur.get(WtbFldHost.getInst());
                    mSmtp_Port=(String)cur.get(WtbFldPort.getInst());
                    mSmtp_Username=(String)cur.get(WtbFldUsername.getInst());
                    mSmtp_Password=(String)cur.get(WtbFldPassword.getInst());
                    return true;
                }

            }
        } catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
            throw new RemoteException("error.infranet");
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
    
    public int getDefaultContractPeriod() throws RemoteException {
        return mDefaultContractPeriod;
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
    
    public void setProperties(Properties props) {
	mProperties = props;
}
    
    
private Properties mProperties;
    private String mHost;
    private String mSmtp_Port;
    private String mSmtp_Username;
    private String mSmtp_Password;
    private List mCities;
    private List mAffinitySlabs;
    private List mUsageLimit;
    private List mContractPeriod;
    private int mDefaultContractPeriod;
}
