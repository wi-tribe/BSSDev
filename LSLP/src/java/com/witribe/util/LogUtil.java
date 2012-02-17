package com.witribe.util;
import org.apache.log4j.Logger;

public class LogUtil {
    public static void info(String messgae,Class className) {
		Logger.getLogger(className).info(messgae);
    }  
    public static void debug(String messgae,Class className) {
		Logger.getLogger(className).debug(messgae);
    }
    public static void warn(String messgae,Class className) {
		Logger.getLogger(className).warn(messgae);
    }
    public static void error(String messgae,Class className) {
		Logger.getLogger(className).error(messgae);
    }
    public static void fatal(String messgae,Class className) {
		Logger.getLogger(className).fatal(messgae);
    }
    public static void info(String messgae,Class className, Throwable t) {
		Logger.getLogger(className).info(messgae,t);
    }  
    public static void debug(String messgae,Class className, Throwable t) {
		Logger.getLogger(className).debug(messgae,t);
    }
    public static void warn(String messgae,Class className, Throwable t) {
		Logger.getLogger(className).warn(messgae,t);
    }
    public static void error(String messgae,Class className, Throwable t) {
		Logger.getLogger(className).error(messgae,t);
    }
    public static void fatal(String messgae,Class className, Throwable t) {
		Logger.getLogger(className).fatal(messgae,t);
    }
}