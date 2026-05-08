brokerSkipApp.factory('FavoriteService', ['$http', function($http) {
    var API_URL = 'http://localhost:8080/api';
    var favorites = [];

    return {
        getFavorites: function(userId) {
            return $http.get(API_URL + '/favorites/user/' + userId).then(function(response) {
                favorites = response.data;
                return response.data;
            });
        },

        addFavorite: function(userId, propertyId) {
            return $http.post(API_URL + '/favorites', {
                userId: userId,
                propertyId: propertyId
            }).then(function(response) {
                favorites.push(propertyId);
                return response.data;
            });
        },

        removeFavorite: function(propertyId) {
            return $http.delete(API_URL + '/favorites/' + propertyId).then(function(response) {
                favorites = favorites.filter(function(id) {
                    return id !== propertyId;
                });
                return response.data;
            });
        },

        toggleFavorite: function(userId, propertyId) {
            if (this.isFavorite(propertyId)) {
                this.removeFavorite(propertyId);
            } else {
                this.addFavorite(userId, propertyId);
            }
        },

        isFavorite: function(propertyId) {
            return favorites.indexOf(propertyId) > -1;
        }
    };
}]);
