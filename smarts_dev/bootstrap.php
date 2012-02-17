<?php
#CORE configuration directives
//path to core files used by other modules
//error_reporting(0);
include("base_config.php");
define('SMARTS', '/var/www/html/dev/smarts_dev');
define('CLASSDIR', SMARTS.'/base/CLASSES');
define('LIB', SMARTS.'/base/LIB');
define('GACL', SMARTS.'/gacl');


define('VIMS_MODULE', SMARTS.'/vims');
define('UTIL_MODULE', SMARTS.'/utility');
define('UTIL_CLASSDIR', UTIL_MODULE.'/CLASSES');
define('CSS', VIMS_MODULE.'/CSS');
define('JSLIB', VIMS_MODULE.'/JS');

define('SMARTS_URL', 'http://smarts.wi-tribe.net.pk');
define('VIMS_URL', SMARTS_URL.'/vims_dev');
define('UTIL_URL', SMARTS_URL.'/smarts_dev/utility');


define('IMAGES', SMARTS_URL.'/images');



include_once(SMARTS."/table_space.php");
//including LIBS
include_once(LIB."/adodb5/adodb.inc.php");
include_once(LIB."/adodb5/adodb-errorhandler.inc.php");
include_once(LIB."/adodb5/adodb-pager.inc.php");
include_once(LIB."/phpexcel/PHPExcel.php");
include_once(LIB.'/general.php');

 $_HTML_LIBS = 	
"<link href='".VIMS_URL."/CSS/style.css' rel='stylesheet' type='text/css'>
"//."<link href='".VIMS_URL."/CSS/JQuery/datePicker.css' rel='stylesheet' type='text/css'>
."<link href='".VIMS_URL."/CSS/JQuery/themes/base/jquery.ui.all.css' rel='stylesheet' type='text/css'>
 
"."<script src='".VIMS_URL."/JS/JQuery/jquery-1.4.2.js' type='text/javascript' language='javascript'></script>
 "//."<script src='".VIMS_URL."/JS/JQuery/jquery.datePicker.js' type='text/javascript' language='javascript'></script>
."<script src='".VIMS_URL."/JS/JQuery/ui/jquery.ui.core.js' type='text/javascript' language='javascript'></script>
"."<script src='".VIMS_URL."/JS/JQuery/ui/jquery.ui.widget.js' type='text/javascript' language='javascript'></script>
"."<script src='".VIMS_URL."/JS/JQuery/ui/jquery.ui.datepicker.js' type='text/javascript' language='javascript'></script>
"."<script src='".VIMS_URL."/JS/ajax.js' type='text/javascript' language='javascript'></script>";
?>