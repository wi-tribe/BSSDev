/*
 * SalesChannel.java
 *
 * Created on January 16, 2010, 9:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.vo;

/**
 *
 * @author SC43278
 */
public class SalesChannel {
    
    /** Creates a new instance of SalesChannel */
    public SalesChannel() {
       iChannelType = -1;
       sChannelName = "";
    }
    public SalesChannel(int ichid, String sChName) {
        iChannelType = ichid;
       sChannelName = sChName;
    }
    public int getChannelId() {
        return iChannelType;
    }
    public String getChannelName() {
        return sChannelName;
    }
    private int iChannelType;
    private String sChannelName;
}
