<?
session_start("VIMS");
set_time_limit(700);

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'editrole'))
{
	echo "Permission denied";
	exit;
}

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Role.php");

//creating new object
$role=new Role();

if(!$data=$role->getEditRole())
{
	echo $channel->LastMsg."Cant not Get Role Name!";
}
?>


<table align="center" width="90%" border=0>
<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                        <form name="updateRole" id="updateRole" method="post" action="">

                          <table width="447" border="0" cellspacing=0" cellpadding="0">
                            <tr>
                              <td width="145"><label> Role Name:</label></td>
                              <td width="302"><label>
                                <input type="text" name="role_name" id="role_name">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label>Parent Role :</label>&nbsp;</td>
                              <td><label>
                                <select name="parent_role_id" id="parent_role_id">

                                   <?php

                                    foreach($data as $arr) { ?>

                                    <option value="<?=$arr['role_id']?>"><?=$arr['role_name']?></option>

                                    <? } ?>

                                </select>
                              </label></td>
                            </tr>
                          </table>
                        </form>
<table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'><input name="FormAction" type='button' value='Update' onclick="javascript:processForm( 'updateRole','FormProcessor/Role/updateRole.php','MsgDiv' );"></td>
				</tr>
			  </table>

              <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
        </table>>