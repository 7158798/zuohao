<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!doctype html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="../comm/link.inc.user.jsp" %>

    <link rel="stylesheet" href="${oss_url}/static/front/css/user/user.css" type="text/css"></link>
    <link rel="stylesheet" href="${oss_url}/static/front/css/user/member.css" type="text/css">

</head>
<body>

<%@include file="../comm/header.jsp" %>



<div class="container-full">
    <div class="container displayFlex">
        <%@include file="../comm/left_menu.jsp" %>
        <div class="col-xs-10 padding-right-clear">
            <div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
                <div class="col-xs-12 rightarea-con">
                    <div class="user-top-icon">
                        <div class="col-xs-2 text-center">
                            <i class="top-icon"></i>
                        </div>
                        <div class="col-xs-6 padding-left-clear">
                            <div class="h5">
                                <span class="top-title1">${fuser.frealName}</span>
                                <span class="user_levelTop">VIP${fscore.flevel}</span>
                            </div>
                            <div class="h5">
                                <span class="top-vipMember"><spring:message code="current_integral"/>:${fuser.integral }</span>
                            </div>
                            <div class="relative" style="height:40px">
                                 <c:if test="${fscore.flevel < 5}">
                                    <span class="user_levelTop user_levelDiv_span">VIP${fscore.flevel + 1}</span>
                                 </c:if>
                                <div class="user_levelDivOne">
                                    <div class="user_levelDivTwo"> </div>
                                </div>
                                <span class="user_level_tips">
                                    <spring:message code="distance_from_vip"/>${fscore.flevel + 1}<spring:message code="worse"/>${difference}<spring:message code="KLine.Config.minute"/>
                                </span>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-12 taskTop">
                        <div class="taskTopLeft"></div>
                        <div  class="col-xs-2 taskTopMiddle"><spring:message code="upgrade_the_task"/></div>
                        <div class="col-xs-6 taskTopRight"></div>
                    </div>
                    <div class="col-xs-12 padding-clear">
                        <div class="con-items">
                            <div class="content">
                                <div class="content-lt" id="${fscore.integralPhone}">
                                    <i class="mobile"></i>
                                    <c:if test="${fscore.integralPhone == false}">
                                    <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                    </c:if>
                                </div>
                                <div class="content-rt">
                                    <p><spring:message code="phone_ver"/></p>
                                    <p>+500<spring:message code="integral"/></p>
                                    <c:if test="${fscore.integralPhone == true}">
                                        <a class="text-link"><spring:message code="completed"/></a>
                                    </c:if>
                                    <c:if test="${fscore.integralPhone == false}">
                                        <a class="text-link bind" href="javascript:void(0)" data-toggle="modal" data-target="#bindphone"><spring:message code="go_now"/></a>

                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <div class="con-items center">

                            <div class="content">
                                <div class="content-lt">
                                    <i class="emails"></i>
                                    <c:if test="${fscore.integralEmail == false}">
                                        <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                    </c:if>
                                </div>

                                <div class="content-rt">
                                    <p><spring:message code="email_ver"/></p>
                                    <p>+500<spring:message code="integral"/></p>
                                    <c:if test="${fscore.integralEmail == true}">
                                        <a class="text-link"><spring:message code="completed"/></a>
                                    </c:if>
                                    <c:if test="${fscore.integralEmail == false}">
                                        <a class="text-link update" href="javascript:void(0)" data-toggle="modal" data-target="#bindemail"><spring:message code="go_now"/></a>
                                    </c:if>
                                </div>

                            </div>

                        </div>
                        <div class="con-items">
                                <div class="content">
                                    <div class="content-lt">
                                        <i class="identity"></i>
                                        <c:if test="${fscore.integralRealName == false}">
                                            <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                        </c:if>
                                    </div>
                                    <div class="content-rt">
                                        <p><spring:message code="authentication"/></p>
                                        <p>+500<spring:message code="integral"/></p>
                                        <c:if test="${fscore.integralRealName == true}">
                                            <a class="text-link"><spring:message code="completed"/></a>
                                        </c:if>
                                        <c:if test="${fscore.integralRealName == false}">
                                            <a class="text-link bind goValidate" href="javascript:void(0)" data-toggle="modal" data-target="#bindrealinfo" ><spring:message code="lmenu_real_certification"/></a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <div class="con-items">
                                <div class="content">
                                    <div class="content-lt">
                                        <i class="bankcard"></i>
                                        <c:if test="${fscore.integralBankInfo == false}">
                                            <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                        </c:if>
                                    </div>
                                    <div class="content-rt">
                                        <p><spring:message code="bind_bank"/></p>
                                        <p>+500<spring:message code="integral"/></p>
                                        <c:if test="${fscore.integralBankInfo == true}">
                                            <a class="text-link"><spring:message code="completed"/></a>
                                        </c:if>
                                        <c:if test="${fscore.integralBankInfo == false}">
                                        <a data-toggle="modal" data-target="#addBankDialog" class="text-link update"  href="javascript:void(0)"><spring:message code="go_now"/></a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                        <div class="con-items center">
                            <div class="content">
                                <div class="content-lt">
                                    <i class="pwdWord"></i>
                                    <c:if test="${fscore.integralTradePWD == false}">
                                        <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                    </c:if>
                                </div>
                                <div class="content-rt">
                                    <p><spring:message code="set_trade_pwd"/></p>
                                    <p>+500<spring:message code="integral"/></p>
                                    <c:if test="${fscore.integralTradePWD == true}">
                                        <a class="text-link"><spring:message code="completed"/></a>
                                    </c:if>
                                    <c:if test="${fscore.integralTradePWD == false}">
                                        <a class="text-link update" href="javascript:void(0)" data-toggle="modal" data-target="#bindtradepass"><spring:message code="go_now"/></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="con-items">
                            <div class="content">
                                <div class="content-lt">
                                    <i class="googles"></i>
                                    <c:if test="${fscore.integralGA == false}">
                                        <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                    </c:if>
                                </div>
                                <div class="content-rt">
                                    <p><spring:message code="bind_google"/></p>
                                    <p>+500<spring:message code="integral"/></p>
                                    <c:if test="${fscore.integralGA == true}">
                                        <a class="text-link"><spring:message code="completed"/></a>
                                    </c:if>
                                    <c:if test="${fscore.integralGA == false}">
                                        <a class="text-link update" href="javascript:void(0)" data-toggle="modal" data-target="#bindgoogle"><spring:message code="go_now"/></a>
                                    </c:if>
                                </div>
                            </div>

                        </div>
                        <div class="con-items">
                            <div class="content">
                                <div class="content-lt">
                                    <i class="FirstRecharge"></i>
                                    <c:if test="${fscore.integralCapitalIn == false}">
                                        <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                    </c:if>
                                </div>
                                <div class="content-rt">
                                    <p><spring:message code="first_regc"/></p>
                                    <p>+500<spring:message code="integral"/></p>
                                    <c:if test="${fscore.integralCapitalIn == true}">
                                        <a class="text-link"><spring:message code="completed"/></a>
                                    </c:if>
                                    <c:if test="${fscore.integralCapitalIn == false}">
                                        <a class="text-link update" href="/account/rechargeCny.html" ><spring:message code="go_now"/></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <div class="con-items center">
                            <div class="content">
                                <div class="content-lt">
                                    <i class="FirstTrade"></i>
                                    <c:if test="${fscore.integralFirstTrade == false}">
                                        <img class="content-warning" src="${oss_url}/static/front/images/user/Remind_@2X.png" alt="warning">
                                    </c:if>
                                </div>
                                <div class="content-rt">
                                    <p><spring:message code="first_trade"/></p>
                                    <p>+500<spring:message code="integral"/></p>
                                    <c:if test="${fscore.integralFirstTrade == true}">
                                        <a class="text-link"><spring:message code="completed"/></a>
                                    </c:if>
                                    <c:if test="${fscore.integralFirstTrade == false}">
                                        <a class="text-link update" href="/trade/coin.html" ><spring:message code="go_now"/></a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 taskTop">
                        <div class="taskTopLeft"></div>
                        <div  class="col-xs-2 taskTopMiddle"><spring:message code="level_info"/></div>
                        <div class="col-xs-6 taskTopRight"></div>
                    </div>

                    <div class="col-xs-12 padding-clear">
                         <div style="text-align: center">
                            <table class="finacial__table finacial__table--big"  border="1px" >
                                <thead>
                                <tr>
                                    <th><spring:message code="member_level"/></th>
                                    <th><spring:message code="integral"/></th>
                                    <th><spring:message code="with_fee_desc"/></th>
                                    <th><spring:message code="cny_regc"/></th>
                                    <th>BTC/LTC<spring:message code="lmenu_rechargeCny"/></th>
                                    <th><spring:message code="trade_fee"/></th>
                                </tr>
                                </thead>
                                <tbody>
                        <c:forEach items="${gradeList }" var="v" varStatus="vs" begin="0">
                                <tr>
                                    <td>
                                        <span class="user_level">${v.level }</span>
                                    </td>
                                    <td>>=${v.integral }</td>
                                    <td>${v.outFee }</td>
                                    <td>${v.inCNY}</td>
                                    <td>${v.inBtc }</td>
                                    <td> ${ v.trade} </td>
                                </tr>
                        </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="col-xs-12 taskTop">
                        <div class="taskTopLeft"></div>
                        <div  class="col-xs-3 taskTopMiddle"><spring:message code="integral_get_rule"/></div>
                        <div class="col-xs-6 taskTopRight"></div>
                    </div>
                    <div class="col-xs-12 padding-clear">
                        <table class="finacial__table task-table"  border="1px" >
                                <thead>
                                <tr>
                                    <th><spring:message code="integral_type"/></th>
                                    <th><spring:message code="integral"/></th>
                                    <th><spring:message code="integral_desc"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${activityList }" var="v" varStatus="vs" begin="0">
                                    <tr>
                                    <td>
                                        <div style="text-align: left;margin:10px 0 10px 50px">
                                            <img class="task-icon" src="${oss_url}/static/front/images/user/point_${v.url}_icon@2X.png" alt="task">
                                                ${v.integralType }
                                        </div>
                                        </td>
                                    <td>+${v.integral }</td>
                                    <td>${v.remark }</td>
                                </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                    </div>
                    <div class="col-xs-12 taskTop">
                        <div class="taskTopLeft"></div>
                        <div  class="col-xs-3 taskTopMiddle"><spring:message code="integral_get_record"/></div>
                        <div class="col-xs-6 taskTopRight"></div>
                    </div>
                    <form class="search form-inline" style="margin-bottom:20px">
                      <div class="form-group">
                        <label style="margin-right:10px"><spring:message code="time"/></label>
                          <input class="form-control databtn datainput" id="begindate" value="${begindate }" readonly="readonly">

                      </div>
                      -
                      <div class="form-group">
                        <label ></label>
                          <input class="form-control  databtn datainput" id="enddate" value="${enddate }" readonly="readonly">
                      </div>
                      <button type="button" class="btn btn-default" id="searchScore"><spring:message code="query_desc"/></button>
                    </form>
                    <table class="finacial__table task-table"  border="1px" >
                            <thead>
                            <tr>
                                <th><spring:message code="time"/></th>
                                <th><spring:message code="integral"/></th>
                                <th><spring:message code="operation_title"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${integrallist}" var="v">
                                <tr>
                                    <td>${v.createTime }</td>
                                    <td>${v.integral }</td>
                                    <td>${v.operate }</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${fn:length(integrallist)==0 }">
                                <tr>
                                    <td colspan="6" class="no-data-tips" align="center">
														<span>
															<spring:message code="no_integral"/>
														</span>
                                    </td>
                                </tr>
                            </c:if>

                            </tbody>
                        </table>
                </div>
                <c:if test="${!empty(pagin) }">
                    <div class="text-right">
                            ${pagin }
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- 邮箱绑定 -->
<div class="modal modal-custom fade" id="bindemail" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-mark"></div>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <span class="modal-title"><spring:message code="bind_email"/></span>
            </div>
            <div class="modal-body form-horizontal">
                <div class="form-group ">
                    <label for="bindemail-email" class="col-xs-3 control-label"><spring:message code="email_address"/></label>
                    <div class="col-xs-6">
                        <input id="bindemail-email" class="form-control" type="text" autocomplete="off">
                    </div>
                </div>
                <div class="form-group">
                    <label for="bindemail-errortips" class="col-xs-3 control-label"></label>
                    <div class="col-xs-6">
                        <span id="bindemail-errortips" class="text-danger"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bindemail-Btn" class="col-xs-3 control-label"></label>
                    <div class="col-xs-6">
                        <button id="bindemail-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 绑定手机 -->
