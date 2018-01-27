$(function() {
	
	


	$('.panel-heading').on('click', function() {
		var $this = $(this);
		if($this.hasClass('active')) {
			$this.removeClass('active');
		} else {
			
			$this.addClass('active');
		}
	});
})