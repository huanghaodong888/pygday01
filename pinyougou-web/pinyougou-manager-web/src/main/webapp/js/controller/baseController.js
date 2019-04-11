// 定义基础控制器
app.controller('baseController', function ($scope) {


    // 定义分页指令需要配置信息对象
    $scope.paginationConf = {
        currentPage : 1, // 当前页码
        totalItems : 0, // 总记录数
        itemsPerPage : 10, // 页大小
        perPageOptions : [10,15,20,25,30], // 页码下拉列表框
        onChange : function () { // 页码发生改变时就会调用
            $scope.reload();
        }
    };

    // 重新加载数据方法
    $scope.reload = function () {
        // 调用搜索的方法
        $scope.search($scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage);
    };

    // 定义封装用户选择品牌的ids数组
    $scope.ids = [];

    // 为checkbox绑定点击事件
    $scope.updateSelection = function (e, id) {
        // e.target.checked
        // 判断checkbox是否选中
        if(e.target.checked){ // 选中
            // 往数组中添加元素
            //alert($scope.ids);
            $scope.ids.push(id);
        }else{
            // 获取元素在数组中的下标
            var idx = $scope.ids.indexOf(id);
            // 删除数组元素
            // 第一个参数：元素在数组中的下标
            // 第二个参数：删除的个数
            $scope.ids.splice(idx,1);
        }
    };

    //优化界面
    $scope.jsonArr2Str = function (jsonStr,key) {
        //首先将数组字符串转换为JSON数组
        var jsonArr = JSON.parse(jsonStr);
        var arr = [];
        for (var i = 0; i < jsonArr.length; i++) {
            var json = jsonArr[i];//json的格式是{id ： 1，text ： ''}
            arr.push(json[key])
        }
        return arr.join(',');
    }
});