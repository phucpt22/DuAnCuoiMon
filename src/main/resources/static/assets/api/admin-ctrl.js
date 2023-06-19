var app = angular.module("admin-app", ["ngRoute"]);

app.config(function($routeProvider) {

    $routeProvider
        .when("/product", {
            templateUrl: "/assets/admin-ctrl/product/index.html",
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
            templateUrl: "/assets/admin-ctrl/account/index.html",
            controller: "account-ctrl"
        })
        .when("/orders", {
            templateUrl: "/assets/admin-ctrl/order/index.html",
            controller: "order-ctrl"
        })
        .when("/userinfo", {
            templateUrl: "/assert/admin-ctrl/order/index.html",
            controller: "userinfo-ctrl"
        })
        .when("/categories", {
            templateUrl: "/assets/admin-ctrl/category/index.html",
            controller: "category-ctrl"
        }).when("/homeadmin", {
        templateUrl: "/static/assets/admin-ctrl/index.html",
    })
        .otherwise({
            template: "<h1 class='text-center'>FPT Polytechnic Administrator</h1>",
            //templateUrl: "/assert/admin-ctrl/index.html"
        })

})