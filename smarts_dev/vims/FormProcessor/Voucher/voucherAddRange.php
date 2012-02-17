<?PHP
session_start("VIMS");
set_time_limit(2000);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addvoucher'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$vouch=new Voucher();

if($vouch->addVoucherRange())
{
	echo $vouch->LastMsg."Vouchers have been added successfully";
} else
	{ echo $vouch->LastMsg; }
?>