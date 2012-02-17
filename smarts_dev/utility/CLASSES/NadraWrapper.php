<?php
/*************************************************
* Written by Saad Bashir 
* imsaady@gmail.com
* NPM Integration base class
**************************************************/
//if(!defined("witribeSaad") || !witribeSaad) die('unauthorized access');
include_once(LIB."/nusoap1.php");
include_once(UTIL_CLASSDIR."/Logging.php");
include_once(CLASSDIR."/DBAccess.php");
include_once("../util_tables.php");
//require_once('lib/nusoap/class.wsdlcache.php');
/**
 * Deals with login authentication and validation
 * 
 * @category   Order Entry
 * @package    CORE
 * @author     Saad Bashir <imsaady@gmail.com> Original Author
 * @copyright  2008-2010 wi-tribe pakistan
 */

class NadraWrapper
{
    private $debug;
	/**
	 * default Configurations 
	 *
	 * @var conf
	 */
	private $conf;
	
	/**
	 * CRM webserice address
	 *
	 * @var crmHost
	 */
	private $npmHost;

	public $error;
	
	/******************************************************** 
	 * Constructor
	 *
	 */
	
	public function __construct()
	{
        $this->debug = false;
		//$this->conf = new config();
		//$this->npmHost = new nusoap_client($this->conf->motorola_npm_api_url,true);
		//$this->npmHost->setCredentials($this->conf->motorola_npm_api_user,$this->conf->motorola_npm_api_pass);
		$this->npmHost = new nusoap_client("http://10.10.10.11:4321/CitizenVerification/ValidationPort?wsdl",false); 
		//$this->npmHost->setCredentials('smarts','wtpkSmarts987');
		$this->npmHost->loadWSDL();
        //print_r($this->npmHost);
		//$this->printDebug(true);
	} //END __construct()
	
	/******************************************************** 
	 * destructor
	 *
	 */
	public function __destruct()
	{
	
	} //END __destruct()
	
        /* Verify Customer by CNIC
	 */
    public function verify($cnic , $mac = "?")
    {
        if($cnic==null)
        {
            return;
        }
        /* Added By: Aasim.Naveed@pk.wi-tribe.com [PKAasimN]
         * Date: 10-05-2011
         * Verifying Citizen# from CNIC LOG TABLE [Smarts2 schema]
         */
	 
        $query = "select * from ".CNIC_LOG_TABLE." where citizen_no = $cnic";
        $db = new DBAccess();
        $data = $db->CustomQuery($query);
        if($data!=null)
        {
            $data = $data[0];
            
            if($data['verification_status']=='verified')
            {
                $data['verified_p']=true;
                $data['applicant_name'] = base64_decode($data['applicant_name']);
                $data['father_name'] = base64_decode($data['father_name']);
                $data['mother_name'] = base64_decode($data['mother_name']);
                $data['address'] = base64_decode($data['address']);
                $data['birth_place'] = base64_decode($data['birth_place']);
                $data['mac'] = base64_decode($data['mac']);
                
                $sales = new SalesPersonnel();
                $salesRep = $sales->getCurrentCSR();
                
                $data['user_id'] = $salesRep['SALESPERSONNEL_ID'];
                $data['username'] = $salesRep['USERID'];
                $data['shop_id'] = $salesRep['SHOP_ID'];
                
                
                 $insert = "CITIZEN_NO,APPLICANT_NAME,FATHER_NAME,MOTHER_NAME,ADDRESS,BIRTH_PLACE,MAC,VERIFICATION_STATUS,USER_ID,USERNAME,SHOP_ID,LOG_DATE,DB_HIT";
                 $vals = "'" .$data['citizen_no']. "','" . base64_encode($data['applicant_name']) . "','" . base64_encode($data['father_name']) . "'
                                ,'" . base64_encode($data['mother_name']) . "','" . base64_encode($data['address']) . "'
                                ,'" . base64_encode($data['birth_place']) . "','" . base64_encode($data['mac']) . "'
                                ,'" . $data['verification_status'] . "','" . $data['user_id'] . "','" . $data['username'] . "','" . $data['shop_id'] . "',SYSDATE,'LOCAL'";
                 
                
                $id = $db->InsertRecord(CNIC_LOG_TABLE, $insert, $vals);
            }
            return $data;
        }
        
        /* Verifying from NADRA
         */
		if($mac == "")
		{
			$mac ="?";
		}
		$phone_no= $mac;
                $trans_code ="?";
		$username='wit_isb1';
		$password='pakistan1234';	
                $params = array(	array (	'citizen_number' => $cnic,
									'phone_no' => $phone_no,
									'username' => $username,
                                    'password' => $password,
                                    'transaction_code' => $trans_code
                                )
                        );             
		$response = $this->npmHost->call('verify', $params,"http://service.citizenverification.nadra.gov.pk/");
		
                $log = new DB_Logging();
		$log_info['action']="cnic_verification";
                $log_info['response'] = $response['verified_p']==true?'verified':'invalid';
                $log_info['mac'] = $mac;
		$values = $response['applicant_name'];
		$log_info['details']=$cnic."_".$mac."_".utf8_encode($values)."_".( $response['verified_p']==true?'verified':'invalid');
                $log_info['debug_details']=$response;
                $log_info['debug_details']['citizen_no']=$cnic;
		$log->logToDB($log_info);
		$this->printDebug(false);
        return $response;
	} // END OF addSubscriberAccount()


     /********************************************************
	 *
	 *
	 */
	public function printDebug($local=false)
    {
        if($this->debug || $local)
   		{   echo '<h2>Request</h2><pre>' . htmlspecialchars($this->npmHost->request, ENT_QUOTES) . '</pre>';
            echo '<h2>Response</h2><pre>' . htmlspecialchars($this->npmHost->response, ENT_QUOTES) . '</pre>';
			echo '<h2>Debug</h2><pre>' . htmlspecialchars($this->npmHost->debug_str, ENT_QUOTES) . '</pre>';
        }

    } //END OF printDebug()

} //END OF NPMWrapper CLASS
?>