<?php

	session_start();
	ob_start();
	include_once("../util_config.php");
	$conf=new config();
       
	include_once(UTIL_CLASSDIR."/SalesPersonnel.php");
        
        $permission = new permissions();

	$sales_obj = new SalesPersonnel();
	$sales_rep = $sales_obj->getCurrentCSR();

        if(!$permission->checkPermission($sales_rep['USERID'], 'AllocateCalls'))
        {
            echo "Permission Denied";
            exit();
        }

        $currentCSR['csr_id'] = $sales_rep['SALESPERSONNNEL_ID'];
        $currentCSR['csr_name'] = $sales_rep['FULL_NAME'];

        $Executives = $sales_obj->getMyExecutives($sales_rep['SHOP_ID']);

        $sales = $sales_obj->getSalesByShop($sales_rep['SHOP_ID']);
 ?>
<script type="text/javascript">
var totalChecked=0;

function MarkCustomers()
{
            for (var i = 0; i < document.allocateCalls.customers.length; i++)
            {
                if (eval("document.allocateCalls.customers[" + i + "].checked") == true)
                {
                    document.allocateCalls.customers[i].checked=false;
                }
            }
                
            StartSerial=''; EndSerial=''; CountMarked=0;
            var startserialIndex = document.allocateCalls.startSerial.selectedIndex;
            if(startserialIndex!=0)
            {
                var StartSerial = document.allocateCalls.startSerial.options[startserialIndex].text;
            }

            var enderialIndex = document.allocateCalls.endSerial.selectedIndex;
            if(enderialIndex!=0)
            {
                var EndSerial = document.allocateCalls.endSerial.options[enderialIndex].text;
            }

            if(StartSerial!=null && EndSerial!=null)
            {
               for(var i=0; i<=document.allocateCalls.customers.length; i++)
               {
                    if( i >=(StartSerial-1) && i <=(EndSerial-1) )
                        { document.allocateCalls.customers[i].checked=true; CountMarked++; }
               }
            }

            if(CountMarked>0)
            {
                document.allocateCalls.markSelectedCustomers.disabled=true;
                document.allocateCalls.startSerial.disabled=true;
                document.allocateCalls.endSerial.disabled=true;
                document.getElementById('Count').innerHTML=CountMarked;
            }
}

function ManualChecked()
{
    var total=0;
    var max = document.allocateCalls.customers.length;
    for(var idx = 0; idx < max; idx++)
    {
        if(eval("document.allocateCalls.customers[" + idx + "].checked") == true)
        {
            total += 1;
        }
    }
    if(total==0)
    {           document.getElementById('Count').innerHTML=0;
                document.allocateCalls.startSerial.disabled=false;
                document.allocateCalls.endSerial.disabled=false;
                document.allocateCalls.markSelectedCustomers.disabled=false;
                exit();
    }
    document.getElementById('Count').innerHTML=total;
}

function unCheckAll()
{
    var chkBoxCount = document.allocateCalls.customers.length;
    for (var i = 0; i < chkBoxCount; i++)
        {
            if (eval("document.allocateCalls.customers[" + i + "].checked") == true)
            {
                document.allocateCalls.startSerial.disabled=false;
                document.allocateCalls.endSerial.disabled=false;
                document.allocateCalls.customers[i].checked=false;
            }
        }

      document.allocateCalls.startSerial.selectedIndex=0;
      document.allocateCalls.endSerial.selectedIndex=0;
      document.allocateCalls.markSelectedCustomers.disabled=false;
      document.getElementById('Count').innerHTML= 0;
}

function preSubmitValidateForm()
{
    if(document.getElementById('executive').selectedIndex == 0)
        {
            alert('Please Select Sales Executive');
            exit();
        }

        var executive = document.getElementById('executive').value;
        var chkBoxCount = document.allocateCalls.customers.length;
        customers = [];
        for (var i = 0; i < chkBoxCount; i++)
        {

            if (eval("document.allocateCalls.customers[" + i + "].checked") == true)
            {
                //it's checked so increment the counter
                totalChecked += 1;
                customers.push(document.allocateCalls.customers[i].value);
            }
        }

       var post_func = function()
				{

					var effect = function(data){ $("#result").html(data).show("normal"); 	};
					$.post("FormProcessor/ajax_allocate_calls.php",{customers:customers,executive:executive},effect);
				}
	$("#result").html("<img src='../../images/loading.gif' /> Loading...");
	$("#result").show("normal",post_func);
}

