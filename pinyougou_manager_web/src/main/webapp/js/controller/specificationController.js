//定义控制器  参数一：控制器名称  参数二：控制器做的事情
//$scope angularjs内置对象，全局作用域对象  作用：html与js数据交换的桥梁
//$http angularjs发起请求的内置服务对象   全部都是异步ajax
app.controller("specificationController",function ($scope,$controller,specificationService) {

    //控制器继承代码  参数一：继承的父控制器名称   参数二：固定写法，共享$scope对象
    $controller("baseController",{$scope:$scope})


    //定义查询所有列表的方法
    $scope.findAll=function () {
        //response接收响应结果
        specificationService.findAll().success(function (response) {
            $scope.list=response;
        })
    }

    //发起分页查询请求
    $scope.findPage=function (pageNum, pageSize) {
        specificationService.findPage(pageNum,pageSize).success(function (response) {
            $scope.paginationConf.totalItems=response.total;
            $scope.list=response.rows;
        })
    }

    //定义封装查询条件的实体对象
    $scope.searchEntity={};
    //发起分页条件查询请求
    $scope.search =function (pageNum, pageSize) {
        specificationService.search($scope.searchEntity,pageNum,pageSize).success(function (response) {
            $scope.paginationConf.totalItems=response.total;
            $scope.list=response.rows;
        })
    }

    //定义保存品牌的方法
    $scope.save=function () {
        var method=null	;
        if($scope.entity.specification.id!=null){
            //修改操作
            method=specificationService.update($scope.entity)
        }else{
            method=specificationService.add($scope.entity)
        }
        method.success(function (response) {
            if (response.success){
                //添加成功，重新加载品牌列表
                $scope.reloadList();
            }else {
                alert(response.message);
            }
        })
    }

    //根据id查询品牌数据
    $scope.findOne=function (id) {
        specificationService.findOne(id).success(function (response) {
            $scope.entity=response;
        })
    }



    //删除操作
    $scope.dele=function () {
        if(confirm("您确定要删除吗?")){
            specificationService.dele($scope.selectIds).success(function (response) {
                if (response.success){
                    //删除成功，重新加载品牌列表
                    $scope.reloadList();
                }else {
                    alert(response.message);
                }
            })
        }
    }

    //初始化entity对象
    $scope.entity = {specificationOptions:[]};

    //新增规格选项行
    $scope.addRow = function () {
        $scope.entity.specificationOptions.push({})
    }

    //删除规格选项
    $scope.deleRow = function (index) {
        $scope.entity.specificationOptions.splice(index,1)
    }
});