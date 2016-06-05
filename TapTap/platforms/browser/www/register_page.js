/**
 * Created by Luis on 4/6/16.
 */


$(document).ready(
    function() {
        var address = "http://taptap.ddns.net:8080/Game/rest/users";
        $("#registration_btn").click(
            function(event) {
                event.preventDefault();
                var username = $("#register_username").val();
                var password = $("#register_password").val();
                var secondPassword = $("#register_second_password").val();
                $(".errorMessage").css("display", "none");
                if (username.length == 0 || password.length == 0
                    || secondPassword.length == 0) {
                    $("#missingInformation").css("display", "block");
                } else if (password != secondPassword) {
                    $("#incorrectPasswords").css("display", "block");
                } else {
                    $.ajax({
                        type : 'POST',
                        url : address,
                        contentType: 'application/json',
                        data : JSON.stringify({
                            username : username,
                            password : password
                        }),
                        success : function(couldRegister) {
                            if (couldRegister) {
                                $("#successContainer").css("display","block");
                                $("#register-inputs-container").css("display","none");
                                $('#login_username').val(username)
                                
                            } else {
                                $("#userExists").css("display", "block");
                            }
                        },
                        error : function() {
                            alert('An error has occurred!');
                        }
                    });
                }
            });
        $("#cancel_registration_btn").click(function() {
            mui.viewport.showPage('login_page','SLIDE_RIGHT')
            
        });
        $("#register_back_btn").click(function() {
            mui.viewport.showPage('login_page','SLIDE_RIGHT')
            $("#successContainer").css("display","none");
            $("#register-inputs-container").css("display","block");
            $('#register-inputs-container input').val('')

        });
        $(document).ajaxStart(function() {
            $("#loadingGif").css("display", "block");
        });
        $(document).ajaxComplete(function() {
            $("#loadingGif").css("display", "none");
        });
    });

