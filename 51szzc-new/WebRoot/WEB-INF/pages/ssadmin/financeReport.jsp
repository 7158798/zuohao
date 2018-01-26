<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="comm/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>

    <div class="pageHeader">
        <form id="searchForm"
              action="ssadmin/financeExport.html" method="post">
            <div class="searchBar">

                <table class="searchContent">
                    <tr>
                        <td>对账日期： <input type="text" name="startDate" class="date" id="startDate"
                                         readonly="true" value="${startDate }" /><font color="red">*</font>
                        </td>
                        <td>
                            <div class="subBar">
                                <ul>
                                    <li><div class="buttonActive">
                                        <div class="buttonContent">
                                            <button type="button" id="export_btn">导出</button>
                                        </div>
                                    </div></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </table>

            </div>
        </form>
    </div>
</head>
<body>

<div id="vcOperationInReport"
     style="min-width: 310px; height: 400px; margin: 0 auto"></div>

</body>
</html>

<script type="text/javascript">
    //点击导出时，导出财务对账单
    $().ready(function(){
        $("#export_btn").click(function(){
            var startDate = $("#startDate").val() || '';
            if(!startDate) {
                alert("请选择对账日期！");
                return;
            }

            $("#searchForm").submit();


        });
    });

</script>
