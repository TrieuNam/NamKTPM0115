using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
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
    public partial class Webzalo : ContentPage
    {

        public Webzalo()
        {
            InitializeComponent();


            Zalo3rdAppInfo appInfo = new Zalo3rdAppInfo(2683610687619458655, "ONr32QXPrsW53C4eD5dA", "https://youruri.com");
            Zalo3rdAppClient appClient = new Zalo3rdAppClient(appInfo);
            string loginUrl = appClient.getLoginUrl();
            string code = "";
            JObject token = appClient.getAccessToken(code);

            //zaloweb.Source =;

        }



    }
}