<?PHP
	session_start("VIMS");
	set_time_limit(200);

	//ob_start();
	include_once("../../vims_config.php");
	$conf=new config();
	include_once(CLASSDIR."/POS.php");

	$return = $GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'receiptreport');
	if(!$return){ echo "Permission denied";	exit;}
	//print $return;
	
	$vouch_obj = new Voucher();
	$ch_obj = new Channel();
		$payment_methods = $vouch_obj->getPaymentModes();
		$receipt_modes = $vouch_obj->getReceiptModes();
		$receipt_types = $vouch_obj->getReceiptTypes();

	$region_id="";
 
	if($return=='NWD')
	{
		$Regions = $vouch_obj->getRegions();
                $channeltypes = $vouch_obj->getBRMChannelTypes();
	}else{
		$user_region = $vouch_obj->getUserRegionLive($_SESSION['user_id']);
		$region_id = $user_region[0]['location_name'];
		$_SESSION['region'] = $user_region[0]['location_name'];
	}

	if($return=='Region')
	{
		$channels = $vouch_obj->getShopByRegion($region_id);
                $channeltypes = $vouch_obj->getBRMChannelTypes();
	}else{	
		$user_shop = $vouch_obj->getUserShop($_SESSION['user_id']);
		$shop_id = $user_shop[0]['shop_id'];
                
	}
 	if($return=='Shop')
	{
                $user = $_USER->getBRMUserDetails($_SESSION['brm_csr_id']);
		$user = $user[0];
                $channeltypes = $vouch_obj->getBRMChannelTypes();
                $users = $vouch_obj->getUserByShop($shop_id,$user['addr_city']);
	}else{
		$user = $_USER->getBRMUserDetails($_SESSION['brm_csr_id']);
		$user = $user[0];
                if($user['channel_type']!=null)
                {
                    $channel_type_name = $vouch_obj->getBRMChannelTypeInfo($user['channel_type']);
                    $channel_type_name = $channel_type_name[0]['channel_name'];
                }
	}

	$_SESSION['reporttype']='receipt';
 

?>

<script>
	$(function()
	{   
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var date1 = month + '/' + '01' + '/' + myDate.getFullYear();
		$("#start_date").val(date1);
	
		var myDate = new Date();
		var month = myDate.getMonth() + 1;
		var date2 = month + '/' + myDate.getDate() + '/' + myDate.getFullYear();
		$("#end_date").val(date2);

		$('#start_date').datepicker(
			{
				changeMonth: true,
				changeYear: true
			}
		);
		$('#end_date').datepicker(
			{
				changeMonth: true,
				changeYear: true
			}
		);
	});
</script>
<script type="text/JavaScript">
  function showhidefield(id1)
  {
      document.getElementById(id1).style.visibility = "visible";
     // document.getElementById(id2).style.visibility = "visible";
  }
  function unselect()
    {
        document.getElementById('channel_type').selectedIndex = 0;
        document.getElementById('user').selectedIndex = 0;
    }
function DisplayNote()
 {
     if(document.getElementById('CachedView').checked==true)
    {
        document.getElementById('note').innerHTML = "Note: Cache Data last updated 15 minutes before.Option is given for quick response/report generation";
    }

    if(document.getElementById('CachedView').checked==false)
    {
        document.getElementById('note').innerHTML = '';
    }
 }
