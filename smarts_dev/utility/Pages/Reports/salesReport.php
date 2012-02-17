<?PHP
	session_start("VIMS");
	set_time_limit(200);

	//ob_start();
	//include_once("../../../vims/vims_config.php");
        include_once("../../util_config.php");
	$conf=new config();
	include_once(CLASSDIR."/POS.php");

	//check permission
	$return = $GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'collectionreport');
	if(!$return) {echo "Permission Denied"; exit();}

	$vouch_obj = new Voucher();
	$ch_obj = new Channel();

	if($return=='NWD')
	{
		$channeltypes = $ch_obj->getAllChannelTypes();
		$regions = $ch_obj->getRegionsLive();
		
	}else{
		$user_region = $vouch_obj->getUserRegionLive($_SESSION['user_id']);
		$region_id = $user_region[0]['location_name'];
	}

	if($return=='Region')
	{
		$channeltypes = $ch_obj->getAllChannelTypes();
	}else{	
		$user_channel = $ch_obj->getChannelDetails($_SESSION['channel_id']);
		$channel_type_id 	= $user_channel[0]['channel_type_id'];
		$channel_type_name	= $user_channel[0]['channel_type_name'];
		$channel_id 		= $user_channel[0]['channel_id'];
		$channel_name		= $user_channel[0]['channel_name'];
	}	

	if($return=='Shop')
	{
		$users = $ch_obj->getUserByChannel($channel_id,$_SESSION['user_team_id'],$channel_type_id);
	}else{	
		$user = $_USER->getUserInfo($_SESSION['user_id']);
		$user = $user[0];
	}
	
	//$_SESSION['reporttype']='receipt';
        
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

            <form name="collectionReport" id="collectionReport" method="post" action="FormProcessor/Reports/salesReport.php">
                 <input type="hidden" name ="permission" id="permission" value="<?=$return?>">
                 
                    <table width="100%">
					<tr>
						<td colspan="2" align="center" style="font-size:16px"><strong>Voucher Collection Report </strong></td>
					</tr>
					<tr>
						<td>
							<table width="100%">
								<tr>
									<td>Region</td>
									<td>
										<?	if($return == 'NWD')
										{	?>
											<select name='region' id='region' class="selectbox" onchange="javascript:processForm( 'collectionReport','FormProcessor/Reports/getRegionalChannels.php','channeldiv' );">										
												<option value="ALL"> -- ALL -- </option>
												<?	foreach($regions as $arr)  
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
									<td>Channel Type</td>
									<td>
										<?	if($return == 'Region' || $return == 'NWD')
										{	?>
											<select name='channel_type' id='channel_type' class="selectbox" onchange=" javascript:processForm( 'collectionReport','FormProcessor/Reports/getRegionalChannels.php','channeldiv' )">
												<option value="ALL"> -- ALL -- </option>
												<? foreach($channeltypes as $arr) 
													{ if($arr['channel_type_name']=='WareHouse') {?>
														<option value="<?=$arr['channel_type_id']?>"><?=$arr['channel_type_name']?> [Retailer]</option>
												<?	} if($arr['channel_type_name']=='Shop') {?>
                                                                                                                <option value="<?=$arr['channel_type_id']?>"><?=$arr['channel_type_name']?> </option>
                                                                                                                <? } ?>
                                                                                                  <?}	?>
												</select>
									<?	} else
										{	?>
											<?=$channel_type_name?>
											<input type="hidden" name ="channel_type" id="channel_type" value="<?=$channel_type_id?>">
									<?	}	?>
									</td>
								</tr>
								<tr> 
									<td>Channel/Shop</td>
									<td>
										<div name="channeldiv" id="channeldiv">
										<?	if($return == 'Region' || $return == 'NWD')
											{	?> 
												<select name='channel' id='channel' class="selectbox" onchange="javascript:processForm( 'collectionReport','FormProcessor/Reports/getChannelUsers.php','userdiv' );">
													<option value="ALL"> -- ALL -- </option>
												<?	foreach($channels as $arr) 
													{ ?>
														<option value="<?=$arr['channel_id']?>"><?=$arr['channel_id']?>--<?=$arr['channel_name']?></option>
												<?	} ?>
												</select>
										<?	} else
											{	?>
												<?=$channel_name?>
												<input type="hidden" name ="channel" id="channel" value="<?=$channel_id?>">
										<?	}	?>
										</div>
									</td>
								</tr>
								<tr>
									<td>User</td>
									<td>
										<div name="userdiv" id="userdiv">
										<?	if($return == 'Shop' || $return == 'Region' or $return == 'NWD')
										{	?>
											<select name='user' id='user' class="selectbox" >
												<option value="ALL"> -- ALL -- </option>
												<?	foreach($users as $arr) 
													{ ?>
														 <option value="<?=$arr['user_id']?>"><?=$arr['first_name'].' '.$arr['last_name']?> 
												<?	} ?>
											</select>
										<?	} else
											{	?>
												<?=$user['first_name'].' '.$user['last_name']?>
												<input type="hidden" name ="user" id="user" value="<?=$user['user_id']?>">
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
                                                    <input name="FormAction" type='button' id="display" value='Display Report' onclick="javascript:processForm( 'collectionReport','FormProcessor/Reports/salesReport.php','Report' );">
						</td>
                    </tr>
					
				 </table>
             </form>           
          <div id="Report"></div>
          <!-- end listing vouchers -->
		   
<script>
//processForm( 'collection_date','FormProcessor/Reports/salesReport.php','Report' );

</script>