<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<title>TaskManager - Niezaakceptowane raporty</title>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
    <script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
    <g:javascript src="tooltip.js"></g:javascript>
</head>
<body>
	<hr>
		Niezaakceptowane raporty
	<hr style="margin-bottom:10px">
	<g:if test="${raportHoursMap.size() > 0}">
	<g:link action="confirmAllRaports" class="button" style="width:300px" onclick="javascript:return confirm('Na pewno chcesz zakonczyć całe zadanie?')">Zatwierdź wszytkie raporty</g:link>
		<table>
			<tr>
				<th>Zadanie</th>
				<th>Developer</th>
				<th>Data</th>
				<th>H</th>
				<th>Ogółem</th>
				<th>Zatwierdź</th>
			</tr>
			<g:each var="raportHoursEntryMap" in="${raportHoursMap}">
				<tr>
					<td rowspan="7">
						${raportHoursEntryMap.key.task.name}
					</td>
					<td rowspan="7">
						${raportHoursEntryMap.key.creator.username}
					</td>
					<g:each var="position" in="${raportHoursEntryMap.key.positions}" status="i">
							<g:if test="${i==0}">
							<td>
							<g:formatDate format="EE dd-MM-yyyy" date="${position.date}" />
							<g:if test="${position.description }">
								<span  title="<center>Opis:</center> 
								${position.description}" class="ui-icon ui-icon-script"></span>
							</g:if>
								</td>
								<td>${position.workHours}
								</td>
							</g:if>
					</g:each>		
					<td rowspan="7">
						${raportHoursEntryMap.value}
					</td>
					<td rowspan="7">
					<g:link action="confirmRaport" class="button" style="width:130px; background-color:#973;" id="${raportHoursEntryMap.key.id}">Zatwierdź raport</g:link>
					<g:link action="cancelRaport" class="button" style="width:130px; background-color:#973;" id="${raportHoursEntryMap.key.id}">Anuluj raport</g:link>
					</td>
					<g:each var="position" in="${raportHoursEntryMap.key.positions}" status="i">
						<g:if test="${i!=0}">
						<tr>
							<td>
							<g:formatDate format="EE dd-MM-yyyy" date="${position.date}" />
							<g:if test="${position.description }">
								<span  title="<center>Opis:</center>${position.description}" class="ui-icon ui-icon-script"></span>
							</g:if>
							</td>
							<td>${position.workHours}
							</td>
						</g:if>
					</g:each>
				</tr>
			</g:each>
		</table>
	</g:if>
	<g:else>
		<h4>Brak raportów do zawierdzenia.</h4>
	</g:else>
	<g:link controller="AdminPanel" class="button">Powrót</g:link>
</body>
</html>
