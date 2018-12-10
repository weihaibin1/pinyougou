//定义控制器  参数一：控制器名称  参数二：控制器做的事情
//$scope angularjs内置对象，全局作用域对象  作用：html与js数据交换的桥梁
//$http angularjs发起请求的内置服务对象   全部都是异步ajax
app.controller("brandController",function ($scope,$controller,brandService) {

    //控制器继承代码  参数一：继承的父控制器名称   参数二：固定写法，共享$scope对象
    $controller("baseController",{$scope:$scope})


    //定义查询所有品牌列表的方法
    $scope.findAll=function () {
        //response接收响应结果
        brandService.findAll().success(function (response) {
            $scope.list=response;
        })
    }

    //发起分页查询请求
    $scope.findPage=function (pageNum, pageSize) {
        brandService.findPage(pageNum,pageSize).success(function (response) {
            $scope.paginationConf.totalItems=response.total;
            $scope.list=response.rows;
        })
    }

    //定义封装查询条件的实体对象
    $scope.searchEntity={};
    //发起分页条件查询请求
    $scope.search =function (pageNum, pageSize) {
        brandService.search($scope.searchEntity,pageNum,pageSize).success(function (response) {
            $scope.paginationConf.totalItems=response.total;
            $scope.list=response.rows;
        })
    }

    //定义保存品牌的方法
    $scope.save=function () {
        var method=null	;
        if($scope.entity.id!=null){
            //修改操作
            method=brandService.update($scope.entity)
        }else{
            method=brandService.add($scope.entity)
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
        brandService.findOne(id).success(function (response) {
            $scope.entity=response;
        })
    }



    //删除操作
    $scope.dele=function () {
        if(confirm("您确定要删除吗?")){
            brandService.dele($scope.selectIds).success(function (response) {
                if (response.success){
                    //删除成功，重新加载品牌列表
                    $scope.reloadList();
                }else {
                    alert(response.message);
                }
            })
        }
    }
});