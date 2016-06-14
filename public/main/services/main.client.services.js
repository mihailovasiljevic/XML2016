angular.module('main').factory('Main',['$resource',
    function($resource){
        return $resource('/api/acts/:uri', {
            uri: '@_id'
        }, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('Search',['$resource',
    function($resource){
        return $resource('/api/acts/search');
    }]);
    