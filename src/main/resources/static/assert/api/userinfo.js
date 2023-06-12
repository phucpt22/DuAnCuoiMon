app.controller("userinfo-ctrl", function($scope, $http) {

    $scope.userInfo={}
    $scope.initialize = function() {
        $http.get("/rest/users/all").then((resp) => {
            $scope.items = resp.data;
        });
        $scope.getUserInfoLogin()
    };

    $scope.getUserInfoLogin=function (){
        $http.get('/rest/users/userinfo-login').then(resp=>{
            $scope.userInfo=resp.data
        })
    }
    $scope.initialize();


});
