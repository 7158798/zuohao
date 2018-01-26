<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>





 



<!doctype html>
<html>
<head>
<jsp:include page="../comm/link.inc.trade.jsp"></jsp:include>
</head>
<body>
	
<jsp:include page="../comm/header.jsp"></jsp:include>



	<div class="container-full">
		<div class="container displayFlex">
			<%@include file="../comm/left_menu.jsp" %>
			<div class="col-xs-10 padding-right-clear">
				<div class="col-xs-12 padding-right-clear padding-left-clear rightarea user">
					<div class="col-xs-12 rightarea-con">
						<div class="col-xs-12 padding-clear">
							<table class="table table-striped text-left">
					<tr class="bg-gary">
									<td class="text-center">
										<spring:message code="entrust_time_title"/>
									</td>
									<td>
										<spring:message code="entrutst_type_title"/>
									</td>
									<td>
										<spring:message code="number"/>
									</td>
									<td>
										<spring:message code="price"/>
									</td>
									<%--<td>--%>
										<%--金额--%>
									<%--</td>--%>
									<td>
										<spring:message code="success_num"/>
									</td>
									<td>
										<spring:message code="success_amount_title"/>
									</td>
									<td>
										<spring:message code="fees_title"/>
									</td>
									<td>
										<spring:message code="success_avg_price"/>
									</td>
									<td>
										<spring:message code="status_title"/>/<spring:message code="operation_title"/>
									</td>
								</tr>
					
									<c:if test="${fn:length(fentrusts)==0 }">
										<tr>
										<td class="no-data-tips" colspan="10">
											<span>
												<spring:message code="no_entrust"/>
											</span>
										</td>
									</tr>
									</c:if>
									
									<c:forEach items="${fentrusts }" var="v" varStatus="vs">
					<tr>
						<td class="gray"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="${v.fentrustType==0?'text-danger':'text-success' }">
						${v.fentrustType_en}
						<c:choose>
							<c:when test="${v.fisLimit==true}">
								[<spring:message code="market_price"/>]
							</c:when>
							<c:otherwise>

							</c:otherwise>
						</c:choose>
						</td>
						<td><ex:DoubleCut value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}</td>
						<td>
							<c:choose>
								<c:when test="${v.fisLimit == true}">
									<spring:message code="market_price"/>
								</c:when>
								<c:otherwise>
									${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/>
								</c:otherwise>
							</c:choose>
						</td>
						<%--<td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.famount}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>--%>
						<td><ex:DoubleCut value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}</td>
						<td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						<c:choose>
						<c:when test="${v.fentrustType==0 }">
						<td><ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/>${ftrademapping.fvirtualcointypeByFvirtualcointype2.fSymbol}</td>
						</c:when>
						<c:otherwise>
						<td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						</c:otherwise>
						</c:choose>
						<td>${v.ftrademapping.fvirtualcointypeByFvirtualcointype1.fSymbol}<ex:DoubleCut value="${((v.fcount-v.fleftCount)==0)?0:  v.fsuccessAmount/((v.fcount-v.fleftCount)) }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						<td>
						${v.fstatus_en }
						<c:if test="${v.fstatus==1 || v.fstatus==2}">
						&nbsp;|&nbsp;<a href="javascript:void(0);" class="tradecancel" data-value="${v.fid}" refresh="1"><spring:message code="cancel_desc1"/></a>
						</c:if>
						<c:if test="${v.fstatus!=1}">
						&nbsp;|&nbsp;<a href="javascript:void(0);" class="tradelook" data-value="${v.fid}"><spring:message code="view_desc"/></a>
						</c:if>
						</td>
                          </tr>
			</c:forEach>
			
								
							</table>
							<div class="text-right">
								${pagin}
							</div>
							
						</div>
						<input type="hidden" value="${currentPage}" id="currentPage">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal modal-custom fade" id="entrustsdetail" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-mark"></div>
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close btn-modal" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span class="modal-title" id="exampleModalLabel"><spring:message  code="lmenu_entrust_record"/></span>
				</div>
				<div class="modal-body"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="comm.error.tips.38"/></button>
				</div>
			</div>
		</div>
	</div>
	

<input type="hidden" id="symbol" value="${ftrademapping.fid }">
<jsp:include page="../comm/footer.jsp"></jsp:include>
<script type="text/javascript" src="${oss_url}/static/front/js/trade/trade.js"></script>
</body>
</html>
