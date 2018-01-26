<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>
<script>
	var _hmt = _hmt || [];
	(function() {
		var hm = document.createElement("script");
		hm.src = "https://hm.baidu.com/hm.js?bc2df6bdc3696056c26c7406a98b6570";
		var s = document.getElementsByTagName("script")[0];
		s.parentNode.insertBefore(hm, s);
	})();
</script>
<style>
.nav .open>a, .nav .open>a:hover, .nav .open>a:focus {
    background-color: transparent !important;
    border-color: #c83935;
}
</style>
<div id="allheader" class="container-full ${isIndex ==1?'home':'' } allheader">
	<div class="header-mark"></div>
	<div class="header-mark-bot"></div>
	<div class="container-full header clearfix ">
		<div class="container-full top header-nav">
			<div class="container padding-right-clear">
				<div class="col-xs-12 padding-right-clear">
					<div class="navbar">
						<div class="navbar-header navbar-default">
							<a class="navbar-brand" href="/"> <img alt="${requestScope.constant['webName']}" src="${isIndex == 1?requestScope.constant['logoImage']:requestScope.constant['logoImage2']}">
							</a>
						</div>
						
						<div class="collapse navbar-collapse navbar-right">
							<ul class="nav navbar-nav ">
								<li class="">
									<a href="/?requestType=1"><spring:message code="home"/>
										<div class="relative">
											<span class="line"></span>
										</div>
									</a>
								</li>		
								<!-- 一级菜单 -->
								<c:if test="${(selectMenu != 'trade' &&selectMenu != 'account' && selectMenu != 'user' && selectMenu != 'financial') || sessionScope.login_user == null}">
									<li class="">
										<a href="/trade/coin.html"><spring:message code="trade_center"/>
											<div class="relative">
												<span class="line"></span>
											</div>
										</a>
									</li>
									<li class="">
										<a href="/market.html"><spring:message code="quotes_center"/>
										</a>
										<div class="subNav text-center">
											<c:forEach items="${requestScope.constant['activetradeMapping'] }" var="v">
												<div><a href="/market.html?symbol=${v.fid}">${v.fvirtualcointypeByFvirtualcointype2.fname}</a></div>
											</c:forEach>
										</div>
									</li>
									<li class="">
										<a href="/service/ourService.html"><spring:message code="farticle_center"/>
										</a>
										<div class="subNav text-center">
											<c:forEach items="${requestScope.constant['articletypes'] }" var="v">
												<div><a href="/service/ourService.html?id=${v.fid}">${v.fname}</a></div>
											</c:forEach>
										</div>
									</li>
									<li>
										<a href="support/index.html"><spring:message code="support_tech"/>
											<div class="relative">
											<span class="line"></span>
										</div>
										</a>
									</li>
									<li class="">
										<a href="/about/help.html"><spring:message code="help_center"/>
											<div class="relative">
												<span class="line"></span>
											</div>
										</a>
									</li>
								</c:if>
								<!-- 二级菜单 -->
								<c:if test="${(selectMenu == 'trade' || selectMenu == 'account' || selectMenu == 'user' || selectMenu == 'financial') && sessionScope.login_user != null}">
									<li class="">
										<a href="/trade/coin.html"><spring:message code="spot_trade"/>
										</a>
									</li>
									<li class="">
										<a href="/market_new.html"><spring:message code="k_trade"/>
										</a>
										<div class="subNav text-center">
											<c:forEach items="${requestScope.constant['activetradeMapping'] }" var="v">
												<div><a href="/market_new.html?symbol=${v.fid}">${v.fvirtualcointypeByFvirtualcointype2.fname}</a></div>
											</c:forEach>
										</div>
									</li>
									<li class="">
										<a href="/financial/index.html"><spring:message code="financial_center"/>
										</a>
									</li>
									<li class="">
										<a href="/user/security.html"><spring:message code="personal_center"/>
										</a>
									</li>
								</c:if>
							</ul>
							<div class="login-register clearfix">
								<div class="lr-mark"></div>
								<div class="lr-con">
									<c:if test="${sessionScope.login_user == null }">
										<a href="/user/login.html" class="lr-item"><spring:message code="signIn"/></a>
										<span class="lr-item padding-left-clear padding-right-clear">|</span>
										<a href="/user/register.html" class="lr-item"><spring:message code="regist"/></a>
									</c:if>
									<c:if test="${sessionScope.login_user != null }">
									<div class="">
											<span class="lr-item user-slide"> <a href="/financial/index.html">hi，${login_user.fnickName}</a> <span class="caret"></span> <span class="slide-box">

													<div class="slide-box-top">
														<span class="slide-levelBox"> <span class="slide_vip vip${login_user.fscore.flevel }"></span> 
																<span class="slide_vip-hint"><spring:message code="user_member"/></span>
															 
														</span> <span class="slide-con"> <span class="loginname">${login_user.floginName}</span> <span class="uid"> UID:${login_user.fid} </span>
														</span> <a href="/user/security.html" class="btn btn-link pull-right slide-link"><spring:message code="sys_set"/>&gt;&gt;</a>
													</div>
													<div class="slide-box-con">
														<div class="slide-box-assets">
															<span> <spring:message code="east_taotal_amount1"/>： <span id="headertotalasset" class="text-danger">￥<ex:DoubleCut value="${totalCapitalTrade }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2" /></span>
															</span> 
														</div>
														<div class="assets-detail">
															<ul class="first title clearfix">
																<li class="col-xs-3 padding-left-clear"><spring:message code="cncy_name_desc"/></li>
																<li class="col-xs-3 text-center"><spring:message code="available"/></li>
																<li class="col-xs-3 text-center"><spring:message code="frozen"/></li>
															</ul>
																
															<ul id="headercny0" class="clearfix">
																<li class="col-xs-3 padding-left-clear"><spring:message code="rmb_desc"/></li>
																<li class="col-xs-3 text-center text-danger">
																	<ex:DoubleCut value="${fwallet.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
																</li>
																<li class="col-xs-3 text-center">
																	<ex:DoubleCut value="${fwallet.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="2"/>
																</li>
															</ul>
															<c:forEach items="${fvirtualwallets }" var="v" varStatus="vs" begin="0">
																<ul id="headercoin${v.value.fvirtualcointype.fid }" class="clearfix first">
																	<li class="col-xs-3 padding-left-clear">
																		<c:if test="${requestScope.locale_language=='zh_CN'}">${v.value.fvirtualcointype.fname }</c:if>
																		<c:if test="${requestScope.locale_language=='en'}">${v.value.fvirtualcointype.fShortName }</c:if>
																	</li>
																	<li class="col-xs-3 text-center text-danger">
																		<ex:DoubleCut value="${v.value.ftotal }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
																	</li>
																	<li class="col-xs-3 text-center">
																		<ex:DoubleCut value="${v.value.ffrozen }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/>
																	</li>
																</ul>
															</c:forEach>

														</div>
														<div class="assets-btn">
															<a href="/account/rechargeCny.html" class="btn btn-danger btn-block"><spring:message code="regc_desc16"/></a>
															 <a href="/account/withdrawCny.html" class="btn btn-success btn-block"><spring:message code="with_desc1"/></a>
														</div>
													</div>
											</span>
											</span> <a href="/user/logout.html" class="lr-item lr-margin"  stype="color:#f99497"> [<spring:message code="log_out"/>]</a>
										</div>
									
									</c:if>
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
<c:if test="${isIndex !=1 }">	
	<div class="container-full notice" style="overflow: hidden;">
		<div class="container text-center" id="newstoplist" style="overflow: hidden; height: 50px; width: auto; color: #ffffff;">
			<div id=newsList>
					<c:forEach items="${requestScope.constant['news']}" var="v">
					<p>
					   <a href="${v.url }" class="notice-item">
							<i class="notice-item-icon"></i>
							${v.ftitle }
						</a>
					</p>
				</c:forEach>
			</div>
		</div>
	</div>
