angular.module('acts').factory('Acts',['$resource',
    function($resource){
        return $resource('/api/acts/:actId', {
            actId: '@_id'
        }, {
            update: {
                method: 'PUT'
            }
        });
    }]);
    