
//Given a table dimensions, it paints a random cell of that table. 

function paintRandomCircle(boardSize) {
	randomX = Math.floor((Math.random() * (boardSize)));
	randomY = Math.floor((Math.random() * (boardSize)));
	paintCircle(randomX,randomY, offlineController.ai_id);
}

//Paints a circle (a <td>)

function paintCircle(x,y,id) {

	 var point = $($('#board_offline')[0].rows[y].cells[x]).children().children()
     point.css('background-color',offlineController.user_color[id]);
	if(offlineController.user_id == id){
		if(point.data('user_id')==offlineController.ai_id){
			offlineController.dataController.selfPainted(true)
		}else if(point.data('user_id') != id){
			offlineController.dataController.selfPainted(false)
		}
		point.data('user_id',offlineController.user_id)
	}else{
		if(point.data('user_id')==offlineController.user_id){
			offlineController.dataController.opponentPainted(true)
		}else if(point.data('user_id') != offlineController.ai_id){
			offlineController.dataController.opponentPainted(false)
		}
		point.data('user_id',offlineController.ai_id)
	}

	$('#offline_time_counter_container .timer_container .point_count_self span').text(offlineController.dataController.my_point_count)
	$('#offline_time_counter_container .timer_container .point_count_opponent span').text(offlineController.dataController.opponent_point_count)

}

//Calls the above function repeatedly in a specific pace.

function startAi(boardSize){
	(function loop() {
		if(!offlineController.paused){
			var rand = Math.round(Math.random() * (100)) + 50;
			setTimeout(function() {
				if(offlineController.can_play){
					paintRandomCircle(boardSize);
					loop();
				}else{
					return
				}
			}, rand);
		}else{
			return;
		}
	}());
}
