angular.module('user').factory('User', ['$resource',
	function($resource){

		return $resource('/api/user/:userId', {
			userId : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}]);


angular.module('user').factory('UserRegister', ['$resource',
                                    	function($resource){

                                    		return $resource('/api/userreg/:userId', {
                                    			userId : '@_id'
                                    		  }, {
                                    		    update : {
                                    		      method: 'PUT'
                                    		    }
                                    		  });

                                    }]);