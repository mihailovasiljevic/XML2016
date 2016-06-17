angular.module('main').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) {    
   
	$urlRouterProvider.otherwise('/');
    $stateProvider
      .state('main.voting', {
        url: 'session/vote/:actURI',
        templateUrl: 'public/session/views/voting.html'
      })
      .state('main.amendmentVoting', {
        url: 'session/vote/:actURI/amendments',
        templateUrl: 'public/session/views/amendments.html'
      })
      .state('main.realAmendmentVoting', {
        url: 'session/vote/:actURI/amendments/:amendmentURI',
        templateUrl: 'public/session/views/amendmentVoting.html'
      })      
     ;
  }
]);
