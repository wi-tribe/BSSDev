<? 
/*************************************************
* Written by Aasim Naveed
* aasim.nazar@gmail.com
* generalized log management
**************************************************/


/**
 * Contains functionality to log important messages and errors
 *
 * @category   General
 * @package    Helper
 * @author     aasim.nazar@gmail.com Original Author
 * @copyright  2007-2020 wi-tribe
 */
class Logging 
{
    // define default log file
    private $log_file = '/var/www/html/logs/ProductionVimsLog.log';
    // define file pointer
    private $fp = null;
    // set log file (path and name)
    public function lfile($path) {
        $this->log_file = $path;
    }

    // write message to the log file
    public function lwrite($class,$method,$file,$line,$contents){
        // if file pointer doesn't exist, then open log file
        if (!$this->fp) $this->lopen();
        // define script name
        $script_name = pathinfo($_SERVER['PHP_SELF'], PATHINFO_FILENAME);
        // define current time
        $time = date('m/d/Y h:i:s a', time());
        // write current time, script name and message to the log file
        fwrite($this->fp, "$time -- [$contents] -- [$line] -- [$method] -- [$class] -- [$file]\n");
    }

    public function LogMsg($class,$method,$file,$line,$contents)
    {
         // if file pointer doesn't exist, then open log file
        if (!$this->fp) $this->lopen();
        // define script name
        $script_name = pathinfo($_SERVER['PHP_SELF'], PATHINFO_FILENAME);
        // define current time

        $time = date('m/d/Y h:i:s a', time());
        // write current time, script name and message to the log file
        fwrite($this->fp, "$time -- [LoginUser:".$_SESSION['username'].",UID:".$_SESSION['user_id']."] [$contents] -- [$line] -- [$method] -- [$class] -- [$file]\n");
    }

    // open log file
    private function lopen(){
        // define log file path and name
        $lfile = $this->log_file;
        // define the current date (it will be appended to the log file name)
        $today = date('Y-m-d');
        // open log file for writing only; place the file pointer at the end of the file
        // if the file does not exist, attempt to create it
        $this->fp = fopen($lfile . '_' . $today, 'a') or exit("Can't open $lfile!");
    }
}

?>