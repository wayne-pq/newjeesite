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
	src="${ctxStatic}/bootstrap/jquery.masonry.min.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.all.min.js">
	
</script>
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/ueditor.parse.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="${ctxStatic}/ueditor/lang/zh-cn/zh-cn.js"></script>
<%--     <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="${ctxStatic}/umeditor1_2_2-src/ueditor.all.js"></script> --%>


<style type="text/css">
/* 留言图片展示CSS */
.container-fluid {
	padding: 20px;
	width: 550px;
}

.box {
	margin-bottom: -3px;
	margin-left: -3px;
	float: left;
}

.box img {
	width: 140px;
	height: 105px;
	/* max-width: 100% */
}
/* 轮播CSS */
.carousel-inner img {
	margin: 0 auto;
	width: auto;
	height: 394px;
	width: auto;
}

/* ueditor图片风琴式CSS */
#imgss .container-fluid {
	padding: 20px;
	width: 650px;
}

#imgss .container-fluid img {
	width: 180px;
	height: 135px;
}
/* #imgss img{
	display:inline;
} */
</style>

<script type="text/javascript">
	$(document)
			.ready(
					function() {

						/* <c:if test="${not empty message}">alert("${message}");
						</c:if> */

						$('.accordion').collapse('hide');

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
													error
															.appendTo($("#validateCodeError"));
												}
											}
										});
						$("#main_nav li").each(function() {
							/* $(this)
									.toggleClass(
											"active",
											$(this).text().indexOf(
													'公共留言') >= 0); */
						});
					});
	function page(n, s) {
		location = "${ctx}/leavewords?pageNo=" + n + "&pageSize=" + s;

	}

	function uploadPicSrc() {
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

	function getPics(id, index) {

		//定义参数
		var options = {
			url : "${ctx}/leavewords/findById",
			dataType : "json",
			data : {
				myid : id
			},
			type : "post",
			success : function(data) {
				var img = null;
				$(".carousel-inner div").remove();
				$(".carousel-indicators  li").remove();
				$("#myCarousel  a").remove();
				for ( var i = 0; i < data.length; i++) {
					img = data[i];
					if (index == i) {
						$(".carousel-inner").append(
								"<div class=\"active item\">" + img + "</div>");
						$(".carousel-indicators")
								.append(
										"<li data-target=\"#myCarousel\" data-slide-to=\""+i+"\" class=\"active\" >");
					} else {
						$(".carousel-inner").append(
								"<div class=\" item\">" + img + "</div>");
						$(".carousel-indicators")
								.append(
										"<li data-target=\"#myCarousel\" data-slide-to=\""+i+"\" >");
					}

				}
				if (data.length != 1) {
					$("#myCarousel")
							.append(
									"<a class=\"carousel-control left\" href=\"#myCarousel\" style=\"margin-top: 3px\" data-slide=\"prev\">&lsaquo;</a> <a class=\"carousel-control right\" href=\"#myCarousel\" data-slide=\"next\" style=\"margin-top: 6px\">&rsaquo;</a>");
				}

				$('#play').modal('show');
				/* $('.carousel').carousel(); */
				/* $("#allImgUrl").attr("src", data.url);
				$("#path").val(data.url); */
			}
		};
		$.ajax(options);

	}

	/* 
		留言回复提交ajax
	 */
	function replysubmit(id) {
		//定义参数
		var options = {
			url : "${ctx}/leavewords/replysubmit",
			dataType : "json",
			data : {
				comment_id : id
			},
			type : "post",
			success : function(data) {

				var name = data.name;
				var byname = data.byname;
				var content = data.content;
				var createDate = data.createDate;
				var ISFIRST = data.ISFIRST;
				var myid = '#replys_' + id;
				var mid = data.id;

				if (ISFIRST == 'N') {
					myid = "#" + id;
					$(myid)
							.append(
									"<div id="+mid+">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"
											+ "<input type=\"hidden\" id=\"reply_name\" value="+name+">"
											+ "<font color=\"blue\">"
											+ name
											+ "&nbsp&nbsp<font color=\"black\">回复</font>&nbsp&nbsp"
											+ byname
											+ "</font>&nbsp: "
											+ content
											+ "<br/>&nbsp&nbsp&nbsp&nbsp&nbsp<font style=\"font-size: 11px\">  "
											+ createDate
											+ "</font>&nbsp&nbsp&nbsp&nbsp<img alt=\"\" onclick=\"reply_img_click3('"
											+ mid
											+ "')\" src=\"/jeesite/servlet/ImgDisplayServlet?path=/20160302094727.png\" /></div>");
				} else {

					$(myid)
							.append(
									"<div id="+mid+">"
											+ "<input type=\"hidden\" id=\"reply_name\" value="+name+">"
											+ "<font color=\"blue\">"
											+ name
											+ "</font> : "
											+ content
											+ "<br/><font style=\"font-size: 11px\">  "
											+ createDate
											+ "</font>&nbsp&nbsp&nbsp<img alt=\"\" onclick=\"reply_img_click('"
											+ mid
											+ "')\" src=\"/jeesite/servlet/ImgDisplayServlet?path=/20160302094727.png\" /></div>");

				}

				/* var list = eval("("+data+")");
				
				
				for ( var i = 0; i < list.length; i++) {
					var name = list[i].name;
					var content = list[i].content;
					$('replys').append(name +" : " + content);
				} */
				UE.getEditor('replyeditor', {
					toolbars : []
				}).destroy();
				$("#replyform").remove();

			}
		};
		$('#replyform').ajaxSubmit(options); //jquery1.9.1的版本中  必须导入 jquery-form.js文件
	}

	/* 
	 //jquery.form使用方式
	 $("#jvForm").ajaxSubmit(options); */
</script>



</head>
<body>
	<div style="padding: 0 0 20px;">
		<h4>留言板</h4>

		<ul>
			<c:forEach items="${page.list}" var="leavewords">

				<li>
					<h5>
						姓名: ${leavewords.name} &nbsp;时间：
						<fmt:formatDate value="${leavewords.createDate}"
							pattern="yyyy-MM-dd HH:mm:ss" />
						<button id="deletebutton" type="button"
							class="btn btn-mini pull-right" value="${leavewords.id}"
							onClick="deleteFunciton('${leavewords.id}')">删除</button>
					</h5>
					<div class="ueditorcontent">内容： ${leavewords.content}</div> <%-- <c:set var="ds" value="${leavewords.content}"></c:set> --%>
					<!-- <script type="text/javascript">
					
					$("#${leavewords.id}").html('${leavewords.content}');
					</script> --> <%-- <ul class="thumbnails container-fluid" id="masonry">
						<c:forEach items="${leavewords.imgs}" var="img" varStatus="status">


							<c:choose>
								<c:when test="${status.index==0 }">
									<li class="span3 box"
										style="height: 126px; width: 168px; white-space: nowrap;"><a
										href="#" class="thumbnail">${img }</a></li>
								</c:when>
								<c:otherwise>
									<li class="span3 box"
										style="height: 126px; width: 168px; margin-left: -4px; white-space: nowrap;"><a
										href="#" class="thumbnail">${img }</a></li>
								</c:otherwise>
							</c:choose>
							<c:if test="${(status.index+1)%3==0 }">
							</c:if>
						</c:forEach>
					</ul> --%>

					<div id="masonry thumbnails " class="container-fluid">

						<c:forEach items="${leavewords.data}" var="img" varStatus="status">
							<div class="box thumbnail"
								onclick="getPics('${leavewords.id}','${status.index }')">${img.imgs
								}</div>
						</c:forEach>
					</div>
					<div class="btn-group" id="comment-btn-group">
						<button class="btn" style="margin: 16px"
							onclick="comment('${leavewords.id}')" id="${leavewords.id}">评论</button>
					</div>
					<div id="replys_${leavewords.id}" style="margin: 16px">
						<c:forEach items="${leavewords.replys}" var="reply"
							varStatus="status">
							<c:if test="${ reply.ISFIRST == 'Y'}">
								<c:if test="${ status.first}">
									<div id="${reply.ID }">
								</c:if>
								<c:if test="${!status.first}">
					</div>
					<div id="${reply.ID }">
						</c:if>

						<input type="hidden" id="reply_name"
							value="${fns:getUserById(reply.REPLY_ID).loginName}"> <font
							color="blue"> ${fns:getUserById(reply.REPLY_ID).loginName}</font>&nbsp:&nbsp${reply.CONTENT}<br />
						<font style="font-size: 11px">${fns:formatDateTime(reply.createDate)}
						</font>&nbsp&nbsp<img alt=""
							src="/jeesite/servlet/ImgDisplayServlet?path=/20160302094727.png"
							onclick="reply_img_click('${reply.ID}')" />
						</c:if>
						<c:if test="${reply.ISFIRST == 'N'}">
							<div id="${reply.ID }">
							<input type="hidden" id="reply_name"
							value="${fns:getUserById(reply.REPLY_ID).loginName}">
								&nbsp&nbsp&nbsp&nbsp&nbsp<font color="blue">
									${fns:getUserById(reply.REPLY_ID).loginName} &nbsp<font
									color="black">回复</font>&nbsp ${fns:getUserById(reply.BYREPLY_ID).loginName}
								</font> : ${reply.CONTENT} <br />&nbsp&nbsp&nbsp&nbsp&nbsp<font
									style="font-size: 11px">
									${fns:formatDateTime(reply.createDate)} </font>&nbsp&nbsp&nbsp<img
									alt=""
									src="/jeesite/servlet/ImgDisplayServlet?path=/20160302094727.png"
									onclick="reply_img_click3('${reply.ID}')" />
							</div>
						</c:if>
						<c:if test="${status.last}">
					</div> </c:if>
			</c:forEach>
	</div>
	<%-- 	<h6>
						回复人：${leavewords.reUser.name} 时间：
						<fmt:formatDate value="${leavewords.reDate}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</h6>
					<div>回复内容：${leavewords.reContent}</div> --%>
	</li>
	</c:forEach>
	<c:if test="${fn:length(page.list) eq 0}">
		<li>暂时还没有人留言！</li>
	</c:if>
	</ul>
	<script type="text/javascript">
		uParse('.ueditorcontent', {
			rootPath : '${ctxStatic}/ueditor/'
		})
	</script>
	<div class="pagination">${page}</div>
	<h4>我要留言</h4>
	<form:form id="inputForm" action="${ctx}/leavewords" method="post"
		class="form-horizontal">
		<input type="hidden" id="editorValue" name="editorValue" value="" />
		<input type="hidden" id="imghtml" name="imghtml" value="" />
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


		<div id="editor" type="text/plain"
			style="width: 920px; height: 200px;"></div>

		<div class="accordion" id="accordion2" style="margin-top: 3px;">
			<div class="accordion-group">
				<div class="accordion-heading">
					<a class="accordion-toggle" data-toggle="collapse"
						data-parent="#accordion2" href="#collapseOne"> 图片 </a>
				</div>
				<div id="collapseOne" class="accordion-body collapse in">
					<div class="accordion-inner" id="imgss">

						<div id="masonry thumbnails" class="container-fluid">
							<%-- <c:forEach items="${leavewords.imgs}" var="img" varStatus="status"> --%>


							<%-- </c:forEach> --%>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div style="margin-left: 540px; margin-top: 12px;"
			class="control-group">
			<label class="control-label">验证码:</label>
			<div class="controls">
				<sys:validateCode name="validateCode" />
			</div>
		</div>
		<div id="validateCodeError" style="margin-left: 750px"></div>
		<div class="form-actions" style="margin-top: 10px;">
			<input class="btn" type="button" value="提 交"
				style="margin-left: 600px;" id="leavewordssubmit" />&nbsp;
		</div>
		<div id="messageBox" class="alert alert-error" style="display: none">输入有误，请先更正。</div>
		<!-- <input type="hidden" id="content" name="content" value="" /> -->


	</form:form>





	<!-- 删除确认 -->
	<div class="modal hide fade" id="delete"
		style="display: block; width: 400px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
			<h3>提醒</h3>
		</div>
		<div class="modal-body">
			<p>是否删除？</p>
		</div>
		<form method="post" action="${ctx}/leavewords/delete"
			id="leavewordidpost">
			<input type="hidden" id="leavewordid" name="leavewordid" value="" />
		</form>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			<button id="formsubmit" class="btn btn-primary" aria-hidden="true"
				onClick="post()">确定</button>
		</div>
	</div>



	<!-- 轮播框 -->
	<div class="modal hide fade" id="play"
		style="display: block; width: 700px; top: 15%; left: 45%">
		<!-- <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h3>提醒</h3>
			</div> -->
		<!-- 轮播组件 -->
		<div id="myCarousel" class=" slide">
			<ol class="carousel-indicators">
				<!-- <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li> -->
			</ol>
			<!-- Carousel items -->
			<div class="carousel-inner"">
				<!-- <div class="active item">…</div>
						<div class="item">…</div>
						<div class="item">…</div> -->
			</div>
			<!-- Carousel nav -->
			<!-- <a class="carousel-control left" href="#myCarousel"
					style="margin-top: 3px" data-slide="prev">&lsaquo;</a> <a
					class="carousel-control right" href="#myCarousel" data-slide="next"
					style="margin-top: 6px">&rsaquo;</a>  -->
		</div>
		<!--  <div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
				<button id="formsubmit" class="btn btn-primary" aria-hidden="true"
					onClick="post()">确定</button>
			</div>  -->
	</div>


	<!-- 实例化编辑器 -->
	<script type="text/javascript">
		var commenteditor;
		var ue = UE.getEditor('editor');
		ue
				.ready(function() {
					ue
							.addListener(
									'beforeInsertImage',
									function(t, arg) {

										$('.accordion').collapse('show');

										for ( var i = 0; i < arg.length; i++) {
											$("#imgss .container-fluid")
													.append(
															"<div class=\"box thumbnail\" id=\"\"> <img id=\"pictrue\" alt=\"\" src=\"${pageContext.request.contextPath}/servlet/ImgDisplayServlet?path="
																	+ arg[i].src
																	+ "\" /> </div>");
										}
										/* //将地址赋值给相应的input,只去第一张图片的路径
										$(“#pictrue”).attr(“value”, arg[0].src);
										//图片预览
										$(“#picview”).attr(“src”,”/Public/”+arg[0]['src']); */
									});
				});
		$("#leavewordssubmit").click(function() {
			var html = $("#imgss .container-fluid").html();

			$('#imghtml').val(html);
			/* 	$("#content").val(arr); */
			$("#inputForm").submit();
		});

		function deleteFunciton(value) {
			$('#delete').modal('show');
			$('#leavewordid').val(value);
		}
		function post() {
			$("#leavewordidpost").submit();
		}
		//三级级回复按钮
		function reply_img_click3(reply_id) {
			var value = $("#replyeditor").length;
			var string = "#" + reply_id;
			var name = "#" + reply_id + " #reply_name";
			if (value > 0) {
				var commenteditor_id = $("#replyeditor").val();

				UE.getEditor('replyeditor', {
					toolbars : []
				}).destroy();
				$("#replyform").remove();
				//$("#commenteditor").remove();
				//$("#conmment-form-actions").remove();

				if (reply_id != commenteditor_id) {
					$(string)
							.parent()
							.after(
									" <form id=\"replyform\"><script  id=\"replyeditor\" value=\""+reply_id+"\" type=\"text/plain\" style=\"width: 558px; height: 100px; margin-top:5px\"><font color=\"grey\"> 回复  "
											+ $(name).val()
											+ "  :</font></\script><div class=\"form-actions\" id=\"conmment-form-actions\" style=\"margin-top: 3px;height:7px;width:520px\"><input class=\"btn\" type=\"button\" value=\"提 交\" style=\"margin-left: 450px; margin-top:-15px\" onclick=\"replysubmit('"
											+ $(string)
											.parent().attr('id')
											+ "')\" />&nbsp;</div></form>");
					UE.getEditor('replyeditor', {
						toolbars : []
					});
					$('#replyeditor').val(reply_id);
				}
			} else {
				$(string)
						.parent()
						.after(
								" <form id=\"replyform\"><script id=\"replyeditor\" value=\""+reply_id+"\" type=\"text/plain\" style=\"width: 558px; height: 100px; margin-top:5px\"><font color=\"grey\"> 回复  "
										+ $(name).val()
										+ "  :</font></\script><div class=\"form-actions\" id=\"conmment-form-actions\" style=\"margin-top: 3px;height:7px;width:520px\"><input class=\"btn\" type=\"button\" value=\"提 交\" style=\"margin-left: 450px; margin-top:-15px\" onclick=\"replysubmit('"
										+ $(string)
										.parent().attr('id')
										+ "')\" />&nbsp;</div></form>");
				UE.getEditor('replyeditor', {
					toolbars : []
				});
				$('#replyeditor').val(reply_id);
			}
		}
		//二级回复按钮
		function reply_img_click(reply_id) {
			var value = $("#replyeditor").length;
			var string = "#" + reply_id;
			var name = "#" + reply_id + " #reply_name";
			if (value > 0) {
				var commenteditor_id = $("#replyeditor").val();

				UE.getEditor('replyeditor', {
					toolbars : []
				}).destroy();
				$("#replyform").remove();
				//$("#commenteditor").remove();
				//$("#conmment-form-actions").remove();

				if (reply_id != commenteditor_id) {
					$(string)
							.after(
									" <form id=\"replyform\"><script  id=\"replyeditor\" value=\""+reply_id+"\" type=\"text/plain\" style=\"width: 558px; height: 100px; margin-top:5px\"><font color=\"grey\"> 回复  "
											+ $(name).val()
											+ "  :</font></\script><div class=\"form-actions\" id=\"conmment-form-actions\" style=\"margin-top: 3px;height:7px;width:520px\"><input class=\"btn\" type=\"button\" value=\"提 交\" style=\"margin-left: 450px; margin-top:-15px\" onclick=\"replysubmit('"
											+ reply_id
											+ "')\" />&nbsp;</div></form>");
					UE.getEditor('replyeditor', {
						toolbars : []
					});
					$('#replyeditor').val(reply_id);
				}
			} else {
				$(string)
						.after(
								" <form id=\"replyform\"><script id=\"replyeditor\" value=\""+reply_id+"\" type=\"text/plain\" style=\"width: 558px; height: 100px; margin-top:5px\"><font color=\"grey\"> 回复  "
										+ $(name).val()
										+ "  :</font></\script><div class=\"form-actions\" id=\"conmment-form-actions\" style=\"margin-top: 3px;height:7px;width:520px\"><input class=\"btn\" type=\"button\" value=\"提 交\" style=\"margin-left: 450px; margin-top:-15px\" onclick=\"replysubmit('"
										+ reply_id
										+ "')\" />&nbsp;</div></form>");
				UE.getEditor('replyeditor', {
					toolbars : []
				});
				$('#replyeditor').val(reply_id);
			}
		}

		//留言(一级)回复按钮点击事件

		function comment(id) {

			var value = $("#replyeditor").length;
			if (value > 0) {
				var commenteditor_id = $("#replyeditor").val();

				UE.getEditor('replyeditor', {
					toolbars : []
				}).destroy();
				$("#replyform").remove();
				//$("#commenteditor").remove();
				//$("#conmment-form-actions").remove();

				if (id != commenteditor_id) {
					/* var string = ".btn-group #" + id; */

					var string = "#replys_" + id;

					$(string)
							.after(
									" <form id=\"replyform\"><script id=\"replyeditor\" value=\""+id+"\" type=\"text/plain\" style=\"width: 558px; height: 100px; margin-top:5px\"></\script><div class=\"form-actions\" id=\"conmment-form-actions\" style=\"margin-top: 3px;height:7px;width:520px\"><input class=\"btn\" type=\"button\" value=\"提 交\" style=\"margin-left: 450px; margin-top:-15px\" onclick=\"replysubmit('"
											+ id + "')\" />&nbsp;</div></form>");
					UE.getEditor('replyeditor', {
						toolbars : []
					});
					$('#replyeditor').val(id);
				}
			} else {
				/* var string = ".btn-group #" + id; */
				var string = "#replys_" + id;
				$(string)
						.after(
								" <form id=\"replyform\"><script id=\"replyeditor\" value=\""+id+"\" type=\"text/plain\" style=\"width: 558px; height: 100px; margin-top:5px\"></\script><div class=\"form-actions\" id=\"conmment-form-actions\" style=\"margin-top: 3px;height:7px;width:520px\"><input class=\"btn\" type=\"button\" value=\"提 交\" style=\"margin-left: 450px; margin-top:-15px\" onclick=\"replysubmit('"
										+ id + "')\" />&nbsp;</div></form>");
				UE.getEditor('replyeditor', {
					toolbars : []
				});
				$('#replyeditor').val(id);
			}
		}
		/* 
		
		$("#commenteditor-comment")
				.click(
						function() {

							/* var value = $('#commenteditor').length;
							if (value > 0) {
								$("#commenteditor").remove();
								$("#conmment-form-actions").remove();
							} else { */
		/* 	
			$(".comment-group").this.
					.append(
							" <div id=\"commenteditor\" type=\"text/plain\" style=\"width: 558px; height: 100px; margin-top:5px\"></div><div class=\"form-actions\" id=\"conmment-form-actions\" style=\"margin-top: 3px;height:10px\"><input class=\"btn\" type=\"button\" value=\"提 交\" style=\"margin-left: 450px; margin-top:-9px\" id=\"commentsubmit\" />&nbsp;</div>");
			var commenteditor = UE.getEditor(
					'commenteditor', {
						toolbars : [
						]
					}); */
		/* }
		});  */
	</script>
	</div>
	<span></span>
</body>
</html>