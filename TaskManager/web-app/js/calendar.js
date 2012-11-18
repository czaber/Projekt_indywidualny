$(document).ready(
		function() {
			$("#datepicker")
					.datepicker(
							{
								closeText: 'Zamknij',
								numberOfMonths: 1,
								showWeek: true,
				                prevText: '&#x3c;Poprzedni',
				                nextText: 'Następny&#x3e;',
				                currentText: 'Dziś',
				                monthNames: ['Styczeń','Luty','Marzec','Kwiecień','Maj','Czerwiec',
				                'Lipiec','Sierpień','Wrzesień','Październik','Listopad','Grudzień'],
				                monthNamesShort: ['Sty','Lu','Mar','Kw','Maj','Cze',
				                'Lip','Sie','Wrz','Pa','Lis','Gru'],
				                dayNames: ['Niedziela','Poniedziałek','Wtorek','Środa','Czwartek','Piątek','Sobota'],
				                dayNamesShort: ['Nie','Pn','Wt','Śr','Czw','Pt','So'],
				                dayNamesMin: ['N','Pn','Wt','Śr','Cz','Pt','So'],
				                weekHeader: 'Tydz',
				                changeMonth: true,
								firstDay : 1,
								dateFormat : 'yy-mm-dd',
								onSelect : function(dateText, inst) {
									var id = $('#taskId').val();
									var pathA = "/TaskManager/adminPanel/showTask/"+id;
									var pathD = "/TaskManager/developerPanel/showDetails/"+id;
									var path;
									if(window.location.pathname==pathA || window.location.pathname==pathD)
									{
										path = "../show/" + id;
									}
									
									$.ajax({
										type : "POST",
										url : path,
										data : {
											data : dateText
										},
										success : function(html) {
											$("#show").html(html);
										},
										error : function(html) {
											$("#show").html(html);
										},
										complete : function(html) {
										}
									});
								}
							});

		});