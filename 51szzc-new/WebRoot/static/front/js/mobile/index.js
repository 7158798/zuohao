var reg = {
	checkRegUserName : function() {
		var regType = document.getElementById("regType").value;
		var regUserName = "";
		var desc = '';
		if (regType == 0) {
			// 验证手机号
			regUserName = util.trim(document.getElementById("register-phone").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.8"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.9"];
				} else if (!util.checkMobile(regUserName)) {
					desc = language["comm.error.tips.10"];
				}
			}
		} else {
			// 验证邮箱
			regUserName = util.trim(document.getElementById("register-email").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.11"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.12"];
				} else if (!util.checkEmail(regUserName)) {
					desc = language["comm.error.tips.13"];
				} else if (new RegExp("[,]", "g").test(regUserName)) {
					desc = language["comm.error.tips.14"];
				} else if (regUserName.length > 100) {
					desc = language["comm.error.tips.15"];
				}
			}
		}
		// if (desc != "") {
		// 	util.showerrortips("register-errortips", desc);
		// 	return;
		// } else {
		// 	util.hideerrortips("register-errortips");
		// }
		var url = "/user/reg/chcekregname.html?name=" + encodeURI(regUserName) + "&type=" + regType + "&random=" + Math.round(Math.random() * 100);
		$.get(url, null, function(data) {
			if (data.code == -1) {
				util.showerrortips("register-errortips", data.msg);
				return;
			} else {
				util.hideerrortips("register-errortips");
			}
		}, "json");
	},
	checkPassword : function() {
		var pwd = util.trim(document.getElementById("register-password").value);
		var desc = util.isPassword(pwd);
		if (desc != "") {
			util.showerrortips("register-errortips", desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
		}
		return true;
	},
	checkRePassword : function() {
		var pwd = util.trim(document.getElementById("register-password").value);
		//var rePwd = util.trim(document.getElementById("register-confirmpassword").value);
		var rePwd = pwd;
		var desc = util.isPassword(pwd);
		if (desc != "") {
			util.showerrortips("register-errortips", desc);
			return false;
		} else {
			util.hideerrortips("register-errortips");
		}
		return true;
	},

	checkRegUserNameNoJquery : function() {
		var regType = document.getElementById("regType").value;
		var regUserName = "";
		var desc = '';

		if (regType == 0) {
			// 验证手机号
			regUserName = util.trim(document.getElementById("register-phone").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.8"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.9"];
				} else if (!util.checkMobile(regUserName)) {
					desc = language["comm.error.tips.10"];
				}
			}
		} else {
			// 验证邮箱
			regUserName = util.trim(document.getElementById("register-email").value);
			if (regUserName.indexOf(" ") > -1) {
				desc = language["comm.error.tips.11"];
			} else {
				if (regUserName == '') {
					desc = language["comm.error.tips.12"];
				} else if (!util.checkEmail(regUserName)) {
					desc = language["comm.error.tips.13"];
				} else if (new RegExp("[,]", "g").test(regUserName)) {
					desc = language["comm.error.tips.14"];
				} else if (regUserName.length > 100) {
					desc = language["comm.error.tips.15"];
				}
			}
		}
		if (desc != "") {
			if(regType == 0) {
				util.showerrortips("register-errortips", desc);
			} else {
				util.showerrortips("register-errortips", desc);
			}
			return false;
		} else {
			util.hideerrortips("register-error-phone");
			util.hideerrortips("register-error-email");
			util.hideerrortips("register-errortips");
			return true;
		}
	},
	regSubmit : function() {
		if (!document.getElementById("agree").checked) {
			util.showerrortips("register-errortips", language["comm.error.tips.23"]);
			return;
		}
		var regType = document.getElementById("regType").value;
		//var areaCode = document.getElementById("register-phone-areacode").innerHTML;
		var areaCode = '+86';
		var flag = this.checkRegUserNameNoJquery() && this.checkPassword() && this.checkRePassword();
		if (flag == true) {
			var regUserName = "";
			if (regType == 0) {
				regUserName = util.trim(document.getElementById("register-phone").value);
			} else {
				regUserName = util.trim(document.getElementById("register-email").value);
			}
			validateCode = document.getElementById("register-imgcode").value;
			var pwd = util.trim(document.getElementById("register-password").value);
			var regPhoneCode = 0;
			if (regType == 0) {
				regPhoneCode = document.getElementById("register-msgcode").value;
				if (regPhoneCode == "") {
					util.showerrortips("register-errortips", language["comm.error.tips.60"]);
					return;
				} else {
					util.hideerrortips("register-errortips");
				}
			}
			var regEmailCode = 0;
			if (regType == 1) {
				regEmailCode = document.getElementById("register-email-code").value;
				if (regEmailCode == "") {
					util.showerrortips("register-errortips", language["comm.error.tips.138"]);
					return;
				} else {
					util.hideerrortips("register-errortips");
				}
			}
			var intro_user = $('#intro_user').value;
			// var intro_user = '';
			var url = "/user/reg/index.html?random=" + Math.round(Math.random() * 100);
			var param = {
				regName : regUserName,
				password : pwd,
				regType : regType,
				vcode : validateCode,
				phoneCode : regPhoneCode,
				ecode : regEmailCode,
				areaCode : areaCode,
				intro_user : intro_user
			};
			jQuery.post(url, param, function(data) {
				if (data.code < 0) {
					// 注册失败
					util.showerrortips("register-errortips", data.msg);
					if (data.code == -20) {
//						document.getElementById("register-imgcode").value = "";
						$(".btn-imgcode").click();
					}
				} else {
					//util.showerrortips("register-errortips", language["comm.succ.register"]);
					//window.setTimeout("window.location='/user/security.html?isreg=1'", 2000);
					var phone = regUserName.substr(0,3) + '****' + regUserName.substr(7);
					$('body').scrollTop(0);
					$('.inviteM-success-phone').text(phone)
					$('.inviteM .register,.register-before').hide();
					$('.inviteM-success,.inviteM-down,.register-after').show();
				}
			}, "json");
		}
	},
	areaCodeChange : function(ele, setEle) {
		var code = $(ele).val();
		$("#" + setEle).html("+" + code);
	},
};
$(function() {
	$("#register-sendmessage").on("click", function() {
		var areacode = $("#register-phone-areacode").html();
		var phone = $("#register-phone").val();
		var vcode = $("#register-imgcode").val() ;
		msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone,vcode);
	});
	$(".btn-imgcode").on("click", function() {
		this.src = "/servlet/ValidateImageServlet?r=" + Math.round(Math.random() * 100);
	});
	$("#register-phone").on("blur", function() {
		reg.checkRegUserName();
	});
	$("#register-email").on("blur", function() {
		reg.checkRegUserName();
	});
	$("#register-submit").on("click", function() {
		reg.regSubmit();
	});
	 $(".register-tab").on("click",
			    function() {
			    	util.hideerrortips("register-errortips");
			        that = $(this);
			        var a = that.attr("class");
			        a.indexOf("active") >= 0 || ($(".register-tab").removeClass("active"),
						that.addClass("active"),
						$("." + that.data().show).show(),
						$("." + that.data().hide).hide(),
						$("#regType").val(that.data().type))

	});
	$("#register-areaCode").on("change", function() {
		reg.areaCodeChange(this, "register-phone-areacode");
	});
	$(".btn-sendemailcode").on("click", function() {
		var address = $("#register-email").val();
		if (address == "") {
			util.showerrortips("register-error-email", language["comm.error.tips.7"]);
			return;
		} else {
			util.hideerrortips("register-error-email");
		}
		email.sendcode($(this).data().msgtype, $(this).data().tipsid, this.id, address,$("#register-imgcode").val());
	});

	$('.register-pwdStatus').on('click', function() {
		var $this = $(this);

		if($this.hasClass('show')){

			$this.removeClass('show');
			$('#register-password').attr('type', 'password');
		} else {

			$this.addClass('show');
			$('#register-password').attr('type', 'text');
		}

	});	
});

