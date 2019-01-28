using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace CLINIC.Service
{
   public class NavigationService
    {
		public async Task Navigate(string pageName)
		{
			App.Master.IsPresented = false;
			switch (pageName)
			{
				case "LoginViews":
					{
						await App.Navigator.PushAsync(new Views.LoginViews());
						break;
					}

                case "SampleMapPage":
                    {
                        await App.Navigator.PushAsync(new SampleMapPage());
                        break;
                    }
                default:
					{
						break;
					}
			}

		}

		internal void setLogin()
		{
			App.Current.MainPage = new Views.MasterPageViews();
		}
        internal void setLoginbs()
        {
            App.Current.MainPage = new MasterPageViews2();
        }
    }
}
