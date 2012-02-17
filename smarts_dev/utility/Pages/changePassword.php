<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once('../util_config.php');
$conf=new config();
include_once(CLASSDIR."/Users.php");

////permission check
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'changepassword'))
//{
//	echo "Permission denied";
//	exit;
//}
?>

<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                        <form name="changePassword" id="changePassword" method="post" action="">

                          <table width="447" border="0" cellspacing=0" cellpadding="0">
                            <tr>
                              <td width="145"><label> Old Password:</label></td>
                              <td width="302"><label>
                                <input type="password" name="old_password" id="old_password">
                              </label></td>
                            </tr>
                            <tr>
                              <td width="145"><label> New Password:</label></td>
                              <td width="302"><label>
                                <input type="password" name="new_password" id="new_password">
                              </label></td>
                            </tr>

                            <tr>
                              <td width="145"><label> Confirm New Password:</label></td>
                              <td width="302"><label>
                                <input type="password" name="c_new_password" id="c_new_password">
                              </label></td>
                            </tr>
                            
                          </table>
                            <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'><input name="FormAction" type='button' value='Reset' onclick="javascript:processForm( 'changePassword','FormProcessor/changePassword.php','MsgDiv' );"></td>
				</tr>
			  </table>
                        </form>
              <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>
