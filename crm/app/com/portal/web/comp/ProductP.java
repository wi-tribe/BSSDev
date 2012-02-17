package com.portal.web.comp;

import java.math.BigDecimal;
import java.util.Date;

public class ProductP
{
  private static final BigDecimal ZERO = new BigDecimal("000000");
  private static final Date BASEDATE = new Date(0L);
  private String m_DealName = null;
  private String m_ProductName = null;
  private String m_ProductStatus = null;
  private String m_ServiceType = null;
  private BigDecimal m_ServiceQty = null;
  private Date m_PurchaseDate = null;
  private Date m_CycleStartDate = null;
  private Date m_UsageDate = null;
  private String m_PlanId = null;

  public ProductP(String dealName, String productName, String productStatus, String serviceType, BigDecimal qty, Date purchaseDate, Date cycleDate, Date usageDate)
  {
    this.m_DealName = dealName;
    this.m_ProductName = productName;
    this.m_ProductStatus = productStatus;
    this.m_ServiceType = serviceType;
    this.m_ServiceQty = qty;
    this.m_PurchaseDate = purchaseDate;
    this.m_CycleStartDate = cycleDate;
    this.m_UsageDate = usageDate;
  }

  public ProductP(String dealName, String productName, String productStatus, String serviceType, BigDecimal qty, Date purchaseDate, Date cycleDate, Date usageDate, String planID)
  {
    this.m_DealName = dealName;
    this.m_ProductName = productName;
    this.m_ProductStatus = productStatus;
    this.m_ServiceType = serviceType;
    this.m_ServiceQty = qty;
    this.m_PurchaseDate = purchaseDate;
    this.m_CycleStartDate = cycleDate;
    this.m_UsageDate = usageDate;
    this.m_PlanId = planID;
  }


  public String getDealName()
  {
    if (this.m_DealName == null) {
      return "";
    }

    return this.m_DealName;
  }

  public String getProductName()
  {
    if (this.m_ProductName == null) {
      return "";
    }

    return this.m_ProductName;
  }

  public String getProductStatus()
  {
    if (this.m_ProductStatus == null) {
      return "";
    }

    return this.m_ProductStatus;
  }

  public String getServiceType()
  {
    if (this.m_ServiceType == null) {
      return "";
    }

    return this.m_ServiceType;
  }

  public BigDecimal getServiceQty()
  {
    if (this.m_ServiceQty == null) {
      return ZERO;
    }

    return this.m_ServiceQty;
  }

  public Date getPurchaseDate()
  {
    if (this.m_PurchaseDate == null) {
      return BASEDATE;
    }

    return this.m_PurchaseDate;
  }

  public Date getCycleStartDate()
  {
    if (this.m_CycleStartDate == null) {
      return BASEDATE;
    }

    return this.m_CycleStartDate;
  }

  public Date getUsageDate()
  {
    if (this.m_UsageDate == null) {
      return BASEDATE;
    }

    return this.m_UsageDate;
  }

  public String getPlanID()
  {
    if (this.m_PlanId == null) {
      return "";
    }

    return this.m_PlanId;
  }

}