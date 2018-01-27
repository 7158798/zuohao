var coinCount1 = 4;
var coinCount2 = 4;
if(document.getElementById("coinCount1") != null){
	coinCount1 = document.getElementById("coinCount1").value;
}
if(document.getElementById("coinCount2") != null){
	coinCount2 = document.getElementById("coinCount2").value;
}
var markt = {
	check : 1,
	NumVerify : function(tradeType) {
		var coinshortName = $("#coinshortName").val();
		if (tradeType == 0) {
			var userCnyBalance = document.getElementById("tradebuyprice").value;
			if (userCnyBalance == "") {
				userCnyBalance = 0;
			}
			var tradebuyAmount = document.getElementById("tradebuyamount").value;
			if (tradebuyAmount == "") {
				tradebuyAmount = 0;
			}
			var tradeTurnover = util.accMul(userCnyBalance, tradebuyAmount);
			var tradecnymoney = parseFloat(document.getElementById("toptradecnymoney").innerHTML);
			document.getElementById("tradebuyTurnover").innerHTML = util.moneyformat(tradeTurnover, 4);
			if (tradeTurnover > tradecnymoney) {
				util.showerrortips("buy-errortips", language["comm.error.tips.41"]);
				return;
			}
			util.hideerrortips("buy-errortips");
		} else {
			var usersellCnyBalance = document.getElementById("tradesellprice").value;
			if (usersellCnyBalance == "") {
				usersellCnyBalance = 0;
			}
			var tradesellAmount = $('.tradesellamount').val();
			if (tradesellAmount == "") {
				tradesellAmount = 0;
			}
			var tradeTurnover = util.accMul(usersellCnyBalance, tradesellAmount);
			var trademtccoin = parseFloat(document.getElementById("toptrademtccoin").innerHTML);
			document.getElementById("tradesellTurnover").innerHTML = util.moneyformat(tradeTurnover, 4);
			if (tradesellAmount > trademtccoin) {
				util.showerrortips("sell-errortips", language["comm.error.tips.42"].format(coinshortName));
				return;
			}
			util.hideerrortips("sell-errortips");
		}
	},
	coinTypeChange : function() {
		var coinType = document.getElementById("coinType").value;
		window.location.href="/market.html?type="+coinType;
	},
	submitTradePwd : function() {
		var tradePwd = document.getElementById("tradePwd").value;
		if (tradePwd != "") {
			document.getElementById("tradePwd").value = tradePwd;
			document.getElementById("isopen").value = "false";
		}
		$('#tradepass').modal('hide');
		var tradeType = document.getElementById("tradeType").value;
		this.submitTradeBtcForm(tradeType, false);
	},

	marktunit:function(buys,sells){
		var tmpBuy=buys.concat(sells);
		if(tmpBuy.length<=0){
			return;
		}
		tmpBuy.sort(function (a, b) {
            return a.amount - b.amount;
        });
		var i=Math.floor((tmpBuy.length/3)*2,0);
        return tmpBuy[i].amount<1?1:tmpBuy[i].amount;
	},
	lastprice: $("#lastprice").val() || 0,
	fristprice : true,
	autoRefresh : function() {
		var symbol = document.getElementById("symbol").value;
		var coinCount1 = $("#coinCount1").val();
		var coinCount2 = $("#coinCount2").val();
		var coinshortName = $("#coinshortName").val();		
		var buysellcount = 5;
		var successcount = 12;
		/*
		 * var url = "/real/userassets.html?random=" + Math.round(Math.random() *
		 * 100); $.post(url, { symbol : symbol }, function(data) { if (data !=
		 * "") {
		 * $("#toptradecnymoney").val(util.moneyformat(Number(data.availableCny),
		 * 2));
		 * $("#toptrademtccoin").val(util.moneyformat(Number(data.availableCoin),
		 * 4));
		 * $("#tradecnymoney").html(util.moneyformat(Number(data.availableCny),
		 * 2));
		 * $("#trademtccoin").html(util.moneyformat(Number(data.availableCoin),
		 * 4)); } }, "json");
		 */
        var depth = $("#sel").find("option:selected").text();
		url = "/real/market2.html?symbol={0}";
		url = url.format(symbol) + "&depth="+depth+"&random=" + Math.round(Math.random() * 100);
		$.get(url, function(data) {

			if (data != "") {
				var cny = $("#toptradecnymoney").val();
				var coin = $("#toptrademtccoin").val();
				var p_new = Number(data.p_new);
				var unit=markt.marktunit(data.buys,data.sells);
				
				$('.marketNew-current span').eq(0).text(p_new);
				
				var rate = 0;

				if(markt.lastprice > 0) {
					rate = (p_new - markt.lastprice)/markt.lastprice * 100;
				}
				rate = util.moneyformat(rate,2);
				if(rate > 0) {
					$('.marketNew-current span').eq(1)
						.css('color', 'rgb(198, 43, 43)')
						.text('+' + rate + '%');
				} else if(rate < 0) {
					$('.marketNew-current span').eq(1)
						.css('color', 'rgb(41, 150, 0)')
						.text(rate + '%');
				} 
				if(markt.lastprice != p_new) {
					markt.lastprice = p_new;
				}

				$('title').text(util.moneyformat(markt.lastprice, 2) +'  ' +$('#coinshortName').val()+'/CNY '+language["title_tdk1"]);
				
				var currentCny = $('#toptradecnymoney').text() * 1;
				var currentVer = $('#toptrademtccoin').text() * 1;
				$('.canBuy').text(util.moneyformat(currentCny / p_new, coinCount2));
				$('.canSell').text(util.moneyformat(currentVer * p_new, coinCount1));

				$.each(data.buys, function(key, value) {
					if (key >= buysellcount) {
						return;
					}
					var buyele = $("#buy" + key);
					if (buyele.length == 0) {
						// $("#buybox").append("<li id='buy" + key + "' class='list-group-item clearfix'>" +
						// 		"<span class='proportioninfo'>"+
						// 		"<span class='col-xs-2 buycolor padding-clear'>买 " + (key + 1) + "</span>" + 
						// 		"<span class='col-xs-4 padding-clear text-right'>" + util.moneyformat(Number(value.price), coinCount1) + "</span>" + 
						// 		"<span class='col-xs-6 padding-left-clear text-right'>" + util.moneyformat(Number(value.amount), coinCount2) + "</span>" + 
						// 		"</span>"+
						// 		"<span class='proportion'><span class='proportion-item-buy' style='width:" + (Number(value.amount) > unit ? 100 : Math.round(Number(value.amount)/unit*100)) + "%'></span></span>" + 
						// 		"</li>");

						$(".marketNew-price--buy").append('<div  data-type="1" data-money="'+value.price+'" data-num="'+value.amount+'" id="buy'+ 
							key+'" class="marketNew-priceItem"> <span class="marketNew-red">'+language["buy"]+(key + 1)+'</span> <span>'+
							util.moneyformat(Number(value.price), coinCount1)+'</span> <span>'+
							util.moneyformat(Number(value.amount), coinCount2)+'</span> </div>');
					} else {
						buyele.children().eq(1).text(util.moneyformat(Number(value.price), coinCount1));
						buyele.children().eq(2).text(util.moneyformat(Number(value.amount), coinCount2));
					}

				});
				for ( var i = data.buys.length; i < buysellcount; i++) {
					$("#buy" + i).remove();
				}


				var sells_arr = data.sells.slice(0,5).reverse();
                var sells_len = 5;
                if (data.sells.length < 5) {
                    sells_len = data.sells.length;
                }

                $('.marketNew-price--sell').html('');

				$.each(sells_arr, function(key, value) {
					if (key >= buysellcount) {
						return;
					}
					var sellele = $("#sell" + key);
					if (sellele.length == 0) {
						$(".marketNew-price--sell").append('<div data-type="0" data-money="'+value.price+'" data-num="'+value.amount+'" id="sell'+
							key+'" class="marketNew-priceItem"> <span class="marketNew-blue">'+language["sell"]+(sells_len - key)+'</span> <span>'+
							util.moneyformat(Number(value.price), coinCount1)+'</span> <span>'+
							util.moneyformat(Number(value.amount), coinCount2)+'</span> </div>');
					} else {
						sellele.children().eq(1).text(util.moneyformat(Number(value.price), coinCount1));
						sellele.children().eq(2).text(util.moneyformat(Number(value.amount), coinCount2));
					}
				});
				for ( var i = data.sells.length; i < buysellcount; i++) {
					$("#sell" + i).remove();
				}
				var loghtml = "";
				$.each(data.trades, function(key, value) {
					if (key >= successcount) {
						return;
					}

					loghtml += '<div class="marketNew-priceItem"> <span>'+
						value.time+'</span> <span class="'+ 
						(value.en_type == 'ask' ? "marketNew-green" : "marketNew-red")+'">'+
						util.moneyformat(Number(value.price), coinCount1)+'</span> <span>'+ 
						util.moneyformat(Number(value.amount), coinCount2)+'</span> </div>';
				});
				$(".marketNew-price--all").html("").append(loghtml);

				if (markt.fristprice) {
					if (data.buys.length <= 0) {
						$("#tradesellprice").val(util.moneyformat(Number(data.p_new), coinCount1));
					} else {
						$("#tradesellprice").val(data.buys[0].price);
					}
					if (data.sells.length <= 0) {
						$("#tradebuyprice").val(util.moneyformat(Number(data.p_new), coinCount1));
					} else {
						$("#tradebuyprice").val(data.sells[0].price);
					}
					markt.fristprice = false;
				}
				$(".marketNew-priceItem").on("click", function() {
					
					if($(this).parents('').hasClass('marketNew-price--all')) {
						return false;
					}

					if(!document.getElementById("userid").value) {
						return false;
					}
					var data = $(this).data();
					var type = data.type;
					var money = data.money;
					var num = data.num;
					var tradeAmount = "";
					if (type == 0) {
						tradeAmount = document.getElementById("tradebuyamount").value;
					} else {
						tradeAmount = $('.tradesellamount').val();
					}
					if (tradeAmount == "") {
						tradeAmount = 0;
					}
					markt.antoTurnover(money, 0, num, type);
				});
			};
		}, "json");
		
		url = "/real/userassets.html?random=" + Math.round(Math.random() * 100);
		$.post(url, {
			symbol : symbol,
			depth : depth
		}, function(data) {
			if (data != "") {
				$("#toptradecnymoney").html(util.moneyformat(Number(data.availableCny), 2));
				$("#toptrademtccoin").html(util.moneyformat(Number(data.availableCoin), 4));
				$("#toptradelevercny").html(util.moneyformat(Number(data.frozenCny), 2));
				$("#toptradelevercoin").html(util.moneyformat(Number(data.frozenCoin), 4));
				if ($("#headertotalasset").length > 0) {
					$("#headertotalasset").html('￥' + util.moneyformat(Number(data.leveritem.total),2));
				}
//				if ($("#headerasset").length > 0) {
//					$("#headerasset").html('￥' + util.moneyformat(Number(data.leveritem.asset), 4));
//				}
//				if ($("#headerscore").length > 0) {
//					$("#headerscore").html(util.moneyformat(Number(data.leveritem.score), 0));
//				}
				if ($("#headercny0").length > 0) {
					var cnychild = $("#headercny0").children();
					cnychild[1].innerHTML = util.moneyformat(Number(data.cnyitem.total), 2);
					cnychild[2].innerHTML = util.moneyformat(Number(data.cnyitem.frozen), 2);
					/*cnychild[3].innerHTML = util.moneyformat(Number(data.cnyitem.borrow), 2);*/
				}
				if ($("#headercoin" + data.coinitem.id).length > 0) {
					var coinchild = $("#headercoin" + data.coinitem.id).children();
					coinchild[1].innerHTML = util.moneyformat(Number(data.coinitem.total, 4));
					coinchild[2].innerHTML = util.moneyformat(Number(data.coinitem.frozen, 4));
					/*coinchild[3].innerHTML = util.moneyformat(Number(data.coinitem.borrow, 4));*/
				}
			}
		}, "json");

		var fentruststitle = $(".fentruststitle");
		var displaytype0, displaytype1, displayvalue0, displayvalue1;
		$.each(fentruststitle, function(index, key) {
			key = $(key);
			if (index == 0) {
				displaytype0 = key.data('type');
				displayvalue0 = key.data('value');
			}
			if (index == 1) {
				displaytype1 = key.data('type');
				displayvalue1 = key.data('value');
			}
		});
		url = "/trade/entrustInfo_current.html?symbol=" + symbol + "&type=0&tradeType=1&random=" + Math.round(Math.random() * 100);
		var hidden0 = $("#fentrustsbody0").is(":hidden") ;
		var hidden1 = $("#fentrustsbody1").is(":hidden") ;
		$.post(url,null,function(data){
			// if(data.indexOf('当前委托')==-1){
			// 	window.location.href = '/' ;
			// 	return ;
			// }
			var list = data.fentrusts1.slice(0,5);
			var text = '';

			if(list.length) {
				
				$.each(list, function (key,item) {
					var finishAmount = item.fcount - item.fleftCount;
					var avaragePrice = 0;

					if(item.fstatus != 1) {
						avaragePrice = util.moneyformat(item.fsuccessAmount/finishAmount, data.ftrademapping.fcount1);
					}
					text += '<tr> <td scope="row">'+
						item.flastUpdatTime+'</td> <td class="'+
						(item.fentrustType == 0 ? 'marketNew-red' : 'marketNew-blue')+'">'+
						item.fentrustType_s+'</td> <td>'+
						(item.fisLimit ? '市价' : '￥' + util.moneyformat(item.fprize, data.ftrademapping.fcount1))+'</td><td>'+
						util.moneyformat(item.fcount, data.ftrademapping.fcount2)+'</td> <td>'+
						util.moneyformat(finishAmount, data.ftrademapping.fcount2)+'</td> <td>'+
						util.moneyformat(item.fsuccessAmount, data.ftrademapping.fcount1)+'</td> <td>￥'+
						avaragePrice+'</td> <td class="'+
						(item.fstatus == 1 ? 'marketNew-pink' : 'marketNew-green')+'">'+
						item.fstatus_s+'</td> <td> <a class="marketNew-cancel" data-id="'+
						item.fid+'" href="javascript:;">'+language["revoked"]+'</a> </td> </tr>';
				});
			}

			$(".marketNew-entrust--current tbody").html(text);
			$(".fentruststitle").on("click", function() {
				trade.entrustInfoTab($(this));
			});
			$(".tradecancel").on("click", function() {
				var id = $(this).data().value;
				trade.cancelEntrustBtc(id);
			});
			$(".allcancel").on("click", function() {
				var id = $(this).data().value;
				trade.cancelAllEntrustBtc(id);
			});
			$(".tradelook").on("click", function() {
				var id = $(this).data().value;
				trade.entrustLog(id);
			});
			
			if(hidden0 == true ){
				$(".fentruststitle").eq(0).click();
			}
			if(hidden1 == true ){
				$(".fentruststitle").eq(1).click();
			}
			
		},'json') ;

		var historyUrl = "/trade/entrustInfo_current.html?symbol=" + symbol + "&type=0&tradeType=3&random=" + Math.round(Math.random() * 100);

		$.post(historyUrl, null, function(data) {
			
			var historylist = data.fentrusts1.slice(0,5);
			var historytext = '';
			$.each(historylist, function(i, item) {
				
				var finishAmount2 = item.fcount - item.fleftCount;
				var avaragePrice2 = 0;

				if(item.fstatus != 4) {
					avaragePrice2 = util.moneyformat(item.fsuccessAmount/finishAmount2, data.ftrademapping.fcount1);
				} else {
					avaragePrice2 = item.fprize;
				}

				historytext += '<tr><td scope="row">'+item.flastUpdatTime+'</td> <td class="'+
				(item.fentrustType == 0 ? 'marketNew-red' : 'marketNew-blue')+'">'+
				item.fentrustType_s+'</td> <td>'+
				(item.fisLimit ? '市价' : '￥' + util.moneyformat(item.fprize, data.ftrademapping.fcount1))+'</td><td>'+
				util.moneyformat(item.fcount, data.ftrademapping.fcount2)+'</td><td>'+
				util.moneyformat(finishAmount2, data.ftrademapping.fcount2)+'</td> <td>'+
				util.moneyformat(finishAmount2 * avaragePrice2, data.ftrademapping.fcount1)+'</td> <td>'+
				util.moneyformat(item.ffees - item.fleftfees, data.ftrademapping.fcount2)+
			 	(item.fentrustType == 0 ?　data.coin2.fSymbol　:　data.coin1.fSymbol) +'</td> <td>'+
				avaragePrice2+'</td></tr>';
			});
			$('.marketNew-entrust--history tbody').html(historytext);
		},'json');

		window.setTimeout(function() {
			markt.autoRefresh();
		}, 2000);
	},

	antoTurnover : function(price, money, mtcNum, tradeType) {
		if (tradeType == 0) {
			document.getElementById("tradebuyprice").value = util.moneyformat(price, coinCount1);
			document.getElementById("tradebuyamount").value = util.moneyformat(mtcNum, coinCount2);
			var tradeTurnover = util.moneyformat(util.accMul(price, mtcNum), 4);
//			console.log(tradeTurnover);
			var tradecnymoney = util.moneyformat(Number(document.getElementById("toptradecnymoney").innerHTML), 4);
			if (parseFloat(tradeTurnover) > parseFloat(tradecnymoney)) {
				util.showerrortips("buy-errortips", language["comm.error.tips.41"]);
				return;
			}
			document.getElementById("tradebuyTurnover").innerHTML = tradeTurnover;
			util.hideerrortips("buy-errortips");
		} else {
			var coinName = document.getElementById("coinshortName").value;
			document.getElementById("tradesellprice").value = util.moneyformat(price, coinCount1);
			document.getElementById("tradesellamount").value = util.moneyformat(mtcNum, coinCount2);
			var tradeTurnover = util.accMul(price, mtcNum);
			var trademtccoin = util.moneyformat(Number(document.getElementById("toptrademtccoin").innerHTML), 4);
			if (parseFloat(mtcNum) > parseFloat(trademtccoin)) {
				util.showerrortips("sell-errortips", language["comm.error.tips.42"].format(coinName));
				return;
			}
			document.getElementById("tradesellTurnover").innerHTML = util.moneyformat(tradeTurnover, 4);
			util.hideerrortips("sell-errortips");
		}
	},
	autotradingbox : function() {
		var offsetop = $(".info-box").offset().top;
		$(window).scroll(function() {
			var scoretop = $('body').scrollTop();
			if (scoretop > offsetop) {
				$(".info-box").css("top", (scoretop - offsetop + 30) + "px");
			} else {
				$(".info-box").css("top", "0px");
			}
		});
	},
	klineFullScreenOpen:function (){
		$('#closefullscreen').show();
		$('#openfullscreen').hide();
		$('#delegateinfo').hide();
		$('#allFooter').hide();
		$('#allheader').hide();
		$('#marketheader').hide();	
		$('#klineFullScreen').addClass('fullscreen');
	},
	klineFullScreenClose:function (isRemote){
		$('#closefullscreen').hide();
		$('#openfullscreen').show();
		$('#delegateinfo').show();
		$('#allFooter').show();
		$('#allheader').show();
		$('#marketheader').show();	
		$('#klineFullScreen').removeClass('fullscreen');
	},
	onPortion : function(portion, tradeType) {
		portion = util.accDiv(portion, 100);
		if (tradeType == 0) {
			var money = Number(document.getElementById("tradebuyprice").value);
			var tradecnymoney = Number(document.getElementById("toptradecnymoney").innerHTML);
			var mtcNum = util.accDiv(tradecnymoney, money);
			mtcNum = util.accMul(mtcNum, portion);
			var portionMoney = util.accMul(money, mtcNum);
			this.antoTurnover(money, portionMoney, mtcNum, tradeType);
		} else {
			var money = Number(document.getElementById("tradesellprice").value);
			var trademtccoin = Number(document.getElementById("toptrademtccoin").innerHTML);
			mtcNum = util.accMul(trademtccoin, portion);
			var portionMoney = util.accMul(money, mtcNum);
			this.antoTurnover(money, portionMoney, mtcNum, tradeType);
		}
	},
	cancelEntrustBtc: function() {
        var id = markt.tradeCancelId;
        var url = "/trade/cancelEntrust.html?random=" + Math.round(100 * Math.random()),
		param = {
			id: id
		};
		$.post(url, param, function(id) {
			null != id && location.reload(true);
		},'json')
    },
    cancelAllEntrustBtc: function(id,type) {
		var url = "/trade/cancelAllEntrust.html?random=" + Math.round(100 * Math.random()),
			param = {
				id: id,
				type: type
			};
		$.post(url, param, function(id) {
			null != id && location.reload(true);
		})
    },
	submitTradeBtcForm : function(tradeType, flag) {
		
		$("#limitedType").val(0) ;
		
		errorele = "";
		if (tradeType == 0) {
			errorele = "buy-errortips";
		} else {
			errorele = "sell-errortips";
		}
		var tradePassword = document.getElementById("tradePassword").value;
		var userid = document.getElementById("userid").value;
		var minBuyCount = new Number(util.trim(document.getElementById("minBuyCount").value));
		if(userid ==0 || userid=="0"){
			util.layerAlert("", language["trade.error.tips.1"], 2);
			return;
		}
		if (tradePassword == "false") {
			util.layerAlert("", language["trade.error.tips.2"], 4);
			return;
		}
		var isTelephoneBind = document.getElementById("isTelephoneBind").value;
		if (isTelephoneBind == "false") {
			util.layerAlert("", language["trade.error.tips.3"], 2);
			return;
		}
		var symbol = document.getElementById("symbol").value;
		var coinName = document.getElementById("coinshortName").value;

		if (tradeType == 0) {
			var tradeAmount = document.getElementById("tradebuyamount").value;
			var tradeCnyPrice = document.getElementById("tradebuyprice").value;
		} else {
			var tradeAmount = $('.tradesellamount').val();
			var tradeCnyPrice = document.getElementById("tradesellprice").value;
		}
		var isAMT = util.checkNumber(tradeAmount);
		var isPrice = util.checkNumber(tradeCnyPrice);
		if(!isAMT || !isPrice){
			util.showerrortips(errorele, language["price_or_num_error"]);
			return;
		}
		var limited = 0;
		if (tradeType == 0) {
			var tradeTurnover = tradeAmount * tradeCnyPrice;
			if (document.getElementById("toptradecnymoney") != null && Number($("#toptradecnymoney").text()) < Number(tradeTurnover)) {
				util.showerrortips(errorele, language["comm.error.tips.41"]);
				return;
			} else {
				util.hideerrortips(errorele);
			}
		} else {
			if (document.getElementById("toptrademtccoin") != null && Number($("#toptrademtccoin").text()) < Number(tradeAmount)) {
				util.showerrortips(errorele, language["comm.error.tips.42"].format(coinName));
				return;
			} else {
				util.hideerrortips(errorele);
			}
		}
		var reg = new RegExp("^[0-9]+\.{0,1}[0-9]{0,8}$");
		if (!reg.test(tradeAmount)) {
			util.showerrortips(errorele, language["comm.error.tips.43"]);
			return;
		} else {
			util.hideerrortips(errorele);
		}
		//console.log("tradeAmount=" + tradeAmount + " minBuyCount=" + minBuyCount);
		//console.log("tradeAmount < minBuyCount = " + tradeAmount < minBuyCount);
		tradeAmount = new Number(tradeAmount);
		if (tradeAmount < minBuyCount) {
			util.showerrortips(errorele, language["comm.error.tips.44"].format(minBuyCount, coinName));
			return;
		} else {
			util.hideerrortips(errorele);
		}
		if (!reg.test(tradeCnyPrice)) {
			util.showerrortips(errorele, language["comm.error.tips.45"]);
			return;
		} else {
			util.hideerrortips(errorele);
		}
		if (tradeCnyPrice <= 0) {
			util.showerrortips(errorele, language["trade.error.tips.4"]);
			return;
		} else {
			util.hideerrortips(errorele);
		}
		var total = util.moneyformat(util.accMul(tradeAmount, tradeCnyPrice), coinCount1);
		if (parseFloat(total) <= 0) {
			util.showerrortips(errorele, language["trade.error.tips.5"]);
			return;
		} else {
			util.hideerrortips(errorele);
		}
		var isopen = document.getElementById("isopen").value;
		if (isopen == "true" && flag) {
			document.getElementById("tradeType").value = tradeType;
			$('#tradepass').modal({
				backdrop : 'static',
				keyboard : false,
				show : true
			});
			return;
		}
		var tradePwd = "";
		if (document.getElementById("tradePwd") != null) {
			tradePwd = util.trim(document.getElementById("tradePwd").value);
		}
		if (tradePwd == "" && isopen == "true") {
			util.showerrortips(errorele, language["comm.error.tips.46"]);
			document.getElementById("isopen").value = true;
			return;
		} else {
			util.hideerrortips(errorele);
		}
		
		var url = "";
		if (tradeType == 0) {
			url = "/trade/buyBtcSubmit.html?random=" + Math.round(Math.random() * 100);
		} else {
			url = "/trade/sellBtcSubmit.html?random=" + Math.round(Math.random() * 100);
		}
		tradePwd = isopen == "true" ? "" : tradePwd;
		var param = {
			tradeAmount : tradeAmount,
			tradeCnyPrice : tradeCnyPrice,
			tradePwd : tradePwd,
			symbol : symbol,
			limited : limited
		};
		var btntext="";
		var btn = "";
		if(tradeType==0){
			btn = document.getElementById("buybtn");
			btntext = btn.innerHTML;
			btn.innerHTML = language["trade.error.tips.6"];
		}else{
			btn = document.getElementById("sellbtn");
			btntext = btn.innerHTML;
			btn.innerHTML = language["trade.error.tips.7"];
		}
		btn.disabled = true;		
		jQuery.post(url, param, function(data) {
			btn.disabled = false;
			btn.innerHTML = btntext;
			if (data.resultCode != 0) {
				document.getElementById("isopen").value = "true";
				util.showerrortips(errorele, data.msg);
			}else if(data.resultCode == 0) {
				util.showerrortips(errorele, language["trade.error.tips.8"]);
				if (tradeType == 0) {
					document.getElementById("tradebuyamount").value="";
					$("#tradebuyTurnover").html("0");
				} else {
					$("#tradesellTurnover").html("0");
				}

				$('#tradeTips').modal('show');
				window.setTimeout(function() {
					$("#"+errorele).html("");
				}, 2000);
			}
		}, "json");
	},
};

