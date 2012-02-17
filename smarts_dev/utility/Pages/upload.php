<?php
/**
* Document   : Upload.PHP
* Created on : Jul 12, 2011, 06:28:23 PM
* Author     : PKAasimN
*
*/
$uploadDir = '/tmp/';
include_once('../util_config.php');
include_once(CLASSDIR."/DBAccess.php");
include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
include_once(CLASSDIR."/Logging.php");

$log_obj = new Logging();
//$myFile = "export.csv";
if($_POST['export']=="Export")
{
    
    $sales_obj = new SalesPersonnel();
    $sql_csv = $sales_obj->getCustomerCompleteData($_POST['month'], $_POST['year']);
    header('Content-Type: application/csv'); //Outputting the file as a csv file
    header('Content-Disposition: attachment; filename=export.csv'); //Defining the name of the file and suggesting the browser to offer a 'Save to disk ' option
    header('Pragma: no-cache');
    
    while($row = mysql_fetch_row($sql_csv)) {
        print '"' . stripslashes(implode('","',$row)) . "\"\n";
    }
    readfile('/tmp/export.csv');
    exit;
}
if(isset($_POST['upload'])) {
    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Started uploading data to db server");
    $fileName = $_FILES['userfile']['name'];
    $tmpName = $_FILES['userfile']['tmp_name'];
    $fileSize = $_FILES['userfile']['size'];
    $fileType = $_FILES['userfile']['type'];
    $pos = strrpos($filename, '.');
    substr($filename, $pos+1);
    $filePath = $uploadDir . "UploadRetentionData.csv";
    $result = move_uploaded_file($tmpName, $filePath);
    if ($result) {
        $fileStts = " Uploaded to Server Directory";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." File ".$tmpName." successfuly uploaded to ".$filePath);
        if ($fileSize>999999) { //IF GREATER THAN 999KB, DISPLAY AS MB
            $fsize = $fileSize / 1000000;
            $fsize = round($fsize, 1)." MB"; //round($WhatToRound, $DecimalPlaces)
        } else { //OTHERWISE DISPLAY AS KB
            $fsize = $fileSize / 1000;
            $fsize = round($fsize, 1)." KB"; //round($WhatToRound, $DecimalPlaces)
        }

        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." File ".$tmpName." file size:".$fsize." successfuly uploaded to ".$filePath);
        $fp = fopen($filePath,'r');
        if($fp) {
            $fileStts = " Uploaded to Server Directory and Read Successfuly";
            $index=0;
            while($csv_line = fgetcsv($fp,1024)) {
                for ($i = 0, $j = count($csv_line); $i < $j; $i++) {
                    $Data[$index][$i] = $csv_line[$i];
                }
                $index++;
            }
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." File ".$filePath." file size:".$fsize." read successfuly");
           //checking data for anomalies
            $errfound=false;
            $cindex=0;
            $total_data_count = count($Data);
            $insertCounter=0;
            $preExists=0;
            $columnsCounter=0;

            //checking whether total count of column selection n data column matches
            if($_POST['Operation']=="Update") {
                
                for($colIndx=1;$colIndx<9;$colIndx++)
                {
                    if($_POST['Column'.$colIndx]!=null)
                    {
                      $columnsCounter++;
                    }
                }

                if($columnsCounter!=count($Data[0]))
                {
                    $errfound =true;
                    $ErrorString.=" Error: Number of Column selected:$columnsCounter do not match with number of columns given in file:".count($Data[0])."  \n";
                }               
            }
             
                for($i=1;$i<count($Data);$i++) {
                    //checking customer id is numeric value given
                    for($j=0;$j<count($Data[$i]);$j++) {
                        if($j==0) {
                            if (!is_numeric($Data[$i][$j])) {
                                $errfound =true;
                                $ErrorString.=" Column: CustomerID,Error: @ Line: $i Column: $j need to be integer value ,Provided Value:(".$Data[$i][$j].")\n";
                            }
                        }

                        //checking whether any data has "'" sign causes problem while insertion
                        $findme   = "'";
                        $pos = strpos($Data[$i][$j], $findme);
                        if ($pos) {
                            $errfound =true;
                            $ErrorString.="CustomerID: ".$Data[$i][0].", Error Column: ".$Data[0][$j]." , Error: special character "."'"." found @ Line: $i column: $j,Error Value:(".$Data[$i][$j].") \n";
                        }
                    }
                    
                    //checking data exists against given selection
                    for($colIndx=1;$colIndx<9;$colIndx++)
                        {
                            if($_POST['Column'.$colIndx]!=null)
                            {
                               if($Data[$i][$colIndx-1]==null)
                               {
                                   $errfound =true;
                                   $ErrorString.="CustomerID: ".$Data[$i][0].", Error: No Data found in file against Column $colIndx \n";
                               }
                            }
                        }
                }

                if($errfound) {
                    $fileStts = "File is not correctly formatted";
                    $msg = "Please correct the file, Operation unsuccessful";
                    $myFile = "errorLog.csv";
                    $fh = fopen('/tmp/'.$myFile, 'w') or die("can't open file");
                    fwrite($fh, $ErrorString);
                    fclose($fh);
                    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." File ".$filePath." contains anomalies");
                    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Error Log ".'/tmp/'.$myFile." contains the details of file anomalies");
                }
               if($_POST['Operation']=="upload") {
                //Upload new data for month given

                if(!$errfound) {
                    $billing_cycle = $_POST['year'].$_POST['month'];
                    
                    //Db Connection
                    $GLOBALS['_DB']->DBlink->StartTrans();
                    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Upload Operation:: File ".$filePath." data is inserted to Database for billing Cycle ".$billing_cycle.", DB transaction started");
                    $insert = "customer_id,first_name,last_name,region_name,customer_status,telephone_number,mobile_number
                               ,email_address,date_created,business_type,cpe_mac_address,usagemonth1
                               ,usagemonth2,package_id,package_name,sales_rep_id,sales_rep_name
                               ,shop_id,address,paytype,parent_acct,total_due,paid,billing_cycle
                               ,customer_profile";
                    
                    for($row=1;$row<$total_data_count;$row++) {
                        $vals="'".$Data[$row][0]."','".$Data[$row][1]."','".$Data[$row][2]."','".$Data[$row][3]."','".$Data[$row][4]."'
                              ,'".$Data[$row][5]."','".$Data[$row][6]."','".$Data[$row][7]."',to_date('".$Data[$row][8]."','MM/DD/YYYY'),'".$Data[$row][9]."'
                              ,'".$Data[$row][10]."','".$Data[$row][11]."','".$Data[$row][12]."','".$Data[$row][13]."','".$Data[$row][14]."'
                              ,'".$Data[$row][15]."','".$Data[$row][16]."','".$Data[$row][17]."','".$Data[$row][18]."','".$Data[$row][19]."'
                              ,'".$Data[$row][20]."','".$Data[$row][21]."','0','".$billing_cycle."','".$Data[$row][22]."' ";

                        $query="select count(*) as checkEx from customer_payables where billing_cycle = $billing_cycle and customer_id = '".$Data[$row][0]."' ";

                        $records = $GLOBALS['_DB']->DBlink->Execute($query);
                        $custExists = $records->FetchRow();

                        if($custExists['CHECKEX'] < 1) {
                            $GLOBALS['_DB']->DBlink->Execute("insert into customer_payables ($insert) values($vals)");
                            $insertCounter++;
                        }
                        else {
                            $preExists++;
                        }
                    }

                    if($GLOBALS['_DB']->DBlink->HasFailedTrans()) {
                        $GLOBALS['_DB']->DBlink->CompleteTrans();
                        $DBstatus="Insertion failed! ";
                        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." File ".$filePath." data is inserted to Database for billing Cycle ".$billing_cycle." failed");
                    }
                    $GLOBALS['_DB']->DBlink->CompleteTrans();
                    $msg = "$insertCounter records being Uploaded to Database ";
                    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." File ".$filePath." data is inserted to Database for billing Cycle ".$billing_cycle." and $insertCounter records being Uploaded to Database ");
                }
            }
            if($_POST['Operation']=="Update") {
                //Update data for the month given
                $billing_cycle = $_POST['year'].$_POST['month'];
                if(!$errfound) {
                $GLOBALS['_DB']->DBlink->StartTrans();
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Update Operation:: File ".$filePath." data updated to DB for billing Cycle ".$billing_cycle.", DB transaction started");
                    //se ids counter for getting counter of total distinct seIds.
                    $seid=0;
                    for($i=1;$i<9;$i++) // loop through all select options
                    {
                        if($_POST['Column'.$i]=="allocated_se") // checking whether executive allocation is selected
                        {
                            if($_POST['allocationsType']=="New Allocation") // if executive are assigning totaly new calls
                            {
                                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Update Operation::New calls allocation is selected");
                                for($row=1;$row<$total_data_count;$row++) {
                                    if($SeIdarray[$seid-1]!=null) //if previously id exists in array
                                    {
                                        if($SeIdarray[$seid-1]!=$Data[$row][$i-1]) //getting distinct se ids which is not inserted before
                                        {
                                            $SeIdarray[$seid++] = $Data[$row][$i-1];
                                        }
                                    }
                                    else {
                                        $SeIdarray[$seid++] = $Data[$row][$i-1]; //very first insertion into seid array
                                    }
                                }
                            }
                        }
                    }

                    //Deallocating calls assigned to mentioned SE ids.
                    for($seidIndex=0;$seidIndex<count($SeIdarray);$seidIndex++) {
                        $query = "update customer_payables set allocated_se = '' where billing_cycle = $billing_cycle
                                and allocated_se = '".$SeIdarray[$seidIndex]."'";
                        $GLOBALS['_DB']->DBlink->Execute($query);
                        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Update Operation::All previouse allocation are deallocated for $SeIdarray[$seidIndex]");
                    }
                    

                    $QuerySetPartIsEMPTY=false;
                    $queryInitialPart=" update customer_payables ";
                    $querySetPart="";
                    $queryWherePart="";
                    for($row=1;$row<$total_data_count;$row++) {
                        for($i=2;$i<9;$i++) {
                            if($_POST['Column'.$i]!=null) {
                                if(!$QuerySetPartIsEMPTY) {
                                    $querySetPart.= " set ".$_POST['Column'.$i]." = '".$Data[$row][$i-1]."'";
                                    $QuerySetPartIsEMPTY=true;
                                }
                                else {
                                    $querySetPart.= " , ".$_POST['Column'.$i]." = '".$Data[$row][$i-1]."'";
                                }
                            }
                        }

                        $queryWherePart = " where billing_cycle = ".$billing_cycle." and customer_id = '".$Data[$row][0]."' ";
                        $query = $queryInitialPart." ".$querySetPart." ".$queryWherePart;
                        //print($query ."<br>");
                        $GLOBALS['_DB']->DBlink->Execute($query);
                        $QuerySetPartIsEMPTY=false;
                        $querySetPart="";
                        if($GLOBALS['_DB']->DBlink->HasFailedTrans()) {
                            $GLOBALS['_DB']->DBlink->CompleteTrans();
                            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']." Update Operation::failed to update customer payables");
                        }
                        $GLOBALS['_DB']->DBlink->CompleteTrans();
                        $msg= "Successfuly Modified ".($total_data_count-1)." records";
                    }
                    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']."Successfuly Modified ".($total_data_count-1)." records");
                }
            }

            if(fclose($fp)) {
                $fileStts="Successfuly Uploaded to Server Directory, Read and Closed";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']."File ".$filePath." Successfuly Uploaded to Server Directory, Read and Closed ");
            }
            else {
                $fileStts= "Can't close file";
                $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']."cant close File ".$filePath);
                die("can't close file");
            }
        }
        else {
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']."cant open File ".$filePath);
            die($fileStts="can't open file");
        }
    }
    else {
        $fileStts = "Error Uploading File to Server";
        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']."error uploading file ".$filePath." to server");
    }
}
else {
    $fileName = "NULL";
    $fileStts = "No file provided!";
    $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"ERROR: ".$_SESSION['username']."error no file provided");
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Retention Data Load / Update</title>
        <style type="text/css">
            <!--
            @import url("../../vims/CSS/style.css");
            -->
        </style>
    </head>

    <body>
        <table align="center">
            <tr>
                <td height="50" colspan="2" background="../../images/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
                    <a href="http://smarts.wi-tribe.net.pk/sales/index.php" onclick="javascript:animatedAjaxCall( '../../images/nav.php','LeftNavigationDiv' );" ><img src="../../images/main/home.gif" border="0"/></a> &nbsp;&nbsp;|&nbsp; &nbsp;
                    <a href="http://smarts.wi-tribe.net.pk/sales/logout.php" border="0"><img src="../../images/main/logout.gif" border="0"/></a> &nbsp;&nbsp;|&nbsp; &nbsp;
                </td>
            </tr>
            <tr>
                <td align="left">
                    <table width="1000" border="0" cellspacing="1" cellpadding="1" align="center" bgcolor="#FFFFFF">
                        <tr>
                            <td width="277">
                                <table width="1000" height="57">
                                    <tr>
                                        <td width="1000">
                                            <div align="center"><font face="MS Reference Sans Serif" size="4">Retention Data Loading/ Updation</font> </div></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td><strong><font size="3">Upload / Update Summary:</font></strong></td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td><strong><font size="2"><u>File Details and Status:</u></font></strong></td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>
                                <font face="MS Reference Sans Serif" size="2">File Name: <strong><?=$fileName?> </strong></font>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>
                                <font face="MS Reference Sans Serif" size="2">File Status: <strong><?=$fileStts?> </strong></font>
                            </td>
                        </tr>
                         <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>
                                <font face="MS Reference Sans Serif" size="2">File Size: <strong><?=$fsize?> </strong></font>
                            </td>
                        </tr>
                          <tr><td>&nbsp;</td></tr>
                           <tr><td><font size="2">Total File Records: <?=($total_data_count-1)?> [Excluding Header]</font></td></tr>
                        <tr><td>&nbsp;</td></tr>
                         <tr>
                            <td>
                                <font face="MS Reference Sans Serif" size="2">Error in Data: <strong><? if($errfound) { ?>
                                        <a href="errorlog.php"><font color="red">Click here to download Error log file</font></a>
                                     <?} else { ?><font color="green"> No Error found!</font>
                                                                                    <?
                                                                                }?></strong></font>
                            </td>
                        </tr>
                         <tr><td>&nbsp;</td></tr>
                         <tr><td>&nbsp;</td></tr>
                        <tr><td><strong><font size="2"><u>Operation Details and Status:</u></font></strong></td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr>
                            <td>
                                <font face="MS Reference Sans Serif" size="2">Operation Performed: <strong><?=ucfirst($_POST['Operation'])?> </strong></font>
                            </td>
                        </tr>
                         <tr><td>&nbsp;</td></tr>
                         <tr>
                            <td>
                                <font face="MS Reference Sans Serif" size="2">Operation Status: <strong><?=$msg?></strong></font>
                            </td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <? if($_POST['Operation']=="upload") {?>
                        <tr><td><font size="2">Pre-Existing records found for <?=date( 'F', mktime(0, 0, 0, $_POST['month']) ).",".$_POST['year']?> :  <font color="maroon"><strong><?=($preExists)?></strong></font> </font></td></tr>
                        <? } ?>
                        <tr><td>&nbsp;</td></tr>                   
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td>&nbsp;</td></tr>
                    </table>
                </td>
            </tr>

            <tr>
                <td align="center" valign="middle" background="../../images/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
            </tr>

        </table>

    </body>
</html>