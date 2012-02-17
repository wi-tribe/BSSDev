<?php
	session_start();
	ob_start();
	include_once("../vims_config.php");
	$conf=new config();
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>wi-tribe Coverage Tool </title>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="../JS/JQuery/jquery-1.4.2.js"></script>

<script type="text/javascript">
 function SubmitForm()
 {
    if(document.getElementById('Operation').value=="")
    {
        alert('Please Selection Operation');
        return false;
    }
    
    if(document.getElementById('Operation').value=="Update")
    {
            var found=0;
            var SE_Selection=false;
            var f = document.dataload; // The form we're currently working on
            for(var j = 0; j < f.elements.length; j++){
            var e = f.elements[j];
            
            if(e.type == "select-one" && e.value!="" )
              {
                  if(e.name =="Operation" || e.name=="month" || e.name == "year")
                  {
                    found;
                  }
                  else
                  {
                     found = found+1;
                     if(e.value=="Allocate_SE" && SE_Selection==false)
                        {
                            SE_Selection=true;
                        }
                  }
              }
            }
            
            if(found<1)
            {
               alert("Customer ID and One Custom Column is mandatory for Update Operation");
               return false;
            }

            if(SE_Selection==true)
            {
                var radLstCheck=false;
                var radList = document.getElementsByName('allocationsType');
                for (var i = 0; i < radList.length; i++) {
                    if(radList[i].checked)
                    {
                        radLstCheck=true;
                    }
                }

                if(!radLstCheck)
                {
                   alert("Please specify executive allocation");
                   return false;
                }
            }
    }

    if(document.getElementById('userfile').value=="")
    {
        alert("Please Provide CSV formate file");
        return false;
    }
    
     var filename = document.getElementById('userfile').value;
     if( filename.length == 0 ) return false;
     var dot = filename.lastIndexOf(".");
     if( dot == -1 ) return false;
     var extension = filename.substr(dot,filename.length);

     if(extension == ".csv")
     {
         var confirmation= confirm("Are you sure to "+document.getElementById('Operation').value+" data for Month "+ document.getElementById('month').value+","+document.getElementById('year').value);
         if (confirmation== true)
         {
            return true;
         }
        else
          {
             return false;
          }
     }
     else
        {
            alert("Provide CSV file");
            return false;
        }
 }

function show()
{
    if (document.getElementById('allocationType').style.display == 'none')
      {
            document.getElementById('allocationType').style.display = 'inline';
            var radList = document.getElementsByName('allocationsType');
            for (var i = 0; i < radList.length; i++) {
                if(radList[i].checked) radList[i].checked = false;
            }
      }    
}

function hide()
{
      if (document.getElementById('allocationType').style.display == 'inline')
      {
            document.getElementById('allocationType').style.display = 'none';
      }
}

function checkForDuplicates(form,ColumnID) {
    
	var i,j;
	var formElements = form.elements;

	for (i=0; i<formElements.length; i++) {
            for(j=0;j<formElements.length;j++)
            {
                if(i!=j)
                {
                    if (formElements[i].type == "select-one" && formElements[j].type=="select-one") {
                        if(formElements[i].value==formElements[j].value && formElements[i].value!="" && formElements[j].value!="")
                        {
                            alert("Cannot select column twice: please correct your selection");
                             document.getElementById(ColumnID).selectedIndex=0;
                        }
                    }
                }
            }

        }
}

function CheckSelection(Column)
 {
     if(document.getElementById(Column).value=="Allocate_SE")
     {
         show();
     }
     else
      {     var found=0;
            var f = document.dataload; // The form we're currently working on
            for(var j = 0; j < f.elements.length; j++){
            var e = f.elements[j];
            if(e.type == "select-one" && e.value!="")
              {
                  if(e.value=="Allocate_SE")
                  {
                     found = 1;
                  }
              }
            }
            if(found==0)
            {
                hide();
            }
      }
 }
            
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%">
  <tr>
    <td colspan="7" align="center" style="font-size:20px"><strong><font color="#810541">Retention Data Management</font></strong></td>
  </tr>
   <tr>
   <td colspan="2" align="left" style="font-size:12px"><strong><font color="#C24641">Load/Update Retention Portal Customer Data</font></strong></td>
  </tr>
