<?php
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
	$sales_obj = new SalesPersonnel();

	$sales_rep = $sales_obj->getCurrentCSR();

        $filter = $_POST;
        $Customer_Data = $sales_obj->getCustomerPayable($filter);

        if($Customer_Data==null)
        {
            echo "Search resulted with no records";
            exit();
        }
?>

<table id="hor-minimalist-b" summary="Customer Details">
    <thead>
    	<tr>
            <th scope="col"><strong>Customer ID</strong></th>
            <th scope="col"><strong>Name</strong></th>
            <th scope="col"><strong>Identification</strong></th>
            <th scope="col"><strong>Tel Number</strong></th>
            <th scope="col"><strong>Cell Number</strong></th>
            <th scope="col"><strong>Email</strong></th>
            <th scope="col"><strong>Creation Date</strong></th>
            <th scope="col"><strong>Business Type</strong></th>
            <th scope="col"><strong>Status</strong></th>
            <th scope="col"><strong>Due Now</strong></th>

        </tr>
    </thead>
    <tbody>
    	<? if(!($Customer_Data==null)) {
                $index=1;
                foreach($Customer_Data as $arr) {
                ?>
                    <tr>
            	    <td><?=$arr[CUSTOMER_ID]?></td>
            	    <td><?=$arr[FIRST_NAME]?>&nbsp;<?=$arr[LAST_NAME]?></td>
            	    <td><?=$arr[IDENTIFICATION_NUMBER]?></td>
            	    <td><?=$arr[TELEPHONE_NUMBER]?></td>
            	    <td><?=$arr[MOBILE_NUMBER]?></td>
            	    <td><?=$arr[EMAIL_ADDRESS]?></td>
            	    <td><?=$arr[DATE_CREATED]?></td>
            	    <td><?=$arr[BUSINESS_TYPE]?></td>
            	    <td><?=$arr[CUSTOMER_STATUS]?></td>
                    <td><?=$arr[DUE_NOW]?></td>
                    <td>
                        <!--<a href="ajax_insert_customer_payment.php?customer_id=<?=$arr[CUSTOMER_ID]?>" title="Customer Payment" onclick="return GB_showFullScreen('Customer Payment', this.href,550,1210)"><font color="red">Details</font></a>!-->
                        <a href="../FormProcessor/ajax_insert_customer_payment.php?customer_id=<?=$arr[CUSTOMER_ID]?>" title="Customer Payment" onclick="return GB_myShow('Customer Payment ', this.href,570,1210)"><font color="red">Details</font></a>
                    </td>
          	    </tr>
                <? } } ?>

    </tbody>
</table>