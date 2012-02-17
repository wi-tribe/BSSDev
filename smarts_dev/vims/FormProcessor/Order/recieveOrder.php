<?
session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');

//permission check
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'recieveinventory'))
//{
//	echo "Permission denied";
//	exit;
//}

	$order_obj = new Order();
	$log_obj = new Logging();

        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." trying to receive assigned inventory OrderID:".$_POST['order_id']);
	$response = $order_obj->ConfirmOrderReciept($_POST['order_id'],$_POST['vouch_serial_start'],$_POST['vouch_serial_end']);
	if(!$response)
	{
		$msg = $order_obj->error;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to receive assigned inventory [".$order_obj->error."] OrderID:".$_POST['order_id']);
	}else
	{
		$msg = "SUCCESS;Order processed successfully";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly received inventory OrderID:".$_POST['order_id']);
	}

?>
<?=$msg?>