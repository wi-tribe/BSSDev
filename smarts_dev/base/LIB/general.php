<?



//format mac address
function formatMacAddress($mac)
{
	$small=str_split ($mac ,2);
	
	$count=0;
	foreach ($small as $arr)
	{
		if($count != 0)
			$fmac.=":";
			
		$fmac.=$arr;
		$count++;
	}
		
	return $fmac;
}


//convert given seconds to h m s
function seconds2hms($seconds)
{
	if($seconds<60)
	{//added this check on 03 april 2007
		$seconds=($seconds==1)? $seconds.'sec ' : $seconds.'secs ';
	} else
	{
		$s=round($seconds/60,2);
		$temp=explode(".",$s);
		//modifying 03 april 2007
		if(strlen($temp[1])==1)
		{
			$temp[1]=$temp[1]."0";
		}
		//end modify
		$seconds=substr(round((60*round($temp[1],2))/100),0,2);
		$seconds=($seconds)? $seconds : '';
		if($seconds>0 && $seconds!="")
			$seconds=($seconds==1)? $seconds.'sec ' : $seconds.'secs ';
		
		
		if($temp[0]<60)
		{//added this check on 03 april 2007
			$minutes=($temp[0]==1)? $temp[0].'min ' : $temp[0].'mins ';
		} else
		{
			$m=round($temp[0]/60,2);
			$temp=explode(".",$m);
			//modifying 03 april 2007
			if(strlen($temp[1])==1)
			{
				$temp[1]=$temp[1]."0";
			}
			//end modify
			$minutes=substr(round((60*round($temp[1],2))/100),0,2);
			$minutes=($minutes)? $minutes : '';				
			if($minutes>0 && $minutes!="")
				$minutes=($minutes==1)? $minutes.'min ' : $minutes.'mins ';
				
			$hour=$temp[0];
			$hour=($hour)? $hour : '';
			if($hour>0 && $hour!="")
				$hour=($hour==1)? $hour.'hr ' : $hour.'hrs ';
		}
	}
	
	return $hour.$minutes.$seconds;
}

//adjust currency
function formatJD($value)
{
	$result = number_format($value/100,2,'.',',');
	if($result < 0)
	{
		$result = '('.( (-1)*$result ).')';
	}
	return $result;
}

//detect utf8
function detectUTF8($string)
{
        return preg_match('%(?:
        [\xC2-\xDF][\x80-\xBF]        # non-overlong 2-byte
        |\xE0[\xA0-\xBF][\x80-\xBF]               # excluding overlongs
        |[\xE1-\xEC\xEE\xEF][\x80-\xBF]{2}      # straight 3-byte
        |\xED[\x80-\x9F][\x80-\xBF]               # excluding surrogates
        |\xF0[\x90-\xBF][\x80-\xBF]{2}    # planes 1-3
        |[\xF1-\xF3][\x80-\xBF]{3}                  # planes 4-15
        |\xF4[\x80-\x8F][\x80-\xBF]{2}    # plane 16
        )+%xs', $string);
}


//password generation
function genpassword($length){

    srand((double)microtime()*1000000);

    $vowels = array("a", "e", "i", "o", "u");
    $cons = array("b", "c", "d", "g", "h", "j", "k", "l", "m", "n", "p", "r", "s", "t", "u", "v", "w", "tr",
    "cr", "br", "fr", "th", "dr", "ch", "ph", "wr", "st", "sp", "sw", "pr", "sl", "cl");

    $num_vowels = count($vowels);
    $num_cons = count($cons);

    for($i = 0; $i < $length; $i++){
        $password .= $cons[rand(0, $num_cons - 1)] . $vowels[rand(0, $num_vowels - 1)];
    }

    return substr($password, 0, $length);
}

