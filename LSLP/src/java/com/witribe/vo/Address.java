/*
 * Address.java
 *
 * Created on March 30, 2011, 12:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author MK64021
 */
public class Address implements java.io.Serializable {
    
    /** Creates a new instance of Address */
    public Address() {
    }
    private int recId;
    private String floorNo;
    private String buildingNo;
    private String streetName;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public String getFloorNo() {
        if(floorNo == null) {
            return "";
        } else {
            return floorNo;
        }
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getBuildingNo() {
         if(buildingNo == null) {
            return "";
        } else {
            return buildingNo;
        }
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getStreetName() {
        if(streetName == null) {
            return "";
        } else {
            return streetName;
        }
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAddress() {
        if(address == null) {
            return "";
        } else {
            return address;
        }
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        if(city == null) {
            return "";
        } else {
            return city;
        }
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        if(state == null) return "";
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        if(country == null) return "";
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        if(zipCode == null) return "";
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
