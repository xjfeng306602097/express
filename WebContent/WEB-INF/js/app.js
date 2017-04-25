/**
 * Created by wshibiao on 2017/4/22.
 */

Date.prototype.Format = function (fmt) {
    //在不引入日期控件情况下，采用此方法格式化日期为yyyy-mm-dd，否则更新快件信息时弹出框不能获取快件日期
    const o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (const k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
const admin= {
    route:function () {
        /* 创建组件构造器  */
        const Home = {
            template: '#home',
        };
        const About = {
            template: '#about',

        };
        const Express = {
            template: '#query-template',
            data: function () {
                return {
                    isShow:false,
                    express: {},
                    newExpress: {},
                    upExpress: {},
                    expresses:[],
                    shelf:{},
                }
            },
            methods: {
                //查询快件
                getExpressList: function () {
                    this.$http.post("getExpress", this.express).then(function (response) {
                        // this.$set(this.expresses,response.data)
                        response.data.forEach(function (item) {
                            item.fromDate = new Date(item.fromDate).Format('yyyy-MM-dd');
                            item.arriveDate = new Date(item.arriveDate).Format('yyyy-MM-dd');
                            item.receiveDate=(item.receiveDate!=null)?new Date(item.receiveDate).Format('yyyy-MM-dd'):"";
                        });
                        this.isShow=true;
                        this.expresses = response.data
                    }).catch(function (response) {
                        alert("error")
                    });
                },
                //修改快递模态框
                openModal: function (e) {
                    e.fromDate=(new Date(e.fromDate)).Format('yyyy-MM-dd');
                    e.arriveDate=(new Date(e.arriveDate)).Format('yyyy-MM-dd');
                    e.arriveDate=(e.arriveDate!=null)?(new Date(e.arriveDate)).Format('yyyy-MM-dd'):"";
                    this.upExpress=e;
                    $('#updateModal').modal('show');
                    // alert(this.$refs.menuItem[index]);

                },
                //录入快递模态框
                createModal: function () {
                    $('#createExpressModal').modal('show');
                    //采用下面方法打开模态框无法绑定点击事件，暂时没有找到解决方案，因此模态框写在sap组件当中
                    // $('#createExpressModal').modal({
                    //     show : true, // 显示弹出层
                    //     backdrop : 'static', // 禁止位置关闭
                    //     keyboard : true, // 关闭键盘事件
                    //     remote : "getCreateModal"
                    // }).on('loaded.bs.modal', function() {
                    //
                    // }).on('shown.bs.modal', function(){
                    //
                    // });
                },
                createExpress: function () {
                    this.$http.post("createExpress", this.newExpress).then(function (response) {
                        response.data.forEach(function (item) {
                            const date = new Date(item.fromDate).toLocaleDateString();
                            item.fromDate = date;
                            const arriveDate = new Date(item.arriveDate).toLocaleDateString();
                            item.arriveDate = arriveDate;
                        });
                        this.expresses.unshift(response.data[0]);
                        this.isShow=true;
                        $('#createExpressModal').modal('hide');

                    }).catch(function (response) {
                        alert("error")
                    });
                },
                updateExpress:function () {
                    this.$http.post("updateExpress",this.upExpress).then(function (response) {
                        alert("success");
                        $('#updateModal').modal('hide');
                    }).catch(function (response) {
                        alert("error")
                    })
                },
                notification:function (e) {
                    e.fromDate=(new Date(e.fromDate)).Format('yyyy-MM-dd');
                    e.arriveDate=(new Date(e.arriveDate)).Format('yyyy-MM-dd');
                    e.arriveDate=(e.arriveDate!=null)?(new Date(e.arriveDate)).Format('yyyy-MM-dd'):"";
                    this.$http.post("sendMail",e).then(function (response) {
                        alert("success");
                    }).catch(function (response) {
                        alert("error");
                    })
                }
            }

        };
        /* 创建路由映射  */
        const routes = [
            {path: '/home', component: Home},
            {path: '/today', component: Express},
            {path: '/new', component: About}

        ];
        /* 创建路由器  */
        const router = new VueRouter({routes});
        /* 启动路由  */
        const app = new Vue({router}).$mount('#app');
    }
};


