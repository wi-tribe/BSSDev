	<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td>
              <!-----------------------------THIS PAGE------------------------------------->     
				<form name="VoucherAdd" method="post" action="">
				  <table  align="left" border="0" cellspacing="0" cellpadding="0">
					<tr>
					  <td width="200" >Voucher ID</td>
					  <td ><input type="text" name="vouch_id" id="vouch_id"></td>
					</tr>
					<tr>
					  <td>Voucher Serial Number</td>
					  <td><input type="text" name="vouch_serial" id="vouch_serial"></td>
					</tr>
					<tr>
					  <td>Date Added</td>
					  <td><input type="text" name="date_added" id="date_added"></td>
					</tr>
					<tr>
					  <td>Voucher Denomination</td>
					  <td><input type="text" name="vouch_denomination" id="vouch_denomination"></td>
					</tr>
                    <tr>
					  <td>&nbsp;</td>
					  <td><input name="FormAction" type='button' value='Add' onclick="javascript:processForm( 'VoucherAdd','FormProcessor/Voucher/VoucherAdd.php','MsgDiv' );"></td>
					</tr>
				  </table>
				</form>
			  <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>







