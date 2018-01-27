$(function() {

	var slideCount = 0;
	var $player = videojs('player');
	
	//类型列表
	var $typeList = $('.help-nav').slick({

		slidesToShow: 6,
		slidesToScroll: 1,
	});
	$('.help-nav-prev').on('click', function() {

		$typeList.slickPrev();
	});
	$('.help-nav-next').on('click', function() {

		$typeList.slickNext();
	});

	//问题列表
	var t = $('.help-issues').slick({

			dots: true,
			arrows: false,
			speed: 300,
			slidesToShow: 1,
			slidesToScroll: 1
		});

	//视频
	var $classList = $('.help-classList').slick({

		dots: true,
		slidesToShow: 4,
		slidesToScroll: 4,
		autoplay: true,
		autoplaySpeed: 3000,
	});

	//切换视频
	$(document).on('click', '.help-classItem', function(event) {

		var $this = $(this);
		setPlayer($this.attr('data-src'), $this.find('img').attr('src'),$this.attr('data-desc'),$this.find('p').text());
		$this.addClass('active').siblings().removeClass('active');
		$('.video-title').show();
	});

	//视频分类
	$('.help-nav-tiem').on('click', function() {

		var $this = $(this);
		var typeId = $this.attr('data-id');

		if ($this.hasClass('active')) {

			return false;
		}


		$.get('/about/videoIndex.html', {

			typeId: typeId
		}, function(data, textStatus, xhr) {

			if (data && data.length > 0) {
				setClassList(data);
			}
			$('.help-nav-tiem').removeClass('active');
			$this.addClass('active');
		}, 'json');

	});

	$player.on('play',function () {
		
		$('.video-title').hide();
	})


	function setPlayer(video, pic, desc,title) {

		$player.src(video);
		$player.poster(pic);
		$('.help-classDesc').text(desc);
		$('.video-title').text(title);
	}

	function setClassList(data) {
		var length = $('.help-classList .slick-slide:not(.slick-cloned)').length;

		while (length > 0) {
			$classList.slickRemove(length, true);
			length--;
		}

		$.each(data, function(index, item) {
			var html = '<div data-desc="'+
				item.fDescription+'" data-src="' +
				item.fVideoUrl + '" class="help-classItem"><img src="' +
				item.fPictureUrl + '" alt="thumbnail" class="help-thumbnail"> <p>' +
				item.fTitle + '</p> </div>';
			$('.help-classList').slickAdd(html);
		});

		$classList.slickPlay();
		setPlayer(data[0].fVideoUrl, data[0].fPictureUrl, data[0].fDescription,data[0].fTitle);
		$('.help-classList .slick-slide:not(.slick-cloned)').eq(0).addClass('active');
	}

	$('.help-nav .slick-slide:not(.slick-cloned) .help-nav-tiem').eq(0).trigger('click');
});