<?php
session_start("VIMS");
set_time_limit(500);

//ob_start();
//include_once("../../vims_config.php");
include_once("../util_config.php");
$conf=new config();
include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Export.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Logging.php");

$sales_obj = new SalesPersonnel();
$user_obj = new User();
$channel_obj = new Channel();
$log_obj = new Logging();

$date_start = $_POST['start_date'];
$date_end = $_POST['end_date'];

$region_id 		= $_POST['region'];
$shop_id 		= $_POST['channel'];

	$filters = $_POST;
	if($return == 'basic')
	{
		unset($filters['region']);
		unset($filters['channel']);
	}

        $data = $sales_obj->get_SalesReturnReq_Customers($date_start, $date_end,$_POST);
        
        if(!$data)
		{
			echo "Data not found for this search criteria";
                        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Sales Return Req Customers: NO records found against search between dates ".$date_start." to ".$date_end);
                        exit();
		}

		$fname = "Customers_SalesReturnRequest_".date("Y").date("m").date("d").".csv";

		$userdetail = $user_obj->getUserInfo($_SESSION['user_id']);
		$user = $userdetail[0]['first_name'].' '.$userdetail[0]['last_name'];
                
if($_POST['export'] == 'Export Report')
    {
		$columns[] = array('index'=>'A','name'=>'DUN_ID','label'=>'DUNNING ID' );
		$columns[] = array('index'=>'B','name'=>'CUSTOMER_ID','label'=>'CUSTOMER ID' );
		$columns[] = array('index'=>'C','name'=>'SALES_REP_ID','label'=>'SALES PERSONNEL ID' );
		$columns[] = array('index'=>'D','name'=>'ACTION_DATE','label'=>'ACTION DATE' );
                $columns[] = array('index'=>'E','name'=>'REASON_CODE','label'=>'REASON CODE' );
		$columns[] = array('index'=>'F','name'=>'REGION','label'=>'REGION' );
                $columns[] = array('index'=>'G','name'=>'SHOP_ID','label'=>'SHOP ID' );

		$fname = "CustomerSalesReturn_Req_".date("Y").date("m").date("d").".xls";
                $userdetail = $user_obj->getUserInfo($_SESSION['user_id']);
		$user = $userdetail[0]['first_name'].''.$userdetail[0]['last_name'];
		$region_id = $_POST['region'];
                $shop_id = $_POST['channel'];
		$DocInfo = array(
						 'CCTitle' => 'Customers Sales Return Requests',
						  'Heading'=>'Customers Sales Return Requests',
                                                  'username'=>'',
						 'Filename' => $fname,
						 'Start Date' => $date_start,
						 'End Date' => $date_end,
						 );

		$Searchcriteria = array( 'Channel' => $shop_id,
								 'Region' => $region_id,
								 'Created By' => $user,
			);

		$export_obj = new Export();
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Exported Sales Return Req Customers report between dates ".$date_start." to ".$date_end);
		$export_obj->export($columns,$data,$DocInfo,$Searchcriteria);
	}

?>
<? if($data!=null) { ?>
<div align="right">
<form name='exportReport' id='exportReport' method='post' action="./FormProcessor/SalesReturnReq_Dunning.php">
            <input type="hidden" name="region" id="region" value="<?=$_POST['region']?>" />
            <input type="hidden" name="channel" id="channel" value="<?=$_POST['channel']?>" />
            <input type="hidden" name="start_date" id="start_date" value="<?=$_POST['start_date']?>" />
            <input type="hidden" name="end_date" id="end_date" value="<?=$_POST['end_date']?>" />
            <input name="export" type="submit" id="export" value="Export Report">

</form>
</div>

<table width="100%">
	<tr>
		<td class="textboxBur" align="center" ><strong>CUSTOMER ID</strong></td>
		<td class="textboxBur" align="center" ><strong>SALES REP ID</strong></td>
		<td class="textboxBur" align="center" ><strong>ACTION DATE</strong></td>
                <td class="textboxBur" align="center" ><strong>REASON CODE</strong></td>
                <td class="textboxBur" align="center" ><strong>REGION</strong></td>
		<td class="textboxBur" align="center" ><strong>SHOP ID</strong></td>
    </tr>
    <?
	foreach ($data as $arr)
	{ ?>
		<tr>
			<td class="textboxGray"><?=$arr['CUSTOMER_ID']?></td>
			<td class="textboxGray"><?=$arr['SALES_REP_ID']?></td>
			<td class="textboxGray"><?=$arr['ACTION_DATE']?></td>
			<td class="textboxGray"><?=$arr['REASON_CODE']?></td>
                        <td class="textboxGray"><?=$arr['REGION']?></td>
			<td class="textboxGray"><?=($arr['SHOP_ID'])?></td>
                        <td class="textboxGray">
                        <a href="#" class= "options" onclick="javascript:window.open('./FormProcessor/customer_details.php?rec_id=<?=$arr['REC_ID']?>', '_blank','top=100, left=100,height=520, width=1100, status=no, menubar=no, resizable=no, scrollbars=no, toolbar=no,location=no, directories=no');"><img src='../../images/icon-details-red.jpg' /></a>
                            </td>
		</tr>
<?	} ?>
</table>
<? } ?>