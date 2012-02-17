/*
 * SalesHierarchyVO.java
 *
 * Created on February 6, 2009, 6:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.sales.vo;

/**
 *
 * @author HY27465
 */
public class SalesHierarchyVO {
    
    /** Creates a new instance of SalesHierarchyVO */
    public SalesHierarchyVO() {
    }
    
    private String parent_salespersonnel_id;

    private String child_salespersonnel_id;

    private String create_date;

    private String modified_date;
    
    private String shop_id;

    public String getParent_salespersonnel_id() {
        return parent_salespersonnel_id;
    }

    public void setParent_salespersonnel_id(String parent_salespersonnel_id) {
        this.parent_salespersonnel_id = parent_salespersonnel_id;
    }

    public String getChild_salespersonnel_id() {
        return child_salespersonnel_id;
    }

    public void setChild_salespersonnel_id(String child_salespersonnel_id) {
        this.child_salespersonnel_id = child_salespersonnel_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }


  
}
