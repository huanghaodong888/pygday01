/** 定义控制器层 */
app.controller('goodsController', function($scope, $controller, baseService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});
    /** 定义搜索对象 */
    $scope.searchEntity = {};
    /** 定义商品状态数组 */
    $scope.status = ['未审核','已审核','审核未通过','关闭'];
    //多条件分页查询
    $scope.search = function (page,rows) {
        baseService.findByPage('/goods/findByPage',page,rows,$scope.searchEntity).then(function (response) {
            $scope.paginationConf.totalItems = response.data.total;
            $scope.dataList = response.data.rows;
        })
    };

    //审核通过与驳回
    $scope.updateStatus = function (status) {
        if ($scope.ids.length > 0) {
            baseService.sendGet('/goods/updateStatus?status=' + status + '&ids=' + $scope.ids).then(function (response) {
                if (response.data) {
                    $scope.reload();
                    $scope.ids = [];
                } else {
                    alert("操作失败");
                }
            })
        } else {
            alert("请勾选");
        }
    }

    //删除商品，非物理删除，只是把is_deleted的状态码改变
    $scope.delete = function () {

        if ($scope.ids.length > 0) {
            baseService.sendGet('/goods/delete?ids=' + $scope.ids).then(function (response) {
                if (response.data) {
                    $scope.reload();
                    $scope.ids = [];
                } else {
                    alert("操作失败");
                }
            })
        }else {
            alert("请勾选")
        }


    }
});