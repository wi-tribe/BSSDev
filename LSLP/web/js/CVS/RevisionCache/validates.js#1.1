function validate(F){
	var E,N,V;
	for(var i=0;i<F.elements.length;i++){
		E=F.elements[i];
		if(E.type=="text"||E.type=="password"||E.type=="textarea"){
			N=E.id;V=E.value;
			if(N.indexOf("req")==3&&V==""){return arf(E,"This field should not be empty");}
			else if(N.indexOf("eml")==0&&V!=""&&!iseml(V)){return arf(E,"Invalid e-mail addresses format");}
			else if(N.indexOf("int")==0&&V!=""&&!isint(V)){return arf(E,"Please enter only integer data.");}
			else if(N.indexOf("prc")==0&&V!=""&&!isprc(V)){return arf(E,"Illegal price format.");}
			else if(N.indexOf("str")==0&&V!=""&&!isstr(V)){return arf(E,"Please enter only character data");}			
			else{
				if(N.indexOf("dat")==0&&V!=""&&!isdat(V,N.substr(6,10))){return arf(E,"Invalid date.");}	
				else if(N.indexOf("daa")==0&&V!=""&&!isdaa(V,N.substr(6,10))){return arf(E,"Invalid date.");}
				else if(N.indexOf("tim")==0&&V!=""&&!istim(V,N.substr(6,3))){return arf(E,"Illegal time.");}
				else if(N.substr(6,3)!="000"&&V!=""&&V.length<parseInt(N.substr(6,3),10)){return arf(E,"The length of this field should not be less than " + parseInt(N.substr(6,3),10));}				
			}
		}
		else{ 
			if(E.type=="select-one"||E.type=="select-multiple")
				if(E.id.indexOf("req")==3&&(E.selectedIndex==-1||E.options[E.selectedIndex].value=="")){
					alert("Please select.");
					E.focus();
					return false;
				}
		}
	}
	//alert(F.elements.password.value);
	//P=F.elements.password.value;
	//RP=F.elements.re_type_password.value;
	/*if(P!=RP)
	{
		alert("Password and re typed Password are not same");
		F.elements.password.focus();
		return false;
	}*/
	
	if(i==F.elements.length) return true;
	
}
function arf(E,M){
	alert(M);
	E.focus();
	E.select();
	return false;
}

function isint(s){
	var R=/^[0-9]{1,}$/;
	return R.test(s);
}

function isprc(s){
	var R=/^[0-9]{1,}(\.[0-9]{1,2}|[0-9]{0,})$/i;
	return R.test(s);
}

function isstr(s){
	var R=/^[a-zäöüß \r\n]{1,}$/i;
	return R.test(s);
}

function istim(s,tf){
	var rE=/(\d{1}|\d{2})(:|\.)(\d{1}|\d{2})$/,rS=":",U=24,T;
	if(tf.substring(1,2)=="p") rS=".";
	T=s.split(rS);
	if(T.length<2) return false;
	switch(tf){
		case "scz":
		case "spz":	
			rE=/(\d{1}|\d{2})(:|\.)(\d{1}|\d{2})( AM| PM)$/i;
			T[1]=T[1].substring(0,s.indexOf(" "));
			U=12;
			break;
	}	
	return(rE.test(s) && parseInt(T[0],10)<U && parseInt(T[1],10)<60);
}

function isdate(s){
	return isdat(s,"ddsmmsyyyy"); 	
}

