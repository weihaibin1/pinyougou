//服务层
app.service('searchService',function($http){

	//商品搜索
	this.searchItem=function (searchMap) {
        return $http.post('search/searchItem.do',searchMap);
    }
});
