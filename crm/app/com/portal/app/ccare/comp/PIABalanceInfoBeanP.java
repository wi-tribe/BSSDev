package com.portal.app.ccare.comp;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;

import play.mvc.Scope.Session;

import com.portal.bas.PModelHandle;
import com.portal.pcm.Poid;

public abstract interface PIABalanceInfoBeanP extends Remote
{
  public static final int C_D_COL = 0;
  public static final int C_CL_COL = 1;
  public static final int C_A_COL = 2;
  public static final int C_B_COL = 3;
  public static final int NC_D_COL = 0;
  public static final int NC_CL_COL = 1;
  public static final int NC_B_COL = 2;

  public abstract List getCurrencyData()
    throws RemoteException;

  public abstract Object getCurrencyData(int paramInt)
    throws RemoteException;

  public abstract List getNonCurrencyData()
    throws RemoteException;

  public abstract Object getNonCurrencyData(int paramInt)
    throws RemoteException;

  public abstract void update(int paramInt, Object paramObject)
    throws RemoteException;

  public abstract Object getCurrencySymbol(int paramInt)
    throws RemoteException;

  public abstract Object getBEIDData(int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract Object getBEIDDescription(int paramInt)
    throws RemoteException;
  
  public abstract Object getBEIDDescription(int paramInt, Session session)
		    throws RemoteException;

  public abstract Boolean isSecIDNonZero()
    throws RemoteException;

  public abstract Object getReasonData(Object paramObject)
    throws RemoteException;

  public abstract void getAccountDetails(Poid pAcctPoid, Session session);
  
  public abstract void setAccountPoid(Poid paramPoid);

  public abstract Poid getAccountPoid();

  public abstract void setBalanceGroupPoid(Poid paramPoid);

  public abstract Poid getBalanceGroupPoid();

  public abstract void setBillinfoPoid(Poid paramPoid);

  public abstract Poid getBillinfoPoid();

  public abstract Hashtable getAllSvcLvlBillinfos();

  public abstract Hashtable getAcctLvlBillinfoDetails();

  public abstract PNameinfoBeanP getAllNameinfo();

  public abstract Hashtable getSvcsForBalGrpOfAcctAndBillinfo();

  public abstract Hashtable getBalsOfAllBalGrpsOfAcctAndBillinfo();

  public abstract void getAcctLvlBalanceGroupAndBillinfoID(Poid pAcctPoid, Session session)
    throws RemoteException;

  public abstract void getBalGrpsAndSvcsForAcctAndBillInfo(Poid paramPoid1, Poid paramPoid2)
    throws RemoteException;

  public abstract PModelHandle getAllBillInfoAndDetailsForAcct(Poid pAcctPoid, Session session)
    throws RemoteException;

  public abstract void processOutputFlistOfBillinfos(PModelHandle paramPModelHandle, Session session)
    throws RemoteException;

  public abstract Hashtable getBalancesForAcctAndBalGrpID(Poid paramPoid1, Poid paramPoid2, Session session)
    throws RemoteException;

  public abstract BigDecimal getCurrencyCurrentBal();

  public abstract String getPrimaryCurrencySymbol();

  public abstract BigDecimal getBalanceSummaryForBillInfo(Poid paramPoid, int paramInt)
    throws RemoteException;

  public abstract BigDecimal getCurrencyCreditLimitForBillunit();
}