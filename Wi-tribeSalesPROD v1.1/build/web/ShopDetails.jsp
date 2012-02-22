<%@page contentType="text/html" import="com.witribe.bo.WitribeBO,com.witribe.sales.bo.WitribeSalesBO,java.util.ArrayList,java.util.List, com.witribe.vo.CityVO, com.witribe.vo.AddressMappingVO, com.witribe.vo.ZoneVO, com.witribe.vo.SubzoneVO,com.witribe.sales.vo.BRMShopsVO"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<p>Fields marked with an asterisk (<span class="mandatory">*</span>) are Mandatory.</p>
<div id="inner-content">
    <div class="shadow-box">
        <div class="shadow-box-inner">
            <form action="./ShopDetails.do" method="post" name="shopform" onsubmit="javascript: return ValidateShopForm(this);">
                <%WitribeBO wbo = new WitribeBO();
                WitribeSalesBO wsBO = new WitribeSalesBO();
                WitribeBO wb = new WitribeBO();
                String prov = null;%>
                <table width="642" border="0" cellspacing="0" cellpadding="0" class="info-listing">
                    <tr><td width="126"><label>Shop Id</label><span class="mandatory">*</span></td>
                        <!--<input type="text" name="shopId" class=txtxx value="" maxlength="20"/><p class="validationmsg"></p>-->
                        <td><select name="shopId">
                            <%try{
                            List objList = new ArrayList();
                            objList = wsBO.fetchBRMShops();
                            int listSize = objList.size();
                            BRMShopsVO svo = new BRMShopsVO();
                            for (int i=0;i<listSize;i++)                                   
                            {
                            svo = (BRMShopsVO)objList.get(i);
                            String shopid = svo.getSubInventoryCode()+'-';
                            shopid=shopid+svo.getSubInventoryDesc();
                            %> 
                            <option  value="<%=svo.getSubInventoryCode()%>"><%=shopid%></option> 
                            <% } }catch(Exception e){}%>
                        <p class="validationmsg"></p></td>
                    <td width="126"><label>Shop Name</label><span class="mandatory">*</span></td>
                        <td width="200"><input type="text" name="shopname" class=txtxx value="" maxlength="30"/><p class="validationmsg"></p></td>
                        <!--<td width="134"><label>Shop Type</label><span class="mandatory">*</span></td>
                        <td width="180"><select name="shopType">
                                <option value="1">Wi-Tribe owned</option>
                              <option value="2">Kiosks</option>
                    </select></td> --></tr>
                    
                    <tr><td width="126"><label>House/Plot</label><span class="mandatory">*</span></td>
                        <td width="200"><input type="text" name="plot" class=txtxx value="" maxlength="30"/><p class="validationmsg"></p></td>
                        <td width="134"><label>Street</label><span class="mandatory">*</span></td>
                    <td width="180"><input type="text" class=txtxx name="street" value="" maxlength="30"/><p class="validationmsg"></p></td></tr>
                    <tr><td width="126"><label>Country</label><span class="mandatory">*</span></td>
                        <td width="200"><input type="text" class=txtxx name="country" value="${WitribeConstants.COUNTRY}" maxlength="30" readonly/><p class="validationmsg"></p></td>
                        <td width="134"><label>Province</label><span class="mandatory">*</span></td>
                        <%--<td width="180"><select name="province">
                                <option value=""></option>
                                <%try{
                                List objList = new ArrayList();
                                objList = wbo.getProvinces();
                                int listSize = objList.size();
                                for (int i=0;i<listSize;i++)                                   
                                {
                                String province = (String)objList.get(i);
                                %> 
                                <option value="<%=province%>"><%=province%></option>
                                <% } }catch(Exception e){}%>

                        </select></td><p class="validationmsg"></p>--%>
                        <td width="180">
                        <%try{
                        List objList1 = new ArrayList(); 
                        objList1 = wbo.getSubAddress("1","Pakistan","0");
                        int listSize1 = objList1.size();                               
                        AddressMappingVO objAddressVO = new AddressMappingVO();
                        
                        %>
                        <select id="province" name="province" onchange="fetchSubAddress(2,this.options[this.selectedIndex].value);">
                                <option></option> 
                                <%  for (int i=0;i<listSize1;i++) {
                            objAddressVO = (AddressMappingVO)objList1.get(i);
                            String pro = objAddressVO.getSubAddress();                                
                                
                                %> 
                                <option value="<%=pro%>"><%=pro%></option> 
                                <% } }catch(Exception e){}%>
                        </select><p class="validationmsg"></p></td></tr>
                    <!--<input type="text" name="province" class=txtxx value="<=prov%>" maxlength="30" readonly /><p class="validationmsg"></p></td>-->
                    <tr><td width="126"><label>City</label><span class="mandatory">*</span></td>
                        <td width="200"><!--<select name="city" onchange="document.shopform.province.value = this.options[this.selectedIndex].province;">-->
                                <%/*try{
                                List objList = new ArrayList(); 
                                objList = wbo.getCities();
                                int listSize = objList.size();
                                CityVO objCityVO = new CityVO();
                                for (int i=0;i<listSize;i++)                                   
                                {
                                objCityVO = (CityVO)objList.get(i);
                                String city = objCityVO.getAddr_city();
                                String pro = objCityVO.getAddr_province();                    
                                if(prov==null){
                                prov = pro;
                                }*/
                                %> 
                                <select name="city" id="city" onchange="fetchSubAddress(3,this.options[this.selectedIndex].value);">
                                    <option ></option>
                                <!--<option province="<=pro%>" value="<=city%>"><=city%></option> -->
                                <%/* } }catch(Exception e){}*/%>
                        </select><p class="validationmsg"></p></td>
                        
                        
                        <td width="134"><label>Zip</label></td>
                    <td width="180"><input type="text"  class=txtxx name="zip" value="" maxlength="10" /><p class="validationmsg"></p></td></tr>
                            <tr><td width="134"><label>Zone</label><span class="mandatory">*</span></td>
                                <td width="180"><select name="zone" id="zone" onchange="fetchSubAddress(4,this.options[this.selectedIndex].value);">
                                        <option ></option>
                            </select><p class="validationmsg"></p></td></tr>
                            <!--<input type="text" name="zone" class=txtxx value="" maxlength="30"/><p class="validationmsg"></p></td>-->
                        <tr><td width="126"><label>SubZone</label><span class="mandatory">*</span></td>
                        <td width="200" colspan="4"><select name="subzone" id="subzone">
                                        <option ></option>
                                        </select><p class="validationmsg"></p></td>                            
                            <!--<input type="text" name="subzone" class=txtxx value="" maxlength="30"/><p class="validationmsg"></p></td>-->
                    </tr>
                    
                    <TR>
                        <TD>&nbsp;</TD>
                        <TD>&nbsp;</TD>
                        <TD align=left><INPUT class=submit-btn 
                                                  onmouseover="this.src='images/btn-submit-thickbox-on.gif'" 
                                                  onmouseout="this.src='images/btn-submit-thickbox.gif'" type=image 
                                                  src="images/btn-submit-thickbox.gif" 
                                          name=input> </TD></TR>
                    
                </table>
</form></div></div></div>   



