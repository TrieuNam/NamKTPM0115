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
	public partial class MasterPageViews : MasterDetailPage
	{
	//	public static string Cus_NO;
		public MasterPageViews ()
		{
			InitializeComponent ();
			//cus_no = Views.Login.CCode;
		//	cus_no = Cus_NO;
		}
		protected override void OnAppearing()
		{
			base.OnAppearing();
			App.Master = this;
			App.Navigator = Navigator;
		}
	}
}