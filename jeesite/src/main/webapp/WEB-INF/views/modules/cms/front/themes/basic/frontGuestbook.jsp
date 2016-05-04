<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>留言板</title>
<meta name="decorator" content="cms_default_${site.theme}" />
<meta name="description" content="JeeSite ${site.description}" />
<meta name="keywords" content="JeeSite ${site.keywords}" />
<link
	href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css"
	type="text/css" rel="stylesheet" />
<script
	src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js"
	type="text/javascript"></script>
<script
	src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js"
	type="text/javascript"></script>

<%-- <script type="text/javascript" src="${ctxStatic}/ckeditor/ckeditor.js"></script> --%>
<!-- 配置文件 -->
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.all.min.js">
	
</script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
<%--     <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ctxStatic}/umeditor1_2_2-src/ueditor.all.js"></script> --%>




<script type="text/javascript">


	$(document)
			.ready(
					function() {
						
						<c:if test="${not empty message}">alert("${message}");
						</c:if>
						$("#inputForm")
								.validate(
										{
											rules : {
												validateCode : {
													//后台验证
													remote : "${pageContext.request.contextPath}/servlet/validateCodeServlet"
												}
											},
											//错误消息
											messages : {
												content : {
													required : "请填写留言内容"
												},
												validateCode : {
													remote : "验证码不正确"
												}
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												if (element.is(":checkbox")
														|| element.is(":radio")
														|| element
																.parent()
																.is(
																		".input-append")) {
													error.appendTo(element
															.parent().parent());
												} else {
													/* error.insertAfter(element); */
													error.appendTo($("#validateCodeError"));
												}
											}
										});
						$("#main_nav li").each(
								function() {
									$(this)
											.toggleClass(
													"active",
													$(this).text().indexOf(
															'公共留言') >= 0);
								});
					});
	function page(n, s) {
		location = "${ctx}/guestbook?pageNo=" + n + "&pageSize=" + s;
		
	}

	function uploadPic() {
		//定义参数
		var options = {
			url : "/upload/uploadPic.do",
			dataType : "json",
			type : "post",
			success : function(data) {
				//回调 二个路径  
				//url
				//path
				$("#allImgUrl").attr("src", data.url);
				$("#path").val(data.url);
			}
		};

		//jquery.form使用方式
		$("#jvForm").ajaxSubmit(options);

	}
	
	
	
</script>



</head>
<body>
	<div style="padding: 0 0 20px;">
		<h4>公共留言</h4>
		<ul>
			<c:forEach items="${page.list}" var="guestbook">
				<li>
					<h5>
						姓名: ${guestbook.name} &nbsp;时间：
						<fmt:formatDate value="${guestbook.createDate}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</h5>
					<div>内容：${guestbook.content}</div>
					<h6>
						回复人：${guestbook.reUser.name} 时间：
						<fmt:formatDate value="${guestbook.reDate}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</h6>
					<div>回复内容：${guestbook.reContent}</div>
				</li>
			</c:forEach>
			<c:if test="${fn:length(page.list) eq 0}">
				<li>暂时还没有人留言！</li>
			</c:if>
		</ul>
		<div class="pagination">${page}</div>
		<h4>我要留言</h4>
		<form:form id="inputForm" action="${ctx}/guestbook" method="post"
			class="form-horizontal">
			<%-- <div class="control-group">
				<label class="control-label">名称:</label>
				<div class="controls">
					<input type="text" name="name" maxlength="11" class="required" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">邮箱:</label>
				<div class="controls">
					<input type="text" name="email" maxlength="50" class="required email" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">电话:</label>
				<div class="controls">
					<input type="text" name="phone" maxlength="50" class="required phone" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">单位:</label>
				<div class="controls">
					<input type="text" name="workunit" maxlength="50" class="required" style="width:300px;"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">留言分类:</label>
				<div class="controls">
					<select name="type" class="txt required" style="width:100px;">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('cms_guestbook')}" var="type">
							<option value="${type.value}">${type.label}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">留言内容:</label>
				<div class="controls">
					<textarea name="content" rows="4" maxlength="200" class="required" style="width:400px;"></textarea>
				</div>
			</div> --%>
			<script id="editor"  type="text/plain"
				style="width: 920px; height: 200px;"></script>

			
			<div style="margin-left:540px;margin-top:12px;" class="control-group">
				<label class="control-label">验证码:</label>
				<div class="controls">
					<sys:validateCode name="validateCode" />
				</div>
			</div>
			<div id="validateCodeError" style="margin-left:750px"></div>
			<div class="form-actions" style="margin-top:10px;">
				<input class="btn" type="button" value="提 交" style="margin-left:600px;"/>&nbsp;
			</div>
			<div id="messageBox" class="alert alert-error" style="display: none">输入有误，请先更正。</div>
			<input type="hidden" id="content" name="content" value=""/>
			
			
		</form:form>
			<!-- 实例化编辑器 -->
			<script type="text/javascript">
			var ue = UE.getEditor('editor');

			$(".btn").click(function(){
				var arr = ue.getContentTxt();
				$("#content").val(arr);
				$("#inputForm").submit();
			});
			</script>
	</div>

</body>
</html>