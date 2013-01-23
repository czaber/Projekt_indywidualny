<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<title>TaskManager - Statystyka Miesięczna</title>
<g:javascript src="calendar.js" />
  <gvisualization:apiImport/>
</head>
<body>
	<hr>
	Statystyka Miesięczna
	<hr style="margin-bottom:10px">
	<div style="margin-top:10px; overflow:hidden; width:100%;">
	<div style="width:40%; float:right;height:270px;">
	<div type="text" id="datepicker"></div>
	</div>
	<div id="showStat">
	<div style="width:57%; float:left;"><div id="show">
		
		<g:render template="/templates/hoursMonthPanel"
			model="${[taskHoursMap: taskHoursMap,allHours:allHours, wykresKolumny : wykresKolumny, wykresDane : wykresDane, status : 0]}" />
		</div>
	</div>
	</div>
	</div>
	<g:link controller="developerPanel" action="index" class="button">Powrót</g:link>
</body>
</html>