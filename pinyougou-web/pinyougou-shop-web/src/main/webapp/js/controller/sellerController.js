/** 定义控制器层 */
app.controller('sellerController', function($scope, $controller, baseService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    //点击申请
    $scope.saveOrUpdate = function () {
        /*//发送异步请求
        baseService.sendPost('/seller/save' ,$scope.seller).then(function (response) {
            if (response.data) {
                //跳转到商家登录首页
                location.href('/shoplogin.html');
            } else {
                alert ("操作失败")
            }
        });*/
        baseService.sendPost("/seller/save",$scope.seller).then(function (response) {

            if(response.data){
                location.href="/shoplogin.html";
            } else {
                alert("操作失败");
            }
        });
    };

});