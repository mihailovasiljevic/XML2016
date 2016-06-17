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
      .state('main.metadata', {
        url: 'search',
        templateUrl: 'public/main/views/searchMetadata.html'
      })
      .state('main.act', {
        url: 'acts/propose',
        templateUrl: 'public/act/views/addAct.html'
      })
      .state('main.acts', {
        url: 'acts',
        templateUrl: 'public/act/views/listActs.html'
      })
      .state('main.amendment',{
    	  url:'amendments/propose',
    	  templateUrl: 'public/amendment/views/addAmendment.html'
      })
      .state('main.amandmans',{
    	  url: 'amendments',
    	  templateUrl: 'public/amendment/views/listAmendments.html'
      })
      .state('main.search.results', {
        url: 'search',
        templateUrl: 'public/main/views/searchResults.html'
      })
      .state('main.session', {
        url: 'session',
        templateUrl: 'public/session/views/session.html'
      })
      .state('main.showXHTML', {
        url: '/tmp/xhtml/tmp.xhtml',
        templateUrl: 'public/tmp/xhtml/tmp.html'
      })
     ;
  }
]);