<div class="modal modal-custom fade" id="bindphone" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-mark"></div>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <span class="modal-title"><spring:message code="bind_phone"/></span>
            </div>
            <div class="modal-body form-horizontal">
                <div class="form-group text-center">
								<span class="modal-info-tips">
									<spring:message code="security_title16"/>
									<span class="text-danger">${loginName}</span>
									<spring:message code="bind_phone"/>
								</span>
                </div>
                <div class="form-group ">
                    <label for="bindphone-areaCode" class="col-xs-3 control-label"><spring:message code="security_title18"/></label>
                    <div class="col-xs-6">
                        <select class="form-control" id="bindphone-areaCode">
                            <option value="+86">中国大陆(China)</option>
                        </select>
                    </div>
                </div>
                <div class="form-group ">
                    <label for="bindphone-newphone" class="col-xs-3 control-label"><spring:message code="phone_number"/></label>
                    <div class="col-xs-6">
                        <span id="bindphone-newphone-areacode" class="btn btn-areacode">+86</span>
                        <input id="bindphone-newphone" class="form-control padding-left-92" type="text" autocomplete="off">
                    </div>
                </div>
                <div class="form-group ">
                    <label for="bindphone-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
                    <div class="col-xs-6">
                        <input id="bindphone-msgcode" class="form-control" type="text" autocomplete="off">
                        <button id="bindphone-sendmessage" data-msgtype="2" data-tipsid="bindphone-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
                    </div>
                </div>

                <c:if test="${isBindGoogle ==true}">
                    <div class="form-group">
                        <label for="bindphone-googlecode" class="col-xs-3 control-label"><spring:message code="send_ver_code"/></label>
                        <div class="col-xs-6">
                            <input id="bindphone-googlecode" class="form-control" type="text" autocomplete="off">
                        </div>
                    </div>
                </c:if>

                <div class="form-group ">
                    <label for="bindphone-imgcode" class="col-xs-3 control-label"><spring:message code="ver_code"/></label>
                    <div class="col-xs-6">
                        <input id="bindphone-imgcode" class="form-control" type="text" autocomplete="off">
                        <img class="btn btn-imgcode" src="/servlet/ValidateImageServlet?r=<%=new java.util.Date().getTime() %>"></img>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bindphone-errortips" class="col-xs-3 control-label"></label>
                    <div class="col-xs-6">
                        <span id="bindphone-errortips" class="text-danger"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bindemail-Btn" class="col-xs-3 control-label"></label>
                    <div class="col-xs-6">
                        <button id="bindphone-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
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
                <span class="modal-title"><spring:message code="lmenu_real_certification"/></span>
            </div>
            <div class="modal-body form-horizontal">
                <div class="form-group text-center">
						<span class="modal-info-tips ">
							<span class=" "></span>
                            <!-- 	<span class="text-danger  Certificationtsimg">认证年龄需满18周岁，最高年龄为60周岁</span> -->
						</span>
                </div>
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
                <span class="modal-title" id="exampleModalLabel"><spring:message code="add_bank"/></span>
            </div>
            <div class="modal-body form-horizontal">
                <div class="accountInfo clearfix">
                    <div class="accountInfo-left pull-left">
                        账<br>户<br>信<br>息
                    </div>
                    <div class="accountInfo-right">
                        <p><spring:message code="account_name"/>：<span class="account-name"></span><span class="level-icon">VIP1</span></p>
                        <p><spring:message code="bind_email"/>：
                            <c:choose>
                                <c:when test="${ !empty email}">
                                    ${email}
                                </c:when>
                                <c:otherwise>
                                    <spring:message code="user_no_bind"/>
                                </c:otherwise>
                            </c:choose>
                       <%-- ${email ? email : '未绑定'} --%>
                        </p>
                        <p><spring:message code="card_num"/>：<span class="accountInfo-id"></span></p>
                        <p><spring:message code="bank_card"/>：<spring:message code="wait_bind"/></p>
                    </div>
                </div>
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


