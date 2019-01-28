using SQLite.Net.Attributes;
using System;
using System.Collections.Generic;
using System.Text;

namespace CLINIC.Models
{
   public class LoginUsersModels
    {
		[PrimaryKey, AutoIncrement, Column("ID")]
		public int ID { get; set; }
		[MaxLength(50), Unique, Column("Account")]
		public string Account { get; set; }
		[MaxLength(50), Unique, Column("PassWords")]
		public string PassWords { get; set; }
		public string AccountName { get; set; }
		public string Password { get; set; }
		public string CusNo_ { get; set; }
		public string EmpNo_ { get; set; }
        public string Role { get; set; }
    }
}
