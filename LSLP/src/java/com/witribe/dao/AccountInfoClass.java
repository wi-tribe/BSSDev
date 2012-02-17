/*
 * AccountDetails.java
 *
 * Created on September 29, 2009, 4:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.portal.bas.comp.PIAComponentCollectionBean;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.PortalContext;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldAccessCode1;
import com.portal.pcm.fields.FldAccountNo;
import com.portal.pcm.fields.FldAccountObj;
import com.portal.pcm.fields.FldArgs;
import com.portal.pcm.fields.FldCurrentBal;
import com.portal.pcm.fields.FldFlags;
import com.portal.pcm.fields.FldLastBillT;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldPoidId;
import com.portal.pcm.fields.FldResults;
import com.portal.pcm.fields.FldTemplate;
import com.portal.pcm.fields.FldTotals;
import com.witribe.util.LogUtil;
import com.witribe.vo.LeadAccntVO;
import customfields.WtbFldBilledAmount;
import customfields.WtbFldLastPaidAmount;
import customfields.WtbFldLastPaidT;
import customfields.WtbFldOverdueAmount;
import customfields.WtbFldTotalBalance;
import customfields.WtbFldUnbilledAmount;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Properties;;

/**
 *
 * @author SH68481
 */
public class AccountInfoClass extends PIAComponentCollectionBean{
    
    /** Creates a new instance of AccountDetails */
    public AccountInfoClass() throws RemoteException{
    }
    public LeadAccntVO loaddata(String searchBy, String searchByValue) throws Exception{
        LeadAccntVO obj = new LeadAccntVO();
        String acctno =null;
        String accesscode=null;
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("Infranet.properties"));
            PortalContext ctx = new PortalContext(properties);
            ctx.connect();
            FList ProfileInpflist=new FList();
            FList Result=new FList();
            ProfileInpflist.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(), -1L, "/search"));
            ProfileInpflist.set(FldFlags.getInst(),0);
            ProfileInpflist.set(FldTemplate.getInst(), "select X from /account where F1 = V1 ");
            ProfileInpflist.setElement(FldResults.getInst(),0);
            FList args = new FList();
            if(searchBy.equalsIgnoreCase("CNIC_ID")) {
                accesscode =searchByValue;
                args.set(FldAccessCode1.getInst(),accesscode);
            } else {
                //acctno = "0.0.0.1-"+searchByValue;
                acctno = searchByValue;
                args.set(FldPoid.getInst(),new Poid(ctx.getCurrentDB(), Integer.parseInt(acctno), "/account"));
            }
            
            ProfileInpflist.setElement(FldArgs.getInst(), 1 ,args);
            ProfileInpflist.dump();
            Result = ctx.opcode(7, ProfileInpflist);
            if(Result.hasField(FldResults.getInst())) {
            SparseArray results= Result.get(FldResults.getInst());
            FList profilelist = results.elementAt(0);
            Poid macctpoid = profilelist.get(FldPoid.getInst());
            FList volumeflist = new FList();
            volumeflist.set(FldPoid.getInst(),macctpoid);
            volumeflist.dump();
            FList volumeconsumed = ctx.opcode(10001, volumeflist);
            obj.setCurrentBal(volumeconsumed.get(FldCurrentBal.getInst()));
            obj.setBillAmount(volumeconsumed.get(WtbFldBilledAmount.getInst()));
            obj.setUnBillAmount(volumeconsumed.get(WtbFldUnbilledAmount.getInst()));
            obj.setLastPaidAmt(volumeconsumed.get(WtbFldLastPaidAmount.getInst()));
            obj.setLastPaidDate(volumeconsumed.get(WtbFldLastPaidT.getInst()));
            obj.setLastBillDate(volumeconsumed.get(FldLastBillT.getInst()));
            obj.setOverDueAmount(volumeconsumed.get(WtbFldOverdueAmount.getInst()));
            obj.setVolumeConsumedInPercent(volumeconsumed.get(FldTotals.getInst()));
            obj.setTotalBalance(volumeconsumed.get(WtbFldTotalBalance.getInst()));
             } else {
                obj = null;
             }
        } catch(EBufException ex) {
            throw ex;
        } catch (Exception e) {
            throw e;
        }
        //releaseContext(ctx);
        
        //System.out.println(obj);
        return(obj);
    }
    
}