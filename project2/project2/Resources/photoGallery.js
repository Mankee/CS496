var userInterface = require('userInterface');

function storeImage(image) {
	var currentTime = new Date().getMilliseconds().toString();
 	var file = Ti.Filesystem.getFile(Ti.Filesystem.applicationDataDirectory, currentTime + ".png");
	if(!file.exists()){
    	file.write(image);
   }	  
   return file;
}

exports.openPhotoGallery = function(window) {
		Titanium.Media.openPhotoGallery({
		success:function(event) {
			// called when media returned from the camera
			Ti.API.debug('Our type was: '+event.mediaType);
			if(event.mediaType == Ti.Media.MEDIA_TYPE_PHOTO) {		
				imageFile = storeImage(event.media);
				var fullImageWindow = userInterface.createFullImageWindow(event.media, imageFile);
				fullImageWindow.addEventListener('savePhoto', function(e) {		
					database.saveEntry(e);
					net.sendToServer(e, database, event.media);
					fullImageWindow.close();
				});
				fullImageWindow.open();
			} else {
				alert("got the wrong type back ="+event.mediaType);
			}
		},
		cancel:function() {
			// called when user cancels taking a picture
		},
		error:function(error) {
			// called when there's an error
			var a = Titanium.UI.createAlertDialog({title:'Camera'});
			if (error.code == Titanium.Media.NO_CAMERA) {
				a.setMessage('Please run this test on device');
			} else {
				a.setMessage('Unexpected error: ' + error.code);
			}
			a.show();
		},
		saveToPhotoGallery:false,
		allowEditing:false,
		mediaTypes:[Ti.Media.MEDIA_TYPE_VIDEO,Ti.Media.MEDIA_TYPE_PHOTO]
	});
};