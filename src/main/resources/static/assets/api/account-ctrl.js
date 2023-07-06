app.controller("account-ctrl", function($scope, $http) {
    $scope.items = [];
    $scope.form = {};
    $scope.accounts = [];

    $scope.initialize = function() {
        $http.get("/rest/users/all").then((resp) => {
            $scope.items = resp.data;
        });
        // $http.get("/rest/accounts").then((resp) => {
        //     $scope.accounts = resp.data;
        // });
    };

    $scope.edit = function(item) {
        $scope.form = angular.copy(item);

    };

    $scope.reset = function() {
        $scope.form = {

        };
    };

    $scope.create = function() {
        var item = angular.copy($scope.form);
        $http
            .post("/rest/users", item)
            .then((resp) => {
                $scope.items.push(resp.data);
                $scope.reset();
                alert("Thêm mới tài khoản thành công!");
            })
            .post("/rest/accounts", item)
            .then((resp) => {
                $scope.items.push(resp.data);
                $scope.reset();
            })
            .catch((error) => {
                alert("Lỗi thêm mới tài khoản!");
                console.log("Error", error);
            });
    };

    $scope.update = function() {
        var item = angular.copy($scope.form);
        $http
            .put(`/rest/users/${item.username}`, item)
            .then((resp) => {
                var index = $scope.items.findIndex((p) => p.username == item.username);
                $scope.items[index] = item;
                alert("Cập nhật tài khoản thành công!");
            })
            .catch((error) => {
                alert("Lỗi cập nhật tài khoản!");
                console.log("Error", error);
            });
    };

    $scope.delete = function(item) {
        $http
            .delete(`/rest/users/${item.username}`, item)
            .then((resp) => {
                var index = $scope.items.findIndex((p) => p.username == item.username);
                $scope.items.splice(index, 1);
                $scope.reset();
                alert("Xóa tài khoản thành công!");
            })
            .catch((error) => {
                alert("Lỗi xóa tài khoản!");
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

    $scope.initialize();
});
