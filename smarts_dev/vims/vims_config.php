<?
error_reporting(E_ERROR|E_PARSE|E_USER_WARNING|E_USER_ERROR|E_CORE_ERROR|E_COMPILE_ERROR|E_RECOVERABLE_ERROR);
ini_set('display_errors', 1);

include_once('/var/www/html/dev/smarts_dev/bootstrap.php');

#configuration directives
class config extends base_config
{
	public $debug=true;
	public $db_prefix='VIMS_';

	public function __contstruct()
	{	
	}
}

include_once(CLASSDIR."/Logging.php");
include_once(CLASSDIR.'/DBAccess.php');
include_once(CLASSDIR.'/Voucher.php');
include_once(CLASSDIR.'/Tracker.php');
include_once(CLASSDIR.'/Order.php');
include_once(GACL.'/permission.php');
include_once(CLASSDIR.'/User.php');

session_start("VIMS");
set_time_limit(700);

$_DB = new DBAccess();
//$_CONF = new config();
$_GACL = new permissions();
$_USER = new User();
$_LOG= new Logging();

if(basename($_SERVER['PHP_SELF']) != 'login.php' && !$_USER->IsLoggedIn() )
{
        ob_end_clean();
        $_LOG->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "User redirected to login page ");
	header("Location: ".VIMS_URL."/login.php");
	exit;
}
?>