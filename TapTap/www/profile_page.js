/**
 * Created by Luis on 5/6/16.
 */


function makeProfile() {
    var html = '';

    userdata = mui.localStorage.get('user');
    $.ajax({
        type:'GET',
        url:'http://taptap.ddns.net:8080/Game/rest/users/userInformation/' + userdata.username,
        async : false,
        success: function(data){
            html += '<div id="user_name_profile" align="center">'
                + data.username
                + '</div>'
                + ' '
                + '<div id="user_score_profile" align="center">'
                + 'Score: ' + data.points
                + '</div>'
                + ' '
        },
        error: function(jqXHR, textStatus, errorThrown){
            html += '<div id="user_name_profile" align="center">'
                + "Error"
                + '</div>'
                + ' '
                + '<div id="user_score_profile" align="center">'
                + 'Score: ' + "Error"
                + '</div>'
                + ' '

        }
    });

    return html;
}

// Esta función toma un html y lo inserta en el documento en el "hijo" deseado (o div).

function insertHTML(id, html) {
    var el = document.getElementById(id);

    if(!el) {
        alert('Element with id ' + id + ' not found.');
    }

    el.innerHTML = html;

}

// Esta función será la que ejecute todo.

function profile_run() {
    var profileHtml = makeProfile();

    insertHTML('user_container_profile', profileHtml);
    mui.viewport.refreshScroll('profile_page')
}

$('#logout_profile').click(function(){

    user = mui.localStorage.get('user')
    if(user != null){
        user.loged_in=false;
        mui.localStorage.set('user',user)
    }

    mui.viewport.showPage('login_page','NONE')
    
});

$('#goto_menu_profile').click(function(){

    mui.viewport.showPage('menu_page','SLIDE_RIGHT')

})


	






