<?
session_start("VIMS");
set_time_limit(700);


	include('../../vims_config.php');

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'edituser'))
{
	echo "Permission denied";
	exit;
}
        include_once(CLASSDIR."/User.php");
        include_once(CLASSDIR."/Role.php");
        include_once(CLASSDIR."/Channel.php");
        $CHANNEL = new Channel();
        $ROLE=new Role();
	$user_id = $_POST['user_id'];
        
        $user = $_USER->getUserDetailInfo($user_id);
        foreach($user as $arr)
            {
        $currentHead = $_USER->getCurrentHead($arr['parent_user_id']);
            }
        foreach($user as $arr)
            {
        $heads = $ROLE->getHeads($arr['user_role_id']);
            }
        $teams = $_USER->getTeams();
        $roles = $ROLE->getAllRole();
        $channels = $CHANNEL->getAllChannels();

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<?=$_HTML_LIBS?>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <table width="600" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">

		<table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td height="30" colspan="2" background="../images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>USER DETAILS</strong> </span>

				</td>
			</tr>
		</table>

    <table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                        <form name="UserEdit" id="UserEdit" method="post" action="FormProcessor/User/UserEdit.php">

                          <table width="447" border="0" cellspacing=0" cellpadding="0">
                              <tr>
                              <td width="145"><label> User ID:</label></td>
                              <td width="302"><label>
                                      <?php
                                      foreach($user as $arr) { ?>
                                      <input type="text" name="user_id" id="user_id" readonly="readonly" value="<? echo $arr['user_id']?>"/>
                                     <? } ?>
                              </label></td>
                            </tr>
                            <tr>
                              <td width="145"><label> First Name:</label></td>
                              <td width="302"><label>
                                      <?php
                                      foreach($user as $arr) { ?>
                                      <input type="text" name="first_name" id="first_name" value="<? echo $arr['first_name']?>"/>
                                     <? } ?>
                              </label></td>
                            </tr>
                                <tr>
                              <td width="145"><label> Last Name:</label></td>
                              <td width="302"><label>
                               <?php
                              foreach($user as $arr) { ?>
                                <input type="text" name="last_name" id="last_name" value="<? echo $arr['last_name']?>"/>
                               <? } ?>
                              </label></td>
                            </tr>
                            <tr>
                              <td width="145"><label> UserName:</label></td>
                              <td width="302"><label>
                              <?php
                              foreach($user as $arr) { ?>
                              <input type="text" name="user_name" id="user_name" value="<?echo $arr['username']?>"/>
                               <? } ?>
                              </label></td>
                            </tr>
                            <tr>
                              <td width="145"><label> Email:</label></td>
                              <td width="302"><label>
                              <?php
                                foreach($user as $arr) { ?>
                                      <input type="text" name="email" id="email" value="<? echo $arr['email']?>"/>
                                    <? } ?>
                              </label></td>
                            </tr>
                            <tr>
                              <td><label> Department:</label>&nbsp;</td>
                              <td><label>
                                <select name="team_id" id="team_id">
                                    <?php
                                foreach($user as $arr) { ?>
                                    <option selected="selected" value="<?=$arr['user_team_id']?>"><?=$arr['team_name']?></option>
                                    <? } ?>
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
                                <select name="user_role_id" id="user_role_id" onchange="javascript:processForm( 'UserEdit','FormProcessor/User/populateHead.php','headDiv' );">
                                 <?php
                                foreach($user as $arr) { ?>
                                    <option selected="selected" value="<?=$arr['user_role_id']?>"><?=$arr['role_name']?></option>
                                    <? } ?>
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
                                   <?php
                                    foreach($currentHead as $arr) { ?>
                                    <option selected="selected" value="<?=$arr['user_id']?>"><?=$arr['first_name']?>---<?=$arr['last_name']?></option>
                                  <? } ?>
                                    <option value="">Select Head</option>
                                    </select>
                                  </div>
                              </td>
                            </tr>
                            <tr>
                              <td><label> Channel:</label>&nbsp;</td>
                              <td><label>
                                <select name="user_channel_id" id="user_channel_id">
                                    <?php
                                    foreach($user as $arr) { ?>
                                    <option selected="selected" value="<?=$arr['user_channel_id']?>"><?=$arr['channel_name']?></option>
                                    <? } ?>
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
				  <td align='right'>
                                  <input name="FormAction" type='button' value='Update' onclick="submit()"/></td>
				</tr>
			  </table>
                        </form>
              </td>
		</tr>
	</table>
    </td>
</tr>
</table>

</body>
</html>



