<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Logger
 *
 * @author Waqas Nasir Toor
 */
class Logger {
    //put your code here
    private $file_ptr;

    public function __construct()
    {

    }
    public function init( $filename )
    {
        $this->file_ptr = fopen($filename, "a+");
    }
    public function info( $text )
    {
        $date_today = date("d-M-Y");
        fputs($this->file_ptr, $date_today . " [Info] " . $text . "\n", 4096);

    }
    public function error( $text )
    {
        $date_today = date("d-M-Y");
        fputs($this->file_ptr, $date_today . " [Error] " . $text . "\n", 4096);
    }
}
?>
