app.config(function ($routeProvider) {
    $routeProvider.when("/",{
      templateUrl: "app/core/main/searchProductView.html",
      controller: "SearchProductCtrl"
    }).when("/products",{
        templateUrl: "app/core/main/searchProductView.html",
        controller: "SearchProductCtrl"
    })
    //     .when("/products/create-product",{
    //     templateUrl: "view/createProductView.html",
    //     controller: "CreateProductCtrl"
    // })
        .otherwise({
        redirectTo: '/'
    });
});
