<?php
session_start("VIMS");
set_time_limit(700);
include_once("../../vims_config.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'addretailer'))
{
	echo "Permission denied";
	exit;
}
// ************************** FOR POPULATION OF RETAILER CHANNEL **************************** //
$ROLE = new Role();
$CHANNEL=new Channel();
if(!$data=$CHANNEL->getAllChannelTypes())
{
	echo $CHANNEL->LastMsg."Cant not Get All Channels Type Name!";
}

   for($index = 0; $index < sizeof($data); $index++)
   {
       if($data[$index]['channel_type_name']=='Retailer')
       {
           $retailer []= $data[$index];
       }
   }

   $user_obj =  new User();
   $TL_info = $user_obj->getUserDetailInfo($_SESSION['user_id']);

   $TL_region = $CHANNEL->getLocationDetails($TL_info[0]['region']);
   $TL_city = $CHANNEL->getLocationDetails($TL_info[0]['city']);
   $TL_zone = $CHANNEL->getLocationDetails($TL_info[0]['zone']);
   $TL_channel = $CHANNEL->getChannelDetails($_SESSION['channel_id']);
   //print_r($TL_channel);

if(!$regions=$CHANNEL->getRegions())
{
	echo $CHANNEL->LastMsg."Can not Get All Channels Name!";
}
// ************************** THE END **************************** //

// ************************** FOR POPULATION OF RETAILER USER **************************** //
$teams = $_USER->getTeams();
$roles = $ROLE->getAllRole();
//$channels = $CHANNEL->getAllChannels();
$user_id = $_SESSION['user_id'];
if(($region = $CHANNEL->getuserRegion($user_id))!=false)
{
    $user_region = $region[0]['location_id'];
    $channels = $CHANNEL->getRetailersChannel($retailer[0]['channel_type_id'], $user_region);
}
else
{
    echo("operation failed!!");
}

foreach($roles as $arr) {
    if($arr['role_name'] == 'Retailer')
     {
        $roles = $arr;
     }
   }

$se = $_USER->getSubordinates($_SESSION['user_id']);

   foreach($teams as $arr) {
    if($arr['team_name'] == 'Sales')
     {
        $teams = $arr;
     }
   }

   // ************************** THE END **************************** //

?>
<script type="text/javascript">
 function Validateform()
 {
     
     if(document.getElementById('channel_name').value=='')
         {
            alert('You must enter Retailer Name');
            document.getElementById('channel_name').focus();
            return false;
         }
     if(document.getElementById('channel_name').value.length > 200)
         {
            alert('Retailer Name should not be more than 200 characters [including spaces]');
            document.getElementById('channel_name').focus();
            return false;
         }
     var iChars = "!@#$%^&*()+=[]\\\';/{}\":<>?`";
        for (var i = 0; i < document.getElementById('channel_name').value.length; i++) {
            if (iChars.indexOf(document.getElementById('channel_name').value.charAt(i)) != -1)
            {
            alert ("Please remove any special character "+iChars+" from Retailer name.");
            document.getElementById('channel_name').focus();
            return false;
            }
        }
        
        if(document.getElementById('address').value=='')
         {
            alert('You must enter Address');
            document.getElementById('address').focus();
            return false;
         }
         
     if(document.getElementById('address').value.length > 200)
         {
            alert('Address should not be more than 200 characters [including spaces]');
            document.getElementById('address').focus();
            return false;
         }

        for (var i = 0; i < document.getElementById('address').value.length; i++) {
            if (iChars.indexOf(document.getElementById('address').value.charAt(i)) != -1) {
            alert ("Please remove any special character "+iChars+" from Address");
            document.getElementById('channel_name').focus();
            return false;
            }
        }
        
//        if(document.getElementById('owner_name').value=='')
//         {
//            alert('You must enter Owner Name');
//            document.getElementById('owner_name').focus();
//            return false;
//         }
//
//     if(document.getElementById('owner_name').value.length > 50)
//         {
//            alert('Owner name should not be more than 50 characters [including spaces]');
//            document.getElementById('owner_name').focus();
//            return false;
//         }
//
//        for (var i = 0; i < document.getElementById('owner_name').value.length; i++) {
//            if (iChars.indexOf(document.getElementById('owner_name').value.charAt(i)) != -1) {
//            alert ("Please remove any special character "+iChars+" from Owner Name");
//            document.getElementById('owner_name').focus();
//            return false;
//            }
//        }
        
         if(document.getElementById('first_name').value=='')
         {
            alert('You must enter First Name');
            document.getElementById('first_name').focus();
            return false;
         }
         
     if(document.getElementById('first_name').value.length > 50)
         {
            alert('First name should not be more than 50 characters [including spaces]');
            document.getElementById('first_name').focus();
            return false;
         }

        for (var i = 0; i < document.getElementById('first_name').value.length; i++) {
            if (iChars.indexOf(document.getElementById('first_name').value.charAt(i)) != -1) {
            alert ("Please remove any special character "+iChars+" from First Name");
            document.getElementById('owner_name').focus();
            return false;
            }
        }
        
     if(document.getElementById('last_name').value=='')
     {
        alert('You must enter Last Name');
        document.getElementById('last_name').focus();
        return false;
     }
         
     if(document.getElementById('last_name').value.length > 50)
         {
            alert('Last name should not be more than 50 characters [including spaces]');
            document.getElementById('last_name').focus();
            return false;
         }

        for (var i = 0; i < document.getElementById('last_name').value.length; i++) {
            if (iChars.indexOf(document.getElementById('last_name').value.charAt(i)) != -1) {
            alert ("Please remove any special character "+iChars+" from Last Name");
            document.getElementById('owner_name').focus();
            return false;
            }
        }
        
        if (document.getElementById('email').value.length >0) {
	 var i = document.getElementById('email').value.indexOf("@")
	 var j =document.getElementById('email').value.indexOf(".",i)
	 var k =document.getElementById('email').value.indexOf(",")
	 var kk =document.getElementById('email').value.indexOf(" ")
	 var jj =document.getElementById('email').value.lastIndexOf(".")+1
	 var len =document.getElementById('email').value.length
 
 	if ((i>0) && (j>(1+1)) && (k==-1) && (kk==-1) && (len-jj >=2) && (len-jj<=3)) {
 	}
 	else {
 		alert("Please enter an exact email address.\n" +
		document.getElementById('email').value + " is invalid.");
		return false;
            }
        }
        
     var numericExpression = /^[0-9-]+$/; 
     if(document.getElementById('phoneno').value.length>0)
     {
	if(document.getElementById('phoneno').value.match(numericExpression)){
	}else{
		alert('Phone Number Should be Digits');
		 document.getElementById('phoneno').focus();
		return false;
	}
     }
        
     if(document.getElementById('phoneno').value.length > 40)
         {
            alert('Phone Number should not be more than 40 digits');
            document.getElementById('phoneno').focus();
            return false;
         }

        for (var i = 0; i < document.getElementById('phoneno').value.length; i++) {
            if (iChars.indexOf(document.getElementById('phoneno').value.charAt(i)) != -1) {
            alert ("Please remove any special character "+iChars+" from Phone");
            document.getElementById('owner_name').focus();
            return false;
            }
        }
        
        Submitform();
}
    
 function Submitform()
 {
     processForm( 'RetailerAdd','FormProcessor/User/CreateRetailer.php','MsgDiv' );
 }
</script>
<table width="100%">
    <tr>
    <td colspan="7" align="center" style="font-size:16px"><strong>Create Retailer</strong></td>
  </tr>
  <tr>
      <td>&nbsp;</td>
  </tr>
</table>
<table align="center" width="90%" border=0>
		<tr valign="top" class="textbox" >
			<td align="center">
              <!-----------------------------THIS PAGE------------------------------------->
                        <form name="RetailerAdd" id="RetailerAdd" method="post" action="">
                            <input type="hidden" name="channel_type_id" id="channel_type_id" value="<?=$retailer[0]['channel_type_id']?>" />
                            <input type="hidden" name="region_id" id="region_id" value="<?=$TL_info[0]['region']?>"/>
                            <input type="hidden" name="city_id" id="city_id" value="<?=$TL_info[0]['city']?>"/>
                            <input type="hidden" name="zone_id" id="zone_id" value="<?=$TL_info[0]['zone']?>"/>
                            <input type="hidden" name="user_role_id" id="user_role_id" value="<?=$roles['role_id']?>"/>
                            <input type="hidden" name="parent_channel_id" id="parent_channel_id" value="<?=$TL_channel[0]['channel_id']?>"/>
                            <input type="hidden" name="team_id" id="team_id" value="<?=$teams['team_id']?>"/>
                            <input type="hidden" name="password" id="password" value="wi-tribe"/>
                            
                          <table width="447" border="0" cellspacing=0" cellpadding="0">
                            <tr>
                              <td width="145"><label> Retailer Name:</label></td>
                              <td width="302"><label>
                                <input type="text" name="channel_name" id="channel_name">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label>Channel Type :</label>&nbsp;</td>
                              <td><label><?=$retailer[0]['channel_type_name']?>
                               <!-- <select name="channel_type_id" id="channel_type_id">
                                <option value="">Select Channel Type</option>
                                    <?php

                                    foreach($data as $arr) {
                                      ?>
                                    <option value="<?=$arr['channel_type_id']?>"> <?=$text.$arr['channel_type_name']?> </option>

                                    <? } ?>

                                </select>!-->
                              </label></td>
                            </tr>
                            <tr>
                              <td><label><label>Address:</label></label>&nbsp;</td>
                              <td><label>
                                <input type="text" name="address" id="address">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label>Region:</label>
                              &nbsp;</td>
                              <td><label><?=$TL_region[0]['location_name']?>
                                <!--<select name="region_id" id="region_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateCity.php','city_Div' );">

				<option value="">Select a Region</option>
				<?php
                                   //   print_r($result);
                                    foreach($regions as $arr) { ?>

                                    <option value="<?=$arr['location_id']?>"><?=$arr['location_name']?></option>

                                    <? } ?>
                                </select>!-->
                              </label></td>
                            </tr>

                            <tr>
                              <td><label> City:</label>&nbsp;</td>
                              <td> <?=$TL_city[0]['location_name']?><!--<div name="city_Div" id="city_Div">
                                    <select name="city_id" id="city_id" onchange="javascript:processForm( 'ChannelAdd','FormProcessor/Channel/populateZone.php','zone_Div' );">
                                    <option value="">Select a City</option>
                                    </select>
                                  </div>!-->
                              </td>
                            </tr>
                           <!-- <tr>
                              <td><label>Owner  Name:</label>&nbsp;</td>
                              <td><label>
                                <input type="text" name="owner_name" id="owner_name">
                              </label></td>
                            </tr>
                           -->

                            <tr>
                              <td width="145"><label> First Name:</label></td>
                              <td width="302"><label>
                                <input type="text" name="first_name" id="first_name">
                              </label></td>
                            </tr>
                            <tr>
                                <tr>
                              <td width="145"><label> Last Name:</label></td>
                              <td width="302"><label>
                                <input type="text" name="last_name" id="last_name">
                              </label></td>
                            </tr>
                            <tr>
                              <td width="145"><label> Email:</label></td>
                              <td width="302"><label>
                                <input type="text" name="email" id="email">
                              </label></td>
                            </tr>
                            <tr>
                              <td width="145"><label> Phone No:</label></td>
                              <td width="302"><label>
                                <input type="text" name="phoneno" id="phoneno">
                              </label></td>
                            </tr>
                            <tr>
                              <td><label> Designation:</label>&nbsp;</td>
                              <td><label><?=$roles['role_name']?><!--
                             <select name="user_role_id" id="user_role_id" onchange="javascript:processForm( 'RetailerAdd','FormProcessor/User/populateRetailerHead.php','headDiv' );">
                                <option value="">Select Designation</option>
				<?php
                                    foreach($roles as $arr) {
                                        if($arr['role_name'] == 'Retailer'){?>
                                    }
                                    <option selected ="selected" value="<?=$arr['role_id']?>"><?=$arr['role_name']?></option>
                                    <? } }?>
                                </select>!-->
                              </label></td>
                            </tr>
                            <tr>
                              <td><label> SE:</label>&nbsp;</td>
                              <td>
                                  <select name="parent_user_id" id="parent_user_id" >
                                    <option value="">Select SE</option>
                                    <?php
                                        foreach($se as $arr){?>
                                        <option value="<?=$arr['user_id']?>"><?=$arr['first_name']?>-<?=$arr['last_name']?>---<?=$arr['username']?></option>
                                        <? } ?>
                                  </select>
                              </td>
                            </tr>
                             

                          </table>
                            <table width="80%" align="center" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td align='right'><input name="FormAction" type='button' value='Add' onclick="Validateform();"></td>
				</tr>
			  </table>
                        </form>
              <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>
