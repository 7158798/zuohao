var login = {
	loginNameOnblur : function() {
		var uName = document.getElementById("login-account").value;
		if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
			util.layerTips("login-account", language["comm.error.tips.1"]);
		}
	},
	checkLoginUserName : function() {
		var uName = document.getElementById("login-account").value;
		if (uName == "") {
			util.layerTips("login-account", language["comm.error.tips.5"]);
			return false;
		} else if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
			util.layerTips("login-account", language["comm.error.tips.1"]);
			return false;
		}
		util.hideerrortips("login-errortips");
		return true;
	},
	checkLoginPassword : function() {
		var password = document.getElementById("login-password").value;
		if (password == "") {
			util.layerTips("login-password", language["comm.error.tips.2"]);
			return false;
		} else if (password.length < 6) {
			util.layerTips("login-password", language["comm.error.tips.3"]);
			return false;
		}
		util.hideerrortips("login-errortips");
		return true;
	},
	loginSubmit : function() {
		if (login.checkLoginUserName() && login.checkLoginPassword()) {
			var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
			//var url = "/user/login/indexTest.html?random=" + Math.round(Math.random() * 100);
			var uName = document.getElementById("login-account").value;
			var pWord = document.getElementById("login-password").value;
			var longLogin = 0;
			if (util.checkEmail(uName)) {
				longLogin = 1;
			}
			var forwardUrl = "";
			if (document.getElementById("forwardUrl") != null) {
				forwardUrl = document.getElementById("forwardUrl").value;
			}
			var param = {
				loginName : uName,
				password : pWord,
				type : longLogin
			};
			$.post(url, param, function(data) {
				if (data.code < 0) {
					
					if(data.code == -2){
						$("#login-password").val("");
						util.layerTips("login-password", data.msg);
					}else if(data.code == -3){
						$('.js_login').hide()
						$('.google_login').show()
					}else{
						util.layerTips("login-account", data.msg);
					}
				} else {
					if (forwardUrl.trim() == "") {
						window.location.href = "/index.html";
					} else {
						window.location.href = forwardUrl;
					}
				}
			}, "json");
		}
	},
	googleCode2:function(code){
	    var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
		//var url = "/user/login/indexTest.html?random=" + Math.round(Math.random() * 100);
		var uName = document.getElementById("login-account").value;
		var pWord = document.getElementById("login-password").value;
		var longLogin = 0;
		if (util.checkEmail(uName)) {
			longLogin = 1;
		}
		var forwardUrl = "";
		if (document.getElementById("forwardUrl") != null) {
			forwardUrl = document.getElementById("forwardUrl").value;
		}
	    var param = {
	        loginName: uName,
	        password: pWord,
	        type: longLogin,
	        googleCode:code
	    };
		$.post(url,param,function(data){
			if (data.code == 0) {
				if (document.getElementById("forwardUrl") != null && document.getElementById("forwardUrl").value != "") {
	                var forward = document.getElementById("forwardUrl").value;
	                forward = decodeURI(forward);
	                window.location.href = forward;
	            } else {
            		var whref = document.location.href;
	                if (whref.indexOf("#") != -1) {
	                    whref = whref.substring(0, whref.indexOf("#"));
	                }
	                window.location.href = whref;
	            }
			}else if (data.code ==-4) {
	        	util.showerrortips("google_err2", data.msg);
	            document.getElementById("google_password").value = "";
	        }
		});
	}	
};
$(function() {
	$("#login-password").on("focus", function() {
		login.loginNameOnblur();
		util.callbackEnter(login.loginSubmit);
	});
	$("#login-submit").on("click", function() {
		login.loginSubmit();
	});
	$('.google_login #to_sign').click(function(event) {
		var code=$(".google_login .login-ipt").val()
		var exg=/[0-9]{6}/
		if (exg.test(code)) {
			login.googleCode2(code);
			$('#google_err2').html('')
		}else{
			util.showerrortips("google_err2", '请输入六位数字验证码！');
		    return;
		}
	});

});