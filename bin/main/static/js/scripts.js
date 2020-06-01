function addRecord()
{
	var form = $('#add_record_form');
	var data = form.serializeArray();
	var type = form.find('#counterType').val();
	
	$('#add_form_errors').html('');
	$('#add_form_errors').hide();
	
	$.ajax({
        url: '/' + type + '/add',
        data: data,
        async: true,
        method: 'post',
        dataType: 'json',
        success: function(data){
            if (data.result == false) {
            	for (var i in data.errors) {
            		$('#add_form_errors').append(data.errors[i] + '<br>');
            	}
            	
            	$('#add_form_errors').show();
            } else {
            	window.location.href = '/' + type;
            }
        }
    });
}

function editRecord()
{
	var form = $('#edit_record_form');
	var data = form.serializeArray();
	var type = form.find('#counterType').val();
	
	$('#edit_form_errors').html('');
	$('#edit_form_errors').hide();
	
	$.ajax({
        url: '/' + type + '/edit',
        data: data,
        async: true,
        method: 'post',
        dataType: 'json',
        success: function(data){
            if (data.result == false) {
            	for (var i in data.errors) {
            		$('#edit_form_errors').append(data.errors[i] + '<br>');
            	}
            	
            	$('#edit_form_errors').show();
            } else {
            	window.location.href = '/' + type;
            }
        }
    });
}

function loadChart(type, date_from, date_to)
{
	var request_data = {
		type: type,
		period_from: date_from,
		period_to: date_to
	};
	
	$.ajax({
        url: '/charts/load',
        data: request_data,
        async: true,
        method: 'post',
        dataType: 'json',
        success: function(data){
            if (data.result === true) {
            	buildChart(data.records, 'chart_' + type);
            }
        }
    });
}

function reloadCharts()
{
	var date_from = $('#filter_period_from').val();
	var date_to = $('#filter_period_to').val();
	
	loadChart('electricity', date_from, date_to);
	loadChart('water', date_from, date_to);
	loadChart('gas', date_from, date_to);
}

function buildChart(data, selector)
{
	var chart = am4core.create(selector, am4charts.XYChart);
    chart.hiddenState.properties.opacity = 0;
    
    chart.data = [];
    for (var i in data) {
        chart.data[chart.data.length] = {
            date: data[i].period,
            volume: data[i].volume
        };
    }
    
    var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
    categoryAxis.renderer.grid.template.location = 0;
    categoryAxis.dataFields.category = "date";

    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    var series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.categoryX = "date";
    series.dataFields.valueY = "volume";
    
    series.name = 'Цей рік';
    series.strokeWidth = 3;
    series.stroke = am4core.color("#1b7cb3");
    series.fill = am4core.color("#1b7cb3");
    series.minBulletDistance = 10;
    series.tooltipText = "{valueY}";
    series.tooltip.pointerOrientation = "vertical";
    series.tooltip.background.cornerRadius = 20;
    series.tooltip.background.fillOpacity = 0.5;
    series.tooltip.label.padding(12,12,12,12)

    var bullet = series.bullets.push(new am4charts.CircleBullet())
    bullet.fill = new am4core.InterfaceColorSet().getFor("background");
    bullet.fillOpacity = 1;
    bullet.strokeWidth = 2;
    bullet.circle.radius = 4;
    
    chart.cursor = new am4charts.XYCursor();
    chart.cursor.xAxis = categoryAxis;
    chart.cursor.snapToSeries = series;
}

function loadCompareChart(type)
{
	var request_data = {
		type: type,
	};
	
	$.ajax({
        url: '/charts/loadcompare',
        data: request_data,
        async: true,
        method: 'post',
        dataType: 'json',
        success: function(data){
            if (data.result === true) {
            	buildCompareChart(data.records, 'chart_compare');
            }
        }
    });
}

function buildCompareChart(data, selector)
{
	var chart = am4core.create(selector, am4charts.XYChart);
    
    chart.data = [];
    for (var i in data.current) {
        chart.data[chart.data.length] = {
            date: data.current[i].period,
            volume_1: data.current[i].volume,
            volume_2: data.last[i].volume
        };
    }
    
    var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
    categoryAxis.renderer.grid.template.location = 0;
    categoryAxis.dataFields.category = "date";
    
    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    
    var series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.valueY = "volume_1";
    series.dataFields.categoryX = "date";
    series.name = 'Цей рік';
    series.strokeWidth = 3;
    series.stroke = am4core.color("#1b7cb3");
    series.fill = am4core.color("#1b7cb3");
    series.minBulletDistance = 10;
    series.tooltipText = "{name} {valueY}";
    series.tooltip.pointerOrientation = "vertical";
    series.tooltip.background.cornerRadius = 20;
    series.tooltip.background.fillOpacity = 0.5;
    series.tooltip.label.padding(12,12,12,12)

    var bullet = series.bullets.push(new am4charts.CircleBullet())
    bullet.fill = new am4core.InterfaceColorSet().getFor("background");
    bullet.fillOpacity = 1;
    bullet.strokeWidth = 2;
    bullet.circle.radius = 4;
    
    var series_2 = chart.series.push(new am4charts.LineSeries());
    series_2.dataFields.valueY = "volume_2";
    series_2.dataFields.categoryX = "date";
    series_2.name = 'Минулий рік';
    series_2.strokeWidth = 3;
    series_2.stroke = am4core.color("#ec0800");
    series_2.fill = am4core.color("#ec0800");
    series_2.minBulletDistance = 10;
    series_2.tooltipText = "{name} {valueY}";
    series_2.tooltip.pointerOrientation = "vertical";
    series_2.tooltip.background.cornerRadius = 20;
    series_2.tooltip.background.fillOpacity = 0.5;
    series_2.tooltip.label.padding(12,12,12,12)

    var bullet_2 = series_2.bullets.push(new am4charts.CircleBullet())
    bullet_2.fill = new am4core.InterfaceColorSet().getFor("background");
    bullet_2.fillOpacity = 1;
    bullet_2.strokeWidth = 2;
    bullet_2.circle.radius = 4;
    
    chart.cursor = new am4charts.XYCursor();
    chart.cursor.xAxis = categoryAxis;
    chart.cursor.snapToSeries = series;
    
    chart.legend = new am4charts.Legend();
}