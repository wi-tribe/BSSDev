/*
 * ViewUsers.java
 *
 * Created on January 27, 2009, 3:29 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.witribe.action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.witribe.actionform.UserRegForm;
import com.witribe.bo.WitribeBO;
import com.witribe.constants.WitribeConstants;
import com.witribe.util.LogUtil;
import com.witribe.vo.UserRegVO;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author SC43278
 */
public class ViewUsersAction extends BaseAction {
    
    /** Creates a new instance of ViewUsers */
    public ViewUsersAction() {
    }
    public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest req,HttpServletResponse res) throws Exception{
        UserRegForm uform = (UserRegForm)form;
      try{
               if(!validateSession(req,res)) {
                return mapping.findForward("login");
            }
              String forwardName = "";
               WitribeBO objBO= new WitribeBO();
              if(uform.getUserId() != null) {
                  UserRegVO objUserVO = objBO.getUser(uform.getUserId());
                  req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_USER);
                  req.setAttribute("objuser",objUserVO);
                  forwardName = "showuser";
              } else {
                   List objList = objBO.getUsers();
                   req.setAttribute(WitribeConstants.HEADING,WitribeConstants.VIEW_USERS);
                   int listSize = objList.size();
                   if((listSize > 0 ) && (objList.get(listSize-1) instanceof String)) {
                    req.setAttribute("next",objList.get(listSize-1));
                    objList.remove(listSize-1);
                    }
                   req.setAttribute("objUserList",objList);
                   req.setAttribute("page","0");
                   forwardName = "viewusers";
              }

              return mapping.findForward(forwardName); 
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
          
        }  
    }
   
    

