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
	public partial class ItemView : ContentPage
	{
		ExecuteData ex = new ExecuteData();
		public List<ItemModel> menuList2 = new List<ItemModel>();
		public ItemView(string prono_)
		{
			InitializeComponent();
			ItemList.ItemsSource = menuList2;
			LoadData(prono_);
		}
		async Task LoadData(string prono_)
		{
			var abc = prono_;
			var json = await ex.getDataBFO(@"SELECT PresLine.[PrecLineNo_] as PrecLineNo_ , PresLine.[MedicineNo_] as MedicineNo_ , Item.[Name] as Name, Item.[Base Unit of Measure] as [Base_Unit_of_Measure], PresLine.[Dosage] as Dosage, PresLine.[Amount] as Amount , Item.Origination as Origination , Item.Pharmacodynamic as  Pharmacodynamic
		FROM [PresLine], [Pres], Item where Pres.ProNo_ = '" + prono_ + "' and PresLine.PrecLineNo_= '" + abc + "' and PresLine.[MedicineNo_]= Item.No_");

			foreach (var item in json)
			{
				ItemModel _cldh = new ItemModel();
				_cldh = new ItemModel();
				_cldh.PrecLineNo_ = item["PrecLineNo_"].ToString();
				_cldh.MedicineNo_ = item["MedicineNo_"].ToString();
				_cldh.Name = item["Name"].ToString();
				_cldh.Base_Unit_of_Measure = item["Base_Unit_of_Measure"].ToString();
				_cldh.Dosage = item["Dosage"].ToString();
				_cldh.Amount = item["Amount"].ToString();
				_cldh.Origination = item["Origination"].ToString();
				_cldh.Pharmacodynamic = item["Pharmacodynamic"].ToString();

				menuList2.Add(_cldh);
			}

		}
		private void ItemList_ItemTapped(object sender, ItemTappedEventArgs e)
		{
            // khong xai
		}
	}
}