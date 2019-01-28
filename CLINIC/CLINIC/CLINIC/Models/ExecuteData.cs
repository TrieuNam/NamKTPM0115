using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms.Maps;

namespace CLINIC.Models
{
	class ExecuteData
	{
#if __ANDROID__
		public static CLINIC.Droid.BFO_Clinic.WebServiceBFO clinic = new CLINIC.Droid.BFO_Clinic.WebServiceBFO();
#elif __IOS__
        public static CLINIC.iOS.BFO_Clinic.WebServiceBFO clinic = new CLINIC.iOS.BFO_Clinic.WebServiceBFO();
#endif
		public async Task<JArray> getDataBFO(string strSQL)
		{
			var results = clinic.getDataTable(strSQL);
			var jsonObj = JObject.Parse(results);
			JArray arr = (JArray)jsonObj["Table"];
			return arr;
		}
		public async Task<int> setDataBFO(string strSQL)
		{
			var results = clinic.setDataTable(strSQL);
			return results;
		}
        public async Task<bool> setDataBFO2(string strSQL)
        {
            var results = clinic.setDataTable(strSQL);
            return true;
        }

        //get pin marker
        public async Task<List<Pin>> getPin()
        {
            string sql = @"select Name, Adr, Latitude, Longitude   
                            from Clinic";
            List<Pin> list_pin_p = new List<Pin>();
            Pin pin_p = new Pin();
            try
            {
                JArray arr = await getDataBFO(sql);
                foreach (var i in arr)
                {
                    pin_p = new Pin();
                    double la = Convert.ToDouble(i["Latitude"]);
                    double lo = Convert.ToDouble(i["Longitude"]);

                    pin_p.Address = i["Adr"].ToString();
                    pin_p.Label = i["Name"].ToString();
                    pin_p.Type = PinType.Place;
                    pin_p.Position = new Position(la, lo);

                    list_pin_p.Add(pin_p);
                }
                return list_pin_p;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}
