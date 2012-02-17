<?php
/*************************************************
* @Author: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
* Replacement Class used to manage voucher replacements
**************************************************/
define( OVERRIDE_STATUS_PENDING,1);
define( OVERRIDE_STATUS_ALLOW,2);
define( OVERRIDE_STATUS_PROCESSED,3);
define( OVERRIDE_STATUS_DECLINED,4);


//database access file
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/BRMIntegration.php");
include_once(CLASSDIR."/Voucher.php");
include_once(CLASSDIR."/POS.php");


Class Replacement
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
   private $conf;
   private $mlog;

/**
 * Contructor
 *
 */
   public function  __construct()
   {
        $this->db=new DBAccess();
        $this->conf=new config();
        $this->mlog=new Logging();
   }


 /** function to add replacement information
   * Returns true upon successful insertion else returns false
   */
   public function addReplacement()
   {
     if(!$this->preprocesReplacement())
     {
        return false;
     }
     $vch_obj = new Voucher();

     $return_vouch_id = $vch_obj->getVoucherID($_POST['replace_voucher_serial']);

     foreach($_POST['voucher_serial'] as $arr)
         $issued_voucher[] = $arr;

     $issued_vouch_id = $vch_obj->getVoucherID($issued_voucher[0]);

    if($this->checkPriorReplacement($return_vouch_id))
        $recursive_replacement=1;
     else
         $recursive_replacement=0;

     $insert=" returned_voucher_id,
               issued_voucher_id,replacement_date,
               replaced_by,recursive_replacement,replacement_reason";
     
     $values="$return_vouch_id,$issued_vouch_id,current_timestamp ,".$_SESSION['user_id'].", $recursive_replacement,'".$_POST['replace_reason']."'";
     //$GLOBALS['_DB']->DBlink->Execute("insert into ".$this->conf->db_prefix."replacement ($insert) values ($values)");
     $result = $this->db->InsertRecord(REPLACEMENT_T,$insert,$values);
     
     //$query = "select * from ".REPLACEMENT_T." where issued_voucher_id = '".$issued_vouch_id."'";
     if($result)
     {
          return true;
     }

      $this->LastMsg.="Insertion failed <br>";
      return false;
   }
/**  function to preprocess information taken from form through post method
   * Returns true form get validated else returns LastMsg containing error
   * messages
   */
   public function preprocesReplacement()
   {
       if($_POST==null)
           {
                return false;
           }
        if(empty($_POST['replace_voucher_serial']) || is_null($_POST['replace_voucher_serial']))
         {
            
           $this->LastMsg.="Please enter returned voucher serial <br>";
         }
         foreach($_POST['voucher_serial'] as $arr)
   			$vouchSerials[]=$arr;
        if(count($vouchSerials)> 1)
   	{
   	   $this->LastMsg.= "Please select only one voucher";
        }
       
         $query = "select * from ".ITEM_INFO_T."
                   where voucher_serial ='".$_POST['replace_voucher_serial']."'";
         if($this->db->CustomQuery($query)==null)
         {
            $this->LastMsg.="returned voucher serial does not exist <br>";
         }

         foreach($vouchSerials as $voucher)
             if($voucher==$_POST['replace_voucher_serial'])
                {
                       $this->LastMsg.= "Vouchers Serials Same";
                }
                
         $vch_obj = new Voucher();
         $replace_vouch_id = $vch_obj->getVoucherID($_POST['replace_voucher_serial']);
         $replace_denomination = $vch_obj->getVoucherDenomination($replace_vouch_id);
         if(!($replace_denomination==$_POST['voucher_denomination']))
         {
           $this->LastMsg.="Vouchers are not of same denomination";
         }
         
         if(empty($_POST['replace_reason']) || is_null($_POST['replace_reason']))
         {
           $this->LastMsg.="Please select Replace reason <br>";
         }

         
        // TODO: Validate replacement from provisioning

         if($this->LastMsg!=null)
		return false;

	return true;
       }
/**  Function to check prior replacement case
   * Returns true if prior replacement exist against current return
   */
   public function checkPriorReplacement($return_vouch_id)
   {
       $query="select *
               from ".REPLACEMENT_T."
               where issued_voucher_id = $return_vouch_id";
       
       if(($replaced=$this->db->CustomQuery($query))!=null)
        {
            return true;
        }
        return false;
   }

   public function updateReplaceOverride($replaced_vouch_id)
   {
        $query = "update ".REPLACEMENT_OVERIDE_T."
                  set override = ".OVERRIDE_STATUS_PROCESSED."
                  where voucher_id = $replaced_vouch_id";
        if($this->db->CustomModify($query))
        {
            return true;
        }
        return false;
   }

   public function getrepOverrideEntries()
   {
       $query = " select rep_id,voucher_id,voucher_serial,date_added,voucher_denomination,override,raised_by,by_channel,
       DECODE(override,1,'Pending',2,'Replacement Allowed',3,'Processed',4,'Replacement Declinded') status
       from ".REPLACEMENT_OVERIDE_T."";
       $data=$this->db->CustomQuery($query);
       if($data!=null)
        {
         return $data;
        }
       $this->LastMsg."Information not found, Operation failed!";
        return false;
   }

   public function getrepoveridePendings()
   {
       $query = " select rep_id,voucher_id,voucher_serial,date_added,voucher_denomination,override,raised_by,by_channel
       from ".REPLACEMENT_OVERIDE_T."
       where override = ".OVERRIDE_STATUS_PENDING."";
       $data=$this->db->CustomQuery($query);
       if($data!=null)
        {
         return $data;
        }
       $this->LastMsg."Information not found, Operation failed!";
        return false;
   }

   public function getrepOverrideEntryDetail($rep_id)
   {
       $query = " select * from ".REPLACEMENT_OVERIDE_T." where rep_id = ".$rep_id."";
       $data=$this->db->CustomQuery($query);
       if($data!=null)
        {
         return $data;
        }

       $this->LastMsg."Information not found, Operation failed!";
        return false;
   }

   public function getallReplacements()
   {
       $query = " select * from ".REPLACEMENT_T." ";
       $data=$this->db->CustomQuery($query);
       if($data!=null)
        {
         return $data;
        }

       $this->LastMsg."Information not found, Operation failed!";
        return false;

   }

   public function allowreplacement($replaced_vouch_id)
   {
       $query = "update ".REPLACEMENT_OVERIDE_T."
                  set override = ".OVERRIDE_STATUS_ALLOW."
                  where voucher_id = $replaced_vouch_id";
       
        if($this->db->CustomModify($query))
        {
            return true;
        }
        return false;
   }

   public function declinereplacement($replaced_vouch_id)
   {
       $query = "update ".REPLACEMENT_OVERIDE_T."
                  set override = ".OVERRIDE_STATUS_DECLINED."
                  where voucher_id = $replaced_vouch_id";

        if($this->db->CustomModify($query))
        {
            return true;
        }
        return false;
   }
   

}
?>
