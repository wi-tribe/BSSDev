<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Commission.php");
include_once(CLASSDIR."/Logging.php");
//check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'rentaladd'))
//{
//	echo "Permission denied";
//	exit;
//}

$commission_obj = new Commission();
$log_obj = new Logging();

$result = $commission_obj->updateCommission();
if(!$result)
{
    echo "$rental_obj->LastMsg";
    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." unable to modifiy commission rule status to ".$_POST['status']." where commissionID:".$_POST['commission_id']."error:".$rental_obj->LastMsg);
}
else
{
    echo "Successfuly Updated";
    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." Successfuly modified commission rule status to ".$_POST['status']." where commissionID:".$_POST['commission_id']);
}

?>
<script>
window.opener.animatedAjaxCall( 'Pages/Commissioning/commissionMain.php','FormDiv' );
//setTimeout('window.close()',3000);
</script>