$(function() {

	$('.mIndex-banner').slick({
		dots: true,
		infinite: true,
		speed: 300,
		slidesToShow: 1,
		lazyLoad: 'ondemand',
		arrows: false,
		autoplay: true,
		autoplaySpeed: 4000,
	});

	//get data
	function getData() {
		$.get('/mobile/getmarketdata.html?random=' + Math.round(Math.random() * 100), function(data) {

			if (data.cncyList.length) {
				var html = '';
				$.each(data.cncyList, function(index, item) {

					var rateType = '';
					if(item.rate > 0) {
						rateType = 'text-danger';
					}
					if(item.rate < 0) {
						rateType = 'text-success';
					}

					html += '<div class="mIndex-dataItem text-center "> <div class="row"> <div class="col-xs-4">' +
						item.cncy_name + '(' +
						item.shortName + ')' + '</div> <div class="col-xs-4" style="color:#5486fc">￥' +
						item.new_price + '</div> <div class="col-xs-4 text-right '+
						rateType+'">' +
						item.rate + '%</div> </div> <div class="row"> <div class="col-xs-4"> <span>24H最高价(￥)</span> <p>' +
						item.OneDayHighest + '</p> </div> <div class="col-xs-4 mIndex-data-gap"> <span>24H最低价(￥)</span> <p>' +
						item.OneDayLowest + '</p> </div> <div class="col-xs-4"> <span>24H成交量</span> <p>' +
						item.OneDayTotal + '</p> </div> </div> </div>';
				});
				$('.mIndex-datas').html(html);
			}
		});
		window.setTimeout(function() {
			getData();
		}, 5000);
	}
	getData();
});