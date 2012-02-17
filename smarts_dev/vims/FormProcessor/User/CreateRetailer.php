<?
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Channel.php");
include_once(CLASSDIR."/Logging.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addretailer'))
{
	echo "Permission denied";
	exit;
}

//print_r($_POST);
$usr = new User();
$channel=new Channel();
$log_obj = new Logging();

if($_POST['parent_user_id']==null)
{
    echo "Please select SE for Retailer";
    exit();
}
$fname = $_POST['first_name'];
$username  = $fname[0].".".$_POST['last_name'].rand(0,99);
$query = "select * from users where username like '".$username."'";
$exists = $GLOBALS['_DB']->CustomQuery($query);

while($exists!=null)
{
    $username  = $fname[0]."_".$_POST['last_name'].rand(100,200);
    $query = "select * from users where username like '".$username."'";
    $exists = $GLOBALS['_DB']->CustomQuery($query);
}

 $_POST['user_channel_id'] = -1;
 $_POST['user_name'] = $username;
 $preUsrChk = $usr->preProcessUser();

 if(!$preUsrChk)
 {
     echo $usr->LastMsg."<BR>";
     exit();
 }
 
$_POST['owner_name'] = $_POST['first_name'].' '.$_POST['last_name'];
$result = $channel->addChannel();
if(!$result)
{
    echo $channel->LastMsg;
    exit();
}
    $_POST['user_channel_id'] = $result;
    $_POST['user_name'] = $username;
    
    if($usr->addUser())
   {
        echo $usr->LastMsg."Retailer Successfully Added!";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "INFO".$_SESSION['username']." successfuly created retailer :".$_POST['channel_name']);
   }
   else
       {
            echo $usr->LastMsg."Error while adding new retailer";
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__, "ERROR".$_SESSION['username']." unable to create retailer ,".$usr->LastMsg);
       }
?>