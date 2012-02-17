<?php
/*************************************************
* @Auhor: Aasim Naveed
* Aasim.Naveed@pk.wi-tribe.com
* Channel Class used to manage channel information
**************************************************/


//database access file
include_once("DBaccess.php");
Class Channel
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


 /** function to add channel information to database
   * Returns true upon successful insertion else returns false
   *
   */
   public function addChannel()
   {
      
     if(!$this->preProcesChannel())
     {
        return false;
     }
     $insert = "channel_name,
               channel_type_id,parent_channel_id,address,
               owner_name,user_defined_1,user_defined_2,user_defined_3,
               user_defined_4,user_defined_5,city,zone,region,creation_date";


     if(($region_details = $this->getDetails($_POST['region_id']))!=false)
     {
         $region_name = $region_details[0]['location_name'];
     }
     else
     {
         echo("operation failed");
        $this->LastMsg.="Selection failed <br>";
     }
     if(($city_details = $this->getDetails($_POST['city_id']))!=false)
     {
         $city_name = $city_details[0]['location_name'];
     }
     else
     {
         echo("operation failed");
        $this->LastMsg.="Selection failed <br>";
     }

        //format vals
        $vals="'".$_POST['channel_name']."' , '".$_POST['channel_type_id']."', '".$_POST['parent_channel_id']."'
             , '".$_POST['address']."' ,'".$_POST['owner_name']."'
             , '".$region_name."', '".$_POST['user_defined_2']."'
             , '".$city_name."', '".$_POST['user_defined_4']."'
             ,'".$_POST['user_defined_5']."', '".$_POST['city_id']
             ."', '".$_POST['zone_id']."', '".$_POST['region_id']."',CURRENT_DATE";

          //   print("insert into ".CHANNEL_T."($insert) values($vals)");
          //   $this->LastMsg.=$vals;
         if(($result=$this->db->InsertRecord($this->conf->db_prefix."channel", $insert, $vals))!=null)
            {
                $this->LastMsg.="Channel Successfully Added! <br>";
                return $result;
             }
          else{
               $this->LastMsg.="Insertion failed <br>";
               return false;
          }
             
   }
/**  function to preprocess information taken from form through post method
   * Returns true form get validated else returns LastMsg containing error
   * messages
   */
   public function preProcesChannel()
   {
        if($_POST==null)
           {
            return false;
           }

         $query = "select * from vims_channel where channel_name = '".$_POST['channel_name']."'";
         if(($GLOBALS['_DB']->CustomQuery($query))!=null)
         {
             $this->LastMsg="Channel with this name exists,please try other <br>";
         }
         
         if(empty($_POST['channel_name']) || is_null($_POST['channel_name']))
         {
           $this->LastMsg="Please enter channel name <br>";
         }

        // if(!preg_match("/^[a-zA-Z0-9_-]+$/", $_POST['channel_name']))
	//	{
       //    $this->LastMsg.="Channel name should not contain invalid characters <br>";
       //  }
       if(strlen($_POST['channel_name'])<3)
         {
           $this->LastMsg.="Channel name cannot be that small <br>";
         }

      if(empty($_POST['channel_type_id']) || is_null($_POST['channel_type_id']))
         {
           $this->LastMsg.="Please provide channel type <br>";
         }

       if(!(is_int((int)$_POST['channel_type_id'])))
         {
          $this->LastMsg.="Channel type is not integer value <br>";
         }

//         $query = "select * from channel_type
//                   where channel_type_id = '".$_POST['channel_type_id']."'";

 //        if($this->db->CustomQuery($query)==null)
   //      {
     //       $this->LastMsg."Channel_type_id not exist <br>";
       //  }
         if(empty($_POST['address']) || is_null($_POST['address']))
         {
            $this->LastMsg.="Please provide address <br>";
         }
         if(empty ($_POST['owner_name']) || is_null($_POST['owner_name']))
         {
            $this->LastMsg.="Please provide owner name <br>";
         }
         if(strlen($_POST['owner_name'])<3)
         {
            $this->LastMsg.=" Owner name cannot be that small <br>";
         }
         if(!preg_match("/[A-Za-z\'\"]/", $_POST['owner_name']))
         {
            $this->LastMsg.="Owner name subject to characters <br>";
         }
         if(empty($_POST['region_id']) || is_null($_POST['region_id']))
         {
           $this->LastMsg.="Please provide region <br>";
         }

         if(empty($_POST['city_id']) || is_null($_POST['city_id']))
         {
           $this->LastMsg.="Please provide city <br>";
         }

         if(empty($_POST['zone_id']) || is_null($_POST['zone_id']))
         {
           $this->LastMsg.="Please provide zone<br>";
         }

         if(empty($_POST['parent_channel_id']) || is_null($_POST['parent_channel_id']))
         {
           $this->LastMsg.="Please provide parent channel <br>";
         }
         
//        if(!(is_int((int)$_POST['region_id'])))
//         {
//          $this->LastMsg.="region type id is not integer value <br>";
//         }

//         $query = "select * from channel
//                  where parent_channel_id = '".$_POST['parent_channel_id']."'";
//         if($this->db->CustomQuery($query)==null)
//         {
//            $this->LastMsg."Parent_channel_id not exist <br>";
//         }

         if($this->LastMsg!=null)
		return false;

	return true;
       }
