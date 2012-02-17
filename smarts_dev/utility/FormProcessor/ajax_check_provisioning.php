<?php
session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();

        include_once(UTIL_CLASSDIR.'/NPMWrapper.php');
        include_once(CLASSDIR."/Logging.php");

        $log_obj = new Logging();
        $msg = null;

        $mac = trim($_POST['txt_search_term']);
	//	print "$mac<BR>";
        $net_op = new NPMWrapper();
	//	print_r($net_op);

        $result = $net_op->getSubscriberAccount($mac);
       
        if( $net_op->fault  || $net_op->error )
        {
            $msg = "MAC address not provisioned in the AAA";
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." MAC ".$mac." not provissioned in AAA");
        }
        else
        {
            if($result['faultcode'])
                {
                     $msg = "Unknown subscriber: ".$mac;
                     $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." MAC ".$mac." unknown subscriber");
                     echo $msg;
                     exit();
                }
             $msg = "<table width='100%' >";
             $msg .= "<tr><td><strong>MAC Address</strong></td><td> &nbsp; </td><td> ". $result['name'] ." </td></tr>";
             $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly check provisioning against MAC ".$mac);
             $profile_count = count($result['subscriptionIds']);
             
             for( $i = 0; $i < $profile_count; $i ++)
             {
                 $profile = $result['subscriptionIds'][$i];
                 switch($profile)
                 {
					 		case 'post_b_1024_256':
								$prof = '1Mbps download 256Kbps upload';
								break;
							case 'post_b_512_128':
								$prof = '512Kbps download 128Kbps upload';
								break;
							case 'post_b_256_64':
								$prof = '256Kbps download 64Kbps upload';
								break;
							case 'post_b_384_96':
								$prof = '384Kbps download 96Kbps upload';
								break;
							case 'post_b_768_192':
								$prof = '768Kbps download 192Kbps upload';
								break;
							case 'post_b_1536_384':
								$prof = '1536Kbps download 384Kbps upload';
								break;
							case 'unlimited_b_1536_384':
								$prof = 'Endless 1536Kbps download 384Kbps upload';
								break;
							case 'unlimit_b_512_128':
								$prof = 'Endless 512Kbps download 128Kbps upload';
								break;
							case 'Payment_Reminder_Redirect':
								$prof = 'Payment_Reminder_Redirect';
								break;
							case 'Custody_Redirect':
								$prof = 'Account Freezed';
								break;
							case 'throttle_64_16':
								$prof = 'Throttle - 64Kbps Download 16Kbps upload';
								break;
							case 'Bill_info_redirect_post':
								$prof = 'Bill Info Page';
								break;
							case 'Nonpayment_Redirect':
								$prof = 'Non-Payment Redirection';
								break;
							case 'Suspension_Redirect':
								$prof = 'Suspension Redirection';
								break;
							case 'pullback_redirect':
								$prof = 'Pullback period Expiry Notification';
								break;
							case 'welcome_survey_redirect_post':
								$prof = 'welcome_survey_redirect_post';
								break;
							default:
							  $prof = $profile;
							 break;
                 }
                $msg .= "<tr><td><strong>Profile</strong></td><td> &nbsp; </td><td> " . $prof  . " </td></tr>";
             }

             $msg .= "</table>";

        }
?>
<? echo $msg ?>