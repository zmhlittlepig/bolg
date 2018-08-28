$(function () {


    if ($.cookie("rmbUser") == "true") {
        $("#rmbUser").prop("checked", true);
        $("#username").val($.cookie("userName"));
        $("#password").val("****************");
    }



    $('#submit').click(function (e) {
        if ($("#password").val() == "****************") {
            $("#password").val($.cookie("passWord"));
            if ($("#rmbUser").prop("checked") == false) {
                $.cookie("rmbUser", "false", {
                    expires: -1
                });
                $.cookie("userName", '', {
                    expires: -1
                });
                $.cookie("passWord", '', {
                    expires: -1
                });
            }
        } else {
        	
            saveUserInfo();
        }
        $.ajax({
        	url: "/user?method=login",
        	method: "post",
        	data: {'username': $("#username").val() , 'password': $("#password").val()},
        	success: function(data) {
				if(data == -1){
					layer.msg('您已被禁封不允许登陆', {
						  offset: 't',
						  anim: 6
						});
					$("#username").val("");
					$("#password").val("");
				}else if(data == 0){
					//配置一个透明的询问框
					layer.msg('登陆成功',{time: 1000},function(){
						location.href = "index.html";
					});
					//location.href = "index.html";
				}else if(data == 1){
					layer.msg('欢迎您管理员，登陆成功',{time: 1000},function(){
						location.href = "index.html";
					});
				}else{
					//正上方
					layer.msg('用户名或者密码错误', {
					  offset: 't',
					  anim: 6
					});
					 $("#username").val("");
					 $("#password").val("");
				}
			}
        	
        	
        });
        
    });

});

//校验成功函数
function success(Obj, counter) {
    Obj.parent().parent().removeClass('has-error').addClass('has-success');
    $('.tips').eq(counter).hide();
    $('.glyphicon-ok').eq(counter).show();
    $('.glyphicon-remove').eq(counter).hide();
    check[counter] = true;

}

// 校验失败函数
function fail(Obj, counter, msg) {
    Obj.parent().parent().removeClass('has-success').addClass('has-error');
    $('.glyphicon-remove').eq(counter).show();
    $('.glyphicon-ok').eq(counter).hide();
    $('.tips').eq(counter).text(msg).show();
    check[counter] = false;
}

function saveUserInfo() {

    if ($("#rmbUser").prop("checked") == true) {
        var userName = $("#username").val();
        var passWord = $.md5($.md5($("#password").val()));
        $("#password").val(passWord);
        $.cookie("rmbUser", "true", {
            expires: 7
        });
        // 存储一个带7天期限的 cookie
        $.cookie("userName", userName, {
            expires: 7
        });
        // 存储一个带7天期限的 cookie
        $.cookie("passWord", passWord, {
            expires: 7
        });
    }
    else {
        var passWord = $.md5($.md5($("#password").val()));
        $("#password").val(passWord);
        $.cookie("rmbUser", "false", {
            expires: -1
        });
        $.cookie("userName", '', {
            expires: -1
        });
        $.cookie("passWord", '', {
            expires: -1
        });
    }
}