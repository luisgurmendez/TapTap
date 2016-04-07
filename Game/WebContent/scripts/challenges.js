function checkChallenge(challenge){
	
	
	completed=true;
	switch(challenge){

	case 'square':
		board.children('tbody').children('tr').each(function(y){
			$(this).children('td').each(function(x){
				point = $(this).children().children();
				if(y==0 || y==boardSize-1){
					if(point.data('user_id')!=user_id){
						completed=false;
						return completed;
					}
				}else{
					if(x==0 || x==boardSize){
						if(point.data('user_id') != user_id){
							completed=false;
							return completed;
						}
					}
				}
				
			});
		});
		break;
	case 'diagonals':
		
		board.children('tbody').children('tr').each(function(y){
			$(this).children('td').each(function(x){
				point = $(this).children().children();
				if(x==y){
					if(point.data('user_id')!=user_id){
						completed=false;
						return completed;
					}
				}else{
					if(x+y==boardSize){
						if(point.data('user_id') != user_id){
							completed=false;
							return completed;
						}
					}
				}
				
			});
		});
		break;
	}
	
	return completed;
	
}





