$(function () {

	$('#login-submit').on('click', function () {

		var name = $('#login-account').val();
		var pwd = $('#login-password').val();

		var param = {
			loginName: name,
			password: pwd,
			type: 0
		}

		$.post('/external/login.html', param, function (data) {
			
			if (data.code < 0) {
                if(data.code == -2) {
                    $("#login-password").val("");
                    util.layerTips("login-password", data.msg);
				}else {
                    util.layerTips("login-account", data.msg);
				}
				/*if (data.code == -2) {
					$("#login-password").val("");
					util.layerTips("login-password", data.msg);
				} else if (data.code == -3) {
					$('.js_login').hide();
					$('.google_login').show();
				} else {
					util.layerTips("login-account", data.msg);
				}*/
			} else {
				window.location.href = "/external/user/articleListByWeb.html";
			}
		}, "json");
	});
})