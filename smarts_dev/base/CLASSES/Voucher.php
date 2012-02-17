<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Voucher
 *
 * @author PKRizwanAI
 * E-mail: rizwan.ali@pk.wi-tribe.com and rizwanali89@gmail.com
 * Voucher class is used to do different operation with voucher for
 * example add,preprocess and getting information related to spacfic voucher
 */

include_once(CLASSDIR."/Tracker.php");
include_once(CLASSDIR."/BRMIntegration.php");

class Voucher {
    //put your code here

    /*
     * Variable declration
     */
   
    public $LastMsg=null;
    private $db;
    private $mlog;
    private $conf;

     /*
     * initialization of variables in constructer
     */
    public function   __construct() {
        $this->db = new DBAccess();
        $this->mlog=new Logging();
        $this->conf=new config();
    }
    
    
    /*
     * get serial number base on the the vouch_id
     */
    public function get_Serial($vouch_id)
    {
       
        if(empty ($_POST['vouch_id'])|| strlen($_POST['vouch_id'] > 11)  )
        {
            $this->LastMsg.="Voucher Id Invalid! <br>";
        }
         if(!empty ($_POST['vouch_id']))
         {
            if(strlen($_POST['vouch_id']<11))
            {
                $_POST['vouch_id'] = (int)$_POST['vouch_id'];
                if(is_int($_POST['vouch_id'])==TRUE)
                {
                 
                    $obj=new DBAccess();
                    $query="SELECT vouch_serial FROM ".$this->db->db_prefix."voucher_information where vouch_id='".$_POST['vouch_id']."'";
                    $result=$this->db->CustomQuery($query);
                   // echo $result[0]['vouch_serial'] ;
                    return $result;
                }
            }
           
        }

        if($this->LastMsg!=null)
			return false;

		return true;
       


    }
    
 	/**
     * @name getVoucherDenominations
     * @return list of denominations
     * @param 
     */
    public function getVoucherDenominations()
    {
    	$query="SELECT voucher_denomination FROM 
	    	".$this->db->db_prefix."item_information 
	    	group by voucher_denomination order by voucher_denomination"
			;
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;              
    }
    
    
    /*
     * get voucher number based on the serial number
     * @return the result after executeing the customquery from DBaccess
     */
    public function getVoucherID($vouch_serial)
    {
    	$query="SELECT voucher_id FROM 
	    	".$this->db->db_prefix."item_information 
	    	where 
	    	voucher_serial='".$vouch_serial."'"
			;
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data[0]['voucher_id'];              
    }
    
    public function getVoucherSerial($vouch_id)
    {
    	$query="SELECT voucher_serial FROM
	    	".$this->db->db_prefix."item_information 
	    	where 
	    	voucher_id=".$vouch_id." ";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data[0]['voucher_serial'];
    }



    /*
     * get voucher price on the base of voucher id or voucher serial number
     * @return the result after executeing the customquery from DBaccess
     */
    public function getTransVouchers($channel_id)
    {
    	$query="select voucher_serial,channel_name as to_channel_id,transfer_type_name,
                u.first_name as action_by,u2.first_name as Assigned_to,action_date
                from ".TRACKER_T." vt
                inner join ".ITEM_INFO_T." vii
                on vt.voucher_id = vii.voucher_id
                inner join ".TRANSFER_TYPE_T." vtt
                on vt.transfer_type_id = vtt.transfer_type_id
                inner join ".CHANNEL_T." vc
                on vt.to_channel_id = vc.channel_id
                inner join ".USER_T." u
                on vt.action_by = u.user_id
                inner join ".USER_T." u2
                on vt.assigned_to = u2.user_id
                where from_channel_id=".$channel_id."
                and transfer_status=2 and end_date in null";
        
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}

