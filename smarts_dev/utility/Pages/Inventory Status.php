<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");

	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR();

        $permission = new permissions();
        $accesslevel = $permission->getaccessLevel($_SESSION['username'], 'DeviceTrackingReport');

        if(!$accesslevel)
        {
            echo "Permission Denied";
            exit();
        }
        
        $filters['Region']='ALL';
        $filters['Shop']='ALL';
        $filters['CCE']='ALL';

        if($accesslevel=='NWD')
        {
            $result = $sales_obj->getcallsummary($filters);
        }

        if($accesslevel=='Region')
        {
            $filters['Region'] = $sales_rep['ADDR_CITY'];
            $channels = $sales_obj->getRegionCCShops($filters['Region']);
            //print_r($channels); exit();
        }

        if($accesslevel=='Shop')
        {
            $filters['Region'] = $sales_rep['ADDR_CITY'];
            $filters['Shop'] = $sales_rep['SHOP_ID'];
            $users = $sales_obj->getShopPersonnels($filters['Shop']);
        }

        if($accesslevel=='basic')
        {
             $filters['Region'] = $sales_rep['ADDR_CITY'];
             $filters['Shop'] = $sales_rep['SHOP_ID'];
             $filters['CCE']= $sales_rep['SALESPERSONNEL_ID'];
             $filters['CCE_NAME'] = $sales_rep['FULL_NAME'];
        }


