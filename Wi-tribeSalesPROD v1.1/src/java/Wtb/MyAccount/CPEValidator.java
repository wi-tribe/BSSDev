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
import java.util.*;
import customfields.*;
import com.portal.pfc.infranet.connpool.*;



/**
 * This object holds the CPE object parameters and if any further validations to be taken care at Device object level.
 *
 */
public class CPEValidator extends PIAComponentCollectionBean  {
    
    /** Creates a new instance of CPEValidator */
    public CPEValidator() throws RemoteException {
        mMacAddress = "";
        mSalesId = "";
    }
    
    
    public String getMacAddr() throws RemoteException {
        return mMacAddress;
    }
    public String getSalesid() throws RemoteException {
        return mSalesId;
    }
    
    public void setProperties(Properties props) {
        mProperties = props;
    }
    
    /**
     *  sets the cpe MAC address of the device.
     *  @sDeviceMac   Set the device MAC address received from UI component.
     */
    public void setMacAddr(String sDeviceMac) throws RemoteException {
        mMacAddress = sDeviceMac;
    }
     public void setSalesid(String sSalesId) throws RemoteException {
        mSalesId = sSalesId;
    }
    
    public String getDeviceID() throws RemoteException {
        return mDeviceID;
    }
    
    public void setDeviceID(String sDeviceID) throws RemoteException {
        mDeviceID = sDeviceID;
    }
    
    /**
     *  This method returns the the CPE Device details in the form of XML.
     */
    public String getDeviceXML() throws RemoteException {
        String rXML = "";
        if (mCPE != null) {
            rXML = rXML + "<CPE>";
            rXML = rXML + "<INFO>" + mCPE.getAdditionalInfo() + "</INFO>";
            rXML = rXML + "<DEVICEID>" + mCPE.getDeviceId() + "</DEVICEID>";
            rXML = rXML + "<DEVICESTATE>" + mCPE.getDeviceState() + "</DEVICESTATE>";
            rXML = rXML + "<DEVICETYPE>" + mCPE.getDeviceSubType() + "</DEVICETYPE>";
            rXML = rXML + "<LAN>" + mCPE.getLANAddr() + "</LAN>";
            rXML = rXML + "<MANUFACTURER>" + mCPE.getManufacturer() + "</MANUFACTURER>";
            rXML = rXML + "<MODEL>" + mCPE.getModel() + "</MODEL>";
            rXML = rXML + "<WAN>" + mCPE.getWANAddr() + "</WAN>";
            rXML = rXML + "<POID>" + mCPE.getPoid() + "</POID>";
            rXML = rXML + "<VOIP_ENABLED>" + mCPE.getVoIPServiceEnabled() + "</VOIP_ENABLED>";
            rXML = rXML + "<NO_VOIP_PORTS>" + mCPE.getNoOfVoIPPorts() + "</NO_VOIP_PORTS>";
            rXML = rXML + "</CPE>";
        }
        return rXML;
    }
    
