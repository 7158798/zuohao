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
<h2 class="contentTitle">修改资讯信息</h2>


<div class="pageContent">

     <form method="post" action="ssadmin/updateArticle.html"
		class="pageForm required-validate" enctype="multipart/form-data"
		onsubmit="return iframeCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt>资讯标题：</dt>
				<dd>
				    <input type="hidden" name="fid" value="${farticle.fid}"/>
					<input type="text" name="ftitle" 
						class="required" size="70" value="${farticle.ftitle}"/>
				</dd>
			</dl>
			<dl>
				<dt>是否站外文章：</dt>
				<dd>
					<c:choose>
						<c:when test="${farticle.fisout}">
							<input type="checkbox" name="fisout" checked='1' />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="fisout" />
						</c:otherwise>
					</c:choose>
				</dd>
			</dl>
			<dl>
				<dt>站外链接：</dt>
				<dd>
					<input type="text" name="foutUrl" 
						 size="70"  value="${farticle.foutUrl}"/>
				</dd>
			</dl>
			<dl>
				<dt>来源：</dt>
				<dd>
					<input id="forigin" type="text" name="forigin"
						 size="70"  value="${farticle.forigin}"/>
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					<input type="hidden" name="articleLookup.id" id="articleLookup.id" value="${farticle.farticletype.fid}"/>
				    <input type="text" class="required" name="articleLookup.articleType" value="${farticle.farticletype.fname}"
				     suggestFields="id,articleType" suggestUrl="ssadmin/articleTypeLookup.html" lookupGroup="orgLookup" readonly="readonly"/>
				    <a class="btnLook" href="ssadmin/articleTypeLookup.html" lookupGroup="articleLookup">查找带回</a>	
				</dd>
			</dl>
			<dl>
				<dt>标签：</dt>
				<dd>
					<input type="hidden" name="tag.id" id="tag.id" value="${tag.id}"/>
					<input type="text" name="tag.tagName" size="70" readonly value="${farticle.ftag}"/>
					<a class="btnLook" height="350" width="400" href="ssadmin/articleTagLookup.html" lookupGroup="tag">选择资讯标签</a>
				</dd>
			</dl>
			<dl>
				<dt>图片：</dt>
				<dd>
					<input type="file" class="inputStyle" value="" name="filedata" id="filedata" />
				</dd>
			</dl>
			<%--<dl>
				<dt>资讯内容：</dt>
				<dd>
					<textarea class="editor" name="fcontent" rows="20" cols="105"
						tools="simple" upImgUrl="ssadmin/upload.html"
						upImgExt="jpg,jpeg,gif,png">
						${farticle.fcontent}
				</textarea>
			</dl>--%>
			<%--<input type="hidden" id="fcontent" name="fcontent" value='${farticle.fcontent}' />--%>
			<dl>
				<dt>资讯内容：</dt>
				<dd>
					<textarea id="editor-trigger" name="fcontent">${farticle.fcontent}</textarea>
					<%--<div id="editor-trigger">
						${farticle.fcontent}
						<p></p>
					</div>--%>

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

