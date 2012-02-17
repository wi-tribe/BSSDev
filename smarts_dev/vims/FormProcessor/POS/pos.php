<?PHP
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");
include_once(CLASSDIR."/Logging.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'sellvoucher'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$pos=new POS();
$log_obj = new Logging();

//get wh data
$log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." trying to sell voucher, voucher:".$_POST['voucher_serial'][0]." to ".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]);
if($pos->sellInventory())
{
	echo "Vouchers marked as sold successfully";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly marked sold vouchers, voucher:".$_POST['voucher_serial'][0]." to ".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]);
}
else
{
   echo $pos->LastMsg;
   $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to mark sold vouchers, voucher:".$_POST['voucher_serial'][0]." to ".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]);
}

?>


<script>
processForm( 'POSForm','Pages/POS/ajax_get_vouchers.php','POS' );
</script>