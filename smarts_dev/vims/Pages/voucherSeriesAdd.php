<?	
	include_once('header.php');
?>
	<table align='center' width='100%' >
		<tr valign='top' class='textbox' >
			<td>
              <!-----------------------------THIS PAGE------------------------------------->     
				<form name='voucher_add_series' method='post' action='ajax_voucher_add_series.php'>
				  <table width='883' border='0' cellspacing='0' cellpadding='0'>
					<tr>
					  <td>Voucher Denomination</td>
					  <td><input type='text' name='vouch_denomination' id='vouch_denomination' />
					  </td>
					</tr>
					<tr>
					  <td>Voucher Serial Number Start</td>
					  <td><input type='text' name='vouch_serial' id='vouch_serial' /></td>
					  <td>Voucher Serial Number End</td>
					  <td><input type='text' name='voucher_serial_number_end' id='voucher_serial_number_end' /></td>
					</tr>
					<tr>
					  <td colspan=4 align='right'><input type='submit' value='Add'></td>
					</tr>
				  </table>
				</form
				
			  <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>
<? include_once('footer.php');?>






