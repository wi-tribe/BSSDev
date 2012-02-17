<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/BRMIntegration.php");
include_once(CLASSDIR."/POS.php");
include_once(CLASSDIR."/Logging.php");


//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'blockvoucher'))
{
	echo "Permission denied";
	exit;
}
$pos_obj = new POS();
$log_obj = new Logging();
if($_POST['voucher_serial']!=null)
    {
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." trying to block voucher:".$_POST['voucher_serial']);
        if($pos_obj->blockvoucher($_POST['voucher_serial']))
        {
            echo $pos_obj->LastMsg."Successfuly Blocked!";
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly block voucher:".$_POST['voucher_serial']);
        }
        else
            {
                echo "Error!! ".$pos_obj->LastMsg;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to block voucher:".$_POST['voucher_serial']." error:".$pos_obj->LastMsg);
            }
    }
    else
        echo "Provide Voucher Serial";

?>
