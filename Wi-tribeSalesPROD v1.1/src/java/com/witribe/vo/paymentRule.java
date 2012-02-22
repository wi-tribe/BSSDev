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
 * @author mp52081
 */
public class paymentRule {
    
    /** Creates a new instance of planList */
    private String payruleId;
    private String duration_type;
    private String duration;
    private String amount;
    private String ruletype;
    public paymentRule(String payruleId,String Duration_Type,String Duration,String Amount,String Rule_Type) {
    this.payruleId=payruleId;
    this.duration_type=Duration_Type;
    this.duration=Duration;
    this.amount=Amount;
    this.ruletype=Rule_Type;

      }
    public String getPayruleId()
    {
        return payruleId;
    }
    public String getDuration_Type()
    {
        return duration_type;
    }
    public String getDuration()
    {
        return duration;
    }
    public String getAmout()
    {
        return amount;
    }    
     public String getRuleType()
    {
        return ruletype;
    }  
}
