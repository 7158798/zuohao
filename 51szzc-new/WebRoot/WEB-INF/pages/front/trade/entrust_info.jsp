<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../comm/include.inc.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>



<div class="col-xs-12">
	<div class="panel">
		<div class="panel-heading">
			<span class="text-danger"><spring:message code="current_entrust_title"/></span>
			<span class="pull-right">
               <a class="allcancel opa-link" href="javascript:void(0);" data-value="${ftrademapping.fid }"><spring:message code="cancel"/></a>
            </span>
		</div>
		<div  id="fentrustsbody0" class="panel-body">
			<table class="table">
			
			<tr>
				<td><spring:message code="entrust_time_title"/></td>
				<td><spring:message code="entrutst_type_title"/></td>
				<td><spring:message code="entrust_price_title"/></td>
				<td><spring:message code="entrust_num_title"/></td>
				<%--<td>总金额</td>--%>
				<td><spring:message code="success_num_title"/></td>
				<td><spring:message code="success_amount_title"/></td>
				<td><spring:message code="fees_title"/></td>
				<td><spring:message code="status_title"/></td>
				<td class="width-65"><spring:message code="operation_title"/></td>
			</tr>
				<c:if test="${fn:length(fentrusts1)==0 }">
					<tr>
						<td colspan="10" class="no-data-tips">
							<span>
								<spring:message code="no_entrust"/>
							</span>
						</td>
					</tr>
				</c:if>
					
				<c:forEach items="${fentrusts1 }" var="v" varStatus="vs">
					<tr>
						<td class="gray"><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="${v.fentrustType==0?'text-danger':'text-success' }">${v.fentrustType_s}
							<c:choose>
								<c:when test="${v.fisLimit == true}">
									[<spring:message code="market_price"/>]
								</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${v.fisLimit == true}">
									<spring:message code="market_price"/>
								</c:when>
								<c:otherwise>
									${coin1.fSymbol}<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td><ex:DoubleCut value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/>${coin2.fSymbol}</td>
						<%--<td>${coin1.fSymbol}<ex:DoubleCut value="${v.famount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>--%>
						<td><ex:DoubleCut value="${v.fcount-v.fleftCount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount2 }"/>${coin2.fSymbol}</td>
						<td>${coin1.fSymbol}<ex:DoubleCut value="${v.fsuccessAmount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						<c:choose>
						<c:when test="${v.fentrustType==0 }">
						<td><ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/>${coin2.fSymbol}</td>
						</c:when>
						<c:otherwise>
						<td>${coin1.fSymbol}<ex:DoubleCut value="${v.ffees-v.fleftfees}" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${ftrademapping.fcount1 }"/></td>
						</c:otherwise>
						</c:choose>
						<td>
						${v.fstatus_s }
						</td>
						<td  class="opa-link">
						<c:if test="${v.fstatus==1 || v.fstatus==2}">
						<a href="javascript:void(0);" class="tradecancel opa-link" data-value="${v.fid}"><spring:message code="cancel_desc1"/></a>
						</c:if>
						<c:if test="${v.fstatus==3}">
						<a href="javascript:void(0);" class="tradelook opa-link" data-value="${v.fid}"><spring:message code="view_desc"/></a>
						</c:if>
						</td>
                          </tr>
			</c:forEach>
				
			</table>
		</div>
	</div>
</div>
<%-- <div class="col-xs-12">
	<div class="panel">
		<div class="panel-heading">
			<span class="text-danger">最近成交记录</span>
			<span class="pull-right fentruststitle" data-type="1" data-value="0">收起 -</span>
		</div>
		<div  id="fentrustsbody1" class="panel-body">
			<table class="table">
				<tr>
					<td>成交时间</td>
					<td>买/卖</td>
					<td>成交价</td>
					<td>成交量</td>
					<td>总金额</td>
				</tr>
				<c:if test="${fn:length(successEntrusts)==0 }">
					<tr>
						<td colspan="5" class="no-data-tips">
							<span>
								暂无成交记录
							</span>
						</td>
					</tr>
				</c:if>
					
				<c:forEach items="${successEntrusts }" var="v" varStatus="vs" begin="0" end="30">
					<tr>
						<td><fmt:formatDate value="${v.fcreateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td class="${v.fEntrustType==0?'text-success':'text-danger' }">${v.fEntrustType==0?'买入':'卖出' }</td>
						<td class="${v.fEntrustType==0?'text-success':'text-danger' }">${coin1.fSymbol}<ex:DoubleCut value="${v.fprize }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="${fvirtualcointype.fcount }"/></td>
						<td>${coin2.fSymbol}<ex:DoubleCut value="${v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
						<td>${coin1.fSymbol}<ex:DoubleCut value="${v.fprize*v.fcount }" pattern="##.##" maxIntegerDigits="15" maxFractionDigits="4"/></td>
					</tr>	
			</c:forEach>
				
			</table>
		</div>
	</div>
</div> --%>