/**  Function to get channel name
   * Returns channel_name of given channel_id
   * @param channel_id
   */
   public function getChannelName($channel_id)
   {

      $query="select channel_name  from ".CHANNEL_T." where channel_id= ".$channel_id."";
      
       if(($channelname=$this->db->CustomQuery($query))!=null)
        {
         return $channelname[0]['channel_name'];

        }
        
       $this->LastMsg."Channel name not found <br>";
        return false;
   }
/*
* Get All channels type
*/
   public function getAllChannelTypes()
   {
       $query="select channel_type_id, channel_type_name From ".$this->conf->db_prefix."channel_type";
       if(($result=$this->db->CustomQuery( $query))!=null)
        {
            return $result;
        }

    	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
    	$this->LastMsg."Channel id not found <br>";
        return false;
   }

   public function getChannelTypeDetail($channel_type_id)
   {
        $query="select * From ".$this->conf->db_prefix."channel_type
                where channel_type_id = $channel_type_id";
        //print($query);
       if(($result=$this->db->CustomQuery( $query))!=null)
        {
            return $result;
        }

    	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
    	$this->LastMsg."Channel id not found <br>";
        return false;
       
   }

   /*
    * Get all the Channel name from the table channel
    */
   public function getAllChannels()
   {
      //$query="select c.channel_id, c.channel_name, c.address From ".$this->conf->db_prefix."channel c";
       $query=	" SELECT * From ".CHANNEL_TYPE_T." ct"
			   ." INNER JOIN ".CHANNEL_T." ch ON ct.channel_type_id = ch.channel_type_id";

      // $this->LastMsg."Helloosds";
       if(($result=$this->db->CustomQuery( $query))!=null)
        {
            return $result;
        }


    	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
    	$this->LastMsg."Channel not found! <br>";
        return false;

   }

   /*
    * get child channels for a specific channel ID
    */
   public function getChildChannel($channel_id) {

      // $query="select * from vims_channel, vims_channel_type  where parent_channel_id IN (select channel_id from vims_channel where channel_id = '".$channel_id. "') and  vims_channel_type.channel_type_id =  vims_channel.channel_type_id";
       $query="select * from ".CHANNEL_T." ch, ".CHANNEL_TYPE_T." ct  where ch.parent_channel_id IN (select channel_id from ".CHANNEL_T." where channel_id = '".$channel_id. "') and  ct.channel_type_id =  ch.channel_type_id and ch.channel_id!='".$channel_id. "'";

       if(($result=$this->db->CustomQuery( $query))!=null)
        {
            return $result;
        }


    	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
    	$this->LastMsg."Channel not found! <br>";
        return false;
   }

  

   /**  Function to get channel id
   * Returns channel_id of given channel_name
   * @param channel_name
   */


   public function getChannelId($channel_name)
   {
       $query="select channel_id
               from channel
               where channel_name= '".$_POST['channel_name']."'";
       if(($result=$this->db->CustomQuery($query))!=null)
        {
            return $result;
        }
       $this->LastMsg."Channel name not found <br>";
        return false;
   }

 /** Function to get channel details
   * Returns channel details of given channel_id
   * @param channel_id
   */
   public function getChannelDetails($channel_id)
   {
           	$query="select * from ".CHANNEL_T."
                        inner join vims_channel_type
                        on vims_channel.channel_type_id = vims_channel_type.channel_type_id
                       where channel_id='".$channel_id."'";
         if(($data=$this->db->CustomQuery($query))!=null)
		{
			return $data;
		}
        $this->LastMsg."Query failed, cannot get info <br>";
        return false;
   }

 /** Function to update channel information
   * Returns true upon successfull updation else returns false
   */
   public function updateChannelInfo()
   {
      if(!$this->preprocesChannel())
           {
                return false;
           }

      $query="select *
             from channel
             where channel_id= '".$_POST['channel_id']."'";

      if(($return=$this->db->CustomQuery($query))!=null)
      {
       $query="update channel set channel_name='".$_POST['channel_name']."',
	channel_type_id='".$_POST['channel_type_id']."',address='".$_POST['address']."',
	owner_name='".$_POST['owner_name']."',
	param_1='".$_POST['param_1']."',
	param_2='".$_POST['param_2']."',
	param_3='".$_POST['param_3']."',
	param_4='".$_POST['param_4']."',
	param_5='".$_POST['param_5']."' ,
	parent_channel_id='".$_POST['parent_channel_id']."'
	where channel_id='".$_POST['channel_id']."'
	";
           if($this->db->CustomModify($query))
	   {
                // Successfuly Record Modified
                return true;
            }
            $this->LastMsg.="Updatation failed <br>";
	     return false;
         }
         $this->LastMsg.="Channel id not found <br>";
         return false;
     }

     public function getRegions()
     {
         $query = "select * from ".LOCATIONS_HIE_T."
                   where location_type_id in (select GEO_TYPE_ID from ".GEOTYPE_T."
                   where lower(geo_type_name) = 'region')";
         
         if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }
            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     }

     public function getRegionCities($region_id)
     {
        $query = "select * from ".LOCATIONS_HIE_T."
                  where parent_location_id = ".$region_id." ";

         if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }
            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     }

     public function getCityZones($city_id)
     {
         $query = "select * from ".LOCATIONS_HIE_T."
                  where parent_location_id = ".$city_id."";
                  
         if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }
            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     }

     public function getZoneChannels($zone_id,$channel_type_id)
     {
         $query = "select * from  ".CHANNEL_T."
                   where zone = ".$zone_id."
                   and channel_type_id = ".$channel_type_id."";
                 
         if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }
            
            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     }

     public function getDetails($location_id)
     {
         $query = "select * from  ".LOCATIONS_HIE_T."
                   where location_id = ".$location_id."";

         if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }

            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     }

    public function getregionChannels($region_id,$channel_type_id)
     {
        $query = "select * from ".CHANNEL_T."
                  where user_defined_1 in (
                          select location_id
                          from ".LOCATIONS_HIE_T."
                          where parent_location_id in ( select location_id
                                                        from ".LOCATIONS_HIE_T."
                                                        where location_id = ".$region_id."))
                                                        and channel_type_id = ".$channel_type_id."";

          if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }

            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;  
     }

      public function getchannelRegion($channel_id)
     {
        $query = "select * from ".LOCATIONS_HIE_T."
                   where location_id in (select user_defined_1 from ".CHANNEL_T."
                    where channel_id in ($channel_id))";

            
          if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }

            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     }


     public function getShopByRegion($city)
     {
         $query = "select distinct(sd.shop_id)
                    as shop_id,shop_name,addr_city
                    from shop_salesid_mapping ssm
                    inner join shop_details sd
                    on ssm.shop_id = ssm.shop_id 
                    where lower(addr_city) like "."'%"."$city"."%'";
         
         $data = $this->db->CustomQuery($query);
          if($data!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }

            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     } 

     public function getregionshopids($city)
     {
         $query = "select distinct(sd.shop_id)
                    as shop_id,shop_name,addr_city
                    from shop_salesid_mapping ssm
                    inner join shop_details sd
                    on ssm.shop_id = ssm.shop_id 
                    where lower(addr_city) like "."'%"."$city"."%'";
         
         $data = $this->db->CustomQuery($query);
          if($data!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }

            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;
     }
     
	 public function getuserregionShops($user_id)
     {
         $query = "select * from vims_locations_hierarchy
                    where location_id = (
                                            select user_defined_3 from vims_channel where
                                            channel_id = ( select user_channel_id from users where users.user_id = ".$user_id."))";
         
         $city = $this->db->CustomQuery($query);
         $city = $city[0]['location_name'];
         $query = "select * from shop_details where lower(addr_city) like lower("."'%"."$city"."%')";
         //print($query);
         $data = $this->db->CustomQuery($query);
             if($data!=null)
                {
                    return $data;
                }
                else
                    $this->LastMsg = "Data not found";
                    return false;
     }

     public function getuserRegion($user_id)
     {
         $query = "select * from vims_locations_hierarchy
                    where location_id = (
                                            select region from vims_channel where
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
	 
	 public function getUserShop($user_id)
     { 
			$query = "	select * from users 
						inner join salespersonnel_details on lower(users.username) = lower(salespersonnel_details.userid)
						inner join sales_hierarchy on sales_hierarchy.child_salespersonnel_id = salespersonnel_details.salespersonnel_id
						where users.user_id = '$user_id'";
						//print $query;
			$data = $this->db->CustomQuery($query);
			if($data!=null)
			{
				return $data;
			}
			else
				$this->LastMsg = "Data not found";
			return false;
     }
	
	//added/modified by saad
	public function getRegionsLive()
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
		//$data[6]['region_id'] = 'test';
		return $data;
	}
    
	public function getChannelByRegionLive($region_id, $channel_type_id)
    {
		if($channel_type_id !="ALL" && $channel_type_id !== null )
		{
			$condition = "and channel_type_id = '$channel_type_id'";
		}
		
        $query = "select * from ".CHANNEL_T."
                  where lower(user_defined_3) = lower('$region_id') $condition";
		//print $query;
          if(($data = $this->db->CustomQuery($query))!=null)
	   {
                // Successfuly Retrived Data
                return $data;
            }

            $this->LastMsg.=" Operattion failed,could'nt get data <br>";
	     return false;  
     }
 
	public function getUserByChannel($channel_id,$user_team_id=null,$user_channel_type=null)
	{
            $condition='';
            if($user_team_id!=null)
                $condition = " and user_team_id = $user_team_id";
             if($user_channel_type!=null)
                $condition.= " and channel_type_id = $user_channel_type";
		$query = "select * from ".USER_T." inner join ".CHANNEL_T."
                                    on users.user_channel_id = vims_channel.channel_id
				  where user_channel_id = ".$channel_id." and status = 1
                                  --and user_role_id != 62
                                  $condition";
                
              $data = $GLOBALS['_DB']->CustomQuery($query);

             if($data==null)
                    {
                             $this->LastMsg.="Operation Failed <br>";
                                    return false;
                     }
                                    return $data;
	}


        public function getAllRetailerWH()
        {
            $query = "select *
                        from ".CHANNEL_T."
                            where channel_name like '%Retailer%WH%' AND channel_type_id = 1";
                  
            $data = $this->db->CustomQuery($query);
            
            if($data!=NULL)
            {
                return $data;
            }
            else
            $this->LastMsg.="Operation Failed <br>";
            return false;
        }


       public function getRegionalChannelsName($channel_type, $region_id)
       {
           $query=	" SELECT * From ".CHANNEL_TYPE_T." ct"
			   ." INNER JOIN ".CHANNEL_T." ch ON ct.channel_type_id = ch.channel_type_id
                               where ch.CHANNEL_TYPE_ID = $channel_type
                                AND ch.REGION = $region_id
                                AND ch.CHANNEL_NAME LIKE '%Retailer%'";
                                
           if(($result=$this->db->CustomQuery( $query))!=null)
            {
                return $result;
            }

         //   $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
            $this->LastMsg."Channel not found! <br>";
            return false;

       }

       public function getLocationDetails($location_id)
       {
           $query= " SELECT * From ".LOCATIONS_HIE_T."
                               where LOCATION_ID = $location_id";
                              
           if(($result=$this->db->CustomQuery( $query))!=null)
            {
                return $result;
            }

         //   $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
            $this->LastMsg."Channel not found! <br>";
            return false;
       }

       public function getRetailersChannel($channel_type_id, $region)
       {
           $query = "SELECT * From ".CHANNEL_T."
                               where CHANNEL_TYPE_ID = $channel_type_id
                                AND region = '$region'";
           
           if(($result=$GLOBALS['_DB']->CustomQuery($query))!=null)
            {
                return $result;
            }

         //   $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
            $this->LastMsg."Channel not found! <br>";
            return false;
       }

       public function getRegionLostShop($regionID)
       {
           if($regionID==null)
               {
                    $this->LastMsg = "Region ID is null";
                    return false;
               }
           
           $query = "select * from vims_channel
                        inner join vims_channel_type
                        on vims_channel.channel_type_id = vims_channel_type.channel_type_id
                        where region = $regionID and lower(channel_name) like lower('%Lost%') and channel_type_name like 'Shop'";
           
            if(($result=$GLOBALS['_DB']->CustomQuery($query))!=null)
            {
                return $result;
            }

             $this->LastMsg."Lost Shop not found! <br>";
             return false;
       }

       public function getAllLostInventoryShops()
       {
            $query = "select channel_id,channel_name from vims_channel
                        inner join vims_channel_type
                        on vims_channel.channel_type_id = vims_channel_type.channel_type_id
                        where lower(channel_name) like lower('%Lost%') and channel_type_name like 'Shop'";

            if(($result=$GLOBALS['_DB']->CustomQuery($query))!=null)
            {
                return $result;
            }

             $this->LastMsg."Lost Shop not found! <br>";
             return false;
       }
 }
 ?>
