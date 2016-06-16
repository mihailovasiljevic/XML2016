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
    }])
     .factory('MetaData',['$resource',
    function($resource){
        return $resource('/api/acts/search/metaData');
    }]).factory('Xhtml',['$resource',
    function($resource){
        return $resource('/api/acts/xhtml/:uri', {
            uri: '@_id'
        }, {
            update: {
                method: 'PUT'
            }
        });
    }]).factory('Pdf',['$resource',
    function($resource){
        return $resource('/api/acts/pdf/:uri', {
            uri: '@_id'
        }, {
            update: {
                method: 'PUT'
            }
        });
    }]).
    factory('playSession', ['$cookies','$cookieStore', function($cookies, $cookieStore) {
        // read Play session cookie
        var testData= $cookieStore.get('PLAY_SESSION');
        var rawCookie = $cookies['PLAY_SESSION'];
        var rawData = rawCookie.substring(rawCookie.indexOf('-') + 1);
        var session = {};
        _.each(rawData.split("\u0000"), function(rawPair) {
            var pair = rawPair.split(':');
            session[pair[0]] = pair[1];
        });
        return session;
    }]);
  