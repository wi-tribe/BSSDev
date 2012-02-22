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
import java.util.*;
import customfields.*;

/**
 * This object holds the CPE object parameters
 *
 */
public class CPEObject {
    
    /**
     * Creates a new instance of CPEObject
     */
    public CPEObject() {
        mFldPOID = null;
        mDeviceID = "";
        mDeviceState = 0;
        mManufacturer = "";
        mModel ="";
        mMacAddrLan = "";
        mMacAddrWan = "";
        mSubType = "";
        mAdditionalInfo = "";
        mVoIPServiceEnabled = false;
        mNoOfVoIPPorts = 0;
    }
    
    /**
     *  returns the POID of the device object
     */
    public Poid getPoid() {
        return mFldPOID;
    }
    
    /**
     *  sets the POID of the device object
     *  @poid   PIN_FLD_POID value of the /device/apn/cpe object
     */
    public void setPoid(Poid poid) {
        mFldPOID = poid;
    }
    
    /**
     *  returns the PIN_FLD_DEVICE_ID of the /device/apn/cpe object
     */
    public String getDeviceId() {
        return mDeviceID;
    }
    
    /**
     *  sets the devid of the device object
     *  @sDeviceId   PIN_FLD_DEVICE_ID value of the /device/apn/cpe object
     */
    public void setDeviceId(String sDeviceId) {
        mDeviceID = sDeviceId;
    }
    
    /**
     *  returns the PIN_FLD_STATE_ID of the /device/apn/cpe object
     */
    public int getDeviceState() {
        return mDeviceState;
    }
    
    /**
     *  sets the State ID of the device object
     *  @state   PIN_FLD_STATE_ID value of the /device/apn/cpe object
     */
    public void setDeviceState(int iState) {
        mDeviceState = iState;
    }
    
    /**
     *  returns the PIN_FLD_MANUFACTURER of the /device/apn/cpe object
     */
    public String getManufacturer() {
        return mManufacturer;
    }
    
    /**
     *  sets the Manufacturer of the device object
     *  @sManufacturer   PIN_FLD_MANUFACTURER value of the /device/apn/cpe object
     */
    public void setManufacturer(String sManufacturer) {
        mManufacturer = sManufacturer;
    }
    
    /**
     *  returns the PIN_FLD_MODEL of the /device/apn/cpe object
     */
    public String getModel() {
        return mModel;
    }
    
    /**
     *  sets the Model  of the device object
     *  @sDeviceModel   PIN_FLD_MODEL value of the /device/apn/cpe object
     */
    public void setModel(String sDeviceModel) {
        mModel = sDeviceModel;
    }
    
    /**
     *  returns the WTB_FLD_MAC_ADDRESS_LAN of the /device/apn/cpe object
     */
    public String getLANAddr() {
        return mMacAddrLan;
    }
    
    /**
     *  sets the LAN Address of the device object
     *  @sMACLan   WTB_FLD_MAC_ADDRESS_LAN value of the /device/apn/cpe object
     */
    public void setLANAddr(String sMACLan) {
        mMacAddrLan = sMACLan;
    }
    
    /**
     *  returns the WTB_FLD_MAC_ADDRESS_WAN of the /device/apn/cpe object
     */
    public String getWANAddr() {
        return mMacAddrWan;
    }
    
    /**
     *  sets the CPE Serial  of the device object
     *  @sMACWan   WTB_FLD_MAC_ADDRESS_WAN value of the /device/apn/cpe object
     */
    public void setWANAddr(String sMACWan) {
        mMacAddrWan = sMACWan;
    }
    
    /**
     *  returns the WTB_FLD_SUB_TYPE of the /device/apn/cpe object
     */
    public String getDeviceSubType() {
        return mSubType;
    }
    
    /**
     *  sets the Device type of the device object
     *  @sSupportType   WTB_FLD_SUB_TYPE value of the /device/apn/cpe object this holds the valud either Indoor support (or) OutDoor support.
     *
     */
    public void setDeviceSubType(String sSupportType) {
        mSubType = sSupportType;
    }
    
    /**
     *  returns the additional information set by the business component.
     */
    public String getAdditionalInfo() {
        return mAdditionalInfo;
    }
    
    /**
     *  sets the devid of the device object
     *  @sAddlInfo   Set the additional information set by business component.
     */
    public void setAdditionalInfo(String sAddlInfo) {
        mAdditionalInfo = sAddlInfo;
    }
    
    public void setVoipServiceEnabled(boolean voipenabled) {
        mVoIPServiceEnabled = voipenabled;
    }
    
    public boolean getVoIPServiceEnabled() {
        return mVoIPServiceEnabled;
    }
    
    public void setNoOfVoIPPorts(int no) {
        mNoOfVoIPPorts = no;
    }
    
    public int getNoOfVoIPPorts() { 
        return mNoOfVoIPPorts;
    }
    
    
    
    private Poid mFldPOID;
    private String mDeviceID;
    private int mDeviceState;
    private String mManufacturer;
    private String mModel;
    private String mMacAddrLan;
    private String mMacAddrWan;
    private String mSubType;
    private String mAdditionalInfo;
    private boolean mVoIPServiceEnabled;
    private int mNoOfVoIPPorts;
}