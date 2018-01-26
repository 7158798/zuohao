<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<form id="pagerForm" method="post"
	action="ssadmin/virtualCapitalInList.html">
	<input
		type="hidden" name="keywords" value="${keywords}" /> <input
		type="hidden" name="ftype" value="${ftype}" /><input type="hidden"
		name="pageNum" value="${currentPage}" /> <input type="hidden"
		name="numPerPage" value="${numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" /><input type="hidden"
		name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
	<input type="hidden" name="fstatus" value="${status}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="ssadmin/virtualCapitalInList.html" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td>会员信息：<input type="text" name="keywords" value="${keywords}"
						size="40" /></td>
					<td>虚拟币类型： <select type="combox" name="ftype">
							<c:forEach items="${typeMap}" var="type">
								<c:if test="${type.key == ftype}">
									<option value="${type.key}" selected="true">${type.value}</option>
								</c:if>
								<c:if test="${type.key != ftype}">
									<option value="${type.key}">${type.value}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>
					<td>状态：
						<select type="combox" name="fstatus">
							<option value="-1" ${status == -1 ? 'selected':''}>全部</option>
							<option value="0" ${status == 0 ? 'selected':''}>0确认</option>
							<option value="1" ${status == 1 ? 'selected':''}>1确认</option>
							<option value="2" ${status == 2 ? 'selected':''}>待确认</option>
							<option value="10" ${status == 10 ? 'selected':''}>二级审核中</option>
							<option value="11" ${status == 11 ? 'selected':''}>三级审核中</option>
							<option value="3" ${status == 3 ? 'selected':''}>审核成功</option>
							<option value="4" ${status == 4 ? 'selected':''}>用户撤销</option>
						</select>
					</td>


					<td>开始日期： <input type="text" name="startDate" class="date"
									 readonly="true"  value="${startDate }" />
					</td>
					<td>结束日期： <input type="text" name="endDate" class="date"
									 readonly="true" value="${endDate }" />
					</td>

				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
	<ul class="toolBar">
	<shiro:hasPermission name="ssadmin/auditVCInlog.html">
        <li>
            <c:if test="${isneedpwd  == 1}">
                <li><a class="edit"
                href="ssadmin/goVirtualCapitaloperationJSP.html?uid={sid_user}&url=ssadmin/auditVCInlog"
                height="320" width="800"  rel="auditVCInlog" target="dialog"><span>审核</span>
                </a>
            </c:if>
            <c:if test="${isneedpwd == 0}">
                <a class="edit"
                href="ssadmin/auditVCInlog.html?uid={sid_user}" target="ajaxTodo"
                rel="auditVCInlog" title="确定要审核吗?"><span>审核</span>
                </a>
            </c:if>
        </li>
	</shiro:hasPermission>
		<li class="line">line</li>
		<shiro:hasPermission name="ssadmin/virtualCapitalInListExport.html">
			<li><a class="icon" href="ssadmin/virtualCapitalInListExport.html"
				   target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出</span>
			</a></li>
		</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="20">会员UID</th>
				<th width="60" orderField="fuser.floginName"
					<c:if test='${param.orderField == "fuser.floginName" }'> class="${param.orderDirection}"  </c:if>>登陆名</th>
				<th width="60" orderField="fuser.fnickName"
					<c:if test='${param.orderField == "fuser.fnickName" }'> class="${param.orderDirection}"  </c:if>>会员昵称</th>
				<th width="60" orderField="fuser.frealName"
					<c:if test='${param.orderField == "fuser.frealName" }'> class="${param.orderDirection}"  </c:if>>会员真实姓名</th>
				<th width="60" orderField="fvirtualcointype.fname"
					<c:if test='${param.orderField == "fvirtualcointype.fname" }'> class="${param.orderDirection}"  </c:if>>虚拟币类型</th>
				<th width="60" orderField="fconfirmations"
					<c:if test='${param.orderField == "fconfirmations" }'> class="${param.orderDirection}"  </c:if>>确认数</th>
				<th width="60" >是否合约转账</th>
				<th width="60" orderField="fstatus"
					<c:if test='${param.orderField == "fstatus" }'> class="${param.orderDirection}"  </c:if>>状态</th>
				<th width="60" orderField="ftype"
					<c:if test='${param.orderField == "ftype" }'> class="${param.orderDirection}"  </c:if>>交易类型</th>
				<th width="60" orderField="famount"
					<c:if test='${param.orderField == "famount" }'> class="${param.orderDirection}"  </c:if>>数量</th>
				<th width="60" orderField="ffees"
					<c:if test='${param.orderField == "ffees" }'> class="${param.orderDirection}"  </c:if>>手续费</th>
				<th width="60">充值地址</th>
				<th width="60" orderField="fcreateTime"
					<c:if test='${param.orderField == "fcreateTime" }'> class="${param.orderDirection}"  </c:if>>创建时间</th>
				<th width="60" orderField="flastUpdateTime"
					<c:if test='${param.orderField == "flastUpdateTime" }'> class="${param.orderDirection}"  </c:if>>最后修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${virtualCapitaloperationList}"
				var="virtualCapitaloperation" varStatus="num">
				<tr target="sid_user" rel="${virtualCapitaloperation.fid}">
					<td>${virtualCapitaloperation.fuser.fid}</td>
					<td>${virtualCapitaloperation.fuser.floginName}</td>
					<td>${virtualCapitaloperation.fuser.fnickName}</td>
					<td>${virtualCapitaloperation.fuser.frealName}</td>
					<td>${virtualCapitaloperation.fvirtualcointype.fname}</td>
					<td>${virtualCapitaloperation.fconfirmations}</td>
					<td>${virtualCapitaloperation.isContract}</td>
					<td>${virtualCapitaloperation.fstatus_s}</td>
					<td>${virtualCapitaloperation.ftype_s}</td>
					<td><fmt:formatNumber value="${virtualCapitaloperation.famount}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/></td>
					<td><fmt:formatNumber value="${virtualCapitaloperation.ffees}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/></td>
					<td>${virtualCapitaloperation.recharge_virtual_address}</td>
					<td>${virtualCapitaloperation.fcreateTime}</td>
					<td>${virtualCapitaloperation.flastUpdateTime}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="panelBar">
		<div class="pages">
			<span>总共: ${totalCount}条</span>
		</div>
		<div class="pages">
			<span>充值数量:<fmt:formatNumber value="${totalDeposit}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/></span>
		</div>
		<div class="pages">
			<span>充值手续费:<fmt:formatNumber value="${totalFees}" pattern="##.######" maxIntegerDigits="20" maxFractionDigits="6"/></span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}"
			numPerPage="${numPerPage}" pageNumShown="5"
			currentPage="${currentPage}"></div>
	</div>
</div>
