var check = [false, false, false, false];
var password;
$(function () {

// 用户名匹配
    $('.container').find('input').eq(0).change(function () {
        if ($(this).val().length < 5) {
            fail($(this), 0, '用户名太短，不能少于5个字符');
        }
        else {
        	//1.获取输入框的内容
    		var username = $("#username").val();
    		var res = 1;
    		//2.发送请求
    		$.ajaxSettings.async = false;
    		$.post("/user?method=checkUsername", {username:username}, function(data, status) {	
    		
    		//3.输出响应的数据
    			//alert(data);
    			console.log(data);
    			if(data != 1){res = 0;}	
    		});
    		$.ajaxSettings.async = true;
    		if(res == 1){
    			fail($(this), 0, '用户名已存在');
    		}else{
    			success($(this), 0);
    		}
          
        }
    });

    $('.container').find('input').eq(1).change(function () {

        password = $(this).val();

        if ($(this).val().length == 0) {
            fail($(this), 1, '请输入昵称');
        } else {
            success($(this), 1);
        }
    });

    $('.container').find('input').eq(2).change(function () {
        password = $(this).val();

        if ($(this).val().length < 8) {
            fail($(this), 2, '密码太短，不能少于8个字符');
        } else {
            success($(this), 2);
        }
    });

    $('.container').find('input').eq(3).change(function () {

        if ($(this).val() == password) {
            success($(this), 3);
        } else {
            fail($(this), 3, '两次密码不一致');
        }
    });


    $('#submit').click(function (e) {
        var flag = true;
        for (key in check) {
            console.log(check[key]);
            if (!check[key]) {
                flag = false;
                $('.container').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error');
            }
        }
        if(!flag){
        	
            return;
        }
        var newpd = $.md5($.md5($("#password").val()));
        $("#password").val(newpd);
       
        $("#password2").val(newpd);
        
        $.post("/user?method=register", {username : $("#username").val(), nickname : $("#nickname").val(), password : $("#password").val()}, function (data, status) {
			
        	if(data == 1){
        		layer.msg('注册成功，是否跳转登录界面', {
        			  time: 0 //不自动关闭
        			  ,btn: ['跳转', '不跳转']
        			  ,yes: function(){
        				  location.href = "login.html";
        			  }
        			});	
        		clear();
        	}else{
        		layer.msg('注册失败', {icon: 5});
        		clear();
        	}
			
		
        });
//        //alert("success");
//        // clear();
//
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

function clear() {
    $('input').slice(0, 4).parent().parent().removeClass('has-error has-success');
    $('input').slice(0, 4).val("");
    $('.tips').hide();
    $('.glyphicon-ok').hide();
    $('.glyphicon-remove').hide();
    check = [false, false, false, false];

}