<!-- 交易密码设置 -->
<div class="modal modal-custom fade" id="bindtradepass" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-mark"></div>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <span class="modal-title"><spring:message code="set_trade_pwd"/></span>
            </div>
            <div class="modal-body form-horizontal">
                <div class="form-group ">
                    <label for="bindtradepass-newpass" class="col-xs-3 control-label"><spring:message code="new_trade_pwd"/></label>
                    <div class="col-xs-6">
                        <input id="bindtradepass-newpass" class="form-control" type="password" autocomplete="off">
                    </div>
                </div>
                <div class="form-group ">
                    <label for="bindtradepass-confirmpass" class="col-xs-3 control-label"><spring:message code="config_login_pwd"/></label>
                    <div class="col-xs-6">
                        <input id="bindtradepass-confirmpass" class="form-control" type="password" autocomplete="off">
                    </div>
                </div>

                <c:if test="${fuser.fisTelephoneBind}">
                    <div class="form-group">
                        <label for="bindtradepass-msgcode" class="col-xs-3 control-label"><spring:message code="sms_ver_code"/></label>
                        <div class="col-xs-6">
                            <input id="bindtradepass-msgcode" class="form-control" type="text" autocomplete="off">
                            <button id="bindtradepass-sendmessage" data-msgtype="7" data-tipsid="bindtradepass-errortips" class="btn btn-sendmsg"><spring:message code="send_ver_code"/></button>
                        </div>
                    </div>
                </c:if>

                <c:if test="${isBindGoogle }">
                    <div class="form-group">
                        <label for="bindtradepass-googlecode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
                        <div class="col-xs-6">
                            <input id="bindtradepass-googlecode" class="form-control" type="text" autocomplete="off">
                        </div>
                    </div>
                </c:if>

                <div class="form-group">
                    <label for="bindtradepass-errortips" class="col-xs-3 control-label"></label>
                    <div class="col-xs-6">
                        <span id="bindtradepass-errortips" class="text-danger"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bindtradepass-Btn" class="col-xs-3 control-label"></label>
                    <div class="col-xs-6">
                        <button id="bindtradepass-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 谷歌绑定 -->