//replace string
function replacestring($search,$replace,$subject) { 
    $srchlen=strlen($search);    // lenght of searched string 
    if ($srchlen==0) return $subject; 
    $find = $subject; 
    while ($find = stristr($find,$search)) {    // find $search text in $subject - case insensitiv 
        $srchtxt = substr($find,0,$srchlen);    // get new search text  
        $find=substr($find,$srchlen); 
        $subject = str_replace($srchtxt,$replace,$subject);    // replace founded case insensitive search text with $replace 
    } 
    return $subject; 
}

/**
 * @name getLastChar
 * @param String
 * @return int Last Char Position 
 */
function getLastChar($string)
{
	for ($i = 0; $i < strlen($string); $i++) {
		// Load the current character and the next one
        // if the string has not arrived at the last character
        $current_char = substr($string,$i,1);
        
        if(!is_numeric($current_char))
        {
        	$toReturn=$i;
        }
	}
	return $toReturn;
}

//generate the numeric code randomly
function genNumericCode($length){

    srand((double)microtime()*1000000);

    $vowels = array("1", "3", "5", "7", "9");
    $cons = array("0", "2", "4", "6", "8", "15", 
	"46", "85", "05", "03", "92", "33", "41", 
	"68", "51", "53", "55", "56", "97");

    $num_vowels = count($vowels);
    $num_cons = count($cons);

    for($i = 0; $i < $length; $i++){
        $password .= $cons[rand(0, $num_cons - 1)] . $vowels[rand(0, $num_vowels - 1)];
    }

    return substr($password, 0, $length);
}

//function for random no generation seeding
function make_seed() {
    list($usec, $sec) = explode(' ', microtime());
    return (float) $sec + ((float) $usec * 100000);
}

//seeding the above function
function new_pass()
{
    $nums=array('6','7','8','9','10','11','12');
    srand(make_seed());
    $randval = rand();
    $randval = $randval % 7;
    return genpassword($nums[$randval]);
}

//function to generate email
function SendEmail($mTo,$mFrom,$mSubject,$mMessage)
{
    @mail($mTo, $mSubject, $mMessage, "MIME-Version: 1.0\r\nContent-type: text/html; charset=iso-8859-1\r\nFrom: $mFrom\r\nX-Priority: 3 (Normal)" );
}

//function for check email syntax
function emailsyntax_is_valid($email) {
  list($local, $domain) = explode("@", $email);

  $pattern_local = '^([0-9a-z]*([-|_]?[0-9a-z]+)*)(([-|_]?)\.([-|_]?)[0-9a-z]*([-|_]?[0-9a-z]+)+)*([-|_]?)$';
  $pattern_domain = '^([0-9a-z]+([-]?[0-9a-z]+)*)(([-]?)\.([-]?)[0-9a-z]*([-]?[0-9a-z]+)+)*\.[a-z]{2,4}$';

  $match_local = eregi($pattern_local, $local);
  $match_domain = eregi($pattern_domain, $domain);

  if ($match_local && $match_domain) {

  	//if valid email then
    return true;

  } else {

  	//if not valid email
    return false;

  }
}

//validate url IPv4 url validator
function isURLValid($url)
{	
	if (!preg_match('/^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\//i', $url)) 
	{
    	return false; 
	} else 
	{ 

		$hostname=gethostbyname($ip);
		$arr=explode(".",$hostname);		
		if(count($arr)!=4)
			return false;
		return true;
	}
}

/* This function accepts a credit card number and, optionally, a code for
* a credit card name. If a Name code is specified, the number is checked
* against card-specific criteria, then validated with the Luhn Mod 10
* formula. Otherwise it is only checked against the formula. Valid name
* codes are:
*
*    mcd - Master Card
*    vis - Visa
*    amx - American Express
*    dsc - Discover
*    dnc - Diners Club
*    jcb - JCB */

