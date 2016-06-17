angular.module('main').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) {    
   
	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('main.voting', {
        url: ':actURI',
        templateUrl: 'public/session/views/voting.html'
      })
     ;
  }
]);
