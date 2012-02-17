<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	
        include_once(UTIL_CLASSDIR."/Logging.php");
	include_once(UTIL_CLASSDIR.'/NPMWrapper.php');
	
	$mac = trim($_POST['mac_address']);
	$profile = trim($_POST['profile']);
	$net_op = new NPMWrapper();
	$log= new DB_Logging();

	$net_op->removeService($mac,$profile);
	$net_op->applyServicesToSubAcctName( $mac );
	
	$log_info['action']="remove_service";
	$log_info['details']=$mac."__".$profile;
	$log->logToDB($log_info);

	$result = $net_op->getSubscriberAccount($mac);
?>
	<table width='100%' >
        <tr >
            <td><strong>MAC ADDRESS</strong></td>
            <td ><?=$mac?></td>   
        </tr>
          <tr>
            <td valign="top"><strong>PROFILES</strong></td>
            <td>
				<table width="100%">
					<?       
                     foreach($result['subscriptionIds'] as  $profile)
                     {
						 $remove_check =false;
                         switch($profile)
                         {
							 case 'post_b_1024_256':
								$prof = '1Mbps download 256Kbps upload';
								$remove_check =false;
								break;
							case 'post_b_512_128':
								$prof = '512Kbps download 128Kbps upload';
								$remove_check =false;
								break;
							case 'Custody_Redirect':
								$prof = 'Account Freezed';
								$remove_check =false;
								break;
							case 'throttle_64_16':
								$prof = 'Throttle - 64Kbps Download 16Kbps upload';
								$remove_check =true;
								break;
							case 'Bill_info_redirect_post':
								$prof = 'Bill Info Page';
								$remove_check =true;
								break;
							case 'Nonpayment_Redirect':
								$prof = 'Non-Payment Redirection';
								$remove_check =false;
								break;
							case 'Suspension_Redirect':
								$prof = 'Suspension Redirection';
								$remove_check =true;
								break;
							case 'pullback_redirect': 
								$prof = 'Pullback period Expiry Notification';
								$remove_check =true;
								break;
							case 'Login_Redirect': 
								$prof = 'Non-Transparent Login Page';
								$remove_check =true;
								break;
							case 'welcome_survey_redirect_post': 
								$prof = 'welcome survey redirect';
								$remove_check =true;
								break;
							case 'Announcement_Redirect': 
								$prof = 'Announcement_Redirect';
								$remove_check =true;
								break;
							case 'Payment_Reminder_Redirect': 
								$prof = 'Payment_Reminder_Redirect';
								$remove_check =true;
								break;
							default:
							 $prof = $profile;
							 break;
								
                         }
                    ?>
                        <tr class="textboxBur">
                            <td><?=$prof?></td>
                            <? if($remove_check)
							{ ?>
                            	<td align="left"><input type="button" value="remove" class="button" onclick="javascript:removeProfile('<?=$mac?>','<?=$profile?>');";/></td>
                            <? } ?>
                        </tr>
                    <? }?>
        		</table>
         	</td>       
		</tr>
		 <tr class="textboxBur">
			<td colspan="2" ><STRONG> BEAWARE! EACH OF YOUR ACTION IS BEING LOGGED :)</STRONG></td>
		</tr>
	</table>
