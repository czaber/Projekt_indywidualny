	<g:if test="${raportsUser.size() > 0}">
		<table>
			<tr>
				<th>Developer</th>
				<th>Status</th>
				<th>Dzień</th>
				<th>Tydzień</th>
				<th>Miesiąc</th>
				<th>Ogółem</th>
				<th>Raporty</th>
			</tr>
			<g:each var="mapEntry" in="${raportsUser}">
					<tr>
						<td>
							${mapEntry.key.user.username}
						</td>
						<td>
								<g:if test="${mapEntry.key.confirm}">Zakończone</g:if>
								<g:else>W trakcie</g:else>
						</td>
						<td>
							${mapEntry.value.getAt(0)}
						</td>
						<td>
							${mapEntry.value.getAt(1)}
						</td>
						<td>
							${mapEntry.value.getAt(2)}
						</td>
						<td>
							${mapEntry.value.getAt(3)}
						</td>
						<td>
							<g:link action="showDetails" class="button" style="width:130px; background-color:#973;" params="${[userId:mapEntry.key.user.id,taskId:mapEntry.key.task.id]}">Pokaż raporty</g:link>
						</td>
					</tr>
		
			</g:each>
		</table>
	</g:if>
	<g:else>
		<h3>Nikt obecnie nie pracuje nad tym zadaniem.</h3>
	</g:else>