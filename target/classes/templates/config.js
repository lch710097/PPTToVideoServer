var serverurl="http://localhost:8080";  //后台服务

//上传ppt接口
var fileuploadurl=serverurl+"/api/files/upload";

//上传ppt页面接口
var pageuploadurl=serverurl+"/api/files/pageupload";

//获取所有ppt接口
var allfileurl=serverurl+"/api/files";

//更新ppt页面讲解内容接口
var updatepagecontenturl=serverurl+"/api/files/page";

var updatepageparamurl=serverurl+"/api/files/page/param";

//获取ppt所有页面接口
var pptpagesurl=serverurl+"/api/files/pages";


var exportppturl=serverurl+"/api/export";
var exportpptsaveurl=serverurl+"/api/export/save";

var exportpptfindurl=serverurl+"/api/export/findbypptid";

//声音克隆设置
var voiceurl=serverurl+"/api/voices";
var voiceuploadurl=serverurl+"/api/voices/upload";

var voicecloneurl=serverurl+"/api/voices/clone";


//数字人设置
var avatarurl=serverurl+"/api/digithumans";

var aicontenturl=serverurl+"/api/aiserver/generator";


var avataruploadurl=serverurl+"/api/digithumans/upload";