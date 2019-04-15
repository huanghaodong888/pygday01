app.controller('indexController',function ($scope, baseService) {
    //显示用户的名字
    $scope.showLoginName = function () {
        //发送异步请求
        baseService.sendGet('/index/showLoginName').then(function (response) {
            $scope.userName = response.data.userName;
        })
    }
});