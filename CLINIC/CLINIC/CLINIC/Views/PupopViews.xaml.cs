using Rg.Plugins.Popup.Pages;
using Rg.Plugins.Popup.Services;
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
	public partial class PupopViews : PopupPage
	{
        // hien popu  cho trang DisProfile
        public static string cusNo;
		public static string proNo;
		public PupopViews (string prono, string cus_No)
		{
			InitializeComponent ();
			cusNo = cus_No;
			proNo = prono;
		}

		private async void Button_Clicked(object sender, EventArgs e)
		{
			await PopupNavigation.PopAllAsync();
		await	App.Navigator.PushAsync(new Views.DisProLineView(proNo, cusNo));
		}

		private async void Button_Clicked_1(object sender, EventArgs e)
		{
			await PopupNavigation.PopAllAsync();
			await App.Navigator.PushAsync(new Views.ItemView(proNo));
		}
	}
}