<div class="modal modal-custom fade" id="bindgoogle" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-mark"></div>
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <span class="modal-title"><spring:message code="bind_google_code"/></span>
            </div>
            <div class="modal-body form-horizontal">
                <div class="form-group ">
                    <div class="col-xs-12 clearfix">
                        <div id="bindgoogle-code" class="form-control border-fff  form-qrcodebox">
                            <div class="col-xs-12 clearfix form-qrcode-quotes form-qrcode-quotess"></div>
                            <%--<div class="form-qrcodebox-cld">--%>
                            <%--<div class="form-qrcode-coded">--%>
                            <%--<div class="form-qrcode-title text-center">--%>
                            <%--<span>下载谷歌验证器</span>--%>
                            <%--</div>--%>
                            <%--<div class="form-qrcode">--%>
                            <%--<img class="form-qrcode-img" src="${requestScope.constant['googleImages'] }"/>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="form-qrcode-tips">--%>
                            <%--<span>--%>
                            <%--若未安装谷歌验证器请--%>
                            <%--<span class="text-danger">扫码下载</span>--%>
                            <%--。--%>
                            <%--</span>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <div class="col-xs-12">
                                <div class="form-qrcode-codec">
                                    <div class="form-qrcode-title text-center">
                                        <span><spring:message code="bind_google_code"/></span>
                                    </div>
                                    <div class="form-qrcode" id="qrcode">
                                    </div>
                                </div>
                                <%--<div class="form-qrcode-tips">--%>
                                <%--<span>--%>
                                <%--请扫码或手工输入密钥，将手机上生成的--%>
                                <%--<span class="text-danger">动态验证码</span>--%>
                                <%--填到下边输入框。--%>
                                <%--</span>--%>
                                <%--</div>--%>
                            </div>
                            <div class="col-xs-12 clearfix form-qrcode-quotes form-qrcode-quotese"></div>
                        </div>
                    </div>
                </div>
                <div class="form-group ">
                    <label for="bindgoogle-key" class="col-xs-3 control-label"><spring:message code="key_desc"/></label>
                    <div class="col-xs-7">
                        <span id="bindgoogle-key" class="form-control border-fff"></span>
                        <input id="bindgoogle-key-hide" type="hidden">
                    </div>
                </div>
                <div class="form-group ">
                    <label for="bindgoogle-device" class="col-xs-3 control-label"><spring:message code="device_name"/></label>
                    <div class="col-xs-7">
                        <span id="bindgoogle-device" class="form-control border-fff">${device_name }</span>
                    </div>
                </div>
                <div class="form-group ">
                    <label for="bindgoogle-topcode" class="col-xs-3 control-label"><spring:message code="google_ver_code"/></label>
                    <div class="col-xs-7">
                        <input id="bindgoogle-topcode" class="form-control" type="text" autocomplete="off">
                    </div>
                </div>
                <div class="form-group text-center">
                    <div class="col-xs-8 col-xs-offset-2"><spring:message code="google_code_desc1"/></div>
                </div>
                <div class="form-group text-center">
                    <div class="col-xs-8 col-xs-offset-2"><spring:message code="google_code_desc2"/></div>
                </div>
                <div class="form-group clearfix">
                    <div class="col-xs-8 col-xs-offset-2"><a class="pull-right btn" target="_blank" href="/about/index.html?id=43&type=9"><spring:message code="look_help"/></a></div>
                </div>
                <div class="form-group">
                    <label for="bindgoogle-errortips" class="col-xs-3 control-label"></label>
                    <div class="col-xs-7">
                        <span id="bindgoogle-errortips" class="text-danger"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="bindgoogle-Btn" class="col-xs-3 control-label"></label>
                    <div class="col-xs-7">
                        <button id="bindgoogle-Btn" class="btn btn-danger btn-block"><spring:message code="ok_submit"/></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<input type="hidden" id="changePhoneCode" value="${fuser.ftelephone }" />
<input type="hidden" id="isEmptyPhone" value="${isBindTelephone ==true?1:0 }" />
<input type="hidden" id="isEmptygoogle" value="${isBindGoogle==true?1:0 }" />
<input id="messageType" type="hidden" value="0" />
<input id="currentPoint" type="hidden" value="${fuser.integral } ">
<input id="nextPoint" type="hidden" value="${fuser.integral + difference}">


<%@include file="../comm/footer.jsp" %>

<script type="text/javascript" src="${oss_url}/static/front/js/comm/msg.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.qrcode.min.js"></script>
    <script type="text/javascript" src="${oss_url}/static/front/js/plugin/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/user.member.js"></script>
<script type="text/javascript" src="${oss_url}/static/front/js/user/user.security.js"></script>






</body>
</html>
