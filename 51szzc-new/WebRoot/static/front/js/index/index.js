var login={
		indexLoginOnblur:function () {
		    var uName = document.getElementById("indexLoginName").value;
		    if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
		        util.showerrortips("indexLoginTips",language["comm.error.tips.1"]);
		    } else {
		    	util.hideerrortips("indexLoginTips");
		    }
		},
		loginIndexSubmit:function () {
		    util.hideerrortips("indexLoginTips");
		    var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
		    var uName = document.getElementById("indexLoginName").value;
		    var pWord = document.getElementById("indexLoginPwd").value;
		    var longLogin = 0;
		    if (util.checkEmail(uName)) {
		        longLogin = 1;
		    }
		    if (!util.checkEmail(uName) && !util.checkMobile(uName)) {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.1"]);
		        return
		    }
		    if (pWord == "") {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.2"]);
		        return;
		    } else if (pWord.length < 6) {
		    	util.showerrortips("indexLoginTips", language["comm.error.tips.3"]);
		        return;
		    }
		    var param = {
		        loginName: uName,
		        password: pWord,
		        type: longLogin
		    };
		    jQuery.post(url, param, function (data) {
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
		        } else if (data.code ==-3) {
		        	$('.login-other-bg').hide()
		            $('.login-cn').hide()
		            $('.google_code').show()
		        }else {
		        	util.showerrortips("indexLoginTips", data.msg);
		            document.getElementById("indexLoginPwd").value = "";
		        }
		    },"json");
		},
		googleCode:function(code){
			var url = "/user/login/index.html?random=" + Math.round(Math.random() * 100);
		    var uName = document.getElementById("indexLoginName").value;
		    var pWord = document.getElementById("indexLoginPwd").value;
		    var longLogin = 0;
		    if (util.checkEmail(uName)) {
		        longLogin = 1;
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
		        	util.showerrortips("google_err", data.msg);
		            document.getElementById("google_code_input").value = "";
		        }
			});
		},
		refreshMarket:function(){
			var url="/real/indexmarket.html?random=" + Math.round(Math.random() * 100);
			$.post(url,{},function(data){
				$.each(data,function(key,value){
					$("#"+key+"_total").html(Number(value.total));
					if(Number(value.rose)>=0){
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html('+'+value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+value.buy1 );
						$("#"+key+"_sellPrice").removeClass("text-danger").removeClass("text-success").addClass("text-danger").html(value.symbol+value.sell1);
					}else{
						$("#"+key+"_rose").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.rose+'%');
						$("#"+key+"_price").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+value.buy1);
						$("#"+key+"_sellPrice").removeClass("text-danger").removeClass("text-success").addClass("text-success").html(value.symbol+value.sell1);
					}
					
				});
			});
			window.setTimeout(function() {
				login.refreshMarket();
			}, 5000);
		},
		loginerror:function(){
			var errormsg =$("#errormsg").val();
			if(errormsg!="" && errormsg!="/"){
				util.showerrortips("", errormsg);
			}
		},
		switchChart: function (symbol) {
			
			$('.trade-chart').html('<iframe frameborder="0" border="0" width="100%" height="482" id="klineFullScreen" name="klineiframe" src="/kline/fullstart.html?symbol='+ symbol +'&themename=Light"></iframe>');
		}
};
$(function(){
	login.loginerror();
	$("#indexLoginPwd").on("focus",function(){
		login.indexLoginOnblur();
		util.callbackEnter(login.loginIndexSubmit);
	});
	$("#loginbtn").on("click",function(){
		login.loginIndexSubmit();
	});
	/*谷歌验证*/
	$(".google_code .sign_in .btn").on("click",function(){
		var code=$(".google_code .write_in input").val()
		var exg=/[0-9]{6}/
		if (exg.test(code)) {
			login.googleCode(code);
			$('#google_err2').html('')
		}else{
			util.showerrortips("google_err", '请输入六位数字验证码！');
		    return;
		}
		
	});

	$(".help_list li").mousemove(function() {
        var long = 104;
        $(this).data("long") && (long = 134),
        $(this).find("span").stop().animate({
            width: long + "px"
        },
        200);
    }).mouseout(function() {
        $(this).find("span").stop().animate({
            width: "0px"
        },
        200);
    });
	login.refreshMarket();	

	//chartSettings 设置
	var chartSettings =  {"ver":3,"charts":{"chartStyle":"CandleStickHLC","mIndic":"MA","indics":["VOLUME","MACD"],"indicsStatus":"close","period":"1h","period_weight":{"0":7,"1":6,"2":5,"3":2,"4":1,"7":0,"9":4,"10":3,"11":0,"12":0,"13":0,"14":0,"15":0,"900":8,"line":8},"areaHeight":[]},"indics":{"MA":[7,30,0,0],"EMA":[7,30,0,0],"VOLUME":[5,10],"MACD":[12,26,9],"KDJ":[9,3,3],"StochRSI":[14,14,3,3],"RSI":[6,12,24],"DMI":[14,6],"OBV":[30],"BOLL":[20],"DMA":[10,50,10],"TRIX":[12,9],"BRAR":[26],"VR":[26,6],"EMV":[14,9],"WR":[10,6],"ROC":[12,6],"MTM":[12,6],"PSY":[12,6]},"theme":"Light"};
	var t = new Date;
	t.setDate(t.getDate() + 365);
	document.cookie = "chartSetting=" + escape(JSON.stringify(chartSettings)) + ";expires=" + t.toGMTString() + ";path=/";
	$('.trade-chart').html('<iframe frameborder="0" border="0" width="100%" height="482" id="klineFullScreen" name="klineiframe" src="/kline/fullstart.html?symbol='+ $('.trade-switch').eq(0).attr('data-id') +'&themename=Light"></iframe>');
	
	//切换货币
	var $dataBar = $('.trade-statistics');
	var $switchBtn = $('.trade-switch');

	login.symbol = $switchBtn.eq(0).attr('data-id');

	$switchBtn.on('click', function(event) {
		var $this = $(this);
		var index = $this.index();

		if($this.hasClass('active')) {
			return false;
		}

		$switchBtn.removeClass('active').eq(index).addClass('active');
		$dataBar.removeClass('active').eq(index).addClass('active');
		
		login.switchChart($switchBtn.eq(index).attr('data-id'));

	});

    //服务窗
    $('#contentxiaokui').click(function(event) {
        _MEIQIA._SHOWPANEL();
    });

	//kycDialog
	if ($('#kyclevel').val() == 1 &&　$('#islogin').val() == 'true') {
		var userName = $('#userName').val();

		if (decodeURIComponent(document.cookie.replace(new RegExp("(?:(?:^|.*;)\\s*" + encodeURIComponent(userName).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*([^;]*).*$)|^.*$"), "$1")) || null) {

			return false;
		}

		var now = new Date();
		var lastTime = new Date();
		//明日0时
		lastTime.setDate(now.getDate() + 1);
		lastTime.setHours(0); 
		lastTime.setMinutes(0); 
		lastTime.setSeconds(0);

		document.cookie = userName + "=" + escape(true) + ";expires=" + lastTime.toGMTString() + ';'; 

		$('#kycDialog').modal('show');
	}
});
/*
function lottery(){
	    var url = "/gamecenter/golottery.html";
	    jQuery.post(url, null, function (data) {
	        if (data.code == 0) {
	            if (data.qty ==0) {
	            	util.showerrortips("", "很遗憾，没有中奖，再接再厉！", {
						okbtn : function() {
							location.reload(true);
						}
					});
	            } else {
	            	$("#showQty").html(data.qty);
	            	$("#showName").html(data.name);
	            	document.getElementById("showDiv").style.display = "block";
	            }
	        } else if (data.code == -1) {
	        	util.showerrortips("",data.msg, {
					okbtn : function() {
						location.reload(true);
					}
				});
	        }
	    },"json");
	
}*/
 