/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package witribe.brm;
import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import java.util.ArrayList;
import java.util.Date;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import utils.MyLog4j;
/**
 *
 * @author PKSaadB
 */
public class BRMOpcodes 
{
    protected PortalContext ctx = null;
    MyLog4j loggs = new MyLog4j();
    public BRMOpcodes() throws EBufException 
    {
          this.ctx = new PortalContext();
          this.ctx.connect();
    }
    
    @Override
    protected void finalize() throws EBufException, Throwable
    {
        // Close PCM connection
        this.ctx.close(true);
        super.finalize();
    }
    
    public void TEST_LOOPBACK()
    {
        try {
                // Get the input flist
                FList inflist = new FList();
                // add data to the flist
                inflist.set(FldPoid.getInst(), new Poid(1));
                inflist.set(FldFirstName.getInst(), "Mickey");
                inflist.set(FldLastName.getInst(), "Mouse");

                // Print input flist
//                System.out.println("Input flist");
//                System.out.println(inflist);
                loggs.log1("FUNCTION:TEST_LOOPBACK(), OPcode:TEST_LOOPBACK input flist:\n ", inflist.toString());

                // Create PCM connext necessary for connecting to the server. 
                // A valid Infranet.properties file should be in the classpath.
                // See context examples for additional information.
                PortalContext ctx = new PortalContext();
                ctx.connect();

                // Call the opcode
                FList outflist = ctx.opcode(PortalOp.TEST_LOOPBACK, inflist);

                // Close PCM connection
                ctx.close(true);

                // Print the return flist
//                System.out.println("Output flist:");
//                outflist.dump(); // this is an alternate way to print out an flist
                loggs.log1("FUNCTION:TEST_LOOPBACK(), OPcode:TEST_LOOPBACK input flist:\n ", outflist.toString());
                //System.out.println("Success!");

        } catch (EBufException ebuf) {
                System.out.println("You weren't able to call the PCM_OP_TEST_LOOPBACK opcode.");
                System.out.println(ebuf);
                loggs.log("Function:TEST_LOOPBACK()" , ebuf.toString());
        }
    }
      
    public FList BAL_GET_ACCT_BAL_GRP_AND_SVC(String strAcctPoid )
    {
        FList outflist =null;
        Poid acctPoid = (Poid) Poid.valueOf("0.0.0.1 /account "+strAcctPoid);
        FList inflist = new FList();  
        inflist.set(FldPoid.getInst(), acctPoid);
        loggs.log1("FUNCTION:BAL_GET_ACCT_BAL_GRP_AND_SVC(), OPcode:BAL_GET_ACCT_BAL_GRP_AND_SVC Input Flist:\n ", inflist.toString());
        try {
                outflist = this.ctx.opcode(PortalOp.BAL_GET_ACCT_BAL_GRP_AND_SVC, inflist);
                loggs.log1("FUNCTION:BAL_GET_ACCT_BAL_GRP_AND_SVC(), OPcode:BAL_GET_ACCT_BAL_GRP_AND_SVC Output Flist:\n ", outflist.toString());        
        } catch (EBufException ebuf) {
//                System.out.println(ebuf);
//                Logger.getLogger(BRMOpcodes.class.getName()).log(Level.SEVERE, null, ebuf);
            loggs.log("Function:BAL_GET_ACCT_BAL_GRP_AND_SVC", ebuf.toString());
        }
        return outflist;
    }
    
    public FList PYMT_TOPUP(Poid acctPoid, Poid billinfoPoid, Poid balanceGroupPoid, String voucher_id, String voucher_pin,int flags) throws EBufException
    {
        FList vouchersElem = new FList();
        vouchersElem.set(FldBillinfoObj.getInst(), billinfoPoid);
        vouchersElem.set(FldVoucherPin.getInst(), voucher_pin);
        vouchersElem.set(FldDeviceId.getInst(), voucher_id);
        if (balanceGroupPoid != null)
            vouchersElem.set(FldBalGrpObj.getInst(), balanceGroupPoid);
      SparseArray vouchersArray = new SparseArray();
      vouchersArray.add(vouchersElem);
      
      FList inheritedInfoSubstruct = new FList();
      inheritedInfoSubstruct.setElement(FldVouchersInfo.getInst(), 0, vouchersElem);
      FList voucherFlist = new FList();
      voucherFlist.set(FldInheritedInfo.getInst(), inheritedInfoSubstruct);

      voucherFlist.set(FldPoid.getInst(), acctPoid);
      voucherFlist.set(FldProgramName.getInst(), "WTB Web APIs");

        Date currentDate = new Date();
        voucherFlist.set(FldStartT.getInst(), currentDate);
        voucherFlist.set(FldEndT.getInst(), currentDate);
        loggs.log1("FUNCTION:PYMT_TOPUP(), OPcode:PYMT_TOPUP Voucher Flist:\n ", voucherFlist.toString()); 
       // voucherFlist.dump();
        FList result=null;
     //   try {
        if(flags==0)
        {
            result = this.ctx.opcode(PortalOp.PYMT_TOPUP, voucherFlist);
            loggs.log1("FUNCTION:PYMT_TOPUP(), flags=0, ", result.toString());
        }else
        {
            result = this.ctx.opcode(PortalOp.PYMT_TOPUP, flags, voucherFlist);
            loggs.log1("FUNCTION:PYMT_TOPUP(), flags != 0, ", result.toString());
        }
       
      //  } catch (EBufException ex) {
       //     Logger.getLogger(BRMOpcodes.class.getName()).log(Level.SEVERE, null, ex);
       // }
        //result.dump();
        return result;
    }
    public FList toArray()
    {
        return new FList();
    }
    
    public FList execute(int opcode ,FList inFlist)
    {
        FList outFlist =null;
        try {
          outFlist = this.ctx.opcode(PortalOp.TEST_LOOPBACK, inFlist);
          loggs.log1("FUNCTION:execute(), OPcode:execute Output Flist:\n ", outFlist.toString());
          
         } catch (EBufException ebuf) {
                //System.out.println("You weren't able to call the opcode.");
                //System.out.println(ebuf);
                loggs.log1("You weren't able to call the opcode.", ebuf.toString());
        }
        return outFlist;
    }

    public String searchDeviceID(String voucherPin) 
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
            loggs.log1("FUNCTION:searchDeviceID(), OPcode:searchDeviceID Input Flist:\n ", input.toString());
            /* Execute the status (active) opcode */
            FList output = this.ctx.opcode(PortalOp.SEARCH, input);
            loggs.log1("FUNCTION:searchDeviceID(), OPcode:searchDeviceID Output Flist:\n ", output.toString());
            //output.dump();
            SparseArray results = output.get(FldResults.getInst());
            FList deviceFlist = results.getAnyElement();
            return deviceFlist.get(FldDeviceId.getInst());
        } catch(Exception e)
        {
            //System.out.println(e);
            loggs.log("Function:searchDeviceID()", e.toString());
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

}
