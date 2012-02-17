<?
#CORE configuration directives
class core_config {
	
	/**************************************************************************/
	/***************	LOCAL DATABASE CONFIGURATIONS		*******************/
	/**************************************************************************/
	// database host
	public $DB_HOST								=	"localhost";
	// database name
	public $DB_NAME								=	"smarts_db_uat";
	// database user
	public $DB_USER								=	"root";
	// database user's password
	public $DB_PASSWORD							=	"jiR@b$$1";
	
	/**************************************************************************/
	/***************	INTEGRATION API CONFIGURATIONS		 ******************/
	/**************************************************************************/
	//SugarCRM webservice URL
	public $CRM_WEBSERVICE_URL					=		"http://crm.wi-tribe.net.pk/soap.php?wsdl";
    //SugarCRM LEAD MODULE NAME
	public $CRM_LEAD_MODULE                                         =		"wtp_lead";
	//url to use for calling Oracle BRM APIs
	public $ORACLE_BRM_API_URL					=		"";#include ending /
	//url to use for calling Intraisp APIs
//	public $MOTOROLA_NPM_API_URL                                    =		"https://10.1.70.254:8443/NPM_API-6.1.4.4/service?WSDL";  //balancer
	public $MOTOROLA_NPM_API_URL                                    =		"https://10.1.69.10:8443/NPM_API-6.1.4.4/service?WSDL";  //direct to API Server
	//username to use for calling Intraisp APIs
	public $MOTOROLA_NPM_API_USER                                   =		"smarts";
	//password to use for calling Intraisp APIs
	public $MOTOROLA_NPM_API_PASS                                   =		"wtpkSmarts987";
	//url to use for calling Netspan APIs
	public $COVERAGE_TOOL_URL					=		"http://10.1.82.220/WiTribeGCService/Service.asmx?WSDL";
	
	/**************************************************************************/
	/**********************		SYSTEM CONFIGYRATIONS		*******************/
	/**************************************************************************/

	//path to core files used by other modules
	public $CORE_FILES_PATH						=	"/var/www/html/smarts/utility/CLASSES";
	public $ORDER_ENTRY_MODULE_ABSOLUTE_PATH	=	"/var/www/html/uat/oe/";#include ending /
	public $CUSTOMER_CARE_MODULE_ABSOLUTE_PATH	=	"/var/www/html/uat/cc/";
	public $COVERAGE_TOOL_MODULE_ABSOLUTE_PATH	=	"/var/www/html/uat/coveragetool/";
//	public $INVENTORY_MODULE_ABSOLUTE_PATH		=	"/usr/local/apache2/htdocs/inventory/";
//	public $CONFIGURATION_MODULE_ABSOLUTE_PATH	=	"/usr/local/apache2/htdocs/configuration/";
//	public $REPORTING_MODULE_ABSOLUTE_PATH		=	"/usr/local/apache2/htdocs/reports/";
	public $PERMISSIONING_MODULE_ABSOLUTE_PATH      =       "/var/www/html/uat/core/";
        public $EXPORT_MODULE_ABSOLUTE_PATH		=	"/var/www/html/uat/oe/";

        public $ORDER_ENTRY_MODULE_SITE_URL			=	"http://oe-fut.wi-tribe.net.pk";
	public $CUSTOMER_CARE_MODULE_SITE_URL		=	"http://smarts.wi-tribe.net.pk";
	public $COVERAGE_TOOL_MODULE_SITE_URL		=	"http://ct.wi-tribe.net.pk/";
//	public $INVENTORY_MODULE_SITE_URL			=	"http://localhost/inventory-new/";
//	public $CONFIGURATION_MODULE_SITE_URL		=	"http://localhost/configuration/";

//	public $REPORTING_MODULE_SITE_URL			=	"http://localhost/reports/";

	public $ADMIN_EMAIL							=	"saad.bashir@pk.wi-tribe.com";//email to use as FROMs
	public $SITE_NAME							=	"wi-tribe.net.pk";
	public $LOG_PATH							=	"/var/log/brm/";
	
	public function __construct()
	{
	}
	
	public function __destruct()
	{
	}

}
?>
