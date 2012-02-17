<? 
/*************************************************
* Written by Saad Bashir Dar 
* imsaady@gmail.com
* sales rep class, authentication based on
* Oracle BRM /SugarCRM
**************************************************/
include_once("../util_tables.php");
include_once("../core_config_uat.php");
include_once(UTIL_CLASSDIR."/Database.php");
include_once(CLASSDIR."/DBAccess.php");
include_once(UTIL_CLASSDIR."/encryption.php");
include_once(UTIL_CLASSDIR."/Sales.php");
include_once(UTIL_CLASSDIR."/SalesPersonnel.php");

/**
 * used for different reporting requirements
 *
 * @category   Order Entry
 * @package    WOE
 * @author     Saad Bashir <imsaady@gmail.com> Original Author
 * @copyright  2008-2010 wi-tribe Pakistan
 */
 
class DB_Logging
{
	private $debug;
	/**
	 * Default configuration object
	 *
	 * @var config
	 */
	private $conf;
	/**
	 * db object
	 *
	 * @var db
	 */
	private $db;
	/**
	 * Store local class messages/errors
	 *
	 * @var string
	 */
	public $error;
	
	/**
	 * Constructor
	 *
	 */
	public function __construct()
	{
            $this->db = new DBAccess();
	}
	
	/**
	 * Destructor
	 *
	 */
	public function __destruct()
	{
		
	}

        //tested
	private function debugMsg($message,$arr=NULL)
	{
		if($this->debug)
		{
			print "debug:______________________ $message<BR>";
			if($arr!=NULL)
				print_r($arr);
				print "<BR>";
		}
	}
	/**
	 * Store prospect information into database
	 *
	 * @return bool
	 */
	public function logToDB($log_info)
	{   
            $sales = new SalesPersonnel();
            $salesRep = $sales->getCurrentCSR();
            
            if ($log_info['action'] == 'cnic_verification') {
                    $insert = "CITIZEN_NO,APPLICANT_NAME,FATHER_NAME,MOTHER_NAME,ADDRESS,BIRTH_PLACE,MAC,VERIFICATION_STATUS,USER_ID,USERNAME,SHOP_ID,LOG_DATE,DB_HIT";
                    $vals = "'" . $log_info['debug_details']['citizen_no']. "','" . base64_encode($log_info['debug_details']['applicant_name']) . "','" . base64_encode($log_info['debug_details']['father_name']) . "'
                                ,'" . base64_encode($log_info['debug_details']['mother_name']) . "','" . base64_encode($log_info['debug_details']['address']) . "'
                                ,'" . base64_encode($log_info['debug_details']['birth_place']) . "','" . base64_encode($log_info['mac']) . "'
                                ,'" . $log_info['response'] . "','" . $salesRep['SALESPERSONNEL_ID'] . "','" . $salesRep['USERID'] . "','" . $salesRep['SHOP_ID'] . "',SYSDATE,'LIVE'";
                
                    $id = $this->db->InsertRecord(CNIC_LOG_TABLE, $insert, $vals);
            }
            else
            {                
                $log_record['user_id'] 			= $salesRep['SALESPERSONNEL_ID'];
                $log_record['username'] 		= $salesRep['USERID'];
		$log_record['action'] 			= $log_info['action'];
		$log_record['details'] 			= $log_info['details'];
                $log_record['status'] 			= $log_info['status'];
		$log_record['debug_details']            = preg_replace('/\<br(\s*)?\/?\>/i', "\n", $log_info['debug_details']);
                
                 $insert = "USER_ID,USERNAME,ACTION,DETAILS,VERIFICATION_STATUS,TIME,DEBUG_DETAILS";
                 $vals = "'" . $log_record['user_id']. "','" . $log_record['username'] . "','" . $log_record['action'] . "'
                                ,'" . $log_record['details'] . "','" . $log_record['status'] . "',SYSDATE,'" . $log_record['debug_details'] . "'";
                 
                 $id = $this->db->InsertRecord(SMARTS_LOG_TABLE, $insert, $vals);

                if (!$id) {
                    $this->error .= "<BR> Unable to save prospect information";
                    return -1;
                }
            return $id;
            }
        }
}
?>