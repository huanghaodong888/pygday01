//定义规格控制器
app.controller('specificationController',function ($scope, $controller, baseService) {
    //规格控制器继承基础控制器
    $controller('baseController',{$scope:$scope});

    //分页查询全部数据
    $scope.search = function (page,rows) {
        //发送异步请求
        baseService.findByPage('/specification/findByPage',page,rows,$scope.searchEntity).then(function (response) {
            //当前页数据
            $scope.dataList = response.data.rows;
            //查询总条数
            $scope.paginationConf.totalItems = response.data.total;
        })

    }

    //新增规格选项
    $scope.addTableRow = function () {
        $scope.entity.specificationOptions.push({});
    }

    //删除规格选项
    $scope.deleteTableRow = function (index) {
        $scope.entity.specificationOptions.splice(index,1);

    }

    //保存规格选项
    $scope.saveOrUpadte = function () {
        var url = 'save';
        if ($scope.entity.id) {
            url = 'update'
        }
        //发送异步请求
        baseService.sendPost('/specification/' + url,$scope.entity).then(function (response) {
            if (response.data) {
                //重新加载数据
                $scope.reload();
            } else {
                alert("保存失败！")
            }
        })
    }

    //点击修改，显示数据
    $scope.show = function (entity) {
        // 把entity的json对象转化成一个新的json对象
        //1.先把entity对象转化为一个字符串对象
        var jsonSrt = JSON.stringify(entity);
        //2.再把jsonSrt字符串对象转化为新的entity对象
        $scope.entity = JSON.parse(jsonSrt);
        //调用service
        baseService.sendGet("/specification/findSpecById?id=" + entity.id).then(function (response) {
            $scope.entity.specificationOptions = response.data;
        })
    }
    
    //点击删除，批量删除数据
    $scope.deleteAll = function () {
        if ($scope.ids.length > 0) {
            if (confirm("您确认要删除吗")) {
                //发送异步请求
                baseService.sendPost('/specification/deleteAll',$scope.ids).then(function (response) {
                    if (response.data) {
                        //重新加载数据
                        $scope.reload();
                        //清空ids
                        $scope.ids = [];
                    } else {
                        alert("删除失败")
                    }
                })
            }
        } else {
            alert("请选择需要删除的数据")
        }
    }

    

});