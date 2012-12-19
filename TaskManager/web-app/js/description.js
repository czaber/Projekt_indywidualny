  $(document).ready(function() {
	  $('.hours').focus(function(){
		  $('.description').hide();
		  var id=$(this).attr('id');
		  $('#'+id+'.description').show();
	  });
  });
