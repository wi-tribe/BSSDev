<?
session_start("VIMS");
set_time_limit(700);
include_once("../../vims_config.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'adduser'))
{
	echo "Permission denied";
	exit;
}

$ROLE = new Role();
$CHANNEL=new Channel();

$teams = $_USER->getTeams();
$roles = $ROLE->getAllRole();
$channels = $CHANNEL->getAllChannels();
?>

<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                        <form name="UserAdd" id="UserAdd" method="post" action="">

                          <table width="447" border="0" cellspacing=0" cellpadding="0">
                            <tr>
                              <td width="145"><label> First Name:</label></td>
                              <td width="302"><label>
                                <input type="text" name="first_name" id="first_name">
                              </label></td>
                            </tr>
                            <tr>
                                <tr>
                              <td width="145"><label> Last Name:</label></td>
                              <td width="302"><label>
                                <input type="text" name="last_name" id="last_name">
                              </label></td>
                            </tr>
                            <tr>
                            <tr>
                              <td width="145"><label> UserName:</label></td>
                              <td width="302"><label>
                                <input type="text" name="user_name" id="user_name">
                              </label></td>
                            </tr>
                            <tr>
                            <tr>
                              <td width="145"><label> Password:</label></td>
                              <td width="302"><label>
                                <input type="password" name="password" id="password">
                              </label></td>
                            </tr>
                            <tr>
                            <tr>
                              <td width="145"><label> Email:</label></td>
                              <td width="302"><label>
                                <input type="text" name="email" id="email">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label> Department:</label>&nbsp;</td>
                              <td><label>
                                <select name="team_id" id="team_id">
                                <option value="">Select Department</option>
				<?php
                                    foreach($teams as $arr) { ?>
                                    <option value="<?=$arr['team_id']?>"><?=$arr['team_name']?></option>
                                    <? } ?>
                                </select>
                              </label></td>
                            </tr>
                            <tr>
                              <td><label> Designation:</label>&nbsp;</td>
                              <td><label>
                                      <select name="user_role_id" id="user_role_id" onchange="javascript:processForm( 'UserAdd','FormProcessor/User/populateHead.php','headDiv' );">
                                <option value="">Select Designation</option>
				<?php
                                    foreach($roles as $arr) { ?>
                                    <option value="<?=$arr['role_id']?>"><?=$arr['role_name']?></option>
                                    <? } ?>
                                </select>
                              </label></td>
                            </tr>
                            <tr>
                              <td><label> Head:</label>&nbsp;</td>
                              <td> <div name="headDiv" id="headDiv">
                                    <select name="parent_user_id" id="parent_user_id">
                                    <option value="">Select Head</option>
                                    </select>
                                  </div>
                              </td>
                            </tr>
                            <tr>
                              <td><label> Channel:</label>&nbsp;</td>
                              <td><label>
                                <select name="user_channel_id" id="user_channel_id">
                                <option value="">Select Channel</option>
				<?php
                                    foreach($channels as $arr) { ?>
                                    <option value="<?=$arr['channel_id']?>"><?=$arr['channel_name']?></option>
                                    <? } ?>
                                </select>
                              </label></td>
                            </tr>
                          </table>
                            <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'><input name="FormAction" type='button' value='Add' onclick="javascript:processForm( 'UserAdd','FormProcessor/User/UserAdd.php','MsgDiv' );"></td>
				</tr>
			  </table>
                        </form>
              <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>

