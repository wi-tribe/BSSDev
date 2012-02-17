<?
        session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/DBAccess.php");
        include_once(CLASSDIR."/Logging.php");
        
	$sales_obj = new SalesPersonnel();
        $log_obj = new Logging();
        
       $values = $_POST;
       $result = $sales_obj->InsertCustomerPayment($values);
       
       if(!$result)
       {
           print('Error! Payment record saving failed..');
           $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." unable to post customer payment, customerId:".$values['customerid'].",amount paid: Rs.".$values['amount_paid']);
           exit();
       }
       else
       {
           $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." successfuly posted customer payment, customerId:".$values['customerid'].",amount paid: Rs.".$values['amount_paid']);
       }
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
<body>
<blockquote>
<form id="form1" name="form1" method="post" action="">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="946">
<tbody><tr>
<td colspan="2" align="right" valign="top">
<table align="center" cellpadding="0" cellspacing="0" width="869">
<tbody>
    <tr>
<td colspan="2" align="center" width="902"><span class="heading-fom"><img src="../images/invoiceheader.jpg" alt="" border="0" height="134" width="925"></span></td>
</tr>
</tbody></table>
</td>
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
                <td><font size="4">Serial No:</font></td><td><label><font size="4">PS-<?=$result?></font></label></td>
            </tr>

            <tr>
                <td><font size="4">Date:</font></td><td><label><font size="4"><?=date('d').'/'.date('m').'/'.date("Y")?></font></label></td>
            </tr>

            <tr>
                <td><font size="4">Customer ID:</font></td><td><label><font size="4"><?=$values['customerid']?></font></label></td>
            </tr>
            <tr>
                <td><font size="4">Customer Name:</font></td><td><label><font size="4"><?=$values['first_name'].' '.$values['last_name']?></font></label></td>
            </tr>
            <tr>
                <td ><font size="4">Payment Mode:</font></td><td><label><font size="4">Cash Payment</font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Region ID:</font></td><td><label><font size="4"><?=$values['region']?></font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Sales Shop ID:</font></td><td><label><font size="4"><?=$values['shop_id']?></font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Agent ID:</font></td><td><label><font size="4"><?=$values['sales_rep_id']?></font></label></td>
            </tr>
            <tr>
                <td class="orangeText"><font size="4">Payment Amount:</font></td><td><label><font size="4">Rs. <?=$values['amount_paid']?></font></label></td><td></td>
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
</blockquote>
</body></html>