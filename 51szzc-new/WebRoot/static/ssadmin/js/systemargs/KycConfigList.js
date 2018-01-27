$().ready(function () {

    var kyc1_isEdited = false;
    var kyc2_isEdited = false;


    $('#kyc1_edit').click(function () {
            var flag = true;
            var tbody1 = $(".grid tbody").eq(0);
            if (kyc1_isEdited == false) {
                $('#kyc1_edit span').html("保存");
                kyc1_isEdited = true;
                //将表格可变数据部分，变为文本框

                $("tr", tbody1).each(function (i, trobj) {
                    $(this).find("td").each(function (j, tdobj) {
                        if (j == 0) {  //第一列是币种名称，跳过
                            return true;
                        }

                        //修改前数值
                        var old_num = $(tdobj).find("div").html();
                        //改为文本框
                        var text_obj_html = "<input type='text' value='" + old_num + "'/>";
                        $(tdobj).find("div").html(text_obj_html);
                    });
                });

            } else {
                //获取表格修改的数据
                var data_arr0 = new Array();
                $("tr", tbody1).each(function (i, trobj) {
                    var row_data = {};
                    $(this).find("td").each(function (j, tdobj) {
                        if (j == 0) {  //币种id
                            row_data["ftype"] = $(tdobj).attr("rel");
                            return true;
                        }
                        var new_num = $(tdobj).find("input").eq(0).val();
                        //必须为数字,第一列必须为整数
                        if (isNaN(new_num) || (j == 1 && !/^\d+$/.test(new_num))) {
                            console.log(i + "  " + j + "dd  = " + new_num);
                            flag = false;
                            $(tdobj).find("input").eq(0).css("border", "1px solid red");
                            return true;
                        } else {
                            $(tdobj).find("input").eq(0).css("border", "1px solid #cccccc");
                        }

                        //获取文本框内容
                        if (j == 1) {  //每日提现次数
                            row_data["kyc1_day_count"] = new_num;
                        } else if (j == 2) {  //单笔提现额度
                            row_data["kyc1_single_limit"] = new_num;
                        } else if (j == 3) {  //每日提现额度
                            row_data["kyc1_day_limit"] = new_num;
                        } else if (j == 4) {//每月提现额度
                            row_data["kyc1_month_limit"] = new_num;

                        }
                        //此处不能还原文本框为数字
                    });
                    data_arr0.push(row_data);
                });

                if (!flag) {
                    alertMsg.error("数据格式不正确，请修改后再保存");
                    return;
                }

                //输出数据
                // console.log(data_arr);
                //保存数据到后台
                $.ajax({
                    url: "/ssadmin/kycconfig/save.html?type=1",
                    type: "POST",
                    dataType: "json",
                    async: false,
                    data: "jsonStr=" + encodeURIComponent(JSON.stringify(data_arr0)),
                    success: function (data) {
                        console.log(data);
                        if (data.statusCode == 300) {
                            alertMsg.error(data.message);
                        } else {
                            $('#kyc1_edit span').html("修改");
                            kyc1_isEdited = false;
                            alertMsg.info(data.message);
                            //还原文本框为数字
                            $("tr", tbody1).each(function (i, trobj) {
                                $(this).find("td").each(function (j, tdobj) {
                                    var new_num = $(tdobj).find("input").eq(0).val();
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


    //kyc2


    $('#kyc2_edit').click(function () {
            var flag = true;
            var tbody2 = $(".grid tbody").eq(1);
            if (kyc2_isEdited == false) {
                $('#kyc2_edit span').html("保存");
                kyc2_isEdited = true;
                //将表格可变数据部分，变为文本框
                var a = $("tr", tbody2).length;
                console.log(a);
                $("tr", tbody2).each(function (i, trobj) {
                    console.log("111");
                    $(this).find("td").each(function (j, tdobj) {
                        console.log("222");
                        if (j == 0) {  //第一列是币种名称，跳过
                            return true;
                        }

                        //修改前数值
                        var old_num = $(tdobj).find("div").html();
                        //改为文本框
                        var text_obj_html = "<input type='text' value='" + old_num + "'/>";
                        $(tdobj).find("div").html(text_obj_html);
                    });
                });

            } else {
                //获取表格修改的数据
                var data_arr1 = new Array();
                $("tr", tbody2).each(function (i, trobj) {
                    var row_data = {};
                    $(this).find("td").each(function (j, tdobj) {
                        if (j == 0) {  //币种id
                            row_data["ftype"] = $(tdobj).attr("rel");
                            return true;
                        }
                        var new_num = $(tdobj).find("input").eq(0).val();

                        if (isNaN(new_num) || (j == 1 && !/^\d+$/.test(new_num))) {
                            console.log(i + "  " + j + "dd  = " + new_num);
                            flag = false;
                            $(tdobj).find("input").eq(0).css("border", "1px solid red");
                            return true;
                        } else {
                            $(tdobj).find("input").eq(0).css("border", "1px solid #cccccc");
                        }

                        //获取文本框内容
                        if (j == 1) {  //每日提现次数
                            row_data["kyc2_day_count"] = new_num;
                        } else if (j == 2) {  //单笔提现额度
                            row_data["kyc2_single_limit"] = new_num;
                        } else if (j == 3) {  //每日提现额度
                            row_data["kyc2_day_limit"] = new_num;
                        } else if (j == 4) {//每月提现额度
                            row_data["kyc2_month_limit"] = new_num;
                        }
                        //此处不能还原文本框为数字
                    });
                    data_arr1.push(row_data);
                });

                if (!flag) {
                    alertMsg.error("数据格式不正确，请修改后再保存");
                    return;
                }

                //输出数据
                // console.log(data_arr);
                //保存数据到后台
                $.ajax({
                    url: "/ssadmin/kycconfig/save.html?type=2",
                    type: "POST",
                    dataType: "json",
                    async: false,
                    data: "jsonStr=" + encodeURIComponent(JSON.stringify(data_arr1)),
                    success: function (data) {
                        console.log(data);
                        if (data.statusCode == 300) {
                            alertMsg.error(data.message);
                        } else {
                            $('#kyc2_edit span').html("修改");
                            kyc2_isEdited = false;
                            alertMsg.info(data.message);
                            //还原文本框为数字
                            $("tr", tbody2).each(function (i, trobj) {
                                $(this).find("td").each(function (j, tdobj) {
                                    var new_num = $(tdobj).find("input").eq(0).val();
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
