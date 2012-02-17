package com.portal.web.comp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import models.utils.ExecuteOpcode;

import play.mvc.Scope.Session;

import com.portal.bas.PCachedContext;
import com.portal.bas.PControllerImpl;
import com.portal.pcm.DefaultLog;
import com.portal.pcm.EBufException;
import com.portal.pcm.FList;
import com.portal.pcm.Poid;
import com.portal.pcm.SparseArray;
import com.portal.pcm.fields.FldCycleStartT;
import com.portal.pcm.fields.FldDeals;
import com.portal.pcm.fields.FldDiscounts;
import com.portal.pcm.fields.FldName;
import com.portal.pcm.fields.FldPlanObj;
import com.portal.pcm.fields.FldPoid;
import com.portal.pcm.fields.FldProducts;
import com.portal.pcm.fields.FldPurchaseStartT;
import com.portal.pcm.fields.FldQuantity;
import com.portal.pcm.fields.FldServices;
import com.portal.pcm.fields.FldStatus;
import com.portal.pcm.fields.FldUsageStartT;

public class PProductsBeanPImpl extends PControllerImpl
  implements PProductsBeanP, Serializable
{
  private List mProducts = new ArrayList();
  private List mDiscounts = new ArrayList();

  public PProductsBeanPImpl()
    throws RemoteException
  {
  }

  public List getProducts()
    throws RemoteException
  {
    if (DefaultLog.doLog(8)) {
      DefaultLog.log(8, "WebKit:***getProducts()***\n");
    }

    return this.mProducts;
  }

  public Product getProducts(int index)
    throws RemoteException
  {
    if (DefaultLog.doLog(8)) {
      DefaultLog.log(8, "WebKit:***getProducts(int index)***\n");
    }

    if ((index < 0) || (index >= this.mProducts.size()))
    {
      throw new IndexOutOfBoundsException();
    }

    return (Product)this.mProducts.get(index);
  }

  public void setProducts(ProductP sResult)
  {
    this.mProducts.add(sResult);
  }

  public void update(int reason, Object data)
  {
  }

  public Object getSelectionDataFor(String dataItem, int index)
  {
    return null;
  }

  public void products(Poid accountpoid, Session session)
    throws RemoteException
  {
    try
    {
      FList input = new FList();
      input.set(FldPoid.getInst(), accountpoid);

      FList out = ExecuteOpcode.execute(81, input, session, 0);

      processOutputFList(out);
    } finally {
    }
  }

  protected void processOutputFList(FList out)
    throws RemoteException
  {
    try
    {
      if ((!out.hasField(FldServices.getInst())) && (!out.hasField(FldDeals.getInst())) && (!out.hasField(FldProducts.getInst())))
      {
        throw new RemoteException("error.products");
      }

      if (out.hasField(FldServices.getInst())) {
        SparseArray servicesArray = out.get(FldServices.getInst());

        if (servicesArray == null) {
          throw new RemoteException("error.products");
        }

        Enumeration enumVals = servicesArray.getValueEnumerator();

        while (enumVals.hasMoreElements())
        {
          FList services = (FList)enumVals.nextElement();

          Poid poid = services.get(FldPoid.getInst());

          if (poid == null) {
            throw new RemoteException("error.products");
          }

          String service = poid.toString();
          int start = service.indexOf('/');
          int end = service.indexOf(' ', start);

          if (!services.hasField(FldDeals.getInst()))
          {
            continue;
          }
          processDealsFlist(services, service.substring(start, end));
        }

      }

      if (out.hasField(FldDeals.getInst()))
      {
        processDealsFlist(out, null);
      }

      if (out.hasField(FldProducts.getInst()))
      {
        processProductsFlist(out, null, null);
      }

      if (out.hasField(FldDiscounts.getInst()))
      {
        processDiscountsFlist(out, null, null);
      }

    }
    catch (EBufException e)
    {
      if (DefaultLog.doLog(2)) {
        DefaultLog.log(this, 2, e);
      }
      throw createClientException(e);
    }
  }

  protected void processDealsFlist(FList out, String serviceStr)
    throws RemoteException
  {
    try
    {
      SparseArray dealsArray = out.get(FldDeals.getInst());

      if (dealsArray == null) {
        throw new RemoteException("error.products");
      }

      Enumeration enum1 = dealsArray.getValueEnumerator();

      while (enum1.hasMoreElements())
      {
        FList deals = (FList)enum1.nextElement();

        String deal = deals.get(FldName.getInst());

        if (deals.hasField(FldProducts.getInst())) {
          processProductsFlist(deals, serviceStr, deal);
        }

        if (deals.hasField(FldDiscounts.getInst()))
        {
          processDiscountsFlist(deals, serviceStr, deal);
        }

      }

    }
    catch (EBufException e)
    {
      if (DefaultLog.doLog(2)) {
        DefaultLog.log(this, 2, e);
      }
      throw createClientException(e);
    }
  }

  protected void processProductsFlist(FList out, String serviceStr, String deal)
    throws RemoteException
  {
    try
    {
      SparseArray productsArray = out.get(FldProducts.getInst());

      if (productsArray == null) {
        throw new RemoteException("error.products");
      }

      Enumeration enum2 = productsArray.getValueEnumerator();

      while (enum2.hasMoreElements())
      {
        FList products = (FList)enum2.nextElement();
        if (products == null) {
          throw new RemoteException("error.products");
        }

        String product = products.get(FldName.getInst());

        Integer integerStatus = products.get(FldStatus.getInst());
        int intStatus = integerStatus.intValue();

        String productStatus = getProductStatusAsString(intStatus);

        BigDecimal qty = products.get(FldQuantity.getInst());

        Date purchaseDt = products.get(FldPurchaseStartT.getInst());
        Date cycleDt = products.get(FldCycleStartT.getInst());
        Date usageDt = products.get(FldUsageStartT.getInst());
        String planID = products.get(FldPlanObj.getInst()).toString();

        ProductP p = new ProductP(deal, product, productStatus, serviceStr, qty, purchaseDt, cycleDt, usageDt, planID);

        setProducts(p);
      }

    }
    catch (EBufException e)
    {
      if (DefaultLog.doLog(2)) {
        DefaultLog.log(this, 2, e);
      }
      throw createClientException(e);
    }
  }

  protected void processDiscountsFlist(FList out, String serviceStr, String deal)
    throws RemoteException
  {
    try
    {
      SparseArray discountsArray = out.get(FldDiscounts.getInst());

      if (discountsArray == null) {
        throw new RemoteException("error.NoDiscounts");
      }

      Enumeration enum2 = discountsArray.getValueEnumerator();

      while (enum2.hasMoreElements())
      {
        FList discounts = (FList)enum2.nextElement();
        if (discounts == null) {
          throw new RemoteException("error.NoDiscounts");
        }

        String discountName = discounts.get(FldName.getInst());

        Integer integerStatus = discounts.get(FldStatus.getInst());
        int intStatus = integerStatus.intValue();

        String discountStatus = getProductStatusAsString(intStatus);

        BigDecimal qty = discounts.get(FldQuantity.getInst());

        Date purchaseDt = discounts.get(FldPurchaseStartT.getInst());
        Date cycleDt = discounts.get(FldCycleStartT.getInst());
        Date usageDt = discounts.get(FldUsageStartT.getInst());

        Discount disObj = new Discount(deal, discountName, discountStatus, serviceStr, qty, purchaseDt, cycleDt, usageDt);

        setDiscounts(disObj);
      }

    }
    catch (EBufException e)
    {
      if (DefaultLog.doLog(2)) {
        DefaultLog.log(this, 2, e);
      }
      throw createClientException(e);
    }
  }

  public List getDiscounts()
  {
    if (DefaultLog.doLog(8)) {
      DefaultLog.log(8, "WebKit:***getDiscounts()***\n");
    }

    return this.mDiscounts;
  }

  public Discount getDiscounts(int index)
  {
    if (DefaultLog.doLog(8)) {
      DefaultLog.log(8, "WebKit:***getDiscounts(int index)***\n");
    }

    if ((index < 0) || (index >= this.mDiscounts.size()))
    {
      throw new IndexOutOfBoundsException();
    }

    return (Discount)this.mDiscounts.get(index);
  }

  public void setDiscounts(Discount sDiscount)
  {
    this.mDiscounts.add(sDiscount);
  }

  protected String getProductStatusAsString(int sValue)
  {
    String pStatus = "";
    switch (sValue)
    {
    case 0:
      pStatus = "product.status.notset";
      break;
    case 1:
      pStatus = "product.status.active";
      break;
    case 2:
      pStatus = "product.status.inactive";
      break;
    case 3:
      pStatus = "product.status.cancelled";
      break;
    default:
      pStatus = "product.status.unknown";
    }

    return pStatus;
  }
}