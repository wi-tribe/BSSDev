<?
//Updated by Fahad Pervaiz
//Layer that communicates with adobdb5
//include adodb5 classes


class DBAccess extends config {

      public $DBlink;
      private $Result;
      public $LastMsg;
      protected $mlog;
	  
      public function connectToDB()
      {
         //Fahad Pervaiz-14 nov 2006-modified this function
		$objRw=rwDB::makeRwConnection();
		$this->DBlink=$objRw->getConnection();		
      }
      
 	  public function __construct()
	  {
	  		$this->connectToDB();
	  		$this->mlog=new Logging();
	  }

          public function  __destruct() {
              $this->DBlink->Disconnect();
          }
      //function to check username and password
      public function CheckUser($table, $fnm, $fval, $fnm1, $fval1)
      {
      		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"SELECT * FROM $table WHERE $fnm='$fval' and $fnm1='$fval1'");
      		
      		//make oracle safe escape string
      		$fval=replacestring("\\","'",$fval);
      		$fval1=replacestring("\\","'",$fval1);
      		
      		$this->Result=$this->DBlink->Execute("SELECT * 
      		FROM $table WHERE $fnm='$fval' and $fnm1='$fval1'");
      		
      		if(!$this->Result->RecordCount() || $this->Result->RecordCount()<0)
      		{      			
      			return false;
      		}
      		
      		$this->Result->Close();
      		return $this->Result->RecordCount();
      		
		    /*if ($this->Result)
		    { 
		    	return true;
		        while (!$this->Result->EOF)
		        {
		            ProcessArray($this->Result->fields);     
		            $this->Result->MoveNext(); 
		        }
		    }*/
      }
	  
	  //returns number of records a query will generate
	  public function RecordsInQuery($query)
		{
			$query=replacestring("NOW()","current_timestamp",$query);
		  $query=replacestring("now()","current_timestamp",$query);
		   $query=replacestring("Now()","current_timestamp",$query);
		   
		   	//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,$query);
		   		   
		   //make oracle safe escape string
      		$query=replacestring("\\","'",$query);
		   
			$this->Result=$this->DBlink->Execute($query);
      		
      		if(!$this->Result->RecordCount() || $this->Result->RecordCount()<0)
      		{      			
      			return false;
      		}
      		
			$totll=$this->Result->RecordCount();
      		$this->Result->Close();
      		return $totll;
      		
		    /*if ($this->Result)
		    { 
		    	return true;
		        while (!$this->Result->EOF)
		        {
		            ProcessArray($this->Result->fields);     
		            $this->Result->MoveNext(); 
		        }
		    }*/		    
		}

      //getting total no of a particular record
      public function GetNumberOfRecords($table, $fnm, $fval)
      {
      		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"SELECT * FROM $table WHERE $fnm='$fval'");
      		
      		//make oracle safe escape string
      		$fval=replacestring("\\","'",$fval);
      		
      		$this->Result=$this->DBlink->Execute("SELECT * 
      		FROM $table WHERE $fnm='$fval'");
      		
      		if(!$this->Result->RecordCount() || $this->Result->RecordCount()<0)
      		{      			
      			return false;
      		}
      		
      		$this->Result->Close();
      		return $this->Result->RecordCount();
      		
		    /*if ($this->Result)
		    { 
		    	return true;
		        while (!$this->Result->EOF)
		        {
		            ProcessArray($this->Result->fields);     
		            $this->Result->MoveNext(); 
		        }
		    }*/
	}			
	
	//function to insert data into the table
	//seqstable for oracle or mssql is tablenameSEQ
	public function InsertRecord($table, $insert, $vals)
	{		
		 $vals=replacestring("NOW()","current_timestamp",$vals);
		  $vals=replacestring("now()","current_timestamp",$vals);
		   $vals=replacestring("Now()","current_timestamp",$vals);
		   
		//Fahad Pervaiz: Commented due to bulk inserts
		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"INSERT INTO $table ($insert)VALUES ($vals)");
		
		//make oracle safe escape string
      	$vals=replacestring("\\","'",$vals);
		
		$this->Result=$this->DBlink->Execute("INSERT INTO $table ($insert)
	            VALUES ($vals)");
      		
  		if(!$this->Result)
  		{      			
  			return false;
  		}
		
  		//get autoincrement field    
  		if(($id=$this->DBlink->Insert_ID()))
  		{
  			return $id;
  		}else if($this->doesSeqExists($table.'_SEQ') && ($id=$this->DBlink->GetOne('SELECT '.$table.'_SEQ.currVal FROM DUAL')))
  		{
  			return $id;
  		}     		
  		
  		return true;
	}
	
