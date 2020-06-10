#! /bin/bash
function read_dir(){
	for file in `ls $1` #注意此处这是两个反引号，表示运行系统命令
		do
			if [ -d $1"/"$file ] #注意此处之间一定要加上空格，否则会报错
			then
				read_dir $1"/"$file
			else
				#echo $1"/"$fullfile #在此处处理文件即可
				# 获取文件扩展名
				filename=$(basename "$fullfile")
				extension="${filename##*.}"
				filename="${filename%.*}"
				
				#处理不同扩展名压缩文件......
				if [ $extension == "gz" ]; then
					 tar zxf $fullfile    #解压
					 sed -i '1s/^cba/abc/' /sdeard/1/123.txt     #这里是将第一行整体替换为abc
					 zip -r "$filename.gz"  123.txt      #压缩文件 把/home目录下面的123.txt压缩成为$filename.gz
                elif [ $extension == "tar" ]; then
					echo "you had enter cd"
				else
					echo "you had enter unexpected word"
				fi	
			fi
		done
} 
#读取第一个参数
read_dir $1  # /usr/local


#https://www.jb51.net/article/142325.htm  遍历文件
#https://www.jb51.net/article/51592.htm   获取扩展名

php7.2.0.tar.gz
php7.2.0 
  cd php7.2.0 
  

