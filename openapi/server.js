const http = require('http');
const chalk = require('chalk');
const path = require('path');
const fs = require('fs');
const opn = require('opn');
const config = require('./server_config.js');

var server = http.createServer(function(req,res){
	const filePath = path.join(config.root,req.url)
	console.info("path",`${chalk.green(filePath)}`)
	fs.stat(filePath,(err,stats)=>{//fs.stat(path,callback),读取文件的状态；
		if(err){//说明这个文件不存在
			console.log(err)
			res.statusCode = 404;
			res.setHeader('Content-Type','text/javascript;charset=UTF-8');//utf8编码，防止中文乱码
			res.end(`${filePath} is not a directory or file.`)
			return;
		}
		var extname = path.extname(filePath)
		console.log('extname', extname);
		if(stats.isFile()){//如果是文件
			res.statusCode = 200;
			if (extname === '.html') {
			    res.setHeader('Content-Type','text/html;charset=UTF-8');
			} else {
			    res.setHeader('Content-Type','text/javascript;charset=UTF-8');
			}
			res.setHeader('Access-Control-Allow-Origin', '*');
			fs.createReadStream(filePath).pipe(res);//以流的方式来读取文件
		}else if (stats.isDirectory()) {//如果是文件夹，拿到文件列表
			fs.readdir(filePath,(err,files)=>{//files是个数组
				res.statusCode = 200;
				res.setHeader('Content-Type','text/plain');
				res.end(files.join(','));//返回所有的文件名
			})
		}
	})
});

server.listen(config.port,config.hostname,()=>{
	var addr = `http://${config.hostname}:${config.port}`;
	console.info(`listenning in:${chalk.green(addr)}`);
	console.log('浏览器会自动打开swagger-ui.html', '同时也可以访问'+`http://${config.hostname}:${config.port}`+'/swagger-ui.html')
    opn('./swagger-ui.html')
})