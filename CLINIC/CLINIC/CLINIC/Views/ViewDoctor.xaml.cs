using CLINIC.Models;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CLINIC.Views
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class ViewDoctor : ContentPage
	{
		Models.ExecuteData ex = new Models.ExecuteData();
		ObservableCollection<DoctorModel> listDoctor_p = new ObservableCollection<DoctorModel>();
		DoctorModel varDoctor_p;
		string id_cs_p;
		public ViewDoctor()
		{

			//string name_clinic_p, string id_clinic_p, string idCS_p, string id_facuty_p, string name_facuty_p
			InitializeComponent();
			// id_cs_p = idCS_p;
			LoadData();//id_facuty_p
			this.Title = "Doctor in Facuty ";// + name_facuty_p;
		}
		public static string abc;
		async Task LoadData()
		{
			abc = ViewClinic.id_Clinic;
			//  string id_faculty_p = name;
			string sql = @"SELECT Employee.[Name] as Name_emp,[EmpNo_],[CliNo_],[FacultyNo_],Faculty.Name as Name_fac,[Address],[Phone No_] as Phone,[Picture], [System Setup].Server as sever FROM [Employee],[System Setup],Faculty where CliNo_='" + abc + "' and [System Setup].Blocked='0' and [System Setup].Status='2' and Employee.FacultyNo_=Faculty.FalNo_";
			try
			{
				JArray arr = await ex.getDataBFO(sql);
				foreach (var item in arr)
				{
					varDoctor_p = new DoctorModel();
					varDoctor_p.EmpNo_ = item["EmpNo_"].ToString();
					varDoctor_p.Name = item["Name_emp"].ToString();
					varDoctor_p.Phone = item["Phone"].ToString();
					varDoctor_p.CliNo_ = item["CliNo_"].ToString();
					varDoctor_p.FacultyNo_ = item["FacultyNo_"].ToString();
					varDoctor_p.Name_fac = item["Name_fac"].ToString();
					varDoctor_p.Address = item["Address"].ToString();
					varDoctor_p.Picture = item["Picture"].ToString();
					varDoctor_p.sever = item["sever"].ToString();
					varDoctor_p.abc = varDoctor_p.sever + varDoctor_p.Picture;
					listDoctor_p.Add(varDoctor_p);
				}
				MylistDoctor.ItemsSource = listDoctor_p;

			}
			catch (InvalidCastException e)
			{
				throw e;
			}

		}

		public static string id_doctor;
		public static string name_doctorname_doctor;
		public static string id_doctor_cli;
		public static string id_doctor_fa;


		private void MylistDoctor_ItemTapped(object sender, ItemTappedEventArgs e)
		{
			var varDoctor = e.Item as DoctorModel;
			name_doctorname_doctor = varDoctor.Name;
			id_doctor = varDoctor.EmpNo_;
			id_doctor_cli = varDoctor.CliNo_;
			id_doctor_fa = varDoctor.FacultyNo_;
			Navigation.PushAsync(new BookView2());
		}
	}
}