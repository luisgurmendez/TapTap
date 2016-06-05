/**
 * Created by Luis on 5/6/16.
 */


var userTest = {"username":"santiago", "userscore":"34356"};


function makeProfile(user) {

    html = '';
    userdata = mui.localStorage.get('user');
    

    html += '<div id="user_name_profile" align="center">'
        + userdata.username
        + '</div>'
        + ' '
        + '<div id="user_score_profile" align="center">'
        + 'Score: ' + userdata.userscore
        + '</div>'
        + ' '

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
    var profileHtml = makeProfile(userTest);

    insertHTML('user_container_profile', profileHtml);
    mui.viewport.refreshScroll('profile_page')
}

$('#logout_profile').click(function(){

    user = mui.localStorage.get('user')
    user.loged_in=false;
    mui.localStorage.set('user',user)
    mui.viewport.showPage('login_page','NONE')
    
});

$('#goto_menu_profile').click(function(){

    mui.viewport.showPage('menu_page','SLIDE_RIGHT')

})


	






