define(['jquery', 'angularjs', 'text!' + ctxStaticTpl + '/frontLeaveWordsTpl.html', 'bootstrap','ueditor','jquery-masonry','css!static/modules/cms/front/css/site.min.css'], function($, angular, tpl) {

	
//	$('.accordion').collapse('hide');
	
	
	$('#header').attr("class", "header--page");
/*	//拉取page
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: "f/leavewords",
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
				 $('.carousel').carousel(); 
				 $("#allImgUrl").attr("src", data.url);
				$("#path").val(data.url); 
			}
		};
		$.ajax(options);

	}

	 
		留言回复提交ajax
	 
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

				 var list = eval("("+data+")");
				
				
				for ( var i = 0; i < list.length; i++) {
					var name = list[i].name;
					var content = list[i].content;
					$('replys').append(name +" : " + content);
				} 
				UE.getEditor('replyeditor', {
					toolbars : []
				}).destroy();
				$("#replyform").remove();

			}
		};
		$('#replyform').ajaxSubmit(options); //jquery1.9.1的版本中  必须导入 jquery-form.js文件
	}
	
	
	
	
	
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
									 //将地址赋值给相应的input,只去第一张图片的路径
									$(“#pictrue”).attr(“value”, arg[0].src);
									//图片预览
									$(“#picview”).attr(“src”,”/Public/”+arg[0]['src']); 
								});
			});
	$("#leavewordssubmit").click(function() {
		var html = $("#imgss .container-fluid").html();

		$('#imghtml').val(html);
		 	$("#content").val(arr); 
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
				 var string = ".btn-group #" + id; 

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
			 var string = ".btn-group #" + id; 
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
	}*/
	
	//根据权限拉取菜单
	/*$.ajax({
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
	});*/

	//登陆表单验证
	/*$("#loginForm")
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
		})*/

	//angular会自动根据controller函数的参数名，导入相应的服务
	return {
		controller: function($scope, $routeParams, $http, $interval) {
			console.log($routeParams); //获得路由中的参数
			$scope.date = '2015-07-13';
		},
		tpl: tpl
	};

});