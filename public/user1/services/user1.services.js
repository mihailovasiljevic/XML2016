angular.module('user1').factory('User', ['$resource',
	function($resource){

		return $resource('/api/user1/:user1Id', {
			user1Id : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}]);
