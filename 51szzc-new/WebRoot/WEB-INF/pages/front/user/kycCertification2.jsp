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
<link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css" type="text/css"></link>
</head>
<body>
	
<jsp:include page="../comm/header.jsp"></jsp:include>

<div class="container-full">
    <div class="container displayFlex">

        <jsp:include page="../comm/left_menu.jsp"></jsp:include>

        <div class="col-xs-10 padding-right-clear">
            <div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
                <div class="col-xs-12 rightarea-con">
                    <div class="user-realCertification-top-icon ">
<h3>您已成功完成KYC1、KYC2全部认证</h3>
</div>
<h3>
    <spring:message code="real_certification_info" />
</h3>
<table class="table table-user ">
    <tbody>
        <tr>
            <td class="col-xs-3 user-tr">
                <i class="iconlist userface"></i>
                <spring:message code="real_name" />
            </td>
            <td colspan="" class="col-xs-1 user-tr">${fuser.frealName }</td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
		<tr style="border-top:1px solid #d8d5d5">
            <td class="col-xs-3 user-tr">
                <i class="iconlist userid"></i>
                证件类型
            </td>
            <td colspan="" class="col-xs-1 user-tr">身份证</td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
        <tr >
            <td class="col-xs-3 user-tr">
                <i class="iconlist userid"></i>
                <spring:message code="card_number" />
            </td>
            <td colspan="" class="col-xs-1 user-tr">${fuser.fidentityNo_s }</td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
		 <tr>
            <td class="col-xs-3 user-tr">
                <i class="iconlist userface"></i>
                绑定银行名称
            </td>
            <td colspan="" class="col-xs-1 user-tr">${bankName}</td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
		<tr>
            <td class="col-xs-3 user-tr">
                <i class="iconlist userface"></i>
                绑定银行卡号
            </td>
            <td colspan="" class="col-xs-1 user-tr">${bankAccount}</td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
		<tr style="border-top:1px dashed #333">
            <td class="col-xs-3 user-tr">
                <i class="iconlist userface"></i>
                 身份证正、反面照片认证
            </td>
            <td colspan="" class="col-xs-1 user-tr"><span style="color:#7dabff">已认证</span></td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
		<tr>
            <td class="col-xs-3 user-tr">
                <i class="iconlist userface"></i>
                 手持身份证照片认证
            </td>
            <td colspan="" class="col-xs-1 user-tr"><span style="color:#7dabff">已认证</span></td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
		<tr>
            <td class="col-xs-3 user-tr">
                <i class="iconlist userface"></i>
                 本人视频语音认证
            </td>
            <td colspan="" class="col-xs-1 user-tr"><span style="color:#7dabff">已认证</span></td>
            <td class="col-xs-7 user-tr"></td>
            <td class="col-xs-1 user-tr"></td>
        </tr>
    </tbody>
</table>
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
							<input id="bindrealinfo-realname" class="form-control" placeholder="${fill_real_name}" type="text" autocomplete="off">
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
							<input id="bindrealinfo-identityno" class="form-control" type="text" autocomplete="off">
						</div>
					</div>
					<div class="form-group ">
						<label for="bindrealinfo-ckinfo" class="col-xs-3 control-label"></label>
						<div class="col-xs-6">
							<div class="checkbox disabled">
								<label id="bindrealinfo-ckinfolb">
									<input type="checkbox" value="" id="bindrealinfo-ckinfo">
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
						<label for="payeeAddr" class="col-xs-3 control-label"><spring:message code="name_title"/></label>
						<div class="col-xs-8">
							<input id="payeeAddr" class="form-control" type="text" autocomplete="off" value="${fuser.frealName }" readonly="readonly">			
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
								<input id="address" class="form-control" type="text" autocomplete="off" placeholder="${bank_account_address1}"/>
							</div>
						</div>
					</div>
					<div class="form-group ">
						<label for="dialogPhone" class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
						<div class="col-xs-8">
							<input id="dialogPhone" class="form-control" type="text" <c:if test="${not empty fuser.ftelephone}">readonly</c:if> value="${fuser.ftelephone}">
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

	<jsp:include page="../comm/footer.jsp"></jsp:include>
    <script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
        <script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
	<script type="text/javascript" src="/static/front/js/finance/city.min.js"></script>
	<script type="text/javascript" src="/static/front/js/finance/jquery.cityselect.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
	<script type="text/javascript" src="${oss_url}/static/front/js/user/kyc.js"></script>
</body>
</html>
