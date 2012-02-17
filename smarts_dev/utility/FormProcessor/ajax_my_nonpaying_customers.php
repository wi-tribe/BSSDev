<?php

	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR();
       // print_r($sales_rep);
       $Executives = $sales_obj->getMyExecutives($sales_rep['SHOP_ID']);
       
        if($_POST['SearchOption']==null)
        {
            //$sales = $sales_obj->getSalesByShop($sales_rep['SHOP_ID']);
            $sales = $sales_obj->getSalesBySalesRepID($sales_rep['SALESPERSONNEL_ID']);
        }

        if($_POST['SearchOption']!=null)
        {
           $filter['SearchOption'] = $_POST['SearchOption'];
           $filter['SearchText'] = $_POST['SearchText'];
           $filter['LastReasonCode'] = $_POST['LastReasonCode'];
           $filter['paymentstatus'] = $_POST['paymentstatus'];
           $filter['Promos'] = $_POST['Promos'];
           //$sales = $sales_obj->getSalesByShop($sales_rep['SHOP_ID'],$filter);
           $sales = $sales_obj->getSalesBySalesRepID($sales_rep['SALESPERSONNEL_ID'],$filter);
        }
         if($sales==null)
         {
             echo "No Data found!!!";
         }
?>

<table width="100%">
  <tr>
	<td  class="textboxBur">S/N</td>
    <td class="textboxBur" align="center" ><strong>Customer ID</strong></td>
     <td class="textboxBur" align="center" ><strong>Promo 1</strong></td>
    <td class="textboxBur" align="center"><strong>Customer Name</strong></td>
    <td class="textboxBur" align="center"><strong>Times Called</strong></td>
    <td class="textboxBur" align="center">Last Called On</td>
	<td class="textboxBur" align="center"><strong>Last Reason Code</strong></td>
	 <td class="textboxBur" align="center"><strong>DUE NOW</strong></td>
         <td class="textboxBur" align="center"><strong>Payment Status</strong></td>
  </tr>
  <?	$index=1;
		foreach($sales as $sale)
		{ ?>
          <tr>
		  <td><?=$index++?></td>
            <td class="textboxGray"><?=$sale['CUSTOMER_ID']?></td>
            <td class="textboxGray"><?=$sale['PROMO1']?></td>
            <td class="textboxGray"><?=$sale['FIRST_NAME']?> <?=$sale['LAST_NAME']?></td>
			
            <td class="textboxGray"><?=$sale['TIMES_CALLED']?></td>
            <td class="textboxGray"><?=$sale['LAST_CALLED_ON']?></td>
			<td class="textboxGray"><?=$sale['LAST_REASON_CODE']?></td>
			 <td class="textboxGray"><strong><?=$sale['TOTAL_DUE']?></strong></td>
                         <td class="textboxGray"><strong><?=$sale['PAYMENT_STATUS']?></strong></td>
                         
            <td class="textboxGray" align="center">
				<a href="#" class= "options" onclick="javascript:window.open('Pages/payable_details.php?rec_id=<?=$sale['REC_ID']?>', '_blank','top=100, left=100,height=520, width=1100, status=no, menubar=no, resizable=no, scrollbars=yes, toolbar=no,location=no, directories=no');"><img src='../images/icon-details-red.jpg' alt="Details"/></a>
			</td>
          </tr>
  <? } ?>
</table>
 