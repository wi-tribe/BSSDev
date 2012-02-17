<?php
            session_start();
            ob_start();

            include_once("../util_config.php");
            $conf=new config();
            include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
            $sales_obj = new SalesPersonnel();
            include_once(CLASSDIR."/Export.php");
            include_once(CLASSDIR."/Logging.php");
            $log_obj = new Logging();

                $columns[] = array('index'=>'A','name'=>'Ex_ID','label'=>'Executive ID' );
                $columns[] = array('index'=>'B','name'=>'Ex_Name','label'=>'Executive Name' );
                $columns[] = array('index'=>'C','name'=>'ATTEMPTED','label'=>'Attempted Calls' );
		$columns[] = array('index'=>'D','name'=>'CONNECTED','label'=>'No of Connected Calls' );
		$columns[] = array('index'=>'E','name'=>'NOTCONNECTED','label'=>'No of Not Connected Calls' );
		$columns[] = array('index'=>'F','name'=>'CONNECTIVITY','label'=>'Connectivity %' );
		$columns[] = array('index'=>'G','name'=>'TOTAL_ALLOCATED_CALLS','label'=>'Total Allocated Calls' );
                $columns[] = array('index'=>'H','name'=>'PAID','label'=>'Payments Received' );
                $columns[] = array('index'=>'I','name'=>'UNPAID','label'=>'Unpaid' );
                $columns[] = array('index'=>'J','name'=>'PAIDRATIO','label'=>'Payment Ratio %' );
                $columns[] = array('index'=>'K','name'=>'SHOP','label'=>'Shop ID' );
                $columns[] = array('index'=>'L','name'=>'REGION','label'=>'Region' );
                $columns[] = array('index'=>'M','name'=>'BILLING_CYCLE','label'=>'Billing Cycle' );
                
                
                $data['Ex_ID'] =  $_POST['Ex_ID_'];
                $data['Ex_Name'] =  $_POST['Ex_Name_'];
                $data['ATTEMPTED'] =  $_POST['ATTEMPTED_'];
                $data['CONNECTED'] =  $_POST['CONNECTED_'];
                $data['NOTCONNECTED'] =  $_POST['NOTCONNECTED_'];
                $data['CONNECTIVITY'] =  $_POST['CONNECTIVITY_'];
                $data['TOTAL_ALLOCATED_CALLS'] =  $_POST['TOTAL_ALLOCATED_CALLS_'];
                $data['PAID'] =  $_POST['PAID_'];
                $data['SHOP'] =  $_POST['SHOP_'];
                $data['UNPAID'] =  $_POST['UNPAID_'];
                $data['PAIDRATIO'] =  $_POST['PAIDRATIO_'];
                $data['REGION'] =  $_POST['REGION_'];
                $data['BILLING_CYCLE'] =  $_POST['BILLING_CYCLE_'];

                $attmptcount = count($data['ATTEMPTED']);
                $connCount = count($data['CONNECTED']);
                $NotconnCount = count($data['NOTCONNECTED']);
                $PAIDCount = count($data['PAID']);
                $UNPAIDCount = count($data['UNPAID']);
                $CONNECTIVITYCount = count($data['CONNECTIVITY']);
                $PAIDRATIOCount = count($data['PAIDRATIO']);
                
                $data['ATTEMPTED'][$attmptcount] = $_POST['GATTEMPTED_'][0];
                $data['CONNECTED'][$connCount] = $_POST['GCONNECTED_'][0];
                $data['NOTCONNECTED'][$NotconnCount] = $_POST['GNOTCONNECTED_'][0];
                $data['TOTAL_ALLOCATED_CALLS'][$attmptcount] = $_POST['GTOTAL_ALLOCATED_CALLS_'][0];
                $data['PAID'][$PAIDCount] = $_POST['GPAID_'][0];
                $data['UNPAID'][$UNPAIDCount] = $_POST['GUNPAID_'][0];
                $data['CONNECTIVITY'][$CONNECTIVITYCount] = round((($_POST['GCONNECTED_'][0]/$_POST['GATTEMPTED_'][0])*100),0);
                $data['PAIDRATIO'][$PAIDRATIOCount] = round((($_POST['GPAID_'][0]/$_POST['GTOTAL_ALLOCATED_CALLS_'][0])*100),0);               
              
                $fname = "retentionCallsSummary_".date("Y").date("m").date("d").".xls";
		$documentInfo = array(
                                        'Title' => 'Retention Calls Summary Report',
					  'Heading' => 'Retention Calls Summary Report',
					 'Filename' => $fname,
					 'Start_date' => $_POST['start_date'],
                                         'End_date' => $_POST['end_date']
					 );
		$Searchcriteria = array( 'Region' => $_POST['region'],
								 'Created By' => $_POST['createdBy'],
								 'Channel' => $_POST['channel'],
								 'CCE' => $_POST['user']
			);
               
         
        $exp_obj = new Export();
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly exported retention call summary between dates :".$_POST['start_date']." to ".$_POST['end_date']);
        $exp_obj->RetentionSummaryExport($columns,$documentInfo,$data,$Searchcriteria,$grand_count);
?>
