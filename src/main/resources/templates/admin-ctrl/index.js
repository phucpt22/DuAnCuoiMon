var app = angular.module("admin-app", ["ngRoute"]);

app.config(function($routeProvider) {
    $routeProvider
        .when("/product", {
            templateUrl: "/admin-ctrl/product/index.html",
            controller: "product-ctrl"
        })
        .when("/authorize", {
            templateUrl: "/admin-ctrl/authority/index.html",
            controller: "authority-ctrl"
        })
        .when("/unauthorized", {
            templateUrl: "/admin-ctrl/authority/unauthorized.html",
        })
        .when("/account", {
            templateUrl: "/admin-ctrl/account/index.html",
            controller: "account-ctrl"
        })
        .when("/orders", {
            templateUrl: "/admin-ctrl/order/index.html",
            controller: "order-ctrl"
        })
        .when("/categories", {
            templateUrl: "/admin-ctrl/category/index.html",
            controller: "category-ctrl"
        }).when("/homeadmin", {
        templateUrl: "/admin-ctrl/index.html",
    })
        .otherwise({
            //template: "<h1 class='text-center'>FPT Polytechnic Administrator</h1>",
            templateUrl: "/admin-ctrl/index.html"
        })
})