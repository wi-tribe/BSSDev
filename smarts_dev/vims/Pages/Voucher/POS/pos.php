<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//creating new object
$pos=new POS();

$vData_=false;


//get wh data
if(($vData=$pos->myVouchers()))
{
	$whData_=true;
}

?>
	<table align="center" width="90%" border=0>
		<tr valign='top' class='textbox' >
			<td>
			  <table width="100%" border="0" cellspacing="1" cellpadding="1">
				  <tr>
				    <td colspan="2" align="center" class="higlight">Point of Sale</td>
			    </tr>
				 
		    </table></td>
		</tr>
		<tr valign='top' class='textbox' >
		  <td align="center"><form name='VoucherAddRange' id='VoucherAddRange' method='post' action=''>
          <!-- List vouchers -->
          <table width="70%" border="0" cellspacing="1" cellpadding="2">
            <tr>
              <td width="13%" align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td colspan="3" bgcolor="#EFEFEF">Voucher (Serial Numbers)</td>
            </tr>
            <tr>
              <td align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td colspan="3" align="right" bgcolor="#EFEFEF"><label>
                <input class="button" type="button" name="sold_button" id="sold_button" value="Sold" onclick="javascript:processForm( 'VoucherAddRange','FormProcessor/POS/pos.php','MsgDiv' );"/>
                <input class="button" type="button" name="activate_button" id="activate_button" value="Activate" />
                <input class="button" type="button" name="deactivate_button" id="deactivate_button" value="Deactivate" />
                <input class="button" type="button" name="Allocate Vouchers" id="Allocate Vouchers" value="Allocate Vouchers" />
                <input class="button" type="button" name="Return Vouchers" id="Return Vouchers" value="Return Vouchers" />
                <input class="button" type="button" name="Replace Voucher" id="Replace Voucher" value="Replace Voucher" />
                <input class="button" type="button" name="Add_discount" id="Add_discount" value="Add Discount" />
              </label></td>
            </tr>
            <div id="POS"></div>
            <?php  if(!empty($vData)) { for($i=0; $i< sizeof($vData);$i++) { ?>
            <tr>
              <td width="13%" align="center">
              <input type="checkbox" name="voucher_serial[]" id="voucher_serial" value="<?=$vData[$i]['voucher_serial']?>" /></td>
              <td width="37%" align="left"><?=$vData[$i++]['voucher_serial']?></td>
              <td width="13%" align="center">
              <input type="checkbox" name="voucher_serial[]" id="voucher_serial" value="<?=$vData[$i]['voucher_serial']?>" /></td>
              <td width="37%" align="left"><?=$vData[$i]['voucher_serial']?></td>
            </tr>
            <?php } } ?>
          </table>
          <!-- end listing vouchers -->
		  </form></td>
  </tr>
	</table>