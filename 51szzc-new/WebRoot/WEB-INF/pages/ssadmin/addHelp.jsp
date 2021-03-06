<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="/static/ssadmin/wangEditor/wangEditor.css"/>
<style type="text/css">
	#editor-trigger {
		height: 400px;
		/*max-height: 500px;*/
	}
	.container {
		width: 100%;
		margin: 0 auto;
		position: relative;
	}
</style>
<h2 class="contentTitle">添加帮助内容</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveHelp.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="100">
			<dl>
				<dt>内容标题：</dt>
				<dd>
					<input type="text" name="ftitle"
						class="required" size="40" />
				</dd>
			</dl>
			<dl>
				<dt>内容类型：</dt>
				<dd>
					<select type="combox" name="fhelptypeId">
						<c:forEach items="${typeMap}" var="type">
							<option value="${type.key}">${type.value}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<%--<textarea style="display: none" name="fcontent"></textarea>
			<dl>--%>
				<dt>内容编辑：</dt>
				<dd>
					<textarea id="editor-trigger" name="fcontent">${fhelp.fcontent}</textarea>
					<%--<div id="editor-trigger">
						<p></p>
					</div>--%>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>

</div>


<script type="text/javascript" src="${oss_url}/static/front/js/plugin/ajaxfileupload.js"></script>
<script type="text/javascript" src="${oss_url}/static/ssadmin/js/comm/common.js"></script>
<script type="text/javascript" src="/static/ssadmin/wangEditor/wangEditor.js"></script>
<script type="text/javascript" src="/static/ssadmin/js/article/article.js"></script>

