<?php include "../../header/header.php" ?>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../../css/demoo.css" />
	<link rel="stylesheet" type="text/css" href="../../css/component.css" />
	<link rel="stylesheet" type="text/css" href="../../css/organicfoodicons.css" />
	<script src="../../js/classie.js"></script>
	<script src="../../js/dummydata.js"></script>
	<script src="../../js/main.js"></script>
		<script src="../../js/modernizr-custom.js"></script>	


	
	
	
<script>	
function myFunction1() {
    var x = document.getElementById("myTopnav1");
    if (x.className === "Menu") {
        x.className += " responsive";
    } else {
        x.className = "Menu";
    }
}
 var trigger = $('.NoiDung1 .Menu  a'),
            container = $('#content');
        
        // Fire on click
        trigger.on('click', function(){
          // Set $this for re-use. Set target from data attribute
          var $this = $(this),
            target = $this.data('target');       
          
          // Load target page into container
          container.load(target + '.php');
          
          // Stop normal link behavior
          return false;
        });
      (function() {
		var menuEl = document.getElementById('ml-menu'),
			mlmenu = new MLMenu(menuEl, {
				// breadcrumbsCtrl : true, // show breadcrumbs
				// initialBreadcrumb : 'all', // initial breadcrumb text
				backCtrl : false, // show back button
				// itemsDelayInterval : 60, // delay between each menu item sliding animation
				onItemClick: loadDummyData // callback: item that doesn´t have a submenu gets clicked - onItemClick([event], [inner HTML of the clicked item])
			});
		// simulate grid content loading
		var gridWrapper = document.querySelector('.content');

		function loadDummyData(ev, itemName) {
			ev.preventDefault();

			
		}
	})();
</script>