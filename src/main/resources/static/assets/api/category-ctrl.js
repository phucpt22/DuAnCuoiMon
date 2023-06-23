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
            .put("/rest/categories/"+c.id, c)
            .then((resp) => {
                var index = $scope.cates.findIndex(p => p.id == c.id);
                $scope.cates[index] = c; 
                alert("Cập nhật sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi cập nhật sản phẩm!");
                console.log("Error", error);
            });
    };

    $scope.delete = function(c) {
        $http
            .delete("/rest/categories/"+c.id)
            .then((resp) => {
                var index = $scope.cates.findIndex(p => p.id == c.id);
                $scope.cates.splice(index, 1);
                $scope.reset();
                alert("Xóa sản phẩm thành công!");
            })
            .catch((error) => {
                alert("Lỗi xóa sản phẩm!");
                console.log("Error", error);
            });
    };

    $scope.pager = {
        page: 0,
        size: 10,
        get cates(){
            const start = this.page * this.size;
            return $scope.cates.slice(start, start + this.size);
        },
        get count(){
            return Math.ceil(1.0 * $scope.cates.length / this.size);
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
