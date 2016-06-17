angular.module('main')
    
 .factory('SessionService', ['$resource',function($resource){
		return $resource('/api/session/:sessionId', {
    		sessionId : '@_id'
           	  }, {
     	    update : {
	      method: 'PUT'
	    }
     });
}])
 .factory('VoteService', ['$resource',function($resource){
		return $resource('/api/session/vote/:sessionId', {
    		sessionId : '@_id'
           	  }, {
     	    update : {
	      method: 'PUT'
	    }
     });
}])
 .factory('AmendmentVoteService', ['$resource',function($resource){
		return $resource('/api/session/:sessionId/:amendmentId', {
    		sessionId : '@_id',
            amendmentId : '@_id'
           	  }, {
     	    update : {
	      method: 'PUT'
	    }
     });
}])
 .factory('AmendmentFactory', ['$resource',function($resource){
		return $resource('/api/session/vote/amendments/:amendmentId', {
    		amendmentId : '@_id'
           	  }, {
     	    update : {
	      method: 'PUT'
	    }
     });
}]);