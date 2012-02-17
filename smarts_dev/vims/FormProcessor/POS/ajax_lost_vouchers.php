<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Tracker.php");
include_once(CLASSDIR."/Logging.php");

////check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'cce_track_voucher'))
//{
//	echo "Permission denied";
//	exit;
//}
$vouch_obj = new Voucher();
$user_obj = new User();
$tracker_obj = new Tracker();
$log_obj =  new Logging();
if($_POST['voucher_denomination']==null)
 {
    echo "No Vouchers selected!!!";
    exit();
 }
 
 if($_POST['voucher_serial']==null)
 {
    echo "No Vouchers selected!!!";
    exit();
 }
 $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." trying move vouchers to lost shop, Serial:".$_POST['voucher_serial'][0]." to ".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]);
 $voucherSerial = $_POST['voucher_serial'];
 $response = $tracker_obj->MoveInventoryToLostShop($voucherSerial, $_SESSION['user_id']);

 if(!$response)
     {
        echo $tracker_obj->LastMsg;
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to move vouchers to lost shop, Serial:".$_POST['voucher_serial'][0]." to ".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]." error:".$tracker_obj->LastMsg);
        exit();
     }
     else
        {
         echo "Success: Vouchers has been moved successfuly to Lost Shop";
         $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly moved vouchers to lost shop, Serial:".$_POST['voucher_serial'][0]." to ".$_POST['voucher_serial'][count($_POST['voucher_serial'])-1]);
        }
?>