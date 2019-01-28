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
	public partial class DisProLineView : ContentPage
	{
        
		ExecuteData ex = new ExecuteData();
		public List<DisProModelss> menuList2 = new List<DisProModelss>();
		public DisProLineView(string prono_, string cusNo_)
		{
			InitializeComponent();
			DisProList.ItemsSource = menuList2;
			LoadData(prono_, cusNo_);
		}
        // get du lieu
		async Task LoadData(string prono_, string cusNo_)
		{
			var abc = prono_;
			var json = await ex.getDataBFO(@"SELECT [ProLineNo_]	   
		    ,[DisProLine].[DisNo_] as DisNo_
		    ,[DisProLine].[Diagnose] as Diagnose
		    ,[DisProLine].[StaringDate] as StaringDate
		    ,[Duration]
		    ,[DisProLine].[Descrip] as Descrip
			,DisPro.CusNo_ as CusNo_
		FROM[DisProLine],DisPro where DisPro.ProNo_ = '" + prono_ + "' and DisProLine.ProLineNo_='" + abc + "' ");

			foreach (var item in json)
			{
				DisProModelss _cldh = new DisProModelss();
				_cldh = new DisProModelss();
				_cldh.ProLineNo_ = item["ProLineNo_"].ToString();
				_cldh.DisNo_ = item["DisNo_"].ToString();
				_cldh.Diagnose = item["Diagnose"].ToString();
				_cldh.StaringDate = DateTime.Parse(item["StaringDate"].ToString());
				_cldh.staringdate = _cldh.StaringDate.ToString("dd/MM/yyyy");
				_cldh.Duration = DateTime.Parse(item["Duration"].ToString());
				_cldh.duration = _cldh.Duration.ToString("dd/MM/yyyy");
				_cldh.Descrip = item["Descrip"].ToString();
				_cldh.CusNo_ = item["CusNo_"].ToString();
				menuList2.Add(_cldh);
			}
		}

		private void DisProList_ItemTapped(object sender, ItemTappedEventArgs e)
		{
			var item = e.Item as DisProModelss;
			var prono_ = item.ProLineNo_;
			var cusNo_ = item.CusNo_;
		//	Navigation.PushAsync(new Views.ItemView());
		}
	}
}