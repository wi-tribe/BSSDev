<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="ur">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="../../vims/CSS/style.css" rel="stylesheet" type="text/css"></link>
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
<link href="../styles/style.css" rel="stylesheet" type="text/css"></link>
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
</head>
<title>Customer Activation Page</title>
<script type="text/javascript">
function trim(s)
{
	var l=0; var r=s.length -1;
	while(l < s.length && s[l] == ' ')
	{	l++; }
	while(r > l && s[r] == ' ')
	{	r-=1;	}
	return s.substring(l, r+1);
}

function VerfiyCNIC()
{	
	var cnic_var = trim($("#txt_cnic").val());
	var mac_address = $("#txt_mac").val();
        
        if(cnic_var=="")
        {
             alert('Please Enter CNIC');
             exit();
        }

        if(isNaN(cnic_var))
        {
             alert('Please Enter Numbers Only');
             exit();
        }

        if(cnic_var.length < 13 )
        {
             alert('CNIC should be 13 Digit Value');
             exit();
        }
                
	var post_func = function()
				{
					
					var effect = function(data){ $("#verification_info").html(data).show("normal"); 	};
					$.post("../FormProcessor/ajax_verify_cnic.php",{CNIC: cnic_var, MAC: mac_address},effect);
				}
	$("#verification_info").html("<img src='../../images/loading.gif' /> Loading...");
	$("#verification_info").show("normal",post_func);
}

        
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7"><? include_once("../header.php")?></td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7">
      <tr>
        <td>
        <!-- ================================= Start Content TD =============================== -->
                         <form name="search_form" id="search_form" action="" method="post"  >
                            <table width="60%" border="0" cellspacing="5" cellpadding="5" align="center">
                          
                           <tr>
                            <td class="textboxBur">CNIC: </td>
                            <td class="textboxBur" align="center"><input type="text" name="txt_cnic" id="txt_cnic" maxlength="13"/></td>
                            <td align="center"><input type="button" value="Verify CNIC" class="button" onclick="javascript:VerfiyCNIC();"/></td>
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
                            </table>
                          <table width="40%" border="0" align="center">
                          <tr>
                            <td ><div id="verification_info"> 
                                </div>
                            </td>
                          </tr>
                          
                        </table>
                        </form>
                      
        <!-- =============================== End Content TD ================================== -->
       </td>
      </tr>
    </table>
   </td>
  </tr>
  	<tr>
		<td><? include_once("../footer.php")?>
		</td>
	</tr>
</table>
<!-- End ImageReady Slices -->
</body>
</html>