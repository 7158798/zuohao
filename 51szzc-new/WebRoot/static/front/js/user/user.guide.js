$(function() {

	var videos = $('.center').slick({
		centerMode: true,
		arrows: false,
		slidesToShow: 3
	});
	
	var $videoNavs = $('.userGuide-videoNav');

	$('.videos-prev').on('click', function() {
		$videoNavs.removeClass('active').eq(videos.slickCurrentSlide() - 1).addClass('active');
		videos.slickPrev();
	});
	$('.videos-next').on('click', function() {

		$videoNavs.removeClass('active').eq((videos.slickCurrentSlide() + 1) % 4).addClass('active');
		videos.slickNext();
	});

	$videoNavs.on('click', function() {

		var $this = $(this);
		var index = $this.index();
		var href = $this.attr('data-href');
		$this.addClass('active').siblings().removeClass('active');
		$('.userGuide-textBtn').attr('href',href);
		videos.slickGoTo(index);
	});

	var player = videojs('video');
	

	$(document).on('click', '.slick-center .userGuide-video',function() {
		var src = $(this).attr('data-src');
		var poster = $(this).find('img').attr('src');
		
		if(!src) {
			
			return false;
		}

		player.src({type: "video/mp4", src: src});

		$('#videoDialog').modal('show');
	});

	$('.close').on('click', function() {	
		player.pause();
	});

});