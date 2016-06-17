angular.module('amendment').factory('Amandman', ['$resource',
	function($resource){

		return $resource('/api/amendments/:amendmentId', {
			amendmentId : '@_id'
		  }, {
		    update : {
		      method: 'PUT'
		    }
		  });

}])
.factory('AmandmanXhtml',['$resource',
    function($resource){
        return $resource('/api/amandments/xhtml/:uri', {
            uri: '@_id'
        }, {
            update: {
                method: 'PUT'
            }
        });
    }]).factory('AmandmanPdf',['$resource',
    function($resource){
        return $resource('/api/amandments/pdf/:uri', {
            uri: '@_id'
        }, {
            update: {
                method: 'PUT'
            }
        });
    }]);