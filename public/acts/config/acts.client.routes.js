angular.module('acts').config(['$urlRouterProvider','$stateProvider',
  function($urlRouterProvider,$stateProvider) {    
   
    $stateProvider
      .state('acts', {
        url: '/acts',
        templateUrl: 'public/acts/views/acts.html'
      })
      .state('searchActs', {
        url: '/search',
        templateUrl: 'public/main/views/search.html'
      })
     ;
  }
]);
