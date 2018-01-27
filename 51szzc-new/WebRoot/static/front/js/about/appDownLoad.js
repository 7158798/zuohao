$(function() {
	
	var $iphoneNav = $('.step-dot--iphone');
	var $androidNav = $('.step-dot--android');

	var $iphoneStage = $('.step-stages--iphone').slick({
		dots: false,
		speed: 300,
		fade: true,
		slide: '.step-stages--iphone .step-stage',
		cssEase: 'linear',
		lazyLoad: 'ondemand'
	});

	$('.iphone-step .slick-prev,.iphone-step .slick-next').on('click', function() {
		$iphoneNav.removeClass('active').eq($iphoneStage.slickCurrentSlide()).addClass('active');
	});

	var $androidStage = $('.step-stages--android').slick({
		dots: false,
		speed: 300,
		fade: true,
		slide: '.step-stages--android .step-stage',
		cssEase: 'linear',
		lazyLoad: 'ondemand'
	});

	$('.android-step .slick-prev,.android-step .slick-next').on('click', function() {
		$androidNav.removeClass('active').eq($androidStage.slickCurrentSlide()).addClass('active');
	});
});