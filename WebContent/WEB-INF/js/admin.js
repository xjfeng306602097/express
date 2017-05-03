/**
 * Created by wshibiao on 2017/4/19.
 */
var Admin = {
	login : function(user) {
		if (Admin.checkValidate(user)) {
			vue.$http.post('login',user).then(function (response) {
				if (response.data.loginStatus == 'success'){
					window.location.href = "redirect";
				} else if(response.data.loginStatus == 'errorpassword'){
					vue._data.errorPasswordMessage = response.data.message;
					setTimeout(function(){
						vue._data.errorPasswordMessage = null;
					},3888);
				} else if(response.data.loginStatus == 'erroruserid'){
					vue._data.errorUserIdMessage = response.data.message;
					setTimeout(function(){
						vue._data.errorUserIdMessage = null;
					},3888);
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
//			alert("只能输入5-20个以字母开头、可带数字、“_”、“.”的字符串");
			vue._data.errorUserIdMessage = "只能输入5-20个以字母开头、可带数字、“_”、“.”的字符串";
			setTimeout(function(){
				vue._data.errorUserIdMessage = null;
			},3888);
			return false;
		}
		var passwordPatrn = /^(\w){6,20}$/;
		if (user.password && userPatrn.test(user.password)) {
			return true;
		} else {
//			alert("只能输入6-20个字母、数字、下划线的密码");
			vue._data.errorPasswordMessage = "只能输入6-20个字母、数字、下划线的密码";
			setTimeout(function(){
			},3888)
			vue._data.errorPasswordMessage = null;
			return false;
		}
	},
	logout : function() {

	}
};