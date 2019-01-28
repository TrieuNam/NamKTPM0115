using CLINIC.Models;
using System.Collections.Generic;
using Xamarin.Forms;
using Xamarin.Forms.Maps;

namespace CLINIC
{
    public partial class SampleMapPage : ContentPage
    {
        public SampleMapPage()
        {
            InitializeComponent();
            //var position1 = new Position(10.787639, 106.705060); // thao cam vien 1
            //var position2 = new Position(10.786419, 106.703189);// trường đại học 2
            //var position3 = new Position(10.788120, 106.701498);// sân vận động hoa lu 3
            //var position4 = new Position(10.786754, 106.702140);// dai truyền hình 4
            //var position5 = new Position(10.789451, 106.700433);// nhà hàng nón lá 5
            //var position6 = new Position(10.791254, 106.700942);// Ân Nam quá 6

            //var pin1 = new Pin
            //{
            //    Type = PinType.Place,
            //    Position = position1,
            //    Label = "Thảo Cầm Viên Sài Gòn",
            //    Address = "2 Nguyen Binh Khiem, Ben Nghe, Quan 1",
            //};
            //var pin2 = new Pin
            //{
            //    Type = PinType.Place,
            //    Position = position2,
            //    Label = "Trường Đại học Khoa học Xã hội và Nhân văn ",
            //    Address = "10-12 Đinh Tiên Hoàng, Bến Nghé"
            //};
            //var pin3 = new Pin
            //{
            //    Type = PinType.Place,
            //    Position = position3,
            //    Label = "Sân vận động Hoa Lư",
            //    Address = "2 Đinh Tiên Hoàng, Đa Kao, Quận 1"
            //};
            //var pin4 = new Pin
            //{
            //    Type = PinType.Place,
            //    Position = position4,
            //    Label = "Đài truyền hình Thành phố Hồ Chí Minh",
            //    Address = "14 Đinh Tiên Hoàng, Bến Nghé, Quận 1"
            //};
            //var pin5 = new Pin
            //{
            //    Type = PinType.Place,
            //    Position = position5,
            //    Label = "Nhà Hàng Nón Lá",
            //    Address = "19 Nguyễn Đình Chiểu, Đa Kao, Quận 1"
            //};
            //var pin6 = new Pin
            //{
            //    Type = PinType.Place,
            //    Position = position6,
            //    Label = "Ân Nam Quán",
            //    Address = "59 Nguyễn Bỉnh Khiêm, Đa Kao, Quận 1"
            //};

            //map.Pins.Add(pin2);
            //map.Pins.Add(pin3);
            //map.Pins.Add(pin4);
            //map.Pins.Add(pin5);
            //map.Pins.Add(pin6);

            //Content = map;
            loaddb();
        }
        async void loaddb()
        {
            var map = new Map(MapSpan.FromCenterAndRadius(
               new Position(10.787639, 106.705060),
               Distance.FromMiles(0.5)))
            {
                IsShowingUser = true,
                VerticalOptions = LayoutOptions.FillAndExpand
            };


            ExecuteData executeData = new ExecuteData();
            //get List<Pin> form BFO
            List<Pin> list_pin_p = await executeData.getPin();
            foreach (var pin_t in list_pin_p)
            {
                map.Pins.Add(pin_t);

            }
            Content = map;
        }
    }
}