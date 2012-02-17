<?php
        error_reporting(E_ALL);
	print "START______________".date('Y-m-d H:i:s')."<BR>\n\r";

	include('../../vims_config.php');
        include_once(CLASSDIR."/Channel.php");
        include_once(CLASSDIR."/Rental.php");

        $channel_obj = new Channel();
        $rental_obj = new Rental();
        if(date('m')<=1)
        {
            $start_date = '11'.'/'.'16'.'/'.(date('Y')-1);
            $end_date = '12'.'/'.'15'.'/'.(date('Y')-1);
        }
        if(date('m')>1 && date('m')<=2)
        {
            $start_date = '12'.'/'.'16'.'/'.(date('Y')-1);
            $end_date = (date('m')-1).'/'.'15'.'/'.(date('Y'));
        }
        if(date('m')>2)
        {
            $start_date = (date('m')-2).'/'.'16'.'/'.date('Y');
            $end_date = (date('m')-1).'/'.'15'.'/'.date('Y');
        }
       print($start_date);
        // Include Last Months Registered Active Retailer
        $Retailers = $rental_obj->getRentalPayableRetailerbyDate($start_date,$end_date);
       
        if($Retailers!=null)
        {
            foreach($Retailers as $arr)
            {
                $rental_rule = $rental_obj->getrentalValue($arr['channel_id'], $arr['channel_type_id'], $applied_to);
                $insert = "user_id,first_name,last_name,channel_id,channel_type_id,payable_amount,rental_rule_id,for_month";
                 $vals="'".$arr['user_id']."','".$arr['first_name']."', '".$arr['last_name']."', '".$arr['channel_id']."'
                     , '".$arr['channel_type_id']."' ,'".$rental_rule[0]['value']."', '".$rental_rule[0]['rule_id']."',CURRENT_DATE";

                 $GLOBALS['_DB']->InsertRecord(RENTAL_T,$insert,$vals);
            }
        }
        
        $start_date = '';
        $end_date = '';
        $Retailers = '';

         if(date('m')<=1)
        {
          $start_date = '10'.'/'.'16'.'/'.(date('Y')-1);
          $end_date = '11'.'/'.'15'.'/'.(date('Y')-1);
        }
        if(date('m')>1 && date('m')<=2)
        {
         $start_date = '11'.'/'.'16'.'/'.(date('Y')-1);
        $end_date = '12'.'/'.'15'.'/'.(date('Y')-1);
        }
        if(date('m')>2)
        {
           $start_date = (date('m')-3).'/'.'16'.'/'.date('Y');
          $end_date = (date('m')-2).'/'.'15'.'/'.date('Y');
        }

        $Retailers = $rental_obj->getRentalPayableRetailerbyDate($start_date,$end_date);

        if($Retailers!=null)
        {   
            foreach($Retailers as $arr)
            {
                $rental_rule = $rental_obj->getrentalValue($arr['channel_id'], $arr['channel_type_id'], $applied_to);
                $insert = "user_id,first_name,last_name,channel_id,channel_type_id,payable_amount,rental_rule_id,for_month";
                 $vals="'".$arr['user_id']."','".$arr['first_name']."', '".$arr['last_name']."', '".$arr['channel_id']."'
                     , '".$arr['channel_type_id']."' ,'".$rental_rule[0]['value']."', '".$rental_rule[0]['rule_id']."',CURRENT_DATE";

                 $GLOBALS['_DB']->InsertRecord(RENTAL_T,$insert,$vals);
            }
        }

        print "<br> END ............";

?>