function CCVal($Num, $Name = 'n/a') {

//  Innocent until proven guilty
    $GoodCard = true;

//  Get rid of any non-digits
    $Num = ereg_replace("[^[:digit:]]", "", $Num);

//  Perform card-specific checks, if applicable
    switch ($Name) {

    case "mcd" :
      $GoodCard = ereg("^5[1-5].{14}$", $Num);
      break;

    case "vis" :
      $GoodCard = ereg("^4.{15}$|^4.{12}$", $Num);
      break;

    case "amx" :
      $GoodCard = ereg("^3[47].{13}$", $Num);
      break;

    case "dsc" :
      $GoodCard = ereg("^6011.{12}$", $Num);
      break;

    case "dnc" :
      $GoodCard = ereg("^30[0-5].{11}$|^3[68].{12}$", $Num);
      break;

    case "jcb" :
      $GoodCard = ereg("^3.{15}$|^2131|1800.{11}$", $Num);
      break;
    }

//  The Luhn formula works right to left, so reverse the number.
    $Num = strrev($Num);

    $Total = 0;

    for ($x=0; $x<strlen($Num); $x++) {
      $digit = substr($Num,$x,1);


//    If it's an odd digit, double it
      if ($x/2 != floor($x/2)) {
        $digit *= 2;

//    If the result is two digits, add them
        if (strlen($digit) == 2)
          $digit = substr($digit,0,1) + substr($digit,1,1);
      }

//    Add the current digit, doubled and added if applicable, to the Total
      $Total += $digit;
    }

//  If it passed (or bypassed) the card-specific check and the Total is
//  evenly divisible by 10, it's cool!
    if ($GoodCard && $Total % 10 == 0) return true; else return false;
}

//check witribe username
function checkWitribeUsername($login)
{
    $i=0;
    while ($login[$i]!=''){
	   // echo "$login[$i]<br>";
	    if($login[$i]=='!'||$login[$i]=='`'||$login[$i]=='/'||$login[$i]==','||$login[$i]=='<'||$login[$i]=='>'||$login[$i]=='?'||$login[$i]=='"'||$login[$i]==':'||$login[$i]==';'||$login[$i]=='='||$login[$i]=='+'||$login[$i]=='_'||$login[$i]==')'||$login[$i]=='('||$login[$i]=='*'||$login[$i]=='&'||$login[$i]=='^'||$login[$i]=='%'||$login[$i]=='$'||$login[$i]=='#'||$login[$i]=='['||$login[$i]==']'||$login[$i]=='{'||$login[$i]=='}'){
	                     return true;
	                     break;
	    }
	    $i+=1;
	}
return false;//no error found
}

//function to check firstname validation
function checkName($login)
{
    $i=0;
    while ($login[$i]!=''){
	   // echo "$login[$i]<br>";
	    if($login[$i]=='@'||$login[$i]=='!'||$login[$i]=='`'||$login[$i]=='/'||$login[$i]==','||$login[$i]=='<'||$login[$i]=='>'||$login[$i]=='.'||$login[$i]=='-'||$login[$i]=='?'||$login[$i]=='"'||$login[$i]==':'||$login[$i]==';'||$login[$i]=='='||$login[$i]=='+'||$login[$i]=='_'||$login[$i]==')'||$login[$i]=='('||$login[$i]=='*'||$login[$i]=='&'||$login[$i]=='^'||$login[$i]=='%'||$login[$i]=='$'||$login[$i]=='#'||$login[$i]=='@'||$login[$i]=='['||$login[$i]==']'||$login[$i]=='{'||$login[$i]=='}'){
	                     return true;
	                     break;
	    }
	    $i+=1;
	}
return false;//no error found
}

//function to check general fields for invalids
function invalids($login)
{
    $i=0;
    while ($login[$i]!=''){
	   // echo "$login[$i]<br>";
	    if($login[$i]==' '||$login[$i]=='@'||$login[$i]=='!'||$login[$i]=='`'||$login[$i]=='/'||$login[$i]==','||$login[$i]=='.'||$login[$i]=='<'||$login[$i]=='>'||$login[$i]=='?'||$login[$i]=='"'||$login[$i]==':'||$login[$i]==';'||$login[$i]=='='||$login[$i]=='+'||$login[$i]=='-'||$login[$i]=='_'||$login[$i]==')'||$login[$i]=='('||$login[$i]=='*'||$login[$i]=='&'||$login[$i]=='^'||$login[$i]=='%'||$login[$i]=='$'||$login[$i]=='#'||$login[$i]=='@'||$login[$i]=='['||$login[$i]==']'||$login[$i]=='{'||$login[$i]=='}'){
	                     return true;
	                     break;
	    }
	    $i+=1;
	}
return false;//no error found
}

