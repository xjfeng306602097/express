/**
 * Created by wshibiao on 2017/4/19.
 */
var Admin = {
	login : function(user, vue) {
		if (Admin.checkValidate(user)) {
			vue.$http.post('login',user).then(function (response) {
				if (response.data.loginStatus == 'success'){
					window.location.href = "redirect";
				} else {
					alert(response.data.message);
				}
            }).catch(function (response) {
            });
		}
	},
	checkValidate : function(user) {
		var userPatrn = /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/; // 只能输入5-20个以字母开头、可带数字、“_”、“.”的字符串
		if (user.userId && userPatrn.test(user.userId)) {
			return true;
		} else {
			alert("只能输入5-20个以字母开头、可带数字、“_”、“.”的字符串");
			return false;
		}
		var passwordPatrn = /^(\w){6,20}$/;
		if (user.password && userPatrn.test(user.password)) {
			return true;
		} else {
			alert("只能输入6-20个字母、数字、下划线的密码");
			return false;
		}
	},
	logout : function() {

	}
};