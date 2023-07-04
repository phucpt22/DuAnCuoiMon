app.controller("order-ctrl", function($scope, $http) {
    $scope.items_order = [];
    $scope.items_order_detail = [];
    $scope.items = [];
    $scope.cates = [];
    $scope.cates = [];
    $scope.form = {};

    $scope.initialize = function() {
        $http.get("/rest/ordersall").then((resp) => {
            $scope.items_order = resp.data;
        });
        $http.get("/rest/orderdetailall").then((resp) => {
            debugger;
            $scope.items_order_detail = resp.data;
            console.log($scope.items_order_detail)
        });

    };
    $scope.show = function(item) {
        //debugger;
        //console.log(${item.order.id});
        $http
            .get(`/rest/orderdetailall/${item.id}`, item)
            .then((resp) => {
                //debugger;
                $scope.items = angular.copy(resp.data);
            });
    };
    $scope.initialize();


    $scope.pager_order = {
        page: 0,
        size: 10,
        get items() {
            var start = this.page * this.size;
            return $scope.items_order.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil((1.0 * $scope.items_order.length) / this.size);
        },
        first(){
            this.page=0;
        },
        prev(){
            this.page--;
            if(this.page<0){
                this.first();
            }
        },
        next(){
            this.page++;
            if(this.page>=this.count){
                this.last();
            }
        },
        last(){
            this.page=this.count-1;
        },
    };

    $scope.pager_order_detail = {
        page: 0,
        size: 5,
        get items() {
            var start = this.page * this.size;
            return $scope.items_order_detail.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil((1.0 * $scope.items_order_detail.length) / this.size);
        },
        first(){
            this.page=0;
        },
        prev(){
            this.page--;
            if(this.page<0){
                this.first();
            }
        },
        next(){
            this.page++;
            if(this.page>=this.count){
                this.last();
            }
        },
        last(){
            this.page=this.count-1;
        },
    };
});
