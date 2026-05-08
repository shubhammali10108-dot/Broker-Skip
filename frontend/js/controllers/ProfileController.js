brokerSkipApp.controller('ProfileController', ['$scope', '$http', '$location',
    function($scope, $http, $location) {

    $scope.profile = {};
    $scope.step = 'creation'; // creation, view, edit
    var API_URL = 'http://localhost:8080/api';

    $scope.createProfile = function() {
        if (!$scope.profile.firstName || !$scope.profile.lastName || !$scope.profile.email) {
            alert('Please fill all required fields');
            return;
        }

        var userId = localStorage.getItem('userId');
        $http.post(API_URL + '/users/profile', {
            userId: userId,
            firstName: $scope.profile.firstName,
            lastName: $scope.profile.lastName,
            email: $scope.profile.email,
            city: $scope.profile.city,
            state: $scope.profile.state,
            pincode: $scope.profile.pincode,
            bio: $scope.profile.bio
        }).then(function(response) {
            if (response.data.success) {
                localStorage.setItem('currentUser', JSON.stringify(response.data.user));
                $location.path('/dashboard');
            }
        }).catch(function(error) {
            alert('Error creating profile: ' + error.data.message);
        });
    };

    $scope.updateProfile = function() {
        var userId = localStorage.getItem('userId');
        $http.put(API_URL + '/users/profile/' + userId, $scope.profile)
            .then(function(response) {
                localStorage.setItem('currentUser', JSON.stringify(response.data));
                alert('Profile updated successfully');
            })
            .catch(function(error) {
                alert('Error updating profile');
            });
    };
}]);
