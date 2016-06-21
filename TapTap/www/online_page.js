/**
 * Created by Luis on 29/4/16.
 */

onlineController={
    wsUri: "ws://taptap.ddns.net:8080/Game/gameWS",
    websocket: null,
    user_id: 0,
    opponent_id: 1,
    can_play: false,
    alerted: false,
    windows_height: $( window ).height(),
    windows_width: $( window ).width(),
    boardSize: 5,
    time: 30,
    challengeObjt:null,
    challenge_db_name:"",
    challenge: null,
    challenge_completed: false,
    id_timer: 0,
    id_countdown: 0,
    room_id: null,
    paused: true,
    username: null,
    opponent_username: null,
    dataController: new DataController(),
    generateBoard: function(){
        var tableHTML=""
        var pointHTML = '<div class="point_online point"><div></div></div>'

        for (i=0; i<this.boardSize; i++ ){
            tableHTML +="<tr>"
            for (j=0; j<this.boardSize;j++){
                tableHTML +="<td data-x='" + j + "' data-y='" + i + "'>" + pointHTML + "</td>"
            }
            tableHTML +="</tr>"
        }
        $('#online_board').append(tableHTML);

    },
    determineWinner: function(){

        mine=this.dataController.my_point_count
        his=this.dataController.opponent_point_count
        winner_player=null
        if(mine > his) {
            if(!this.alerted){
                alert("You won!");
                this.alerted=true
                winner_player=this.username;
            }
        }else if(mine < his){
            if(!this.alerted){
                alert("You lost!");
                this.alerted=true
                winner_player=this.opponent_username;
            }
        }else{
            if(!this.alerted){
                alert("Draw");
                this.alerted=true
            }
        }
        if(winner_player==this.username){
            score=0
            score=this.dataController.my_point_count_overall;

            score += this.challengeObjt.points

            msj = {'action':'winner','my_username':this.username,'winner_username':winner_player,'challenge_completed':this.challenge_completed,'challenge':this.challenge_db_name,'score':score}
            console.log(msj)
            this.doSend(msj);
        }else{
            msj={'action':'winner', 'winner_username':null, 'my_username':this.username}
            this.doSend(msj);
        }
    },
    colorController:{
        self_color: null,
        opponent_color: null,
        user_color: {},
        colors: ['#fb5041',"#1968ff","#86F296","#F9FB89","#FF6DE3"],
        setOpponentColor: function(){
            this.opponent_color=this.self_color;
            while(this.opponent_color == this.self_color){
                this.opponent_color=this.colors[Math.floor(Math.random() * this.colors.length)];
                this.user_color[onlineController.opponent_id]=this.opponent_color;
            }
        },
    },
    startOnlineGame: function(){
        $('#pre_game_container').hide();
        this.generateBoard();
        this.can_play=true
        this.timer(this.time);
        this.colorController.setOpponentColor(this.colorController.self_color)
        $('#online_timer_container').show()
        this.reSizePoints(this.boardSize)
        $('#online_timer_span').css('color',this.colorController.self_color)
        $('#online_timer_container').show()

    },
    endGame: function(){
        this.can_play=false
        this.determineWinner()
        this.websocket.close()
        this.colorController.self_color=null;

    },
    timer: function(time){
        var counter = time;
        onlineController.id_timer=setInterval(function() {
            counter--;
            if (counter >= 0) {
                $('#online_timer').text(counter);
            }
            if (counter === 0) {
                onlineController.sendTimeOut();
                onlineController.endGame();
                clearInterval(onlineController.id_timer);
            }
        }, 1000);
    },
    startConnection: function() {
        this.websocket = new WebSocket(this.wsUri)
        this.websocket.onopen = function (evt) {
            //this.joinGame(getURLVariable("id"))
            if (onlineController.room_id != null) {
                onlineController.joinGame(onlineController.room_id)
            } else {
                onlineController.joinGame(null);

            }
            onlineController.room_id = null;
        };
        this.websocket.onmessage = function (evt) {
            onlineController.onMessage(evt);
        };
        this.websocket.onerror = function (evt) {
            onlineController.onError();
        };
        this.websocket.onclose = function (evt) {
            mui.toast("WS closed",'middle','short')
        };
        this.username = mui.localStorage.get('user').username
        $("#self_username").text(this.username)
        this.getNextChallenge()

    },
    getNextChallenge: function(){
        if(this.username != null){
            $.ajax({
                type: 'GET',
                url: 'http://taptap.ddns.net:8080/Game/rest/challenges/nextChallengeApp/' + this.username,
                success: function(challenge){

                    onlineController.challenge_db_name=challenge.name
                    onlineController.challengeObjt=challenge
                    switch(challenge.name){
                        case "CRUZ PEQUEÑA":
                            challenge.name='small cross'
                            break;
                        case "CRUZ":
                            challenge.name='cross'
                            break;
                        case "DIAGONALES":
                            challenge.name='diagonals'
                            break;
                        case "ESQUINAS":
                            challenge.name='corners'
                            break;
                        case "CUADRADO":
                            challenge.name='square'
                            break;
                    }

                    onlineController.challenge = challenge.name
                    html = '<span>Next Challenge: </span><div class="next_challenge">'
                        + ''
                        + '<div class="next_challenge_img" align="center">'
                        + '<img src="data:image/png;base64,' + challenge.image + '" alt="challenge_fail"  align="middle">'
                        + '</div>'
                        + ''
                        + '<div class="next_challenge_data" align="center">'
                        + '<div class="next_challenge_name" align="center">'
                        + challenge.name
                        + '</div>'
                        + '<div class="next_challenge_score" align="center">'
                        + challenge.points
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + ''
                    $('#next_challenge_container').html(html);
                    $('.next_challenge .next_challenge_img img').css('max-height','50%')
                    $('.next_challenge .next_challenge_img img').css('max-width','50%')

                },
                error: function(){
                    $('#next_challenge_container').innerHTML="Error";
                }

            })

        }
    },
    onMessage: function(evt) {
        json = JSON.parse(evt.data);
        action = json.action
        if (action == "paintedSpot") {
            this.paintSpot(json.x, json.y, json.userId)
        } else if (action == "startGame") {
            $('#pre_game_container').hide();
            this.startCountDown();
        } else if (action == 'timeIsOver') {
            this.endGame()
        } else if (action == "specifications") {
            this.boardSize = json.matrixSize;
            this.user_id = json.userId;
            this.time = json.time;
            console.log(json)
            this.challenge = json.challenge;

        } else if (action == "opponentEnterRoom") {
            $('#opponent_point_div').css('background', '#bbbbbb')
            this.opponent_id = json.userId;
            this.opponent_username = json.username;
            $('#status_message').text(this.opponent_username);
        }
    },
    onError: function(){
        alert('WS Error')
        onlineController.endGame();
        onlineController.destroy();
        $('#opponent_color_select > div').css('background','rgba(255, 70, 69, 0.42)');
        $('#status_message').text("Conection error.")

    },
    doSend: function(message){
        this.websocket.send(JSON.stringify(message))
    },
    sendSpecifications: function() {
        message = {'action': 'sendSpecifications'}
        this.doSend(message);
    },
    sendStartGame: function() {
        message = {'action': 'startGame'}
        this.doSend(message);
    },
    joinGame: function(game_id){
        message={'action':'join','gameId':game_id, 'username':this.username}
        this.doSend(message);
    },
    sendPoint : function(x,y){
        message={'action':"paintSpot",'x':x,'y':y,'userId':this.user_id}
        this.doSend(message)
    },
    sendTimeOut: function(){
        message={'action':'timeIsOver'};
        this.doSend(message);
    },
    paintSpot: function(x,y,id){

        var point = $($('#online_board')[0].rows[y].cells[x]).children().children()
        point.css('background-color',this.colorController.user_color[id]);
        if(this.user_id == id){
            if(point.data('user_id')==this.opponent_id){
                this.dataController.selfPainted(true)
            }else if(point.data('user_id') != id){
                this.dataController.selfPainted(false)
            }
            point.data('user_id',this.user_id)
        }else{
            if(point.data('user_id')==this.user_id){
                this.dataController.opponentPainted(true)
            }else if(point.data('user_id') != this.opponent_id){
                this.dataController.opponentPainted(false)
            }
            point.data('user_id',this.opponent_id)
        }

        $('#online_timer_container .point_count_self span').text(onlineController.dataController.my_point_count)
        $('#online_timer_container .point_count_opponent span').text(onlineController.dataController.opponent_point_count)
        
    },
    // Re size point, for diferent screen sizes
    reSizePoints: function(){
        windows_height= this.windows_height - this.windows_height*0.15;
        windows_width= this.windows_width - this.windows_width*0.15;
        max_width_size = windows_width/this.boardSize;
        max_height_size = windows_height/this.boardSize;
        min_size= (max_height_size > max_width_size) ? max_width_size : max_height_size;
        $('.point_online').css('width',"" + min_size+ "px")
        $('.point_online').css('height',"" + min_size + "px")

        $('#online_timer_container .point_count_self').css('width', $('#online_timer_container').height())
        $('#online_timer_container .point_count_self').css('height', $('#online_timer_container').height())
        $('#online_timer_container .point_count_opponent').css('width', $('#online_timer_container').height())
        $('#online_timer_container .point_count_opponent').css('height', $('#online_timer_container').height())
        $('#online_timer_container .point_count_self').css('border','1px solid' + this.colorController.self_color)
        $('#online_timer_container .point_count_opponent').css('border','1px solid' + this.colorController.opponent_color)
        
        
    },
    
    destroy: function(){
        $("#online_board > tbody > tr").remove();
        clearInterval(onlineController.id_timer);
        clearInterval(onlineController.id_countdown);
        $('#timer_online').text(0)
        $('#pre_game_container').show();
        $('#online_timer_container').hide()
        this.can_play=false;
        $('#color_select_container').children('div').each(function(){
            $(this).removeClass('selected');
        });
        $('#self_color_select > div').css('background-color','#bbbbbb')
        this.websocket.close()
        $('#status_message').text("Waiting for other player...")
        $('#status_message').show();
        $('#opponent_point_div').css('background', 'url(ajax-loader.gif) no-repeat center center')
        this.opponent_id = 0;
        this.dataController.reset();
        $('#online_timer_container .point_count_self span').text(0);
        $('#online_timer_container .point_count_opponent span').text(0);
        $('#next_challenge_container').html('<span>Next Challenge: </span>')
    },
    resumeGame: function(){
        this.startCountDown();
    },
    pauseGame: function(){
        this.paused=true;
        $('#online_pause_layer').show();
    },
    startCountDown: function(){
        onlineController.paused=true;
        $('#online_pause_layer').show();
        var counter = 3;
        $('#online_countdown_timer').text(counter);
        onlineController.id_countdown=setInterval(function(){
            counter--;
            if(counter >=0){
                $('#online_countdown_timer').text(counter);
            }
            if(counter === 0){
                clearInterval(onlineController.id_countdown);
                onlineController.paused=false;
                onlineController.can_play=true;
                $('#online_pause_layer').hide();
                onlineController.startOnlineGame();
                $('#online_btn').addClass('online_surrend_btn').removeClass('online_back_btn')
            }
        },1000)
    }

}




