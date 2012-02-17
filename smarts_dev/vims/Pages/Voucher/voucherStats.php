<?PHP
session_start("VIMS");
set_time_limit(200);

//ob_start();
include_once("../../vims_config.php");
$conf=new config();
include_once(CLASSDIR."/Voucher.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'voucherstats'))
{
	echo "Permission denied";
	exit;
}


//creating new object
$vouch=new Voucher();

$whData_=false;
$mData_=false;
$shData_=false;
$soData_=false;

//get wh data
if(($whData=$vouch->countWHVOuchers()))
{
	$whData_=true;
}

//get market data
if(($mData=$vouch->countMarketVouchers()))
{
	$mData_=true;
}

//get shop data
if(($shData=$vouch->countShopVouchers()))
{
	$shData_=true;
}

//get sold data
if(($soData=$vouch->countSoldVouchers()))
{
	$soData_=true;
}

?>
	<table align="center" width="90%" border=0>
		<tr valign='top' class='textbox' >
			<td>
              <!-----------------------------THIS PAGE------------------------------------->     
				  <table width='100%' border='0' cellspacing='1' cellpadding='2'>
					<tr>
					  <td width="50%" valign="top" bgcolor="#EFEFEF">
					  
					  <table width="100%" border="0" cellspacing="1" cellpadding="1">
					    <tr>
					      <td colspan="2" align="center" class="higlight">Available Voucher (In WareHouse)</td>
				        </tr>
					    <tr class="heading">
					      <td width="50%">Face Value</td>
                                              <td>WH</td>
					      <td>Count</td>
				        </tr>
				        <?php if(!empty($whData)) { 
				        
				        	foreach($whData as $arr) {?>
					    <tr class="normalFont">
					      <td><?=$arr['voucher_denomination']?></td>
                                              <td><?=$arr['channel_name']?></td>
					      <td><?=$arr['total']?></td>
				        </tr>
				        <?php }  } ?>
				      </table>
				      
				      </td>
					  <td width="50%" valign="top" bgcolor="#EFEFEF">
					  
					  <table width="100%" border="0" cellspacing="1" cellpadding="1">
					    <tr>
					      <td colspan="2" align="center" class="higlight">Available Vouchers (In Market)</td>
				        </tr>
					    <tr class="heading">
					      <td width="50%">Face Value</td>
					      <td>Count</td>
				        </tr>
				        <?php if(!empty($mData)) { 
				        
				        	foreach($mData as $arr) {?>
					    <tr class="normalFont">
					      <td><?=$arr['voucher_denomination']?></td>
					      <td><?=$arr['total']?></td>
				        </tr>
				        <?php } } ?>
				      </table>
				      
				      </td>
                    </tr>
					<tr>
					  <td valign="top" bgcolor="#EFEFEF">
					  
					  <table width="100%" border="0" cellspacing="1" cellpadding="1">
					    <tr>
					      <td colspan="3" align="center" class="higlight">Available Vouchers (Per Shop)</td>
				        </tr>
					    <tr class="heading">
					      <td width="33%">Shop</td>
					      <td width="33%">Face Value</td>
					      <td width="33%">Count</td>
				        </tr>
				        <?php if(!empty($shData)) { 
				        
				        	foreach($shData as $arr) {?>
					    <tr class="normalFont">
					      <td><?=$arr['channel_name']?></td>
					      <td><?=$arr['voucher_denomination']?></td>
					      <td><?=$arr['total']?></td>
				        </tr>
				        <?php } } ?>
				      </table>
				      
				      </td>
					  <td valign="top" bgcolor="#EFEFEF">
					  
					  <table width="100%" border="0" cellspacing="1" cellpadding="1">
					    <tr>
					      <td colspan="3" align="center" class="higlight">Sold Vouchers (Per Shop)</td>
				        </tr>
					    <tr class="heading">
					      <td width="33%">Shop</td>
					      <td width="33%">Face Value</td>
					      <td width="33%">Count</td>
				        </tr>
				        <?php if(!empty($soData)) { 
				        
				        	foreach($soData as $arr) {?>
					    <tr class="normalFont">
					      <td><?=$arr['channel_name']?></td>
					      <td><?=$arr['voucher_denomination']?></td>
					      <td><?=$arr['total']?></td>
				        </tr>
				        <?php  } } ?>
				      </table>
				      
				      </td>
				    </tr>
				  </table>
                  <!--------------------------------THE END------------------------------------->
			</td>
		</tr>
	</table>