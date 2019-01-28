using CLINIC.Models;
using CLINIC.Service;
using CLINIC.ViewsModels;
using SQLite.Net.Interop;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

[assembly: XamlCompilation (XamlCompilationOptions.Compile)]
namespace CLINIC
{
	public partial class App : Application
	{
        public static double ScreenWidth;
        public static double ScreenHeight;
        public static NavigationPage Navigator { get; internal set; }
		public static MasterDetailPage Master { get; internal set; }
		public static PersonRepository PersonRepo { get; private set; }
		public NavigationService navigationService;
		LoginUsersModels loginabc = new LoginUsersModels();
		private List<LoginUsersModels> pplList;
		ExecuteData ex = new ExecuteData();
		public App (string dbPath, ISQLitePlatform sqlitePlatform)
		{
			InitializeComponent();
			//var cus_no = Views.Login.CCode;
			//MainPage = new Views.Login();
			PersonRepo = new PersonRepository(sqlitePlatform, dbPath);
			navigationService = new NavigationService();
			loade();
		}
		public string a;
		public string b;
		public string c;
        public string d;
        public string f;
        public static string abc;
        public static string Empo;
        async Task  loade()
		{
			pplList = await PersonRepo.GetAllPeopleAsync();

			if (pplList.Count > 0)
			{
				foreach (LoginUsersModels dc in pplList)
				{
					a = dc.Account;
					b = dc.PassWords;
					c = dc.CusNo_;
                    d = dc.EmpNo_;
                    f = dc.Role;

                }
                if (f == "0")
                {
                    foreach (LoginUsersModels dc in pplList)
                    {
                        abc = dc.CusNo_;
                        //MainPage = new NavigationPage(new Views.MasterPageViews());
                        navigationService.setLogin();
                        break;
                    }
                }
                else if (f == "1")
                {
                    foreach (LoginUsersModels dc in pplList)
                    {
                        Empo = dc.EmpNo_.ToString();
                        //MainPage = new NavigationPage(new Views.MasterPageViews());
                        navigationService.setLoginbs();
                        break;
                    }
                }


            }
            else
            {
                MainPage = new NavigationPage(new Views.Login());
            }
        }

		protected override void OnStart ()
		{
			// Handle when your app starts
		}

		protected override void OnSleep ()
		{
			// Handle when your app sleeps
		}

		protected override void OnResume ()
		{
			// Handle when your app resumes
		}
	}
}
