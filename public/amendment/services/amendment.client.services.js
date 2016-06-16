angular.module('amendment').factory('Amandman', ['$resource',
	function($resource){

		return $resource('/api/amendments/:amendmentId', {
			userId : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}]);