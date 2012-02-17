<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Email
 *
 * @author Waqas Nasir Toor
 */



include_once('class.phpmailer.php');
/*
 *
 */


class Email {
    
    private $mailer;   //container object
    private $from;    //from email address that is same to be used in AddReplyTo
    private $from_name;
    private $host;
    private $subject;
    private $body;
    private $isHTML;

    public function __construct()
    {
    }
    public function init_mailer()
    {
        $this->mailer = new PHPMailer();
        $this->mailer->IsSMTP();
        $this->mailer->Host = $this->host;
        $this->mailer->From = $this->from;
        $this->mailer->FromName = $this->from_name;
        $this->mailer->AddReplyTo($this->from , $this->from_name);
        $this->mailer->Subject = $this->subject;
        $this->mailer->IsHTML($this->isHTML);
        $this->mailer->Body = $this->body;
    }
    public function set_host( $host )
    {
        $this->host = $host;   
    }
    public function set_from( $from , $from_name )
    {
        $this->from = $from;
        $this->from_name = $from_name;        
    }

    private function set_to( $data_set )
    {
        $array_count = count( $data_set );
        for( $i = 0; $i < $array_count; $i ++ )
        {
            $this->mailer->AddAddress( $data_set[$i]['email_address'], $data_set[$i]['name']);
        }
    }
    public function set_subject( $subject )
    {
        $this->subject = $subject;
    }
    public function set_body( $body , $isHTML = false )
    {
        $this->body = $body;
        $this->isHTML = $isHTML;
    }

    public function send_mail( $data_set )
    {
            $this->set_to($data_set);
            $this->mailer->Send();
            $this->mailer->ClearAddresses();
    }

	public function send_mail_complete($to, $from, $subject,$body, $isHTML )
	{
	//	print_r($to);
	//	print_r($from);
		 $this->set_from($from['EMAIL'],$from['NAME']);
		 $this->set_subject( $subject);
		 $this->set_body( $body, $isHTML);
		 $this->init_mailer();
		 $this->send_mail($to);
		 
	}
}


?>
