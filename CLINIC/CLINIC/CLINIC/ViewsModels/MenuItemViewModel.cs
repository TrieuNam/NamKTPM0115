using CLINIC.Service;
using GalaSoft.MvvmLight.Command;
using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Input;

namespace CLINIC.ViewsModels
{
   public class MenuItemViewModel
    {
		#region Attributes
		private NavigationService navigationService;
		#endregion

		#region Contructors
		public MenuItemViewModel()
		{
			navigationService = new NavigationService();
		}
		#endregion
		#region Properties
		public string Icon { get; set; }
		public string Title { get; set; }
		public string PageName { get; set; }
		#endregion

		#region Command
		public ICommand NavigateCommand { get { return new RelayCommand(Navigate); } }

		private async void Navigate()
		{
			await navigationService.Navigate(PageName);
		}
		#endregion
	}
}
