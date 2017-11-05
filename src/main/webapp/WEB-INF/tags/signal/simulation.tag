<%@ attribute name="signalValues" required="true" type="java.util.List" %>
<%@ attribute name="arguments" required="true" type="java.util.List" %>
<%@ taglib uri ="http://www.springframework.org/tags/form" prefix = "form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
  $ARGUMENTS = ${arguments};
  $VALUES = ${signalValues};
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.js"></script>
<script src="js/chart-utils.js"></script>
<script src="js/signalFigure.js"></script>
<div>
  Binary sequence:</br>
  <b id="binary-sequence">${binarySequence}</b>
</div>
<div>
  <canvas id="canvas"></canvas>
</div>
<form:form method="POST" action="#" modelAttribute="simulationParameters">
  <form:label path="pulseType">Pulse type:</form:label>
  <form:select id="pulseType" path="pulseType">
    <form:options items="${pulseTypes}"/>
  </form:select>
</form:form>
<button type="button" id="show-signal">Show signal</button>
