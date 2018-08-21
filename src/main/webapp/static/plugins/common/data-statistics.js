/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId,wholeStartTime,wholeEndTime;
(function () {

// 获取用户所有小程序
    allSmallProgram();
    // 自定义时间选择
    laydateTime();

    selecyCondition()

    dateSelecteTime()

    // coreDataSel()
    nestingPie()
    lineBarData()
    barData()
    radarData()

    // coreDataShow()
    // $("#navbarH").height($("#page-wrapper").height() - 80)


}())

// 获取用户所有小程序
function allSmallProgram() {
    $.ajax({
        url: "/wechat/appinfo/findAppInfoListByUser",
        dataType: "json",
        success: function (data) {

            if(data.success){
                var arrData = data.wechatAppInfoEntityList;
                var html='';
                for(var i=0;i<arrData.length;i++){

                    html += '<li data-appInfoId="'+ arrData[i].appInfoId+'" data-appid="'+ arrData[i].appid +'" data-secret="'+ arrData[i].secret +'"><a href="javascript:void(0)">'+arrData[i].appName+ '</a></li>';
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
            if(!wholeEndTime){
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
            if(!wholeStartTime){
                wholeStartTime = value;
            }
            wholeEndTime = value
            changeTimeAfterDataChange()
        }
    });
}

// 筛选条件的选择
function selecyCondition() {
    $("#user_manage .dropdown-menu").off('click',"li");
    $("#user_manage .dropdown-menu").on('click', 'li', function () {

        var that = this;
        var content = $.trim($(this).text());
        var dropMenu;
        var target = $(this).parents(".dropdown").find("button .selectIndex");
        switch ($(this.parentNode).attr("aria-labelledby")) {
            case "dropdownMenu1":
                $(target).html(content)
                var appInfoId = $(that).attr("data-appInfoId");
                if(appInfoId==1){
                    wholeAppInfoId = "5b3dd7c91d76102dd8a2d0d4"
                }else{
                    wholeAppInfoId = appInfoId
                }

                // changeTimeAfterDataChange();
                $($("#contain_main_head > div")[2]).find('input').trigger("click");
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
    // newAndOldUsers(wholeAppInfoId)
    // nestingPie()
}

// 最受欢迎产品系列——嵌套环形图
function nestingPie() {
    var myChartPie = echarts.init(document.getElementById('main_pie'));
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)",
            // position:function(p){ //其中p为当前鼠标的位置
            //     return [p[0] + 10, p[1] - 10];
            // },
            // extraCssText:'width:180px;height:55px;'
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['直达','营销广告','搜索引擎','邮件营销','联盟广告','视频广告','百度','谷歌','必应','其他']
        },
        series: [
            {
                name:'访问来源',
                type:'pie',
                selectedMode: 'single',
                radius: [0, '30%'],

                label: {
                    normal: {
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:335, name:'直达', selected:true},
                    {value:679, name:'营销广告'},
                    {value:1548, name:'搜索引擎'}
                ]
            },
            {
                name:'访问来源',
                type:'pie',
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
                data:[
                    {value:335, name:'直达'},
                    {value:310, name:'邮件营销'},
                    {value:234, name:'联盟广告'},
                    {value:135, name:'视频广告'},
                    {value:1048, name:'百度'},
                    {value:251, name:'谷歌'},
                    {value:147, name:'必应'},
                    {value:102, name:'其他'}
                ]
            }
        ]
    };
    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }
}

// 最受欢迎机型——条形图
function lineBarData() {
    var myChartPie = echarts.init(document.getElementById('main_line'));

    var option = {
        title: {
            text: '世界人口总量',
            subtext: '数据来自网络'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            // position:function(p){ //其中p为当前鼠标的位置
            //     return [p[0] + 10, p[1] - 10];
            // },
            // extraCssText:'width:180px;height:55px;'
        },
        legend: {
            data: [ '2012年']
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
            data: ['巴西','印尼','美国','印度','中国','世界人口(万)']
        },
        series: [
            {
                name: '2012年',
                type: 'bar',
                data: [19325, 23438, 31000, 121594, 134141, 681807]
            }
        ]
    };

    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }

}

// 高频定制需求分析——柱状图
function barData() {
    var myChartPie = echarts.init(document.getElementById('main_bar'));

    var option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            // position:function(p){ //其中p为当前鼠标的位置
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
        xAxis : [
            {
                type : 'category',
                data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'直接访问',
                type:'bar',
                barWidth: '60%',
                data:[10, 52, 200, 334, 390, 330, 220]
            }
        ]
    };

    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }

}
// 最受欢迎价格区间——雷达图
function radarData() {
    var myChartPie = echarts.init(document.getElementById('main_radar'));

    var option = {
        title: {
            text: '基础雷达图'
        },
        tooltip: {
            // position:function(p){ //其中p为当前鼠标的位置
            //     return [p[0] + 10, p[1] - 10];
            // },
            // extraCssText:'width:340px;height:170px;'
        },
        // legend: {
        //     data: [ '实际开销（Actual Spending）']
        // },
        radar: {
            // shape: 'circle',
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]
                }
            },
            indicator: [
                { name: '销售（sales）', max: 6500},
                { name: '管理（Administration）', max: 16000},
                { name: '信息技术（Information Techology）', max: 30000},
                { name: '客服（Customer Support）', max: 38000},
                { name: '研发（Development）', max: 52000},
                { name: '市场（Marketing）', max: 25000}
            ]
        },
        series: [{
            name: '预算 vs 开销（Budget vs spending）',
            type: 'radar',
            // areaStyle: {normal: {}},
            data : [
                {
                    value : [5000, 14000, 28000, 31000, 42000, 21000],
                    name : '实际开销（Actual Spending）'
                }
            ]
        }]
    };

    myChartPie.setOption(option);
    window.onresize = function () {
        myChartPie.resize();
    }
}

