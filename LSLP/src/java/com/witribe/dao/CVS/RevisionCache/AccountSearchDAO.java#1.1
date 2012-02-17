/*
 * AccountSearchDAO.java
 *
 * Created on October 7, 2010, 2:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.dao;

import com.witribe.util.DBUtil;
import com.witribe.util.BRMUtil;
import com.witribe.util.BRRMUtility;
//import DBUtils.GlobalDefinitions;
import com.witribe.account.actionform.AccountSearchform;


import com.portal.bas.*;
import com.portal.pcm.*;
import com.portal.pcm.fields.*;
import com.portal.bas.PCachedContext;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.pcm.Buffer;
import com.portal.pcm.DefaultLog;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Field;
import com.portal.pcm.Poid;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.*;
import customfields.*;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import com.witribe.vo.Address;
import com.witribe.vo.PaymentBean;
import com.witribe.vo.PlanJavaBean;
import com.witribe.vo.InventoryAccountBean;
import com.witribe.vo.AccountDetails;
import com.witribe.vo.InvoiceRecord;
import com.witribe.vo.AccountSearchJavaBean;
import com.witribe.vo.phoneBean;
import com.witribe.vo.BillingAddress;
import com.witribe.vo.RegService;
import com.witribe.vo.ReceiptInfoBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.*;
import java.math.BigDecimal;
//import org.apache.log4j.Logger;

/**
 *
 * @author LB85028
 */
public class AccountSearchDAO implements java.io.Serializable {
    
    //public static final Logger logger =Logger.getLogger("LSLPLog");// For log4j implementation
    //public final String searchAccount="SELECT a.poid_id0, a.account_no,A.ACCESS_CODE2, ACT.FIRST_NAME || ' ' || ACT.LAST_NAME as Name,ACT.EMAIL_ADDR, A.BAL_GRP_OBJ_ID0 FROM ACCOUNT_T A, SERVICE_T S, ACCOUNT_NAMEINFO_T ACT where A.POID_ID0 = S.ACCOUNT_OBJ_ID0 AND A.POID_ID0= ACT. OBJ_ID0  and S.POID_TYPE='/service/ip' and A.ACCOUNT_NO like ? AND S.LOGIN like ? ";
    public final String searchAccount="SELECT a.poid_id0, a.account_no,A.ACCESS_CODE2, ACT.FIRST_NAME || ' ' || ACT.LAST_NAME as Name,ACT.EMAIL_ADDR,A.BAL_GRP_OBJ_ID0 FROM ACCOUNT_T A, SERVICE_T S, ACCOUNT_NAMEINFO_T ACT ,ACCOUNT_PHONES_T AP where A.POID_ID0 = S.ACCOUNT_OBJ_ID0 AND   A.POID_ID0 = AP.OBJ_ID0 AND A.POID_ID0= ACT. OBJ_ID0 AND S.POID_TYPE='/service/ip' and A.ACCOUNT_NO like ? AND S.LOGIN like ? AND AP.TYPE=5 AND AP.PHONE like ? ";
    public final String getParticularAccount="SELECT rec_id,address,city,state,country,zip  FROM ACCOUNT_NAMEINFO_T where obj_id0=?";
    public final String getBillingAddress="select address,city,state,country,zip from payinfo_t P,payinfo_inv_t PV where P.poid_id0=PV.obj_id0 and P.Account_obj_id0 =?";
    public final String getBillInfoPoid="SELECT BILLINFO.POID_ID0 AS BILLINFO_POID FROM ACCOUNT_T A, BILLINFO_T BILLINFO WHERE A.POID_ID0=? and A.POID_ID0 = BILLINFO.ACCOUNT_OBJ_ID0";
    public final String getInventoryDetails="SELECT inv.device_id,inv.is_sell,inv.DEVICE_MAC, inv.MAIN_CPE, inv.OWN_CPE, inv.STATUS, inv.SERIAL_ITEM_NAME FROM WTB_PROFILE_INVENTORY_ITEMS_T inv , PROFILE_T pro WHERE pro.POID_ID0 = inv.OBJ_ID0 and pro.ACCOUNT_OBJ_ID0 = ?";
    public final String getAutoGenDocTypes="SELECT DISTINCT DOC_TYPE, TYPE_DESCR FROM WTB_CONFIG_AUTO_DOC_T ORDER BY TYPE_DESCR";
    public final String getPhoneNumbers="SELECT phone,type FROM ACCOUNT_PHONES_T WHERE OBJ_ID0 = ?";
    public final String getCurrency="SELECT CURRENCY FROM ACCOUNT_T WHERE POID_ID0=?";
    public final String getBillCount="select PARAM_VALUE from WTB_CONFIG_BUSINESS_PARAMS_T where PARAM_NAME='MAX_ADD_TO_BILL_VAS_PURCHASES' ";
    public final String getDefaultPassword="select PARAM_VALUE from WTB_CONFIG_BUSINESS_PARAMS_T where PARAM_NAME='EMAIL_DEFAULT_PASSWORD' ";
    Connection brmcon = null;
    PortalContext ctx = null;
     BRRMUtility  brmUtility=null;
    /** Creates a new instance of AccountSearchDAO */
    public AccountSearchDAO() {
    }
    public ArrayList getAccountDetail(HttpSession session,AccountSearchform accountSearchJavaBean) throws SQLException{
        ArrayList arrlist=new ArrayList();
        Connection con=null;
        String accntNo = null;
        String cnicId= null;
        String usrName= null;
        String accountNum = null;
        String cnic = null;
        String userName = null;
        AccountSearchform accountSearchJavaBean1 =null;
        
          System.out.println("In getAccountDetail DAO....");
          if(accountSearchJavaBean.getAccountNumber()!=null){
           accntNo=(String)accountSearchJavaBean.getAccountNumber();
          }
          if(accountSearchJavaBean.getNationalId()!= null){
           cnicId=(String)accountSearchJavaBean.getNationalId();
          }
          if(accountSearchJavaBean.getUserName()!=null){
           usrName=(String)accountSearchJavaBean.getUserName();
          }
          
         
            Pattern pattern = Pattern.compile("[*]");
            if(accntNo!=null){
              Matcher matcherNo = pattern.matcher(accntNo);
              accountNum = matcherNo.replaceAll("%");
            }
            if(cnicId!=null){
              Matcher matcherId = pattern.matcher(cnicId);
              cnic = matcherId.replaceAll("%");
           }
           if(usrName !=null){
             Matcher matcherUsrName = pattern.matcher(usrName);
             userName = matcherUsrName.replaceAll("%");
           }
                     
            System.out.println("accountNum...."+accountNum);
            System.out.println("cnic...."+cnic);
            System.out.println("userName...."+userName);
        try{
             con = DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(searchAccount);
            
            if(!accountSearchJavaBean.getAccountNumber().equals("")){
                pst.setString(1,accountNum);
            }else{
                pst.setString(1,"%");
            }
            if(!accountSearchJavaBean.getNationalId().equals("")){
                pst.setString(2,cnic);
            }else{
                pst.setString(2,"%");
            }
            if(!accountSearchJavaBean.getUserName().equals("")){
                pst.setString(3,userName);
            }else{
                pst.setString(3,"%");
            }
            
            ResultSet rs=pst.executeQuery();
            System.out.println("rs.."+rs);
            while(rs.next()){
                accountSearchJavaBean1=new AccountSearchform();
                accountSearchJavaBean1.setPoid(rs.getString(1));
                accountSearchJavaBean1.setAccountNumber(rs.getString(2));
                accountSearchJavaBean1.setNationalId(rs.getString(3));
                accountSearchJavaBean1.setName(rs.getString(4));
                accountSearchJavaBean1.setEmailId(rs.getString(5));
                accountSearchJavaBean1.setBalGrpObjId(rs.getInt(6));
                arrlist.add(accountSearchJavaBean1);
            }
             System.out.println("In getAccountDetail DAO...."+arrlist.size());
        }catch(Exception e){
           // logger.error(e);
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(con);
          }
        return arrlist;
    }
  
