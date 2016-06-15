var mainApplicationModuleName = 'parliament';

var underscore = angular.module('underscore', []);
underscore.factory('_', ['$window', function() {
  return $window._;
}]);  

var mainApplicationModule = angular.module(mainApplicationModuleName,
  ['ngResource','ui.router','ngCookies','underscore','main','user','act','user1','amendment','session','angular-loading-bar']);

//tell search engine crawlers that this is single page application
//in order to wait rendering to be done Hashbangs.Hashbangs routing scheme
mainApplicationModule.config(['$locationProvider', function($locationProvider){
  $locationProvider.hashPrefix('!');
}]);
mainApplicationModule.filter('unsafe', function($sce) { return $sce.trustAsHtml; });
if (window.location.hash === '#_=_') window.location.hash = '#!'; //oauth links

//binding function to document-ready event
angular.element(document).ready(function(){
  angular.bootstrap(document, [mainApplicationModuleName]); //initiate a nwe angularjs application using the main application module
});

