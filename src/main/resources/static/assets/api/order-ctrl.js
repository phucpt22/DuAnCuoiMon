app.controller("order-ctrl", function($scope, $http) {
    $scope.items_order = [];


    $scope.initialize = function() {
        $http.get("/rest/orders").then((resp) => {
            $scope.items_order = resp.data;
        });

    };
    $scope.initialize();


});
