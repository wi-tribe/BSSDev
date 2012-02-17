/**
 * Project  		: WiTribe
 * Module   		: SelfCare
 * Author               : Ramesh Udatha
 * Date and Version     : 6 Apr 2009 , 1.0
 * Last modified by     : Ramesh Udatha
 * Copyright            : SCSL
 */

package Wtb.MyAccount;

/**
 *
 * This class contains set of utility functions useful for Account creation / maintenance in Selfcare applicaiton.
 */

import com.portal.bas.*;
import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;
import customfields.*;
import java.text.SimpleDateFormat;
import com.portal.bas.comp.PIAComponentCollection;
import com.portal.bas.comp.PIAComponentCollectionBean;
import com.portal.pfc.infranet.connpool.*;


public class MyAcctUtility extends PIAComponentCollectionBean  {
    
    /** Creates a new instance of MyAcctUtility */
    public MyAcctUtility() throws RemoteException {
        mInvoices = new ArrayList();
        mStartTime = null;
        mEndTime = null;
        macctPOID = null;
        mbillInfoPOID = null;
        mIncludechildren = 0;
        mCNIC = "";
        isSingle = false;
        mProducts = new ArrayList();
        mDiscounts = new ArrayList();
    }
    
    /**
     *  Set the CNIC value.
     *  @sCNIC - set the CNIC/PASSPORT No Value
     */
    public void setCNIC(String sCNIC) {
        mCNIC = sCNIC;
    }
    
    /**
     *  Returns the CNIC/PASSPORT value set for the object.
     */
    public String getCNIC() {
        return mCNIC;
    }
    
    public void setBillInfoPoid(Poid obillpoid) {
        mbillInfoPOID = obillpoid;
    }
    
    public Poid getBillInfoPoid() {
        return mbillInfoPOID;
    }
    
    public Poid getAcctPoid() {
        return macctPOID;
    }
    public void setAcctPoid(Poid oPOID) {
        macctPOID = oPOID;
    }
    
    public void setStartTime(Date startDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        mStartTime = cal.getTime();
    }
    
    public void setEndTime(Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        mEndTime = cal.getTime();
    }
    
    /**
     *  following method verifies the CNIC value with already exisitng accounts in brm and returns the status of the CNIC.
     *  possibel cases can be either DUPLICATE / BLOCK Listed/ New Customer.
     */
    public String checkDuplicate() throws RemoteException {
        String sMsg;
        
        if(checkBlockList()) {
            sMsg = "BLOCKED.";
            return sMsg;
        } else if(! performParentCheck()) {
            sMsg= "DUPLICATE.";
            return sMsg;
        } else {
            sMsg= "OK.";
            return sMsg;
        }
    }
    
