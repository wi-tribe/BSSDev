<?
session_start("VIMS");
set_time_limit(700);

include('../../vims_config.php');
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Rental.php");

////check permission
//if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'commissionadd'))
//{
//	echo "Permission denied";
//	exit;
//}
$rental_obj = new Rental();
$rental_id = $_POST['rental_id'];
$rental_details = $rental_obj->getrentalPayableDetails($rental_id);
$channel_obj = new Channel();

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<?=$_HTML_LIBS?>
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <table width="600" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">

		<table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
			<tr>
				<td height="30" colspan="2" background="../images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
				<span class="textboxBur"  align="center" style="font-size:16px" > <strong>RENTAL PAYABLE DETAILS</strong> </span>

				</td>
			</tr>
		</table>

    <table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                          <form name="rentalPayableStatus" id="rentalPayableStatus"  method="post" action="FormProcessor/Rental/EditRental_payables.php">
                            <table width="447" border="0" cellspacing=0" cellpadding="0">
                                <input type="hidden" name="rental_id" id="rental_id" value="<?=$rental_id?>"/>
                                <tr>
                                    <td>Rental ID</td>
                                    <td><?=$rental_details[0]['rental_id']?></td>
                                </tr>
                                 <tr>
                                     <td>Retailer User ID</td>
                                    <td><?=$rental_details[0]['user_id']?></td>
                                 </tr>
                                 <tr>
                                     <td>Retailer First Name</td>
                                    <td><?=$rental_details[0]['first_name']?></td>
                                 </tr>
                                 <tr>
                                     <td>Retaile Last Name</td>
                                    <td><?=$rental_details[0]['last_name']?></td>
                                     
                                 </tr>
                                 <tr>
                                     <td>Retailer Shop ID</td>
                                    <td><?=$rental_details[0]['channel_id']?></td>
                                 </tr>
                                 <tr>
                                     <td>Rental Rule Applied ID</td>
                                  <td><?=$rental_details[0]['rental_rule_id']?></td>
                                 </tr>
                                 <tr>
                                      <td>For Month</td> <? $year = split('[-]', $rental_details[0]['for_month']);?>
                                    <td><?=date("F",$rental_details[0]['for_month'])."-".$year[0]?></td>
                                 </tr>
                                <tr>
                                     <td> Date Paid:</td> <? $date = split('[-]',$rental_details[0]['action_date']); ?>
                                     <td><?=$date[2].' '.$date[1].' '.$date[0]?></td>
                                </tr>
                                <tr> 
                                    <td> Update Status:</td>
                                <td>
                                    <select name="status" id="status" class="selectbox" >
                                        <? if ($rental_details[0]['status']=='0') {?>
                                        <option selected="selected" value="0">UnPaid</option>
                                        <option value="1">Paid</option>
                                        <? } ?>
                                        <? if ($rental_details[0]['status']=='1') {?>
                                        <option selected="selected" value="1">Paid</option>
                                        <? } ?>
                                </select>
                                </td>
                            </tr>
                            
                          </table>
                            <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'>
                                  <input name="action" type='submit' value='Update' /></td>
				</tr>
			  </table>
                        </form>
              </td>
		</tr>
	</table>
    </td>
</tr>
        <tr>
		<td>
		 <table width="600" cellpadding="0" cellspacing="0" bgcolor="#F7F7F7">
		  <tr>
			<td align="center" valign="middle" background="../images/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
		  </tr>
		  </table>
		</td>
	</tr>
</table>

</body>
</html>





