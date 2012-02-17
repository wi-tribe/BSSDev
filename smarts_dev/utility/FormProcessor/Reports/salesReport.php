<?php
	session_start("VIMS");
	set_time_limit(200);

	//ob_start();
	//include_once("../../vims_config.php");
       include_once("../../util_config.php");
	$conf=new config();
	include_once(CLASSDIR."/Voucher.php");
	include_once(CLASSDIR."/User.php");
	include_once(CLASSDIR."/Export.php");
	include_once(CLASSDIR."/Channel.php");
        include_once(CLASSDIR."/Logging.php");


	//check permission
	$return = $GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'collectionreport');
	//print_r($_POST);

	if(!$return){ echo "no permission"; exit();}

	$vouch_obj = new Voucher();
	$user_obj = new User();
	$channel_obj = new Channel();
        $log_obj = new Logging();
	
	$date_start = $_POST['start_date'];
	$date_end = $_POST['end_date'];

if(strlen($_POST['start_date'])>10 ||strlen($_POST['end_date']) > 10)
    {
    print("<b> Please Correct: Date format </b>");
    exit();
    }
//   if($date_start > $date_end)
//       {
//            print("<b> Please Correct: Start date is greater than end date </b>");
//            exit();
//       }
    
	$filters = $_POST;
        if($filters['channel_type']=='ALL')
        {
            $cc_data = $vouch_obj->getVoucherCollectionReport($filters);
            $se_data = $vouch_obj->getRetailerVoucherCollectionReport($filters);
             if(!$cc_data && !$se_data)
			echo $vouch_obj->LastMsg;
        }
        else
        {
            if($filters['channel_type']!='1')
            {
                //CC data
                $cc_data = $vouch_obj->getVoucherCollectionReport($filters);
                 if(!$cc_data)
			echo $vouch_obj->LastMsg;
            }

            else
            {
                //Retailer SE wise data
                $se_data = $vouch_obj->getRetailerVoucherCollectionReport($filters);
                 if(!$se_data)
			echo $vouch_obj->LastMsg;
            }
        }

        
	if($_POST['export'] == 'Export Report')
	{
                //CC Report Data Columns
		$columns[] = array('index'=>'A','name'=>'action_date','label'=>'Transaction Date' );
		$columns[] = array('index'=>'B','name'=>'action_time','label'=>'Transaction Time' );
		$columns[] = array('index'=>'C','name'=>'action_user_id','label'=>'User ID' );
		$columns[] = array('index'=>'D','name'=>'username','label'=>'Username' );
		$columns[] = array('index'=>'E','name'=>'sale_rep_name','label'=>'Sales Rep' );
                $columns[] = array('index'=>'F','name'=>'parent_user_id','label'=>'Parent User ID' );
                $columns[] = array('index'=>'G','name'=>'parent_user','label'=>'Parent User' );
		$columns[] = array('index'=>'H','name'=>'channel','label'=>'Shop' );
		$columns[] = array('index'=>'I','name'=>'region','label'=>'Region' );
		$columns[] = array('index'=>'J','name'=>'face_value','label'=>'Face Value' );
		$columns[] = array('index'=>'K','name'=>'voucher_count','label'=>'Voucher Count' );
		$columns[] = array('index'=>'L','name'=>'grossvalue','label'=>'Gross Value' );

                //SE Order Wise Data Columns
                $columns2[] = array('index'=>'A','name'=>'order_id','label'=>'Order ID' );
		$columns2[] = array('index'=>'B','name'=>'order_date','label'=>'Order Date' );
		$columns2[] = array('index'=>'C','name'=>'order_by','label'=>'Order By' );
		$columns2[] = array('index'=>'D','name'=>'sales_rep_id','label'=>'Retailer Owner ID' );
		$columns2[] = array('index'=>'E','name'=>'sales_rep_name','label'=>'Retaielr Owner Name' );
                $columns2[] = array('index'=>'F','name'=>'parent_user_id','label'=>'Parent User ID' );
                $columns2[] = array('index'=>'G','name'=>'parent_user_name','label'=>'Parent User Name' );
		$columns2[] = array('index'=>'H','name'=>'voucher_denomination','label'=>'Face Value' );
		$columns2[] = array('index'=>'I','name'=>'sold','label'=>'Voucher Count' );
		$columns2[] = array('index'=>'J','name'=>'grossvalue','label'=>'Gross Value' );
                $columns2[] = array('index'=>'K','name'=>'channel_id','label'=>'Retailer Shop ID' );
                $columns2[] = array('index'=>'L','name'=>'channel_name','label'=>'Retailer Shop Name' );
                $columns2[] = array('index'=>'M','name'=>'channel_type_name','label'=>'Channel Type' );
                $columns2[] = array('index'=>'N','name'=>'region','label'=>'Region' );
                $columns2[] = array('index'=>'O','name'=>'commission_value','label'=>'Commission Value' );
                $columns2[] = array('index'=>'P','name'=>'commission_amount','label'=>'Commission Amount' );
                $columns2[] = array('index'=>'Q','name'=>'discount_amount','label'=>'Discount Amount' );
                $columns2[] = array('index'=>'R','name'=>'wht_on_commission','label'=>'WHT on Commission' );
                $columns2[] = array('index'=>'S','name'=>'wht_on_discount','label'=>'WHT on Discount' );
                $columns2[] = array('index'=>'T','name'=>'net_amount','label'=>'Net Payables' );

                $user_obj = new User();
		$fname = "collectionReport_".date("Y").date("m").date("d").".xls";
		$userdetail = $user_obj->getUserInfo($_SESSION['user_id']);
		$user = $userdetail[0]['first_name'].' '.$userdetail[0]['last_name'];
		$usr="";
                $cse_id = $_POST['user'];
		
		if($cse_id!="ALL")
		{
			//$usr = $user_obj->getBRMUserDetails($cse_id);
			$usr = $user_obj->getUserInfo($cse_id);
			$searheduser = $usr[0]['first_name'].' '.$usr[0]['last_name'];
		}  
		
		$DocInfo = array(
					 'CCTitle' => 'CC Collection Report',
                                         'ReTitle' => 'Retailer SE Collection Report',
					  'Heading'=>'Voucher Collection Report',
					 'Filename' => $fname,
					 'Start Date' => $date_start,
					 'End Date' =>  $date_end,
					 );
		$Searchcriteria = array( 'Region' => $_POST['region'],
								 'Channel Type' => $_POST['channel_type'],
								 'Created By' => $user,
								 'Searched User' => $searheduser,
								 'Channel' => $_POST['channel'],
								 'CSE/SE' => $_POST['user'],
			);
		
		$export_obj = new Export();
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." exported voucher collection report between dates ".$date_start." to ".$date_end);
		$export_obj->export($columns,$cc_data,$DocInfo,$Searchcriteria,$columns2,$se_data);
	}

