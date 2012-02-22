<%@ page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.inventory.bo.WitribeInventoryBO,com.witribe.sales.vo.ShopsVO,java.util.ArrayList,java.util.List,com.witribe.inventory.vo.VoucherVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
    <script language="javascript" type="text/javascript">
   function bodyLoad(){
   var val = document.getElementById('resType').value;
   callAjaxForVoucherInfo(val);
   }
   </script>
   <body onload="javascript:bodyLoad();">
     <p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
        <div id="inner-content">
            <div class="shadow-box">
                <div class="shadow-box-inner">
                    <form action="./VoucherRequest.do" method="Post" onsubmit="return ValidateRaiseVoucherForm(this);">
                        <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                            <tr><td><label>Voucher Type</label><span class="mandatory">*</span></td>
                                <td><select name="voucherType">
                                        <option value="1">Logical Voucher</option>
                                        <option value="2">Physical Voucher</option>
                                        </select></td></tr>
                            <tr><td><label>Resource Type</label><span class="mandatory">*</span></td>
                                <td><select name="resType" id="resType" onchange="callAjaxForVoucherInfo(this.value)">
                                        <option value="1" selected>Currency</option>
                                        <option value="2">Non-Currrency</option>
                            </select></td></tr>

                            <tr><td><label>Resource Value</label><span class="mandatory">*</span></td>
                                <td><select name="voucherInfo" id="voucherInfo">
                                        
                            </select></td> </tr>
                            <tr><td><label>For Shop</label><span class="mandatory">*</span></td>
                               <logic:notEmpty name="objShopList" >
                                <td><select name="shopId">
                                        <logic:iterate id="objShop" name="objShopList" type="com.witribe.sales.vo.ShopsVO" scope="request">
                                        
                                        <option value="${objShop.shopId}">${objShop.shopId}-${objShop.shopname}</option> 
                                        
                                    </logic:iterate>
                            </select></td></logic:notEmpty>  
                        <logic:empty name="objShopList">
                            
                            <td colspan="4"><b>There are no shops to this TL !</b></td>
                            
                        </logic:empty>
                             <tr><td><label>Quantity Required</label><span class="mandatory">*</span></td>
                             <td><input type="text" name="quantity" value="" class="txtxx" maxlength = "3"><p class="validationmsg"></p></td>
                           <tr>
                                   
                        <TR>
                            <TD>&nbsp;</TD>
                                <TD align=left><INPUT class=submit-btn 
                                              onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                              onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                              src="images/btn-submit-thickbox.gif" 
                                      name=input> </TD></TR>
                                     
                   </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
 

