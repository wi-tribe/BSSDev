$(document).ready(function(){

	var cleditors = $('.cledit').cleditor({
			height : 300,
			width:800
		});
	var smallCleditors = $('.small-cledit').cleditor({
			height : 300,
			width:400
		});
		
	$(window).resize(function(){

		$('cledit').each(function(){
		
			this.refresh();
		});
		
	});

});
