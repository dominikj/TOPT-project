<%@ attribute name="signalValues" required="true" type="java.util.List" %>
<%@ attribute name="arguments" required="true" type="java.util.List" %>
<%@ attribute name="binarySequence" required="true" type="java.util.List" %>
<%@ taglib uri ="http://www.springframework.org/tags/form" prefix = "form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
  $ARGUMENTS = ${arguments};
  $VALUES = ${signalValues};
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.0/Chart.bundle.js"></script>
<script src="js/chart-utils.js"></script>
<script src="js/jquery.serialize-object.min.js"></script>
<script src="js/signalSimulation.js"></script>
<div>
  <canvas id="canvas"></canvas>
</div>
<div>
  Binary sequence: <b><i>(You see only 5 first bits from ${bitsNumber} used to simulation)</i></b><br/>
  <b id="binary-sequence">
<c:forEach items="${binaryDataToShow.binarySequence}" var="bit">
  <c:choose>
    <c:when test="${bit}">1</c:when>
    <c:otherwise>0</c:otherwise>
  </c:choose>
</c:forEach>
  </b> (...) <br />
  <button type="button" id="generate-binary-sequence">Generate new ${bitsNumber} bits sequence</button>
</div>
<form:form method="POST" id="simulationParameters" action="#" modelAttribute="simulationParameters">
  <form:label path="pulseType">Pulse type:</form:label>
  <form:select id="pulseType" path="pulseType">
    <form:options items="${pulseTypes}"/>
  </form:select>
  <form:label path="addNoise">Add noise</form:label>
  <form:checkbox path="addNoise" id="addNoise" value="true" /> &nbsp; SNR=<form:input id="noiseSNR" type="number" step="0.01" disabled="true" path="noiseSNR"/>[dB]
</form:form>
<button type="button" id="show-signal">Show signal</button>