$('.color_selector').css('background-color',function(){
    return $(this).data('colorHex')
})

windows_height= $( window ).height() - $(window).height()*0.15;
windows_width= $( window ).width() - $(window).width()*0.15;
$('.color_selector').css('width',windows_width/5);
$('.color_selector').css('height',windows_width/5);
$('.pre_game_point').css('width',windows_width/3)
$('.pre_game_point').css('height',windows_width/3)

$('.color_selector').on('tap',function(){
    color=$(this).data('colorHex')
    $('#self_color_select > div').css('background-color',color)
    onlineController.colorController.self_color=color;
    onlineController.colorController.user_color[onlineController.user_id]=color;
    $('#color_select_container').children('div').each(function(){
        $(this).removeClass('selected');
    });
    $(this).addClass('selected')
});

$('#ready_to_play').on('tap', function(){
    if(onlineController.colorController.self_color!=null){

        onlineController.sendStartGame();

    }else{
        mui.toast('Select a Color!','middle','short')
    }
});

//To prevent zooming and moving around in touch devices
window.addEventListener('touchmove', function(e) {
    // we're not interested in this
    // but prevent default behaviour
    // so the screen doesn't scroll
    // or zoom
    e.preventDefault();
}, false);

//Tap listener, call when user clicks or taps a point.
$('#online_board').on('tap','.point',function(){
    point = $(this);
    if(onlineController.can_play){
        cell = $(point.parents('td')[0]);
        onlineController.paintSpot(cell.data('x'),cell.data('y'),onlineController.user_id);
        onlineController.sendPoint(cell.data('x'),cell.data('y'));
        if(!onlineController.challenge_completed){
            if(checkChallenge(onlineController.challenge)){
                mui.toast("Challenge Completed!!",'bottom','short')
                onlineController.challenge_completed=true;
            }
        }
    }
});

$('#online_back_arrow').on('tap',function(){
    onlineController.endGame();
    onlineController.destroy();
})


$('#online_btn').on('click',function(){

    if($(this).hasClass('online_surrend_btn')){
        if(onlineController.can_play){
            onlineController.surrend();
        }else{
            onlineController.destroy();
        }
        mui.viewport.showPage("menu_page", "SLIDE_RIGHT")
        $(this).addClass('online_back_btn').removeClass('online_surrend_btn')
    }else{
        onlineController.destroy();
        mui.viewport.showPage("menu_page", "SLIDE_RIGHT")
    }
});


