<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/BRMIntegration.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'blockvoucher'))
{
	echo "Permission denied";
	exit;
}
?>
	<table align="center" width="90%" border=0>
		<tr valign='top' class='textbox' >
			<td>
			  <table width="100%" border="0" cellspacing="1" cellpadding="1">
				  <tr>
				    <td colspan="2" align="center" class="higlight">Block Voucher</td>
			    </tr>
		    </table></td>
		</tr>
		<tr valign='top' class='textbox' >
		  <td align="center">
              <form name='BlockForm' id='BlockForm' method='post' action='' onsubmit="javascript: return false;">
          <!-- List vouchers -->
          <table width="70%" border="0" cellspacing="1" cellpadding="2">
            <tr>
              <td align="center" bgcolor="#EFEFEF">&nbsp;</td>
              <td colspan="3" align="right" bgcolor="#EFEFEF">Voucher Serial#:
              <input class="textboxBur" type="text" name="voucher_serial" id="voucher_serial" />
              <input class="button" type="button" name="Block Voucher" id="Block Voucher" value="Block Voucher" onClick="javascript:processForm( 'BlockForm','FormProcessor/POS/ajax_block_voucher.php','MsgDiv' );"/></td>
            </tr>
          </table>
          <div id="POS"></div>
          <!-- end listing vouchers -->
		  </form></td>
  </tr>
	</table>