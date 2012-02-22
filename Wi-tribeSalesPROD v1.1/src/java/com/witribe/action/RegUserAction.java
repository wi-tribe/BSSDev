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
import com.witribe.actionform.UserRegForm;
import com.witribe.bo.WitribeBO;
import com.witribe.exception.DuplicateUserException;
import com.witribe.vo.UserRegVO;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;

public class RegUserAction extends BaseAction{
    
    /** Creates a new instance of RegUserAction */
    public RegUserAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res){
        UserRegForm uform= (UserRegForm)form; 
          if(!validateSession(req,res) || 
                !validateAdminRole(req,res)) {
            return mapping.findForward("login");
        }
        try{
        if(createUser(uform)) {
            req.setAttribute(WitribeConstants.HEADING,WitribeConstants.USER_REGISTRATION_COMPLETED);
            return mapping.findForward("success");
        }
        }catch(DuplicateUserException DE) {
           req.setAttribute(WitribeConstants.ERROR_STRING,WitribeConstants.USER_NAME+uform.getUserId()+WitribeConstants.ALREADY_EXISTS);
           return mapping.findForward("adduser") ;
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
       req.setAttribute(WitribeConstants.HEADING,WitribeConstants.USER_REGISTRATION_FAILED);
       return mapping.findForward("failure") ;
             
    }
    boolean createUser(UserRegForm uform)throws SQLException,DuplicateUserException,Exception{
        UserRegVO objUserRegVO = new UserRegVO();
        objUserRegVO.setName(uform.getName());
        objUserRegVO.setEmail(uform.getEmail());
        objUserRegVO.setMobile(uform.getMobile());
        objUserRegVO.setArea(uform.getArea());
        objUserRegVO.setRole(uform.getRole());
        objUserRegVO.setUserId(uform.getUserId());
        objUserRegVO.setPassword(uform.getPassword());
        WitribeBO objBO= new WitribeBO();
        boolean status = objBO.createUser(objUserRegVO);
        return status;
        
    }
    
    
}
