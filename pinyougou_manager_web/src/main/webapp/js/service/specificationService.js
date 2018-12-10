//自定义服务  参数一：服务名称  参数二：服务层处理的事情  发起http请求
app.service("specificationService",function ($http) {
    //查询所有
    this.findAll=function () {
        return $http.get("../specification/findAll.do");
    }

    this.findPage = function (pageNum,pageSize) {
        return $http.get("../specification/findPage.do?pageNum="+pageNum+"&pageSize="+pageSize);
    }

    this.add = function (entity) {
        return $http.post("../specification/add.do",entity);
    }

    this.update = function (entity) {
        return $http.post("../specification/update.do",entity);
    }

    this.findOne = function (id) {
        return $http.get("../specification/findOne.do?id="+id);
    }

    this.dele = function (ids) {
        return  $http.get("../specification/delete.do?ids="+ids);
    }

    //条件分页查询
    this.search = function (searchEntity,pageNum,pageSize) {
        return $http.post("../specification/search.do?pageNum="+pageNum+"&pageSize="+pageSize,searchEntity)
    }

    //查询模板关联的规格下拉列表数据
    this.selectSpecList=function () {
        return $http.get("../specification/selectSpecList.do");
    }
})