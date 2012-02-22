<?php
/** 
 * @author Fahad Pervaiz
 * @Email fahad.pervaiz@gmail.com
 * Point of sale
 */

include_once(CLASSDIR."/Tracker.php");
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/BRMIntegration.php");
include_once(CLASSDIR."/Replacement.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Logging.php");


Class POS
{

   public $LastMsg=null;
   private $db;
   private $conf;
   private $mlog;
   
   
   public function  __construct()
   {
        $this->db=new DBAccess();
        $this->conf=new config();
        $this->mlog=new Logging();
   }

	/** 
	 * @name myVouchers
	 * @param
	 * @return list of vouchers
	 */   
   public function myVouchers()
   {
   		$vouchers=new Voucher();
   		
   		if(($data=$vouchers->getUserVouchers($_SESSION['user_id'],$_SESSION['channel_id'],$_POST['voucher_denomination'],$_POST['serial_search_start'],$_POST['serial_search_end'])))
   		{
   			return $data;
   		}
   		
   		return false;
   }
   
	/** 
	 * @name assignInventory
	 * @param channel_id
	 * @param user_id
	 * @return boolean
	 * Assigning to same channel excecutive
	 * Assigning should only be done for child users and channel is not catered for
	 * A check is in place to ensure that assignee is a child of assigner
	 */   
   public function assignInventory($channel_id,$user_id)
   {
                
   		$tracker=new Tracker();
   		$vouchers=new Voucher();
   		$users=new User();
                $log = new Logging();
   		
   		//get voucher denomination
   		//$_POST['voucher_denomination']=$vouchers->getVoucherDenomination($vouchers->getVoucherID($_POST['voucher_serial_start']));
   		
   		if(empty($user_id))
   		{
   			$this->LastMsg="Please select a user";
   			return false;
   		}
   		
   		if(!$users->isChild($_SESSION['user_id'],$user_id))
   		{
   			$this->LastMsg="Invalid user selected";
   			return false;
   		}
   		
   		//get user channel information and match with current user channel
   		//in best case scenario both channels should be same only 
   		//then transfer should be allowed. However igonring this rule here
   		$udata=$users->getUserInfo($user_id);
   		$channel_id=$udata[0]['user_channel_id'];

   		//validate owner of vouchers and make sure they are not sold or expired
   		//TODO: validate expiry from brm or from item_information
   		/*if(!$vouchers->preProcessVoucherRange())
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}*/

   		//validate list of serial numbers
   		foreach($_POST['voucher_serial'] as $arr)
   			$vouchSerials[]=$arr;
   		if(count($vouchSerials)<=0)
   		{
   			$this->LastMsg="Please select atleast one voucher";
   			return false;
   		}
   		
   		/**
   		 * @deprecated
   		 if(!($voucherIDs=$vouchers->validateVoucherOwner($_SESSION['user_id'],$_SESSION['channel_id'],
   		$_POST['voucher_serial_start'],$_POST['voucher_serial_end'])))
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}*/
   		
   		if(!($voucherIDs=$vouchers->validateSerialOwner($_SESSION['user_id'],$_SESSION['channel_id'],$vouchSerials)))
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}
   		
   		if($tracker->moveInventory($_SESSION['channel_id'],$channel_id,$user_id,$voucherIDs))
    		return true;
    		
    	$this->LastMsg=$tracker->LastMsg;
    	return false;
   }
   
   
