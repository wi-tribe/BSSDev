/*
 * MainClass.java
 *
 * Created on January 16, 2009, 5:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author HP43196
 */
public class MainClass {
    
    /** Creates a new instance of MainClass */
    public MainClass() {
    }


  public static void main(String[] args) {
    java.util.Date today = new java.util.Date();
    System.out.println(new java.sql.Date(today.getTime()));
    java.sql.Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
    System.out.println(sqlDate);

 }
    void stringToDate(){
        try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
      String date = "2003/01/10";
      java.util.Date utilDate = formatter.parse(date);
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
      System.out.println("date:" + date);
      System.out.println("sqlDate:" + sqlDate);
    } catch (ParseException e) {
      System.out.println(e.toString());
      e.printStackTrace();
    }

    }
}
