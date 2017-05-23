var Express = {
	checkValidate : function(express) {
		if (express.contact == '' && express.expressNo == '') {
			alert("Please input the expressNo or contact");
			return false;
		} else if (express.expresNo != '' && express.contact == '') {
			return true;
		}
		var pattern = /^1[34578]\d{9}$/;
		if (express.contact && pattern.test(express.contact)) {
			return true;
		} else {
			alert("Pleas input the correct phone");
			return false;
		}
	},
	getExpress : function(ev) {
		var target = ev.target;
		$('#searchModal').modal('hide');
		$('#expressModal').modal({
			show : true, // 显示弹出层
			backdrop : 'static', // 禁止位置关闭
			keyboard : true, // 关闭键盘事件
			remote : "getExpressModal"
		}).on('loaded.bs.modal', function() {
			$('#expressNoText').html(target.expressNo);
			$('#selectExpressNo').val(target.expressNo);
			$('#expressStatus').val(target.status);
		}).on('shown.bs.modal', function() {
			$('#expressNoText').html(target.expressNo);
			$('#selectExpressNo').val(target.expressNo);
			$('#expressStatus').val(target.status);
		});
	},
	resendEmail : function(ev) {
		$('#searchModal').modal('hide');
		$('#informModal').modal({
			show : true, // 显示弹出层
			backdrop : 'static', // 禁止位置关闭
			keyboard : true, // 关闭键盘事件
			remote : "getInformModal"
		}).on('loaded.bs.modal', function() {
			$('#loadingImg').show();
			$('#informText').html("请稍后...");
		}).on('shown.bs.modal', function() {
			$('#loadingImg').show();
			$('#informText').html("请稍后...");
		}).on('hidden.bs.modal', function() {
			$('#loadingImg').show();
			$('#informText').html("");
		});
		var expressNo = ev.target.expressNo;
		var express = {
			expressNo : expressNo
		};
		$.ajax({
			type : "POST",
			url : "resendEmail",
			contentType : "application/json;chartset=utf-8",
			dataType : "json",
			data : JSON.stringify(express),
			success : function(data) {
				$('#loadingImg').hide();
				$('#informText').hide().html(data.message).show(300);
			}
		});
	},
	getMyExpress : function() {
		var express = {
			expressNo : $('#selectExpressNo').val(),
			verificationCode : $('#verificationCode').val(),
			status : $('#expressStatus').val()
		};
		if (express.verificationCode === ''
				|| express.verificationCode.length != 6) {
			alert("请输入正确格式的验证码");
			return;
		}
		$.ajax({
			type : "POST",
			url : "getExpress",
			contentType : "application/json;chartset=utf-8",
			dataType : "json",
			data : JSON.stringify(express),
			success : function(data) {
				$('#checkExpress').hide(); // 清空列表
				$('#ensureExpress').show();
				$('#expressLocation').html(data.location);
				$('#expressModal').on('hide.bs.modal', function() {
					$('#checkExpress').show(); // 清空列表
					$('#expressNoText').html('');
					$('#verificationCode').val('');
					$('#ensureExpress').hide();
				});
			}
		});
	},
	getExpressListByPage : function() {
		var express = {
			expressNo : $("#expressNo").val(),
			contact : $("#phoneNumber").val(),
			arriveDate : $("#arriveDate").val()
		};
		if(Express.checkValidate(express)){
			$.ajax({
				type : "POST",
				url : "getExpressListByPage/" + 1 + "/pageNum/" + 5 + "/pageSize",
				contentType : "application/json;chartset=utf-8",
				dataType : "json",
				data : JSON.stringify(express),
				success : function(data) {
					var result = data.list;
					var options = {
							shouldShowPage : true,
							bootstrapMajorVersion : 3, // 必须指定bootstrap版本为3,否则会按div做分页
							alignment : "center",// 居中显示
							currentPage : data.pageNum, // 当前页数
							totalPages : data.pages,// 总页数 注意不是总条数
							pageUrl : function(type, page, current) {
								if (page == current) {
									return "javascript:void(0)";
								} else {
									return "javascript:Express.pagehtml(" + page + ")";
								}
							}
					}
					$('#searchModal').modal({
						show : true, // 显示弹出层
						backdrop : 'static', // 禁止位置关闭
						keyboard : true, // 关闭键盘事件
						remote : "searchModal"
					}).on(
							'shown.bs.modal',
							function() {
								var tb = document.createElement("tbody");
								var table = document
								.getElementById("searchResultTable");
								if (tb != null) {
									$("tbody").remove();
								}
								var expresses = [];
								$.each(result, function(index, e) {
									expresses.push(e);
								});
								$.each(expresses, function(index, obj) {
									tb.style.height = "255px";
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
											.createTextNode(obj.consignee));
									tb.rows[index].insertCell(4);
									tb.rows[index].cells[4].appendChild(document
											.createTextNode(obj.contact));
									tb.rows[index].insertCell(5);
									tb.rows[index].cells[5].appendChild(document
											.createTextNode(obj.addressDest));
									tb.rows[index].insertCell(6);
									var status;
									switch (obj.status) {
									case 'E':
										status = '当日件';
										break;
									case 'O':
										status = '隔日件';
									default:
										break;
									}
									tb.rows[index].cells[6].appendChild(document
											.createTextNode(status));
									tb.rows[index].insertCell(7);
									var input = document.createElement("input");
									input.type = "button";
									input.value = "取件";
									input.className = "btn btn-primary";
									input.addEventListener("click",
											Express.getExpress, false);
									input.expressNo = obj.expressNo; // 记录订单号
									input.status = obj.status; // 记录状态
									tb.rows[index].cells[7].appendChild(input);
									var sendMessage = document
									.createElement("input");
									sendMessage.type = "button";
									sendMessage.value = "重发短信";
									sendMessage.className = "btn btn-success";
									sendMessage.addEventListener("click",
											Express.resendEmail, false);
									sendMessage.expressNo = obj.expressNo; // 记录订单号
									sendMessage.style.marginLeft = "10px";
									tb.rows[index].cells[7]
									.appendChild(sendMessage);
									table.appendChild(tb);
								});
								$("#page").bootstrapPaginator(options);
							});
				}
			});
		}
	},
	pagehtml : function(pageNum) {
		if (pageNum == 0) {
			pageNum = 1;
		} else {
			pageNum = pageNum;
		}
		var express = {
			expressNo : $("#expressNo").val(),
			contact : $("#phoneNumber").val(),
			arriveDate : $("#arriveDate").val()
		};
		$.ajax({
			type : "POST",
			url : "getExpressListByPage/" + pageNum + "/pageNum/" + 5
					+ "/pageSize",
			contentType : "application/json;chartset=utf-8",
			dataType : "json",
			data : JSON.stringify(express),
			success : function(data) {
				var options = {
					shouldShowPage : true,
					bootstrapMajorVersion : 3, // 必须指定bootstrap版本为3,否则会按div做分页
					alignment : 'right',// 居中显示
					currentPage : data.pageNum,// 当前页数
					numberOfPages : 5,
					totalPages : data.pages,// 总页数 注意不是总条数
					pageUrl : function(type, page, current) {
						if (page == current) {
							return "javascript:void(0)";
						} else {
							return "javascript:Express.pagehtml(" + page + ")";
						}
					}
				};
				var result = data.list;
				var tb = document.createElement("tbody");
				var table = document.getElementById("searchResultTable");
				if (tb != null) {
					$("tbody").remove();
				}
				var expresses = [];
				$.each(result, function(index, e) {
					expresses.push(e);
				});
				$.each(expresses, function(index, obj) {
					tb.insertRow(index);
					tb.rows[index].insertCell(0);
					var date = new Date(obj.fromDate).toLocaleDateString();
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
					input.addEventListener("click", Express.getExpress, false);
					input.expressNo = obj.expressNo; // 记录订单号
					input.status = obj.status; // 记录状态
					tb.rows[index].cells[6].appendChild(input);
					var sendMessage = document.createElement("input");
					sendMessage.type = "button";
					sendMessage.value = "重发短信";
					sendMessage.className = "btn btn-success";
					sendMessage.addEventListener("click", Express.resendEmail,
							false);
					sendMessage.expressNo = obj.expressNo; // 记录订单号
					sendMessage.style.marginLeft = "10px";
					tb.rows[index].cells[6].appendChild(sendMessage);
					table.appendChild(tb);
				});
				$("#page").bootstrapPaginator(options);
			},
			error : function(error) {
				alert("error");
			}
		});
	}
}