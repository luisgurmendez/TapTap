/**
 * Created by Luis on 28/4/16.
 */

offlineController={

    user_id: 0,
    ai_id: 1,
    can_play: false,
    windows_height: $( window ).height(),
    windows_width: $( window ).width(),
    can_zoom: false,
    boardSize: 5,
    time: 20,
    id_timer:0,
    id_countdown:0,
    paused: true,
    board: $('#board_offline'),
    user_color: {0:"#1968ff",1:"#fb5041"},
    dataController: new DataController(),

    // Populate table with point DOMs
    generateBoard: function(size){
        var tableHTML=""
        var pointHTML='<div class="point_offline point"><div></div></div>'
        for (i=0; i<size; i++ ){
            tableHTML +="<tr>"
            for (j=0; j<size;j++){
                tableHTML +="<td data-x='" + j + "' data-y='" + i + "'>" + pointHTML + "</td>"
            }
            tableHTML +="</tr>"
        }
        $('#board_offline').append(tableHTML);
        $('#offline_time_counter_container .timer_container .point_count_self').css('width', $('#offline_time_counter_container .timer_container').height())
        $('#offline_time_counter_container .timer_container .point_count_self').css('height', $('#offline_time_counter_container .timer_container').height())
        $('#offline_time_counter_container .timer_container .point_count_opponent').css('width', $('#offline_time_counter_container .timer_container').height())
        $('#offline_time_counter_container .timer_container .point_count_opponent').css('height', $('#offline_time_counter_container .timer_container').height())
        $('#offline_time_counter_container .timer_container .point_count_self').css('border','1px solid' + this.user_color[this.user_id])
        $('#offline_time_counter_container .timer_container .point_count_opponent').css('border','1px solid' + this.user_color[this.ai_id])


    },
    // Logic to determining winner
    determineWinner: function(){
        mine = 0;
        his = 0;
        user_id=this.user_id
        ai_id=this.ai_id
        $('#board_offline tr').each(function(){
            $('td').each(function(){
                point=$(this).children().children()
                if(point.data('user_id') == user_id){
                    mine+=1
                }else if(point.data('user_id')== ai_id){
                    his+=1
                }
            })
        });
        if(mine > his) {
            alert("You won!");
        }else if(mine < his){
            alert("You lost!");
        }else{
            alert("Draw");
        }
    },
    //Called whenever the game ends (timer off, otherplayer disconnects, etc)
    endGame: function(){
        if(this.can_play){
            this.can_play=false;
            this.can_zoom=true;
            this.determineWinner();
        }
        $('#offline_btn').removeClass('offline_surrend_btn')
        $('#offline_btn').addClass('offline_back_btn')
        $('#offline_btn').text('Back')
        this.dataController.reset();


    },
    surrend: function(){
        this.destroy();
        alert("You surrend!")
    },
    // delete the board and timer
    destroy: function(){
        $("#board_offline > tbody > tr").remove();
        clearInterval(offlineController.id_timer);
        clearInterval(offlineController.id_countdown);
        $('#timer_offline').text(0)

    },
    // Re size point, for diferent screen sizes
    reSizePoints: function(){
        windows_height= this.windows_height - this.windows_height*0.15;
        windows_width= this.windows_width - this.windows_width*0.15;
        max_width_size = windows_width/this.boardSize;
        max_height_size = windows_height/this.boardSize;
        min_size= (max_height_size > max_width_size) ? max_width_size : max_height_size;
        $('.point_offline').css('width',"" + min_size+ "px")
        $('.point_offline').css('height',"" + min_size + "px")
    },
    timer: function(time){
        var counter = time;

        offlineController.id_timer=setInterval(function() {
            if(!offlineController.paused){
                counter--;
                if (counter >= 0) {
                    $('#timer_offline').text(counter);
                }
                if (counter === 0) {
                    offlineController.endGame();
                    clearInterval(offlineController.id_timer);
                }
            }
        }, 1000);
    },
    startCountDown: function(){
        offlineController.paused=true;
        $('#offline_pause_layer').show();
        $('#offline_countdown_timer').show();
        var counter = 3;
        $('#offline_countdown_timer').text(counter);
        offlineController.id_countdown=setInterval(function(){
            counter--;
            if(counter >=0){
                $('#offline_countdown_timer').text(counter);
            }
            if(counter === 0){
                clearInterval(offlineController.id_countdown);
                offlineController.paused=false;
                offlineController.can_play=true;
                $('#offline_pause_layer').hide();
                $('#offline_countdown_timer').hide();
                startAi(offlineController.boardSize);
            }
        },1000)
    },
    startOffline: function(){
        this.generateBoard(this.boardSize);
        this.reSizePoints(this.boardSize);
        this.startCountDown();
        this.timer(offlineController.time);
        $('#offline_time_counter_container .timer_container .point_count_self span').text(offlineController.dataController.my_point_count)
        $('#offline_time_counter_container .timer_container .point_count_opponent span').text(offlineController.dataController.opponent_point_count)




    },
    resumeGame: function(){
        this.startCountDown();
    },
    pauseGame: function(){
        this.paused=true;
        $('#offline_pause_layer').show();
        $('#offline_countdown_timer').show();

    }

};
//Tap listener, call when user clicks or taps a point.
$('#board_offline').on('tap','.point_offline',function(){
    point = $(this);
    if(offlineController.can_play && !offlineController.paused){
        cell = $(point.parents('td')[0]);
        paintCircle(cell.data('x'),cell.data('y'),offlineController.user_id);
    }
});

$('#offline_btn').on('click',function(){

    if($(this).hasClass('offline_surrend_btn')){
        if(offlineController.can_play){
            offlineController.surrend();
        }else{
            offlineController.destroy();
        }
        mui.viewport.showPage("menu_page", "SLIDE_RIGHT")
    }else{
        offlineController.destroy();
        mui.viewport.showPage("menu_page", "SLIDE_RIGHT")
        $(this).addClass('offline_surrend_btn').text('Surrend').removeClass('offline_back_btn')
    }
});

