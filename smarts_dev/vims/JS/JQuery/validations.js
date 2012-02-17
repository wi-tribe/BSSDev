

function isEmail(s){
	var R=/^[a-z����][a-z0-9\._-]*@[a-z0-9����\._-]+\.[a-z]+$/i;
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
function isstr(s){
	var R=/^[a-z���� \r\n]{1,}$/i;
	return R.test(s);
}

function isAlphaNum(s){
	var R=/^[a-z 0-9 \. \_ ]{1,}$/i;
	return R.test(s);
}
function isCPEMAC(s){
	var R=/^[A-F0-9]{1,}$/;
	return R.test(s);
}
function isNumeric(s){
	var R=/^[0-9]{1,}$/;
	return R.test(s);
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
