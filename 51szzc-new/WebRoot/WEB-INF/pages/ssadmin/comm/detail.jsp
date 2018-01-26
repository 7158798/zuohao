<%@ page pageEncoding="UTF-8"%>
<div class="accordion" fillSpace="sidebar">
	<shiro:hasPermission name="user">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>会员管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/userList.html">
					<li><a href="ssadmin/userList.html" target="navTab"
						rel="userList">会员列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/userAuditList.html">
					<li><a href="ssadmin/userAuditList.html" target="navTab"
						rel="userAuditList">待审核会员列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/logList.html">
					<li><a href="ssadmin/logList.html" target="navTab"
						rel="logList">会员操作日志列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/introlinfoList.html">
					<li><a href="ssadmin/introlinfoList.html" target="navTab"
						rel="introlinfoList">推广收益列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/bankinfoWithdrawList.html">
					<li><a href="ssadmin/bankinfoWithdrawList.html"
						target="navTab" rel="bankinfoWithdrawList">会员银行帐户列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/alipayBindList.html">
					<li><a href="ssadmin/alipayBindList.html"
						   target="navTab" rel="alipayBindList">会员支付宝帐户列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/virtualaddressWithdrawList.html">
					<li><a href="ssadmin/virtualaddressWithdrawList.html"
						target="navTab" rel="virtualaddressWithdrawList">会员虚拟币提现地址列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/virtualaddressList.html">
					<li><a href="ssadmin/virtualaddressList.html"
						target="navTab" rel="virtualaddressList">会员虚拟币充值地址列表</a></li>	
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/assetList.html">
					<li><a href="ssadmin/assetList.html" target="navTab"
						rel="assetList">会员资产记录列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/entrustList.html">
					<li><a href="ssadmin/entrustList.html" target="navTab"
						rel="entrustList">委托交易列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/userGoUpList.html">
					<li><a href="ssadmin/userGoUpList.html" target="navTab"
						   rel="userGoUpList">会员升级认证列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/userKycList.html">
					<li><a href="ssadmin/userKycList.html" target="navTab"
						   rel="userKycList">用户KYC认证审核</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/gradeSetList.html">
					<li><a href="ssadmin/gradeSetList.html" target="navTab"
						   rel="gradeSetList">积分等级制度管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/userIntegralList.html">
					<li><a href="ssadmin/userIntegralList.html" target="navTab"
						   rel="userIntegralList">用户积分查询管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/integralactivityList.html">
					<li><a href="ssadmin/integralactivityList.html" target="navTab"
						   rel="gradeSetList">
						积分获取途径管理</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="article">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>资讯管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/articleList.html">
					<li><a href="ssadmin/articleList.html" target="navTab"
						rel="articleList">资讯列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/webArticleList.html">
					<li><a href="ssadmin/webArticleList.html" target="navTab"
						   rel="webArticleList">网站录入资讯列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/articleTypeList.html">
					<li><a href="ssadmin/articleTypeList.html" target="navTab"
						rel="articleTypeList">资讯类型</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/tagManagementView.html">
					<li><a href="ssadmin/tagManagementView.html" id="tagDetail" target="navTab"
						rel="articleTypeList">标签管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/hostRecommendedList.html">
					<li><a href="ssadmin/hostRecommendedList.html" target="navTab"
						rel="articleTypeList">推荐管理</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="capital">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>虚拟币操作管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/virtualCoinTypeList.html">
					<li><a href="ssadmin/virtualCoinTypeList.html" target="navTab"
						rel="virtualCoinTypeList">虚拟币类型列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/tradeMappingList.html">
					<li><a href="ssadmin/tradeMappingList.html" target="navTab"
						rel="tradeMappingList">法币类型匹配列表</a></li>	
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/walletAddressList.html">
					<li><a href="ssadmin/walletAddressList.html" target="navTab"
						rel="walletAddressList" title="虚拟币可用地址列表">虚拟币可用地址列表</a>
					</li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCaptualoperationList.html">
					<li><a href="ssadmin/virtualCaptualoperationList.html"
						target="navTab" rel="virtualCaptualoperationList">虚拟币操作总表</a>
					</li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCapitalInList.html">
					<li><a href="ssadmin/virtualCapitalInList.html"
						target="navTab" rel="virtualCapitalInList">虚拟币充值列表</a>
					</li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCapitalOutList.html">
					<li><a href="ssadmin/virtualCapitalOutList.html"
						target="navTab" rel="virtualCapitalOutList">待审核虚拟币提现列表</a>
					</li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualCapitalOutSucList.html">
					<li><a href="ssadmin/virtualCapitalOutSucList.html"
						target="navTab" rel="virtualCapitalOutSucList">虚拟币成功提现列表</a>
					</li>
				</shiro:hasPermission>

				<shiro:hasPermission name="ssadmin/virtualwalletList.html">
					<li><a href="ssadmin/virtualwalletList.html" target="navTab"
						rel="virtualwalletList">会员虚拟币列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/virtualoperationlogList.html">
					<li><a href="ssadmin/virtualoperationlogList.html"
						target="navTab" rel="virtualoperationlogList">虚拟币手工充值列表</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="cnycapital">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>人民币操作管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/capitaloperationList.html">
					<li><a href="ssadmin/capitaloperationList.html"
						target="navTab" rel="capitaloperationList">人民币操作总表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalInSucList.html">
					<li><a href="ssadmin/capitalInSucList.html" target="navTab"
						rel="capitalInSucList">成功充值人民币列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalOutSucList.html">
					<li><a href="ssadmin/capitalOutSucList.html" target="navTab"
						rel="capitalOutSucList">成功提现人民币列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalInList.html">
					<li><a href="ssadmin/capitalInList.html" target="navTab"
						rel="capitalInList">待审核人民币充值列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitalOutList.html">
					<li><a href="ssadmin/capitalOutList.html" target="navTab"
						rel="capitalOutList">待审核人民币提现列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/walletList.html">
					<li><a href="ssadmin/walletList.html" target="navTab"
						rel="walletList">会员人民币列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/operationLogList.html">
					<li><a href="ssadmin/operationLogList.html" target="navTab"
						rel="operationLogList">人民币手工充值列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/withdrawFeesList.html">
					<li><a href="ssadmin/withdrawFeesList.html" target="navTab"
						rel="withdrawFeesList" title="人民币提现手续费列表">人民币提现手续费列表</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="report">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>报表统计
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/userReport.html">
					<li><a href="ssadmin/userReport.html" target="navTab"
						rel="userReport">会员注册统计表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitaloperationReport.html">
					<li><a
						href="ssadmin/capitaloperationReport.html?type=1&status=3&url=ssadmin/capitaloperationReport"
						target="navTab" rel="capitaloperationReport">人民币充值统计表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/capitaloperationOutReport.html">
					<li><a
						href="ssadmin/capitaloperationReport.html?type=2&status=3&url=ssadmin/capitaloperationOutReport"
						target="navTab" rel="capitaloperationOutReport">人民币提现统计表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/vcOperationInReport.html">
					<li><a
						href="ssadmin/vcOperationReport.html?type=1&status=3&url=ssadmin/vcOperationInReport"
						target="navTab" rel="vcOperationInReport">虚拟币充值统计表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/vcOperationOutReport.html">
					<li><a
						href="ssadmin/vcOperationReport.html?type=2&status=3&url=ssadmin/vcOperationOutReport"
						target="navTab" rel="vcOperationOutReport">虚拟币提现统计表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/totalReport.html">
					<li><a href="ssadmin/totalReport.html" target="navTab"
						rel="totalReport">综合统计表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/financeReport.html">
					<li><a href="ssadmin/financeReport.html" target="navTab"
						   rel="totalReport">对账单导出</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="question">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>提问管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/questionList.html">
					<li><a href="ssadmin/questionList.html" target="navTab"
						   rel="questionList">提问记录列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/questionForAnswerList.html">
					<li><a href="ssadmin/questionForAnswerList.html"
						   target="navTab" rel="questionList">待回复提问列表</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="help">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>帮助中心
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/helpList.html">
					<li><a href="ssadmin/helpList.html" target="navTab"
						   rel="helpList">帮助列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/helpTypeList.html">
					<li><a href="ssadmin/helpTypeList.html"
						   target="navTab" rel="helpTypeList">类型管理</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="video">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>视频管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/videoList.html">
					<li><a href="ssadmin/videoList.html" target="navTab"
						   rel="questionList">视频列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/videoTypeList.html">
					<li><a href="ssadmin/videoTypeList.html"
						   target="navTab" rel="questionList">视频类别管理</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

	<shiro:hasPermission name="system">
		<div class="accordionHeader">
			<h2>
				<span>Folder</span>系统管理
			</h2>
		</div>
		<div class="accordionContent">
			<ul class="tree treeFolder">
				<shiro:hasPermission name="ssadmin/systemArgsList.html">
					<li><a href="ssadmin/systemArgsList.html" target="navTab"
						rel="systemArgsList">系统参数列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/operateArgsList.html">
					<li><a href="ssadmin/operateArgsList.html" target="navTab"
						   rel="operateArgsList">运营参数列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/linkList.html">
					<li><a href="ssadmin/linkList.html" target="navTab"
						rel="linkList">友情链接列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/qqList.html">
					<li><a href="ssadmin/qqList.html" target="navTab" rel="qqList">QQ群列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/systemBankList.html">
					<li><a href="ssadmin/systemBankList.html" target="navTab"
						rel="systemBankList">银行帐户列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/sysAlipayList.html">
					<li><a href="ssadmin/sysAlipayList.html" target="navTab"
						   rel="sysAlipayList">支付宝账户列表</a></li>
				</shiro:hasPermission>
				<%--<shiro:hasPermission name="ssadmin/aboutList.html">
					<li><a href="ssadmin/aboutList.html" target="navTab"
						rel="aboutList">帮助分类列表</a></li>
				</shiro:hasPermission>--%>
				<shiro:hasPermission name="ssadmin/securityList.html">
					<li><a
						href="ssadmin/goSecurityJSP.html?url=ssadmin/securityTreeList&treeId=1"
						target="navTab" rel="securityTreeList">权限列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/roleList.html">
					<li><a href="ssadmin/roleList.html" target="navTab"
						rel="roleList">角色列表</a>
					</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/adminList.html">
					<li><a href="ssadmin/adminList.html" target="navTab"
						rel="adminList">管理员列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/countLimitList.html">
					<li><a href="ssadmin/countLimitList.html" target="navTab"
						rel="countLimitList">限制管理列表</a></li>	
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/autoOrderList.html">
					<li><a href="ssadmin/autoOrderList.html" target="navTab"
						   rel="autoOrderList">自动挂单配置</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/tradeSetList.html">
					<li><a href="ssadmin/tradeSetList.html" target="navTab"
						   rel="autoOrderList">交易设置</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/fastTradeList.html">
					<li><a href="ssadmin/fastTradeList.html" target="navTab"
						   rel="fastTradeList">快速交易</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/batchOrderList.html">
					<li><a href="ssadmin/batchOrderList.html" target="navTab"
						   rel="batchOrderList">批量下单列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/robotTradeList.html">
					<li><a href="ssadmin/robotTradeList.html" target="navTab"
						   rel="robotTradeList">机器人交易</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/entrustWarnList.html">
					<li><a href="ssadmin/entrustWarnList.html" target="navTab"
						   rel="robotTradeList">委托预警列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/sysOperatorlog.html">
					<li><a href="ssadmin/sysOperatorlog.html" target="navTab"
						   rel="sysOperatorlog">后台操作日志</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/sysLoginLog.html">
					<li><a href="ssadmin/sysLoginLog.html" target="navTab"
						   rel="sysLoginlog">后台登录日志</a></li>
				</shiro:hasPermission>
				<%--<li><a href="ssadmin/validatemessageList.html" target="navTab"--%>
					   <%--rel="validatemessageList">短信验证码</a></li>--%>
				<shiro:hasPermission name="ssadmin/bannerList.html">
					<li><a href="ssadmin/bannerList.html" target="navTab"
						   rel="bannerList">banner管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/auditProcessList.html">
				<li><a href="ssadmin/auditProcessList.html" target="navTab"
					   rel="bannerList">审核流程配置</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/kycconfig.html">
					<li><a href="ssadmin/kycconfig.html" target="navTab"
						   rel="bannerList">KYC权限列表</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ssadmin/autowithconfig.html">
					<li><a href="ssadmin/autowithconfig.html" target="navTab"
						   rel="autowithconfig">系统自动提现配置</a></li>
				</shiro:hasPermission>
			</ul>
		</div>
	</shiro:hasPermission>

</div>