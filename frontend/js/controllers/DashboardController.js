brokerSkipApp.controller('DashboardController', ['$scope', '$http', '$location', 'PropertyService', 'FavoriteService',
    function($scope, $http, $location, PropertyService, FavoriteService) {

    $scope.properties = [];
    $scope.filterType = 'all';
    $scope.currentUser = JSON.parse(localStorage.getItem('currentUser')) || {};

    // Load properties on init
    $scope.loadProperties = function() {
        if ($scope.filterType === 'all') {
            PropertyService.getAllProperties().then(function(data) {
                $scope.properties = data;
            });
        } else if ($scope.filterType === 'myPosts') {
            PropertyService.getUserProperties($scope.currentUser.id).then(function(data) {
                $scope.properties = data;
            });
        } else if ($scope.filterType === 'favorites') {
            FavoriteService.getFavorites($scope.currentUser.id).then(function(data) {
                $scope.properties = data;
            });
        }
    };

    $scope.$watch('filterType', function() {
        $scope.loadProperties();
    });

    $scope.isFavorite = function(propertyId) {
        return FavoriteService.isFavorite(propertyId);
    };

    $scope.toggleFavorite = function(propertyId) {
        FavoriteService.toggleFavorite($scope.currentUser.id, propertyId);
    };

    $scope.viewProperty = function(propertyId) {
        $location.path('/property/' + propertyId);
    };

    $scope.deleteProperty = function(propertyId) {
        if (confirm('Are you sure?')) {
            PropertyService.deleteProperty(propertyId).then(function() {
                $scope.loadProperties();
            });
        }
    };

    $scope.loadProperties();
}]);
