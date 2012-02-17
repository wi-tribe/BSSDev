<?php
include_once('../util_config.php');
include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
include_once(CLASSDIR."/Logging.php");
$log_obj = new Logging();
$sales_obj = new SalesPersonnel();

$sql_csv = $sales_obj->getCustomerCompleteData($month, $year);

$myFile = "export.csv";
$fh = fopen('/tmp/'.$myFile, 'w') or die("can't open file");

$dataHeading = array('CUSTOMER_ID','FIRST_NAME','LAST_NAME','REGION_NAME','CUSTOMER_STATUS','TELEPHONE_NUMBER','MOBILE_NUMBER','EMAIL_ADDRESS','DATE_CREATED','BUSINESS_TYPE','CPE_MAC_ADDRESS','USAGEMONTH1'
                    ,'USAGEMONTH2','PACKAGE_ID','PACKAGE_NAME','SALES_REP_ID','SALES_REP_NAME','SHOP_ID','SHOP_NAME','ADDRESS','LAST_COMMENT','PROFILE_ID','PAYTYPE','PARENT_ACCT','TOTAL_DUE'
                    ,'LAST_STATUS_DATE','PAID','BILLING_CYCLE','DATE_OF_PAYMENT','REC_ID','LAST_REASON_CODE','CUSTOMER_PROFILE','ALLOCATED_SE','PROMO1','PROMO2','NEARESTRETAILSHOP1','NEARESTRETAILSHOP2'
                    ,'NEARESTRETAILSHOP3');
fputcsv($fh,$dataHeading,",");
for($int=0;$int<count($sql_csv);$int++)
{
    fputcsv($fh, $sql_csv[$int]);
}
fclose($fh);
header('Content-Type: application/csv'); //Outputting the file as a csv file
header('Content-Disposition: attachment; filename=export.csv'); //Defining the name of the file and suggesting the browser to offer a 'Save to disk ' option
header('Pragma: no-cache');
readfile('/tmp/export.csv');
$log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly exported customer payables data ");
exit;

?>
