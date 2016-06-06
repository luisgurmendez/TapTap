
 

dataController = {
		
		my_timeline_count: [],
		opponent_timeline_count:[],
		my_point_count: 0,
		opponent_point_count: 0,
		my_point_count_overall: 0,
		

		opponentPainted: function(wasMine){
			if(wasMine){
				this.my_point_count--;
			}
			this.opponent_point_count++;
		    this.opponent_timeline_count.push([new Date().getTime() * 1000,this.opponent_point_count])
		    this.my_timeline_count.push([new Date().getTime() * 1000,this.my_point_count])
		},
		selfPainted: function(wasOpponents){
			if(wasOpponents){
				this.opponent_point_count--;
			}
			this.my_point_count++;
			this.my_point_count_overall++;
			this.my_timeline_count.push([new Date().getTime() * 1000,this.my_point_count])
			this.opponent_timeline_count.push([new Date().getTime() * 1000,this.opponent_point_count])
		},
		

}










