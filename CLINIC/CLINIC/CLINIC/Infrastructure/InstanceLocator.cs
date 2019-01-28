using CLINIC.ViewsModels;
using System;
using System.Collections.Generic;
using System.Text;

namespace CLINIC.Infrastructure
{
	public class InstanceLocator
	{
		public MainViewModel Main { get; set; }

		public InstanceLocator()
		{
			Main = new MainViewModel();
		}

	}
}
