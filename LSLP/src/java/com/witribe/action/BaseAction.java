/*
 * SubmitLeadAction.java
 *
 * Created on January 7, 2009, 3:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;

/**
 *
 * @author SC43278
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;

public abstract class BaseAction extends Action{
    
    /** Creates a new instance of SubmitLeadAction */
    public BaseAction() {
    }
    public abstract ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws SQLException,Exception;
    public boolean validateSession(HttpServletRequest req,HttpServletResponse res){
        HttpSession userSession = (HttpSession)req.getSession(false);
        if(userSession == null) {
            req.setAttribute("errorString",WitribeConstants.SESSION_TIMEDOUT);
            return false;
        } else if(userSession.getAttribute("username")== null) { 
                    
            req.setAttribute("errorString",WitribeConstants.SESSION_TIMEDOUT);
            return false;
        }
        return validateUrlAccess(req,res);
    } 
    public boolean validateAdminRole(HttpServletRequest req,HttpServletResponse res){
        HttpSession userSession = (HttpSession)req.getSession(false);
        if(userSession == null) {
            req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.SESSION_TIMEDOUT);
            return false;
        } else if(userSession.getAttribute("username")== null) {
            req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.SESSION_TIMEDOUT);
            return false;
        } else if(userSession.getAttribute("role") == null) {
            req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.SESSION_TIMEDOUT);
            return false;
        }
        String role = (String)userSession.getAttribute("role");
        if(WitribeConstants.CSE.equalsIgnoreCase(role)) {
            req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.AUTHORIZE_CHECK);
            return false;
        }
        return true;
    } 
    public boolean validateUrlAccess(HttpServletRequest req,HttpServletResponse res){
        HttpSession userSession = (HttpSession)req.getSession(false);
        String url = req.getRequestURI();
        
        url = url.substring(url.lastIndexOf("/"),url.length());
        if(req.getQueryString()!=null){
            url+="?"+req.getQueryString();
        }
    
        if(userSession.getAttribute("role") == null) {
            req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.SESSION_TIMEDOUT);
            return false;
        }
        String role = (String)userSession.getAttribute("role");
        if(WitribeConstants.TYPE_CSE.equalsIgnoreCase(role)) {
            if(!url.equals("/CreateLead.do") && 
                    !url.equals("/Registerlead.do") && 
                    !url.equals("/Prospect.do") && 
                    !url.contains("/ViewLead.do") && 
                    !url.contains("/LeadAccount.do") &&
                    !url.contains("/Search.do") &&
                    !url.contains("/SearchLead.do") &&
                    !url.equals("/ViewSales.do?viewProfile=true") &&
                    !url.contains("/GetReason.do") && 
                    !url.contains("/GetJobStatus.do") && 
                    !url.contains("/GetMore.do") && 
                    !url.contains("/RegisterProspect.do") && 
                    !url.contains("/UpdateStatus.do") && 
                    !url.equals("/ModifySalesPersonnel.do")&&

                    !url.equals("/AccountDetails.do")&&
                    !url.equals("/ViewAccountDetails.do")&&

                    !url.equals("/RegisterAccount.do?siteId=SC") &&
                     !url.equals("/LeadHistory.do") &&

                    !url.equals("/ViewInventory.do" )) {

            req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.AUTHORIZE_CHECK);
            return false;
            }
        } else if(WitribeConstants.TYPE_BO.equalsIgnoreCase(role) 
                || WitribeConstants.TYPE_NBO.equalsIgnoreCase(role)) {
            if(!url.equals("/ViewSales.do?viewProfile=true")) {
                req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.AUTHORIZE_CHECK);
                return false;
            }
        }
        return true;
    }
    public boolean checkGuest(HttpServletRequest req,HttpServletResponse res){
       String siteId = req.getParameter("siteId");
        if(siteId == null) {
            req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.SESSION_TIMEDOUT);
            return false;
        } else if(siteId.equals("SC")) { 
            return true;
        }
        return false;
    }
    
}
