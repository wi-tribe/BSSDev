<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
session_start("VIMS");
set_time_limit(700);

include_once('../../vims_config.php');
include_once(CLASSDIR."/Replacement.php");
include_once(CLASSDIR."/Logging.php");


//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'repoverride'))
{
	echo "Permission denied";
	exit;
}

 $rep_obj = new Replacement();
 $log_obj = new Logging();
 $replaced_vouch_id = $_POST['voucher_id'];
 $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." trying to allow replacement, voucherID:".$_POST['voucher_id']);
 $response = $rep_obj->allowreplacement($replaced_vouch_id);
    if(!$response)
      {
 	$msg = $rep_obj->LastMsg;
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." unable to allow replacement, voucherID:".$_POST['voucher_id'],"Error:".$rep_obj->LastMsg);
      }
    else
      {
         $msg = "SUCCESS;Replacement Allowed Successfuly ";
         $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." succesfully allowed replacement, voucherID:".$_POST['voucher_id']);
      }

?>
<?=$msg?>
<script>
window.opener.animatedAjaxCall( 'Pages/POS/ticketedVouchersList.php','FormDiv' );
</script>

