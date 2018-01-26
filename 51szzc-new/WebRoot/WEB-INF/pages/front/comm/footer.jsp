<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="include.inc.jsp"%>

<div id="allFooter" class="container-full footer" style="position: static;">
	<div class="container-full footer-top">
		<div class="text-center">
		
			

			
			<div class="footer-partners">
				<div class="footer-title">
					<h1>
						<spring:message code="partner"/>
					</h1>
					<p>partners</p>
				</div>
				<div class="container">
					<a href="http://www.bitecoin.com/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_bitecoin.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_bitecoin_sel.png" class="footer-img--active">
					</a>
					<a href="http://www.btc798.com/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_btchome.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_btchome_sel.png" class="footer-img--active">
					</a>
					<a href="http://www.bitcoin86.com/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_btczx.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_btczx_sel.png" class="footer-img--active">
					</a>
					<a href="https://www.btc123.com/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_btc123.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_btc123_sel.png" class="footer-img--active">
					</a>
					<a href="http://www.hao123.com/bitcoin/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_haobit.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_haobit_sel.png" class="footer-img--active">
					</a>
					<a href="http://www.8btc.com/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_bbt.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_bbt_sel.png" class="footer-img--active">
					</a>
					<a href="http://www.taobtc.net/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_tbt.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_tbt_sel.png" class="footer-img--active">
					</a>
					<a href="http://www.cybtc.net/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_cyb.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_cyb_sel.png" class="footer-img--active">
					</a>
					<a href="https://www.51tzgl.com/" target="_blank" class="footer-imgWrap footer-partner">
						<img src="${oss_url}/static/front/images/footer/link_logo_51tzgl.png" class="footer-img">
						<img src="${oss_url}/static/front/images/footer/link_logo_51tzgl_sel.png" class="footer-img--active">
					</a>
				</div>
			</div>
			
			<div class="footer-left-bot">
				<div class="container">
					<a target="_blank" href="/about/index.html?id=1"><spring:message code="about_us"/></a>
					<span class="plist">|</span>
					<%--<a target="_blank" href="/about/index.html?id=1">使用帮助</a>--%>
					<%--<span class="plist">|</span>--%>
					<%--<a target="_blank" href="/about/index.html?id=52">上币申请</a>--%>
					<%--<span class="plist">|</span>--%>
					<a target="_blank" href="/about/index.html?id=21"><spring:message code="wallet_down"/></a>
					<span class="plist">|</span>
					<a target="_blank" href="/question/question.html"><spring:message code="online_q"/></a>
					<span class="plist">|</span>
					<a target="_blank" href="/about/index.html?id=13"><spring:message code="trade_rule"/></a>
					<span class="plist">|</span>
					<a target="_blank" href="/about/index.html?id=3"><spring:message code="fee_desc"/></a>
					<span class="plist">|</span>
					<a target="_blank" href="/about/index.html?id=5"><spring:message code="user_pact"/></a>
					<span class="plist">|</span>
					<a target="_blank" href="/about/index.html?id=7"><spring:message code="legal_desc"/></a>
					<%--<select class="form-control" name="language" id="language">
						<option value="zh_cn" ${locale_language == 'zh_CN' ? 'selected' : ''}>简体中文</option>
						<option value="en" ${locale_language == 'en' ? 'selected' : ''}>English</option>
					</select>--%>
					<%--<option value="zh_tw" ${locale_language == 'zh_TW' ? 'selected' : ''}>繁体中文</option>--%>
				</div>
				<div class="footer-copyRight">
					<!-- 电子营业执照 -->
					  <a target="_blank" href="https://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=2017050517524166" class="footer-imgWrap">
					 	<img src="${oss_url}/static/front/images/footer/footer_img_zhizhao.png" class="footer-img">
					 	<img src="${oss_url}/static/front/images/footer/footer_img_zhizhao_sel.jpg" class="footer-img--active">
					 </a>

					 <a target="_blank" href="http://webscan.360.cn/index/checkwebsite?url=www.51szzc.com" class="footer-imgWrap">
					 	<img src="${oss_url}/static/front/images/footer/footer_img_360.png" class="footer-img">
					 	<img src="${oss_url}/static/front/images/footer/footer_img_360_sel.png" class="footer-img--active">
					 </a>
					  <!-- <a href="" class="footer-imgWrap">
					 	<img src="${oss_url}/static/front/images/footer/footer_img_kexing.png" class="footer-img">
					 	<img src="${oss_url}/static/front/images/footer/footer_img_kexing_sel.png" class="footer-img--active">
					 </a> -->
					 <span>${requestScope.constant['webinfo'].fcopyRights }</span> <span class="plist">|</span>
					 <%--<span>业务合作： ${requestScope.constant['webinfo'].fsystemMail }</span> <span class="plist">|</span>--%>
					 <span> ${requestScope.constant['webinfo'].fbeianInfo }</span>  <span class="plist">|</span>
					 <span> <spring:message code="market_desc"/> </span>
					 <span class="" style="display: inline-block; text-decoration: none; height: 20px; line-height: 20px;">

					</span>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${oss_url}/static/front/js/plugin/bootstrap.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/layer/layer.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/util.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/comm/comm.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/language/language_cn.js"></script>
<script type="text/javascript">
function weixin(){
var url ="https://open.weixin.qq.com/connect/qrconnect?appid=${requestScope.constant['weixinAppID']}&redirect_uri=${requestScope.constant['fulldomain']}/link/wx/callback.html&response_type=code&scope=snsapi_login#wechat_redirect";
location.href=url;
}

/*$('#language').on('change', function () {

    var type = $(this).val();
    window.location.href = '/language.html?language=' + type;
});*/
</script>