    public ArrayList getParticularAccountDetails(int accountNumber,PPooledConnectionClientServices pCS) throws SQLException{
        ArrayList arrlist=new ArrayList();
        String floorNo ="";
        String buildNo = "";
        String address1 = "";
        String streetName= "";
        String city ="";
        String zip ="";
        Connection con=null;
        try{
            con=DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getParticularAccount);
            PreparedStatement pst1=con.prepareStatement(getBillingAddress);
            pst.setInt(1,accountNumber);
            pst1.setInt(1,accountNumber);
            ResultSet rs=pst.executeQuery();
            ResultSet rs1=pst1.executeQuery();
            while(rs.next()){
                Address address=new Address();
                //address.setRecId(rs.getInt(1));
               
                if(rs.getString(2) != null && rs.getString(2) != "") {
                    address.setAddress(rs.getString(2));
                } else {
                    address.setAddress("");
                }
                if(rs.getString(3) != null && rs.getString(3) != "") {
                    address.setCity(rs.getString(3));
                } else {
                    address.setCity("");
                }
                if(rs.getString(4) != null && rs.getString(4) != "") {
                    address.setState(rs.getString(4));
                } else {
                    address.setState("");
                }
                if(rs.getString(5) != null && rs.getString(5) != "") {
                    address.setCountry(rs.getString(5));
                } else {
                    address.setCountry("");
                }
                if(rs.getString(6) != null && rs.getString(6) != "") {
                    address.setZipCode(rs.getString(6));
                } else {
                    address.setZipCode("");
                }
                
                arrlist.add(address);
            }
            
            while(rs1.next()){
                BillingAddress baddress=new BillingAddress();
               
               
                if(rs1.getString(1) != null && rs1.getString(1) != "") {
                    baddress.setBAddress(rs1.getString(1));
                } else {
                    baddress.setBAddress("");
                }
                if(rs1.getString(2) != null && rs1.getString(2) != "") {
                    baddress.setBCity(rs1.getString(2));
                } else {
                    baddress.setBCity("");
                }
                if(rs1.getString(3) != null && rs1.getString(3) != "") {
                    baddress.setBState(rs1.getString(3));
                } else {
                    baddress.setBState("");
                }
                if(rs1.getString(4) != null && rs1.getString(4) != "") {
                    baddress.setBCountry(rs1.getString(4));
                } else {
                    baddress.setBCountry("");
                }
                if(rs1.getString(5) != null && rs1.getString(5) != "") {
                    baddress.setBZipCode(rs1.getString(5));
                } else {
                    baddress.setBZipCode("");
                }
                arrlist.add(baddress);
            }
            System.out.println(" Array list size after appending Addresslist:"+arrlist.size());
        }catch(Exception e){
            
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(con);
        }
        return arrlist;
    }
     
   public ArrayList getInvoiceHistory(HttpServletRequest request,int accountNumber,PPooledConnectionClientServices pCS) throws RemoteException{
        ArrayList arrlist=new ArrayList();
        PCachedContext ctx=null;
        Connection con=null;
        double totalDue=0;
        int billInfoPoid=0;
        try{
            con=DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getBillInfoPoid);
            pst.setInt(1,accountNumber);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                billInfoPoid=rs.getInt(1);
            }
            System.out.println("billInfoPoid"+billInfoPoid);
            String billInfoObj=Integer.toString(billInfoPoid);
            System.out.println("pCS"+pCS);
            brmUtility= (BRRMUtility) pCS.createController("com.witribe.util.BRRMUtility");
            System.out.println("brmUtility"+brmUtility);
            ctx  =brmUtility.getLocalContext();
           /*   0 PIN_FLD_POID                      POID [0] 0.0.0.1 /search 0 0
                0 PIN_FLD_FLAGS                      INT [0] 0
                0 PIN_FLD_OP_CORRELATION_ID          STR [0] "1:hst-pcs-82377:UnknownProgramName:0:AWT-EventQueue-0:7:1285677512:0"
                0 PIN_FLD_TEMPLATE                   STR [0] "select X from /bill where F1 = V1 and F2 <> 0 and F3.type like V3 order by F4 desc "
                0 PIN_FLD_RESULTS                  ARRAY [*] allocated 0, used 0
                0 PIN_FLD_ARGS                     ARRAY [1] allocated 1, used 1
                1     PIN_FLD_ACCOUNT_OBJ           POID [0] 0.0.0.1 /account 124857 0
                0 PIN_FLD_ARGS                     ARRAY [2] allocated 1, used 1
                1     PIN_FLD_DUE                DECIMAL [0] 0
                0 PIN_FLD_ARGS                     ARRAY [3] allocated 1, used 1
                1     PIN_FLD_INVOICE_OBJ           POID [0] 0.0.0.1 /invoice -1 0
                0 PIN_FLD_ARGS                     ARRAY [4] allocated 1, used 1
                1     PIN_FLD_CREATED_T           TSTAMP [0] NULL
            
            */
            
             
             System.out.println("ctx"+ctx);
            FList inputFlist=new FList();
            inputFlist.set(FldPoid.getInst(),new Poid(1,0,"/search"));
            inputFlist.set(FldFlags.getInst(),0);
            inputFlist.set(FldTemplate.getInst(),"select X from /bill where F1 = V1 and F2 <> 0 and F3.type like V3 order by F4 desc ");
            SparseArray resultArray=new SparseArray();
            FList resultFlist=new FList();
            resultArray.add(resultFlist);
            inputFlist.set(FldResults.getInst(),resultArray);
            FList accountObjFlist=new FList();
            accountObjFlist.set(FldAccountObj.getInst(),new Poid(1,accountNumber,"/account"));
            FList dueFlist=new FList();
            dueFlist.set(FldDue.getInst(),BigDecimal.valueOf(0));
            FList invoiceObjFlist=new FList();
            invoiceObjFlist.set(FldInvoiceObj.getInst(),new Poid(1,-1,"/invoice"));
            FList createdFlist=new FList();
            createdFlist.set(FldCreatedT.getInst());
            SparseArray args=new SparseArray();
            args.add(1,accountObjFlist);
            args.add(2,dueFlist);
            args.add(3,invoiceObjFlist);
            args.add(4,createdFlist);
            inputFlist.set(FldArgs.getInst(),args);
            inputFlist.dump();
            
            FList outFlist=new FList();
            outFlist=ctx.opcode(7,inputFlist);
            outFlist.dump();
            NumberFormat formatter = new DecimalFormat("#0.000");
            if(outFlist!=null && outFlist.hasField(FldResults.getInst())){
                SparseArray outResultsArray=new SparseArray();
                outResultsArray=outFlist.get(FldResults.getInst());
                FList resultsFlist=new FList();
                for(int i=0;i<outResultsArray.size();i++){
                    resultsFlist=outResultsArray.elementAt(i);
                    Poid billpoid = resultsFlist.get(FldPoid.getInst());
                    String billNo=resultsFlist.get(FldBillNo.getInst());
                    double invoiceAmount=resultsFlist.get(FldCurrentTotal.getInst()).doubleValue();
                    double dueAmount=resultsFlist.get(FldDue.getInst()).doubleValue();
                    Date billDate=resultsFlist.get(FldCreatedT.getInst());
                    SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
                    String strCreatedT=sdf.format(billDate);
                   
                    InvoiceRecord invoiceRecord=new InvoiceRecord();
                    
                    System.out.println("billpoid"+billpoid.getId());
                    System.out.println("accountNumber"+accountNumber);
                    System.out.println("invoiceAmount"+invoiceAmount);
                    System.out.println("dueAmount"+dueAmount);
                    System.out.println("strCreatedT"+strCreatedT);
                       
                       
                   invoiceRecord.setIntBillNo(Integer.valueOf(Long.toString(billpoid.getId())));
                   invoiceRecord.setAccountNo(accountNumber);
                   invoiceRecord.setBillNo(billNo);
                   invoiceRecord.setInvoiceAmount(Double.parseDouble(formatter.format(invoiceAmount)));
                   invoiceRecord.setDueAmount(Double.parseDouble(formatter.format(dueAmount)));
                   invoiceRecord.setAccountNumber(Integer.toString(accountNumber));
//                   double due=invoiceRecord.getDueAmount();
//                  totalDue+=due;
                   invoiceRecord.setBillDate(strCreatedT);
                   arrlist.add(invoiceRecord);
                }
                
            }else{
                arrlist=null;
            }
            // Flist for getting total due amount
            FList inputFlist1=new FList();
            inputFlist1.set(FldPoid.getInst(),new Poid(1,billInfoPoid,"/billinfo"));
            inputFlist1.set(FldIncludeChildren.getInst(),1);
            inputFlist1.set(FldArBillinfoObj.getInst(),new Poid(1,billInfoPoid,"/billinfo"));
            System.out.println("input flist to get total due");
            inputFlist1.dump();
            
            FList outFlist1=new FList();
            outFlist1=ctx.opcode(1321,inputFlist1);
            System.out.println("out Flist after 1321 opcode");
            outFlist1.dump();
            if(outFlist1 !=null && outFlist1.hasField(FldPoid.getInst())){
                double pendingBillDue=outFlist1.get(FldPendingbillDue.getInst()).doubleValue();
                double openbillDue=outFlist1.get(FldOpenbillDue.getInst()).doubleValue();
                double unappliedAmount=outFlist1.get(FldUnappliedAmount.getInst()).doubleValue();
                totalDue=pendingBillDue+openbillDue+unappliedAmount;
            }
            System.out.println("total due:::"+formatter.format(totalDue));
             System.out.println("total due:::"+arrlist.size());
            request.setAttribute("totalDue",formatter.format(totalDue));
        }catch(Exception e){
           System.out.println("Exception::"+e);
            e.printStackTrace();
        }finally{
            brmUtility.getreleaseContext(ctx);
            brmUtility=null;
        }
        return arrlist;
    }
    
    public ArrayList getPayHistory(HttpSession session,int accountNumber,PPooledConnectionClientServices pCS) throws RemoteException, SQLException{
        ArrayList arrlist=new ArrayList();
        PCachedContext ctx=null;
        Connection con=null;
        int billInfoPoid=0;
        try{
            con=DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getBillInfoPoid);
            pst.setInt(1,accountNumber);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                billInfoPoid=rs.getInt("BILLINFO_POID");
            }
            String billInfoObj=Integer.toString(billInfoPoid);
            session.setAttribute("billInfoObj",billInfoObj);
            brmUtility= (BRRMUtility) pCS.createController("com.witribe.util.BRRMUtility");
            ctx  =brmUtility.getLocalContext();
            /*  0 PIN_FLD_POID                      POID [0] 0.0.0.1 /account 644160 0
                0 PIN_FLD_STATUS                    ENUM [0] 6
                0 PIN_FLD_INCLUDE_CHILDREN           INT [0] 1
                0 PIN_FLD_AMOUNT_INDICATOR           INT [0] 0
                0 PIN_FLD_POID_TYPE                  STR [0] "/item/payment,/item/payment/reversal"
                0 PIN_FLD_AR_BILLINFO_OBJ           POID [0] 0.0.0.1 /billinfo 645696 0
             */
            FList inputFlist=new FList();
            inputFlist.set(FldPoid.getInst(),new Poid(1,accountNumber,"/account"));
            inputFlist.set(FldStatus.getInst(),6);
            inputFlist.set(FldIncludeChildren.getInst(),1);
            inputFlist.set(FldAmountIndicator.getInst(),0);
            inputFlist.set(FldPoidType.getInst(),"/item/payment,/item/payment/reversal");
            inputFlist.set(FldArBillinfoObj.getInst(),new Poid(1,billInfoPoid,"/billinfo"));
            inputFlist.dump();
            
            FList outFlist=new FList();
            outFlist=ctx.opcode(4506,inputFlist);
            outFlist.dump();
            NumberFormat formatter = new DecimalFormat("#0.000");
            if(outFlist!=null && outFlist.hasField(FldResults.getInst())){
                SparseArray resultsArray=new SparseArray();
                resultsArray=outFlist.get(FldResults.getInst());
                FList resultFlist=new FList();
                for(int i=0;i<resultsArray.size();i++){
                    resultFlist=resultsArray.elementAt(i);
                    PaymentBean paymentBean=new PaymentBean();
                    
                    paymentBean.setPaymentId(resultFlist.get(FldItemNo.getInst()));
                    if(resultFlist.hasField(FldPayType.getInst())){
                        if(resultFlist.get(FldPayType.getInst()) == 10011){
                            paymentBean.setPaymentType("Cash");
                        }else if(resultFlist.get(FldPayType.getInst()) == 10002){
                            paymentBean.setPaymentType("Debit");
                        }else if(resultFlist.get(FldPayType.getInst()) == 10012){
                            paymentBean.setPaymentType("Cheque");
                        }else{
                            paymentBean.setPaymentType("Credit");
                        }
                        Date payDate=resultFlist.get(FldEffectiveT.getInst());
                        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
                        String strCreatedT=sdf.format(payDate);
                        paymentBean.setPaymentDate(strCreatedT);
                        String amount=resultFlist.get(FldItemTotal.getInst()).abs().toString();
                        amount = amount.replace("-","");
                        paymentBean.setPaidAmount(formatter.format(Double.parseDouble(amount)));
                      
                        arrlist.add(paymentBean);
                        
                    }
                }
            }else{
                arrlist=null;
            }
        }catch(Exception e){
          
            e.printStackTrace();
        }finally{
            brmUtility.getreleaseContext(ctx);
            brmUtility=null;
            DBUtil.closeConnection(con);
        }
        return arrlist;
    }
    
    public Hashtable getAccountStatus(int accountNumber) throws SQLException{
        Hashtable accountStatus= null;
        Connection con = null;
        System.out.println("accountNumber in getAccountStatus"+accountNumber);
        try{
            con = DBUtil.getBRMConnection();
            System.out.println("con"+con);
        //  PreparedStatement pst=con.prepareStatement("SELECT AN.FIRST_NAME || ' ' || AN.LAST_NAME as full_name, AN.EMAIL_ADDR || '\n' || SEC_EMAIL_ADDR, A.STATUS,a.BAL_GRP_OBJ_ID0,A.Account_No,(case when wi.service_type=1 then 'Postpaid' when wi.service_type=2 then 'Prepaid' else '' end) Service_Type FROM ACCOUNT_T A, ACCOUNT_NAMEINFO_T AN ,SERVICE_T S, WTB_SERVICE_WIMAX_T WI WHERE A.POID_ID0=AN.OBJ_ID0 AND AN.REC_ID=0 AND S.ACCOUNT_OBJ_ID0=A.POID_ID0 AND S.POID_TYPE='/service/wtb_wimax' and s.poid_id0 = wi.obj_id0 AND a.POID_ID0=?");
           PreparedStatement pst=con.prepareStatement("SELECT AN.FIRST_NAME || ' ' || AN.LAST_NAME as full_name,AN.EMAIL_ADDR, A.STATUS,A.BAL_GRP_OBJ_ID0,A.Account_No FROM ACCOUNT_T A, ACCOUNT_NAMEINFO_T AN WHERE A.POID_ID0=AN.OBJ_ID0 AND AN.REC_ID=1 AND a.POID_ID0=?");
           System.out.println("pst"+pst);
           pst.setInt(1,accountNumber);
            ResultSet rs=pst.executeQuery();
           System.out.println("rs"+rs.getFetchSize());
            while(rs.next()){
                 System.out.println("into rs rs");
                accountStatus= new Hashtable();
                System.out.println("rs"+rs.getInt(3));
                
                if(rs.getInt(3) == 10100){
                    accountStatus.put("ActStatus","Active");
                }else if(rs.getInt(3) == 10102){
                    accountStatus.put("ActStatus","Inactie");
                }else{
                    accountStatus.put("ActStatus","Closed");
                }
                System.out.println("rs.getString(1)"+rs.getString(1));
                accountStatus.put("ActName",rs.getString(1));
                accountStatus.put("ActEmail",rs.getString(2));
                accountStatus.put("ActBalGrp",rs.getString(4));
                accountStatus.put("ActNo",rs.getString(5));
               System.out.println("accountStatus in getAccountStatus"+accountStatus.size());
            }
        }catch(Exception e){
           System.out.println("exception"+e);
        }finally{
            DBUtil.closeConnection(con);
        }
        return accountStatus;
    }
    
 /*   public ArrayList getInventoryHistory(int accountNumber) throws SQLException{
        ArrayList arrlist=new ArrayList();
        Connection con=null;
        try{
            con =DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getInventoryDetails);
            pst.setInt(1,accountNumber);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                InventoryAccountBean inventoryAccountBean=new InventoryAccountBean();
                inventoryAccountBean.setDeviceId(rs.getString(1));
                if(rs.getInt(2)==0){
                    inventoryAccountBean.setIsSell(GlobalDefinitions.yesString);
                }else{
                    inventoryAccountBean.setIsSell(GlobalDefinitions.noString);
                }
                inventoryAccountBean.setDeviceMac(rs.getString(3));
                if(rs.getInt(4)==1){
                    inventoryAccountBean.setMainCpe(GlobalDefinitions.yesString);
                }else{
                    inventoryAccountBean.setMainCpe(GlobalDefinitions.noString);
                }
                if(rs.getInt(5)==1){
                    inventoryAccountBean.setOwnCpe(GlobalDefinitions.yesString);
                }else{
                    inventoryAccountBean.setOwnCpe(GlobalDefinitions.noString);
                }
                if(rs.getInt(6)==1 || rs.getInt(6)==0){
                    inventoryAccountBean.setStatus(GlobalDefinitions.DEPTACTIVESTRING);
                }else if(rs.getInt(6) == 2){
                    inventoryAccountBean.setStatus(GlobalDefinitions.DEPTINACTIVESTRING);
                }else if(rs.getInt(6) == 3){
                    inventoryAccountBean.setStatus("Cancelled");
                }
                inventoryAccountBean.setSerialItemName(rs.getString(7));
                arrlist.add(inventoryAccountBean);
            }
           
        }catch(Exception e){
           
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(con);
        }
        return arrlist;
    }*/
 public ArrayList getPhoneNumbers(int accountObj) throws SQLException{
        ArrayList arrlist=new ArrayList();
        Connection con=null;
        try{
            con =DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getPhoneNumbers);
            pst.setInt(1,accountObj);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                phoneBean phBean=new phoneBean();
                phBean.setPhoneNo(rs.getString(1));
                String type=Integer.toString(rs.getInt(2));
                phBean.setPhoneType(type);
                arrlist.add(phBean);
            }
        }catch(Exception e){
           
        }finally{
            DBUtil.closeConnection(con);
        }
        return arrlist;
 
 }
 
 
 
  public String creditAgingStatus(int accountPoid) throws SQLException{
        String status="";
        Connection con=null;
        String getCreditAgingStatus="select aseg.descr,cseg.cust_seg_desc from wtb_config_aging_segments_t aseg,account_t a , config_customer_segment_t cseg where aseg.aging_segment=a.aging_segment and cseg.rec_id=to_number(a.cust_seg_list) and a.poid_id0=?";
        try{
            con =DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getCreditAgingStatus);
            pst.setInt(1,accountPoid);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                status=rs.getString(1)+"/"+rs.getString(2);
            }else{
                status="";
            }
        }catch(Exception e){
            
        }finally{
            DBUtil.closeConnection(con);
        }
        return status;
    }
    
    public ArrayList getWimaxlogin(int accountPoid) throws SQLException{
        ArrayList arrSvsList = new ArrayList();
        RegService svs = null;
        Connection con=null;
        String getWimaxLogin="SELECT REPLACE(S.POID_TYPE,'/service/','') as ServiceType, Login, (case when s.status = 10100 then 'Active' When s.status = 10101 then 'In-Active' when s.status=10103  then 'Closed' else '' end) as Status from service_t s where account_obj_id0=?";
        try{
            con =DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getWimaxLogin);
            pst.setInt(1,accountPoid);
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                svs=new RegService();
                svs.setSvsType(rs.getString(1));
                svs.setSvsLogin(rs.getString(2));
                svs.setSvsStatus(rs.getString(3));
                arrSvsList.add(svs);
            }
        }catch(Exception e){
           
        }finally{
            DBUtil.closeConnection(con);
        }
        return arrSvsList;
    }
    
   public Object getInvoice(long billPoid, long accountPoid,PPooledConnectionClientServices pCS) throws RemoteException, EBufException{
        FList invoiceOutput;
        String html = null;
        SparseArray results;
        Enumeration enumResults;
        Buffer inputBuffer;
        FList flist;
        Poid retrievedBillObjPoid = null;
        Poid retrievedAccountPoid = null;
        PCachedContext ctx=null;
        try{
            FList invoiceInput = new FList();
            FList argsArray = new FList();
            brmUtility= (BRRMUtility) pCS.createController("com.witribe.util.BRRMUtility");
            ctx  =brmUtility.getLocalContext();
            Poid actPoid = new Poid(ctx.getCurrentDB(),accountPoid, "/account", 0);
            Poid billobjpoid = new Poid(ctx.getCurrentDB(),billPoid, "/bill", 0);
            argsArray.set(FldBillObj.getInst(), billobjpoid);
            invoiceInput.set(FldPoid.getInst(), actPoid);
            invoiceInput.setElement(FldArgs.getInst(), 0, argsArray);
            invoiceOutput = null;
            invoiceInput.dump();
            invoiceOutput = ctx.opcode(147, 0, invoiceInput);
            invoiceOutput.dump();
            results = invoiceOutput.get(FldResults.getInst());
            enumResults = results.getValueEnumerator();
            flist = (FList)enumResults.nextElement();
            inputBuffer = flist.get(FldBuffer.getInst());
            html = inputBuffer.toString();
            System.out.println("Invoice HTML.. "+html);
            if(html == null)
                return html;
            else
                return html.trim();
        }finally{
            brmUtility.getreleaseContext(ctx);
            brmUtility=null;
        }
    }  
   
    public FList getPaymentDetails(String paymentId,PPooledConnectionClientServices pCS) throws RemoteException, EBufException{
        FList paymentOutput = null;
        String html = null;
        SparseArray results;
        Enumeration enumResults;
        Buffer inputBuffer;
        FList flist;
        Poid retrievedBillObjPoid = null;
        Poid retrievedAccountPoid = null;
        PCachedContext ctx=null;
        ArrayList paymentReceiptList = new ArrayList();
        
        String accountno = null;
        BigDecimal amount = null;
        String city = null;
        String descr = null;
        String name = null;
        String itemNo = null;
        String title = null;
        String transId = null;
        String payDate = null;
        ReceiptInfoBean receiptInfoBean = null;
        String receiptInfo = null;
        FList resFlist = null;
        try{
            
            /*sample search flist for RECEIPT 
             
                0 PIN_FLD_POID                      POID [0] 0.0.0.1 /search -1 0
                0 PIN_FLD_FLAGS                      INT [0] 256
                0 PIN_FLD_OP_CORRELATION_ID          STR [0] "1:HST-PCS-19332:UnknownProgramName:0:AWT-EventQueue-0:7:1302589312:0"
                0 PIN_FLD_TEMPLATE                   STR [0] " select X from /receipt_info where F1 = V1 and F2 like V2 "
                0 PIN_FLD_RESULTS                  ARRAY [*] allocated 0, used 0
                0 PIN_FLD_ARGS                     ARRAY [1] allocated 1, used 1
                1     PIN_FLD_ITEM_NO                STR [0] "P1-113"
                0 PIN_FLD_ARGS                     ARRAY [2] allocated 1, used 1
                1     PIN_FLD_POID                  POID [0] 0.0.0.1 /receipt_info -1 0
             *
          sample search flist for RECEIPT*/
            
            
            FList paymentInput = new FList();
            FList argsArray = new FList();
            FList argsArray1 = new FList();
            brmUtility= (BRRMUtility) pCS.createController("com.witribe.util.BRRMUtility");
            ctx  =brmUtility.getLocalContext();
            
            Poid searchPoid = new Poid(ctx.getCurrentDB(),-1, "/search", 0);
            Poid receiptPoid = new Poid(ctx.getCurrentDB(),-1, "/receipt_info", 0);
           
            paymentInput.set(FldPoid.getInst(), searchPoid);
            paymentInput.set(FldFlags.getInst(), 256);
            paymentInput.set(FldTemplate.getInst(), "select X from /receipt_info where F1 = V1 and F2 like V2 ");
            paymentInput.setElement(FldResults.getInst(), -1);
            
            argsArray.set(FldPoid.getInst(), receiptPoid);
            argsArray1.set(FldItemNo.getInst(), paymentId);
            paymentInput.setElement(FldArgs.getInst(), 1, argsArray1);
            paymentInput.setElement(FldArgs.getInst(), 2, argsArray);
            
            
            paymentInput.dump();
            paymentOutput = ctx.opcode(7, 0, paymentInput);
            paymentOutput.dump();
            results = paymentOutput.get(FldResults.getInst());
            
             if (paymentOutput.hasField(FldResults.getInst()))
              {
                 results = paymentOutput.get(FldResults.getInst());

                 resFlist = results.getAnyElement();
                 resFlist.dump();
                 
               /* accountno = resFlist.get(FldAccountNo.getInst());
                amount    = resFlist.get(FldAmount.getInst());
                city      = resFlist.get(FldCity.getInst());
                descr     = resFlist.get(FldDescr.getInst());
                transId   = resFlist.get(FldTransId.getInst());  
                title     = resFlist.get(FldTitle.getInst());
                payDate   = resFlist.get(FldAacAccess.getInst());
                    
                receiptInfoBean = new ReceiptInfoBean();
                
                if(accountno!= null){
                receiptInfoBean.setAccountno(accountno);
                }
                if(amount!= null){
                receiptInfoBean.setAmount(amount);
                }
                if(city!= null){
                receiptInfoBean.setCity(city);
                }
               if(descr!= null){
                receiptInfoBean.setDescr(descr); 
                }
               if(transId!= null){
                receiptInfoBean.setTransId(transId);
                }
                if(title!= null){
                receiptInfoBean.setTitle(title);
                }
               if(payDate!= null){
                receiptInfoBean.setPayDate(payDate);
                }
                receiptInfo = 
                
                 System.out.println("getAccountno"+receiptInfoBean.getAccountno());
                 System.out.println("getAmount"+receiptInfoBean.getAmount());
                 paymentReceiptList.add(receiptInfoBean); */
             }

        }catch(Exception e){
           System.out.println("Exception in paymentdetails "+e);
        }finally{
            brmUtility.getreleaseContext(ctx);
            brmUtility=null;
        }
        return resFlist;
    }  
   
       public String getCurrency(int accountObj) throws SQLException{
        String curr="";
        Connection con=null;
        try{
            con =BRMUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getCurrency);
            pst.setInt(1,accountObj);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                curr=Integer.toString(rs.getInt(1));
            }
        }catch(Exception e){
           System.out.println("Exception "+e);
        }finally{
            BRMUtil.closeConnection(con);
        }
        return curr;
    }
    public String getEmaillogin(int accountPoid) throws SQLException{
        String email="";
        Connection con=null;
        String getEmailLogin="select login  from service_t s, service_email_t smail where s.poid_id0=smail.obj_id0 and account_obj_id0=?";
        try{
            con =DBUtil.getBRMConnection();
            PreparedStatement pst=con.prepareStatement(getEmailLogin);
            pst.setInt(1,accountPoid);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                email=rs.getString(1);
            }else{
                email=null;
            }
        }catch(Exception e){
           
        }finally{
            DBUtil.closeConnection(con);
        }
        return email;
    }
    public int getAacPromoCode(int accountPoid) throws SQLException{
        int aacPromoCode=100;
        Connection con=null;
        try{
            con =DBUtil.getBRMConnection();
            String sqlQuery="select aac_promo_code from account_t where poid_id0=?";
            PreparedStatement pst=con.prepareStatement(sqlQuery);
            pst.setInt(1,accountPoid);
            ResultSet rs=pst.executeQuery();
            if(rs.next()){
                aacPromoCode=rs.getInt(1);
            }
        }catch(Exception e){
            
            e.printStackTrace();
        }finally{
            DBUtil.closeConnection(con);
        }
        return aacPromoCode;
    }
    
    
     public String createSinglePayment(HttpSession session,int accountObj,AccountSearchJavaBean accountSearchJavaBean,PPooledConnectionClientServices pCS) throws RemoteException{
        String paymentNo="";
        PCachedContext ctx=null;
        Connection con=null;
        try{
            brmUtility= (BRRMUtility) pCS.createController("com.witribe.util.BRRMUtility");
            ctx  =brmUtility.getLocalContext();
            /*
                0 PIN_FLD_POID           POID [0] 0.0.0.1 /account 1 0
                0 PIN_FLD_PROGRAM_NAME    STR [0] "Payment Tool"
                0 PIN_FLD_BATCH_INFO    ARRAY [0] allocated 20, used 4
                1     PIN_FLD_CURRENCY        INT [0] 586
                1     PIN_FLD_BATCH_TOTAL  DECIMAL [0] 400.00
                1     PIN_FLD_CHANNEL_ID      INT [0] 0
                1     PIN_FLD_SUBMITTER_ID    STR [0] "root.0.0.0.1"
                0 PIN_FLD_CHARGES       ARRAY [0] allocated 20, used 7
                1     PIN_FLD_ACCOUNT_NO      STR [0] "0.0.0.1-1713308"
                1     PIN_FLD_BILLINFO_OBJ      POID [0] 0.0.0.1 /billinfo 1714844 0
                1     PIN_FLD_COMMAND        ENUM [0] 0
                1     PIN_FLD_PAY_TYPE       ENUM [0] 10011
                1     PIN_FLD_BILLS         ARRAY [0] allocated 20, used 1
                2         PIN_FLD_BILL_NO         STR [0] "IS-B1-441"
                1     PIN_FLD_CURRENCY        INT [0] 586
                1     PIN_FLD_AMOUNT       DECIMAL [0] 400.00
                1     PIN_FLD_PAYMENT      SUBSTRUCT [0] allocated 20, used 2
                2         PIN_FLD_DESCR           STR [0] ""
                2         PIN_FLD_INHERITED_INFO SUBSTRUCT [0] allocated 20, used 1
                3             PIN_FLD_CASH_INFO     ARRAY [0] allocated 20, used 2
                4                 PIN_FLD_EFFECTIVE_T  TSTAMP [0] (1280255400) Tue Jul 27 23:30:00 2010
                4                 PIN_FLD_RECEIPT_NO      STR [0] ""
             */
            FList in=new FList();
            in.set(FldPoid.getInst(),new Poid(1,accountObj,"/account"));
            in.set(FldProgramName.getInst(),accountSearchJavaBean.getProgramName()+","+accountSearchJavaBean.getCsrMakingPayment());
            SparseArray batchInfoArray=new SparseArray();
            FList batchInfoFlist=new FList();
           // batchInfoFlist.set(FldSubmitterId.getInst(),accountSearchJavaBean.getCsrMakingPayment());
            batchInfoFlist.set(FldSubmitterId.getInst(),"root.0.0.0.1");
            BigDecimal  amountPaid= new BigDecimal(accountSearchJavaBean.getPaidAmount());
            batchInfoFlist.set(FldBatchTotal.getInst(),amountPaid);
            int curr=Integer.parseInt(accountSearchJavaBean.getCurrency());
            batchInfoFlist.set(FldCurrency.getInst(),curr);
            batchInfoFlist.set(FldChannelId.getInst(),0);
            batchInfoArray.add(batchInfoFlist);
            in.set(FldBatchInfo.getInst(),batchInfoArray);
            SparseArray chargesArray=new SparseArray();
            FList chargesFlist=new FList();
            chargesFlist.set(FldAccountNo.getInst(),accountSearchJavaBean.getAccountNumber());
            int billInfoObj=Integer.parseInt((String)session.getAttribute("billInfoObj"));
            chargesFlist.set(FldBillinfoObj.getInst(),new Poid(1,billInfoObj,"/billinfo"));
            chargesFlist.set(FldAmount.getInst(),amountPaid);
            chargesFlist.set(FldCommand.getInst(),0);
            int payType=Integer.parseInt(accountSearchJavaBean.getPaymentType());
            chargesFlist.set(FldPayType.getInst(),payType);
            chargesFlist.set(FldCurrency.getInst(),curr);
            SparseArray billArray=new SparseArray();
            if(accountSearchJavaBean.getBillNo() != null && !accountSearchJavaBean.getBillNo().equals("null")) {
                FList billFlist=new FList();
                billFlist.set(FldBillNo.getInst(),accountSearchJavaBean.getBillNo());
                billArray.add(billFlist);
                chargesFlist.set(FldBills.getInst(),billArray);
            }
            FList paymentFlist=new FList();
            //cash payment start
            if(accountSearchJavaBean.getPaymentType().equals("10011")){
                paymentFlist.set(FldDescr.getInst(),"Cash Payment");
                // added on 20/12/2010
                FList inheritedInfoFlist=new FList();
                SparseArray cashArray=new SparseArray();
                FList cashFlist=new FList();
                cashFlist.set(FldEffectiveT.getInst(),new Date(accountSearchJavaBean.getReceiptDate()));
                if(!accountSearchJavaBean.getReceiptNo().equals("")){
                    cashFlist.set(FldReceiptNo.getInst(),accountSearchJavaBean.getReceiptNo());
                }
                cashArray.add(cashFlist);
                inheritedInfoFlist.set(FldCashInfo.getInst(),cashArray);
                paymentFlist.set(FldInheritedInfo.getInst(),inheritedInfoFlist);
            }
            //cash payment end
            //Cheque payment start
            if(accountSearchJavaBean.getPaymentType().equals("10012")){
                paymentFlist.set(FldDescr.getInst(),"Cheque Payment");
                FList inheritedInfoFlist=new FList();
                SparseArray checkInfoArray=new SparseArray();
                FList checkInfoFlist=new FList();
                checkInfoFlist.set(FldEffectiveT.getInst(),new Date(accountSearchJavaBean.getChequeDate()));
                checkInfoFlist.set(FldCheckNo.getInst(),accountSearchJavaBean.getChequeNo());
                checkInfoFlist.set(FldBankName.getInst(),accountSearchJavaBean.getBankName());
               // checkInfoFlist.set(WtbFldBranchName.getInst(),accountSearchJavaBean.getBranchName());
                // checkInfoFlist.set(WtbFldPayersName.getInst(),accountSearchJavaBean.getPayersName());
                checkInfoArray.add(checkInfoFlist);
                inheritedInfoFlist.set(FldCheckInfo.getInst(),checkInfoArray);
                paymentFlist.set(FldInheritedInfo.getInst(),inheritedInfoFlist);
            }
            //Cheque payment end
            // credit card payment start
            if(accountSearchJavaBean.getPaymentType().equals("10101")){
                String creditCardNo=getCreditCardNo(accountSearchJavaBean.getCreditCardNo());
                System.out.println("credit card no :::"+creditCardNo);
                System.out.println("credit card no :::"+creditCardNo);
                paymentFlist.set(FldDescr.getInst(),"Credit Card No :"+creditCardNo+" Card Type :"+accountSearchJavaBean.getCardType()+
                        " Expiry Date :"+accountSearchJavaBean.getExpiryDate()+" Transaction No :"+accountSearchJavaBean.getTransactionNo());
                FList inheritedInfoFlist=new FList();
                SparseArray creditCardArray=new SparseArray();
                FList creditCardFlist=new FList();
                creditCardFlist.set(FldAuthCode.getInst(),accountSearchJavaBean.getTransactionNo());
                creditCardFlist.set(FldAuthDate.getInst(),accountSearchJavaBean.getTransactionDate());
                creditCardFlist.set(FldResult.getInst(),0);
                //creditCardFlist.set(FldVendorResults.getInst(),"VC :"+accountSearchJavaBean.getVendorCode()+"&SR=2&AVS=3");
                creditCardFlist.set(FldNumber.getInst(),creditCardNo);
                creditCardFlist.set(FldBankName.getInst(),accountSearchJavaBean.getCardType());
                if(accountSearchJavaBean.getTransactionDate() != "" && accountSearchJavaBean.getTransactionDate() !=null){                    
                    creditCardFlist.set(FldEffectiveT.getInst(),new Date(accountSearchJavaBean.getTransactionDate()));
                }
                creditCardArray.add(creditCardFlist);
                inheritedInfoFlist.set(FldCcInfo.getInst(),creditCardArray);
                paymentFlist.set(FldInheritedInfo.getInst(),inheritedInfoFlist);
            }
            //credit card payment end
            
            chargesFlist.set(FldPayment.getInst(),paymentFlist);
            chargesArray.add(chargesFlist);
            in.set(FldCharges.getInst(),chargesArray);
            in.dump();
            
            FList outFlist=new FList();
            outFlist=ctx.opcode(113,in);
            outFlist.dump();
            if(outFlist!=null && outFlist.hasField(FldResults.getInst())){
                SparseArray resultsArray=new SparseArray();
                resultsArray=outFlist.get(FldResults.getInst());
                FList resultFlist=new FList();
                for(int j=0;j<resultsArray.size();j++){
                    resultFlist=resultsArray.elementAt(j);
                }
                paymentNo=resultFlist.get(FldItemNo.getInst());
                
            }else{
                paymentNo="";
            }
        }catch(Exception e){
           System.out.println(e);
            e.printStackTrace();
        }finally{
            brmUtility.getreleaseContext(ctx);
            brmUtility=null;
        }
        return paymentNo;
    }
    
 public String createTotalPayment(HttpSession session,int accountObj,AccountSearchJavaBean accountSearchJavaBean,PPooledConnectionClientServices pCS) throws RemoteException{
        String paymentNo="";
        PCachedContext ctx=null;
        Connection con=null;
        try{
            brmUtility= (BRRMUtility) pCS.createController("com.witribe.util.BRRMUtility");
            ctx  =brmUtility.getLocalContext();
             /*  0 PIN_FLD_POID           POID [0] 0.0.0.1 /account 1 0
                0 PIN_FLD_PROGRAM_NAME    STR [0] "Payment Tool"
                0 PIN_FLD_BATCH_INFO    ARRAY [0] allocated 20, used 4
                1     PIN_FLD_SUBMITTER_ID    STR [0] "root.0.0.0.1"
                1     PIN_FLD_BATCH_TOTAL  DECIMAL [0] 1000
                1     PIN_FLD_CURRENCY        INT [0] 586
                1     PIN_FLD_BATCH_ID        STR [0] ""
                0 PIN_FLD_DESCR           STR [0] "Payment Batch:: Number of Payments in Batch:1"
                0 PIN_FLD_CHARGES       ARRAY [0] allocated 20, used 8
                1     PIN_FLD_ACCOUNT_NO      STR [0] "0.0.0.1-15164287"
                1     PIN_FLD_BILLINFO_OBJ   POID [0] 0.0.0.1 /billinfo 15167359 2   – adding billinfo obj
                1     PIN_FLD_ACCOUNT_OBJ    POID [0] 0.0.0.1 /account 15164287 22
                1     PIN_FLD_AMOUNT       DECIMAL [0] 1000
                1     PIN_FLD_COMMAND        ENUM [0] 0
                1     PIN_FLD_PAYMENT      SUBSTRUCT [0] allocated 20, used 2
                2         PIN_FLD_INHERITED_INFO SUBSTRUCT [0] allocated 20, used 1
                3             PIN_FLD_CASH_INFO     ARRAY [0] allocated 20, used 2
                4                 PIN_FLD_RECEIPT_NO      STR [0] ""
                4                 PIN_FLD_EFFECTIVE_T  TSTAMP [0] (1329894109) Wed Feb 22 12:01:49 2012
                2         PIN_FLD_DESCR           STR [0] ""
                1     PIN_FLD_PAY_TYPE       ENUM [0] 10011
                1     PIN_FLD_CURRENCY        INT [0] 586
              */
            
            FList in=new FList();
            in.set(FldPoid.getInst(),new Poid(1,accountObj,"/account"));
           // in.set(FldProgramName.getInst(),accountSearchJavaBean.getProgramName()+","+accountSearchJavaBean.getCsrMakingPayment());
            in.set(FldProgramName.getInst(),"Payment Tool");
            SparseArray batchInfoArray=new SparseArray();
            FList batchInfoFlist=new FList();
            batchInfoFlist.set(FldSubmitterId.getInst(),"root.0.0.0.1");
            BigDecimal amountPaid= new BigDecimal(accountSearchJavaBean.getPaidAmount());
            batchInfoFlist.set(FldBatchTotal.getInst(),amountPaid);
            int curr=Integer.parseInt(accountSearchJavaBean.getCurrency());
            batchInfoFlist.set(FldCurrency.getInst(),curr);
            batchInfoFlist.set(FldBatchId.getInst(),"");
            batchInfoArray.add(batchInfoFlist);
            in.set(FldBatchInfo.getInst(),batchInfoArray);
            in.set(FldDescr.getInst(),"Payment Batch:: Number of Payments in Batch:1");
            SparseArray chargesArray=new SparseArray();
            FList chargesFlist=new FList();
            chargesFlist.set(FldAccountNo.getInst(),accountSearchJavaBean.getAccountNumber());
            int billInfoObj=Integer.parseInt((String)session.getAttribute("billInfoObj"));
            chargesFlist.set(FldBillinfoObj.getInst(),new Poid(1,billInfoObj,"/billinfo",2));
            chargesFlist.set(FldAccountObj.getInst(),new Poid(1,accountObj,"/account",22));
            chargesFlist.set(FldAmount.getInst(),amountPaid);
            chargesFlist.set(FldCommand.getInst(),0);
            SparseArray cashInfoArray=new SparseArray();
            FList paymentFlist=new FList();
            //cash payment start
            if(accountSearchJavaBean.getPaymentType().equals("10011")){
                paymentFlist.set(FldDescr.getInst(),"Cash Payment");
                //added on 20/12/2010
                FList inheritedInfoFlist=new FList();
                SparseArray cashArray=new SparseArray();
                FList cashFlist=new FList();
                cashFlist.set(FldEffectiveT.getInst(),new Date(accountSearchJavaBean.getReceiptDate()));
                if(!accountSearchJavaBean.getReceiptNo().equals("")){
                    cashFlist.set(FldReceiptNo.getInst(),accountSearchJavaBean.getReceiptNo());
                }
                cashArray.add(cashFlist);
                inheritedInfoFlist.set(FldCashInfo.getInst(),cashArray);
                paymentFlist.set(FldInheritedInfo.getInst(),inheritedInfoFlist);
            }
            //cash payment end
            //Cheque payment start
            if(accountSearchJavaBean.getPaymentType().equals("10012")){
                paymentFlist.set(FldDescr.getInst(),"Cheque Payment");
                FList inheritedInfoFlist=new FList();
                SparseArray checkInfoArray=new SparseArray();
                FList checkInfoFlist=new FList();
                checkInfoFlist.set(FldEffectiveT.getInst(),new Date(accountSearchJavaBean.getChequeDate()));
                checkInfoFlist.set(FldCheckNo.getInst(),accountSearchJavaBean.getChequeNo());
                checkInfoFlist.set(FldBankName.getInst(),accountSearchJavaBean.getBankName());
            //    checkInfoFlist.set(WtbFldBranchName.getInst(),accountSearchJavaBean.getBranchName());
               // checkInfoFlist.set(WtbFldPayersName.getInst(),accountSearchJavaBean.getPayersName());
                checkInfoArray.add(checkInfoFlist);
                inheritedInfoFlist.set(FldCheckInfo.getInst(),checkInfoArray);
                paymentFlist.set(FldInheritedInfo.getInst(),inheritedInfoFlist);
            }
            //Cheque payment end
            // credit card payment start
            if(accountSearchJavaBean.getPaymentType().equals("10101")){
                String creditCardNo=getCreditCardNo(accountSearchJavaBean.getCreditCardNo());
                System.out.println("credit card no :::"+creditCardNo);
                System.out.println("credit card no :::"+creditCardNo);
                paymentFlist.set(FldDescr.getInst(),"Credit Card No :"+creditCardNo+" Card Type :"+accountSearchJavaBean.getCardType()+
                        " Expiry Date :"+accountSearchJavaBean.getExpiryDate()+" Transaction No :"+accountSearchJavaBean.getTransactionNo());
                FList inheritedInfoFlist=new FList();
                SparseArray creditCardArray=new SparseArray();
                FList creditCardFlist=new FList();
                creditCardFlist.set(FldAuthCode.getInst(),accountSearchJavaBean.getTransactionNo());
                creditCardFlist.set(FldAuthDate.getInst(),accountSearchJavaBean.getTransactionDate());
                creditCardFlist.set(FldResult.getInst(),0);
//               creditCardFlist.set(FldVendorResults.getInst(),"VC :"+accountSearchJavaBean.getVendorCode()+"&SR=2&AVS=3");
                creditCardFlist.set(FldNumber.getInst(),creditCardNo);
                creditCardFlist.set(FldBankName.getInst(),accountSearchJavaBean.getCardType());
                if(accountSearchJavaBean.getTransactionDate() != "" && accountSearchJavaBean.getTransactionDate() !=null){
                    creditCardFlist.set(FldEffectiveT.getInst(),new Date(accountSearchJavaBean.getTransactionDate()));
                }
                creditCardArray.add(creditCardFlist);
                inheritedInfoFlist.set(FldCcInfo.getInst(),creditCardArray);
                paymentFlist.set(FldInheritedInfo.getInst(),inheritedInfoFlist);
            }
            //credit card payment end
            chargesFlist.set(FldPayment.getInst(),paymentFlist);
            int payType=Integer.parseInt(accountSearchJavaBean.getPaymentType());
            chargesFlist.set(FldPayType.getInst(),payType);
            chargesFlist.set(FldCurrency.getInst(),curr);
            chargesArray.add(chargesFlist);
            in.set(FldCharges.getInst(),chargesArray);
            in.dump();
            
            FList outFlist=new FList();
            outFlist=ctx.opcode(113,in);
            outFlist.dump();
            if(outFlist!=null && outFlist.hasField(FldResults.getInst())){
                SparseArray resultsArray=new SparseArray();
                resultsArray=outFlist.get(FldResults.getInst());
                FList resultFlist=new FList();
                for(int j=0;j<resultsArray.size();j++){
                    resultFlist=resultsArray.elementAt(j);
                }
                paymentNo=resultFlist.get(FldItemNo.getInst());
            }else{
                paymentNo="";
            }
        }catch(Exception e){
            System.out.println("Exception"+e);
            e.printStackTrace();
        }finally{
            brmUtility.getreleaseContext(ctx);
            brmUtility=null;
        }
        return paymentNo;
    }
  public String getCreditCardNo(String oldCreditCardno){
        String newCreditCardNo="";
        try{
            int len=oldCreditCardno.length();
            System.out.println("len"+len);
            int finallen=len-4;
            for(int i=0;i<finallen;i++){
                newCreditCardNo=newCreditCardNo+"x";
            }
            System.out.println("newCreditCardNo::"+newCreditCardNo);
            String lastNo=oldCreditCardno.substring(finallen,len);
            System.out.println("lastNo:::::"+lastNo);
            newCreditCardNo=newCreditCardNo+lastNo;
            System.out.println("newCreditCardNo final :::::"+newCreditCardNo);
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
        return newCreditCardNo;
    }      
    public static void main(String args[]){
        try {
            System.out.println("into main");
            String xx="*0.0.0.1-1561*";
            AccountSearchDAO AcSearchDAO = new AccountSearchDAO();
             Pattern pattern = Pattern.compile("[*]");
             Matcher matcher = pattern.matcher(xx);
             String str = matcher.replaceAll("%");
              System.out.println("str "+str);
            AcSearchDAO.getAccountStatus(1545217);
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
