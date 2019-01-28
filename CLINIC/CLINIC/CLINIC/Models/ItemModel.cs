using System;
using System.Collections.Generic;
using System.Text;

namespace CLINIC.Models
{
   public class ItemModel
    {
		public string PrecLineNo_ { get; set; }// mã hồ sơ
		public string MedicineNo_ { get; set; }// mã thuốc
		public string Name { get; set; }// tên thuóc
		public string Base_Unit_of_Measure { get; set; } // Đơn vị tính
		public string Dosage { get; set; }// liều dùng
		public string Amount { get; set; }// số lượng
		public string Origination { get; set; }//tên tắt đt
		public string Pharmacodynamic { get; set; }//dược lực học
	}
}
