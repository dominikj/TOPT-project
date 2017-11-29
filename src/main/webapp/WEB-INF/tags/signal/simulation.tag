<%@ attribute name="simulationData" required="true" type="pl.topt.project.data.SimulationDataResponse" %>
<%@ attribute name="binaryData" required="true" type="pl.topt.project.data.BinaryData" %>
<%@ attribute name="numberOfBits" required="true" type="java.lang.Integer" %>
<%@ taglib uri ="http://www.springframework.org/tags/form" prefix = "form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/signal" prefix="signal" %>

<script>
  $ARGUMENTS = ${simulationData.simulatedSignal.arguments};
  $VALUES = ${simulationData.simulatedSignal.values};
  $BINARY_VALUES = ${binaryData.binarySignal.values};
  $SEQUENCE = ${binaryData.binarySequence};
  $BITS_TO_SHOW = ${bitsToShow};
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.js"></script>
<script src="http://rawgithub.com/aino/throbber.js/master/throbber.js"></script>
<script src="js/jquery.ajax-progress.js"></script>
<script src="js/chart-utils.js"></script>
<script src="js/jquery.serialize-object.min.js"></script>
<script src="js/signalSimulation.js"></script>
<div>
  <div id="throbber" class="throbber"></div>
  <canvas id="canvas"></canvas>
</div>
<signal:simulationDataTable simulationData="${simulationData}" />
<signal:binarySequence binarySequence="${binaryData.binarySequence}" numberOfBits="${numberOfBits}"  />
<div class="mt-1">
<form:form method="POST" id="simulationParameters" action="#" modelAttribute="simulationParameters">
  <div class="input-group mt-1">
    <span class="input-group-addon">Pulse type</span>
    <form:select id="pulseType" class="form-control" path="pulseType">
      <form:options items="${pulseTypes}"/>
    </form:select><br />
  </div>
  <div class="checkbox mt-1">
    <form:label path="addNoise"><form:checkbox path="addNoise" class="checkbox" id="addNoise" value="true" />Noise</form:label>
    </div>
      <div class="input-group mt-1">
    <span class="input-group-addon">SNR [dB]</span>
    <form:input id="noiseSNR" class="form-control" type="number" step="0.01" disabled="true" path="noiseSNR"/>
  </div>
  <div class="checkbox mt-1">
    <form:label path="addIsi"><form:checkbox path="addIsi" class="checkbox" id="addIsi" value="true" />ISI</form:label>
  </div>
  <div class="input-group mt-1">
    <span class="input-group-addon">&#1013;</span>
    <form:input class="form-control" id="isiRate" type="number" min="0.01" step="0.01" disabled="true" path="isiRate"/>
</div>
<div class="mt-1">
<button type="button" class="btn btn-default" id="show-signal">Simulate</button>
</div>
</form:form>
</div>
