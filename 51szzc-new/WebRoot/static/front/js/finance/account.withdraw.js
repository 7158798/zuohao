var withdraw = {
	btc : {
		submitWithdrawBtcAddrForm : function() {
			var coinName = document.getElementById("coinName").value;
			var withdrawAddr = util.trim(document.getElementById("withdrawBtcAddr").value);
			var withdrawRemark = util.trim(document.getElementById("withdrawBtcRemark").value);
			var withdrawBtcPass = util.trim(document.getElementById("withdrawBtcPass").value);
			var withdrawBtcAddrTotpCode = 0;
			var withdrawBtcAddrPhoneCode = 0;
			var symbol = document.getElementById("symbol").value;
			if (withdrawAddr == "") {
				util.showerrortips("binderrortips", language["comm.error.tips.63"]);
				return;
			} else {
				util.hideerrortips("binderrortips");
			}
			var start = withdrawAddr.substring(0, 1);
			/*if (withdrawAddr.length != 35 && withdrawAddr.length != 34 && withdrawAddr.length != 42) {
				util.showerrortips("binderrortips", language["comm.error.tips.64"].format(coinName));
				return;
			}*/
			if (withdrawAddr.length < 25 || withdrawAddr.length > 50) {
				util.showerrortips("binderrortips", language["comm.error.tips.64"].format(coinName));
				return;
			}

			if (document.getElementById("withdrawBtcAddrTotpCode") != null) {
				withdrawBtcAddrTotpCode = util.trim(document.getElementById("withdrawBtcAddrTotpCode").value);
				if (!/^[0-9]{6}$/.test(withdrawBtcAddrTotpCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.65"]);
					document.getElementById("withdrawBtcAddrTotpCode").value = "";
					return;
				} else {
					util.hideerrortips("binderrortips");
				}
			}
			if (document.getElementById("withdrawBtcAddrPhoneCode") != null) {
				withdrawBtcAddrPhoneCode = util.trim(document.getElementById("withdrawBtcAddrPhoneCode").value);
				if (!/^[0-9]{6}$/.test(withdrawBtcAddrPhoneCode)) {
					util.showerrortips("binderrortips", language["comm.error.tips.66"]);
					document.getElementById("withdrawBtcAddrPhoneCode").value = "";
					return;
				} else {
					util.hideerrortips("binderrortips");
				}
			}
			var url = "/user/modifyWithdrawBtcAddr.html?random=" + Math.round(Math.random() * 100);
			var param = {
				withdrawAddr : withdrawAddr,
				totpCode : withdrawBtcAddrTotpCode,
				phoneCode : withdrawBtcAddrPhoneCode,
				symbol : symbol,
				withdrawBtcPass : withdrawBtcPass,
				withdrawRemark : withdrawRemark
			};
			$.post(url, param, function(result) {
				if (result != null) {
					if (result.code == -1) {
						util.showerrortips("", language["comm.error.tips.34"], {
							okbtn : function() {
								window.location.href = '/user/security.html#traingtr';
							},
							noshow : true
						});
					} else if (result.code == -4) {
						util.showerrortips("binderrortips", result.msg);
					} else if (result.code == -2) {
						util.showerrortips("binderrortips", result.msg);
						document.getElementById("withdrawBtcAddrTotpCode").value = "";
					} else if (result.code == -3) {
						util.showerrortips("binderrortips", result.msg);
						document.getElementById("withdrawBtcAddrPhoneCode").value = "";
					} else if (result.code == 0) {
						window.location.reload(true);
					}
				}
			}, "json");
		},
		submitWithdrawBtcForm : function() {
			var coinName = document.getElementById("coinName").value;
			var withdrawAddr = util.trim(document.getElementById("withdrawAddr").value);
			var withdrawAmount = util.trim(document.getElementById("withdrawAmount").value);
			var tradePwd = util.trim(document.getElementById("tradePwd").value);
			var max_double = util.trim(document.getElementById("max_double").value);
			var min_double = util.trim(document.getElementById("min_double").value);
			var btcfee = document.getElementById("btcfee").value;
			var totpCode = 0;
			var phoneCode = 0;
			var symbol = document.getElementById("symbol").value;
			if (document.getElementById("btcbalance") != null && document.getElementById("btcbalance").value == 0) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.54"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (withdrawAddr == "") {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.55"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
			if (!reg.test(withdrawAmount)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.56"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (parseFloat(withdrawAmount) < parseFloat(min_double)) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.1"].format(min_double,coinName));
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (parseFloat(withdrawAmount) > parseFloat(max_double)) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.2"].format(max_double,coinName));
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (tradePwd == "") {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.58"]);
				return;
			} else {
				util.hideerrortips("withdrawerrortips");
			}
			if (document.getElementById("withdrawTotpCode") != null) {
				totpCode = util.trim(document.getElementById("withdrawTotpCode").value);
				if (!/^[0-9]{6}$/.test(totpCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.59"]);
					return;
				} else {
					util.hideerrortips("withdrawerrortips");
				}
			}
			if (document.getElementById("withdrawPhoneCode") != null) {
				phoneCode = util.trim(document.getElementById("withdrawPhoneCode").value);
				if (!/^[0-9]{6}$/.test(phoneCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.60"]);
					return;
				} else {
					util.hideerrortips("withdrawerrortips");
				}
			}

			var url = "/account/withdrawBtcSubmit.html?random=" + Math.round(Math.random() * 100);
			var param = {
				withdrawAddr : withdrawAddr,
				withdrawAmount : withdrawAmount,
				tradePwd : tradePwd,
				totpCode : totpCode,
				phoneCode : phoneCode,
				symbol : symbol,
				level:btcfee
			};
			$.post(url, param, function(result) {
				if (result != null) {
					if (result.code == 0) {
						var isbindemail = $('#isbindemali').val();
						var type = result.type;   //绑定了，则判断type，区分是人工还是自动
						if(isbindemail == "true" && type == "email") {
							$("#email-show-span").html(result.msg);
							$('#sendEmailDialog').modal('show')
						} else {  //非绑定，则提示申请成功
							util.layerAlert("", language["withdraw.error.tips.4"], 1);
						}
					} else if(result.code == -1) {
						util.showerrortips("withdrawerrortips", result.msg);
					} else if(result.code == -2) {

						if(result.kyc_level == 1) {
							$('.kycTip-1').show();
						}
						if(result.kyc_level == 2) {
							$('.kycTip-2').show();
						}
						util.showerrortips("withdrawerrortips", result.msg);

					} else {
						util.showerrortips("withdrawerrortips", result.msg);
					}
				}
			});
		},
		cancelWithdrawBtc: function(id) {
	        util.layerConfirm(language["comm.error.tips.67"],
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/account/cancelWithdrawBtc.html?random=" + Math.round(100 * Math.random()),
	            param = {
	    			id : id
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        })
	    },
		sendAgain: function (id) {
			$.post("/account/email/resendwith.html", {fid : id}, function (data, textStatus, jqXHR) {

				if (data.code == 0) {
					$('#sendEmailDialog').modal('show')
				} else {
					util.layerAlert("", data.msg, 2);
				}
			}, "json");
		}
	},
	cny : {
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
						window.location.reload(true);
					} else {
						util.showerrortips("binderrortips", result.msg);
					}
				}
				$('#withdrawCnyAddrBtn').attr('disabled', false);
			}, "json");
		},
		submitWithdrawCnyForm : function(ele) {
			var withdrawBlank = $("#withdrawBlank").val();
			var withdrawBalance = util.trim(document.getElementById('withdrawBalance').value);
			var tradePwd = util.trim(document.getElementById("tradePwd").value);
			var totpCode = 0;
			var phoneCode = 0;
			var min = document.getElementById("min_double").value;
			var max = document.getElementById("max_double").value;
			var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
			if (withdrawBlank == "" || withdrawBlank ==0 || withdrawBlank == null) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.71"]);
				return;
			}
			if (!reg.test(withdrawBalance)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.74"]);
				return;
			}
			if (parseFloat(withdrawBalance) < parseFloat(min)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.77"].format(min));
				return;
			}
			if (parseFloat(withdrawBalance) > parseFloat(max)) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.78"].format(max));
				return;
			}
			if (tradePwd == "" || tradePwd.length > 200) {
				util.showerrortips("withdrawerrortips", language["comm.error.tips.79"]);
				return;
			}
			if (document.getElementById("withdrawTotpCode") != null) {
				totpCode = util.trim(document.getElementById("withdrawTotpCode").value);
				if (!/^[0-9]{6}$/.test(totpCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.80"]);
					return;
				}

			}
			if (document.getElementById("withdrawPhoneCode") != null) {
				phoneCode = util.trim(document.getElementById("withdrawPhoneCode").value);
				if (!/^[0-9]{6}$/.test(phoneCode)) {
					util.showerrortips("withdrawerrortips", language["comm.error.tips.81"]);
					return;
				}
			}
			if (document.getElementById("withdrawPhoneCode") == null) {
				util.showerrortips("withdrawerrortips", language["withdraw.error.tips.3"]);
				return;
			}
			ele.disabled = true;
			var url = "/account/withdrawCnySubmit.html?random=" + Math.round(Math.random() * 100);
			var param = {
				tradePwd : tradePwd,
				withdrawBalance : withdrawBalance,
				phoneCode : phoneCode,
				totpCode : totpCode,
				withdrawBlank : withdrawBlank
			};
			jQuery.post(url, param, function(result) {
				ele.disabled = false;
				if (result != null) {
					if (result.code == 0) {
						util.layerAlert("", language["withdraw.error.tips.4"], 1);
					} else if(result.code == -1) {
						util.showerrortips("withdrawerrortips", result.msg);
					} else if(result.code == -2) {

						if(result.kyc_level == 1) {
							$('.kycTip-1').show();
						}
						if(result.kyc_level == 2) {
							$('.kycTip-2').show();
						}

						util.showerrortips("withdrawerrortips", result.msg);
					} else {
						util.showerrortips("withdrawerrortips", result.msg);
					}
				}
			}, "json");
		},
		cancelWithdrawCny: function(outId) {
	        util.layerConfirm(language["comm.error.tips.67"],
	        function(result) {
	        	$('#confirmTips').modal('hide');
	            var url = "/account/cancelWithdrawcny.html?random=" + Math.round(100 * Math.random()),
	            param = {
	    			id : outId
	    		};
	            $.post(url, param,
	            function(id) {
	                null != id && (location.reload(true), layer.close(result))
	            })
	        })
	    },
		calculatefeesRate : function() {

			var amount = $("#withdrawBalance").val();
			var feesRate = $("#feesRate").val();
			if (amount == "") {
				amount = 0;
			}
			var feeamt = util.moneyformat(util.accMul(amount, feesRate), 2);

			//人民币提现最低手续费开关(0关1开)
			var cnyfeeLimit = $("#cnyfeeLimit").val() || 0;

			if(cnyfeeLimit == 1) {
                if(parseFloat(feeamt) < 5 && amount > 0){
					var min_5_yuan = language["min_5_yuan"];
                    if(amount > 5){
                        $("#free").html('5.00');
                        $("#amount").html(util.moneyformat(parseFloat(amount) - parseFloat(5.00), 2));
                        $("#lessAmount").html('('+min_5_yuan+')');
                    }else {

                        $("#free").html('5.00');
                        $("#amount").html(util.moneyformat(0.0, 2));
                        $("#lessAmount").html('('+min_5_yuan+')');
                    }

                }else {
                    $("#free").html(feeamt);
                    $("#amount").html(util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 2));
                    $("#lessAmount").html('');
                }
			}else{
                $("#free").html(feeamt);
                $("#amount").html(util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 2));
                $("#lessAmount").html('');
			}

		}
	}
};
$(function() {
	$(".btn-sendmsg").on("click", function() {
		if(this.id == "bindsendmessage") {
			var areacode = '86';
			var phone = $("#dialogPhone").val();
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);
		} else {

			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
		}
	});
	$("#withdrawBtcAddrBtn").on("click", function() {
		withdraw.btc.submitWithdrawBtcAddrForm();
	});
	$("#withdrawBtcButton").on("click", function() {
		withdraw.btc.submitWithdrawBtcForm();
	});
	$("#withdrawAmount").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 4);
	});
	$(".cancelWithdrawBtc").on("click", function(event) {
		withdraw.btc.cancelWithdrawBtc($(this).data().fid);
	});

	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
	$("#withdrawCnyAddrBtn").on("click", function() {
		withdraw.cny.submitWithdrawCnyAddress();
	});
	$("#withdrawBalance").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 2);
	}).on("keyup", function() {
		withdraw.cny.calculatefeesRate();
	});
	$("#withdrawCnyButton").on("click", function(event) {
		withdraw.cny.submitWithdrawCnyForm(this);
	});
	$(".cancelWithdrawcny").on("click", function(event) {
		withdraw.cny.cancelWithdrawCny($(this).data().fid);
	});
	$("#withdrawAccountAddr2").bind("copy cut paste", function(e) {
		return false;
	});
	
	$('#openBankTypeAddr').autocomplete({
	    lookup: BANKLIST,
	    autoSelectFirst: true,
	    minChars:0,
	    onSelect: function (suggestion) {
	        $("#openBankTypeAddr").attr('data-id',suggestion.data);
	    }
	});

	// if($('#isbindemali').val() == 'false') {
	// 	$('#addEmailDialog').modal('show');
	// }
	$(document).on('click','.sendAgain-btn',function () {

		var id = $(this).attr('data-id');
		withdraw.btc.sendAgain(id);
	})
});