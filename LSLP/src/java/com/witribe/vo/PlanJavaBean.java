/*
 * PlanJavaBean.java
 *
 * Created on March 30, 2011, 1:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author MK64021
 */
public class PlanJavaBean {
    
     private String plans;
    private String planId;
    private String txPlanName;
    private String txOfferId;
    private String cycleLength;
    private String planSpeed;
    private String txVLANId;
    private String txMaxLogin;
    private String chAutoRenewal;
    private String chBlackList;
    private String netSpanServiceProfile;
    private String aptiloAccessProfile;
    private String txPromoMsg;
    private int[] SelectedMarkets;
    private int[] selectedSalesChannels;
    private int[] SelectedBusinessType;
    private String[] SelectedAccessChannels;
    private int[]  SelectedPlanThresdolds;
    private String[] SelectedAptiloProfiles;
    private int[] SelectedContractPeriods;
    private float[] selectedContractFee;
    private String serviceType;
    private String  ratingMode;
    private String  refDisFlag;
    private String  dayTimeOffer;
    private String txtDunCanFee;
    private String txtretDepMonths;
    private String txtPlanSecSpeed;
    private String txtDayStartHrs;
    private String txtNightStartHrs;
    private String[] selectedUsersList;
    
    
    /** Creates a new instance of PlanJavaBean */
    public PlanJavaBean() {
    }
    public String getPlans() {
        return plans;
    }
    
    public void setPlans(String plans) {
        this.plans = plans;
    }
    
    public String getPlanId() {
        return planId;
    }
    
    public void setPlanId(String planId) {
        this.planId = planId;
    }
    
    public String getTxPlanName() {
        return txPlanName;
    }
    
    public void setTxPlanName(String txPlanName) {
        this.txPlanName = txPlanName;
    }
    
    public String getTxOfferId() {
        return txOfferId;
    }
    
    public void setTxOfferId(String txOfferId) {
        this.txOfferId = txOfferId;
    }
    
    public String getCycleLength() {
        return cycleLength;
    }
    
    public void setCycleLength(String cycleLength) {
        this.cycleLength = cycleLength;
    }
    
    public String getPlanSpeed() {
        return planSpeed;
    }
    
    public void setPlanSpeed(String planSpeed) {
        this.planSpeed = planSpeed;
    }
    
    public String getTxVLANId() {
        return txVLANId;
    }
    
    public void setTxVLANId(String txVLANId) {
        if(txVLANId == null ) 
            this.txVLANId = "";
        else 
        this.txVLANId = txVLANId;
    }
    
    public String getTxMaxLogin() {
        return txMaxLogin;
    }
    
    public void setTxMaxLogin(String txMaxLogin) {
        this.txMaxLogin = txMaxLogin;
    }
    
    public String getChAutoRenewal() {
        return chAutoRenewal;
    }
    
    public void setChAutoRenewal(String chAutoRenewal) {
        this.chAutoRenewal = chAutoRenewal;
    }
    
    public String getChBlackList() {
        return chBlackList;
    }
    
    public void setChBlackList(String chBlackList) {
        this.chBlackList = chBlackList;
    }
    
    public String getNetSpanServiceProfile() {
        return netSpanServiceProfile;
    }
    
    public void setNetSpanServiceProfile(String netSpanServiceProfile) {
        this.netSpanServiceProfile = netSpanServiceProfile;
    }
    
    public String getAptiloAccessProfile() {
        return aptiloAccessProfile;
    }
    
    public void setAptiloAccessProfile(String aptiloAccessProfile) {
        this.aptiloAccessProfile = aptiloAccessProfile;
    }
    
    public String getTxPromoMsg() {
        return txPromoMsg;
    }
    
    public void setTxPromoMsg(String txPromoMsg) {
        this.txPromoMsg = txPromoMsg;
    }
    
    public int[] getSelectedMarkets() {
        return SelectedMarkets;
    }
    
