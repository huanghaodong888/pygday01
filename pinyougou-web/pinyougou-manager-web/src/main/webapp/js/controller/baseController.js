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
    $scope.jsonArr2Str = function (brandList,key) {
        //将brandList的json字符串转化为json对象
        var jsonBrandList = JSON.parse(brandList);//jsonBrandList的格式是 {id ：'' ，text ：''},{id ：'' ，text ：''},...
        var jsonArr = [];
        for (var i = 0; i < jsonBrandList.length; i++) {
            var json = jsonBrandList[i];//json的格式是{id ：'' ，text ：''}
            jsonArr.push(json[key]);
        }
        // 返回数组中的元素用逗号分隔的字符串
        return jsonArr.join(",");
    }
});