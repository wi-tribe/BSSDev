/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package witribe.brm;
import com.portal.pcm.*;
import com.portal.pcm.fields.*;
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.InputStream;
import java.math.BigDecimal;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
//import utils.MyLog4j;
//import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import utils.AppLogger;
//import utils.SessionDetails;
/**
 *
 * @author PKSaadB
 */
public class BRMOpcodes extends PortalManager 
{
    protected PortalContext ctx = null;
    private static final Object lock = new Object();
    
    public BRMOpcodes()
    {
        super();
    }
    
    // added by malik
    MyLog4j loggs = new MyLog4j();
    
    public BRMOpcodes(String username, String password, String server, String port) {
        super(username, password, server, port);
    }

    @Override
    protected void finalize() throws EBufException, Throwable
    {
        // Close PCM connection
        this.ctx.close(true);
        super.finalize();
    }
    
    public void TEST_LOOPBACK(String TransID)
    {
        // Get the input flist
        FList inflist = new FList();
        // add data to the flist
        inflist.set(FldPoid.getInst(), new Poid(1));
        inflist.set(FldFirstName.getInst(), "Mickey");
        inflist.set(FldLastName.getInst(), "Mouse");
        openContext();
        FList outflist =execute(PortalOp.TEST_LOOPBACK, inflist);
        
        loggs.smsLogger("TransID: "+TransID,", FUNCTION:TEST_LOOPBACK(), OPcode:TEST_LOOPBACK, input flist:\r\n",inflist.toString());
        if (outflist != null)
        //loggs.log("FUNCTION:TEST_LOOPBACK(), OPcode:TEST_LOOPBACK output flist:\n ", outflist.toString());
        loggs.smsLogger("TransID: "+TransID,", FUNCTION:TEST_LOOPBACK(), OPcode:TEST_LOOPBACK ,OutPut flist:\r\n",outflist.toString());
        closeContext();
    }
      
    public FList BAL_GET_ACCT_BAL_GRP_AND_SVC(String strAcctPoid, String TransID )
    {
        //System.out.println("***************************** "+TransID);
        FList outflist =null;
        Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account "+strAcctPoid);
        FList inflist = new FList(); 
        inflist.set(FldPoid.getInst(), acctPoid);
        //inflist.dump();
        loggs.smsLogger("TransID: "+TransID,", FUNCTION:BAL_GET_ACCT_BAL_GRP_AND_SVC(), OPcode:BAL_GET_ACCT_BAL_GRP_AND_SVC, input flist:\r\n ",inflist.toString());
        openContext();
        outflist = execute(PortalOp.BAL_GET_ACCT_BAL_GRP_AND_SVC, inflist);
        //outflist.dump();
        if(outflist!=null)
        loggs.smsLogger("TransID: "+TransID,", FUNCTION:BAL_GET_ACCT_BAL_GRP_AND_SVC(), OPcode:BAL_GET_ACCT_BAL_GRP_AND_SVC, outflist:\r\n ",outflist.toString());
        closeContext();
        
        return outflist;
    }
    
    // malik added
    public FList GET_ACC_INFO(String cnic,String TransID )
    {
        FList input = new FList();
        FList output = new FList();
            input.set(FldPoid.getInst(), new Poid(1, 0, "/search"));
            input.set(FldFlags.getInst(), 512);
            input.set(FldTemplate.getInst(), "select X from /account where F1 = V1 ");
            SparseArray getPoid = new SparseArray();
            FList flistgetpoid = new FList();
            flistgetpoid.set(FldPoid.getInst(), new Poid(1, 0 ,"/account"));
            // 0 PIN_FLD_RESULTS  ARRAY [0] allocated 1, used 1
            // 1 PIN_FLD_POID     POID [0] 0.0.0.1 /account 0 0
            getPoid.add(flistgetpoid); // display total array for result set 
            input.set(FldResults.getInst(),getPoid);
            
            SparseArray args = new SparseArray(); 
            FList Access_flist = new FList();
            Access_flist.set(FldAccessCode1.getInst(), cnic);
            args.add(1,Access_flist); // 1 use for just display 1 element
            input.set(FldArgs.getInst(), args);
            loggs.smsLogger("TransID: "+TransID,", FUNCTION:GET_ACC_INFO(), Opcode:GET_ACC_INFO, input flist: ",input.toString());
            openContext();
            output = execute(PortalOp.SEARCH, input);
            loggs.smsLogger("TransID: "+TransID,", FUNCTION:GET_ACC_INFO() OPcode:GET_ACC_INFO, output flist: ",output.toString());
            closeContext();
        return output;
    }
    
