var config = {
  type: 'line',
  data: {
    labels: $ARGUMENTS, //.slice(50,$ARGUMENTS.length),
    datasets: [{
      label: "Signal",
      backgroundColor: window.chartColors.red,
      borderColor: window.chartColors.red,
      data:  $VALUES, //.slice(50,$VALUES.length),
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
        },
        ticks: {
                  //  min: 0,
                   max: 2
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

function updateSimulationResults(data){
  $("#low-mean-value").text(data.lowMeanValue);
  $("#high-mean-value").text(data.highMeanValue);
  $("#low-standard-deviation").text(data.lowStandardDeviation);
  $("#high-standard-deviation").text(data.higStandardDeviation);
  $("#q-parameter").text(data.qParameter);
  $("#ber").text(data.bitErrorRate);
  $("#p-isi").text(data.receiverSensitivityLoss);
}

function showBits(binarySequence, numberOfBits){
  $("#binary-sequence").empty();
  $.each(binarySequence.slice(0,numberOfBits), function(index, bit) {
    $("#binary-sequence").append(function(bitToConvert) {
      if (bitToConvert) {
        return "1 "
      }
      return "0 "
    }(bit));
  });
}

window.onload = function() {
  var ctx = $("#canvas")[0].getContext("2d");
  window.myLine = new Chart(ctx, config);
};

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
        $ARGUMENTS = data.simulatedSignal.arguments;
        $VALUES = data.simulatedSignal.values;
        config.data.datasets[0].data = $VALUES;
        config.data.labels = $ARGUMENTS;
        if($("#binary-signal-hide").hasClass("hidden")){
          config.data.datasets[1].data = [];
        }else{
          config.data.datasets[1].data = $BINARY_VALUES;
        }
        updateSimulationResults(data);
        window.myLine.update();
      }
    });
  });

  $("#generate-binary-sequence").click(function() {
    $.ajax({
      url: "/binary-sequence/new/" + $("#number-of-bits").val(),
      dataType: 'json',
      success: function(data) {
        if($("#binary-sequence-hide").hasClass("hidden")){
          showBits(data.binarySequence,$BITS_TO_SHOW);
        }else{
          showBits(data.binarySequence,data.binarySequence.length);
        }
        $BINARY_VALUES = data.binarySignal.values;
        $SEQUENCE = data.binarySequence;
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
    config.data.datasets[1].data = $BINARY_VALUES;
    window.myLine.update();
    $(this).toggleClass("hidden");
    $("#binary-signal-hide").toggleClass("hidden");
  });

  $("#binary-signal-hide").click(function() {
    config.data.datasets[1].data = [];
    window.myLine.update();
    $(this).toggleClass("hidden");
    $("#binary-signal-show").toggleClass("hidden");
  });

  $("#binary-sequence-show").click(function() {
    showBits($SEQUENCE,$SEQUENCE.length);
    $(this).toggleClass("hidden");
    $("#binary-sequence-hide").toggleClass("hidden");
  });

  $("#binary-sequence-hide").click(function() {
    showBits($SEQUENCE,$BITS_TO_SHOW);
    $(this).toggleClass("hidden");
    $("#binary-sequence-show").toggleClass("hidden");
  });

  showBits($SEQUENCE,$BITS_TO_SHOW);

});
