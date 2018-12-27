<?php 
if(isset($_POST['submit'])){ 
    $dbHost = "localhost";        //Location Of Database usually its localhost 
    $dbUser = "root";            //Database User Name 
    $dbPass = "";            //Database Password 
    $dbDatabase = "taocauhoitracnghiem";    //Database Name 
     
    $db = mysql_connect($dbHost,$dbUser,$dbPass)or die("Error connecting to database."); 
    //Connect to the databasse 
    mysql_select_db($dbDatabase, $db)or die("Couldn't select the database."); 
    //Selects the database 
     
    /* 
    The Above code can be in a different file, then you can place include'filename.php'; instead. 
    */ 
     
    //Lets search the databse for the user name and password 
    //Choose some sort of password encryption, I choose sha256 
    //Password function (Not In all versions of MySQL). mysql_real_escape_string
    $usr = $_POST['username']; 
    $pas =  $_POST['password'] ; //hash('sha256', mysql_real_escape_string($_POST['password'])); 
   $sql = mysql_query("SELECT * FROM nguoidung  
        WHERE ma_NgDung=' $usr' AND 
        matKhau=' $pas ' 
        "); 
		echo mysql_num_rows($sql);
		
    if(mysql_num_rows($sql) == 1){ 
        $row = mysql_fetch_array($sql); 
        session_start(); 
        $_SESSION['username'] = $row['ma_NgDung']; 
        $_SESSION['namend'] = $row['ten_NgDung']; 
        $_SESSION['logged'] = TRUE; 
        header("Location: ../view/welcome.php"); // Modify to go to the page you would like 
        exit; 
    }else{ 
        header("Location: ../view/login.php"); 
        exit; 
    } 
}else{    //If the form button wasn't submitted go to the index page, or login page 

    header("Location: ../view/taocauhoi.php");     
    exit; 
	
} 
?>