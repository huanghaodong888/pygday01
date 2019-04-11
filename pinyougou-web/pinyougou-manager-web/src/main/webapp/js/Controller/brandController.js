// 定义品牌控制器
app.controller('brandController', function ($scope, $controller, baseService) {

    // 品牌控制器继承基础的控制器
    $controller('baseController', {$scope : $scope});

    // 分页查询品牌的方法
    $scope.search = function (page, rows) {
        // 发送异步请求查询品牌的数据
        baseService.findByPage("/brand/findByPage", page, rows,
            $scope.searchEntity).then(function(response){
            // 获取响应数据: response.data:
            // js : {total : 100, rows : [{},{}]}
            // java: Map<String, Object>
            // 获取品牌的分页数据
            $scope.dataList = response.data.rows;
            // 设置总记录数
            $scope.paginationConf.totalItems = response.data.total;

        });
    };


    // 添加或修改品牌
    $scope.saveOrUpdate = function () {
        // 添加URL
        var url = "save";
        // 判断品牌id是否存在
        if ($scope.entity.id){
            // {"firstChar":"H","id":2,"name":"华为"}
            url = "update";
        }
        // 发送异步请求
        baseService.sendPost("/brand/" + url, $scope.entity)
            .then(function(response){
            // 获取响应数据 true|false
            if (response.data){
                // 重新加载数据
                $scope.reload();
            }else {
                alert("操作失败！");
            }
        });
    };

    // 为修改按钮绑定点击事件
    $scope.show = function (entity) {
        // 把entity的json对象转化成一个新的json对象
        // 把json对象转化成json字符串
        var jsonStr = JSON.stringify(entity);
        // 把jsonStr字符串转化成新的json对象
        $scope.entity = JSON.parse(jsonStr);
    };

    // 为删除按钮绑定点击事件
    $scope.delete = function () {
        // 判断ids数组
        if ($scope.ids.length > 0){
            if (confirm("亲，您确定删除吗?")){
                // 发送异步请求
                baseService.deleteById("/brand/delete", $scope.ids)
                    .then(function(response){
                    // 获取响应数据
                    if (response.data){
                        // 重新加载数据
                        $scope.reload();
                        // 清空ids数组
                        $scope.ids = [];
                    }else{
                        alert("删除失败！");
                    }
                });
            }
        }else{
            alert("请选择要删除的品牌！");
        }
    };
});