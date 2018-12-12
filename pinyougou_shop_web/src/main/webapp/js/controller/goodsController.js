 //控制层 
app.controller('goodsController' ,function($scope,$controller   ,goodsService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			//设置商品介绍字段  通过kindEditor获取html内容
			$scope.entity.goodsDesc.introduction = editor.html();

			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//清空录入的商品获取h数据
		        	$scope.entity={};//重新加载
                    editor.html("");//清空编辑器内容
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	//查询一级分类的操作
	$scope.selectItemCat1Service=function () {
		itemCatService.findByParentId(0).success(function (response) {
			$scope.itemCat1List = response;
        })
    }

    //基于一级分类，查询关联的二级分类列表数据  参数一：监控变量值 参数二：监控从内容变化后，需要做的事情
	//newValue监控的变量变化后的值   oldValue监控的变量变化前的值
	$scope.$watch("entity.goods.category1Id",function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat2List = response;
            $scope.itemCat3List = {};
        })
    })

    //基于二级分类，查询关联的三级分类列表数据  参数一：监控变量值 参数二：监控从内容变化后，需要做的事情
    //newValue监控的变量变化后的值   oldValue监控的变量变化前的值
    $scope.$watch("entity.goods.category2Id",function (newValue,oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat3List = response;
        })
    })

    //基于三级分类，查询关联的模板列表数据  参数一：监控变量值 参数二：监控从内容变化后，需要做的事情
    //newValue监控的变量变化后的值   oldValue监控的变量变化前的值
    $scope.$watch("entity.goods.category3Id",function (newValue,oldValue) {
        itemCatService.findOne(newValue).success(function (response) {
            $scope.entity.goods.typeTemplateId = response.typeId;
        })
    })

    //基于模板变化，查询关联的数据  参数一：监控变量值 参数二：监控从内容变化后，需要做的事情
    //newValue监控的变量变化后的值   oldValue监控的变量变化前的值
    $scope.$watch("entity.goods.typeTemplateId",function (newValue,oldValue) {
        typeTemplateService.findOne(newValue).success(function (response) {
            //将返回的品牌json字符串转为json数组
			$scope.brandList = JSON.parse(response.brandIds);
        })
    })
});	
