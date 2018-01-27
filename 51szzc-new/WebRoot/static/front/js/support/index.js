$(function() {

	var $navs = $('.support-tabNav');
	var $items = $('.support-tabItem');

	$navs.on('click', function() {
		var $this = $(this);
		var index = $this.index() - 1;

		if ($this.hasClass('active')) {
			return false;
		}

		$navs.removeClass('active');
		$items.removeClass('active');
		$this.addClass('active');
		$items.eq(index).addClass('active');
	});
});