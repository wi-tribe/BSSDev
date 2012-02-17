<? 
/*************************************************
* @author Saad Bashir Dar 
* imsaady@gmail.com
* sales rep class, authentication based on
**************************************************/

define( VOUCHER_ACTIVE,		1);
define( VOUCHER_USED,		2);
define( VOUCHER_EXPIRED,	3);
define( VOUCHER_BLOCKED,	8);#its actually inactive
define( VOUCHER_STOLEN,		5);
define( VOUCHER_LOST,		6);
define( VOUCHER_DAMAGED,	7);

include_once(CLASSDIR."/Logging.php");

class BRMIntegration
{

	private $db;
	public $LastMsg;
        private $debug;

	public function __construct()
	{ 
		$this->accounting_table = "Wtb_usage";
//		$tns_name = "(DESCRIPTION =
//                        (ADDRESS_LIST =
//                            (ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.67.42)(PORT = 1521))
//                        )
//                        (CONNECT_DATA =
//                           (SERVICE_NAME = BSSPINDB)
//                        )
//                      )";
                // RAC PROD TNS
		$tns_name = "(DESCRIPTION =
                                (ADDRESS = (PROTOCOL = TCP)(HOST = brm02vip)(PORT = 1521))
                                (ADDRESS = (PROTOCOL = TCP)(HOST = brm03vip)(PORT = 1521))
                                (LOAD_BALANCE = yes)
                                (CONNECT_DATA =
                                  (SERVER = DEDICATED)
                                  (SERVICE_NAME = bsspindb)
                                  (FAILOVER_MODE =
                                    (TYPE = SELECT)
                                    (METHOD = BASIC)
                                    (RETRIES = 180)
                                    (DELAY = 5)
                                  )
                                )
                              )";
               $this->db = &ADONewConnection("oci8");
               $this->db->Connect($tns_name , 'pin' , 'pin');
		

	}
	
	public function __destruct()
	{
		
	}
	
	public function verifyVoucherList($vouchers)
	{
                
		$mac = $acct_info['CPE_MAC'];
		$account_id = $acct_info['ACCT_NO'];
		
		$tablen="VIMS_VOUCVER_".time();
		$query="select count(*) as t from tab where tname='$tablen'";
   		$data=$GLOBALS['_DB']->CustomQuery($query);
   		
   		if($data[0]['t']>=1)
   		{
   			$GLOBALS['_DB']->DBlink->Execute("truncate table $tablen");#drop temp table
   			$GLOBALS['_DB']->DBlink->Execute("drop table $tablen");#drop temp table
   		}
		
		$table="create global temporary table $tablen
				(voucher_serial varchar2(20))
				on commit preserve rows";
 
		$GLOBALS['_DB']->DBlink->Execute($table);
		foreach($vouchers as $voucher)
   			$GLOBALS['_DB']->DBlink->Execute("insert into $tablen (voucher_serial) values ('".$voucher."')");
                
                //CHECK PRE-EXISTENCE IN CURRENT SYSTEM
                $query = "SELECT * FROM VIMS_ITEM_INFORMATION WHERE VOUCHER_SERIAL IN (SELECT VOUCHER_SERIAL FROM $tablen)";
                $data = $GLOBALS['_DB']->CustomQuery($query);
                
                if($data!=null)
                {
                   $this->LastMsg = "Caution: Voucher Serial is already exists in the system!!!";
                   return false;
                }
                
		$query = "	SELECT * from device_t 
					WHERE poid_type ='/device/voucher' and state_id = ".VOUCHER_ACTIVE."
					and device_id in( select voucher_serial from $tablen )
				 ";
		$data = $GLOBALS['_DB']->CustomQuery($query);
		
		$GLOBALS['_DB']->DBlink->Execute("truncate table $tablen");
   		$GLOBALS['_DB']->DBlink->Execute("drop table $tablen");#drop temp table
		$vouchers_verified="";
		foreach($data as $row)
		{
			$vouchers_verified[$row['device_id']] = $row['poid_id0']; 
		}
		return $vouchers_verified;
	}
	
	public function updateVoucherStatus($vouchers, $status)
	{   
                $log_obj = new Logging();
		if($status == VOUCHER_ACTIVE || $status == VOUCHER_BLOCKED)
		{
			$statusList = array(VOUCHER_USED, VOUCHER_EXPIRED, VOUCHER_STOLEN, VOUCHER_LOST, VOUCHER_DAMAGED);
                        
			$data = $this->getVoucherStatus($vouchers,$statusList);

			if(count($data)>0)
			{
				$this->LastMsg = 'This operation is not allowed for some the vouchers.';
				return false;
			}
			$this->db->StartTrans();
			foreach($vouchers as $poid_id0)
			{
                                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." Updating voucher: $poid_id0 in device_t");
				$query = "update device_t set state_id = $status where poid_id0 ='$poid_id0'";
				$this->db->Execute($query);
                                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." Updated! voucher: $poid_id0 in device_t");
			}
			if ($this->db->HasFailedTrans())
				{
					$this->db->CompleteTrans();
					return false;
				}
			$this->db->CompleteTrans();//commit
			return true;
		}

	}
        
	public function getVoucherStatus($vouchers,$statusArray=null)
	{       
                $log_obj = new Logging();
		
		if($statusArray!=null)
		{	$order_states="";
			foreach($statusArray as $status)
			{
				$order_states.= "$status,";
			}
			$order_states=trim($order_states,",");
			
			$condition = "state_id in ($order_states) or";
		}
                
		$table = $this->createGTempTable('temp_vouch_status');
		foreach($vouchers as $voucher)
                {   
                        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." inseting into $table Voucher: $voucher before UpdateVouch Status");
   			$GLOBALS['_DB']->DBlink->Execute("insert into $table (voucher_serial) values ('".$voucher."')");
                }               
               
                
                $query = "	SELECT * from device_t d left outer join device_voucher_t dvt on d.poid_id0 = dvt.obj_id0
                                WHERE poid_id0 in (select * from $table) 
                                and ( $condition
                                      (((sysdate - to_date('01-JAN-1970','DD-MON-YYYY')) * (86400)-18000) > expiration)
                                    )";
                
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." before executing $query");                
		$data = $GLOBALS['_DB']->CustomQuery($query);
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." Executed! $query");
                
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." Droping Global Table $table");
		$this->dropGTempTable($table);
		$result=null;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." got voucher Status from Table $table");
		foreach($data as $row)
		{
			$result[$row['poid_id0']] = array('device_id' => $row['device_id'], 'status' => $row['state_id']);
		}
		return $result;
		
	}
        
	private function createGTempTable($table)
	{
		$table = $table.'_'.time();
		$query="select count(*) as t from tab where tname='$table'";
   		$data=$GLOBALS['_DB']->CustomQuery($query);
   		
   		if($data[0]['t']>=1)
   		{
   			$GLOBALS['_DB']->DBlink->Execute("truncate table $table");#drop temp table
   			$GLOBALS['_DB']->DBlink->Execute("drop table $table");#drop temp table
   		}
		
		$query="create global temporary table $table
				(voucher_serial varchar2(20))
				on commit preserve rows";
		$GLOBALS['_DB']->DBlink->Execute($query);
		return $table;
	}
	
	private function dropGTempTable($table)
	{
		$GLOBALS['_DB']->DBlink->Execute("truncate table $table");
   		$GLOBALS['_DB']->DBlink->Execute("drop table $table");#drop temp table
		
		return true;
	}

        public function MarkVoucherLost($vouchers)
	{
                    $this->db->StartTrans();
			foreach($vouchers as $poid_id0)
			{
				$query = "update device_t set state_id = ".VOUCHER_LOST." where poid_id0 ='$poid_id0'";
				$this->db->Execute($query);
			}
                      if ($this->db->HasFailedTrans())
				{
					$this->db->CompleteTrans();
					return false;
				}
			$this->db->CompleteTrans();//commit
			return true;
        }
	
	
	
}

?>