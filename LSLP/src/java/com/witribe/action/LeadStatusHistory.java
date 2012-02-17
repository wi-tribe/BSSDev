/*
 * LeadStatusHistory.java
 *
 * Created on September 7, 2009, 3:25 PM
 */

package com.witribe.action;

import com.witribe.actionform.LeadEntryForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import com.witribe.vo.LeadHistoryVO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author BS68483
 * @version
 */

public class LeadStatusHistory extends BaseAction {
    
    /* forward name="success" path="" */
    
    public LeadStatusHistory() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        LeadEntryForm lform = (LeadEntryForm)form;
        WitribeBO objBO= new WitribeBO();
        HttpSession userSession =  (HttpSession)req.getSession(false);
        try{
            if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            //List objList = null;
            LeadHistoryVO objLeadStatusVO= new LeadHistoryVO();
            
            objLeadStatusVO.setLeadID(lform.getLeadId());
            List statusList = new ArrayList();
            statusList = objBO.getLeadStatusHistory(objLeadStatusVO);
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.SHOW_LEAD_HISTORY);
            //System.out.println(statusList.size());
            req.setAttribute("objLeadHistory",statusList);
            return mapping.findForward("success");
            
        } catch(SQLException e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass());
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL);
            req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG);
            
            e.printStackTrace();
            
            return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
            LogUtil.error("Exception "+e.getMessage(),this.getClass());
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL);
            req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG);
            e.printStackTrace();
            return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
    }
}

