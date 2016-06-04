/**
 * Created by Luis on 26/5/16.
 */




window_height=$(window).height()-$(window).height()*0.2;
console.log(window_height);
$("#challenges_container").css("height", window_height);
$("#challenges_container").css("max-height", window_height);
$(".challenge").height($($(".challenge")[0]).innerWidth());

var challengesTotalNumber = 10;

var challengesTest = [];

$.ajax({
    type : 'GET',
    async : false,
    url : 'http://localhost:8080/Game/rest/challenges/Nicolas',
    success : function(challenges) {
        challengesTest = challenges;
        run()
        $('.challenge .challenge_img').css('height',$($(".challenge > .challenge_img")[0]).height())

    },

    error : function() {
        alert('An error has occurred!');
    }

});


function makeChallenges (challenges){

    html = '';
    challengesCounter = 0;
    for(i=0; i<challenges.length; i++){

        challenge = challenges[i];
        challengesCounter += 1;

        html += '<div class="challenge">'
            + ''
            + '<div class="challenge_img" align="center">'
            + '<img src="data:image/png;base64,' + challenge.image + '" alt="challenge_fail"  align="middle">'
            + '</div>'
            + ''
            + '<div class="challenge_data" align="center">'
            + '<div class="challenge_name" align="center">'
            + challenge.name
            + '</div>'
            + '<div class="challenge_score" align="center">'
            + challenge.points
            + '</div>'
            + '</div>'
            + '</div>'
            + ''

    }

    challengesTotalNumber = challengesTotalNumber - challengesCounter;

    for(j=0; j<challengesTotalNumber; j++){

        html += '<div class="challenge">'
            + ''
            + '<div class="challenge_img" align="center">'
            + '<img src="locked-white.png" alt="challenge_fail"  align="middle">'
            + '</div>'
            + ''
            + '<div class="challenge_data" align="center">'
            + '<div class="challenge_name" align="center">'
            + 'Locked'
            + '</div>'
            + '<div class="challenge_score" align="center">'
            + '?'
            + '</div>'
            + '</div>'
            + '</div>'
            + ''

    }

    return html;

}


$(".challenge .challenge_img").css("height", $($(".challenge > .challenge_img")[0]).height())

// Esta función toma un html y lo inserta en el documento en el "hijo" deseado (o div).

function insertHTML(id, html) {
    var el = document.getElementById(id);

    if(!el) {
        alert('Element with id ' + id + ' not found.');
    }

    el.innerHTML = html;
}

// Esta función será la que ejecute todo.
function run() {
    var challengesHtml = makeChallenges(challengesTest);
    insertHTML('challenges_container', challengesHtml);

}

// Corre todo cuando carga el documento.
//window.onload = run;

$(window).on('resize', function(){
    console.log("Caca")
    $('.cahllenge .challenge_img').css('height','80%');
    //$('.challenge .challenge_img').css('height',$($(".challenge > .challenge_img")[0]).height())
})



