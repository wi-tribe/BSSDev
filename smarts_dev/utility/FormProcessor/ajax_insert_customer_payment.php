<?php
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        
        $Customer_Data['CUSTOMER_ID'] = base64_decode($_REQUEST['CUSTOMER_ID']);
        $Customer_Data['FIRST_NAME'] = base64_decode($_REQUEST['FIRST_NAME']);
        $Customer_Data['LAST_NAME'] = base64_decode($_REQUEST['LAST_NAME']);
        $Customer_Data['IDENTIFICATION_NUMBER'] = base64_decode($_REQUEST['IDENTIFICATION_NUMBER']);
        $Customer_Data['TELEPHONE_NUMBER'] = base64_decode($_REQUEST['TELEPHONE_NUMBER']);
        $Customer_Data['MOBILE_NUMBER'] = base64_decode($_REQUEST['MOBILE_NUMBER']);
        $Customer_Data['EMAIL_ADDRESS'] = base64_decode($_REQUEST['EMAIL_ADDRESS']);
        $Customer_Data['DATE_CREATED'] = base64_decode($_REQUEST['DATE_CREATED']);
        $Customer_Data['BUSINESS_TYPE'] = base64_decode($_REQUEST['BUSINESS_TYPE']);
        $Customer_Data['CUSTOMER_STATUS'] = base64_decode($_REQUEST['CUSTOMER_STATUS']);
        $Customer_Data['DUE_NOW'] = base64_decode($_REQUEST['DUE_NOW']);
        $Customer_Data['DATE_CREATED'] = base64_decode($_REQUEST['DATE_CREATED']);
        $Customer_Data['BUSINESS_TYPE'] = base64_decode($_REQUEST['BUSINESS_TYPE']);
        $Customer_Data['ADDRESS'] = base64_decode($_REQUEST['ADDRESS']);
        $Customer_Data['CPE_MAC_ADDRESS'] = base64_decode($_REQUEST['CPE_MAC_ADDRESS']);
        $Customer_Data['LOGIN'] = base64_decode($_REQUEST['LOGIN']);
        $Customer_Data['SERIAL_NO'] = base64_decode($_REQUEST['SERIAL_NO']);      
        
        $sales_obj = new SalesPersonnel();

        $filter['searchOption'] = 'CustomerID';
        $Searchfilter['customerid'] = $Customer_Data['CUSTOMER_ID'];
        $lastPayment_made = $sales_obj->getCustomerPayments($Searchfilter);
        $sales_rep = $sales_obj->getCurrentCSR();
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

function preSubmitvalidate()
{
    if(document.getElementById('amount_paid').value=='')
    {
        alert('Paid amount is empty');
        exit();
    }

    if(isNaN(document.getElementById('amount_paid').value))
    {
        alert('Amount paid subject to be integer values');
        exit();
    }

    if(document.getElementById('amount_paid').value<1)
    {
        alert('Amount paid subject to be non zero positive value ');
        exit();
    }

    document.forms["customerPayment"].submit();
}
function printReceipt()
{
    var customerID = document.getElementById('customerid').value;
    window.open ("../FormProcessor/printReceipt.php?customer_id="+customerID,"mywindow","menubar=1,resizable=1,scrollbars=1,width=350,height=250");
}

function hide()
{
      if (document.getElementById('payment_detail_row1').style.display == 'inline')
      {
            document.getElementById('payment_detail_row1').style.display = 'none';
            document.getElementById('payment_detail_row2').style.display = 'none';
            document.getElementById('payment_detail_row3').style.display = 'none';
            document.getElementById('payment_detail_row4').style.display = 'none';
      }
      else
      {
            document.getElementById('payment_detail_row1').style.display = 'inline';
            document.getElementById('payment_detail_row2').style.display = 'inline';
            document.getElementById('payment_detail_row3').style.display = 'inline';
            document.getElementById('payment_detail_row4').style.display = 'inline';
      }

}

function init()
{
            document.getElementById('payment_detail_row1').style.display = 'none';
            document.getElementById('payment_detail_row2').style.display = 'none';
            document.getElementById('payment_detail_row3').style.display = 'none';
            document.getElementById('payment_detail_row4').style.display = 'none';
}

