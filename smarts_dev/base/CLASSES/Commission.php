<?php
/*************************************************
* @Auhor: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
**************************************************/

define(STATUS_ACTIVE,1);
define(STATUS_EXPIRED,2);
define(STATUS_INACTIVE,3);

include_once(CLASSDIR."/Discount.php");
include_once(CLASSDIR."/Channel.php");

//database access file

class Commission
{
    /**
 * Store local class messages and errors
 *
 * @var string
 */

    public $LastMsg;
    private $db;
    private $mlog;
    private $conf;
    
/**
 * Store database connection pointer
 *
 * @var object
 */


/**
 * Contructor
 *
 */
   public function  __construct()
   {
        $this->db=new DBAccess();
        $this->mlog=new Logging();
        $this->conf=new config();
        
   }

   /**
 * Adds the commision rule values to DB from Commisionining Add Popup Form
 *
 */

   public function addcommissionRule()
   {
        if(!$this->preProcessCommission())
             {
                return false;
             }
             
            $insert = "channel_type_id,channel_id,region_id,city_id,zone_id,value,start_date,end_date";
            $vals="'".$_POST['channel_type_id']."' , '".$_POST['channel_id']."', '".$_POST['region_id']."'
                     , '".$_POST['city_id']."' ,'".$_POST['zone_id']."', '".$_POST['commission']."', CURRENT_DATE, null";

            if(($result = $this->db->InsertRecord(COMMISSION_T,$insert,$vals)))
            {
                return true;
            }

              $this->LastMsg.="Insertion failed <br>";
              return false;
   }

   private function preProcessCommission()
   {
        if($_POST==null)
           {
            return false;
           }
           
         if(empty($_POST['channel_type_id']) || is_null($_POST['channel_type_id']))
         {
           $this->LastMsg.="Please select channel type <br>";
         }
         
         if(empty($_POST['commission']) || is_null($_POST['commission']))
         {
           $this->LastMsg.="Please select commission value <br>";
         }

         if($this->LastMsg!=null)
		return false;
	return true;
   }

   public function getcommissionDetails($commisssion_id)
   {
        $query = " select * from ".COMMISSION_T."
                    where commission_id = ".$commisssion_id."";
                    
      if(($data=$this->db->CustomQuery($query))!=null)
        {
           return $data;
        }
         $this->LastMsg.="Operation failed, data not found";
         return false;
  }
   public function getallCommissions()
   {
       $query = " select
                   vcr.commission_id,vcr.channel_type_id,
                   vct.channel_type_name as channel_type_name,
                   reg.location_name as region,
                   city.location_name as city,
                   vzone.location_name as zone,
                   vc.channel_name as channel,
                   vcr.value,vcr.start_date,vcr.end_date,vcr.status

                    from
                    vims_commission_rules vcr
                    left outer join vims_channel_type vct
                    on vct.channel_type_id = vcr.channel_type_id
                    left outer join vims_locations_hierarchy reg
                    on reg.location_id = vcr.region_id
                    left outer join vims_locations_hierarchy city
                    on city.location_id = vcr.city_id
                    left outer join vims_locations_hierarchy vzone
                    on vzone.location_id = vcr.zone_id
                    left outer join vims_channel vc
                    on vcr.channel_id = vc.channel_id
                    where vcr.status = 1
                    ORDER BY CHANNEL,ZONE,CITY,REGION";

       if(($commisions=$this->db->CustomQuery($query))!=null)
        {
         return $commisions;
        }
       $this->LastMsg."Data not found <br>";
        return false;
   }

   public function updateCommission()
   {
         $query = "update ".COMMISSION_T." set
                                         status = '".$_POST['status']."'
                                         where commission_id = '".$_POST['commission_id']."' ";
                                         //print($query);
             $update = $GLOBALS['_DB']->CustomModify($query);

             if(!$update)
                 {
                    $this->LastMsg.="Operation Failed";
                    return false;
                 }
              return true;
       
   }

