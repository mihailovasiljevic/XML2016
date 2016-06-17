angular.module('main')
    
 .factory('SessionService', ['$resource',function($resource){
		return $resource('/api/session/:sessionId', {
    		userId : '@_id'
           	  }, {
     	    update : {
	      method: 'PUT'
	    }
     });
}]);;