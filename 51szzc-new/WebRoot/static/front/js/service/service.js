$(function() {

	var url = window.location.href;
	var isSearch = (url.indexOf('?') != -1);
	var $tags = $('.service-tag');

	var param = util.getUrlParam();

	//search tags
	$tags.on('click', function() {

		var tagName = $(this).text();

		if (param.id) {
			window.location.href = '/service/ourService.html?' +
				'id=' + param.id +
				'&tag=' + tagName;
		} else {
			window.location.href = '/service/ourService.html?' +
				'tag=' + tagName;
		}
	});

	//search keyword
	$('.service-searchIcon').on('click', function() {

		getkeyword();
	});

	$('#keyword').on('keydown', function(e) {
		if(e.keyCode == 13) {
			
			getkeyword();
			return false;
		}
	});


	function getkeyword() {
		var keyword = util.trim($('#keyword').val());
		if (!keyword.length) {
			$('#keyword').val('');
			return false;
		}
		if (param.id) {
			window.location.href = '/service/ourService.html?' +
				'id=' + param.id +
				'&keyword=' + keyword;
		} else {
			window.location.href = '/service/ourService.html?' +
				'keyword=' + keyword;
		}
	}
});