//定义分类管理控制器
app.controller('itemCatController',function ($scope, $controller, baseService) {
    //分类管理控制器继承基础控制器
    $controller('baseController',{$scope:$scope});
   /* //分页查询全部数据
    $scope.search = function (page,rows) {
        //发送异步请求
        baseService.findByPage('/itemCat/findAll',page,rows).then(function (response) {
            $scope.dataList = response.data.rows;
            $scope.paginationConf.totalItems = response.data.total;
        })

    }*/
   //根据父级id查询
    $scope.findByParentId = function (parentId) {
        //发送异步请求
        baseService.sendGet('/itemCat/findByParentId?parentId='+parentId).then(function (response) {

            $scope.dataList = response.data;
        })
    };

    //定义一个级别grand
    $scope.grand = 1;
    //点击下一级
    $scope.selectList = function (entity,grand) {
        $scope.grand = grand;
        if (grand == 1) {
            //$scope.one = null;
            //$scope.two = null;
        }
        if (grand == 2) {
            $scope.one = entity;
            $scope.two = null;
        }
        if (grand == 3) {
            $scope.two = entity;
        }

        $scope.findByParentId(entity.id);
    }


});