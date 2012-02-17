<?php
/*************************************************
* @Auhor: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
**************************************************/

define(STATUS_ACTIVE,1);
define(STATUS_EXPIRED,2);
define(STATUS_INACTIVE,3);

define(WEEK_PERIOD,1);
define(MONTH_PERIOD,2);
define(YEAR_PERIOD,3);

include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Channel.php");

//database access file
class Rental
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
 * Adds the rental rule values to DB from Rental Add Popup Form
 *
 */
   public function addrentalRule()
   {
        if(!$this->preProcessRental())
             {
                return false;
             }

            $insert = "channel_type_id,action_date,action_by,channel_id,region_id,city_id,zone_id,value,start_date,end_date,period";
            $vals="".$_POST['channel_type_id'].",CURRENT_DATE,".$_SESSION['user_id'].", '".$_POST['channel_id']."', '".$_POST['region_id']."'
                     , '".$_POST['city_id']."' ,'".$_POST['zone_id']."', '".$_POST['rental_value']."',CURRENT_DATE , null,'".$_POST['period']."'";

            //print("insert into ".RENTAL_RULE_T."($insert) values($vals)");
            if(($result = $this->db->InsertRecord(RENTAL_RULE_T,$insert,$vals)))
            {
                return true;
            }

              $this->LastMsg.="Insertion failed <br>";
              return false;
   }

   private function preProcessRental()
   {
        if($_POST==null)
           {
            return false;
           }
       if($_POST['rental_value']==null)
       {
           $this->LastMsg = "Rental Value Cannot be Null <br>";
       }
       
       if($_POST['rental_value']!=null)
       {
           if($_POST['rental_value'] < 0)
           {
               $this->LastMsg = "Rental Value Must be Non Negative Integer Value <br>";
           }

           if(!is_numeric($_POST['rental_value']))
           {
               $this->LastMsg = "Rental Value Must be Non Negative Integer Value <br>";
           }
       }

         if(empty($_POST['channel_type_id']) || is_null($_POST['channel_type_id']))
         {
           $this->LastMsg.="Please select channel type <br>";
         }

         if(empty($_POST['period']) || is_null($_POST['period']))
         {
           $this->LastMsg.="Please give rule period <br>";
         }

         if($this->LastMsg!=null)
		return false;
	return true;
   }

   public function rentalUpdate()
   {
             if(!$this->preProcessRental())
             {
                return false;
             }

             $query = "update ".RENTAL_RULE_T." set
                                         value = '".$_POST['rental_value']."',
                                         status = '".$_POST['status']."',
                                         period = '".$_POST['period']."',
                                         end_date = CURRENT_DATE
                                         where rule_id = '".$_POST['rule_id']."' ";
                                         //print($query);
             $update = $GLOBALS['_DB']->CustomModify($query);

             if(!$update)
                 {
                    $this->LastMsg.="Operation Failed";
                    return false;
                 }
              return true;
   }

   public function getrentalRuleDetails($rental_rule_id)
   {
        $query = " select * from ".RENTAL_RULE_T."
                    where rule_id = ".$rental_rule_id."";

      if(($data=$this->db->CustomQuery($query))!=null)
        {
           return $data;
        }
         $this->LastMsg.="Operation failed, data not found";
         return false;
   }

   public function getrentalPayableDetails($rental_id)
   {
        $query = " select * from ".RENTAL_T."
                    where rental_id = ".$rental_id."";

      if(($data=$this->db->CustomQuery($query))!=null)
        {
           return $data;
        }
         $this->LastMsg.="Operation failed, data not found";
         return false;
   }

   public function updateRentalPayableStatus()
   {
        $query = "update ".RENTAL_T." set
                                         status = '".$_POST['status']."',
                                         action_date = CURRENT_DATE
                                         where rental_id = '".$_POST['rental_id']."' ";
                                         //print($query);
             $update = $GLOBALS['_DB']->CustomModify($query);

             if(!$update)
                 {
                    $this->LastMsg.="Operation Failed";
                    return false;
                 }
              return true;
   }
   public function getallRentalRules()
   {
       $query = " select
                   rental.rule_id,rental.channel_type_id,
                   vct.channel_type_name as channel_type_name,
                   reg.location_name as region,
                   city.location_name as city,
                   vzone.location_name as zone,
                   vc.channel_name as channel,
                   rental.value,rental.start_date,rental.end_date,decode(rental.status,1,'Active',2,'Expired',3,'InActive') status
                   ,decode(period,1,'WEEK',2,'MONTH',3,'YEAR') period

                    from
                    ".RENTAL_RULE_T." rental
                    left outer join vims_channel_type vct
                    on vct.channel_type_id = rental.channel_type_id
                    left outer join vims_locations_hierarchy reg
                    on reg.location_id = rental.region_id
                    left outer join vims_locations_hierarchy city
                    on city.location_id = rental.city_id
                    left outer join vims_locations_hierarchy vzone
                    on vzone.location_id = rental.zone_id
                    left outer join vims_channel vc
                    on rental.channel_id = vc.channel_id
                    where rental.status = 1
                    ORDER BY CHANNEL,ZONE,CITY,REGION";
                   //print($query);
       if(($rentals=$this->db->CustomQuery($query))!=null)
        {
         return $rentals;
        }
       $this->LastMsg."Data not found <br>";
        return false;
   }

   public function getrentalValue($channel_id,$channel_type_id,&$applied_to)
   {
       $ch_obj = new Channel();
       $query_ch = "select * from ".RENTAL_RULE_T."
                    inner join ".CHANNEL_T."
                    on ".RENTAL_RULE_T.".channel_id = ".CHANNEL_T.".channel_id
                    where ".RENTAL_RULE_T.".channel_id = ".$channel_id."
                    and ".RENTAL_RULE_T.".channel_type_id = ".$channel_type_id."
                    and status=1";

       if(($channel = $this->db->CustomQuery($query_ch))!=null)
        {
          $applied_to = 'Channel Wise/'.$channel['channel_name'];
          return $channel;
        }

        $query_z = "select * from ".RENTAL_RULE_T." where zone_id
                   in (select zone from ".CHANNEL_T."
                        where channel_id = ".$channel_id.")
                        and ".RENTAL_RULE_T.".channel_id is null
                        and ".RENTAL_RULE_T.".channel_type_id = ".$channel_type_id."
                        and status=1";


        if(($zone=$this->db->CustomQuery($query_z))!=null)
        {
            $detail = $ch_obj->getDetails($zone['zone_id']);
            $applied_to = 'Zone Wise/'.$detail['location_name'];
            return $zone;
        }

        $query_c = "select * from ".RENTAL_RULE_T."
            where city_id in (select city from ".CHANNEL_T."
                                where channel_id = ".$channel_id.")
             and ".RENTAL_RULE_T.".channel_id is null
             and ".RENTAL_RULE_T.".zone_id is null
             and ".RENTAL_RULE_T.".channel_type_id = ".$channel_type_id."
             and status=1";

        if(($city=$this->db->CustomQuery($query_c))!=null)
        {
          $detail = $ch_obj->getDetails($city['city_id']);
          $applied_to = 'City Wise/'.$detail['location_name'];
          return $city;
        }

        $query_r = "select * from ".RENTAL_RULE_T."
                    where region_id in (select region from ".CHANNEL_T."
                                         where ".CHANNEL_T.".channel_id = ".$channel_id.")
                    and channel_id is null
                    and zone_id is null
                    and city_id is null
                    and ".RENTAL_RULE_T.".CHANNEL_TYPE_ID = ".$channel_type_id."
                    and status=1";
        //print("Region Wise: ".$query_r);
        if(($region=$this->db->CustomQuery($query_r))!=null)
        {
           $detail = $ch_obj->getDetails($region[0]['region_id']);
           $applied_to = 'Region Wise/'.$detail[0]['location_name'];
           return $region;
        }

         $query_n = "select * from ".RENTAL_RULE_T."
                    where region_id is null
                    and channel_id is null
                    and zone_id is null
                    and city_id is null
                    and ".RENTAL_RULE_T.".CHANNEL_TYPE_ID = ".$channel_type_id."
                    and status=1";
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
 if($_POST['channel_id']==null && $_POST['region_id']==null  && $_POST['city_id']==null && $_POST['zone_id']==null && $_POST['rental_value']!=null)
                 {
                      $query = " select * from ".RENTAL_RULE_T."
                                where channel_type_id = ".$_POST['channel_type_id']."
                                and status = ".STATUS_ACTIVE."
                                and channel_id is null
                                and region_id is null
                                and city_id is null
                                and zone_id is null";

                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="RENTAL VALUE EXISTS FOR NWD <BR>";
                           return true;
                        }
                         return false;
                 }

            if($_POST['channel_id']!=null && $_POST['city_id']!=null && $_POST['zone_id']!=null && $_POST['region_id']!=null )
                {

                        $query = "select * from ".RENTAL_RULE_T."
                           where region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city_id']."
                           and zone_id = ".$_POST['zone_id']."
                           and channel_id = ".$_POST['channel_id']."
                           and status = ".STATUS_ACTIVE."";

                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="RENTAL VALUE EXISTS FOR THIS CHANNEL <BR>";
                           return true;
                        }
                         return false;
                }

                if($_POST['city_id']!=null && $_POST['zone_id']!=null && $_POST['region_id']!=null)
                {
                    $query = " select * from ".RENTAL_RULE_T."
                           where region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city_id']."
                           and zone_id = ".$_POST['zone_id']."
                           and status = ".STATUS_ACTIVE."";

                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="RENTAL VALUE EXISTS FOR THIS ZONE <BR>";
                           return true;
                        }
                         return false;
                }

               if($_POST['city_id']!=null && $_POST['region_id']!=null)
                {
                    $query = " select * from ".RENTAL_RULE_T."
                           where region_id = ".$_POST['region_id']."
                           and city_id = ".$_POST['city_id']."
                           and status = ".STATUS_ACTIVE."";

                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="RENTAL VALUE EXISTS FOR THIS CITY <BR>";
                           return true;
                        }
                         return false;
                }
                if($_POST['region_id']!=null)

                        $query = " select * from ".RENTAL_RULE_T."
                           where region_id = ".$_POST['region_id']."
                           and status = ".STATUS_ACTIVE."
                           and city_id is null
                           and zone_id is null
                           and channel_id is null";

                      if(($exist=$this->db->CustomQuery($query))!=null)
                        {
                           $this->LastMsg.="RENTAL VALUE EXISTS FOR THIS REGION <BR>";
                           return true;
                        }
                         return false;
                    }

   public function getRentalPayableRetailerbyDate($start_date,$end_date)
    {
                $query = "select USER_ID,FIRST_NAME,LAST_NAME,CHANNEL_TYPE_ID,USERS.CREATION_DATE as USER_CREATION_DATE,USERS.PARENT_USER_ID
                            ,USERS.STATUS as USER_STATUS,TO_DATE(TO_CHAR(VIMS_CHANNEL.CREATION_DATE,'YYYY-MM-DD'),'YYYY-MM-DD') as CHANNEL_CREATION_DATE,USERS.USER_CHANNEL_ID as CHANNEL_ID,
                            VIMS_CHANNEL.REGION as REGION,VIMS_CHANNEL.CITY as CITY,VIMS_CHANNEL.zone as zone
                            from users
                    inner join CFG_USER_ROLES
                    on USERS.USER_ROLE_ID = CFG_USER_ROLES.ROLE_ID
                    inner join VIMS_CHANNEL
                    on USERS.USER_CHANNEL_ID = VIMS_CHANNEL.CHANNEL_ID
                    inner join VIMS_CHANNEL_TYPE
                    on VIMS_CHANNEL.CHANNEL_TYPE_ID = VIMS_CHANNEL_TYPE.CHANNEL_TYPE_ID
                    inner join VIMS_INVOICE
                    on users.user_id = vims_invoice.order_by
                    where CFG_USER_ROLES.ROLE_NAME like '%Retailer%'
                    and TO_DATE(TO_CHAR(VIMS_CHANNEL.CREATION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') >= TO_DATE('".$start_date."','MM/DD/YYYY')
                    and TO_DATE(TO_CHAR(VIMS_CHANNEL.CREATION_DATE,'MM/DD/YYYY'),'MM/DD/YYYY') <= TO_DATE('".$end_date."','MM/DD/YYYY')
                    and  USERS.STATUS=1 and vims_invoice.discount_value = '5000'
                    and VIMS_CHANNEL.CHANNEL_ID not in (select CHANNEL_ID from VIMS_RENTAL
                                                where TO_CHAR(VIMS_RENTAL.FOR_MONTH,'MMYYYY')=TO_CHAR(sysdate, 'MMYYYY'))
                    and (select COUNT(*) from VIMS_RENTAL
                    where CHANNEL_ID = VIMS_CHANNEL.channel_id ) < 2
                    ";
                     //print($query);
                   $LastMonthsRetailers = $GLOBALS['_DB']->CustomQuery($query);
                   
                    if($LastMonthsRetailers!=null)
                    {
                        return $LastMonthsRetailers;
                    }
                    else
                        return false;
    }
  

    public function getMonthRentalPayables($start_date,$end_date)
    {
        $query="select RENTAL_ID,USER_ID,FIRST_NAME,LAST_NAME,VR.CHANNEL_ID,VR.CHANNEL_TYPE_ID, PAYABLE_AMOUNT,CHANNEL_NAME ,
                TO_CHAR(VR.FOR_MONTH,'MON-YYYY') as FOR_MONTH,CREATION_DATE,decode(status,'0','UnPaid','1','Paid') as status,ACTION_DATE
                    from VIMS_RENTAL VR
                inner join VIMS_CHANNEL on VR.CHANNEL_ID = VIMS_CHANNEL.CHANNEL_ID
                where TO_CHAR(FOR_MONTH,'YYYYMM') >= TO_CHAR(TO_DATE('".$start_date."','MM/DD/YYYY'),'YYYYMM') and
                TO_CHAR(FOR_MONTH,'YYYYMM') <= TO_CHAR(TO_DATE('".$end_date."','MM/DD/YYYY'),'YYYYMM')
                order by for_month,status desc";

       // print($query);
        $oncePaidRetailers = $GLOBALS['_DB']->CustomQuery($query);

        if($oncePaidRetailers!=null)
        {
            return $oncePaidRetailers;
        }
        else
            return false;
    }

   public function getChannelRentalStats($channel_id)
   {
       $query = "select CHANNEL_ID,COUNT(*) as count from VIMS_RENTAL
                where CHANNEL_ID not in (select CHANNEL_ID from VIMS_RENTAL
                      where TO_CHAR(FOR_MONTH,'MMYYYY')=TO_CHAR(sysdate, 'MMYYYY'))
                and channel_id = $channel_id
                group by CHANNEL_ID
                having COUNT(*)<2";
        $result = $GLOBALS['_DB']->CustomQuery($query);
        if($result!=null)
        {
            return $result;
        }
        return false;   
   }
}

   
?>
