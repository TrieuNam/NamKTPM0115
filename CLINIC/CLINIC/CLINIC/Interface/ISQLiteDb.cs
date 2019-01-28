using SQLite.Net;
using System;
using System.Collections.Generic;
using System.Text;

namespace CLINIC.Interface
{
	public interface ISQLiteDb
	{
		SQLiteConnection CreateConnection();
	}
}
