<?php
session_start("VIMS");
set_time_limit(2000);
////ob_start();
include_once("../../vims_config.php");

include_once(CLASSDIR."/POS.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Retailer.php");
include_once(CLASSDIR."/Logging.php");


////check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processretailerorder'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$pos=new POS();
$voucher_obj= new Voucher();
$users = new User();
$channels = new channel();
$retailer = new Retailer();
$log_obj = new Logging();

if($_POST['TeamLead']==null){print "Please Select Team Lead <br>"; }
if($_POST['SE']==null){print "Please Select SE <br>"; }
if($_POST['Retailer']==null){print "Please Select Retailer <br>"; exit();}

$se_details = $users->getUserDetailInfo($_POST['SE']);
$retailer_details = $users->getUserDetailInfo($_POST['Retailer']);

$values = $_POST;

 if(!$retailer->processRequestedOrder($values,$se_details[0]['user_channel_id'],$values['Retailer'],$retailer_details[0]['user_channel_id']))
 {
     echo $retailer->LastMsg;
     $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR ".$_SESSION['username']." unable to process retailer(".$values['Retailer'].") Order ,".$retailer->LastMsg);
     exit();
 }
 
 else{
	     echo "<br> Order Successfuly Processed <br>";
	    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO ".$_SESSION['username']." successfuly processed retailer(".$values['Retailer'].") Order");
	}
?>