//checking for email mx records
function checkMxRecord($email)
{
	$domain = substr(strstr($email, '@'), 1);
    //$x = getmxrr($domain, $mxs);
    
    #$mxs[0] will return the first (or only) MX record
    #of the domain. If $mxs[0] has a value, the email
    #address is good, if $mxs[0] comes up empty, the
    #domain has no MX record(s) and cannot receive mail.
    
    if ($mxs[0]=='') {
    	return false;
    }
    
    return true;
}

//get student password encypted
function encStudentPass($pass)
{
	//first encrpting the password
	$pass=md5($pass);
	
	//now trimiming the paaword
	//ecece$pass=substr($pass,0,$trimChar);
	
	//return password
	return $pass;
}

//function to validate some html text area format
function htmlFormat($text)
{
	$msg=nl2br(strip_tags($text, '<b><ol><li><ul><strong><em><hr><i><u><center><img><a><hr><p>'));
	
	return $msg;
}

//function to add,subtract date and time
define("ADD","add",false);
define("SUB","sub",false);
/**
 * @return date
 * @param day day
 * @param month month
 * @param year year
 * @param sec = null second
 * @param min = null minutes
 * @param hr = null hours
 * @param to_day = null to add/subtract days
 * @param to_month = null to add/subtract month
 * @param to_year = null to add/subtract year
 * @param to_sec = null to add/subtract second
 * @param to_min = null to add/subtract minutes
 * @param to_hr = null to add/subtract hours
 * @param Flag ADD | SUB
 * @param format format of the returned date
 * @desc Date Processor
 */
function ProcessDate($day,$month,$year,$sec=0,$min=0,$hr=0,$to_day=0,$to_month=0,$to_year=0,$to_sec=0,$to_min=0,$to_hr=0,$Flag,$format)
{
	if($Flag==ADD)
	{
		$form_D= mktime ($hr+$to_hr,$min+$to_min,$sec+$to_sec,$month+$to_month,$day+$to_day,$year+$to_year);		
	}
	elseif ($Flag==SUB)
	{
		$form_D= mktime ($hr-$to_hr,$min-$to_min,$sec-$to_sec,$month-$to_month,$day-$to_day,$year-$to_year);
	}
	
	return date($format,$form_D);
}

