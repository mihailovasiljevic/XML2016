angular.module('act').factory('Propis', ['$resource',
	function($resource){

		return $resource('/api/act/:actId', {
			userId : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}]);