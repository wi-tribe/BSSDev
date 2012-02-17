<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");
include_once(CLASSDIR."/Logging.php");
//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'assignvoucher'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$pos=new POS();
$log_obj =  new Logging();
$vData_=false;


//get wh data
$log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." allocating vouchers to userID:".$_POST['user_id']." Vouchers: ".$_POST['voucher_serial'][0]." to".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]);
if($pos->assignInventory($_SESSION['channel_id'],$_POST['user_id']))
{
	echo "Vouchers have been allocated successfully";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfully allocated vouchers to userID:".$_POST['user_id']." Vouchers: ".$_POST['voucher_serial'][0]." to".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]);
}else {
	echo $pos->LastMsg;
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to allocate vouchers to userID:".$_POST['user_id']." Vouchers: ".$_POST['voucher_serial'][0]." to".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]." error:".$pos->LastMsg);
}
?>
<script>
processForm( 'POSForm','Pages/POS/ajax_get_vouchers.php','POS' );
</script>