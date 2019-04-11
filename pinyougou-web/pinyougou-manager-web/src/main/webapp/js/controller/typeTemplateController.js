//定义规格控制器
app.controller('typeTemplateController',function ($scope, $controller, baseService) {
    //规格控制器继承基础控制器
    $controller('baseController',{$scope:$scope});
    /** 定义搜索对象 */
    $scope.searchEntity = {};

    //按模板名称查询
    $scope.search = function (page,rows) {
        //发送异步请求
        baseService.findByPage('/typeTemplate/findAll', page, rows ,$scope.searchEntity).then(function (response) {
            //获得当前页数据
            $scope.dataList = response.data.rows;
            //获得总记录数
            $scope.paginationConf.totalItems = response.data.total;
        })
    };

    /** 新增扩展属性行 */
    $scope.addTableRow = function(){
        $scope.entity.customAttributeItems.push({});

    };

    //删除扩展属性行
    $scope.deleteTableRow = function (index) {
        $scope.entity.customAttributeItems.splice(index,1);
    }

    //查询关联的品牌
    $scope.findBrandList = function () {
        //发送异步请求
        baseService.sendGet('/typeTemplate/findBrandList').then(function (response) {
            $scope.brandList  = {data : response.data};

        })
    }

    //查询关联的规格
    $scope.findSpecList = function () {
        //发送异步请求
        baseService.sendGet('/typeTemplate/findSpecList').then(function (response) {
            $scope.specList = {data : response.data};
        })
    }

    //保存或修改
    $scope.saveOrUpdate = function () {
        //定义一个url
        var url = '/save';
        //判断是否为保存还是修改
        if ($scope.entity.id) {
            url = '/update'
        }
        //发送异步请求
        baseService.sendPost('/typeTemplate' + url,$scope.entity).then(function (response) {
            if (response.data) {
                //保存成功，重新加载
                $scope.reload();
            } else {
                //保存失败
                alert("对不起！保存失败！");
            }
        })
    }

    //点击删除
    $scope.deleteAll = function () {
        if ($scope.ids.length > 0) {
            if (confirm('确认删除吗')) {
                //发送异步请求
                baseService.sendPost('/typeTemplate/deleteAll',$scope.ids).then(function (response) {
                    if (response.data) {
                        //删除成功，重新加载数据
                        $scope.reload();
                    } else {
                        alert("删除失败")
                    }
                })
            }
        } else {
            alert("请选择需要删除的")
        }
    }

    //点击修改显示数据
    $scope.show = function (entity) {
        //将原来的entity  json对象转化为string对象
        var jsonStr =  JSON.stringify(entity);
        //再将string对象转化为 JSON对象
        $scope.entity =  JSON.parse(jsonStr);
        //把品牌字符串对象转化品牌JSON对象
        if (entity.brandIds == undefined) {
            $scope.entity.brandIds = [];
        }else {
            $scope.entity.brandIds = JSON.parse(entity.brandIds);
        }

        //把规格字符串对象转化为规格JSON对象
        if (entity.specIds == undefined) {
            $scope. entity.specIds = [];
        } else {
            $scope.entity.specIds = JSON.parse(entity.specIds);
        }

        //转化扩展属性
        if (entity.customAttributeItems == undefined) {
            $scope.entity.customAttributeItems = [];
        } else {
            $scope.entity.customAttributeItems = JSON.parse(entity.customAttributeItems);
        }
    }


});