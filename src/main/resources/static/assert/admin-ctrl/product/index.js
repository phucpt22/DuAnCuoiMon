app.controller("product-ctrl", function($scope, $http) {
    $scope.items = [];
    $scope.cates = [];
    $scope.form = {};

    $scope.initialize = function() {
        $http.get("/rest/products").then((resp) => {
            $scope.items = resp.data;
            $scope.items.forEach((item) => {
                item.createDate = new Date(item.createDate);
            });
        });
        $http.get("/rest/categories").then((resp) => {
            $scope.cates = resp.data;
        });
    };
    $scope.search = function(p) {
        //debugger;
        //console.log(${item.order.id});
        $http
            .get(`/rest/products/find/${p.name}`, p)
            .then((resp) => {
                //debugger;
                $scope.items = resp.data;
            });
    };

    $scope.initialize();

    $scope.edit = function(item) {
        $scope.form = angular.copy(item);
        $(".nav-tabs a:eq(0)").tab("show");
    };

    $scope.reset = function() {
        $scope.form = {
            createDate: new Date(),
            image: "cloud-upload.jpg",
            available: true,
        };
    };
    $scope.create = function() {
        var item = angular.copy($scope.form);
        $http
            .post("/rest/products", item)
            .then((resp) => {
                resp.data.createDate = new Date(resp.data.createDate);
                $scope.items.push(resp.data);
                $scope.reset();
                alert("Thêm mới sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi thêm mới sản phẩm!");
                console.log("Error", error);
            });
    };

    $scope.update = function() {
        var item = angular.copy($scope.form);
        $http
            .put("/rest/products/${item.id}", item)
            .then((resp) => {
                var index = $scope.items.findIndex((p) => p.id == item.id);
                $scope.items[index] = item;
                alert("Cập nhật sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi cập nhật sản phẩm!");
                console.log("Error", error);
            });
    };

    $scope.delete = function(item) {

        $http
            .delete("/rest/products/${item.id}", item)
            .then((resp) => {
                var index = $scope.items.findIndex((p) => p.id == item.id);
                $scope.items.splice(index, 1);
                $scope.reset();
                alert("Xóa sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi xóa sản phẩm!");
                console.log("Error", error);
            });
    };

    $scope.imageChanged = function(files) {
        var data = new FormData();
        data.append("file", files[0]);
        $http
            .post("/rest/upload/images", data, {
                transformRequest: angular.identity,
                headers: { "Content-Type": undefined },
            })
            .then((resp) => {
                $scope.form.photo = resp.data.name;
            })
            .catch((error) => {
                alert("Lỗi upload hình ảnh!");
                console.log("Error", error);
            });
    };

    $scope.pager = {
        page: 0,
        size: 10,
        get items() {
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil((1.0 * $scope.items.length) / this.size);
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