	//function check if a seq exists or not
	//true if exists false otherwise
	private function doesSeqExists($tocheck)
	{
		$query="select * from cat where TABLE_TYPE='SEQUENCE'";
		
		if(($data=$this->CustomQuery($query))!=null)
		{
			foreach($data as $arr)
			{
				if(strtolower($arr['table_name'])==strtolower($tocheck))
					return true;
			}
			
			return false;
		}
		
		return false;
	}
	
	//function to retrieve a single field
	public function GetSingleField($table, $fnm, $fval, $required)
	{
		
		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"SELECT $required FROM $table WHERE $fnm='$fval'");
		
		//make oracle safe escape string
      	$fval=replacestring("\\","'",$fval);
		
		$this->Result=$this->DBlink->Execute("SELECT $required  
  		FROM $table WHERE $fnm='$fval'");  				
		
  		if(!$this->Result->RecordCount() || $this->Result->RecordCount()<0)
  		{      			
  			return false;
  		}
  		
  		$toReturn=$this->Result->fields[strtoupper($required)];  		
  		$this->Result->Close();
  		return $toReturn;  		
	}		
		
	//strip slashes
	public function mstripslashes($val)
	{
		$val=stripslashes($val);
		$val=stripslashes($val);
		$val=stripslashes($val);
		$val=stripslashes($val);
		$val=stripslashes($val);
		
		return $val;
	}
	

	
	//function to modify fields by passing query
	public function CustomModify($Cquery)
	{
		$Cquery=replacestring("NOW()","current_timestamp",$Cquery);
		  $Cquery=replacestring("now()","current_timestamp",$Cquery);
		   $Cquery=replacestring("Now()","current_timestamp",$Cquery);
		  
		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,$Cquery);
		
		//make oracle safe escape string
      	$Cquery=replacestring("\\","'",$Cquery);
		
		$this->Result=$this->DBlink->Execute($Cquery);
	  	
		
		if(!$this->Result)
  		{      			
  			return false;
  		}
  		