//function to upload images--can be used to upload other files
//with little modifications
/*
returned values
-1=file size increased
-2=file type does not match
-3=unknown upload error

$myFile=uploaded successfully and returns file name
0=file not set

type=0 means just to validate file not to upload
type=1 means to validate and upload
*/
function nowUpload($name,$dir,$size,$type=1)
{
	//valid and active images types
	$cert1 = "image/pjpeg"; //Jpeg type 1
	$cert2 = "image/jpeg"; //Jpeg type 2
	$cert3 = "image/gif"; //Gif type
	//$cert4 = "text/plain"; //Ief type
	$cert5 = "image/png"; //Png type
	//$cert6 = "image/tiff"; //Tiff type
	//$cert7 = "image/bmp"; //Bmp Type
	//$cert8 = "application/msword"; //Wbmp type---
	//$cert9 = "application/mspowerpoint"; //Ras type---power point
	//$cert10 = "application/vnd.ms-powerpoint"; //Pnm type--ppt
	//$cert11 = "application/vnd.ms-powerpoint"; //Pbm type--ppt
	//$cert12 = "application/powerpoint"; //Pgm type--ppt
	//$cert13 = "application/x-mspowerpoint"; //Ppm type---ppt
	//$cert14 = "application/pdf"; //Rgb type---pdf
	//$cert15 = "application/x-bzip2"; //Xbm type--zip
	//$cert16 = "application/x-bzip"; //Xpm type---zip
	//$cert17 = "application/x-gtar"; //Xpm type---zip
	//$cert18 = "multipart/x-gzip"; //Xpm type---zip
	//$cert19 = "application/x-compressed"; //Xpm type---zip
	//$cert20 = "application/x-zip-compressed"; //Xpm type---zip
	//$cert21 = "application/zip"; //Xpm type---zip
	//$cert22 = "multipart/x-zip"; //Xpm type---zip
	//$cert23="text/csv";
	//$cert24="text/comma-separated-values";
	//$cert25="application/csv";
	//$cert26="application/excel";
	//$cert27="application/vnd.ms-excel";
	//$cert28="application/vnd.msexcel";
	//$cert29="text/anytext";
	//$cert30="application/octet-stream";
	//link: http://www.webmaster-toolkit.com/mime-types.shtml 
	//checking that if the file name exists
	if(isset($_FILES[$name]['name']) && !empty($_FILES[$name]['name']))
	{//upload checks				
		
		//checking for the valid file size
		if($_FILES[$name]['size']>$size || $_FILES[$name]['size']<=0)
		{//not valid
			
			//echo "Max size increased ".$_FILES[$name]['size'];
			return -1;
			
		}
		
		//checking for valid file type
		if(($_FILES[$name]['type']==$cert1) || ($_FILES[$name]['type']==$cert2) 
		|| ($_FILES[$name]['type']==$cert3) /*|| ($_FILES[$name]['type']==$cert5) */ 
		 )
		{}else
		{
			//echo "file type not supported";
			return -2;
		}
		
		//checking if the file already exists
		$copy="";
		$n=0;
		
		//spliting the extension and name
		$arr=split("\.",$_FILES[$name]['name']);
		
		//storing file name
		$myFile="";
		for($ia=0;$ia<(count($arr)-1);$ia++)
		{
			$myFile.=$arr[$ia];
		}
		
		//storing extension
		$myExtension=".".$arr[count($arr)-1];
		
		//comparing the existance and renaming new file
		while(file_exists($dir.$myFile.$copy.$myExtension)) {
			$copy = "_copy" . $n;
			$n++;
		}
		
		//storing the new file name
		$myFile = $myFile.$copy.$myExtension;
		
		//setting up the directory variable
		$uploaddir = $dir;
		
		//print "<pre>";

		//checking condition of 'type'
		if($type==1)
		{//valid type
		
			//uploading here
			if (@move_uploaded_file($_FILES[$name]['tmp_name'], $uploaddir . $myFile)) {
				
				//successful upload
			    //print_r($_FILES);
				
			    return $myFile;
			    
			} else {//failed to upload--unknown reason
			    //print "Possible file upload attack!  Here's some debugging info:\n";
			    //print_r($_FILES);
			    return -3;
			}
		} else 
			return true;
	
	}
	
	//file not set
	return 0;
}


