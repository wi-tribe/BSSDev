<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script language="javascript" type="text/javascript">
    function bodyload(){
            document.getElementById('shoprow').style.display = "none";
            document.getElementById('childsalespersonnelid').style.display = "none";
            document.getElementById('submitbutton').style.display = "none";
            }
</script>
<body onload="bodyload();">
    <div id="inner-content">
        <div class="shadow-box">
            <div class="shadow-box-inner"> 
                <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
                <form action="./SalesHierarchyDetails.do" name="saleshierarchy" method="post">
                    <input type="hidden" name="page" />
                    <input type="hidden" name="flag" />
                    
                    <% WitribeSalesBO wsBO = new WitribeSalesBO();
                    WitribeBO wb = new WitribeBO();%>
                    <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                        <tr><td><label>Manager&nbsp;Sales&nbsp;Id</label><span class="mandatory">*</span></td>
                            <td><select name="parent_salespersonnel_id" id ="test" onchange="getShopListforSales(this.options[this.options.selectedIndex].id,this.value)">
                                    <option></option>
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
                                    <option  id="<%=wb.SalesType(objSPVO.getSalestype())%>" value="<%=objSPVO.getSalesId()%>"><%=parentid%></option> 
                                    <% } }catch(Exception e){}%>
                                    <!--    <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>-->
                        </select>
                     <!--   <label id="noparents" style="{display:none}">No parents available for mapping.</label>-->
                    </td></tr>
                        <tr id="shoprow" style="{display:none}"><td><label id="shoplabel">Shop ID</label><span class="mandatory">*</span></td>
                            <td><select name="shop_id" id="shopid">
                                    
                               <!--     <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>--> 
                        </select>
                        <label id="noshops" style="{display:none}">No shops mapped for the salesid.</label></td>
                    </tr>
                        <tr><td><label>Sales Person Id</label><span class="mandatory">*</span></td>
                            
                            <%try{
                            List objList = new ArrayList();
                            objList = wsBO.SalesforChild(request);
                            int listSize = objList.size();
                            SalesPersonnelVO objSPVO = new SalesPersonnelVO();
                          //  wsBO.SalesforChildbyLead("2","RSM");
                            if(listSize >= 0) {%>
                            <td><select name="child_salespersonnel_id" id="childsalespersonnelid" style="{display:none}">
                                    <%for (int i=0;i<listSize;i++)                                   
                                    {
                                    objSPVO = (SalesPersonnelVO)objList.get(i);
                                    String salesid = objSPVO.getSalesId()+'-';
                                    salesid = salesid+objSPVO.getFullname()+'-';
                                    salesid = salesid+wb.SalesType(objSPVO.getSalestype());
                                    %> 
                                    <option value="<%=objSPVO.getSalesId()%>"><%=salesid%></option> 
                                    <% }//for
                                    %>
                        </select>
                        <label id="nochilds" >No Sales Personnel available for mapping.</label>
                    </td>
                        
                        </tr>
                        <TR>
                            <TD>&nbsp;</TD>
                            <TD align=left><INPUT class=submit-btn 
                                                      onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                      onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                      src="images/btn-submit-thickbox.gif" 
                                              name=input id="submitbutton" STYLE="{display:none}"> </TD></TR>
                        <%}//if
                        else{%>
                        <td><label>No child's available for hierarchy</label></td>  
                        <%}//else
                        }//try
                        catch(Exception e){}%>
                        <!-- <option value="1">1</option>
                                        <option value="2" selected>2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>-->
                    
                    
                    
                    
                    </table>
                    
            </form></div> 
        </div>
    </div>
</body>




