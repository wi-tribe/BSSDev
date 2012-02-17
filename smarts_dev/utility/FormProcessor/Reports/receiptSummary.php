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

$return = $GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'receiptsummaryreport');
if(!$return){ echo "Permission denied";	exit;}

$vouch_obj = new Voucher();
$user_obj = new User();
$channel_obj = new Channel();
$log_obj = new Logging();

$date_start = $_POST['start_date'];
$date_end = $_POST['end_date'];

$region_id 		= $_POST['region'];
$shop_id 		= $_POST['channel'];
$cse_id 		= $_POST['user'];

$filters = $_POST;

$msg='';
	if(strlen($_POST['start_date'])>10 ||strlen($_POST['end_date']) > 10)
	{
		//print("<b> Please Correct: Date format </b>");
		//exit();
	}
	
	//if($date_start > $date_end)
	//{
	//	print("<b> Please Correct: Start date is greater than end date </b>");
	//	exit();
	//}
	
	
	if($return == 'basic')
	{
		unset($filters['region']);
		unset($filters['channel']);	
	}
        
        if($_POST['Export']=='Export Report')
       {
            $data = $vouch_obj->getReceiptSummaryReport($filters);
            if($data==null)
            {
                $msg='No Data Found!!!';
            }
            else
            {
                $columns[] = array('index'=>'A','name'=>'cse_name','label'=>'CSE/SE Name' );
		$columns[] = array('index'=>'B','name'=>'cse_id','label'=>'CSE/SE ID' );
		$columns[] = array('index'=>'C','name'=>'cash','label'=>'Cash' );
		$columns[] = array('index'=>'D','name'=>'cheque','label'=>'Cheque' );
		$columns[] = array('index'=>'E','name'=>'creditcard','label'=>'Credit Card' );
                $columns[] = array('index'=>'F','name'=>'vouchercollection','label'=>'Voucher Collection' );
                $columns[] = array('index'=>'G','name'=>'cashrefund','label'=>'Cash Refund' );
		$columns[] = array('index'=>'H','name'=>'grossamnt','label'=>'Gross Receipt Amount' );
		
		$fname = "receiptSummaryReport_".date("Y").date("m").date("d").".xls";
                $userdetail = $user_obj->getUserInfo($_SESSION['user_id']);
		$user = $userdetail[0]['first_name'].''.$userdetail[0]['last_name'];
		$DocInfo = array(
						 'CCTitle' => 'Receipt Summary Report',
						  'Heading'=>'Receipt Summary Report',
						 'Filename' => $fname,
						 'Start Date' => $date_start,
						 'End Date' => $date_end,
						 );
		$searheduser = $cse_id;
                if($_POST['channel_type']!='ALL' and $_POST['channel_type']!=null)
                {
                    $channel_type = $vouch_obj->getBRMChannelTypeInfo($_POST['channel_type']);
                    //print_r($channel_type);
                    $channel_type = $channel_type[0]['channel_name'];
                }
		if($cse_id!="ALL") 
		{
			$usr = $user_obj->getBRMUserDetails($cse_id);
			$searheduser = $usr[0]['full_name'];
		}  
		$Searchcriteria = array( 'Channel' => $shop_id, 
								 'Region' => $region_id,
								 'Created By' => $user,
								 'Searched User' => $searheduser,
                                                                  'Channel Type' => $channel_type
			);

		$export_obj = new Export();
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." exported receipt summary report between dates ".$date_start." to ".$date_end);
		$export_obj->export($columns,$data,$DocInfo,$Searchcriteria,'','');
                exit();
            }
         }
	else
       {
            $data = $vouch_obj->getReceiptSummaryReport($filters);
            if($data==null)
            {
                $msg='No Data Found!!!';
            }
       }
	
	
?>
<?if($data!=null)
{ if($_POST['Export']!='Export Report') {?> 
<table width="100%">
	<tr>
		<td class="textboxBur" align="center" ><strong>CSE/SE Name</strong></td>
		<td class="textboxBur" align="center" ><strong>CSE/SE ID</strong></td>
		<td class="textboxBur" align="center" ><strong>Cash (PKR)</strong></td>
		<td class="textboxBur" align="center" ><strong>Cheque (PKR)</strong></td>
                <td class="textboxBur" align="center" ><strong>Credit Card (PKR)</strong></td>
                <td class="textboxBur" align="center" ><strong>Voucher Collection (PKR)</strong></td>
                <td class="textboxBur" align="center" ><strong>Cash Refund (PKR)</strong></td>
		<td class="textboxBur" align="center" ><strong>Gross Receipt Amount (PKR)</strong></td>
    </tr>
    <?
	foreach ($data as $arr)
	{ ?>
		<tr>
			<td class="textboxGray"><?=$arr['cse_name']?></td>
			<td class="textboxGray"><?=$arr['cse_id']?></td>
			<td class="textboxGray"><?=$arr['cash']?></td>
			<td class="textboxGray"><?=$arr['cheque']?></td>
			<td class="textboxGray"><?=$arr['creditcard']?></td>
                        <td class="textboxGray"><?=$arr['vouchercollection']?></td>
                        <td class="textboxGray"><?=$arr['cashrefund']?></td>
			<td class="textboxGray"><?=($arr['grossamnt'])?></td>
		</tr>
<?	} ?>
</table>
<? } }?>

<?=$msg?>



