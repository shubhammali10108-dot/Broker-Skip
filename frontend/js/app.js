var brokerSkipApp = angular.module('brokerSkipApp', ['ngRoute', 'ngResource']);

brokerSkipApp.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'AuthController'
        })
        .when('/profile-creation', {
            templateUrl: 'views/profile-creation.html',
            controller: 'ProfileController'
        })
        .when('/dashboard', {
            templateUrl: 'views/dashboard.html',
            controller: 'DashboardController'
        })
        .when('/my-posts', {
            templateUrl: 'views/my-posts.html',
            controller: 'DashboardController'
        })
        .when('/favorites', {
            templateUrl: 'views/favorites.html',
            controller: 'DashboardController'
        })
        .when('/profile', {
            templateUrl: 'views/profile.html',
            controller: 'ProfileController'
        })
        .when('/create-post', {
            templateUrl: 'views/create-post.html',
            controller: 'DashboardController'
        })
        .otherwise({
            redirectTo: '/login'
        });

    // Add JWT token to all requests
    $httpProvider.interceptors.push('TokenInterceptor');
}]);

// Token Interceptor
brokerSkipApp.factory('TokenInterceptor', ['$q', '$window', '$location', function($q, $window, $location) {
    return {
        request: function(config) {
            config.headers = config.headers || {};
            var token = $window.localStorage.getItem('token');
            if (token) {
                config.headers.Authorization = 'Bearer ' + token;
            }
            return config;
        },
        responseError: function(response) {
            if(response.status === 401) {
                $window.localStorage.removeItem('token');
                $location.path('/login');
            }
            return $q.reject(response);
        }
    };
}]);
