var Express = {
	getExpressList : function() {
		var express = {
			expressNo : $("#expressNo").val(),
			contact : $("#phoneNumber").val(),
			arriveDate : $("#arriveDate").val()
		};
		if (Express.checkValidate(express)) {
			$.ajax({
				type : "POST",
				url : "getExpressList",
				contentType : "application/json;chartset=utf-8",
				dataType : "json",
				data : JSON.stringify(express),
				success : function(data) {
					$('#searchModal').modal({
						show : true, // 显示弹出层
						backdrop : 'static', // 禁止位置关闭
						keyboard : true, // 关闭键盘事件
						remote : "searchModal"
					});
					setTimeout(function() {
						var tb = document.createElement("tbody");
						var table = document
								.getElementById("searchResultTable");
						if (tb != null) {
							$("tbody").remove();
						}
						var expresses = [];
						$.each(data, function(index, e) {
							expresses.push(e);
						});
						$.each(expresses, function(index, obj) {
							tb.insertRow(index);
							tb.rows[index].insertCell(0);
							var date = new Date(obj.fromDate)
									.toLocaleDateString();
							tb.rows[index].cells[0].appendChild(document
									.createTextNode(date));
							tb.rows[index].insertCell(1);
							tb.rows[index].cells[1].appendChild(document
									.createTextNode(obj.expressNo));
							tb.rows[index].insertCell(2);
							tb.rows[index].cells[2].appendChild(document
									.createTextNode(obj.company));
							tb.rows[index].insertCell(3);
							tb.rows[index].cells[3].appendChild(document
									.createTextNode(obj.contact));
							tb.rows[index].insertCell(4);
							tb.rows[index].cells[4].appendChild(document
									.createTextNode(obj.addressDest));
							tb.rows[index].insertCell(5);
							tb.rows[index].cells[5].appendChild(document
									.createTextNode(obj.status));
							tb.rows[index].insertCell(6);
							var input = document.createElement("input");
							input.type = "button";
							input.value = "取件";
							input.className = "btn btn-primary";
							input.addEventListener("click", getExpress, false);
							input.expressNo = obj.expressNo; // 记录订单号
							tb.rows[index].cells[6].appendChild(input);
							table.appendChild(tb);
						});
					}, 0);

				}
			});
		}
	},
	checkValidate : function(express) {
		if (express.contact == '' && express.expressNo == '') {
			alert("Please input the expressNo or contact");
			return false;
		}
		var pattern = /^1[34578]\d{9}$/;
		if (express.contact && pattern.test(express.contact)) {
			return true;
		} else {
			alert("Pleas input the correct phone");
			return false;
		}
	},
	getExpress : function(){
		
	}
}