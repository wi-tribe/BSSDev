<?

/*************************************************
* Written by Saad Bashir Dar
* imsaady@gmail.com
* sales rep class, authentication based on
* Oracle BRM /SugarCRM
**************************************************/

include_once(UTIL_CLASSDIR."/encryption.php");
include('../lib/adodb/adodb.inc.php');
include_once(CLASSDIR."/DBAccess.php");

define( SALE_USERS,'SALESPERSONNEL_DETAILS');
define( SALES_HIERARCHY,'SALES_HIERARCHY');
define( CUSTOMER_PAYABLES_CURRENT,'CUSTOMER_PAYABLES');
define( CUSTOMER_PAYABLES_CALLS,'CUSTOMER_PAYABLES_CALLS');
define( CUSTOMER_PAYMENT,'BRM_CUSTOMER_PAYMENTS');

class SalesPersonnel
{
	private $db;
	private $_table;
	private $conf;
	public $error;

	public function __construct()
	{
            $tns_name = "(DESCRIPTION =
                        (ADDRESS_LIST =
                            (ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.67.201)(PORT = 1522))
                        )
                        (CONNECT_DATA =
                           (SERVICE_NAME = pindb)
                        )
                      )";
             
            $this->db = &ADONewConnection("oci8");
            $this->db->PConnect($tns_name , 'smarts2' , 'smarts321');
	}

	public function __destruct()
	{
            $this->db->Disconnect();
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
              if($_SESSION['brm_csr_id']!=null)
              {
                  $sales_rep['csr_id'] = $_SESSION['brm_csr_id'];
              }
              else
              {
                  $sales_rep['csr_username'] = $_SESSION['username'];
              }
		return $this->getSalesRep($sales_rep);
	}

	public function getSalesRep($sales_rep)
	{
		$condition="1";
		if(isset($sales_rep['csr_id']))
		{
			$condition = "salespersonnel_id='".$sales_rep['csr_id']."'";
		}
		else if(isset($sales_rep['csr_username']))
		{
			$condition = "lower(userid)= lower('".$sales_rep['csr_username']."')";
		}

		$query =	"SELECT * ".
					" FROM ".SALE_USERS.
					" left JOIN ". SALES_HIERARCHY.
					" ON ".SALE_USERS.".salespersonnel_id = ".SALES_HIERARCHY.".child_salespersonnel_id".
					" WHERE $condition";

		$user_set= $this->db->Execute($query);
		$user = $user_set->FetchRow();
	//	print_r($user);
		return $user;
	}

	public function getSalesByShop($shop_id,$filter=null)
	{
                $condition = "SHOP_ID = '$shop_id'";
                if($filter['SearchOption']=='CustomerID')
                {
                    $condition.= " and customer_payables.CUSTOMER_ID = '".$filter['SearchText']."' ";
                }

                if($filter['SearchOption']=='CustomerName')
                {
                    $Name = split(' ',$filter['SearchText']);
                    $condition.= " and customer_payables.FIRST_NAME like '".$Name[0]."' or customer_payables.LAST_NAME like '".$Name[1]."'";
                }

                if($filter['SearchOption']=='TimesCalled')
                {
                    $condition.= " and nvl2(CALLS.TIMES_CALLED,CALLS.TIMES_CALLED,0) = ".$filter['SearchText']." ";
                }

                if($filter['SearchOption']=='DueAmnt')
                {
                    $condition.= " and (
                                         ceil(customer_payables.TOTAL_DUE) = ceil('".$filter['SearchText']."')
                                             or
                                          floor(customer_payables.TOTAL_DUE) = floor('".$filter['SearchText']."')
                                            )";
                }

                if($filter['SearchOption']=='LastCalledOn')
                {
                    $condition.= " and TO_DATE(LAST_CALLED_ON,'YYYY-MM-DD') = TO_DATE('".$filter['SearchText']."','YYYY-MM-DD')";
                }

                 if($filter['SearchOption']=='LastReasonCode')
                {
                    $condition.= " and LAST_REASON_CODE = '".$filter['LastReasonCode']."' ";
                }

                if($filter['SearchOption']=='PaymentStatus')
                {
                    $condition.= " and customer_payables.paid = '".$filter['paymentstatus']."' ";
                }

                if($filter['SearchOption']=='CustomerProfile')
                {
                    $condition.= " and customer_payables.customer_profile = '".$filter['c_profile']."' ";
                }
		
		
		$billing_cycle = date('Ym');

		$query = "select decode(calls.times_called,null,0,calls.times_called)times_called,
                decode(calls.last_called_on,null,'-',calls.last_called_on) last_called_on,
                decode(paid,1,'Paid','Unpaid') Payment_status,salespersonnel_details.full_name as previous_se,
                customer_payables.*
                from customer_payables
		left outer join
		(select rec_id, count(*) times_called, max(datetime) last_called_on  from customer_payables_calls group by rec_id) calls
		 on calls.rec_id = customer_payables.rec_id
                left outer join salespersonnel_details
                on salespersonnel_details.salespersonnel_id = customer_payables.allocated_se
		 where $condition and TOTAL_DUE >200
		 and billing_cycle = $billing_cycle and CUSTOMER_STATUS not in ('Closed')
		  order by payment_status desc,date_created asc , total_due desc";

		//print $query; exit();
	/*	$query =	"SELECT * ".
					" FROM ".CUSTOMER_PAYABLES_CURRENT.
					" WHERE $condition".
					" ORDER BY total_due desc";*/
            $sales_rec= $this->db->Execute($query);


		//print_r($_sales_rec);
		$shop_sales =null;
		$sale_count=0;
		while ( $sale = $sales_rec->FetchRow())
		{

			$shop_sales[$sale_count]=$sale;
			$sale_count++;

		}
		return $shop_sales;
	}

     //added by Fahad Pervaiz for seraching customer with NIC
     public function getCustomerByNIC($nic)
	{
            if(empty($nic))
                           return false;

		$query = "	select vu_cust.*,billinfo_t.pending_recv due_now from vu_cust
					inner join billinfo_t on  vu_cust.customer_id = billinfo_t.account_obj_id0
					where identification_number='$nic'
					";

		$sales_rec= $this->db->Execute($query);

		//print_r($_sales_rec);

		$cus_count=0;
		while ( $acct = $sales_rec->FetchRow())
		{

			$cus_acct[$cus_count]=$acct;
			$cus_count++;

		}
		return $cus_acct;


	}

	public function getCustomerInfo($acct_id)
	{
		 $condition ='';

            if($acct_id!=null )
			$condition.=  " where customer_id = '".$acct_id."'";

            $query = "	select vu_cust.*,billinfo_t.pending_recv due_now,pvs.* from vu_cust
						inner join billinfo_t on  vu_cust.customer_id = billinfo_t.account_obj_id0
						left outer join vu_plan_speed_volume pvs on pvs.poid_id0 = vu_cust.customer_id 
						$condition
					";
		//	print $query;
			
		$sales_rec= $this->db->Execute($query);

		//print_r($_sales_rec);

		$cus_count=0;
		while ( $acct = $sales_rec->FetchRow())
		{

			$cus_acct[$cus_count]=$acct;
			$cus_count++;

		}
		
		return $cus_acct;
	
	}
        //added by Aasim Naveed for seraching customer with Tele/Mobile No
     public function getCustomerByNumber($Customerno)
	{
            $condition ='';

            if($Customerno!=null )
			$condition.=  " where (telephone_number = '".$Customerno."' or mobile_number = '".$Customerno."')";

            $query = "	select vu_cust.*,billinfo_t.pending_recv due_now from vu_cust
					inner join billinfo_t on  vu_cust.customer_id = billinfo_t.account_obj_id0
					 $condition
					";

		$sales_rec= $this->db->Execute($query);

		//print_r($_sales_rec);

		$cus_count=0;
		while ( $acct = $sales_rec->FetchRow())
		{

			$cus_acct[$cus_count]=$acct;
			$cus_count++;

		}
		return $cus_acct;


	}
        
        //
         public function getDeviceInfoByAccNo($Customerno)
	{
            $condition ='';

            if($Customerno!=null )
			$condition.=  " where customer_id = $Customerno ";

            $query = "	select * from vu_macs $condition ";

            $sales_rec= $this->db->Execute($query);

		$cus_count=0;
		while ( $acct = $sales_rec->FetchRow())
		{

			$cus_acct[$cus_count]=$acct;
			$cus_count++;

		}
		return $cus_acct;
	}
        
        public function getCustomerVolumeDetails($AcctNo)
        {
//            $tns_name = "(DESCRIPTION =
//                        (ADDRESS_LIST =
//                            (ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.67.33)(PORT = 1521))
//                        )
//                        (CONNECT_DATA =
//                           (SERVICE_NAME = bsspindb)
//                        )
//                      )";
            $tns = "(DESCRIPTION =
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
            
                $pinDb = &ADONewConnection("oci8");
                $pinDb->Connect($tns, 'pinclone', 'clonebygg');

                $query = "select max(stop_time) as lastupdate,SUM(upload) +SUM(download) total_volume from  DATAUSAGEHISTORY_" . date("mY") . " WHERE customer_id=$AcctNo";
        	
                $result = $pinDb->Execute($query);

                $cus_count = 0;
                while ($acct = $result->FetchRow()) {
                    $cus_acct[$cus_count] = $acct;
                    $cus_count++;
                }
                $pinDb->Disconnect();
                return $cus_acct;
        }
        
	//added by Aasim Naveed, returns Executives against a shop
        public function getMyExecutives($shop_id)
        {
            $query = "select distinct(CHILD_SALESPERSONNEL_ID),full_name from SALES_HIERARCHY
                     inner join SALESPERSONNEL_DETAILS
                     on child_salespersonnel_id = salespersonnel_id
                     where SHOP_ID = '".$shop_id."'
                    and salespersonnel_details.salespersonnel_type = 4 and channel_type=2";

            $result = $this->db->Execute($query);
            $executive_count=0;
            while ( $executive = $result->FetchRow())
		{
			$executives[$executive_count]=$executive;
			$executive_count++;
		}
            return $executives;
        }

        //added by Aasim Naveed,allocates calls to sales executives
        public function allocateCallsToExecutives($executive,$customers)
        {
            $billing_cycle = date('Ym');
            foreach($customers as $arr)
            {
                $query =	" UPDATE ".CUSTOMER_PAYABLES_CURRENT.
					" SET allocated_se = '$executive'".
					" WHERE customer_id = $arr
                                            and billing_cycle = $billing_cycle";
                
               $user_set= $this->db->Execute($query);

            }
        }

        //added by Aasim Naveed,allocates calls to sales executives
        public function getExecutiveAllocatedCalls($executive)
        {
            $billing_cycle = date('Ym');
            $query = "select decode(calls.times_called,null,0,calls.times_called)times_called,
                decode(calls.last_called_on,null,'-',calls.last_called_on) last_called_on,
                decode(paid,1,'Paid','Unpaid') Payment_status,salespersonnel_details.full_name as previous_se,
                customer_payables.*
                from customer_payables
		left outer join
		(select rec_id, count(*) times_called, max(datetime) last_called_on  from customer_payables_calls group by rec_id) calls
		 on calls.rec_id = customer_payables.rec_id
                left outer join salespersonnel_details
                on salespersonnel_details.salespersonnel_id = customer_payables.allocated_se
		 where TOTAL_DUE >200
		 and BILLING_CYCLE = '$billing_cycle'
                and allocated_se = $executive
		  order by payment_status desc,date_created asc , total_due desc";

            $result = $this->db->Execute($query);
            $executive_count=0;
            while ( $executive = $result->FetchRow())
		{
			$executives[$executive_count]=$executive;
			$executive_count++;
		}
            return $executives;
        }

         //added by Aasim Naveed,get calls summary
        public function getcallsummary($filters)
        {
            $start_date = split('/',$filters['start_date']);
            $start_date[1] = date("m", mktime(0, 0, 0, $start_date[1]));
            $start_date = $start_date[0].$start_date[1];

            $end_date = split('/',$filters['end_date']);
            $end_date[1] = date("m", mktime(0, 0, 0, $end_date[1]));
            $end_date = $end_date[0].$end_date[1];

            $billing_cycle = date('Ym');
            $condition = "";
		if($filters['region']!="ALL" and $filters['region']!=null )
                {
                    if($filters['region']=='Islamabad')
                    {
                        $condition =  " and SHOP_ID like '%ISB%' ";
                    }
                    if($filters['region']=='Rawalpindi')
                    {
                        $condition =  " and SHOP_ID like '%RWP%' ";
                    }
                    if($filters['region']=='Lahore')
                    {
                        $condition =  " and SHOP_ID like '%LHR%' ";
                    }
                    if($filters['region']=='Karachi')
                    {
                        $condition =  " and SHOP_ID like '%KHI%' ";
                    }
                    if($filters['region']=='Faisalabad')
                    {
                        $condition =  " and SHOP_ID like '%FSD%' ";
                    }
                }

		if($filters['channel']!="ALL" and $filters['channel']!=null )
			$condition = " and SHOP_ID = '".$filters['channel']."' ";
		if($filters['user']!="ALL" and $filters['user']!=null)
                {
                    $SALES_REP = " and SALESPERSONNEL_ID = '".$filters['user']."' ";
                    $ALLOCATED_SE = " and allocated_se = '".$filters['user']."'";
                    }

             $query = "select TOTAL_CALLS,CONNECTED,NOTCONNECTED,TRUNC( (CONNECTED/TOTAL_CALLS*100) , 2 ) as CONNECTIVITY,
                        PAID,TRUNC(AVG_CALLS_PER_CC,0) as AVG_CALLS_PER_CC,CALLS.SHOP_ID,BILLING_CYCLE,CPROFILE
                        from (select CP.BILLING_CYCLE,COUNT(*) as TOTAL_CALLS ,cp.shop_id,
                              COUNT(DECODE(REASON_CODE, 'Will Pay - Themselves','Will Pay - Themselves' ,
                              'Will not pay - Sales Return Request','Will not pay - Sales Return Request' ,
                              'Out of Station','Out of Station' ,'Did not use - Waiver Request',
                              'Did not use - Waiver Request' ,'Safe Custody / Freeze Account','Safe Custody / Freeze Account' ,
                              'Incorrect customer details','Incorrect customer details' ,'Device already returned','Device already returned' ,
                              'Troubleshooting request - Waiver','Troubleshooting request - Waiver' ,
                              'Fake Sales','Fake Sales' ,
                              'Will Pay - Home Collection ','Will Pay - Home Collection' ,
                              'Troubleshooting request','Troubleshooting request' )) as CONNECTED ,
                                COUNT(DECODE(REASON_CODE,'Customer not available','Customer not available'
                            ,'Call not contacted/busy/switched off','Call not contacted/busy/switched off')) as NOTCONNECTED
                            ,CP.CUSTOMER_PROFILE AS CPROFILE
                            from CUSTOMER_PAYABLES_CALLS CPC
                            left outer join CUSTOMER_PAYABLES CP on CP.CUSTOMER_ID = CPC.CUSTOMER_ID
                            where TO_DATE(DATETIME,'YYYY/MM/DD') between TO_DATE('".$filters['start_date']."','YYYY/MM/DD')
                            and TO_DATE('".$filters['end_date']."','YYYY/MM/DD') $condition $SALES_REP
                            group by CP.BILLING_CYCLE, cp.shop_id,CP.CUSTOMER_PROFILE ) CALLS

                            inner join

                            (select count(decode(paid,1,paid)) as PAID,BILLING_CYCLE,cp.shop_id as shop,CUSTOMER_PROFILE
                            from CUSTOMER_PAYABLES CP
                              where CP.BILLING_CYCLE in ('$start_date','$end_date') $condition 
                              AND CUSTOMER_PROFILE IS NOT NULL
                              group by BILLING_CYCLE, cp.shop_id,CUSTOMER_PROFILE) PAID

                                on CALLS.BILLING_CYCLE = PAID.BILLING_CYCLE and calls.shop_id = shop AND CALLS.CPROFILE = PAID.CUSTOMER_PROFILE

                              inner join

                              (select BILLING_CYCLE, AVG(TOTAL_CALLS) as AVG_CALLS_PER_CC,SHOP_ID,CUSTOMER_PROFILE
                              from (select CP.BILLING_CYCLE as BILLING_CYCLE,CP.SHOP_ID,COUNT(*) as TOTAL_CALLS,CUSTOMER_PROFILE
                              from CUSTOMER_PAYABLES_CALLS CPC
                              left outer join CUSTOMER_PAYABLES CP on CP.CUSTOMER_ID = CPC.CUSTOMER_ID
                              where TO_DATE(DATETIME,'YYYY/MM/DD') between TO_DATE('".$filters['start_date']."','YYYY/MM/DD')
                                and TO_DATE('".$filters['end_date']."','YYYY/MM/DD')
                                AND CUSTOMER_PROFILE IS NOT NULL
                              group by CP.BILLING_CYCLE, CP.SHOP_ID,CUSTOMER_PROFILE)
                              group by BILLING_CYCLE, SHOP_ID,CUSTOMER_PROFILE) AVG

                              on avg.billing_cycle = calls.billing_cycle and avg.shop_id = calls.shop_id AND AVG.CUSTOMER_PROFILE = CALLS.CPROFILE";

             $newQuery = "select nvl(total_calls,0) as attempted,nvl(connected,0) as connected,nvl(notconnected,0) as notconnected,round(nvl((connected/total_calls)*100,0),0) as connectivity,
                        allocated_se,paid_status.shop,paid_status.total_allocated_calls,paid,unpaid
                        ,round(NVL( ((paid/total_allocated_calls)*100) ,0),0) as paidratio
                        ,case
                                                                   when instr(shop,'ISB') > 0 then 'Islamabad'
                                                                   when instr(shop,'RWP') > 0 then 'Rawalpindi'
                                                                   when instr(shop,'LHR') > 0 then 'Lahore'
                                                                   when instr(shop,'KHI') > 0 then 'Karachi'
                                                                   when instr(shop,'FSD') > 0 then 'Faisalabad'
                                                                    else '--'
                                                                    END region
                         ,paid_status.BILLING_CYCLE,full_name

                        from
                            (select nvl(count(*),0) as total_calls,salespersonnel_id,p.shop_id
                            ,COUNT(DECODE(REASON_CODE, 'Will Pay - Themselves','Will Pay - Themselves' ,
                              'Will not pay - Sales Return Request','Will not pay - Sales Return Request' ,
                              'Out of Station','Out of Station' ,'Did not use - Waiver Request',
                              'Did not use - Waiver Request' ,'Safe Custody / Freeze Account','Safe Custody / Freeze Account' ,
                              'Incorrect customer details','Incorrect customer details' ,'Device already returned','Device already returned' ,
                              'Troubleshooting request - Waiver','Troubleshooting request - Waiver' ,
                              'Fake Sales','Fake Sales' ,
                              'Will Pay - Home Collection ','Will Pay - Home Collection' ,
                              'Troubleshooting request','Troubleshooting request' )) as CONNECTED ,
                                COUNT(DECODE(REASON_CODE,'Customer not available','Customer not available'
                            ,'Call not contacted/busy/switched off','Call not contacted/busy/switched off')) as NOTCONNECTED
                                from customer_payables_calls c
                                inner join customer_payables p
                                on c.customer_id = p.customer_id
                                where TO_DATE(DATETIME,'YYYY/MM/DD') between TO_DATE('".$filters['start_date']."','YYYY/MM/DD')
                                and TO_DATE('".$filters['end_date']."','YYYY/MM/DD') and billing_cycle = to_char(TO_DATE('".$filters['start_date']."','YYYY/MM/DD'),'YYYYMM')
                                    $condition $SALES_REP
                                group by salespersonnel_id,p.shop_id
                                ) retention_effort

                                right outer join
                                (select count(decode(paid,1,paid)) as PAID,count(decode(paid,0,paid)) as UNPAID,count(*) as total_allocated_calls,BILLING_CYCLE,cp.shop_id as shop,allocated_se
                                        from CUSTOMER_PAYABLES CP
                                    where to_char(TO_DATE('".$filters['start_date']."','YYYY/MM/DD'),'YYYYMM') = billing_cycle and allocated_se is not null
                                        $condition $ALLOCATED_SE
                                      group by BILLING_CYCLE, cp.shop_id,allocated_se) paid_status

                              on paid_status.shop = retention_effort.shop_id and paid_status.allocated_se = retention_effort.salespersonnel_id

                              left outer join salespersonnel_details
                              on paid_status.allocated_se = salespersonnel_details.salespersonnel_id
                              order by shop,allocated_se";
                              //    print($query);
             $result = $this->db->Execute($newQuery);
             $rows=0;
             while ( $datarow = $result->FetchRow())
		{
			$data[$rows]=$datarow;
			$rows++;
		}
            return $data;
        }

	public function getAccount($account)
	{
		$condition = "rec_id=".$account['rec_id'];
		$query =	"SELECT * ".
					" FROM ".CUSTOMER_PAYABLES_CURRENT.
					" WHERE $condition";
		$user_set= $this->db->Execute($query);
		$user = $user_set->FetchRow();
		return $user;
	}

	public function updateCallStatus($call_status)
	{
		$customer_id = $call_status['CUSTOMER_ID'];
		$rec_id = $call_status['REC_ID'];
		$reason_code = $call_status['CALL_REASON_CODE'];
		$salespersonnel_id = $call_status['SALESPERSONNEL_ID'];
                $remarks = $call_status['REMARKS'];
		$query =	"INSERT INTO ".CUSTOMER_PAYABLES_CALLS."(CUSTOMER_ID,REASON_CODE,SALESPERSONNEL_ID,REC_ID,REMARKS)".
					" VALUES($customer_id,'$reason_code',$salespersonnel_id,$rec_id,'$remarks') ";

		$user_set= $this->db->Execute($query);

		$query =	"UPDATE ".CUSTOMER_PAYABLES_CURRENT.
					" SET LAST_REASON_CODE = '$reason_code'".
					" WHERE customer_id = $customer_id";
		$user_set= $this->db->Execute($query);

	}

	public function getCalls($call)
	{
		$condition = "rec_id = ".$call['rec_id'];

		$query =	"SELECT * FROM ".CUSTOMER_PAYABLES_CALLS.
					" WHERE $condition";

		$calls_set= $this->db->Execute($query);
		$calls=null;
		$call_count =0;
		while ( $call = $calls_set->FetchRow())
		{

			$calls[$call_count]=$call;
			$call_count++;

		}
		return $calls;
	}

	public function getSalesBySalesRepID($sales_rep_id,$filter)
	{
		$billing_cycle = date('Ym');
                if($filter['SearchOption']=='CustomerID')
                {
                    $condition.= " and customer_payables.CUSTOMER_ID = '".$filter['SearchText']."' ";
                }

                if($filter['SearchOption']=='CustomerName')
                {
                    $Name = split(' ',$filter['SearchText']);
                    $condition.= " and customer_payables.FIRST_NAME like '".$Name[0]."' and customer_payables.LAST_NAME like '".$Name[1]."'";
                }

                if($filter['SearchOption']=='DueAmnt')
                {
                    $condition.= " and (
                                         ceil(customer_payables.TOTAL_DUE) = ceil('".$filter['SearchText']."')
                                             or
                                          floor(customer_payables.TOTAL_DUE) = floor('".$filter['SearchText']."')
                                            )";
                }

                if($filter['SearchOption']=='LastCalledOn')
                {
                    $condition.= " and TO_DATE(LAST_CALLED_ON,'YYYY-MM-DD') = TO_DATE('".$filter['SearchText']."','YYYY-MM-DD')";
                }

                 if($filter['SearchOption']=='LastReasonCode')
                {
                    $condition.= " and LAST_REASON_CODE = '".$filter['LastReasonCode']."' ";
                }

                if($filter['SearchOption']=='PaymentStatus')
                {
                    $condition.= " and customer_payables.paid = '".$filter['paymentstatus']."' ";
                }

                 if($filter['SearchOption']=='Promo')
                {
                    $condition.= " and (customer_payables.promo1 like '".$filter['Promos']."' or customer_payables.promo2 like '".$filter['Promos']."')";
                }
		
		  if($filter['SearchOption']=='TimesCalled')
                {
                    $condition.= " and calls.times_called=  '".$filter['SearchText']."' ";
                }

		$query = "select decode(calls.times_called,null,0,calls.times_called)times_called, decode(calls.last_called_on,null,'-',calls.last_called_on) last_called_on,
                            decode(paid,1,'Paid','Unpaid') Payment_status,
                            CUSTOMER_PAYABLES.* from CUSTOMER_PAYABLES
                            left outer join (select REC_ID, COUNT(*) TIMES_CALLED, max(DATETIME) LAST_CALLED_ON from CUSTOMER_PAYABLES_CALLS group by REC_ID) CALLS
                            on CALLS.REC_ID = CUSTOMER_PAYABLES.REC_ID
                            where allocated_se = '$sales_rep_id'
                            and TOTAL_DUE >200 and paid!=1
                            and billing_cycle = $billing_cycle $condition
                            order by payment_status desc,date_created asc , total_due desc ";

		$sales_rec= $this->db->Execute($query);

		$shop_sales =null;
		$sale_count=0;
		while ( $sale = $sales_rec->FetchRow())
		{
			$shop_sales[$sale_count]=$sale;
			$sale_count++;
		}
		return $shop_sales;
	}


    public function getRegionCCShops($region)
    {
        $query="select * from SHOP_DETAILS
                where lower(ADDR_CITY) like lower('%$region%') and LOWER(category) like LOWER('$region')
                and ADDR_CITY not like '%SIS%'
                and shop_id like '%-S-%'";

        $ccshops = $this->db->Execute($query);

		$shops=null;
		$shops_count =0;
		while ( $shop = $ccshops->FetchRow())
		{
			$shops[$shops_count]=$shop;
			$shops_count++;
		}
		return $shops;
    }

    public function getShopExecutives($shop)
    {
        $query="select * from SALESPERSONNEL_DETAILS
                                inner join SALES_HIERARCHY
                                on CHILD_SALESPERSONNEL_ID = SALESPERSONNEL_ID
                                where SHOP_ID = '$shop' and salespersonnel_type = 4
                                and channel_type = 2
                                order by full_name";

        $ccExecutives = $this->db->Execute($query);

		$executives=null;
		$executive_count =0;
		while ( $executive = $ccExecutives->FetchRow())
		{
			$executives[$executive_count]=$executive;
			$executive_count++;
		}
		return $executives;
    }

    public function getCustomerPayable($filter)
    {
        $condition='';
                if($filter['searchOption']=='CustomerID')
                {
                    $condition = " and VU_CUST.customer_id= '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='CustomerName')
                {
                    $Name = split(' ',$filter['SearchTxt']);
                    if($Name[0]!=null && $Name[1]!=null)
                    {
                        $condition = " and lower(VU_CUST.FIRST_NAME) like lower('".$Name[0]."') and lower(VU_CUST.LAST_NAME) like lower('".$Name[1]."')";
                    }
                    else
                        if($Name[0]!=null)
                        {
                            $condition = " and (lower(VU_CUST.FIRST_NAME) like lower('".$Name[0]."') or lower(VU_CUST.LAST_NAME) like lower('".$Name[0]."'))";
                        }
                }

                if($filter['searchOption']=='CustomerNo')
                {
                    $condition = " and VU_CUST.telephone_number = '".$filter['SearchTxt']."' or mobile_number = '".$filter['SearchTxt']."'";
                }

                if($filter['searchOption']=='CustomerCNIC')
                {
                    $condition = " and VU_CUST.identification_number = '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='CustomerEmail')
                {
                    $condition = " and VU_CUST.email_address like '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='MAC')
                {
                    $condition = " and vu_macs.cpe_mac_address = '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='SerialID')
                {
                    $condition = " and vu_macs.cpe_device_id = '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='Username')
                {
                    $condition = " and service_t.login = '".$filter['SearchTxt']."'";
                }

		$query = "select VU_CUST.*,BILLINFO_T.PENDING_RECV+nvl((Select amount from LFee where account_obj_id0 = VU_CUST.Customer_Id and trunc(sysdate) <= to_date('06/30/2011','mm/dd/yyyy')),0)
                        as DUE_NOW,vu_macs.cpe_mac_address,vu_macs.cpe_device_id as serial_no,service_t.login from VU_CUST
				inner join BILLINFO_T on  VU_CUST.CUSTOMER_ID = BILLINFO_T.ACCOUNT_OBJ_ID0
                                      left outer join VU_MACS on VU_MACS.CUSTOMER_ID = VU_CUST.CUSTOMER_ID
                                      left outer join SERVICE_T on SERVICE_T.ACCOUNT_OBJ_ID0 = VU_CUST.CUSTOMER_ID
                                      where customer_status not in ( 'Closed') $condition";
                                     
                $customer_recs= $this->db->Execute($query);

		$customer_array =null;
		$customer_count=0;
		while ( $customer = $customer_recs->FetchRow())
		{
			$customer_array[$customer_count]=$customer;
			$customer_count++;
		}
		return $customer_array;
    }

    public function getCachedCustomerPayables($filter)
    {
         $condition='';
                if($filter['searchOption']=='CustomerID')
                {
                    $condition = " and customer_id= '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='CustomerName')
                {
                    $Name = split(' ',$filter['SearchTxt']);
                    if($Name[0]!=null && $Name[1]!=null)
                    {
                        $condition = " and lower(FIRST_NAME) like lower('".$Name[0]."') and lower(LAST_NAME) like lower('".$Name[1]."')";
                    }
                    else
                        if($Name[0]!=null)
                        {
                            $condition = " and (lower(FIRST_NAME) like lower('".$Name[0]."') or lower(LAST_NAME) like lower('".$Name[0]."'))";
                        }
                }

                if($filter['searchOption']=='CustomerNo')
                {
                    $condition = " and telephone_number = '".$filter['SearchTxt']."' or mobile_number = '".$filter['SearchTxt']."'";
                }

                if($filter['searchOption']=='CustomerCNIC')
                {
                    $condition = " and identification_number = '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='CustomerEmail')
                {
                    $condition = " and email_address like '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='MAC')
                {
                    $condition = " and cpe_mac_address = '".$filter['SearchTxt']."' ";
                }

                if($filter['searchOption']=='SerialID')
                {
                    return $customer_array;
                }

                if($filter['searchOption']=='Username')
                {
                    $condition = " and login = '".$filter['SearchTxt']."'";
                }

		$query = "select * from 
                (
                   select customer_id,first_name,last_name,address,region_name,identification_number,telephone_number
                          ,mobile_number,email_address,customer_status,date_created,business_type,login,total_due as due_now
                          ,cpe_mac_address
                          from brm_activations 
                          where customer_status not in ( 'Closed') $condition) where rownum <=300";
                
                $customer_recs= $this->db->Execute($query);

		$customer_array =null;
		$customer_count=0;
		while ( $customer = $customer_recs->FetchRow())
		{
			$customer_array[$customer_count]=$customer;
			$customer_count++;
		}
		return $customer_array;
        
    }
    public function InsertCustomerPayment($values)
    {
                $db_con = new DBAccess();
		$customer_id = $values['customerid'];
		$sales_rep_id = $values['sales_rep_id'];
                $shop_id = $values['shop_id'];
                $amount_paid = $values['amount_paid'];
                $due_amount = $values['amount_due'];
                $payment_desc = $values['payment_desc'];

                $insert = "CUSTOMER_ID,SALES_REP_ID,SHOP_ID,AMOUNT_PAID,PAYMENT_DATE,PAYMENT_DESC,DUE_AMOUNT";
                $vals="'".$customer_id."' , '".$sales_rep_id."', '".$shop_id."', '$amount_paid' ,CURRENT_DATE, '".$payment_desc."', $due_amount";

               if(($result = $db_con->InsertRecord(CUSTOMER_PAYMENT,$insert,$vals)))
               {
                    return $result;
               }

               return false;
    }

    public function getCustomerPayments($filter,$JOIN_VU_CUST=false)
    {
        if($filter['customerid']!=null)
        {
            $condition = " where customer_id = ".$filter['customerid']."";
        }
        if($filter['shop_id']!=null)
        {
            $condition.= " and shop_id = '".$filter['shop_id']."'";
        }

        if($filter['sales_rep_id']!=null)
        {
            $condition.= " and sales_rep_id = ".$filter['sales_rep_id']."";
        }
        if($JOIN_VU_CUST==TRUE)
        {
            $columns = ",addr_city";
            $joincondition = " inner join salespersonnel_details on BRM_CUSTOMER_PAYMENTS.sales_rep_id = salespersonnel_details.salespersonnel_id";
        }

        $query = "select  BRM_CUSTOMER_PAYMENTS.* $columns  from BRM_CUSTOMER_PAYMENTS
                    $joincondition
                    where payment_date in (select max(PAYMENT_DATE) from BRM_CUSTOMER_PAYMENTS $condition) 
                    and customer_id = ".$filter['customerid']." ";
         
                $customer_recs= $this->db->Execute($query);

		$customer_array =null;
		$customer_count=0;
		while ( $customer = $customer_recs->FetchRow())
		{
			$customer_array[$customer_count]=$customer;
			$customer_count++;
		}
		return $customer_array;
    }

    public function insertReasonCode_WillNotPay_Dunning($accontInfo,$formValues)
    {
                $db_con = new DBAccess();
		$customer_id = $accontInfo['CUSTOMER_ID'];
		$sales_rep_id = $formValues['salespersonnel_id'];
                $reason_code = $formValues['reason_code'];
                $rec_id = $formValues['rec_id'];
                $region = $formValues['region'];
                $shop_id = $formValues['shop_id'];
                $insert = "CUSTOMER_ID,SALES_REP_ID,ACTION_DATE,REC_ID,REASON_CODE,REGION,SHOP_ID";
                $vals="'".$customer_id."' , '".$sales_rep_id."', CURRENT_DATE, '".$rec_id."', '".$reason_code."','".$region."','".$shop_id."'";

               if(($result = $db_con->InsertRecord(DUN_SALES_RETURN_CUSTOMERS,$insert,$vals)))
               {
                    return $result;
               }

               return false;
    }

    public function get_SalesReturnReq_Customers($start_date,$end_date,$filters)
    {
        $condition='';
        if($filters['region']!='ALL' && $filters['region']!=null)
        {
            $condition = " and lower(NVL(DSC.REGION,SD.addr_city)) like lower('%".$filters['region']."%')";
        }
        if($filters['channel']!='ALL' && $filters['channel']!=null)
        {
            $condition.= " and NVL(DSC.SHOP_ID,SH.SHOP_ID) = '".$filters['channel']."'";
        }

        $query = "select DUN_ID,CUSTOMER_ID,SALES_REP_ID,ACTION_DATE,REC_ID,REASON_CODE,NVL(DSC.REGION,SD.addr_city) as REGION,NVL(DSC.SHOP_ID,SH.SHOP_ID) as SHOP_ID
                          from DUN_SALES_RETURN_CUSTOMERS DSC
                         left outer join SALESPERSONNEL_DETAILS  SD on DSC.SALES_REP_ID = SD.SALESPERSONNEL_ID
                         left join SALES_HIERARCHY SH on SH.CHILD_SALESPERSONNEL_ID = SD.SALESPERSONNEL_ID
                        where TO_DATE(TO_CHAR(ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') >= TO_DATE('$start_date','MM/DD/YYYY')
			and To_date(to_char(action_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= To_date('$end_date','MM/DD/YYYY') $condition";

        $customer_recs = $this->db->Execute($query);

        $customer_array = null;
        $customer_count=0;
        while ( $customer = $customer_recs->FetchRow())
        {
                $customer_array[$customer_count]=$customer;
                $customer_count++;
        }
            return $customer_array;
    }

    public function getCustomerCurrentPayables($customer_id)
    {
         $query = "select BILLINFO_T.PENDING_RECV TOTAL_DUE from BILLINFO_T where
                                      BILLINFO_T.ACCOUNT_OBJ_ID0 = $customer_id";

        $customer_recs = $this->db->Execute($query);

        $customer_array = null;
        $customer_count=0;
        while ( $customer = $customer_recs->FetchRow())
        {
                $customer_array[$customer_count]=$customer;
                $customer_count++;
        }
            return $customer_array;
    }

    public function getCurrentExistingPromos()
    {
        $query = "select  distinct(promo1)as promo from customer_payables where billing_cycle = '".date("Ym")."'
                    and promo1 is not null
                union all
                select  distinct(promo2)as promo from customer_payables where billing_cycle = '".date("Ym")."'
                                    and promo2 is not null ";
        $promo_recs = $this->db->Execute($query);

        $promo_array = null;
        $promo_array_c=0;
        while ( $promo = $promo_recs->FetchRow())
        {
                $promo_array[$promo_array_c]=$promo;
                $promo_array_c++;
        }
         return $promo_array;
    }

    public function getMyPaidUnpaidCustStats($UserID)
    {
        $query = "select count(*) total,count(decode(paid,'1','1')) as paid,count(decode(paid,'0','0')) as unpaid from customer_payables
                    where billing_cycle = '".date("Ym")."' and allocated_se =$UserID ";
        $paymentStats = $this->db->Execute($query);

        $paymentStats_array = null;
        $paymentStats_array_c=0;
        while ( $payment = $paymentStats->FetchRow())
        {
                $paymentStats_array[$paymentStats_array_c]=$payment;
                $paymentStats_array_c++;
        }
         return $paymentStats_array;
    }

    public function getCustomerCompleteData($month,$year)
    {
         $query = "select CUSTOMER_ID,FIRST_NAME,LAST_NAME,REGION_NAME,CUSTOMER_STATUS,TELEPHONE_NUMBER,MOBILE_NUMBER,EMAIL_ADDRESS,DATE_CREATED,BUSINESS_TYPE,CPE_MAC_ADDRESS,USAGEMONTH1
                    ,USAGEMONTH2,PACKAGE_ID,PACKAGE_NAME,SALES_REP_ID,SALES_REP_NAME,SHOP_ID,SHOP_NAME,ADDRESS,LAST_COMMENT,PROFILE_ID,PAYTYPE,PARENT_ACCT,TOTAL_DUE
                    ,LAST_STATUS_DATE,PAID,BILLING_CYCLE,DATE_OF_PAYMENT,REC_ID,LAST_REASON_CODE,CUSTOMER_PROFILE,ALLOCATED_SE,PROMO1,PROMO2,NEARESTRETAILSHOP1,NEARESTRETAILSHOP2
                    ,NEARESTRETAILSHOP3
                    from customer_payables
                    where billing_cycle = '".date("Ym")."' ";
          $this->db->SetFetchMode(ADODB_FETCH_ASSOC);

          $paymentStats = $this->db->Execute($query);
        $paymentStats_array = null;
        $paymentStats_array_c=0;
        while ( $payment = $paymentStats->FetchRow())
        {
                $paymentStats_array[$paymentStats_array_c]=$payment;
                $paymentStats_array_c++;
        }
        $this->db->SetFetchMode(ADODB_FETCH_NUM);
         return $paymentStats_array;
    }
    
    /*
     * Author: PKAasimN
     * Date: 16-Jan-2012
     * Function: Getting device inventory current status & movement history
     */
    
    public function getDeviceInvStatus($filters)
    {
        $condition = "";
        if($filters['channel']!=null && $filters['channel']!='ALL')
        {
            $condition.= "where source = '".$filters['channel']."'";
        }
        
        if($filters['user']!=null && $filters['user']!='ALL')
        {
            $condition.= " and salesid = ".$filters['user']."  ";
        }
        
        if($filters['device_status']!=null && $filters['device_status']!='ALL' )
        {
            if($condition=="")
            {
                $condition.= " where  state_id = ".$filters['device_status'];
            }else
            {
                $condition.= " and  state_id = ".$filters['device_status'];
            }
        }
        
        else if($filters['device_wan_mac'] == null && $filters['inventory_id'] == null)
        {
                $condition.= " and state_id in (1,2)";
        }

        if($filters['device_wan_mac']!=null)
        {
            if($condition=="")
            {
                $condition.= " where mac_addr_wan = '".$filters['device_wan_mac']."'";
            }
            else
            {
                $condition.= " and mac_addr_wan = '".$filters['device_wan_mac']."'";
            }
            
        }

        if($filters['inventory_id']!=null)
        {
            if($condition=="") {
                $condition.= " where device_id = '".$filters['inventory_id']."'";
            }
            else {
                $condition.= " and device_id = '".$filters['inventory_id']."'";
            }
        }
         
        $query = " select * from
                   (select poid_id0,device_id,source,manufacturer,model,state_id,decode(dt.state_id,'1','Available at Shop','2','Assigned to CSE','3','Sold','4') as status,
                   salesid,sub_type,mac_addr_wan
                   from device_t dt
                   inner join wtb_cpe_details_t cp on dt.poid_id0 = cp.obj_id0

                    union all

                    select  poid_id0,device_id,source,manufacturer,model,state_id,decode(dt.state_id,'1','Available at Shop','2','Assigned to CSE','3','Sold','4') as status,
                    salesid,sub_type,'' as mac_addr_wan
                    from device_t dt
                    inner join wtb_router_t rt on dt.poid_id0 = rt.obj_id0

                    union all

                    select  poid_id0,device_id,source,manufacturer,model,state_id,decode(dt.state_id,'1','Available at Shop','2','Assigned to CSE','3','Sold','4') as status,
                    salesid,sub_type,'' as mac_addr_wan
                    from device_t dt
                    inner join wtb_device_others_t odv on dt.poid_id0 = odv.obj_id0)
                    $condition
                    order by state_id";
//        print($query);
//        exit();
        $paymentStats = $this->db->Execute($query);

        $paymentStats_array = null;
        $paymentStats_array_c=0;
        while ( $payment = $paymentStats->FetchRow())
        {
                $paymentStats_array[$paymentStats_array_c]=$payment;
                $paymentStats_array_c++;
        }
         return $paymentStats_array;
    }
    
    public function getDeviceAssignmentHistory($inventoryID)
    {
         $query = "select inventory_id,from_shop_id,assignedto ||':'||assignto.full_name as assignedto
                     ,assignedby ||':'||assignee.full_name as assignedby
                     ,assignedfrom ||':'||assignfrom.full_name as assignedfrom
                     ,to_shop_id,TO_CHAR(actiondate, 'DD-MON-YYYY HH24:MI:SS') as actiondate
                     ,decode(device_old_status,'1','Available at Shop','2','New Assigned','3','Assigned Sold','4','TO ERP Damaged','5'
                    ,'Refurbished','6','Damaged','7','Lost','8','TO ERP Transfer','9','Recvd Damaged from ERP') AS DEVICE_OLD_STATUS,decode(device_current_status,'1','Available at Shop','2','New Assigned','3','Assigned Sold','4','TO ERP Damaged','5'
                    ,'Refurbished','6','Damaged','7','Lost','8','TO ERP Transfer','9','Recvd Damaged from ERP') AS DEVICE_NEW_STATUS,DEVICE_MAC
                   from inventory_assignment_tracker 
                   left join salespersonnel_details assignee on assignee.salespersonnel_id = assignedby
                   left join salespersonnel_details assignto on assignto.salespersonnel_id = assignedto
                   left join salespersonnel_details assignfrom on assignfrom.salespersonnel_id = ASSIGNEDFROM
                   where inventory_id = '".$inventoryID."'
                   order by actiondate desc";
                   
        $paymentStats = $this->db->Execute($query);
        
        $paymentStats_array = null;
        $paymentStats_array_c=0;
        while ( $payment = $paymentStats->FetchRow())
        {
                $paymentStats_array[$paymentStats_array_c]=$payment;
                $paymentStats_array_c++;
        }
         return $paymentStats_array;
    }

    public function getShopPersonnels($shop_id)
        {
            $query = "select distinct(CHILD_SALESPERSONNEL_ID),full_name from SALES_HIERARCHY
                     inner join SALESPERSONNEL_DETAILS
                     on child_salespersonnel_id = salespersonnel_id
                     where SHOP_ID = '".$shop_id."'";

            $result = $this->db->Execute($query);
            $executive_count=0;
            while ( $executive = $result->FetchRow())
		{
			$executives[$executive_count]=$executive;
			$executive_count++;
		}
            return $executives;
        }
}
?>
