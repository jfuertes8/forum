/*This JS file controls the appearance of both the Register and Login forms */

$(document).ready(function () {

    //Function which makes Login Form appear
    $("#login_button").on("click", function (e) {
        if ($("#register").css("display") == "block") {
            e.preventDefault(); // in some browsers a button submits if no type=
            $("#register").hide();
            $("#login").show();
        }
    });

    //Function which makes Register form appear
    $("#register_button").on("click", function (e) {
        if ($("#login").css("display") == "block") {
            e.preventDefault(); // in some browsers a button submits if no type=
            $("#login").hide();
            $("#register").show();
        }
    });

});