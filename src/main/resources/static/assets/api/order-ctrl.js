app.controller("order-ctrl", function($scope, $http) {
    $scope.items_order = [];
    $scope.form = {};
    $scope.items_order_detail=[];


    $scope.initialize = function() {
        $http.get("/rest/orders").then((resp) => {
            $scope.items_order = resp.data;
            $scope.getOrdersByStatus("Chờ xác nhận")
        });

    };
    $scope.getOrdersByStatus = function(status) {
        $http.get("/rest/orders/orders", { params: { status: status } })
            .then((resp) => {
                $scope.items_order = resp.data;
                $scope.items_order_detail=[];
                $scope.show(null);
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
        var newStatus;

        if (item_1.status_order === "Chờ xác nhận") {
            newStatus = "Đang giao";
        } else if (item_1.status_order === "Đang giao") {
            newStatus = "Đã giao";
        }

        if (newStatus) {
            item_1.status_order = newStatus;

            $http.put(`/rest/orders/${item_1.id}`, item_1)
                .then((resp) => {
                    let index = $scope.items_order.findIndex((o) => o.id == item_1.id);
                    if (index !== -1) {
                        $scope.items_order[index].status_order = newStatus;
                        alert("Cập nhật sản phẩm thành công!");
                        $scope.getOrdersByStatus(newStatus);
                    } else {
                        alert("Không tìm thấy đơn hàng trong danh sách!");
                    }
                })
                .catch((error) => {
                    alert("Lỗi cập nhật sản phẩm!");
                    console.log("Error", error);
                });
        } else {
            alert("Trạng thái đơn hàng không hợp lệ!");
        }
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



    $scope.searchOrders = function(username, status) {
        $http.get("/rest/orders/search", { params: { username: username, status: status } })
            .then(function(resp) {
                $scope.items_order = resp.data;
                $scope.items_order_detail = [];
                $scope.show(null);
            })
            .catch(function(error) {
                console.log("Error", error);
            });
    };









    $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            const start = this.page * this.size;
            return $scope.items_order.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items_order.length / this.size);
        },
        first() {
            this.page = 0;
        },
        prev() {
            this.page--;
            if (this.page < 0) {
                this.last();
            }
        },
        next() {
            this.page++;
            if (this.page >= this.count) {
                this.first();
            }
        },
        last() {
            this.page = this.count - 1;
        }
    }
});
