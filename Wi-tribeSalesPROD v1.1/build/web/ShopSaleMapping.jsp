<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List,com.witribe.sales.vo.SalesPersonnelVO,com.witribe.sales.vo.ShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<script>
   function bodyLoad(){
   
   var val = document.getElementById('shop_id').value;
   callAjaxForTLtoShop(val);
   }
</script>
<body onload="bodyLoad();">
 <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner"> 
           <form action="./AssignShopToSale.do" method="post">
                <input type="hidden" name="page" />
                <input type="hidden" name="flag" />
                <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                     <logic:notEmpty name="objShopsList">  
                         <tr><td><label>Shop</label><span class="mandatory">*</span></td>
                            <logic:notEmpty name="objShopsList">
                            <td><select id = "shop_id" name="shop_id" onchange="callAjaxForTLtoShop(this.value)">   
                                    <!--<option value=""></option>-->
                                    <logic:iterate id="objShop" name="objShopsList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        
                                        <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option> 
                                        
                                    </logic:iterate>  
                                    
                        </select></td></tr>

                        <!--<tr><td><label>Shop Zone</label></td>
                        <td><input type="text" id="zone" class=txtxx size="25"  readonly disabled /></td></tr>-->

                    </logic:notEmpty>
                    
                   
                    <!--<option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>-->
                    <tr><td><label>Shop Zone</label></td>
                        <td><input type="text" id="zone" value="" class=txtxx size="25"  readonly disabled /></td></tr>
                    <tr><td><label>Sales Person</label><span class="mandatory">*</span></td>
                        <td><select name="salespersonnel_id" id="salespersonnel_id"> 
                            </select></td></tr> 
                    
                    <%--<%try{
                                List objList = new ArrayList();
                                objList = wsBO.shopsForSalesId(request);
                                int listSize = objList.size();
                                SalesPersonnelVO objSPVO = new SalesPersonnelVO();
                                if(listSize > 0) {%>
                                <td><select name="salespersonnel_id">
                                <%for (int i=0;i<listSize;i++)                                   
                                {
                                objSPVO = (SalesPersonnelVO)objList.get(i);
                                String childid = objSPVO.getSalesId()+'-';
                                childid = childid+objSPVO.getFullname()+'-';
                                childid = childid+wb.SalesType(objSPVO.getSalestype());
                                %> 
                                <option value="<%=objSPVO.getSalesId()%>"><%=childid%></option> 
                                <% }//for
                                %>--%>
                    
                    <TR>
                        <TD>&nbsp;</TD>
                       
                        <TD align=left><INPUT class=submit-btn 
                                                  onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                  onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                  src="images/btn-submit-thickbox.gif" 
                                                 
                                          name=input> </TD></TR>
                   </logic:notEmpty>
                      
                    <logic:empty name="objShopsList">
                        <td><label>No shop's available for TL mapping.</label></td>
                    </logic:empty>
                    
                    
                </table>
                
        </form></div> 
    </div>
</div>
</body>



