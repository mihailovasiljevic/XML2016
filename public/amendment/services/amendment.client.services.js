angular.module('amendment').factory('Amandman', ['$resource',
	function($resource){

		return $resource('/api/amendments/:amendmentId', {
			amendmentId : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}]);