<?php
/*************************************************
* @Auhor: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
**************************************************/

define(STATUS_ACTIVE,1);
define(STATUS_EXPIRED,2);
define(STATUS_INACTIVE,3);


//database access file

class Discount
{
    /**
 * Store local class messages and errors
 *
 * @var string
 */

    public $LastMsg=null;
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

   public function adddiscountType()
   {
             if(!$this->preProcessDiscount())
              {
                 return false;
              }
              
            $insert = "discount_type_name,discount_type_desc";
            $vals="'".$_POST['dis_type_name']."','".$_POST['dis_type_desc']."'";
            
            if(($result = $this->db->InsertRecord(DISCOUNT_TYPE_T,$insert,$vals)))
            {
                return true;
            }

          $this->LastMsg.="Insertion failed <br>";
          return false;
   }

   public function adddiscount()
   {
        if(!$this->preProcessDiscount())
              {
                 return false;
              }

            $insert = "channel_type_id,discount_type_id,discount_name,value,region_id,city_id,zone_id,channel_id,start_date";
            $vals="'".$_POST['channel_type_id']."','".$_POST['discount_type_id']."','".$_POST['discount_name']."','".$_POST['discount']."'
                    ,'".$_POST['region_id']."','".$_POST['city_id']."','".$_POST['zone_id']."','".$_POST['channel_id']."',CURRENT_DATE";
            
            if(($result = $this->db->InsertRecord(DISCOUNT_T,$insert,$vals)))
            {
                return true;
            }

          $this->LastMsg.= "Insertion failed <br>";
          return false;   
   }

   public function getdiscDetails($disc_id)
   {
       $query = "select * from ".DISCOUNT_T."
                 where discount_id = ".$disc_id."";
         
       if(($dis_detail=$this->db->CustomQuery($query))!=null)
        {
         return $dis_detail;
        }

       $this->LastMsg."Data not found <br>";
        return false;  
       
   }
   
   private function preProcessDiscount()
   {
          if($_POST==null)
           {
              $this->LastMsg.="Form is empty <br>";
              return false;
           }
          if($_POST['myaction']=='add_dis_type')
             {
                 if($_POST['dis_type_name']==null)
                 {
                   $this->LastMsg.="Please enter discount type name <br>";
                 }
                 
                  if($_POST['dis_name']==null)
                 {
                   $this->LastMsg.="Please enter discount name <br>";
                 }
            }
          else
           { 
              if($_POST['discount_name']==null)
                 {
                   $this->LastMsg.="Please enter discount name <br>";
                 }

             $query = "select * from ".DISCOUNT_T." 
                 where discount_name = '".$_POST['discount_name']."'";
            if(($this->db->CustomQuery($query))!=null)
            {
               $this->LastMsg.=" Discount Name exists before!!";
            }

             if(empty($_POST['channel_type_id']) || is_null($_POST['channel_type_id']))
             {
               $this->LastMsg.="Please select channel type <br>";
             }

             if(empty($_POST['discount']) || is_null($_POST['discount']))
             {
               $this->LastMsg.="Please select discount value <br>";
             }
           }
                      
         if($this->LastMsg!=null)
		return false;
	return true;
   }

   public function getallDiscounts($channel_type_id)
   {
       $query = "select
                   vdr.discount_id as discount_id,vdt.discount_type_name as discount_type_name,vdr.status as status,
                   vdr.discount_name as discount_name,vct.channel_type_name as channel_type_name,
                   reg.location_name as region,
                   city.location_name as city,
                   vzone.location_name as zone,
                   ch.channel_name as channel,
                   vdr.value,vdr.start_date,vdr.end_date
                from

                vims_discount_rules vdr
                left outer join vims_discount_types vdt
                on vdt.discount_type_id = vdr.discount_type_id
                left outer join vims_locations_hierarchy reg
                on reg.location_id = vdr.region_id
                left outer join vims_locations_hierarchy city
                on city.location_id = vdr.city_id
                left outer join vims_locations_hierarchy vzone
                on vzone.location_id = vdr.zone_id
                left outer join vims_channel ch
                on ch.channel_id = vdr.channel_id
                left outer join vims_channel_type vct
                on vct.channel_type_id = vdr.channel_type_id
                where vdr.status = 1
                ORDER BY CHANNEL,ZONE,CITY,REGION";
               
       if(($commisions=$this->db->CustomQuery($query))!=null)
        {
         return $commisions;
        }
       $this->LastMsg."Data not found <br>";
        return false;
   }

