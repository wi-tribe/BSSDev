<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
	include_once(UTIL_CLASSDIR."/NadraWrapper.php");
        include_once(LIB."/adodb5/adodb.inc.php");
        include_once(CLASSDIR."/Logging.php");

        $log_obj = new Logging();
        
        if($_POST['customer_id']==null)
            {
                echo "Customer ID is Null";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Smarts Ballance check failed because user provided empty value");
                exit();
            }
	$customer_id = trim($_POST['customer_id']);
	//$MAC = trim($_POST['MAC']);

	$TnsName = "(DESCRIPTION =
    (ADDRESS_LIST =
      (ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.67.33)(PORT = 1521))
    )
    (CONNECT_DATA =
      (SERVICE_NAME = BSSPINDB)
    )
  )
";

 
	
 $db = &NewADOConnection("oci8");
 $db->Connect($TnsName, 'pin', 'pin');

 $rs = $db->Execute("SELECT BGT.ACCOUNT_OBJ_ID0, ACN.FIRST_NAME, ACN.LAST_NAME, ACN.EMAIL_ADDR, SUM(BST.CURRENT_BAL) TOTAL_DUE
FROM BAL_GRP_SUB_BALS_T BST, 
     BAL_GRP_T BGT, 
     SERVICE_T ST,
     ACCOUNT_NAMEINFO_T ACN
WHERE BGT.ACCOUNT_OBJ_ID0 = ST.ACCOUNT_OBJ_ID0
AND BST.REC_ID2 = '586' 
AND (BST.VALID_TO = 0 OR TRUNC(U2S(BST.VALID_TO)) > SYSDATE) 
AND BST.CURRENT_BAL <> 0
AND BST.OBJ_ID0 = BGT.POID_ID0 
AND ST.POID_TYPE = '/service/ip'
AND ACN.OBJ_ID0 = ST.ACCOUNT_OBJ_ID0
AND ST.ACCOUNT_OBJ_ID0 = '$customer_id'
GROUP BY BGT.ACCOUNT_OBJ_ID0,ACN.FIRST_NAME, ACN.LAST_NAME, ACN.EMAIL_ADDR");

$data = $rs->FetchRow();
if(!$data)
{
    echo "Record Not found!";
    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Unable to check ballance because no record against customer: $customer_id");
}
else
{
  //  $data = $rs->FetchRow();
    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly checked ballance against customer: $customer_id");
?>

<table width='100%' >
        <tr >
            <td width="22%"><strong>Customer Balance</strong></td>
             <td width="78%"><strong>Account ID: <?php echo $data['ACCOUNT_OBJ_ID0']; ?></strong></td>
        </tr>
          <tr>
            <td valign="top"><strong>Details</strong></td>
            <td width="78%">
            	<table width="100%">
                	<tr>
                        <td width="26%">First Name</td>
                        <td width="58%" style="font-size: 16px"><?php echo $data['FIRST_NAME']; ?></td>
                    </tr>
                    <tr>
                        <td width="26%">Last Name</td>
                        <td width="58%" style="font-size: 16px"><?php echo $data['LAST_NAME']; ?></td>
                    </tr>
                    <tr>
                        <td width="26%">Email Address</td>
                        <td width="58%" style="font-size: 16px"><?php echo $data['EMAIL_ADDR']; ?></td>
                    </tr>
                    <tr>
                        <td width="26%">Total Due</td>
                        <td width="58%" style="font-size: 16px"><?php echo $data['TOTAL_DUE']; ?></td>
                    </tr>
                </table>
           	
      </td>
      </tr>
</table>


<?php 
}

?>
