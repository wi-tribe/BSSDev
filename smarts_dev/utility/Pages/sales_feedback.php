<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../../vims/CSS/style.css" rel="stylesheet" type="text/css"></link>
<style type="text/css">
<!--
body {
	background-color:#CCCCCC ;/*#5F5D60*/
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="styles/style.css" rel="stylesheet" type="text/css"></link>
<script type="text/javascript" src="../FormProcessor/sales_feedback/jquery.js"></script>
<script type="text/javascript" src="../FormProcessor/sales_feedback/jquery-calendar.js"></script>
<link rel="stylesheet" type="text/css" href="../FormProcessor/sales_feedback/jquery-calendar.css" />
<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>
</head>
<?php
session_start();
ob_start();
include_once("../util_config.php");
$conf = new config();

$message = null;
if( $_GET['msg'] == "success")
{
    $message = "Ticket Created Successfully";
}

?>
    <script type="text/javascript">
	//<![CDATA[
                $(document).ready(function (){
                $("#txt_time_call").calendar();
                $("#div_time_call").hide();
                $("#div_email_address").hide();
                $("#txt_time_call").attr("value" , "");
                $("#txt_email_address").attr("value" , "");
			});

            function enable_button_call()
            {
                $("#div_time_call").show("normal");
                $("#div_email_address").hide("normal");
                $("#txt_email_address").attr("value" , "");
            }

            function enable_button_email()
            {
                $("#div_time_call").hide("normal");
                $("#txt_time_call").attr("value" , "");
                $("#div_email_address").show("normal");
            }
	    function enable_button_none()
            {
                $("#div_time_call").hide("normal");
                $("#div_email_address").hide("normal");
                $("#txt_time_call").attr("value" , "");
                $("#txt_email_address").attr("value" , "");
            }

		//]]>
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1000" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="50" colspan="2" background="<?=IMAGES?>/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
					<a href="http://smarts.wi-tribe.net.pk/sales/index.php" onclick="javascript:animatedAjaxCall( 'nav.php','LeftNavigationDiv' );" ><img src="<?=IMAGES?>/main/home.gif" border="0"/></a> &nbsp;&nbsp;|&nbsp; &nbsp;
                                        <a href="http://smarts.wi-tribe.net.pk/sales/logout.php" border="0"><img src="<?=IMAGES?>/main/logout.gif" border="0" alt=""/> </a> &nbsp;&nbsp;|&nbsp; &nbsp;
				</td>
  </tr>
</table>
    <table>
        <tr>
            <td>

            </td>
        </tr>
    </table>
    <table width="1000" align="center" cellpadding="0" cellspacing="0">
  <tr >
    <td>
<form action="../FormProcessor/sales_feedback/issue_handler.php" method="post" name="frm_sales_feedback" id="frm_sales_feedback" >
    <table width="1000" border="0" cellpadding="5" bgcolor="#f7f7f7">
    <?php
        if( $message )
            echo "<tr><td colspan='3'>$message</td></tr>";
    ?>
  <tr>
    <td width="34%"><p>Customer ID :</p></td>
    <td width="3%">&nbsp;</td>
    <td width="63%"><input type="text" name="txt_customer_id" id="txt_customer_id" /></td>
  </tr>
  <tr>
    <td>Sales Shop :</td>
    <td>&nbsp;</td>
    <td><select name="txt_shop_name" id="txt_shop_name">
    <optgroup label="Rawalpindi">
      <option value="shop.RWP-chaklala">Chaklala Shop Rawalpindi</option>
      <option value="shop.RWP-Sadar">Sadar Shop Rawalpindi</option>
      <option value="shop.RWP-SatelliteT">Satellite Town Shop Rawalpindi</option>
      </optgroup>
      <optgroup label="Islamabad">
      <option value="shop.isb-BA">Blue Area Shop Islamabad</option>
      <option value="shop.isb-f10">F-10 Shop Islamabad</option>
      <option value="shop.isb-f8">F-8 Shop Islamabad</option>
      </optgroup>
      <optgroup label="Karachi">
      <option value="shop.KHI-AishaM">Aisha Manzil Shop Karachi</option>
      <option value="shop.KHI-DHA">DHA Shop Karachi</option>
      <option value="shop.KHI-GulistanJ">Gulistan-E-Juhar Shop Karachi</option>
      <option value="shop.KHI-GulshanI">Gulshan-E-Iqbal Shop Karachi</option>
      <option value="shop.KHI-NNazimabad">North Nazimabad Shop Karachi</option>
      <option value="shop.KHI-Sadar">Sadar Atrium Mall Shop Karachi</option>
      <option value="shop.KHI-ShahrahF">Shahrah-E-Faisal Shop Karachi</option>
      <option value="shop.khi-clifton">Clifton Shop Karachi</option>
      </optgroup>
      <optgroup label="Lahore">
      <option value="shop.LHR-AllamaIT">Allama Iqbal Town Shop Lahore</option>
      <option value="shop.LHR-DHA">DHA Shop Lahore</option>
      <option value="shop.LHR-Gulberg">Gulberg Shop Lahore</option>
      <option value="shop.LHR-JoharT">Johar Town Shop Lahore</option>
      <option value="shop.LHR-ModelT">Model Town Shop Lahore</option>
      <option value="shop.LHR-Shadman">Shadman Shop Lahore</option>
      </optgroup>
    </select></td>
  </tr>
  <tr>
    <td><p>How was the experience :</p></td>
    <td>&nbsp;</td>
    <td><select name="txt_issue_type" id="txt_issue_type">
    <optgroup label="Billing issues">
      <option value="Didn't receive bill at all">Didn't receive bill at all</option>
      <option value="Received bill after due date">Received bill after due date</option>
      <option value="Over charging in the bill">Over charging in the bill</option>
      <option value="Late fees">Late fees</option>
      <option value="Bill not easy to understand">Bill not easy to understand</option>
      <option value="Promotions too hard to understand">Promotions too hard to understand</option>
      <option value="Duration of due date is very short">Duration of due date is very short</option>
      <option value="Plan change requested but not changed">Plan change requested but not changed</option>
      </optgroup>
      <optgroup label="Sales issues">
      <option value="Sales person was not answering the phone for issue resolution">Sales person was not answering the phone for issue resolution</option>
      <option value="Plan change requested but not changed">Plan change requested but not changed</option>
      <option value="Miss Commitment by sales executive">Miss Commitment by sales executive</option>
      </optgroup>
      <optgroup label="Customer Care issues">
      <option value="No call back as promised by contact center within given time">No call back as promised by contact center within given time</option>
      <option value="Call back after the given time by contact center">Call back after the given time by contact center</option>
      <option value="No reply back on e-mail correspondence">No reply back on e-mail correspondence</option>
      <option value="Miscommunication / wrong information given by customer care">Miscommunication / wrong information given by customer care</option>
      <option value="Customer care person misbehaved">Customer care person misbehaved</option>
      <option value="Plan change requested but not changed">Plan change requested but not changed</option>
      <option value="Connectivity with helpline">Connectivity with helpline</option>
      </optgroup>
      <optgroup label="Coverage issues issues">
      <option value="Moved out of coverage area">Moved out of coverage area</option>
      <option value="Not good indoor coverage">Not good indoor coverage</option>
      <option value="Intermittent coverage">Intermittent coverage</option>
      <option value="Coverage issue since first day. No proper signals on CPE">Coverage issue since first day. No proper signals on CPE</option>
      </optgroup>
      <optgroup label="Package issues">
      <option value="No unlimited packages offered">No unlimited packages offered</option>
      <option value="Packages too expensive and not competitive">Packages too expensive and not competitive</option>
      <option value="Wrong package was assigned">Wrong package was assigned</option>
      </optgroup>
      <optgroup label="Technical issues">
      <option value="Connectivity issues">Connectivity issues</option>
      <option value="Speed issues">Speed issues</option>
      <option value="Slow down loading speed">Slow down loading speed</option>
      <option value="Particular website / software not being connected">Particular website / software not being connected</option>
      <option value="Particular settings issue">Particular settings issue</option>
      <option value="Network outage too often">Network outage too often</option>
      </optgroup>
      <optgroup label="Personal issues">
      <option value="Personal Issues -Not using internet anymore">Personal Issues -Not using internet anymore</option>
      </optgroup>
      <optgroup label="Payment related issues">
      <option value="Paid the amount at shop but not entered on time">Paid the amount at shop but not entered on time</option>
      <option value="Paid the amount to CSE but not entered on time">Paid the amount to CSE but not entered on time</option>
      <option value="Paid the amount at bank but not entered on time">Paid the amount at bank but not entered on time</option>
      <option value="Paid the amount to home collection person but not entered on time">Paid the amount to home collection person but not entered on time</option>
      </optgroup>
    </select></td>
  </tr>
  <tr>
    <td >Details (if any):</td>
    <td >&nbsp;</td>
    <td ><textarea name="txt_details" id="txt_details" cols="45" rows="5"></textarea></td>
  </tr>
  <tr>
    <td colspan="3">Customer Contact Type</td>
  </tr>
  <tr>
    <td height="33">None :
      <input type="radio" name="rad_notification_type" id="rad_notification_type"  value="none" onclick="enable_button_none()" checked="checked" /></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="33">Call :
    <input type="radio" name="rad_notification_type" id="rad_notification_type" value="call" onclick="enable_button_call()" /></td>
    <td>&nbsp;</td>
    <td><div id="div_time_call" >
    When to call: <input type="text" name="txt_time_call" id="txt_time_call" /></div></td>
  </tr>
  <tr>
    <td>Email:
    <input type="radio" name="rad_notification_type" id="rad_notification_type" value="email" onclick="enable_button_email()" /></td>
    <td>&nbsp;</td>
    <td><div id="div_email_address">
    Email Address:
      <input type="text" name="txt_email_address" id="txt_email_address" /></div></td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input type="submit" name="button" id="button" value="Submit" /></td>
  </tr>
</table>
</form>
                </td>
             </tr>
    <tr>
		<td><? include_once("footer.php")?>
		</td>
	</tr>
             </table>

</body>
</html>


