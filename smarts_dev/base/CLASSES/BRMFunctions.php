<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of BRMFunctions
 *
 * @author PKRizwanAI
 */

include_once(LIB."/nusoap.php");

class BRMFunctions {
    //put your code here

      /*
     * @poid is the voucher FLD POID
     * p2 is the status value of the voucher
     */
    public function UpdateVoucherStatus($status,$poids) {

        $opcode="WRITE_FLDS";
        $inputXML="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>
            <flist>
                <POID>0.0.0.1 /device ".$poids." 0</POID>
                <STATE_ID>".$status."</STATE_ID>
            </flist>";

        $params = array('opcode' => $opcode,'inputXML' => $inputXML);
        $client = new nusoap_client("http://myaccount-uat.wi-tribe.net.pk/infranetwebsvc_uat/services/Infranet?wsdl",true);
        $clientreturn=$client->call("opcode" , $params);

        $xml=simplexml_load_string($clientreturn);
        if(!$xml){
            return FALSE;
        }
        else{
                 
                 $sms=strpbrk($xml->POID,"/");
                 $pieces = explode(" ", $sms);
                 $result=array($pieces[1]);                      
                //print_r( $result[0]);
            if($result[0]==$poids)
                return TRUE;
            else
                return FALSE;
        }


    }  

    /*
     * Function for searching vouchers based on voucher POID
     * @parameter is the voucher POID for example 374270838 and return voucher Status and poid
     */
    public function SearchVoucher($parampoid)
    {
        $opcode="PCM_OP_SEARCH";
        $inputXML="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>
                <flist>
                    <ARGS elem=\"1\">
                        <POID>0.0.0.1 /device ".$parampoid."</POID>
                    </ARGS>
                    <FLAGS>256</FLAGS>
                    <POID>0.0.0.1 /search 0 0</POID>
                    <RESULTS elem=\"1\">
                    <DEVICE_ID/>
                    <POID/>
                    <STATE_ID/>
                    </RESULTS>
                    <TEMPLATE>select x from /device where F1 = V1 </TEMPLATE>
                </flist>";
        $params = array('opcode' => $opcode,'inputXML' => $inputXML);
        $client = new nusoap_client("http://myaccount-uat.wi-tribe.net.pk/infranetwebsvc_uat/services/Infranet?wsdl",true);
        $result=$client->call("opcode" , $params);
        $xml=simplexml_load_string($result);
        if(!$xml){
            trigger_error('Search Error or Error reading XML ! ',E_USER_ERROR);
        }
        $returnval;
            foreach($xml->RESULTS as $row)
            {
                 $sms=strpbrk($row->POID,"/");
                 $pieces = explode(" ", $sms);
                 $returnval=array($row->STATE_ID,$pieces[1],$row->DEVICE_ID);
            }
        return $returnval;
    }
    /*
     * Search the vouchers based of the array on in put vouchers id and
     * return their voucher ids, poids and status
     */
    public function GetVoucherIds($voucherid) {


        $opcode="PCM_OP_SEARCH";
        $inputXML="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>
                <flist>
                    <ARGS elem=\"1\">
                        <DEVICE_ID>".$voucherid."</DEVICE_ID>
                    </ARGS>
                    <FLAGS>256</FLAGS>
                    <POID>0.0.0.1 /search 0 0</POID>
                    <RESULTS elem=\"1\">
                    <DEVICE_ID/>
                    <POID/>
                    <STATE_ID/>
                    </RESULTS>
                    <TEMPLATE>select x from /device where F1 = V1 </TEMPLATE>
                </flist>";
        $params = array('opcode' => $opcode,'inputXML' => $inputXML);
        $client = new nusoap_client("http://myaccount-uat.wi-tribe.net.pk/infranetwebsvc_uat/services/Infranet?wsdl",true);
         //$client = new nusoap_client("http://10.1.67.41/infranetwebsvc_uat/services/Infranet?wsdl",true);
        $result=$client->call("opcode" , $params);
        $xml=simplexml_load_string($result);
        if(!$xml){
            trigger_error('Searching Error or Error reading XML !',E_USER_ERROR);
        }
       
            foreach($xml->RESULTS as $row)
            {
               
                 $sms=strpbrk($row->POID,"/");
                 $pieces = explode(" ", $sms);
                 $result12=array($pieces[1],$row->DEVICE_ID,$row->STATE_ID);
        }
    
        return $result12;

    }


}
?>
