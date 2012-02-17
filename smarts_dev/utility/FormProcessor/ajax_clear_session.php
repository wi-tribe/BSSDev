<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
        
	include_once(UTIL_CLASSDIR.'/NPMWrapper.php');
        
	$mac = trim($_POST['mac_address']);
	$net_op = new NPMWrapper();
		
	$result = $net_op->clearSubSessionBySubAcctName($mac);
	ob_end_clean();	
	header("Location: ajax_provision_status.php");
	exit(0);
?>
	