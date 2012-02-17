<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");
include_once(CLASSDIR."/Logging.php");

//permission check
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'replacevoucher'))
//{
//	echo "Permission denied";
//	exit;
//}
$vData_=false;
//creating new object
$pos =  new POS();
$rep_obj = new Replacement();
$log_obj = new Logging();
$log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." trying to replace voucher:".$_POST['voucher_serial']." with ".$_POST['replace_voucher_serial']);
  if($pos->replaceVoucher())
   {
        echo "Successfuly Replaced";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly posted voucher replace request for ".$_POST['voucher_serial']." with ".$_POST['replace_voucher_serial']);
   }
   else
   {
            //echo $rep_obj->LastMsg."Process Failed";
            echo $pos->LastMsg;
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to replace voucher ".$_POST['voucher_serial']." with ".$_POST['replace_voucher_serial']." error:".$pos->LastMsg);
            exit();
   }

?>

<script>
processForm( 'POSForm','Pages/POS/ajax_get_vouchers.php','POS' );
</script>