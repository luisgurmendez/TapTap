function checkChallenge(challenge){
	completed=true;
	board=$('#online_board')
	switch(challenge){

	case 'corners':
		board.children('tbody').children('tr').each(function(y){
			$(this).children('td').each(function(x){
				point = $(this).children().children();
				if((y==0 && x==0 )|| (y==onlineController.boardSize-1 && x==0) || (y==onlineController.boardSize-1 && x==onlineController.boardSize-1)|| (y==0 && x==onlineController.boardSize-1)){
					if(point.data('user_id')!=onlineController.user_id){
						completed=false
						return completed;
					}
				}
			});
		});
		break;

	case 'square':
		board.children('tbody').children('tr').each(function(y){
			$(this).children('td').each(function(x){
				point = $(this).children().children();
				if(y==0 || y==onlineController.boardSize-1){
					if(point.data('user_id')!=onlineController.user_id){
						completed=false;
						return completed;
					}
				}else{
					if(x==0 || x==onlineController.boardSize){
						if(point.data('user_id') != onlineController.user_id){
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
					if(point.data('user_id')!=onlineController.user_id){
						completed=false;
						return completed;
					}
				}else{
					if(x+y==onlineController.boardSize-1){
						if(point.data('user_id') != onlineController.user_id){
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





