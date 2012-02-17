<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Rental.php");

////check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'rentaladd'))
//{
//	echo "Permission denied";
//	exit;
//}

$rental_obj = new Rental();
if($rental_obj->checkExistance())
    {
            echo $rental_obj->LastMsg;
    }
    else
        {
            if($rental_obj->addrentalRule())
            {
                echo "Successfuly Added";
            }
            else
            {
                echo $rental_obj->LastMsg;
            }
        }

?>
<script>
window.opener.animatedAjaxCall( 'Pages/Rental/RentalMain.php','FormDiv' );
//setTimeout('window.close()',3000);
</script>


