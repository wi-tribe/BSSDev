<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%> 
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>      
<script language="javascript" type="text/javascript" src="js/witribescript.js"></script>
<DIV class=more-width id=col2>
<P>Fields marked with an asterisk (<SPAN class=mandatory>*</SPAN>) are 
Mandatory.</P>
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
      <FORM action="./RegUser.do" method="post" onsubmit="javascript: return ValidateRegisterForm(this);" >
      <TABLE cellSpacing=0 cellPadding=0 width=646 align=center border=0>
        <TBODY>
        <TR>
          <TD width="31%">&nbsp;</TD>
          <TD class=error colspan=2>
                    <b> ${errorString}</b>
                  </TD>
          </TR>
                   
                    <tr><td><label>Name</label><span class="mandatory">*</span></td>
                    <td><input type="text" name="name" value="" size="32" maxlength="30" class="txtbx"/><p class="validationmsg"></p></td></tr>
                    <tr><td><label>Email</label><span class="mandatory">*</span></td>
                    <td><input type="text" name="email" value="" size="32" maxlength="60" class="txtbx"/><p class="validationmsg"></p></td></tr>
                    <tr><td><label>Mobile</label><span class="mandatory">*</span></td>
                    <td><input type="text" name="mobile" value="" maxlength="12" class="txtbx"/><p class="validationmsg"></p> </td></tr>
                     <tr><td><label>Sector/area</label><span class="mandatory">*</span></td>
                   <td><input type="text" name="area" value="" class="txtbx" maxlength="20"/><p class="validationmsg"></p></td></tr>
                    <tr><td><label>User Id</label><span class="mandatory">*</span></td>
                    <td><input type="text" name="userId" value="" maxlength="20" class="txtbx"/><p class="validationmsg"></p></td></tr>
                    <tr><td><label>password</label><span class="mandatory">*</span></td>
                    <td><input type="password" name="password" value="" maxlength="30" class="txtbx"/><p class="validationmsg"></p></td></tr>
                    <tr><td><label>Role</label></td>
                        <td><select name="role" maxlength="20">
                             <option value="CSE">CSE</option>
                             <option value="TL">Team Lead</option>
                             <option value="Admin">Admin</option>
                          </select></td></tr>
                     <tr><td>&nbsp;</td></tr>
                    <tr><td align="center" colspan="2"><input  type="submit" name="Register" value="Register" /></td></tr> </TBODY></TABLE></FORM></TD>
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