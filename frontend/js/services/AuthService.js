brokerSkipApp.factory('AuthService', ['$http', function($http) {
    var API_URL = 'http://localhost:8080/api';

    return {
        sendOtp: function(phoneNumber) {
            return $http.post(API_URL + '/auth/send-otp', {
                phoneNumber: phoneNumber
            }).then(function(response) {
                return response.data;
            });
        },

        verifyOtp: function(phoneNumber, otpCode) {
            return $http.post(API_URL + '/auth/verify-otp', {
                phoneNumber: phoneNumber,
                otpCode: otpCode
            }).then(function(response) {
                return response.data;
            });
        },

        logout: function() {
            localStorage.removeItem('token');
            localStorage.removeItem('userId');
            return $http.post(API_URL + '/auth/logout');
        },

        getCurrentUser: function() {
            var userId = localStorage.getItem('userId');
            if (!userId) return null;
            
            return $http.get(API_URL + '/users/profile/' + userId).then(function(response) {
                return response.data;
            });
        },

        isLoggedIn: function() {
            return !!localStorage.getItem('token');
        }
    };
}]);
