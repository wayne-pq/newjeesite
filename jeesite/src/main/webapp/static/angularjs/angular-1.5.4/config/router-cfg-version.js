/**
 * Created by kenkozheng on 2015/7/10.
 * 更适合团队配合的配置版router
 * 彻底解耦，按需加载，router的配置可以放到服务器直出，更便于团队合作
 */
define(['angularjs', 'requirejs', 'angular-route'], function (angular) {

    var app = angular.module('webapp', [
        'ngRoute'
    ]);

    app.config(['$routeProvider', '$controllerProvider',
        function($routeProvider, $controllerProvider) {

            var routeMap = {
                '/index': {                             //路由
                    path: window.ctxStaticTpl+'frontTpl.js'         //模块的代码路径
                    //controller: 'module2Controller'     //控制器名称
                }/*,
                '/leavewords': {                             //路由
                    path: 'static/modules/cms/front/tpl/frontIndex.js',         //模块的代码路径
                    //controller: 'module2Controller'     //控制器名称
                }*/
            };
            var defaultRoute = '/index';              //默认跳转到某个路由
           
            $routeProvider.otherwise({redirectTo: defaultRoute});
            for (var key in routeMap) {
                $routeProvider.when(key, {
                    template: '',
                    controller: routeMap[key].controller,
                    resolve:{
                        keyName: requireModule(routeMap[key].path, routeMap[key].controller)
                    }
                });
            }

            function requireModule(path, controller) {
                return function ($route, $q) {
                    var deferred = $q.defer();
                    require([path], function (ret) {
                        $controllerProvider.register(controller, ret.controller);
                        $route.current.template = ret.tpl;
                        deferred.resolve();
                    });
                    return deferred.promise;
                }
            }

        }]);

    return app;
});