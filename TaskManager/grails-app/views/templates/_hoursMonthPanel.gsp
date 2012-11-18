

<g:if test="${taskHoursMap.size() > 0}">
		<table>
			<tr>
				<th>Zadania</th>
				<th>Dzień</th>	
				<th>Tydzień</th>
				<th>Miesiąc</th>
				<th>Ogółem</th>
			</tr>
			<g:each var="taskHoursMapEntry" in="${taskHoursMap}">
				<tr>
					<td>
						${taskHoursMapEntry.key.name}	
					</td>
					<td>
						${taskHoursMapEntry.value.getAt(0)}
					</td>
					<td>
						${taskHoursMapEntry.value.getAt(1)}
					</td>
					<td>
						${taskHoursMapEntry.value.getAt(2)}
					</td>
					<td>
						${taskHoursMapEntry.value.getAt(3)}
					</td>
				</tr>
			</g:each>
			<tr style="background-color:#a2a2a2;">
				<td>
					-Razem-
				</td>
				<td>
					${allHours[0]}
				</td>
				<td>
					${allHours[1]}
				</td>
				<td>
					${allHours[2]}
				</td>
				<td>
					${allHours[3]}
				</td>
			</tr>
		</table>
	</g:if>
	<g:else>
		Brak wykonywanych zadań w tym miesiącu
	</g:else>