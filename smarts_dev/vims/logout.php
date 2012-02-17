<?
include_once('vims_config.php');
session_start();
$GLOBALS['_GACL']->CloseConnection();
$userid= $_SESSION['user_id'];
unset($_SESSION);
session_destroy();
header("Refresh: 1; URL=login.php");
 $log = new Logging();
 $log->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." user successfuly logged out UserID:$userid ");
?>
<html>
<head>
<title>Logging out... Please wait</title>
<style type="text/css">
<!--
.logout {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	color: #333333;
}
-->
</style>
</head>
<body>
<div class="logout">
Please Wait...... <br>
You are being logged out
</div>
</body>
</html>