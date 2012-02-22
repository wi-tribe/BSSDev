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
public class planList {
    
    /** Creates a new instance of planList */
    private String planId;
    private String cityZone;
    private String plan_type;
    private String applies_to;
    private String status;
    public String valid_from; //--Declared By Murali
    public String valid_to;//--Declared By Murali
    

    public planList(String Planid,String CityZone,String Plan_type,String Applies_to,String Status,String Valid_From,String Valid_To) {
        this.planId=Planid;
        this.cityZone=CityZone;
        this.plan_type=Plan_type;
        this.applies_to=Applies_to;
        this.status=Status;
        this.valid_to = Valid_To;
        this.valid_from = Valid_From;        
      }
    public String getPlanId()
    {
        return planId;
    }
    public String getCityZone()
    {
        return cityZone;
    }
    public String getPlanType()
    {
        return plan_type;
    }
    public String getAppliesTo()
    {
        return applies_to;
    }    
    public String getStatus()
    {
        return status;
    }   
    public String getValidFrom() {
        return valid_from;
    }

    public String getValidTo() {
        return valid_to;
    }
}