</script>
            <form name="receipt_report" id="receipt_report" method="post" action="FormProcessor/Reports/receiptReport.php">
                 <input type="hidden" name ="permission" id="permission" value="<?=$return?>">
				 <input type="hidden" name ="formName" id="formName" value="receipt_report">
				 <table width="100%">
					<tr>
						<td colspan="2" align="center" style="font-size:16px"><strong>Receipts Detail Report </strong></td>
					</tr>
					<tr>
						<td>
							<table width="100%">
							<tr>
								<td>Payment Type</td>
								 <td>
									 <select name='payment_method' id='payment_method' class="selectbox" >
										<option value="ALL"> -- ALL -- </option>
									<?	foreach($payment_methods as $arr) 
										{ ?>
											<option value="<?=$arr['payment_method']?>"><?=$arr['payment_method']?> </option>
									<?	} ?>
									</select>
								</td>
							</tr>
							<tr>
								<td>Receipt Type</td>
								 <td>
									 <select name='receipt_type' id='receipt_type' class="selectbox" >
										<option value="ALL"> -- ALL -- </option>
									<?	foreach($receipt_types as $arr) 
										{ ?>
											<option value="<?=$arr['receipt_type']?>"><?=$arr['receipt_type']?> </option>
									<?	} ?>
									</select>
								</td>
							</tr>
							<tr>
								<td>Receipt Mode</td>
								<td>
									<select name='receipt_mode' id='receipt_mode' class="selectbox" >
                                                                        <option value="ALL"> -- ALL -- </option>
                                                            <?  foreach($receipt_modes as $arr)
                                                                       { ?>
                                                                         <option value="<?=$arr['receipt_mode']?>"><?=$arr['receipt_mode']?> </option>
                                                                <?	} ?>
									</select>
								</td>
							</tr>
							</table>
						</td>
						<td>
							<table width="100%">
								<tr>
									<td>Region</td>
									<td>
										<?	if($return == 'NWD')
										{	?>
											<select name='region' id='region' class="selectbox" onchange="javascript:processForm( 'receipt_report','FormProcessor/Reports/getRegionalShops.php','channeldiv' ); unselect(); ">
												<option value="ALL"> -- ALL -- </option>
												<?	foreach($Regions as $arr) 
												{ ?>
													<option value="<?=$arr['region_id']?>"><?=$arr['region_id']?> </option>
											<?	} ?>
											</select>
									<?	} else
										{	?>
											<?=$region_id?>
											<input type="hidden" name ="region" id="region" value="<?=$region_id?>">
									<?	}	?>
									</td>
								</tr>
								<tr>
									<td>Channel/Shop</td>
									<td>
										<div name="channeldiv" id="channeldiv">
										<?	if($return == 'Region' || $return == 'NWD')  
											{	?>
												<select name='channel' id='channel' class="selectbox" onchange="javascript:processForm( 'receipt_report','FormProcessor/Reports/getShopUsers.php','userdiv' ); unselect();">
													<option value="ALL"> -- ALL -- </option>
												<?	foreach($channels as $arr) 
													{ ?>
														<option value="<?=$arr['shop_id']?>"><?=$arr['shop_id']?>--<?=$arr['shop_name']?></option>
												<?	} ?>
												</select>
										<?	} else
											{	?>
												<?=$shop_id?>
												<input type="hidden" name ="channel" id="channel" value="<?=$shop_id?>">
										<?	}	?>
										</div>
									</td>
								</tr>
                                                                 <tr>
									<td>Channel Type</td>
									<td>
										<?	if($return == 'Region' || $return == 'NWD' || $return == 'Shop')
										{	?>
											<select name='channel_type' id='channel_type' class="selectbox" onchange=" javascript:processForm( 'receipt_report','FormProcessor/Reports/getUsersByChannelType.php','userdiv' )">
												<option value="ALL"> -- ALL -- </option>
												<? foreach($channeltypes as $arr)
													{ ?>
														<option value="<?=$arr['channel_id']?>"><?=$arr['channel_name']?> </option>
												<?	}	?>
												</select>
									<?	} else
										{	?>
											<?=$channel_type_name?>
											<input type="hidden" name ="channel_type" id="channel_type" value="<?=$user['channel_type']?>">
									<?	}	?>
									</td>
								</tr>
								<tr>
									<td>User</td>
									<td>
										<div name="userdiv" id="userdiv">
										<?	if($return == 'Shop' or $return == 'Region' or $return == 'NWD')
										{	?>
											<select name='user' id='user' class="selectbox" >
												<option value="ALL"> -- ALL -- </option>
												<?	foreach($users as $arr) 
													{ ?>
														 <option value="<?=$arr['salespersonnel_id']?>"><?=$arr['full_name']?> 
												<?	} ?>
											</select>
										<?	} else
											{	?>
												<?=$user['first_name'].' '.$user['last_name']?>
												<input type="hidden" name ="user" id="user" value="<?=$user['salespersonnel_id']?>">
										<?	}	?>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td> Start Date <input name="start_date" id="start_date" class="date-pick dp-applied"></td>
						<td> End Date <input name="end_date" id="end_date" class="date-pick dp-applied">
						<input name="FormAction" type='submit' id="display" value='Process' onclick="submit()">
                                                &nbsp;
                                                Cached Data
                                                <input type="Checkbox" id="CachedView" name="CachedView" value="Cached" onchange="DisplayNote();" checked="checked" />
                                                </td>
                    </tr>
                                <tr>
                                    <td colspan="7">
                                        <font color="red"><strong><div id="note">Note: Cache Data last updated 15 minutes before.Option is given for quick response/report generation</div></strong></font>
                                    </td>
                                </tr>
					
				 </table>
                 
		   </form>
		   

