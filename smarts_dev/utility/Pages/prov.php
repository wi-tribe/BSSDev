<?php
	session_start();
	ob_start();

        include_once("../util_config.php");
	//$conf=new config();
        
        //$permission = new permissions();

        //if(!$permission->checkPermission($sales_rep['USERID'], 'ProvisionCPE'))
       // {
       //     echo "Permission Denied";
        //    exit();
        //}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/style.css" rel="stylesheet" type="text/css">

<!--<link href="styles/style.css" rel="stylesheet" type="text/css">!-->
<link href="../js/jquery/ui.datepicker.css" rel="stylesheet" type="text/css">
<script src="../js/jquery/jquery.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/ui.core.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/ui.datepicker.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/jquery.validate.js" type="text/javascript" language="javascript"></script>
<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>
</head>
<title>Customer Activation Page</title></head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script type="text/javascript">

function checkStatus()
{	
	var mac = $("#mac_address").val();
	var post_func = function()
				{
					var effect = function(data){ $("#provision_info").html(data).show("normal"); 	};
					$.post('FormProcessor/ajax_provision_status.php',{mac_address: mac},effect);
				}
	$("#provision_info").fadeOut("slow",post_func);
}

function clearSession()
{
	
	var mac = $("#mac_address").val();
	var post_func = function()
				{
					var effect = function(data){ $("#provision_info").html(data).show("normal"); 	};
					$.post("FormProcessor/ajax_clear_session.php",{mac_address: mac},effect);
				}
	$("#provision_info").fadeOut("slow",post_func);
	

}

function removeProfile(mac,profile)
{
//	alert(mac);
//	alert(profile);
	//var mac = $("#mac_address").val();
	var post_func = function()
				{
					var effect = function(data){ $("#provision_info").html(data).show("normal"); 	};
					$.post("FormProcessor/ajax_remove_profile.php",{mac_address: mac, profile: profile},effect);
				}
	$("#provision_info").fadeOut("slow",post_func);
	
}
        
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
      <table width="100%" border="0" align="center" valign="top" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
      <tr>
        <td>
        <!-- ================================= Start Content TD =============================== -->
                        <table align="center">
                            <tr>
                                <td>
                                    <table width="100%" align="center">
                                        <td align="center">
                                            <span class="heading"> CPE Status </span>	</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                         <form name="search_form" id="search_form" action="" method="post"  >
                            <table width="60%" border="0" cellspacing="5" cellpadding="5" align="center">
                          <tr>
                            <td class="textboxBur">MAC Address: </td>
                            <td class="textboxBur"><input type="text" name="mac_address" id="mac_address"  />
                            </td>
                            <td align="center"><input type="button" value="Check Status" class="button" onclick="javascript:checkStatus();";/></td>
                          <tr>
     <!--                     <tr>
                            <td class="textboxBur">Speed</td>
                            <td class="textboxBur"><select >
                                <option value=""> -- select speed --</option>
                                <option value="post_b_256_64">256 Kbps</option>
                                <option value="post_b_512_128">512 Kbps</option>
                                <option value="post_b_1024_256">1 Mbps</option>
                            </select>
                            </td>
                            <td><input type="button" value="Activate CPE" class="button" onclick="javascript:activateCPE();";/></td>
                          <tr> -->
                          <tr>
                            <td colspan="3"><div id="provision_info"> 
                                </div>
                            </td>
                          </tr>
                          <tr>
                            <td colspan="3"><p>&nbsp;</p></td>
                          </tr>
                        </table>
                        </form>
        
                      
        <!-- =============================== End Content TD ================================== -->
 
<!-- End ImageReady Slices -->
</body>
</html>