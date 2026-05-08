brokerSkipApp.factory('PropertyService', ['$http', function($http) {
    var API_URL = 'http://localhost:8080/api';

    return {
        getAllProperties: function() {
            return $http.get(API_URL + '/properties').then(function(response) {
                return response.data;
            });
        },

        getUserProperties: function(userId) {
            return $http.get(API_URL + '/properties/user/' + userId).then(function(response) {
                return response.data;
            });
        },

        getPropertyById: function(id) {
            return $http.get(API_URL + '/properties/' + id).then(function(response) {
                return response.data;
            });
        },

        createProperty: function(property) {
            return $http.post(API_URL + '/properties', property).then(function(response) {
                return response.data;
            });
        },

        updateProperty: function(id, property) {
            return $http.put(API_URL + '/properties/' + id, property).then(function(response) {
                return response.data;
            });
        },

        deleteProperty: function(id) {
            return $http.delete(API_URL + '/properties/' + id).then(function(response) {
                return response.data;
            });
        }
    };
}]);
