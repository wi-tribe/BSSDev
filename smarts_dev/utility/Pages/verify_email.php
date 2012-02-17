<?php
	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
?>
<script type="text/javascript">

function verify_email()
{
	var email = $("#email_address").val();
	var post_func = function()
				{
					$("#verification_info").html("<img src='../images/loading.gif' border=0> verifying email address ...").show("normal");
					var effect = function(data){ $("#verification_info").html(data).show("normal"); 	};
					$.post("./FormProcessor/ajax_email_verification.php",{email_address: email},effect);
				}
	$("#verification_info").fadeOut("slow",post_func);
	

}

        
</script>
<!-- ================================= Start Content TD =============================== -->

<table align="center">
    <tr>
        <td>
                    <table width="100%" align="center">
					 <td align="center">
			<span class="heading"> Verify Customer Email Address </span>	</td>
  </tr>
                       </table>
                </td>
    </tr>
</table>
<form name="search_form" id="search_form" action="" method="post"  >
	<table width="60%" border="0" cellspacing="5" cellpadding="5" align="center">
		<tr>
			<td class="textboxBur">Email Address: </td>
			<td class="textboxBur"><input type="text" name="email_address" id="email_address"  />
		</td>
			<td align="center"><input type="button" value="Verify Email" class="button" onclick="javascript:verify_email();"/></td>
		<tr>
		<tr>
			<td colspan="3"><div id="verification_info"></div></td>
		</tr>
		<tr>
			<td colspan="3"><p>&nbsp;</p></td>
		</tr>
	</table>
</form>

<!-- =============================== End Content TD ================================== -->

   