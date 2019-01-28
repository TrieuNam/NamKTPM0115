using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Text;

namespace CLINIC.ViewsModels
{
   public class MainViewModel
    {
		#region Properties
		public ObservableCollection<MenuItemViewModel> Menu { get; set; }

		#endregion

		#region Constructons
		public MainViewModel()
		{
			Menu = new ObservableCollection<MenuItemViewModel>();

			LoadMenu();
		}

		#endregion
		#region Methods
		private void LoadMenu()
		{
			Menu.Add(new MenuItemViewModel
			{
				Icon = "ic_Profile.png",
				PageName = "LoginViews",
				Title = "Login",
			});
            Menu.Add(new MenuItemViewModel
            {
                Icon = "ic_Profile.png",
                PageName = "SampleMapPage",
                Title = "Location Clinic",
            });
        }
		#endregion
	}
}
