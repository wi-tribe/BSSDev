/**
 * Project  		: WiTribe
 * Module   		: Account Creation
 * Author               : Muralidhar Kambala
 * Date and Version     : 13 August 2010 , 1.0
 * Last modified by     : Muralidhar Kambala
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


public class SpecialNumbers extends PIAComponentCollectionBean  {
    
    /** Creates a new instance of SpecialNumbers */
    public SpecialNumbers() throws RemoteException {
       wanaddr = "";
    }
    //start 
      //Set WanAddress
       public String getWanAdrr() throws RemoteException {
        return wanaddr;
    }
    /**
     *  sets the cpe MAC address of the device.
     *  @sDeviceMac   Set the device MAC address received from UI component.
     */
    public void setWanAdrr(String password) throws RemoteException {
        wanaddr = password;
    }
       
      
      //Get Special Numbers
      public String getSpecialNumbers(String sCity, int ServiceType) throws RemoteException {
         
          ArrayList resList = null; //new ArrayList();
          FList inList = new FList();
         
          String CityCode = "";
          PortalContext ctx=null;
          StringBuffer strBuf=new StringBuffer();
          String rXML = "";
          //mCPE = new CPEObject();
          try {
              //System.out.println("getSpecialNumbers ....");
              ctx = getContext();
              if(sCity.equalsIgnoreCase("Karachi")) {
                  CityCode = "KA";
              } else if(sCity.equalsIgnoreCase("Lahore")) {
                  CityCode = "LA";
              } else if(sCity.equalsIgnoreCase("Islamabad")) {
                  CityCode = "IS";
              } else if(sCity.equalsIgnoreCase("Rawalpindi")) {
                  CityCode = "RA";
              } else if(sCity.equalsIgnoreCase("Gujranwala")) {
                  CityCode = "GU";
              }
              
              if(ServiceType == 1) {
                  CityCode = CityCode + "_" + "Prepaid"+"%";
              } else {
                  CityCode = CityCode + "_" + "Postpaid"+"%";
              }
          
             /*
                        0 PIN_FLD_POID                      POID [0] 0.0.0.1 /search -1 0
                        0 PIN_FLD_FLAGS                      INT [0] 0
                        0 PIN_FLD_OP_CORRELATION_ID          STR [0] "1:hst-pcscub16:UnknownProgramName:0:AWT-EventQueue-0:7:1274445416:0"
                        0 PIN_FLD_TEMPLATE                   STR [0] "select X from /device/num 1, /block 2 where 1.F1 like V1 AND 2.F2 = V2 AND ( 1.F3 >= 2.F4 AND 1.F5 <= 2.F6 ) AND 1.F7 = V7 AND ( 1.F8 = V8 or 1.F9 = V9 ) AND 1.F10 = V10 AND 1.F11 like V11 "
                        0 PIN_FLD_RESULTS                  ARRAY [0] allocated 4, used 4
                        1     PIN_FLD_ACCOUNT_OBJ           POID [0] NULL
                        1     PIN_FLD_DEVICE_ID              STR [0] ""
                        1     PIN_FLD_STATE_ID               INT [0] 0
                        1     PIN_FLD_DEVICE_NUM       SUBSTRUCT [0] allocated 2, used 2
                        2         PIN_FLD_PERMITTED          STR [0] ""
                        2         PIN_FLD_VANITY            ENUM [0] 0
             *
                        0 PIN_FLD_ARGS                     ARRAY [1] allocated 1, used 1
                        1     PIN_FLD_DEVICE_ID              STR [0] "%"
                        0 PIN_FLD_ARGS                     ARRAY [2] allocated 1, used 1
                        1     PIN_FLD_NAME                   STR [0] "Aisha_Block_pre"
                        0 PIN_FLD_ARGS                     ARRAY [3] allocated 1, used 1
                        1     PIN_FLD_DEVICE_ID              STR [0] ""
                        0 PIN_FLD_ARGS                     ARRAY [4] allocated 1, used 1
                        1     PIN_FLD_START_NUMBER           STR [0] "%"
                        0 PIN_FLD_ARGS                     ARRAY [5] allocated 1, used 1
                        1     PIN_FLD_DEVICE_ID              STR [0] ""
                        0 PIN_FLD_ARGS                     ARRAY [6] allocated 1, used 1
                        1     PIN_FLD_END_NUMBER             STR [0] "%"
                        0 PIN_FLD_ARGS                     ARRAY [7] allocated 1, used 1
                        1     PIN_FLD_POID                  POID [0] 0.0.0.1 /device/num -1 0
                        0 PIN_FLD_ARGS                     ARRAY [8] allocated 1, used 1
                        1     PIN_FLD_STATE_ID               INT [0] 1
                        0 PIN_FLD_ARGS                     ARRAY [9] allocated 1, used 1
                        1     PIN_FLD_STATE_ID               INT [0] 4
                        0 PIN_FLD_ARGS                     ARRAY [10] allocated 1, used 1
                        1     PIN_FLD_DEVICE_NUM       SUBSTRUCT [0] allocated 1, used 1
                        2         PIN_FLD_CATEGORY_ID        INT [0] 1
                        0 PIN_FLD_ARGS                     ARRAY [11] allocated 1, used 1
                        1     PIN_FLD_DEVICE_NUM       SUBSTRUCT [0] allocated 1, used 1
                        2         PIN_FLD_PERMITTED          STR [0] "LA_Prepaid%"
             
             */
          if(CityCode != "") {
             FList inFlist = new FList();
              Poid searchPoid = new Poid(1, -1, "/search");//-1 means new
              inFlist.set(FldPoid.getInst(),searchPoid);
              inFlist.set(FldFlags.getInst(),0);
              inFlist.set(FldTemplate.getInst()," select X from /device/num 1, /block 2 where 1.F1 like V1 AND 2.F2 = V2 AND ( 1.F3 >= 2.F4 AND 1.F5 <= 2.F6 ) AND 1.F7 = V7 AND ( 1.F8 = V8 or 1.F9 = V9 ) AND 1.F10 = V10 AND 1.F11 like V11 ");
              
              FList resultFList = new FList();
              resultFList.set(FldAccountObj.getInst());
              resultFList.set(FldDeviceId.getInst(),"");
              resultFList.set(FldStateId.getInst(),0);
              
              FList deviceFList = new FList();
              deviceFList.set(FldPermitted.getInst(),"");
              deviceFList.set(FldVanity.getInst(),0);
              
              resultFList.set(FldDeviceNum.getInst(),deviceFList);
              SparseArray resultArray = new SparseArray();
              resultArray.add(resultFList);
              inFlist.set(FldResults.getInst(),resultArray);
              
              FList deviceIDFlist1 = new FList();
              deviceIDFlist1.set(FldDeviceId.getInst(),"%");
              
              FList nameFlist = new FList();
              nameFlist.set(FldName.getInst(),wanaddr);
              
              FList deviceIDFlist2 = new FList();
              deviceIDFlist2.set(FldDeviceId.getInst(),"");
              
              FList startNumberFlist = new FList();
              startNumberFlist.set(FldStartNumber.getInst(),"%");
              
              FList deviceIDFlist3 = new FList();
              deviceIDFlist3.set(FldDeviceId.getInst(),"");
              
              FList endNumberFlist = new FList();
              endNumberFlist.set(FldEndNumber.getInst(),"%");
              
              FList poidFlist = new FList();
              poidFlist.set(FldPoid.getInst(),new Poid(1,-1,"/device/num"));
              
              FList stateId1Flist = new FList();
              stateId1Flist.set(FldStateId.getInst(),1);
              
              FList stateId2Flist = new FList();
              stateId2Flist.set(FldStateId.getInst(),4);
              
              FList categoryIdFlist = new FList();
              categoryIdFlist.set(FldCategoryId.getInst(),1);
              FList deviceNum1Flist = new FList();
              deviceNum1Flist.set(FldDeviceNum.getInst(),categoryIdFlist);
              
              FList premittedFlist = new FList();
              premittedFlist.set(FldPermitted.getInst(),CityCode);
              FList deviceNum2Flist = new FList();
              deviceNum2Flist.set(FldDeviceNum.getInst(),premittedFlist);
              
              SparseArray argsArray = new SparseArray();
              argsArray.add(1,deviceIDFlist1);
              argsArray.add(2,nameFlist);
              argsArray.add(3,deviceIDFlist2);
              argsArray.add(4,startNumberFlist);
              argsArray.add(5,deviceIDFlist3);
              argsArray.add(6,endNumberFlist);
              argsArray.add(7,poidFlist);
              argsArray.add(8,stateId1Flist);
              argsArray.add(9,stateId2Flist);
              argsArray.add(10,deviceNum1Flist);
              argsArray.add(11,deviceNum2Flist);
              
              inFlist.set(FldArgs.getInst(), argsArray);
              inFlist.dump();
              
              FList flResult = ctx.opcode(7, inFlist);
             flResult.dump();
              
              if(flResult.hasField(FldResults.getInst())==false) {
                 System.out.println("***********NONO NO NON ON Numbers are there");
                 return null;
                  } else {
                      SparseArray results = flResult.get(FldResults.getInst());
                      if(results == null) {
                         return null;
                      }
                      Enumeration enumVals = results.getValueEnumerator();
                      resList = new ArrayList();
                      do
                      {
                          if(!enumVals.hasMoreElements())
                              break;
                          FList r1 = (FList)enumVals.nextElement();
                          //System.out.println(r1.toString());
                          resList.add(r1);
                      } while(true);
                  }
             for(int i=0;i<resList.size();i++){
                  FList flTemp = (FList)resList.get(i);
                 strBuf.append("<VOIPNUMBER>");
                 strBuf.append("<VOIPPOID>"+flTemp.get(FldPoid.getInst())+"</VOIPPOID>");
                 strBuf.append("<VOIPNUM>"+flTemp.get(FldDeviceId.getInst())+"</VOIPNUM>");
                 strBuf.append("<VOIPVANITY>"+flTemp.get(FldDeviceNum.getInst()).get(FldVanity.getInst())+"</VOIPVANITY>");
                  strBuf.append("</VOIPNUMBER>");
             }
              //System.out.println("String Buffer..."+strBuf);
              rXML = rXML + "<VOIPNUMBERS>";
              rXML = rXML +strBuf;
              rXML = rXML + "</VOIPNUMBERS>";
             //System.out.println("Final XML Format..."+rXML);
          } 
          }catch (Exception e) {
              
          } finally {
              try {
                  ConnectionPool.getInstance().releaseConnection(ctx);
              } catch (EBufException ebuf) {
                  DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
              }
          }
          return rXML;
      }
//      /**
//     *  This method returns the the CPE Device details in the form of XML.
//     */
//    public String getSpecialNumbersXML() throws RemoteException {
//        String rXML = "";
//        if (mCPE != null) {
//            rXML = rXML + "<VOIPNUMBERS>";
//            rXML = rXML + "<VOIPNUMBER>" + mCPE.getSpecialNumbers() + "</VOIPNUMBER>";
//            rXML = rXML + "</VOIPNUMBERS>";
//        }
//        System.out.println("getSpecialNumbersXML.... "+rXML);
//        return rXML;
//    }
    
      private String wanaddr;
    //  private CPEObject mCPE;
}
      //Ended  

