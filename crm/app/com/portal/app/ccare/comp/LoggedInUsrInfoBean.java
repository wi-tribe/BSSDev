/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.app.ccare.comp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.utils.TaskDetails;

import com.portal.pcm.Poid;

/**
 *
 * @author PKAasimN
 */
public class LoggedInUsrInfoBean {

    private String CSRID;
    private Poid AccoutNo;
    private String Source;
    private String FirstName;
    private String LastName;
    private String Title;
    private String Email;
    private Enum Type;

    public Poid getAccoutNo() {
        return AccoutNo;
    }

    public void setAccoutNo(Poid AccoutNo) {
        this.AccoutNo = AccoutNo;
    }

    public String getCSRID() {
        return CSRID;
    }

    public void setCSRID(String CSRID) {
        this.CSRID = CSRID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public Enum getType() {
        return Type;
    }

    public void setType(Enum Type) {
        this.Type = Type;
    }
}




