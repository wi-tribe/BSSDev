<?php
/*************************************************
* Written by Saad Bashir 
* imsaady@gmail.com
* NPM Integration base class
**************************************************/
//if(!defined("witribeSaad") || !witribeSaad) die('unauthorized access');
include_once(LIB."/nusoap1.php");
include_once("../core_config_uat.php");
//require_once('lib/nusoap/class.wsdlcache.php');
/**
 * Deals with login authentication and validation
 *
 * @category   Order Entry
 * @package    CORE
 * @author     Saad Bashir <imsaady@gmail.com> Original Author
 * @copyright  2008-2010 wi-tribe pakistan
 */
class NPMWrapper 
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
		$this->conf = new core_config();
		$this->npmHost = new nusoap_client($this->conf->MOTOROLA_NPM_API_URL,true);
		$this->npmHost->setCredentials($this->conf->MOTOROLA_NPM_API_USER,$this->conf->MOTOROLA_NPM_API_PASS);
		$this->npmHost->loadWSDL();
        
		//$this->printDebug(true); 
	} //END __construct()
	
	/******************************************************** 
	 * destructor
	 *
	 */
	public function __destruct()
	{
	
	} //END __destruct()
	
    /********************************************************
	 * 
	 *
	 */
	public function addSubscriberAccount($activated, $circuitAttributes, $creationDate, $local, $locationLock, $account_name, $password="", $subscriptionIds=NULL)
    {
        $circuitAttributes = array();
        $creationDate = "";
        $subscriptionIds = ""; // NOT USED HERE
        
        $params = array(	array (	'activated' => $activated,
                                    'circuitAttributes' => $circuitAttributes,
                               //     'creationDate' => $creationDate,
                                    'local' => $local,
                                    'locationLock' => $locationLock ,
                                    'name' => $account_name,
                                    'password' => $password
                                )
                        );             
		$response = $this->npmHost->call('addSubscriberAccount', $params);
		$this->printDebug();
        return false;
	} // END OF addSubscriberAccount()

    public function removeSubscriberAccount($account_name)
    {
        $params = array ( 'String_1' => $account_name);
		$response = $this->npmHost->call('removeSubscriberAccount', $params);
		$this->printDebug();
        return false;
	} // END OF addSubscriberAccount()

    public function clearSubSessionBySubAcctName($account_name)
    {
        $params = array ( 'String_1' => $account_name);
		$response = $this->npmHost->call('clearSubSessionBySubAcctName', $params);
		$this->printDebug();
        return false;
	} // END OF addSubscriberAccount()

     /********************************************************
	 * 
	 *
	 */
    public function changeAndApplyServicesToSubAcctName($account_name, $profiles)
    {
        $params = array (   'String_1' => $account_name,
                            'arrayOfString_2' => $profiles
                        );

        $response = $this->npmHost->call('changeAndApplyServicesToSubAcctName', $params);
        $this->printDebug();
        return false;
    } // END OF changeAndApplyServicesToSubAcctName()


	/********************************************************
	 *
	 *
	 */
    public function getSubscriberAccount($account_name)
    {
	 	$params = array( $account_name );
		$response = $this->npmHost->call('getSubscriberAccount', $params);
        $this->printDebug();
        return $response;
    } // END OF getSubscriberAccount()


	/********************************************************
	 *
	 *
	 */
    public function getSubAcctName($ip_address)
    {
	 	$params = array ( 'String_1' => $ip_address);
		$response = $this->npmHost->call('getSubAcctName', $params);
		$this->printDebug();
		return $response;
    } // END OF getSubAcctName()
	
	
	 /********************************************************
	 *
	 *
	 */
	public function getAllServices()
	{
		try {
			$response = $this->npmHost->call('getAllServices');		
		}catch (Exception $e)
		{
			$this->error = $e->getMessage();
			return false;
		}
        $this->printDebug();
		return true;	
	} //END getAllServices()
	
	
	/********************************************************
	 * 
	 *
	 */
    public function reactivateAndApplyServicesToSubAcctName($account_name, $profiles)
    {
        $params = array (   'String_1' => $account_name,
                            'arrayOfString_2' => $profiles
                        );

        $response = $this->npmHost->call('reactivateAndApplyServicesToSubAcctName', $params);
        $this->printDebug();
        return false;
    } // END OF reactivateAndApplyServicesToSubAcctName()
	/********************************************************
	 * 
	 *
	 */
	public function authenticateSubscriber($account_name, $password)
    {
        $params = array (   'String_1' => $account_name,
                            'String_2' => $password
                        );

        $response = $this->npmHost->call('authenticateSubscriber', $params);
        $this->printDebug(true);
        return $response;
    }	// END OF authenticateSubscriber()
	/********************************************************
	 * 
	 *
	 */
	public function authenticateSubscriberWithReply($account_name, $password)
    {
        $params = array (   'String_1' => $account_name,
                            'String_2' => $password
                        );

        $response = $this->npmHost->call('authenticateSubscriberWithReply', $params);
        $this->printDebug(true);
        return $response;
    }// END OF authenticateSubscriberWithReply()
	
	/********************************************************
	 * 
	 *
	 */
	public function bindSubscriberToSession($ipAddr, $account_name)
    {
        $params = array (   'String_1' => $ipAddr,
                            'String_2' => $account_name
                        );

        $response = $this->npmHost->call('bindSubscriberToSession', $params);
        $this->printDebug(true);
		return $response;
    }// END OF bindSubscriberToSession()
	
	/********************************************************
	 * 
	 *
	 */
	public function logonSubSession($ipAddr, $account_name, $password)
    {
        $params = array (   'String_1' => $ipAddr,
                            'String_2' => $account_name,
							'String_3' => $password
                        );

        $response = $this->npmHost->call('logonSubSession', $params);
        $this->printDebug(true);
		return $response;
    }// END OF logonSubSession()
	
	/********************************************************
	 * 
	 *
	 */
	public function logonSubSessionWithReply($ipAddr, $account_name, $password)
    {
        $params = array (   'String_1' => $ipAddr,
                            'String_2' => $account_name,
							'String_3' => $password
                        );

        $response = $this->npmHost->call('logonSubSessionWithReply', $params);
        $this->printDebug(true);
		return $response;
    }// END OF logonSubSession()
	

	/********************************************************
	 * 
	 *
	 */
    public function reactivateAndApplyServicesToSubSession($account_name, $profiles)
    {
        $params = array (   'String_1' => $account_name,
                            'arrayOfString_2' => $profiles
                        );

        $response = $this->npmHost->call('reactivateAndApplyServicesToSubSession', $params);
        $this->printDebug();
        return false;
    } // END OF reactivateAndApplyServicesToSubAcctName()
	
	
	
	/********************************************************
	 * 
	 *
	 */
    public function reactivateServicesForSubSession($account_name, $profiles)
    {
        $params = array (   'String_1' => $account_name,
                            'arrayOfString_2' => $profiles
                        );

        $response = $this->npmHost->call('reactivateServicesForSubSession', $params);
        $this->printDebug();
        return false;
    } // END OF reactivateServicesForSubSession()
	
	
	/********************************************************
	 * 
	 *
	 */
    public function reactivateServicesForSubAcctName($account_name, $profiles)
    {
        $params = array (   'String_1' => $account_name,
                            'arrayOfString_2' => $profiles
                        );

        $response = $this->npmHost->call('reactivateServicesForSubAcctName', $params);
        $this->printDebug();
        return false;
    } // END OF reactivateServicesForSubAcctName()
	
	
	/********************************************************
	 *
	 *
	 */
    public function removeService($account_name, $profile)
    {
	 	$params = array (   'String_1' => $account_name,
							'String_2' => $profile
						);
		$response = $this->npmHost->call('removeService', $params);
		$this->printDebug();
		return $response;
    } // END OF getSubAcctName()
	
	/********************************************************
	 *
	 *
	 */
    public function addServiceToSubAcctName($account_name, $profile)
    {
	 	$params = array (   'String_1' => $account_name,
							'String_2' => $profile
						);
		$response = $this->npmHost->call('addServiceToSubAcctName', $params);
		$this->printDebug();
		return $response;
    } // END OF getSubAcctName()
	
	/********************************************************
	 *
	 *
	 */
    public function removeServiceFromSubSession($account_name, $profile)
    {
		
	 	$params = array (   'String_1' => $account_name,
							'String_2' => $profile
						);
		$response = $this->npmHost->call('removeServiceFromSubSession', $params);
		$this->printDebug();
		return $response;
    } // END OF removeServiceFromSubSession()

    public function applyServicesToSubAcctName($account_name)
    {
        $params = array(
            'String_1' => $account_name
        );

        $response = $this->npmHost->call( 'applyServicesToSubAcctName' , $params);
        $this->printDebug();
        return $response;
    }


    


     /********************************************************
	 *
	 *
	 */
	public function printDebug($local=false)
    {
        if($this->debug || $local)
   		{   echo '<h2>Request</h2><pre>' . htmlspecialchars($this->npmHost->request, ENT_QUOTES) . '</pre>';
            echo '<h2>Response</h2><pre>' . htmlspecialchars($this->npmHost->response, ENT_QUOTES) . '</pre>';
            //echo '<h2>Debug</h2><pre>' . htmlspecialchars($this->npmHost->debug_str, ENT_QUOTES) . '</pre>';
        }

    } //END OF printDebug()

} //END OF NPMWrapper CLASS
?>