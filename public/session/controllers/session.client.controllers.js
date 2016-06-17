angular.module('session')
  .controller('SessionController', ['$scope', '$rootScope', '$timeout','$location','$state','Main','Search','SessionService',
    function($scope,$rootScope, $timeout,$location,$state, Main, Search, SessionService){

        //console.log(playSession);
        var sednica = new SessionService();
        var checkSednica = function(callback){
            sednica.$get(function(response){

                console.log(JSON.stringify(response));
                callback(response.map);
            }, function(errorResponse){
                alert(errorResponse);
                console.log(JSON.stringify(errorResponse));
            });
        }

        

        var acts = new Main({
            criteria: 'predlozen'
        });

        $scope.sessionStarted = false;
        $scope.actForVoting= {};

        $scope.find = function(){
            checkSednica(function(sednica){
                if(sednica.session == "SESSION_STARTED"){

                     $scope.results = Main.query(
                        function(response){
                            for(var i = 0; i < $scope.results.length; i++){
                                $scope.results[i].sessionStarted = true;
                            }

                            $scope.listForShowing = [];
                            for(var i = 0; i < $scope.results.length; i++) {
                                if($scope.results[i].status != "povucen") {
                                    $scope.listForShowing.push($scope.results[i]);
                                }
                            }
                            $scope.results = $scope.listForShowing;
                            $scope.sessionDescription = "Počela sednica za dan " + sednica.date + " u "+sednica.time+" . Na glasanje se mogu staviti sledeći akti: ";
                            $scope.sessionStarted = true;
                            $scope.error = "";
                    });
                }else{
                    $scope.results = Main.query(
                        function(response){
                            for(var i = 0; i < $scope.results.length; i++){
                                $scope.results[i].sessionStarted = false;
                            }

                            $scope.listForShowing = [];
                            for(var i = 0; i < $scope.results.length; i++) {
                                if($scope.results[i].status != "povucen") {
                                    $scope.listForShowing.push($scope.results[i]);
                                }
                            }
                            $scope.results = $scope.listForShowing;
                            $scope.sessionDescription = "";
                            $scope.error = "";
                            $scope.sessionStarted = false;

                    });
                }
            });

        };

        $scope.start = function(){
            var date = new Date();
            var today = date.getDay()+"."+date.getMonth()+"."+date.getFullYear()+".";
            var hour = date.getHours()+":"+date.getMinutes();
            
            var sednica = new SessionService({
                data: "START",
                date: today,
                time: hour
            });

            sednica.$save(function(response){
                $scope.sessionDescription = "Počela sednica za dan " + today + " u "+hour+" . Na glasanje se mogu staviti sledeći akti: ";
                for(var i = 0; i < $scope.results.length; i++){
                    $scope.results[i].sessionStarted = true;
                }
                $scope.sessionStarted = true;
            }, function(errorResponse){
                alert(errorResponse);
                console.log(JSON.stringify(errorResponse));
            });            
        }

        $scope.stop = function(){
            var sednica = new SessionService({
                data: "STOP"
            });
            sednica.$save(function(response){
                var date = new Date();
                var today = date.getDay()+"."+date.getMonth()+"."+date.getFullYear()+".";
                var hour = date.getHours()+":"+date.getMinutes();
                $scope.sessionDescription = "Sednica završena dana " + today + " u "+hour+" . ";
                for(var i = 0; i < $scope.results.length; i++){
                    $scope.results[i].sessionStarted = false;
                }
                $scope.sessionStarted = false;
            }, function(errorResponse){
                alert(errorResponse);
                console.log(JSON.stringify(errorResponse));
            });   

        }

        $scope.vote = function(act){
            $scope.actForVoting = act;
            var uri = act.uri.trim();
            alert(uri);
            $state.go('main.voting',{actURI:act.uri});
        }
    }])
    .controller('VotingController', ['$scope', '$rootScope', '$timeout','$location','$state','$stateParams','Main','Search','SessionService','VoteService',
    function($scope,$rootScope, $timeout,$location,$state,$stateParams, Main, Search, SessionService, VoteService){



        $scope.find = function(){
            var act = new Main();
            
            act.$get({uri:$stateParams.actURI}, function(response){
                $scope.act = response;
            });
        }

        $scope.vote = function(){
            var vote = new VoteService({
                glasaliZa : $scope.glasaliZa,
                glasaliProtiv : $scope.glasaliProtiv
            });

            vote.$save({sessionId : $stateParams.actURI}, function(response){
                if(response.map.success){
                    $state.go("main.amendmentVoting", {actURI:$stateParams.actURI})
                }else{  
                    alert(response);
                }
            });
        };

    }])
    .controller('AmendmentSessionController', ['$scope', '$rootScope', '$timeout','$location','$state','$stateParams','Main','Search','SessionService','VoteService','AmendmentFactory','Amandman','AmendmentVoteService',
    function($scope,$rootScope, $timeout,$location,$state,$stateParams, Main, Search, SessionService, VoteService,AmendmentFactory, Amandman,AmendmentVoteService){



        $scope.find = function(){
            var act = new Main();
            var amendments = new AmendmentFactory();
            act.$get({uri:$stateParams.actURI}, function(response){
                $scope.act = response;

                console.log("URI " + $stateParams.actURI);
                amendments.$get({amendmentId:$stateParams.actURI}, function(response){
                    $scope.amendments = response.map.lista.myArrayList;
                    alert(JSON.stringify(response));
                }, function(errorResponse){
                    alert(JSON.stringify(errorResponse));
                });

            });
        }
        $scope.initialize = function(){
             var act = new Main();
            
            act.$get({uri:$stateParams.actURI}, function(response){
                $scope.act = response;
                var amendment = new Amandman();
                amendment.$get({amendmentId:$stateParams.amendmentURI}, function(response){
                    $scope.amendment = response;
                });              
            });





        }
        $scope.vote = function(){

            var vote = new AmendmentVoteService({
                glasaliZa : $scope.glasaliZa,
                glasaliProtiv : $scope.glasaliProtiv
            });

            vote.$save({sessionId : $stateParams.actURI, amendmentId : $stateParams.amendmentURI}, function(response){
                if(response.map.success){
                    $state.go("main.amendmentVoting", {actURI:$stateParams.actURI})
                }else{  
                    alert(response);
                }
            });
        };

        $scope.amendmentVote = function(amendment){
            $state.go("main.realAmendmentVoting",{actURI:$stateParams.actURI, amendmentURI:amendment.map.oznaka});
        }

    }]);