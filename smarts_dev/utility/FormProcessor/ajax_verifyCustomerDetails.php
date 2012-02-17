<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();

	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/Logging.php");

        $log_obj = new Logging();
        $Customerno = trim($_POST['Customerno']);


         if(!(is_numeric($Customerno)))
         {
             echo "Number subject to be Integer Digits";
             exit();
         }
       

     $customer=new SalesPersonnel();
     $cdata=$customer->getCustomerInfo($Customerno);
     $MACInfo=$customer->getDeviceInfoByAccNo($Customerno);
     $VolumeDetails = $customer->getCustomerVolumeDetails($Customerno);
     
     if($cdata==null)
     {
         echo "No Record Found!";
         $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." No record found against customer mobile/telephone number ".$Customerno);
         exit();
     }

     $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Successfuly verified against customer mobile/telephone number ".$Customerno);
     unset($customer);
?>
<table width="100%">
    <tr>
        <td>
            <table width="100%" > 
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">CUSTOMER ID: </font></td>
                                <td><strong><font size="2"><?= $cdata[0][CUSTOMER_ID] ?></font></strong></td>
                            </tr>
                        </table>
                
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Customer Name: </font></td>
                                <td><font size="2"><?= $cdata[0][FIRST_NAME] ?>&nbsp;<?= $cdata[0][LAST_NAME] ?></font></td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Identification: </font> </td>
                                <td><font size="2"><?= $cdata[0][IDENTIFICATION_NUMBER] ?></font> </td>
                            </tr>
                        </table>
                    </td>
            
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Tel Number: </font> </td>
                                <td><font size="2"><?= $cdata[0][TELEPHONE_NUMBER] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Cell Number: </font> </td>
                                <td><font size="2"><?= $cdata[0][MOBILE_NUMBER] ?></font> </td>
                            </tr>
                        </table>
                    </td>
            
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Cell Number: </font> </td>
                                <td><font size="2"><?= $cdata[0][MOBILE_NUMBER] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Email: </font> </td>
                                <td><font size="2"><?= $cdata[0][EMAIL_ADDRESS] ?></font> </td>
                            </tr>
                        </table>
                    </td>
            
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Email: </font> </td>
                                <td><font size="2"><?= $cdata[0][EMAIL_ADDRESS] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Creation Date: </font> </td>
                                <td><font size="2"><?= $cdata[0][DATE_CREATED] ?></font> </td>
                            </tr>
                        </table>
                    </td>
            
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Churn: </font> </td>
                                <td><font size="2"><?= $cdata[0][CHURN] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Business Type: </font> </td>
                                <td><font size="2"><?= $cdata[0][BUSINESS_TYPE] ?></font> </td>
                            </tr>
                        </table>
                    </td>        
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Status: </font> </td>
                                <td><font size="2"><?= $cdata[0][CUSTOMER_STATUS] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Status Date: </font> </td>
                                <td><font size="2"><?= $cdata[0][LAST_STATUS_DATE] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Due Now: </font> </td>
                                <td><font size="2"><?= $cdata[0][DUE_NOW] ?> PKR</font> </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Plan Name: </font> </td>
                                <td><font size="2"><?= $cdata[0][PLAN_NAME] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Speed: </font> </td>
                                <td><font size="2"><?= $cdata[0][SPEED] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">MAC Address: </font> </td>
                                <td><font size="2"><?= $MACInfo[0][CPE_MAC_ADDRESS] ?></font> </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Package Volume: </font> </td>
                                <td><font size="2"><?= $cdata[0][VOLUME] ?> </font> </td>
                            </tr>
                        </table>                        
                    </td>
                      <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Volume Consumed: </font> </td>
                                <td><font size="2"><?= $VolumeDetails[0][TOTAL_VOLUME] ?> MB</font> </td>
                            </tr>
                        </table>                        
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td class="orangeText"><font size="2">Volume Last Update: </font> </td>
                                <td><font size="2"><?= $VolumeDetails[0][LASTUPDATE] ?> </font> </td>
                            </tr>
                        </table>                        
                    </td>
                </tr>
            </table>
    
        </td>
    </tr>
    
</table>
  