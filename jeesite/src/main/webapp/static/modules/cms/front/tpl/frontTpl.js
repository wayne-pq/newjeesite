define(['jquery', 'angularjs', 'text!' + ctxStaticTpl + '/frontTpl.html', 'bootstrap', 'jquery-validation', 'css!static/modules/cms/front/css/site.min.css'], function($, angular, tpl) {

	//根据权限拉取菜单
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: "f/getMainNavList",
		dataType: 'json',
		success: function(data) {
			if (null != data && 　data.flag == "success") {
				$("#site-nav #nav_index").after(data.buffer);
			}
		},
		error: function(req, textStatus, errorThrown) {
			if (req.responseJSON) {
				var validateObj = req.responseJSON;
				if (validateObj.code) {
					jAlert(validateObj.code, "错误");
				}
			}
		}
	});

	//登陆表单验证
	$("#loginForm")
		.validate({
			//在input失去焦点时验证
			onfocusout: function(element) {
				$(element).valid();
			},
			//在敲击键盘时验证
			onkeyup: false,
			rules: {
				username: "required",
				password: "required",
				validateCode: {
					required: true,
					remote: "/jeesite/servlet/validateCodeServlet"
				}
			},
			messages: {
				username: {
					required: "请填写用户名."
				},
				password: {
					required: "请填写密码."
				},
				validateCode: {
					remote: "验证码不正确.",
					required: "请填写验证码."
				}
			},
			errorElement: "em",
			errorPlacement: function(error, element) {
				// Add the `help-block` class to the error element
				error.addClass("help-block");

				// Add `has-feedback` class to the parent div.form-group
				// in order to add icons to inputs
				element.parents(".form-group").addClass("has-feedback");

				if (element.prop("type") === "checkbox") {
					error.insertAfter(element.parent("label"));
				} else {
					if (null != error[0].firstChild) {
						element[0].value = "";
						element[0].placeholder = error[0].firstChild.data;
					}
					/*  */
					//error.insertAfter( element );
				}

				// Add the span element, if doesn't exists, and apply the icon classes to it.
				if (!element.next("span")[0]) {
					if (element[0].id != "validateCode")
						$("<span class='entypo entypo-cross form-control-feedback'></span>").insertAfter(element);
					else
						$("<span class='entypo entypo-cross form-control-feedback' style='margin-right:6.8em;margin-top:-1.1em;'></span>").insertAfter(element);
				}
			},
			success: function(label, element) {
				// Add the span element, if doesn't exists, and apply the icon classes to it.
				if (!$(element).next("span")[0]) {
					$("<span class='entypo entypo-check form-control-feedback'></span>").insertAfter($(element));
				}
			},
			highlight: function(element, errorClass, validClass) {
				$(element).parents(".form-group").addClass("has-error").removeClass("has-success");
				$(element).next("span").addClass("entypo-cross").removeClass("entypo-check");
			},
			unhighlight: function(element, errorClass, validClass) {
				$(element).parents(".form-group").addClass("has-success").removeClass("has-error");
				$(element).next("span").addClass("entypo-check").removeClass("entypo-cross");
			}
		});

	$(document).keydown(function(e) { //键盘回车执行登录
		var evt = window.event ? window.event : e;
		if (evt.keyCode === 13) {
			$("#loginForm").submit();
		}
	});

	$("#formsubmit").click(function() {
		$("#loginForm").submit();
	});

	$("#logout").click(function() {
		$("#hidden").submit();
	});

	$(".i_face").hover(
		function() {
			$('.i_face').addClass("scale-in");
			$('#i_menu_profile').attr("style", "display: block;opacity:0.45;border-radius:10px;");
		},
		function() {
			$('.i_face').removeClass("scale-in");
			$('#i_menu_profile').attr("style", "display: none;");
		})

	//angular会自动根据controller函数的参数名，导入相应的服务
	return {
		controller: function($scope, $routeParams, $http, $interval) {
			console.log($routeParams); //获得路由中的参数
			$scope.date = '2015-07-13';
		},
		tpl: tpl
	};

});