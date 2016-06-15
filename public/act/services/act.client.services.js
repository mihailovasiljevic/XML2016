angular.module('act').factory('Propis', ['$resource',
	function($resource){

		return $resource('/api/acts/:actId', {
			actId : '@_id'
		  }, {
		    update : {
		      method: 'PUT',
		    }
		  });

}]);