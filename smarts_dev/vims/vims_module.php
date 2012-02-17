<?

public class VimsModule
{
	protected debug;
	
	public __construct()
	{
		$this->debug = $_CONF->debug;
	}
	
	protected function debug($str,$bug)
	{
		if($this->debug || $debug)
		{
			print_r($str);
		}
	}
}

?>