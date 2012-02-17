<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Role
 *
 * @author PKRizwanAI
 * 
 */
class Role {
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
/**  function to preprocess information taken from form through post method
   * Returns true form get validated else returns LastMsg containing error
   * messages
   */
   public function preProcess() {

         if($_POST==null)
           {
            print "pre False";
            return false;
           }
         if(empty($_POST['role_name']) || is_null($_POST['role_name']))
         {
           $this->LastMsg="Please enter role name <br>";
         }
        if(!preg_match("/[A-Za-z\'\"]/", $_POST['role_name']))
         {
           $this->LastMsg.="Role should not contain invalid characters <br>";
         }
       if(strlen($_POST['role_name'])<3)
         {
           $this->LastMsg.="Role name cannot be that small <br>";
         }

      if(empty($_POST['parent_role_id']) || is_null($_POST['parent_role_id']))
         {
           $this->LastMsg.="Please select proper parent role name <br>";
         }
         
         if($this->LastMsg!=null)
		return false;

	return true;

   }

   /*
    * Add a new role with its parent role
    */
   public function addRole() {
   print "Rizwan";
   if(!$this->preProcess())
     { print "false";
        return false;
     }
      $query = "role_name,parent_role_id";
      $vals="'".$_POST['role_name']."' , '".$_POST['parent_role_id']."'";
       if(($result=$this->db->InsertRecord(USER_ROLES_T, $query, $vals))!=null)
            {
                $this->LastMsg.="Role Successfully Added! <br>";
                return true;
             }
          else{
               $this->LastMsg.="Insertion failed <br>";
               return false;
          }


   }

   /*
    * Select all the role for parent role selection
    */
   public function getAllRole() {
   
       $query="select *  From ".USER_ROLES_T."";
       if(($result=$this->db->CustomQuery( $query))!=null)
        {
            return $result;
        }
    	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
    	$this->LastMsg."Error while getting roles! <br>";
        return false;
   }

   public function getRoleDetails($role_id)
   {
       $query = "select * from ".USER_ROLES_T."
                   where role_id = $role_id";

       $data = $this->db->CustomQuery($query);

       if($data!=null)
       {
           return $data;
       }
       else
           $this->LastMsg="Operation Failed";
           return false;
   }
     public function getEditRole() {


       $query="select *  From ".USER_ROLES_T." where role_id=1";
       if(($result=$this->db->CustomQuery( $query))!=null)
        {
            return $result;
        }
    	$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
    	$this->LastMsg."Error while getting roles! <br>";
        return false;
   }

   public function getHeads($role_id)
        {
           $query = "select * from ".USER_T."
                    inner join ".CHANNEL_T."
                    on vims_channel.channel_id = users.user_channel_id
                    where user_role_id IN ( select parent_role_id from ".USER_ROLES_T." where role_id= $role_id)";
         // print($query);
          $roles = $GLOBALS['_DB']->CustomQuery($query);
            if(!$roles)
            {   
                $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"User: ".$_SESSION['user_id']."\r\nFailed to add: $start");
                $this->LastMsg."Operation Failed";
                            return false;
            }
                    return $roles;
        }

        public function checkParentRoleExists($role_id)
        {
            $query = "select parent_role_id from ".USER_ROLES_T." where role_id= $role_id";
           
            $parent_role = $GLOBALS['_DB']->CustomQuery($query);
            
            if($parent_role!=null)
                {
                    return true;
                }
             return false;
        }
}
?>