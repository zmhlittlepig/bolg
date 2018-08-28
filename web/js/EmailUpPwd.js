

	function forgetpwd() {
		var msg	='<div style="margin: 30px;">'
			msg	+='<input type="password" name="userpassword" id="userpassword" class="form-control" placeholder="输入大于8位的新密码" />'
			msg	+='</div>'
			msg += '<div style="margin: 30px;">'
			msg	+='<input type="email" name="email" id="email" class="form-control" placeholder="请输入绑定的邮箱地址" />'
			msg	+='</div>'
			msg	+='<div style="margin: 30px;">'
			msg	+='<input type="text" name="captcha" id="captcha" class="form-control" placeholder="输入验证码" />'
			msg	+='</div>'
			msg	+='<div style="margin: 30px; text-align: center;">'
			msg	+='<input type="button" id="btn02" value="提交" class="btn btn-success" onclick="updatePwd()" />&nbsp;<input type="button" id="btn01" value="获取验证码" class="btn btn-success " onclick="settime(this)" />'
			msg	+='</div>';
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  shadeClose: true,
			  skin: 'yourclass',
			  content:  msg
			});
		
	}
	
	
	var countdown = 30;
	function settime(val) {
		if(countdown == 0) {
			val.removeAttribute("disabled");
			//val.value = "免费获取验证码";
			countdown = 30;
		} else {
			val.setAttribute("disabled", true);
			val.value = "(" + countdown + ")秒后重新发送";
			countdown--;
		}
		if(countdown >= 0){
			setTimeout(function() {
				settime(val)
			}, 1000)
		}
		
		
		if(countdown == 29){
			$.ajax({
				
				url: "/user?method=setEmail",
				method: "post",
				data: { email: $("#email").val() },
				success: function(data) {
					if(data == 1){
						layer.msg('发送成功');
					}else{
						layer.msg('发送失败');
					}
					
				},
				error: function () {
					layer.msg('发送失败');
				}
				
			});
		}
	
	}
	
	
	function updatePwd() {
		alert($("#userpassword").val());
		$.ajax({
			
			url: "/user?method=checkYzm",
			method: "post",
			data: { email: $("#email").val(), password: $.md5($.md5($("#userpassword").val())), captcha: $("#captcha").val() },
			success: function (data) {

				if(data == 1){
					layer.msg('修改成功');
				}else{
					layer.msg('修改失败');
				}
			},
			error: function () {
				layer.msg('修改失败');
			}
			
		});
		
	}
	