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
	public partial class MainViews : ContentPage
	{
		
		public MainViews ()
		{
			InitializeComponent ();
		
		}
		private void FindDoctor(object sender, EventArgs e)
		{
			Navigation.PushAsync(new ViewClinic());
		}

		private void FindPatient(object sender, EventArgs e)
		{

			Navigation.PushAsync(new DisProfile());
		}
		private List<LoginUsersModels> pplList;
		private async void click_DangXuat_Clicked(object sender, EventArgs e)
		{
			pplList = await App.PersonRepo.GetAllPeopleAsync();
			foreach (LoginUsersModels dc in pplList)
			{

				await App.PersonRepo.DeleteToDo(dc);
				break;
			}
            App.Current.MainPage = new Login();
           // await Navigation.PushAsync(new Login());
		}

		private void TapGestureRecognizer_Tapped(object sender, EventArgs e)
		{
			Navigation.PushAsync(new MainPageChat());
		}
	}
}