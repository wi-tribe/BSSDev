<?php
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/Logging.php");
        
	$sales_obj = new SalesPersonnel();
        $log_obj = new Logging();

       
	$sales_rep = $sales_obj->getCurrentCSR();

        $filter = $_POST;
        if($_POST['CacheView']=='cacheView')
        {
            $Customer_Data = $sales_obj->getCachedCustomerPayables($filter);
        }
        
        else
        {
            $Customer_Data = $sales_obj->getCustomerPayable($filter);
        }
        
        if($Customer_Data==null)
        {
            echo "Search resulted with no records";
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Customer Payment Search: NO records found against search ");
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
                        <? $query_string = 'CUSTOMER_ID=' . base64_encode($arr[CUSTOMER_ID]) . '&FIRST_NAME=' . base64_encode($arr['FIRST_NAME']). '&LAST_NAME='.base64_encode($arr['LAST_NAME']).
                                            '&IDENTIFICATION_NUMBER=' . base64_encode($arr[IDENTIFICATION_NUMBER]) .'&TELEPHONE_NUMBER=' . base64_encode($arr[TELEPHONE_NUMBER]) .'&MOBILE_NUMBER=' . base64_encode($arr[MOBILE_NUMBER]).
                                            '&EMAIL_ADDRESS=' . base64_encode($arr[EMAIL_ADDRESS]) .'&DATE_CREATED=' . base64_encode($arr[DATE_CREATED]).'&BUSINESS_TYPE='.base64_encode($arr[BUSINESS_TYPE]).'&DUE_NOW=' . base64_encode($arr[DUE_NOW]).
                                            '&CUSTOMER_STATUS=' . base64_encode($arr[CUSTOMER_STATUS]).'&ADDRESS=' . base64_encode($arr[ADDRESS]).'&CPE_MAC_ADDRESS=' . base64_encode($arr[CPE_MAC_ADDRESS]).'&LOGIN=' . base64_encode($arr[LOGIN]).
                                            '&SERIAL_NO=' . base64_encode($arr[SERIAL_NO]);
                       ?>
                        <!--<a href="ajax_insert_customer_payment.php?customer_id=<?=$arr[CUSTOMER_ID]?>" title="Customer Payment" onclick="return GB_showFullScreen('Customer Payment', this.href,550,1210)"><font color="red">Details</font></a>!-->
                        <a href="../FormProcessor/ajax_insert_customer_payment.php?<?=$query_string?>" title="Customer Payment" onclick="return GB_myShow('Customer Payment ', this.href,570,1210)"><font color="red">Details</font></a>
                    </td>
          	    </tr>
                <? } } ?>
    </tbody>
</table>