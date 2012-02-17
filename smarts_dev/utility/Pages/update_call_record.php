<?php
	session_start();
	ob_start();
        include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/Logging.php");
        
	$sales_obj = new SalesPersonnel();
        $log_obj = new Logging();

	$call_status['REC_ID'] = $_REQUEST['rec_id'];
	$call_status['CUSTOMER_ID'] = $_REQUEST['customer_id'];
	$call_status['CALL_REASON_CODE'] = $_REQUEST['reason_code'];
	$call_status['SALESPERSONNEL_ID'] = $_REQUEST['salespersonnel_id'];
        $call_status['REMARKS'] = $_REQUEST['remarks'];
	$sales_rep = $sales_obj->updateCallStatus($call_status);

        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']."/".$call_status['SALESPERSONNEL_ID']." successfuly saved call record against customer: ".$call_status['CUSTOMER_ID']." reason code: ".$call_status['CALL_REASON_CODE']);
        if($call_status['CALL_REASON_CODE']=='Will not pay - Sales Return Request')
        {
             //$result = $sales_obj->getAccount($_POST['rec_id']);
             $sales_obj->insertReasonCode_WillNotPay_Dunning($call_status,$_POST);
             $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']."/".$call_status['SALESPERSONNEL_ID']." successfuly inserted information for dunning team against customer: ".$call_status['CUSTOMER_ID']." reason code: ".$call_status['CALL_REASON_CODE']);
        }
?>
call record saved successfully

	
	
	
