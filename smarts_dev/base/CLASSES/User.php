<?
include_once("../../vims_config.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/User.php");

Class User //extends VimsModule
{


/**
 * Store local class messages and errors
 *
 * @var string
 */
    public $LastMsg=null;
    
	
	public function __construct()
	{
           $this->log = new Logging();
	}
	
	public function Login($user_info)
	{
		$query = "select * from ".USER_T." where lower(username) = lower('".$user_info['username']."') and password = '".$user_info['password']."' and status = 1";
		$user = $GLOBALS['_DB']->CustomQuery($query);
		
		if(!$user)
                {   $this->LastMsg = "Invalid login information / Account Inactive";
                    $this->log->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR: ".$_SESSION['username']." Unable to Login: ".$user_info['username']);
                    unset($_SESSION);
                    return false;
                }
                $query = "select * from  salespersonnel_details where lower(userid)='".strtolower($user_info['username'])."'";
		$brm_user = $GLOBALS['_DB']->CustomQuery($query);
		$brm_user = $brm_user[0];

		$user = $user[0];
		$_SESSION['user_id']			= $user['user_id'];
		$_SESSION['username']			= $user['username'];
		$_SESSION['user_team_id'] 		= $user['user_team_id'];
		$_SESSION['user_role_id'] 		= $user['user_role_id'];
		$_SESSION['parent_user_id']             = $user['parent_user_id'];
		$_SESSION['channel_id'] 		= $user['user_channel_id'];
                $_SESSION['brm_csr_id'] 		= $brm_user['salespersonnel_id'];

                $this->log->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO: ".$_SESSION['username']." User Login Successful User: ".$_SESSION['username']." User ID: ".$user['user_id']." UserShop:".$user['user_channel_id']."");
		return true;
	}

        public function addUser()
        {
            if(!$this->preProcessUser())
             {
                return false;
             }
           // print_r($_POST);
             $insert = "first_name,last_name,username,password,email,
                         user_team_id,user_role_id,parent_user_id,user_channel_id,Creation_date,phoneno";
             $vals="'".$_POST['first_name']."' , '".$_POST['last_name']."', '".$_POST['user_name']."'
             , '".$_POST['password']."' ,'".$_POST['email']."'
             , '".$_POST['team_id']."', '".$_POST['user_role_id']."'
             , '".$_POST['parent_user_id']."', '".$_POST['user_channel_id']."',CURRENT_DATE,'".$_POST['phoneno']."'";

            // print("insert into users ($insert) values($vals)");
            if(($result=$GLOBALS['_DB']->InsertRecord(USER_T, $insert, $vals))!=null)
            {
                return true;
            }
          else{
               $this->LastMsg.="Insertion failed <br>";
               return false;
          }
        }

        public function updateUser()
        {
            if(!$this->preProcessUser())
             {
                return false;
             }
             $query = "update ".USER_T." set
                                         first_name = '".$_POST['first_name']."',
                                         last_name = '".$_POST['last_name']."',
                                         username =  '".$_POST['user_name']."',
                                         email = '".$_POST['email']."',
                                         user_team_id = ".$_POST['team_id'].",
                                         user_role_id = ".$_POST['user_role_id'].",
                                         parent_user_id = '".$_POST['parent_user_id']."',
                                         user_channel_id = ".$_POST['user_channel_id']."
                                         where user_id = '".$_POST['user_id']."' ";

             $update = $GLOBALS['_DB']->CustomModify($query);

             if(!$update)
                 {
                    $this->LastMsg.="Operation Failed";
                    return false;
                 }
              return true;
        }

        public function preProcessUser()
        {
            $ROLE = new Role();
            if($_POST==null)
            {
                 return false;
            }
            else if(empty($_POST['first_name']) || empty($_POST['first_name']))
            {
                  $this->LastMsg.="Please enter first name <br>";
            }
            else if(!preg_match("/[A-Za-z\'\"]/", $_POST['first_name']))
            {
                  $this->LastMsg.="Name should not contain invalid characters <br>";
            }
            else if(strlen($_POST['first_name'])<3)
             {
                   $this->LastMsg.="First name cannot be that small <br>";
             }
             if(empty($_POST['last_name']) || is_null($_POST['last_name']))
             {
                   $this->LastMsg.="Please enter last name <br>";
             }
             else if(!preg_match("/[A-Za-z\'\"]/", $_POST['first_name']))
             {
                    $this->LastMsg.="Name should not contain invalid characters <br>";
             }
             else if(strlen($_POST['last_name'])<3)
             {
                    $this->LastMsg.="Last name cannot be that small <br>";
             }
             if(empty($_POST['user_name']) || is_null($_POST['user_name']))
             {
                   $this->LastMsg.="Please enter username <br>";
             }

             
                 
                if(($_POST['action'])==Add)
                {
                    if($_POST['username']!=null)
                    {
                     $query = "select * from ".USER_T." where username = '".$_POST['user_name']."'";
                     if(($return = $GLOBALS['_DB']->CustomQuery($query))!=null)
                      {
                         $this->LastMsg.="Username already exists";
                      }
                    }
                    $query = "select * from ".USER_T." where email = '".$_POST['email']."'";
                    if(($return = $GLOBALS['_DB']->CustomQuery($query))!=null)
                    {
                         $this->LastMsg.="Email address already exists <br>";
                    }
                    if(empty($_POST['password']) || is_null($_POST['password']))
                     {
                      $this->LastMsg.="Please enter password <br>";
                     }
                     $data = $ROLE->getHeads($_POST['user_role_id']);
                     if($data)
                     {
                         if(empty($_POST['parent_user_id']))
                         {
                               $this->LastMsg.="Please select Head <br>";
                         }
                      }
                }
//             if(empty($_POST['email']) || is_null($_POST['email']))
//             {
//                   $this->LastMsg.="Please enter email <br>";
//             }
                if($_POST['email']!=null)
                {
                     if(!(eregi("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$", $_POST['email'])))
                     {
                            $this->LastMsg.="Please enter email in valid form <br>";
                     }
                }
             
             if(is_null($_POST['team_id']) ||empty ($_POST['team_id']))
             {
                   $this->LastMsg.="Please select department <br>";
             }
             if( is_null($_POST['user_role_id']) || empty ($_POST['user_role_id']))
             {
                   $this->LastMsg.="Please select designation <br>";
             }
             
             if( is_null($_POST['user_channel_id']) || empty($_POST['user_channel_id']))
             {
                   $this->LastMsg.="Please select Channel <br>";
             }
             
         if($this->LastMsg!=null)
		return false;

	return true;
        }
        
	public function getTeams()
	{
           $query = "select * from ".USER_TEAMS_T; 
           $teams = $GLOBALS['_DB']->CustomQuery($query);
            if(!$teams)
            {   $this->error = "Operation Failed";
                            return false;
            }
                    return $teams;
        }

        /**
         * @name getUserInfo
         * @param $user_id
         */
	public function getUserDetailInfo($user_id)
        {
           $query = "select u.user_id,u.first_name,u.last_name,u.username,u.email,c.role_name,u.user_role_id,u.parent_user_id,
                           v.channel_name,v.channel_type_id,u.user_channel_id,u.user_team_id,t.team_name,v.region,v.city,v.zone
                           ,u.status as status_id,decode(u.status,1,'Active','InActive') status
                        from ".USER_T." u inner join ".CHANNEL_T." v
                        on(u.user_channel_id=v.channel_id)
                        inner join ".USER_ROLES_T." c
                        on(u.user_role_id=c.role_id)
                        inner join ".USER_TEAMS_T." t
                        on(u.user_team_id=t.team_id)
                        where user_id = $user_id";
           //print($query);
           $data = $GLOBALS['_DB']->CustomQuery($query);

            if($data==null)
            {
            	$this->LastMsg = "No data";
                            return false;
            }
                    return $data ;
        }

        public function getAllUsersInfo()
        {
            $query = "select user_id,first_name,last_name,username,email,role_name,team_name,parent_user_id,channel_name,channel_id
                        from ".USER_T." u inner join ".CHANNEL_T." v
                        on(u.user_channel_id=v.channel_id)
                        inner join ".USER_ROLES_T." c
                        on(u.user_role_id=c.role_id)
                        inner join ".USER_TEAMS_T." t
                        on(u.user_team_id=t.team_id)";
            
            $employees = $GLOBALS['_DB']->CustomQuery($query);
            if(!$employees)
            {   $this->error = "Operation Failed";
                            return false;
            }
                    return $employees ;
        }

        public function getSubordinates($user_id)
        {
           $query = "select * from ".USER_T." 
                      inner join vims_channel
                       on users.user_channel_id = vims_channel.channel_id
                       where parent_user_id = $user_id and status =1 order by channel_name";
           $subordinates = $GLOBALS['_DB']->CustomQuery($query);
           //print_r($subordinates);
            if(!$subordinates)
            {   $this->error = "Operation Failed";
                            return false;
            }
                    return $subordinates ;
           }

        public function getSpecificUsers($department,$designation,$channel)
        {
              $query = "select * from ".USER_T."
                        where user_team_id = $department
                        and user_role_id= $designation
                        and user_channel_id= $channel";
            $users = $GLOBALS['_DB']->CustomQuery($query);
            if(!$users)
            {   $this->error = "Operation Failed";
                            return false;
            }
                    return $users;
        }


     public function getCurrentHead($parent_user_id)
        {
            $query = "select * from ".USER_T." where user_id = $parent_user_id ";
            $head = $GLOBALS['_DB']->CustomQuery($query);
            if(!$head)
              {
                    $this->LastMsg.="Operatoin Failed";
                    $this->log->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "Failed operation: $query");
                    return false;
              }
              return $head;
        }
	public function IsLoggedIn()
	{
		if(!isset($_SESSION['user_id']) )//|| !isset($_SESSION['channel_id']))
		{		
			//proper session is not set
                        //$this->log->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "User Log out / User Session timed out ");
			return false;
		}
         //$this->log->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "User already logged In UserID:".$_SESSION['user_id']."");
         return true;
	}
	
   /**
    * @name isChild
    * @param $parent_user_id
    * @param $child_user_id
    * verifies if a user is a child user of parent
    */
     public function isChild($parent_user_id,$child_user_id)
     {
      $query = "select count(*) as t
      from ".USER_T." where parent_user_id = $parent_user_id  and user_id=$child_user_id";
      $subordinates = $GLOBALS['_DB']->CustomQuery($query);

       if($subordinates[0]['t']<=0)
       {
       	$this->LastMsg = "Not a child";
                       return false;
       }
               return true ;
     }
     
	/**
         * @name getUserInfo
         * @param $user_id
         */
	public function getUserInfo($user_id)
        {
           $query = "select *
           from ".USER_T." where user_id = $user_id";
           $data = $GLOBALS['_DB']->CustomQuery($query);

            if($data==null)
            {
            	$this->LastMsg = "No data";
                            return false;
            }
                    return $data ;
        }

        public function preprocessPassword()
        {
             if($_POST==null)
            {
                 return false;
            }
            else if(empty($_POST['old_password']) || empty($_POST['old_password']))
            {
                  $this->LastMsg.="Please enter Old Password <br>";
            }

            else if(empty($_POST['new_password']) || empty($_POST['new_password']))
            {
                  $this->LastMsg.="Please enter New Password <br>";
            }

            else if(empty($_POST['c_new_password']) || empty($_POST['c_new_password']))
            {
                  $this->LastMsg.="Please enter Confirm New Password <br>";
            }

            if($this->LastMsg!=null)
		return false;

	return true;
            
        }

        public function changePassword()
        {
            if(!$this->preprocessPassword())
            {
                return false;
            }

           $query = "select *
           from ".USER_T." where user_id = ".$_SESSION['user_id']." and password = '".$_POST['old_password']."'";
           
           $data = $GLOBALS['_DB']->CustomQuery($query);
          
           if($data!=null)
            {
               if($_POST['new_password']!=$_POST['c_new_password'])
                {
                   $this->LastMsg.="Confirm New Password does not match with New Password <br>";
                   return false;
                }
                else
                    {
                      $query = "update ".USER_T." set
                                         password = '".$_POST['c_new_password']."'
                                         where user_id = ".$_SESSION['user_id']." ";
                      
                     $update = $GLOBALS['_DB']->CustomModify($query);
                     if(!$update)
                        {
                             $this->LastMsg.="Operation Failed <br>";
                                return false;
                         }
                                return true;
                    }
            }
            else
                $this->LastMsg="Please provide correct old password <br>";
                return false;
        }
        public function getchannelUsers($channel_id)
        {
            $query = "select * from ".USER_T."
                      where user_channel_id = ".$channel_id." ";

                      $data = $GLOBALS['_DB']->CustomQuery($query);
                     if($data==null)
                        {
                             $this->LastMsg.="Operation Failed <br>";
                                return false;
                         }
                                return $data;
        }
        //Get Retailers for all SE's related to a specefic TL
        public function getTLRetailers($TL_id)
        {
            $query = "select user_id,first_name,last_name,email,username,channel_name,
                        vims_channel.channel_type_id,user_team_id,user_role_id,role_name,parent_user_id,
                        user_channel_id,status as status_id,decode(status,1,'Active','InActive') status,vims_channel.address
                        from users
                        inner join
                        cfg_user_roles cur
                        on users.user_role_id = cur.role_id
                        inner join vims_channel
                        on users.user_channel_id = vims_channel.channel_id
                        where parent_user_id in (select user_id from users where parent_user_id = $TL_id) 
                        order by channel_name,first_name,last_name desc";
           
            // print($query);
            $data = $GLOBALS['_DB']->CustomQuery($query);
           
            if($data!=null)
            {
                return $data;
            }
            return false;
        }

        public function getshopSalesid($shop_id)
        {
            $query = "	select * from salespersonnel_details 
						inner join sales_hierarchy on sales_hierarchy.child_salespersonnel_id = salespersonnel_details.salespersonnel_id
						where shop_id ='$shop_id'
						";
               
                $data = $GLOBALS['_DB']->CustomQuery($query);
                     if($data==null)
                        {
                             $this->LastMsg.="Operation Failed <br>";
                                return false;
                         }
                                return $data;
        }

	public function getuserShop($user_id)
	{
		$query = "select * from shop_salesid_mapping where salespersonnel_id in (
					select salespersonnel_id from salespersonnel_details 
					where salespersonnel_details.userid in ( select username from users where users.user_id = ".$user_id."))";
	   
	$data = $GLOBALS['_DB']->CustomQuery($query);
	 if($data!=null)
	 {
		return $data;
	 }
	 else
		$this->LastMsg = "Data not found";
		return false; 
	}

	public function getBRMUserDetails($salesrep_id)
	{
		$query = "select * from salespersonnel_details
				 where salespersonnel_id = '$salesrep_id'";
                
		$data = $GLOBALS['_DB']->CustomQuery($query);
		if($data!=null)
		{
			return $data;
		}
		else
			$this->LastMsg = "Data not found";
		return false; 
	}

        public function getsalesrepID($user_id)
        {
            $query = "select * from salespersonnel_details
                                                where lower(salespersonnel_details.userid) in ( select username from users where users.user_id = ".$user_id.")
                                                        or lower(salespersonnel_details.addr_city) in (select location_name from vims_locations_hierarchy
                                                                        where location_id in (select user_channel_id from users
                                                                                              where users.user_id = ".$user_id."))";
            $data = $GLOBALS['_DB']->CustomQuery($query);
         if($data!=null)
         {
            return $data;
         }
         else
            $this->LastMsg = "Data not found";
            return false; 
            
            
        }
}

?>
