<?php
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Discount.php");
include_once(CLASSDIR."/Logging.php");


////check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'discountadd'))
//{
//	echo "Permission denied";
//	exit;
//}

$dis_obj = new Discount();
$log_obj = new Logging();
//if($dis_obj->checkExistance())
//    {
//            echo $dis_obj->LastMsg;
//    }
//    else
//    {
      if($dis_obj->adddiscount())
        {
            echo "Successfuly Added";
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." successfuly added discount rule ");
        }
        else
        {
            echo $dis_obj->LastMsg;
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." unable to add discount rule ,".$dis_obj->LastMsg);

        }
   // }

?>
<script>
window.opener.animatedAjaxCall( 'Pages/Discounts/discountMain.php','FormDiv' );
//setTimeout('window.close()',3000);
</script>
