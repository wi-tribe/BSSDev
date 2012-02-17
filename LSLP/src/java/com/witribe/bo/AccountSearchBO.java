/*
 * AccountSearchBO.java
 *
 * Created on October 7, 2010, 2:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.bo;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.witribe.dao.AccountSearchDAO;
import com.witribe.account.actionform.AccountSearchform;
import com.portal.bas.PPooledConnectionClientServices;
import com.portal.pcm.EBufException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.witribe.vo.AccountSearchJavaBean;
import com.portal.pcm.FList;
/**
 *
 * @author LB85028
 */
public class AccountSearchBO implements java.io.Serializable {
      public static final Logger logger =Logger.getLogger("LSLPLog");// For log4j implementation
    /** Creates a new instance of AccountSearchBO */
    public AccountSearchBO() throws IOException {
        
    }
    public List getAccountDetail(HttpSession session,AccountSearchform accountSearchJavaBean){
        List arrlist=new ArrayList();
        System.out.println("In getAccountDetail BO....");
        
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            arrlist=accountSearchDAO.getAccountDetail(session,accountSearchJavaBean);
        }catch(Exception e){
            logger.error(e);
            e.printStackTrace();
        }
        return arrlist;
    }
  public List getParticularAccountDetails(int accountNumber,PPooledConnectionClientServices pCS){
        List arrlist=new ArrayList();
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            arrlist=accountSearchDAO.getParticularAccountDetails(accountNumber,pCS);
        }catch(Exception e){
            logger.error(e);
        }
        return arrlist;
    }
    public List getInvoiceHistory(HttpServletRequest request,int accountNumber,PPooledConnectionClientServices pCS){
        List arrlist=new ArrayList();
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            arrlist=accountSearchDAO.getInvoiceHistory(request,accountNumber,pCS);
        }catch(Exception e){
            logger.error(e);
        }
        return arrlist;
    }
    public Hashtable getAccountStatus(int accountNumber){
        Hashtable accountStatus = null;
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            accountStatus=accountSearchDAO.getAccountStatus(accountNumber);
        }catch(Exception e){
            logger.error(e);
        }
        return accountStatus;
    }
    
    public ArrayList getPayHistory(HttpSession session,int accountNumber,PPooledConnectionClientServices pCS){
        ArrayList arrlist=new ArrayList();
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            arrlist=accountSearchDAO.getPayHistory(session,accountNumber,pCS);
        }catch(Exception e){
            logger.error(e);
        }
        return arrlist;
    }
    public ArrayList getInventory(int accountNumber){
        ArrayList arrlist=new ArrayList();
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            //arrlist=accountSearchDAO.getInventoryHistory(accountNumber);
        }catch(Exception e){
            logger.error(e);
        }
        return arrlist;
    }   
  public ArrayList getPhoneNumbers(int accountObj){
     ArrayList arrlist=new ArrayList();
     try{
         AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
         arrlist=accountSearchDAO.getPhoneNumbers(accountObj);
     }catch(Exception e){
         e.printStackTrace();
     }
     return arrlist;
 }
  public String getCurrency(int accountObj){
      String curr="";
      try{
          AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
          curr=accountSearchDAO.getCurrency(accountObj);
      }catch(Exception e){
          logger.error(e);
      }
      return curr;
  }  
  
  
    public Object getInvoice(long billPoid, long accountPoid,PPooledConnectionClientServices pCS) throws RemoteException, EBufException {
        AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
        return accountSearchDAO.getInvoice(billPoid,accountPoid,pCS);
    }
    public FList getPaymentDetails(String paymentId,PPooledConnectionClientServices pCS) throws RemoteException, EBufException {
        ArrayList payArrlist=new ArrayList();
        FList resFlist = new FList();
     try{
         AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
         resFlist = accountSearchDAO.getPaymentDetails(paymentId,pCS);
     }catch(Exception e){
         e.printStackTrace();
     } 
        return resFlist;
    } 
     public String createSinglePayment(HttpSession session,int accountNumber,AccountSearchJavaBean accountSearchJavaBean,PPooledConnectionClientServices pCS){
        String paymentNo="";
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            paymentNo=accountSearchDAO.createSinglePayment(session,accountNumber,accountSearchJavaBean,pCS);
        }catch(Exception e){
            logger.error(e);
        }
        return paymentNo;
    }
    public String createTotalPayment(HttpSession session,int accountObj,AccountSearchJavaBean accountSearchJavaBean,PPooledConnectionClientServices pCS){
        String paymentNo="";
        try{
            AccountSearchDAO accountSearchDAO=new AccountSearchDAO();
            paymentNo=accountSearchDAO.createTotalPayment(session,accountObj,accountSearchJavaBean,pCS);
        }catch(Exception e){
            logger.error(e);
        }
        return paymentNo;
    }
    
}
