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
       .state('main.act', {
        url: '',
        templateUrl: 'public/act/views/addAct.html'
      })
      .state('main.amandmans',{
    	  url: 'amendments',
    	  templateUrl: 'public/amendment/views/listAmendments.html'
      })
      .state('main.amendment',{
    	  url:'amendments',
    	  templateUrl: 'public/amendment/views/addAmendment.html'
      })
      .state('main.search.results', {
        url: 'search',
        templateUrl: 'public/main/views/searchResults.html'
      })
      .state('main.session', {
        url: '',
        templateUrl: 'public/session/views/session.html'
      });
     ;
  }
]);

