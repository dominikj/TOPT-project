<%@ attribute name="simulationData" required="true" type="pl.topt.project.data.SimulationDataResponse" %>

<table class="table">
  <thead>
     <tr>
       <th>"0" mean</th>
       <th>"1" mean</th>
       <th>"0" standard deviation</th>
       <th>"1" standard deviation</th>
       <th>Q</th>
       <th>BER</th>
       <th>P<sub>ISI</sub> [dB]</th>
     </tr>
   </thead>
   <tbody>
     <tr>
       <td id="low-mean-value">${simulationData.lowMeanValue}</td>
       <td id="high-mean-value">${simulationData.highMeanValue}</td>
       <td id="low-standard-deviation">${simulationData.lowStandardDeviation}</td>
       <td id="high-standard-deviation">${simulationData.highStandardDeviation}</td>
       <td id="q-parameter">${simulationData.qParameter}</td>
       <td id="ber">${simulationData.bitErrorRate}</td>
       <td id="p-isi">${simulationData.receiverSensitivityLoss}</td>
     </tr>
   </tbody>
</table>
