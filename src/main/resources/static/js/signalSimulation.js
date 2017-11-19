var config = {
  type: 'line',
  data: {
    labels: $ARGUMENTS.slice(25,$ARGUMENTS.length),
    datasets: [{
      label: "Signal",
      backgroundColor: window.chartColors.red,
      borderColor: window.chartColors.red,
      data:  $VALUES.slice(25,$VALUES.length),
      fill: false,
      pointRadius: 0
    },
    {
    label: "Binary data",
    backgroundColor: window.chartColors.green,
    borderColor: window.chartColors.green,
    data: [],
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
var throb = Throbber({
    color: 'red',
    padding: 50,
    size: 70,
    fade: 200,
    clockwise: false
})

window.onload = function() {
  var ctx = $("#canvas")[0].getContext("2d");
  window.myLine = new Chart(ctx, config);
};

$SHOW_BINARY_SIGNAL = false;

$(document).ready(function() {
  $("#show-signal").click(function() {
    $.ajax({
      method: "POST",
      url: "/simulate",
      dataType: 'json',
      contentType: "application/json",
      data: $("#simulationParameters").serializeJSON(),
      progress: function(){
        throb.appendTo(document.getElementById('throbber')).start();
      },
      success: function(data) {
        throb.stop();
        $ARGUMENTS = data.arguments;
        $VALUES = data.values;
        config.data.datasets[0].data = $VALUES.slice(25,$VALUES.length);
        config.data.labels = $ARGUMENTS.slice(25,$ARGUMENTS.length);
        if($SHOW_BINARY_SIGNAL){
          config.data.datasets[1].data = $BINARY_VALUES.slice(25,$BINARY_VALUES.length);
        }
        window.myLine.update();
      }
    });
  });

  $("#generate-binary-sequence").click(function() {
    $.ajax({
      url: "/binary-sequence/new",
      dataType: 'json',
      success: function(data) {
        $("#binary-sequence").empty();
        $.each(data.binarySequence, function(index, bit) {
          $("#binary-sequence").append(function(bitToConvert) {
            if (bitToConvert) {
              return "1&nbsp;"
            }
            return "0&nbsp;"
          }(bit));
        });
        $BINARY_VALUES = data.binarySignal.values;
      }
    });
  });

  $("#addNoise").click(function() {
    if ($("#noiseSNR").attr("disabled")) {
      $("#noiseSNR").attr("disabled", false)
    } else {
      $("#noiseSNR").attr("disabled", true)
    }
  });

  $("#addIsi").click(function() {
    if ($("#isiRate").attr("disabled")) {
      $("#isiRate").attr("disabled", false)
    } else {
      $("#isiRate").attr("disabled", true)
    }
  });

  $("#binary-signal-show").click(function() {
    config.data.datasets[1].data = $BINARY_VALUES.slice(25,$BINARY_VALUES.length);
    window.myLine.update();
    $SHOW_BINARY_SIGNAL = true;
    $(this).attr("hidden", "hidden");
    $("#binary-signal-hide").removeAttr("hidden");
  });

  $("#binary-signal-hide").click(function() {
    config.data.datasets[1].data = [];
    window.myLine.update();
    $SHOW_BINARY_SIGNAL = false;
    $(this).attr("hidden", "hidden");
    $("#binary-signal-show").removeAttr("hidden");
  });

});
