var config = {
  type: 'line',
  data: {
    labels: $ARGUMENTS,
    datasets: [{
      label: "Signal",
      backgroundColor: window.chartColors.red,
      borderColor: window.chartColors.red,
      data: $VALUES,
      fill: false,
      pointRadius: 0
    }]
  },
  options: {
    responsive: true,
    title: {
      display: true,
      text: 'Signal'
    },
    tooltips: {
      mode: 'index',
      intersect: false
    },
    hover: {
      mode: 'nearest',
      intersect: true
    },
    scales: {
      xAxes: [{
        display: true,
        scaleLabel: {
          display: true,
          labelString: 'time'
        }
      }],
      yAxes: [{
        display: true,
        scaleLabel: {
          display: true,
          labelString: 'Value'
        }
      }]
    }
  }
};

window.onload = function() {
  var ctx = $("#canvas")[0].getContext("2d");
  window.myLine = new Chart(ctx, config);
};

$(document).ready(function() {
  $("#show-signal").click(function() {
    $.ajax({
      url: "/figure/" + $("#pulseType").val(),
      dataType: 'json',
      success: function(data) {
        $ARGUMENTS = data.arguments;
        $VALUES = data.values;
        config.data.datasets[0].data = $VALUES;
        config.data.labels = $ARGUMENTS;
        window.myLine.update();
      }
    });
  });
});
