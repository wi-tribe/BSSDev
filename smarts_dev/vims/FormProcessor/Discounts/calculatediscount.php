<?php
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Discount.php");

$disc_obj = new Discount();
$dis_value_type;

if($_POST['discount']==null)
    {
        $disc_amount = 0;
        $commission_deduced_amount = $_POST['total_price'] - $_POST['commisssion_amount'] ;
    }
    
 else{
        $disc_value = $disc_obj->getdiscDetails($_POST['discount']);
        $disc_type = $disc_obj->getdisctypeName($disc_value[0]['discount_type_id']);

        if($disc_type[0]['discount_type_name']=='percentage')
        {
            $disc_amount = $_POST['total_price']*($disc_value[0]['value']/100);
            $dis_value_type = " %";
        }
        
        if($disc_type[0]['discount_type_name']=='denomination')
        {
            $disc_amount = $disc_value[0]['value'];
             $dis_value_type = " Rupees";
        }

        if($disc_type[0]['discount_type_name']=='quantity')
        {
             $disc_amount = $disc_value[0]['value'] * $_POST['order_denomination'];
             $dis_value_type = " Vouchers";
        }

        $commission_deduced_amount = $_POST['total_price'] - $_POST['commisssion_amount'] ;
     }

?>
<?=$disc_amount?>

<script>
   calculateNetAmount();
 </script>