<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<link rel="stylesheet" type="text/css" href="${oss_url}/static/ssadmin/wangEditor/wangEditor.css"/>
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
<h2 class="contentTitle">添加资讯信息</h2>


<div class="pageContent">

	<form method="post" action="ssadmin/saveArticle.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>资讯标题：</dt>
				<dd>
					<input type="text" name="ftitle"
						class="required" size="70" />
				</dd>
			</dl>
			<dl>
				<dt>是否站外文章：</dt>
				<dd>
					<input type="checkbox" name="fisout" />
				</dd>
			</dl>
			<dl>
				<dt>站外链接：</dt>
				<dd>
					<input type="text" name="foutUrl"
						 size="70" />
				</dd>
			</dl>
			<dl>
				<dt>来源：</dt>
				<dd>
					<input type="text" name="forigin"
						 size="70" />
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					<input type="hidden" name="articleLookup.id" id="articleLookup.id" value="${articleLookup.id}"/>
				    <input type="text" class="required" name="articleLookup.articleType" value="" suggestFields="id,articleType"
				     suggestUrl="ssadmin/articleTypeLookup.html" lookupGroup="orgLookup" readonly="readonly" size="70"/>
				    <a class="btnLook" href="ssadmin/articleTypeLookup.html" lookupGroup="articleLookup">查找带回</a>	
				</dd>
			</dl>
			<dl>
				<dt>标签：</dt>
				<dd>
					<input type="hidden" name="tag.id" id="tag.id" value="${tag.id}"/>
				    <input type="text" name="tag.tagName" readonly size="70"/>
				    <a class="btnLook" height="350" width="400" href="ssadmin/articleTagLookup.html" lookupGroup="tag">选择资讯标签</a>
				</dd>
			</dl>
			<dl>
				<dt>图片：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata"
						id="filedata" />
				</dd>
			</dl>
			<%--<input type="hidden" id="fcontent" name="fcontent"></input>--%>
			<dl>
				<dt>资讯内容：</dt>
				<dd>
					<textarea id="editor-trigger" name="fcontent"></textarea>
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
