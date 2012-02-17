<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        include_once(CLASSDIR."/Logging.php");
        
	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR();
        $permission = new permissions();
        $log_obj = new Logging();
        
         
        $accesslevel = $permission->getaccessLevel($_SESSION['username'], 'retentionCallSummary');
        if(!$accesslevel)
        {
            echo "Permission Denied";
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Permission Denied on Retention Call Summary");
            exit();
        }

        $permission = $_POST['permission'];

        $filters = $_POST;
	if($permission == 'basic')
	{
		unset($filters['region']);
		unset($filters['channel']);
	}

        $result = $sales_obj->getcallsummary($filters);
        $UserName = $_SESSION['username'];

        if($result==null)
        {
            echo "Search resulted with no records ";
            $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." Retention Call Summary: No Record found between dates ".$_POST['start_date']." to ".$_POST['end_date']);
            exit();
        }

        $log_obj->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INFO: ".$_SESSION['username']." successfuly generated retention call summary between dates ".$_POST['start_date']." to ".$_POST['end_date']);
?>

<div align="right">
<form name='exportReport' id='exportReport' method='post' action="../FormProcessor/ajax_export.php">
        <input type="hidden" name="region" id="region" value="<?=$_POST['region']?>" />
	 <input type="hidden" name="channel" id="channel" value="<?=$_POST['channel']?>" />
	 <input type="hidden" name="user" id="user" value="<?=$filters['user']?>" />
	 <input type="hidden" name="start_date" id="start_date" value="<?=$filters['start_date']?>" />
         <input type="hidden" name="end_date" id="end_date" value="<?=$filters['end_date']?>" />
          <input type="hidden" name="createdBy" id="createdBy" value="<?=$UserName?>" />
          
    <? foreach($result as $arr)
    { ?>
         <input type="hidden" name="Ex_ID []" id="Ex_ID" value="<?=$arr['ALLOCATED_SE']?>"/>
         <input type="hidden" name="Ex_Name []" id="Ex_Name" value="<?=$arr['FULL_NAME']?>"/>
         <input type="hidden" name="ATTEMPTED []" id="ATTEMPTED" value="<?=$arr['ATTEMPTED']?>"/>
         <input type="hidden" name="CONNECTED []" id="CONNECTED" value="<?=$arr['CONNECTED']?>"/>
         <input type="hidden" name="NOTCONNECTED []" id="NOTCONNECTED" value="<?=$arr['NOTCONNECTED']?>"/>
         <input type="hidden" name="CONNECTIVITY []" id="CONNECTIVITY" value="<?=$arr['CONNECTIVITY']?>"/>
         <input type="hidden" name="TOTAL_ALLOCATED_CALLS []" id="TOTAL_ALLOCATED_CALLS" value="<?=$arr['TOTAL_ALLOCATED_CALLS']?>"/>
         <input type="hidden" name="PAID []" id="PAID" value="<?=$arr['PAID']?>"/>
         <input type="hidden" name="UNPAID []" id="UNPAID" value="<?=$arr['UNPAID']?>"/>
         <input type="hidden" name="PAIDRATIO []" id="PAIDRATIO" value="<?=$arr['PAIDRATIO']?>"/>
         <input type="hidden" name="SHOP []" id="SHOP" value="<?=$arr['SHOP']?>"/>
         <input type="hidden" name="REGION []" id="REGION" value="<?=$arr['REGION']?>"/>
         <input type="hidden" name="BILLING_CYCLE []" id="customer_profile" value="<?=$arr['BILLING_CYCLE']?>"/>
         <?
          $grand_count['ATTEMPTED'] += $arr['ATTEMPTED'];
          $grand_count['CONNECTED'] += $arr['CONNECTED'];
          $grand_count['NOTCONNECTED'] += $arr['NOTCONNECTED'];
          $grand_count['TOTAL_ALLOCATED_CALLS'] += $arr['TOTAL_ALLOCATED_CALLS'];
          $grand_count['PAID'] += $arr['PAID'];
          $grand_count['UNPAID'] += $arr['UNPAID'];
         } ?>

         <input type="hidden" name="GATTEMPTED []" id="GATTEMPTED" value="<?=$grand_count['ATTEMPTED']?>"/>
         <input type="hidden" name="GCONNECTED []" id="GCONNECTED" value="<?=$grand_count['CONNECTED']?>"/>
         <input type="hidden" name="GNOTCONNECTED []" id="GNOTCONNECTED" value="<?=$grand_count['NOTCONNECTED']?>"/>
         <input type="hidden" name="GTOTAL_ALLOCATED_CALLS []" id="GTOTAL_ALLOCATED_CALLS" value="<?=$grand_count['TOTAL_ALLOCATED_CALLS']?>"/>
         <input type="hidden" name="GPAID []" id="GPAID" value="<?=$grand_count['PAID']?>"/>
         <input type="hidden" name="GUNPAID []" id="GUNPAID" value="<?=$grand_count['UNPAID']?>"/>

	 <input type="submit" name="export" id="export" value="Export Report" />
