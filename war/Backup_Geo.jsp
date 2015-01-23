<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="jp.sangi.backup.Backup" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>System Backup Intervals : Result</title>
<style type="text/css">
	body { background-color:#f0f0ff }
	.tbl { border-style:solid; border-width:1px;border-color:darkgreen;border-collapse:collapse; width:1200px }
	.dt_o { border-style:solid; border-width:1px; border-color:darkgreen }
	.dt_e { border-style:solid; border-width:1px; border-color:darkgreen;background-color:#c0c0ff }
	.head { border-style:solid; border-width:1px; border-color:darkgreen;background-color:darkgreen;color:white }
</style>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      google.setOnLoadCallback(drawChartAll);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
			['T', 'C(T)'],
                                                          
	<%
	Backup backup1 = (Backup)request.getAttribute( "res" );
	double result1[] = backup1.getResult();
	int T1 = backup1.getT();
	for(int i = 1; i <= T1; i++){
		 if(i == T1){
			 %> 
			 ['<%= i %>',<%=result1[i]%>]
			 <%
		 }else{
		 %> 
		 ['<%= i %>',<%=result1[i]%>],
		 <%
		 }
		
	}
	
	%>
        ]);

        var options = {
          title: 'Transition for value of C(T)'
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

        chart.draw(data, options);
      }//drawChart関数終了
      
      function drawChartAll() {
          var dataAll = google.visualization.arrayToDataTable([
  			['T','h=10','h=20','h=30','h=40','h=50','h=60','h=70','h=80','h=90','h=100'],
                                                            
  	<%
  	double resultAll[][] = backup1.getResultAll();
  	for(int i = 1; i <= T1; i++){
  		 if(i == T1){
  			 %> 
  			 ['<%= i %>',<%=resultAll[0][i]%>,<%=resultAll[1][i]%>,<%=resultAll[2][i]%>,<%=resultAll[3][i]%>,<%=resultAll[4][i]%>,<%=resultAll[5][i]%>,<%=resultAll[6][i]%>,<%=resultAll[7][i]%>,<%=resultAll[8][i]%>,<%=resultAll[9][i]%>]
  			 <%
  		 }else{
  		 %> 
  		 ['<%= i %>',<%=resultAll[0][i]%>,<%=resultAll[1][i]%>,<%=resultAll[2][i]%>,<%=resultAll[3][i]%>,<%=resultAll[4][i]%>,<%=resultAll[5][i]%>,<%=resultAll[6][i]%>,<%=resultAll[7][i]%>,<%=resultAll[8][i]%>,<%=resultAll[9][i]%>],
  		 <%
  		 }
  		
  	}
  	
  	%>
          ]);

          var optionsAll = {
            title: 'Transition for value of C(T)'
          };

          var chartAll = new google.visualization.LineChart(document.getElementById('chart_divAll'));

          chartAll.draw(dataAll, optionsAll);
        }//drawchartAll終了
      
      
      </script>


</head>
<body>
<h2 style='text-align:center'>System Backup Intervals : Result</h2>
<hr/>
<a href="/index.html">TO main</a>

<br>
[Setting and result]
<table class='tbl'>
<tr>
<th class='head'>Distribution</th><th class='head'>System backup costs:S</th><th class='head'>System loss cost:h</th><th class='head'>System loss probability:p</th><th class='head'>Max Backup interval:T</th><th class='head'>Optimized T</th><th class='head'>Optimized C(T)</th><th class='head'>Number of Device</th>
</tr>

<% 
	int cnt = 0;
	Backup backup = (Backup)request.getAttribute( "res" );
	double result[] = backup.getResult();
	double h = backup.getH();
	double p = backup.getP();
	int s = backup.getS();
	int minT = backup.getMinT();
	int T = backup.getT();
	double minCT = backup.getMinCT();
	String title = backup.getTitle(); 
	int n = backup.getN();

%>

<% String cls = "dt_e"; %>
	<tr>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=title%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=s%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=h%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=p%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=T%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=minT%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=minCT%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=n%></td>
	</tr>
</table>

<br>
<div id="chart_div" style="width: 1000px; height: 500px;"></div>

<br>
[LOG]
<table class='tbl'>
<tr>
<th class='head' >T</th><th class='head'>C(T)</th>
</tr>

<%
	for(int i = 1; i <= T; i++){
		if ((i % 2) == 0) {
			cls = "dt_e";
		} else {
			cls = "dt_o";
		}%>

	<tr>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=i%></td>
	<td class='<%=cls%>' width='200' style='text-align:center'><%=result[i]%></td>
	</tr>
 	<%}%>
 	
 </table>
 
  <br>
[All result for each h]
<table class='tbl'>
<tr>
<th class='head' >h</th><th class='head'>T</th><th class='head'>C(T)</th>
</tr>

<%
	h = backup.getH();
	int minTAll[] = backup.getMinTAll();
	double minCTAll[] = backup.getMinCTAll();
	for(int i = 0; i < 10; i++){
		if ((i % 2) == 0) {
			cls = "dt_e";
		} else {
			cls = "dt_o";
		}%>

	<tr>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=h%></td>
	<td class='<%=cls%>' width='120' style='text-align:center'><%=minTAll[i]%></td>
	<td class='<%=cls%>' width='200' style='text-align:center'><%=minCTAll[i]%></td>
	</tr>
 	<%
 	h += 100000;}%>
 	
 </table>
 
 <br>

 
<div id="chart_divAll" style="width: 1000px; height: 500px;"></div>
 
</body>
</html>