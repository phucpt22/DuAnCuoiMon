app.controller("order-ctrl", function($scope, $http) {
    $scope.items_order = [];
    $scope.items_order_detail=[];


    $scope.initialize = function() {
        $http.get("/rest/orders").then((resp) => {
            $scope.items_order = resp.data;
        });

    };
    $scope.show = function(item) {
        //debugger;
        //console.log(${item.order.id});
        $http
            .get(`/rest/orders/${item.id}`, item)
            .then((resp) => {
                //debugger;
                $scope.items_order_detail = angular.copy(resp.data);
            });
    };
    $scope.initialize();
    $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            const start = this.page * this.size;
            return $scope.items_order.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items_order.length / this.size);
        }
    }
});