    public FList AR_GET_BAL_SUMMARY(Poid acctPoid,String TransID )
    {
       //String TransID=null;
        FList outflist =null;
        FList inflist = new FList(); 
        inflist.set(FldPoid.getInst(), acctPoid);
        inflist.set(FldIncludeChildren.getInst(),new Integer(1) );
        
        openContext();
        outflist = execute(PortalOp.AR_GET_BAL_SUMMARY, inflist);
        //outflist.dump();
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:AR_GET_BAL_SUMMARY(), OPcode:AR_GET_BAL_SUMMARY, input flist: ",inflist.toString());
        if (outflist != null)
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:AR_GET_BAL_SUMMARY(), OPcode:AR_GET_BAL_SUMMARY, output flist: ",outflist.toString());
        closeContext();
        
        return outflist;
    }
    
    public FList PCM_OP_CUST_POL_GET_SUBSCRIBED_PLANS(Poid acctPoid,String TransID)
    {
         FList outflist =null;
        FList inflist = new FList(); 
        inflist.set(FldPoid.getInst(), acctPoid);
        openContext();
        outflist = execute(PortalOp.CUST_POL_GET_SUBSCRIBED_PLANS, inflist);
        //System.out.println("____________");
        //outflist.dump();
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:PCM_OP_CUST_POL_GET_SUBSCRIBED_PLANS(), OPcode:CUST_POL_GET_SUBSCRIBED_PLANS, input flist is:\r\n ",inflist.toString());
        if (outflist != null)
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:PCM_OP_CUST_POL_GET_SUBSCRIBED_PLANS(), OPcode:CUST_POL_GET_SUBSCRIBED_PLANS, opuput flist is:\r\n ", outflist.toString());
        closeContext();
        
        return outflist;
    }
    
    public FList BAL_GET_BALANCES(Poid acctPoid,String TransID )
    {
        FList outflist =null;
        FList inflist = new FList(); 
        inflist.set(FldPoid.getInst(), acctPoid);
        
        FList balflist = new FList(); 
        balflist.set(FldCreditLimit.getInst(),new BigDecimal(0) );
        balflist.set(FldCurrentBal.getInst(),new BigDecimal(0) );

        inflist.setElement(FldBalances.getInst(),0,balflist);
        openContext();
        outflist = execute(PortalOp.BAL_GET_BALANCES, inflist);
        //outflist.dump();
        try{
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:BAL_GET_BALANCES(), OPcode:BAL_GET_BALANCES, input flist:\r\n ", inflist.toString());
        if (outflist != null)
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:BAL_GET_BALANCES(), OPcode:BAL_GET_BALANCES, output flist:\r\n ", outflist.toString());
        }
        catch(Exception ex)
        {
            loggs.smsLogger("","Exception:BAL_GET_BALANCES() ", ex.toString());
        }
        closeContext();
        
        return outflist;
    }
    
