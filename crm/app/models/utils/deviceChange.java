/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models.utils;

/**
 *
 * @author PKAasimN
 */
public class deviceChange {

    public String accountno;
    public String mac;
    public String Removalreason;


 public String getRemovalreason() {
        return Removalreason;
    }

    public void setRemovalreason(String Removalreason) {
        this.Removalreason = Removalreason;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}