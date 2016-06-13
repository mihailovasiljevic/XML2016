angular.module('main').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) {    
   
	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('main', {
        url: '/',
        templateUrl: 'public/main/views/main.html'
      })
      .state('main.search', {
        url: 'search',
        templateUrl: 'public/main/views/search.html'
      })
  
}]);
