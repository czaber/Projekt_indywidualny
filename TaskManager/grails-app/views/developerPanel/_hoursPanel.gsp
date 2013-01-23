<g:if test="${taskRaport}">
	<h4>Liczba godzin przepracowanych nad  ${task.name}</h4>
	<table style="margin-top:10px; margin-bottom:10px;">
		<tr>
			<th>Dzień</th>			
			<td>
				${taskRaport.getAt(0)}
			</td>
		<tr>
			<th>Tydzień</th>
			<td>
				${taskRaport.getAt(1)}
			</td>
		<tr>
			<th>Miesiąc</th>
			<td>
				${taskRaport.getAt(2)}
			</td>
		<tr>
			<th>Ogółem</th>
			<td>
				${taskRaport.getAt(3)}
			</td>
		</tr>
	</table>
		<g:link action="showRaports" class="button" params="${[userId:userId,taskId:task.id] }">Raporty</g:link>
	
	 <gvisualization:lineCoreChart dynamicLoading="${true}" elementId="linechartShowDetails" width="${600}" height="${300}" columns="${wykresKolumny}" data="${wykresDane}"  vAxis="${new Expando(title: 'Liczba godzin',minValue:1)}"  hAxis="${new Expando(title: 'Data')}"/>
	 <div id="linechartShowDetails"></div>
	 
</g:if>

<g:else>
	<h3>Brak raportów.</h3>
</g:else>