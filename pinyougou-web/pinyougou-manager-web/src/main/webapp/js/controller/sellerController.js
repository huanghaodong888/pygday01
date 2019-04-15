/** 定义控制器层 */
app.controller('sellerController', function($scope, $controller, baseService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});

    /** 查询条件对象 */
    //$scope.searchEntity = {status : '0'};
    /** 分页查询(查询条件) */
    $scope.search = function(page, rows){
        baseService.findByPage("/seller/findByPage", page,
            rows, $scope.company)
            .then(function(response){
                /** 获取分页查询结果 */
                $scope.dataList = response.data.rows;
                /** 更新分页总记录数 */
                $scope.paginationConf.totalItems = response.data.total;
            });
    };

    //点击详情显示数据
    $scope.show = function (entity) {
        $scope.entity = JSON.parse(JSON.stringify(entity));
    };
    
    //点击通过
    $scope.updateStatus = function (sellerId,status) {
        //发送异步请求
        baseService.sendGet('/seller/updateStatus?sellerId=' + sellerId + '&status=' + status).then(function (response) {
            if (response.data) {
                $scope.reload();
            }
        })
    }
});