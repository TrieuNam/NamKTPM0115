using CLINIC.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CLINIC.Views
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class ViewClinic : ContentPage
	{
		public string IdCs;// ma benh nhan
		ExecuteData ex = new ExecuteData();
		Models.ClinicModel varClinic = new Models.ClinicModel();
		public List<Models.ClinicModel> listClinic = new List<Models.ClinicModel>();
		public ViewClinic()
		{
			//IdCs = BCNO_;
			InitializeComponent();
			Loadata();
		}
		public async Task Loadata()
		{
			string sql = @"SELECT [CliNo_]
                        ,[Name]
                        ,[Adr]
                         ,[Phone]
                        ,[Mail]
                        ,[Descrip]
                        FROM [Clinic]";
			try
			{
				var json = await ex.getDataBFO(sql);
				foreach (var item in json)
				{
					varClinic = new ClinicModel();
					varClinic.Name = item["Name"].ToString();
					varClinic.CliNo_ = item["CliNo_"].ToString();
					varClinic.Address = item["Adr"].ToString();
					varClinic.Phone = item["Phone"].ToString();
					varClinic.Email = item["Mail"].ToString();
					varClinic.Description = item["Descrip"].ToString();
					listClinic.Add(varClinic);
				}
				mylist.ItemsSource = listClinic;
			}
			catch (Exception ex)
			{
				await DisplayAlert("thong bao", ex.ToString(), "ÖK");
			}
		}
		public static string id_Clinic;

		private void listClinic_ItemTapped(object sender, ItemTappedEventArgs e)
		{

			var iclinic = e.Item as ClinicModel;
			id_Clinic = iclinic.CliNo_;
			var name_clinic = iclinic.Name;
			// ten phong kham, id phong kham, id benh nhan
			Navigation.PushAsync(new ViewDoctor());
		}
	}
}