/**
 * Created by wshibiao on 2017/4/19.
 */
var Admin={
    login:function () {
        var user= {
            userId: $("#userid" +
                "").val(),
            password: $("#password").val()
        };
        $.ajax({
            type : "POST",
            url : "login",
            contentType : "application/json;chartset=utf-8",
            data : JSON.stringify(user),
            success : function(data) {
                    window.location.href = "redirect";
            },
            error:function (data) {
                alert(JSON.stringify(data));
                window.location.href = "sap.html";
            }
            }
        ,0);
    },
    logout:function () {
        
    }
};