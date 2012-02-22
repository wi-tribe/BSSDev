<%@page contentType="text/html" import="com.witribe.sales.vo.SalesHierarchyVO,com.witribe.bo.WitribeBO,com.witribe.sales.vo.SalesPersonnelVO,java.util.ArrayList,java.util.List, com.witribe.sales.bo.WitribeSalesBO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>

<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./UpdateSalesHierarchy.do" method="Post">
                <input type="hidden" name="page" />
                <input type="hidden" name="flag" />
                <div id="inner-content">
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <tr><td width="10%" align="left">child Sales Person Id</td>
                            <%try{String[] arr = request.getParameterValues("check");
                            String childid = arr[0];%>
                           <td width="10%" align="left"><select name="child_salespersonnel_id">
                                    <option value="<%=childid%>"><%=childid%></option> 
                                <!--<input type = text name="child_salespersonnel_id" disabled="disabled" value="childid">-->
                            <%}catch(Exception e){}%>
                        </tr> 
                        <% WitribeSalesBO wsBO = new WitribeSalesBO();
                        WitribeBO wb = new WitribeBO();%>
                        <tr><td>Parent Sales Person Id</td>
                            <td width="10%" align="left"><select name="parent_salespersonnel_id">
                                    <%try{
                                    List objList = new ArrayList();
                                    objList = wsBO.SalesforParent(request);
                                    int listSize = objList.size();
                                    SalesPersonnelVO objSPVO = new SalesPersonnelVO();
                                    for (int i=0;i<listSize;i++)                                   
                                    {
                                    objSPVO = (SalesPersonnelVO)objList.get(i);
                                    String parentid = objSPVO.getSalesId()+'-';
                                    parentid = parentid+objSPVO.getFullname()+'-';
                                    parentid = parentid+wb.SalesType(objSPVO.getSalestype());
                                    %> 
                                    <option value="<%=objSPVO.getSalesId()%>"><%=parentid%></option> 
                                    <% } }catch(Exception e){}%>
                                    
                        </select></td></tr>
                        
                        <tr><TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                                               
            </table></div></form>
        </div>
    </div>
</div>
   
            


