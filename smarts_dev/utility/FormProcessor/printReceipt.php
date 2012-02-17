<?
        session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/DBAccess.php");
	$sales_obj = new SalesPersonnel();

       $Searchfilter['customerid'] = $_REQUEST['customer_id'];
       $JOIN_VU_CUST=true;
       $lastPayment_made = $sales_obj->getCustomerPayments($Searchfilter,$JOIN_VU_CUST);
?>
<!DOCTYPE HTML>
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Receipt</title>
<!--
<link href="styles/CustomerPayment_style.css" rel="stylesheet" type="text/css">!-->
<style type="text/css"></style>
</head>
<style type="text/css">
@media print {
input#Print { display: none; }
}
</style>
<script type="text/javascript">    
function onbodyload()
{
    document.getElementById('MyDiv').innerHTML = window.opener.document.getElementById("complete_name").value;
}

</script>
<body onload="onbodyload();">
<form id="form1" name="form1" method="post" action="">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="946">
<tbody><tr>
<td colspan="2" align="right" valign="top">
<table align="center" cellpadding="0" cellspacing="0" width="869">
<tbody>
    <tr>
<td colspan="2" align="center" width="902"><span class="heading-fom"><img src="../../images/invoice-header-letter.jpg" alt="" border="0" height="134" width="925"></span></td>
</tr>
</tbody></table>
</td>
</tr>
<tr>
<td valign="top" width="669"></td><td valign="top" width="267">
<br>
<img src="../../images/invoice-wi-tribe.png" height="100" width="260" alt=""></td>
</tr>
<tr>
<td colspan="2" valign="top">
<table align="center" border="0" width="916">
<tbody>
    <tr>
        <td colspan="2" class="heading" align="right"><input type="button" class="button" id="Print" name="Print" value="Print" onclick=" window.print();"</td>
    </tr>
    <tr>
        <td colspan="2" class="heading">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <strong><font size="6">Payment Receipt</font></strong></td>
</tr>
</tbody></table>
</td>
</tr>
</tbody></table>
<table align="center"><tbody>
        <tr>
            <td>
                &nbsp;
            </td>
        </tr>
            <tr>
                <td><font size="4">Serial No:</font></td><td><label><font size="4">PS-<?=$lastPayment_made[0]['PAYMENT_ID']?></font></label></td>
            </tr>

            <tr>
                <td><font size="4">Date:</font></td><td><label><font size="4"><?=$lastPayment_made[0]['PAYMENT_DATE']?></font></label></td>
            </tr>

            <tr>
                <td><font size="4">Customer ID:</font></td><td><label><font size="4"><?=$lastPayment_made[0]['CUSTOMER_ID']?></font></label></td>
            </tr>
            <tr>
                <td><font size="4">Customer Name:</font></td>
                <td><div id="MyDiv"></div></td>
            </tr>
            <tr>
                <td ><font size="4">Payment Mode:</font></td><td><label><font size="4">Cash Payment</font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Region ID:</font></td><td><label><font size="4"><?=$lastPayment_made[0]['ADDR_CITY']?></font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Sales Shop ID:</font></td><td><label><font size="4"><?=$lastPayment_made[0]['SHOP_ID']?></font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Agent ID:</font></td><td><label><font size="4"><?=$lastPayment_made[0]['SALES_REP_ID']?></font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Payment Amount:</font></td><td><label><font size="4">Rs. <?=$lastPayment_made[0]['AMOUNT_PAID']?></font></label></td><td></td>
            </tr>
            <tr>
                <td colspan="6"></td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
            </tr>
            <tr>
                <td colspan="6">*This is a system generated receipt and does not require any signatures </td>
            </tr>
<tr>
    <td colspan="2" align="center"><img src="../images/footer.jpg" height="51" width="906" alt=""></td>
</tr>
<tr>
    <td colspan="2" align="center"><img src="../images/retail.jpg" alt=""></td>
</tr>
</tbody></table>
</form>
</body></html>