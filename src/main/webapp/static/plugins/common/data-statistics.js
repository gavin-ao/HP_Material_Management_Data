/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId, wholeStartTime, wholeEndTime;
(function () {

// 获取用户所有小程序
//     allSmallProgram();
    // 自定义时间选择
    laydateTime();

    // selecyCondition()

    dateSelecteTime()

    selecyModel()

    $($("#contain_main_head > div")[2]).find('input').trigger("click");
}())

// 获取用户所有小程序
function allSmallProgram() {
    $.ajax({
        url: "/wechat/appinfo/findAppInfoListByUser",
        dataType: "json",
        success: function (data) {

            if (data.success) {
                var arrData = data.wechatAppInfoEntityList;
                var html = '';
                for (var i = 0; i < arrData.length; i++) {

                    html += '<li data-appInfoId="' + arrData[i].appInfoId + '" data-appid="' + arrData[i].appid + '" data-secret="' + arrData[i].secret + '"><a href="javascript:void(0)">' + arrData[i].appName + '</a></li>';
                }
                $("#contain_head_left_ul").html(html);

                $($($("#user_manage .dropdown-menu")[0]).find("li")[0]).trigger("click");
            }
        }
    })
}
function laydateTime() {
    lay('#version').html('-v' + laydate.v);
    var myDate = new Date();
    var time = currentTime(myDate);
//执行一个laydate实例
//     开始时间
    laydate.render({
        elem: '#startTime' //指定元素
        , value: time
        , done: function (value, date) {
            $(".contain_main_title .time1").html(value)
            wholeStartTime = value;
            if (!wholeEndTime) {
                wholeEndTime = value;
            }
            changeTimeAfterDataChange()
        }

    });

    // 结束时间
    laydate.render({
        elem: '#endTime' //指定元素
        , value: time
        , done: function (value, date) {
            $(".contain_main_title .time2").html(value)
            if (!wholeStartTime) {
                wholeStartTime = value;
            }
            wholeEndTime = value
            changeTimeAfterDataChange()
        }
    });
}

// 筛选条件的选择
function selecyCondition() {
    $("#user_manage .dropdown-menu").off('click', "li");
    $("#user_manage .dropdown-menu").on('click', 'li', function () {

        var that = this;
        var content = $.trim($(this).text());
        var dropMenu;
        var target = $(this).parents(".dropdown").find("button .selectIndex");
        switch ($(this.parentNode).attr("aria-labelledby")) {
            case "dropdownMenu1":
                $(target).html(content)
                var appInfoId = $(that).attr("data-appInfoId");
                if (appInfoId == 1) {
                    wholeAppInfoId = "5b3dd7c91d76102dd8a2d0d4"
                } else {
                    wholeAppInfoId = appInfoId
                }

                // changeTimeAfterDataChange();
                break;
            case "dropdownMenu2":
                $(target).html(content)
                break;
        }
    });
}


// 时间选择
function dateSelecteTime() {
    var startTime, endTime;
    $("#contain_main_head").off('click', "div>input");
    $("#contain_main_head").on('click', "div>input", function () {
        var inputs = $(this).parent().siblings().find("input");
        for (var i = 0; i < inputs.length; i++) {
            inputs[i].removeAttribute("checked");
        }
        $(this).attr("checked", true)
        className = $(this).attr("class");
        $(".datePicker").css("display", "none");
        var dateTimes;

        switch (className) {
            case "todayTime":
                dateTimes = currentTime(new Date());
                startTime = dateTimes
                endTime = dateTimes
                break;
            case "yesterdayTime":
                dateTimes = currentTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
                endTime = dateTimes
                startTime = dateTimes
                break;
            case "Nearly7days":
                dateTimes = currentTime(new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000));
                startTime = dateTimes
                var curdateTimes = currentTime(new Date());
                endTime = curdateTimes
                break;
            case "userdefined":
                $(".datePicker").css("display", "block");
                dateTimes = currentTime(new Date());
                startTime = dateTimes
                endTime = dateTimes

                break;
        }
        $("#startTime").val(startTime);
        $("#endTime").val(endTime);
        $(".contain_main_title .time1").html(startTime)
        $(".contain_main_title .time2").html(endTime)
        wholeStartTime = startTime;
        wholeEndTime = endTime
        changeTimeAfterDataChange()

    })
}

// 获取当前时间
function currentTime(myDate) {
    var year = myDate.getFullYear();
    var mounth = (myDate.getMonth() + 1) > 9 ? (myDate.getMonth() + 1) : "0" + (myDate.getMonth() + 1);
    var date = myDate.getDate() > 9 ? myDate.getDate() : "0" + myDate.getDate();
    return year + "-" + mounth + "-" + date;
}

// 改变时间，数据及图表相继改变
function changeTimeAfterDataChange() {

    // 最受欢迎产品系列——嵌套环形图
    gainPieData();
    // 最受欢迎机型——条形图
    gainLineData();

    // 高频定制需求分析——柱状图
    allCustomizedModel();

    // 最受欢迎价格区间——雷达图
    gainRadarData();
}

// 获取 嵌套环形图 数据
function gainPieData() {
    $.ajax({
        url: "/ctoproduct/total/totalCtoCatgView",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        dataType: "json",
        success: function (data) {
            if (data.success) {
                nestingPie(data.data)
            }
        }
    })
}

// 获取 条形图 数据
function gainLineData() {
    $.ajax({
        url: "/ctoproduct/total/totalHotTopTenView",
        data: {startDate: wholeStartTime, endDate: wholeEndTime, topNum: 10},
        dataType: "json",
        success: function (data) {
            if (data.success) {
                lineBarData(data.data)
            }
        }
    })
}

