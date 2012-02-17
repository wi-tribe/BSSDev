<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="ur">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../../../vims/CSS/style.css" rel="stylesheet" type="text/css"></link>
<style type="text/css">
<!--
body {
	background-color:#CCCCCC ;/*#5F5D60*/
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="../../vims/styles/style.css" rel="stylesheet" type="text/css"></link>
<link href="../js/jquery/ui.datepicker.css" rel="stylesheet" type="text/css"></link>
<script src="../js/jquery/jquery.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/ui.core.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/ui.datepicker.js" type="text/javascript" language="javascript"></script>
<script src="../js/jquery/jquery.validate.js" type="text/javascript" language="javascript"></script>
<style type="text/css">
<!--
.style2 {font-size: 16px}
-->
</style>

<title>BRM Balance Check</title></head>
<script type="text/javascript">

function VerfiyBRM()
{
	
	//var cnic_var = $("#txt_cnic").val();
	var cust_id = $("#txt_cust_id").val();
	var post_func = function()
				{
					
					var effect = function(data){ $("#verification_info").html(data).show("normal"); 	};
					$.post("FormProcessor/ajax_check_customer_bal.php",{customer_id: cust_id},effect);
				}
	$("#verification_info").html("<img src='../../../images/loading.gif' /> Loading...");
	$("#verification_info").show("normal",post_func);
	

}

        
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7">
          <tr>
    <td align="center">
			<span class="heading"> Customer Current Ballance Check </span>	</td>
  </tr>
      <tr>
        <td>
        <!-- ================================= Start Content TD =============================== -->
                         <form name="search_form" id="search_form" action="" method="post"  >
                            <table width="60%" border="0" cellspacing="5" cellpadding="5" align="center">
                          <tr>
                            <td class="textboxBur">Customer ID: </td>
                            <td class="textboxBur"><input type="text" name="txt_mac" id="txt_cust_id"  /></td>
                            <td align="center"></td>
                          </tr>
                           <tr>
                           <!-- <td class="textboxBur"></td>
                            <td class="textboxBur"></td>-->
                            <td align="center" colspan='3'><input type="button" value="Check Customer" class="button" onclick="javascript:VerfiyBRM();"/></td>
                          </tr>
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
                            <td colspan="3"><div id="verification_info"> 
                                </div>
                            </td>
                          </tr>
                          <tr>
                            <td colspan="3"><p>&nbsp;</p></td>
                          </tr>
                        </table>
                        </form>
                      
        <!-- =============================== End Content TD ================================== -->
       </td>
      </tr>
    </table>
<!-- End ImageReady Slices -->
</body>
</html>
