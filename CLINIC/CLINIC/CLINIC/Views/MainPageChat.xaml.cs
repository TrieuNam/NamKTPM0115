using CLINIC.ViewsModels;
using Xamarin.Forms;

namespace CLINIC.Views
{
	public partial class MainPageChat : ContentPage
	{
		chatViewModels vm;
		public MainPageChat ()
		{
			InitializeComponent ();
			BindingContext = vm = new chatViewModels();
			vm.ListMessages.CollectionChanged += (sender, e) =>
			{
				var target = vm.ListMessages[vm.ListMessages.Count - 1];
				MessagesListView.ScrollTo(target, ScrollToPosition.End, true);
			};
		}
	}
}