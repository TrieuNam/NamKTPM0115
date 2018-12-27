<?php
//ham bat loi sql injection
function sql_escape($value)
  {
      
      if(get_magic_quotes_gpc())
        {
            $value=stripcslashes($value);    
        }
        
      if(function_exists("mysql_real_escape_string"))
       {
           $value=mysql_real_escape_string($value);   
       }else{
              $value=addslashes($value);
            }
     return $value;  
  }
  ?>