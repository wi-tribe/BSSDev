<? 
/*************************************************
* Written by Saad Bashir Dar 
* imsaady@gmail.com
* sales rep class, authentication based on
* Oracle BRM /SugarCRM
**************************************************/

//include_once("classes/CRMClient.php");
include_once("../util_tables.php");
include_once("../core_config_uat.php");
include_once(UTIL_CLASSDIR."/Database.php");
include_once(UTIL_CLASSDIR."/encryption.php");

//header('Content-Type: text/html; charset=utf-8');
//mb_internal_encoding("UTF-8");

/**
 * handles sales rep's login request authentication and validation
 *
 * @category   Order Entry
 * @package    WOE
 * @author     Saad Bashir <imsaady@gmail.com> Original Author
 * @copyright  2008-2010 wi-tribe Pakistan
 */
 
class Prospect
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
		$this->conf = new  core_config();
		
		//connect to DB
		 $oe = array (	'host_name' => $this->conf->DB_HOST,
						'database' => $this->conf->DB_NAME,
						'user_name' => $this->conf->DB_USER,
						'password' => $this->conf->DB_PASSWORD
			);
		$this->db = new Database();
 		$this->db->connect($oe);
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
	
	private function PreProcessProspect( $lead_info)
	{	
		if(empty($lead_info['first_name']))
		{
			$this->error.="Please provide first name<br>";			
		} else 
		{
			if(strlen($lead_info['first_name'])>25)
				$this->error.="First name is too large<br>";
				
			if(checkName($lead_info['first_name']))
				$this->error.="First name contains invalid characters<br>";
				
			if(preg_match("/[0-9]/i", $lead_info['first_name']))
				$this->error.="First name should not contain digits<br>";

		}
		
		if(empty($lead_info['last_name']))
		{
			$this->error.="Please provide last name<br>";			
		} else 
		{
			if(strlen($lead_info['last_name'])>25)
				$this->error.="Last name is too large<br>";
				
			if(checkName($lead_info['last_name']))
				$this->error.="Last name contains invalid characters<br>";
				
			if(preg_match("/[0-9]/i", $lead_info['last_name']))
				$this->error.="Last name should not contain digits<br>";
		}
		
		//validate email
		if(empty($lead_info['email_address']) && empty($lead_info['contact_number']))
		{
			$this->error.="Please provide email address or contact number<br>";			
		}else 
		{
			if(!empty($lead_info['email_address']) &&
			!emailsyntax_is_valid($lead_info['email_address']))
			{
				$this->error.="Please provide a valid email address<br>";	
			}
			
			if(!empty($lead_info['contact_number']) && !is_numeric($lead_info['contact_number']))
			{
				$this->error.="Contact number should only contain digits without spaces<br>";	
			}
			
			if(!empty($lead_info['contact_number']) && strlen($lead_info['contact_number'])>17)
			{
				$this->error.="Contact number should not contain more than 17 digits<br>";	
			}
		}
		
		if($this->error!=null)
			return false;
		
		return true;	
	}

    private function clearLastSale()
    {
        $_SESSION['Reg_cust_id']        =NULL;
        $_SESSION['sale_id']            =NULL;
        $_SESSION['cust_add_saved']     =NULL;
        $_SESSION['cust_prd_saved']     =NULL;
        $_SESSION['serv_acct_saved']    =NULL;
        $_SESSION['pros_rec_saved']     =NULL;
        $_SESSION['in_inv_gen']         =NULL;
        $_SESSION['welcome_mail']       =NULL;

    }

    private function clearProspect()
    {
        $_SESSION['current_prospect_id']		= NULL;
        $_SESSION['saved_prospect_id'] 			= NULL;
		$_SESSION['prospect_title']    			= NULL;
        $_SESSION['prospect_first_name']    	= NULL;
        $_SESSION['prospect_last_name']     	= NULL;
        $_SESSION['prospect_contact_number']	= NULL;
        $_SESSION['prospect_email_address'] 	= NULL;
        $_SESSION['prospect_cnic_number']   	= NULL;
        $_SESSION['prospect_occupation']   		= NULL;
        $_SESSION['prospect_city']          	= NULL;
		$_SESSION['saved_prospect_address'] 	= NULL;

    }
    
	/**
	 * Store prospect information into database
	 *
	 * @return bool
	 */
	public function AddProspect($lead_info)
	{
		if(!$this->PreProcessProspect($lead_info))
			return false;

        $this->clearLastSale();

		$this->db->insert_table_data(PROSPECTS_TABLE,$lead_info);
		
		$id = $this->db->get_last_insert_id();
        
		if($id== NULL)
		{
			$this->error .= "<BR>unable to save prospect information";
			return -1;
		}
        return $id;
	}
	
	public function UpdateProspect($prospect_update,$prospect_id)
	{
        $this->debugMsg("prospect_id:$prospect_id<BR> update:",$prospect_update);
		$this->db->update_table_data(PROSPECTS_TABLE, $prospect_update, "prospect_id" , $prospect_id);

        $_SESSION['saved_prospect_address'] = $_POST['gc'];
		$_SESSION['saved_prospect_id'] = $_SESSION['current_prospect_id'];
		return true;
	}
    
	public function prospectRegistered($reg_customer_id,$prospect_id)
	{
		$this->debugMsg("prospect_id:$prospect_id<BR> update:$reg_customer_id");
		$prospect_update['registered_customer_id'] = $reg_customer_id;
		$this->db->update_table_data(PROSPECTS_TABLE, $prospect_update, "prospect_id" , $prospect_id);
        $this->clearProspect();
		return true;
	}

    public function getProspect($p_info)
    {
        if(isset($p_info['customer_id']))
        {
            return $this->db->get_table_data(PROSPECTS_TABLE, "where","registered_customer_id", $p_info['customer_id']);
        }
        return null;
    }
    public function getAllProspects()
    {
        return $this->db->get_table_data(PROSPECTS_TABLE , "where" , "registered_customer_id" , "0");
    }

    public function searchProspect($p_info)
    {
        $query = " SELECT * from ".PROSPECT_TABLE
                ." WHERE 1 ";

        foreach($p_info as $key=>$value)
        {
            if($key=="")
            {
                $query.=" and $key = '%$value%'";
            }
            else $query.="$key = '$value'";
        }
        return $this->db->execute_complex_query($query);
    }
}
?>