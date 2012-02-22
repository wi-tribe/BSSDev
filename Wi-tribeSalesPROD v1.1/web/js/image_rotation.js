var ads_728_90 = new adArray(
					"images/wt_image_library/wt_library_1.jpg","#",
					"images/wt_image_library/wt_library_2.jpg","#",
					"images/wt_image_library/wt_library_3.jpg","#",
					"images/wt_image_library/wt_library_4.jpg","#",
					"images/wt_image_library/wt_library_5.jpg","#",
					"images/wt_image_library/wt_library_6.jpg","#",
					"images/wt_image_library/wt_library_7.jpg","#",
					"images/wt_image_library/wt_library_8.jpg","#",
					"images/wt_image_library/wt_library_9.jpg","#",
					"images/wt_image_library/wt_library_10.jpg","#",
					"images/wt_image_library/wt_library_11.jpg","#",
					"images/wt_image_library/wt_library_12.jpg","#"
			);

	function images_400_300() {
		var ads = ads_728_90;
		var ad_num = getAdNum(ads); 
		document.write('<CENTER><TABLE CELLPADDING=0 CELLSPACING=1 BORDER=0><TR><TD '
		+'ALIGN=CENTER><FONT SIZE=2 FACE=Arial><B>'
		+'</FONT></TD><TR></TR><TD><A HREF="'+ads[ad_num].href+'"><IMG SRC="'+ads[ad_num].src+'" '
		+'WIDTH="400" HEIGHT="300" BORDER=0 name=js_ad></A></TD></TR></TABLE></CENTER>');//  
		link_num = document.links.length-1;
		setTimeout("rotateSponsor_728_90()",5000);
	}
	
	function rotateSponsor_728_90() {
		rotateSponsor(ads_728_90);
	}


 function images_728_90() {
	var ads = ads_728_90;
	var ad_num = getAdNum(ads); 
	document.write('<CENTER><TABLE CELLPADDING=0 CELLSPACING=1 BORDER=0><TR><TD '
	+'ALIGN=CENTER><FONT SIZE=2 FACE=Arial><B>'
	+'</FONT></TD><TR></TR><TD><A HREF="'+ads[ad_num].href+'"><IMG SRC="'+ads[ad_num].src+'" '
	+'WIDTH="728" HEIGHT="90" BORDER=0 name=js_ad></A></TD></TR></TABLE></CENTER>');//  
	link_num = document.links.length-1;
	setTimeout("rotateSponsor_728_90()",5000);
	}
	
	function rotateSponsor_728_90() {
		rotateSponsor(ads_728_90);
	}


	function adArray() {
		for (i=0; i*2<adArray.arguments.length; i++) {
			this[i] = new Object();
			this[i].src = adArray.arguments[i*2];
			this[i].href = adArray.arguments[i*2+1];
		}
		this.length = i;
	}
	function getAdNum(ads) {
		dat = new Date();
		dat = (dat.getTime()+"").charAt(8);
		if (dat.length == 1)
			ad_num = dat%ads.length;
		else
			ad_num = 0;
		return ad_num;
	}
	
	function rotateSponsor(ads) {
		if (document.images) {
			ad_num = (ad_num+1)%ads.length;
			document.js_ad.src = ads[ad_num].src;
			document.links[link_num].href = ads[ad_num].href;
			setTimeout("rotateSponsor_728_90()",5000);
		}
	}
