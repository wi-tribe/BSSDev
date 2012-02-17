	function animatedAjaxCall( page, div )
	{ 
		div = "#"+div;
		var post_func = function()
					{
						$(div).html("<span align='center' style='font-size:16px'>Retrieving data, please wait...<img src='images/loading.gif' border=0> </span>").show("normal");;
						var effect = function(data){$(div).html(data).show("normal");};
						$.post(page,{},effect);
					}
		$("#MsgDiv").fadeOut("slow");
		
		$(div).fadeOut("slow",post_func);
	}

	function test( )
	{	
		alert('heheheheh');

	}

	function processForm(form_name, page, div)
	{
		formValues = $("#"+form_name).serialize();
		disableForm(form_name);
		div = "#"+div;
		var post_func = function() 
					{
						$(div).html("<span align='center' style='font-size:16px'>Please wait...<img src='images/loading.gif' border=0> </span>").show("normal");
						var effect = function(data)
									{
										data = $.trim(data);
										var status = data.substr(0,7);
										if(status=="SUCCESS")
										{					
											data = data.substr(8);
											//$("#FormDiv").hide(0);
										}else
										{
											enableForm(form_name);
										}
										
										$(div).html(data).show(0);
									};
		//alert($("#"+form_name).serialize());
						$.post(page,formValues,effect);
					}
		$(div).hide(0,post_func);

	}

	function disableForm(form_name)
	{
		$(':input','#'+form_name).each
		(
			function()
			{
			   // alert(this.name);
			   // alert(this.value);
				this.disabled=true;
			}
		 );

		
	}

	function enableForm(form_name)
	{
		$(':input','#'+form_name).each
		(
			function()
			{
			  //  alert(this.name);
			  //  alert(this.value);
				this.disabled=false;
			}
		 );

		
	}

	function AjaxPopUpWindow(url,arg, win_properties)
	{
			var effect = function(data)
			{ 
				var x= window.open('index.php','_blank', win_properties);
				x.document.write(data);
				x.document.close();
			};
			$.post(url,arg,effect);
			
			
		
	}

        function getManualSeriesSelection(form_name, page, div,series)
        {
          // = series;
         // IF CHECKED
          $("#"+form_name+" input[name=manual_series_show]").val(series);
         if ($('input[name=Manual_Select_'+series+']').attr("checked") == true)
             {
                 $('#start_serial_txt_'+series).hide();
                  $('#start_serial_'+series).hide();
                  $('#end_serial_txt_'+series).hide();
                  $('#end_serial_'+series).hide();
                 processForm(form_name, page, div);
             }
             else
                 {
                    $('#start_serial_txt_'+series).show();
                    $('#start_serial_'+series).show();
                    $('#end_serial_txt_'+series).show();
                    $('#end_serial_'+series).show();
                    $('#manualVoucherDiv_'+series).empty();
                 }

         //   HIDE SERIES
           
         // ELSE CLEAN MANUAL SELECTION DIV
         // SHOW SERIES
        }