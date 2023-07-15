app.controller("order-ctrl", function($scope, $http) {
    $scope.items_order = [];
    $scope.form = {};
    $scope.items_order_detail=[];


    $scope.initialize = function() {
        $http.get("/rest/orders").then((resp) => {
            $scope.items_order = resp.data;
        });

    };
    $scope.getOrdersByStatus = function(status) {
        $http.get("/rest/orders/orders", { params: { status: status } })
            .then((resp) => {
                $scope.items_order = resp.data;
            });
    };
    $scope.show = function(item) {
        //debugger;
        //console.log(${item.order.id});
        $scope.form = angular.copy(item);
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
    $scope.change = function (item) {
        var item_1 = angular.copy(item);
        $http
            .put(`/rest/orders/${item_1.id}`, item_1)
            .then((resp) => {
                var index = $scope.items_order.findIndex((o) => o.id == item_1.id);
                if($scope.items_order[index].status_order==="Chờ xác nhận"){
                    $scope.items_order[index].status_order = "Đang giao";
                }else{
                    $scope.items_order[index].status_order = "Đã giao";
                }
                alert("Cập nhật sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi cập nhật sản phẩm!");
                console.log("Error", error);
            });
    };
    $scope.cancel = function (item) {
        var item_1 = angular.copy(item);
        $http
            .put(`/rest/orders/${item_1.id}`, item_1)
            .then((resp) => {
                var index = $scope.items_order.findIndex((o) => o.id == item_1.id);
                    $scope.items_order[index].status_order = "Đã hủy";
                alert("Cập nhật sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi cập nhật sản phẩm!");
                console.log("Error", error);
            });
    };
});
