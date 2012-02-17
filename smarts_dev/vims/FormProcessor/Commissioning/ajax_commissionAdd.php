<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Commission.php");
include_once(CLASSDIR."/Logging.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionadd'))
{
	echo "Permission denied";
	exit;
}
$log_obj =  new Logging();
$c_obj = new Commission();
$rule = "INFO: ".$_SESSION['username']." successfuly added commision rule = Commission Value: ".$_POST['commission']."Channel Type ID:".$_POST['channel_type_id'].", ChannelID:".$_POST['channel_id'].",RegionID:".$_POST['region_id'].",City:".$_POST['city_id'].",Zone:".$_POST['zone_id']."";

if($c_obj->checkExistance())
    {
            echo $c_obj->LastMsg;
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$c_obj->LastMsg);
    }
    else
        {
            if($c_obj->addcommissionRule())
            {
                echo "Successfuly Added";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, $rule);
            }
            else
            {
                echo $c_obj->LastMsg;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, " ERROR:".$_SESSION['username']." Failed to add commission rule ".$c_obj->LastMsg);
            }
        }

?>
<script>
window.opener.animatedAjaxCall( 'Pages/Commissioning/commissionMain.php','FormDiv' );
//setTimeout('window.close()',3000);
</script>


