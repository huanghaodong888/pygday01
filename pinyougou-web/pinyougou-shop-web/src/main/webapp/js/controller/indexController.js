/** 定义控制器层 */
app.controller('indexController', function($scope, baseService){
    $scope.showUsername = function () {
        //发送异步请求
        baseService.sendGet('/index/showUsername').then(function (response) {
            $scope.username = response.data.username;
        })
    }


});
