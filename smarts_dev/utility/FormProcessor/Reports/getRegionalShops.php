<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
//include_once("../../vims_config.php");
include_once("../../util_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

$formName = $_REQUEST['formName'];
//check permission
//if(!$_POST['permission']=='NWD')
//    {
//        echo "Permission Denied";
//        exit();
//    }
 
$vouch_obj = new Voucher();
//if($_POST['permission']=='Region')
//   {     $ch_obj = new Channel();
//        $_SESSION['region'] = $ch_obj->getuserRegion($_SESSION['user_id']);
//        print_r($_SESSION);
//        $channels = $ch_obj->getuserregionShops($_SESSION['user_id']);
//        $_SESSION['reporttype']='receipt';
//   }
	$Shops = $vouch_obj->getShopByRegion($_POST['region']);
	$_SESSION['reporttype']='receipt';
        
	
?>
<select name='channel' id='channel' class="selectbox" onchange="javascript:processForm( '<?=$formName?>','FormProcessor/Reports/getShopUsers.php','userdiv' ); unselect();">
	<option value="ALL"> -- ALL -- </option>
<?	foreach($Shops as $arr) 
	{ ?>
		<option value="<?=$arr['shop_id']?>"><?=$arr['shop_id']?>--<?=$arr['shop_name']?></option>
<?	} ?>
</select>

