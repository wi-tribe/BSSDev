<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<DIV class=more-width id=col2>   
   
         <TABLE class=form cellSpacing=0 cellPadding=0  border=0 
                background="images/table_bg.gif">
         <TBODY>
         <TR height=19>
         <TD width=12>&nbsp;</TD>
         <TD>&nbsp;</TD>
         <TD width=15 background="./images/up_right.gif">&nbsp;</TD>
         <TR>
         <TD>&nbsp;</TD>
         <TD> 
         <form action="./ModifyShop.do" method="post">
              <input type="hidden" name="page" />
              <input type="hidden" name="flag" />
           <div id="inner-content">
                        <table width="679" border="0" cellspacing="0" cellpadding="0">
                             <bean:define id="objShop"  name="objShop" type="com.witribe.sales.vo.ShopsVO" />
                            <tr><td><label>Shop Name</label></td>
                            <td><input type="text" name="shopname" class=txtbx value="${objShop.shopname}" maxlength="60"/></td></tr>
                            <tr><td><label>Shop Type</label></td>
                            <td><select name="shoptype">
                                        <option value="wi-tribe owned">wi-tribe owned</option>
                                        <option value="Kiosks">Kiosks</option>
                                 </select></td></tr>
                            <tr><td><label>House/Plot</label></td>
                            <td><input type="text" name="plot" class=txtbx value="${objShop.plot}" maxlength="60"/></td></tr>
                            <tr><td><label>Street</label></td>
                            <td><input type="text" class=txtbx name="street" value="${objShop.street}" maxlength="60"/></td></tr>
                            <tr><td><label>Zone</label></td>
                            <td><input type="text" name="zone" class=txtbx value="${objShop.zone}" maxlength="60"/></td></tr>
                            <tr><td><label>Sub Zone</label></td>
                            <td><input type="text" name="subzone" class=txtbx value="${objShop.subzone}" maxlength="60"/></td></tr>
                            <tr><td><label>City</label></td>
                                <td><select name="city">
                                        <option value="Hyderabad">Hyderabad</option>
                                        <option value="Hyderabad">Warangal</option>
                                        <option value="Hyderabad">Khammam</option>
                            </select></td></tr>
                            <tr><td><label>Province</label></td>
                            <td><input type="text" name="province" class=txtbx value="${objShop.province}" maxlength="60"/></td></tr>
                            <tr><td><label>Country</label></td>
                            <td><input type="text" class=txtbx name="country" value="${objShop.country}" maxlength="60"/></td></tr>
                            <tr><td><label>Zip</label></td>
                            <td><input type="text"  class=txtbx name="zip" value="${objShop.zip}" maxlength="20" onkeypress="javascript: return numbersonly(event);"/></td></tr>
                           <tr align="center"><td colspan="1"><input type="submit" value="Edit" name="submit"/></td>
                           <td colspan="1"><input type="submit" value="Delete" name="submit"/></td></tr>
                                
        </table></div>
         </form>   
             
             
             
     <TD vAlign=top 
         background="images/border_pixel.gif">&nbsp;</TD></TR>
     <TR height=17>
         <TD background="./images/bottom_left.gif">&nbsp;</TD>
         <TD background="./images/border_pixel.gif">&nbsp;</TD>
         <TD 
         background="images/border_pixel.gif">&nbsp;</TD></TR>
     </TBODY>
     </TABLE>
     </DIV>
    
   