   public function getalldiscTypes()
   {
        $query = "select * from ".DISCOUNT_TYPE_T."";

       if(($dis_types=$this->db->CustomQuery($query))!=null)
        {
         return $dis_types;
        }
        
       $this->LastMsg."Data not found <br>";
        return false;   
   }

   public function getdisctypeName($discount_type_id)
   {
       $query = "select * from ".DISCOUNT_TYPE_T."
                 where discount_type_id = ".$discount_type_id."";
       
       if(($dis_type=$this->db->CustomQuery($query))!=null)
        {
         return $dis_type;
        }

       $this->LastMsg."Data not found <br>";
        return false;
   }

/*
   public function checkExistance()
   {
            if($_POST['channel']==null && $_POST['region_id']==null  && $_POST['city']==null && $_POST['zone']==null && $_POST['discount_type']!=null && $_POST['discount']!=null && $_POST['channel_type']!=null )
                 {
                      $query = " select * from ".DISCOUNT_T."
                                where channel_type_id = ".$_POST['channel_type']."
                                and status = ".STATUS_ACTIVE."
                                and dis_type_id = ".$_POST['discount_type']."
                                and channel_id is null
                                and region_id is null
                                and city_id is null
                                and zone_id is null";
                       
                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="DISCOUNT TYPE VALUE EXISTS FOR NWD <BR>";
                           return true;
                        }
                         return false;
                 }
              

            if($_POST['channel']!=null && $_POST['city']!=null && $_POST['zone']!=null && $_POST['region_id']!=null )
                {

                        $query = "select * from ".DISCOUNT_T."
                           where channel_type_id = ".$_POST['channel_type']."
                           and dis_type_id = ".$_POST['discount_type']."
                           and region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city']."
                           and zone_id = ".$_POST['zone']."
                           and channel_id = ".$_POST['channel']."
                           and status = ".STATUS_ACTIVE."";

                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="DISCOUNT VALUE EXISTS FOR THIS CHANNEL <BR>";
                           return true;
                        }
                         return false;
                }

                if($_POST['city']!=null && $_POST['zone']!=null && $_POST['region_id']!=null)
                {
                    $query = " select * from ".DISCOUNT_T."
                           where channel_type_id = ".$_POST['channel_type']."
                           and dis_type_id = ".$_POST['discount_type']."
                           and region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city']."
                           and zone_id = ".$_POST['zone']."
                           and status = ".STATUS_ACTIVE."";

                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR THIS ZONE <BR>";
                           return true;
                        }
                         return false;
                }
//
//               if($_POST['city']!=null && $_POST['region_id']!=null)
//                {
//                    $query = " select * from ".DISCOUNT_T."
//                           where region_id = ".$_POST['region_id']."
//                           and city_id = ".$_POST['city']."
//                           and status = ".STATUS_ACTIVE."";
//
//                      if(($exist=$this->db->CustomQuery($query))!=null)
//                        {
//                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR THIS CITY <BR>";
//                           return true;
//                        }
//                         return false;
//                }
//                if($_POST['region_id']!=null)
//                    {
//                        $query = " select * from ".DISCOUNT_T."
//                           where region_id = ".$_POST['region_id']."
//                           and status = ".STATUS_ACTIVE."
//                           and city_id is null
//                           and zone_id is null
//                           and channel_id is null";
//
//                      if(($exist=$this->db->CustomQuery($query))!=null)
//                        {
//                           $this->LastMsg.="COMMISSION VALUE EXISTS FOR THIS REGION <BR>";
//                           return true;
//                        }
//                         return false;
//                    }
   }*
 * 
 */
}



?>
