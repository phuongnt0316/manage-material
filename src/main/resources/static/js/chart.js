google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);


function drawChart() {
  //  console.log(chart2)
   // var array  = JSON.parse(chart2);
    var rs = new Array();
    var x1 = chart2.map(x => x["name"])
    var y1 = chart2.map(x => x["sum"])
    rs.push(['name', 'sum'])
    for (var i = 0; i < x1.length; i++) {
        rs.push([x1[i],y1[i]]);

    }
    //console.log(rs)
    var data = google.visualization.arrayToDataTable(rs);

    var options = {
        title: 'Doanh thu theo loại'
    };

    var chart = new google.visualization.PieChart(document.getElementById('piechart'));

    chart.draw(data, options);
}
//google.charts.load('current', {'packages':['corechart']});

google.charts.load('current', {'packages':['line']});
google.charts.setOnLoadCallback(drawChartLine);
function drawChartLine() {
   // console.log(chart3)
    // var array  = JSON.parse(chart2);
    var rs = new Array();
    var x1 = chart3.map(x => x["month"])
    var y1 = chart3.map(x => x["sum"])
    rs.push(['Tháng', 'Tổng'])
    for (var i = 0; i < x1.length; i++) {
        rs.push([x1[i],y1[i]]);

    }
    var data = google.visualization.arrayToDataTable(rs);


    // var options = {
    //     title: 'Company Performance',
    //     curveType: 'function',
    //     legend: { position: 'bottom' }
    // };
    //
    // var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
    //
    // chart.draw(data, options);
    var options = {
        chart: {
            title: '',
            subtitle: ''
        }
    };

    var chart = new google.charts.Line(document.getElementById('curve_chart'));

    chart.draw(data, google.charts.Line.convertOptions(options));

}
