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
include_once(UTIL_CLASSDIR."/encryption.php");

class Sales
{
	private $db;
	private $conf;
	public $error;	
	
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
	
	public function __destruct()
	{
	}

    public function startNewSale($sale_info)
    {
        $csr = $this->getCurrentCSR();
        $new_sale['customer_id']		= $sale_info['customer_id'];
        $new_sale['package_id']			= $sale_info['package_id'];
		$new_sale['sales_rep']			= $csr[0]['csr_username'];
		$new_sale['sales_rep_id']		= $csr[0]['csr_id'];
		$new_sale['sale_channel_id']	= $csr[0]['sale_channel_id'];
		$new_sale['shop_id']			= $csr[0]['shop_id'];
		$new_sale['shop_tl_id']			= $csr[0]['tl_id'];
		$new_sale['region_id']			= $csr[0]['region_id'];
		$new_sale['region_rsm_id']		= $csr[0]['rsm_id'];
        $this->db->insert_table_data(SALES_TABLE,$new_sale);
		$id = $this->db->get_last_insert_id();
        return $id;

    }

	public function getCurrentCSR()
	{
		$sales_rep['csr_username'] = rc4crypt::decrypt("saadi-witribe",base64_decode($_SESSION['username']),1);
            return $this->getSalesRep($sales_rep);

	}
	
	//testesd
	public function getSalesRep($sales_rep)
	{
		$condition="1";
		if(isset($sales_rep['csr_id']))
		{
			$condition = "csr_id='".$sales_rep['csr_id']."'";
		}
		else if(isset($sales_rep['csr_username']))
		{
			$condition = "csr_username='".$sales_rep['csr_username']."'";
		}	
		$query =	"SELECT "
					.SALE_USERS_TABLE.".csr_id, "
					.SALE_USERS_TABLE.".csr_name, "
					.SALE_USERS_TABLE.".csr_username, "
					.SALE_USERS_TABLE.".sale_channel_id, "
                                        .SALE_CHANNELS_TABLE.".channel_name, "
                                        .SALE_LEVELS_TABLE.".sale_level_name, "
					.SALE_LEVELS_TABLE.".short_name, "
					.SHOPS_TABLE.".shop_id, "
					.SHOPS_TABLE.".shop_name, "
					.SHOPS_TABLE.".tl_id, "
					.REGIONS_TABLE.".region_id, "
					.REGIONS_TABLE.".region_name, "
					.REGIONS_TABLE.".region_prefix, "
					.REGIONS_TABLE.".rsm_id ".
					" FROM ".SALE_USERS_TABLE.
					" INNER JOIN ".SALE_LEVELS_TABLE." on ".SALE_USERS_TABLE.".csr_level = ".SALE_LEVELS_TABLE.".sale_level_id".
					" INNER JOIN ".SHOPS_TABLE." on ".SALE_USERS_TABLE.".csr_shop_id = ".SHOPS_TABLE.".shop_id".
					" INNER JOIN ".SALE_CHANNELS_TABLE." on ".SALE_CHANNELS_TABLE.".channel_id = ".SALE_USERS_TABLE.".sale_channel_id".
					" INNER JOIN ".REGIONS_TABLE." on ".SHOPS_TABLE.".region_id = ".REGIONS_TABLE.".region_id".
					" WHERE $condition";
		return $this->db->execute_complex_query($query);
	}
	
	
	public function getSaleRegion($sale_info)
	{
		$condition="1";
		if(isset($sale_info['sale_id']))
		{
			$condition = "sale_id=".$sale_info['sale_id'];
		}else if(isset($sale_info['customer_id']))
		{
			$condition = "customer_id=".$sale_info['customer_id'];
		}
		
		$query =	"SELECT "
					.SALES_TABLE.".sale_id, "
					.REGIONS_TABLE.".region_id, "
					.REGIONS_TABLE.".region_name, "
					.REGIONS_TABLE.".region_prefix, "
					.REGIONS_TABLE.".rsm_id ".
					" FROM ".SALES_TABLE.
					" INNER JOIN ".REGIONS_TABLE." on ".SALES_TABLE.".region_id = ".REGIONS_TABLE.".region_id".
					" WHERE $condition";
		return $this->db->execute_complex_query($query);
	}
	
	public function getSales($sales_rep)
	{

		$condition="1";
		if(isset($sales_rep['csr_id']))
		{
			$condition .=" AND ".SALES_TABLE.".sales_rep_id='".$sales_rep['csr_id']."'";
        }
        
        if(isset($sales_rep['customer_status']))
		{
			$condition .=" AND ".CUSTOMER_TABLE.".customer_status='".$sales_rep['customer_status']."'";
        }
            $query =	"SELECT "
                        .CUSTOMER_TABLE.".customer_id, "
                        .CUSTOMER_TABLE.".first_name, "
                        .CUSTOMER_TABLE.".last_name, "
                        .CUSTOMER_TABLE.".identification_number, "
                        .CUSTOMER_TABLE.".telephone_number, "
                        .CUSTOMER_TABLE.".email_address, "
                        .CUSTOMER_TABLE.".customer_status, "
                        .CUSTOMER_TABLE.".status_reason "
                        ." FROM ".SALES_TABLE
                        ." INNER JOIN ".CUSTOMER_TABLE." on ".SALES_TABLE.".customer_id = ".CUSTOMER_TABLE.".customer_id"
                        ." WHERE ".$condition;

        return $this->db->execute_complex_query($query);
	}

	
	
}
?>