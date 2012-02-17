<?
session_start("VIMS");
set_time_limit(2000);

include_once('../../vims_config.php');
include_once(CLASSDIR."/Invoice.php");
include_once(CLASSDIR."/Discount.php");
include_once(CLASSDIR."/Commission.php");
include_once(CLASSDIR."/Logging.php");


//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processorders'))
{
	echo "Permission denied";
	exit;
}

$comm_obj = new Commission();
$disc_obj = new Discount();
$invoice_obj = new Invoice();
$log_obj = new Logging();

$order_obj = new Order();

if($_POST['payment_mode']==null)
{
    print "Please select mode of payment";
    exit();
}
	$log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO:".$_SESSION['username']." processing inventory request order OID:".$_POST['order_id']." VoucherSerial:".$_POST['vouch_serial_start']." to ".$_POST['vouch_serial_end']);
	$response = $order_obj->processOrder($_POST['order_id'],$_POST['vouch_serial_start'],$_POST['vouch_serial_end']);
        if(!$response)
	{
		$msg = $order_obj->error;
                echo $msg;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR:".$_SESSION['username']." unable to process inventory request order OID:".$_POST['order_id']." VoucherSerial:".$_POST['vouch_serial_start']." to ".$_POST['vouch_serial_end']."Error:".$order_obj->error);
                exit();
	}else
	{
            $result = $invoice_obj->generateInvoice();
            if($result){
                $msg = "SUCCESS;Order processed successfully";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO:".$_SESSION['username']." successfuly processed order OID:".$_POST['order_id']." VoucherSerial:".$_POST['vouch_serial_start']." to ".$_POST['vouch_serial_end']);
            }
            else
            {
                echo $invoice_obj->LastMsg;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR:".$_SESSION['username']." unable to generate invoice for order OID:".$_POST['order_id']." VoucherSerial:".$_POST['vouch_serial_start']." to ".$_POST['vouch_serial_end']."Error".$invoice_obj->LastMsg);
            }
            
	}

?>
<?=$msg?>
<script>
window.opener.animatedAjaxCall( 'Pages/Order/processOrderList.php','FormDiv' );
</script>