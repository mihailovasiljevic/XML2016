<<<<<<< HEAD
angular.module('user').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) { 

	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('register', {
        url: '/register',
        templateUrl: 'public/user/views/register.html',
      })
      .state('login', {
        url: '/login',
        templateUrl: 'public/user/views/login.html',
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
      
      .state('main.show', {
    	  url:'/show',
    	  templateUrl: 'public/main/views/main.html'
      })
    
}]);





=======
angular.module('user').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) { 

	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('register', {
        url: '/register',
        templateUrl: 'public/user/views/register.html',
      })
      .state('login', {
        url: '/login',
        templateUrl: 'public/user/views/login.html',
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





>>>>>>> 3ab489ad78a31ced89b50e5429b0c0cdbbc4129c
