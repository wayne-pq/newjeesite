'use strict';

	

	window.ctxStaticTpl = "/jeesite/static/modules/cms/front/tpl/";


(function (win) {

	    	
	    	
		var config = {
			baseUrl: ".",
			paths: {
				
				// css: "trd/requirejs/css",
				requirejs: "static/requirejs/require",
				domReady:"static/requirejs/domReady",
				text: "static/requirejs/text",
				css: "static/requirejs/css",
				jquery: "static/jquery/jquery-2.2.3.min",
				bootstrap: "static/bootstrap/3.3.5/dist/js/bootstrap.min",
				'jquery-form': "static/jquery/jquery-form",
				'jquery-migrate': "static/jquery/jquery-migrate-1.1.1.min",
				'jquery-validation': "static/jquery-validation/1.15.0/jquery.validate.min",
				'jquery-validate-method': "static/jquery-validation/1.15.0/additional-methods.min",
				defult: "static/modules/cms/front/js/defult",
				angularjs: "static/angularjs/angular-1.5.4/angular.min",
				'angular-route': "static/angularjs/angular-1.5.4/angular-route",
				'angular-route-cfg': "static/angularjs/angular-1.5.4/config/router-cfg-version"
			},
			shim: {
				/*
				 * 'bootstrap': { deps:
				 * ["jquery","css!static/bootstrap/3.3.5/dist/css/bootstrap.min.css","css!static/common/jeesite.min.css"
				 * ,"css!static/modules/cms/front/themes/basic/style.css"] }
				 */
				'bootstrap': {
					deps: ["jquery"]
				},
				'jquery-form': {
					deps: ["jquery"]
				},
				'jquery-migrate': {
					deps: ["jquery"]
				},
				'jquery-validation': {
					deps: ["jquery"]
				},
				'jquery-validate-method': {
					deps: ["jquery", "jqueryvalidation"]
				},
				angularjs: {
					exports: 'angular'
				},
				'angular-route': {
					deps: ['angularjs'], //依赖什么模块
					exports: 'ngRouteModule'
				}
			}
		};

	  require.config(config);
	  
	  
	  
	  require(['angularjs', 'angular-route-cfg'], function(angular){
	    angular.bootstrap(document, ['webapp']);
	  });
	  
})(window);