<?php
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Rental.php");

$rental_obj = new Rental();
$result = $rental_obj->updateRentalPayableStatus();

if(!$result)
{
    echo "$rental_obj->LastMsg";
}
else
{
    echo "Successfuly Updated";
}

?>
<script>
//window.opener.animatedAjaxCall( 'FormProcessor/Rental/Rental_Payables.php','FormDiv' );
//setTimeout('window.close()',3000);
</script>


