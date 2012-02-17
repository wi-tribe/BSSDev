<?php
            session_start();
            ob_start();

            include_once("../util_config.php");
            $conf=new config();
            include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
            $sales_obj = new SalesPersonnel();

            include_once(LIB."/phpmailer/class.phpmailer.php");
            include_once(CLASSDIR."/Logging.php");
            $log_obj = new Logging();
            
            $mailer = new PHPMailer();

            $mailer->IsSMTP();
            $mailer->Host = "10.1.81.22";

            $mail->SMTPAuth = false;

            if($_POST['Emailtemplate']==null) {
                echo "<font color="."#FF0000"."><strong>Please select one subject!!</strong></font>";
                exit();
            }

            $mailer->From = "noreply@pk.wi-tribe.com";
            $mailer->FromName = "wi-tribe Pakistan";
            $mailer->AddReplyTo("noreply@pk.wi-tribe.com", "wi-tribe Pakistan");
            $mailer->Subject = "Billing Update";

            $mailer->IsHTML(true);

            if($_POST['Emailtemplate']=='billingupdate/index')
            {
                $name = "Dear ".ucfirst($_POST['first_name']).' '.ucfirst($_POST['last_name']);
                $customerID = "Customer ID: <font color="."#000000".">".$_POST['customer_id']."</font>";
                $total_amount_due = "Total amount due: <font color="."#000000".">".$_POST['due_amount']."</font>";
                $customer_email = $_POST['customer_email'];
                $package = "Package: <font color="."#000000".">".$_POST['package_info']."</font>";

                $selectedFile = 1;
                $myFile = "/var/www/html/smarts/utility/Pages/mailtemplate/".$_POST['Emailtemplate'].".html";

                $fh = fopen($myFile, 'r');
                $theData = fread($fh, filesize($myFile));
                fclose($fh);

                $newdata = str_replace("Dear [FirstName LastName]", $name, $theData);
                $newdata = str_replace("Customer ID: [Insert ID]", $customerID, $newdata);
                $newdata = str_replace("Total amount due: [INSERT AMOUNT DUE]", $total_amount_due, $newdata);
                $newdata = str_replace("Package:[INSERT PACKAGE INFO ]", $package, $newdata);

                $mailer->Body = $newdata;

                $mailer->AddAddress($customer_email , ucfirst($_POST['first_name'])." ".ucfirst($_POST['last_name']));
              //  $mailer->AddAddress("aasim.nazar@gmail.com" , $first_name." ".$last_name);
                //$mailer->AddAddress("asim_naveed2000@live.com" , $first_name." ".$last_name);
               // $mailer->AddAddress("amir.mirza@pk.wi-tribe.com" , $first_name." ".$last_name);
            }
              // $mailer->AddAddress($email_address , $email_address);
            $result = $mailer->Send();

            if($result) {
                print("<font color="."#FF8040"."><strong>Mail Sent Successfuly to Following Recipient: </strong></font><font color="."#FF0000"."><strong> $customer_email</strong></font>");
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Billing Update successfuly sent to customer at email: ".$customer_email);
            }
            else {
                print("<font color="."#FF0000"."><strong>Mail Sending Failed!!</strong></font>");
                $mail->ErrorInfo;
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Unable to send Billing Update to customer at email: ".$customer_email);
            }
            $mailer->ClearAddresses();


?>
