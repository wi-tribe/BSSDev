<?php
session_start("VIMS");
set_time_limit(500);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Export.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Logging.php");


//$return = $GLOBALS['_GACL']->getaccessLevel($_SESSION['username'],'receiptreport');
//exit();

$vouch_obj = new Voucher();
$user_obj = new User();
$channel_obj = new Channel();
$log_obj = new Logging();

$date_start = $_POST['start_date'];
$date_end = $_POST['end_date'];

$payment_method = $_POST['payment_method'];
$receipt_type 	= $_POST['receipt_type'];
$receipt_mode 	= $_POST['receipt_mode'];
$region_id 		= $_POST['region'];
$shop_id 		= $_POST['channel'];
$cse_id 		= $_POST['user'];

	$filters = $_POST;
	if($return == 'basic')
	{
		unset($filters['region']);
		unset($filters['channel']);	
	}	
    
        $data = $vouch_obj->getReceiptReport($filters);
      
		$fname = "receiptReport_".date("Y").date("m").date("d").".csv";
               
		$userdetail = $user_obj->getUserInfo($_SESSION['user_id']);
		$user = $userdetail[0]['first_name'].' '.$userdetail[0]['last_name'];
                $channel_type = $_POST['channel_type'];
                if($_POST['channel_type']!='ALL' and $_POST['channel_type']!=null)
                {
                    $channel_type = $vouch_obj->getBRMChannelTypeInfo($_POST['channel_type']);
                    //print_r($channel_type);
                    $channel_type = $channel_type[0]['channel_name'];
                }
		$usr="";
                $cse_id = $_POST['user'];
		$searheduser = $cse_id;
		if($cse_id!="ALL") 
		{
			$usr = $user_obj->getBRMUserDetails($cse_id);
			$searheduser = $usr[0]['full_name'];
		}  

                $docInfoC = array(array("",'Title','Receipt Report','','Channel',$shop_id)
                                ,array("",'Start Date',$date_start,'','Region',$region_id)
                                ,array("",'End Date',$date_end,'','Searched User',$searheduser)
                                ,array("",'Created By',$user,'','Mode of Payment',$payment_method)
                                ,array("",'','','','Receipt Type',$receipt_type)
                                ,array("",'','','','Receipt Mode',$receipt_mode)
                                ,array("",'','','','Channel Type',$channel_type)
                                ,array("",'','','')
                        );
                $dataHeading = array('Receipt ID','Receipt Date','Customer ID','Customer Type','Region Name','Shop Name','CSE/SE ID','CSE/SE Name','Channel Type','Transaction ID','Mode of Payment','Instrument Number','Instrument Bank','Receipt Amount','Receipt Mode','Receipt Type');
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." exported receipt report between dates ".$date_start." to ".$date_end);
		$export_obj = new Export();
                $export_obj->CSVExport($dataHeading,$data,$docInfoC,$fname);
                exit();
?>