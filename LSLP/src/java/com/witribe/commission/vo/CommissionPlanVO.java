/*
 * CommissionPlanVO.java
 *
 * Created on October 23, 2009, 12:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.commission.vo;

/**
 *
 * @author SC43278
 */
public class CommissionPlanVO {
    
    /** Creates a new instance of CommissionPlanVO */
    public CommissionPlanVO() {
    }
    private String salesId;
    private String salesType;
    private String channelId;
    private String channelType;
    private String city;
    private String zone;
    private String productId;
    private String commissionType;
    private String productName;
    private String durationMeasure;
    private String[] duration;
    private String[] paymentAmount;
    private String paymentDesc;
    private String paymentId;
    private String targetMeasure;
    private String commType;
    private String[] stepId;
    private String[] eligibilityFrom;
    private String[] eligibilityTo;
    private String[] amount;
    private String eligibilityTarget;
    private String eligibilityMeasure;
    private String eligibilityPeriod;
    private String minEligibility;
    private String eligibilityMeasurePer;
    private String plan; //--Declared By Murali
    private String plantype;//--Declared By Murali
    public String valid_from; //--Declared By Murali
    public String valid_to;//--Declared By Murali
    public String plan_id;
    public String plan_name;
    public String status;
    public String ruleid;    
    public String rule_detail_ids;    
    
    public String getRule_Detail_Id() {
        return rule_detail_ids;
    }

    public void setRule_Detail_Id(String rule_detail_ids) {
        this.rule_detail_ids = rule_detail_ids;
    }      
    
    public String getRuleId() {
        return ruleid;
    }

    public void setRuleId(String ruleid) {
        this.ruleid = ruleid;
    }     
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }  
    

    public String getPlanName() {
        return plan_name;
    }

    public void setPlanName(String planname) {
        this.plan_name = planname;
    }      
    
    public String getPlanId() {
        return plan_id;
    }

    public void setPlanId(String plan_id) {
        this.plan_id = plan_id;
    }
    
    public String getValidFrom() {
        return valid_from;
    }

    public void setValidFrom(String valid_from) {
        this.valid_from = valid_from;
    }
    public String getValidTo() {
        return valid_to;
    }

    public void setValidTo(String valid_to) {
        this.valid_to = valid_to;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
    public String getPlanType() {
        return plantype;
    }

    public void setPlanType(String plantype) {
        this.plantype = plantype;
    }
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getDurationMeasure() {
        return durationMeasure;
    }

    public void setDurationMeasure(String durationMeasure) {
        this.durationMeasure = durationMeasure;
    }

    public String getPaymentDesc() {
        return paymentDesc;
    }

    public void setPaymentDesc(String paymentDesc) {
        this.paymentDesc = paymentDesc;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTargetMeasure() {
        return targetMeasure;
    }

    public void setTargetMeasure(String targetMeasure) {
        this.targetMeasure = targetMeasure;
    }

    public String getCommType() {
        return commType;
    }

    public void setCommType(String commType) {
        this.commType = commType;
    }

    public String getEligibilityTarget() {
        return eligibilityTarget;
    }

    public void setEligibilityTarget(String eligibilityTarget) {
        this.eligibilityTarget = eligibilityTarget;
    }

    public String getEligibilityMeasure() {
        return eligibilityMeasure;
    }

    public void setEligibilityMeasure(String eligibilityMeasure) {
        this.eligibilityMeasure = eligibilityMeasure;
    }

    public String getEligibilityPeriod() {
        return eligibilityPeriod;
    }

    public void setEligibilityPeriod(String eligibilityPeriod) {
        this.eligibilityPeriod = eligibilityPeriod;
    }

    public String getMinEligibility() {
        return minEligibility;
    }

    public void setMinEligibility(String minEligibility) {
        this.minEligibility = minEligibility;
    }

    public String[] getStepId() {
        return stepId;
    }

    public void setStepId(String[] stepId) {
        this.stepId = stepId;
    }

    public String[] getEligibilityFrom() {
        return eligibilityFrom;
    }

    public void setEligibilityFrom(String[] eligibilityFrom) {
        this.eligibilityFrom = eligibilityFrom;
    }

    public String[] getEligibilityTo() {
        return eligibilityTo;
    }

    public void setEligibilityTo(String[] eligibilityTo) {
        this.eligibilityTo = eligibilityTo;
    }

    public String[] getAmount() {
        return amount;
    }

    public void setAmount(String[] amount) {
        this.amount = amount;
    }


    public String getEligibilityMeasurePer() {
        return eligibilityMeasurePer;
    }

    public void setEligibilityMeasurePer(String eligibilityMeasurePer) {
        this.eligibilityMeasurePer = eligibilityMeasurePer;
    }

    public String[] getDuration() {
        return duration;
    }

    public void setDuration(String[] duration) {
        this.duration = duration;
    }

    public String[] getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String[] paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

   
}
