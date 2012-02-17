<?
session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'reqinventory'))
{
	echo "Permission denied";
	exit;
}


	$order = new Order();
        $log_obj =  new Logging();
	$order_type_id ='1';
	$order_by =$_SESSION['user_id'];
	$total_items =$_POST['total_items'];
	$order_desc =$_POST['order_desc'];
	$order_denomination =$_POST['v_price'];
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO ".$_SESSION['username']." placing inventory request order, Face Value: ".$order_denomination.",total items:".$total_items);
	if(!$order->orderInventory($order_type_id, $order_by, $order_denomination, $total_items, $order_desc))
	{
		print $order->error;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR ".$_SESSION['username']." unable to place inventory request order, Face Value: ".$order_denomination.",total items:".$total_items);
		exit;
	}
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO ".$_SESSION['username']." SUCCESS;Inventory request has been successfully posted, Face Value: ".$order_denomination.",total items:".$total_items);
?>
SUCCESS;Inventory request has been successfully posted.