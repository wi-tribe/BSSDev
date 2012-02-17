<?
        include_once('/var/www/html/dev/smarts_dev/gacl/permission.php');
	$user = new permissions();
        if($_SESSION['username']=='admin')
        {
                header('Location: admin/acl_admin.php');
        }
	if($_POST!= NULL)
	{
                $msg='';
		$login_info['username'] =   $_POST['username'];
		$login_info['password'] =   $_POST['password'];
                $data = $user->Login($login_info);
               
               if($data[0]['USERNAME']=='admin')
               {
                    $_SESSION['permission'] = 'authenticated';
                    ob_end_clean();
                    session_start('Admin');
                    set_time_limit(700);
                    header('Location: admin/acl_admin.php');
                    exit;
               }
                else
                 {
                    $msg = "Administrator only allowed!!!";
                 }
	}
?>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="ur">
    <head></head>
<title>wi-tribe Permissioning Tool </title>
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
<link href="admin.css" rel="stylesheet" type="text/css"></link>
              <!-----------------------------THIS PAGE------------------------------------->
              <table>
                  <tr align="left" valign="top" bgcolor="#000000"> 
                      <td bgcolor="#000000"><a href="http://phpgacl.sourceforge.net/" /><img src="admin/images/logo.png"/></td>
                  </tr>
              </table>
              <table width="40%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="White">
			  <tr>
				<td width="5" align="left" valign="top"><img src="../../vims/images/left_table_curve.gif" width="5" height="23"/></td>
				<td background="../../vims/images/pink_line.gif" class="whiteHeading"> Generic Access Control List Login</td>
				<td width="5" align="right" valign="top"><img src="../../vims/images/right_table_curve.gif" width="5" height="23"/></td>
			  </tr>
			  <tr>
				<td align="left" valign="top" background="../../vims/images/table_left_line.gif"></td>
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
                                        <tr>
                                            <td>
                                                <font color="red"><?=$msg?></font>
                                            </td>
                                        </tr>
				  </table>
				  </form>
				</td>
				<td background="images/table_right_line.gif">&nbsp;</td>
			  </tr>        
			  <tr>
				<td colspan="3" height="1" align="left" valign="top" background="../../vims/images/pink_line.gif"><img src="../../vims/images/pink_line.gif" width="1" height="1"/></td>
			  </tr>
			</table>
			<p>&nbsp;</p>
		<!--------------------------------THE END------------------------------------->
</html>
