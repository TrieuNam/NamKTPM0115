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
    public partial class MainDocctor : ContentPage
    {
        public MainDocctor()
        {
            InitializeComponent();
        }
        // click xem lich bs
        private void FindDoctor(object sender, EventArgs e)
        {
            Navigation.PushAsync(new DoctorViewsBook());
        }

        //Dang xuat
        private List<LoginUsersModels> pplList;
        private async void click_DangXuat_Clicked(object sender, EventArgs e)
        {
            pplList = await App.PersonRepo.GetAllPeopleAsync();
            foreach (LoginUsersModels dc in pplList)
            {

                await App.PersonRepo.DeleteToDo(dc);
                break;
            }
          //  navigationService.setLoginbs();
            App.Current.MainPage = new Login();
        }


    }
}