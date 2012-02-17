<?
include_once("./var/www/html/dev/smarts_dev/vims/vims_config.php");
include_once ("./var/www/html/dev/smarts_dev/base/CLASSES/DBAccess.php");

include('gacl.class.php');
include('gacl_api.class.php');

require_once( ADODB_DIR .'/adodb.inc.php');
require_once( ADODB_DIR .'/adodb-pager.inc.php');

class permissions
{
   	public function checkPermission($person,$function)
	{
		$gacl = new gacl();
		$arosection = $gacl->getSectionValue($person);
                
                $arosection = $arosection[1];
                
                $result = $gacl->getModule($function);
                $module = $result[1];
                $operation = $result[2];
		
        if($gacl->acl_check($module,$operation,$arosection,$person))
        {	
        	return true;
        }
        else
           { return false; }
    }

    public function getaccessLevel($person,$reportname)
    {
           $gacl_api = new gacl_api();
           $gacl = new gacl();
           $arodetails = $gacl->getSectionValue($person);
           $arosection = $arodetails['SECTION_VALUE'];

           $group = $gacl->getparentGroup($arosection);

          $data = $gacl->getaccesslevelAXO($person,$reportname,$arosection);
        
         if($data == null) return false;

          foreach ($data as $arr)
              {
                        if($arr['ARO']==$person)
                        {
                            return $arr['AXO'];
                        }         
              }
              
              foreach ($data as $arr)
              {
                    if($arr['ARO_GROUP']==$arosection)
                        {
                            return $arr['AXO'];
                        }
              }
              
              foreach ($data as $arr)
                  {
                        if($arr['ARO_GROUP']==$group[0]['VALUE'])
                         {
                            return $arr['AXO'];
                         }
                  }
    }

    public function Login($login_info)
    {
        $gacl = new gacl();
        $response = $gacl->login($login_info);
        return $response;
    }

    public function CloseConnection()
    {
        $gacl = new gacl();
        $gacl->logout();
    }
}
?>