//thumbnail generator
//filename is file name
//filepath is absulute path to the file directory with ending slash
//width is thumbnail width
function generateThumbnail($filename,$filepath,$width=100,$height=0)
{
	$file=$filepath.$filename;
	$tn_w=$width;
	
	//detect extension
	$pos=strrpos($filename,".");
	$type=substr($filename,$pos+1);
	$name=substr($filename,0,$pos);

	if($type=="jpeg" || $type=="jpg")
	{//jpg detected 
	
		#-+ Read the source image 
  		$src_img = ImageCreateFromJPEG($file); 

		#-+ Get image width and height 
			$org_h = imagesy($src_img); 
			$org_w = imagesx($src_img); 
		
		#-+ Calculate thumbnail height 
			$tn_h = floor($tn_w * $org_h / $org_w); 
			
		#-+ Initialize destination image
		$yoffset=0; 
		if($height==0)
			$dst_img = imagecreatetruecolor($tn_w,$tn_h); 
		else
		{
			$dst_img= imagecreatetruecolor($tn_w,$height); 
			
			//calculate offset
			if($tn_h!=$height)
			{
				$yoffset=0;//$tn_h-$height
			}
		}
		
		#-+ Do it! 
			imagecopyresampled($dst_img, $src_img, 0, $yoffset, 0, 0, $tn_w, $tn_h, $org_w, $org_h);  
		
		#-+ Save it! 
			ImageJPEG($dst_img, $filepath."_".$filename);  

	}else if($type=="gif")
	{//gif detected
	
		#-+ Read the source image 
  		$src_img = ImageCreateFromGIF($file); 

		#-+ Get image width and height 
			$org_h = imagesy($src_img); 
			$org_w = imagesx($src_img); 
		
		#-+ Calculate thumbnail height 
			$tn_h = floor($tn_w * $org_h / $org_w); 
		
		#-+ Initialize destination image 
			$dst_img = imagecreatetruecolor($tn_w,$tn_h); 
		
		#-+ Do it! 
			imagecopyresampled ($dst_img, $src_img, 0, 0, 0, 0, $tn_w, $tn_h, $org_w, $org_h);  
		
		#-+ Save it! 
			ImageGIF($dst_img, $filepath."_".$filename);
		
	}else if($type=="png")
	{//png detected
	
		#-+ Read the source image 
  		$src_img = ImageCreateFromPNG($file); 

		#-+ Get image width and height 
			$org_h = imagesy($src_img); 
			$org_w = imagesx($src_img); 
		
		#-+ Calculate thumbnail height 
			$tn_h = floor($tn_w * $org_h / $org_w); 
		
		#-+ Initialize destination image 
			$dst_img = imagecreatetruecolor($tn_w,$tn_h); 
		
		#-+ Do it! 
			imagecopyresampled($dst_img, $src_img, 0, 0, 0, 0, $tn_w, $tn_h, $org_w, $org_h);  
		
		#-+ Save it! 
			ImagePNG($dst_img, $filepath."_".$filename);
	}
	
}

//function to maintain the state
/**
 * @return void
 * @param $name colname
 * @param $state state 1=dropdown--state 2=radio--state 3=textarea--no-state=textfield
 * @param $val value
 * @desc post method has more periority :-)
 */
function State($name,$state=0,$val=null)
{	
	global $data;
	if(isset($_POST[$name]) && !empty($_POST[$name]))
	{
		if($state==1 && $val==$_POST[$name])
			echo "selected";
		elseif ($state==2 && $val==$_POST[$name])
			echo "checked";
		elseif($state==3)
			echo stripslashes($_POST[$name]);//Fahad Pervaiz/ added stripslashes function
		elseif($state!=1 && $state!=2 && $state!=3)//Fahad Pervaiz//updated on 09 may 2006
			echo "value=\"".replacestring('"',"''",stripslashes($_POST[$name]))."\"";//Fahad Pervaiz/updated on 25 aug 2006
	} 
	else if(isset($_GET[$name]) && !empty($_GET[$name]))
	{
		if($state==1 && $val==$_GET[$name])
			echo "selected";
		elseif ($state==2 && $val==$_GET[$name])
			echo "checked";
		elseif($state==3)
			echo $_GET[$name];
		elseif($state!=1 && $state!=2 && $state!=3)//Fahad Pervaiz//updated on 09 may, 2006
			echo "value=\"".replacestring('"',"''",stripslashes($_POST[$name]))."\"";
	} else {
		
		if($state==1 && $val==$data[0][$name])
			echo "selected";
		elseif ($state==2 && $val==$data[0][$name])
			echo "checked";
		elseif($state==3)
			echo strip_tags(stripslashes($data[0][$name]));//Fahad Pervaiz/ added stripslashes function
		elseif($state!=1 && $state!=2 && $state!=3)//Fahad Pervaiz//updated on 09 may 2006
			echo "value=\"".replacestring('"',"''",stripslashes($data[0][$name]))."\"";//Fahad Pervaiz/updated on 25 aug 2006
	}
}

