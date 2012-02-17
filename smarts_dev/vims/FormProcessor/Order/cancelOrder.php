<?
session_start("VIMS");
set_time_limit(700);

	include_once('../../vims_config.php');
        include_once(CLASSDIR."/Order.php");
        include_once(CLASSDIR."/Logging.php");
	$order_obj = new Order();
        $log_obj = new Logging();
	//print_r($_POST);
        
	$response = $order_obj->cancelOrder($_POST['order_id']);
	if(!$response)
	{
		$msg = $order_obj->error;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." unable to cancel order, OrderID:".$_POST['order_id']."Reason:".$order_obj->error);
	}else
	{
		$msg = "SUCCESS; Order Cancelled successfully";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." successfuly canceled the order, OrderID:".$_POST['order_id']);
	}

?>
<?=$msg?>
<script> 
window.opener.animatedAjaxCall( 'Pages/Order/orderStatusListings.php','FormDiv' );
</script> 