</form>
</div>
    
                <table border="1" cellspacing="1" cellpadding="2">
                    <tr>
                     <td><strong>Shop ID</strong></td>
                    <td><strong>Ex ID</strong></td>
                    <td><strong>Ex Name</strong></td>
                    <td><strong>Attempted Calls</strong></td>
            	    <td><strong>Connected</strong></td>
            	    <td><strong>NotConnected</strong></td>
            	    <td><strong>Connectivity</strong></td>
                     <td><strong>Retention Count</strong></td>
            	    <td><strong>PAID</strong></td>
                    <td><strong>Unpaid</strong></td>
                    <td><strong>Paid %</strong></td>
                    <td><strong>REGION</strong></td>
                    <td><strong>BILLING CYCLE</strong></td>

       	        </tr>
                
                <? $count=0;
                $prevshop="";
                $shopCount;

                if(!($result==null)) {
                    foreach($result as $arr) {

                    if($prevshop==null)
                    {
                        $prevshop = $arr[SHOP];
                    }

                    if($prevshop!=null)
                    {
                        if($prevshop!=$arr[SHOP])
                        {
                            ?>
                <tr border="5" bgcolor="#CCCCCC">
                    <td><?=$prevshop?></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><?=$shopCount[ATTEMPTED]?></td>
                    <td><?=$shopCount[CONNECTED]?></td>
                    <td><?=$shopCount[NOTCONNECTED]?></td>
                    <td><?=round(($shopCount[CONNECTED]/$shopCount[ATTEMPTED]*100),0).'%'?></td>
                    <td><?=$shopCount[TOTAL_ALLOCATED_CALLS]?></td>
                    <td><?=$shopCount[PAID]?></td>
                    <td><?= $shopCount[UNPAID]?></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
		    <td>&nbsp;</td>

                </tr>

                <?       $prevshop=$arr[SHOP];
                         $shopCount[ATTEMPTED]="";
                         $shopCount[CONNECTED]="";
                         $shopCount[NOTCONNECTED]="";
                         $shopCount[TOTAL_ALLOCATED_CALLS]="";
                         $shopCount[PAID]="";
                         $shopCount[UNPAID]="";
                         }
                    }

                    $shopCount[ATTEMPTED] += $arr[ATTEMPTED];
                    $shopCount[CONNECTED] += $arr[CONNECTED];
                    $shopCount[NOTCONNECTED] += $arr[NOTCONNECTED];
                    $shopCount[TOTAL_ALLOCATED_CALLS] += $arr[TOTAL_ALLOCATED_CALLS];
                    $shopCount[PAID] += $arr[PAID];
                    $shopCount[UNPAID] += $arr[UNPAID];
                ?>
            	  <tr>
                    <td><?=$arr[SHOP]?></td>
                    <td><?=$arr[ALLOCATED_SE]?></td>
                    <td><?=$arr[FULL_NAME]?></td>
                    <td><?=$arr[ATTEMPTED]?></td>
            	    <td><?=$arr[CONNECTED]?></td>
            	    <td><?=$arr[NOTCONNECTED]?></td>
            	    <td><?=$arr[CONNECTIVITY].'%'?></td>
            	    <td><?=$arr[TOTAL_ALLOCATED_CALLS]?></td>
            	    <td><?=$arr[PAID]?></td>
                    <td><?=$arr[UNPAID]?></td>
                    <td><?=$arr[PAIDRATIO].'%'?></td>
                    <td><?=$arr[REGION]?></td>
                    <td><?=$arr[BILLING_CYCLE]?></td>
          	    </tr>
                
                 <? $count++;?>
                    
                 <? } } ?>

                    <?
                         $grand_count['CONNECTIVITY'] = ceil(($grand_count['CONNECTED']/$grand_count['ATTEMPTED'])*100);                    ?>
                    
                    <tr border="5" bgcolor="#CCCCCC">
                    <td>&nbsp;</td>
		    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td><?=$grand_count[ATTEMPTED]?></td>
                    <td><?=$grand_count[CONNECTED]?></td>
                    <td><?=$grand_count[NOTCONNECTED]?></td>
                    <td><?=round(($grand_count[CONNECTED]/$grand_count[ATTEMPTED]*100),0).'%'?></td>
                    <td><?=$grand_count[TOTAL_ALLOCATED_CALLS]?></td>
                    <td><?=$grand_count[PAID]?></td>
                    <td><?= $grand_count[UNPAID]?></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
		    <td>&nbsp;</td>
          	    </tr>

 </table>