    public FList PYMT_TOPUP(String TransID,Poid acctPoid, Poid billinfoPoid, Poid balanceGroupPoid, String voucher_id, String voucher_pin,int flags)
    {
        FList vouchersElem = new FList();
        vouchersElem.set(FldBillinfoObj.getInst(), billinfoPoid);
        vouchersElem.set(FldVoucherPin.getInst(), voucher_pin);
        vouchersElem.set(FldDeviceId.getInst(), voucher_id);
        if (balanceGroupPoid != null)
            vouchersElem.set(FldBalGrpObj.getInst(), balanceGroupPoid);
     // SparseArray vouchersArray = new SparseArray();
     // vouchersArray.add(vouchersElem);
      
      FList inheritedInfoSubstruct = new FList();
      inheritedInfoSubstruct.setElement(FldVouchersInfo.getInst(), 0, vouchersElem);
      FList voucherFlist = new FList();
      voucherFlist.set(FldInheritedInfo.getInst(), inheritedInfoSubstruct);

      voucherFlist.set(FldPoid.getInst(), acctPoid);
      voucherFlist.set(FldProgramName.getInst(), "websms");

        Date currentDate = new Date();
        voucherFlist.set(FldStartT.getInst(), currentDate);
        voucherFlist.set(FldEndT.getInst(), currentDate);
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:PYMT_TOPUP(), OPcode: PYMT_TOPUP, input flist:\r\n", voucherFlist.toString());
       // voucherFlist.dump();
        FList result=null;
     //   try {
        synchronized(lock)
        {
            openContext();
            if(flags==0)
            {
                result = execute(PortalOp.PYMT_TOPUP, voucherFlist);

            }else
            {
                result = execute(PortalOp.PYMT_TOPUP, flags, voucherFlist);

            }
            closeContext();
        }
        if(result != null)
        {
            loggs.smsLogger("TransID:"+TransID,", FUNCTION:PYMT_TOPUP(), OPcode: PYMT_TOPUP, output flist:\r\n ",result.toString());
        }
        else
        {
//            String str = Arrays.toString(ebuf.getStackTrace());
//            String[] myArray = str.split("\\,");
//            for(String count : myArray)
//            {
//                loggs.log("Exception:PYMT_TOPUP(), Line:229 ",count);
//            }
            loggs.smsLogger("","Exception:PYMT_TOPUP(), Line:229 ",Arrays.toString(ebuf.getStackTrace()));
        }
        
        return result;
    }
    
    public FList toArray()
    {
        return new FList();
    }

    public String searchDeviceID(String voucherPin,String TransID) 
    {    
        try {
            FList input = new FList();
            input.set(FldPoid.getInst(), new Poid(1, 0, "/search"));
            input.set(FldFlags.getInst(), 256);
            input.set(FldTemplate.getInst(), "select x from /device/voucher where F1 = V1 ");
            input.set(FldResults.getInst());
            SparseArray args = new SparseArray();
            /* Device sub structure */
            FList device_sub = new FList();
            /* substructure elements */
            FList device_elem = new FList();
            device_elem.set(FldVoucherPin.getInst(), voucherPin);
            /* put elements in substructure */
            device_sub.set(FldDeviceVoucher.getInst(), device_elem);
            /* add subtructure to search flist */
            args.add(1, device_sub);
            input.set(FldArgs.getInst(), args);
            loggs.smsLogger("TransID:"+TransID,", FUNCTION:searchDeviceID(), OPcode: searchDeviceID, input flist:\r\n ",input.toString());
            openContext();
            //input.dump();
            /* Execute the status (active) opcode */
            FList output = execute(PortalOp.SEARCH, input);
           // output.dump();
            closeContext();
            if (output != null)
            loggs.smsLogger("TransID:"+TransID,", FUNCTION:searchDeviceID(), OPcode: searchDeviceID, output flist:\r\n ", output.toString());
            //output.dump();
            SparseArray results = output.get(FldResults.getInst());
            FList deviceFlist = results.getAnyElement();
            return deviceFlist.get(FldDeviceId.getInst());
        } catch(Exception e)
        {
            loggs.smsLogger("","Exception:searchDeviceID(), ", e.toString());
        }
        return "";
    }
    