// 获取 柱状图 数据
function gainBarData(productId) {
    $.ajax({
        url: "/ctoproduct/total/totalChangePartsView",
        data: {startDate: wholeStartTime, endDate: wholeEndTime, productName: productId},
        dataType: "json",
        success: function (data) {
            if (data.success) {
                barData(data.data,productId)
            }
        }
    })
}

// 获取 雷达图 数据
function gainRadarData() {
    $.ajax({
        url: "/ctoproduct/total/totalHotPricesView",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        dataType: "json",
        success: function (data) {
            if (data.success) {
                radarData(data.data)
            }
        }
    })
}


// 最受欢迎产品系列——嵌套环形图
function nestingPie(data) {
    var largeData = [], lowerData = []
    for (var i = 0; i < data.length; i++) {
        largeData.push({name: data[i].name, value: data[i].number});
        if (data[i].child && data[i].child.length > 0) {
            var childData = data[i].child;
            for (var j = 0; j < childData.length; j++) {
                lowerData.push({name: childData[j].name, value: childData[j].number})
            }
        }
    }
    var myChartPie = echarts.init(document.getElementById('main_pie'));
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
        series: [
            {
                name: '电脑类型',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '30%'],

                label: {
                    position: 'inner',
                    color: "#fff"
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: largeData
            },
            {
                name: '系列',
                type: 'pie',
                radius: ['40%', '55%'],
                label: {
                    normal: {
                        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                        backgroundColor: '#eee',
                        borderColor: '#aaa',
                        borderWidth: 1,
                        borderRadius: 4,
                        rich: {
                            a: {
                                color: '#999',
                                lineHeight: 22,
                                align: 'center'
                            },
                            hr: {
                                borderColor: '#aaa',
                                width: '100%',
                                borderWidth: 0.5,
                                height: 0
                            },
                            b: {
                                fontSize: 16,
                                lineHeight: 33
                            },
                            per: {
                                color: '#eee',
                                backgroundColor: '#334455',
                                padding: [2, 4],
                                borderRadius: 2
                            }
                        }
                    }
                },
                data: lowerData
            }
        ]
    };
    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }
}

// 最受欢迎机型——条形图
function lineBarData(data) {
    var nameData = [], setData = [];
    data = data.sort(compare("totalNum"))
    console.log(data)
    for (var i = 0; i < data.length; i++) {
        nameData.push(data[i].showText)
        setData.push(data[i].totalNum)
    }

    var myChartPie = echarts.init(document.getElementById('main_line'));

    var option = {
        // title: {
        //     text: '世界人口总量',
        //     subtext: '数据来自网络'
        // },
        tooltip: {
            trigger: 'item',
            // axisPointer: {
            //     type: 'shadow'
            // }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: nameData
        },
        series: [
            {
                name: '最受欢迎机型',
                type: 'bar',
                barMaxWidth: "50px",
                data: setData
            }
        ]
    };

    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }

}

// 高频定制需求分析——柱状图
function barData(data,productId) {
    console.log(data)
    var xData = [],setData=[]
    for(var i=0;i<data.length;i++){
        xData.push(data[i].showText)
        setData.push(data[i].totalNum)
    }
    var myChartPie = echarts.init(document.getElementById('main_bar'));

    var option = {
        color: ['#3398DB'],
        tooltip: {
            trigger: 'item',
            // axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            //     type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            // },
            // position:function(p){ //其中p为当前鼠标的位置git
            //     return [p[0] + 10, p[1] - 10];
            // },
            // extraCssText:'width:180px;height:55px;'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: xData,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: productId,
                type: 'bar',
                barWidth: '50px',
                data: setData
            }
        ]
    };

    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }

}

// 最受欢迎价格区间——雷达图
function radarData(data) {
    var nameData = [], setData = []
    for(var i= 0;i<data.length;i++){
        nameData.push({name: data[i].name})
        setData.push(data[i].value)
    }
    var max = Math.max.apply(null,setData);
    for(var i=0;i<nameData.length;i++){
        nameData[i].max = max/8 + max;
    }
    console.log(max)
    var myChartPie = echarts.init(document.getElementById('main_radar'));

    var option = {
        tooltip: {},
        radar: {
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]
                }
            },
            indicator: nameData
        },
        series: [{
            name: '最受欢迎价格区间',
            type: 'radar',
            data: [
                {
                    value: setData,
                    name: '最受欢迎价格区间'
                }
            ]
        }]
    };

    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }
}


function compare(property) {
    return function (a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value1 - value2;
    }
}

// 获取 高频定制需求分析 所有机型
function allCustomizedModel() {
    $.ajax({
        url: "/ctoproduct/total/selectProductList",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        dataType: "json",
        success: function (data) {
            console.log(data)
            if (data.success) {
                var arrData = data.data;
                var html = '';
                for (var i = 0; i < arrData.length; i++) {
                    html += '<li data-productid="' + arrData[i] + '"><a href="javascript:void(0)">' + arrData[i] + '</a></li>';
                }
                $("#modelContent").html(html);
                $($("#modelContent").find("li")[0]).trigger("click");
            }
        }
    })
}

// 选择机型
function selecyModel() {
    $("#modelContent").off('click', "li");
    $("#modelContent").on('click', 'li', function () {
        var that = this;
        var content = $.trim($(this).text());
        var target = $(this).parents(".dropdown").find("button .selectIndex");
        $(target).html(content)
        var productId = $(that).attr("data-productid");
        gainBarData(productId);
    });
}