    	return $data;
    }
    
    /*
     * get voucher price on the base of voucher id or voucher serial number
     * @return the result after executeing the customquery from DBaccess
     */
    public function getVoucherDenomination($voucher_id)
    {
    	$query="SELECT voucher_denomination FROM 
	    	".$this->db->db_prefix."item_information 
	    	where 
	    	voucher_id='".$voucher_id."'";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data[0]['voucher_denomination']; 
    }

    public function getVouchStatus($vouch_serial)
    {
        if(is_null($vouch_serial)|| empty($vouch_serial))
                {
                    $this->LastMsg.="Please Enter Voucher Serial#";
                    return false;
                }
        $vouch_serial = strtoupper($vouch_serial);
        $query = " SELECT VIMS_ITEM_INFORMATION.VOUCHER_SERIAL, VIMS_ITEM_INFORMATION.VOUCHER_DENOMINATION,
                        To_date(TO_CHAR(VIMS_TRACKER.ACTION_DATE,'YYYY-MM-DD'),'YYYY-MM-DD') as action_date, to_char(VIMS_TRACKER.action_date,'HH24:MI:SS') as action_time,
                        VIMS_CHANNEL.USER_DEFINED_3 as from_location,VIMS_CHANNEL.CHANNEL_NAME as from_channel,Coalesce(VIMS_CHANNEL_TYPE.CHANNEL_TYPE_NAME,'New Inventory') as from_channel_type,
                        TO_VIMS_CHANNEL.USER_DEFINED_3 to_location,TO_VIMS_CHANNEL.CHANNEL_NAME to_channel,coalesce(TO_VIMS_CHANNEL_TYPE.CHANNEL_TYPE_NAME,'Sold To Customer') as to_channel_type,
                        TRACKER_ACTION.FIRST_NAME||' '||TRACKER_ACTION.LAST_NAME as by_user,to_date(to_char(coalesce(VIMS_TRACKER.end_date,current_date),'YYYY-MM-DD'),'YYYY-MM-DD')-to_date(to_char(VIMS_TRACKER.action_date,'YYYY-MM-DD'),'YYYY-MM-DD') as shelf_life,
                        ASSIGNED_TO.FIRST_NAME||' '||ASSIGNED_TO.LAST_NAME as assigned_to,EVENT_T.ACCOUNT_OBJ_ID0 as customer_id,VU_ACT_CUST.FIRST_NAME||' '||VU_ACT_CUST.LAST_NAME as customername,
                        decode(EVENT_T.service_obj_type,'/service/ip','IP','/service/telco/voip','VOIP'),U2S(DEVICE_VOUCHER_T.expiration) as expiration,U2S(VALID_FROM) as usage_date,
                        decode(state_id,1,'Active',2,'Used',3,'Expired',8,'InActive',5,'Stolen',6,'Lost',7,'Damaged') as vouhcer_status
                        FROM
                       VIMS_CHANNEL_TYPE RIGHT OUTER JOIN VIMS_CHANNEL ON (VIMS_CHANNEL.CHANNEL_TYPE_ID=VIMS_CHANNEL_TYPE.CHANNEL_TYPE_ID)
                       RIGHT OUTER JOIN VIMS_TRACKER ON (VIMS_TRACKER.FROM_CHANNEL_ID=VIMS_CHANNEL.CHANNEL_ID)
                       RIGHT OUTER JOIN VIMS_ITEM_INFORMATION ON (VIMS_ITEM_INFORMATION.VOUCHER_ID=VIMS_TRACKER.VOUCHER_ID)
                       RIGHT OUTER JOIN DEVICE_T ON (VIMS_ITEM_INFORMATION.Voucher_serial=DEVICE_T.device_id)
                       LEFT OUTER JOIN EVENT_BILL_PYMT_VOUCHER_T ON (EVENT_BILL_PYMT_VOUCHER_T.device_id = DEVICE_T.device_id)
                       LEFT OUTER JOIN EVENT_T ON (EVENT_BILL_PYMT_VOUCHER_T.OBJ_ID0 = EVENT_T.POID_ID0)
                       LEFT OUTER JOIN VU_ACT_CUST ON (VU_ACT_CUST.CUSTOMER_ID = EVENT_T.ACCOUNT_OBJ_ID0)
                       LEFT OUTER JOIN DEVICE_VOUCHER_T ON (DEVICE_T.poid_id0 =DEVICE_VOUCHER_T.obj_id0)
                       LEFT OUTER JOIN VIMS_CHANNEL  TO_VIMS_CHANNEL ON (VIMS_TRACKER.TO_CHANNEL_ID=TO_VIMS_CHANNEL.CHANNEL_ID)
                       LEFT OUTER JOIN VIMS_CHANNEL_TYPE  TO_VIMS_CHANNEL_TYPE ON (TO_VIMS_CHANNEL.CHANNEL_TYPE_ID=TO_VIMS_CHANNEL_TYPE.CHANNEL_TYPE_ID)
                       LEFT OUTER JOIN USERS  ASSIGNED_TO ON (ASSIGNED_TO.USER_ID=VIMS_TRACKER.ASSIGNED_TO)
                       LEFT OUTER JOIN USERS  TRACKER_ACTION ON (TRACKER_ACTION.USER_ID=VIMS_TRACKER.ACTION_BY)
                        WHERE
                        ( DEVICE_T.device_id = '".$vouch_serial."')
                        order by action_date,action_time";
        
        /*if(($data = $GLOBALS['_DB']->DBlink->Execute($query))!=null)
          {
            return $data;
          }
         * 
         */
        if(($data = $this->db->CustomQuery($query))!=null)
          {
            return $data;
          }
          $this->LastMsg.="Operation failed,could'nt get data";
          return false;
         
    }
   
    /**
     * @access public
     * @name addVoucherRange
     * @param 
     */
    public function addVoucherRange()
    {    	
    	//check required params
    	if(!$this->preProcessVoucherRange())
    		return false;

    	//identify ranges
    	$start=substr($_POST['vouch_serial_start'],getLastChar($_POST['vouch_serial_start'])+1);
    	$end=substr($_POST['vouch_serial_end'],getLastChar($_POST['vouch_serial_end'])+1);
    	$prefix=substr($_POST['vouch_serial_start'],0,getLastChar($_POST['vouch_serial_start'])+1);

    	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nStart Range: $start\r\nEnd Range: $end\r\nPrefix: $prefix");
    	
    	//verifying from BRM and getting voucherIDs
    	for($i=$start; $i<=$end; $i++)
    	{
    		$vouchArr[]=$prefix.$i;
    	}
       
    	$brm=new BRMIntegration();
    	$assVouchArr=$brm->verifyVoucherList($vouchArr);
        if(!$assVouchArr)
         {
            $this->LastMsg = $brm->LastMsg;
            return false;
         }
    	//TODO: validate denomination from BRM  

    	
    	//iterative insert
    	//start a transaction
    	$this->db->DBlink->StartTrans();
    	for($start; $start<=$end; $start++)
    	{    		
    		if(empty($assVouchArr[$prefix.$start]))
    			continue;
    		
    		$insert='voucher_id,voucher_serial,date_added, voucher_denomination';
    		$vals="'".$assVouchArr[$prefix.$start]."','".$prefix.$start."',current_timestamp,'".$_POST['vouch_denomination']."'";
    		
    		$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nAdding Voucher: $start");
    		$this->db->DBlink->Execute("insert into ".$this->conf->db_prefix."item_information ($insert) values ($vals)");
			$vouchID_array[]=$assVouchArr[$prefix.$start];//TODO: get voucher ID from BRM
			
    		/** 
    		 * @deprecated
    		 * if(($data=$this->db->InsertRecord($this->conf->db_prefix."item_information", $insert, $vals))!=null)
    		{
    			$vouchID_array[]=$start;//TODO: get voucher ID from BRM
    			continue;
    		}	
    		$this->LastMsg.=$start." was not added";
    		$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
    		*/
    	}
 		
    	//validate transaction
 		if ($this->db->DBlink->HasFailedTrans())
 		{
    		$this->db->DBlink->CompleteTrans();#rollback
    		$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nStart Range: $start\r\nEnd Range: $end\r\nPrefix: $prefix\r\nTransaction failed");
    		$this->LastMsg="Please contact administrator";
    		return false;
 		}
 		
    	//moving vouchers to wh
    	$tracker=new Tracker();
    	if($tracker->moveInventory(0,$_SESSION['channel_id'],$_SESSION['user_id'],$vouchID_array))
    	{
    		$this->db->DBlink->CompleteTrans();#commit
    		return true;
    	}
    	
    	$this->db->DBlink->FailTrans();
    	$this->db->DBlink->CompleteTrans();#Rollback
    	$this->LastMsg=$tracker->LastMsg;
    	return false;
    	
    }
    
	/**
     * @access public
     * @name preProcessVoucherRange
     * @param 
     */
    public function preProcessVoucherRange()
    {
    	if(is_null($_POST))
    		return false;
    	//general form fields validations
    	if(empty($_POST['vouch_serial_start']) || $_POST['vouch_serial_start']=='')
    		$this->LastMsg="Please provide starting voucher range<br>";
    	else {
    		if(strlen($_POST['vouch_serial_start']) <7)
    			$this->LastMsg.="Starting range seems invalid<br>";
    	}
    	
    	if(empty($_POST['vouch_serial_end']) || $_POST['vouch_serial_end']=='')
    		$this->LastMsg.="Please provide ending voucher range<br>";
    	else {
    		if(strlen($_POST['vouch_serial_end']) <7)
    			$this->LastMsg.="Ending range seems invalid<br>";
    	}
		
    	if(empty($_POST['vouch_denomination']) || $_POST['vouch_denomination']=='')
    		$this->LastMsg.="Please provide face value of vouchers being added<br>";
    	else {
    		if(!is_numeric($_POST['vouch_denomination']))
    			$this->LastMsg.="Face value must be numeric<br>";
    	}
    	
    	//verify that end range is greater than start range
    	if(substr($_POST['vouch_serial_start'],getLastChar($_POST['vouch_serial_start'])+1) >  
    	substr($_POST['vouch_serial_end'],getLastChar($_POST['vouch_serial_end'])+1) )
    	{
    		$this->LastMsg.="Invalid range provided<br>";
    	}
    	
    	//verify range prefixes
   		if(substr($_POST['vouch_serial_start'],0,getLastChar($_POST['vouch_serial_start'])+1) ==  
    	substr($_POST['vouch_serial_end'],0,getLastChar($_POST['vouch_serial_end'])+1) )
    	{
    		//ok
    	} else
    		$this->LastMsg.="Invalid series provided<br>";
    	
    	if($this->LastMsg!=null)
			return false;
		
		return true;
    }
    
    /** 
     * @name countWHVouchers
     * @param 
     * @return mixed Array
     * 
     */
    public function countWHVOuchers()
    {
    	$query="select  voucher_denomination, count(  *  )  as total,channel_name
			from ".$this->db->db_prefix."tracker t inner join ".$this->db->db_prefix."item_information ii 
			on t.voucher_id = ii.voucher_id 
			and end_date is null
			
			inner join ".$this->db->db_prefix."transfer_type ty
			on ty.transfer_type_id=t.transfer_type_id
                         inner join VIMS_CHANNEL
                        on t.to_channel_id = channel_id
			and transfer_type_name in ('Null_WareHouse', 'Shop_WareHouse','Retailer_WareHouse','WareHouse_WareHouse')
			
			group by voucher_denomination,channel_name";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;
    }
    
	/** 
     * @name countMarketVouchers
     * @param 
     * @return mixed Array
     * 
     */
    public function countMarketVouchers()
    {
    	$query="select  voucher_denomination, count(  *  )  as total
			from ".$this->db->db_prefix."tracker t inner join ".$this->db->db_prefix."item_information ii 
			on t.voucher_id = ii.voucher_id 
			and end_date is null
			
			inner join ".$this->db->db_prefix."transfer_type ty
			on ty.transfer_type_id=t.transfer_type_id
			and transfer_type_name in ('WareHouse_Shop', 'Shop_Shop','WareHouse_Retailer','Retailer_Retailer')
			
			group  by voucher_denomination";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;
    }
    
	/** 
     * @name countShopVouchers
     * @param 
     * @return mixed Array
     * 
     */
    public function countShopVouchers()
    {
    	$query="select  channel_name,voucher_denomination, count(  *  )  as total
			from ".$this->db->db_prefix."tracker t inner join ".$this->db->db_prefix."item_information ii 
			on t.voucher_id = ii.voucher_id 
			and end_date is null
			
			inner join ".$this->db->db_prefix."transfer_type ty
			on ty.transfer_type_id=t.transfer_type_id
			and transfer_type_name in ('WareHouse_Shop','Shop_Shop','WareHouse_Retailer','Retailer_Retailer')
			
			left join ".$this->db->db_prefix."channel c
			on c.channel_id=t.to_channel_id
			
			group  by channel_name,voucher_denomination
			order by channel_name,voucher_denomination
			";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;
    }
    
	/** 
     * @name countSoldVouchers
     * @param 
     * @return mixed Array
     * 
     */
    public function countSoldVouchers()
    {
    	$query="select  channel_name,voucher_denomination, count(  *  )  as total
			from ".$this->db->db_prefix."tracker t inner join ".$this->db->db_prefix."item_information ii 
			on t.voucher_id = ii.voucher_id 
			and end_date is null
			
			inner join ".$this->db->db_prefix."transfer_type ty
			on ty.transfer_type_id=t.transfer_type_id
			and transfer_type_name in ('Shop_Sold','WareHouse_Sold','Retailer_Sold')
			
			left join ".$this->db->db_prefix."channel c
			on c.channel_id=t.from_channel_id
			
			group  by channel_name,voucher_denomination
			order by channel_name,voucher_denomination
			";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;
    }
    
	/** 
     * @name todaySales
     * @param 
     * @return mixed Array
     * 
     */
    public function todaySales()
    {
    	
    	$supressor="";
	    if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'vouchersalestats'))
		{
			$supressor=" and from_channel_id='".$_SESSION['channel_id']."' ";
		}
    	
    	$query="select  channel_name,voucher_denomination, count(*)  as total, round(count(*) * voucher_denomination,2) as amt
			from ".$this->db->db_prefix."tracker t inner join ".$this->db->db_prefix."item_information ii 
			on t.voucher_id = ii.voucher_id 
			and end_date is null
			
			inner join ".$this->db->db_prefix."transfer_type ty
			on ty.transfer_type_id=t.transfer_type_id
			and transfer_type_name in ('Shop_Sold','WareHouse_Sold')
			
			left join ".$this->db->db_prefix."channel c
			on c.channel_id=t.from_channel_id
			where to_char(t.action_date,'YYYY-MM-DD') = to_char(current_date,'YYYY-MM-DD')
			$supressor
			group  by channel_name,voucher_denomination
			order by channel_name,voucher_denomination
			";

    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;
    }
    
	/** 
     * @name yesterdaySales
     * @param 
     * @return mixed Array
     * 
     */
    public function yesterdaySales()
    {
    	$supressor="";
	    if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'vouchersalestats'))
		{
			$supressor=" and from_channel_id='".$_SESSION['channel_id']."' ";
		}
		
    	$query="select  channel_name,voucher_denomination, count(*)  as total, round(count(*) * voucher_denomination,2) as amt
			from ".$this->db->db_prefix."tracker t inner join ".$this->db->db_prefix."item_information ii 
			on t.voucher_id = ii.voucher_id 
			and end_date is null
			
			inner join ".$this->db->db_prefix."transfer_type ty
			on ty.transfer_type_id=t.transfer_type_id
			and transfer_type_name in ('Shop_Sold','WareHouse_Sold')
			
			left join ".$this->db->db_prefix."channel c
			on c.channel_id=t.from_channel_id
			where to_char(t.action_date,'YYYY-MM-DD') = to_char(current_date-1,'YYYY-MM-DD')
			$supressor
			group  by channel_name,voucher_denomination 			
			order by channel_name,voucher_denomination
			";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;
    }
    
	/** 
     * @name getUserVouchers
     * @param user_id,channel_id, $denomination,$searchstring
     * @return Array of Voucher serials, voucher ids, and denomination
     * used in POS for fetching vocher serialz
     */
    public function getUserVouchers($user_id,$channel_id,$denomination,$startSerial,$endSerail)
    {
    	$where="where voucher_denomination='$denomination' ";
    	if(!empty($startSerial) && !empty($endSerail))
    	{
    		$where.="and  voucher_serial between '$startSerial' and '$endSerail' ";
    	}
        else if(!empty($startSerial) || !empty($endSerail))
    	{
    		$where.="and  (voucher_serial like '$startSerial' or voucher_serial like '$endSerail')";
    	}
    	
    	//TODO: change limit 0,200 to rownum when porting to oracle
    	$query="select * from (
                        select voucher_serial, it.voucher_id, voucher_denomination
			from ".$this->db->db_prefix."tracker t inner join  
			".$this->db->db_prefix."item_information it 
			on t.voucher_id=it.voucher_id 
			and end_date is null 
			and to_channel_id='$channel_id' and assigned_to='$user_id' 
			$where  
			and transfer_status=1 
			order by voucher_serial
		) where rownum<=3000
		
		";
    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		return false;
    	}
    	
    	return $data;
    }
    
	/** 
     * @name validateVoucherOwner
     * @param user_id,channel_id, voucher_serial_start,voucher_serial_end
     * @return array of voucher IDs
     * used for allocating vouchers
     */
    public function validateVoucherOwner($user_id,$channel_id, $voucher_serial_start,$voucher_serial_end)
    {
    	//create in list voucher serials
    	//identify ranges
    	$start=substr($_POST['vouch_serial_start'],getLastChar($_POST['vouch_serial_start'])+1);
    	$end=substr($_POST['vouch_serial_end'],getLastChar($_POST['vouch_serial_end'])+1);
    	$prefix=substr($_POST['vouch_serial_start'],0,getLastChar($_POST['vouch_serial_start'])+1);
        
    	///$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nStart Range: $start\r\nEnd Range: $end\r\nPrefix: $prefix");
    	
    	//iterative insert
        $initial = $start;
        $StserialLength = strlen($start);
        $EnSerialLength = strlen($end);

        //checking whether the starting and ending serial starts with zero
        if($start[0]=="0" && $end[0]=="0")
        {
            $startWithZero=true;
        }

        if($StserialLength!=$EnSerialLength)
        {
            echo "Starting and Ending Serial Length Do not match";
            return false;
        }
        $initial=$start;
    	for($start; $start<=$end; $start++)
    	{
            if($initial!=$start)
             {
                if($startWithZero)
                {
                    if(strlen("0".$start)!=$StserialLength || strlen("0".$start)!=$EnSerialLength)
                    {
                        echo "Generated Serial do match with Start and End Serial Provided";
                        return false;
                    }
                    $vouchers[]=$prefix."0".$start;
                }
             else
                 {
                    if(strlen($start)!=$StserialLength || strlen($start)!=$EnSerialLength)
                    {
                        echo "Generated Serial do match with Start and End Serial Provided";
                        return false;
                    }
                     $vouchers[]=$prefix.$start;
                 }
             }
             else
             {
                 $vouchers[]=$prefix.$start;
             }
    	}
        
    	$table = $this->createGTempTable('valid_vouch');
		foreach($vouchers as $voucher)
   			$GLOBALS['_DB']->DBlink->Execute("insert into $table (voucher_serial) values ('".$voucher."')");		    

    	//$vouchers=trim($vouchers,",");
    	
    	$query="
    		select it.voucher_id 
	    	from ".$this->db->db_prefix."tracker t inner join 
	    	".$this->db->db_prefix."item_information it
	    	on t.voucher_id=it.voucher_id
	    	and end_date is null
	    	and assigned_to=$user_id
	    	and to_channel_id=$channel_id
	    	and voucher_serial in (select voucher_serial from $table)
	    	and transfer_status=1 
	    	order by 1";
        
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		$this->LastMsg="No valid vouchers found";
    		$this->dropGTempTable($table);
    		return false;
    	}
    	
    	$this->dropGTempTable($table);
    	
    	foreach($data as $arr)
    	{
    		$voucherIDs[]=$arr['voucher_id'];
    	}
    	
    	return $voucherIDs;
    }
    
	/** 
     * @name validateVoucherOwner
     * @param user_id,channel_id, array of voucher serial
     * @return array of voucher IDs
     * used for selling vouchers
     */
    public function validateSerialOwner($user_id=null,$channel_id, &$voucherSerials)
    {
    	//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nStart Range: ".serialize($voucherSerials));
    	
    	//iterative insert
    	/*foreach($voucherSerials as $arr)
    	{
    		$vouchers.="'".$arr."',";
    	}*/
    	//$vouchers=trim($vouchers,",");
    	$table = $this->createGTempTable('valid_vouch');
		foreach($voucherSerials as $voucher)
   			$GLOBALS['_DB']->DBlink->Execute("insert into $table (voucher_serial) values ('".$voucher."')");
		
    	$query="
    		select it.voucher_id 
	    	from ".$this->db->db_prefix."tracker t inner join 
	    	".$this->db->db_prefix."item_information it
	    	on t.voucher_id=it.voucher_id
	    	and end_date is null
	    	and assigned_to=$user_id
	    	and to_channel_id=$channel_id
	    	and voucher_serial in (select voucher_serial from $table)
	    	and transfer_status=1 
	    	order by 1";
    	    	
    	if(($data=$this->db->CustomQuery($query))==null)
    	{
    		$this->LastMsg="No valid vouchers found";
    		$this->dropGTempTable($table);
    		return false;
    	}
    	$this->dropGTempTable($table);
    	
    	foreach($data as $arr)
    	{
    		$voucherIDs[]=$arr['voucher_id'];
    	}
    	
    	return $voucherIDs;
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
	
	public function getReceiptReport($filters)
	{
			$payment_method = strtolower($filters['payment_method']);
			$receipt_type 	= strtolower($filters['receipt_type']);
			$receipt_mode 	= strtolower($filters['receipt_mode']);
			$region_id 		= strtolower($filters['region']);
			$shop_id 		= strtolower($filters['channel']);
			$cse_id 		= strtolower($filters['user']);
			$start_date             = strtolower($filters['start_date']);
			$end_date 		= strtolower($filters['end_date']);
                        $channel_type 		= strtolower($filters['channel_type']);
                			
            $condition = "";
			if($payment_method!="all" and $payment_method!=null)
				$condition.= " and lower(payment_method) = '$payment_method'";
			if($receipt_type!="all" and $receipt_type!=null)
				$condition.= " and lower(receipt_type) = '$receipt_type'";
			if($receipt_mode!="all" and $receipt_mode!=null )
				$condition.= " and lower(receipt_mode) = '$receipt_mode'";
			if($region_id!="all" and $region_id!=null )
				$condition.= " and lower(region_id) like '%$region_id%'";
			if($shop_id!="all" and $shop_id!=null)
				$condition.= " and lower(shop_id) = '$shop_id'";
			if($cse_id!="all" and $cse_id!=null) 
				$condition.= " and cse_id = '$cse_id'";
                        if($channel_type!="all" and $channel_type!=null)
                        { 
                            $condition.= " and channel_type = '$channel_type'";                          
                        }
                        
                        if($filters['CachedView']=='Cached')
                         {
                             $receipt_t = "receipt_t";
                             $reversal_t = "receipt_reversals_t";
                         }
                         else
                         {
                             $receipt_t = "vu_receipts_new";
                             $reversal_t = "receipt_reversal_v";
                         }
                
                           
                      $query = " select RECEIPT_ID,RECEIPT_DATE,CUSTOMER_ID,CUSTOMER_TYPE,REGION_ID,SHOP_ID
                                ,CSE_ID,CSE_NAME,decode(channel_type,'1','Direct Sale','2','Shop-Sales Executive','3','BD-Business Development','4','Kiosks','5','BO','6','NBO','7','CSO') as channel_type
                                ,TRANSACTION_ID,PAYMENT_METHOD,INSTRUMENT_NUMBER,INSTRUMENT_BANK
                                ,RECEIPT_AMOUNT,RECEIPT_MODE,RECEIPT_TYPE 
                                from 
                                (
                                    select * from (select RECEIPT_ID,RECEIPT_DATE,CUSTOMER_ID,CUSTOMER_TYPE,REGION_ID,SHOP_ID
                                    ,CSE_ID,CSE_NAME,channel_type
                                    ,TRANSACTION_ID,PAYMENT_METHOD,INSTRUMENT_NUMBER,INSTRUMENT_BANK
                                    ,RECEIPT_AMOUNT,RECEIPT_MODE,RECEIPT_TYPE
                                    ,CASE
                                      WHEN trunc(receipt_date) <= to_date('06/20/2011','MM/DD/YYYY') and (payment_method = 'Cash Refund' or payment_method = 'Creditcard Refund' or payment_method = 'Check Refund')
                                        THEN ('1')
                                      ELSE '0'
                                     END AS refund_exclusion_flag
                                    from $receipt_t)
                                    where                             
                                    To_date(to_char(receipt_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('".$start_date."','MM/DD/YYYY')
                                   and To_date(to_char(receipt_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('".$end_date."','MM/DD/YYYY') "." $condition"." 
                                   and refund_exclusion_flag = 0
					   
                               UNION ALL 
					   
                               select RECEIPT_ID,RECEIPT_DATE,CUSTOMER_ID,CUSTOMER_TYPE,REGION_ID,SHOP_ID
                                      ,CSE_ID,CSE_NAME,channel_type
                                      ,TRANSACTION_ID,PAYMENT_METHOD,INSTRUMENT_NUMBER,INSTRUMENT_BANK
                                      ,RECEIPT_AMOUNT,RECEIPT_MODE,RECEIPT_TYPE,'' as refund_exclusion_flag

                                    from ".$reversal_t." 
                               where To_date(to_char(receipt_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('".$start_date."','MM/DD/YYYY')
                               and To_date(to_char(receipt_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('".$end_date."','MM/DD/YYYY') "." $condition".") ";
                      
             $data = $GLOBALS['_DB']->CustomQuery($query);
                               
             if($data!=null)
                {
                     return $data;
                }
                else
                    $this->LastMsg = "Data not found";
                    return false;
        }

	public function getReceiptSummaryReport($filters)
	{
		$region_id 		= strtolower($filters['region']);
		$shop_id 		= strtolower($filters['channel']);
		$cse_id 		= strtolower($filters['user']);
		$start_date             = strtolower($filters['start_date']);
		$end_date 		= strtolower($filters['end_date']);
                $channel_type 		= strtolower($filters['channel_type']);
		
		$condition = "";
		if($region_id!="all" and $region_id!=null )
			$condition.= " and lower(region_id) like '%$region_id%'";
		if($shop_id!="all" and $shop_id!=null)
			$condition.= " and lower(shop_id) = '$shop_id'";
		if($cse_id!="all" and $cse_id!=null) 
			$condition.= " and cse_id = '$cse_id'";
                 if($channel_type!="all" and $channel_type!=null)
                        { 
                            $condition.= " and channel_type = '$channel_type'";                         
                        }
                 
                              
                 if($filters['CachedView']=='Cached')
                 {
                     $receipt_t = "receipt_t";
                     $reversal_t = "receipt_reversals_t";
                 }
                 else
                 {
                     $receipt_t = "vu_receipts_new";
                     $reversal_t = "receipt_reversal_v";
                 }

                $query="  select receipts.cse_id as cse_id,receipts.cse_name as cse_name,receipts.Cash,receipts.Cheque,receipts.CreditCard,NVL(vouch.grossvalue,0) as VoucherCollection,sum(receipts.Cash_refund + receipts.Check_refund + receipts.Creditcard_refund) as CashRefund,SUM(Cash+Cheque+CreditCard+Cash_refund+Check_refund+Creditcard_refund+nvl(grossvalue,0)) as GrossAmnt
                            from
                            (select cse_id,cse_name,round(Cash,2) Cash,round(Cheque,2) Cheque,round(CreditCard,2) CreditCard,round(Cash_refund,2) Cash_refund,round(Check_refund,2) Check_refund,round(Creditcard_refund,2) Creditcard_refund
                            from (SELECT cse_id, cse_name,
				sum(decode(payment_method,'Cash Payment',receipt_amount,0)) Cash,
					sum(decode(payment_method,'Check Payment',receipt_amount,0)) Cheque,
					sum(decode(payment_method,'Creditcard Payment',receipt_amount,0)) CreditCard,
                                        sum(decode(payment_method,'Cash Refund',receipt_amount,0)) Cash_refund,
                                        sum(decode(payment_method,'Check Refund',receipt_amount,0)) Check_refund,
                                        sum(decode(payment_method,'Creditcard Refund',receipt_amount,0)) Creditcard_refund,
					sum(receipt_amount) as GrossAmount
					from (	
                                                select * from (
                                                            SELECT RECEIPT_ID,RECEIPT_DATE,CUSTOMER_ID,CUSTOMER_TYPE,REGION_ID,SHOP_ID
                                                                    ,CSE_ID,CSE_NAME,decode(channel_type,'1','Direct Sale','2','Shop-Sales Executive','3','BD-Business Development','4','Kiosks','5','BO','6','NBO','7','CSO') as channel_type
                                                                    ,TRANSACTION_ID,PAYMENT_METHOD,INSTRUMENT_NUMBER,INSTRUMENT_BANK
                                                                    ,RECEIPT_AMOUNT,RECEIPT_MODE,RECEIPT_TYPE
                                                                    ,CASE
                                                                     WHEN trunc(receipt_date) <= to_date('06/20/2011','MM/DD/YYYY') and (payment_method = 'Cash Refund' or payment_method = 'Creditcard Refund' or payment_method = 'Check Refund')
                                                                        THEN ('1')
                                                                    ELSE '0'
                                                                    END AS refund_exclusion_flag
                                                                    from $receipt_t where 1=1 $condition

                                                                    UNION ALL

                                                                    select RECEIPT_ID,RECEIPT_DATE,CUSTOMER_ID,CUSTOMER_TYPE,REGION_ID,SHOP_ID
                                                                                                    ,CSE_ID,CSE_NAME,decode(channel_type,'1','Direct Sale','2','Shop-Sales Executive','3','BD-Business Development','4','Kiosks','5','BO','6','NBO','7','CSO') as channel_type
                                                                                                    ,TRANSACTION_ID,PAYMENT_METHOD,INSTRUMENT_NUMBER,INSTRUMENT_BANK
                                                                                                    ,RECEIPT_AMOUNT,RECEIPT_MODE,RECEIPT_TYPE,'' as refund_exclusion_flag 
                                                                                                    from $reversal_t where 1=1 $condition
                                                                 ) 
                                
                                                        where refund_exclusion_flag = 0 or refund_exclusion_flag is null 
						) 
					where To_date(to_char(receipt_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('".$start_date."','MM/DD/YYYY')
					and To_date(to_char(receipt_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('".$end_date."','MM/DD/YYYY')
					group by cse_id, cse_name
                                        )
                              group by cse_id,cse_name,Cash,Cheque,CreditCard,Cash_refund,Check_refund,Creditcard_refund
                              ) receipts
                              LEFT OUTER JOIN
                              (select action_user_id,sum(grossvalue) as grossvalue,sale_rep_name
                                  from (SELECT To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') as action_date,
							to_char(VIMS_TRACKER.action_date,'HH24:MI:SS') as action_time,
							salespersonnel_details.salespersonnel_id as action_user_id,
							salespersonnel_details.userid as username,
							users.first_name||' '||users.last_name as sale_rep_name,
							from_channel.channel_name Channel,
							from_channel.user_defined_3 Region,
							vims_item_information.voucher_denomination face_value,
							count(VIMS_TRACKER.tracker_id) as voucher_count,
							((count(VIMS_TRACKER.Tracker_id))*(vims_item_information.voucher_denomination)) as grossvalue
					FROM VIMS_TRACKER
					INNER JOIN  vims_transfer_type ON VIMS_TRACKER.transfer_type_id = vims_transfer_type.transfer_type_id
					INNER JOIN users on users.user_id = VIMS_TRACKER.action_by
					INNER JOIN salespersonnel_details ON (lower(users.username)=lower(salespersonnel_details.userid))
					inner join vims_item_information on vims_item_information.voucher_id = VIMS_TRACKER.voucher_id
					inner join vims_channel from_channel on from_channel.channel_id = VIMS_TRACKER.from_channel_id
					inner join vims_channel_type from_channel_type on from_channel_type.channel_type_id = from_channel.channel_type_id
					WHERE
					vims_transfer_type.transfer_type_name IN ( 'Shop_Sold','Distributor_Sold','WareHouse_Sold' )
					and To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= to_date('".$start_date."','MM/DD/YYYY')
					and To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= to_date('".$end_date."','MM/DD/YYYY')
					group by
					To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY'),
							to_char(VIMS_TRACKER.action_date,'HH24:MI:SS'),
							salespersonnel_details.salespersonnel_id,
							salespersonnel_details.userid,
							users.first_name||' '||users.last_name,
							from_channel.channel_name,
							from_channel.user_defined_3,
							vims_item_information.voucher_denomination)
                                  group by action_user_id, sale_rep_name) vouch
                                  ON receipts.cse_id = vouch.action_user_id 
                                  group by receipts.cse_id, receipts.cse_name, receipts.Cash, receipts.Cheque, receipts.CreditCard,NVL(vouch.grossvalue,0) ";
              
		$data = $GLOBALS['_DB']->CustomQuery($query);
		
		if($data!=null)
		{
			return $data;
		}
		else
			$this->LastMsg = "Data not found";
		return false;
	}
	
	public function getVoucherCollectionReport($filters)
	{
           
		$region_id 			= strtolower($filters['region']);
		$channel_type_id                = strtolower($filters['channel_type']);
		$channel_id                     = strtolower($filters['channel']);
		$cse_id 			= strtolower($filters['user']);
		$start_date                     = strtolower($filters['start_date']);
		$end_date 			= strtolower($filters['end_date']);
		
		$condition = "";
		if($region_id!="all" and $region_id!=null )
			$condition.=  " and lower(user_defined_3) = lower('$region_id')";
		if($channel_type_id!="all" and $channel_type_id!=null)
			$condition.= " and from_channel_type.channel_type_id ='$channel_type_id'";
		if($channel_id!="all" and $channel_id!=null)
			$condition.= " and VIMS_TRACKER.from_channel_id = '$channel_id'";
		if($cse_id!="all" and $cse_id!=null) 
			$condition.= " and VIMS_TRACKER.action_by = '$cse_id'";
 

		$query = "SELECT To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') as action_date, 
                            to_char(VIMS_TRACKER.action_date,'HH24:MI:SS') as action_time, 
                            NVL(SALESPERSONNEL_DETAILS.SALESPERSONNEL_ID,USERS.USER_ID) as ACTION_USER_ID,
                            NVL(SALESPERSONNEL_DETAILS.USERID,USERS.USERNAME) as USERNAME,
                            users.first_name||' '||users.last_name as sale_rep_name,
                                                        U2.user_id as Parent_User_ID, u2.first_name||' ' ||u2.last_name as Parent_User,
                            from_channel.channel_name Channel, 
                            from_channel.user_defined_3 Region, 
                            vims_item_information.voucher_denomination face_value, 
                            count(VIMS_TRACKER.tracker_id) as voucher_count,
                            ((count(VIMS_TRACKER.Tracker_id))*(vims_item_information.voucher_denomination)) as grossvalue 
                    FROM VIMS_TRACKER 
                    INNER JOIN  vims_transfer_type ON VIMS_TRACKER.transfer_type_id = vims_transfer_type.transfer_type_id 
                    INNER JOIN users on users.user_id = VIMS_TRACKER.action_by 
                    LEFT OUTER JOIN salespersonnel_details ON (lower(users.username)=lower(salespersonnel_details.userid))
                    inner join vims_item_information on vims_item_information.voucher_id = VIMS_TRACKER.voucher_id 
                    inner join vims_channel from_channel on from_channel.channel_id = VIMS_TRACKER.from_channel_id 
                    inner join vims_channel_type from_channel_type on from_channel_type.channel_type_id = from_channel.channel_type_id
                                        inner join users u2 on users.parent_user_id =u2.user_id
                    WHERE 
                    vims_transfer_type.transfer_type_name IN ( 'Shop_Sold' ) 
                   and vims_tracker.voucher_id not in ( select issued_voucher_id from vims_replacement)
                   and To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= To_date('".$start_date."','MM/DD/YYYY') 
                    and To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= To_date('".$end_date."','MM/DD/YYYY') 
                    $condition 
                    group by 
                    To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY'), 
                            to_char(VIMS_TRACKER.action_date,'HH24:MI:SS'), 
                            NVL(SALESPERSONNEL_DETAILS.SALESPERSONNEL_ID,USERS.USER_ID),
                            NVL(SALESPERSONNEL_DETAILS.USERID,USERS.USERNAME),
                            users.first_name||' '||users.last_name,U2.user_id,
                                                        u2.first_name||' ' ||u2.last_name,
                            from_channel.channel_name, 
                            from_channel.user_defined_3,
                            vims_item_information.voucher_denomination
                            
                            union all
                            SELECT To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') as action_date, 
                            to_char(VIMS_TRACKER.action_date,'HH24:MI:SS') as action_time, 
                            NVL(SALESPERSONNEL_DETAILS.SALESPERSONNEL_ID,USERS.USER_ID) as ACTION_USER_ID,
                            NVL(SALESPERSONNEL_DETAILS.USERID,USERS.USERNAME) as USERNAME,
                            users.first_name||' '||users.last_name as sale_rep_name,
                                                        U2.user_id as Parent_User_ID, u2.first_name||' ' ||u2.last_name as Parent_User,
                            from_channel.channel_name Channel, 
                            from_channel.user_defined_3 Region, 
                            vims_item_information.voucher_denomination face_value, 
                            count(VIMS_TRACKER.tracker_id) as voucher_count,
                            ((count(VIMS_TRACKER.Tracker_id))*0) as grossvalue 
                    FROM VIMS_TRACKER 
                    INNER JOIN  vims_transfer_type ON VIMS_TRACKER.transfer_type_id = vims_transfer_type.transfer_type_id 
                    INNER JOIN users on users.user_id = VIMS_TRACKER.action_by 
                    LEFT OUTER JOIN salespersonnel_details ON (lower(users.username)=lower(salespersonnel_details.userid))
                    inner join vims_item_information on vims_item_information.voucher_id = VIMS_TRACKER.voucher_id 
                    inner join vims_channel from_channel on from_channel.channel_id = VIMS_TRACKER.from_channel_id 
                    inner join vims_channel_type from_channel_type on from_channel_type.channel_type_id = from_channel.channel_type_id
                                        inner join users u2 on users.parent_user_id =u2.user_id
                    WHERE 
                    vims_transfer_type.transfer_type_name IN ( 'Shop_Sold' ) 
                   and vims_tracker.voucher_id in ( select issued_voucher_id from vims_replacement)
                   and vims_tracker.action_by in (select VIMS_REPLACEMENT.REPLACED_BY from vims_replacement)
                   and To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') >= To_date('".$start_date."','MM/DD/YYYY') 
                   and To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY') <= To_date('".$end_date."','MM/DD/YYYY') 
                   $condition 
                    group by 
                    To_date(to_char(VIMS_TRACKER.action_date,'MM/DD/YYYY'),'MM/DD/YYYY'), 
                            to_char(VIMS_TRACKER.action_date,'HH24:MI:SS'), 
                            NVL(SALESPERSONNEL_DETAILS.SALESPERSONNEL_ID,USERS.USER_ID),
                            NVL(SALESPERSONNEL_DETAILS.USERID,USERS.USERNAME),
                            users.first_name||' '||users.last_name,U2.user_id,
                                                        u2.first_name||' ' ||u2.last_name,
                            from_channel.channel_name, 
                            from_channel.user_defined_3,
                            vims_item_information.voucher_denomination"; 
		//print $query;
		$data = $GLOBALS['_DB']->CustomQuery($query);
		//print_r($data);
                
		if($data!=null)
		{
			return $data;
		}
		else
			$this->LastMsg = "Data not found";
		return false;
	}	

        public function getRetailerVoucherCollectionReport($filters)
        {
                $region_id                      = strtolower($filters['region']);
		$channel_type_id                = strtolower($filters['channel_type']);
		$channel_id                     = strtolower($filters['channel']);
		$cse_id 			= strtolower($filters['user']);
		$start_date                     = strtolower($filters['start_date']);
		$end_date 			= strtolower($filters['end_date']);

		$condition = "";
		if($region_id!="all" and $region_id!=null )
			$condition.=  " and lower(vc.user_defined_3) = lower('$region_id')";
		if($channel_id!="all" and $channel_id!=null)
			$condition.= " and vt.from_channel_id = '$channel_id'";
		if($cse_id!="all" and $cse_id!=null)
			$condition.= " and vt.action_by = '$cse_id'";

                $Viewquery = " select order_id,order_date,order_by,sales_rep_id,sales_rep_name,
                                parent_user_id,parent_user_name,voucher_denomination,
                                sold,channel_id,channel_name,channel_type_name,grossvalue,
                                commission_id, commission_value,
                                NVL (grossvalue / total_price * discount_amount, 0) AS discount_amount,
                                 NVL ( (grossvalue / total_price * wht_on_discount), 0)
                                               AS wht_on_discount,
                                               commission_amount,
                                                wht_on_commission,
                                            (  grossvalue
                                             - commission_amount
                                             + wht_on_commission
                                             - (grossvalue / total_price * discount_amount)
                                             + (grossvalue / total_price * wht_on_discount))
                                               AS net_amount,region
                                 from (   select VO.ORDER_ID,VO.ORDER_DATE,U.FIRST_NAME||' '||U.LAST_NAME as ORDER_BY,
                                    U.USER_ID as SALES_REP_ID,U.FIRST_NAME||' '||U.LAST_NAME as SALES_REP_NAME,
                                    U2.USER_ID as PARENT_USER_ID,U2.FIRST_NAME||' '||U2.LAST_NAME as PARENT_USER_NAME,VII.VOUCHER_DENOMINATION,
                                    COUNT(VT.TRACKER_ID) as SOLD,
                                    VC.CHANNEL_ID,
                                    VC.CHANNEL_NAME,VCT.CHANNEL_TYPE_NAME,VI.INVOICE_ID,
                                    VI.COMMISSION_ID,
                                    VI.COMMISSION_VALUE,
                                    nvl(VI.DISCOUNT_AMOUNT,0) discount_amount,
                                    nvl(VI.DISCOUNT_AMOUNT,0)*(nvl2(VI.WHT_ON_DISCOUNT,VI.WHT_ON_DISCOUNT/100,0)) as WHT_ON_DISCOUNT,
                                    ((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION)) as GROSSVALUE,
                                    ((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION))*(VI.COMMISSION_VALUE/100) as COMMISSION_AMOUNT,
                                    ((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION))*(VI.COMMISSION_VALUE/100)*(NVL2(VI.WHT_ON_COMMISSION,VI.WHT_ON_COMMISSION/100,0)) as WHT_ON_COMMISSION
                                    ,vi.net_amount,vc.user_defined_3 as region, vi.total_price

                                    from VIMS_ORDER VO
                                    left join VIMS_INVOICE_ORDER vio
                                    on VO.ORDER_ID = VIO.ORDER_ID
                                    left join VIMS_INVOICE vi
                                    on VIO.INVOICE_ID = VI.INVOICE_ID
                                    inner join USERS u
                                    on U.USER_ID = VO.ORDER_BY
                                    inner join CFG_USER_ROLES usrrole
                                    on USRROLE.ROLE_ID = U.USER_ROLE_ID
                                    inner join VIMS_ORDER_ITEMS VOI
                                    on VOI.ORDER_ID = VO.ORDER_ID
                                    inner join VIMS_TRACKER VT
                                    on VOI.VOUCHER_ID = VT.VOUCHER_ID
                                    inner join VIMS_TRANSFER_TYPE VTT
                                    on VT.TRANSFER_TYPE_ID = VTT.TRANSFER_TYPE_ID
                                    inner join VIMS_ITEM_INFORMATION vii
                                    on vii.voucher_id = vt.voucher_id
                                    inner join VIMS_CHANNEL VC
                                    on VC.CHANNEL_ID = VO.ORDER_FROM_CHANNEL
                                    inner join VIMS_CHANNEL_TYPE VCT
                                    on VC.CHANNEL_TYPE_ID = VCT.CHANNEL_TYPE_ID
                                    inner join USERS U2
                                    on u2.user_id = vt.action_by
                                    where ROLE_NAME in ('Retailer')
                                    and VTT.TRANSFER_TYPE_NAME in ('WareHouse_Retailer')
                                    and VT.TO_CHANNEL_ID in ( select FROM_CHANNEL_ID from VIMS_TRACKER VT2
                                                                inner join VIMS_TRANSFER_TYPE VTT2
                                                            on VT2.TRANSFER_TYPE_ID = VTT2.TRANSFER_TYPE_ID
                                                           where VTT2.TRANSFER_TYPE_NAME in ('Retailer_Sold') )
                                   and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') >= TO_DATE('$start_date','MM/DD/YYYY')
                                   and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') <= TO_DATE('$end_date','MM/DD/YYYY')
                                   $condition
                                    group by VO.ORDER_ID, VO.ORDER_DATE, U.FIRST_NAME||' '||U.LAST_NAME, U.USER_ID, U.FIRST_NAME||' '||U.LAST_NAME, U2.USER_ID, U2.FIRST_NAME||' '||U2.LAST_NAME, VII.VOUCHER_DENOMINATION,VC.CHANNEL_ID, VC.CHANNEL_NAME, VCT.CHANNEL_TYPE_NAME, VI.INVOICE_ID, VI.COMMISSION_ID, VI.COMMISSION_VALUE, VI.COMMISSION_AMOUNT, VI.DISCOUNT_AMOUNT, VI.COMMISSION_AMOUNT*(10/100), VI.DISCOUNT_AMOUNT*(10/100), VI.NET_AMOUNT,WHT_ON_COMMISSION,WHT_ON_DISCOUNT,vi.net_amount,vc.user_defined_3,vi.total_price

                                    union all

                                    select VO.ORDER_ID,VO.ORDER_DATE,U.FIRST_NAME ||' '||U.LAST_NAME as ORDER_BY,U2.USER_ID as SALES_REP_ID, 
                                    U2.FIRST_NAME|| ' '||U2.LAST_NAME as SALES_REP_NAME,U3.USER_ID as PARENT_USER_ID,
                                    U3.FIRST_NAME||' '||U3.LAST_NAME as PARENT_USER_NAME,VII.VOUCHER_DENOMINATION,COUNT(VT.tracker_id) as SOLD
                                    ,VC.CHANNEL_ID,VC.CHANNEL_NAME,VCT.CHANNEL_TYPE_NAME,VI.INVOICE_ID
                                    ,VI.COMMISSION_ID
                                    ,VI.COMMISSION_VALUE
                                    ,VI.DISCOUNT_AMOUNT
                                    ,VI.DISCOUNT_AMOUNT*(nvl2(VI.WHT_ON_DISCOUNT,VI.WHT_ON_DISCOUNT/100,0)) as WHT_ON_DISCOUNT
                                    ,((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION)) as GROSSVALUE
                                    ,VI.COMMISSION_AMOUNT
                                    ,VI.COMMISSION_AMOUNT*(NVL2(VI.WHT_ON_COMMISSION,VI.WHT_ON_COMMISSION/100,0)) as WHT_ON_COMMISSION
                                    ,vi.net_amount,vc.user_defined_3 as region, vi.total_price

                                    from VIMS_ORDER VO
                                    inner join VIMS_ORDER_ITEMS VOI
                                    on VO.ORDER_ID = VOI.ORDER_ID
                                    inner join VIMS_TRACKER VT
                                    on VOI.VOUCHER_ID = VT.VOUCHER_ID
                                    inner join VIMS_TRANSFER_TYPE VTT
                                    on VTT.TRANSFER_TYPE_ID = VT.TRANSFER_TYPE_ID
                                    inner join VIMS_ITEM_INFORMATION VII
                                    on vii.voucher_id = vt.voucher_id
                                    left join VIMS_INVOICE VI
                                    on VO.ORDER_ID = VI.ORDER_ID
                                    inner join USERS U
                                    on U.USER_ID = VO.ORDER_BY
                                    inner join USERS U2
                                    on U2.USER_ID = VT.ACTION_BY
                                    inner join VIMS_CHANNEL VC
                                    on U2.USER_CHANNEL_ID = VC.CHANNEL_ID
                                    inner join VIMS_CHANNEL_TYPE VCT
                                    on VC.CHANNEL_TYPE_ID = VCT.CHANNEL_TYPE_ID
                                    inner join USERS U3
                                    on U2.PARENT_USER_ID = U3.USER_ID
                                    where VTT.TRANSFER_TYPE_NAME in ('WareHouse_Sold')
                                    and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') >= TO_DATE('$start_date','MM/DD/YYYY')
                                    and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') <= TO_DATE('$end_date','MM/DD/YYYY')
                                    $condition
                                    group by VO.ORDER_ID, VO.ORDER_DATE, U.FIRST_NAME ||' '||U.LAST_NAME, U2.USER_ID, U2.FIRST_NAME|| ' '||U2.LAST_NAME, U3.USER_ID, U3.FIRST_NAME||' '||U3.LAST_NAME, VII.VOUCHER_DENOMINATION,VC.CHANNEL_ID, VC.CHANNEL_NAME, VCT.CHANNEL_TYPE_NAME, VI.INVOICE_ID, VI.COMMISSION_ID, VI.COMMISSION_VALUE, VI.COMMISSION_AMOUNT, VI.DISCOUNT_AMOUNT, VI.COMMISSION_AMOUNT*(NVL2(VI.WHT_ON_COMMISSION,VI.WHT_ON_COMMISSION/100,0)), VI.DISCOUNT_AMOUNT*(NVL2(VI.WHT_ON_DISCOUNT,VI.WHT_ON_DISCOUNT/100,0)),vi.net_amount,vc.user_defined_3,vi.total_price
                                    )  order by order_id
                ";
		/*$query = "select * from (
                            select VO.ORDER_ID,VO.ORDER_DATE,U.FIRST_NAME||' '||U.LAST_NAME as ORDER_BY,U.USER_ID as SALES_REP_ID,U.FIRST_NAME||' '||U.LAST_NAME as SALES_REP_NAME,
                                    U2.USER_ID as PARENT_USER_ID,U2.FIRST_NAME||' '||U2.LAST_NAME as PARENT_USER_NAME,VII.VOUCHER_DENOMINATION,
                                    COUNT(VT.TRACKER_ID) as SOLD,
                                    VC.CHANNEL_NAME,VCT.CHANNEL_TYPE_NAME,VI.INVOICE_ID,
                                    VI.COMMISSION_ID,
                                    VI.COMMISSION_VALUE,
                                    VI.DISCOUNT_AMOUNT*(nvl2(VI.WHT_ON_DISCOUNT,VI.WHT_ON_DISCOUNT/100,0)) as WHT_ON_DISCOUNT,
                                    ((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION)) as GROSSVALUE,
                                    ((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION))*(VI.COMMISSION_VALUE/100) as COMMISSION_AMOUNT,
                                    NVL (grossvalue / total_price * discount_amount, 0) AS discount,
                                    ((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION))*(VI.COMMISSION_VALUE/100)*(NVL2(VI.WHT_ON_COMMISSION,VI.WHT_ON_COMMISSION/100,0)) as WHT_ON_COMMISSION
                                    ,vi.net_amount,vc.user_defined_3 as region
                
                                    from VIMS_ORDER VO
                                    left join VIMS_INVOICE_ORDER vio
                                    on VO.ORDER_ID = VIO.ORDER_ID
                                    left join VIMS_INVOICE vi
                                    on VIO.INVOICE_ID = VI.INVOICE_ID
                                    inner join USERS u
                                    on U.USER_ID = VO.ORDER_BY
                                    inner join CFG_USER_ROLES usrrole
                                    on USRROLE.ROLE_ID = U.USER_ROLE_ID
                                    inner join VIMS_ORDER_ITEMS VOI
                                    on VOI.ORDER_ID = VO.ORDER_ID
                                    inner join VIMS_TRACKER VT
                                    on VOI.VOUCHER_ID = VT.VOUCHER_ID
                                    inner join VIMS_TRANSFER_TYPE VTT
                                    on VT.TRANSFER_TYPE_ID = VTT.TRANSFER_TYPE_ID
                                    inner join VIMS_ITEM_INFORMATION vii
                                    on vii.voucher_id = vt.voucher_id
                                    inner join VIMS_CHANNEL VC
                                    on VC.CHANNEL_ID = VO.ORDER_FROM_CHANNEL
                                    inner join VIMS_CHANNEL_TYPE VCT
                                    on VC.CHANNEL_TYPE_ID = VCT.CHANNEL_TYPE_ID
                                    inner join USERS U2
                                    on u2.user_id = vt.action_by
                                    where ROLE_NAME in ('Retailer')
                                    and VTT.TRANSFER_TYPE_NAME in ('WareHouse_Retailer')
                                    and VT.TO_CHANNEL_ID in ( select FROM_CHANNEL_ID from VIMS_TRACKER VT2
                                                                inner join VIMS_TRANSFER_TYPE VTT2
                                                            on VT2.TRANSFER_TYPE_ID = VTT2.TRANSFER_TYPE_ID
                                                           where VTT2.TRANSFER_TYPE_NAME in ('Retailer_Sold') )
                                    and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') >= TO_DATE('$start_date','MM/DD/YYYY')
                                    and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') <= TO_DATE('$end_date','MM/DD/YYYY')
                                    $condition
                                    group by VO.ORDER_ID, VO.ORDER_DATE, U.FIRST_NAME||' '||U.LAST_NAME, U.USER_ID, U.FIRST_NAME||' '||U.LAST_NAME, U2.USER_ID, U2.FIRST_NAME||' '||U2.LAST_NAME, VII.VOUCHER_DENOMINATION, VC.CHANNEL_NAME, VCT.CHANNEL_TYPE_NAME, VI.INVOICE_ID, VI.COMMISSION_ID, VI.COMMISSION_VALUE, VI.COMMISSION_AMOUNT, VI.DISCOUNT_AMOUNT, VI.COMMISSION_AMOUNT*(10/100), VI.DISCOUNT_AMOUNT*(10/100), VI.NET_AMOUNT,WHT_ON_COMMISSION,WHT_ON_DISCOUNT,vi.net_amount,vc.user_defined_3

                                    union all

                                    select VO.ORDER_ID,VO.ORDER_DATE,U.FIRST_NAME ||' '||U.LAST_NAME as ORDER_BY,U2.USER_ID as SALES_REP_ID, U2.FIRST_NAME|| ' '||U2.LAST_NAME as SALES_REP_NAME,U3.USER_ID as PARENT_USER_ID,U3.FIRST_NAME||' '||U3.LAST_NAME as PARENT_USER_NAME,VII.VOUCHER_DENOMINATION,COUNT(VT.tracker_id) as SOLD
                                    ,VC.CHANNEL_NAME,VCT.CHANNEL_TYPE_NAME,VI.INVOICE_ID
                                    ,VI.COMMISSION_ID
                                    ,VI.COMMISSION_VALUE
                                    ,VI.DISCOUNT_AMOUNT
                                    ,VI.DISCOUNT_AMOUNT*(nvl2(VI.WHT_ON_DISCOUNT,VI.WHT_ON_DISCOUNT/100,0)) as WHT_ON_DISCOUNT
                                    ,((COUNT(VT.TRACKER_ID))*(VII.VOUCHER_DENOMINATION)) as GROSSVALUE
                                    ,VI.COMMISSION_AMOUNT
                                    ,VI.COMMISSION_AMOUNT*(NVL2(VI.WHT_ON_COMMISSION,VI.WHT_ON_COMMISSION/100,0)) as WHT_ON_COMMISSION
                                    ,vi.net_amount,vc.user_defined_3 as region
                
                                    from VIMS_ORDER VO
                                    inner join VIMS_ORDER_ITEMS VOI
                                    on VO.ORDER_ID = VOI.ORDER_ID
                                    inner join VIMS_TRACKER VT
                                    on VOI.VOUCHER_ID = VT.VOUCHER_ID
                                    inner join VIMS_TRANSFER_TYPE VTT
                                    on VTT.TRANSFER_TYPE_ID = VT.TRANSFER_TYPE_ID
                                    inner join VIMS_ITEM_INFORMATION VII
                                    on vii.voucher_id = vt.voucher_id
                                    left join VIMS_INVOICE VI
                                    on VO.ORDER_ID = VI.ORDER_ID
                                    inner join USERS U
                                    on U.USER_ID = VO.ORDER_BY
                                    inner join USERS U2
                                    on U2.USER_ID = VT.ACTION_BY
                                    inner join VIMS_CHANNEL VC
                                    on U2.USER_CHANNEL_ID = VC.CHANNEL_ID
                                    inner join VIMS_CHANNEL_TYPE VCT
                                    on VC.CHANNEL_TYPE_ID = VCT.CHANNEL_TYPE_ID
                                    inner join USERS U3
                                    on U2.PARENT_USER_ID = U3.USER_ID
                                    where VTT.TRANSFER_TYPE_NAME in ('WareHouse_Sold')
                                     and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') >= TO_DATE('$start_date','MM/DD/YYYY')
                                    and TO_DATE(TO_CHAR(VT.ACTION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') <= TO_DATE('$end_date','MM/DD/YYYY')
                                     $condition
                                    group by VO.ORDER_ID, VO.ORDER_DATE, U.FIRST_NAME ||' '||U.LAST_NAME, U2.USER_ID, U2.FIRST_NAME|| ' '||U2.LAST_NAME, U3.USER_ID, U3.FIRST_NAME||' '||U3.LAST_NAME, VII.VOUCHER_DENOMINATION, VC.CHANNEL_NAME, VCT.CHANNEL_TYPE_NAME, VI.INVOICE_ID, VI.COMMISSION_ID, VI.COMMISSION_VALUE, VI.COMMISSION_AMOUNT, VI.DISCOUNT_AMOUNT, VI.COMMISSION_AMOUNT*(NVL2(VI.WHT_ON_COMMISSION,VI.WHT_ON_COMMISSION/100,0)), VI.DISCOUNT_AMOUNT*(NVL2(VI.WHT_ON_DISCOUNT,VI.WHT_ON_DISCOUNT/100,0)),vi.net_amount,vc.user_defined_3
                                    ) order by invoice_id,order_id";

                 * 
                 */
                $data = $GLOBALS['_DB']->CustomQuery($Viewquery);
		if($data!=null)
		{
			return $data;
		}
		else
			$this->LastMsg = "Data not found";
		return false;
        }
	public function getPaymentModes()
    {
	 /*
         $query = "select distinct(payment_method) as payment_method from receipts_t";
         $data = $this->db->CustomQuery($query);
         if($data!=null)
         {
            return $data;
         }
         else
            $this->LastMsg = "Data not found";
		return false;
		*/
			 $data[1]['payment_method']= 'Creditcard Payment';
			 $data[2]['payment_method']= 'Check Payment';
			 $data[3]['payment_method']= 'Cash Payment';
                         $data[4]['payment_method']= 'Cash Refund';
                         $data[5]['payment_method']= 'Check Refund';
                         $data[6]['payment_method']= 'Creditcard Refund';

			return $data; 
                        
     }
	 
	public function getReceiptModes()
	{
		/*
		 $query = "select distinct(receipt_mode) as receipt_mode from receipts_t
					UNION ALL
					select distinct(receipt_mode) as receipt_mode from receipt_reversal_t";
		 $data = $this->db->CustomQuery($query);
		 if($data!=null)
		 {
			return $data;
		 }
		 else
			$this->LastMsg = "Data not found";
			return false;         
		*/
		$data[0]['receipt_mode'] = 'Receipt';
		$data[1]['receipt_mode'] = 'Reversal';
                $data[2]['receipt_mode'] = 'Refund';
		return $data;
	}

	public function getReceiptTypes()
	{
		/*
		 $query = "select distinct(receipt_type) as receipt_type from receipts_t
					UNION
					select distinct(receipt_type) as receipt_type from receipt_reversal_t";
		 $data = $this->db->CustomQuery($query);
		 if($data!=null)
		 {
			return $data;
		 }
		 else
			$this->LastMsg = "Data not found";
			return false;
		*/
		$data[0]['receipt_type'] = 'Billing';
		$data[1]['receipt_type'] = 'New Sale';
		
		return $data;
	}

	public function getRegions()
	{
		/*
		 $query = "select distinct(lower(region_id)) as region_id from receipts_t";
			$data = $this->db->CustomQuery($query);
			 if($data!=null)
				{
					return $data;
				}
				else
					$this->LastMsg = "Data not found";
					return false;
		*/
		$data[0]['region_id'] = 'Islamabad';
		$data[1]['region_id'] = 'Faisalabad';
		$data[2]['region_id'] = 'Lahore';
		$data[3]['region_id'] = 'Karachi';
		$data[4]['region_id'] = 'Rawalpindi';
		
		return $data;
	}

	public function getUserRegionLive($user_id)
	{
	 $query = "select user_defined_3 location_name from users 
				inner join vims_channel on vims_channel.channel_id = users.user_channel_id
				where users.user_id = ".$user_id."";

	$data = $this->db->CustomQuery($query);
		 if($data!=null)
			{
				return $data;
			}
			else
				$this->LastMsg = "Data not found";
				return false;
	}

	public function getUserRegion($user_id)
	{
	 $query = "select * from vims_locations_hierarchy
				where location_id = (
										select user_defined_3 from vims_channel where
										channel_id = ( select user_channel_id from users where users.user_id = ".$user_id."))";

	$data = $this->db->CustomQuery($query);
		 if($data!=null)
			{
				return $data;
			}
			else
				$this->LastMsg = "Data not found";
				return false;
	}

	public function getShopByRegion($city)
	{
         $query = "select distinct(sd.shop_id)
                    as shop_id,shop_name,addr_city
                    from shop_salesid_mapping ssm
                    inner join shop_details sd
                    on ssm.shop_id = ssm.shop_id 
                    where lower(addr_city) like lower('%$city%')";
         
         $data = $this->db->CustomQuery($query);
          if($data!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }

            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     } 
	
	public function getUserShop($user_id)
	{ 
		$query = "	select * from users 
					inner join salespersonnel_details on lower(users.username) = lower(salespersonnel_details.userid)
					inner join sales_hierarchy on sales_hierarchy.child_salespersonnel_id = salespersonnel_details.salespersonnel_id
					where users.user_id = '$user_id'";
		//			print $query;
		$data = $this->db->CustomQuery($query);
		//	print_r($data);
		if($data!=null)
		{
			return $data;
		}
		else
			$this->LastMsg = "Data not found";
		return false;
     }

	public function getUserByShop($shop_id,$region)
	{
            $condition='';
            $region = strtolower($region);
            if($region!="all" and $region!=null)
            {
                $condition= " and lower(addr_city) like lower('%$region%')";
            }
		$query = "	select * from SALESPERSONNEL_DETAILS
                                inner join SALES_HIERARCHY
                                on CHILD_SALESPERSONNEL_ID = SALESPERSONNEL_ID
                                where SHOP_ID = '$shop_id' $condition and salesperson_status not in('Closed')
                                order by full_name ";
		  
			$data = $GLOBALS['_DB']->CustomQuery($query);
			if($data==null)
			{
				$this->LastMsg.="Operation Failed <br>";
				return false;
			}
			return $data;
        }

        public function getBRMChannelTypes()
        {
            $query = "select * from MST_CHANNEL";

            $data = $this->db->CustomQuery($query);
            if($data!=null)
            {
                return $data;
            }

            else
                $this->LastMsg = "Operation failed, data not found!!!";
                return false;
        }

        public function getBRMChannelTypeInfo($channel_type_id)
        {
            $query = "select * from mst_channel
                        where channel_id = $channel_type_id";
            $data = $this->db->CustomQuery($query);

            if($data!=null)
            {
                return $data;
            }
            else
                $this->LastMsg = "Operation failed, data not found!!!";
                return false;
        }

        public function getUsersByChannelType($region,$shop,$channeltype)
        {
            //print($shop);
            $condition = '';
            $region = strtolower($region);
            //$shop = strtolower($_POST['channel']);

            if($_POST['channel']!="ALL" and $_POST['channel']!=null )
            {
                $condition.= " and shop_id = '".$_POST['channel']."'";
            }

            if($region!="all" and $region!=null)
            {
                $condition.= " and lower(addr_city) like lower('%$region%') ";
            }
            
            $query = "select * from SALESPERSONNEL_DETAILS
                        inner join SALES_HIERARCHY
                        on CHILD_SALESPERSONNEL_ID = SALESPERSONNEL_ID
                        where channel_type = $channeltype $condition and salesperson_status = 'Active'";
            $data = $this->db->CustomQuery($query);
            if($data!=null)
            {
                return $data;
            }
            else
                $this->LastMsg = "Operation failed,data not found!!";
                return false;
        }

        public function getLostShopInventory($shop,$serialSearch=null)
        {
            $condition = '';
            if($serialSearch['serial_search_start']!=null && $serialSearch['serial_search_end'] )
             {
                $condition = "and vii.voucher_serial between '".$serialSearch['serial_search_start']."' and '".$serialSearch['serial_search_end']."'";
             }
             else if($serialSearch['serial_search_start']!=null || $serialSearch['serial_search_end'])
                 $condition = "and (vii.voucher_serial like '".$serialSearch['serial_search_start']."' or vii.voucher_serial like '".$serialSearch['serial_search_end']."' )";
             
            $query = "select vii.voucher_serial from vims_tracker vt
                        inner join vims_item_information vii
                        on vt.voucher_id = vii.voucher_id
                        where to_channel_id = '$shop' and end_date is null
                        and assigned_to = -1 $condition";
            
            $data = $this->db->CustomQuery($query);
            if($data!=null)
            {
                return $data;
            }
            else
                $this->LastMsg = "Operation failed,data not found!!";
                return false;
        }
	
}

?>