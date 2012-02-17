<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");

         $permission = new permissions();

	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR();

        $Executives = $sales_obj->getMyExecutives($sales_rep['SHOP_ID']);

        if($_POST['executive']!=null)
        {
            $sales = $sales_obj->getExecutiveAllocatedCalls($_POST['executive']);
        }
        else
        {
            $sales = $sales_obj->getSalesByShop($sales_rep['SHOP_ID']);
        }

         if($sales==null)
         {
             echo "No Data found!!!";
             exit();
         }

?>   
<table width="100%">
  <tr>
	<td  class="textboxBur">S/N</td>
	<td  class="textboxBur">Profile</td>
    <td class="textboxBur" align="center" ><strong>Customer ID</strong></td>
    <td class="textboxBur" align="center" ><strong>Metering Promo</strong></td>
    <td class="textboxBur" align="center"><strong>Customer Name</strong></td>
    <td class="textboxBur" align="center"><strong>Times Called</strong></td>
    <td class="textboxBur" align="center">Last Called On</td>
	<td class="textboxBur" align="center"><strong>Last Reason Code</strong></td>
	 <td class="textboxBur" align="center"><strong>DUE NOW</strong></td>
         <td class="textboxBur" align="center"><strong>Payment Status</strong></td>
         <td class="textboxBur" align="center"><strong>Previously Allocated Executive</strong></td>
         <td class="textboxBur" align="center"><strong>Mark Customer for Allocation</strong></td>
  </tr>
  <?	$index=1;
		foreach($sales as $sale)
		{ ?>
          <tr>
		  <td><?=$index++?></td>
			<td class="textboxGray"><?=$sale['CUSTOMER_PROFILE']?></td>
            <td class="textboxGray"><?=$sale['CUSTOMER_ID']?></td>
            <td class="textboxGray"><?=$sale['METERING_PROMO']?></td>
            <td class="textboxGray"><?=$sale['FIRST_NAME']?> <?=$sale['LAST_NAME']?></td>

            <td class="textboxGray"><?=$sale['TIMES_CALLED']?></td>
            <td class="textboxGray"><?=$sale['LAST_CALLED_ON']?></td>
			<td class="textboxGray"><?=$sale['LAST_REASON_CODE']?></td>
			 <td class="textboxGray"><strong><?=$sale['TOTAL_DUE']?></strong></td>
                         <td class="textboxGray"><strong><?=$sale['PAYMENT_STATUS']?></strong></td>
                         <td class="textboxGray"><strong><?=$sale['PREVIOUS_SE']?></strong></td>
                         <td class="textboxGray">
                             <input type="checkbox" name="customers []" id="customers" value="<?=$sale['CUSTOMER_ID']?>" onclick="ManualChecked();">
                </td>

            <td class="textboxGray" align="center">
				<a href="#" class= "options" onclick="javascript:window.open('Pages/payable_details.php?rec_id=<?=$sale['REC_ID']?>', '_blank','top=100, left=100,height=520, width=1100, status=no, menubar=no, resizable=no, scrollbars=yes, toolbar=no,location=no, directories=no');"><img src='../images/icon-details-red.jpg' alt="Details" /></a>
			</td>
          </tr>
  <? } ?>
</table>