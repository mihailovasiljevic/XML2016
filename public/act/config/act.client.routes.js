angular.module('act').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) { 

	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('main', {
        url: '/main',
        templateUrl: 'public/act/views/main.html',
      })
     
    
}]);