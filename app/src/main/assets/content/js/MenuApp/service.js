app.service('myService', function($q) {
  this.getMenu = function() {
    var q = $q.defer();
    var menu = AndroidMainActLoader.getJSONData();
    q.resolve(menu);
    return q.promise;
  };

});