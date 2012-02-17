<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of SMS
 *
 * @author Waqas Nasir Toor
 */


class SMS {
    //put your code here
    private $session_key;

    public function __construct()
    {


    }
    public function init_engine()
    {
        $auth_contents = file_get_contents("http://203.215.160.178:8080/corporatesms/api/auth.jsp?msisdn=923458589049&password=wi-tribe");
		//http://124.109.52.210/api/auth.php?name=923458589049&password=wi-tribe"
        $xml_obj = new SimpleXMLElement($auth_contents);
        $this->session_key = $xml_obj->data;
    }
    public function send_sms( $mobile_number , $sms_text)
    {
		$mobile_number = '92'.substr($mobile_number,1);
        $sms_encode = urlencode($sms_text);
        $sms_contents = file_get_contents("http://203.215.160.178:8080/corporatesms/api/sendsms.jsp?session_id=$this->session_key&to=$mobile_number&text=$sms_encode");
		//http://124.109.52.210/api/sendsms.php?session_id=$this->session_key&to=$mobile_number&time=now&text=$sms_encode
     //   print $sms_contents;
		$xml_obj = new SimpleXMLElement($sms_contents);
        return $xml_obj->response;
    }

}
?>
