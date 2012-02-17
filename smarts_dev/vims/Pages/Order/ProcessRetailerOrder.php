<?
session_start("VIMS");
set_time_limit(2000);
include_once("../../vims_config.php");
include_once(CLASSDIR."/User.php");
include_once(CLASSDIR."/Role.php");
include_once(CLASSDIR."/Channel.php");

//check permission
if(!$GLOBALS['_GACL']->checkPermission($_SESSION['username'],'processretailerorder'))
{
	echo "Permission denied";
	exit;
}

$ROLE = new Role();
$CHANNEL=new Channel();

$RetWarehouses = $CHANNEL->getAllRetailerWH();

foreach($RetWarehouses as $WH)
{
    $Usr =  $_USER->getSpecificUsers(5,61,$WH['channel_id']);
    $TLInfo[] = $Usr[0];
}

$user_id = $_SESSION['user_id'];
$role_id = $_POST['user_role_id'];
$_SESSION['order_no']=0;

$userInfo = $_USER->getUserInfo($_SESSION['user_id']);
$userInfo = $userInfo[0];

if(($data = $_USER->getTLRetailers($userInfo['parent_user_id']))==false)
{
    echo("Operation Failed");
}


if(($region = $CHANNEL->getuserRegion($user_id))!=false)
{
    $user_region = $region[0]['location_id'];
    $channels = $CHANNEL->getRetailersChannel(4, $user_region);
}
else
{
    echo("operation failed!!");
}


?>
<script type="text/javascript">
function anyCheck(checkbox)
{
 var b = checkbox;
 var series = b.charAt(checkbox.length-1);
 var total=0;
var max = ProcessRetailerOrder.elements['voucher_serial_'+series].length;

for(var idx = 0; idx < max; idx++)
{
if(eval("document.forms.ProcessRetailerOrder.voucher_serial_"+series+"[" + idx + "].checked") == true)
{
total += 1;
}
}
if(total<2)
    document.getElementById('vcount_'+series).innerHTML=total+' voucher';
else
    document.getElementById('vcount_'+series).innerHTML=total+' vouchers';
}

function resetOrderEntries()
{
    var i=1;
    for (i=1;i<=5;i++)
    {
        
        document.getElementById("denomination_"+i).selectedIndex=0;
        document.getElementById("quantity_"+i).selectedIndex=0;
        document.getElementById("manualVoucherDiv_"+i).innerHTML="";
        document.getElementById("start_serial_"+i).value="";
        document.getElementById("end_serial_"+i).value="";
        document.getElementById("Manual_Select_"+i).checked=false;
        document.getElementById("discount_applied").checked=false;
    }
    
}

</script>

	<table align="center" width="100%" border=0>
		<tr valign='top' class='textbox' >
			<td>
			  <table width="100%" border="0">
				  <tr>
				    <td colspan="2" align="center" class="higlight">Process Retailer Order</td>
			    </tr>
		    </table>
                        </td>
		</tr>
		<tr valign='top' class='textbox' >
		  <td align="center">
              <form name='ProcessRetailerOrder' id='ProcessRetailerOrder' method='post' action=''>
         
          <table width="100%" border="0">
              <tr>
                <td bgcolor="#EFEFEF">
                  <div name="TLDiv" id="TLDiv"> Team Lead:
                        <select name="TeamLead" id="TeamLead" class="selectbox" onchange="javascript:processForm( 'ProcessRetailerOrder','FormProcessor/Order/populateSE.php','SEDiv' ); resetOrderEntries(); ">
                            <option value="">Select Team Lead</option>
                            <?php
                            foreach($TLInfo as $TL) {
                                ?>
                            <option value="<?=$TL['user_id']?>"><?=$TL['first_name']?> <?=$TL['last_name']?></option>
                            <? } ?>
                            </select>
                   </div>
                </td>
                <td bgcolor="#EFEFEF">
                     <input type="checkbox" name="discount_applied" id="discount_applied"> Discount Applied
                </td>
              </tr>
              <tr>
                <td bgcolor="#EFEFEF">
                    <div name="SEDiv" id="SEDiv">Sales Executive:
                     <select name="SE" id="SE" class="selectbox">
                            <option value="">Sales Executive</option>
                            </select>
                     </div>
               </td>
                <td bgcolor="#EFEFEF">
                  <div name="RetailerDiv" id="RetailerDiv"> Retailer:
                        <select name="Retailer" id="Retailer" class="selectbox" >
                        <option value="">Select Retailer</option>
                        </select>
                   </div>
                </td>
            </tr>
            <tr>
                <td>
                    &nbsp;
                </td>
            </tr>
           
          </table><table width="80%" border="0" >
            <? $total_rows = 5;
            for($f = 1; $f <= $total_rows;$f += 1)
            { ?>
            <tr>
              <td bgcolor="#EFEFEF">
                Face Value: <select name="denomination_<?=$f?>" id="denomination_<?=$f?>" class="selectbox" >
                                <option value="">Select Denomination</option>
                                <option value="100">100</option>
                                <option value="250">250</option>
                                <option value="500">500</option>
                                <option value="1000">1000</option>
                </select></td>
                 <td bgcolor="#EFEFEF">
                  Quantity:<select name="quantity_<?=$f?>" id="quantity_<?=$f?>" class="selectbox" >
                      <? $granule = 1; $items=$granule;?>
                                <option value="">Quantity</option>
                                <? while($items <=2000){  ?>
                                <option value='<?=$items?>'><?=$items?></option>
						<?  $items=$items+$granule; }?>
                                </select>
              </td>
            </tr>
            <tr>
                <td bgcolor="#EFEFEF">
                   <? $manualVoucherDiv="manualVoucherDiv_".$f;?>
                <input type="checkbox" name="Manual_Select_<?=$f?>" id="Manual_Select_<?=$f?>" onclick="javascript:getManualSeriesSelection('ProcessRetailerOrder','Pages/Order/ajax_processRetailerOrder.php','<?=$manualVoucherDiv?>',<?=$f?>);" > Manual Selection
                </td>
                <td bgcolor="#EFEFEF"></td>
            </tr>
           <tr>
               <td colspan="2">
                  <div id="<?=$manualVoucherDiv?>"></div>
                  </td>
          </tr>
            <tr>    
                <td class='orangeText' bgcolor="#EFEFEF"><label id="start_serial_txt_<?=$f?>">Start Serial:</label>
                    <input class='textboxBur' type="text" name="start_serial_<?=$f?>" id="start_serial_<?=$f?>" value=""/>
                    </td>
                    <td class='orangeText' bgcolor="#EFEFEF"><label id="end_serial_txt_<?=$f?>">End Serial:</label>
                    <input class='textboxBur' type="text" name="end_serial_<?=$f?>" id="end_serial_<?=$f?>" value=""/>
                    </td>
            </tr>
            
            <? } ?>
                <input type="hidden" name="manual_series_show" id="manual_series_show" value="0" />
                <input type="hidden" name="total_rows" id="total_rows" value="<?=$f-1?>" />
            <tr>
                <td></td>
                <td align="right" class='orangeText' colspan="4">
                    <input class="button" type="button" value="Process Order" onclick="javascript:processForm( 'ProcessRetailerOrder','FormProcessor/Order/ProcessRetailerOrder.php','MsgDiv' );"/></td>
            </tr>
          </table>          
          </form>
		  </td>
  </tr>
	</table>