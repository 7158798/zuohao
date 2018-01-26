<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>





 



<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.user.jsp"></jsp:include>
<link rel="stylesheet" href="${oss_url}/static/front/css/user/kyc.css" type="text/css"></link>
</head>
<body>
	
<jsp:include page="../comm/header.jsp"></jsp:include>

<div class="container-full">
    <div class="container displayFlex">

        <jsp:include page="../comm/left_menu.jsp"></jsp:include>

        <div class="col-xs-10 padding-right-clear">
            <div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
                <div class="col-xs-12 rightarea-con">
                    <div class="wrap">
                        <div class="section">
                            <div class="top">
                                <p>【温馨提示】为了全方位保障用户资产安全，同时响应国家反洗钱政策，请您配合完成相关验证。
                                </p>
                            </div>
                            <!-- kyc1验证 -->
                            <div class="kyc_01">
                                <div class="title">
                                    <span class="s1">KYC1认证</span>
                                    <c:if test="${fuser.kyclevel < 2}">
                                        <span class="s2">(未认证)</span>
                                    </c:if>
                                    <c:if test="${fuser.kyclevel >= 2}">
                                        <span class="s2" style="color:#0174fa">(已认证)</span>
                                    </c:if>
                                    <a target="_blank" href="/about/index.html?id=54&type=1" class="to_other">什么是KYC1认证？</a>
                                </div>
                                <div class="user_set">
                                    <div class="lead">
                                        <p>您只需要进行<span class="s1">真实身份认证</span>和<span class="s2">绑定银行卡</span>即可完成KYC1认证</p>
                                    </div>
                                    <div class="nav_btn">
                                        <div class="id_card all_card">
                                            <p class="p1"><span><em>Step1</em>身份认证</span></p>
                                            <p class="p3">
                                                <c:if test="${fuser.kyclevel > 0}">
                                                    ${fuser.frealName} : ${fuser.fidentityNo_s}
                                                </c:if>
                                            </p>

                                            <p class="p2">
                                                <c:if test="${fuser.kyclevel == 0}">
                                                    <span class="step1-btn" data-toggle="modal" data-target="#bindrealinfo">点击认证</span>
                                                </c:if>
                                                <c:if test="${fuser.kyclevel != 0}">
                                                    <span class="step1-btn disabled">已认证</span>
                                                </c:if>
                                            </p>
                                        </div>
                                        <div class="bank_card all_card">
                                            <p class="p1"><span><em>Step2</em>认证银行卡</span></p>
                                            <p class="p3">
                                                <c:if test="${fuser.kyclevel > 1}">
                                                    ${fvalidatekyc.bankName}： ${fvalidatekyc.bankNumber_s}
                                                </c:if>
                                            </p>
                                            <p class="p2">
                                                <c:choose>
                                                    <c:when test="${fuser.kyclevel == 0}">
                                                        <span class="step2-btn disabled">点击认证</span>
                                                    </c:when>
                                                    <c:when test="${fuser.kyclevel == 1}">
                                                        <span class="step2-btn" data-toggle="modal" data-target="#addBankDialog">点击认证</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="step2-btn disabled">已认证</span>
                                                    </c:otherwise>
                                                </c:choose>                                                   
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- kyc2验证 -->
                            <div class="kyc_02">
                                <div class="title">
                                    <span class="s1">KYC2认证</span>
                                    <span class="s2">(进行此认证前请务必完成KYC1认证)</span>  
                                    <a target="_blank" href="/about/index.html?id=54&type=1" class="to_other">什么是KYC2认证？</a>
                                </div>
                                <div class="step_all">
                                    <div class="left">
                                        <img src="${oss_url}/static/front/images/user/step-1_icon_default.png" alt="">
                                        <p>Step 1 本人身份证照片认证</p>
                                    </div>
                                    <div class="right">
                                        <img src="${oss_url}/static/front/images/user/step-1_icon_default.png" alt="">
                                        <p>Step 2 本人视频认证</p>
                                    </div>
                                    <p class="zhu">*正常审核时间为一个工作日，如有问题请 <a href="javascript:;" onclick="js_method()">联系客服</a></p>
                                    <c:if test="${fuser.kyclevel == 3}">
                                        <p class="text-left" style="color:#0174fa;">审核中</p>
                                    </c:if>
                                    <c:if test="${fuser.kyclevel == 5}">
                                        <p class="text-left" style="color:red;">审核未通过，理由：${reject_info}</p>
                                    </c:if>
                                </div>
                                <!-- kyc2验证第一步 -->
                            <c:if test="${fuser.kyclevel != 3}">
                                <div class="step_01">
                                    <div class="sub_title">
                                        <span class="s1">Step 1</span>
                                        <span class="">本人身份证照片认证</span>
                                    </div>
                                    <p class="mind">请按照要求上传您身份证的正面照片，仅支持<span>jpg、bmp、png</span>格式，大小限制<span>5MB以内</span>。</p>
                                    <div class="up_img_01 all_up">
                                        <p class="left">本人身份证正面照片</p>
                                        <div class="user_up">
                                            <div class="work_info">
                                                <label for="pic1" class="s1">点击上传</label>
                                                <input id="pic1" type="file" onchange="uploadImg1()">
                                                <input id="pic1Url" type="hidden">
                                                <span class="s2"> <span>未选择文件</span>  <em class="pic1name"></em></span>
                                                <span class="f2">示例</span>
                                            </div>
                                            <div class="imgs">
                                                <div class="pic1show"></div>
                                                <div class="exemple"><img src="${oss_url}/static/front/images/user/zheng_image.png" alt=""></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="up_img_02 all_up">
                                        <p class="left">本人身份证反面照片</p>
                                        <div class="user_up">
                                            <div class="work_info">
                                                <label for="pic2" class="s1">点击上传</label>
                                                <input id="pic2" type="file" onchange="uploadImg2()">
                                                <input type="hidden" id="pic2Url">
								                <span class="s2"> <span>未选择文件</span>  <em class="pic2name"></em></span>
                                                <span class="f2">示例</span>
                                            </div>
                                            <div class="imgs">
                                                <div class="pic2show"></div>
                                                <div class="exemple"><img src="${oss_url}/static/front/images/user/fan_image.png" alt=""></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="up_img_03 all_up">
                                        <p class="left">手持本人身份证照片</p>
                                        <div class="user_up">
                                            <div class="work_info">
                                                <label for="pic3" class="s1">点击上传</label>
                                                <input id="pic3" type="file" onchange="uploadImg3()">
                                                <input type="hidden" id="pic3Url">
								                <span class="s2"> <span>未选择文件</span>  <em class="pic3name"></em></span>
                                                <span class="f2">示例</span>
                                            </div>
                                            <div class="imgs">
                                                <div class="pic3show"></div>
                                                <div class="exemple"><img src="${oss_url}/static/front/images/user/chi_image.png" alt=""></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- kyc2验证第二步 -->
                                <div class="step_02">
                                    <div class="sub_title">
                                        <span class="s1">Step 2</span>
                                        <span class="">本人视频认证</span>
                                    </div>
                                    <p class="mind">请您打开电脑摄像头和麦克风，点击"开始录制"按钮后进行录制（15秒以内），录制期间需要<span>个人出镜</span>，并<span>读出相应文案</span>，完成后点击"结束录制"，系统会自动进行保存，请您在确认视频无误后进行提交。</p>
                                    <div class="up_video">
                                        <div class="left">
                                            <div class="video_btn">
                                                <span class="s1">本人视频认证录制</span>
                                                <label for="vedio" class="s2">上传</label>
                                                <span class="vedioTips">已选择 <em style="color:#88abda" class="vedioname"></em></span>
                                                <input type="file" id="vedio"  onchange="uploadVideo()" accept="video/mp4,video/x-m4v,video/*">
                                                <input type="hidden" id="vedioUrl">
                                            </div>
                                            <p class="text-left" >*请确保在您录制过程中清晰读出以下内容：</p>
                                            <p class="text-left" style="color:#ff7b7b">我是***，我的身份证号码后四位是****，我申请通过51数字资产的KYC认证，
                                                本人自愿购买数字货币，所购数字货币不用于其他非法用途，资金来源合法。
                                            </p>
                                            <!--<div class="show_video">
                                                 <video src="/"></video>
                                            </div>-->
                                        </div>
                                        <div class="right">
                                            <p class="tishi">您还可以通过51数字资产APP进行验证。</p>
                                            <div class="download">
                                                <img src="${oss_url}/static/front/images/user/image.png" alt="">
                                                <a target="_blank" href="https://fir.im/51szzc?utm_source=fir&utm_medium=qr">
                                                    <img src="${oss_url}/static/front/images/user/IOS_icon_default.png" alt="">
                                                </a>
                                                <a target="_blank" href="/download.html">
                                                    <img src="${oss_url}/static/front/images/user/Android_icon_default.png" alt="">
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            </div>

                            <c:if test="${fuser.kyclevel != 3}">
                                <div class="to_up">
                                    <p id="error" class="text-danger"></p>
                                    <c:choose>
                                        <c:when test="${fuser.kyclevel < 2}">
                                            <span class="s2">提交</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="s1">提交</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:if>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 实名认证 -->
	<div class="modal modal-custom fade" id="bindrealinfo" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">KYC1认证</span>
                    <div class="KYC1-types text-center">
                        <span class="KYC1-type active">01身份认证</span>
                        <span class="KYC1-type ">02认证银行卡</span>
                    </div>
				</div>
				<div class="modal-body form-horizontal">
					<div class="form-group ">
						<label for="bindrealinfo-realname" class="col-xs-3 control-label"><spring:message code="real_name"/></label>
						<div class="col-xs-6">
							<input id="bindrealinfo-realname" class="form-control" placeholder="请填写您的真实姓名" type="text" autocomplete="off">
							<span id="bindrealinfo-realname-errortips " class="text-danger certificationinfocolor">*<spring:message code="veri_info_desc"/></span>
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-areaCode" class="col-xs-3 control-label"><spring:message code="regional"/></label>
						<div class="col-xs-6">
							<select class="form-control" id="bindrealinfo-address">
								<option value="86" selected>中国大陆(China)</option>
							</select>
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-areaCode" class="col-xs-3 control-label"><spring:message code="card_type"/></label>
						<div class="col-xs-6">
							<select class="form-control" id="bindrealinfo-identitytype">
								<option value="0"><spring:message code="id_card"/></option>
							</select>
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-imgcode" class="col-xs-3 control-label"><spring:message code="card_number"/></label>
						<div class="col-xs-6">
							<input  id="bindrealinfo-identityno" class="form-control" type="text" autocomplete="off">
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-ckinfo" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<div class="checkbox disabled">
								<label id="bindrealinfo-ckinfolb">
									<input type="checkbox" value="" checked id="bindrealinfo-ckinfo">
									<spring:message code="submit_desc"/>
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="bindrealinfo-errortips" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<span id="certificationinfo-errortips" class="text-danger"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="bindemail-Btn" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<button id="bindrealinfo-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="addBankDialog" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark" style="height: 565px; width: 575px;"></div>
			<div class="modal-content" style="margin-top: 0px;">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<span class="modal-title" id="exampleModalLabel">KYC1认证</span>
                    <div class="KYC1-types text-center">
                        <span class="KYC1-type">01身份认证</span>
                        <span class="KYC1-type active">02认证银行卡</span>
                    </div>
				</div>
				<div class="modal-body form-horizontal">

					<div class="form-group ">
						<label for="payeeAddr" class="col-xs-3 control-label">真实姓名</label>
						<div class="col-xs-8">
							<input id="payeeAddr" class="form-control" type="text" autocomplete="off" readonly value="${fuser.frealName }" />
						</div>
					</div>
					<div class="form-group ">
						<label for="openBankTypeAddr" class="col-xs-3 control-label"><spring:message code="bank_name"/></label>
						<div class="col-xs-8">
							<input id="openBankTypeAddr" class="form-control" type="text" ></div>
					</div>
					<div class="form-group ">
						<label for="withdrawAccountAddr" class="col-xs-3 control-label"><spring:message code="bank_account"/></label>
						<div class="col-xs-8">
							<input id="withdrawAccountAddr" class="form-control" type="" text="">			
							<div class="help-block text-danger tips"><spring:message code="regc_desc1"/></div>
						</div>
					</div>
				
					<div id="prov_city" class="form-group ">
						<label for="prov" class="col-xs-3 control-label"><spring:message code="bank_account_address"/></label>
						<div class="col-xs-8 ">
							<div class="col-xs-4 padding-right-clear padding-left-clear margin-bottom-15">
								<select id="prov" class="form-control"></select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="city" class="form-control"></select>
							</div>
							<div class="col-xs-4 padding-right-clear margin-bottom-15">
								<select id="dist" class="form-control prov"></select>
							</div>
							<div class="col-xs-12 padding-right-clear padding-left-clear">
								<input id="address" class="form-control" type="text" autocomplete="off" placeholder="请输入开户支行名称"/>
							</div>
						</div>
					</div>
					<div class="form-group ">
						<label for="dialogPhone" class="col-xs-3 control-label">银行预留号码</label>
						<div class="col-xs-8">
							<input id="dialogPhone" class="form-control" type="text"  value="${fuser.ftelephone}">
						</div>
					</div>
					<div class="form-group">
						<label for="addressPhoneCode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
						<div class="col-xs-8">
							<input id="addressPhoneCode" class="form-control" type="text" autocomplete="off">			
							<button id="bindsendmessage" data-msgtype="10" data-tipsid="binderrortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
						</div>
					</div>

				<div class="form-group">
					<label for="binderrortips" class="col-xs-3 control-label"></label>
					<div class="col-xs-8">
						<span id="binderrortips" class="text-danger"></span>
					</div>
				</div>
				<div class="form-group">
					<label for="withdrawCnyAddrBtn" class="col-xs-3 control-label"></label>
					<div class="col-xs-8">
						<button id="withdrawCnyAddrBtn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
<script type="text/javascript">
	var BANKLIST = [];
	var userInfo = {};

	<c:forEach items="${bankTypes }" var="v">
		BANKLIST.push({data: '${v.key}',value:'${v.value}'});
	</c:forEach>
</script>
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

	<jsp:include page="../comm/footer.jsp"></jsp:include>
    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
    <script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="/static/front/js/finance/city.min.js"></script>
	<script type="text/javascript" src="/static/front/js/finance/jquery.cityselect.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/kyc.js"></script>
</body>
</html>
