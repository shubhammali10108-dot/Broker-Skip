brokerSkipApp.controller('MainController', ['$scope', '$location', 'AuthService',
    function($scope, $location, AuthService) {

    $scope.isLoggedIn = AuthService.isLoggedIn();
    $scope.currentPage = 'dashboard';
    $scope.showProfileMenu = false;
    $scope.currentUser = JSON.parse(localStorage.getItem('currentUser')) || {};

    $scope.navigateTo = function(page) {
        $scope.currentPage = page;
        switch(page) {
            case 'dashboard':
                $location.path('/dashboard');
                break;
            case 'myPosts':
                $location.path('/my-posts');
                break;
            case 'favorites':
                $location.path('/favorites');
                break;
            case 'profile':
                $location.path('/profile');
                break;
        }
    };

    $scope.goToCreatePost = function() {
        $location.path('/create-post');
    };

    $scope.toggleProfileMenu = function() {
        $scope.showProfileMenu = !$scope.showProfileMenu;
    };

    $scope.logout = function() {
        AuthService.logout().then(function() {
            $location.path('/login');
            $scope.isLoggedIn = false;
        });
    };

    // Load current user
    if ($scope.isLoggedIn) {
        AuthService.getCurrentUser().then(function(user) {
            $scope.currentUser = user;
            localStorage.setItem('currentUser', JSON.stringify(user));
        });
    }
}]);
