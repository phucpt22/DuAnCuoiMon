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
        .otherwise({
            templateUrl: "/assets/admin/dashboard/index.html",
            //templateUrl: "/assert/admin-ctrl/index.html"
        })

})
app.controller("HomeController", function($scope) {
    // Controller logic for the home view
});