</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="init();">
<form name="customerPayment" id="customerPayment" action="ajax_show_customer_payment.php" method="post">
<table>
    <input type="hidden" name="customerid" id="customerid" value="<?=$Customer_Data['CUSTOMER_ID']?>"></input>
    <input type="hidden" name="first_name" id="first_name" value="<?=$Customer_Data['FIRST_NAME']?>"></input>
    <input type="hidden" name="last_name" id="last_name" value="<?=$Customer_Data['LAST_NAME']?>"></input>
     <input type="hidden" name="complete_name" id="complete_name" value="<?=$Customer_Data['FIRST_NAME'].' '.$Customer_Data['LAST_NAME']?>"></input>
    <input type="hidden" name="cnic" id="cnic" value="<?=$Customer_Data['IDENTIFICATION_NUMBER']?>"></input>
    <input type="hidden" name="telephoneno" id="telephoneno" value="<?=$Customer_Data['TELEPHONE_NUMBER']?>"></input>
    <input type="hidden" name="mobileno" id="mobileno" value="<?=$Customer_Data['MOBILE_NUMBER']?>"></input>
    <input type="hidden" name="email" id="email" value="<?=$Customer_Data['EMAIL_ADDRESS']?>"></input>
    <input type="hidden" name="mobileno" id="mobileno" value="<?=$Customer_Data['MOBILE_NUMBER']?>"></input>
    <input type="hidden" name="date_created" id="date_created" value="<?=$Customer_Data['DATE_CREATED']?>"></input>
    <input type="hidden" name="business_type" id="business_type" value="<?=$Customer_Data['BUSINESS_TYPE']?>"></input>
    <input type="hidden" name="customer_status" id="customer_status" value="<?=$Customer_Data['CUSTOMER_STATUS']?>"></input>
    <input type="hidden" name="amount_due" id="amount_due" value="<?=$Customer_Data['DUE_NOW']?>"></input>
    <input type="hidden" name="sales_rep_id" id="sales_rep_id" value="<?=$sales_rep['SALESPERSONNEL_ID']?>"></input>
    <input type="hidden" name="sales_rep_name" id="sales_rep_name" value="<?=$sales_rep['FIRST_NAME'].' '.$sales_rep['LAST_NAME']?>"></input>
    <input type="hidden" name="shop_id" id="shop_id" value="<?=$sales_rep['SHOP_ID']?>"></input>
    <input type="hidden" name="region" id="region" value="<?=$sales_rep['ADDR_CITY']?>"></input>
    <input type="hidden" name="address" id="address" value="<?=$Customer_Data['ADDRESS']?>"></input>
    <input type="hidden" name="cpe_mac" id="cpe_mac" value="<?=$Customer_Data['CPE_MAC_ADDRESS']?>"></input>
    <input type="hidden" name="cpe_serial" id="cpe_serial" value="<?=$Customer_Data['SERIAL_NO']?>"></input>
    <input type="hidden" name="username" id="username" value="<?=$Customer_Data['LOGIN']?>"></input>

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
<table  width="100%" bordercolor="#CCCCCC" bgcolor="#FFFFFF">
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
                      <td><?=$Customer_Data['CUSTOMER_ID']?></td>
                </tr>
            </table>
        </td>
    </tr>
   <tr>
        <td>
            <table>
                <tr>
                     <td class="orangeText">CUSTOMER NAME: </td>
                      <td><?=$Customer_Data['FIRST_NAME']?>&nbsp;<?=$Customer_Data['LAST_NAME']?></td>
                </tr>
            </table>
        </td>
        <td>
            <table>
                <tr>
                     <td class="orangeText">USERNAME: </td>
                      <td><?=$Customer_Data['LOGIN']?></td>
                </tr>
            </table>
        </td>
    </tr>
      <tr>
        <td>
            <table>
                <tr>
                     <td class="orangeText">CUSTOMER ADDRESS: </td>
                      <td><?=$Customer_Data['ADDRESS']?></td>
                </tr>
            </table>
        </td>
           <td>
             <table>
                <tr>
                     <td class="orangeText">IDENTIFICATION NUMBER: </td>
                      <td class=""><?=$Customer_Data['IDENTIFICATION_NUMBER']?></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
       <td>
             <table>
                <tr>
                     <td class="orangeText">MOBILE NUMBER: </td>
                      <td class=""><?=$Customer_Data['MOBILE_NUMBER']?></td>
                </tr>
            </table>

        </td>
        <td>
             <table>
                <tr>
                     <td class="orangeText">TELEPHONE NUMBER: </td>
                      <td class=""><?=$Customer_Data['TELEPHONE_NUMBER']?></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td>
             <table>
                <tr>
                     <td class="orangeText">EMAIL ADDRESS: </td>
                      <td class=""><?=$Customer_Data['EMAIL_ADDRESS']?></td>
                </tr>
            </table>

        </td>
        <td>
             <table>
                <tr>
                     <td class="orangeText">DATE CREATED: </td>
                      <td class=""><?=$Customer_Data['DATE_CREATED']?></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr>
        <td>
             <table>
                <tr>
                     <td class="orangeText">BUSINESS TYPE: </td>
                      <td class=""><?=$Customer_Data['BUSINESS_TYPE']?></td>
                </tr>
            </table>
        </td>
        <td>
             <table>
                <tr>
                     <td class="orangeText">CUSTOMER STATUS: </td>
                      <td class=""><?=$Customer_Data['CUSTOMER_STATUS']?></td>
                </tr>
            </table>

        </td>
    </tr>
     <? if($lastPayment_made!=null)
    { ?>
    <tr>
					<td colspan="2" align="center" style="font-size:16px"><strong>LAST PAYMENT MADE FROM THIS PORTAL</strong></td>
					</tr>
    <tr>
      <td><a href="#" onclick="hide();">Hide/Unhide Last Payment Details</a></td>
      <td>&nbsp</td>
</tr>
    <tr id="payment_detail_row1">
        <td>
            <table>
                <tr>
                      <td class="orangeText">PAYMENT ID: </td>
                      <td class="">PS-<?=$lastPayment_made[0]['PAYMENT_ID']?></td>
                </tr>
            </table>
        </td>
        <td>
            <table>
                <tr>
                      <td class="orangeText">SALES REP ID: </td>
                      <td class=""><?=$lastPayment_made[0]['SALES_REP_ID']?></td>
                </tr>
            </table>
        </td>
     </tr>
    <tr id="payment_detail_row2">
        <td>
            <table>
                <tr>
                      <td class="orangeText">SHOP ID: </td>
                      <td class=""><?=$lastPayment_made[0]['SHOP_ID']?></td>
                </tr>
            </table>
        </td>
        <td>
            <table>
                <tr>
                    <td class="orangeText">AMOUNT PAID: </td>
                      <td class=""><?=$lastPayment_made[0]['AMOUNT_PAID']?></td>
                </tr>
            </table>
        </td>
     </tr>
    <tr id="payment_detail_row3">
        <td>
            <table>
                <tr>
                     <td class="orangeText">DUE AMOUNT: </td>
                      <td class=""><?=$lastPayment_made[0]['DUE_AMOUNT']?></td>
                </tr>
            </table>
        </td>
        <td>
            <table>
                <tr>
                      <td class="orangeText">PAYMENT DATE: </td>
                      <td class=""><?=$lastPayment_made[0]['PAYMENT_DATE']?></td>
                </tr>
            </table>
        </td>
     </tr>
     <? if($lastPayment_made[0]['PAYMENT_NO']!=null) { ?>
    <tr id="payment_detail_row4">
        <td>
             <table>
                <tr>
                      <td class="orangeText">BRM PAYMENT NUMBER: </td>
                      <td class=""><?=$lastPayment_made[0]['PAYMENT_NO']?></td>
                       </tr>
            </table>
        </td>
    </tr>
    <? } ?>
    <tr>
        <td>
            <input class ="button" type="button" id="PrintLastPaymentReceipt" name="PrintLastPaymentReceipt" value="Print Last Payment Receipt" onclick="printReceipt();"/>
        </td>
    </tr>
    <? } ?>
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
                      <td class=""><?=$Customer_Data['DUE_NOW']?></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
         <td>
             <table>
                <tr>
                     <td class="orangeText"><strong>AMOUNT PAID: </strong></td>
                     <td class=""><input type="text" id="amount_paid" name="amount_paid" value="" maxlength="5"/></td>
                       <td class="orangeText"><strong>PAYMENT DESCRIPTION: </strong></td>
                     <td><textarea id="payment_desc" name="payment_desc"></textarea></td>
                     <td>
                         <input type="button"  class="button" id="Save" name="Save" value="Make Payment" onclick="preSubmitvalidate();"/>
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
