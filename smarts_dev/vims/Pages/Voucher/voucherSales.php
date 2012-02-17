<?PHP
session_start("VIMS");
set_time_limit(200);


//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");


//creating new object
$vouch=new Voucher();

$whData_=false;
$yData_=false;
//get today data
if(($whData=$vouch->todaySales()))
{
	$whData_=true;
}

//get yesterday data
if(($yData=$vouch->yesterdaySales()))
{
	$yData_=true;
}

?>
	<table align="center" width="90%" border=0>
		<tr valign='top' class='textbox' >
			<td>
              <!-----------------------------THIS PAGE------------------------------------->     
				  <table width='100%' border='0' cellspacing='1' cellpadding='2'>
					<tr>
					  <td valign="top" bgcolor="#EFEFEF">
					  
					  <table width="100%" border="0" cellspacing="1" cellpadding="1">
					    <tr>
					      <td colspan="4" align="center" class="higlight">Today's Sales (Per Shop)</td>
				        </tr>
					    <tr class="heading">
					      <td width="35%">Shop</td>
					      <td width="25%">Face Value</td>
					      <td width="20%">Count</td>
					      <td width="20%">Amount</td>
				        </tr>
				        <?php if(!empty($whData)) { 
				        
				        	foreach($whData as $arr) {?>
					    <tr class="normalFont">
					      <td><?=$arr['channel_name']?></td>
					      <td><?=$arr['voucher_denomination']?></td>
					      <td><?=$arr['total']?></td>
					      <td><?=$arr['amt']?></td>
				        </tr>
				        <?php } } ?>
				      </table>  
				      
				      </td>
					  <td valign="top" bgcolor="#EFEFEF"><table width="100%" border="0" cellspacing="1" cellpadding="1">
					    <tr>
					      <td colspan="4" align="center" class="higlight">Yesterday's Sales (Per Shop)</td>
				        </tr>
					    <tr class="heading">
					      <td width="35%">Shop</td>
					      <td width="25%">Face Value</td>
					      <td width="20%">Count</td>
					      <td width="20%">Amount</td>
				        </tr>
					    <?php if(!empty($yData)) { 
				        
				        	foreach($yData as $arr) {?>
					    <tr class="normalFont">
					      <td><?=$arr['channel_name']?></td>
					      <td><?=$arr['voucher_denomination']?></td>
					      <td><?=$arr['total']?></td>
					      <td><?=$arr['amt']?></td>
				        </tr>
					    <?php } } ?>
				      </table></td>
				    </tr>
				  </table>
                  <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>