$(function() {
	if(document.getElementById("tradesellTurnover") != null){
		document.getElementById("tradesellTurnover").value=0;
	}
	if(document.getElementById("tradebuyTurnover") != null){
		document.getElementById("tradebuyTurnover").value=0;
	}

	//chartSettings 设置
	var chartSettings =  {"ver":3,"charts":{"chartStyle":"CandleStickHLC","mIndic":"MA","indics":["VOLUME","MACD"],"indicsStatus":"close","period":"1h","period_weight":{"0":7,"1":6,"2":5,"3":2,"4":1,"7":0,"9":4,"10":3,"11":0,"12":0,"13":0,"14":0,"15":0,"900":8,"line":8},"areaHeight":[]},"indics":{"MA":[7,30,0,0],"EMA":[7,30,0,0],"VOLUME":[5,10],"MACD":[12,26,9],"KDJ":[9,3,3],"StochRSI":[14,14,3,3],"RSI":[6,12,24],"DMI":[14,6],"OBV":[30],"BOLL":[20],"DMA":[10,50,10],"TRIX":[12,9],"BRAR":[26],"VR":[26,6],"EMV":[14,9],"WR":[10,6],"ROC":[12,6],"MTM":[12,6],"PSY":[12,6]},"theme":"Dark"};
	var t = new Date;
	t.setDate(t.getDate() + 365);
	document.cookie = "chartSetting=" + escape(JSON.stringify(chartSettings)) + ";expires=" + t.toGMTString() + ";path=/";

	$("#tradebuyprice").on("keyup", function() {
		markt.NumVerify(0);
	}).on("keypress", function(event) {
		console.log(util.VerifyKeypress(this, event, 2))
		return util.VerifyKeypress(this, event, 2);
	});
	$("#tradebuyamount").on("keyup", function() {
		markt.NumVerify(0);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount2);
	});
	$("#tradesellamount").on("keyup", function() {
		markt.NumVerify(1);
	}).on("keypress", function(event) {
		return util.VerifyKeypress(this, event, coinCount2);
	}).on("click", function() {
		this.focus();
		//this.select();
	});
	$("#buybtn").on("click", function() {
		markt.submitTradeBtcForm(0, true);
	});
	$("#sellbtn").on("click", function() {
		markt.submitTradeBtcForm(1, true);
	});
	$("#modalbtn").on("click", function() {
		markt.submitTradePwd();
	});
	$("#openfullscreen").on("click", function() {
		markt.klineFullScreenOpen();
	});
	$("#closefullscreen").on("click", function() {
		markt.klineFullScreenClose();
	});
	$("#coinType").on("change", function() {
		markt.coinTypeChange();
	});
	markt.autoRefresh();
	//markt.autotradingbox();

	$("#buyslider").on("change", function(e, val) {
		markt.onPortion(val, 0);
	//	$("#buyslidertext").html(val + "%");
	});
	$("#sellslider").on("change", function(e, val) {
		markt.onPortion(val, 1);
	//	$("#sellslidertext").html(val + "%");
	});

	$(document).on('click','.marketNew-cancel', function() {
		
		var id = $(this).attr('data-id');
		$('#tradeCancelDialog').modal('show');
		markt.tradeCancelId = id;
	});

	$('#tradeCancelDialog-confime').on('click', function() {
		
		markt.cancelEntrustBtc();
	});
	$('#tradeCancelDialog-cancel').on('click', function() {
		$('#tradeCancelDialog').modal('hide');
	});
	
	var $window = $(window);
	var $chartWrap = $('.marketNew-statistics');
	var topHeight = $('.marketNew-header').height();
	var bottomHeight = $('.marketNew-panel').height();
	var dataWidth = $('.marketNew-datas').width();
	var $chart = $('.marketNew-chart');
	var $record = $('.marketNew-record');

	$window.resize(function() {	
		var height = $window.height();
		var width = $window.width();

		var chartHeight = height - topHeight - bottomHeight;
		var chartWidth = Math.floor(width - dataWidth - 0.1);
		var lh = Math.floor((chartHeight - $('.marketNew-dataTitle').height()) / 12);
		
		if(lh < 12) {
			lh = 12;
		}

		$chartWrap.height(chartHeight);
		$chart.height(chartHeight).width(chartWidth);
		$record.width(chartWidth);
		$('.marketNew-price').css('line-height', lh + 'px');
	});

	$(window).trigger('resize');

	$('.marketNew-recordType').on('click', function() {
		var index = $(this).index();

		$('.marketNew-recordType').removeClass('active').eq(index).addClass('active');
		$('.marketNew-moreRecord').removeClass('active').eq(index).addClass('active');
		$('.marketNew-entrust').removeClass('active').eq(index).addClass('active');
	});
	
	//批量操作
	$('.marketNew-multiCancel').on('click', function() {
		
		if(!$('.marketNew-entrust--current tbody').children().length) {

			util.layerAlert("", language["cancel_entrutst_desc"], 2);
			return false;
		}

		$('#tradeMutiCancel').modal('show');
		$('[name=cancelType]').eq(0).trigger('click');
	});
	$('#tradeMutiCancel-cancel').on('click', function() {
		$('#tradeMutiCancel').modal('hide');
	});
	$('#tradeMutiCancel-confime').on('click', function() {
		var type = $('[name=cancelType]:checked').val() || 0;
		var id = $('#symbol').val();
		markt.cancelAllEntrustBtc(id,type);
	});

	$('#tradeTips-close').on('click', function() {
		
		$('#tradeTips').modal('hide');
	});

	$('#tradepass-close').on('click', function() {
		
		$('#tradepass').modal('hide');
	});
    // 下拉框状态改变 重新刷新数据
    $("#sel").on("change",function () {
        markt.autoRefresh();
    });
});