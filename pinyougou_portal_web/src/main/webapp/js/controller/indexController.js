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

    //门户网站搜索功能    与搜索模块对接
    $scope.search=function () {
        //注意：angularjs页面传参时(路由传参)，请求参数？号前面，需要加#
        location.href="http://search.pinyougou.com/search.html#?keywords="+$scope.keywords;
    }
})