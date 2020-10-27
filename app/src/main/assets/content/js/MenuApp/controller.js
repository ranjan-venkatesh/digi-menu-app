app.controller('menuController', function ($scope,myService) {
$scope.menu = '';
$scope.weeks = '';
myService.getMenu().then(function(result) {
if (isJson(result)) {
  $scope.menu = JSON.parse(result);
  $scope.weeks = Object.keys($scope.menu[0]).filter(function(col) {
    return $scope.menu.some(function(item) {
      return item[col] !== null;
    });
  });
}
else AndroidMainActToaster.print("Json Parse failed on JS!");
});
});