  		$this->Result->Close();
  		return true;
	}
		
	//function to delete a whole record
	public function DeleteSingleRecord($table, $frn, $fval, $frn1, $fval1)
	{
		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"DELETE FROM $table WHERE $frn='$fval' && $frn1='$fval1'");
		
		//make oracle safe escape string
      	$fval=replacestring("\\","'",$fval);
      	$fval1=replacestring("\\","'",$fval1);
		
		$query="DELETE FROM $table WHERE $frn='$fval' && $frn1='$fval1'";
		$this->Result=$this->DBlink->Execute($query);
	  	
		
		if(!$this->Result)
  		{      			
  			return false;
  		}
  		
  		$this->Result->Close();
  		return true;
	
	}
	
	//delete function
	//function to delete a whole record
	public function DeleteSetOfRecords($table, $frn, $fval)
	{
		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,"DELETE FROM $table WHERE $frn='$fval'");
		
		//make oracle safe escape string
      	$fval=replacestring("\\","'",$fval);
		
		$query="DELETE FROM $table WHERE $frn='$fval'";
		$this->Result=$this->DBlink->_Execute($query);
	  	
		
		if(!$this->Result)
  		{      			
  			return false;
  		}
  		
  		$this->Result->Close();
  		return true;
	
	}
	
	//custom delete query
	//Fahad Pervaiz-- added on 22 jan 2007
	public function CustomDelete($query)
	{
		$query=replacestring("NOW()","current_timestamp",$query);
		  $query=replacestring("now()","current_timestamp",$query);
		   $query=replacestring("Now()","current_timestamp",$query);
		
		 //make oracle safe escape string
      	$query=replacestring("\\","'",$query);  
		 
		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,$query);
		
		$this->Result=$this->DBlink->_Execute($query);	  	
		
		if(!$this->Result)
  		{      			
  			return false;
  		}
  		
  		$this->Result->Close();
  		return true;
	
	}
	
	public function CustomQuery($Query_C)
	{	
		$Query_C=replacestring("NOW()","current_timestamp",$Query_C);
		  $Query_C=replacestring("now()","current_timestamp",$Query_C);
		   $Query_C=replacestring("Now()","current_timestamp",$Query_C);
		
		  // $this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,$Query_C);
		
		//make oracle safe escape string
      	$Query_C=replacestring("\\","'",$Query_C);   
		   
		$this->Result=$this->DBlink->_Execute($Query_C);
      		
  		if(!$this->Result->RecordCount() || $this->Result->RecordCount()<0)
  		{      			
  			return false;
  		}
  		
	    if ($this->Result)
	    {
	        while (!$this->Result->EOF)
	        {
	        	
	            $toReturn[]=$this->Result->fields;     
	            $this->Result->MoveNext(); 
	        }
	        
	        $count=0;
	        foreach($toReturn as $arr)
	        {
	        	foreach ($arr as $key=>$val)
	        	{
	        		//echo $key."--".$val."<BR>";
	        		if(!is_numeric($key))
	        			$final[$count][strtolower($key)]=$val;
	        	}
	        	
	        	$count++;
	        }
	       
	        return $final;
	    }
	    
	    return false;				
	
	}
	
	//pagged rows
	//uses modified adodb pagging function called render
	public function CustomPageQuery($Query_C,$offset,$limit)
	{	
		$Query_C=replacestring("NOW()","current_timestamp",$Query_C);
		  $Query_C=replacestring("now()","current_timestamp",$Query_C);
		   $Query_C=replacestring("Now()","current_timestamp",$Query_C);
		   
		//$this->mlog->LogMsg(__CLASS__,__METHOD__, __FILE__,__LINE__,$Query_C);
		
		//make oracle safe escape string
      	$Query_C=replacestring("\\","'",$Query_C);
		
		/*$paging=new ADODB_Pager(&$this->DBlink,$Query_C);
		$paging->cache=0;
		$paging->curr_page=$offset;
		   
		$this->Result=$paging->Render($limit);*/
		$offset=$offset-1;
		$offset=$offset*$limit;
		$this->Result=$this->DBlink->SelectLimit($Query_C,$limit,$offset);
		
  		if(!$this->Result->RecordCount() || $this->Result->RecordCount()<0)
  		{      			
  			return false;
  		}
  		
	    if ($this->Result)
	    {
	        while (!$this->Result->EOF)
	        {
	        	
	            $toReturn[]=$this->Result->fields;     
	            $this->Result->MoveNext(); 
	        }
	        
	        $count=0;
	        foreach($toReturn as $arr)
	        {
	        	foreach ($arr as $key=>$val)
	        	{
	        		//echo $key."--".$val."<BR>";
	        		if(!is_numeric($key))
	        			$final[$count][strtolower($key)]=$val;
	        	}
	        	
	        	$count++;
	        }
	       
	        return $final;
	    }
	    
	    return false;				
	
	}	
	
	//funtion to free and close sql result and connection
	public function DBDisconnect()
	{
	
	}
	
	//function to report bug incase of database errors
	public function ReportBug($line,$file,$error)
	{
		$mTo="aasim.naveed@pk.wi-tribe.com";
		$mSubject="DB Error";
		$mMessage="Error on line: ".$line."<br>Error in file: ".$file."<br> Error: ".$error;
		$mFrom="aasim.naveed@pk.wi-tribe.com";
		mail($mTo, $mSubject, $mMessage, "MIME-Version: 1.0\r\nContent-type: text/html; 
		charset=iso-8859-1\r\nFrom: $mFrom\r\nX-Priority: 1 (Highest)" );
	}
	
}

//Fahad Pervaiz-14 nov 2006-added rwDB class
//singelton class for managing connection for reading and writing datases
class rwDB extends config 
{
	// Hold an instance of the class
	private static $instance;
	private $conn;
	
	//cannot be called outside
	public function __construct() 
	{
		$this->conn=&NewADOConnection($this->DB_TYPE);
		$this->conn->debug = $this->DB_DEBUG;
		$this->conn->PConnect($this->DB_TNS, $this->DB_USER,$this->DB_PASSWORD);
	}
	
	// The singleton method that makes DB connection
	//this allows only 1 instance of this class to be created
	public static function makeRwConnection() 
	{
	   if (!isset(self::$instance)) {
		   $c = __CLASS__;
		   self::$instance = new $c;
	   }
	
	   return self::$instance;
	}

	//return conn object
	//most probably not going to be used...
	public function getConnection()
	{
		return $this->conn;
	}
	
	//destruction
	public function __destruct()
	{
		$this->conn->Close();
	}
	
}
?>