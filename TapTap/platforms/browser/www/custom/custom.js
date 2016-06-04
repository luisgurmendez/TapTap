//All ready!. Page &  Cordova loaded => $(document).ready && cordova "deviceready"
//Todo listo!. Página & Cordova cargados => $(document).ready && cordova "deviceready"
function deviceReady() {
	try {
		//Typically fired when the device changes orientation.
		//Típicamente disparado cuando el dispositivo cambia de orientación.
		$(window).resize(function() {
			//somthing
			mui.viewport.iScrollRefresh();
		});
	
		//Sample when Internet connection is needed but not mandatory
		//Ejemplo de cuando se necesita conexióna a Internet pero no es obligatoria.
		if (!mui.connectionAvailable()){
			if ("plugins" in window && "toast" in window.plugins)
				window.plugins.toast.showLongCenter("We recommend you connect your device to the Internet");
			else
				mui.alert("We recommend you connect your device to the Internet");
		}
		
		installEvents();
		
		//Hide splash.
		//Ocultar el splash.
		if (navigator.splashscreen) {
			setTimeout(function() {	//setTimeout is optional. SetTimeout es opcional.
				navigator.splashscreen.hide();
			}, 2000);
		}
		

	} catch (e) {
		//your decision
		//tu decisión
	}
}

function installEvents() {

	//It's a good idea to consider what happens when the device is switched on and off the internet.
	//Es buena idea considerar que pasa cuando el dispositivo se conecta y desconecta a Internet.
	document.addEventListener("online", function() {
		//somthing
	}, false);
	
	//Back button.
	$(".mui-backarrow").click(function() {
		mui.history.back();
		return false;
	});
	
	//Open menu.
	$(".mui-headmenu").click(function() {
		mui.screen.showPanel("menu-panel", "SLIDE_LEFT");	//ATTENTION!!! mui.screen instead of mui.viewport
		return false;
	});

	
	
}

/**
 * Courtesy: Open an url using InAppBrowser plugin.
 * Cortesía: Abre una url usando InAppBrowser plugin.
 * @param url
 */
function openInAppBrowser(url) { 
	window.open(encodeURI(url), "_blank", "location=yes,closebuttoncaption=Volver,presentationstyle=pagesheet,transitionstyle='fliphorizontal',EnableViewPortScale=yes");
}




