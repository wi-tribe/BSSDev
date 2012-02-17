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
        $log_obj = new Logging();
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." raising invetory return order [FaceValue: ".$_POST['voucher_denomination']." Serial:".$_POST['voucher_serial'][0]." to".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]." ");
	$response = $order_obj->ReturnInventoryOrder($_SESSION['user_id'],$_POST['voucher_denomination'],$_POST['voucher_serial'],$_POST['order_desc']);
	if(!$response)
	{
		$msg = $order_obj->error;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." unable to raise inventory return order ".$order_obj->error);
	}else
	{
		$msg = "SUCCESS;Inventory Return Order has been placed successfully.";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." successfuly placed invetory return order [FaceValue: ".$_POST['voucher_denomination']." Serial:".$_POST['voucher_serial'][0]." to".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]." ");
	}
?>
<?=$msg?>