<tr>
    <td>
        <form id="dataload" name ="dataload" method="post" enctype="multipart/form-data" action="Pages/upload.php">
        <table width="80%" border="0" cellpadding="0" cellspacing="1" class="box">
        <tr>
        <td>
         Select Operation <select name="Operation" id="Operation" class="selectbox">
                  <option value=""> --- NONE ---</option>
                  <option value="upload">Upload New Data</option>
                   <option value="Update">Update Existing Data</option>
                </select></td>
        </tr>
            
        <tr><td>&nbsp;</td></tr>
         <tr>
        <td>Uploading for:  Month <select name="month" id="month" class="selectbox">
                <option value="<?=date("m");?>" selected="selected"><?=date("M")?></option>
                 <option value="01"><?="Jan"?></option>
                 <option value="02"><?="Feb"?></option>
                 <option value="03"><?="Mar"?></option>
                 <option value="04"><?="Apr"?></option>
                 <option value="05"><?="May"?></option>
                 <option value="06"><?="Jun"?></option>
                 <option value="07"><?="Jul"?></option>
                 <option value="08"><?="Aug";?></option>
                 <option value="09"><?="Sept"?></option>
                 <option value="10"><?="Oct"?></option>
                 <option value="11"><?="Nov"?></option>
                 <option value="12"><?="Dec"?></option>
                </select>
          Year <select name="year" id="year" class="selectbox">
                <option value="<?=date("Y");?>" selected="selected"><?=date("Y")?></option>
                 <option value="<?=date("Y")+1;?>"><?=date("Y")+1;?></option>
                 <option value="<?=date("Y")+2;?>"><?=date("Y")+2;?></option>
                 <option value="<?=date("Y")+3;?>"><?=date("Y")+3;?></option>
                 <option value="<?=date("Y")+4;?>"><?=date("Y")+4;?></option>
                 <option value="<?=date("Y")+5;?>"><?=date("Y")+5;?></option>
                 <option value="<?=date("Y")+6;?>"><?=date("Y")+6;?></option>
                </select></td>
        </tr>
         <tr><td>&nbsp;</td></tr>
        <tr>
            <td><label> Column 1: </label>
                <input type="hidden" id="Column1" name="Column1" value="CustomerID"/>
         Customer ID</td>
                <td><label> Column 5: </label>
         <select name="Column5" id="Column5" class="selectbox" onchange="CheckSelection(this.id); checkForDuplicates(this.form,this.id); ">
                  <option value=""> --- NONE ---</option>
                  <option value="Due Now">Due Now</option>
                  <option value="Allocate_SE">Executive Allocation</option>
                  <option value="Promo1">Promo1</option>
                  <option value="Promo2">Promo2</option>
                  <option value="PaymentStatus">Payment Status</option>
                  <option value="CustomerStatus">Customer Status</option>
                  <option value="CTelephoneNo">Customer Telephone Number</option>
                  <option value="CMobileNo">Customer Mobile Number</option>
                </select></td>
        </tr>
        <tr>
            <td><label> Column 2: </label>
         <select name="Column2" id="Column2" class="selectbox" onchange="CheckSelection(this.id); checkForDuplicates(this.form,this.id);">
                  <option value=""> --- NONE ---</option>
                  <option value="Due Now">Due Now</option>
                  <option value="Allocate_SE">Executive Allocation</option>
                  <option value="Promo1">Promo1</option>
                  <option value="Promo2">Promo2</option>
                  <option value="PaymentStatus">Payment Status</option>
                  <option value="CustomerStatus">Customer Status</option>
                  <option value="CTelephoneNo">Customer Telephone Number</option>
                  <option value="CMobileNo">Customer Mobile Number</option>
                </select></td>
                <td><label> Column 6: </label>
         <select name="Column6" id="Column6" class="selectbox" onchange="CheckSelection(this.id); checkForDuplicates(this.form,this.id);">
                  <option value=""> --- NONE ---</option>
                  <option value="Due Now">Due Now</option>
                  <option value="Allocate_SE">Executive Allocation</option>
                  <option value="Promo1">Promo1</option>
                  <option value="Promo2">Promo2</option>
                  <option value="PaymentStatus">Payment Status</option>
                  <option value="CustomerStatus">Customer Status</option>
                  <option value="CTelephoneNo">Customer Telephone Number</option>
                  <option value="CMobileNo">Customer Mobile Number</option>
                </select></td>
        </tr>
        <tr>
            <td><label> Column 3: </label>
                <select name="Column3" id="Column3" class="selectbox" onchange="CheckSelection(this.id); checkForDuplicates(this.form,this.id);">
                  <option value=""> --- NONE ---</option>
                  <option value="Due Now">Due Now</option>
                  <option value="Allocate_SE">Executive Allocation</option>
                  <option value="Promo1">Promo1</option>
                  <option value="Promo2">Promo2</option>
                  <option value="PaymentStatus">Payment Status</option>
                  <option value="CustomerStatus">Customer Status</option>
                  <option value="CTelephoneNo">Customer Telephone Number</option>
                  <option value="CMobileNo">Customer Mobile Number</option>
                </select></td>
                <td><label> Column 7: </label>
         <select name="Column7" id="Column7" class="selectbox" onchange="CheckSelection(this.id); checkForDuplicates(this.form,this.id);">
                  <option value=""> --- NONE ---</option>
                  <option value="Due Now">Due Now</option>
                  <option value="Allocate_SE">Executive Allocation</option>
                  <option value="Promo1">Promo1</option>
                  <option value="Promo2">Promo2</option>
                  <option value="PaymentStatus">Payment Status</option>
                  <option value="CustomerStatus">Customer Status</option>
                  <option value="CTelephoneNo">Customer Telephone Number</option>
                  <option value="CMobileNo">Customer Mobile Number</option>
                </select></td>        
        </tr>
        <tr>
            <td><label> Column 4: </label> 
         <select name="Column4" id="Column4" class="selectbox" onchange="CheckSelection(this.id); checkForDuplicates(this.form,this.id);">
                  <option value=""> --- NONE ---</option>
                  <option value="Due Now">Due Now</option>
                  <option value="Allocate_SE">Executive Allocation</option>
                  <option value="Promo1">Promo1</option>
                  <option value="Promo2">Promo2</option>
                  <option value="PaymentStatus">Payment Status</option>
                  <option value="CustomerStatus">Customer Status</option>
                  <option value="CTelephoneNo">Customer Telephone Number</option>
                  <option value="CMobileNo">Customer Mobile Number</option>
                </select></td>
                
                <td><label> Column 8: </label>
         <select name="Column8" id="Column8" class="selectbox" onchange="CheckSelection(this.id); checkForDuplicates(this.form,this.id);">
                  <option value=""> --- NONE ---</option>
                  <option value="Due Now">Due Now</option>
                  <option value="Allocate_SE">Executive Allocation</option>
                  <option value="Promo1">Promo1</option>
                  <option value="Promo2">Promo2</option>
                  <option value="PaymentStatus">Payment Status</option>
                  <option value="CustomerStatus">Customer Status</option>
                  <option value="CTelephoneNo">Customer Telephone Number</option>
                  <option value="CMobileNo">Customer Mobile Number</option>
                </select></td>
                
        </tr>

        <tr><td>&nbsp;</td></tr>
        <tr id="allocationType" style="display:none">
            <td>
                 <table>
                     <tr>
                        <td colspan="2" align="left" style="font-size:10px"><font color="#C24641">Please Specify: Executive Call Allocations</font></td>
                    </tr>
                    <tr>
                        <td>
                            <input type="radio" name="allocationsType" id="allocationsType" value="New Allocation"/> New Allocation
                            <input type="radio" name="allocationsType" id="allocationsType" value="Add to Current Allocation"/> Add to Current Allocation
                        </td>
                    </tr>
                 </table>
            </td>
        </tr>
        <tr>
        <td>
            <input name="userfile" type="file" id="userfile" value=""/>
        </td>
        </tr>
        <tr>
        <td><input name="upload" type="submit" class="box" id="upload" src="../images/upload.png" alt="Angry face" value=" Upload " class="button" onclick="return SubmitForm();"/>
            </td>
        </tr>
        </table>
</form>
    </td>
</tr>
</table>
</body>
</html>