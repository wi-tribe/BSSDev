<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
        session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/Sales.php");
        include_once(UTIL_CLASSDIR."/Prospect.php");
        include_once(UTIL_CLASSDIR."/SmartsUtil.php");	
	
	$smarts_obj = new SmartsUtil();
	$acctInfo['customer_id'] = $_REQUEST['cust_id'];
	$result = $smarts_obj->getAccountDetails($acctInfo);
        
?>

	<table width="100%" border="0" cellpading="0" cellspacing="0">
	<tr align="left">
		<td >
			<div id="customer_detail" class="normalFont">
				<!--////////////////////////////////////////////////////////-->
				<table>
					<tr><td colspan='0'><img src='../images/orderform/customer_details.gif' /></td></tr>
					<tr><td width="250" class='orangeText'>Customer ID</td>			<td>&nbsp;</td><td><strong><?=$result['CUSTOMER_ID']?></strong></td></tr>
					<tr><td width="250" class='orangeText'>Name</td>				<td>&nbsp;</td><td><?=$result['FIRST_NAME']." ".$result['LAST_NAME']?></td></tr>
					<tr><td width="250" class='orangeText'>Customer Address</td>	<td>&nbsp;</td><td><?=$result['ADDRESS']?></td></tr>
					<tr><td width="250" class='orangeText'>City</td>				<td>&nbsp;</td><td><?=$result['REGION_NAME']?></td></tr>
					<tr><td width="250" class='orangeText'>Telephone</td>			<td>&nbsp;</td><td><?=$result['TELEPHONE_NUMBER']?></td></tr>
					<tr><td width="250" class='orangeText'>Mobile</td>				<td>&nbsp;</td><td><?=$result['MOBILE_NUMBER']?></td></tr>
					<tr><td width="250" class='orangeText'>Email Address</td>		<td>&nbsp;</td><td><?=$result['EMAIL_ADDRESS']?></td></tr>

				</table>
		<!--////////////////////////////////////-->
			</div>
		</td>

	</tr>
	<tr align="left">
		<td>
			<div id="customer_account" class="normalFont">
				<table>  
                                    <tr>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
					<tr><td colspan='0'><img src='../images/orderform/account_details.png' /></td></tr>
					
					<tr><td width="250" class='orangeText'>Status</td>						<td>&nbsp;</td><td><strong><?=$result['CUSTOMER_STATUS']?></strong></td></tr>
					<tr><td width="250" class='orangeText'>Creation Date</td>				<td>&nbsp;</td><td><?=$result['DATE_CREATED']?></td></tr>
					<tr><td width="250" class='orangeText'>Last Status Change Date</td>		<td>&nbsp;</td><td><?=$result['LAST_STATUS_DATE']?></td></tr>
					<tr><td width="250" class='orangeText'>Last Status Change Reason</td>	<td>&nbsp;</td><td><?=$result['LAST_COMMENT']?></td></tr>
					<tr><td width="250" class='orangeText'>Account Type</td>				<td>&nbsp;</td><td><?=$result['BUSINESS_TYPE']?></td></tr>
					<tr><td width="250" class='orangeText'>MAC Address</td>					<td>&nbsp;</td><td><?=$result['CPE_MAC_ADDRESS']?></td></tr>
					<tr><td width="250" class='orangeText'>Device Model</td>				<td>&nbsp;</td><td><?=$result['CPE_MODEL']?></td></tr>
					<tr><td width="250" class='orangeText'>Device Type</td>					<td>&nbsp;</td><td><?=$result['CPE_TYPE']?></td></tr>
					<tr><td width="250" class='orangeText'>Profile ID as per package</td>	<td>&nbsp;</td><td><?=$result['PROFILE_ID']?></td></tr>
					
				</table>
			</div>
		</td>
	</tr>
	<tr align="left">
		<td>
			<div id="customer_package" class="normalFont">
				<table>
                                    <tr>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
					<tr><td colspan='0'><img src='../images/orderform/package_selection.png' /></td></tr>
					<tr><td width="250" class='orangeText'>Package</td>			<td>&nbsp;</td><td><?=$result['PACKAGE_NAME']?></td></tr>
					<tr><td width="250" class='orangeText'>Pay Type</td>		<td>&nbsp;</td><td><?=$result['PAY_TYPE']?></td></tr>
					<tr><td width="250" class='orangeText'>Parent Account</td>	<td>&nbsp;</td><td><?=$result['PARENT_ACCT']?></td></tr>
					<tr><td width="250" class='orangeText'>Shop ID</td>			<td>&nbsp;</td><td><?=$result['SHOP_ID']?></td></tr>
					<tr><td width="250" class='orangeText'>Sales Rep</td>		<td>&nbsp;</td><td><?=$result['SALES_REP_NAME']?></td></tr>
					<tr><td width="250" class='orangeText'>Sales Rep ID</td>	<td>&nbsp;</td><td><?=$result['SALES_REP_ID']?></td></tr>
					<tr><td width="250" class='orangeText'>Total Due Now</td>	<td>&nbsp;</td><td><strong><?=$result['TOTAL_DUE']?></strong></td></tr>
				</table>
			</div>
		</td>

	</tr>
	
	</table>