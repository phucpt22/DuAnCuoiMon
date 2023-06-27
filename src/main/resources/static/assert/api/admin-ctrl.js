var app = angular.module("admin-app", ["ngRoute"]);

app.config(function($routeProvider) {

    $routeProvider
        .when("/product", {
            templateUrl: "/assert/admin-ctrl/product/index.html",
            controller: "product-ctrl"
        })
        .when("/authorize", {
            templateUrl: "/admin-ctrl/authorit1y/index.html",
            controller: "authority-ctrl"
        })
        .when("/unauthorized", {
            templateUrl: "/admin-ctrl/authority/unauthorized.html",
        })
        .when("/account", {
            templateUrl: "/assert/admin-ctrl/account/index.html",
            controller: "account-ctrl"
        })
        .when("/orders", {
            templateUrl: "/assert/admin-ctrl/order/index.html",
            controller: "order-ctrl"
        })
        .when("/stats", {
        templateUrl: "/assert/admin-ctrl/stats/index.html",
        // controller: "order-ctrl"
        })
        .when("/userinfo", {
            templateUrl: "/assert/admin-ctrl/order/index.html",
            controller: "userinfo-ctrl"
        })
        .when("/categories", {
            templateUrl: "/admin-ctrl/category/index.html",
            controller: "category-ctrl"
        }).when("/homeadmin", {
        templateUrl: "/assert/admin-ctrl/index.html",
    })
        .otherwise({
            template: "<h1 class='text-center'>FPT Polytechnic Administrator</h1>",
            //templateUrl: "/assert/admin-ctrl/index.html"
        })

})