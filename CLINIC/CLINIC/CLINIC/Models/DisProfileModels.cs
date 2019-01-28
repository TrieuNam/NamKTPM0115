using System;
using System.Collections.Generic;
using System.Text;

namespace CLINIC.Models
{
   public class DisProfileModels
    {
		public string CliNo_ { get; set; }
		public string CliName { get; set; }//Tên phòng khám
		public string CliAdr { get; set; }//Địa chỉ
		public string CliPhone { get; set; }//Điện thoại
		public string CusNo_ { get; set; }//Mã bệnh nhân
		public string Name_Cus { get; set; }//tên  bệnh nhân
		public DateTime DateBirth_Cus { get; set; }//sinh nhật
		public string dateofbirth { get; set; }
		public string ID_Emp { get; set; }//mã bác sĩ
		public string Name_Emp { get; set; }//Tên bác sĩ
		public DateTime StaringDate { get; set; }
		public string straidate { get; set; }
		public DateTime EndingDate { get; set; }
		public string endingdate { get; set; }
		public string ProNo_ { get; set; }
		public string Address { get; set; }
		public string Phone { get; set; }
	}
}
