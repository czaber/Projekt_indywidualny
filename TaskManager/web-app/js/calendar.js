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
									var userId = $('#userId').val();
									if(window.location.pathname==pathA || window.location.pathname==pathD)
									{
										path = "../show/" + id;
									}else {						
										pathA = "/TaskManager/adminPanel/statMonth";
										pathD = "/TaskManager/developerPanel/statMonth";
										if(window.location.pathname == pathA){
											path = "/TaskManager/adminPanel/statMonthAjax/";
										}									
										else if(window.location.pathname == pathD)
											path = "/TaskManager/developerPanel/statMonthAjax/";
									}
									
									$.ajax({
										type : "POST",
										url : path,
										data : {
											data : dateText,userId :userId,
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