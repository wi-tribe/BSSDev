/*
 * planList.java
 *
 * Created on January 16, 2010, 9:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author RU47105
 */
public class commissionRule {
    
 /** Creates a new instance of planList */
    private String ruleId;
    private String ruleType;
    private String eligibility_From;
    private String eligibility_To;
    private String amount;
    private String commission_mode;
    private String eligibility_target;
    private String min_eligibility;
    private String target_measure;
    private String commission_type;
    private String eligibility_period;
    private String eligibility_measure;
    private String rule_detail_id; 
    public commissionRule(String Rule_detail_id,String RuleType,String Eligibility_From,String Eligibility_To,String Amount,String Commission_Mode,String Stepid,String ELIGIBILITY_TARGET,String MIN_ELIGIBILITY,String TARGET_MEASURE,String COMMISSION_TYPE,String ELIGIBILITY_PERIOD,String Eligibility_Measure,String RuleId) {
        this.ruleId=RuleId;
        this.rule_detail_id=Rule_detail_id;
        this.ruleType=RuleType;
        this.eligibility_From=Eligibility_From;
        this.eligibility_To=Eligibility_To;
        this.amount=Amount;
        this.commission_mode=Commission_Mode;
        this.eligibility_target=ELIGIBILITY_TARGET;
        this.min_eligibility=MIN_ELIGIBILITY;
        this.target_measure=TARGET_MEASURE;
        this.commission_type=COMMISSION_TYPE;
        this.eligibility_period=ELIGIBILITY_PERIOD;
        this.eligibility_measure=Eligibility_Measure;        
      }
     public String getEligibilty_Measure()
    {
        return eligibility_measure;
    }
    public String getEligibility_Target()
    {
        return eligibility_target;
    }
    public String getMinEligibility()
    {
        return min_eligibility;
    }
	public String getTargetMeasure()
    {
        return target_measure;
    }
	public String getCommission_Type()
    {
        return commission_type;
    }
	public String getEligibility_Period()
    {
        return eligibility_period;
    }

    public String getCommission_Mode()
    {
        return commission_mode;
    }
    public String getRuleId()
    {
        return ruleId;
    }
    public String getRule_Detail_Id()
    {
        return rule_detail_id;
    }
    public String getRuleType()
    {
        return ruleType;
    }
    public String getEligibility_From()
    {
        return eligibility_From;
    }
    public String getEligibility_To()
    {
        return eligibility_To;
    }    
    public String getAmount()
    {
        return amount;
    }    
}
