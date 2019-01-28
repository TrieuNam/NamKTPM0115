using CLINIC.Models;
using SQLite.Net;
using SQLite.Net.Async;
using SQLite.Net.Interop;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace CLINIC.ViewsModels
{
	public class PersonRepository
	{
		private SQLiteAsyncConnection dbConn;

		public string StatusMessage { get; set; }
		public PersonRepository(ISQLitePlatform sqlitePlatform, string dbPath)
		{
			//initialize a new SQLiteConnection 
			if (dbConn == null)
			{
				var connectionFunc = new Func<SQLiteConnectionWithLock>(() =>
					new SQLiteConnectionWithLock
					(
						sqlitePlatform,
						new SQLiteConnectionString(dbPath, storeDateTimeAsTicks: false)
					));

				dbConn = new SQLiteAsyncConnection(connectionFunc);
				dbConn.CreateTableAsync<LoginUsersModels>();
			}

		}
		public async Task AddNewPersonAsync(string name, string password,string cusno, string empno, string role)
		{
			int result = 0;
			try
			{
				//basic validation to ensure a name was entered
				if (string.IsNullOrEmpty(name))
					throw new Exception("Valid name required");

				//insert a new person into the Person table
				result = await dbConn.InsertAsync(new LoginUsersModels { Account = name, PassWords = password,CusNo_=cusno, EmpNo_=empno, Role=role });
				StatusMessage = string.Format("{0} record(s) added [Name: {1})", result, name);
			}
			catch (Exception ex)
			{
				StatusMessage = string.Format("Failed to add {0}. Error: {1}", name, ex.Message);
			}
		}
		public async Task<List<LoginUsersModels>> GetAllPeopleAsync()
		{
			//return a list of people saved to the Person table in the database
			List<LoginUsersModels> people = await dbConn.Table<LoginUsersModels>().ToListAsync();
			return people;
		}
		public async Task<int> DeleteToDo(LoginUsersModels item)
		{
			var result = await dbConn.DeleteAsync(item);
			return result;
		}


	}
}