function iseml(s){
	var R=/^[a-zäöüß][a-z0-9äöüß\._-]*@[a-z0-9äöüß\._-]+\.[a-z]+$/i;
	if(!R.test(s)) return false;
	R=/\.-/;
	if(R.exec(s)) return false;
	R=/@\./;
	if(R.exec(s)) return false;
	R=/\.@/;
	if(R.exec(s)) return false;
	R=/\._/;
	if(R.exec(s)) return false;
	R=/@-/;
	if(R.exec(s)) return false;
	R=/@_/;
	if(R.exec(s)) return false;
	R=/\.\./;
	if(R.exec(s)) return false;
	return true;
}	
function isdaa(s,d){	
	var v=0,f=new Array('ddsmmsyyyy','ddpmmpyyyy','ddsmmsyyzz','ddpmmpyyzz','ddsmnsyyyy','ddpmnpyyyy','ddsmnsyyzz','ddpmnpyyzz','mmsddsyyyy','mmpddpyyyy','mmsddsyyzz','mmpddpyyzz','mnsddsyyyy','mnpddpyyyy','mnsddsyyzz','mnpddpyyzz','yyyysmmsdd');
	switch(d){case"ddsmnsyyyy":s=4;break;case"mmsddsyyyy":s=8;break;case"mnsddsyyyy":s=12;}
	for(i=v;i<v+4;i++){if(isdat(s,f[i])) return true;}
	return false;
}
function isdat(str,datformat){
	var rE=/((\d{1}|\d{2})(\/|\.)(\d{1}|\d{2})(\/|\.)\d{4})$/,rS=/ /,rN=/\./;
	if(datformat.substr(2,1)=="p") rN=/\//;
	switch(datformat){		
		case "ddsmmsyyyy":
		case "ddpmmpyyyy":	
		case "mmsddsyyyy":
		case "mmpddpyyyy":	
			break;
		case "ddsmmsyyzz":
		case "ddpmmpyyzz":
		case "mmsddsyyzz":
		case "mmpddpyyzz":
			rE=/((\d{1}|\d{2})(\/|\.)(\d{1}|\d{2})(\/|\.)(\d{1}|\d{2}))$/;
			break;
		case "ddsmnsyyyy":
		case "ddpmnpyyyy":	
			rE=/((\d{1}|\d{2})(\/|\.)([A-Za-z]{3})(\/|\.)\d{4})$/;
			break;
		case "mnsddsyyyy":
		case "mnpddpyyyy":	
			rE=/(([A-Za-z]{3})(\/|\.)(\d{1}|\d{2})(\/|\.)\d{4})$/;
			break;
		case "ddsmnsyyzz":
		case "ddpmnpyyzz":
			rE=/((\d{1}|\d{2})(\/|\.)([A-Za-z]{3})(\/|\.)(\d{1}|\d{2}))$/;
			break;
		case "mnsddsyyzz":
		case "mnpddpyyzz":
			rE=/(([A-Za-z]{3})(\/|\.)(\d{1}|\d{2})(\/|\.)(\d{1}|\d{2}))$/;
			break;
	}
	if(!rE.test(str)||rN.exec(str)||rS.exec(str))
		return false;
	else{
		var D=splitdate(str,datformat);
		if(!(D[2]>1753&&D[2]<=9999)||D[1]==0||D[0]==0) return false;
		if(daycount(D[2],D[1])>=D[0]) return true;
	}
	return false;
}		
function splitdate(dat,datFormat){
	var s="/";
	datFormat=datFormat.toLowerCase();
	if(datFormat.charAt(2)=="p") s=".";
	var D=dat.split(s),t;
	if(datFormat.charAt(0)!="d"){t=D[0];D[0]=D[1];D[1]=t;}
	if(datFormat.charAt(1)=="n"||datFormat.charAt(4)=="n"){
		var m=new Array('','jan','feb','mar','apr','may','jun','jul','aug','sep','oct','nov','dec'),i;		
		D[1]=D[1].toLowerCase();
		for(i=1;i<13;i++)
			if(m[i]==D[1]){D[1]=i;break;}		
		if(i==13)D[1]=0;
	}  
	if(datFormat.charAt(9)=="z"){
		if(D[2].length==1) 
			D[2]="200"+D[2];
		else
			D[2]="20"+D[2];	
	}
	D[0]=parseInt(D[0],10);
	D[1]=parseInt(D[1],10);
	D[2]=parseInt(D[2],10);
	return D;
}
function daycount(Year,Month){
	var D=new Array(0,31,28,31,30,31,30,31,31,30,31,30,31);
	if(Month==2&&((Year%100==0 && Year%400==0)||(Year%100!=0 && Year%4==0))) return 29;
	return D[Month];		
}

