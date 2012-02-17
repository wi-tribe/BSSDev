<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
        
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

<script type="text/javascript" src="../js/jquery.js"></script>
<link href="../css/style.css" rel="stylesheet" type="text/css"></link>
<link href="../css/style_order.css" rel="stylesheet" type="text/css"></link>
<title>Check Provisioning </title></head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<script type="text/javascript">

function GetResult()
{
            var txt_search_term = $("#txt_search_term").val();
            if(txt_search_term == "")
                {
                    alert("Please enter any search term!");
                    return false;
                }
                
         var post_func = function()
				{
					var effect = function(data){ $("#user_div").html(data).show("normal"); };
					$.post("FormProcessor/ajax_check_provisioning.php",$("#search_form").serialize() ,effect);
				}
	$("#user_div").html("<img src='../../../images/loading.gif' /> Loading...");
        
	$("#user_div").show("normal",post_func);
}
        
</script>
<table width="100" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="right" valign="top" bgcolor="#f7f7f7">
    <?php include_once("header.php"); ?>
    </td>
  </tr>
  <tr>
    <td><br>
      <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#CCCCCC" bgcolor="#f7f7f7" class="td">
      <tr>
        <td>
        <!-- ================================= Start Content TD =============================== -->



<table width="500" border="0" cellspacing="0" cellpadding="0"> 
  <tr>
        <td colspan="2" align="center" style="font-size:16px"><strong>Check Provisioning </strong></td>
        </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    
    <td>
	<form name="search_form" id="search_form" action="" method="post"  >
	<table border="0" cellspacing="5" cellpadding="5" align="center">
  <tr>
    <td>MAC Address: </td>
    <td><input type="text" name="txt_search_term" id="txt_search_term" class="textbox" /></td>
   
    <td>&nbsp;</td>
    <td><input type="button" name="Search" id="Search" value="Search" class="button" onclick="GetResult();" /></td>
  </tr>
</table>
<table>
    <tr>
        <td align="center">
            <p>
                <div id="user_div">
                </div>

            </p></td>

    </tr>
</table>
</form>	</td>
  </tr>
</table>


			

        <!-- =============================== End Content TD ================================== -->
        </td>
      </tr>
    </table>
    </br></td>
  </tr>
  	<tr>
		<td>
        <?php include_once("footer.php"); ?>
		</td>
	</tr>
</table>
<!-- End ImageReady Slices -->
</body>
</html>