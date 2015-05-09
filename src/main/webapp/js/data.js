
$(function(){


		$(".slider")
		.slider({
			max: 5,
			step: 0.1,
			value: 2.5,
			change: function(event, ui) {

				/*var sliderType = $(this).attr("id");
				var value = ui.value;
				console.log( sliderType );
				console.log( value );
				getData();*/
			},
			create: sliderTooltip,
			slide: sliderTooltip
		})
		.slider("pips", {
			rest: "pip"
		});
		
		
		var words = [{ 'text': 'aman', 'weight' : 1 }, { 'text': 'swapnil', 'weight' : 2 }, { 'text': 'ankit', 'weight' : 3 },
		{ 'text': 'shashank', 'weight' : 4 }];
		$('#wordCloud').jQCloud(words);
		
		
		
		 $(".container").on("click", ".btn.btn-default",function(){
			 $(this).attr("disabled", true);
			 $(".btn.btn-default").removeClass("active");
			 $(this).addClass("active");
			 getData();			
			
		 });
		 
		 

		 $(".container").on("click", ".btn.btn-success",function(){
			 $(this).attr("disabled", true);
			 getData();
		 });
		 
		getData();
		
});

var initialValue = 2.5;
var sliderTooltip = function(event, ui) {
    var curValue = ui.value || initialValue;
    var target = ui.handle || $('.ui-slider-handle');                                     
    var tooltip = '<div class="tooltip"><div class="tooltip-inner">' + curValue + '</div><div class="tooltip-arrow"></div></div>';
   $(target).html(tooltip);
}




function getData(){

	$("body").css("cursor", "wait");
    var sendData = {
           			"value" : $("#value").slider('value'),
           			"rooms" : $("#rooms").slider('value'),
           			"cleanliness" : $("#cleanliness").slider('value'),
           			"service" : $("#service").slider('value'),
           			"type" : $(".active").attr("id"),
           			"bussService" : $("#businessservices").slider('value'),
           			"locations" : $("#location").slider('value'),
           			"checkIn" : 0 //$("#checkinCheckout").slider('value')
           		};

	$.ajax({
		method : "POST",
		url : "/salesorderapp/rest/BiGrams/",
		dataType : "json",
		contentType: 'application/json',
		data : JSON.stringify(sendData),
		success: function(response){
			$("body").css("cursor", "default");
			$(".btn.btn-success").attr("disabled", false);
			$(".btn.btn-default").attr("disabled", false);
			$('#wordCloud').jQCloud('update', response);
		},
		error: function(err){
			$(".btn.btn-success").attr("disabled", false);
			$(".btn.btn-default").attr("disabled", false);
			$("body").css("cursor", "default");
			alert(err.statusText);
			console.log(err);
		}
	})
}