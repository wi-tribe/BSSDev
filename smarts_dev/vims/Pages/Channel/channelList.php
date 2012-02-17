<?
session_start("VIMS");
set_time_limit(700);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Channel.php");

//permission check
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'listchannel'))
{
	echo "Permission denied";
	exit;
}

//creating new object
$channel=new Channel();
$channel_id = $_SESSION['channel_id'];

if(!$result=$channel->getAllChannels())
{
	echo $channel->LastMsg."Can not Get All Channels Name!";
}
if(!$data=$channel->getChildChannel($channel_id))
{
    echo $channel->LastMsg."No Child Channels Exist!";
}

if((!$channelname=$channel->getChannelName($channel_id)))
{
    echo $channel->LastMsg."Channel not Exist!";
}


?>

<label>Child channels of <? echo "$channelname"?>
<br />
<br />

    <?php
 
    foreach ($data as $value) {
        echo " -->";
         print $value['channel_name'];
         echo"   ";
         print $value['channel_type_name'];
         echo"<br />";

    }

    ?>
    
</label>
<br />
<br />
<table width="1000" border="1" cellspacing="1" cellpadding="1">
  <tr>
    <th width="100" scope="col">Channel Name</th>
    <th width="100" scope="col">Channel Type</th>
    <th width="100" scope="col">Owner Name</th>
    <th width="100" scope="col">Address</th>
    <th width="150" scope="col">Channel Type Description</th>
  </tr>

   <?php
   // $row is array... foreach( .. ) puts every element
    // of $row to $cell variable
 foreach($result as $arr) {
 


    echo "<tr>";
    

    echo "<td>";
    print $arr['channel_name'];
    echo "</td>";

    echo "<td>";
    print $arr['channel_type_name'];
    echo "</td>";

    echo "<td>";
    print $arr['owner_name'];
    echo "</td>";

    echo "<td>";
    print $arr['address'];
    echo "</td>";
    
    

    echo "<td>";
    print $arr['channel_type_desc'];
    echo "</td>";

    echo "</tr>";
    
}

 ?>
 
 
</table>

