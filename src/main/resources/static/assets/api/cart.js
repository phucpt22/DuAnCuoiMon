const app = angular.module("shopping-cart", []);
app.controller("shopping-cart-ctrl", function ($scope, $http) {
    $scope.cart = {
        items: [],
        add: function (id) {
            var item = this.items.find((item) => item.id == id);
            if (item) {
                item.qty++;
                this.saveToLocalStorage();
                // Hiển thị thông báo thành công
                Swal.fire({
                    text: "Sản phẩm đã được thêm vào giỏ hàng!",
                    icon: "success"
                });
            } else {
                $http.get(`/rest/products/${id}`).then((resp) => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                    // Hiển thị thông báo thành công
                    Swal.fire({
                        text: "Sản phẩm đã được thêm vào giỏ hàng!",
                        icon: "success"
                    });
                });
            }
        },
        remove(id) {
            var index = this.items.findIndex((item) => item.id == id);
            this.items.splice(index, 1);
            this.saveToLocalStorage();
        },
        clear() {
            this.items = [];
            this.saveToLocalStorage();
        },
        amt_of() {
        },
        get count() {
            return this.items
                .map((item) => item.qty)
                .reduce((total, qty) => (total += qty), 0);
        },
        get amount() {
            return this.items
                .map((item) => item.qty * item.currentprice)
                .reduce((total, qty) => (total += qty), 0);
        },
        get total_price() {
            const amount = this.items
                .map((item) => item.qty * item.currentprice)
                .reduce((total, qty) => (total += qty), 0);
            return amount + (amount * 0.1);
        },
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },
        loadFormLocalStrorage() {
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        },
    };

    $scope.cart.loadFormLocalStrorage();
    $scope.order = {
        createDate: new Date(),
        address: "",
        vat: 10,
        status_pay: "Chưa thanh toán",
        origninal_price: $scope.cart.amount,
        total_price: $scope.cart.total_price,
        payments: "",
        status_order: "Chờ xác nhận",
        user: {
            id: $("#id").val()
        },
        get orderDetails() {
            return $scope.cart.items.map((item) => {
                return {
                    product: {id: item.id},
                    price: item.currentprice,
                    quantity: item.qty,
                    amount: item.currentprice * item.qty
                };
            });
        },
        purchase() {
            const a = $scope.order.payments
            //var order = angular.copy(this);
            if (a === 'COD') {
                var order = angular.copy(this);
                $http.post("/rest/orders", order)
                    .then((resp) => {
                        // Hiển thị thông báo thành công
                        Swal.fire({
                            text: "Đặt hàng thành công!",
                            icon: "success"
                        }).then(() => {
                            $scope.cart.clear();
                            location.href = "/order/detail/" + resp.data.id;
                        });
                    })
                    .catch((error) => {
                        // Hiển thị thông báo lỗi
                        Swal.fire({
                            text: "Đặt hàng lỗi!",
                            icon: "error"
                        });
                        console.log(error);
                    });
            } else if (a === 'VNPAY') {
                var order = angular.copy(this);
                $http.post("/rest/orders", order)
                    .then((resp) => {
                        //console.log(resp.data.id)
                        $scope.cart.clear();
                        location.href = "/submitOrder/" + resp.data.id + "/" + resp.data.total_price
                    });
            }
        }
    };
});

app.controller("userinfo-ctrl", userInfoController)

function userInfoController($scope, $http) {
    $scope.userInfo = {
        id: 0, fullName: '', phone: null, photo: null, gmail: '',
    }

    $scope.initialize = function () {
        $scope.getUserInfoLogin()
    };

    $scope.getUserInfoLogin = function () {
        $http.get('userinfo/userinfo-login').then(resp => {
            $scope.userInfo = resp.data
            $scope.fullName = $scope.userInfo.fullName
            $scope.userInfoBackUp = angular.copy($scope.userInfo)
        })
    }
    $scope.initialize();

    $scope.redoClick = function () {
        $scope.userInfo = angular.copy($scope.userInfoBackUp)
    }

    $scope.saveClick = function () {
        if (JSON.stringify($scope.userInfo) === JSON.stringify($scope.userInfoBackUp)) {
            Swal.fire({
                position: 'top-end',
                icon: 'info',
                title: 'Nothing changes',
                showConfirmButton: false,
                timer: 1500
            })
        } else {
            $http.post("/userinfo/userinfo-login", $scope.userInfo).then(resp => {
                Swal.fire('Update successfully')
            })
        }

    }


}

