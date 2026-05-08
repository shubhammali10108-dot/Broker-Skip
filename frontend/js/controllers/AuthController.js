brokerSkipApp.controller('AuthController', ['$scope', '$http', '$location', 'AuthService',
    function($scope, $http, $location, AuthService) {

    $scope.step = 'phone'; // phone, otp, profile
    $scope.phoneNumber = '';
    $scope.otp = '';
    $scope.otpTimer = 0;
    $scope.canResendOtp = false;

    // Send OTP
    $scope.sendOtp = function() {
        if (!$scope.phoneNumber || $scope.phoneNumber.length !== 10) {
            alert('Please enter a valid 10-digit phone number');
            return;
        }

        AuthService.sendOtp($scope.phoneNumber).then(function(response) {
            if (response.success) {
                $scope.step = 'otp';
                $scope.startOtpTimer();
                alert('OTP sent successfully');
            }
        }).catch(function(error) {
            alert('Error sending OTP: ' + error.data.message);
        });
    };

    // Verify OTP
    $scope.verifyOtp = function() {
        if (!$scope.otp || $scope.otp.length !== 6) {
            alert('Please enter a valid 6-digit OTP');
            return;
        }

        AuthService.verifyOtp($scope.phoneNumber, $scope.otp).then(function(response) {
            if (response.success) {
                localStorage.setItem('token', response.token);
                localStorage.setItem('userId', response.userId);
                
                // Check if user profile exists
                if (response.profileExists) {
                    $location.path('/dashboard');
                } else {
                    $location.path('/profile-creation');
                }
            }
        }).catch(function(error) {
            alert('Invalid OTP: ' + error.data.message);
        });
    };

    // Start 5 minute timer
    $scope.startOtpTimer = function() {
        $scope.otpTimer = 300; // 5 minutes in seconds
        $scope.canResendOtp = false;

        var timer = setInterval(function() {
            $scope.otpTimer--;
            $scope.$apply();

            if ($scope.otpTimer <= 0) {
                clearInterval(timer);
                $scope.canResendOtp = true;
                $scope.$apply();
            }
        }, 1000);
    };

    // Resend OTP
    $scope.resendOtp = function() {
        $scope.sendOtp();
    };

    // Format timer display
    $scope.getTimerDisplay = function() {
        var minutes = Math.floor($scope.otpTimer / 60);
        var seconds = $scope.otpTimer % 60;
        return minutes + ':' + (seconds < 10 ? '0' : '') + seconds;
    };
}]);