?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="ur">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--<link href="../css/style.css" rel="stylesheet" type="text/css"></link>!-->
<style type="text/css">
body {
	background-color:#CCCCCC ;/*#5F5D60*/
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<link href="../../../vims/CSS/style.css" rel="stylesheet" type="text/css"></link>
<link href="../js/jquery/ui.datepicker.css" rel="stylesheet" type="text/css"></link>
<script src="../js/jquery/jquery.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/jquery.ui.core.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/ui.datepicker.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/jquery.validate.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>
<link href="../js/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/paging.js"></script>
<script src="../js/ajax.js" type="text/javascript" language="javascript"></script>

<style type="text/css">
            .pg-normal {
                color: black;
                font-weight: normal;
                text-decoration: none;
                cursor: pointer;
            }
            .pg-selected {
                color: black;
                font-weight: bold;
                text-decoration: underline;
                cursor: pointer;
            }
</style>
</head>
<title>Device Inventory Tracker</title>
<script type="text/javascript">

function popualateShops()
{
    var post_func = function()
				{
					var effect = function(data){ $("#channelsDiv").html(data).show("normal"); };
					$.post("../FormProcessor/ajax_getRegionShops.php",$("#retentionCallsSummary").serialize() ,effect);
				}
	$("#channelsDiv").html("<img src='../../images/loading.gif' /> Loading...");
	$("#channelsDiv").show("normal",post_func);
}
function popualteUsers()
{
    var post_func = function()
				{
					var effect = function(data){ $("#userdiv").html(data).show("normal"); };
					$.post("../FormProcessor/ajax_getShopExecutives.php",$("#retentionCallsSummary").serialize() ,effect);
				}
	$("#userdiv").html("<img src='../../images/loading.gif' /> Loading...");
	$("#userdiv").show("normal",post_func);
}

function getDetail()
{
    var invID = document.getElementById('selectedDeviceID').value;
    var post_func = function()
    {

        var effect = function(data){ $("#deviceInfo").html(data).show("normal"); 	};
        $.post("../FormProcessor/ajax_inventory_status_details.php",{inventoryID: invID},effect);
    }
    $("#deviceInfo").html("<img src='../../images/loading.gif' /> Loading...");
    $("#deviceInfo").show("normal",post_func);
}

function getReport()
{
     document.getElementById('pageNavPosition').innerHTML = "";
     document.getElementById('pageNavPosition2').innerHTML = "";
     document.getElementById('deviceInfo').innerHTML = "";
     var post_func = function()
				{
					var effect = function(data){ $("#ReportData").html(data).show("normal"); };
					$.post("../FormProcessor/ajax_inventory_status.php",$("#retentionCallsSummary").serialize() ,effect);
				}


	$("#ReportData").html("Loading... <img src='../../../images/loading.gif' />");
	$("#ReportData").show("normal",post_func);
}

</script> 

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1000" align="center" cellpadding="0" cellspacing="0">
    <tr>
         <td height="50" colspan="2" background="<?=IMAGES?>/nav-rep.png" valign="middle" style=" background-repeat:repeat-x; height:50px; vertical-align:middle; text-align:center;">
					<a href="http://smarts.wi-tribe.net.pk/sales/index.php" onclick="javascript:animatedAjaxCall( 'nav.php','LeftNavigationDiv' );" ><img src="<?=IMAGES?>/main/home.gif" border="0"/></a> &nbsp;&nbsp;|&nbsp; &nbsp;
					<a href="http://smarts.wi-tribe.net.pk/sales/logout.php" border="0"><img src="<?=IMAGES?>/main/logout.gif" border="0"/></a> &nbsp;&nbsp;|&nbsp; &nbsp;
				</td>
    </tr>
  <tr>
    <td><br>
      <table width="1200" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
      <tr>
          <td>
        <!-- ================================= Start Content TD =============================== -->
                       <table width="100%">
					<tr>
					<td colspan="2" align="center" style="font-size:16px"><strong>Device Tracking System </strong></td>
					</tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>
                       </table>


                   <form name="retentionCallsSummary" id="retentionCallsSummary" method="post" action="">
                 <input type="hidden" name ="permission" id="permission" value="<?=$accesslevel?>"/>
                 <input type="hidden" name ="selectedDeviceID" id="selectedDeviceID" value="" onchange="getDetail();"/>

                    <table width="100%">

					<tr>
                                            <td>
                                                <strong>Shop / Agent Wise Search Option </strong>
                                            </td>
                                        </tr>

                                        <tr>
						<td>
                                                    <div id="searchOptions">
							<table width="80%">
								<tr>
									<td>Region</td>
									<td>
										<?	if($accesslevel == 'NWD')
										{	?>
											<select name='region' id='region' class="selectbox" onchange="popualateShops();">
												<option value="ALL"> -- ALL -- </option>
                                                                                                <option value="Islamabad">Islamabad</option>
                                                                                                <option value="Rawalpindi">Rawalpindi</option>
                                                                                                <option value="Lahore">Lahore</option>
                                                                                                <option value="Karachi">Karachi</option>
                                                                                                <option value="Faisalabad">Faisalabad</option>
											</select>
									<?	} else
										{	?>
											<?=$filters['Region']?>
											<input type="hidden" name ="region" id="region" value="<?=$filters['Region']?>"/>
									<?	}	?>
									</td>
                                                                        <td>
                                                                            &nbsp;
                                                                        </td>
                                                                        <td> Device ID </td><td><input name="inventory_id" id="inventory_id" /></td>
								</tr>
								<tr>
									<td>Channel/Shop</td>
									<td>
										<?	if($accesslevel == 'Region' || $accesslevel == 'NWD')
											{	?>
                                                                            <div id="channelsDiv">
                                                                                <select name='channel' id='channel' class="selectbox" onchange="popualteUsers();">
													<option value="ALL"> -- ALL -- </option>
												<?	foreach($channels as $arr)
													{ ?>
														<option value="<?=$arr['SHOP_ID']?>"><?=$arr['SHOP_ID']?> ::: <?=$arr['SHOP_NAME']?></option>
												<?	} ?>
												</select>

										<?	} else
											{	?>
												<?=$filters['Shop']?>
												<input type="hidden" name ="channel" id="channel" value="<?=$filters['Shop']?>"/>
										<?	}	?>
                                                                            </div>
									</td>
                                                                        <td>
                                                                            &nbsp;
                                                                        </td>
                                                                        <td> Device WAN Mac</td><td> <input name="device_wan_mac" id="device_wan_mac" /></td>

								</tr>
								<tr>
									<td>User</td>
									<td>
										<div name="userdiv" id="userdiv">
										<?	if($accesslevel == 'Shop' || $accesslevel == 'Region' or $accesslevel == 'NWD')
										{	?>
											<select name='user' id='user' class="selectbox" >
												<option value="ALL"> -- ALL -- </option>
												<?	foreach($users as $arr)
													{ ?>
                                                                                                <option value="<?=$arr['SALESPERSONNEL_ID']?>"><?=$arr['SALESPERSONNEL_ID'] ?> ::: <?=$arr['FULL_NAME']?></option>
												<?	} ?>
											</select>
										<?	} else
											{	?>
												<?= $filters['CCE_NAME']?>
												<input type="hidden" name ="user" id="user" value="<?= $filters['CCE']?>"/>
                                                                                                <input type="hidden" name ="cce_name" id="cce_name" value="<?= $filters['CCE_NAME']?>"/>
										<?	}	?>
										</div>
									</td>
                                                                        <td>
                                                                            &nbsp;
                                                                        </td>
                                                                        <td> Device Status </td><td> <select name='device_status' id='device_status' class="selectbox" >
												<option value="ALL"> -- ALL -- </option>
												<option value="1"> -- Available At Shop -- </option>
                                                                                                <option value="2"> -- New Assigned to CSE -- </option>
                                                                                                <option value="3"> -- Assigned Sold -- </option>
                                                                                                <option value="4"> -- Damaged to ERP -- </option>
                                                                                                <option value="5"> -- Refurbished -- </option>
                                                                                                <option value="6"> -- Damaged -- </option>
                                                                                                <option value="7"> -- Lost -- </option>
                                                                                                <option value="8"> -- To ERP Transfer Req -- </option>
                                                                                                <option value="9"> -- Received Damaged from ERP -- </option>
											</select></td>
								</tr>

							</table>
                                                    </div>
						</td>
                                        </tr>
                                            <tr>
                                                <td>
						<input name="FormAction" type='button' id="display" value='Display Report' onclick="getReport();"/>
						</td>
                    </tr>
                        <tr>
                         <td>&nbsp;</td>
                     </tr>
		</table>
                 <table width="100%">
                      <tr>
                            <td>
                                <div id="ReportData"></div>
                            </td>
                        </tr>
                 </table>
                 <table>
                      <tr>
                          <td align="left">
                              <div id="pageNavPosition"></div>
                          </td>
                      </tr>
                 </table>
                 <table>
                     <tr>
                         <td>
                             <div id="deviceInfo"></div>
                         </td>
                     </tr>
                 </table>
                  <table>
                      <tr>
                          <td align="left">
                              <div id="pageNavPosition2"></div>
                          </td>
                      </tr>
                 </table>
             </form>
          </td>
      </tr>
        </table>
    </br></td>
  </tr>
    <tr>
              <td>
                  &nbsp;
              </td>
          </tr>
  	<tr>
		<td align="center" valign="middle" background="../../../images/nav-rep.png" style=" background-repeat:repeat-x; height:50px;"><a href="#">&copy; wi-tribe Pakistan Limited. All Rights Reserved &reg;</a></td>
	</tr>

</table>
<!-- End ImageReady Slices -->
</body>
</html>