<?
session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');
include_once(CLASSDIR."/User.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listretailers'))
{
	echo "Permission denied";
	exit;
}

$user = new User();
$retailers = $user->getTLRetailers($_SESSION['user_id']);

        if(!$retailers)
        {
            echo $_USER->LastMsg."Retailer not found!";
        }
 
?>	


<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Retailers Information</strong></td>
  </tr>
  <tr>
	<td  class="textboxBur">S/N</td>
    <td class="textboxBur" align="center" ><strong>First Name</strong></td>
    <td class="textboxBur" align="center"><strong>Last Name</strong></td>
    <td class="textboxBur" align="center"><strong>User ID</strong></td>
    <td class="textboxBur" align="center"><strong>Username</strong></td>
	<td class="textboxBur" align="center"><strong>Email</strong></td>
        <td class="textboxBur" align="center"><strong>Designation</strong></td>
        <td class="textboxBur" align="center"><strong>Retailer ID</strong></td>
	<td class="textboxBur" align="center"><strong>Retailer Name</strong></td>
        <td class="textboxBur" align="center"><strong>Retailer Address</strong></td>
        <td class="textboxBur" align="center"><strong>User Status</strong></td>


  </tr>
  <?	$index=1;
		foreach($retailers as $arr)
		{ ?>
          <tr>
		  <td><?=$index++?></td>
            <td class="textboxGray"><?=$arr['first_name']?></td>
            <td class="textboxGray"><?=$arr['last_name']?></td>
            <td class="textboxGray"><?=$arr['user_id']?></td>
            <td class="textboxGray"><?=$arr['username']?></td>
			<td class="textboxGray"><?=$arr['email']?></td>
			 <td class="textboxGray"><?=$arr['role_name']?> </td>
                         <td class="textboxGray"><?=$arr['user_channel_id']?> </td>
			 <td class="textboxGray"><?=$arr['channel_name']?></td>
                         <td class="textboxGray"><?=$arr['address']?></td>
                         <td class="textboxGray"><?=$arr['status']?></td>
            <td class="textboxGray" align="center">
				<a href="#" class= "options" onclick="javascript:AjaxPopUpWindow( 'Pages/User/EditRetailer.php','retailer_user_id=<?=$arr['user_id']?>','top=100, left=100,height=400, width=600, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no' );" >Edit Retailer</a>
			</td>
          </tr>
  <? } ?>
  
	<tr>
		<td colspan = "10"><div id="processForm"></div></td>
	</tr>
</table>

