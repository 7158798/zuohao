<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="include.inc.jsp"%>

<!-- 用戶中心 -->
<div class="col-xs-2 leftmenu">
	<ul class="nav nav-pills nav-stacked">
		
		<span class="leftmenu-title"> <i class="lefticon user"></i>
			<spring:message code="lmenu_user_center"/> </span>
		<li class=""><a href="/user/security.html"> <spring:message code="lmenu_base_info"/> </a>
		</li>
		<li><a href="/user/myMember.html"><spring:message code="lmenu_member_center"/> </a>
		<li class=""><a href="/user/userloginlog.html?type=1"> <spring:message code="lmenu_login_log"/> </a>
		</li>
		<li><a href="/user/realCertification.html">KYC&nbsp;认证</a>
		</li>
		<li>
			<a href="/user/myintrol.html"> <spring:message code="lmenu_friends_invited"/> </a>
		</li>
	</ul>
</div>

<!-- 财务中心 -->
<div class="col-xs-2 leftmenu">
	<ul class="nav nav-pills nav-stacked">
		<span class="leftmenu-title top">
			<i class="lefticon financial"></i>
			<spring:message code="lmenu_financial"/>
		</span>
		<li class="">
			<a href="/account/rechargeCny.html"> <spring:message code="lmenu_rechargeCny"/> </a>
		</li>
		<li class="">
			<a href="/account/withdrawCny.html"> <spring:message code="lmenu_withdrawCny"/> </a>
		</li>
		<li class="">
			<a href="/financial/index.html"> <spring:message code="lmenu_account_assets"/> </a>
		</li>
		<li class="">
			<a href="/account/record.html"> <spring:message code="lmenu_account_record"/> </a>
		</li>
		<li class="">
			<a href="/financial/accountbank.html"> <spring:message code="lmenu_asset_account"/> </a>
		</li>
		<li class="">
			<a href="/financial/assetsrecord.html"> <spring:message code="lmenu_assets_record"/> </a>
		</li>
	</ul>
</div>

<!-- 交易中心 -->
<div class="col-xs-2 leftmenu">
	<ul class="nav nav-pills nav-stacked ">
	
		<c:forEach var="v" varStatus="vs" items="${ftrademappings }">
			<span class="leftmenu-title leftmenu-folding top"
					data-folding="trademenu${v.fid }"> <i class="lefticon"
					style="background: url('${v.fvirtualcointypeByFvirtualcointype2.furl }') no-repeat 0 0;background-size:100% 100%;"></i>
					${v.fvirtualcointypeByFvirtualcointype2.fShortName } <spring:message code="lmenu_trade"/> </span>
				<li class=" trademenu${v.fid }" style="display:${ftrademapping.fid!=v.fid?'none':''}"><a
					href="/trade/coin.html?coinType=${v.fid }&tradeType=0"> <spring:message code="lmenu_buy_sell"/> </a></li>
				<li class=" trademenu${v.fid }" style="display:${ftrademapping.fid!=v.fid?'none':''}"><a
					href="/trade/entrust.html?symbol=${v.fid }&status=0"> <spring:message code="lmenu_entrust"/> </a></li>
				<li class=" trademenu${v.fid }" style="display:${ftrademapping.fid!=v.fid?'none':''}"><a
					href="/trade/entrust.html?symbol=${v.fid }&status=1"> <spring:message code="lmenu_entrust_record"/> </a></li>
		</c:forEach>
	</ul>
</div>

<script type="text/javascript">
var leftpath = window.location.pathname;
	var path = window.location.href.replace('http://'+window.location.host,'') ;
	if(leftpath.startWith("/trade/")){//交易中心特殊处理

	var count = 0 ;
	var isshow = false;
	$(".leftmenu").each(function(){
			var flag = false ;
			$(this).find("a").each(function(){
				if(path.indexOf($(this).attr("href")) != -1){
					$(this).parent().addClass("active") ;
					flag = true ;
					count++ ;
				}
			}) ;
			if(flag == false ){
				$(this).remove();
			}
	}) ;

	//if(count==0){
//		$(".leftmenu").each(function(){
//				var flag = false ;
//				$(this).find("a").each(function(){
//					if($(this).attr("href").startWith(leftpath)){
//						if(isshow == false ){
//							$(this).parent().addClass("active") ;
//						}
//						flag = true ;
//						isshow = true ;
//						count++ ;
//					}
//				}) ;
//				if(flag == false ){
//					$(this).remove();
//				}
//		});
	//}
//				
				
	}else{//除交易中心的其他菜单
	var left = "${leftMenu}";
		$(".leftmenu").each(function(){
				
				var flag = false ;
				$(this).find("a").each(function(){
					if($(this).attr("href").indexOf(leftpath) != -1){
						$(this).parent().addClass("active") ;
						flag = true ;
					}else{
					   if(($(this).attr("href").indexOf("/account/rechargeCny") != -1
					    || $(this).attr("href").indexOf("/account/proxyCode") != -1
					    || $(this).attr("href").indexOf("/account/payCode") != -1)
					     && left == "recharge"){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   }else  if($(this).attr("href").indexOf("/account/withdrawCny") != -1 && left == "withdraw"){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   }else  if($(this).attr("href").indexOf("/financial/accountbank") != -1 && left == "accountAdd"){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   } else  if($(this).attr("href").indexOf("/divide/") != -1  && left == "divide" ){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   } else  if($(this).attr("href").indexOf("/crowd/") != -1  && left == "mylogs" ){
					      $(this).parent().addClass("active") ;
					      flag = true ;
					   }
					}
				}) ;
				if(flag == false){
					$(this).remove();
				}
		}) ;
	}
</script>