<?php
	session_start();
	ob_start();
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(UTIL_CLASSDIR."/NadraWrapper.php");
        include_once(CLASSDIR."/Logging.php");
	
	$CNIC = trim($_POST['CNIC']);
	$MAC = trim($_POST['MAC']);
	
	$nadra_obj = new NadraWrapper();
        $log_obj = new Logging();
        
        $result = $nadra_obj->verify($CNIC, $MAC);
        
	
	$customer=new SalesPersonnel();
        $cdata=$customer->getCustomerByNIC($CNIC);


	
?>
<table width='70%' >
        <tr >
            <td width="22%"><strong>CNIC Verification</strong></td>
             <td width="78%"><strong><?=$CNIC?></strong></td>
        </tr>
          <tr>
            <td valign="top"><strong>Details</strong></td>
            <td width="78%">
            	<table width="100%">
                	<tr>
                        <td width="26%">Citizen Number</td>
                        <td width="58%" style="font-size: 16px"><?=$result['citizen_no']?></td>
                    </tr>
                    <tr>
                        <td width="26%">Name</td>
                        <td width="58%" style="font-size: 16px"><?=$result['applicant_name']?></td>
                    </tr>
                    <tr>
                        <td width="26%">Father's Name</td>
                        <td width="58%" style="font-size: 16px"><?=$result['father_name']?></td>
                    </tr>
                    <tr>
                        <td width="26%">Mother's Name</td>
                        <td width="58%" style="font-size: 16px"><?=$result['mother_name']?></td>
                    </tr>
                    <tr>
                        <td width="26%">Birth Place</td>
                        <td width="58%" style="font-size: 16px"><?=$result['birth_place']?></td>
                    </tr>
                    <tr>
                        <td width="26%">Address</td>
                        <td width="58%" style="font-size: 16px"><?=$result['address']?></td>
                    </tr>
                    <tr>
						<? if(!is_numeric($_POST['CNIC']) || strlen($_POST['CNIC'])!=13)
							{ ?>
								<td colspan="2" align="center" bgcolor="#CCCCCC" style="color: #F00; font-size: 18px;"><strong>Please Provide 13 Digit Numeric Value</strong></td>
							<? $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." provided incorrect CNIC less than 13 digit numeric value");

                                                        } else{
							 if($result['verified_p'])
                                                            { ?>
								<td colspan="2" align="center" bgcolor="#CCCCCC" style="color: #093; font-size: 18px;"><strong>CNIC Verified</strong></td>
                         <? 
                            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." successfuly verified customer CNIC: ".$CNIC);
                         } else
						 	{ ?>
                   	  <td width="16%" colspan="2" bgcolor="#CCCCCC"  align="center" style="color: #F00; font-size: 18px;">CNIC Verification Failed</td>
                    	<? $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." unable to verify customer CNIC: ".$CNIC);
                        } ?>
                    <? } ?>
                  </tr>
                </table>
           	</td>

		</tr>
       <tr>
      <td colspan="2">
  		<table width="100%" border="1" cellspacing="1" cellpadding="2">
            	  <tr>
                  	<td><strong>Customer ID</strong></td>
            	    <td><strong>Name</strong></td>
            	    <td><strong>Address</strong></td>
            	    <td><strong>Identification</strong></td>
            	    <td><strong>Tel Number</strong></td>
            	    <td><strong>Cell Number</strong></td>
            	    <td><strong>Email</strong></td>
            	    <td><strong>Creation Date</strong></td>
            	    <td><strong>Churn</strong></td>
            	    <td><strong>Business Type</strong></td>
            	    <td><strong>Status</strong></td>
            	    <td><strong>Status Date</strong></td>
            	    <td><strong>Due Now</strong></td>
       	        </tr>

                <? if(!($cdata==null)) {

                foreach($cdata as $arr) {

                ?>
            	  <tr>
            	    <td><?=$arr[CUSTOMER_ID]?></td>
            	    <td><?=$arr[FIRST_NAME]?>&nbsp;<?=$arr[LAST_NAME]?></td>
            	    <td><?=$arr[ADDRESS]?>,&nbsp;<?=$arr[CITY]?></td>
            	    <td><?=$arr[IDENTIFICATION_NUMBER]?></td>
            	    <td><?=$arr[TELEPHONE_NUMBER]?></td>
            	    <td><?=$arr[MOBILE_NUMBER]?></td>
            	    <td><?=$arr[EMAIL_ADDRESS]?></td>
            	    <td><?=$arr[DATE_CREATED]?></td>
            	    <td><?=$arr[CHURN]?></td>
            	    <td><?=$arr[BUSINESS_TYPE]?></td>
            	    <td><?=$arr[CUSTOMER_STATUS]?></td>
            	    <td><?=$arr[LAST_STATUS_DATE]?></td>
					<td><?=$arr[DUE_NOW]?></td>
          	    </tr>
                <? } } ?>
       	    </table>
      </td>
      </tr>
</table>