
<!--<div style="{
		width:255px;
		float:left;
		padding:122px 0 0px 0px;
	}">-->
    <div id="sidebar-left">         
    <UL class="mktree" id="tree1">
         
         <li style="{ font-size: 10pt; }" id="lm">Lead Management
            <ul>
                <li><a href="./CreateLead.do">Create Lead</a></li>
                <li><a href='./LeadAccount.do'>View Leads</a></li>
                <li><a href='./Search.do'>Search Leads</a></li>
               <!-- <li><a href="{scdUrl}?salesweb=DEALER&Dealerid={SALES_ID}">Create Account</a></li>-->

            </ul>
        </li>
         <li style="{ font-size: 10pt; }" id="prof">Profile
                     <ul>
                         <li style="{ font-size: 10pt; }" id="ind"><a href="./ViewSales.do?viewProfile=true" >View/Modify Profile</a></li>
                 </ul></li>     
                            <li><a href="./MyCommissionReport.do?salesid=${SALES_ID}">My Commission</a></li>    <!---Added by Murali-->    
                            
                         <li><a href="./MyCommissionPayments.do?salesid=${SALES_ID}">My Commission Payments</a></li>   
         <li style="{ font-size: 10pt; }"><a href="./Logout.do">Logout</a></li>   
        
    </UL>
    
</div>