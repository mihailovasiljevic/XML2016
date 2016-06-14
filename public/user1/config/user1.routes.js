angular.module('user1').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) { 

	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('register1', {
        url: '/register',
        templateUrl: 'public/user1/views/register.html',
      })
      .state('login1', {
        url: '/login',
        templateUrl: 'public/user1/views/login.html',
      })
      .state('view1', {
        url: '/view',
        templateUrl: 'public/user1/views/view.html',
      })
      .state('view.addAct1', {
    	  url:'/addAct',
    	  templateUrl: 'public/act/views/addAct.html'
      })
      .state('view.main1', {
    	  url:'/main',
    	  templateUrl: 'public/act/views/main.html'
      })
      
      .state('main.show1', {
    	  url:'/show1',
    	  templateUrl: 'public/main/views/main.html'
      })
    
}]);





