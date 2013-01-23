<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.0/jquery-ui.js"></script>
<g:javascript src="drop.js" />
<title>TaskManager - Przydzielanie zadań</title>
</head>
<body>
	<hr>
		Zadania
	<hr style="margin-bottom:10px">
	<div class="tasksPanel">
		<div class="tasksDeveloper">
			<hr>
			<div class="header">Zadania</div>
			<hr>
			<g:each var="task" in="${taskList}">
				<div class="taskactive task" id="${task.id}">${task.name}
				</div>
			</g:each>
		<div class="tasksPanelOther" >
			<g:link action="createTask" class="button" style="width:200px;">Stwórz zadanie</g:link>
			<g:link action="endTasks" class="button" style="width:200px;">Zakończ zadanie</g:link>
			<g:link action="tasks" class="button" style="width:200px;">Pokaż szczegóły</g:link>
			<g:link action="showEndedTasks" class="button" style="width:200px;">Pokaż zadania zakończone</g:link>
		</div>
		</div>

		<div class="developers">
			<g:each var="mapEntry" in="${tasksUserMap}">
				<div class="developer">
					<hr>
					<div class="header">${mapEntry.key.username}<span class="ui-icon2 ui-icon-triangle-1-s znaczek"></span><span class="ui-icon2 ui-icon-triangle-1-n znaczek"></span></div>
					<hr>
					
					<div class="sort" id="${mapEntry.key.id}">
						<g:each var="task" in="${mapEntry.value}">
							<div class="taskactive task" id="${task.id}">
								${task.name}
							</div>
						</g:each>
						
					</div>
					
				</div>
			</g:each>
		</div>
	</div>
		
	<g:link action="index" class="button">Powrót</g:link>
</body>
</html>
