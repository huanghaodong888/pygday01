/** 定义控制器层 */
app.controller('goodsController', function($scope, $controller, baseService){

    /** 指定继承baseController */
    $controller('baseController',{$scope:$scope});
    //点击保存
    $scope.saveOrUpdate = function () {
        /** 获取富文本编辑器的内容 */
       $scope.goods.goodsDesc.introduction = editor.html();
        baseService.sendPost('/goods/save',$scope.goods).then(function (response) {
            if (response.data) {
                //清空数据
                $scope.goods = {};
                //清空富文本编辑器的内容
               editor.html('');
            } else {
                alert("操作失败");
            }
        })
    }

    //上传图片
    $scope.uploadFile = function () {
        baseService.uploadFile().then(function (response){
            if (response.data.status == 200) {
                $scope.itemImages.url= response.data.url;
            } else {
                alert("操作失败");
            }
        })
    };

    //保存上传的图片
    //定义数据存储结构
   $scope.goods = {goodsDesc : {itemImages :[],specificationItems : []}};
    $scope.addPic = function () {
        $scope.goods.goodsDesc.itemImages.push($scope.itemImages);
    };

    //删除上传的图片
    $scope.deleteItemImages = function (idnex) {
        $scope.goods.goodsDesc.itemImages.splice(idnex,1)
    }

    //根据父级id查询分类属性
    $scope.findByParentId = function (parentId,name) {
        baseService.sendGet('/itemCat/findByParentId?parentId=' +  parentId).then(function (response) {
            $scope[name] = response.data;
        })
    }

    //$scope.$watch():方法，它可以监控$scope数据模型中的变量发生改变，就会回调一个函数
    $scope.$watch('goods.category1Id',function (newVal,oldVal) {
        if (newVal) {
            $scope.findByParentId(newVal,'itemCatList2');
        } else {
            $scope.itemCatList2 = [];
        }

    })

    $scope.$watch('goods.category2Id',function (newVal,oldVal) {
        if (newVal) {
            $scope.findByParentId(newVal,'itemCatList3');
        } else {
            $scope.itemCatList3 = [];
        }

    });

    $scope.$watch("goods.category3Id",function (newVal,oldVal) {
        if (newVal) {
            for (var i = 0; i < $scope.itemCatList3.length; i++) {
                var a = $scope.itemCatList3[i];
                if (a.id == newVal) {
                    $scope.goods.typeTemplateId = a.typeId;
                    break;
                }
            }
        } else {
            $scope.goods.typeTemplateId = null;
        }
    });

    $scope.$watch("goods.typeTemplateId",function (newVal, oldVal) {
        if (newVal) {
            baseService.sendGet('/typeTemplate/findOne?id=' + newVal).then(function (response) {
                $scope.brandIds = JSON.parse(response.data.brandIds);
                $scope.goods.goodsDesc.customAttributeItems = JSON.parse(response.data.customAttributeItems);

            });
            baseService.sendGet('/typeTemplate/findSpecIds?id=' + newVal).then(function (response) {
                // 获取响应数据
                /**
                 * [{"id":27,"text":"网络","options" : [{},{}]},
                 *  {"id":32,"text":"机身内存","options" : [{},{}]}]
                 */
                $scope.specList = response.data;
                // 清空用户选中的规格选项
                $scope.goods.goodsDesc.specificationItems = [];
            })
        } else {
            $scope.brandIds = [];
        }
    });


    /*
      $scope.goods.goodsDesc.specificationItems =
      [{"attributeValue":["联通4G","移动4G","电信4G"],"attributeName":"网络"},
      {"attributeValue":["64G","128G"],"attributeName":"机身内存"}]
     */
    // 用户选择规格选项
    $scope.updateSpecAttr = function (event, specName, optionName) {
        // 根据specName从specificationItems数组中搜索指定的json对象
        var obj = $scope.searchJsonBykey($scope.goods.goodsDesc.specificationItems, specName);
        // 判断obj对象是否为空
        if (obj){ // obj: {"attributeValue":["联通4G","移动4G","电信4G"],"attributeName":"网络"}
            // 判断checkbox是否选中
            if (event.target.checked){ // 选中
                obj.attributeValue.push(optionName);
            }else{ // 没有选中
                // 得到索引号
                var idx = obj.attributeValue.indexOf(optionName);
                // 从数组中删除一个元素
                obj.attributeValue.splice(idx,1);

                // 判断obj.attributeValue数组长度是否等于零
                if (obj.attributeValue.length == 0){
                    // 得到索引号
                    var idx = $scope.goods.goodsDesc.specificationItems.indexOf(obj);
                    // 从外面数组中删除元素 obj
                    $scope.goods.goodsDesc.specificationItems.splice(idx,1);
                }
            }
        }else {
            $scope.goods.goodsDesc.specificationItems
                .push({attributeValue: [optionName], attributeName: specName});
        }
    };

    // 根据specName从specificationItems数组中搜索指定的json对象
    $scope.searchJsonBykey = function (jsonArr, value) {
        /* jsonArr:
             [{"attributeValue":["联通4G","移动4G","电信4G"],"attributeName":"网络"},
              {"attributeValue":["64G","128G"],"attributeName":"机身内存"}]
         */
        for (var i = 0; i < jsonArr.length; i++){
            // {"attributeValue":["联通4G","移动4G","电信4G"],"attributeName":"网络"}
            var obj = jsonArr[i];
            if (obj.attributeName == value){
                return obj;
            }
        }
        return null;
    };

    // 为规格选项checkbox绑定点击事件
    $scope.createItems = function () {
        // 1. 获取用户选中的规格选项数组
        var specItems = $scope.goods.goodsDesc.specificationItems;

        // 2. 定义SKU数组变量，并初始化
        $scope.goods.items = [{spec:{}, price:0, num:9999,status:'0', isDefault:'0'}];

        // 3. 迭代用户选中的的规格选项数组
        // specItems: [{"attributeValue":["移动3G","移动4G","联通3G"],"attributeName":"网络"}]
        for (var i = 0; i < specItems.length; i++){
            // 获取一个数组元素
            // {"attributeValue":["移动3G","移动4G","联通3G"],"attributeName":"网络"}
            var specItem = specItems[i];

            // 调用转换SKU的方法
            $scope.goods.items  = $scope.swapItems($scope.goods.items,
                specItem.attributeValue, specItem.attributeName);
        }

    };

    // 转换SKU的方法
    $scope.swapItems = function (items, attributeValue, attributeName) {

        // 定义一个新的SKU数组
        var newItems = [];

        // items : [{spec:{}, price:0, num:9999,status:'0', isDefault:'0'}]
        // attributeValue: ["移动3G","移动4G","联通3G"]
        // attributeName: 网络
        for (var i = 0; i < items.length; i++){ // 循环1次
            // 取数组元素
            // {spec:{}, price:0, num:9999,status:'0', isDefault:'0'}
            var item = items[i];
            // 迭代规格选项数组 ["移动3G","移动4G","联通3G"]
            for (var j = 0; j < attributeValue.length; j++){ // 循环3次
                // 把item转化成的新的item
                var newItem = JSON.parse(JSON.stringify(item));
                newItem.spec[attributeName] = attributeValue[j];

                // 添加到新的SKU数组
                newItems.push(newItem);
            }
        }

        return newItems;
    };
});