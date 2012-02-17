<?
session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'returninventory'))
{
	echo "Permission denied";
	exit;
}

	$order_obj = new Order();
	$response = $order_obj->ReturnInventoryOrder($_SESSION['user_id'],$_POST['vouch_serial_start'],$_POST['vouch_serial_end'],$_POST['order_desc']);
	if(!$response)
	{
		$msg = $order_obj->error;
	}else
	{
		$msg = "SUCCESS;Inventory Return Order has been placed successfully.";
		
	}

?>
<?=$msg?>
