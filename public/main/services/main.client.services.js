angular.module('main').factory('Main',['$resource',
    function($resource){
        return $resource('/api/main/:userId', {
            userId: '@_id'
        }, {
            update: {
                method: 'PUT'
            }
        });
    }]);
    