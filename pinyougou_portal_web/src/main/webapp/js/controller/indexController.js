app.controller("indexController",function ($scope,$controller,contentService) {

    //控制器继承代码
    $controller("baseController",{$scope:$scope});

    //根据广告分类id查询广告列表数据
    $scope.findByCategoryId=function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (response) {
            //定义广告列表接受返回的数据
            $scope.contentList=response;
        })
    }
})