    public ArrayList getVoucherErrorMessage(EBufException e)
    {
    String errMsg = "voucher.error.general";
    ArrayList arrayErrMsg = new ArrayList();
    if (e == null) {
      arrayErrMsg.add("-1");
      arrayErrMsg.add("voucher.error.general");
      return arrayErrMsg;
    }
    arrayErrMsg.add(e.getError());
    switch (e.getError()) {
    case 3:
      if (Field.getNameString(e.getField()).equals("PIN_FLD_POID")) {
         
        arrayErrMsg.add("voucher.error.idpinmismatch.error");
      }
      else if (Field.getNameString(e.getField()).equals("PIN_FLD_EXTENDED_INFO")) {
        arrayErrMsg.add("voucher.error.used");
      }
      else {
        arrayErrMsg.add("voucher.error.notfound");
      }

      break;
    case 46:
      if (Field.getNameString(e.getField()).equals("PIN_FLD_STATE_ID")) {
        arrayErrMsg.add("voucher.error.invalid.state");
      } else {
        if (!Field.getNameString(e.getField()).equals("PIN_FLD_EXPIRATION_T")) break;
        arrayErrMsg.add("voucher.error.expired");
      }break;
    case 84:
      if (!Field.getNameString(e.getField()).equals("PIN_FLD_BRAND_OBJ")) break;
      arrayErrMsg.add("voucher.error.brand");
      break;
    case 4:
      if (Field.getNameString(e.getField()).equals("PIN_FLD_VOUCHER_PIN")) {
        arrayErrMsg.add("voucher.error.idpinmismatch.error");
      } else {
        if (!Field.getNameString(e.getField()).equals("PIN_FLD_STATE_ID")) break;
        arrayErrMsg.add("voucher.error.invalid.state");
      }break;
    default:
      arrayErrMsg.add("voucher.error.general");
    }

    return arrayErrMsg;
  }

   public FList SUBSCRIPTION_GET_PURCHASED_OFFERINGS(FList inflist,String TransID)
   {
       FList result = null;
       openContext(); 
       result = execute(PortalOp.SUBSCRIPTION_GET_PURCHASED_OFFERINGS, inflist);
       if (result != null)
       loggs.smsLogger("TransID:"+TransID,", FUNCTION:SUBSCRIPTION_GET_PURCHASED_OFFERINGS(), OPcode:SUBSCRIPTION_GET_PURCHASED_OFFERINGS, output flist:\r\n",result.toString());
       closeContext();
       return result;
   }

