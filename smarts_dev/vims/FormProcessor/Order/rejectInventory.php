<?
session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');

////permission check
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'rejectorder'))
//{
//	echo "Permission denied";
//	exit;
//}
        $order = new Order();
        $log_obj = new Logging();
	$order_id = $_POST['order_id'];
        //print_r($_POST);
        
	$orderDetails = $order->getOrder($order_id);
	$orderSeries = $order->getOrderVouchers($order_id);
        $voucherIDs='';
        $counter=0;
        
        foreach($orderSeries as $voucher)
            {
                $voucherIDs[$counter]=$voucher['voucher_id'];
                $counter++;
            }
          
	$orderDetails = $orderDetails[0];
        
        $order_id = $orderDetails['order_id'];
        $order_by = $orderDetails['order_by'];
        $order_denomination = $_POST['order_denomination'];
        $total_items = $_POST['total_items'];
        if($total_items!=count($voucherIDs))
         {
            echo "Order Processing failed!!";
            exit();
         }
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." returning transit inventory/reject order OID:".$order_id,",total items:".$total_items.",face value:".$order_denomination."");
        $response = $order->ReturnTransitInventoryOrder($order_id,$order_by,$voucherIDs,$order_denomination,$total_items);

	if(!$response)
	{
		$msg = $order->error;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to reject order OID:".$order_id,",total items:".$total_items.",face value:".$order_denomination.", error: ".$order->error);
	}else
	{
		$msg = "SUCCESS; Order rejected successfully";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly rejected order OID:".$order_id,",total items:".$total_items.",face value:".$order_denomination."");
	}

?>
<?=$msg?>
<script>
window.opener.animatedAjaxCall( 'Pages/Order/recieveOrderList.php','FormDiv' );
</script> 