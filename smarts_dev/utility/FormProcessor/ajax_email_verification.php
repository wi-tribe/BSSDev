<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/Logging.php");
	
	$mail =$_POST['email_address'];
	$debug = "checking for $mail ...<BR>";
	$verified = false;
	$result = '';
	$host = substr(strchr($mail, '@'), 1);

	
	if (!checkdnsrr($host, 'ANY'))
	{
		$result ="Email server $host does not seem valid.<BR> Incorrect EMAIL";
	}
	else
	{	
		$mxHosts = array();
		if (! getmxrr($host, $mxHosts)) 
		{
			$mxHosts[] = $host;
		}
		foreach ($mxHosts as $smtpServer) 
		{
			$debug .= "connecting $smtpServer server......................";
			$connect = @fsockopen($smtpServer, 25, $errno, $errstr, 15);
			if (!$connect) 
				continue;
			if (ereg("^220", $out = fgets($connect, 1024))) 
			{
				$debug .= "CONNECTED<BR>verifying email address......................";
				$from = "imsaady@gmail.com";
				if (preg_match('/\<(.*)\>/', $from, $match) > 0) 
				{
					$from = $match[1];
				}
				$localhost ="wi-tribe.com";// $_SERVER["HTTP_HOST"];
				if (!$localhost) 
					$localhost = 'localhost';

				fputs($connect, "HELO $localhost\r\n");
				$out  = fgets($connect, 1024);
				fputs($connect, "MAIL FROM: <$from>\r\n");
				$from = fgets($connect, 1024);
				fputs($connect, "RCPT TO: <{$mail}>\r\n");
				$to   = fgets($connect, 1024);
				fputs($connect, "QUIT\r\n");
				fclose($connect);
				$debug .= "<BR>Respone:$to<BR>";
				if (!ereg ("^250", $to ))
				{
					$result = "Email address '$mail' doesn't exist";
					$debug 	.=  "INVALID EMAIL<BR>";
				}else
				{
					$verified = true;
					$result = "'$mail' is valid email address ";
					$debug 	.=  "VALIDATED<BR>";
					break;
				}
				
			} else 
			{
				$result = "Could not verify email address at host $host: $out <BR><input type='button' value='Retry verification' class='button' onclick='javascript:verify_email();'/>";
			}
			
		}
	}
	$log= new DB_Logging();
	$log_info['action']="email_verification";
	$log_info['details']=$mail;
	$log_info['debug_details']=$debug;
        $log_info['status'] = ( $verified==true?'valid':'invalid');
	$log->logToDB($log_info);
	print "$debug<BR><BR><BR>";
?>
	<table width='100%' >
		<tr >
			<td width="150"><strong>Email Verification</strong></td> 
			<?	if($verified)
				{ ?>
					<td align="center" bgcolor="#CCCCCC" style="color: #093; font-size: 12px;"><strong><?=$result?></strong></td>
				<? } else
				{ ?>
					<td align="center" bgcolor="#CCCCCC" style="color: #F00; font-size: 12px;"><strong><?=$result?></strong></td>
				<?	} ?>
		</tr>
	</table>