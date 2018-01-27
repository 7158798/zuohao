$().ready(function () {

    var isEdited = false;

    $('#a_edit').click(function () {
            var flag = true;
            if (isEdited == false) {
                $('#a_edit span').html("保存");
                isEdited = true;
                //将表格可变数据部分，变为文本框
                $(".grid tbody tr").each(function(i, trobj) {
                    $(this).find("td").each(function(j, tdobj){
                        if(j == 0) {  //第一列是币种名称，跳过
                            return true;
                        }

                        //修改前数值
                        var old_num = $(tdobj).find("div").html();
                        //改为文本框
                        var text_obj_html =  "<input type='text' value='"+old_num+"'/>";
                        $(tdobj).find("div").html(text_obj_html);
                    });
                });

            } else {
                //获取表格修改的数据
                var data_arr = new Array();
                $(".grid tbody tr").each(function(i, trobj) {
                    var row_data = {};
                    $(this).find("td").each(function(j, tdobj){
                        if(j == 0) {  //币种id
                            row_data["fid"] = $(tdobj).attr("rel");
                            return true;
                        }

                        var input_text = $(tdobj).find("input").eq(0);
                        var new_num  = input_text.val();

                        if(isNaN(new_num)) {
                            flag = false;
                            $(tdobj).find("input").eq(0).css("border", "1px solid red");
                            return true;
                        }else{
                            $(tdobj).find("input").eq(0).css("border", "1px solid #cccccc");
                        }

                        //获取文本框内容
                        if(j == 1) {  //单笔额度
                            row_data["auto_single_limit"] = new_num;
                        }else if(j == 2) {  //每日额度
                            row_data["auto_day_limit"] = new_num;
                        }else if(j == 3) {  //每日次数
                            row_data["auto_day_count"] = new_num;
                        }
                        //此处不能还原文本框为数字
                    });
                    data_arr.push(row_data);
                });

                if(!flag) {
                    alertMsg.error("数据格式不正确，请修改后再保存");
                    return;
                }

                //输出数据
                // console.log(data_arr);
                //保存数据到后台
                $.ajax({
                    url: "/ssadmin/updateautowithconfig.html",
                    type: "POST",
                    dataType: "json",
                    async:false,
                    data: "jsonStr=" + encodeURIComponent(JSON.stringify(data_arr)),
                    success: function (data) {
                        if(data.statusCode == 300) {
                            alertMsg.error(data.message);
                        }else{
                            $('#a_edit span').html("修改");
                            isEdited = false;
                            alertMsg.info(data.message);
                            //还原文本框为数字
                            $(".grid tbody tr").each(function(i, trobj) {
                                $(this).find("td").each(function(j, tdobj) {
                                    var new_num  = $(tdobj).find("input").eq(0).val();
                                    $(tdobj).find("div").html(new_num);  //隐藏文本框，单元格内容设置为最新的值
                                });
                            });

                        }
                    }
                });
                                //保存结束
            }

        }
    );

});
