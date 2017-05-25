<?php 
	#caffe model path
	#$dir = "/home/msprj_security/010775479/caffe";
	#$orig_dir="/home/msprj_security/public_html";
	
	#receiving the APK file from mobile to this path
	$file_path = "/home/msprj_security/apkfile/";
	
	$file_name = basename($_FILES['sent_file']['name']);

	$file_path = $file_path . basename($_FILES['sent_file']['name']);

	if( move_uploaded_file ($_FILES['sent_file']['tmp_name'], $file_path))
	{
		#echo "File transfer in php file success!!!\n";
	}else {
		echo "Error while file in php file transfer!!\n".$_FILES['sent_file']['error'];
	}
	
	
	#echo "file_name\n";
	#echo $file_name;
	#echo "\n";
	

	#calling script for generating pngfile from APK file
	$command = escapeshellcmd ('python /home/msprj_security/script/matrix.py '.$file_name);
	$out = shell_exec($command);
	echo $out;
	
	#changing dirctory path
	#chdir($dir);
	#echo getcwd() . "\n";

	ini_set('display_errors',1);
	error_reporting(E_ALL);
	
	#exec("export LD_LIBRARY_PATH=/usr/local/cuda/lib64");
	#exec("export CUDA_HOME=/usr/local/cuda");
	#exec("export PYTHONPATH=/usr/local/cuda/lib64");
	
	#$saved=getenv("LD_LIBRARY_PATH");
	#$newld = "/usr/local/cuda/lib64";  // extra paths to add 
	#if ($saved) { $newld .= ":$saved"; }           // append old paths if any 
	#putenv("LD_LIBRARY_PATH=/usr/local/cuda/lib64");
	
	#putenv("CUDA_HOME=/usr/local/cuda");

	#calling the caffe model file to predict and print the output in out_text.txt file in pngfile folder
	$command=escapeshellcmd ('python /home/msprj_security/010775479/caffe/malware_predict.py '.$file_name);
	$out = shell_exec($command);
	#exec("/home/msprj_security/010775479/caffe/malware_script.sh", $out1);
	echo $out;

	#chdir($orig_dir);

	#reading prediction from ouptfile store in below location
	$result_file = "/home/msprj_security/pngfile/caffe_output.txt";
	$rf = fopen($result_file, "r");
	$content = fread($rf,filesize($result_file));
	fclose($rf);
	
	#$result=strstr($content,"\n");
	

	echo $content;
	
	exit();
 ?>
