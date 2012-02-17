<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
	$sales_obj = new SalesPersonnel();
        $values = $_POST;
?>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../../vims/styles/style.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>
</head>
<script type="text/javascript">
function submitForm()
{
        document.getElementById('ConfirmPayment').disabled=true;
        document.forms["customerPayment"].submit();
}
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="customerPayment" id="customerPayment" action="../FormProcessor/ajax_payment_confirmed.php" method="post">
<table bordercolor="#CCCCCC" bgcolor="#FFFFFF">
    <input type="hidden" name="customerid" id="customerid" value="<?=$values['customerid']?>"></input>
    <input type="hidden" name="first_name" id="first_name" value="<?=$values['first_name']?>"></input>
    <input type="hidden" name="last_name" id="customerid" value="<?=$values['last_name']?>"></input>
    <input type="hidden" name="cnic" id="cnic" value="<?=$values['cnic']?>"></input>
    <input type="hidden" name="telephoneno" id="telephoneno" value="<?=$values['telephoneno']?>"></input>
    <input type="hidden" name="mobileno" id="mobileno" value="<?=$values['mobileno']?>"></input>
    <input type="hidden" name="email" id="email" value="<?=$values['email']?>"></input>
    <input type="hidden" name="date_created" id="date_created" value="<?=$values['date_created']?>"></input>
    <input type="hidden" name="business_type" id="business_type" value="<?=$values['business_type']?>"></input>
    <input type="hidden" name="customer_status" id="customer_status" value="<?=$values['customer_status']?>"></input>
    <input type="hidden" name="amount_due" id="amount_due" value="<?=$values['amount_due']?>"></input>
    <input type="hidden" name="amount_paid" id="amount_paid" value="<?=$values['amount_paid']?>"></input>
    <input type="hidden" name="sales_rep_id" id="sales_rep_id" value="<?=$values['sales_rep_id']?>"></input>
    <input type="hidden" name="sales_rep_name" id="sales_rep_name" value="<?=$sales_rep['sales_rep_name']?>"></input>
    <input type="hidden" name="shop_id" id="shop_id" value="<?=$values['shop_id']?>"></input>
    <input type="hidden" name="region" id="region" value="<?=$values['region']?>"></input>
    <input type="hidden" name="payment_desc" id="payment_desc" value="<?=$values['payment_desc']?>"></input>
    
    <tr>
        <td>
            <table width="100%">
                <tr>
                            <td><img src="../../images/invoice-header-letter.jpg" alt=""/></td>
                </tr>
            </table>
            </td>
        <td>
            <table>
                <tr><td><img src="../../images/invoice-wi-tribe.png" alt="" /></td></tr>
            <tr>
                     <td>&nbsp;</td>
            </tr>
            </table>
        </td>
    </tr>
</table>
    <table bordercolor="#CCCCCC" bgcolor="#FFFFFF" width="70%">
    <tr>
        <td>
        <table>
                 <tr>
                     <td>&nbsp;</td>
                </tr>
                <tr>
                     <td><img src='../../images/orderform/account_details.png' alt="" /></td>
                </tr>
        </table>
        </td>
    </tr>
     <tr>
        <td>
            <table>
                <tr>
                     <td class="orangeText">CUSTOMER ID: </td>
                      <td><?=$values['customerid']?></td>
                </tr>
            </table>
        </td>
     </tr>
    <tr>
        <td>
            <table>
                <tr>
                     <td class="orangeText">CUSTOMER NAME: </td>
                      <td><?=$values['first_name']?>&nbsp;<?=$values['last_name']?></td>
                </tr>
            </table>
        </td>
                     <td>&nbsp;</td>
        <td>
            <table>
                <tr>
                     <td class="orangeText">USERNAME: </td>
                      <td><?=$values['username']?></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
             <table>
                <tr>
                     <td class="orangeText">ADDRESS: </td>
                      <td class=""><?=$values['address']?></td>
                </tr>
            </table>

        </td>
        <td>&nbsp;</td>
        <td>
             <table>
                <tr>
                     <td class="orangeText">IDENTIFICATION NUMBER: </td>
                      <td class=""><?=$values['cnic']?></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td>
             <table>
                <tr>
                     <td class="orangeText">TELEPHONE NUMBER: </td>
                      <td class=""><?=$values['telephoneno']?></td>
                </tr>
            </table>

        </td>
        <td>&nbsp;</td>
        <td>
             <table>
                <tr>
                     <td class="orangeText">MOBILE NUMBER: </td>
                      <td class=""><?=$values['mobileno']?></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td>
             <table>
                <tr>
                     <td class="orangeText">EMAIL ADDRESS: </td>
                      <td class=""><?=$values['email']?></td>
                </tr>
            </table>

        </td>
        <td>&nbsp;</td>
        <td>
             <table>
                <tr>
                     <td class="orangeText">DATE CREATED: </td>
                      <td class=""><?=$values['date_created']?></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td>
             <table>
                <tr>
                     <td class="orangeText">BUSINESS TYPE: </td>
                      <td class=""><?=$values['business_type']?></td>
                </tr>
            </table>
        </td>
        <td>&nbsp;</td>
        <td>
             <table>
                <tr>
                     <td class="orangeText">CUSTOMER STATUS: </td>
                      <td class=""><?=$values['customer_status']?></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td>
             <table>
                <tr>
                     <td class="orangeText">DUE AMOUNT: </td>
                      <td class=""><?=$values['amount_due']?></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
         <td>
             <table>
                <tr>
                     <td class="orangeText"><strong>AMOUNT PAID:</strong></td>
                     <td>Rs. <?=$values['amount_paid']?></td>
                </tr>
             </table>
         </td>
        <td>&nbsp;</td>
        <td>
            <table>
                 <tr>
                       <td class="orangeText"><strong>PAYMENT DESCRIPTION: </strong></td>
                     <td><?=$values['payment_desc']?></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table>
                 <tr>
                     <td>
                         <input type="button"  class="button" id="ConfirmPayment" name="ConfirmPayment" value="Confirm Payment" onclick="submitForm();"/>
                     </td>
                </tr>
            </table>
        </td>
    </tr>
            </table>
    </form>

<!-- End ImageReady Slices -->
</body>
</html>
