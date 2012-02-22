<?
#CORE configuration directives
class base_config {
	
	/**************************************************************************/
	/***************	LOCAL DATABASE CONFIGURATIONS		*******************/
	/**************************************************************************/
	// database host
	
	protected $DB_HOST				= "10.1.67.201";
	protected $DB_NAME				= "pindb";	// database name
	protected $DB_USER				= "smarts2";// database user
	protected $DB_PASSWORD                          = "smarts321";// database user's password
	protected $DB_PORT				= "1522";
	protected $DB_TYPE				= "oci8";
	protected $DB_TNS				= 	"(DESCRIPTION =
											(ADDRESS_LIST =
												(ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.67.201)(PORT = 1522))
											)
											(CONNECT_DATA =
											   (SERVICE_NAME = pindb)
											)
										  )";
	protected $DB_DEBUG				= false;
	/**************************************************************************/
	/**********************		SYSTEM CONFIGYRATIONS		*******************/
	/**************************************************************************/

	//path to core files used by other modules
	public $ADMIN_EMAIL							=	"saad.bashir@pk.wi-tribe.com";//email to use as FROMs
	public $SITE_NAME							=	"wi-tribe.net.pk";
	public $LOG_PATH							=	"/var/log/smarts/";
	
	public function __construct()
	{
	}
	
	public function __destruct()
	{
	}

}
?>