    /**
     *  following method called by performParentCheck() method.
     *  this function verifies with the existing /account storable class for the CNIC mentioned.
     */
    public FList getDuplicateAccountSearch() throws RemoteException {
        PortalContext ctx = null;
        
        FList FListIn = new FList();
        FList FListOut = new FList();
        Poid searchPoid = new Poid(1, -1, "/search");
        SparseArray resArray = new SparseArray();
        SparseArray argsArray = new SparseArray();
        FList resFlist = new FList();
        FList argsList = new FList();
        Poid rsPoid = new Poid(1, -1, "/search");
        resFlist.set(FldPoid.getInst(), rsPoid);
        resFlist.set(FldGroupObj.getInst(), rsPoid);
        resArray.add(resFlist);
        argsList.set(FldAccessCode1.getInst(),mCNIC);
        argsArray.add(1, argsList);
        String searchTmpt = "select X from /account where upper( F1 ) = upper( V1 ) ";
        FListIn.set(FldPoid.getInst(), searchPoid);
        FListIn.set(FldFlags.getInst(), 256);
        FListIn.set(FldTemplate.getInst(), searchTmpt);
        FListIn.set(FldResults.getInst(), resArray);
        FListIn.set(FldArgs.getInst(), argsArray);
        
        try {
            ctx = getLocalContext();
            FListOut = ctx.opcode(PortalOp.SEARCH, FListIn);
        } catch (Exception e) {
            e.getMessage();
        }
        finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
}
        return FListOut;
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
     *  following method called by checkDuplicate() method.
     *  this function verifies with the existing /account storable class for the CNIC mentioned.
     *  this function verifies either this account is the parent account or not.
     */
    public boolean performParentCheck() {
        boolean blRetValue = false;
        PortalContext ctx = null;
        FList iFList = new FList();
        try {
            ctx = getLocalContext();
            iFList.set(FldPoid.getInst(), new Poid(1, -1, "/search"));
            iFList.set(FldFlags.getInst(), 256 );
            iFList.set(FldTemplate.getInst(), "select X from /account where length( F1 ) = length( '/0.0.0.1:' || substr ( F2 , 9 ) || '/' ) and F3 in ( 1 , 2 , 3 , 4 , 5 , 7, 8 , 9 ) and F4 in ( 10100 , 10102  ) and F5 like V5 ");
            FList rFList = new FList();
            rFList.set(FldPoid.getInst(), new Poid(1, -1, "/account"));
            iFList.setElement(FldResults.getInst(),0, rFList);
            FList arFList = new FList();
            arFList.set(FldLineage.getInst(), "");
            iFList.setElement(FldArgs.getInst(), 1, arFList);
            arFList = new FList();
            arFList.set(FldAccountNo.getInst(), "");
            iFList.setElement(FldArgs.getInst(), 2, arFList);
            arFList = new FList();
            arFList.set(FldBusinessType.getInst(), Integer.parseInt("1"));
            iFList.setElement(FldArgs.getInst(), 3, arFList);
            arFList = new FList();
            arFList.set(FldStatus.getInst(), Integer.parseInt("1"));
            iFList.setElement(FldArgs.getInst(), 4, arFList);
            arFList = new FList();
            arFList.set(FldAccessCode1.getInst(), mCNIC+"%");
            iFList.setElement(FldArgs.getInst(), 5, arFList);
            iFList.dump();
            FList oFList = ctx.opcode(7, iFList);
            oFList.dump();
            if(oFList.hasField(FldResults.getInst())==false) {
                System.out.println("***********No records Available");
                return true;
            } else {
                SparseArray results = oFList.get(FldResults.getInst());
                if(results == null) { 
                    return true;
                }

                int NumberOfAccounts = results.size();
                System.out.println("Number of Account found against CNIC: "+mCNIC+" "+results.size()+" where maxVal is: "+miMaxCNIC);
                if(NumberOfAccounts >= miMaxCNIC) {
                     System.out.println("Return Status :: false");
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
              try {
                  ConnectionPool.getInstance().releaseConnection(ctx);
              } catch (EBufException ebuf) {
                  DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
              }
          }
    }
    
    /**
     *  This method checks if the is there any Child account available with the same CNIC
     *
     */
    public boolean performChildCheck() throws RemoteException {
        
        isSingle=false;
        boolean isChild=false;
        PortalContext ctx = null;
        try {
            ctx = getLocalContext();
            FList fcOut = getDuplicateAccountSearch();
            if (fcOut.hasField(FldResults.getInst())) {
                isSingle = true;
                SparseArray childArray = fcOut.get(FldResults.getInst());
                for (int i = 0; i < childArray.size(); i++) {
                    isSingle = true;
                    FList FListIn = new FList();
                    FList FListOut = new FList();
                    Poid searchPoid = new Poid(1, -1, "/search");
                    SparseArray resArray = new SparseArray();
                    SparseArray argsArray = new SparseArray();
                    SparseArray membArray = new SparseArray();
                    SparseArray outArray = new SparseArray();
                    FList resFlist = new FList();
                    FList argsList = new FList();
                    FList membList = new FList();
                    resFlist.set(FldPoid.getInst(), searchPoid);
                    resFlist.set(FldParent.getInst(), searchPoid);
                    resArray.add(resFlist);
                    String searchTmpt = "select X from /group/billing where upper( F1 ) = upper( V1 ) ";
                    FListIn.set(FldPoid.getInst(), searchPoid);
                    FListIn.set(FldFlags.getInst(), 256);
                    FListIn.set(FldTemplate.getInst(), searchTmpt);
                    FListIn.set(FldResults.getInst(), resArray);
                    FList res = childArray.elementAt(i);
                    Poid actPoid = res.get(FldPoid.getInst());
                    membList.set(FldObject.getInst(), actPoid);
                    membArray.add(membList);
                    argsList.set(FldMembers.getInst(), membArray);
                    argsArray.add(i + 1, argsList);
                    FListIn.set(FldArgs.getInst(), argsArray);
                    FListOut = ctx.opcode(PortalOp.SEARCH, FListIn);
                    if (FListOut.hasField(FldResults.getInst())) {
                        isChild = true;
                    }
                }
            }
        } catch (Exception che) {
            che.getMessage();
        }
        finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
}
        return isChild;
    }
    
    /**
     *  This method checks if the account is blocklisted
     *  This will check in /profile/wtb_blacklisted_accounts storable class for the verificcation
     *  if the the account is block listed then the user cannot create an account for this.
     *
     */
    public boolean checkBlockList() throws RemoteException {
        PortalContext ctx=null;
        boolean isBlocked = false;
        FList FListIn = new FList();
        FList FListOut = new FList();
        Poid searchPoid = new Poid(1, -1, "/search");
        SparseArray resArray = new SparseArray();
        SparseArray argsArray = new SparseArray();
        FList resFlist = new FList();
        FList argsList = new FList();
        FList blockList = new FList();
        Poid rsPoid = new Poid(1, -1, "/search");
        resFlist.set(FldPoid.getInst(), rsPoid);
        resFlist.set(FldAccountObj.getInst(), rsPoid);
        resArray.add(resFlist);
        blockList.set(WtbFldCustomerid.getInst().getInst(),mCNIC);
        argsList.set(WtbFldBlklistCustinfo.getInst(), blockList);
        argsArray.add(1, argsList);
        String searchTmpt = "select X from /profile/wtb_blacklisted_accounts where upper( F1 ) = upper( V1 ) ";
        FListIn.set(FldPoid.getInst(), searchPoid);
        FListIn.set(FldFlags.getInst(), 256);
        FListIn.set(FldTemplate.getInst(), searchTmpt);
        FListIn.set(FldResults.getInst(), resArray);
        FListIn.set(FldArgs.getInst(), argsArray);
        try {
            ctx = getLocalContext();
            FListOut = ctx.opcode(PortalOp.SEARCH, FListIn);
            
            if (FListOut.hasField(FldResults.getInst())) {
                isBlocked = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
}
        return isBlocked;
    }
    
    /**
     *  By default PCM library will not return the service exprity date for the each product/service
     *  So, this funtion was written so that customer can see the service expiry date in My Services dashboard.
     *
     */
    public void getRegdServices() throws RemoteException {
        List resList = new ArrayList();
        FList inList = new FList();
        PortalContext ctx = null;
        try {
            ctx = getLocalContext();
            inList.set(FldPoid.getInst(),macctPOID);
            inList.set(FldFlags.getInst(),1);
            FList out = ctx.opcode(81,inList);
            if(!out.hasField(FldServices.getInst()) && !out.hasField(FldDeals.getInst()) && !out.hasField(FldProducts.getInst()))
                throw new RemoteException("error.products");
            
            if(out.hasField(FldServices.getInst())) {
                SparseArray sp = out.get(FldServices.getInst());
                if(sp == null)
                    throw new RemoteException("error.products");
                Enumeration results = sp.getValueEnumerator();
                do  {
                    if(!results.hasMoreElements())
                        break;
                    FList services = (FList)results.nextElement();
                    Poid poid = services.get(FldPoid.getInst());
                    if(poid == null) {
                        if(DefaultLog.doLog(8))
                            DefaultLog.log(8, "No poid");
                        throw new RemoteException("error.products");
                    }
                    String service = poid.toString();
                    int start = service.indexOf('/');
                    int end = service.indexOf(' ', start);
                    if(services.hasField(FldDeals.getInst()))
                        processDealsFlist(services, service.substring(start, end));
                    
                } while(true);
            }
            if(out.hasField(FldDeals.getInst()))
                processDealsFlist(out, null);
            if(out.hasField(FldProducts.getInst()))
                processProductsFlist(out, null, null);
            if(out.hasField(FldDiscounts.getInst()))
                processDiscountsFlist(out, null, null);
        } catch(Exception e) {
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
     *  This method is called from getRegdServices function to process the list of deals associated to the account
     *  This method returns the list deals associatedt o the account in the form of ArrayList.
     */private void processDealsFlist(FList out, String serviceStr)
     throws RemoteException {
         try {
             SparseArray dealsArray = out.get(FldDeals.getInst());
             if(dealsArray == null)
                 throw new RemoteException("error.products");
             Enumeration enum1 = dealsArray.getValueEnumerator();
             do
             {
                 if(!enum1.hasMoreElements())
                     break;
                 FList deals = (FList)enum1.nextElement();
                 String deal = deals.get(FldName.getInst());
                 if(deals.hasField(FldProducts.getInst()))
                     processProductsFlist(deals, serviceStr, deal);
                 if(deals.hasField(FldDiscounts.getInst()))
                     processDiscountsFlist(deals, serviceStr, deal);
             } while(true);
         } catch(EBufException e) {
             if(DefaultLog.doLog(2))
                 DefaultLog.log(this, 2, e);
         }
     }
     
     /**
      *  This method is called from getRegdServices function to process the list of discounted products associated to the account
      *  This method returns the list discounted products associatedt o the account in the form of ArrayList.
      */private void processDiscountsFlist(FList out, String serviceStr, String deal)
      throws RemoteException {
          try {
              SparseArray discountsArray = out.get(FldDiscounts.getInst());
              if(discountsArray == null)
                  throw new RemoteException("error.NoDiscounts");
              for(Enumeration enum2 = discountsArray.getValueEnumerator(); enum2.hasMoreElements(); ) {
                  FList discounts = (FList)enum2.nextElement();
                  if(discounts == null)
                      throw new RemoteException("error.NoDiscounts");
                  mDiscounts.add(discounts);
              }
              
          } catch(EBufException e) {
              if(DefaultLog.doLog(2))
                  DefaultLog.log(this, 2, e);
          }
      }
      
      /**
       *  This method is called from getRegdServices function to process the list of products associated to the account
       *  This method returns the list products associatedt o the account in the form of ArrayList.
       */
      private void processProductsFlist(FList out, String serviceStr, String deal)
      throws RemoteException {
          try {
              SparseArray productsArray = out.get(FldProducts.getInst());
              if(productsArray == null)
                  throw new RemoteException("error.products");
              for(Enumeration enum2 = productsArray.getValueEnumerator(); enum2.hasMoreElements(); ) {
                  FList products = (FList)enum2.nextElement();
                  products.set(FldServiceId.getInst(),serviceStr);
                  products.set(FldStatusMsg.getInst(),getProductStatusAsString((products.get(FldStatus.getInst())).intValue()));
                  mProducts.add(products);
              }
              
          } catch(EBufException e) {
              if(DefaultLog.doLog(2))
                  DefaultLog.log(this, 2, e);
          }
      }
      
      /**
       *  Folloing funciton is called from getRegdServices to get the product status based ont he code passed to it.
       */
      public String getProductStatusAsString(int sValue) {
          String pStatus = "";
          switch(sValue) {
              case 0: // '\0'
                  pStatus = "product.status.notset";
                  break;
                  
              case 1: // '\001'
                  pStatus = "product.status.active";
                  break;
                  
              case 2: // '\002'
                  pStatus = "product.status.inactive";
                  break;
                  
              case 3: // '\003'
                  pStatus = "product.status.cancelled";
                  break;
                  
              default:
                  pStatus = "product.status.unknown";
                  break;
          }
          return pStatus;
      }
      
       
    
      
      
     
     
      
       public List getVoIPNumbers(String sCity, int ServiceType) throws RemoteException {
        List resList = null; //new ArrayList();
        FList inList = new FList();
        String CityCode = "";
        PortalContext ctx=null;
        try {
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
                CityCode = CityCode + "_" + "Prepaid";
            } else {
                CityCode = CityCode + "_" + "Postpaid";
            }
            if(CityCode != "") {
                FList flInput = new FList();
                FList flResults = null;
                flInput.set(FldPoid.getInst(), new Poid(ctx.getCurrentDB(), -1L, "/search"));  
                flInput.set(FldFlags.getInst(), 256); 
                flInput.set(FldTemplate.getInst(), "select X from /device/num where ( F1 = V1 or F3 = V3 ) and F2 = V2 order by F4 asc ");   
                flInput.setElement(FldResults.getInst(),0,flResults);
                FList args = new FList();
                args.set (FldStateId.getInst(),1);
                flInput.setElement (FldArgs.getInst (), 1 , args);
                args = new FList();
                args.set (FldStateId.getInst(),4);
                flInput.setElement (FldArgs.getInst (), 3 , args);
                args = new FList();
                FList flDeviceNum = new FList();
                flDeviceNum.set(FldPermitted.getInst(), CityCode);
                args.set(FldDeviceNum.getInst(),flDeviceNum);
                flInput.setElement (FldArgs.getInst (), 2 , args);
                args = new FList();
                flDeviceNum.set(FldCategoryId.getInst(), 0);
                args.set(FldDeviceNum.getInst(),flDeviceNum);
                flInput.setElement (FldArgs.getInst (), 5 , args);
                FList flDeviceId = new FList();
                flDeviceId.set(FldDeviceId.getInst(),"");
                flInput.setElement(FldArgs.getInst (), 4 , flDeviceId);
                flInput.dump();
                FList flResult = ctx.opcode (7, flInput);
                //flResult.dump();
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
            }
            
        } catch (Exception e) {
            
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
}
          return resList;
      }
      
      public FList getAffinityNewAccountOrderdetails(String businessType) {
          FList output = new FList();
          PortalContext ctx=null;
          try {
              ctx=getLocalContext();
              FList input = new FList();
              
              } catch(Exception e) {
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
          return output;
      } 
       
      /**
       *  Once new account is created in BRM one provision request will be created in /profile/provrequest table.
       *  This method is called when the new account is created fromt he Lead. and we need to return the newly created account no with the provision order
       *  created for the new account.
       */
      public FList getNewAccountOrderdetails(String strLogin) throws RemoteException {
          FList output = new FList();
          PortalContext ctx=null;
          try {
              ctx=getLocalContext();
              FList input = new FList();
              FList outflst = new FList();
              input.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(), -1L, "/search"));
              input.set(FldFlags.getInst(), 256);
              input.set(FldTemplate.getInst(), "select X from /service/ip where F1 = V1 ");
              SparseArray resArray = new SparseArray();
              FList resFlist = new FList();
              
              SparseArray argsArray = new SparseArray();
              FList argsList1 = new FList();
              argsList1.set(FldLogin.getInst(),strLogin);
              argsArray.add(argsList1);
              resArray.add(resFlist);
              
              input.setElement(FldArgs.getInst(),1,argsList1);
              input.setElement(FldResults.getInst(),0,resFlist);
              outflst = ctx.opcode(7, input);
              if(!outflst.hasField(FldResults.getInst())) {
                  if(DefaultLog.doLog(8))
                      DefaultLog.log(8, "No Account Found with the user id:");
                  throw new RemoteException("error.pay");
              }
              SparseArray resultsArray = outflst.get(FldResults.getInst());
              if(resultsArray == null)
                  throw new RemoteException("error.pay");
              Enumeration enumVals = resultsArray.getValueEnumerator();
              do
              {
                  if(!enumVals.hasMoreElements())
                      break;
                  FList r1 = (FList)enumVals.nextElement();
                  Poid poid = r1.get(FldAccountObj.getInst());
                  if(poid == null) {
                      if(DefaultLog.doLog(8))
                          DefaultLog.log(8, "No poid");
                      throw new RemoteException("error.pay");
                  }
                  output.set(FldAccountObj.getInst(), poid);
                  setAcctPoid(output.get(FldAccountObj.getInst()));
                  break;
              } while(true);
              
              String sOrderId = getNewAccountOrderid();
              if ( sOrderId == null ) {
                  sOrderId = "";
              }
              output.set(FldOrderId.getInst(), sOrderId);
              DefaultLog.log ("Output Returned(OrderId) : " + output.toString());
              output.dump ();
          } catch(Exception e) {
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
          return output;
      }
      public FList getAffinityNewAccountOrderdetails( String email, String FirstName, String address ) throws RemoteException  {
        FList output = new FList();
          PortalContext ctx=null;
          try {
              ctx=getLocalContext();
              Date dt = new Date();
              System.out.println(dt.toString());
              dt.setMinutes((dt.getMinutes() - 2));
              System.out.println(dt.toString());
              Poid searchPoid = new Poid(1,-1,"/search");
              String searchTmpt = "select X from /account where F1 = V1 and F2 > V2  and F3 = V3 and F4 = V4 and F5 = V5 and F6 = V6 ";
              FList inputfl = new FList();
              inputfl.set(FldFlags.getInst(),0);
              FList inputargs = new FList();
              inputargs.set(FldAccessCode1.getInst(), mCNIC);
              inputfl.setElement(FldArgs.getInst(),1,inputargs);
              inputargs = new FList();
              inputargs.set(FldCreatedT.getInst(), dt);
              inputfl.setElement(FldArgs.getInst(), 2, inputargs);
              inputargs = new FList();
              inputargs.set(FldBusinessType.getInst(),4 );
              inputfl.setElement(FldArgs.getInst(),3,inputargs);
              inputargs = new FList();
              FList flNameInfo = new FList();
              
              flNameInfo.set(FldAddress.getInst(),address);
              inputargs.setElement(FldNameinfo.getInst(),1,flNameInfo);
              inputfl.setElement(FldArgs.getInst(), 4, inputargs);
              inputargs = new FList();
              flNameInfo = new FList();
              flNameInfo.set(FldFirstName.getInst(),FirstName);
              inputargs.setElement(FldNameinfo.getInst(),1,flNameInfo);
              inputfl.setElement(FldArgs.getInst(), 6, inputargs);
              inputargs = new FList();
              flNameInfo = new FList();
              flNameInfo.set(FldEmailAddr.getInst(),email);
              inputargs.setElement(FldNameinfo.getInst(),1,flNameInfo);
              inputfl.setElement(FldArgs.getInst(), 5, inputargs);
              inputargs = new FList();
              flNameInfo = new FList();
              flNameInfo.set(FldFirstName.getInst(),FirstName);
              inputargs.setElement(FldNameinfo.getInst(),1,flNameInfo);
              inputfl.setElement(FldArgs.getInst(), 6, inputargs);
              inputfl.set(FldPoid.getInst(), searchPoid);
              inputfl.set(FldTemplate.getInst(), searchTmpt);
              SparseArray resArray = new SparseArray();
              FList resFlist = new FList();
              resArray.add(resFlist);
              inputfl.setElement(FldResults.getInst(),0,resFlist);
              output = ctx.opcode(7, inputfl);
              output.dump();
              if(!output.hasField(FldResults.getInst())) {
                  if(DefaultLog.doLog(8))
                      DefaultLog.log(8, "No Account Found with the user id:");
                  throw new RemoteException("error.pay");
              }
              SparseArray resultsArray = output.get(FldResults.getInst());
              if(resultsArray == null)
                  throw new RemoteException("error.pay");
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
                      throw new RemoteException("error.pay");
                  }
                  output.set(FldAccountObj.getInst(), poid);
                  setAcctPoid(output.get(FldAccountObj.getInst()));
                  break;
              } while(true);
              
              String sOrderId = getNewAccountOrderid();
              if ( sOrderId == null ) {
                  sOrderId = "";
              }
              output.set(FldOrderId.getInst(), sOrderId);
              DefaultLog.log ("Output Returned(OrderId) : " + output.toString());
              output.dump ();
              } catch(Exception e) {
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
          return output;
    }
      
      /**
       *  following method is called from getNewAccountOrderDetails function.
       *  based on the account poid, this function will return the order id generated in /profile/provrequest table for the newly created account.
       */
      private String getNewAccountOrderid() throws RemoteException  {
          String sOrderId = null;
          PortalContext ctx=null;
          
          try {
              ctx = getLocalContext();
              if (ctx!= null) {
                  Poid lpoid = null;
                  FList flInput = new FList();
                  FList flResultParam = new FList();
                  FList flOrderId = new FList();
                  FList flOutput = new FList();
                  FList flArgs1 = new FList();
                  FList flArgs2 = new FList();
                  FList flAcctId = new FList();
                  String searchTmpt = "select X from /provrequest where F1 = V1 and F2 = V2 ";
                  Poid searchPoid = new Poid(1,-1,"/search");
                  
                  flOrderId.set(FldOrderId.getInst(),"");
                  flResultParam.setElement(FldOrders.getInst(),0, flOrderId);
                  SparseArray arArguments = new SparseArray();
                  flInput.set(FldPoid.getInst(), searchPoid);
                  flInput.set(FldFlags.getInst(),256);
                  flInput.set(FldTemplate.getInst(), searchTmpt);
                  flInput.setElement(FldResults.getInst(),0,flResultParam);
                  flArgs1.set(FldAccountObj.getInst(),macctPOID);
                  arArguments.add(1,flArgs1);
                  flAcctId.set(WtbFldCnicPassport.getInst(), mCNIC);
                  flArgs2.setElement(FldOrders.getInst(),0,flAcctId);
                  arArguments.add(2,flArgs2);
                  flInput.set(FldArgs.getInst(),arArguments);
                  flOutput = ctx.opcode(PortalOp.SEARCH,flInput);
                  if(flOutput.hasField(FldResults.getInst())) {
                      SparseArray arResults = flOutput.get(FldResults.getInst());
                      sOrderId = arResults.getAnyElement().get(FldOrders.getInst()).getAnyElement().get(FldOrderId.getInst());
                      return( sOrderId);
                      
                  } else {
                      return null;
                  }
                  
              } else {
              }
          } catch(Exception e) {
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
          return sOrderId;
      }
      
      
      /**
       *  for the prepaid accounts(in new account scenario), customer need not enter the billing address,
       *  so by default we need to set the service address as the billing address for the account.
       */
      public PModelHandle setPrepaidData(PModelHandle newacct) throws RemoteException {
          PCachedContext ctx=null;
          
          FList newAccountFL;
          try {
              ctx = getContext();
              FList inFlist = (FList)ctx.lookupModel(newacct);
              FList payInfoFlist;
              if(inFlist.hasField(FldPayinfo.getInst())) {
                  payInfoFlist = inFlist.getElement(FldPayinfo.getInst(), 1);
              } else {
                  payInfoFlist = new FList();
                  inFlist.setElement(FldPayinfo.getInst(), 1, payInfoFlist);
              }
              int billType = 10000;
              payInfoFlist.set(FldPayType.getInst(), billType);
              payInfoFlist.set(FldName.getInst(),"Prepaid");
              Poid payinfoDDPoid = new Poid(ctx.getCurrentDB(), -1L, "/payinfo/prepaid");
              payInfoFlist.set(FldPoid.getInst(), payinfoDDPoid);
              FList inheritedInfoFlist;
              if(payInfoFlist.hasField(FldInheritedInfo.getInst())) {
                  inheritedInfoFlist = payInfoFlist.get(FldInheritedInfo.getInst());
              } else {
                  inheritedInfoFlist = new FList();
                  payInfoFlist.set(FldInheritedInfo.getInst(), inheritedInfoFlist);
              }
              
              FList ddebitInfoFlist;
              if(inheritedInfoFlist.hasField(FldInvInfo.getInst()))
                  inheritedInfoFlist.remove(FldInvInfo.getInst());
              if(inheritedInfoFlist.hasField(FldDdInfo.getInst()))
                  inheritedInfoFlist.remove(FldDdInfo.getInst());
              if(inheritedInfoFlist.hasField(FldCcInfo.getInst()))
                  inheritedInfoFlist.remove(FldCcInfo.getInst());
              
              if(inheritedInfoFlist.hasField(WtbFldPpdInfo.getInst())) {
                  ddebitInfoFlist = inheritedInfoFlist.getElement(WtbFldPpdInfo.getInst(), 0);
              } else {
                  ddebitInfoFlist = new FList();
                  inheritedInfoFlist.setElement(WtbFldPpdInfo.getInst(), 0, ddebitInfoFlist);
              }
              if(!ddebitInfoFlist.hasField(FldName.getInst()))
                  ddebitInfoFlist.set(FldName.getInst(), "");
              if(!ddebitInfoFlist.hasField(FldAddress.getInst()))
                  ddebitInfoFlist.set(FldAddress.getInst(), "");
              if(!ddebitInfoFlist.hasField(FldCity.getInst()))
                  ddebitInfoFlist.set(FldCity.getInst(), "");
              if(!ddebitInfoFlist.hasField(FldState.getInst()))
                  ddebitInfoFlist.set(FldState.getInst(), "");
              if(!ddebitInfoFlist.hasField(FldZip.getInst()))
                  ddebitInfoFlist.set(FldZip.getInst(), "");
              if(!ddebitInfoFlist.hasField(FldCountry.getInst()))
                  ddebitInfoFlist.set(FldCountry.getInst(), "");
              
          } catch(Exception e) {
              if(DefaultLog.doLog(2))
                  DefaultLog.log(this, 2, e);
          }
          finally {
            releaseContext(ctx);
}
          return newacct;
      }
      
      /**
       *  By default while creating the account, profile id's were not generated so Profile Objects cannot be created for the new account.
       *  To overcome this functionality following method adds the profile object POID fields to the new account input flist.
       */
      public PModelHandle updateProfileIds(PModelHandle newacct) throws  RemoteException {
          PCachedContext ctx=null;
          FList newAccountFL;
          try {
              ctx = getContext();
              newAccountFL = (FList)ctx.lookupModel(newacct);
              FldProfileObj fldProfileObj = FldProfileObj.getInst();
              FldProfiles fldProfilesA = FldProfiles.getInst();
              Poid profilepoid = null;
              if(newAccountFL.hasField(fldProfilesA)) {
                  SparseArray sArray = newAccountFL.get(fldProfilesA);
                  if(sArray != null){
                      Enumeration results = sArray.getValueEnumerator();
                      while(results.hasMoreElements()){
                          FList tmp,tmp1;
                          tmp = (FList)results.nextElement();
                          tmp1 = (FList) tmp.get(FldInheritedInfo.getInst());
                          if(tmp1.hasField(FldCustomerCareInfo.getInst())){
                              profilepoid = new Poid(ctx.getCurrentDB(), -1L, (new StringBuilder()).append("/profile/").append("customer_care").toString());
                          }
                          if(tmp1.hasField(WtbFldCustinfo.getInst())){
                              profilepoid = new Poid(ctx.getCurrentDB(), -1L, (new StringBuilder()).append("/profile/").append("wtb_customer_details").toString());
                          }
                          if(tmp1.hasField(WtbFldTaxExemptions.getInst())){
                              profilepoid = new Poid(ctx.getCurrentDB(), -1L, (new StringBuilder()).append("/profile/").append("tax_exemption").toString());
                          }
                          if(tmp1.hasField(WtbFldContractDetails.getInst())){
                              profilepoid = new Poid(ctx.getCurrentDB(), -1L, (new StringBuilder()).append("/profile/").append("contract_details").toString());
                          }
                          tmp.set(fldProfileObj, profilepoid);
                      }
                  }
              }
          } catch(Exception e) {
              if(DefaultLog.doLog(2))
                  DefaultLog.log(this, 2, e);
          }
          finally {
            releaseContext(ctx);
}
          return newacct;
      }
      
      
      /**
       * returns the Purchased products assocated to the account
       */
      public List getProducts() throws RemoteException {
          return mProducts;
      }
      
      /**
       * returns the discounted products associated to the account.
       */
      public List getDiscounts() throws RemoteException {
          return mDiscounts;
      }
      
      //Override method
      public void update(int i, Object obj) {
          
      }
      
      /**to get account password
0 PIN_FLD_POID                      POID [0] 0.0.0.1 /search 0 0
0 PIN_FLD_FLAGS                      INT [0] 256
0 PIN_FLD_OP_CORRELATION_ID          STR [0] "1:HDL-PCS469800:UnknownProgramName:0:AWT-EventQueue-0:7:1242910014:0"
0 PIN_FLD_TEMPLATE                   STR [0] "select X from /service/ip where F1 = V1 "
0 PIN_FLD_RESULTS                  ARRAY [*] allocated 1, used 1
1     PIN_FLD_PASSWD                 STR [0] "XXXX"
0 PIN_FLD_ARGS                     ARRAY [1] allocated 1, used 1
1     PIN_FLD_ACCOUNT_OBJ           POID [0] 0.0.0.1 /account 414501 0
       **/
      public String getAcctPwd()throws RemoteException{
          PortalContext ctx=null;
          String pwd = null;
          try {
              ctx = getLocalContext();
              FList input = new FList();
              FList outflst = new FList();
              input.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(), -1L, "/search"));
              input.set(FldFlags.getInst(), 256);
              input.set(FldTemplate.getInst(), "select X from /service/ip where F1 = V1 ");
              SparseArray resArray = new SparseArray();
              FList resFlist = new FList();
              
              SparseArray argsArray = new SparseArray();
              FList args = new FList();
              args.set(FldAccountObj.getInst(), macctPOID);
              argsArray.add(1,args);
              input.set(FldArgs.getInst(),argsArray);
              //input.set(FldTemplate.getInst(), searchTmpt);
              input.setElement(FldResults.getInst(),-1);
              if(DefaultLog.doLog(8))
                  DefaultLog.log(8,input.toString());
              outflst = ctx.opcode(PortalOp.SEARCH,input);
              if(outflst.hasField(FldResults.getInst())) {
                      SparseArray arResults = outflst.get(FldResults.getInst());
                      pwd = arResults.getAnyElement().get(FldPasswd.getInst());
                      return( pwd);
              
          } 
          } catch(Exception e) {
              if(DefaultLog.doLog(2))
                  DefaultLog.log(this, 2, e);
          }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(ctx);
            } catch (EBufException ebuf) {
                DefaultLog.log("Exception while Releaseing the Connection: " + ebuf.toString());
            }
}
        return pwd;
      }
      
      
                  
      
      //Override method
      public Object getSelectionDataFor(String dataItem, int index) {
          return null;
      }
      public void setProperties(Properties props) {
	mProperties = props;
        }
      public void setMaxCNIC (int iVal) {
          miMaxCNIC = iVal;
      }
      private Poid macctPOID;
      
      private Properties mProperties;
      private int miMaxCNIC;
      private int mIncludechildren;
      private Poid mbillInfoPOID;
      private Date mStartTime;
      private Date mEndTime;
      private List mInvoices;
      private List mProducts;
      private List mDiscounts;
      private String mCNIC;
      private boolean isSingle;
      
}
