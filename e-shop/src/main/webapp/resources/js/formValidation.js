$(document).ready(function() {

	$('#form').bind('submit', function(){
            
		var valid = true;
		 
        $(':input', this).each(function(){
            if($(this).val()==''){
                $(this).addClass('invalid');
                valid = false;
            }else{
                $(this).removeClass('invalid');
            }
        });
       
        if(!valid){
            alert('Podałeś złe dane! Wprowadź poprawne dane.');
            return false;
        }
    }); 

});
