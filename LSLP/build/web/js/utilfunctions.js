function createRequestObject(){
    var req;
    if(window.XMLHttpRequest){
        //For Firefox, Safari, Opera
        req = new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        //For IE 5+
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else{
        //Error for an old browser
        alert('Your browser is not IE 5 or higher, or Firefox or Safari or Opera');
    }
    return req;
}

function loadinvhist()
{
var a  = new Date();
var ed = 1;
var em = a.getMonth();
em=em+1;
var ey = a.getYear();
var sd=ed;
if (em>=6){
var sm=em-6;
var sy=ey;
}else
{
var sm=em+6;
var sy=ey-1;
}
document.invform.startday.value=sd;
document.invform.startmonth.value=sm;
document.invform.startyear.value=sy;
document.invform.endday.value=ed;
document.invform.endmonth.value=em;
document.invform.endyear.value=ey;
document.invform.submit();
}
