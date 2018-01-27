var recharge = {
	submitFinForm : function(that) {
		var sbank = $('#sbank').val();
		var type = document.getElementById("finType").value;
		var random = document.getElementById("random").value;
		var minMoney = document.getElementById("minRecharge").value;
		var money = 0;
		if (sbank == "" && type == 0) {
			util.showerrortips("", language["comm.error.tips.83"]);
			return;
		}
		money = document.getElementById("diyMoney").value;
		if (money.toString().indexOf(".") != -1) {
			money = money.toString().split(".")[0];
			money = money + (random.substring(1));
		} else {
			money = money + (random.substring(1));
		}
		
		if (Number(money) < Number(minMoney) || isNaN(money)) {
			util.showerrortips("", language["comm.error.tips.84"].format(minMoney));
			return;
		}
		var url = "/account/alipayManual.html?random=" + Math.round(Math.random() * 100);
		var param = {
			money : money,
			type : type,
			sbank : sbank
		};
		that.disabled = true;
		$.post(url, param, function(result) {
			that.disabled = false;
			if (result != null) {
				if (result.resultCode < 0) {
					util.showerrortips("rechargebtn-erorr", result.msg);
				} else {
					var tail_num = i18nMsg("tail_num");  //尾号
					$('.target-bank').text($('#sbank option:selected').attr('data-bank'));
					$('.target-card').text(tail_num + $('#sbank option:selected').attr('data-card'));

					if (type == 3) {// 支付宝
						
						$('.order-money').text(money);

						recharge.refTenbody();
					} else if (type == 0) {
						document.getElementById("fownerName").innerHTML = result.fownerName;
						document.getElementById("fbankNumber").innerHTML = result.fbankNumber;
						document.getElementById("fbankAddress").innerHTML = result.fbankAddress;
						document.getElementById("bankMoney").innerHTML = result.money;
						document.getElementById("bankInfo").innerHTML = result.tradeId;
						document.getElementById("desc").value = result.tradeId;
						document.getElementById("bankInfotips").innerHTML = result.tradeId;
						document.getElementById("rechage1").style.display = "none";
						document.getElementById("rechage2").style.display = "block";
						recharge.refTenbody();
					}
					$(".recharge-process span").removeClass("active");
					$("#rechargeprocess2").addClass("active");
				}
			}
		});
	},
	submitPaymentInformation : function() {
		var type = document.getElementById("finType").value;
		var des = language["comm.error.tips.93"];
		if (type == 0) {
			des = language["comm.error.tips.93"];
		} else if (type == 1) {
			des = language["comm.error.tips.94"];
		}
		document.getElementById("rechage2").style.display = "none";
		document.getElementById("rechage4").style.display = "block";
		$(".recharge-process span").removeClass("active");
		$("#rechargeprocess4").addClass("active");
		var desc = document.getElementById("desc").value;
		var url = "/account/rechargeCnySubmit.html?random=" + Math.round(Math.random() * 100);
		var param = {
			bank : '无',
			account : '无',
			payee : '无',
			phone : '无',
			type : 0,
			desc : desc

		};
		$.post(url, param, function(result) {
			if (result != null) {
				if (result.code < 0) {
					util.showerrortips("", result.msg);
				} else if (result.code == 0) {
					document.getElementById("rechage3").style.display = "none";
					document.getElementById("rechage4").style.display = "block";
					$(".recharge-process span").removeClass("active");
					$("#rechargeprocess4").addClass("active");
					recharge.refTenbody();
				}
			}
		});
	},
	submitTransferAccounts : function() {

		var bank = $("#fromBank").val();
		var account = document.getElementById("fromAccount").value;
		var payee = document.getElementById("fromPayee").value;
		var phone = document.getElementById("fromPhone").value;
		var type = document.getElementById("finType").value;
		var desc = document.getElementById("desc").value;
		if (bank == "" || bank == "-1" || account == "" || payee == "" || phone == "") {
			util.showerrortips("", language["comm.error.tips.95"]);
			return;
		}
		var reg = /^(\d{16}|\d{17}|\d{18}|\d{19})$/;
		if(!reg.test(account)){
			//银行卡号不合法
			util.showerrortips("",language["comm.error.tips.134"]);
			return;
		}
		var url = "/account/rechargeCnySubmit.html?random=" + Math.round(Math.random() * 100);
		var param = {
			bank : bank,
			account : account,
			payee : payee,
			phone : phone,
			type : type,
			desc : desc

		};
		$.post(url, param, function(result) {
			if (result != null) {
				if (result.code < 0) {
					util.showerrortips("", result.msg);
				} else if (result.code == 0) {
					document.getElementById("rechage3").style.display = "none";
					document.getElementById("rechage4").style.display = "block";
					$(".recharge-process span").removeClass("active");
					$("#rechargeprocess4").addClass("active");
					recharge.refTenbody();
				}
			}
		});
	},
	refTenbody : function() {
		var currentPage = $('#ccurrentPage').val();
		var type = document.getElementById("finType").value;
		var url = "/account/refTenbody.html?currentPage=1&type=" + type + "&random=" + Math.round(Math.random() * 100);
		$("#recordbody0").load(url, null, function(data) {
			$(".completioninfo").on("click", function() {
				var fid = $(this).data().fid;
				recharge.updateCnyRecharge(fid);
			}),
//			$(".recordtitle").on("click", function() {
//				util.recordTab($(this));
//			}),
			$(".rechargecancel").on("click", function() {
				var fid = $(this).data().fid;
				recharge.cancelRechargeCNY(fid);
			}),
				$(".rechargelook").on("click", function() {
                    var fid = $(this).data().fid;
                    recharge.lookRechargeMessage(fid);
                }),
			$(".rechargesub").on("click", function() {
				var fid = $(this).data().fid;
				recharge.subRechargeCNY(fid);
			})
		})
	},
    updateCnyRecharge: function(id) {
        $("#desc").val(id),
        $("#rechage1").hide(),
        $("#rechage2").hide(),
        $("#rechage3").show(),
        $("#rechage4").hide(),
        $("html,body").stop(true),
        $("html,body").animate({
            scrollTop: $(".rightarea").offset().top
        },
        10),
        $(".recharge-process span").removeClass("active"),
        $("#rechargeprocess3").addClass("active")
    },
	cancelRechargeCNY: function(id) {
        var type = document.getElementById("finType").value;
        var msg = language["recharge.error.tips.1"];
        if(type == 3) {
        	msg = language["recharge.error.tips.5"];
		}
        util.layerConfirm(msg,
        function(result) {
            var url = "/account/cancelRechargeCnySubmit.html?random=" + Math.round(100 * Math.random()),
            param = {
    			id : id
    		};
            $.post(url, param,
            function(id) {
                null != id && (recharge.refTenbody(), layer.close(result))
            })
        })
    },
    subRechargeCNY: function(id) {
        util.layerConfirm(language["recharge.error.tips.2"],
        function(result) {
            var url = "/account/subRechargeCnySubmit.html?random=" + Math.round(100 * Math.random()),
            param = {
    			id : id
    		};
            $.post(url, param,
            function(id) {
                null != id && (recharge.refTenbody(), layer.close(result))
            })
        })
    },
	lookRechargeMessage:function (id) {

        var url="/account/detailRecharge.html?id="+id + "&random=" + Math.round(Math.random() * 100);
        $.get(url,function(data){
            document.getElementById("fownerNameOne").innerHTML = data.sysBankOwnerName;
            document.getElementById("fbankNumberOne").innerHTML = data.sysAccount;
            document.getElementById("fbankAddressOne").innerHTML = data.sysAddress;
            document.getElementById("bankMoneyOne").innerHTML = data.amount;
            document.getElementById("bankInfoOne").innerHTML = data.id;
            document.getElementById("bankInfotipsOne").innerHTML = data.id;

            $('.target-bankOne').text(data.bankName);
            if(data.account == "-") {
                $('.target-cardOne').text(data.account);
			}else{
                var tail_num = i18nMsg("tail_num");  //尾号
                $('.target-cardOne').text(tail_num+ data.account);
			}


            $('#lookRechargeMsg').modal('show');
		});
    },
	commission : function() {
		var amount = $("#diyMoney").val();
		var fee = 0;
		var fee = util.accMul(amount, fee);
		$("#ffee").html(fee);
		$("#realamount").html(amount - fee);
	},
	bankitemcheck : function(ele, value) {
		$(".bank-item-checked", ".bank-item").hide(0, function() {
			ele.find(".bank-item-checked").show();
		});
		$("#bankid").val(value);
	},
//	submitOnlineFinForm : function(that) {
//		var sbank = document.getElementById("bankid").value;
//		var type = document.getElementById("finType").value;
//		var minMoney = document.getElementById("minRecharge").value;
//		var money = 0;
//		if (sbank == "" || sbank == "0") {
//			util.showerrortips("errortips", language["comm.error.tips.83"]);
//			return;
//		}
//		money = document.getElementById("diyMoney").value;
//		if (Number(money) < Number(minMoney) ||
// isNaN(money)) {
//			util.showerrortips("errortips", language["comm.error.tips.84"].format(minMoney));
//			return;
//		}
//		var url = "/onlinepay/sumapay.html?random=" + Math.round(Math.random() * 100);
//		var param = {
//			money : money,
//			type : type,
//			sbank : sbank
//		};
//		that.disabled = true;
//		var isLink = false;
//		$.ajax({
//	         type: 'post',
//	         url: url,
//	         async: false,
//	         data:param,
//	         dataType: "json",
//	         success: function(result) {
//	        	if (result.code < 0) {
//					util.showerrortips("errortips", result.msg);
//				} else {
//					if (type == 2) {// 在线充值
//						$(that).attr("href",result.msg);
//						isLink=true;
//					}
//				}
//	         },
//	         error: function() {
//	             alert('发生未知错误，请稍候再试？');
//	             isLink = false;
//	         }
//	     });
//	     return isLink;
//		
//	},
	submitQrCodeFinForm : function(that) {
//		var accounts = document.getElementById("accounts").value;
		//var imgcode = document.getElementById("imgcode").value;
		var accounts = $("#accounts").val();
		var type = document.getElementById("finType").value;
		var random = document.getElementById("random").value;
		var minMoney = document.getElementById("minRecharge").value;
		var money = 0;
//		if (accounts == "") {
//			util.showerrortips("errortips", language["recharge.error.tips.3"]);
//			return;
//		}
		/*if (imgcode == "") {
			util.showerrortips("errortips", language["recharge.error.tips.4"]);
			return;
		}*/
		money = document.getElementById("diyMoney").value;
		if (money.toString().indexOf(".") != -1) {
			money = money.toString().split(".")[0];
			money = money + (random.substring(1));
		} else {
			money = money + (random.substring(1));
		}

		//根据充值方式，判断最大，最小充值金额
		if(type && type == 3) {  //支付宝充值

            //充值次数
            var user_alipay_num = $("#user_alipay_num").val() || 0;
            var alipayrgcnum = $("#alipayrgcnum").val() || 0;
            user_alipay_num = new Number(user_alipay_num);
            alipayrgcnum = new Number(alipayrgcnum);
            if(user_alipay_num >= alipayrgcnum) {
                util.showerrortips("errortips", language["comm.error.tips.146"].format(alipayrgcnum));
                return;
            }

            //上下限
			var minalipayrgc = $("#minalipayrgc").val() || 0;
			var maxalipayrgc = $("#maxalipayrgc").val() || 0;
			//统一数据类型
			if(minalipayrgc) {
                minalipayrgc = new Number(minalipayrgc);
			}
			if(maxalipayrgc) {
                maxalipayrgc = new Number(maxalipayrgc);
			}

			if(money) {
                money = new Number(money);
			}

			if(money < minalipayrgc || isNaN(money)) {
                util.showerrortips("errortips", language["comm.error.tips.145"].format(minalipayrgc));
                return;
			}

            if(money > maxalipayrgc || isNaN(money)) {
                util.showerrortips("errortips", language["comm.error.tips.144"].format(maxalipayrgc));
                return;
            }

		}else{
            if (Number(money) < Number(minMoney) || isNaN(money)) {
                util.showerrortips("errortips", language["comm.error.tips.84"].format(minMoney));
                return;
            }
		}

		var url = "/account/alipayTransfer.html?random=" + Math.round(Math.random() * 100);
		var param = {
			money : money,
			type : type,
			accounts : accounts,
			//imageCode:imgcode
		};
		that.disabled = true;
        if(type && type == 3) {  //支付宝充值
            util.showerrortips("errortips", language["comm.error.tips.147"]);
        }
		$.post(url, param, function(result) {
			that.disabled = false;
            util.hideerrortips("errortips");
			if (result != null) {
			
				if (result.resultCode < 0) {
					util.showerrortips("errortips", result.msg);
				} else {
					$('#refreshid').val(result.refreshid) ;  //保存人民币充值的流水id
					setTimeout(recharge.fcapitaloperationStatus, 1000) ;
					$("#ali_account").html(result.sys_ali_account);
                    $("#company").html(result.sys_ali_name);
					$("#qrcode_iframe").attr("src", result.aliay_req_url);
					$('.order-money').text(money)
					$("#orderDiolog").modal("show");
					
					/*$(".btn-imgcode").click();
					$('#qrcode').html("").qrcode({
						text : toUtf8(result.qrcode),
						width : "200",
						height : "200",
						render : "table",
						correctLevel:0
					});
					$("#rechargeDialog").modal("show");*/
					recharge.refTenbody();
				}
			}
		}, "json");
	},
	fcapitaloperationStatus:function(){
		$.post('/account/fcapitaloperationStatus.html',{id:$('#refreshid').val()},function(data){
			if(data.code == 0 ){
				window.location.reload() ;
			}else{
				setTimeout(recharge.fcapitaloperationStatus, 1000) ;
			}
			
		},'json') ;
	},
	getvirtualaddress:function(){
		var symbol=$("#symbol").val();
		var url="/account/getVirtualAddress.html?symbol="+symbol+"&random=" + Math.round(Math.random() * 100);
		$.get(url,function(data){
			if(data.code<0){
				util.showerrortips("", data.msg);
			}else{
				window.location.reload(true);
			}
		},"json");
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
					if (result.code == 0 || result.code == "0") {
						util.layerAlert("", language["withdraw.error.tips.4"], 1);
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
			$("#free").html(feeamt);
			$("#amount").html(util.moneyformat(parseFloat(amount) - parseFloat(feeamt), 2));
		}
	},
	addAlipy: function () {
		var name = $('#alipyname').val();
		var account = $('#alipayAccount').val();

		if(!util.checkEmail(account) && ! util.checkMobile(account)) {
			util.showerrortips("alipyerrortips", language["comm.error.tips.149"]);
			return false;
		}
		$('.addAlipy-confirm').attr('disabled', true);
		$.post('/user/addAlipayBind.html?random=' + Math.round(Math.random() * 100), {
			name: name,
			account: account
		}, function(data, textStatus, xhr) {
			
			if(data.code == 0) {
				var msg_content = i18nMsg("");
				$('#addAlipy-confirm').text(language["bind_success"]);
				location.reload(); 
			} else {
				util.showerrortips("alipyerrortips", data.msg || language["bind_fail"]);
			}
			$('.addAlipy-confirm').attr('disabled', false);

		},'json');
	},
    alipyform: function() {
        var random = document.getElementById("random").value;
        var money = document.getElementById("diyMoney").value;
        if (money.toString().indexOf(".") != -1) {
            money = money.toString().split(".")[0];
            money = money + (random.substring(1));
        } else {
            money = money + (random.substring(1));
        }

        if(money) {
            money = new Number(money);
		}

        $.post('/account/buildAliFormRequest.html?random=' + Math.round(Math.random() * 100), {
            fid: $('#refreshid').val(),
            money : money
        }, function(data, textStatus, xhr) {
        	console.log(data);
        	if(data.code == -1) {
                util.showerrortips("alipyerrortips", data.msg || language["network_anomaly"]);
			}else{
                $('#alipay_form_div').html(data.aliay_form_url);
                //让表单提交在新页面打开
                $("#alipaysubmit").attr("target", "_bank");
                $("#alipaysubmit").submit();
            }
        },'json');
	}
};
$(function() {
	$(".btn-imgcode").on("click", function() {
		this.src = "/servlet/ValidateImageServlet?r=" + Math.round(Math.random() * 100);
	});
	$(".recordtitle").on("click", function() {
		util.recordTab($(this));
	});
	$("#rechargebtn").on("click", function() {
		recharge.submitFinForm(this);
	});
	$("#rechargenextbtn").on("click", function() {
		recharge.submitPaymentInformation();
	});
	$("#rechargesuccessbtn").on("click", function() {
		recharge.submitTransferAccounts();
	});
	$(".completioninfo").on("click", function() {
		var fid = $(this).data().fid;
		recharge.updateFinTransactionReceive(fid);
	});
	$(".rechargecancel").on("click", function() {
		var fid = $(this).data().fid;
		recharge.cancelRechargeCNY(fid);
	});
    $(".rechargelook").on("click", function() {

        var fid = $(this).data().fid;
        recharge.lookRechargeMessage(fid);
    }),

        $(".rechargesub").on("click", function() {
		var fid = $(this).data().fid;
		recharge.subRechargeCNY(fid);
	});
	$("#diyMoney").on("keypress", function(event) {
		return util.VerifyKeypress(this, event, 0);
	});
	$("#diyMoney", ".online").on("keyup", function() {
		recharge.commission();
	});
	$(".bank-item").on("click", function() {
		var that = $(this);
		recharge.bankitemcheck(that, that.data().fid);
	});
	$("#rechargeonlinebtn").on("click", function() {
		return recharge.submitOnlineFinForm(this);
	});
	$("#qrcoderechargebtn").on("click", function() {
		recharge.submitQrCodeFinForm(this);
	});
	$("#virtualaddress").on("click", function() {
		recharge.getvirtualaddress(this);
	});
	$("#rechargeDialog-Btn").on("click", function() {
		window.location.reload(true) ;
	});
	$("#withdrawCnyAddrBtn").on("click", function() {
		recharge.cny.submitWithdrawCnyAddress();
	});

	//msg
	$(".btn-sendmsg").on("click", function() {
		if(this.id == "bindsendmessage") {
			var areacode = '86';
			var phone = $("#dialogPhone").val();
			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id, areacode, phone);
		} else {

			msg.sendMsgCode($(this).data().msgtype, $(this).data().tipsid, this.id);
		}
	});
	//银行账户

	if (document.getElementById('openBankTypeAddr')) {

		$('#openBankTypeAddr').autocomplete({
			lookup: BANKLIST,
			autoSelectFirst: true,
			minChars: 0,
			onSelect: function(suggestion) {
				$("#openBankTypeAddr").attr('data-id', suggestion.data);
			}
		});
	}

	//添加支付宝
	$('#addAlipy-confirm').on('click', function() {
		
		recharge.addAlipy();
	});

	//支付宝网页支付
	$("#ali_form_pay_a").on("click", function(){
		return recharge.alipyform();
	});
	
});