   public function getcommissionValue($channel_id,$channel_type_id,&$applied_to)
   {
       $ch_obj = new Channel();
       $query_ch = "select * from ".COMMISSION_T."
                    inner join ".CHANNEL_T."
                    on ".COMMISSION_T.".channel_id = ".CHANNEL_T.".channel_id
                    where ".COMMISSION_T.".channel_id = ".$channel_id."
                    and ".COMMISSION_T.".channel_type_id = ".$channel_type_id." ";
      
       if(($channel = $this->db->CustomQuery($query_ch))!=null)
        {

          $applied_to = 'Channel Wise/'.$channel['channel_name'];
          return $channel;
        }
      
        $query_z = "select * from ".COMMISSION_T." where zone_id
                   in (select zone from ".CHANNEL_T."
                        where channel_id = ".$channel_id.")
                        and ".COMMISSION_T.".channel_id is null
                        and ".COMMISSION_T.".channel_type_id = ".$channel_type_id."";

       
        if(($zone=$this->db->CustomQuery($query_z))!=null)
        {
            $detail = $ch_obj->getDetails($zone['zone_id']);
            $applied_to = 'Zone Wise/'.$detail['location_name'];
            return $zone;
        }

        $query_c = "select * from ".COMMISSION_T."
            where city_id in (select city from ".CHANNEL_T."
                                where channel_id = ".$channel_id.")
             and ".COMMISSION_T.".channel_id is null
             and ".COMMISSION_T.".zone_id is null
             and ".COMMISSION_T.".channel_type_id = ".$channel_type_id."";
        
        if(($city=$this->db->CustomQuery($query_c))!=null)
        {
          $detail = $ch_obj->getDetails($city['city_id']);
          $applied_to = 'City Wise/'.$detail['location_name'];
          return $city;
        }
        
        $query_r = "select * from ".COMMISSION_T."
                    where region_id in (select region from ".CHANNEL_T."
                                         where ".CHANNEL_T.".channel_id = ".$channel_id.")
                    and channel_id is null
                    and zone_id is null
                    and city_id is null
                    and ".COMMISSION_T.".CHANNEL_TYPE_ID = ".$channel_type_id."";
        //print("Region Wise: ".$query_r);
        if(($region=$this->db->CustomQuery($query_r))!=null)
        {
           $detail = $ch_obj->getDetails($region[0]['region_id']);
           $applied_to = 'Region Wise/'.$detail[0]['location_name'];
           return $region;
        }

         $query_n = "select * from ".COMMISSION_T."
                    where region_id is null
                    and channel_id is null
                    and zone_id is null
                    and city_id is null
                    and ".COMMISSION_T.".CHANNEL_TYPE_ID = ".$channel_type_id."";
// print("Country Wise: ".$query_n);
        if(($nwd=$this->db->CustomQuery($query_n))!=null)
        {
          $applied_to = 'NWD';
          return $nwd;
        }

        $this->LastMsg."Data not found <br>";
        return false;


   }
   
   public function checkExistance()
   {
            if($_POST['channel_id']==null && $_POST['region_id']==null  && $_POST['city_id']==null && $_POST['zone_id']==null && $_POST['commission']!=null)
                 {
                      $query = " select * from ".COMMISSION_T."
                                where channel_type_id = ".$_POST['channel_type_id']."
                                and status = ".STATUS_ACTIVE."
                                and channel_id is null
                                and region_id is null
                                and city_id is null
                                and zone_id is null";
                    
                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR NWD <BR>";
                           return true;
                        }
                         return false;  
                 }

            if($_POST['channel_id']!=null && $_POST['city_id']!=null && $_POST['zone_id']!=null && $_POST['region_id']!=null )
                {

                        $query = "select * from ".COMMISSION_T."
                           where region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city_id']."
                           and zone_id = ".$_POST['zone_id']."
                           and channel_id = ".$_POST['channel_id']."
                           and status = ".STATUS_ACTIVE."";
                      
                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR THIS CHANNEL <BR>";
                           return true;
                        }
                         return false;
                }

                if($_POST['city_id']!=null && $_POST['zone_id']!=null && $_POST['region_id']!=null)
                {
                    $query = " select * from ".COMMISSION_T."
                           where region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city_id']."
                           and zone_id = ".$_POST['zone_id']."
                           and status = ".STATUS_ACTIVE."";
                  
                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR THIS ZONE <BR>";
                           return true;
                        }
                         return false;
                }

               if($_POST['city_id']!=null && $_POST['region_id']!=null)
                {
                    $query = " select * from ".COMMISSION_T."
                           where region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city_id']."
                           and status = ".STATUS_ACTIVE."";
                        
                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR THIS CITY <BR>";
                           return true;
                        }
                         return false;
                }
                if($_POST['region_id']!=null)

                        $query = " select * from ".COMMISSION_T."
                           where region_id = ".$_POST['region_id']."
                           and status = ".STATUS_ACTIVE."
                           and city_id is null
                           and zone_id is null
                           and channel_id is null";
                         
                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR THIS REGION <BR>";
                           return true;
                        }
                         return false;
                    }
}



?>