function arrayState($name,$state=0,$val=null)
{
	global $data;
	if(isset($_POST[$name]) && !empty($_POST[$name]))
	{
		for($i=0;$i<count($_POST[$name]);$i++)
		{
			if($_POST[$name][$i]==$val)
			{
				if($state==1)
					echo "selected";
				elseif ($state==2 )
					echo "checked";
				elseif($state==3)
					echo stripslashes($_POST[$name]);//Fahad Pervaiz/ added stripslashes function
				elseif($state!=1 && $state!=2 && $state!=3)//Fahad Pervaiz//updated on 09 may 2006
					echo "value=\"".replacestring('"',"''",stripslashes($_POST[$name]))."\"";//Fahad Pervaiz/updated on 25 aug 2006
			}
			
		}
	} 
	else if(isset($_GET[$name]) && !empty($_GET[$name]))
	{
		for($i=0;$i<count($_GET[$name]);$i++)
		{
			if($_GET[$name][$i]==$val)
			{
				if($state==1)
					echo "selected";
				elseif ($state==2 )
					echo "checked";
				elseif($state==3)
					echo stripslashes($_GET[$name]);//Fahad Pervaiz/ added stripslashes function
				elseif($state!=1 && $state!=2 && $state!=3)//Fahad Pervaiz//updated on 09 may 2006
					echo "value=\"".replacestring('"',"''",stripslashes($_GET[$name]))."\"";//Fahad Pervaiz/updated on 25 aug 2006
			}
			
		}
	} else 
	{	
		for($i=0;$i<count($data);$i++)
		{
			if($data[$i][$name]==$val)
			{
				if($state==1)
					echo "selected";
				elseif ($state==2 )
					echo "checked";
				elseif($state==3)
					echo stripslashes($data[$i][$name]);//Fahad Pervaiz/ added stripslashes function
				elseif($state!=1 && $state!=2 && $state!=3)//Fahad Pervaiz//updated on 09 may 2006
					echo "value=\"".replacestring('"',"''",stripslashes($data[$i][$name]))."\"";//Fahad Pervaiz/updated on 25 aug 2006
			}			
		}
	}
}

//this is the similar function as above but with little modification
//that is used to handle the categories in autoselect
function State2($name,$state=0,$val=null)
{
	if(isset($_POST[$name]) && !empty($_POST[$name]))
	{
		if($state==1 && $val==$_POST[$name])
			return "selected";
		/*elseif ($state==2 && $val==$_POST[$name])
			echo "checked";
		elseif($state==3)
			echo $_POST[$name];
		else
			echo "value=\"".$_POST[$name]."\"";*/
	} 
	else if(isset($_GET[$name]) && !empty($_GET[$name]))
	{
		if($state==1 && $val==$_GET[$name])
			return "selected";
		/*elseif ($state==2 && $val==$_GET[$name])
			echo "checked";
		elseif($state==3)
			echo $_GET[$name];
		else
			echo "value=\"".$_GET[$name]."\"";*/
	} 
}

//function to maintain the state
/**
 * @return void
 * @param $name colname
 * @param $state state-1=dropdown--state-2=radio--state-3=textarea--no-state=textfield
 * @param $val value
 * @desc post method has more periority :-)
 *///just returns instead of echo
function State3($name,$state=0,$val=null)
{
	if(isset($_POST[$name]) && !empty($_POST[$name]))
	{
		if($state==1 && $val==$_POST[$name])
			return "selected";
		elseif ($state==2 && $val==$_POST[$name])
			return "checked";
		elseif($state==3)
			return $_POST[$name];
		else
			return "value=\"".$_POST[$name]."\"";
	} 
	else if(isset($_GET[$name]) && !empty($_GET[$name]))
	{
		if($state==1 && $val==$_GET[$name])
			return "selected";
		elseif ($state==2 && $val==$_GET[$name])
			return "checked";
		elseif($state==3)
			return $_GET[$name];
		else
			echo "value=\"".$_GET[$name]."\"";
	} 
}
?>