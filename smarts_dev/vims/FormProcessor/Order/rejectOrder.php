<?
session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processorders'))
{
	echo "Permission denied";
	exit;
}

	$order_obj = new Order();
	$log_obj = new Logging();
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO ".$_SESSION['username']." rejecting order OID:".$_POST['order_id']);
	$response = $order_obj->rejectOrder($_POST['order_id']);
	if(!$response)
	{
		$msg = $order_obj->error;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to reject order OID:".$_POST['order_id']." error:".$order_obj->error);
	}else
	{
		$msg = "SUCCESS; Order rejected successfully";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO ".$_SESSION['username']." successfuly rejected order OID:".$_POST['order_id']);
	}

?>
<?=$msg?>
<script> 
window.opener.animatedAjaxCall( 'Pages/Order/processOrderList.php','FormDiv' );
</script> 