   public FList SUBSCRIPTION_PurchaseDeal(String TransID,Poid acctPoid, Poid svcPoid, Poid dealPoid,boolean applyCreditLimit )// throws IOException
   {
        FList dealFlist  = READ_OBJ(dealPoid,TransID);
        /*
        if(applyCreditLimit)
        {
            //----------START TEMP CODE FOR PRICE-----------------
            Properties businessProps = new Properties();
            InputStream localInputStream = getClass().getResourceAsStream("/business.properties");
            try{
                businessProps.load(new BufferedInputStream(localInputStream));
            } catch (IOException ex) {
//                Logger.getLogger(BRMWebService.class.getName()).log(Level.SEVERE, null, ex);
                logg.smsLogger("","Exception:SUBSCRIPTION_PurchaseDeal() Line:311", ex.toString());
                return null;
            }
            //----------END  TEMP CODE FOR PRICE-----------------
            SessionDetails sessionDetails = new SessionDetails();
            String acctNo = ""+acctPoid.getId();
            String creditLimitCheck = null;
           // System.out.println();
            loggs.smsLogger("TransID:"+TransID,", SUBSCRIPTION_PurchaseDeal(), Credit Limit Checks,\r\n","Deal Price: deal."+dealPoid.getId()+".price = " + businessProps.getProperty("deal."+dealPoid.getId()+".price"));
            Float price = new Float(businessProps.getProperty("deal."+dealPoid.getId()+".price"));
            //Float price = new Float(businessProps.getProperty("deal."+svcPoid.getId()+".price"));
            
            try {
                creditLimitCheck = sessionDetails.getCreditDetails(acctNo, price);
                if(creditLimitCheck!="")
                {
                    return null;
                }
            } catch (SQLException ex) {
                logg.smsLogger("","Exception:SUBSCRIPTION_PurchaseDeal Line:323", ex.toString());
                
            } catch (Exception ex) {
                
                logg.smsLogger("","Exception:SUBSCRIPTION_PurchaseDeal() Line:327", ex.toString());
            }
           
        }*/
        
        FList PurchaseDealOpt =null;
        FList ReadDealObj = new FList();
        SparseArray ReadDealArray = new SparseArray();
        FList ReadDealFl = new FList();
        FList PurcahseDeal = new FList();
        /*************Preparing Main FList For Purchase Deal****************/
        try
        { 
            String permitted = dealFlist.get(FldPermitted.getInst());
            dealFlist.remove(FldCreatedT.getInst());
            dealFlist.remove(FldModT.getInst());
           // dealFlist.remove(FldEndT.getInst());
            dealFlist.remove(FldPermitted.getInst());
            dealFlist.remove(FldWriteAccess.getInst());
            dealFlist.remove(FldReadAccess.getInst());
            dealFlist.remove(FldOpCorrelationId.getInst());
            dealFlist.remove(FldAccountObj.getInst());
            ReadDealArray = dealFlist.get(FldProducts.getInst());
            ReadDealFl = ReadDealArray.elementAt(0);
            ReadDealFl.remove(FldUsageEndDetails.getInst());
            ReadDealFl.set(FldUsageEndDetails.getInst(), 0);
            ReadDealFl.remove(FldCycleEndDetails.getInst());
            ReadDealFl.set(FldCycleEndDetails.getInst(), 0);
            ReadDealFl.remove(FldPurchaseEndDetails.getInst());
            ReadDealFl.set(FldPurchaseEndDetails.getInst(), 0);
            PurcahseDeal.set(FldDealInfo.getInst(), dealFlist);
            PurcahseDeal.set(FldPoid.getInst(), acctPoid);
            PurcahseDeal.set(FldProgramName.getInst(), "websms");
           // System.out.println("*********** enter in SUB_purchase fun in try block b4 if condition ");
            if(svcPoid!=null && !permitted.equalsIgnoreCase("/account"))
            {
                PurcahseDeal.set(FldServiceObj.getInst(), svcPoid);
            }
            
            //System.out.println("purchase input Flist........");
            
            loggs.smsLogger("TransID:"+TransID,", SUBSCRIPTION_PurchaseDeal(), opcode: SUBSCRIPTION_PURCHASE_DEAL, InputFlist\r\n",PurcahseDeal.toString());
            openContext();
            PurchaseDealOpt = execute(PortalOp.SUBSCRIPTION_PURCHASE_DEAL, PurcahseDeal);
            closeContext();
            if (PurchaseDealOpt != null)
            loggs.smsLogger("TransID:"+TransID,", SUBSCRIPTION_PurchaseDeal(), opcode: SUBSCRIPTION_PURCHASE_DEAL, outputFlist\r\n",PurchaseDealOpt.toString());
            
             
        }catch(EBufException ebuf)
        {
  loggs.smsLogger("","Eception:SUBSCRIPTION_PurchaseDeal ",ebuf.toString());

        }catch(Exception ex)
        {
            loggs.smsLogger("","Exception:SUBSCRIPTION_PurchaseDeal ", ex.toString());
            
            
        }
            
      // FList resultFList = null;
       
       return PurchaseDealOpt;    
   }
   
   public FList READ_OBJ(Poid objPoid,String TransID)
   {
        FList resultFList = null;
        FList inputFList = new FList();
        inputFList.set(FldPoid.getInst(), objPoid);
        openContext();
        resultFList = execute(PortalOp.READ_OBJ, inputFList);
         loggs.smsLogger("TransID:"+TransID,", FUNCTION:READ_OBJ(), OPcode:READ_OBJ, input flist:\r\n",inputFList.toString());
         if (resultFList != null)
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:READ_OBJ(), OPcode:READ_OBJ, output flist:\r\n ",resultFList.toString());
        closeContext();
        
        return resultFList;
   }
   
    public FList SEARCH(FList searchFlist,String TransID)
   {
        FList resultFList = null;
        openContext();
        resultFList = execute(PortalOp.SEARCH, searchFlist);
        if(resultFList != null)
        loggs.smsLogger("TransID:"+TransID,", FUNCTION:SEARCH(), OPcode:SEARCH, output flist:\r\n",resultFList.toString());
        closeContext();
        return resultFList;
   }
}
