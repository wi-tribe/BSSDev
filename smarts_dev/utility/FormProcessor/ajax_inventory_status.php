<?php
        
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();

	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/Logging.php");

        $log_obj = new Logging();
        
        $invenory_id = trim($_POST['inventory_id']);
        $device_wan_mac = trim($_POST['device_wan_mac']);
         
         if($_POST['device_wan_mac']==null && $_POST['inventory_id']==null)
         {
             if($_POST['region']=='ALL' && $_POST['channel']=='ALL')
             {
                 echo "no search details provided";
                 exit();
             }
             if($_POST['region']=='ALL' || $_POST['channel']=='ALL')
             {
                 echo "Region & Shop must be selected if CCC wise search is made";
                 exit();   
             }
         }
        
     $filters = $_POST;  
     $customer=new SalesPersonnel();
     $cdata=$customer->getDeviceInvStatus($filters);
     
     if($cdata==null)
     {
         echo "No Record Found!";
         $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." No record found against customer mobile/telephone number ".$Customerno);
         exit();
     }

     $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly verified against customer mobile/telephone number ".$Customerno);
    
?>
<table id="results" width="100%">
	<tr>
		<td class="orangeText"><font size="2">DEVICE ID </font></td>
		<td class="orangeText"><font size="2">CCC </font></td>
                <td class="orangeText"><font size="2">MODEL </font></td>
                <td class="orangeText"><font size="2">TYPE </font></td>
		<td class="orangeText"><font size="2">MAC </font></td>
		<td class="orangeText"><font size="2">Status </font></td>
		<td class="orangeText"><font size="2">Agent Sales ID </font></td>
		<td class="orangeText"><font size="2">Details </font></td>    
	</tr>
	<?foreach($cdata as $arr)
	{?>
		<tr>
			<td><font size="2"><?=$arr['DEVICE_ID']?></font></td>
			<td><font size="2"><?=$arr['SOURCE']?></font></td>
                        <td><font size="2"><?=$arr['MODEL']?></font></td>
                        <td><font size="2"><?=$arr['SUB_TYPE']?></font></td>
			<td ><font size="2"><?=$arr['MAC_ADDR_WAN']?></font></td>
			<td ><font size="2"><?=$arr['STATUS']?></font></td>
			<td ><font size="2"><?=$arr['SALESID']?></font></td>
			<td><font size='2'> <a href='#' onClick="$('#selectedDeviceID').val('<?echo $arr[DEVICE_ID]?>').change();"><font color='orange'> View Details</font></a></font></td>

		</tr>
	<?}?>
</table>
<script type='text/javascript'>
var pager = new Pager('results', 400);
pager.init();
pager.showPageNav('pager', 'pageNavPosition');
pager.showPage(1);

</script>