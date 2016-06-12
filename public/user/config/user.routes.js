angular.module('user').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) { 

	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('input', {
        url: '/input',
        templateUrl: 'public/user/views/input.html',
      })
      .state('view', {
        url: '/view',
        templateUrl: 'public/user/views/view.html',
      })
    
}]);