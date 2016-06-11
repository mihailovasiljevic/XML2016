angular.module('player').factory('Player', ['$resource',
	function($resource){

		return $resource('/api/player/:playerId', {
			playerId : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}]);