    public void setSelectedMarkets(int[] SelectedMarkets) {
        this.SelectedMarkets = SelectedMarkets;
    }
    
    public int[] getSelectedSalesChannels() {
        return selectedSalesChannels;
    }
    
    public void setSelectedSalesChannels(int[] selectedSalesChannels) {
        this.selectedSalesChannels = selectedSalesChannels;
    }
    
    public int[] getSelectedBusinessType() {
        return SelectedBusinessType;
    }
    
    public void setSelectedBusinessType(int[] SelectedBusinessType) {
        this.SelectedBusinessType = SelectedBusinessType;
    }
    
    public String[] getSelectedAccessChannels() {
        return SelectedAccessChannels;
    }
    
    public void setSelectedAccessChannels(String[] SelectedAccessChannels) {
        this.SelectedAccessChannels = SelectedAccessChannels;
    }
    
    public int[] getSelectedPlanThresdolds() {
        return SelectedPlanThresdolds;
    }
    
    public void setSelectedPlanThresdolds(int[] SelectedPlanThresdolds) {
        this.SelectedPlanThresdolds = SelectedPlanThresdolds;
    }
    
    public String[] getSelectedAptiloProfiles() {
        return SelectedAptiloProfiles;
    }
    
    public void setSelectedAptiloProfiles(String[] SelectedAptiloProfiles) {
        this.SelectedAptiloProfiles = SelectedAptiloProfiles;
    }
    
    public int[] getSelectedContractPeriods() {
        return SelectedContractPeriods;
    }
    
    public void setSelectedContractPeriods(int[] SelectedContractPeriods) {
        this.SelectedContractPeriods = SelectedContractPeriods;
    }
    
    public String getServiceType() {
        return serviceType;
    }
    
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    
    public String getRatingMode() {
        return ratingMode;
    }
    
    public void setRatingMode(String ratingMode) {
        this.ratingMode = ratingMode;
    }
    
    public String getRefDisFlag() {
        return refDisFlag;
    }
    
    public void setRefDisFlag(String refDisFlag) {
        this.refDisFlag = refDisFlag;
    }
    
    public String getDayTimeOffer() {
        return dayTimeOffer;
    }
    
    public void setDayTimeOffer(String dayTimeOffer) {
        this.dayTimeOffer = dayTimeOffer;
    }
    
    public String getTxtDunCanFee() {
        return txtDunCanFee;
    }
    
    public void setTxtDunCanFee(String txtDunCanFee) {
        this.txtDunCanFee = txtDunCanFee;
    }
    
    public String getTxtretDepMonths() {
        return txtretDepMonths;
    }
    
    public void setTxtretDepMonths(String txtretDepMonths) {
        this.txtretDepMonths = txtretDepMonths;
    }
    
    public String getTxtPlanSecSpeed() {
        return txtPlanSecSpeed;
    }
    
    public void setTxtPlanSecSpeed(String txtPlanSecSpeed) {
        this.txtPlanSecSpeed = txtPlanSecSpeed;
    }
    
    public String getTxtDayStartHrs() {
        return txtDayStartHrs;
    }
    
    public void setTxtDayStartHrs(String txtDayStartHrs) {
        this.txtDayStartHrs = txtDayStartHrs;
    }
    
    public String getTxtNightStartHrs() {
        return txtNightStartHrs;
    }
    
    public void setTxtNightStartHrs(String txtNightStartHrs) {
        this.txtNightStartHrs = txtNightStartHrs;
    }

    public String[] getSelectedUsersList() {
        return selectedUsersList;
    }

    public void setSelectedUsersList(String[] selectedUsersList) {
        this.selectedUsersList = selectedUsersList;
    }

    public float[] getSelectedContractFee() {
        return selectedContractFee;
    }

    public void setSelectedContractFee(float[] selectedContractFee) {
        this.selectedContractFee = selectedContractFee;
    }
    
    
}
