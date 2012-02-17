/*
 * RegService.java
 *
 * Created on January 2, 2011, 2:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author Administrator
 */
public class RegService implements java.io.Serializable {
    
    /** Creates a new instance of RegService */
    public RegService() {
    }
    private String svsType;
    private String svsLogin;
    private String svsStatus;

    public String getSvsType() {
        return svsType;
    }

    public void setSvsType(String svsType) {
        this.svsType = svsType;
    }

    public String getSvsLogin() {
        return svsLogin;
    }

    public void setSvsLogin(String svsLogin) {
        this.svsLogin = svsLogin;
    }

    public String getSvsStatus() {
        return svsStatus;
    }

    public void setSvsStatus(String svsStatus) {
        this.svsStatus = svsStatus;
    }
}
