var security = {
	submitRealCertificationForm : function() {
		var realname = document.getElementById("bindrealinfo-realname").value;
		var address = document.getElementById("bindrealinfo-address").value;
		var identitytype = document.getElementById("bindrealinfo-identitytype").value;
		var identityno = document.getElementById("bindrealinfo-identityno").value;
		var ckinfo = document.getElementById("bindrealinfo-ckinfo").checked;
		var desc = '';
		// 验证是否同意
		if (!ckinfo) {
			desc = language["certification.error.tips.1"];
			util.showerrortips('certificationinfo-errortips', desc);
			return;
		}
		//验证姓名
		if (realname.length > 6 || realname.trim() == "") {
			desc = language["certification.error.tips.2"];
			util.showerrortips('certificationinfo-errortips', desc);
			return;
		}
		// 验证证件类型
		if (identitytype != 0) {
			desc = language["certification.error.tips.3"];
			util.showerrortips('certificationinfo-errortips', desc);
			return;
		}
		// 验证身份证
		var isIDCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		var re = new RegExp(isIDCard);
		if (!re.test(identityno)) {
			desc = language["certification.error.tips.4"];
			util.showerrortips('certificationinfo-errortips', language["comm.error.tips.119"]);
			return false;
		}
		// 隐藏错误消息
		util.hideerrortips('certificationinfo-errortips');
		// 提交信息
		
		var url = "/user/validateIdentity.html?random=" + Math.round(Math.random() * 100);
		var param = {
			realName : realname,
			identityType : identitytype,
			identityNo : identityno
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				
				//first time
				if(isreg == 1) {
					
					var id = param.identityNo.substr(0,3) + '*********' + param.identityNo.substr(-3);

					userInfo.name = realname;
					userInfo.identityType = param.identityType;
					userInfo.id = id;

					$('.account-name').text(userInfo.name);
					$('.accountInfo-id').text(userInfo.id);
					$('#payeeAddr').val(userInfo.name)
					$('.accountInfo').show();
					$('#bindrealinfo').modal('hide');

					setTimeout("$('#addBankDialog').modal('show')",500)
					
					return false;
				}

				util.showerrortips('certificationinfo-errortips', data.msg);
				window.location.reload(false);
			} else {
				util.showerrortips('certificationinfo-errortips', data.msg);
			}
		}, "json");

	},
	loadgoogleAuth : function() {
		var url = "/user/googleAuth.html?random=" + Math.round(Math.random() * 100);
		var param = null;
		$.post(url, param, function(data) {
			if (data.code == 0) {
				if (navigator.userAgent.indexOf("MSIE") > 0) {
					$('#qrcode').html("").qrcode({
						text : toUtf8(data.qecode),
						width : "140",
						height : "140",
						render : "table",
						correctLevel:0
					});
				} else {
					$('#qrcode').html("").qrcode({
						text : toUtf8(data.qecode),
						width : "190",
						height : "190",
						correctLevel:0
					});
				}
				document.getElementById("bindgoogle-key").innerHTML = data.totpKey;
				document.getElementById("bindgoogle-key-hide").value = data.totpKey;
			}
		}, "json");
	},
	submitValidateEmailForm : function(email, flag) {
		var desc = '';
		if (email.indexOf(" ") > -1) {
			desc = language["comm.error.tips.11"];
		}
		if (email == '') {
			desc = language["comm.error.tips.7"];
		}
		if (!util.checkEmail(email)) {
			desc = language["comm.error.tips.16"];
		}
		if (desc != "") {
			util.showerrortips('bindemail-errortips', desc);
			return;
		} else {
			util.hideerrortips('bindemail-errortips');
		}
		var url = "/validate/postValidateMail.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			email : email
		}, function(data) {
			if (data.code == 0) {
				if (flag) {
					util.showerrortips('', data.msg, {
						okbtn : function() {
							$('#alertTips').modal('hide');
							location.reload(true);
						}
					});
				} else {
					util.showerrortips('bindemail-errortips', data.msg);
					window.setTimeout(function() {
						window.location.href = '/user/security.html';
					}, 1000);
				}
			} else {
				if (flag) {
					util.showerrortips('', data.msg);
				} else {
					util.showerrortips('bindemail-errortips', data.msg);
				}
			}
		}, "json");

		return;
	},
	areaCodeChange : function(ele, setEle) {
		var code = $(ele).val();
		$("#" + setEle).html("+" + code);
	},
	submitValidatePhoneForm : function(isUpdate) {
		var phone = document.getElementById("unbindphone-newphone").value;
		var oldcode = document.getElementById("unbindphone-msgcode").value;
		var newcode = document.getElementById("unbindphone-newmsgcode").value;
		var areaCode = document.getElementById("unbindphone-newphone-areacode").innerHTML;
		var imgcode = document.getElementById("unbindphone-imgcode").value;
		var totpCode = 0;
		var desc = '';
		if (phone.indexOf(" ") > -1) {
			desc = language["comm.error.tips.8"];
			util.showerrortips('unbindphone-errortips', desc);
			return false;
		} else {
			if (phone == '') {
				desc = language["comm.error.tips.6"];
				util.showerrortips('unbindphone-errortips', desc);
				return false;
			}
		}
		if (oldcode.indexOf(" ") > -1 || oldcode.length != 6 || !/^[0-9]{6}$/.test(oldcode)) {
			desc = language["comm.error.tips.124"];
			util.showerrortips('unbindphone-errortips', desc);
			return false;
		}
		if (newcode.indexOf(" ") > -1 || newcode.length != 6 || !/^[0-9]{6}$/.test(newcode)) {
			desc = language["comm.error.tips.124"];
			util.showerrortips('unbindphone-errortips', desc);
			return false;
		}
		if (document.getElementById("unbindphone-googlecode") != null) {
			totpCode = document.getElementById("unbindphone-googlecode").value;
			if (totpCode.indexOf(" ") > -1 || totpCode.length != 6 || !/^[0-9]{6}$/.test(totpCode)) {
				desc = language["comm.error.tips.98"];
				util.showerrortips('unbindphone-errortips', desc);
				return false;
			}
		}
		if (!util.checkMobile(phone)) {
			util.showerrortips('unbindphone-errortips', language["comm.error.tips.10"]);
			return;
		}
		util.hideerrortips('unbindphone-errortips');
		var url = "/user/validatePhone.html?random=" + Math.round(Math.random() * 100);
		var param = {
			isUpdate:isUpdate,
			phone : phone,
			oldcode : oldcode,
			newcode : newcode,
			totpCode : totpCode,
			areaCode : areaCode,
			imgcode : imgcode
		};
		jQuery.post(url, param, function(result) {
			util.showerrortips('unbindphone-errortips', result.msg);
			if (result.code == 0) {
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			}
		}, "json");
	},
	submitBindPhone : function(isUpdate) {
		var phone = document.getElementById("bindphone-newphone").value;
		var newcode = document.getElementById("bindphone-msgcode").value;
		var areaCode = document.getElementById("bindphone-newphone-areacode").innerHTML;
		var imgcode = document.getElementById("bindphone-imgcode").value;
		var totpCode = 0;
		var oldcode = 0;
		var desc = '';
		if (phone.indexOf(" ") > -1) {
			desc = language["comm.error.tips.8"];
			util.showerrortips('bindphone-errortips', desc);
			return false;
		} else {
			if (phone == '') {
				desc = language["comm.error.tips.6"];
				util.showerrortips('bindphone-errortips', desc);
				return false;
			}
		}
		if (newcode.indexOf(" ") > -1 || newcode.length != 6 || !/^[0-9]{6}$/.test(newcode)) {
			desc = language["comm.error.tips.124"];
			util.showerrortips('bindphone-errortips', desc);
			return false;
		}
		if (document.getElementById("bindphone-googlecode") != null) {
			totpCode = document.getElementById("bindphone-googlecode").value;
			if (totpCode.indexOf(" ") > -1 || totpCode.length != 6 || !/^[0-9]{6}$/.test(totpCode)) {
				desc = language["comm.error.tips.98"];
				util.showerrortips('bindphone-errortips', desc);
				return false;
			}
		}
		if (!util.checkMobile(phone)) {
			util.showerrortips('bindphone-errortips', language["comm.error.tips.10"]);
			return;
		}
		util.hideerrortips('bindphone-errortips');
		var url = "/user/validatePhone.html?random=" + Math.round(Math.random() * 100);
		var param = {
			isUpdate:isUpdate,
			phone : phone,
			oldcode : oldcode,
			newcode : newcode,
			totpCode : totpCode,
			areaCode : areaCode,
			imgcode : imgcode
		};
		jQuery.post(url, param, function(result) {
			util.showerrortips('bindphone-errortips', result.msg);
			if (result.code == 0) {
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			}
		}, "json");
	},
	submitbindGoogleForm : function() {
		var code = document.getElementById("bindgoogle-topcode").value;
		var totpKey = document.getElementById("bindgoogle-key-hide").value;
		var phoneCode = 0;
		var desc = '';
		if (!/^[0-9]{6}$/.test(code)) {
			desc = language["comm.error.tips.98"];
		}
		if (desc != "") {
			util.showerrortips('bindgoogle-errortips', desc);
			return;
		} else {
			util.hideerrortips('bindgoogle-errortips', false);
		}
		var url = "/user/validateAuthenticator.html?random=" + Math.round(Math.random() * 100);
		var param = {
			code : code,
			totpKey : totpKey
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				util.showerrortips('bindgoogle-errortips', data.msg);
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			} else {
				util.showerrortips('bindgoogle-errortips', data.msg);
				document.getElementById("bindgoogle-topcode").value = "";
			}
		}, "json");
	},
	submitlookbindGoogleForm : function() {
		var totpCode = 0;
		var desc = '';
		totpCode = document.getElementById("unbindgoogle-topcode").value;
		if (!/^[0-9]{6}$/.test(totpCode)) {
			desc = language["comm.error.tips.98"];
		}
		if (desc != "") {
			util.showerrortips('unbindgoogle-errortips', desc);
			return;
		} else {
			util.hideerrortips('unbindgoogle-errortips');
		}
		var url = "/user/getGoogleTotpKey.html?random=" + Math.round(Math.random() * 100);
		var param = {
			totpCode : totpCode
		};
		jQuery.post(url, param, function(data) {
			if (data.code == -1) {
				util.showerrortips('unbindgoogle-errortips', data.msg);
				document.getElementById("unbindgoogle-topcode").value = "";
			} else if (data.code == 0) {

				if (data.code == 0) {
					if (navigator.userAgent.indexOf("MSIE") > 0) {
						jQuery('#unqrcode').qrcode({
							text : toUtf8(data.qecode),
							width : "200",
							height : "200",
							render : "table",
							correctLevel:0
						});
					} else {
						jQuery('#unqrcode').qrcode({
							text : toUtf8(data.qecode),
							width : "200",
							height : "200",
							correctLevel:0
						});
					}
					$("#unbindgoogle-key").html(data.totpKey);
					$(".unbindgoogle-hide-box").show();
					$(".unbindgoogle-show-box").hide();
					centerModals();
				}
			}
		}, "json");
	},
	submitWithdrawCnyAddress : function(type) {
			var payeeAddr = document.getElementById("payeeAddr").value;
			var openBankTypeAddr = $("#openBankTypeAddr").attr('data-id');
			var withdrawAccount = util.trim(document.getElementById("withdrawAccountAddr").value);
			var address = util.trim(document.getElementById("address").value);
			var prov = util.trim(document.getElementById("prov").value);
			var city = util.trim(document.getElementById("city").value);
			var dist = util.trim(document.getElementById("dist").value);
			var totpCode = 0;
			var phoneCode = 0;
			var phone = util.trim(document.getElementById("dialogPhone").value);


			if (payeeAddr == "" || payeeAddr == language["withdraw.error.tips.5"] || payeeAddr == language["withdraw.error.tips.6"]) {
				util.showerrortips("binderrortips", language["comm.error.tips.129"]);
				return;
			}
			if (openBankTypeAddr == -1) {
				util.showerrortips("binderrortips", language["comm.error.tips.70"]);
				return;
			}
			var reg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
			if(!reg.test(withdrawAccount)){
				//银行卡号不合法
				util.showerrortips("binderrortips", language["comm.error.tips.134"]);
				return;
			}
			if (withdrawAccount == "" || withdrawAccount.length > 200 || withdrawAccount == language["comm.error.tips.62"]) {
				util.showerrortips("binderrortips", language["comm.error.tips.71"]);
				return;
			}
			// var withdrawAccount2 = util.trim(document.getElementById("withdrawAccountAddr2").value);
			// if (withdrawAccount != withdrawAccount2) {
			// 	util.showerrortips("binderrortips", language["comm.error.tips.72"]);
			// 	return;
			// }
			if ((prov == "" || prov == language["withdraw.error.tips.7"]) || (city == "" || city == language["withdraw.error.tips.7"]) || address == "") {
				util.showerrortips("binderrortips", language["comm.error.tips.73"]);
				return;
			}
			if (address.length > 300) {
				util.showerrortips("binderrortips", language["comm.error.tips.73"]);
				return;
			}

			if (phone) {
				if (!util.checkMobile(phone)) {
					util.showerrortips("binderrortips", language["comm.error.tips.148"]);
					document.getElementById("phone").value = "";
					return;
				}
			}

			if (document.getElementById("addressTotpCode") != null) {
				totpCode = util.trim(document.getElementById("addressTotpCode").value);
				if (!/^[0-9]{6}$/.test(totpCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.65"]);
					document.getElementById("addressTotpCode").value = "";
					return;
				}
			}

			if (document.getElementById("addressPhoneCode") != null) {
				phoneCode = util.trim(document.getElementById("addressPhoneCode").value);
				if (!/^[0-9]{6}$/.test(phoneCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.66"]);
					document.getElementById("addressPhoneCode").value = "";
					return;
				}
			}

			$('#withdrawCnyAddrBtn').attr('disabled', true);

			util.hideerrortips("binderrortips");
			var url = "/user/updateOutAddress.html?random=" + Math.round(Math.random() * 100);
			jQuery.post(url, {
				account : withdrawAccount,
				openBankType : openBankTypeAddr,
				totpCode : totpCode,
				phoneCode : phoneCode,
				address : address,
				prov : prov,
				city : city,
				dist : dist,
				payeeAddr : payeeAddr,
				phone : phone
			}, function(result) {
				if (result != null) {
					if (result.code == 0) {
						
						if (isreg == 1) {
							userInfo.phone = phone;
							userInfo.account = withdrawAccount;
							
							$('.accountInfo-phone').text(phone);
							$('.accountInfo-account').text(withdrawAccount);
							$('#addBankDialog').modal('hide');

							setTimeout("$('#finishDialog').modal('show')",500);

							return false;
						}

						$('#addBankDialog').modal('hide');
						$('#bindbank-success').modal('show');
					} else {
						util.showerrortips("binderrortips", result.msg);
					}
				}
				$('#withdrawCnyAddrBtn').attr('disabled', false);

			}, "json");
		},
	submitPwdForm : function(pwdType, istrade) {
		var originPwdEle = "";
		var newPwdEle = "";
		var reNewPwdEle = "";
		var phoneCodeEle = "";
		var totpCodeEle = "";
		var errorEle = "";
		if (pwdType == 0) {
			originPwdEle = "unbindloginpass-oldpass";
			newPwdEle = "unbindloginpass-newpass";
			reNewPwdEle = "unbindloginpass-confirmpass";
			phoneCodeEle = "unbindloginpass-msgcode";
			totpCodeEle = "unbindloginpass-googlecode";
			errorEle = "unbindloginpass-errortips";
		} else {
			if (istrade) {
				originPwdEle = "unbindtradepass-oldpass";
				newPwdEle = "unbindtradepass-newpass";
				reNewPwdEle = "unbindtradepass-confirmpass";
				phoneCodeEle = "unbindtradepass-msgcode";
				totpCodeEle = "unbindtradepass-googlecode";
				errorEle = "unbindtradepass-errortips";

			} else {
				originPwdEle = "bindtradepass-oldpass";
				newPwdEle = "bindtradepass-newpass";
				reNewPwdEle = "bindtradepass-confirmpass";
				phoneCodeEle = "bindtradepass-msgcode";
				totpCodeEle = "bindtradepass-googlecode";
				errorEle = "bindtradepass-errortips";
			}
		}
		if (istrade) {
			var originPwd = util.trim(document.getElementById(originPwdEle).value);
		}
		var newPwd = util.trim(document.getElementById(newPwdEle).value);
		var reNewPwd = util.trim(document.getElementById(reNewPwdEle).value);
		if (istrade) {
			var originPwdTips = util.isOriginPassword(originPwd);
		}
		var newPwdTips = util.isPassword(newPwd);
		var reNewPwdTips = util.isPassword(reNewPwd);
		var originTip = true;
		if (istrade && originPwdTips != "") {
			util.showerrortips(errorEle, originPwdTips);
			return;
		} else {
			util.hideerrortips(errorEle);
		}
		if (newPwdTips != "") {
			util.showerrortips(errorEle, newPwdTips);
			return;
		} else {
			util.hideerrortips(errorEle);
		}
		if (reNewPwdTips != "") {
			util.showerrortips(errorEle, reNewPwdTips);
			return;
		} else if (newPwd != reNewPwd) {
			util.showerrortips(errorEle, language["comm.error.tips.109"]);
			document.getElementById(reNewPwdEle).value = "";
			return;
		} else {
			util.hideerrortips(errorEle);
		}
		var phoneCode = "";
		var totpCode = "";
		if (document.getElementById(phoneCodeEle) != null) {
			phoneCode = util.trim(document.getElementById(phoneCodeEle).value);
			if (phoneCode == "") {
				util.showerrortips(errorEle, language["comm.error.tips.60"]);
				return;
			}
			if (!/^[0-9]{6}$/.test(phoneCode)) {
				util.showerrortips(errorEle, language["comm.error.tips.124"]);
				return;
			} else {
				util.hideerrortips(errorEle);
			}
		}
		if (document.getElementById(totpCodeEle) != null) {
			totpCode = util.trim(document.getElementById(totpCodeEle).value);
			if (!/^[0-9]{6}$/.test(totpCode)) {
				util.showerrortips(errorEle, language["comm.error.tips.98"]);
				return;
			} else {
				util.hideerrortips(errorEle);
			}
		}
		if (document.getElementById(phoneCodeEle) == null && document.getElementById(totpCodeEle) == null) {
			util.showerrortips(errorEle, language["comm.error.tips.110"]);
			return;
		}
		var url = "/user/modifyPwd.html?random=" + Math.round(Math.random() * 100);
		var param = {
			pwdType : pwdType,
			originPwd : originPwd,
			newPwd : newPwd,
			reNewPwd : reNewPwd,
			phoneCode : phoneCode,
			totpCode : totpCode
		};
		jQuery.post(url, param, function(data) {
			if (data.code == 0) {
				if (istrade) {
					//"修改成功，请牢记您的" + (pwdType == 0 ? '登录' : '交易') + "密码！"
					var type = (pwdType == 0 ? language["comm.error.tips.142"] :language["comm.error.tips.143"]);
					util.showerrortips(errorEle, language["comm.error.tips.141"].format(type));
				} else {					
					util.showerrortips(errorEle, language["comm.error.tips.140"]);
				}
				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
			} else if (data.code == -3) {
				util.showerrortips(errorEle, data.msg);
				document.getElementById(reNewPwdEle).value = "";
			} else if (data.code == -5) {
				util.showerrortips(errorEle, data.msg);
				document.getElementById(originPwdEle).value = "";
			} else if (data.code == -6) {
				util.showerrortips(errorEle, data.msg);
				document.getElementById(totpCodeEle).value = "";
			} else if (data.code == -7) {
				util.showerrortips(errorEle, data.msg);
				document.getElementById(phoneCodeEle).value = "";
			} else if (data.code == -10) {
				util.showerrortips(errorEle, data.msg);
			} else if (data.code == -13) {
				util.showerrortips(errorEle, data.msg);
			} else if (data.code == -20) {
				util.showerrortips(errorEle, data.msg);
			}
		}, "json");
	},
	findPassword : function () {
	
		var phone = $('#retrievePassword-userPhone').val();
		var megCode = util.trim($('#retrievePassword-msgcode').val());
		var idcard = $('#retrievePassword-id').val();
		var imgCode = $('#retrievePassword-imgpwd').val();
		var newPass = $('#retrievePassword-newpassword').val();

		if(!/^\d{6}$/.test(megCode)) {
			util.showerrortips("retrievePassword-errortips", language["comm.error.tips.66"]);
			$('#retrievePassword-msgcode').val('');

			return false;
		}

		if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idcard)) {
			util.showerrortips("retrievePassword-errortips", language["comm.error.tips.119"]);
			$('#retrievePassword-id').val('');

			return false;
		}

		var newPassTips = util.isPassword(newPass);

		if(newPassTips != '') {
			util.showerrortips("retrievePassword-errortips", newPassTips);
			$('#retrievePassword-newpassword').val('');

			return false;
		}

		var param = {
			phone : phone,
			msgcode : megCode,
			idcardno : idcard,
			imgCode : imgCode,
			tradepwd : newPass,
		};
        var url ="/user/resetTradePassword.html?random=" + Math.round(Math.random() * 100);
		$.post(url, param, function(data, textStatus, xhr) {

			if (data != null) {
				if (data.code == 0) {
					util.showerrortips('retrievePassword-errortips', language["update_success_desc"]);
					window.location.reload();
				} else {
					util.showerrortips('retrievePassword-errortips', data.msg);
				}
			}
		},'json');

	},
	submitUnbindBank: function() {
		var bankId = $('#unbindbankid').val();
		var url = "/user/deteleBankInfoWithdraw.html?random=" + Math.round(Math.random() * 100);
		var code = 0;
		var errorEle = 'unbindbank-errortips';

		if (document.getElementById("unbindBank-msgcode").value != null) {
			code = util.trim(document.getElementById("unbindBank-msgcode").value);
			if (!/^[0-9]{6}$/.test(code)) {
				util.showerrortips("binderrortips", language["comm.error.tips.66"]);
				document.getElementById("unbindBank-msgcode").value = "";
				return;
			}
		}

		var param = {
			uid: bankId,
			code : code
		};

		$.post(url, param, function(data, textStatus, xhr) {

			if (data != null) {
				if (data.code == 0) {
					
					$('#unbindbank').modal('hide');
					$('#unbindbank-success').modal('show');
				} else {

					util.showerrortips(errorEle, data.msg);
				}
			}
			
		},'json');
	},
	questionSave: function () {

		var answer1 = $('#answer1').val();
		var answer2 = $('#answer2').val();
		var answer3 = $('#answer3').val();
		var question1 = $('#question1').val();
		var question2 = $('#question2').val();
		var question3 = $('#question3').val();

		if (!question1 && !question2 && !question3) {
			util.showerrortips("questionSave-errortips", '至少设置一个问题');
			return false;
		}

		var list = [];
			
		if (question1 && answer1) {
			list.push({
			"fquestion": question1,
			"fanswer": answer1
			});
		}
		if (question2 && answer2) {
			list.push({
			"fquestion": question2,
			"fanswer": answer2
			});
		}
		if (question3 && answer3) {
			list.push({
			"fquestion": question3,
			"fanswer": answer3
			});
		}


		
		$.post('/user/savequest.html', {jsonstr:JSON.stringify(list)}, function(data, textStatus, xhr) {
			
			if (data.code == 0) {


				window.setTimeout(function() {
					window.location.href = '/user/security.html';
				}, 1000);
				util.showerrortips("questionSave-errortips", data.msg);
			} else {

				util.showerrortips("questionSave-errortips", data.msg);
			}
		},'json');
	},
	questionUpdate: function () {

		var answer1 = $('#answer1').val();
		var answer2 = $('#answer2').val();
		var answer3 = $('#answer3').val();
		var question1 = $('#question1').val();
		var question2 = $('#question2').val();
		var question3 = $('#question3').val();

		if (!question1 && !question2 && !question3) {
			util.showerrortips("questionSave-errortips", '至少设置一个问题');
			return false;
		}

		var list = [];
			
		if (question1 && answer1) {
			list.push({
			"fquestion": question1,
			"fanswer": answer1,
			"fid":　$('#question1').attr('data-id')
			});
		}
		if (question2 && answer2) {
			list.push({
			"fquestion": question2,
			"fanswer": answer2,
			"fid":　$('#question2').attr('data-id')
			});
		}
		if (question3 && answer3) {
			list.push({
			"fquestion": question3,
			"fanswer": answer3,
			"fid":　$('#question3').attr('data-id')
			});
		}

		
		$.post('/user/updatequest.html', {jsonstr:JSON.stringify(list)}, function(data, textStatus, xhr) {
			if (data.code == 0) {

				util.showerrortips("questionSave-errortips", data.msg);

				window.location.reload();
			} else {
				
				util.showerrortips("questionSave-errortips", data.msg);
			}
		},'json');
	},
	getRandomQuestion: function () {
		
		$.post('/user/onequest.html',{}, function(data) {
			
			if(data.code == 0 ) {
				$('#randomQuestion').val(data.question).attr('data-id',data.fid);
				$('#randomQuestionAns').val('');
				$('#questionreComfime').modal('show');
			} else {
				util.layerAlert("", data.msg, 2);
			}
		},'json');
	},
	checkQuestionVal: function (targetDialogId) {

		if (!$('#randomQuestionAns').val()) {

			util.showerrortips("randomQuestion-errortips", '答案不能为空');

			return false;
		}
		
		var params = {
			fid: $('#randomQuestion').attr('data-id'),
			user_answer: $('#randomQuestionAns').val()
		}

		$.post('/user/checkQuestionVal.html', params, function(data) {

			if (data.code == 0) {

				$('#questionreComfime').modal('hide');

				window.setTimeout(function() {
					$('#'+ targetDialogId).modal('show');
				}, 1000);
			} 
			util.showerrortips("randomQuestion-errortips", data.msg);
		});
	},
	setUserQuestion: function () {
		$.post('/user/questlist.html', {}, function(data, textStatus, xhr) {


			if(data.list && data.list.length > 0) {

				$('#question1').attr('data-id',data.list[0].fid);

				$.each($('#question1 option'), function(index, val) {
					
					if ($(val).val() == data.list[0].fquestion) {
						$(val).attr('selected',true);
						$('#answer1').val(data.list[0].fanswer)
					}
				});

				if(data.list.length < 2) {
					return false;
				}
				
				$('#question2').attr('data-id',data.list[1].fid);
				$.each($('#question2 option'), function(index, val) {
					
					if ($(val).val() == data.list[1].fquestion) {
						$(val).attr('selected',true);
						$('#answer2').val(data.list[1].fanswer)
					}
				});

				if(data.list.length < 3) {
					return false;
				}
				$('#question3').attr('data-id',data.list[2].fid);
				$.each($('#question3 option'), function(index, val) {
					
					if ($(val).val() == data.list[2].fquestion) {
						$(val).attr('selected',true);
						$('#answer3').val(data.list[2].fanswer)
					}
				});
			}
			
			
		});
	}
};
$(function() {
	$(".btn-imgcode").on("click", function() {
		this.src = "/servlet/ValidateImageServlet?r=" + Math.round(Math.random() * 100);
	});
	$('#bindgoogle').on("show.bs.modal", function() {
		security.loadgoogleAuth();
	});
	$("#bindemail-Btn").on("click", function() {
		var email = $("#bindemail-email").val();
		security.submitValidateEmailForm(email, false);
	});
	$("#bindemail-send").on("click", function() {
		var email = $("#bindemail-send-email").val();
		security.submitValidateEmailForm(email, true);
	});
	$("#unbindphone-areaCode").on("change", function() {
		security.areaCodeChange(this, "unbindphone-newphone-areacode");
	});
	$(".btn-sendmsg").on("click", function() {
		if (this.id == "unbindphone-newsendmessage") {
			var areacode = $("#unbindphone-newphone-areacode").html();
			var phone = $("#unbindphone-newphone").val();
			if (phone == "") {
				util.showerrortips("unbindphone-errortips", language["comm.error.tips.6"]);
				return;
			}
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);
		} else if(this.id == "unbindbank-megbtn") {
			var areacode = $("#bindphone-newphone-areacode").html();
			var phone = $("#unbindBank-phone").val();
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

		} else if (this.id == "bindphone-sendmessage") {
			var areacode = $("#bindphone-newphone-areacode").html();
			var phone = $("#bindphone-newphone").val();
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

		} else if (this.id == "bindsendmessage") {
			var areacode = '86';
			var phone = $("#dialogPhone").val();
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);

		} else if(this.id == "retrievePassword-sendmessage") {
			var imgcode = $('#retrievePassword-imgpwd').val();
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone,imgcode);
		} else {
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
		}
	});
	$("#unbindphone-Btn").on("click", function() {
		security.submitValidatePhoneForm(1);
	});
	$("#bindphone-areaCode").on("change", function() {
		security.areaCodeChange(this, "bindphone-newphone-areacode");
	});
	$("#bindphone-Btn").on("click", function() {
		security.submitBindPhone(0);
	});
	$("#bindgoogle-Btn").on("click", function() {
		security.submitbindGoogleForm();
	});
	$("#unbindgoogle-Btn").on("click", function() {
		security.submitlookbindGoogleForm();
	});
	$("#unbindloginpass-Btn").on("click", function() {
		security.submitPwdForm(0, true);
	});
	$("#unbindtradepass-Btn").on("click", function() {
		security.submitPwdForm(1, true);
	});
	$("#bindtradepass-Btn").on("click", function() {
		security.submitPwdForm(1, false);
	});
	$("#retrievePassword-Btn").on("click", function() {
		security.findPassword();
	});


	//查看银行卡
	$('.usershowbanklist').on('click', function() {
		
		$('.con-items').hide();
		$('.banksWrap').show();
	});
	
	//解绑弹窗
	$('.unbindbank-btn').on('click', function() {
		var $this = $(this);
		var bankid = $this.attr('data-id');
		var index = $this.parents('.bank-item').index();

		$('#unbindbankid').val(bankid);
		$('.formbanks .bank-item').hide().eq(index).show();
	});

	//解绑
	$('#confirm-unbindbank').on('click', function() {
		
		security.submitUnbindBank();
	});

	$('#openBankTypeAddr').autocomplete({
	    lookup: BANKLIST,
	    autoSelectFirst: true,
	    minChars:0,
	    onSelect: function (suggestion) {
	        $("#openBankTypeAddr").attr('data-id',suggestion.data);
	    }
	});

	//确认解绑成功 刷新
	$('#unbindbank-success-btn').on('click', function() {
		
		window.location.reload(true);
	});
	//确认绑定卡成功 刷新
	$('#bindbank-success-btn').on('click', function() {
		
		window.location.reload(true);
	});

	//切换
	$('.dialog-success-switch--next').on('click', function() {
		
		$('.dialog-success-item').eq(0).addClass('active');
	});
	$('.dialog-success-switch--prev').on('click', function() {
		
		$('.dialog-success-item').eq(0).removeClass('active');
	});

	if(isreg == 1) {
		$('#reg-success').modal('show');

		$('#finishDialog .close,#addBankDialog .close,#bindrealinfo .close').on('click', function() {
			
			window.location.href='/user/security.html';
		});
	}
	
	$('#withdrawCnyAddrBtn').on('click', function() {

		//$('#addBankDialog').modal('hide');
		//$('#finishDialog').modal('show');
		
		security.submitWithdrawCnyAddress();
	});
	

	//实名认证
	$("#bindrealinfo-Btn").on("click", function() {

		security.submitRealCertificationForm(false);
	});


	//next stage
	$('#go-bindrealinfo').on('click', function() {
		
		$('#reg-success').modal('hide');
		setTimeout("$('#bindrealinfo').modal('show')",500)
	});

	$('.finishDialog-passwor').on('click', function(event) {
		
		$('#finishDialog').modal('hide');
		
		setTimeout("$('#bindtradepass').modal('show')",500)
	});

	$('.finishDialog-password').on('click', function() {
		
		$('#finishDialog').modal('hide');
		
		setTimeout("$('#bindemail').modal('show')",500)
	});

	$('.goValidate').on('click', function() {
		
		$('#bindrealinfo').modal('show');
	});
	
	//密码切换
	$('.pwdSwitch').on('click', function() {
		var $this = $(this);
		if($this.hasClass('show')) {
			$('#retrievePassword-newpassword').attr('type', 'password');
			$this.removeClass('show');
		}else {
			$('#retrievePassword-newpassword').attr('type', 'text');
			$this.addClass('show');
		}
	});

	$('.findPassBtn').on('click', function () {
		$('#unbindtradepass').modal('hide');

		if ($('#isQuestionValidate').val() == 'true') {
			security.getRandomQuestion();
			$('#randomQuestionAns').val('');
			setTimeout("$('#questionreComfime').modal('show')", 500);
			//安全问题验证
			$('.questionComfime-btn').unbind();
			$('.questionComfime-btn').click(function () {

				security.checkQuestionVal('retrievePassword');
			});
		} else {
			setTimeout("$('#retrievePassword').modal('show')", 500);
		}
	});

	//安全问题设置
	$('.submit-question').on('click', function() {
		
		security.questionSave();
	});

	//开始安全问题验证
	$('.startConfime-btn').on('click', function () {

		security.getRandomQuestion();

		//安全问题验证
		$('.questionComfime-btn').unbind();
		$('.questionComfime-btn').click(function () {

			security.checkQuestionVal('questionUpdate');
		});
	});



	//修改
	$('.submit-updataQue').on('click', function() {
		
		security.questionUpdate();
	});
	
	security.setUserQuestion();
});