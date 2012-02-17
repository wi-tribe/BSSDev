<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../vims_config.php");
$conf=new config();


	 $user_id = $_GET['sup_id'];
	 $channel_id = $_GET['from_channel'];
	 $to_channel_id = $_GET['to_channel'];
	
	$query="select voucher_serial, it.voucher_id, voucher_denomination 
				from ".VIMS_REFIX."tracker t inner join  
				".VIMS_REFIX."item_information it 
				on t.voucher_id=it.voucher_id 
				and end_date is null 
				and to_channel_id='$channel_id' and assigned_to='$user_id' 
				and transfer_status=1 
				order by voucher_serial ";
	
	$check = false;
	if(($vdata = $GLOBALS['_DB']->CustomQuery($query))!=null)
	 {
		foreach($vdata as $arr)
   			$vouchSerials[]=$arr['voucher_serial'];
		
		
		$vouch_obj		= new Voucher();
	
		$voucher_ids = $vouch_obj->validateSerialOwner($user_id,$channel_id, $vouchSerials);
		//$tracker_obj	= new Tracker();
		print_r($voucher_ids);
	 }

?>