?>
<div align="right">
<form name='exportReport' id='exportReport' method='post' action="FormProcessor/Reports/salesReport.php">
	 <input type="hidden" name="region" id="region" value="<?=$_POST['region']?>" />
	 <input type="hidden" name="channel_type" id="channel_type" value="<?=$_POST['channel_type']?>" />
	 <input type="hidden" name="channel" id="channel" value="<?=$_POST['channel']?>" />
	 <input type="hidden" name="user" id="user" value="<?=$_POST['user']?>" />
	 <input type="hidden" name="start_date" id="start_date" value="<?=$_POST['start_date']?>" />
	 <input type="hidden" name="end_date" id="end_date" value="<?=$_POST['end_date']?>" />
	 <input name="export" type="submit" id="export" value="Export Report">
</form>
</div>
<? if($cc_data!=null  && $se_data!=null)
    { ?>
<font color="red" size="4">Please click <font color="green" size="4">Export Report</font> to View CC && Retailer SE Collection Data</font>
 <? }?>
    
<? if($cc_data!=null  && $se_data==null)
{ ?>
        <table width="100%">
            <tr>
		<td class="textboxBur" align="center" ><strong>Sr.#</strong></td>
		<td class="textboxBur" align="center" ><strong>Transaction Date</strong></td>
		<td class="textboxBur" align="center" ><strong>Transaction Time</strong></td>
		<td class="textboxBur" align="center" ><strong>Action User ID</strong></td>
		<td class="textboxBur" align="center" ><strong>Username</strong></td>
		<td class="textboxBur" align="center" ><strong>Sales Rep</strong></td>
                <td class="textboxBur" align="center" ><strong>Parent User ID</strong></td>
                <td class="textboxBur" align="center" ><strong>Parent User</strong></td>
		<td class="textboxBur" align="center" ><strong>Shop</strong></td>
		<td class="textboxBur" align="center" ><strong>Region</strong></td>
		<td class="textboxBur" align="center" ><strong>Face Value</strong></td>
		<td class="textboxBur" align="center" ><strong>Voucher Count</strong></td>
		<td class="textboxBur" align="center" ><strong>Gross value</strong></td>
	</tr>
    <?
	$index=0;
        foreach ($cc_data as $arr)
	{
		$index++;
	?>
		<tr>
			<td class="textboxGray"><?=$index?></td>
			<td class="textboxGray"><?=$arr['action_date']?></td>
			<td class="textboxGray"><?=$arr['action_time']?></td>
			<td class="textboxGray"><?=$arr['action_user_id']?></td>
			<td class="textboxGray"><?=$arr['username']?></td>
			<td class="textboxGray"><?=$arr['sale_rep_name']?></td>
                        <td class="textboxGray"><?=$arr['parent_user_id']?></td>
                        <td class="textboxGray"><?=$arr['parent_user']?></td>
			<td class="textboxGray"><?=$arr['channel']?></td>
			<td class="textboxGray"><?=($arr['region'])?></td>
			<td class="textboxGray"><?=$arr['face_value']?></td>
			<td class="textboxGray"><?=$arr['voucher_count']?></td>
			<td class="textboxGray"><?=$arr['grossvalue']?></td>
		</tr>
<?	} ?>
</table>
<? }
 if($cc_data==null && $se_data!=null) { ?>
<table width="100%">
	<tr>
		<td class="textboxBur" align="center" ><strong>Sr.#</strong></td>
		<td class="textboxBur" align="center" ><strong>Order ID</strong></td>
		<td class="textboxBur" align="center" ><strong>Order Date</strong></td>
		<td class="textboxBur" align="center" ><strong>Sales Rep Name</strong></td>
                <td class="textboxBur" align="center" ><strong>Parent User Name</strong></td>
		<td class="textboxBur" align="center" ><strong>Face Value</strong></td>
		<td class="textboxBur" align="center" ><strong>Voucher Count</strong></td>
                <td class="textboxBur" align="center" ><strong>Gross value</strong></td>
		<td class="textboxBur" align="center" ><strong>Channel Name</strong></td>
		<td class="textboxBur" align="center" ><strong>Channel Type</strong></td>
	</tr>
    <?
	$index=0;
	foreach ($se_data as $arr)
	{
		$index++;
	?>
		<tr>
			<td class="textboxGray"><?=$index?></td>
			<td class="textboxGray"><?=$arr['order_id']?></td>
			<td class="textboxGray"><?=$arr['order_date']?></td>
			<td class="textboxGray"><?=$arr['sales_rep_name']?></td>
                        <td class="textboxGray"><?=$arr['parent_user_name']?></td>
			<td class="textboxGray"><?=$arr['voucher_denomination']?></td>
			<td class="textboxGray"><?=($arr['sold'])?></td>
			<td class="textboxGray"><?=$arr['voucher_denomination']*$arr['sold']?></td>
			<td class="textboxGray"><?=$arr['channel_name']?></td>
			<td class="textboxGray"><?=$arr['channel_type_name']?></td>
		</tr>
<?	} ?>
</table>
<? } ?>





