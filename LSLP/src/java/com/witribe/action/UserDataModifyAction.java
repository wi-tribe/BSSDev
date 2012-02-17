/*
 * UserDataModifyAction.java
 *
 * Created on January 27, 2009, 5:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;
import com.witribe.vo.UserRegVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.actionform.UserRegForm;
import com.witribe.bo.WitribeBO;
import java.sql.SQLException;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
/**
 *
 * @author SC43278
 */
public class UserDataModifyAction extends BaseAction{
    
    /** Creates a new instance of UserDataModifyAction */
    public UserDataModifyAction() {
    }
     public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) {
        UserRegForm uform= (UserRegForm)form; 
        try{
             if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
            if(createUser(uform)) {
                req.setAttribute(WitribeConstants.HEADING,WitribeConstants.USER_DATA_MODIFIED);
                return mapping.findForward("success");
            }
        } catch(SQLException e) {
              LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
              req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
              req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
              return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        } catch(Exception e) {
               LogUtil.error("Exception "+e.getMessage(),this.getClass()); 
               req.setAttribute(WitribeConstants.HEADING,WitribeConstants.HEADING_APP_FAIL); 
               req.setAttribute(WitribeConstants.ERR_VAR,WitribeConstants.APP_FAIL_MSG); 
               return mapping.findForward(WitribeConstants.APP_FAIL_FWD);
        }
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.FAIL_MODIFY_USER);
       return mapping.findForward("failure") ;
    }
    boolean createUser(UserRegForm uform)throws SQLException,Exception{
        UserRegVO objUserVO = new UserRegVO();
        objUserVO.setUserId(uform.getUserId());
        WitribeBO objBO= new WitribeBO();
        boolean status = objBO.createUser(objUserVO);
        return status;
          
    }
}
