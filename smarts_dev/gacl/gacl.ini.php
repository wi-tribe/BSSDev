;<? //if ( //Cause parse error to hide from prying eyes)?>
;
; *WARNING*
;
; DO NOT PUT THIS FILE IN YOUR WEBROOT DIRECTORY.
;
; *WARNING*
;
; Anyone can view your database password if you do!
;
debug 			= FALSE

;
;Database
;
db_type 		= "oci8"
db_user			= "smarts2"
db_password		= "smarts321"
db_table_prefix		= "ACL_"
DB_TNS				= 	"(DESCRIPTION =
											(ADDRESS_LIST =
												(ADDRESS = (PROTOCOL = TCP)(HOST = 10.1.67.201)(PORT = 1522))
											)
											(CONNECT_DATA =
											   (SERVICE_NAME = pindb)
											)
										  )";

;
;Caching
;
caching			= TRUE
force_cache_expire	= TRUE
cache_dir		= "/tmp/phpgacl_cache"
cache_expire_time	= 600

;
;Admin interface
;
items_per_page 		= 100
max_select_box_items 	= 100
max_search_return_items = 500

;NO Trailing slashes
smarty_dir 		= "smarty/libs"
smarty_template_dir 	= "templates"
smarty_compile_dir 	= "templates_c"