$(function()
{
    var post_func = function()
				{
					var effect = function(data){ $("#return").html(data).show("normal"); };
					$.post("FormProcessor/ajax_get_customers_to_allocate.php",effect);
				}
	$("#return").html("<img src='../../images/loading.gif' /> Loading...");
	$("#return").show("normal",post_func);
});

function showExecutiveAllocatedCalls()
{
    if(document.getElementById('executive').selectedIndex == 0)
        {
            alert('Please Select Sales Executive');
            exit();
        }
       var executive = document.getElementById('executive').value;
       var post_func = function()
				{
					var effect = function(data){ $("#return").html(data).show("normal");  };
					$.post("FormProcessor/ajax_get_customers_to_allocate.php",{executive:executive},effect);
				}
                                
	$("#return").html("<img src='../../images/loading.gif' /> Loading...");
	$("#return").show("normal",post_func);
}


</script>
<form name="allocateCalls" id="allocateCalls" method="post" action="dashboard/ajax_allocate_calls.php" >
<table width="100%">
    <tr><td><div id="result"></div></td></tr>
            <tr>
    <td colspan="7" align="center" style="font-size:20px"><strong>Allocate Calls to Individual CCE's</strong></td>
  </tr>
</table>
    <table width="40%">
        <tr>
            <td>
                <strong>Total Customer Records:</strong></td>
            <td> <?=count($sales);?>
            </td>
        </tr>
        <tr>
            <td>Selection Counter:</td>
            <td>
                <div id="Count">0</div>
            </td>
        </tr>
        <tr>
            <td>
                &nbsp;
            </td>
        </tr>
    </table>
    <table width="100%">
            <tr>
                <td><strong>Mark Customer From Serial:</strong><select name="startSerial" id="startSerial" class="selectbox">
                  <option value=""> --- Serial No ---</option>
                  <?PHP for($i=1;$i<=count($sales);$i++) { ?>
                  <option value="<?=$i?>"><?=$i?></option>
                  <? } ?>
                </select> TO  <select name="endSerial" id="endSerial" class="selectbox">
                  <option value=""> --- Serial No ---</option>
                  <?PHP for($i=1;$i<=count($sales);$i++) { ?>
                  <option value="<?=$i?>"><?=$i?></option>
                  <? } ?>
                </select>||<input type="button" class="button" align="right" name="markSelectedCustomers" id="markSelectedCustomers" value="Mark Customers" onclick="MarkCustomers();"/>||<input type="button" class="button" align="right" name="uncheckMarked" id="uncheckMarked" value="UnCheck Marked" onclick="unCheckAll();"/></td></tr>
             <tr>
                 <td><strong>Executive :</strong> <select name="executive" id="executive" class="selectbox">
                  <option value=""> --- Select Executive ---</option>
                  <?PHP foreach($Executives as $arr) { ?>
                  <? if($currentCSR['csr_id']!=$arr['CHILD_SALESPERSONNEL_ID']) { ?>
                  <option value="<?=$arr['CHILD_SALESPERSONNEL_ID']?>"><?=$arr['CHILD_SALESPERSONNEL_ID']?> --- <?=$arr['FULL_NAME']?> </option>
                  <? } else
                  { ?>
                  <option value="<?=$currentCSR['csr_id']?>"><?=$currentCSR['csr_id']?> --- <?=$currentCSR['csr_name']?> </option>
                  <? } ?>
                  <? } ?>
                </select>||<input type="button" class="button" align="right" name="allocateCalls" id="allocateCalls" value="Allocate Calls" onclick="preSubmitValidateForm();"/>
                ||<input type="button" class="button" align="right" name="show_allocatedCalls" id="show_allocatedCalls" value="Show Allocated Calls" onclick="showExecutiveAllocatedCalls();"/>
    </td> </tr>
             <tr><td><div id="return"></div></td></tr>
</table>
    
</form>



 