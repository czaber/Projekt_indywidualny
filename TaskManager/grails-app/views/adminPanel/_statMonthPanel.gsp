<g:each var="user" in="${users}" status="i">
		<h3>${user.username}</h3>
		<hr style="margin-bottom:10px">
		<g:render template="/templates/hoursMonthPanel" model="${[taskHoursMap: taskHoursMapsList[i],allHours: allHoursList[i], wykresKolumny : wykresKolumnyAll[i], wykresDane : wykresDaneAll[i]]}" />
</g:each>		