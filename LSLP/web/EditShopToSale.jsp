<%@page contentType="text/html" import="com.witribe.sales.vo.SalesHierarchyVO,com.witribe.sales.vo.ShopsVO,com.witribe.bo.WitribeBO,com.witribe.sales.vo.SalesPersonnelVO,java.util.ArrayList,java.util.List, com.witribe.sales.bo.WitribeSalesBO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>

<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./ModifyShopToSales.do" method="Post">
                <input type="hidden" name="page" />
                <input type="hidden" name="flag" />
                 <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                                 <tr><td width="126" align="left"><label>Shop Id</label></td>
                                     <%ShopsVO objShopVO = new ShopsVO();
                                     try{String[] arr = request.getParameterValues("check");
                                     //added by bhawna on 8th october 2009
                                     String child = arr[0];
                                     String[] temp = child.split("~");
                                     String childid = temp[0];
                                     String zone = temp[1];
                                     objShopVO.setShopId(childid);                                 
                                     //end
                                     %>
                                     <td width="200" align="left"><select name="shop_id">
                                         <option value="<%=childid%>"><%=childid%></option> 
                                     </select>
                                 <!--Added by bhawna on 8th october-->
                                 <tr><td width="126"><label>Zone</label></td>
                                <td width="200"><input type="text" class="txtxx" name="zone" value="<%=zone%>" readonly disabled/><p class="validationmsg"></p></td>
                                 <!--end-->

                                <!--<input type = text name="child_salespersonnel_id" disabled="disabled" value="childid">-->
                            <%}catch(Exception e){}%>
                        </tr> 
                        <% WitribeSalesBO wsBO = new WitribeSalesBO();
                        WitribeBO wb = new WitribeBO();%>

                           <!--<tr><td width="126"><label>Zone</label><span class="mandatory"></span></td>
        <td width="200"><input type="text" class="txtxx" name="zone" value="" maxlength="30" /><p class="validationmsg"></p></td>-->

                        <tr><td width="134"><label>Sales Person Id</label></td>
                            <td width="180" align="left"><select name="salespersonnel_id" id="salespersonnel_id">
                                    <%try{
                                    List objList = new ArrayList();
                                    objList = wsBO.getUnAssignedTLCSE(objShopVO);//edited by bhawna on 13th october,2009
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
                        
                        <tr>
                                 <TD>&nbsp;</TD>
                                 
                                <TD align="left" ><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input></TD> </tr>
            </table></form>
        </div>
    </div>
</div>
   
            


