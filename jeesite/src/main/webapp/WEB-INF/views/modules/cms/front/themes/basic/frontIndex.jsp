<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>首页</title>
<%-- 	<meta name="decorator" content="cms_default_${site.theme}"/>
	<meta name="description" content="JeeSite ${site.description}" />
	<meta name="keywords" content="JeeSite ${site.keywords}" /> --%>
<script data-baseurl="./" data-main="${ctxStatic}/requirejs/config/require.config.js" src="${ctxStatic}/requirejs/require.js" id="main"></script>
</head>
<body class="sitesection-home" >
	<div class="site-wrapper has-sticky-footer">
		<header class="header--home" id="header" role="banner">
			<nav class="navbar" role="navigation">
				<div class="container">


					<div class="navbar-header">
						<img class="site-branding" src="static/img/致橡树_logo.png" style=""></img>
						<a id="site-nav-toggle" class="site-nav-toggle"></a>
					</div>




					<div id="site-nav">
						<nav role="navigation">
							<ul class="navbar-nav navbar-nav--left">


								<li class="nav-item-home active"><a
									style="font-size: 15px;" href="f">主页</a></li>

								<shiro:user>
									<c:forEach items="${fnc:getMainNavList(site.id)}"
										var="category" varStatus="status">
										<c:if test="${status.index lt 6}">
											<c:set var="menuCategoryId" value=",${category.id}," />
											<li
												class="nav-item-home ${requestScope.category.id eq category.id||fn:indexOf(requestScope.category.parentIds,menuCategoryId) ge 1? 'active':'' }"><a
												href="${category.url}" target="${category.target}"
												style="font-size: 15px;">${category.name}</a></li>
										</c:if>
									</c:forEach>
								</shiro:user>



							</ul>
							<ul class="navbar-nav navbar-nav--right">
							
							<shiro:guest>
								<li><a class="js-auth-trigger" data-initial="signin"
									style="font-size: 15px;">注册</a></li>
								<li><a class="js-auth-trigger" style="font-size: 15px;"
									data-toggle="modal" data-target="#loginModal">登陆</a></li>
									
							</shiro:guest>		
							<shiro:user>
							
								<div class="uns_box">
										<div class="menu">
											<li id="i_menu_profile_btn" class="u-i i_user">
												<a class="i-link">
													<img class="i_face" src="static/img/head.jpg" />
												</a>
												<div id="i_menu_profile" class="i_menu" style="display: none;">
													<div class="info clearfix">
														<div class="uname">
															<b>JJLin</b>
														</div>
													</div>
												</div>
											</li>
										</div>
									</div>
								</ul>
								<script>
									
								</script>
							
							
							 </shiro:user>
							</ul>
						</nav>
					</div>
				</div>
			</nav>
		</header>
		
		<div id="content" ng-view></div> 

	<!-- Modal 登陆弹出框-->
	<div id="loginModal" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" style="font-family: '微软雅黑';opacity:0.95;">
		<div class="modal-dialog" role="document"
			style="transform: scale(0.88, 1)">
			<div class="modal-content">
				<div class="auth-box modal-pane-join">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">登陆</h4>
					</div>
					<div class="modal-body">

						<%-- ${ctxadmin}/login --%>
						<form id="loginForm" class="" novalidate="novalidate" role="form"
							action="${ctxadmin}/login" method="post">
							<div class="form-groups">

								<input type="hidden" name="loginplace" id="loginplace"
									value="${fns:getFrontPath()}">
								<div class="form-group ">
									<label class="sr-only" for="username">用户名</label> <input
										type="text" id="username" name="username"
										class="form-control form-control--first" placeholder="用户名">
									<!-- <div id="usernameerror"></div> -->
								</div>
								<div class="form-group">
									<label for="password" class="sr-only">密码</label> <input
										type="password" id="password" name="password"
										class="form-control" placeholder="密码">
								</div>
								<div class="form-group clearfix">

									<!-- <label  for="validateCode" class="sr-only">验证码</label> -->
									<sys:validateCode name="validateCode"
										inputCssStyle="margin-bottom:0;" />
									&nbsp;&nbsp;
								</div>
								<%-- <div class="control-group text-center">
								<label for="rememberMe" title="下次不需要再登录" class="sr-only" ><input
											type="checkbox" id="rememberMe" name="rememberMe"
											${rememberMe ? 'checked' : ''} /> 记住我</label>

									</div>
							
							</div> --%>
						</form>
					</div>

				</div>
				<div class="modal-footer"
					style="padding-right: 0px; padding-left: 0px">
					<!-- <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> -->
					<button id="formsubmit"
						class="btn btn-block btn-primary btn-submit" href="#">登陆</button>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