/** 
	 * @name returnInventory
	 * @param channel_id
	 * @param user_id
	 * @return boolean
	 * ideally should return inventory to channel who transfered inventory to this Channel
	 * but we are going to transfer back to parent user channel
	 */   
   public function returnInventory()
   {
   		$tracker=new Tracker();
   		$vouchers=new Voucher();
   		$users=new User();
   		
   		//get voucher denomination
   		//$_POST['voucher_denomination']=$vouchers->getVoucherDenomination($vouchers->getVoucherID($_POST['voucher_serial_start']));
   		   		   		
   		//get user channel information and match with current user channel
   		//in best case scenario both channels should be same only 
   		//then transfer should be allowed. However igonring this rule here
   		$udata=$users->getUserInfo($_SESSION['user_id']);
		$pdata=$users->getUserInfo($udata[0]['parent_user_id']);
   		$parent_channel_id=$pdata[0]['user_channel_id'];
   		$parent_user_id=$pdata[0]['user_id'];
   		
   		//validate owner of vouchers and make sure they are not sold or expired
   		//TODO: validate expiry from brm or from item_information
   		/*if(!$vouchers->preProcessVoucherRange())
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}*/

   		//validate list of serial numbers
   		foreach($_POST['voucher_serial'] as $arr)
   			$vouchSerials[]=$arr;
   		if(count($vouchSerials)<=0)
   		{
   			$this->LastMsg="Please select atleast one voucher";
   			return false;
   		}
   		
   		
   		/**
   		 * @deprecated
   		 if(!($voucherIDs=$vouchers->validateVoucherOwner($_SESSION['user_id'],$_SESSION['channel_id'],
   		$_POST['voucher_serial_start'],$_POST['voucher_serial_end'])))
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}*/
   		
   		if(!($voucherIDs=$vouchers->validateSerialOwner($_SESSION['user_id'],$_SESSION['channel_id'],
   		$vouchSerials)))
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}
   		
   		if($tracker->moveInventory($_SESSION['channel_id'],$parent_channel_id,$parent_user_id,$voucherIDs))
    		return true;
    		
    	$this->LastMsg=$tracker->LastMsg;
    	return false;
   }
   
	/** 
	 * @name sellInventory
	 * @param voucherSerials
	 * @return boolean
	 * 
	 */   
   public function sellInventory()
   {
   		$tracker=new Tracker();
   		$vouchers=new Voucher();
   		$logging = new Logging();
                
   		//validate list of serial numbers
   		foreach($_POST['voucher_serial'] as $arr)
   			$vouchSerials[]=$arr;
   		if(count($vouchSerials)<=0)
   		{
   			$this->LastMsg="Please select atleast one voucher";
   			return false;
   		}
   			
   		//TODO: validate user_id and make sure that selected user is a child of current user
   		
   		//TODO: check voucher in brm is not marked as lost or stolen
   		
   		//TODO: ensure that voucher is activated
   		
   		//validate owner of vouchers and make sure they are not sold or expired
   		//TODO: validate expiry from brm or from item_information
   		foreach($vouchSerials as $voucher)
                {
                   $logging->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." validate serial".$voucher." by Owner before Selling");
                }
   		
   		if(!($voucherIDs=$vouchers->validateSerialOwner($_SESSION['user_id'],$_SESSION['channel_id'],
   		$vouchSerials)))
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}
   		
   		//forced activating vouchers before selling
   		//executive is supposed to mark vouchers as sold on each sale
   		$brm=new BRMIntegration();
    	if(!$brm->updateVoucherStatus($voucherIDs,VOUCHER_ACTIVE))
    	{
	    	$this->LastMsg=$brm->LastMsg;
	    	return false;
    	}
    	
   		if($tracker->moveInventory($_SESSION['channel_id'],'-1',0,$voucherIDs))
    		return true;
    		
    	$this->LastMsg=$tracker->LastMsg;
    	return false;
   }
   
	/** 
	 * @name activateVouchers
	 * @param voucherSerials
	 * @return boolean
	 * 
	 */   
   public function activateVouchers()
   {
   		$tracker=new Tracker();
   		$vouchers=new Voucher();
   		
   		//validate list of serial numbers
   		foreach($_POST['voucher_serial'] as $arr)
   			$vouchSerials[]=$arr;
   		if(count($vouchSerials)<=0)
   		{
   			$this->LastMsg="Please select atleast one voucher";
   			return false;
   		}
   			
   		//TODO: validate user_id and make sure that selected user is a child of current user
   		
   		//validate owner of vouchers and make sure they are not sold or expired
   		//TODO: validate expiry from brm or from item_information
   		
   		if(!($voucherIDs=$vouchers->validateSerialOwner($_SESSION['user_id'],$_SESSION['channel_id'],
   		$vouchSerials)))
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}
		
   		$brm=new BRMIntegration();
    	if($brm->updateVoucherStatus($voucherIDs,VOUCHER_ACTIVE))
    		return true;
    	
    	$this->LastMsg=$brm->LastMsg;
    	return false;
   }
   
	/** 
	 * @name deactivateVouchers
	 * @param voucherSerials
	 * @return boolean
	 * 
	 */   
   public function deactivateVouchers()
   {
   		$tracker=new Tracker();
   		$vouchers=new Voucher();
   		
   		//validate list of serial numbers
   		foreach($_POST['voucher_serial'] as $arr)
   			$vouchSerials[]=$arr;
   		if(count($vouchSerials)<=0)
   		{
   			$this->LastMsg="Please select atleast one voucher";
   			return false;
   		}
   			
   		//TODO: validate user_id and make sure that selected user is a child of current user
   		
   		//validate owner of vouchers and make sure they are not sold or expired
   		//TODO: validate expiry from brm or from item_information
   		
   		
   		if(!($voucherIDs=$vouchers->validateSerialOwner($_SESSION['user_id'],$_SESSION['channel_id'],
   		$vouchSerials)))
   		{
   			$this->LastMsg=$vouchers->LastMsg;
   			return false;
   		}
   		
   		$brm=new BRMIntegration();
    	if($brm->updateVoucherStatus($voucherIDs,VOUCHER_BLOCKED))
    		return true;
    	
    	$this->LastMsg=$brm->LastMsg;
    	return false;
   }

   public function replaceVoucher()
   {
       
       foreach($_POST['voucher_serial'] as $arr)
   			$vouchSerials[]=$arr;
   		if(count($vouchSerials)> 1)
   		{
   			$this->LastMsg ="Please select one voucher only!";
   			return false;
   		}
                if(count($vouchSerials)<= 0)
   		{
   			$this->LastMsg ="Please select one voucher ";
   			return false;
   		}
                
         /**************** Checking Both Vouchers of Same Denomination ****************/

                $vch = new Voucher();
                $replace_voucher = $_POST['replace_voucher_serial'];
                $replace_vouch_id = $vch->getVoucherID($replace_voucher);
                $replace_denomination = $vch->getVoucherDenomination($replace_vouch_id);
                if(!($replace_denomination==$_POST['voucher_denomination']))
                {
                   $this->LastMsg.="Vouchers are not of same denomination";
                   return false;
                }

         /****** Checking Whether Replacement Voucher is Sold or not *******************/
                
                //$data= $vch->getUserVouchers($_SESSION['user_id'],$_SESSION['channel_id'],$_POST['voucher_denomination'],$_POST['replace_voucher_serial']);
                $query = "select * from ".$this->conf->db_prefix."tracker where voucher_id = $replace_vouch_id and to_channel_id = -1";
                $data = $this->db->CustomQuery($query);
   		if($data==null)
                {
                   $this->LastMsg.="Cannot Replace an Unsold Voucher";
                   return false;
                }
         
         /**************** Check Replacement Exists Against Replacement Voucher *************/

                $query = "select * from ".$this->conf->db_prefix."replacement 
                            left outer join users
                            on replaced_by = users.user_id
                            where returned_voucher_id = $replace_vouch_id";
                $data = $this->db->CustomQuery($query);
   		if($data!=null)
                {
                   $this->LastMsg.="This Voucher is already Replaced on ".$data[0]['replacement_date']." by ".$data[0]['first_name'].' '.$data[0]['last_name']; 
                   return false;
                }

        /************************************************************************************/
                
        $brm = new BRMIntegration();
        $vouchrepSerials[]= $replace_vouch_id;
        $repvouch = $brm->getVoucherStatus($vouchrepSerials);
        
        if($repvouch[$replace_vouch_id]['status']==VOUCHER_EXPIRED) //Case if Replacement Voucher is Expired
        {
            $this->LastMsg.="Replacement Voucher is Expired";
            return false;
        }
        if($repvouch[$replace_vouch_id]['status']==VOUCHER_USED) // Case if Replacement Voucher is Used
        {            
            $query = "select * FROM ".REPLACEMENT_OVERIDE_T." where voucher_serial = '$replace_voucher'";
            $data = $this->db->CustomQuery($query);
            
            if($data[0]['override']==OVERRIDE_STATUS_PENDING) // If Replacement case is with Finance department
            {
                $this->LastMsg.="Case raised on ".$data[0]['date_added']." & Pending to Finance Department ";
                return false;
            }
            if($data[0]['override']==OVERRIDE_STATUS_PROCESSED) // If Replacement voucher is already processed
            {
                $query = "select * from ".$this->conf->db_prefix."replacement 
                            left outer join users
                            on replaced_by = users.user_id
                            where returned_voucher_id = ".$data[0]['voucher_id'];                
                $data = $this->db->CustomQuery($query);
            
                $this->LastMsg.="Replacement is already provided at this voucher on ".$data[0]['replacement_date']."by ".$data[0]['first_name'].' '.$data[0]['last_name'];
                return false; 
            }
            if($data[0]['override']==OVERRIDE_STATUS_DECLINED ) //If Finance Deny the Replacement
            {
                $this->LastMsg.="Replacement is declined by Finance Dept";
                return false;
            }
            if($data[0]['override']==OVERRIDE_STATUS_ALLOW) // If Finance allows the Replacement
            {
                $rep_obj = new Replacement();
                $pos_obj = new POS();
                if($rep_obj->addReplacement())
                {
                   if($rep_obj->updateReplaceOverride($replace_vouch_id))
                     {
                         $pos_obj->sellInventory(); // Mark the voucher as sold provided in replacement
                          return true;
                     }
                }
                $this->LastMsg.=$rep_obj->LastMsg;
                return false;
            }
            else{
                $this->LastMsg.="Replacement Voucher is Used";
                return false; 
                
                $insert="voucher_id,voucher_serial,date_added,voucher_denomination,override,raised_by,by_channel";
                $values="$replace_vouch_id,'".$replace_voucher."',current_timestamp,'".$_POST['voucher_denomination']."',".OVERRIDE_STATUS_PENDING.",'".$_SESSION['user_id']."','".$_SESSION['channel_id']."'";
                $this->db->DBlink->Execute("insert into ".$this->conf->db_prefix."rep_override ($insert) values ($values)");
                if(($data=$this->db->DBlink->Execute("select * from ".$this->conf->db_prefix."rep_override where voucher_id = $replace_vouch_id"))!=null)
                {
                    $this->LastMsg.="Replacement Voucher is Used, Raise Ticket to Finance";
                    return false;
                }
           }
        }

        if(!($repvouch[$replace_vouch_id]['status']==VOUCHER_USED) || !($repvouch[$replace_vouch_id]['status']==VOUCHER_EXPIRED)) // Rest of Cases of Voucher Replacements
       {
                $rep_obj = new Replacement();
                $pos_obj = new POS();
                
                if(($brm->updateVoucherStatus($vouchrepSerials,VOUCHER_BLOCKED)) && ($rep_obj->addReplacement()))
                {
                       $pos_obj->sellInventory();
                       return true;
                }
                $this->LastMsg=$rep_obj->LastMsg;
                $this->LastMsg.=$brm->LastMsg;
                return false;
       }
        
   }

   public function blockvoucher($voucher)
   {
       $brm_obj = new BRMIntegration();
       $vouch_obj = new Voucher();
       $vouch_id = $vouch_obj->getVoucherID($voucher);       
       $vouchrSerial[]= $vouch_id;
       $data = $brm_obj->getVoucherStatus($vouchrSerial);
       if($vouch_id==null)
           {
                $this->LastMsg.="Provide Correct Voucher Serial <br>";
                return false;
            }
          else if($data[$vouch_id]['status']==VOUCHER_ACTIVE)
              {
                if($brm_obj->updateVoucherStatus($vouchrSerial, VOUCHER_BLOCKED))
                      {
                            //TODO: LOG the User Who Blocked The Voucher 
                            return true;
                      }
                 else
                   {   $this->LastMsg = "Operation failed! <br>";
                            return false;
                  }
              }
           else
               $this->LastMsg = "Voucher is not Active! <br>";
                            return false;
   }
}
?>