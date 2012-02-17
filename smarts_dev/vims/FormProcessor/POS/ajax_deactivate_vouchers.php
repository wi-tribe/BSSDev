<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'deactivatevoucher'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$pos=new POS();

$vData_=false;


//get wh data
if($pos->deactivateVouchers())
{
	echo "Vouchers have been deactivated successfully";
}else {
	echo $pos->LastMsg;
}


?>