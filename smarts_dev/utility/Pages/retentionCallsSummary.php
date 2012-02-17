<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
       
	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR();
        
        $permission = new permissions();
        $accesslevel = $permission->getaccessLevel($_SESSION['username'], 'retentionCallSummary');

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
            $users = $sales_obj->getShopExecutives($filters['Shop']);
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
<script src="../js/ajax.js" type="text/javascript" language="javascript"></script>

<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>
</head>
<title>Retention Call Summary</title>
<script type="text/javascript">
$(function()
{
     var myDate = new Date();
    var month = myDate.getMonth() + 1;
    var date1 = myDate.getFullYear()+ '/' + month + '/' + '01';
    $("#start_date").val(date1);

    var month = myDate.getMonth() + 1;
    var date2 = myDate.getFullYear() + '/' + month + '/' + myDate.getDate() ;
    $("#end_date").val(date2);
    
    $('#start_date').datepicker(
		{
                        dateformat: 'yyyy/mm/dd',
			changeMonth: true,
			changeYear: true
		}
	);
	$('#end_date').datepicker(
		{
                        dateformat: 'yyyy/mm/dd',
			changeMonth: true,
			changeYear: true
		}
	); 
	
});


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

function getReport()
{
     var post_func = function()
				{
					var effect = function(data){ $("#ReportData").html(data).show("normal"); };
					$.post("../FormProcessor/ajax_retentionCallSummary.php",$("#retentionCallsSummary").serialize() ,effect);
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
					<td colspan="2" align="center" style="font-size:16px"><strong>CC Retention Calls Summary <?=date("M").'-'.date("Y");?> </strong></td>
					</tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                        </tr>	
                       </table>


                   <form name="retentionCallsSummary" id="retentionCallsSummary" method="post" action="">
                 <input type="hidden" name ="permission" id="permission" value="<?=$accesslevel?>"/>

                    <table width="100%">
                       
					<tr>
						<td>
							<table width="100%">
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
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td> Start Date <input name="start_date" id="start_date" class="date-picker"/></td>
						<td> End Date <input name="end_date" id="end_date" class="date-picker"/>
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
                     <tr>
                         <td>&nbsp;</td>
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