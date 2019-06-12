// JavaScript Document

$(document).ready(function(e) {
    
	$(function(){
	$('.menubtn').live('mousedown',function(){
		var $bg = $('.shadow');
		var $box = $('#nav');
	    if($box.is(':visible') && $bg.is(':visible') ){
			$bg.hide();
			$box.slideUp(300);
		}else{
			$bg.show();
			$box.slideDown(300);
			
		}
	});	
	$('.btn_up').click(function(){
		$('.shadow').hide();
		$('#nav').slideUp(300);	
		
	})
})
	
	$(".btn_plus").toggle(function(){
		$(this).children('img').rotate({ 
  		 angle:0,animateTo:225,easing: $.easing.easeInOutExpo 
   
});
		$('.swhite').fadeIn();
		$('.plusbox').fadeIn();
		
	},function(){
		$(this).children('img').rotate({ 
  		 angle:0,animateTo:-270,easing: $.easing.easeInOutExpo 
   
});
		$('.swhite').fadeOut();
		$('.plusbox').fadeOut();	
	})
$('#nav li a.on').on("click", function () {
			$(this).next('div').slideToggle(100);
			$('#nav li div').not($(this).next()).slideUp(300);
		});

	
});