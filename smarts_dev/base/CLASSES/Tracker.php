<?php
/*
 * @Author: Aasim Naveed
 * Email: PKAasimN@pk.wi-tribe.com
 * Tracker Class used to keep track of inventory movement,channels current stock,inventory sold
 */
define( INVENTORY_RECEIVED,1);
define( INVENTORY_IN_TRANSIT,2);

include_once(CLASSDIR."/Tracker.php");
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/BRMIntegration.php");
include_once(CLASSDIR."/Replacement.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Discount.php");
include_once(CLASSDIR."/Invoice.php");


Class Tracker
{
/**
 * Store local class messages and errors
 *
 * @var string
 */
   public $LastMsg;
/**
 * Store database connection pointer
 *
 * @var object
 */
   private $db;
/**
 * Contructor
 *
 */

   private $conf;
   private $mlog;


   public function  __construct()
   {
        $this->db=new DBAccess();
        $this->conf=new config();
        $this->mlog=new Logging();
   }
/** function to add inventory movement information to database
   * Returns true upon successful insertion else returns false
   * @param from_channel_id
   * @param to_channel_id
   * @param vouchers id array
   * @param $transfer_status (1=available, 2=transition)
   */
  public function moveInventory($from_channel_id,$to_channel_id,$user_id,&$vouchers,$transfer_status=1)
   {
         if(!$this->preProcessInventory($from_channel_id,$to_channel_id,$user_id,$vouchers))
         {
               return false;
         }
         
        // $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVouchres: ".serialize($vouchers));

         if(!($movement_type = $this->getMovementType($from_channel_id, $to_channel_id)))
        	return false;

        //$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nDetected Movement Type: $movement_type");

        $action_by = $_SESSION['user_id'];

        //TODO: assuming sessions for user_id and channel_id
        //starting the update insert loop

      //foreach($vouchers as $arr)
     // $voucher_ids.=$arr.",";
      #trim($voucher_ids,',')
     
        if(!$this->closePreviousRow($vouchers))
       	{
                
        	//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVouchers: $voucher_ids\r\nResult: Failed update..skipping");
        	$this->LastMsg.="Failed Processing<br>";
        	return false;
        }

        foreach($vouchers as $arr)
        {
        	$insert = "from_channel_id,to_channel_id,action_by,action_date,voucher_id,transfer_type_id, end_date, assigned_to,transfer_status";
        	$vals = "'".$from_channel_id."','".$to_channel_id."','".$_SESSION['user_id']."'
                ,current_timestamp ,'".$arr."', $movement_type, NULL,'$user_id','$transfer_status'";


			if(($result=$this->db->InsertRecord($this->conf->db_prefix."tracker", $insert, $vals))==null)
			{
				//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
				$this->LastMsg.="Failed Insertion: $arr<br>";
			}
        }
		  if($this->LastMsg!=null)
		  {
	          	$this->LastMsg="Please notify administrator imidiately:<br>".$this->LastMsg;
	          	return false;
		  }

		return true;
   }

   /**
    * @name closePreviousRow
    * @param voucher_id
    * @return boolean
    */
   public function closePreviousRow($voucher_id)
   {
   		//fail safe: delete tmp table if already exists
   		$tname="VIMSCLPREVR_".time();
   		$query="select count(*) as t from tab where tname='$tname'";
   		$data=$this->db->CustomQuery($query);

   		if($data[0]['t']>=1)
   		{
   			$this->db->DBlink->Execute("truncate table $tname");#drop temp table
   			$this->db->DBlink->Execute("drop table $tname");#drop temp table
   		}

   		//create a temp table
   		$table="create global temporary table $tname
				(
				  voucher_id varchar2(20)
				)
				on commit preserve rows";

		$this->db->DBlink->Execute($table);

   		//create insert array
   		for($i=0; $i< sizeof($voucher_id); $i++)
   			$this->db->DBlink->Execute("insert into $tname (voucher_id) values (".$voucher_id[$i].")");

   		//insert voucher ids
		//$this->db->DBlink->Execute('insert into $tname (voucher_id) values (?)',$insert);

   		$query="update ".$this->conf->db_prefix."tracker set end_date=current_timestamp where voucher_id in (select voucher_id from $tname) and end_date is null";
   		if($this->db->CustomModify($query))
   		{
   			$this->db->DBlink->Execute("truncate table $tname");
   			$this->db->DBlink->Execute("drop table $tname");#drop temp table
   			return true;
   		}

   		$this->db->DBlink->Execute("truncate table $tname");
   		$this->db->DBlink->Execute("drop table $tname");#drop temp table
   		return false;
   }


/** function to preprocess data before get inserted into database
   * Returns true upon successful validation of data
   * @param from channel id
   * @param to channel id
   */

   public function preProcessInventory($from_channel_id,$to_channel_id,$user_id,&$vouchers)
   {

   		//validate vouchers
   		foreach($vouchers as $arr)
   		{
   			if(empty($arr) || strlen($arr)<5)
   			{
   				$this->LastMsg.="Invalid vouchers detected";
   				break;
   			}
   		}

   		//validate from channel
         $query = "select * from ".$this->db->db_prefix."channel
                   where channel_id = '$from_channel_id'";

         if(($this->db->CustomQuery($query))==null && !empty($from_channel_id))
         {
            $this->LastMsg.="From channel does not exist <br>";
         }

         //validate to channel
         $query = "select * from ".$this->db->db_prefix."channel
                   where channel_id = '$to_channel_id'";
         if($this->db->CustomQuery($query)==null && $to_channel_id<>-1)
         {
            $this->LastMsg.="To channel does not exist <br>";
         }

         //validate user
         //TODO: validate user

        if($this->LastMsg!=null)
		return false;

	return true;

   }

   /** function to get sold inventory information of any desired channel
   * Returns sold inventory information upon successful retrieval else returns false
   * @param channel_id
   */
   public function getSoldInventory($channel_id)
   {
        $query= "select * from ".$this->db->db_prefix."channel where channel_id = $channel_id";
        if(($this->db->CustomQuery($query))!=null)
        {
            $query =  "select *
                      from ".$this->db->db_prefix."tracker
                      where from_channel_id = $channel_id
                      AND to_channel_id = -1";
            if(($data=$this->db->CustomQuery($query))!=null)
             {
                return $data;
             }
             $this->LastMsg.="Query failed, cannot get info";
             return false;
        }
        $this->LastMsg.="Channel_id not exist";
        return false;
   }

/** function to get moved inventory information of any desired channel
   * Returns moved inventory information upon successful retrieval else returns false
   * @param channel_id
   */
   public function getMovedInvontory($channel_id)
   {
       $query= "select * from ".$this->db->db_prefix."channel where channel_id = $channel_id";
        if(($this->db->CustomQuery($query))!=null)
        {
            $query = "select *
                       from ".$this->db->db_prefix."tracker
                       where from_channel_id = $channel_id
                       AND to_channel_id != -1";

            if(($data=$this->db->CustomQuery($query))!=null)
             {
                return $data;
             }
             $this->LastMsg.="Query failed, cannot get info";
             return false;
        }

        $this->LastMsg.="Channel_id not exist";
        return false;
   }

/** function to get current inventory information of any desired channel
   * Returns channels inventory upon successful retrieval else returns false
   * @param channel_id
   */
    public function getChannelInventory($channel_id)
    {
        $query= "select * from ".$this->db->db_prefix."channel where channel_id = $channel_id";
        if(($this->db->CustomQuery($query))!=null)
        {
            $query = "select * from
                                   (select voucher_id, max(action_date) as dt
                                    from ".$this->db->db_prefix."tracker
                                    where voucher_id IN ( select voucher_id
                                                          from ".$this->db->db_prefix."tracker
                                                          where to_channel_id = $channel_id)
                                    group by voucher_id) a
                     inner join ".$this->db->db_prefix."tracker b
                     on a.voucher_id = b.voucher_id
                     AND a.dt = b.action_date
                     AND to_channel_id = $channel_id
                     group by b.voucher_id";
            if(($data=$this->db->CustomQuery($query))!=null)
             {
                return $data;
             }

             $this->LastMsg.="Query failed, cannot get info";
             return false;
        }
        $this->LastMsg.="Channel_id not exist";
        return false;
    }

/** function to get newly moved unassigned invontory of all warehouses
   * Returns new inventory upon successful retrieval else returns false
   *
   */

    public function getNewInventory()
    {
        $query = "select * from ".$this->db->db_prefix."tracker
                  where voucher_id not in
                       (select distinct voucher_id from
                                                    (SELECT to_channel_id
                                                       from ".$this->db->db_prefix."tracker) a,tracker t
                                                         where a.to_channel_id = t.from_channel_id)
                                                         group by voucher_id";
         if(($data=$this->db->CustomQuery($query))!=null)
             {
                return $data;
             }

             $this->LastMsg.="Query failed, cannot get info";
             return false;
    }

/** function to get movement type id against two channels name
   * Returns movement type id upon successful retrieval else returns false
   *
   */
    private function getMovementType($from_channel_id,$to_channel_id)
    {
    	//get transfer type id
    	//TODO: change join to full outer join when uploaded
    	$from_channel_id=empty($from_channel_id)?0:$from_channel_id;
    	$to_channel_id=$to_channel_id=='-1'?0:$to_channel_id;
    	$query="
    	select * from ".$this->db->db_prefix."transfer_type
		where
		transfer_type_name=
		(
			select concat(concat(coalesce(fromc.channel_type_name,'Null'),'_'),coalesce(toc.channel_type_name,'Sold'))
			from


			(select channel_type_name, 1 as j from
			".$this->db->db_prefix."channel c inner join ".$this->db->db_prefix."channel_type ct
			on c.channel_type_id=ct.channel_type_id
			where channel_id=coalesce(nullif(0,0),$to_channel_id)
			) toc

			full outer join


			(select channel_type_name, 1 as j from
			".$this->db->db_prefix."channel c inner join ".$this->db->db_prefix."channel_type ct
			on c.channel_type_id=ct.channel_type_id
			where channel_id=coalesce(nullif(0,0),$from_channel_id)
			) fromc

			on fromc.j=toc.j
		)

    	";
		//print $query;
		$data=$this->db->CustomQuery($query);
                //$result = $this->db->DBlink->Execute($query);
                //$data = $result->GetArray();
               // print_r($data[0][0]['transfer_type_id']);
               
                if($data!=null)
		{	
                    //return $data[0][0]['transfer_type_id'];
                    return $data[0]['transfer_type_id'];
		}

        $this->LastMsg.="Movement not authorized. Please contact administrator <br>";
        return false;
    }

    // ***************************** Retailer Related Work ************************************ //
    
     public function moveInventoryToRetailer($from_channel_id,$from_user,$to_channel_id,$to_user_id,&$vouchers,$transfer_status=1)
   {
         if(!$this->preProcessInventory($from_channel_id,$to_channel_id,$to_user_id,$vouchers))
         {
               return false;
         }

         //$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$from_user."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVouchres: ".serialize($vouchers));

         if(!($movement_type = $this->getMovementType($from_channel_id, $to_channel_id)))
         {
             return false;
         }

            
        //$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$from_user."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nDetected Movement Type: $movement_type");

        $action_by = $from_user;

//        TODO: assuming sessions for user_id and channel_id
//        starting the update insert loop
//
      foreach($vouchers as $arr)
      $voucher_ids.=$arr.",";
      #trim($voucher_ids,',')

        if(!$this->closePreviousRow($vouchers))
       	{

        	//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$from_user."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVouchers: $voucher_ids\r\nResult: Failed update..skipping");
        	$this->LastMsg.="Failed Processing<br>";
        	return false;
        }
        
        foreach($vouchers as $arr)
        {
        	$insert = "from_channel_id,to_channel_id,action_by,action_date,voucher_id,transfer_type_id, end_date, assigned_to,transfer_status";
        	$vals = "'".$from_channel_id."','".$to_channel_id."','".$from_user."',current_timestamp ,'".$arr."', $movement_type, NULL,'$to_user_id','$transfer_status'";

                if(($result=$this->db->InsertRecord($this->conf->db_prefix."tracker", $insert, $vals))==null)
                {
                       // $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$from_user."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
                        $this->LastMsg.="Failed Insertion: $arr<br>";
                }
        }
		  if($this->LastMsg!=null)
		  {
	          	$this->LastMsg="Please notify administrator imidiately:<br>".$this->LastMsg;
	          	return false;
		  }

		return true;
   }

   public function MoveInventoryToLostShop($voucherSerial,$fromUser)
   {
       $user_obj = new User();
       $channel_obj = new Channel();
       $voucher_obj = new Voucher();
       
       //get user & channel information
       $userInfo = $user_obj->getUserDetailInfo($fromUser);
       $from_channel_id = $userInfo[0]['user_channel_id'];
       
       $regionLostShop = $channel_obj->getRegionLostShop($userInfo[0]['region']);
       if(!$regionLostShop)
         {
            $this->LastMsg.= $channel_obj->LastMsg;
            return false;
         }
       $to_channel_id = $regionLostShop[0]['channel_id'];
      
       //get movement type
        if(!($movement_type = $this->getMovementType($from_channel_id, $to_channel_id)))
         {
             return false;
         }
         
       //validate inventory
       $voucher_ids = $voucher_obj->validateSerialOwner($fromUser, $from_channel_id, $voucherSerial);
		if(!$voucher_ids)
   		{
   			$this->error=$voucher_obj->LastMsg;
   			return false;
   		}

       //start transaction
        $GLOBALS['_DB']->DBlink->StartTrans();

        $brm = new BRMIntegration();
        //Update Vouchers Status in BRM
        if(!$brm->MarkVoucherLost($voucher_ids))
             {
                $this->LastMsg.= $brm->LastMsg;
                return false;
             }

       //close prev rows
        if(!$this->closePreviousRow($voucher_ids))
          {
               // $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVouchers: $voucher_ids\r\nResult: Failed update..skipping");
        	$this->LastMsg.="Failed Processing<br>";
        	return false;
          }
          
       //moveinventory
       foreach($voucher_ids as $arr)
        {
        	$insert = "from_channel_id,to_channel_id,action_by,action_date,voucher_id,transfer_type_id, end_date, assigned_to,transfer_status";
        	$vals = "'".$from_channel_id."','".$to_channel_id."','".$fromUser."',current_timestamp ,'".$arr."', $movement_type, NULL,'-1','1'";

                if(($result=$this->db->InsertRecord($this->conf->db_prefix."tracker", $insert, $vals))==null)
                {
                       // $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$from_user."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
                        $this->LastMsg.="Failed Insertion: $arr<br>";
                }
        }
                        
        //if transaction fails rollback
       if($GLOBALS['_DB']->DBlink->HasFailedTrans())
        {
                $GLOBALS['_DB']->DBlink->CompleteTrans();
                return false;
        }
        
        //commit
        $GLOBALS['_DB']->DBlink->CompleteTrans();

        return true;
   }

   public function moveTransitInventoryToParentChannel($order_by,$from_channel_id,$to_channel_id,$voucherIDs,$tname,$to_user)
   {
                if(!($movement_type = $this->getMovementType($from_channel_id, $to_channel_id)))
                    return false;

                $query="update ".$this->conf->db_prefix."tracker set end_date=current_timestamp where voucher_id in (select voucher_id from $tname)";
   		if(!$this->db->CustomModify($query))
   		{
   			$this->error.="Order reject failed";
			return false;
   		}

                 $this->LastMsg = null;
                 foreach($voucherIDs as $arr)
                    {
                            $insert = "from_channel_id,to_channel_id,action_by,action_date,voucher_id,transfer_type_id, end_date, assigned_to,transfer_status";
                            $vals = "'".$from_channel_id."','".$to_channel_id."','".$_SESSION['user_id']."'
                            ,current_timestamp ,'".$arr."', $movement_type, NULL,'$to_user','2'";

                                    if(($result=$this->db->InsertRecord($this->conf->db_prefix."tracker", $insert, $vals))==null)
                                    {
                                           // $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFrom Channel: $from_channel_id\r\nTo Channel: $to_channel_id\r\nVoucher: $arr\r\nResult: Failed insertion");
                                            $this->LastMsg.="Failed Insertion: $arr<br>";
                                    }
                    }
                  if($this->LastMsg!=null)
		  {
	          	$this->LastMsg="Please notify administrator imidiately:<br>".$this->LastMsg;
	          	return false;
		  }

		return true;
   }
}
?>