function formatdate(dat,OldFormat,NewFormat){
	if(!isdat(dat,OldFormat)) return "";
	var D=splitdate(dat,OldFormat);
	return createdate(D[2],D[1],D[0],NewFormat); 
}
function createdate(Year,Month,Day,datFormat){
	var m=new Array('','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'),s="-";
	datFormat=datFormat.toLowerCase();
	if(datFormat.charAt(2)=="p") s="-";
	if(datFormat.charAt(1)=="n"||datFormat.charAt(4)=="n") Month=m[Month];
	if(datFormat.charAt(9)=="z") Year=String(Year).substring(4,2);
	if(datFormat.charAt(0)!="d") return Year+ s + Month + s + Day ;
	return Year+ s + Month + s + Day ;
}
function getcalendertable(Year,Month){
	var D=new Date(),L=new Array('Su','Mo','Tu','We','Th','Fr','Sa');			
	D.setYear(Year);D.setMonth(Month-1);D.setDate(1);
	var C=D.getDay(),i,w;
	w='<TABLE width=170 cellpadding=2 cellspacing=0 border=0><TR bgcolor=#E0E0E0>';
	for(i=0;i<7;i++) w+='<TD align=center><FONT face=arial size=2><B>' + L[i] + '</B></FONT></TD>';
	w+='</TR><TR>';
	if(C>0) for(i=0;i<C;i++) w+='<TD bgcolor=#F0F0F0>&nbsp;</TD>';
	for(i=1;i<=daycount(Year,Month);i++){
		w+="<TD bgcolor=#F0F0F0 align=center><A href=javascript:jWDGiveDate('" + i + "') style='TEXT-DECORATION: none'><FONT color=black size=1 face=arial>" + i + "</FONT></A></TD>";
		C++;
		if(C==7){w+='</TR><TR>';C=0;}
	}
	if(C>0){
		for(i=C;i<7;i++) w+='<TD bgcolor=#F0F0F0>&nbsp;</TD>';
		w+='</TR>';
	}
	else
		w+='<TD height=1></TD><TR>';
	return w + '</TABLE>';
}
function showcalender(str){
	var w="<HTML><HEAD><TITLE>Select Date</TITLE></HEAD><BODY onLoad='jWDAddData()' leftmargin=11 topmargin=10 marginwidth=10 marginheight=10>\r\n<TABLE width=175 cellpadding=2 cellspacing=0 border=0><TR>"
		+ "<TD align=left><FORM name='f'><SELECT name='mon' width=50 onChange='javascript:jWDUpdateMiddleString(self.document.h.yea.options[self.document.h.yea.options.selectedIndex].value,self.document.f.mon.options[self.document.f.mon.options.selectedIndex].value)'><OPTION>&nbsp;</OPTION><OPTION>&nbsp;</OPTION><OPTION>&nbsp;</OPTION></SELECT></FORM></TD>"
		+ "<TD align=right><FORM name='h'><SELECT name='yea' width=60 onChange='javascript:jWDUpdateMiddleString(self.document.h.yea.options[self.document.h.yea.options.selectedIndex].value,self.document.f.mon.options[self.document.f.mon.options.selectedIndex].value)'><OPTION>&nbsp;</OPTION><OPTION>&nbsp;</OPTION><OPTION>&nbsp;</OPTION></SELECT></FORM></TD>"
		+ "</TR></TABLE><SPAN id='DatePart' style=\"LEFT:11px;POSITION:absolute;TOP:42px;\"></SPAN></BODY></HTML><" + "SCRIPT language='Javascript'>" 
		+ "function jWDAddData(){\r\n;"
		+ "var m=Array('','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');\r\n"
		+ "var D=new Date(),Y=D.getFullYear(),M=D.getMonth()+1,i=0;self.document.f.mon.options.length=0;\r\n"
		+ "for(i=1;i<m.length;i++)self.document.f.mon.options[self.document.f.mon.options.length]=new Option(m[i],i);\r\n"
		+ "self.document.f.mon.options[M-1].selected=true;self.document.h.yea.options.length=0;\r\n"
		+ "for(i=1900;i<Y+1;i++)self.document.h.yea.options[self.document.h.yea.options.length]=new Option(i,i);\r\n"
		+ "self.document.h.yea.options[Y-1900].selected=true;jWDUpdateMiddleString(Y,M);}\r\n"
		+ "function jWDUpdateMiddleString(Year,Month){\r\n"
		+ "var w=window.opener.getcalendertable(Year,Month);"
		+ "if(self.document.all)document.all['DatePart'].innerHTML=w;"
		+ "else{self.document.DatePart.document.open();self.document.DatePart.document.write(w);self.document.DatePart.document.close();}}\r\n"
		+ "function jWDGiveDate(Day){window.opener." + str + ".value=opener.createdate(self.document.h.yea.options[self.document.h.yea.options.selectedIndex].value,self.document.f.mon.options[self.document.f.mon.options.selectedIndex].value,Day,'" + mid(str,instrrev(str,".")+6,10) + "');window.close();}\r\n</SCRIPT>" 
	var l=open('','DateBox',"scrollbars=no,titlebar=no,height=180,width=190,top="+((screen.height)/2 - 100)+",left="+((screen.width)/2 - 100));
	l.document.write(w);
	l.document.close();
	l.focus();
}

