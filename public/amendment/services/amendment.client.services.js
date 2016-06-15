angular.module('amendment').factory('Amandman', ['$resource',
	function($resource){

		return $resource('/api/amendment/:amendmentId', {
			userId : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}]);