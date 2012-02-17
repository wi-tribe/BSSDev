<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();

	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/Logging.php");

        $log_obj = new Logging();
        $Customerno = trim($_POST['Customerno']);

         if($Customerno==null)
         {
             echo "Please Provide Tele/Mobile Number";
             exit();
         }

         if(!(is_numeric($Customerno)))
         {
             echo "Number subject to be Integer Digits";
             exit();
         }
       

     $customer=new SalesPersonnel();
     $cdata=$customer->getCustomerByNumber($Customerno);

     if($cdata==null)
     {
         echo "No Record Found!";
         $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." No record found against customer mobile/telephone number ".$Customerno);
         exit();
     }

     $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly verified against customer mobile/telephone number ".$Customerno);
    unset($customer);
?>
<table width="100%" >
	<tr>
		<td class="orangeText"><font size="2">CUSTOMER ID </font></td>
		<td class="orangeText"><font size="2">CUSTOMER NAME </font></td>
		<td class="orangeText"><font size="2">CNIC </font></td>
		<td class="orangeText"><font size="2">Telephone No. </font></td>
		<td class="orangeText"><font size="2">Cell No. </font></td>
		<td class="orangeText"><font size="2">Details </font></td>    
	</tr>
	<?foreach($cdata as $arr)
	{?>
		<tr>
			<td><font size="2"><?=$arr['CUSTOMER_ID']?></font></td>
			<td><font size="2"><?=$arr[FIRST_NAME] ?>&nbsp;<?= $arr[LAST_NAME]?></font></td>
			<td ><font size="2"><?=$arr['IDENTIFICATION_NUMBER']?></font></td>
			<td ><font size="2"><?=$arr['TELEPHONE_NUMBER']?></font></td>
			<td ><font size="2"><?=$arr['MOBILE_NUMBER']?></font></td>
			<td ><font size="2"> <a href="#" onclick="javascript:viewDetails(<?=$arr['CUSTOMER_ID']?>)"><font color="orange"> View Details</font></a></font></td>    
		</tr>
	<?}?>
</table>