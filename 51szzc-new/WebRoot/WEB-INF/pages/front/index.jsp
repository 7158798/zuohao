<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="comm/link.inc.index.jsp"%>
<link rel="stylesheet" href="${oss_url}/static/front/css/index/index.css?r=<%=new java.util.Date().getTime() %>" type="text/css"></link>
</head>
<body class="gray-bg">

	<div class="container-full contact-bar text-center">
		<div class="container">
			<span class="pull-left">
				<img src="${oss_url}/static/front/images/index/nav_btn_qq@2.png" alt="qq">
				<a href="http://wpa.qq.com/msgrd?v=3&uin=${requestScope.constant['serviceQQ'] }&menu=yes" target="_black"><spring:message code="qq_consulting"/></a>
			</span>
			<span class="pull-left">
				<img src="${oss_url}/static/front/images/index/nav_btn_phone@2x.png" alt="phone">
				400-900-6615
			</span>
			<spring:message code="hello_trade_platform"/>
			<a class="pull-right newslink" href="/service/ourService.html"><spring:message code="news_desc"/></a>
			<a target="_blank" href="/about/appDownload.html" class="pull-right down-app-btn"><spring:message code="down_app"/></a>
		</div>
	</div>

<%@include file="comm/header.jsp"%>



	<div class="container-full ">
		<div class="container login-box">
			
			
			<c:if test="${sessionScope.login_user!=null }">
				<div class="login loginsuccess">
					<div class="login-bg loginin"></div>
					<div class="login-cn form-horizontal">
						<div class="form-group">
							<a href="/financial/index.html">
								<h3 class="margin-top-clear font-size-18">${login_user.floginName}</h3>
							</a>
						</div>
						<div class="login-login-cn">
							<div class="form-group">
								<div class="col-xs-12">
									<span class="infobox">
										<span class="info-left"><spring:message code="east_taotal_amount1"/></span>
										<span class="info-right">
											￥<ex:DoubleCut
										value="${totalCapitalTrade }" pattern="##.##"
										maxIntegerDigits="15" maxFractionDigits="2" />
										</span>
									</span>
								</div>
								<div class="col-xs-12">
									<span class="infobox">
										<span class="info-left"><spring:message code="avai_rmb"/></span>
										<span class="info-right">
											￥<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
										</span>
									</span>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-12">
								<a href="/trade/coin.html" class="btn btn-comin btn-block"><spring:message code="to_trade_center1"/></a>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-6 btn-right">
								<a href="/account/rechargeCny.html?type=3" class="btn btn-addmoney btn-block"><spring:message code="lmenu_rechargeCny"/></a>
							</div>
							<div class="col-xs-6 btn-left">
								<a href="/account/withdrawCny.html" class="btn btn-getmoney btn-block"><spring:message code="lmenu_withdrawCny"/></a>
							</div>
						</div>
					</div>
				</div>
				</c:if>
				
				<c:if test="${sessionScope.login_user==null }">
				<div class="login" >
					<div class="login-bg"></div>
					<div class="login-other-bg"></div>
					<div class="login-cn">
						<div class="form-group">
							<h3 class="margin-top-clear" style="font-size: 20px;"><spring:message code="signIn"/> ${requestScope.constant['webName']}</h3>
						</div>
						<div class="form-group">
							<input class="form-control" id="indexLoginName" placeholder="${fill_phone_email}" type="text" autocomplete="off">
						</div>
						<div class="form-group">
							<input class="form-control" id="indexLoginPwd" placeholder="${fill_pwd}" type="password" autocomplete="off">
						</div>
						<div class="form-group has-error">
							<span id="indexLoginTips" class="errortips text-danger help-block"></span>
						</div>
						<div class="form-group">
							<button id="loginbtn" class="btn btn-block btn-danger">
								<spring:message code="signIn"/>
							</button>
						</div>
						<div class="form-group">
							<a href="/validate/reset.html"><spring:message code="forgot_password"/></a>
							<a href="/user/register.html" class="pull-right"><spring:message code="regist"/></a>
						</div>
					</div>
					<div class="google_code">
						<div class="form-group">
							<h3>登录51数字资产</h3>
						</div>
						<div class="form-group write_in">
							<input class="form-control" id="google_code_input"  placeholder="请输入谷歌验证码" type="text" maxlength="6" onkeyup="this.value=this.value.replace(/\D/gi,'')">
						</div>
						<div class="form-group has-error">
							<span id="google_err" class="errortips text-danger help-block"></span>
						</div>
						<div class="form-group sign_in">
							<button class="btn btn-block btn-danger">登录</button>
						</div>
					</div>
					<!--<div class="login-other">
						<a href="/link/qq/call.html">QQ登录</a><span class="split"></span>
						<a href="javascript:weixin();" class="pull-right">微信登录</a>
					</div>-->
				</div>
				</c:if>	
			
		</div>
		<div id="shuffling" class="carousel slide" data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#shuffling" data-slide-to="0" class="active"></li>
				<li data-target="#shuffling" data-slide-to="1"></li>
				<li data-target="#shuffling" data-slide-to="2"></li>
				<li data-target="#shuffling" data-slide-to="3"></li>
                <c:if test="${!requestScope.constant['bigImage5'].equals('#')}">
					<li data-target="#shuffling" data-slide-to="4"></li>
				</c:if>
				<c:if test="${!requestScope.constant['bigImage6'].equals('#')}">
					<li data-target="#shuffling" data-slide-to="5"></li>
				</c:if>


			</ol>
			<div class="carousel-inner">
				<a class="item active" target="_blank" href="${requestScope.constant['bigImageURL1'] }" style="background: url('${requestScope.constant['bigImage1'] }') no-repeat 50% 50%;height: 546px;"></a>
				<a class="item" target="_blank" href="${requestScope.constant['bigImageURL2'] }" style="background: url('${requestScope.constant['bigImage2'] }') no-repeat 50% 50%;height: 546px;"></a>
				<a class="item" target="_blank" href="${requestScope.constant['bigImageURL3'] }" style="background: url('${requestScope.constant['bigImage3'] }') no-repeat 50% 50%;height: 546px;"></a>
				<a class="item" target="_blank" href="${requestScope.constant['bigImageURL4'] }" style="background: url('${requestScope.constant['bigImage4'] }') no-repeat 50% 50%;height: 546px;"></a>
				<c:if test="${!requestScope.constant['bigImage5'].equals('#')}">
				   <a class="item" target="_blank" href="${requestScope.constant['bigImageURL5'] }" style="background: url('${requestScope.constant['bigImage5'] }') no-repeat 50% 50%;height: 546px;"></a>
				</c:if>
				<c:if test="${!requestScope.constant['bigImage6'].equals('#')}">
					<a class="item" target="_blank" href="${requestScope.constant['bigImageURL6'] }" style="background: url('${requestScope.constant['bigImage6'] }') no-repeat 50% 50%;height: 546px;"></a>
				</c:if>
			</div>
		</div>
	</div>

	<c:forEach var="vv" items="${fMap }">
	<div class="container-full trade-container">
		<div class="trade clearfix">
			<div class="trade-slide">
				<p class="text-center"><spring:message code="trade_info"/></p>
				<ul class="list-unstyled">
					<c:forEach items="${vv.value }" var="v" varStatus="vs">
					<li data-id="${v.fid }" class="trade-switch ${vs.index == 0 ? 'active' : '' }">
						${v.fvirtualcointypeByFvirtualcointype2.fShortName } / <span>${v.fvirtualcointypeByFvirtualcointype1.fShortName }</span>
					</li>
					</c:forEach>
				</ul>
			</div>
			<div class="trade-main ">
				<c:forEach items="${vv.value }" var="v" varStatus="vs">
				<div class="trade-statistics ${vs.index==0 ? 'active':'' } clearfix text-center">
					<div class="trade-data">
						<p>
							<span id="${v.fid }_price">--</span>
						</p>
						<p>买一价</p>
					</div>
					<div class="trade-data">
						<p>
							<span id="${v.fid }_sellPrice">--</span>
						</p>
						<p>卖一价</p>
					</div>
					<div class="trade-data">
						<p id="${v.fid }_total">
							--
						</p>
						<p>24H成交量</p>
					</div>
					<div class="trade-data">
						<p id="${v.fid }_rose">
							--
						</p>
						<p>今日涨跌幅</p>
					</div>
					<div class="trade-data">
						<a class="trade-link-btn btn" href="/trade/coin.html?coinType=${v.fid }&tradeType=0"><spring:message code="go_trade" /></a>
					</div>
					<div class="trade-link-trade">
						<a style="font-size:14px" class="pull-right" href="/market.html?symbol=${v.fid }"><spring:message code="trade_info" />&gt;&gt;</a>
					</div>
				</div>
				</c:forEach>
				<div class="trade-chart">
					
				</div>
			</div>
		</div>
	</div>
	</c:forEach>
	<div class="container-full advantage">
		<div class="container text-center">
			<div class="home-title">
				<h1>
					我们的优势 
				</h1>
				<p>our strengths</p>
			</div>
			<div class="advantage-inner">
				<div>
					<span class="advantage-item">
						<img class="pull-left" src="${oss_url}/static/front/images/index/home_awards_safe@2.png" alt="advantage">
						<h4><spring:message code="home_safety"/></h4>
						<p><spring:message code="home_safety_title"/></p>
					</span>
					<span class="advantage-item">
						<img class="pull-left" src="${oss_url}/static/front/images/index/home_awards_trustworthy@2.png" alt="advantage">
						<h4><spring:message code="home_reliable"/></h4>
						<p><spring:message code="home_reliable_title"/></p>
					</span>
				</div>
				<div>
					<span class="advantage-item">
						<img style="margin-top:-8px" class="pull-left" src="${oss_url}/static/front/images/index/home_awards_service@2.png" alt="advantage">
						<h4><spring:message code="home_userFirst"/></h4>
						<p><spring:message code="home_userFirst_title"/></p>
					</span>
					<span class="advantage-item">
						<img style="margin-top:-8px" class="pull-left" src="${oss_url}/static/front/images/index/home_awards_free@2.png" alt="advantage">
						<h4><spring:message code="home_lowFee"/></h4>
						<p><spring:message code="home_lowFee_title"/> </p>
					</span>
					<span class="advantage-item">
						<img class="pull-left" src="${oss_url}/static/front/images/index/home_awards_quick@2.png" alt="advantage">
						<h4><spring:message code="home_intoAccount"/></h4>
						<p><spring:message code="home_intoAccount_title"/></p>
					</span>
				</div>
			</div>
		</div>
	</div>

	<div class="news">
		<div class="container">
			<div class="news-wrap clearfix">
				<div class="news-type news-type--red">热门资讯</div>
				<div class="news-list">
				<c:forEach items="${requestScope.constant['hostNews']}" var="v">
					<a href="/service/article.html?id=${v.fid}" class="news-item pull-left">
						<img class="news-itemPic" src="${v.furl}" alt="new">
						<p class="news-itemTitle">
							<span class="news-itemTitle-text">${v.ftitle}</span>
							<span class="news-itemTime"><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/></span>
						</p>
						<p class="news-itemDesc">
							${v.fcontent_m}
						</p>
					</a>
				</c:forEach>
				</div>
				<a href="/service/ourService.html" class="news-more pull-right"><spring:message code="see_more"/>&gt;&gt;</a>
			</div>
		</div>
		<div class="container clearfix">	
			<div class="media">
				<div class="news-type"><spring:message code="home_media_reports"/></div>
				<ul class="affiche-list list-unstyled">
				<c:forEach items="${requestScope.constant['outArticle']}" var="v">
					<li>
						<a target="_blank" href="${v.foutUrl}">${v.ftitle}</a>
						<span class="pull-right"><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/></span>
					</li>
				</c:forEach>
				</ul>
				<a href="/service/ourService.html" class="news-more pull-right"><spring:message code="see_more"/>&gt;&gt;</a >
			</div>
			<div class="affiche">
				<div class="news-type"><spring:message code="home_bulletin"/></div>
				<ul class="affiche-list list-unstyled">
				<c:forEach items="${requestScope.constant['notices']}" var="v">
					<li>
						<a target="_blank" href="${v.url }">${v.ftitle }</a>
						<span class="pull-right"><fmt:formatDate value="${v.fcreateDate }" pattern="yyyy-MM-dd"/></span>
					</li>
				</c:forEach>
				</ul>
			<a href="/service/ourService.html?id=3" class="news-more pull-right">&gt;&gt;</a >
			</div>
		</div>
	</div>

	<div class="container-full services">
		 <div class="container clearfix">
		 	<div class="service-item">
		 		<img class="service-item-icon" src="/static/front/images/index/home_contact_phone@2.png" alt="service">
		 		 <p style="width: 140px"><spring:message code="home_phone_service"/>(9:00-19:00)</p>
		 		<p>${requestScope.constant['telephone'] }</p>
		 	</div>
		 	<div class="service-item">
		 		<a href="http://wpa.qq.com/msgrd?v=3&uin=${requestScope.constant['serviceQQ'] }&menu=yes" target="_black">
		 			<img class="service-item-icon" src="/static/front/images/index/home_contact_QQ@2.png" alt="service">
		 		</a>
		 		<p><spring:message code="home_qq"/></p>
		 		<p>51数字资产服务</p>
		 	</div>
		 	<div class="service-item">
		 		<img class="service-item-icon" src="/static/front/images/index/home_contact_wechat@2.png" alt="service">
		 		<p><spring:message code="home_weixin"/> </p>
		 		<p>${requestScope.constant['weixin'] }</p>
		 		<img class="service-item-qr" src="${requestScope.constant['weixinURL'] }" alt="weixin">
		 	</div>
		 	<div class="service-item">
		 		<img class="service-item-icon" src="/static/front/images/index/home_contact_weibo@2.png" alt="service">
		 		<p><spring:message code="home_weibo"/></p>
		 		<p>@${requestScope.constant['weiboName'] }</p>
		 		<img class="service-item-qr" alt="weibo" src="${requestScope.constant['weiboImage'] }">
		 	</div>
		 	<div class="service-item">
		 		<a href="mailto:${requestScope.constant['email'] }">
		 			<img class="service-item-icon" src="/static/front/images/index/home_contact_email@2.png" alt="service">
		 		</a>
		 		<p><spring:message code="home_email"/></p>
		 		<p>${requestScope.constant['email'] }</p>
		 	</div>
		 </div>
	</div>


			

	
	
	
	<div class="function text-right">
		<img style="width:100px" src="/static/front/images/niuwa.gif" alt="niuwa">
		<ul class="help_list">
			<li>
				<a id="contentxiaokui" href="javascript:void(0);" onclick="js_method()">
					<span style="width: 0px;">在线客服</span>
					<i class="help_udesk"></i>
				</a>
			</li>
			<li>
				<a href="/question/question.html" target="_blank">
					<span style="width: 0px;">在线问答</span>
					<i class="help_fixed"></i>
				</a>
			</li>
			<li class="online_Service" action="udesk">
				<a href="http://wpa.qq.com/msgrd?v=3&uin=${requestScope.constant['serviceQQ'] }&menu=yes">
					<span style="width: 0px;">QQ交流</span>
					<i class="help_qq"></i>
				</a>
			</li>
			<li>
				<span style="width: 0px;">${requestScope.constant['telephone'] }</span>
				<i class="tel_fixed"></i>
			</li>
		</ul>
	</div>
	
	<div class="modal modal-custom fade" id="kycDialog" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark" style="height: 565px; width: 575px;"></div>
			<div class="modal-content" style="margin-top: 0px;">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">全新KYC客户识别系统上线！</span>
				</div>
				<div class="modal-body form-horizontal">
					<div class="kyc-rule">
						为响应国家反洗钱等政策的要求，减少资金安全风险，51数字资产新增KYC客户识别系统。认证包括KYC1-KYC2等步骤，要求每个用户提供有效身份信息，如身份证、银行卡、手机号码、本人视频认证等。
						<br>
						所有用户都须通过认证才可享有提现、提币、交易等权益功能。感谢您的理解与支持，51数字资产将竭诚为您的交易保驾护航！
						<br>
						有任何疑问，请拨打客服电话 <a href="javascript:;">400-900-6615</a> 或在线咨询。
						<div>
							<img src="${oss_url}/static/front/images/index/pub_img_teamheader@2x.png" alt="">
							51数字资产运营团队
						</div>
					</div>
				</div>
				<a class="kyc-btn text-center" href="/user/realCertification.html">立即认证</a>
			</div>
		</div>
	</div>

	<!--底部开始-->
	<%@include file="comm/footer.jsp" %>
	<input type="hidden" id="errormsg" value= />
	<input type="hidden" id="kyclevel" value="${kyclevel}" />
	<input type="hidden" id="islogin" value="${sessionScope.login_user!=null}" />
	<input type="hidden" id="userName" value="${login_user.floginName}" />


	<script>
        (function(m, ei, q, i, a, j, s) {
            m[a] = m[a] || function() {
                    (m[a].a = m[a].a || []).push(arguments)
                };
            j = ei.createElement(q),
                s = ei.getElementsByTagName(q)[0];
            j.async = true;
            j.charset = 'UTF-8';
            j.src = i + '?v=' + new Date().getUTCDate();
            s.parentNode.insertBefore(j, s);
        })(window, document, 'script', '//eco-api.meiqia.com/dist/meiqia.js', '_MEIQIA');
        _MEIQIA('entId', 8913);
        _MEIQIA('withoutBtn', true);

        function js_method() {
            _MEIQIA._SHOWPANEL();
        }

	</script>
<script type="text/javascript" src="${oss_url}/static/front/js/index/index.js?r=<%=new java.util.Date().getTime() %>"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.flot.js"></script>
</body>
</html>