    /**
     *  This method reads the CPE Device details from PORTAL  and assign the required details to CPEObject.
     */
    
       
    public void getCPEDetails() throws  RemoteException {
        PortalContext ctx=null;
         String msg = "";
        mCPE = new CPEObject();
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
                return;
            }
        }

        try {
            
            FList searchFlist = new FList();
            searchFlist.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));  //Creating POID for search object at runtime
            searchFlist.set(FldFlags.getInst(), 256);                         //Specifying the type of search
            String template = "select X from /device/apn/cpe where F1 = V1 ";
            searchFlist.set(FldTemplate.getInst(), template);      //Specifying search template
            SparseArray args = new SparseArray();
            FList subFlist = new FList();
            
            subFlist.set(WtbFldMacAddressWan.getInst(),mMacAddress);
            args.add(1, subFlist);
            SparseArray args2 = new SparseArray();
            FList subFlist2= new FList();
            subFlist2.set(WtbFldCpeDetails.getInst(),args);
            args2.add(1,subFlist2);
            searchFlist.set(FldArgs.getInst(),args2);
            searchFlist.setElement(FldResults.getInst(), 0);
            FList out = ctx.opcode(7, searchFlist);
            if(out.hasField(FldResults.getInst())==false) {
                mCPE.setDeviceId("");
                mCPE.setDeviceState(0);
                mCPE.setDeviceSubType("");
                mCPE.setLANAddr("");
                mCPE.setManufacturer("");
                mCPE.setModel("");
                mCPE.setPoid(null);
                mCPE.setWANAddr("");
                mCPE.setAdditionalInfo("Device Not Found");
            }
            
            SparseArray results = out.get(FldResults.getInst());
            //out.dump();
            FList orderList = results.elementAt(0);
            mCPE.setManufacturer(orderList.get(FldManufacturer.getInst()));
            mCPE.setModel(orderList.get(FldModel.getInst()));
            SparseArray results2 = orderList.get(WtbFldCpeDetails.getInst());
            FList orderList2 = results2.elementAt(0);
            mCPE.setDeviceSubType(orderList2.get(WtbFldSubType.getInst()).toString());
             System.out.println("in da function   "   + orderList2.get(WtbFldSubType.getInst()).toString());


            if(orderList2.get(WtbFldSalesId.getInst()).equalsIgnoreCase(mSalesId)) 
             {
            mCPE.setDeviceSubType(orderList2.get(WtbFldSubType.getInst()).toString());
            mCPE.setWANAddr(orderList2.get(WtbFldMacAddressWan.getInst()).toString());
            mCPE.setLANAddr(orderList2.get(WtbFldMacAddressLan.getInst()).toString());
            mCPE.setDeviceId(orderList.get(FldDeviceId.getInst()).toString());
            mCPE.setPoid(orderList.get(FldPoid.getInst()));
            mCPE.setDeviceState(orderList.get(FldStateId.getInst()).intValue ());
            if(orderList2.get(WtbFldUsrDefined3.getInst()).equalsIgnoreCase("")) {
                mCPE.setNoOfVoIPPorts(0);
            } else {
                mCPE.setNoOfVoIPPorts(Integer.parseInt(orderList2.get(WtbFldUsrDefined3.getInst())));
            }
            if(orderList2.get(WtbFldUsrDefined5.getInst()).equalsIgnoreCase("")) {
                mCPE.setVoipServiceEnabled(false);
            } else if (orderList2.get(WtbFldUsrDefined5.getInst()).equalsIgnoreCase("1")) {
                mCPE.setVoipServiceEnabled(true);
            } else {
                mCPE.setVoipServiceEnabled(false);
            }            
            
            if(orderList.get(FldStateId.getInst()).toString().equals("1")) {
                mCPE.setAdditionalInfo("Device is NOT Ready for Assignment.");
            } else if (orderList.get(FldStateId.getInst()).toString().equals("2")) {
                mCPE.setAdditionalInfo("NEW");
            } else {
              
                msg = "Device cannot be assigned, status is";
                if(orderList.get(FldStateId.getInst()).toString().equals("3")) {
                    msg = msg + " : Assigned.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("4")) {
                    msg = msg + " : ToERP_DAMAGED.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("5")) {
                    msg = msg + " : Refurbished.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("6")) {
                    msg = msg + " : Damaged.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("7")) {
                    msg = msg + " : Lost.";
                }
                else if (orderList.get(FldStateId.getInst()).toString().equals("8")) {
                    msg = msg + " : ToERP_Transfer.";
                }
                else {
                    msg = msg + " : NOT Defined.";
                }               
                mCPE.setAdditionalInfo(msg);
            }
             }
             else {
                mCPE.setAdditionalInfo("Assinged to Another Sales ID.");
                //msg = msg + " :Assinged to Another Sales ID.";
               }
                         
        } catch(Exception e) {
            //
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
        }
        
        
    }
    
    public void getCPEDetailsUsingDeviceID() throws  RemoteException {
        PortalContext ctx=null;
        mCPE = new CPEObject();
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
                return;
            }
        }
        try {
            
            FList searchFlist = new FList();
            searchFlist.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));  //Creating POID for search object at runtime
            searchFlist.set(FldFlags.getInst(), 256);                         //Specifying the type of search
            String template = "select X from /device where F1 = V1 ";
            
            searchFlist.set(FldTemplate.getInst(), template);      //Specifying search template
            SparseArray args = new SparseArray();
            FList subFlist = new FList();
            subFlist.set(FldDeviceId.getInst(), mDeviceID);
            args.add(1, subFlist);
            SparseArray args2 = new SparseArray();
            FList subFlist2= new FList();
            subFlist2.set(WtbFldCpeDetails.getInst(),args);
            args2.add(1,subFlist);
            searchFlist.set(FldArgs.getInst(),args2);
            searchFlist.setElement(FldResults.getInst(), 0);
            //searchFlist.dump ();
            FList out = ctx.opcode(7, searchFlist);
            if(out.hasField(FldResults.getInst())==false) {
                mCPE.setDeviceId("");
                mCPE.setDeviceState(0);
                mCPE.setDeviceSubType("");
                mCPE.setLANAddr("");
                mCPE.setManufacturer("");
                mCPE.setModel("");
                mCPE.setPoid(null);
                mCPE.setWANAddr("");
                mCPE.setAdditionalInfo("Device Not Found");
            }
            SparseArray results = out.get(FldResults.getInst());
            //out.dump();
            FList orderList = results.elementAt(0);
            mCPE.setManufacturer(orderList.get(FldManufacturer.getInst()));
            mCPE.setModel(orderList.get(FldModel.getInst()));
            SparseArray results2 = orderList.get(WtbFldCpeDetails.getInst());
            FList orderList2 = results2.elementAt(0);
            if(orderList2.get(WtbFldSalesId.getInst()).equalsIgnoreCase(mSalesId)) 
             {
            mCPE.setDeviceSubType(orderList2.get(WtbFldSubType.getInst()).toString());
            mCPE.setWANAddr(orderList2.get(WtbFldMacAddressWan.getInst()).toString());
            mCPE.setLANAddr(orderList2.get(WtbFldMacAddressLan.getInst()).toString());
            mCPE.setDeviceId(orderList.get(FldDeviceId.getInst()).toString());
            mCPE.setPoid(orderList.get(FldPoid.getInst()));
            mCPE.setDeviceState(orderList.get(FldStateId.getInst()).intValue ());
            if(orderList2.get(WtbFldUsrDefined3.getInst()).equalsIgnoreCase("")) {
                mCPE.setNoOfVoIPPorts(0);
            } else {
                mCPE.setNoOfVoIPPorts(Integer.parseInt(orderList2.get(WtbFldUsrDefined3.getInst())));
            }
            if(orderList2.get(WtbFldUsrDefined5.getInst()).equalsIgnoreCase("")) {
                mCPE.setVoipServiceEnabled(false);
            } else if (orderList2.get(WtbFldUsrDefined5.getInst()).equalsIgnoreCase("1")) {
                mCPE.setVoipServiceEnabled(true);
            } else {
                mCPE.setVoipServiceEnabled(false);
            }       
            if(orderList.get(FldStateId.getInst()).toString().equals("1")) {
                mCPE.setAdditionalInfo("Device is NOT Ready for Assignment.");
            } else if (orderList.get(FldStateId.getInst()).toString().equals("2")) {
                mCPE.setAdditionalInfo("NEW");
            } else {
                String msg = "";
                msg = "Device cannot be assigned, status is";
                if(orderList.get(FldStateId.getInst()).toString().equals("3")) {
                    msg = msg + " : Assigned.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("4")) {
                    msg = msg + " : ToERP_DAMAGED.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("5")) {
                    msg = msg + " : Refurbished.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("6")) {
                    msg = msg + " : Damaged.";
                }
                else if(orderList.get(FldStateId.getInst()).toString().equals("7")) {
                    msg = msg + " : Lost.";
                }
                else if (orderList.get(FldStateId.getInst()).toString().equals("8")) {
                    msg = msg + " : ToERP_Transfer.";
                }
                else {
                    msg = msg + " : NOT Defined.";
                }               
                mCPE.setAdditionalInfo(msg);
            }
            }
             else {
                mCPE.setAdditionalInfo("Assinged to Another Sales ID.");
                //msg = msg + " :Assinged to Another Sales ID.";
               }
            //System.out.println (getDeviceXML());
           
}

        catch(Exception e) {
            if(DefaultLog.doLog(2))
                DefaultLog.log(this, 2, e);
        }
        finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
        }
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
    
    private Properties mProperties;
    
    private CPEObject mCPE;
    private String mDeviceID;
    private String mMacAddress;
    private String mSalesId;
}
