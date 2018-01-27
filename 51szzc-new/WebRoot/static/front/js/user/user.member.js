
var  record= {
    search : function() {
        var begindate = begindate ? begindate : $("#begindate").val();
        var enddate = enddate ? enddate : $("#enddate").val();
        var url = "&begindate=" + begindate + "&enddate=" + enddate + "&currentPage=" + 1;
        window.location.href = "/user/myMember.html?1=1" + url;
    }
};

$(function() {
    
    var currentPoint = $('#currentPoint').val();
    var nextPoint = $('#nextPoint').val();
    var percentage = (currentPoint / nextPoint).toFixed(2);
    var pointwidth = $('.user_levelDivOne').width() * percentage;
    var tipswidth = Math.max(-60,pointwidth - 80);
    $('.user_levelDivTwo').width(pointwidth);
    $('.user_level_tips').css('left', tipswidth);

    $('#begindate').click(function() {
        WdatePicker({
            el : 'begindate',
            maxDate : '#F{$dp.$D(\'enddate\')}',
            dchanged : function() {
                var d = $dp.cal.newdate['d'];
                var m = $dp.cal.newdate['M'];
                var y = $dp.cal.newdate['y'];
                if (m < 0) {
                    m = "07";
                }
            }
        });
    });
    $('#enddate').click(function() {
        WdatePicker({
            el : 'enddate',
            minDate : '#F{$dp.$D(\'begindate\')}',
            dchanged : function() {
                var d = $dp.cal.newdate['d'];
                var m = $dp.cal.newdate['M'];
                var y = $dp.cal.newdate['y'];
                if (m < 0) {
                    m = "07";
                }

            }
        });
    });

    $("#searchScore").click(function() {
        record.search();
    });


});