function matchCurrentDate()
{
	var D=new Date(),Y=D.getFullYear(),M=D.getMonth()+1;
	var dt=D.getDate();
	var curDate=M+"/"+dt+"/"+Y;
	
	var cdate=document.personal_information.date_of_birth.value;
	
	if(Date.parse(curDate)<=Date.parse(cdate))
	{
		alert("Birth day can not be greater than todays date");
		return false;
	}
	else
		return true;
}


function trim(str){
	var t
	if(typeof(str)=="object")
		t=str.value;
	else
		t=str;
	t=ltrim(t);t=rtrim(t);
	if(typeof(str)=="object") 
		str.value=t;
	else
		return t;
}
function ltrim(str){
	for(i=0;str.charAt(i)==" "||str.charAt(i)=="\r"||str.charAt(i)=="\n"; i++){}
	return str.substr(i,str.length);
}
function rtrim(str){
	for(i=str.length-1;str.charAt(i)==" "||str.charAt(i)=="\r"||str.charAt(i)=="\n";i--){}
	return str.substr(0,i+1);
}	
function checkformaxlength(ele,MaxLength){
	if(ele.value.length>MaxLength){
		alert("Die Länge dieses Feldes sollte nicht größer sein als " + MaxLength + ".");
		ele.focus();
		ele.select();
		return false;
	}	
	return true;
}
function mid(exp,start,len){
	if(start<0||len<0)return "";
	var L=String(exp).length;
    return String(exp).substring(start,(start+len>L)?L:start+len);
}
function right(exp,n){
    if(n<=0) return "";
	if(n>String(exp).length) return exp;
	var L=String(exp).length;
	return String(exp).substring(L,L-n);
}
function left(exp,n){
	if(n<=0) return "";
	if(n>String(exp).length) return exp;
	return String(exp).substring(0,n);
}
function len(exp){
	return String(exp).length;
}
function replace(exp,find,rep){
	if(find.length ==0 || exp.length < find.length) return exp;
	return exp.split(find).join(rep);
}
function replaceenter(exp,rep){
	return exp.replace(/\r\n/g,rep);
}
function count(exp,find){
	if(find.length==0||exp.length<find.length) return 0;
	return exp.split(find).length-1;
}
function instr(exp,find){
	if(find.length==0||exp.length<find.length) return 0;
	a=exp.split(find);
	if(a.length-1!=0) return a[0].length+1;
	return 0;
}
function instrrev(exp,find){
	var e=exp.length,f=String(find).length,i;
	if(e>=f&&f>0) for(i=e-1;i>=0;i--) if(exp.substr(i,f)==find) return ++i;
	return 0;
}