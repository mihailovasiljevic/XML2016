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
      .state('view.addAct', {
    	  url:'/addAct',
    	  templateUrl: 'public/act/views/addAct.html'
      })
      .state('view.main', {
    	  url:'/main',
    	  templateUrl: 'public/act/views/main.html'
      })
    
}]);