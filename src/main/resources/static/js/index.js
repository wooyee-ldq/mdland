$(function(){

    $.ajax({
        url: "/mdland/filelist",
        method: "get",
        async: true,
        dataType: "json",
        data: {},
        success:function(data){
            var code = data.code;
            if(code == 600){
                var list = data.data;
                for(var i = 0; i < list.length; i++){
                    var filename = list[i];
                    var li = "<li title='" + filename + "'>" + filename + "</li>";
                    $("#list").append(li);
                }
            }else{
                var li = "<li>获取文件列表失败，请刷新页面！</li>"
                $("#list").append(li);
            }
        },
        error:function(err){
            var li = "<li>error！</li>"
            $("#list").append(li);
        }
    });

    $.ajax({
        url: "/mdland/getlsfiln",
        method: "get",
        async: true,
        dataType: "json",
        data: {},
        success:function(data){
            var code = data.code;
            if(code == 600){
                var file = data.data;
                if(file != null && file != ""){
                    $.ajax({
                        url: "/mdland/readfile",
                        method: "get",
                        async: true,
                        dataType: "json",
                        data: {
                            filename:file
                        },
                        success:function(data){
                            var code = data.code;
                            var context = data.data;
                            if(code == 600){
                                // 修改原文本数据副本内容
                                oldText = context;
                                // 设置编写区域内容
                                $("#editArea").val(context);
                                // 设置文件列表打开文件样式
                                $("#list li").removeClass("editnow");
                                var thisli = $("[title='" + file + "']");
                                thisli.addClass("editnow");
                                // 设置编写区域的文件属性为当前文件
                                $("#savefile").attr("file", file);
                                // 渲染文本
                                render(context);
                            }else{
                                alert("文件打开失败：" + context);
                            }
                        },
                        error:function(err){
                            alert("error!");
                        }
                    });
                }
            }else{
                alert("read last file error!");
            }
        },
        error:function(err){
            alert("read last file error!");
        }
    });

    // 定时保存修改
    setInterval(function () {
        var data = $("#editArea").val();
        var file = $("#savefile").attr("file");
        if(file == null || file == ""){
            return;
        } else{
            $.ajax({
                url: "/mdland/savefile",
                method: "post",  // 这里要设置为post，get传输的参数在请求头，当传输要保存的文本太大就会请求失败！！！
                async: true,
                dataType: "json",
                data: {
                    filename:file,
                    context:data
                },
                success:function(data){
                    var code = data.code;
                    var context = data.data;
                    if(code == 600){
                        // alert("保存成功：" + context);
                    }else{
                        // alert("保存失败：" + context);
                    }
                },
                error:function(err){
                    // alert("error!");
                }
            });
        }
    },10000);

    // 测试---------------------------------------------------------------------------------------------------------------------------------------
    // let li = "<li title = '获取文件列表失败，请刷新页面！'>获取文件列表失败，请刷新页面！</li>";
    // $("#list").append(li); 



    // 测试---------------------------------------------------------------------------------------------------------------------------------------

    // 实时转换
    var oldText = $("#editArea").val();
    $("#editArea").on('input propertychange', function() {
        var currentVal = $(this).val();
        if(currentVal != oldText){
            oldText = currentVal;
            render(currentVal)
            $("#editArea").attr("isSave", "false");
        }
    
    });

    // html渲染
    function render(text){
        var html = marked(text);
        var htmlElem = $("#html");
        htmlElem.empty();
        htmlElem.append(html);
    }
    // function render(text){
    //     var converter = new showdown.Converter();
    //     var html = converter.makeHtml(text);
    //     var htmlElem = $("#html");
    //     htmlElem.empty();
    //     htmlElem.append(html);
    // }

    // 点击列表拉链
    $("#showli").click(function(){
        var text = $(this).html();
        if(text == "&lt;&lt;" || text == "<<"){
            $(this).html("&gt;&gt;");
            $("#main").removeClass("bwid").addClass("swid");
        }else{
            $(this).html("&lt;&lt;");
            $("#main").removeClass("swid").addClass("bwid");
        }
        $("#filelist").toggle(200);
    });


    // 读取文件
    $("#list").on('click', 'li', function(){
        if($("#editArea").attr("isSave") == "false"){
            var isSave = confirm($("#savefile").attr("file") + " 文件被修改，是否保存？");
            if(isSave){
                var data = $("#editArea").val();
                var file = $("#savefile").attr("file");
                $.ajax({
                    url: "/mdland/savefile",
                    method: "get",  // 这里设置为post，禁用了此功能，本地电脑无法测试
                    async: true,
                    dataType: "json",
                    data: {
                        filename:file,
                        context:data
                    },
                    success:function(data){
                        var code = data.code;
                        var context = data.data;
                        if(code == 600){
                            alert("保存成功：" + context);
                            $("#editArea").attr("isSave", "true");
                        }else{
                            alert("保存失败：" + context);
                            return;
                        }
                    },
                    error:function(err){
                        alert("error!");
                        return;
                    }
                });
            }else{
                return;
            }
        }
        var thisli = $(this);
        var file = thisli.attr("title");
        if(file != null && file != ""){
            $.ajax({
                url: "/mdland/readfile",
                method: "get",
                async: true,
                dataType: "json",
                data: {
                    filename:file
                },
                success:function(data){
                    var code = data.code;
                    var context = data.data;
                    if(code == 600){
                        // 修改原文本数据副本内容
                        oldText = context;
                        // 设置编写区域内容
                        $("#editArea").val(context);
                        // 设置文件列表打开文件样式
                        $("#list li").removeClass("editnow");
                        thisli.addClass("editnow");
                        // 设置编写区域的文件属性为当前文件
                        $("#savefile").attr("file", file);
                        // 渲染文本
                        render(context);
                    }else{
                        alert("文件打开失败：" + context);
                    }
                },
                error:function(err){
                    alert("error!");
                }
            });
        }
    });

    // 新建文件
    $("#newfile").click(function(){
        // 显示输入文件名
        var file = prompt("文件名", "");
        // 请求后台创建文件
        if(file == null){
            return;
        }
        if(file == ""){
            alert("文件名不能为空！");
        }else{
            $.ajax({
                url: "/mdland/newpost",
                method: "get",  // 这里设置为post，禁用了此功能，本地电脑无法测试
                async: true,
                dataType: "json",
                data: {
                    filename:file
                },
                success:function(data){
                    var code = data.code;
                    var context = data.data;
                    if(code == 600){
                        $("#list li").removeClass("editnow");
                        if(!file.endsWith(".md")){
                            file = file + ".md";
                        }
                        var li = "<li class='editnow' title='" + file + "'>" + file + "</li>";
                        $("#list").prepend(li);
                        oldText = context;
                        $("#editArea").val(context);
                        $("#savefile").attr("file", file);
                        $("#editArea").attr("isSave", "true");
                        render(context);
                    }else{
                        alert("文件创建失败：" + context);
                    }
                },
                error:function(err){
                    alert("error!");
                }
            });
        }
    });


    // 保存文件
    $("#savefile").click(function(){
        var data = $("#editArea").val();
        var file = $("#savefile").attr("file");
        if(file == null || file == ""){
            alert("请打开要保存的文件！");
        } else{
            $.ajax({
                url: "/mdland/savefile",
                method: "post",  // 这里要设置为post，get传输的参数在请求头，当传输要保存的文本太大就会请求失败！！！
                async: true,
                dataType: "json",
                data: {
                    filename:file,
                    context:data
                },
                success:function(data){
                    var code = data.code;
                    var context = data.data;
                    if(code == 600){
                        alert("保存成功：" + context);
                        $("#editArea").attr("isSave", "true");
                    }else{
                        alert("保存失败：" + context);
                    }
                },
                error:function(err){
                    alert("error!");
                }
            });
        }
    });

    // 发布
    $("#release").click(function(){
        var isrl = confirm("确认要发布到blog吗？");
        if(isrl){
            $.ajax({
                url: "/mdland/release",
                method: "get",  // 这里设置为post，禁用了此功能，本地电脑无法测试
                async: true,
                dataType: "json",
                data: {},
                success:function(data){
                    var code = data.code;
                    var context = data.data;
                    if(code == 600){
                        alert("发布成功：" + context);
                    }else{
                        alert("发布失败：" + context);
                    }
                },
                error:function(err){
                    alert("error!");
                }
            });
        }
    });




});
