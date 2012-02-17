<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();  
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/POS.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'poslist'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$voucher=new Voucher();

$vData_=false;
$vUsers_=false;

if(($vUsers=$_USER->getSubordinates($_SESSION['user_id'])))
{
	$vUsers_=true;
}

//get wh data
if(($vData=$voucher->getVoucherDenominations()))
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
		  <td align="center">
              <form name='POSForm' id='POSForm' method='post' action='' onsubmit="javascript: return false;">
          <!-- List vouchers -->
          <table width="75%" border="0" cellspacing="1" cellpadding="2">
            <tr>
              <td width="13%" align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td colspan="3" align="center" bgcolor="#EFEFEF" class="higlight">
                <?php  if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'sellvoucher')) { ?>
                <input class="button" type="button" name="sold_button" id="sold_button" value="Marks As Sold" onclick ="confirmmarkSold(document.POSForm.voucher_serial)"/>
                <!--onclick="javascript:processForm( 'POSForm','FormProcessor/POS/pos.php','MsgDiv' );"!-->
                |<?php  } ?>
                <?php  if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'activatevoucher')) { ?>
                <input class="button" type="button" name="activate_button" id="activate_button" value="Activate"  onclick="javascript:processForm( 'POSForm','FormProcessor/POS/ajax_activate_vouchers.php','MsgDiv' );"/>
                |<?php } ?>
                <?php  if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'deactivatevoucher')) { ?>
                <input class="button" type="button" name="deactivate_button" id="deactivate_button" value="Deactivate" onclick="javascript:processForm( 'POSForm','FormProcessor/POS/ajax_deactivate_vouchers.php','MsgDiv' );"/>
                <?php } ?>
              </td>
            </tr>
            <tr>
              <td width="13%" align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td colspan="3" align="right" bgcolor="#EFEFEF" class="higlight">
              <?php  if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'assignvoucher')) { ?>
                <select name="user_id" id="user_id" class="selectbox">
                  <option value="">Select User</option>
                  <?PHP if(!empty($vUsers)) { for($i=0; $i< sizeof($vUsers);$i++) { ?>
                  <option value="<?=$vUsers[$i]['user_id']?>"><?=$vUsers[$i]['first_name']." ".$vUsers[$i]['last_name']?> (<?=$vUsers[$i]['username']?>)</option>
                  <? } } ?>
                </select>
                <input class="button" type="button" name="Allocate Vouchers" id="Allocate Vouchers" value="Allocate Vouchers" onclick="javascript:processForm( 'POSForm','FormProcessor/POS/ajax_assign_vouchers.php','MsgDiv' );"/>
                |<?php } ?>
                <?php  if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'returnvoucher')) { ?>
                <input class="button" type="button" name="Return Vouchers" id="Return Vouchers" value="Return Vouchers" onclick="javascript:processForm( 'POSForm','FormProcessor/POS/ajax_return_vouchers.php','MsgDiv' );"/>
                <?php } ?>
                 <?php  if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'voucherlost')) { ?>
                <input class="button" type="button" name="Mark Vouchers Lost" id="Mark Vouchers Lost" value="Mark Vouchers Lost" onclick="javascript:processForm( 'POSForm','FormProcessor/POS/ajax_lost_vouchers.php','MsgDiv' );"/>
                |<?php } ?>
              </td>
            </tr>
            <tr>
              <td align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td bgcolor="#EFEFEF">Face Value
                <select class="selectbox" name="voucher_denomination" id="voucher_denomination" onchange="javascript:processForm( 'POSForm','Pages/POS/ajax_get_vouchers.php','POS' );">
                <option value="">Select Face Value</option>
                <?PHP if(!empty($vData)) { for($i=0; $i< sizeof($vData);$i++) { ?>
                <option value="<?=$vData[$i]['voucher_denomination']?>"><?=$vData[$i]['voucher_denomination']?></option>
				<? } } ?>
              </select>
              </td>
               <td align="center" bgcolor="#EFEFEF">&nbsp;</td>
            </tr>
            <tr><td align="center" bgcolor="#EFEFEF">&nbsp;</td>
                <td bgcolor="#EFEFEF">Serial Start#: 
                <input class="textboxBur" type="text" name="serial_search_start" id="voucher_serial_search" />
                <td bgcolor="#EFEFEF">Serial End#:
                <input class="textboxBur" type="text" name="serial_search_end" id="voucher_serial_search" />
                <input type="button" name="button" id="button" value="Go" onClick="javascript:processForm( 'POSForm','Pages/POS/ajax_get_vouchers.php','POS' );"/></td>
            </tr>

            <?php  if($GLOBALS['_GACL']->checkPermission($_SESSION['username'],'replacevoucher')) { ?>
            <tr>
                <td>&nbsp;</td>
            </tr>
            <tr>
              <td align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td colspan="3" align="right" bgcolor="#EFEFEF">Replacement Serial#:
              <input class="textboxBur" type="text" name="replace_voucher_serial" id="replace_voucher_serial" />
              <input class="button" type="button" name="Replace Voucher" id="Replace Voucher" value="Replace Voucher" onClick="javascript:processForm( 'POSForm','FormProcessor/POS/ajax_replace_voucher.php','MsgDiv' );"/></td>
            </tr>            
            <tr>
              <!--<td colspan="2" align="right" bgcolor="#EFEFEF">Forward to Finance:
              <input type="checkbox" name="forward_to_finance" id="forward_to_finance" value="FwdToFianance" /></td>
              -->
              <td colspan="3" align="right" bgcolor="#EFEFEF">Replacement Reason:
              <select name="replace_reason" id="replace_reason" class="selectbox" >
              <option value="">Select Reason</option>
              <option value="OverScratched">Over Scratched</option>
              <option value="Damaged">Damaged</option>
              <option value="Already Used">Already Used</option>
              </select>
              </td>
              </tr>                             
            </tr>
              <? } ?> 
           
          </table>
          <div id="POS"></div>
          <!-- end listing vouchers -->
		  </form></td>
  </tr>
	</table>

