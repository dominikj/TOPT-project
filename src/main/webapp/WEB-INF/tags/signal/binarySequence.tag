<%@ attribute name="binarySequence" required="true" type="java.util.List" %>
<%@ attribute name="numberOfBits" required="true" type="java.lang.Integer" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
  <p>Binary sequence: <b><i>(You see only 5 first bits)</i></b></p>
<p>
  <b id="binary-sequence">
  </b>
  <button type="button" class="btn btn-default" id="binary-sequence-show">Show all</button>
  <button type="button" class="btn btn-default hidden" id="binary-sequence-hide">Hide</button>
  <br />
</p>
  <button type="button" class="btn btn-default" id="generate-binary-sequence">Generate new bits sequence</button>
  <button type="button" class="btn btn-default" id="binary-signal-show">Show Figure</button>
  <button type="button" class="btn btn-default hidden" id="binary-signal-hide">Hide Figure</button>
  <label for="number-of-bits" class="d-inline">Number of bits:</label>
  <input type="number" min="100" step="1" value="${numberOfBits}" class="d-inline" id="number-of-bits">
</div>
