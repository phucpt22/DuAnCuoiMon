app.controller("category-ctrl", function($scope, $http) {
    $scope.cates = [];
    $scope.form = {};

    $scope.initialize = function() {  
        $http.get("/rest/categories").then((resp) => {
            $scope.cates = resp.data;
        });
    };
    $scope.search = function(c) {
        //debugger;
        //console.log(${item.order.id});
        $http
            .get(`/rest/categories/find/${c.name}`, c)
            .then((resp) => {
                //debugger;
                $scope.items = resp.data;
            });
    };

    $scope.initialize();

    $scope.edit = function(c) {
        $scope.form = angular.copy(c);
      //  $(".nav-tabs a:eq(0)").tab("show");
    };

    $scope.reset = function() {
        $scope.form = {
            createDate: new Date(),
            image: "cloud-upload.jpg",
            available: true,
        };
    };
    $scope.create = function() {
        var c = angular.copy($scope.form);

        $http
            .post("/rest/categories", c)
            .then((resp) => {
                $scope.cates.push(resp.data);
                $scope.reset();
                alert("Thêm mới sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi thêm mới sản phẩm!");
                console.log("Error", error);
            });
    };

    $scope.update = function() {
        var c = angular.copy($scope.form);
        $http
            .put("/rest/categories/${c.id}", c)
            .then((resp) => {
                var index = $scope.cates.findIndex(cate => cate.id == c .id);
                $scope.cates[index] = c; 
                alert("Cập nhật sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi cập nhật sản phẩm!");
                console.log("Error", error);
            });
    };

    $scope.delete = function(item) {

        $http
            .delete("/rest/products/${item.id}")
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
        get items(){
            const start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count(){
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },
        first(){
            this.page = 0;
        },
        prev(){
            this.page--;
            if(this.page<0){
                this.last();
            }
        },
        next(){
            this.page++;
            if(this.page>=this.count){
                this.first();
            }
        },
        last(){
            this.page = this.count-1;
        } 
    }
});
