<?
session_start("VIMS");
set_time_limit(700);

	include_once('../../vims_config.php');

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listuser'))
{
	echo "Permission denied";
	exit;
}

	$users = $_USER->getAllUsersInfo();
        if(!$users)
        {
            echo $_USER->LastMsg."Can not Get All Users!";
        }
?>	

<script>

	
</script>
<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Users Information</strong></td>
  </tr>
  <tr>
	<td  class="textboxBur">S/N</td>
    <td class="textboxBur" align="center" ><strong>Fisrt Name</strong></td>
    <td class="textboxBur" align="center"><strong>Last Name</strong></td>
    <td class="textboxBur" align="center"><strong>Username</strong></td>
	<td class="textboxBur" align="center"><strong>Email</strong></td>
	<td class="textboxBur" align="center"><strong>Department</strong></td>
        <td class="textboxBur" align="center"><strong>Designation</strong></td>
	<td class="textboxBur" align="center"><strong>Channel</strong></td>


  </tr>
  <?	$index=1;
		foreach($users as $arr)
		{ ?>
          <tr>
		  <td><?=$index++?></td>
            <td class="textboxGray"><?=$arr['first_name']?></td>
            <td class="textboxGray"><?=$arr['last_name']?></td>
            <td class="textboxGray"><?=$arr['username']?></td>
			<td class="textboxGray"><?=$arr['email']?></td>
			 <td class="textboxGray"><?=$arr['team_name']?> </td>
			 <td class="textboxGray"><?=$arr['role_name']?> </td>
			 <td class="textboxGray"><?=$arr['channel_name']?></td>
            <td class="textboxGray" align="center">
				<a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/User/UserEdit.php','user_id=<?=$arr['user_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit User</a>
			</td>
          </tr>
  <? } ?>
  
	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>

