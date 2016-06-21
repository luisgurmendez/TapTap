
DataController = function(){
	this.my_timeline_count=[];
	this.opponent_timeline_count=[];
	this.my_point_count=0;
	this.opponent_point_count=0;
	this.my_point_count_overall=0;
	this.opponentPainted = function(wasMine){
		if(wasMine){
			this.my_point_count--;
			
		}
		this.opponent_point_count++;
		this.opponent_timeline_count.push([new Date().getTime() * 1000,this.opponent_point_count])
		this.my_timeline_count.push([new Date().getTime() * 1000,this.my_point_count])
	};
	this.selfPainted=function(wasOpponents){
		if(wasOpponents){
			this.opponent_point_count--;
		}
		this.my_point_count++;
		this.my_point_count_overall++;
		this.my_timeline_count.push([new Date().getTime() * 1000,this.my_point_count])
		this.opponent_timeline_count.push([new Date().getTime() * 1000,this.opponent_point_count])
	};
	this.reset=function(){
		this.my_timeline_count=[];
		this.opponent_timeline_count=[];
		this.my_point_count=0;
		this.opponent_point_count=0;
	};



}









