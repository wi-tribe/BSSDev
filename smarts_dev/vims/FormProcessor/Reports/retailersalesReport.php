<?php
	session_start("VIMS");
	set_time_limit(200);

	//ob_start();
	include_once("../../vims_config.php");
	$conf=new config();
	include_once(CLASSDIR."/Voucher.php");
	include_once(CLASSDIR."/User.php");
	include_once(CLASSDIR."/Export.php");
	include_once(CLASSDIR."/Channel.php");


	//check permission
	$return = $GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'retailercollectionreport');
	//print_r($_POST);
	$vouch_obj = new Voucher();
	$user_obj = new User();
	$channel_obj = new Channel();

	if(!$return){ echo "no permission"; exit();}

	$vouch_obj = new Voucher();
	$user = new User();
	$channel_obj = new Channel();

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
	$data = $vouch_obj->getRetailerVoucherCollectionReport($filters);
	if($_POST['export'] == 'Export Report')
	{
		if(!$data)
			echo $vouch_obj->LastMsg;
		$columns[] = array('index'=>'A','name'=>'order_id','label'=>'Order ID' );
		$columns[] = array('index'=>'B','name'=>'order_date','label'=>'Order Date' );
		$columns[] = array('index'=>'C','name'=>'order_by','label'=>'Order By' );
		$columns[] = array('index'=>'D','name'=>'sales_rep_id','label'=>'Sales Rep ID' );
		$columns[] = array('index'=>'E','name'=>'sales_rep_name','label'=>'Sales Rep Name' );
                $columns[] = array('index'=>'F','name'=>'parent_user_id','label'=>'Parent User ID' );
                $columns[] = array('index'=>'G','name'=>'parent_user_name','label'=>'Parent User Name' );
		$columns[] = array('index'=>'H','name'=>'voucher_denomination','label'=>'Face Value' );
		$columns[] = array('index'=>'I','name'=>'sold','label'=>'Voucher Count' );
		$columns[] = array('index'=>'J','name'=>'grossvalue','label'=>'Gross Value' );
                $columns[] = array('index'=>'K','name'=>'channel_name','label'=>'Channel Name' );
                $columns[] = array('index'=>'L','name'=>'channel_type_name','label'=>'Channel Type Name' );
                $columns[] = array('index'=>'M','name'=>'invoice_id','label'=>'Invoice ID' );
                $columns[] = array('index'=>'N','name'=>'commission_id','label'=>'Commission ID' );
                $columns[] = array('index'=>'O','name'=>'commission_value','label'=>'Commission Value' );
                $columns[] = array('index'=>'P','name'=>'commission_amount','label'=>'Commission Amount' );
                $columns[] = array('index'=>'Q','name'=>'discount_amount','label'=>'Discount Amount' );
                $columns[] = array('index'=>'R','name'=>'wht_on_commission','label'=>'WHT on Commission' );
                $columns[] = array('index'=>'S','name'=>'wht_on_discount','label'=>'WHT on Discount' );
                $columns[] = array('index'=>'T','name'=>'net_payable','label'=>'Net Payables' );
                


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
					 'Title' => 'Collection Report',
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
		$export_obj->export($columns,$data,$DocInfo,$Searchcriteria);

	}

?>
<div align="right">
<form name='exportReport' id='exportReport' method='post' action="FormProcessor/Reports/retailersalesReport.php">
	 <input type="hidden" name="region" id="region" value="<?=$_POST['region']?>" />
	 <input type="hidden" name="channel_type" id="channel_type" value="<?=$_POST['channel_type']?>" />
	 <input type="hidden" name="channel" id="channel" value="<?=$_POST['channel']?>" />
	 <input type="hidden" name="user" id="user" value="<?=$_POST['user']?>" />
	 <input type="hidden" name="start_date" id="start_date" value="<?=$_POST['start_date']?>" />
	 <input type="hidden" name="end_date" id="end_date" value="<?=$_POST['end_date']?>" />
	 <input name="export" type="submit" id="export" value="Export Report">
</form>
</div>



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
	foreach ($data as $arr)
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





