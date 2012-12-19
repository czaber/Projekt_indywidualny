  $(document).ready(function() {
	  $(".sort").each(function(){
			if($(this).find('.task').length>6)
			{
				$(this).parent().find(".znaczek").show();
			}
	  });
	    $(".taskactive").draggable({revert: "invalid", helper: "clone", snap: ".sort", snapMode: "inner" });
	    $(".sort").find('.task').live( 'dblclick',function() {
	    	if (confirm('Czy na pewno chcesz zakończyć zadanie dla usera?')) {
	    	var path = "../adminPanel/endTaskForUser/";
	    	var id=$(this).parent().attr("id");
	    	$.ajax({
				type : "POST",
				url : path,
				data : {
					taskId : $(this).attr('id'),
					userId : id,
				},
				cache: false,
				complete : function(html) {
					if($("#"+id+".sort").find('.task').length<=6)
					{
						$("#"+id+".sort").parent().find(".znaczek").hide();
					}
				}
				
			});
	    	$(this).remove();
	    	}
	    });
    $(".sort").droppable({
    	accept: function(el){
    		for(var i=0;i<$(this).children().length;i++)
    		{
    			if($(this).children().eq(i).attr('id')==el.attr('id'))
    				return false;
    		}
    		return true;
    	},
        revert: true,
        cursor:'pointer',
        dropOnEmpty: true,
        drop: function(event, ui) {
        	var a=true;
        	if(a==true) ui.draggable.clone().appendTo(this);
			var path = "../adminPanel/addTaskUser/";
			var id=$(this).attr("id");
			$.ajax({
				type : "POST",
				url : path,
				data : {
					idTask : ui.draggable.attr('id'),
					idDeveloper : $(this).attr("id"),
				},
				cache: false,
				complete : function(html) {
					if($("#"+id+".sort").find('.task').length>6)
					{
						$("#"+id+".sort").parent().find(".znaczek").show();
					}
				}
			});
        }
    }).disableSelection();
    $(".tasksDeveloper").find('.task').live( 'dblclick',function() {
    	var id = $(this).attr('id');
    	var path = "../adminPanel/showTask/"+id;
    	window.location.href = path;
    });	
	  $(".sort").find(".task").draggable(
			  "disable"
		  );
  });
