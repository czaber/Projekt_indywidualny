			<table>
				<tr>
					<th>Data wystawienia raportu</th>
					<th>Data</th>
					<th>Godziny</th>

				</tr>
				<g:each var="raport" in="${raports}">
					<tr>

						<td rowspan="7" style="width:250px;"><g:formatDate format="EE dd-MM-yyyy"
								date="${raport?.dateConfirm}" /></td>
						<g:each var="position" in="${raport.positions}">
							<td>
							<g:formatDate format="EE dd-MM-yyyy" date="${position.date}" />
							</td>
							<td>
								${position.workHours}
							</td>
							</tr><tr>
						</g:each>
					</tr>
				</g:each>
			</table>