<?	
	include_once("header.php");
	
	$user=new User();
	//check if already logged in
	if($user->IsLoggedIn() )
	{
		ob_end_clean();	
		header("Location: home.php");
		exit;
	}
	if($_POST!= NULL)
	{	
		$login_info['username'] =   $_POST['username'];
		$login_info['password'] =   $_POST['password'];
		if($user->Login($login_info))
		{
			ob_end_clean();	
			header("Location: index.php");
			exit;
		}
	}
?>
              <!-----------------------------THIS PAGE------------------------------------->
			  <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0">
			  <tr>
				<td width="5" align="left" valign="top"><img src="<?=IMAGES?>/left_table_curve.gif" width="5" height="23"></td>
				<td background="<?=IMAGES?>/pink_line.gif" class="whiteHeading">Sales Rep Login Area </td>
				<td width="5" align="right" valign="top"><img src="<?=IMAGES?>/right_table_curve.gif" width="5" height="23"></td>
			  </tr>
			  <tr>
				<td align="left" valign="top" background="<?=IMAGES?>/table_left_line.gif"></td>
				<td valign="top">
				<form id="oe_login" name="oe_login"  method="post" action="<?=$_SERVER['SCRIPT_NAME']?>">
				  <table width="100%" border="0" align="center" cellpadding="2" cellspacing="1">
					<tr>
					  <td colspan="2" class="orangeText">Fields marked with * (asterisk) are necessary </td>
					</tr>
					<tr>
					  <td width="40%">&nbsp;</td>
					  <td class="error"><?=$user->LastMsg;?></td>
					</tr>
					<tr>
					  <td align="right" class="normalFont"><span class="mandatoryRed">*</span>Username </td>
					  <td><input name="username" type="text" class="textbox" id="username"/></td>
					</tr>
					<tr>
					  <td align="right" class="normalFont"><span class="mandatoryRed">*</span>Password </td>
					  <td><input name="password" type="password" class="textbox" id="password" /></td>
					</tr>
					<tr>
					  <td align="right" class="normalFont">&nbsp;</td>
					  <td><a class="orangeText" href="#"></a> </td>
					</tr>
					<tr>
					  <td class="normalFont">&nbsp;</td>
					  <td><input class="button" type="submit" name="Submit" value="Login" /></td>
					</tr>
				  </table>
				  </form>
				</td>
				<td background="<?=IMAGES?>/table_right_line.gif">&nbsp;</td>
			  </tr>        
			  <tr>
				<td colspan="3" height="1" align="left" valign="top" background="<?=IMAGES?>/pink_line.gif"><img src="images/pink_line.gif" width="1" height="1"></td>
			  </tr>
			</table>
			<p>&nbsp;</p>
		<!--------------------------------THE END------------------------------------->
<? include_once("footer.php")?>
