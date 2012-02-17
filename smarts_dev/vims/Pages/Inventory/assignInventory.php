	<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->

                                <form name="AssignInventory" method="post" action="">
                                  <table width="277" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                      <td><label for="From_channel_Name">Channel Name:</label></td>
                                      <td><select name="From_channel_Name" id="From_channel_Name">
                                        <option value="1">Wi-tribe F-8 </option>
                                        <option value="2">Wi-tribe F-10</option>
                                        <option value="3" selected="selected">Wi-tribe Blue Area</option>
                                      </select></td>
                                    </tr>
                                    <tr>
                                      <td width="134"><label>From Channel ID :</label>&nbsp;</td>
                                      <td width="144"><label>
                                        <input type="text" name="from_channel_id" id="from_channel_id">
                                      </label></td>
                                    </tr>
                                    <tr>
                                      <td><label>To Channel ID :</label>&nbsp;</td>
                                      <td><label>
                                        <input type="text" name="to_channel_id" id="to_channel_id">
                                      </label></td>
                                    </tr>
                                    <tr>
                                      <td><label>Action By :</label>&nbsp;</td>
                                      <td><label>
                                        <input type="text" name="action_by" id="action_by">
                                      </label></td>
                                    </tr>
                                    <tr>
                                      <td>Voucher ID :</td>
                                      <td><label>
                                        <input type="text" name="voucher_id" id="voucher_id">
                                      </label></td>
                                    </tr>
                                    <tr>
                                      <td>Movement Type ID :</td>
                                      <td><input type="text" name="movement_type_id" id="movement_type_id" /></td>
                                    </tr>
                                  </table>
                                
                                <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'><input name="FormAction" type='button' value='Assign' onclick="javascript:processForm( 'VoucherAdd','FormProcessor/placeOrder.php','MsgDiv' );"></td>
				</tr>
				</table>
                                </form>
<!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>