</c:if>
	
</div>
<input type="hidden" id="locale_language" value="${locale_language}">

<script type="text/javascript">
var headerpath = window.location.pathname;
var selectMenu = "${selectMenu}";
var lis = $(".navbar-nav li") ;
console.log(headerpath)
if(headerpath.startWith("/about/")) {
    lis.eq(5).addClass("active") ;
    console.log(lis.eq(5))
}
else if(headerpath.startWith("/support/")){
	lis.eq(4).addClass("active") ;
}
else if(headerpath.startWith("/index.html") || selectMenu.startWith("index")
 || headerpath.startWith("/question/")){
	lis.eq(0).addClass("active") ;
}
else if(headerpath.startWith("/trade/")){
	lis.eq(1).addClass("active") ;
}else if(headerpath.startWith("/financial/")||headerpath.startWith("/account/") || headerpath.startWith("/service/") || headerpath.startWith("/introl/")){
	lis.eq(3).addClass("active") ;
}
else if( headerpath.startWith("/crowd/logs") ||headerpath.startWith("/divide/")
|| headerpath.startWith("/lottery/logs")|| headerpath.startWith("/exchange/index")){
	lis.eq(2).addClass("active") ;

}
else if(headerpath.startWith("/market.html")){
	lis.eq(2).addClass("active") ;
}
else if(headerpath.startWith("/user/")){
	lis.eq(4).addClass("active") ;
}


</script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/language.js"></script>