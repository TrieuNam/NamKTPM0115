using System;
using System.Collections.Generic;
using System.Text;
using CLINIC.Models;
using Xamarin.Forms;

namespace CLINIC.Views
{
	class SelectorDataTemplate : DataTemplateSelector
	{
		private readonly DataTemplate textInDataTemplate;
		private readonly DataTemplate textOutDataTemplate;

		protected override DataTemplate OnSelectTemplate(object item, BindableObject container)
		{
			var messageVm = item as Message;
			if (messageVm == null)
				return null;
			return messageVm.IsTextIn ? this.textInDataTemplate : this.textOutDataTemplate;
		}
		public SelectorDataTemplate()
		{
			this.textInDataTemplate = new DataTemplate(typeof(ChatViews));
			this.textOutDataTemplate = new DataTemplate(typeof(chatOutViews));
		}
	}
}
