<?

include_once('c:\apache2triad\htdocs\phpgacl\gacl.class.php');

/*$gacl_options = array(
								'debug' => $gacl_options['debug'],
								'items_per_page' => 100,
								'max_select_box_items' => 100,
								'max_search_return_items' => 200,
								'db_type' => $gacl_options['db_type'],
								'db_host' => $gacl_options['db_host'],
								'db_user' => $gacl_options['db_user'],
								'db_password' => $gacl_options['db_password'],
								'db_name' => $gacl_options['db_name'],
								'db_table_prefix' => $gacl_options['db_table_prefix'],
								'caching' => FALSE,
								'force_cache_expire' => TRUE,
								'cache_dir' => '/tmp/phpgacl_cache',
								'cache_expire_time' => 600
							);

$gacl_api = new gacl_api($gacl_options);
 * 
 */

$gacl = new gacl();
$aco_section_value = Site;
$aco_value =Login;
$aro_section_value =Finance;
$aro_value = Rizwan;

$result = $gacl->acl_check('Site','Login','Finance','Rizwan');

  
 if($gacl->acl_check('Site','Login','Finance','Rizwan'))
         echo "passed <br>";
 else
     echo "failed <br>";

 if ( $gacl->acl_check('system','login','users','john_doe') ) {
	echo "John Doe has been granted access to login!<br>\n";
} else {
	echo "John Doe has been denied access to login!<br>\n";
}
  


?>