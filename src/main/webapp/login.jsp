<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <style>
        .form {
            border: 1px solid #ebebeb;
            padding: 50px;
            text-align: center;
            margin: auto;
            margin-top: 5%;
            width: 20%;
        }

        .btn {
            width: 100%;
        }

        el-input {
            width: 200px;
        }

        h3 {
            margin: 0 0 15px 0;
            font-size: 1.5em;
            color: #4d5e73;
            padding: 25px 30px 15px 30px;
        }
    </style>
</head>
<body>
<div id="app" class="form">
    <h3>账号登录</h3>
    <el-form label-width="0px">
        <el-form-item prop="username">
            <el-input placeholder="输入用户名" v-model="username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input placeholder="输入密码" type="password" v-model="password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" class="btn">登录</el-button>
        </el-form-item>
    </el-form>
</div>
</body>

<!-- 先引入 Vue -->
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                username: "",
                password: "",
                rules: {
                    username: [
                        {required: true, message: "请输入用户名", trigger: "blur"},
                        {min: 5, max: 30, message: "长度过短", trigger: "blur"}
                    ],
                    password: [
                        {required: true, message: "请输入密码", trigger: "blur"},
                        {min: 5, max: 35, message: "长度过短", trigger: "blur"}
                    ]
                }
            }
        }, methods: {
            onSubmit: function () {
                console.log("submit!");
                var that = this;
                axios.post('/api/login?username=' + this.username + "&password=" + this.password)
                    .then(function (rs) {
                        rs = rs.data;
                        if (rs.code === 0) {
                            window.location.href = that.getQueryParam("redirectUrl");
                        } else {
                            that.$alert(rs.msg);
                        }
                    }).catch(function (err) {
                        console.error(err);
                    });
            }, getQueryParam: function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]);
                return null;
            }
        }
    })
</script>
</html>
