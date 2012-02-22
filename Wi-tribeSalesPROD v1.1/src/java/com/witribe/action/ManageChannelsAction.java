/*
 * ManageChannelsAction.java
 *
 * Created on January 16, 2010, 7:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 *
 * @author SC43278
 */
public class ManageChannelsAction extends org.apache.struts.action.Action {
    
    /** Creates a new instance of ManageChannelsAction */
    public ManageChannelsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        return mapping.findForward("managechannelsaction");
    }
    
    
}
