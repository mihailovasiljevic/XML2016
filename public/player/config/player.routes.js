angular.module('player').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) { 

	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('input', {
        url: '/input',
        templateUrl: 'public/player/views/input.html',
      })
      .state('view', {
        url: '/view',
        templateUrl: 'public/player/views/view.html',
      })
    
}]);