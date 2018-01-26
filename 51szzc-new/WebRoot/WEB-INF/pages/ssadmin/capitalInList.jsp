<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post" action="ssadmin/capitalInList.html">
	<input type="hidden" name="status" value="${param.status}"> <input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="capitalId" value="${capitalId}" /> <input
		type="hidden" name="pageNum" value="${currentPage}" /> <input
		type="hidden" name="logDate" value="${logDate}" /><input
		type="hidden" name="numPerPage" value="${numPerPage}" /> <input
		type="hidden" name="orderField" value="${param.orderField}" /><input
		type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/capitalInList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}" /></td>
					<td>充值ID：<input type="text" name="capitalId"
						value="${capitalId}" size="10" /></td>
					<td>日期： <input type="text" name="logDate" class="date"
						readonly="true" value="${logDate }" />
					</td>
					<td>状态：
						<select type="combox" name="fstatus">

							<option value="0" ${status == 0 ? 'selected':''}>全部</option>
							<option value="2" ${status == 2 ? 'selected':''}>处理中</option>
							<option value="10" ${status == 10 ? 'selected':''}>二级审核中</option>
							<option value="11" ${status == 11 ? 'selected':''}>三级审核中</option>
							<option value="3" ${status == 3 ? 'selected':''}>提现成功</option>
							<option value="4" ${status == 4 ? 'selected':''}>用户撤销</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="ssadmin/capitalInAudit.html">
				<li>
				<c:if test="${isneedpwd  == 1}">
					<li><a class="edit"
					href="ssadmin/goCapitaloperationJSP.html?uid={sid_user}&url=ssadmin/capitalInAudit"
					height="320" width="800"  rel="auditVCInlog" target="dialog"><span>审核</span>
					</a>
				</c:if>
				<c:if test="${isneedpwd == 0}">
					<a class="edit"
					   href="ssadmin/capitalInAudit.html?uid={sid_user}" target="ajaxTodo"
					   rel="auditVCInlog" title="确定要审核吗?"><span>审核</span>
					</a>
				</c:if>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/capitalInCancel.html">
			<li>
				<a class="edit"
				   href="ssadmin/goCapitaloperationJSP.html?uid={sid_user}&url=ssadmin/capitalInCancel"
				   height="320" width="800" target="dialog"
				   rel="viewVirtualCapitaloperation" title="确定要取消充值吗?"><span>取消充值</span>
				</a>
			</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="ssadmin/capitalInPayStatusQuery.html">
				<li><a class="edit"
					   href="ssadmin/capitalInPayStatusQuery.html?uid={sid_user}"
					   target="ajaxTodo"><span>支付状态查询</span> </a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="120%" layoutH="138">
		<thead>
			<tr>
				<th width="20">会员UID</th>
				<th width="20">订单ID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="fremittanceType"
					<c:if test='${param.orderField == "fremittanceType" }'> class="${param.orderDirection}"  </c:if>>充值方式</th>	
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>金额</th>
				<th width="60" orderField="ffees"
					<c:if test='${param.orderField == "ffees" }'> class="${param.orderDirection}"  </c:if>>手续费</th>
				<%-- <th width="60" orderField="fBank"
					<c:if test='${param.orderField == "fBank" }'> class="${param.orderDirection}"  </c:if>>汇款银行</th>
				<th width="60" orderField="fAccount"
					<c:if test='${param.orderField == "fAccount" }'> class="${param.orderDirection}"  </c:if>>汇款人帐号</th>
				<th width="60" orderField="fPhone"
					<c:if test='${param.orderField == "fPhone" }'> class="${param.orderDirection}"  </c:if>>汇款人手机</th>	 --%>
				<th width="60" orderField="systembankinfo.fbankName"
					<c:if test='${param.orderField == "systembankinfo.fbankName" }'> class="${param.orderDirection}"  </c:if>>官方帐号银行</th>
				<th width="60" orderField="systembankinfo.fbankNumber"
					<c:if test='${param.orderField == "systembankinfo.fbankNumber" }'> class="${param.orderDirection}"  </c:if>>官方帐号</th>
				<th>汇款银行</th>
				<th>汇款帐号</th>
				<th>详细地址</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="fLastUpdateTime"
					<c:if test='${param.orderField == "fLastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>最后修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${capitaloperationList}" var="capitaloperation"
				varStatus="num">
				<tr target="sid_user" rel="${capitaloperation.fid}">
					<td>${capitaloperation.fuser.fid}</td>
					<td>${capitaloperation.fid}</td>
					<td>${capitaloperation.fuser.floginName}</td>
					<td>${capitaloperation.fuser.fnickName}</td>
					<td>${capitaloperation.fuser.frealName}</td>
					<td>${capitaloperation.fstatus_s}</td>
					<td>${capitaloperation.fremittanceType}</td>
					<td><fmt:formatNumber value="${capitaloperation.famount}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${capitaloperation.ffees}" pattern="##.######" maxIntegerDigits="15" maxFractionDigits="6"/></td>

					<td>${capitaloperation.systembankinfo.fbankName}</td>
					<td>${capitaloperation.systembankinfo.fbankNumber}</td>
					<td>${capitaloperation.fBank}</td>
					<td>${capitaloperation.fAccount}</td>
					<td><!--支付宝转账，则显示实际付款帐号，银行卡则显示银行支行地址-->
						<!-- 判断选择的付款帐号和实际付款帐号是否一样 -->
						<c:choose>
							<c:when test="${capitaloperation.fremittanceType  == '支付宝转账'}">
								<c:choose>
									<c:when test="${capitaloperation.fPayee == capitaloperation.fAccount}">
										${capitaloperation.fPayee}
									</c:when>
									<c:otherwise>
										<span style="color:red;">${capitaloperation.fPayee}</span>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								${capitaloperation.faddress}
							</c:otherwise>
						</c:choose>
					</td>
					<td>${capitaloperation.fcreateTime}</td>
					<td>${capitaloperation.fLastUpdateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
