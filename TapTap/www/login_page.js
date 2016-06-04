/**
 * Created by Luis on 1/6/16.
 */

$(document).ready(function() {
    var address = "http://taptap.ddns.net:8080/Game/rest/users";
    $("#login_btn").click(function(event) {
        event.preventDefault();
        console.log($('#login_username'))
        var username = $("#login_username").val();
        var password = $("#login_password").val();
        $(".errorMessage").css("display", "none");
        if (username.length == 0) {
            $("#missingUsername").css("display", "block");
        } else if (password.length == 0) {
            $("#missingPassword").css("display", "block");
        } else {
            $.ajax({
                type : 'GET',
                url : address,
                crossDomain: true,
                data : {
                    username : $('#login_username').val(),
                    password : $('#login_password').val()
                },
                success : function(couldLogIn) {
                    if (couldLogIn) {
                        window.location.href = "menu.html";
                    } else {
                        $("#incorrectInformation").css("display","block");
                    }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    alert('An error has occurred!');
                    console.log(jqXHR,textStatus,errorThrown)
                }
            });
        }
    });

    $("#register_btn").click(function(){
        mui.viewport.showPage('register_page','SLIDE_LEFT')
    });
    $(document).ajaxStart(function() {
        $("#loadingGif").css("display", "block");
    });
    $(document).ajaxComplete(function() {
        $("#loadingGif").css("display", "none");
    });
});


