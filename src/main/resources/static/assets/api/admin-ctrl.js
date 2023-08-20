var app = angular.module("admin-app", ["ngRoute"]);

app.config(function($routeProvider) {

    $routeProvider
        .when("/dashboard", {
            templateUrl: "/assets/admin/dashboard/index.html",
            controller: "HomeController"
        })
        .when("/account", {
            templateUrl: "/assets/admin/account/index.html",
            controller: "account-ctrl"
        })
        .when("/order", {
            templateUrl: "/assets/admin/order/index.html",
            controller: "order-ctrl"
        })
        .when("/product", {
            templateUrl: "/assets/admin/product/index.html",
            controller: "product-ctrl"
        })
        .when("/category", {
            templateUrl: "/assets/admin/category/index.html",
            controller: "category-ctrl"
        })
        .when("/authority", {
            templateUrl: "/assets/admin/authority/index.html",
            controller: "authority-ctrl"
        })
        .otherwise({
            templateUrl: "/assets/admin/dashboard/index.html",
            //controller: "/assert/admin-ctrl/index.html"
        })

})
app.controller("HomeController", function($scope, $http) {
    // Controller logic for the home view
    $scope.item_notification = [];

    $scope.initialize = function() {
        $http.get("/rest/orders/notification").then((resp) => {
            $scope.item_notification = resp.data;
            console.log(resp.data);
        });
    };

});