<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <script type="text/javascript"
            src="${pageContext.request.contextPath }/static/js/jquery-1.12.4.min.js"></script>
    <link
            href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
            rel="stylesheet">
    <script
            src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

    <!-- jqGrid组件基础样式包-必要 -->
    <link rel="stylesheet" href="jqgrid/css/ui.jqgrid.css" />

    <!-- jqGrid主题包-非必要 -->
    <!-- 在jqgrid/css/css这个目录下还有其他的主题包，可以尝试更换看效果 -->
    <link rel="stylesheet" href="jqgrid/css/css/redmond/jquery-ui-1.8.16.custom.css" />

    <!-- jquery插件包-必要 -->
    <!-- 这个是所有jquery插件的基础，首先第一个引入 -->
    <script type="text/javascript" src="js/jquery-1.7.1.js"></script>

    <!-- jqGrid插件包-必要 -->
    <script type="text/javascript" src="jqgrid/js/jquery.jqGrid.src.js"></script>

    <!-- jqGrid插件的多语言包-非必要 -->
    <!-- 在jqgrid/js/i18n下还有其他的多语言包，可以尝试更换看效果 -->
    <script type="text/javascript" src="jqgrid/js/i18n/grid.locale-cn.js"></script>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-6 col-md-offset-2 form-inline">
            <form id="form1">
            <input type="text" class="form-control" id="standardCode" placeholder="请输入本位码">
            <input type="text" class="form-control" id="name" placeholder="请输入商品名称">
            <input type="text" class="form-control" id="dosageFormName" placeholder="请输入剂型">
            <input type="text" class="form-control" id="producerName" placeholder="请输入厂家名称">
            <input type="text" class="form-control" id="model" placeholder="请输入规格">
            </form>
            <button id="search" class="form-control btn-primary">搜索</button>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <table id="list2"></table>
            <div id="pager2"></div>
        </div>

    </div>
</div>

<br>
</body>


<script type="text/javascript">

    $(function(){
        //页面加载完成之后执行
        pageInit();
    });
    function pageInit(){
        //创建jqGrid组件
        jQuery("#list2").jqGrid(
                {
                    url : '${pageContext.request.contextPath }/es/getPageGoods',//组件创建完成之后请求数据的url
                    datatype : "json",//请求数据返回的类型。可选json,xml,txt
                    colNames : [ '商品编码', '本位码', '商品名称', '剂型', '厂家名称','规格' ],//jqGrid的列显示名字
                    height:300,
                    colModel : [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
                        {name : 'code',code : 'id',width : 100,align : "center"},
                        {name : 'standardCode',index : 'standardCode',width : 120,align : "center"},
                        {name : 'name',index : 'name',width : 150,align : "center"},
                        {name : 'dosageFormName',index : 'dosageFormName',width : 120,align : "center"},
                        {name : 'producerName',index : 'producerName',width : 250,align : "center"},
                        {name : 'model',index : 'model',width : 150,align : "center"}
                    ],
                    rowNum : 10,//一页显示多少条
                    rowList : [ 10, 20, 30 ],//可供用户选择一页显示多少条
                    pager : '#pager2',//表格页脚的占位符(一般是div)的id
                   // sortname : 'id',//初始化的时候排序的字段
                    sortorder : "desc",//排序方式,可选desc,asc
                    mtype : "post",//向后台请求数据的ajax的类型。可选post,get
                    viewrecords : true,
                    caption : "商品信息"//表格的标题名字
                });
        /*创建jqGrid的操作按钮容器*/
        /*可以控制界面上增删改查的按钮是否显示*/
        jQuery("#list2").jqGrid('navGrid', '#pager2', {edit : false,add : false,del : false,search:false});
    }
    //
    $("#search").click(function(){
        //var content = $("#form1").serialize();
        //传入查询条件参数
        $("#list2").jqGrid("setGridParam",{postData:{
            standardCode:$("#standardCode").val(),
            name:$("#name").val(),
            dosageFormName:$("#dosageFormName").val(),
            producerName:$("#producerName").val(),
            model:$("#model").val()
        }});
        //每次提出新的查询都转到第一页
        $("#list2").jqGrid("setGridParam",{page:1});
        //提交post并刷新表格
        $("#list2").jqGrid("setGridParam",{url:'${pageContext.request.contextPath }/es/getPageGoods'}).trigger("reloadGrid");
    })

</script>
</html>