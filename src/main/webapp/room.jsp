<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>聊天室</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="format-detection" content="telephone=no">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.js"></script>
</head>
<body>
<header class="am-topbar am-topbar-fixed-top">
    <div class="am-container">
        <h1 class="am-topbar-brand">
            <a href="#">聊天室</a>
        </h1>
    </div>
</header>
<div id="main">
    <!-- 聊天内容展示区域 -->
    <div id="ChatBox" class="am-g am-g-fixed">
        <div class="am-u-lg-12" style="height:400px;border:1px solid #999;overflow-y:scroll;">
            <ul id="chatContent" class="am-comments-list am-comments-list-flip">
                <li id="msgtmp" class="am-comment" style="display:none;">
                    <a href="">
                        <img class="am-comment-avatar" src="assets/images/other.jpg" alt=""/>
                    </a>
                    <div class="am-comment-main">
                        <header class="am-comment-hd">
                            <div class="am-comment-meta">
                                <a ff="nickname" href="#link-to-user"
                                   class="am-comment-author">某人</a>
                                <time ff="msgdate" datetime="" title="">2014-7-12 15:30</time>
                            </div>
                        </header>
                        <div ff="content" class="am-comment-bd">此处是消息内容</div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <!-- 聊天内容发送区域 -->
    <div id="EditBox" class="am-g am-g-fixed">
        <!--style给定宽度可以影响编辑器的最终宽度-->
        <input id="text" type="text"/>
        <button id="send" type="button" class="am-btn am-btn-primary am-btn-block">发送</button>
    </div>
</div>
<script type="text/javascript">$(function () {
  //窗口大小变化时，调整显示效果
  $("#ChatBox div:eq(0)").height($(this).height() - 290);
  $(window).resize(function () {
    $("#ChatBox div:eq(0)").height($(this).height() - 290);
  });
});

//向聊天窗口加入一条消息
function addMessage(msg) {
  var box = $("#msgtmp").clone();     //复制一份模板，取名为box
  box.show();                         //设置box状态为显示
  box.appendTo("#chatContent");       //把box追加到聊天面板中
  if (msg.isSelf) {
      msg.nickname = '我';
  }
  box.find('[ff="nickname"]').html(msg.nickname); //在box中设置昵称
  box.find('[ff="msgdate"]').html(msg.date);      //在box中设置时间
  box.find('[ff="content"]').html(msg.content);   //在box中设置内容
  box.addClass(msg.isSelf ? 'am-comment-flip' : ''); //右侧显示
  box.addClass(msg.isSelf ? 'am-comment-warning' : 'am-comment-success');//颜色
  box.css((msg.isSelf ? 'margin-left' : 'margin-right'), "40%");//外边距
  $("#ChatBox div:eq(0)").scrollTop(999999);  //滚动条移动至最底部
}

//自己的昵称
var nickname = '';
//建立一条与服务器之间的连接
var socket = new WebSocket("ws://localhost:8080/room");
//接收服务器的消息
socket.onmessage=function(ev){
  var obj = eval(   '('+ev.data+')'   );
  addMessage(obj)
}
//发送按钮被点击时
$("#send").click(function(){
    var message = document.getElementById('text').value;
    if (message=='') {  // 判断消息输入框是否为空
        alert("不能发送空内容");
    } else {
        //构建一个标准格式的JSON对象
            var obj = JSON.stringify({
                nickname:nickname,
                content:message
    });
      // 发送消息
     socket.send(obj);
     // 清空消息输入框
     document.getElementById('text').value = "";
     // 消息输入框获取焦点
     document.getElementById('text').focus();
  }
});
</script>
</body>
</html>