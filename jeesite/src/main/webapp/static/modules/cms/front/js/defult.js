define(['jquery','bootstrap'], function($) {

	/*function check() {
		$('#usera').popover('hide');
	}　　　*/
	　
	function foo() {

		　　　　　　
		$("[data-toggle='popover']")
		.popover();
		
		$('#usera').popover('show');
		
		
		/* 2秒后执行 */
		 var i = setTimeout("$('#usera').popover('hide');",3000);
		 
		 
		 
		  $("#userSwitch").click(function() {
				 $("#usera").popover('hide');
		  }); 

		　　　　
	}

	　　　　
	return {

		//前面一个是返回的变量（方法）   后面一个指的是前面定义的方法　　　　　　
		foo: foo

		　　　　
	};

	　　
});