webpackJsonp([1],{"/NH1":function(e,t){},"KCv+":function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=n("7+uW"),s={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var r=n("VU/8")({name:"App"},s,!1,function(e){n("/NH1")},null,null).exports,o=n("/ocq"),a=n("Dd8w"),c=n.n(a),d=n("//Fk"),u=n.n(d),l={wx:{getFriendInfo:"/openwx/get_friend_info",getAvatar:"/openwx/get_avatar",sendFriendMsg:"/openwx/send_friend_message",getStatus:"/openwx/check_client",startClient:"/openwx/start_client",getQrCode:"/openwx/get_qrcode",getChatHistory:"/api/wx/getChatHistory",sendToMany:"/api/wx/sendToMany",continueSendToMany:"/api/wx/continueSendToMany",cleanMission:"/api/wx/cleanMission",getGroupBasicInfo:"/openwx/get_group_basic_info",sendGroupMsg:"/openwx/send_group_message",getFriendGroups:"/api/wx/getFriendGroups",addFriendGroup:"/api/wx/addFriendGroup",removeFriendGroup:"/api/wx/removeFriendGroup",removeFriendGroupMembers:"/api/wx/removeFriendGroupMembers"},server:""},f=n("mtWM"),p=n.n(f),m=n("mw3O"),h=n.n(m);function g(e,t,n){p.a.get(e,{params:t}).then(function(e){if(-2==e.data.code){var t=e.data.msg.split("?")[0];window.location.href=l.server+t+"?redirectUrl="+window.location.href}else n(e.data)}).catch(function(t){console.error(e+" "+t),n({code:-1,msg:"网络错误"})})}function v(e,t,n){p.a.post(e,t).then(function(e){if(-2==e.data.code){var t=e.data.msg.split("?")[0];window.location.href=l.server+t+"?redirectUrl="+window.location.href}else n(e.data)}).catch(function(t){console.error(e+" "+t),n({code:-1,msg:"网络错误"})})}p.a.defaults.withCredentials=!0,p.a.defaults.headers.patch["Content-Type"]="application/x-www-form-urlencoded";var _={get:g,post:v,del:function(e,t){p.a.delete(e).then(function(e){t(e.data)}).catch(function(e){console.error(e),t({code:-1,msg:"网络错误"})})},patch:function(e,t,n){var i={data:t};p.a.patch(e,{},i).then(function(e){n(e.data)}).catch(function(e){console.error(e),n({code:-1,msg:"网络错误"})})},promisePost:function(e,t){return new u.a(function(n,i){v(e,h.a.stringify(t),function(e){e.code&&e.code<0?i(e.msg):n(e)})})},promiseGet:function(e,t){return new u.a(function(n,i){g(e,t,function(e){e.code&&e.code<0?i(e.msg):n(e)})})},promiseDel:function(e,t){return new u.a(function(n,i){p.a.delete(e,{data:h.a.stringify(t)}).then(function(e){var t=e.data;t.code&&t.code<0?i(t.msg):n(t)}).catch(function(e){console.error(e),i("网络错误")})})}};var w={getFriendInfo:function(){return new u.a(function(e,t){_.get(l.wx.getFriendInfo,{},function(n){if(n.code&&n.code<0)t(n.msg);else{var i=(n.data||n).filter(function(e){return"好友"===e.category});e(i)}})})},sendFriendMsg:function(e,t){return new u.a(function(n,i){var s=h.a.stringify({id:t,content:e});console.log(l.wx.sendFriendMsg),_.post(l.wx.sendFriendMsg,s,function(e){var t=e.data||e;console.log(t),0!==t.code?t.code<0?i(t.msg):i("发送出错:"+t.status):n(t.data||t)})})},getChatHistory:function(e){return new u.a(function(t,n){_.get(l.wx.getChatHistory,{friend:e},function(e){e.code&&e.code<0?n(e.msg):t(e)})})},getStatus:function(){return new u.a(function(e,t){_.get(l.wx.getStatus,{},function(n){0===n.code?e(n.client[0].state):1===n.code?t("没有登录"):t(n.msg||"查询状态出错")})})},startClient:function(e){return new u.a(function(t,n){_.get(l.wx.startClient,{client:e},function(e){0===e.code?t("OK"):n(e.msg||"启动微信出错")})})},getFriendGroups:function(){return _.promiseGet(l.wx.getFriendGroups,{})},addFriendGroup:function(e,t){var n={name:e,members:t};return _.promisePost(l.wx.addFriendGroup,n)},removeFriendGroup:function(e){var t=l.wx.removeFriendGroup+"/"+e;return _.promiseDel(t,{})},removeFriendGroupMembers:function(e,t){var n={name:e,members:t};return _.promisePost(l.wx.removeFriendGroupMembers,n)},getGroupBasicInfo:function(){return _.promiseGet(l.wx.getGroupBasicInfo,{})},sendGroupMsg:function(e,t){var n=h.a.stringify({id:t,content:e});return new u.a(function(e,t){_.post(l.wx.sendGroupMsg,n,function(n){var i=n.data||n;0!==i.code?i.code<0?t(i.msg):t("发送出错:"+i.status):e(i.data||i)})})},sendToMany:function(e,t){var n={ids:e,content:t};return _.promisePost(l.wx.sendToMany,n)},continueSendToMany:function(){return _.promisePost(l.wx.continueSendToMany,{})},cleanMission:function(){return _.promisePost(l.wx.cleanMission,{})}},y=n("NYxO"),x={male:"男",female:"女"},b={name:"FriendInfo",data:function(){return{}},methods:{},computed:c()({},Object(y.e)("wx",["selectedFriend"]),{sex:function(){return x[this.selectedFriend.sex]||"未知"},avatar:function(){var e=this.selectedFriend.id;if(e&&e.length>10&&e.substr)return l.wx.getAvatar+"?id=@"+e.substr(1)}})},F={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"card-container"},[n("el-card",{staticClass:"box-card",attrs:{shadow:"hover"}},[n("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[n("span",[e._v("好友信息")])]),e._v(" "),e.avatar?n("img",{staticClass:"avatar",attrs:{src:e.avatar}}):e._e(),e._v(" "),n("div",{attrs:{title:e.selectedFriend.displayname}},[e._v("显示名称："+e._s(e.selectedFriend.displayname))]),e._v(" "),n("div",{attrs:{title:e.selectedFriend.markname}},[e._v("备注名："+e._s(e.selectedFriend.markname))]),e._v(" "),n("div",{attrs:{title:e.selectedFriend.name}},[e._v("昵称: "+e._s(e.selectedFriend.name)+",")]),e._v(" "),n("div",{attrs:{title:e.selectedFriend.signature}},[e._v("签名："+e._s(e.selectedFriend.signature))]),e._v(" "),n("div",[e._v("性别："+e._s(e.sex))]),e._v(" "),n("div",[e._v("地址："+e._s(e.selectedFriend.province)+" -- "+e._s(e.selectedFriend.city))]),e._v(" "),n("div",{attrs:{title:e.selectedFriend.id}},[e._v("用户ID："+e._s(e.selectedFriend.id))]),e._v(" "),n("div")])],1)},staticRenderFns:[]};var S=n("VU/8")(b,F,!1,function(e){n("lUog")},"data-v-f345cee6",null).exports,T=n("mvHQ"),G=n.n(T),E=new(n("TXMN").a),M={fire:function(e,t){E.$emit(e,t)},on:function(e,t){E.$on(e,t)},EVT_WX_SEND_MANY:"evt_wx_send_many",EVT_WX_POST_MSG:"evt_wx_post_msg",EVT_WX_EVT_POST_MSG:"evt_wx_evt_post_msg",EVT_WX_EVT_POST_STATE_CHANGE:"evt_wx_evt_post_msg_state_change",EVT_WX_EVT_POST_FRIEND_REQUEST:"evt_wx_evt_post_msg_friend_request"};var C={set:function(e,t){var n=window.localStorage;n&&n.setItem(e,G()(t))},read:function(e){var t=window.localStorage;if(t){var n=t.getItem(e);if(n)return JSON.parse(n)}},del:function(e){var t=window.localStorage;t&&t.getItem(e)&&t.removeItem(e)}},k={name:"ChattingRoom",data:function(){return{tip:"",content:"",historys:[]}},created:function(){var e=this,t=C.read("wx_chat_history");t&&(this.historys=JSON.parse(t)),M.on(M.EVT_WX_POST_MSG,function(t){console.debug("收到ws消息：",t);var n=void 0;switch(t.format){case"text":n=t.content;break;case"media":n="收到媒体信息，当前不支持图片展示，请在手机上查看";break;case"app":n=t.content;break;case"revoke":break;case"card":n="收到推送名片："+t.card_name||"";break;case"payment":n=t.content}n&&("receive_message"===t.postType&&"friend_message"===t.type&&e.$notify({title:""+t.fromName,message:t.content,position:"bottom-right"}),t.content=n,e.historys.push(t),C.set("wx_chat_history",G()(e.historys)))})},methods:{send:function(){var e=this;(this.selectedFriend.id.startsWith("@@")?w.sendGroupMsg(this.content.trim(),this.selectedFriend.id):w.sendFriendMsg(this.content.trim(),this.selectedFriend.id)).then(function(t){e.content="",e.tip="",console.log("发送成功")}).catch(function(t){e.tip="发送失败，请检查后重试",console.log("发送失败")})}},computed:c()({},Object(y.e)("wx",["selectedFriend"]),{chatHistory:function(e,t){var n=this;return"微信群"===this.selectedFriend.id?[]:this.selectedFriend.id&&this.selectedFriend.id.startsWith("@@")?this.historys.filter(function(e){return e.groupId===n.selectedFriend.id}):this.historys.filter(function(e){return e.from===n.selectedFriend.id||e.to===n.selectedFriend.id})}})},O={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return e.selectedFriend?n("div",[n("div",{staticClass:"history"},[n("el-card",{staticClass:"box-card",attrs:{shadow:"hover"}},[n("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[n("span",[e._v(e._s(e.selectedFriend.displayname))])]),e._v(" "),n("div",{staticStyle:{overflow:"scroll",height:"400px"}},e._l(e.chatHistory,function(t){return e.chatHistory&&e.chatHistory.length>0?n("div",{key:t.id,staticClass:"text item"},[n("div",{staticStyle:{"font-size":"12px"}},[e._v(e._s(t.fromName+": "+t.time))]),e._v(" "),n("div",[e._v(e._s(t.content))])]):e._e()}))])],1),e._v(" "),n("el-input",{attrs:{type:"textarea",autosize:{minRows:4,maxRows:10}},nativeOn:{keyup:function(t){return"button"in t||!e._k(t.keyCode,"enter",13,t.key,"Enter")?e.send(t):null}},model:{value:e.content,callback:function(t){e.content=t},expression:"content"}}),e._v(" "),e.tip?n("div",{staticClass:"tip"},[e._v(e._s(e.tip))]):e._e()],1):e._e()},staticRenderFns:[]};var N=n("VU/8")(k,O,!1,function(e){n("QmzH")},"data-v-288a7697",null).exports,D={name:"SendToManyDialog",props:["show"],data:function(){return{content:"",disabled:!1}},methods:{sendToMany:function(){var e=this;this.disabled=!0;var t=this.selectedFriends.length;if(t<=0)return this.disabled=!1,this.showDialog=!1,void this.$alert("请先选择要群发的好友或分组");var n="确定要将内容【"+this.content+"】群发给选中的 "+t+" 位好友吗？";this.$confirm(n,"提示").then(function(){var t=e.selectedFriends.map(function(e){return e.id});return w.sendToMany(t,e.content)}).then(function(t){e.disabled=!1,e.showDialog=!1,e.content="",e.$message({message:t.data,type:"success"})}).catch(function(t){e.disabled=!1,"cancel"!==t&&(e.$message(t),console.error(t))})}},computed:c()({},Object(y.e)("wx",["selectedFriends"]),{showDialog:{get:function(){return this.show},set:function(e){this.$emit("setShow",e)}}})},V={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{title:"群发信息",visible:e.showDialog},on:{"update:visible":function(t){e.showDialog=t}}},[n("el-input",{attrs:{type:"textarea",autosize:{minRows:8,maxRows:20},clearable:""},model:{value:e.content,callback:function(t){e.content=t},expression:"content"}}),e._v(" "),n("div",{staticClass:"btns"},[n("el-button",{attrs:{type:"primary",disabled:e.disabled||!e.content},on:{click:e.sendToMany}},[e._v("发送")]),e._v(" "),n("el-button",{on:{click:function(t){e.content=""}}},[e._v("清空")]),e._v(" "),n("el-button",{attrs:{type:"info"},on:{click:function(t){e.showDialog=!1}}},[e._v("取消")])],1)],1)},staticRenderFns:[]};var $=n("VU/8")(D,V,!1,function(e){n("eMzq")},null,null).exports,P={name:"AddFriendGroupDialog",props:{show:[Boolean]},data:function(){return{groupName:""}},methods:c()({},Object(y.b)("wx",["initFriendGroup"]),{submit:function(){var e=this,t=this.selectedFriends.map(function(e){return e.displayname});if(this.groupName.trim()||this.$alert("请填写组名"),!t||t.length<=0)return this.$alert("请先选择需要分组的好友"),void(this.showDialog=!1);w.addFriendGroup(this.groupName.trim(),t).then(function(t){e.groupName="",e.initFriendGroup(),e.showDialog=!1}).catch(function(e){return console.error(e)})},getSuggestions:function(e,t){var n=this.friendGroupNames;e&&(n=this.friendGroupNames.filter(function(t){return t.startsWith(e)})),t(n.map(function(e){return{value:e}}))}}),computed:c()({},Object(y.e)("wx",["selectedFriends"]),Object(y.c)("wx",["friendGroupNames"]),{showDialog:{get:function(){return this.show},set:function(e){this.$emit("setShow",e)}}})},I={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-dialog",{attrs:{title:"添加到分组",visible:e.showDialog},on:{"update:visible":function(t){e.showDialog=t}}},[n("el-form",{attrs:{"label-width":"80px"},nativeOn:{submit:function(e){e.preventDefault()}}},[n("el-form-item",{attrs:{label:"组名："}},[n("el-autocomplete",{staticClass:"inline-input",attrs:{"fetch-suggestions":e.getSuggestions,placeholder:"请输入内容"},model:{value:e.groupName,callback:function(t){e.groupName=t},expression:"groupName"}})],1)],1),e._v(" "),n("div",{staticClass:"btns"},[n("el-button",{attrs:{type:"primary",disabled:!e.groupName.trim()},on:{click:e.submit}},[e._v("添加")])],1)],1)},staticRenderFns:[]};var R=n("VU/8")(P,I,!1,function(e){n("Y9+U")},null,null).exports,W=n("162o"),A={name:"FriendList",components:{SendToManyDialog:$,AddFriendGroupDialog:R},data:function(){return{filterText:"",styleObj:{height:window.innerHeight-200+"px",overflow:"scroll"},showSendToManyDialog:!1,showAddFriendGroupDialog:!1,defaultProps:{children:"members",label:"displayname"}}},methods:c()({},Object(y.d)("wx",["setVal"]),Object(y.b)("wx",["initFriendGroup"]),{onChange:function(e,t){this.setVal({name:"selectedFriend",val:e})},checkChange:function(e,t,n){var i=this.$refs.tree.getCheckedNodes(!0);this.setVal({name:"selectedFriends",val:i})},selectAll:function(){this.$refs.tree.setCheckedKeys(this.groupedFriends.map(function(e){return e.id}))},clearSelection:function(){this.$refs.tree.setCheckedKeys([])},filterNode:function(e,t){return!e||-1!==t.displayname.indexOf(e)},removeFriendGroupMembers:function(){var e=this,t=this.$refs.tree.getCheckedNodes();t.length<=0?this.$alert("请选择要移除的好友或分组"):this.$confirm("确定把选中的好友从相应的组中移除吗？","提示",{type:"warning"}).then(function(){for(var n=t.filter(function(e){return"group"===e.type}).map(function(e){return e.displayname}),i=t.filter(function(e){return"group"!==e.type&&n.indexOf(e.group)<0&&e.group}),s={},r=0;r<i.length;r++){var o=i[r];s[o.group]=s[o.group]||{name:o.group,members:[]},s[o.group].members.push(o.displayname)}var a=function(t){if(s.hasOwnProperty(t)){var n=s[t];w.removeFriendGroupMembers(n.name,n.members).then(function(n){e.$message({type:"success",message:"组 "+t+" 被选中成员删除完成"})})}};for(var c in s)a(c);n.forEach(function(t){return w.removeFriendGroup(t).then(function(n){e.$message({type:"success",message:"组 "+t+" 删除完成"})})}),Object(W.setTimeout)(e.initFriendGroup,5e3)})}}),computed:c()({},Object(y.e)("wx",["selectedFriends"]),Object(y.c)("wx",["groupedFriends"])),watch:{filterText:function(e){this.$refs.tree.filter(e)}}},H={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",[n("el-input",{staticClass:"kw",attrs:{placeholder:"输入关键字进行过滤",clearable:""},model:{value:e.filterText,callback:function(t){e.filterText=t},expression:"filterText"}}),e._v(" "),n("div",{staticClass:"list",style:e.styleObj},[n("el-tree",{ref:"tree",attrs:{data:e.groupedFriends,"show-checkbox":"","node-key":"id","filter-node-method":e.filterNode,"expand-on-click-node":!0,"highlight-current":"",props:e.defaultProps},on:{"check-change":e.checkChange,"node-click":e.onChange}})],1),e._v(" "),n("div",{staticClass:"operate"},[n("div",{staticClass:"btns"},[n("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(t){e.showSendToManyDialog=!0}}},[e._v("群发")]),e._v(" "),n("el-button",{attrs:{size:"mini",type:"primary"},on:{click:function(t){e.showAddFriendGroupDialog=!0}}},[e._v("添至分组")]),e._v(" "),n("el-button",{attrs:{size:"mini",type:"primary"},on:{click:e.removeFriendGroupMembers}},[e._v("移出分组")])],1),e._v(" "),n("div",{staticClass:"btns"},[n("el-button",{attrs:{size:"mini",type:"primary"},on:{click:e.selectAll}},[e._v("全选")]),e._v(" "),n("el-button",{attrs:{size:"mini"},on:{click:e.clearSelection}},[e._v("清空")])],1)]),e._v(" "),n("send-to-many-dialog",{attrs:{show:e.showSendToManyDialog},on:{setShow:function(t){e.showSendToManyDialog=!1}}}),e._v(" "),n("add-friend-group-dialog",{attrs:{show:e.showAddFriendGroupDialog},on:{setShow:function(t){e.showAddFriendGroupDialog=!1}}})],1)},staticRenderFns:[]};var U=n("VU/8")(A,H,!1,function(e){n("n/iX")},null,null).exports,X={name:"Notification",props:["sendStatus","title"],data:function(){return{status:"",oldPercentage:0}},methods:{cancel:function(){var e=this;this.$confirm("确定要取消本次群发任务吗？","提示",{type:"warning"}).then(function(){return w.cleanMission()}).then(function(t){return e.$message({type:"success",message:t.data})}).catch(function(t){"cancel"!==t&&(e.$message(t),console.error(t))})}},computed:{percentage:function(){if(!this.sendStatus||void 0===this.sendStatus.code)return 100;if(1===this.sendStatus.code||-1===this.sendStatus.code)return this.status="exception",this.oldPercentage;var e=parseInt(this.sendStatus.progress/this.sendStatus.all*100);return this.status=e>=100?"success":"",this.oldPercentage=e,e},msg:function(){return this.sendStatus.desc||""}}},j={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"card-container"},[n("el-card",{staticClass:"box-card"},[n("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[n("span",[e._v(e._s(e.title))]),e._v(" "),n("el-button",{directives:[{name:"show",rawName:"v-show",value:"success"!==e.status,expression:"status!=='success'"}],staticClass:"cancelBtn",attrs:{type:"text"},on:{click:e.cancel}},[e._v("取消")])],1),e._v(" "),n("div",{staticClass:"msg-wrapper"},[n("el-progress",{attrs:{type:"circle",percentage:e.percentage,status:e.status}}),e._v(" "),n("div",[e._v(e._s(e.msg))])],1)])],1)},staticRenderFns:[]};var q=n("VU/8")(X,j,!1,function(e){n("Ra0I")},"data-v-47abf84b",null).exports;var z=6e4,Q=void 0;function B(){if(window.WebSocket){0;var e=new WebSocket(encodeURI("ws://localhost:8787/"));e.onopen=function(){console.log("Ws已连接"),Q=setInterval(function(){var t;0===(t=e).bufferedAmount&&t.send("~ping~")},2e4)},e.onerror=function(e){console.log("连接发生错误",e)},e.onclose=function(){console.log("已经断开连接"),Q&&clearInterval(Q),setTimeout(B,z)},e.onmessage=function(e){!function(e){if("~pong~"===e.data)return void console.debug(e);var t=JSON.parse(e.data);1===t.type?M.fire(M.EVT_WX_SEND_MANY,t.msg):2===t.type?M.fire(M.EVT_WX_POST_MSG,t.msg):3===t.type&&function(e){switch(e.eventType){case"login":case"stop":break;case"state_change":M.fire(M.EVT_WX_EVT_POST_STATE_CHANGE,e.params[1]);break;case"input_qrcode":case"friend_request":var t={uid:e.params[0],fname:e.params[1],info:e.params[2],ticket:e.params[3]};M.fire(M.EVT_WX_EVT_POST_FRIEND_REQUEST,t)}}(t.msg)}(e)}}else console.error("该浏览器不支持websocket")}B();var Y={initWs:B},J={init:"初始化",loading:"加载中",scaning:"等待扫描二维码",confirming:"确认登录",updating:"更新数据",running:"运行中",stop:"停止"},K={name:"Wx",components:{FriendInfo:S,ChattingRoom:N,FriendList:U,Notification:q},data:function(){return{tip:"",logged:!1,qrPath:"",sendStatus:{},loading:!1,status:""}},created:function(){var e=this;this.init(),M.on(M.EVT_WX_SEND_MANY,function(t){e.sendStatus=t,1===t.code&&(e.logged=!1,e.qrPath="",e.tip="掉线了，请重新登录")}),M.on(M.EVT_WX_EVT_POST_STATE_CHANGE,function(t){e.status=t}),M.on(M.EVT_WX_EVT_POST_FRIEND_REQUEST,function(e){console.log("收到好友请求",e)})},methods:c()({},Object(y.d)("wx",["setVal"]),Object(y.b)("wx",["initWxInfo"]),{startClient:function(){var e=this;this.loading=!0,w.startClient().then(function(t){Y.initWs(),Object(W.setTimeout)(function(){e.loading=!1,e.qrPath=l.wx.getQrCode+"?v="+(new Date).getTime()},2e3)}).catch(function(t){console.error(t),e.$alert(t)})},init:function(){var e=this;w.getStatus().then(function(t){e.status=t}).catch(function(e){console.error(e)})}}),computed:{},watch:{status:function(e){console.log("newStatus: "+e),this.tip="当前微信状态："+J[e],"running"===e?(this.initWxInfo(),this.qrPath="",this.logged=!0):"stop"===e?this.logged=!1:"scaning"===e&&(this.logged=!1,this.qrPath=l.wx.getQrCode+"?v="+(new Date).getTime())}}},L={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"wx"},[e.logged?n("div",{staticStyle:{"text-align":"right","margin-right":"20px"}},[e._v(e._s(e.tip))]):e._e(),e._v(" "),e.logged?e._e():n("div",[e.qrPath?e._e():n("div",{staticStyle:{"text-align":"center","margin-top":"15%"}},[n("el-button",{staticStyle:{width:"200px",height:"80px"},attrs:{type:"primary",loading:e.loading},on:{click:e.startClient}},[e._v("启动微信")])],1),e._v(" "),e.qrPath?n("div",{staticStyle:{"text-align":"center","margin-top":"5%"}},[n("img",{attrs:{src:e.qrPath}}),e._v(" "),n("div",[e._v(e._s(e.tip))])]):e._e()]),e._v(" "),e.logged?n("el-row",[n("el-col",{staticStyle:{padding:"0 5px"},attrs:{span:5}},[n("friend-list")],1),e._v(" "),n("el-col",{attrs:{span:10}},[n("chatting-room")],1),e._v(" "),n("el-col",{attrs:{span:9}},[n("el-row",[n("friend-info")],1),e._v(" "),n("el-row",[n("notification",{attrs:{sendStatus:e.sendStatus,title:"群发进度"}})],1)],1)],1):e._e()],1)},staticRenderFns:[]};var Z=n("VU/8")(K,L,!1,function(e){n("SEmX"),n("KCv+")},"data-v-3802badb",null).exports;i.default.use(o.a);var ee=new o.a({routes:[{path:"/",name:"Wx",component:Z}]}),te=n("bOdI"),ne=n.n(te),ie=n("Gu7T"),se=n.n(ie),re={namespaced:!0,state:{friends:[],friendGroups:[],selectedFriend:{},selectedFriends:[],wxGroups:[]},getters:{groupedFriends:function(e){if(!(e.friends.length<=0)){var t=[],n=[];e.friendGroups.forEach(function(i){var s=e.friends.filter(function(e){return i.members.indexOf(e.displayname)>=0});(s=JSON.parse(G()(s))).forEach(function(e){return e.group=i.name});var r={id:i.id,displayname:i.name,members:s,type:"group"};t.push(r),n.push.apply(n,se()(s.map(function(e){return e.displayname})))});var i=e.friends.filter(function(e){return n.indexOf(e.displayname)<=0});e.wxGroups&&e.wxGroups.length>0&&t.push({id:"微信群",displayname:"微信群",members:e.wxGroups,type:"group"});var s=i.map(function(e){return{id:e.id,displayname:e.displayname,type:"noneGroup",sex:e.sex,account:e.account,category:e.category,city:e.city,province:e.province,markname:e.markname,signature:e.signature,name:e.name}});return t.push.apply(t,se()(s)),t}},friendGroupNames:function(e){return e.friendGroups.map(function(e){return e.name})}},mutations:{setVal:function(e,t){t instanceof Array?t.forEach(function(t){e.hasOwnProperty(t.name)&&(e[t.name]=t.val)}):e.hasOwnProperty(t.name)&&(e[t.name]=t.val)}},actions:{initWxInfo:function(e){var t=e.commit,n=[];w.getFriendInfo().then(function(e){return e.data&&(e=e.data),n.push({name:"friends",val:e}),n.push({name:"selectedFriend",val:e[0]}),w.getFriendGroups()}).then(function(e){return n.push({name:"friendGroups",val:e.data||e}),w.getGroupBasicInfo()}).then(function(e){n.push({name:"wxGroups",val:e.data||e}),t("setVal",n)}).catch(function(e){return console.error(e)})},initGroups:function(e){var t=e.commit;w.getGroupBasicInfo().then(function(e){t("setVal",{name:"wxGroups",val:e.data||e})})},initFriendGroup:function(e){var t=e.commit;w.getFriendGroups().then(function(e){t("setVal",{name:"friendGroups",val:e.data})})}}};i.default.use(y.a);var oe=new y.a.Store({state:{},mutations:{},getters:{},actions:{},modules:ne()({},"wx",re),strict:!1}),ae=n("zL8q"),ce=n.n(ae);n("tvR6");i.default.config.productionTip=!1,i.default.use(ce.a),new i.default({el:"#app",router:ee,store:oe,components:{App:r},template:"<App/>"}),Date.prototype.format=function(e){e=e||"yyyy-MM-dd hh:mm:ss";var t={"M+":this.getMonth()+1,"d+":this.getDate(),"h+":this.getHours(),"m+":this.getMinutes(),"s+":this.getSeconds(),"q+":Math.floor((this.getMonth()+3)/3),S:this.getMilliseconds()};for(var n in/(y+)/.test(e)&&(e=e.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length))),t)new RegExp("("+n+")").test(e)&&(e=e.replace(RegExp.$1,1==RegExp.$1.length?t[n]:("00"+t[n]).substr((""+t[n]).length)));return e}},QmzH:function(e,t){},Ra0I:function(e,t){},SEmX:function(e,t){},"Y9+U":function(e,t){},eMzq:function(e,t){},lUog:function(e,t){},"n/iX":function(e,t){},tvR6:function(e,t){}},["NHnr"]);