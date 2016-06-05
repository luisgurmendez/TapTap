/**
 * Created by Luis on 5/6/16.
 */



$(document).ready(function() {
    $("#invite_btn").click(function(event) {
        console.log('caca')
        event.preventDefault();
        $(".errorMessage").css("display", "none");
        var identifier = $("#invite_room_identifier").val();
        if (identifier.length == 0) {
            $("#missingidentifier").css("display", "block");
        }else{
            mui.viewport.showPage('play_online_page','NONE');
            onlineController.room_id=identifier;
            onlineController.startConnection();
        }
    });

    $("#cancel_invite_btn").click(function() {
        mui.viewport.showPage('menu_page','SLIDE_RIGHT');
    });

    $(document).ajaxStart(function() {
        $(".loadingGif").css("display", "block");
    });
    $(document).ajaxComplete(function() {
        $(".loadingGif").css("display", "none");
    });

});





